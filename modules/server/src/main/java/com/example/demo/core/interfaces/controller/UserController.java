package com.example.demo.core.interfaces.controller;

import com.example.demo.core.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserById")
    public String getUserById(Integer id) {
        System.out.println(id);
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public Integer login(@RequestBody(required = false) Map<String, String> loginData, HttpServletResponse resp) {
        Cookie ck1 = new Cookie("sessionId", "fooBar");
        resp.addCookie(ck1);
        return 1;
    }

    @GetMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        System.out.println(id);
        userService.deleteUserById(id);
    }
}
