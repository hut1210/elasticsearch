package com.example.demo.controller;

import com.example.demo.domain.WarnRules;
import com.example.demo.lock.ConcurrentLockHelper;
import com.example.demo.lock.ExclusiveProcessor;
import com.example.demo.strategy.Impl.OrderWarnService;
import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/23 10:31
 */
@RequestMapping("/redis")
@Controller
@Slf4j
public class RedisController {

    @Autowired
    StringRedisTemplate template;

    @Resource
    RedisUtils2 redisUtils2;

    @Resource
    OrderWarnService orderWarnService;

    /**
     * 发布消息
     *
     * @param num
     * @return
     */
    @RequestMapping("/sendMessage/{num}")
    public String sendMessage(@PathVariable Integer num) {
        for (int i = 0; i < num; i++) {
            redisUtils2.leftPush("testMessage", String.valueOf(i));
        }
        template.convertAndSend("channel:test", String.format("收到消息%tT", new Date()));
        return "";
    }

    @RequestMapping("/sendMessage2/{num}")
    public String sendMessage2(@PathVariable Integer num) {
        orderWarnService.triggerRule(new WarnRules());
        return "success";
    }

    @RequestMapping("/testLock/{num}")
    @ResponseBody
    public String testLock(@PathVariable Integer num) {
        log.info("收到请求 testLock num ={}",num);
        ConcurrentLockHelper<String> concurrentLockHelper = ConcurrentLockHelper.getInstance(redisUtils2);
        String testLock = null;
        try {
            testLock = concurrentLockHelper.doAround("testLock", new ExclusiveProcessor<String>() {
                @Override
                public String doProcess() {
                    log.info("执行请求 testLock num ={}",num);
                    long l = redisUtils2.decr("role",1);
                    log.info("执行请求 l ={}",l);
                    if (l >= 0){
                        return "success";
                    }
                    return "fail";
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("执行请求 testLock = {}",testLock);
        return testLock;
    }

}
