package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

/**
 * @author hut
 * @date 2022/10/19 11:02 上午
 */
@Configuration
public class RabbitMqConfig {

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        // TODO 封装RabbitTemplate
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate(rabbitTemplate);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        // 使用json序列化器来序列化消息，发送消息时，消息对象会被序列化成json格式
        return new Jackson2JsonMessageConverter();
    }

    //@PostConstruct   // (MyRabbitConfig对象创建完成以后，执行这个方法)
    public void initRabbitTemplate(RabbitTemplate rabbitTemplate) {
        /**
         * 发送消息触发confirmCallback回调
         * @param correlationData：当前消息的唯一关联数据（如果发送消息时未指定此值，则回调时返回null）
         * @param ack：消息是否成功收到（ack=true，消息抵达Broker）
         * @param cause：失败的原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            /*System.out.println("发送消息触发confirmCallback回调" +
                    "\ncorrelationData ===> " + correlationData +
                    "\nack ===> " + ack + "" +
                    "\ncause ===> " + cause);
            System.out.println("=================================================");*/
        });

        rabbitTemplate.setMandatory(true);
        /**
         * 消息未到达队列触发returnCallback回调
         * 只要消息没有投递给指定的队列，就触发这个失败回调
         * @param message：投递失败的消息详细信息
         * @param replyCode：回复的状态码
         * @param replyText：回复的文本内容
         * @param exchange：接收消息的交换机
         * @param routingKey：接收消息的路由键
         */
        rabbitTemplate.setReturnsCallback((returnedMessage)->{
            /*System.out.println("消息未到达队列触发returnCallback回调" +
                    "\nmessage ===> " + returnedMessage.getMessage() +
                    "\nreplyCode ===> " + returnedMessage.getReplyCode() +
                    "\nreplyText ===> " + returnedMessage.getReplyText() +
                    "\nexchange ===> " + returnedMessage.getExchange() +
                    "\nroutingKey ===> " + returnedMessage.getRoutingKey());*/
        });
    }

    @Bean
    public Queue queue1(){
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        return new Queue("hello-java-queue1",true,false,false,null);
    }

    @Bean
    public Queue queue2(){
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        return new Queue("hello-java-queue2",true,false,false,null);
    }

    @Bean
    public Exchange topicExchange(){
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("hello-java-topic-exchange",true,false,null);
    }

    @Bean
    public Binding binding1(){
        //String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object
        return new Binding("hello-java-queue1",Binding.DestinationType.QUEUE,"hello-java-topic-exchange","hello.java.test1",null);
    }

    @Bean
    public Binding binding2(){
        //String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object
        return new Binding("hello-java-queue2",Binding.DestinationType.QUEUE,"hello-java-topic-exchange","hello.java.test0",null);
    }
}
