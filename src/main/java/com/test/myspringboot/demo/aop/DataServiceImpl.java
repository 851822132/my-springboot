package com.test.myspringboot.demo.aop;

public class DataServiceImpl implements DataService {
    @Override
    public void save(String param) {
        System.out.println("这是真正的业务逻辑--save方法" + "，参数是：" + param);
    }

    @Override
    public void update(String param) {
        System.out.println("这是真正的业务逻辑--update方法" + "，参数是：" + param);
    }
}
