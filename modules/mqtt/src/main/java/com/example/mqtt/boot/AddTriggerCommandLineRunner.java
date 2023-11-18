package com.example.mqtt.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//@Component
public class AddTriggerCommandLineRunner implements CommandLineRunner {
    @Autowired
    private MqttTriggerService triggerService;

    @Override
    public void run(String... args) throws Exception {
//        triggerService.run();
    }
}
