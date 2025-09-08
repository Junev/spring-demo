package com.example.mytask.service.archiveMqttMessage;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SiloMqttBoot {
    private static MqttClient mqttClient;
    private static MqttConnectOptions mqttConnectOptions;
    private static SiloMqttHandler handler;
    
    @Value("${mqtt.enabled:true}")
    private boolean mqttEnabled;

    @EventListener(ContextRefreshedEvent.class)
    public void run() {
        if (!mqttEnabled) {
            return;
        }
        
        try {
            System.out.println("handler" + SiloMqttBoot.handler);
            mqttClient.setCallback(SiloMqttBoot.handler);
            System.out.println("mqttConnectOptions: " + mqttConnectOptions.getUserName());
            mqttClient.connect(mqttConnectOptions);
            // SiloMqttHandler中定义的topic
            String[] topics = {"silo/in/1", "silo/in/2", "silo/out/1", "silo/out/2"};
            mqttClient.subscribe(topics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MqttClient getMqttClient() {
        return mqttClient;
    }

    @Autowired
    public void setMqttClient(MqttClient mqttClient) {
        SiloMqttBoot.mqttClient = mqttClient;
    }

    public static MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

    @Autowired
    public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
        SiloMqttBoot.mqttConnectOptions = mqttConnectOptions;
    }

    public static SiloMqttHandler getHandler() {
        return handler;
    }

    @Autowired
    public void setHandler(SiloMqttHandler handler) {
        SiloMqttBoot.handler = handler;
    }
}