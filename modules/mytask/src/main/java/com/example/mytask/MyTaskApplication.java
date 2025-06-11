package com.example.mytask;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.mytask.mapper")
@MapperScan("com.example.repository.mapper")
@EnableTransactionManagement
@EnableScheduling
public class MyTaskApplication {
    private static final Logger logger
            = LoggerFactory.getLogger(MyTaskApplication.class);
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MyTaskApplication.class, args);
//        SiloMqttBoot.run();
    }
}
