package com.springboot.service.impl;

import com.springboot.mybatis.dao.UsersDao;
import com.springboot.mybatis.model.UsersDomain;
import com.springboot.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UsersDao usersDao;

    public int register(UsersDomain usersDomain){
        return usersDao.registUser(usersDomain);
    }

    public UsersDomain findUserByEmail(String email){
        return usersDao.findUserByEmail(email);
    }


}
