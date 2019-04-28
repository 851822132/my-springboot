package com.test.myspringboot.test.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolDemo {

    //1.需要一个仓库
    private LinkedBlockingQueue<Runnable> blockingQueue;
    //2.需要一个线程的集合
    private List<Thread> workers;
    //3.需要一个人来干活
    public static class Worker extends Thread{
        private ThreadPoolDemo threadPoolDemo;
        public Worker(ThreadPoolDemo poolDemo){
            this.threadPoolDemo = poolDemo;
        }
        @Override
        public void run() {
            //正在运行或者任务队列里有值，防止关闭线程池的时候任务队列有值为执行
            while (this.threadPoolDemo.isWorking || this.threadPoolDemo.blockingQueue.size() > 0){
                Runnable task = null;
                try {
                    //当线程池活跃，以阻塞的方式去任务，否则
                    if (this.threadPoolDemo.isWorking) {
                        task = this.threadPoolDemo.blockingQueue.take();
                    }else {
                        task = this.threadPoolDemo.blockingQueue.poll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null){
                    task.run();
                    System.out.println("线程：" + Thread.currentThread().getName() + "执行完毕！");
                }
            }
        }
    }
    //4.线程池初始化

    public ThreadPoolDemo(int poolSize, int taskSize) {
        if (poolSize <= 0 || taskSize <= 0) {
            throw new IllegalArgumentException("非法参数");
        }
        this.blockingQueue = new LinkedBlockingQueue<>(taskSize);
        this.workers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    //5.需要将任务放入仓库（非阻塞）
    public boolean submit(Runnable task){
        if (isWorking) {
            return this.blockingQueue.offer(task);
        }else {
            return false;
        }

    }

    //6.需要将任务放入仓库（阻塞）
    public void execute(Runnable task){
        try {
            this.blockingQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //7.需要一个关闭方法
    //添加任务的方法，不能再进入新任务
    //已经进来的任务需要执行完毕
    //从任务队列中获取任务方法不能是阻塞
    //关闭处于等待和阻塞的线程
    private volatile boolean isWorking = true;
    public void shutdown(){
        this.isWorking = false;
        //关闭在等待或者阻塞的线程
        workers.forEach(e->{
            if (e.getState().equals(Thread.State.WAITING) || e.getState().equals(Thread.State.BLOCKED)){
                e.interrupt();
            }
        });
    }
}
