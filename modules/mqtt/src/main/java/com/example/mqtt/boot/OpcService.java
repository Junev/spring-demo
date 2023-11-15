package com.example.mqtt.boot;

import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.mapper.PrdTaskCpMapper;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.PdsEquippropertyExample;
import com.example.repository.model.PrdTaskCp;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.UaRuntimeException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OpcService {
    @Autowired
    private UaClient client;

    @Autowired
    private PdsEquippropertyMapper equipPropertyMapper;

    @Autowired
    private PrdTaskCpMapper cpMapper;

    void subscribeNode() throws UaException {
        ManagedSubscription managedSubscription = ManagedSubscription.create((OpcUaClient) client);
        managedSubscription.addStatusListener(new MyOpcStatusListener(this));

        List<PdsEquipproperty> batchStartPdsEquipProperties = getPdsEquipProperties("%cpc0033");
        // 各段批开始信号地址List
        List<String> batchStartAddresses = batchStartPdsEquipProperties.stream()
                .map(PdsEquipproperty::getTagaddress)
                .collect(Collectors.toList());

        // 数采点地址反向查找数采点属性的MAP
        Map<String, PdsEquipproperty> batchStartMap = batchStartPdsEquipProperties.stream()
                .collect(Collectors.toMap(PdsEquipproperty::getTagaddress, d -> d));


        List<PdsEquipproperty> pdsEquipProperties = getPdsEquipProperties("%cpc%", "%0em%");
        Map<String, List<PdsEquipproperty>> cpcGroupMap = pdsEquipProperties.stream()
                .collect(Collectors.groupingBy(PdsEquipproperty::getEquipmentid));
        // 设备分组下，产生该分组cp地址列表
        Map<String, List<NodeId>> cpcNodeIdGroupMap = pdsEquipProperties.stream()
                .collect(Collectors.groupingBy(PdsEquipproperty::getEquipmentid,
                        Collectors.mapping(c -> {
                                    NodeId d = null;
                                    try {
                                        d = NodeId.parse(c.getTagaddress());
                                    } catch (UaRuntimeException e) {
                                        System.out.println("Bad tag address :" + c.getPropertyid());
                                    }
                                    return d;
                                },
                                Collectors.toList())));

//        List<NodeId> nodeIds = extractNodeIds(addresses);
        List<NodeId> nodeIds = batchStartAddresses.stream()
                .map(NodeId::parse)
                .collect(Collectors.toList());

        List<ManagedDataItem> dataItemList = managedSubscription.createDataItems(nodeIds);
        for (ManagedDataItem d : dataItemList) {
            d.addDataValueListener(t -> {
                String parseableAddr = d.getNodeId()
                        .toParseableString();
                Object value = t.getValue().getValue();
                System.out.println(
                        "\n----------------------------------------------------");
                System.out.println("批开始信号：" + parseableAddr + ": " + value);

                if (value.equals(true)) {
                    String equipmentId = batchStartMap.get(parseableAddr).getEquipmentid();
                    try {
                        final List<DataValue> dataValues = client.readValues(0.0,
                                TimestampsToReturn.Neither,
                                cpcNodeIdGroupMap.get(equipmentId)).get();
                        List<Object> dvs = dataValues.stream()
                                .map(c -> c.getValue().getValue())
                                .collect(Collectors.toList());
                        List<PdsEquipproperty> groupProperties = cpcGroupMap.get(equipmentId);

                        savePrdTaskCp(equipmentId, groupProperties, dvs, t.getSourceTime());

                        for (int i = 0; i < groupProperties.size(); i++) {
                            PdsEquipproperty p = groupProperties.get(i);
                            Object value1 = dataValues.get(i).getValue().getValue();

                            if (value1 != null) {
                                p.setDescription(value1.toString());
                                System.out.println(p.getPropertyname() + ": " + p.getDescription());
                            }
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        client.connect();
                    }
                }
            });
        }
    }

    private void savePrdTaskCp(String equipmentId, List<PdsEquipproperty> groupProperties,
                               List<Object> dvs,
                               DateTime sourceTime) {
        assert dvs.size() == 36 : "CP 地址配置少于36";
        PrdTaskCp taskCps = new PrdTaskCp();
        taskCps.setEquipmentId(String.valueOf(equipmentId));
        taskCps.setTaskId(String.valueOf(dvs.get(33)));
        taskCps.setBatchId(String.valueOf(dvs.get(34)));
        taskCps.setBrandId(String.valueOf(dvs.get(35)));
        taskCps.setTaskStartTime(sourceTime.getJavaDate());
        taskCps.setSource(String.valueOf(dvs.get(0)));
        taskCps.setDestination(String.valueOf(dvs.get(1)));
        taskCps.setLoadType(String.valueOf(dvs.get(2)));
        taskCps.setPathCode(String.valueOf(dvs.get(3)));
        taskCps.setCp5(String.valueOf(dvs.get(4)));
        taskCps.setCp6(String.valueOf(dvs.get(5)));
        taskCps.setCp7(String.valueOf(dvs.get(6)));

        StringBuilder silo = concatCp7toCp31(dvs);
        taskCps.setSilo(silo.toString());
        taskCps.setCp8(String.valueOf(dvs.get(7)));
        taskCps.setCp9(String.valueOf(dvs.get(8)));
        taskCps.setCp10(String.valueOf(dvs.get(9)));
        taskCps.setCp11(String.valueOf(dvs.get(10)));
        taskCps.setCp12(String.valueOf(dvs.get(11)));
        taskCps.setCp13(String.valueOf(dvs.get(12)));
        taskCps.setCp14(String.valueOf(dvs.get(13)));
        taskCps.setCp15(String.valueOf(dvs.get(14)));
        taskCps.setCp16(String.valueOf(dvs.get(15)));
        taskCps.setCp17(String.valueOf(dvs.get(16)));
        taskCps.setCp18(String.valueOf(dvs.get(17)));
        taskCps.setCp19(String.valueOf(dvs.get(18)));
        taskCps.setCp20(String.valueOf(dvs.get(19)));
        taskCps.setCp21(String.valueOf(dvs.get(20)));
        taskCps.setCp22(String.valueOf(dvs.get(21)));
        taskCps.setCp23(String.valueOf(dvs.get(22)));
        taskCps.setCp24(String.valueOf(dvs.get(23)));
        taskCps.setCp25(String.valueOf(dvs.get(24)));
        taskCps.setCp26(String.valueOf(dvs.get(25)));
        taskCps.setCp27(String.valueOf(dvs.get(26)));
        taskCps.setCp28(String.valueOf(dvs.get(27)));
        taskCps.setCp29(String.valueOf(dvs.get(28)));
        taskCps.setCp30(String.valueOf(dvs.get(29)));
        taskCps.setCp31(String.valueOf(dvs.get(30)));
        taskCps.setCp32(String.valueOf(dvs.get(31)));

        String tankString = "";
        for (int i = 36; i < dvs.size(); i++) {
            if (dvs.get(i).equals(true)) {
                tankString = tankString.concat(groupProperties.get(i).getPropertyname());
                tankString = tankString.concat(",");
            }
        }
        if (tankString.length() > 1) {
            tankString = tankString.substring(0, tankString.length() - 1);
        }
        taskCps.setTank(tankString);
        int res = cpMapper.insertSelective(taskCps);
    }

    private StringBuilder concatCp7toCp31(List<Object> dvs) {
        StringBuilder silo = new StringBuilder();
        for (int i = 7; i < 31; i++) {
            if ((float) dvs.get(i) != 0) {
                silo.append(dvs.get(i));
                silo.append(',');
            }
        }

        if (silo.length() != 0) {
            silo.deleteCharAt(silo.length() - 1);
        }
        return silo;
    }

    private List<PdsEquipproperty> getPdsEquipProperties(String s) {
        return this.getPdsEquipProperties(s, null);
    }

    private List<PdsEquipproperty> getPdsEquipProperties(String s, String s2) {
        PdsEquippropertyExample batchStartEx = new PdsEquippropertyExample();
        batchStartEx.createCriteria().andPropertyidLike(s);
        if (s2 != null) {
            batchStartEx.or().andPropertyidLike(s2);
        }
        batchStartEx.setOrderByClause("PROPERTYID asc");
        return equipPropertyMapper.selectByExample(
                batchStartEx);
    }

    private List<NodeId> extractNodeIds(List<String> addresses) {
        String pattern = "^ns=(\\d);s=(.+)";
        Pattern p = Pattern.compile(pattern);
        List<NodeId> nodeIds = new ArrayList<>();
        for (String a : addresses) {
            Matcher matcher = p.matcher(a);
            boolean b = matcher.find();
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            int c = Integer.valueOf(g1);
            nodeIds.add(new NodeId(Integer.valueOf(g1), g2));
        }
        return nodeIds;
    }
}
