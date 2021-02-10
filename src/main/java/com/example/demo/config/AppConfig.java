package com.example.demo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/28 21:21
 */
@Configuration
@ComponentScan("com.example.demo.strategy.Impl")
public class AppConfig {

    /*@Bean
    public TransportClient transportClient() throws UnknownHostException {
        TransportClient client=new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        return client;
    }*/
}
