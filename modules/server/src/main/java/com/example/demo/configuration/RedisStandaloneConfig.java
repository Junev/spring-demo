package com.example.demo.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisStandaloneConfig {
    private static String DEFAULT_HOST = "localhost";
    private static int DEFAULT_PORT = 6379;

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int database;
    private @Nullable
    String username = null;
    private RedisPassword password = RedisPassword.none();

    @Bean
    RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration config =
                new RedisStandaloneConfiguration();
        config.setHostName(this.host);
        config.setPort(this.port);
        config.setDatabase(this.database);
        config.setUsername(this.username);
        config.setPassword(this.password);
        return config;
    }

    @Bean
    RedisURI redisURI() {
        return RedisURI.builder()
                .withHost(this.host)
                .withPort(this.port)
                .withDatabase(this.database)
                .withAuthentication(this.username, this.password.toString())
                .build();
    }

    @Bean
    RedisClient redisClient() {
        RedisURI redisURI = redisURI();
        return RedisClient.create(redisURI);
    }
}
