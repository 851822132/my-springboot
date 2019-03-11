package com.test.myspringboot.service;

import com.test.myspringboot.entity.User;

public interface UserService {

    int insertSelective(User record);
}
