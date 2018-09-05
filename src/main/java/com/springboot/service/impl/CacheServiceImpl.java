package com.springboot.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "sys.cache.")
public class CacheServiceImpl {
    Logger logger  = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisTemplate redisTemplate;

    /**
     *
     * @param owner
     * @param type
     * @return 重复调用该方法，如果KV已存在，则返回value，
     * 不存在，则生成随机数并把KV进行cache
     */
    @Cacheable
    public String getVerificationCode(String owner,String type){
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }

    @CacheEvict(allEntries = true)
    public void removeCache(String owner,String type){
        //remove the key in redis cache
    }


    @Cacheable
    public int times(String owner,String day,String type){
        return 0;
    }

    @CachePut
    public int times(String owner,String day,String type,int times){
        return times;
    }









}
