package com.example.demo.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hut
 * @date 2022/10/27 8:56 下午
 */
//@Api(tags = "RocketMQ 生产者测试")
@RestController
@RequestMapping(value = "/rocketmq")
public class RocketMQTestController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    //@ApiOperation(value = "发送消息")
    @GetMapping("/send")
    public String query(String topic, String s) {

        rocketMQTemplate.convertAndSend(topic, s);

        return "ok";
    }
}
