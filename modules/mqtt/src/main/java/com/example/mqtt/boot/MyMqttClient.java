package com.example.mqtt.boot;

import com.example.mqtt.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMqttClient {
    private static MqttConfig mqttConfig;
    private static final String[] topic = {"silo/in/1", "silo/in/2", "silo/out/1", "silo/out/2"};
    private static final Integer qos = 0;

    public static MqttConfig getMqttConfig() {
        return mqttConfig;
    }

    @Autowired
    public void setMqttConfig(MqttConfig mqttConfig) {
        MyMqttClient.mqttConfig = mqttConfig;
    }

    @Bean
    MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(mqttConfig.getBroker(), mqttConfig.getClientId(),
                new MemoryPersistence());
        return client;
    }

    @Bean
    MqttConnectOptions mqttConnectOptions (){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttConfig.getPassword().toCharArray());
        options.setAutomaticReconnect(true);
        return options;
    }

    static String[] getTopic() {
        return topic;
    }

    public static Integer getQos() {
        return qos;
    }
}
