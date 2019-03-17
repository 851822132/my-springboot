package com.test.myspringboot.service.impl;

import com.test.myspringboot.dao.UserMapper;
import com.test.myspringboot.entity.User;
import com.test.myspringboot.service.UserService;
import com.test.myspringboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTransactionTemplate;

    @Override
    public int insertSelective(User record) {
//        redisUtil.set("bbbbbbbbbbbb","bbbbbbbbbbb");
//        redisUtil.setTranscation("aaaaa","aaaaa");
        int i = userMapper.insertSelective(record);
//        int a = 1/0;
        return i;
    }

    @Override
    public void test1() {
        System.out.println(redisTemplate.opsForValue().get("bbbbbbbbbbbb"));
        redisTemplate.opsForValue().set("bbbbbbbbbbbb","cccccccccccccccccc");
        System.out.println(redisTemplate.opsForValue().get("bbbbbbbbbbbb"));
    }

    @Override
    public void test2() {
        System.out.println(redisTransactionTemplate.opsForValue().get("bbbbbbbbbbbb"));
        redisTransactionTemplate.opsForValue().set("bbbbbbbbbbbb","dddddddddddddddddd");
        System.out.println(redisTransactionTemplate.opsForValue().get("bbbbbbbbbbbb"));
    }
}
