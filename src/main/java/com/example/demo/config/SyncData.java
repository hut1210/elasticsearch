package com.example.demo.config;

import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/23 14:54
 */
@Component
@Slf4j
public class SyncData /*implements InitializingBean*/ {

    @Resource
    RedisUtils2 redisUtils2;

    @Resource(name = "multiplePool")
    private Executor executor;

    //@Override
    public void afterPropertiesSet() throws Exception {
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
                        log.info(Thread.currentThread().getName() + "执行业务逻辑...." + finalO);
                        Thread.sleep(10);
                        redisUtils2.increment("testAsync", 1);
                        log.info("redis 记录同步数量 = "+redisUtils2.get("testAsync"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            o = redisUtils2.rightPop("testMessage");
        }
        log.info("用时" + (System.currentTimeMillis() - start));
    }
}
