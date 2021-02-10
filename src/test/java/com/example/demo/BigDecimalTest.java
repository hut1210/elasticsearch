package com.example.demo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/9 16:15
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i=0;i<4;i++){
            totalAmount = totalAmount.add(new BigDecimal(i));
        }
        System.out.println(totalAmount);

        Map map = new HashMap<>();
        map.put("京东自营",100);
        map.put("终端共配",200);
        map.put("0",100);
        map.put("1",200);

        map.keySet().forEach(key->{
            System.out.println(key+"  "+map.get(key));
                }
        );
    }
}
