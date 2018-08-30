package com.springboot.mybatis.dao;

import com.springboot.mybatis.model.SUserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;


public interface SUserDao {
    LinkedList<SUserDomain> findAll();

    SUserDomain findById(@Param("id") int id);
}
