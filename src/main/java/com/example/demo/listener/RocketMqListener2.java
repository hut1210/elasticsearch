package com.example.demo.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author hut
 * @date 2022/10/27 8:58 下午
 */
//@Component
//@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer", consumeMode = ConsumeMode.ORDERLY)
public class RocketMqListener2 implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("收到消息啦：" + s);
    }
}
