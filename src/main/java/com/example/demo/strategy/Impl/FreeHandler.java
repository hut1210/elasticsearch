package com.example.demo.strategy.Impl;

import com.example.demo.strategy.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:48
 * 免费用户
 */
@Component
public class FreeHandler extends AbstractHandler {

    @Override
    public void handle() {
        System.out.println("免费用户业务开始。。。");
    }

    @Override
    public String type() {
        return "free";
    }
}