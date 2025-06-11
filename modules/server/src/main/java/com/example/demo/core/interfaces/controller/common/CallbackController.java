package com.example.demo.core.interfaces.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CallbackController {

    @RequestMapping("/callback")
    public Object callback() {
        return "/callback.html";
    }
}
