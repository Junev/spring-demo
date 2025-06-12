package com.example.demo.core.interfaces.security.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth2资源服务器配置
 * 用于保护需要OAuth2认证的API接口
 */
@Configuration
@EnableResourceServer  // 启用OAuth2资源服务器功能
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置资源服务器
        resources.resourceId("rid")  // 设置资源ID，与授权服务器配置保持一致
                .stateless(true);    // 无状态模式，不保存会话
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
            // 第一步：定义OAuth2过滤器链的作用范围
            // 只有匹配/api/**的请求才会进入OAuth2过滤器链 OAuth2AuthenticationProcessingFilter
            .antMatchers("/api/**")
            .and()
            .authorizeRequests()
            // 第二步：对进入OAuth2过滤器链的请求进行授权控制
            // 要求这些请求必须携带有效的OAuth2 token
            .antMatchers("/api/**").authenticated();
    }
}
