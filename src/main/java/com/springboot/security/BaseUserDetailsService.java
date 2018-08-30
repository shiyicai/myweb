package com.springboot.security;

import com.springboot.mybatis.model.UsersDomain;
import com.springboot.service.UsersService;
import com.springboot.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BaseUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsersServiceImpl usersService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户的用户名: " + username);
        // 根据用户名，查找到对应的密码，与权限
        UsersDomain usersDomain = usersService.findUserByEmail(username);

        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        User user = new User(username, usersDomain.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(usersDomain.getAuthority()));
        return user;
    }
}
