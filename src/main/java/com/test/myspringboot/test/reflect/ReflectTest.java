package com.test.myspringboot.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws Exception {
        /*
          默认形式 加载-验证-准备-解析-初始化
          1.加载 person.class
          2.在堆内存中分配空间
          3.对对象中的成员进行默认初始化
          4.显示初始化
          5.构造函数初始化
         */
//        Person p = new Person();
//        p.show();
//        p.getClass();
        /*
          获取类的class对象有多种方法
          1.p.getClass
          2.Person.class
          2.反射  java将对象的所有东西均封成了对象，无论是方法或者字段
         */
        Class clazzz = Person.class;
        Class clazz = Class.forName("com.test.myspringboot.test.reflect.Person");
        //对象实例
        Person person = (Person) clazz.newInstance();
        //所有自身方法，包含私有的
//        Method[] methods = clazz.getDeclaredMethods();
        //所有public方法，包含父类的
//        Method[] methods = clazz.getMethods();
//        for (Method method : methods) {
//            System.out.println(method);
//        }

//        Method m = clazz.getMethod("show");
//        System.out.println(m);
//        Method method = clazz.getMethod("show", String.class);
//        System.out.println(method);

        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);

        }
        Field[] fields1 = clazz.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field);
        }

        Method m2 = clazz.getMethod("show", String.class, int.class);
        //invoke方法参数：1：持有方法的实例对象，后面为方法的参数
        m2.invoke(person,"hehe",2);

        Method staticMethod = clazz.getMethod("show", String.class, int.class, double.class);
        //如果是静态方法，无需对象实例，可以是null
        staticMethod.invoke(null,"heh",1,2.3);

        /**
         * 获取构造方法
         */
        Constructor constructor = clazz.getConstructor(String.class,String.class);
        Person person1 = (Person) constructor.newInstance("张三","男");
        person1.show();

    }
}
