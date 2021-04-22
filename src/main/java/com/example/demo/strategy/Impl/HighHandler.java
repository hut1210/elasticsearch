package com.example.demo.strategy.Impl;

import com.example.demo.strategy.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:49
 * 高级会员
 */
@Component
public class HighHandler extends AbstractHandler {

    @Override
    public void handle() {
        System.out.println("高级会员业务开始。。。");
    }

    @Override
    public String type() {
        return "high";
    }
}