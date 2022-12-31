package com.example.mqtt.boot;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boot {
    private static MqttClient mqttClient;
    private static MqttConnectOptions mqttConnectOptions;
    private static Handler handler;

    public static void run() {
        try {
            System.out.println("handler");
            System.out.println(Boot.handler);
            mqttClient.setCallback(Boot.handler);
            System.out.println("mqttConnectOptions: ");
            System.out.println(mqttConnectOptions);
            mqttClient.connect(mqttConnectOptions);
            mqttClient.subscribe(MyMqttClient.getTopic());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MqttClient getMqttClient() {
        return mqttClient;
    }

    @Autowired
    public void setMqttClient(MqttClient mqttClient) {
        Boot.mqttClient = mqttClient;
    }

    public static MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

    @Autowired
    public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
        Boot.mqttConnectOptions = mqttConnectOptions;
    }

    public static Handler getHandler() {
        return handler;
    }

    @Autowired
    public void setHandler(Handler handler) {
        Boot.handler = handler;
    }
}
