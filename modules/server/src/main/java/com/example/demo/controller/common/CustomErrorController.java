package com.example.demo.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public Map<String, Object> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        
        // 记录错误日志
        logger.error("请求错误: {} - 状态码: {}", 
            request.getRequestURI(), 
            statusCode,
            exception);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", statusCode);
        result.put("message", exception != null ? exception.getMessage() : "请求的资源不存在");
        result.put("timestamp", System.currentTimeMillis());
        result.put("path", request.getRequestURI());
        
        return result;
    }
} 