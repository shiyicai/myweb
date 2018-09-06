package com.springboot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseClientDetailService implements ClientDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails client = null;
                 //这里可以改为查询数据库,图方便代码写死
                 if("client".equals(clientId)) {
                         client = new BaseClientDetails();
                         client.setClientId(clientId);
                         client.setClientSecret(passwordEncoder.encode("123456"));
                         client.setAuthorizedGrantTypes(Arrays.asList("authorization_code",
                                         "client_credentials", "refresh_token", "password", "implicit"));
                         //不同的client可以通过 一个scope 对应 权限集
                         client.setScope(Arrays.asList("all", "select"));
                         client.setAuthorities(AuthorityUtils.createAuthorityList("admin"));
                         client.setAccessTokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(2));//access_token life circle
                         client.setRefreshTokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(2));
                         Set<String> uris = new HashSet<>();
                         uris.add("http://localhost:8080/login");
                         client.setRegisteredRedirectUri(uris);
                         logger.debug(client.toString());
                     }
                 if(client == null) {
                         throw new NoSuchClientException("No client width requested id: " + clientId);
                     }
                 return client;
    }
}
