package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRestTemplate {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
        return builder.build();
    }
}
