package com.test.myspringboot.demo.aop.myproxy;

import java.lang.reflect.Method;

public interface MyInvocationHandler {

     Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}
