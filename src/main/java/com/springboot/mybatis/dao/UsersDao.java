package com.springboot.mybatis.dao;

import com.springboot.mybatis.model.UsersDomain;
import org.apache.ibatis.annotations.Param;


public interface UsersDao {

    int registUser(UsersDomain usersDomain);
    UsersDomain findUserByEmail(@Param("email") String email);
}
