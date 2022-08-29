package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserById")
    public String getUserById(Integer id) {
        System.out.println(id);
        return userService.getUserById(id);
    }

    @GetMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        System.out.println(id);
        userService.deleteUserById(id);
    }
}
