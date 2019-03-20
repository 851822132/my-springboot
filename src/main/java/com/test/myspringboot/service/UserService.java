package com.test.myspringboot.service;

import com.test.myspringboot.entity.User;

import java.util.List;

public interface UserService {

    int insertSelective(User record);

    void test1();
    void test2();

    List<User> selectAllUser();
}
