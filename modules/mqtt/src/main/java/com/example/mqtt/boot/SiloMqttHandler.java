package com.example.mqtt.boot;

import com.example.mqtt.entity.MqttMessage;
import com.example.mqtt.entity.SiloMessageRecord;
import com.example.mqtt.mapper.SiloMessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiloMqttHandler implements MqttCallbackExtended {
    private final String[] topic = {"silo/in/1", "silo/in/2", "silo/out/1", "silo/out/2"};

    @Autowired
    MqttClient mqttClient;

    @Autowired
    private SiloMessageMapper mapper;

    public void connectionLost(Throwable cause) {
        System.out.println("connectionLost: " + cause.getMessage());
    }

    public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage message) {
        System.out.println("topic: " + topic);
        System.out.println("Qos: " + message.getQos());
//        System.out.println("message content: " + new String(message.getPayload()));
        ObjectMapper objectMapper = new ObjectMapper();
        MqttMessage mqttMessage = null;
        try {
            mqttMessage = objectMapper.readValue(message.getPayload(), MqttMessage.class);
            if (mqttMessage != null) {
                SiloMessageRecord message1 = objectMapper.readValue(mqttMessage.getMsgstr(),
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
            mqttClient.subscribe(this.topic);
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
