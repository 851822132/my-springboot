package com.test.myspringboot.service.impl;

import com.test.myspringboot.dao.UserMapper;
import com.test.myspringboot.entity.User;
import com.test.myspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }
}
