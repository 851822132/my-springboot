package com.test.myspringboot.test;


public class Test {

    public static void main(String[] args) {
        new Num().start();
        new Num().start();
        System.out.println(Thread.currentThread().getName() + "主线程");
    }
}
     class Num extends Thread{
        static int a = 0;

        @Override
        public void run() {
            synchronized (Num.class){
                for (int i = 0;i< 100000000;i++){
                    a++;
                }
                System.out.println(Thread.currentThread().getName()+"---"+ a);
            }
        }
    }

