package com.test.myspringboot.test.threadpool;

public class Test {
    public static void main(String[] args) {
        ThreadPoolDemo pool = new ThreadPoolDemo(3,6);
        for (int i = 0; i < 6; i++) {
            pool.submit(() -> {
                System.out.println("一个线程被放到了我们的仓库中。。。");
                try {
                    Thread.sleep(2500l);
                } catch (InterruptedException e) {
                    System.out.println("有一个线程被唤醒了");
                }
            });
        }
    pool.shutdown();
    }
}
