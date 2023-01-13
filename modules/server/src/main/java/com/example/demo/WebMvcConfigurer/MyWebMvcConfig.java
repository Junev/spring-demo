package com.example.demo.WebMvcConfigurer;

import com.example.demo.interceptor.MyIntercepter1;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/book/**")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .maxAge(1800)
//                .allowedOrigins("http://127.0.0.1:8080");

        registry.addMapping("/login")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(7200)
                .allowedOrigins("http://127.0.0.1:8080");

        registry.addMapping("/mdb")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(7200)
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyIntercepter1())
                .addPathPatterns("/**")
                .excludePathPatterns("/hello");
    }
}
