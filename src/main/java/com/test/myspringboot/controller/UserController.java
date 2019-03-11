package com.test.myspringboot.controller;

import com.alibaba.druid.util.StringUtils;
import com.test.myspringboot.entity.User;
import com.test.myspringboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add")
    public User addUser(){
        User u = new User();
        u.setBirthday(new Date());
        u.setUserName("张三"+System.currentTimeMillis());
        u.setSex("男");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info("当前时间是：{}",sf.format(new Date()));
        try {
            userService.insertSelective(u);
            LOG.info("插入用户成功");
        }catch (Exception e){
            LOG.error("插入用户异常",e);
        }
        return u;
    }

}
