package com.example.mqtt.boot;

import com.example.mqtt.entity.Silo;
import com.example.mqtt.entity.SiloMessageRecord;
import com.example.mqtt.mapper.SiloMessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Handler implements MqttCallbackExtended {
    @Autowired
    MqttClient mqttClient;

    @Autowired
    private SiloMessageMapper mapper;

    public void connectionLost(Throwable cause) {
        System.out.println("connectionLost: " + cause.getMessage());
    }

    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("topic: " + topic);
        System.out.println("Qos: " + message.getQos());
//        System.out.println("message content: " + new String(message.getPayload()));
        ObjectMapper objectMapper = new ObjectMapper();
        Silo silo = null;
        try {
            silo = objectMapper.readValue(message.getPayload(), Silo.class);
            if (silo != null) {
                SiloMessageRecord message1 = objectMapper.readValue(silo.getMsgstr(),
                        SiloMessageRecord.class);
                message1.setTopic(topic);
                System.out.println(message1.getCreatedAt());
                System.out.println(message1.getTopic());
                System.out.println(message1.getSILOID());
                mapper.addSiloMessage(message1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------");

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void connectComplete(boolean b, String s) {
        System.out.println("connectComplete: " + b + " " + s);
        try {
            mqttClient.subscribe(MyMqttClient.getTopic());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public SiloMessageMapper getMapper() {
        return mapper;
    }

    public void setMapper(SiloMessageMapper mapper) {
        this.mapper = mapper;
    }
}
