package com.test.myspringboot.test.reflect;

/**
 * @author ysh
 */
public class Person {
    private String userName;
    private String sex;
    public Integer age;

    public Person() {
    }

    public Person(String userName, String sex) {
        this.userName = userName;
        this.sex = sex;
    }

    public void show() {
        System.out.println("hehe" + userName + sex);
    }

    public void show(String paramter) {
        System.out.println(paramter);
    }

    public void show(String paramter, int num) {
        System.out.println(paramter + num);
    }

    public static void show(String paramter, int num, double d) {
        System.out.println(paramter + num + d);
    }

    private void func() {
        System.out.println("private");
    }

}
