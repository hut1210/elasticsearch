package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hut
 * @date 2023/3/27 1:55 下午
 */
public class ReplaceTest {
    public static void main(String[] args) {
        String str = "库存商品/000002.测试合并商品2(规格型号2)";
        String temp = str.replace("000002.测试合并商品2(规格型号2)","000001");
        System.out.println(temp);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("3");
        list.add("3");
        list.add("4");
        list.add("10");
        list.add("5");
        list.add("6");
        list.add("2");
        //（o1,o2）->o1  代表有重复的取前面的  想取后面的  改成（o1,o2）->o2
        Map<String, String> newmap = list.stream().collect(Collectors.toMap(o -> o, Function.identity(), (o1, o2) -> o1));

        for (String integer1 : newmap.keySet()) {
            System.out.println(newmap.get(integer1));
        }

        String s = "2221013001";
        System.out.println(s.substring(7,10));
    }
}
