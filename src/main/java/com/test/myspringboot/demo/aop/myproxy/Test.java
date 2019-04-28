package com.test.myspringboot.demo.aop.myproxy;

import com.test.myspringboot.demo.aop.DataService;
import com.test.myspringboot.demo.aop.DataServiceImpl;

public class Test {

    public static void main(String[] args) throws Exception{
        DataService service = new DataServiceImpl();

        DataInvocationHandler handler = new DataInvocationHandler(service);
        Myproxy myproxy = new Myproxy();
        DataService service1 = (DataService) myproxy.newProxyInstance(Test.class.getClassLoader(),new Class[]{DataService.class}, handler);
        service1.save("李四");
    }
}
