package com.test.myspringboot.demo.aop.myproxy;

import com.test.myspringboot.demo.aop.DataService;

import java.lang.reflect.Method;

public class DataInvocationHandler implements MyInvocationHandler {

    private DataService service;

    public DataInvocationHandler(DataService service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        before();
        //执行真正的方法
        method.invoke(service,args);
        after();
        return null;
    }

    public void before(){
        System.out.println("代理类的前置方法");
    }
    public void after(){
        System.out.println("代理类的后置方法");
    }
}
