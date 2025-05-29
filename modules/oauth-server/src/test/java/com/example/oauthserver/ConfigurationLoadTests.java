package com.example.oauthserver;

import com.example.oauthserver.entity.Hr;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigurationLoadTests {

    @Autowired
    Hr users;

    @Test
    void configurationUsersLoads() {
        users.toString();
    }
}
