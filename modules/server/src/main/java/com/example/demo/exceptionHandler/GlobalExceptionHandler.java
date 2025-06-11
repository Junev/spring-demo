package com.example.demo.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e, HttpServletRequest request) {
        // 获取异常堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        
        // 记录异常日志，包含堆栈信息
        logger.error("请求发生异常: {} - {}\n堆栈信息:\n{}", 
            request.getRequestURI(), 
            e.getMessage(), 
            stackTrace);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("message", "服务器内部错误");
        result.put("timestamp", System.currentTimeMillis());
        result.put("path", request.getRequestURI());
        result.put("stackTrace", stackTrace);
        
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        // 获取异常堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        
        // 记录运行时异常日志，包含堆栈信息
        logger.error("运行时异常: {} - {}\n堆栈信息:\n{}", 
            request.getRequestURI(), 
            e.getMessage(), 
            stackTrace);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("message", e.getMessage());
        result.put("timestamp", System.currentTimeMillis());
        result.put("path", request.getRequestURI());
        result.put("stackTrace", stackTrace);
        
        return result;
    }
} 