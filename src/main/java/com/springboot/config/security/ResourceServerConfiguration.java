package com.springboot.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;



/**
 * ResourceServerConfigurerAdapter配置会覆盖WebSecurityConfigurerAdapter
 *
 * protected void configure(HttpSecurity http) 中的配置会以ResourceServerConfigurerAdapter为准。
 */


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
//    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/users/**","/es/**").hasAuthority("admin")
                .antMatchers("/users/**").hasAuthority("users");

    }


}
