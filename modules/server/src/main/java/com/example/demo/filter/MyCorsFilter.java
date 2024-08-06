package com.example.demo.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 第一种开启CORS的方式， 注入MyCorsFilter这个Bean
 * 2. 第二种开启CORS的方式，把MyWebSecurityConfig的Order提高，并在config(HttpSecurity http)中使用cors，和corsConfiguration。副作用其他Filter改变
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyCorsFilter>>>init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyCorsFilter>>>doFilter");

        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.addHeader("Access-Control-Allow-Origin", "http://192.168.40.20:1888");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "Authorization, Cache-Control, Content" +
                "-Type");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        System.out.println(req.getMethod());
        if (req.getMethod().equals("OPTIONS")) {

            servletResponse.getWriter().println("ok");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter>>>destroy");
    }
}
