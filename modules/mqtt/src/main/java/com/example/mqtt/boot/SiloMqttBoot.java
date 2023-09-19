package com.example.mqtt.boot;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiloMqttBoot {
    private static MqttClient mqttClient;
    private static MqttConnectOptions mqttConnectOptions;
    private static SiloMqttHandler handler;

    public static void run() {
        try {
            System.out.println("handler" + SiloMqttBoot.handler);
            mqttClient.setCallback(SiloMqttBoot.handler);
            System.out.println("mqttConnectOptions: " + mqttConnectOptions.getUserName());
            mqttClient.connect(mqttConnectOptions);
//            mqttClient.subscribe(MyMqttClient.getTopic());
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
