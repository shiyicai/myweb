package com.springboot.mybatis.dao;

import com.springboot.mybatis.model.TsmRelationDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedList;


public interface TsmRelationDao {
    LinkedList<TsmRelationDomain> findAll();
}
