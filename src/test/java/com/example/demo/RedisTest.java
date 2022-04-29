package com.example.demo;

import com.example.demo.lock.ConcurrentLockHelper;
import com.example.demo.lock.ExclusiveProcessor;
import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/15 21:33
 */
@SpringBootTest
@Slf4j
public class RedisTest {

    @Resource
    RedisUtils2 redisUtils2;

    @Test
    public void testRedisUtils2(){
        /*redisUtils2.rightPush("list","1");
        redisUtils2.rightPush("list","2");
        redisUtils2.rightPush("list","3");*/

        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);
        redisUtils2.increment("role",1);

        //redisUtils2.leftPop("list");
        //redisUtils2.leftPop("list");
        //redisUtils2.leftPop("list");
    }

    @Test
    public void testRedisExpire(){
        redisUtils2.set("test","test",10);
        redisUtils2.set("test1","test",10);
        redisUtils2.set("test2","test",10);
    }

    @Test
    public void testLockHelper() throws Exception {
        ConcurrentLockHelper<String> concurrentLockHelper = ConcurrentLockHelper.getInstance(redisUtils2);
        String testLock = concurrentLockHelper.doAround("testLock", new ExclusiveProcessor<String>() {
            @Override
            public String doProcess() {
                if (redisUtils2.decr("",1) > 0){
                    return "success";
                }
                return "fail";
            }
        });
        System.out.println("testLock"+testLock);
    }
}
