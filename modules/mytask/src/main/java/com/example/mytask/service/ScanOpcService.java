package com.example.mytask.service;

import com.example.mytask.opcua.MyOpcUaClient;
import com.example.mytask.service.archiveEquipmentTime.TimeByConditionListener;
import com.example.mytask.service.archiveSILO.OpcValueChangeListener;
import com.example.mytask.service.caculate.CaculateAbstractListener;
import com.example.repository.mapper.PdsEquipelementMapper;
import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.mapper.PrdUnitcmdMapper;
import com.example.repository.model.PdsEquipelement;
import com.example.repository.model.PdsEquipelementExample;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.PdsEquippropertyExample;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mytask.utils.PdsEquipproptyUtils.getNodeIds;

@Component
@ConditionalOnProperty(name = "feature.scanOpc.enabled", havingValue = "true")
public class ScanOpcService {
    private static final Logger logger
            = LoggerFactory.getLogger(ScanOpcService.class);

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

    @Autowired
    private CaculateAbstractListener listener3;

    // 分段初始化
    HashMap<String, List<PdsEquipproperty>> unitMap = new HashMap<>();
    HashMap<String, List<PdsEquipproperty>> siloMap = new HashMap<>();

    public void run() {
        UaClient opcUaClient = myUaClient.connect();
//        UaClient opcUaClient = null;

        OpcValueSubject opcSubject = new OpcValueSubject();
//        opcSubject.addListener(listener1);
        opcSubject.addListener(listener2);

//        listener3.init();
//        opcSubject.addListener(listener3);


        init(unitMap, siloMap);

        List<PdsEquipproperty> allEps = getAllEps();
        List<NodeId> allUnitNodeIds = getNodeIds(allEps);

//        List<PdsEquipproperty> allSiloEps = getEps(siloMap);
//        List<NodeId> allSiloNodeIds = getNodeIds(allSiloEps);


//        List<PdsEquipproperty> allEps = getEquipPropertiesByPrefix("6150005203%");
//        List<NodeId> allNodeIds = getNodeIds(allEps);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);


        List<NodeId> tagNodeIds = getNodeIds(allEps);

        Runnable unitTask = getRunnable(opcUaClient, opcSubject, tagNodeIds, allEps);
        scheduler.scheduleAtFixedRate(unitTask, 0, 1, TimeUnit.SECONDS);;

    }

    private static Runnable getRunnable(UaClient opcUaClient,
                                        OpcValueSubject opcSubject,
                                        List<NodeId> allNodeIds,
                                        List<PdsEquipproperty> eps) {
        Runnable scanOnce = () -> {
            try {
                if (allNodeIds.isEmpty()) {
                    return;
                }
                List<DataValue> dataValues = opcUaClient.readValues(0,
                        TimestampsToReturn.Neither,
                        allNodeIds).get();
                List<Object> dvs = dataValues.stream()
                        .map(c -> c.getValue().getValue())
                        .collect(Collectors.toList());
//                logger.info(unitId + " read opcua "  + dvs);
//                if (unitId.startsWith("6150005203ZSC02")) {
//                    System.out.println("dvs = " + dvs);
//                }
                Map<String, PdsEquipproperty> epsValue = IntStream.range(0, eps.size())
                            .mapToObj(c -> {
                                PdsEquipproperty p = new PdsEquipproperty();
                                PdsEquipproperty op = eps.get(c);
//                                BeanUtils.copyProperties(eps.get(c), p);
//                                p.setEquipmentid(op.getEquipmentid());
                                p.setPropertyid(op.getPropertyid());
//                                p.setTagaddress(op.getTagaddress());
                                p.setValue(dataValues.get(c).getValue().getValue());
                                return p;
                            })
                            .collect(Collectors.toMap(PdsEquipproperty::getPropertyid, t -> t,
                                    (v1, v2) -> v2));
//                System.out.println("epsValue = " + epsValue);
                opcSubject.notify(epsValue);
            } catch (Exception e) {
                logger.error("scan opc error: ",e);
//                    e.printStackTrace();
            }
        };

        return scanOnce;
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

        rootEqe.forEach(this::getPdsEquipelementProperties);

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

    private void getPdsEquipelementProperties(PdsEquipelement c) {
        List<PdsEquipproperty> epps = getEquipPropertiesByParentID(
                c.getEquipmentid());
        c.setTags(epps);

        if (c.getEeLevel() == 4) {
            unitMap.put(c.getEquipmentid(), epps);
        }

        if (c.getEeLevel() == 5 && c.getEquipmentname().contains("柜")) {
            siloMap.put(c.getEquipmentid(), epps);
        }

        List<PdsEquipelement> children = c.getChildren();
        if (!children.isEmpty()) {
            children.forEach(this::getPdsEquipelementProperties);
        }
    }
}
