package com.example.mqtt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.mqtt.mapper")
@MapperScan("com.example.repository.mapper")
@EnableTransactionManagement
public class MqttApplication {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MqttApplication.class, args);
    }
}
