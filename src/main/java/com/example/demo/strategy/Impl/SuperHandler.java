package com.example.demo.strategy.Impl;

import com.example.demo.strategy.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:50
 * 超级会员
 */
@Component
public class SuperHandler extends AbstractHandler {

    @Override
    public void handle() {
        System.out.println("超级会员业务开始。。。");
    }

    @Override
    public String type() {
        return "super";
    }
}