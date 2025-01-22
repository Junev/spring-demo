package com.example.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan
public class MyBatisMiningApplication {
    private static final Logger logger
            = LoggerFactory.getLogger(MyBatisMiningApplication.class);
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MyBatisMiningApplication.class, args);
//        SiloMqttBoot.run();
    }
}
