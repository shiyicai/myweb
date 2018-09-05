package com.springboot.config.security;

import com.springboot.security.BaseClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new BaseClientDetailService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                 endpoints
                         .tokenStore(new RedisTokenStore(redisConnectionFactory))
                         .authenticationManager(authenticationManager)
                         .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

                 //配置TokenService参数
                 DefaultTokenServices tokenService = new DefaultTokenServices();
                 tokenService.setTokenStore(endpoints.getTokenStore());
                 tokenService.setSupportRefreshToken(true);
                 tokenService.setClientDetailsService(endpoints.getClientDetailsService());
                 tokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
                 tokenService.setAccessTokenValiditySeconds((int)TimeUnit.HOURS.toHours(6));
                 endpoints.tokenServices(tokenService);

             }

             @Override
     public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
                 //允许表单认证
                 //这里增加拦截器到安全认证链中，实现自定义认证，包括图片验证，短信验证，微信小程序，第三方系统，CAS单点登录
                 //addTokenEndpointAuthenticationFilter(IntegrationAuthenticationFilter())
                 //IntegrationAuthenticationFilter 采用 @Component 注入
                 oauthServer.allowFormAuthenticationForClients()
                            .tokenKeyAccess("isAuthenticated()")
                            .checkTokenAccess("permitAll()");
             }
}
