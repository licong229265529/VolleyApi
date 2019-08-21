package com.example.api.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManage {
    //1: 把任务添加到请求队列中
    private LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

    //添加任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //2：把队列中的任务放入到线程池
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManage() {            //核心线程数4   同时处理20个线程    存活时候15m   数组队列4
        threadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        //开启传送带，让程序运行起来
        threadPoolExecutor.execute(runnable);
    }

    //在处理中心队列 如果超出15m  则应该返回到请求队列中从新执行
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //参数r就是超时的线程
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    //3：让他们开始工作起来
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                //从队列中取出请求
                try {
                    runnable = queue.take();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable!=null){  //如果取到 就执行
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };
    //单例模式
    private static ThreadPoolManage ourInstance=new ThreadPoolManage();
    public static ThreadPoolManage getOurInstance(){
        return ourInstance;
    }
}
