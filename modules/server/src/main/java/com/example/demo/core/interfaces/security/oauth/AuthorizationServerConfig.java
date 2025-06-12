package com.example.demo.core.interfaces.security.oauth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * OAuth2 授权服务器
 * <a href="https://docs.spring.io/spring-security-oauth2-boot/docs/2.2.0.M3/reference/html/boot-features-security-oauth2-authorization-server.html">...</a>
 * <a href="https://stackoverflow.com/questions/44625488/spring-security-cors-error-when-enable-oauth2">...</a>
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 1. 通过/oauth/token接口 获取token
                .withClient("password")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(24 * 60 * 60)
                .resourceIds("rid")
                .scopes("all")
                .secret(passwordEncoder.encode("123"))


                // 1. 请求授权/oauth/authorize?response_type=code&state=&client_id=code&scope=all
                // &redirect_uri=http%3A%2F%2Flocalhost%3A9090%2Fcallback.html
                // 2. 通过/login登录，获取code
                // 3. 拿code交换token /oauth/token
                .and()
                .withClient("code")
                .authorizedGrantTypes("authorization_code")
                .accessTokenValiditySeconds(1800)
                .resourceIds("rid")
                .scopes("all")
                .secret(passwordEncoder.encode("123"))
                .redirectUris("http://localhost:9090/callback");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
