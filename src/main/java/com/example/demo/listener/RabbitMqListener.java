package com.example.demo.listener;

import com.example.demo.domain.Student;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author hut
 * @date 2022/10/19 11:22 上午
 */
@Component
@RabbitListener(queues = {"hello-java-queue1"})
public class RabbitMqListener {

    @RabbitHandler
    public void receiverMessage(Message message, String content, Channel channel) throws IOException{
        System.out.println("RabbitMqListener接收到消息" + content);
        try {
            //int a = 1/0;
            //Thread.sleep(1000);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    @RabbitHandler
    public void receiverMessage(Message message, Student student, Channel channel) throws IOException{
        System.out.println("RabbitMqListener接收到消息" + student.toString());
        try {
            //Thread.sleep(1000);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
