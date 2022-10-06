package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserById")
    public String getUserById(Integer id) {
        System.out.println(id);
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public Integer login(HttpServletResponse resp) {
        Cookie ck1 = new Cookie("sessionId", "fooBar");
//        ck1.setMaxAge(1800);
//        ck1.setHttpOnly(true);
        resp.addCookie(ck1);

//        Cookie ck2 = new Cookie("hello", "world");
//        ck2.setMaxAge(1800);
//        ck2.setHttpOnly(true);
//        resp.addCookie(ck2);

        return 1;
    }

    @GetMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        System.out.println(id);
        userService.deleteUserById(id);
    }
}
