package com.example.mytask.service.preHeatRunTime;

import com.example.mytask.opcua.MyOpcUaClient;
import com.example.repository.mapper.AppConfigItemsMapper;
import com.example.repository.mapper.EmsPreheatRunTimeMapper;
import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.model.*;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.mytask.utils.PdsEquipproptyUtils.getNodeIds;

@Component
public class RecordPreHeatTimeJob {
    private static final Logger logger
            = LoggerFactory.getLogger(RecordPreHeatTimeJob.class);
    @Autowired
    MyOpcUaClient myOpcUaclient;
    
    @Autowired
    private AppConfigItemsMapper appConfigItemsMapper;

    @Autowired
    private PdsEquippropertyMapper pdsEquippropertyMapper;
    
    @Autowired
    private EmsPreheatRunTimeMapper emsPreheatRunTimeMapper;

    @Scheduled(cron = "0 45 23 * * *")
    public void run() {
        UaClient opcUaClient = myOpcUaclient.connect();

        AppConfigItemsExample appItemEx = new AppConfigItemsExample();
        appItemEx.createCriteria().andParentcfgEqualTo("record_everyday");
        List<AppConfigItems> appConfigItems = appConfigItemsMapper.selectByExample(appItemEx);
        List<String> pids = appConfigItems.stream().map(AppConfigItems::getPropertyvalue).collect(Collectors.toList());

        PdsEquippropertyExample peEx = new PdsEquippropertyExample();
        peEx.createCriteria().andPropertyidIn(pids);
        List<PdsEquipproperty> pdsEquipproperties = pdsEquippropertyMapper.selectByExample(peEx);
        List<NodeId> nodeIds = getNodeIds(pdsEquipproperties);

        List<DataValue> dataValues = null;
        try {
            dataValues = opcUaClient.readValues(0,
                    TimestampsToReturn.Neither,
                    nodeIds).get();
        } catch (Exception e) {
            logger.error("Opcua 读取值异常" + e.getStackTrace()[0]);
        }
        List<Object> dvs = dataValues.stream()
                .map(c -> c.getValue().getValue())
                .collect(Collectors.toList());

        EmsPreheatRunTime newRecord = getEmsPreheatRunTime(dvs);
        emsPreheatRunTimeMapper.insert(newRecord);
        logger.info("完成 EMS_PREHEAT_RUN_TIME 记录");
    }

    private static EmsPreheatRunTime getEmsPreheatRunTime(List<Object> dvs) {
        EmsPreheatRunTime newRecord = new EmsPreheatRunTime();
        Date date = new Date();
        newRecord.setDate(date);
        newRecord.setRecordDate(date);
        newRecord.setRecordEverydayP1(Long.valueOf((Short)dvs.get(0)));
        newRecord.setRecordEverydayP2(Long.valueOf((Short)dvs.get(1)));
        newRecord.setRecordEverydayP3((long) Math.round((float) dvs.get(2)));
        newRecord.setRecordEverydayP4((long) Math.round((float) dvs.get(3)));
        newRecord.setRecordEverydayP5((long) Math.round((float) dvs.get(4)));
        newRecord.setRecordEverydayP6((long) Math.round((float) dvs.get(5)));
        newRecord.setRecordEverydayP7((long) Math.round((float) dvs.get(6)));
        newRecord.setRecordEverydayP8((long) Math.round((float) dvs.get(7)));
        return newRecord;
    }
}
