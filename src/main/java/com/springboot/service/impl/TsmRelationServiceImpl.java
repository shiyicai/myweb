package com.springboot.service.impl;

import com.springboot.mybatis.dao.TsmRelationDao;
import com.springboot.mybatis.model.TsmRelationDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TsmRelationServiceImpl {
    @Autowired
    TsmRelationDao tsmRelationDao;

    public LinkedList<TsmRelationDomain> findAll() {
        return tsmRelationDao.findAll();
    }
}
