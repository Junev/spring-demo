package com.example.demo.controller.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Map<String, Object> handleError(HttpServletRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        
        errorDetails.put("code", statusCode);
        errorDetails.put("message", exception != null ? exception.getMessage() : "请求的资源不存在");
        errorDetails.put("timestamp", System.currentTimeMillis());
        
        return errorDetails;
    }
} 