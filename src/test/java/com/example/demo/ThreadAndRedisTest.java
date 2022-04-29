package com.example.demo;

import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/20 15:11
 */
@SpringBootTest
@Slf4j
public class ThreadAndRedisTest {

    @Resource(name = "multiplePool")
    private Executor executor;

    @Resource
    private RedisUtils2 redisUtils2;

    private int num = 10000;

    @Test
    public void testAsync() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(num);
            if (redisUtils2.hasKey("testAsync")) {
                redisUtils2.delete("testAsync");
            }
            long start = System.currentTimeMillis();
            System.out.println(start);
            for (int i = 0; i < num; i++) {
                int finalI = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            log.info(Thread.currentThread().getName() + "执行业务逻辑...." + finalI);
                            Thread.sleep(10);
                            /*redisUtils2.increment("testAsync",1);*/
                            countDownLatch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            countDownLatch.await();
            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error("testAsync 发生异常", e);
        }

    }

    @Test
    public void testSync() throws InterruptedException {
        if (redisUtils2.hasKey("testSync")) {
            redisUtils2.delete("testSync");
        }

        long start = System.currentTimeMillis();
        System.out.println(start);
        for (int i = 0; i < num; i++) {
            System.out.println(Thread.currentThread().getName() + "执行业务逻辑...." + i);
            Thread.sleep(10);
            //redisUtils2.increment("testSync",1);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    void testSupplyAsync() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("test()方法开始了.....");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始执行.....");
            for (int i = 0; i < num; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "执行业务逻辑...." + i);
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("异步线程执行结束.....");
            return "我是返回值";
        }, executor);
        System.out.println("test()方法结束....." + stringCompletableFuture.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    void testRunAsync() {
        long start = System.currentTimeMillis();
        System.out.println("test()方法开始了.....");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("异步线程开始执行.....");
            for (int i = 0; i < num; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "执行业务逻辑...." + i);
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("异步线程执行结束.....");
        }, executor);
        System.out.println("test()方法结束.....");
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    void test() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Thread.currentThread().getName() + "test()方法开始了.....");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "异步线程开始执行.....");
            System.out.println(Thread.currentThread().getName() + "异步执行线程：" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + "执行业务逻辑....");
            System.out.println(Thread.currentThread().getName() + "异步线程执行结束.....");
            return "我是返回值";
        }, executor).whenComplete((result, exception) -> {
            System.out.println(Thread.currentThread().getName() + "异步任务执行结束，返回结果：" + result);
            System.out.println(Thread.currentThread().getName() + "异步任务执行结束，异常：" + exception);
            System.out.println(Thread.currentThread().getName() + "whenComplete线程：" + Thread.currentThread().getName());
        }).exceptionally((t) -> {
            System.out.println("异常：" + t);
            return "异常返回值";
        });
        System.out.println(Thread.currentThread().getName() + "test()方法结束.....");
    }

    @Test
    void testJNDI() {
        String name = "${java:os}";
        log.info("{},登录了", name);
    }

    @Test
    void testCyclicBarrier() {
        CyclicBarrier barrier = new CyclicBarrier(2);//参数为线程数
        Thread t = new Thread(() -> {
            int num = 1000;
            String s = "";
            for (int i = 0; i < num; i++) {
                s += "Java";
            }
            System.out.println("t Over");
            try {
                barrier.await();//阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
        System.out.println("start = " + start);
        t.start();
        try {
            barrier.await();//也阻塞,并且当阻塞数量达到指定数目时同时释放
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("end = " + end);
        System.out.println("end - start = " + (end - start));
    }

    @Test
    void testBlockingQueue() {
        BlockingQueue queue = new ArrayBlockingQueue(1);//数组型队列，长度为1
        Thread t = new Thread(() -> {
            int num = 1000;
            String s = "";
            for (int i = 0; i < num; i++) {
                s += "Java";
            }
            System.out.println("t Over");
            try {
                queue.put("OK");//在队列中加入数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
        System.out.println("start = " + start);
        t.start();
        try {
            queue.take();//主线程在队列中获取数据，take()方法会阻塞队列，ps还有不会阻塞的方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("end = " + end);
        System.out.println("end - start = " + (end - start));
    }

}
