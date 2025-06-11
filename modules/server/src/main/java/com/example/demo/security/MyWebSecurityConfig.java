package com.example.demo.security;

import com.example.demo.service.HrService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://blog.csdn.net/weixin_40379712/article/details/130056716">废弃的</a>
 * @see <a href="https://blog.csdn.net/qq_35067322/article/details/115878528">WebSecurity</a>
 */

@Configuration
@EnableWebSecurity(debug = false)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root").password(passwordEncoder().encode("123")).roles("admin",
                "personnel")
                .and()
                .withUser("bruce").password(passwordEncoder().encode("123")).roles("personnel");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/static/**", "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(getCorsConfigurationSource())
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/index.html", "/static/**", "/css/**", "/js/**", "/login.html")
                        .permitAll()
                    .anyRequest()
                        .hasAnyRole("admin", "personnel")
                .and()
                    .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/index.html", true)
                    .successHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        Map<String, Object> result = new HashMap<>();
                        result.put("code", HttpStatus.OK.value());
                        result.put("message", "登录成功");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        Map<String, Object> result = new HashMap<>();
                        result.put("code", HttpStatus.UNAUTHORIZED.value());
                        result.put("message", "用户名或密码错误");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    })
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        Map<String, Object> result = new HashMap<>();
                        result.put("code", HttpStatus.OK.value());
                        result.put("message", "退出成功");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                    });
    }


    protected CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
