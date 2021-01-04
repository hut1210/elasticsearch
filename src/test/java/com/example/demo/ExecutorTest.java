package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 21:38
 */
@SpringBootTest
@Slf4j
public class ExecutorTest {

    @Resource(name = "multiplePool")
    private Executor executor;

    @Resource(name = "multiplePool2")
    ThreadPoolTaskExecutor executor2;

    @Test
    public void tesTask(){
        System.out.println(Thread.currentThread().getName()+"-----");
    }

    @Test
    public void test() throws InterruptedException {
        final CountDownLatch testExecutor = new CountDownLatch(1000);
        System.out.println(LocalDateTime.now());
        for (int i=0 ;i<1000;i++){
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName()+"-----"+ finalI);
                    testExecutor.countDown();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName()+"-----"+ finalI);
                    testExecutor.countDown();
                }
            }).start();
        }
        testExecutor.await();
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void test2() throws InterruptedException {
        final CountDownLatch testExecutor = new CountDownLatch(1000);
        System.out.println(LocalDateTime.now());
        for (int i=0 ;i<1000;i++){
            int finalI = i;
            Future<?> submit = executor2.submit(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName()+"-----"+ finalI);
                    testExecutor.countDown();
                }
            });
            log.info("submit----"+submit+" "+finalI);
        }
        testExecutor.await();
        System.out.println(LocalDateTime.now());
    }
}
