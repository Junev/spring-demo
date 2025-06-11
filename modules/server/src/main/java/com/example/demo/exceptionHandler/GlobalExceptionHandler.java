package com.example.demo.exceptionHandler;

import com.example.demo.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(Exception e, HttpServletRequest request) {
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
        
        Result<String> result = Result.error("服务器内部错误");
        result.setPath(request.getRequestURI());
        result.setData(stackTrace);
        
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
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
        
        Result<String> result = Result.error(e.getMessage());
        result.setPath(request.getRequestURI());
        result.setData(stackTrace);
        
        return result;
    }
} 