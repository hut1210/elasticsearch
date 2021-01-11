package com.example.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 9:56
 */
public class SingletonTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        //饿汉式
        /*for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    MySingleton mySingleton = MySingleton.getInstance();
                    System.out.println(mySingleton);
                    countDownLatch.countDown();
                }
            };
            service.execute(runnable);
        }*/

        //懒汉式
        for (int i = 0; i < 1000; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    MySingleton2 mySingleton2 = MySingleton2.getInstance();
                    System.out.println(mySingleton2);
                    countDownLatch.countDown();
                }
            };
            service.execute(runnable);
        }
        countDownLatch.await();
        service.shutdown();
    }
}

class MySingleton {
    //已经初始化的静态私有变量
    private static MySingleton mySingleton = new MySingleton();

    //私有构造器
    private MySingleton() {
    }

    //静态方法
    public static MySingleton getInstance() {
        if (mySingleton != null) {
            return mySingleton;
        } else {
            return new MySingleton();
        }

    }
}

class MySingleton2 {
    //静态私有变量
    private volatile static MySingleton2 mySingleton2 = null;

    //私有构造器
    private MySingleton2() {
    }

    //静态获取实例方法
    public static MySingleton2 getInstance() {
        if (mySingleton2 == null) {
            synchronized (MySingleton2.class) {
                if (mySingleton2 == null) {
                    mySingleton2 = new MySingleton2();
                }
            }
        }
        return mySingleton2;
    }
}