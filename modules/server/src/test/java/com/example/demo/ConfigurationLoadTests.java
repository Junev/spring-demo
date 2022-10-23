package com.example.demo;

import com.example.demo.entity.Book;
import com.example.demo.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigurationLoadTests {
    @Autowired
    Book book;

    @Autowired
    Users users;

    @Test
    void configurationBookLoads() {
        book.toString();
    }

    @Test
    void configurationUsersLoads() {
        users.toString();
    }
}
