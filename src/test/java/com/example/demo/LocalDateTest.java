package com.example.demo;

import com.example.demo.config.AppConfig;
import com.example.demo.strategy.Impl.StockWarnService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/14 17:25
 */
public class LocalDateTest {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean(StockWarnService.class));
    }
}
