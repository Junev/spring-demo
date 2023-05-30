package com.example.mqtt;

import com.example.repository.mapper.PdsMqtttopicTriggerMapper;
import com.example.repository.model.PdsMqtttopicTrigger;
import com.example.repository.model.PdsMqtttopicTriggerExample;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.mqtt.mapper")
@MapperScan("com.example.repository.mapper")
public class MqttApplication {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MqttApplication.class, args);
        addMqttConfig();
//        Boot.run();
    }

    public static void addMqttConfig() {
        PdsMqtttopicTriggerMapper m = context.getBean(PdsMqtttopicTriggerMapper.class);
        PdsMqtttopicTriggerExample example = new PdsMqtttopicTriggerExample();
        example.createCriteria()
                .andTriggerCategoryIdIsNotNull();
        List<PdsMqtttopicTrigger> pdsMqtttopicTriggers = m.selectByExample(example);

    }

}
