package com.example.demo.strategy.Impl;

import com.example.demo.strategy.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:49
 * 普通会员
 */
@Component
public class NormalHandler extends AbstractHandler {

    @Override
    public void handle() {
        System.out.println("普通会员业务开始。。。");
    }

    @Override
    public String type() {
        return "normal";
    }
}
