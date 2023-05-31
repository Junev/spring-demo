package com.example.mqtt.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddTriggerCommandLineRunner implements CommandLineRunner {
    @Autowired
    private MqttTriggerService triggerService;

    @Override
    public void run(String... args) throws Exception {
        triggerService.run();
    }
}
