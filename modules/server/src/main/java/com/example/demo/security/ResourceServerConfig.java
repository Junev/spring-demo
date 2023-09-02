package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("rid").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 错误的方法解决跨域
//        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        //唯独user 可以访问 放行我们的资源
        http.requestMatchers().antMatchers("/admin/**", "/user/**", "/hr/**", "/book/**", "/api",
                "/mdb");

        //所有的访问都需要认证访问
        http.authorizeRequests().anyRequest().authenticated();


    }
}
