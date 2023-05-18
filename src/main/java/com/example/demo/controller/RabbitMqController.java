package com.example.demo.controller;

import com.example.demo.domain.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

/**
 * @author hut
 * @date 2022/10/19 11:05 上午
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{num}")
    public String sendMessage(@PathVariable(name = "num") Integer num) {
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            //if (i % 2 == 0) {
                rabbitTemplate.convertAndSend("hello-java-topic-exchange", "hello.java.test0", "hello world" + i);
            /*} else {
                Student student = new Student();
                student.setName("测试" + i);
                student.setAge(20 + random.nextInt(10));
                student.setStudentId(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("hello-java-topic-exchange", "hello.java.test1", student);
            }*/
        }

        return "ok";
    }
}
