package com.example.demo;

import com.example.demo.enums.InBoundMarkEnum;
import com.example.demo.util.MarkUtils;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 15:46
 */
public class MarkUtilsTest {

    public static void main(String[] args) {
        String orderMark = "00000010010001110110000110100000000000100000000000000000000000000000000000000000000000000000" +
                "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        MarkUtils markUtils = new MarkUtils(orderMark);
        //自动生成执行单
        if (markUtils.isMark(InBoundMarkEnum.MARK_9_1)) {
            System.out.println("自动生成执行单");
        }else {
            System.out.println("不自行生成执行单");
        }

        boolean preSale = markUtils.isMark(InBoundMarkEnum.MARK_6_1);
        System.out.println("preSale ===> "+preSale);

    }
}
