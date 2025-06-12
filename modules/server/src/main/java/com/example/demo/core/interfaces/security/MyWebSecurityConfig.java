package com.example.demo.core.interfaces.security;

import com.example.demo.core.application.service.HrService;
import com.example.demo.core.interfaces.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security配置类
 * 用于配置Web应用的安全策略，包括认证、授权、CORS等
 */
@Configuration
@EnableWebSecurity(debug = true)  // 启用Spring Security，debug模式关闭
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    /**
     * 配置密码编码器
     * 使用BCrypt算法进行密码加密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 配置用户详情服务
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    /**
     * 配置内存中的用户
     * 用于测试和开发环境
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root").password(passwordEncoder().encode("123")).roles("admin",
                "personnel")
                .and()
                .withUser("bruce").password(passwordEncoder().encode("123")).roles("personnel");
    }

    /**
     * 配置Web安全
     * 忽略静态资源的认证
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/static/**", "/css/**", "/js/**");
    }

    /**
     * 配置HTTP安全
     * 包括认证、授权、CORS、登录、登出等
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()  // 禁用CSRF保护
                .cors()
                .configurationSource(getCorsConfigurationSource())  // 配置CORS
                .and()
                .authorizeRequests()
                    // 允许所有人访问的路径
                    .antMatchers("/", "/index.html", "/static/**", "/css/**", "/js/**", "/login.html")
                        .permitAll()
                    // 对/api/**路径，通过OAuth2认证
                    .antMatchers("/api/**")
                        .permitAll() 
                    // 需要admin或personnel角色的路径
                    .antMatchers("/admin/**", "/user/**", "/hr/**", "/book/**", "/mdb", "/upload")
                        .hasAnyRole("admin", "personnel")
                    // 其他所有请求需要认证
                    .anyRequest()
                        .authenticated()
                .and()
                    // 配置未认证时的处理
                    .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        Result<?> result = Result.error(HttpStatus.UNAUTHORIZED.value(), "未认证");
                        result.setPath(request.getRequestURI());
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    })
                .and()
                    // 配置登录
                    .formLogin()
                    .loginPage("/login.html")  // 登录页面
                    .loginProcessingUrl("/login")  // 登录处理URL
                    .defaultSuccessUrl("/index.html", true)  // 登录成功后的跳转页面
                    .successHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        Result<?> result = Result.success("登录成功");
                        result.setPath(request.getRequestURI());
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        Result<?> result = Result.error(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
                        result.setPath(request.getRequestURI());
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    })
                    .permitAll()
                .and()
                    // 配置登出
                    .logout()
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        Result<?> result = Result.success("退出成功");
                        result.setPath(request.getRequestURI());
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    });
    }

    /**
     * 配置CORS
     * 允许所有来源的跨域请求
     */
    protected CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // 允许所有来源
        configuration.setAllowedMethods(Arrays.asList("*"));  // 允许所有方法
        configuration.setAllowedHeaders(Arrays.asList("*"));  // 允许所有头
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
