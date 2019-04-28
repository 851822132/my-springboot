package com.test.myspringboot.demo.aop.jdkproxy;

import com.test.myspringboot.demo.aop.DataService;
import com.test.myspringboot.demo.aop.DataServiceImpl;

import java.lang.reflect.Proxy;

public class JdkProxy {
    public static void main(String[] args) throws Exception {
        DataService service = new DataServiceImpl();

        DataInvocationHandler handler = new DataInvocationHandler(service);

        DataService service1 = (DataService) Proxy.newProxyInstance(JdkProxy.class.getClassLoader(),new Class[]{DataService.class},handler);

        service1.save("张三");
    }
}
