package com.example.demo.enumtest;

import com.example.demo.enums.WarnTargetEnum;
import com.example.demo.enums.WarnTargetEnum1;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/30 15:49
 */
public class EnumTest {
    public static void main(String[] args) {
        //预警指标
        Integer warnTarget = 201;
        switch (warnTarget){
            case 201:
                System.out.println("拣货异常");
                break;
            case 202:
                System.out.println("分拣异常");
                break;
            case 203:
                System.out.println("复核异常");
                break;
            case 204:
                System.out.println("超时异常");
                break;
        }
    }
}
