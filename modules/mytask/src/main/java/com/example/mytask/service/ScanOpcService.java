package com.example.mytask.service;

import com.example.mytask.opcua.MyOpcUaClient;
import com.example.mytask.service.archiveEquipmentTime.TimeByConditionListener;
import com.example.mytask.service.archiveSILO.OpcValueChangeListener;
import com.example.repository.mapper.PdsEquipelementMapper;
import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.mapper.PrdUnitcmdMapper;
import com.example.repository.model.PdsEquipelement;
import com.example.repository.model.PdsEquipelementExample;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.PdsEquippropertyExample;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.UaRuntimeException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ScanOpcService {

    @Autowired
    private MyOpcUaClient myUaClient;

    @Autowired
    private PdsEquipelementMapper pdsEqeMapper;

    @Autowired
    private PdsEquippropertyMapper pdsEqMapper;

    @Autowired
    private PrdUnitcmdMapper unitcmdMapper;

    @Autowired
    private OpcValueChangeListener listener1;

    @Autowired
    private TimeByConditionListener listener2;

    public void run() {
        UaClient opcUaClient = myUaClient.connect();

        OpcValueSubject opcSubject = new OpcValueSubject();
//        opcSubject.addListener(listener1);
//        opcSubject.addListener(listener2);

        /* 分段初始化
        HashMap<String, List<PdsEquipproperty>> unitMap = new HashMap<>();
        HashMap<String, List<PdsEquipproperty>> siloMap = new HashMap<>();

        init(unitMap, siloMap);

        List<PdsEquipproperty> allUnitEps = getEps(unitMap);
        List<NodeId> allUnitNodeIds = getNodeIds(allUnitEps);

        List<PdsEquipproperty> allSiloEps = getEps(siloMap);
        List<NodeId> allSiloNodeIds = getNodeIds(allSiloEps);
         */

        List<PdsEquipproperty> allEps = getEquipPropertiesByPrefix("6150005203%");
        List<NodeId> allNodeIds = getNodeIds(allEps);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                List<DataValue> dataValues = opcUaClient.readValues(0,
                        TimestampsToReturn.Neither,
                        allNodeIds).get();
                List<Object> dvs = dataValues.stream()
                        .map(c -> c.getValue().getValue())
                        .collect(Collectors.toList());
                System.out.println(LocalDateTime.now().toString() + " read opcua"  + dvs);
                Map<String, PdsEquipproperty> epsValue = IntStream.range(0, allEps.size())
                        .mapToObj(c -> {
                            PdsEquipproperty p = new PdsEquipproperty();
                            BeanUtils.copyProperties(allEps.get(c), p);
                            p.setValue(dataValues.get(c).getValue().getValue());
                            return p;
                        })
                        .collect(Collectors.toMap(PdsEquipproperty::getPropertyid, t -> t,
                                (v1, v2) -> v2));
//                System.out.println("epsValue = " + epsValue);
                opcSubject.notify(epsValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }

    private List<NodeId> getNodeIds(List<PdsEquipproperty> allUnitEps) {
        List<NodeId> allUnitNodeIds = allUnitEps.stream()
                .map(c -> {
                    try {
                        return NodeId.parse(c.getTagaddress());
                    } catch (UaRuntimeException e) {
                        System.out.println("Tag Address parse Exception: " + c.getPropertyid() +
                                "_" + c.getTagaddress());
//                        e.printStackTrace();
                        return NodeId.parseOrNull(c.getTagaddress());
                    } catch (NullPointerException e) {
                        System.out.println("Null tag Address: " + c.getPropertyid());
                        return null;
                    }
                })
                .collect(Collectors.toList());
        return allUnitNodeIds;
    }

    private List<PdsEquipproperty> getAllEps() {
        PdsEquippropertyExample ex = new PdsEquippropertyExample();
        ex.createCriteria().andPropertyidIsNotNull();
        return pdsEqMapper.selectByExample(ex);
    }

    private List<PdsEquipproperty> getEps(HashMap<String, List<PdsEquipproperty>> unitMap) {
        List<PdsEquipproperty> allUnitEps = new ArrayList<>();
        unitMap.values().forEach(allUnitEps::addAll);
        return allUnitEps;
    }

    private void init(HashMap<String, List<PdsEquipproperty>> unitMap, HashMap<String,
            List<PdsEquipproperty>> siloMap) {
        PdsEquipelementExample pdsEqeEx = new PdsEquipelementExample();
        pdsEqeEx.createCriteria().andParentidIsNull();
        List<PdsEquipelement> rootEqe = pdsEqeMapper.selectByExample(pdsEqeEx);
        rootEqe.forEach(this::getPdsEquipelementChildren);

        rootEqe.forEach(c2 -> {
            // c2第二层
            c2.getChildren().forEach(c3 -> {
                        c3.getChildren().forEach(c4 -> {
                            c4.getChildren().forEach(c5 -> {
//                                System.out.println("c5 = " + c5.getEquipmentname());
                                List<PdsEquipproperty> eps =
                                        getEquipPropertiesByParentID(
                                                c5.getEquipmentid());
                                unitMap.put(c5.getEquipmentid(), eps);

                                c5.getChildren().forEach(c6 -> {
                                    if (c6.getEquipmentname().contains("柜")) {
//                                        System.out.println("c6 = " + c6.getEquipmentname());
                                        List<PdsEquipproperty> eps2 =
                                                getEquipPropertiesByParentID(c6.getEquipmentid());
                                        siloMap.put(c6.getEquipmentid(), eps2);
                                    }
                                });
                            });
                        });
                    }
            );
        });
    }

    private List<PdsEquipproperty> getEquipPropertiesByParentID(String parentId) {
        PdsEquippropertyExample ex1 = new PdsEquippropertyExample();
        ex1.createCriteria().andEquipmentidEqualTo(parentId);
        return pdsEqMapper.selectByExample(ex1);
    }

    private List<PdsEquipproperty> getEquipPropertiesByPrefix(String equipmentPrefix) {
        PdsEquippropertyExample ex1 = new PdsEquippropertyExample();
        ex1.createCriteria().andEquipmentidLike(equipmentPrefix  + "%");
        return pdsEqMapper.selectByExample(ex1);
    }

    private void getPdsEquipelementChildren(PdsEquipelement c) {
        PdsEquipelementExample pdsEqeEx1 = new PdsEquipelementExample();
        pdsEqeEx1.createCriteria().andParentidEqualTo(c.getEquipmentid());
        List<PdsEquipelement> pdsEquipelements1 = pdsEqeMapper.selectByExample(pdsEqeEx1);
        c.setChildren(pdsEquipelements1);
        if (!pdsEquipelements1.isEmpty()) {
            pdsEquipelements1.forEach(this::getPdsEquipelementChildren);
        }
    }
}
