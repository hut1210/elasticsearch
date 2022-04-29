package com.example.demo.config;

import com.example.demo.util.RedisUtils2;
import javafx.beans.binding.ObjectExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/23 10:17
 */
@Component
@Slf4j
public class RedisReceiver implements MessageListener {
    @Resource
    RedisUtils2 redisUtils2;

    @Resource(name = "multiplePool")
    private Executor executor;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println(new String(message.getBody()));
        System.out.println(new String(message.getChannel()));
        if (redisUtils2.hasKey("testAsync")) {
            redisUtils2.delete("testAsync");
        }
        long start = System.currentTimeMillis();
        log.info("开始" + start);
        Object o = redisUtils2.rightPop("testMessage");
        while (o != null) {
            Object finalO = o;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int a = 10 / 0;
                        log.info(Thread.currentThread().getName() + "执行业务逻辑...." + finalO);
                        Thread.sleep(10);
                        redisUtils2.increment("testAsync", 1);
                        log.info("redis 记录同步数量 = " + redisUtils2.get("testAsync"));
                    } catch (Exception e) {
                        log.error("执行业务逻辑出现异常 编号 = " + finalO);
                        log.error(e.getMessage(), e);
                    }
                }
            });
            o = redisUtils2.rightPop("testMessage");
        }
        log.info("用时" + (System.currentTimeMillis() - start));
    }
}
