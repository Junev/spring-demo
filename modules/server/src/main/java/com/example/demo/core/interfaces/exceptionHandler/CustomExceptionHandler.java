package com.example.demo.core.interfaces.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void uploadException(MaxUploadSizeExceededException e, HttpServletResponse res) throws IOException {
        res.setContentType("text/html;chart-set=utf-8");
        PrintWriter pw = res.getWriter();
        pw.write("上传文件超出大小限制。");
        pw.flush();
        pw.close();
    }
}
