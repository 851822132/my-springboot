package com.test.myspringboot.controller;

import com.alibaba.druid.util.StringUtils;
import com.test.myspringboot.entity.User;
import com.test.myspringboot.rabbitmq.producer.FanoutProducer;
import com.test.myspringboot.rabbitmq.producer.StringProducer;
import com.test.myspringboot.rabbitmq.producer.TopicProducer;
import com.test.myspringboot.service.UserService;
import com.test.myspringboot.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  swagger2地址:http://localhost:8083/swagger-ui.html
 */
@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringProducer stringProducer;
    @Autowired
    private FanoutProducer fanoutProducer;
    @Autowired
    private TopicProducer topicProducer;

    @ApiOperation(value = "添加用户",notes = "添加用户测试")
    @ApiImplicitParam(name = "id",value = "用户id",required = false,dataType = "String")
    @RequestMapping(value = "/add",method = {RequestMethod.POST ,RequestMethod.GET})
    public User addUser(String id){
//        redisUtil.set("aaaaaaaaaaaa","aaaaaaaaaaaaa");
        User u = new User();
        u.setBirthday(new Date());
        u.setUserName("张三"+System.currentTimeMillis());
        u.setSex("男");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info("当前时间是：{}",sf.format(new Date()));
        try {
            userService.insertSelective(u);
            LOG.info("插入用户成功");
//            LOG.info("获取到redis的值是{}",redisUtil.get("aaaaaaaaaaaa"));
        }catch (Exception e){
            LOG.error("插入用户异常",e);
        }
        return u;
    }
    @RequestMapping(value = "/sendString")
    public String sendString(){
        try {
            stringProducer.sendString("张三");
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }
    @RequestMapping(value = "/sendFanout")
    public String sendFanout(){
        try {
            fanoutProducer.sendFanout("张三");
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }
    @RequestMapping(value = "/sendTopic")
    public String sendTopic(){
        try {
            topicProducer.topicTopic1Send("张三-A");
            topicProducer.topicTopic2Send("张三-B");
            topicProducer.topicTopic3Send("张三-C");
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }
    @RequestMapping(value = "/test1")
    public String test1(){
        try {
            userService.test1();
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }
    @RequestMapping(value = "/test2")
    public String test2(){
        try {
            userService.test2();
        }catch (Exception e){
            return "error";
        }
        return "ok";
    }

    @RequestMapping(value = "/list")
    public List<User> list(){
        try {
            List<User> users = userService.selectAllUser();
            return users.parallelStream().filter(u->u.getSex().equals("男")).collect(Collectors.toList());
        }catch (Exception e){
            LOG.error("获取用户异常",e);
        }
        return new ArrayList<>();
    }
}
