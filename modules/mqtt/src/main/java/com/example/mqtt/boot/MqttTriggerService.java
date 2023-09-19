package com.example.mqtt.boot;

import com.example.repository.mapper.PdsMqtttopicTriggerMapper;
import com.example.repository.model.PdsMqtttopicTrigger;
import com.example.repository.model.PdsMqtttopicTriggerExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用于配置烟包BOM触发器，在PDS_MQTTTRIGGER表里插入新记录。
 */
@Service
@Transactional
public class MqttTriggerService {
    private PdsMqtttopicTriggerMapper triggerMapper;

    public void run() {
        PdsMqtttopicTriggerExample example = new PdsMqtttopicTriggerExample();
        example.createCriteria()
                .andTriggerCategoryIdIsNotNull();
        List<PdsMqtttopicTrigger> pdsMqtttopicTriggers = triggerMapper.selectByExample(example);

        PdsMqtttopicTrigger trigger1 = triggerMapper.selectByPrimaryKey(654321L);

        PdsMqtttopicTrigger trigger = new PdsMqtttopicTrigger();
        trigger.setTriggerTypeid(1L);
        trigger.setTriggerMqtttopic("bom/doc/1");
        trigger.setTriggerMqttbodyCtl(1L);
        trigger.setTriggerIslog((short) 1);
        trigger.setTriggerIspullOninit((short) 1);
        trigger.setTriggerDesc(null);
        trigger.setTriggerConditionDelaytime(0L);
        trigger.setTriggerIsinuse((short) 1);

        int triggerId = 1300;
        for (int i = 1; i <= 100; i++) {
            triggerId++;
            trigger.setTriggerId((long) triggerId);

            trigger.setTriggerName("20102烟包" + i);

            String iStr = String.format("%03d", i);
            String weightPoint = "u20102bmb" + iStr;
            String serialNumber = "u20102pt0001";

            String condition =
                    weightPoint + ">0" + " & " + weightPoint + " != " + "*" + weightPoint +
                            ".oldvalue" + " & " + serialNumber + "!=null";
            trigger.setTriggerCondition(condition);

            String body = "{\"TASKID\":*[u20102pt0001]*,\"BOMBATCHID\":*[u20102pt0002]*," +
                    "\"BOMID\":" + i + ",\"QTY\":*[u20102bmb" + iStr + "]*," +
                    "\"TOTALQTY\":*[u20102pt0082]*"
                    + ",\"SCANCODE\":*[u20102bms" + iStr + "]*"
                    + "}";
            trigger.setTriggerMqttbody(body);

            triggerMapper.insert(trigger);
        }

    }

    @Autowired
    public void setTriggerMapper(PdsMqtttopicTriggerMapper mapper) {
        triggerMapper = mapper;
    }

    public PdsMqtttopicTriggerMapper getTriggerMapper() {
        return triggerMapper;
    }
}
