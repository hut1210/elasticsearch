package com.example.demo.cacheTemplate;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 15:29
 */
public class TestBloom {
    public static void main(String[] args) {
        /* 第一个入参：Funnels 提供如何把一个具体的对象类型转化为Java基本数据类型
           第二个入参：expectedInsertions 预估要插入数据量
           第三个入参：预期可接受的误判率，必须大于0（不能等于0）
         */
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 100_000, 0.001);
        for (int i = 0; i < 100_000; i++) {
            bloomFilter.put("element"+i ); // 循环放入不同的元素
        }

        int exist_count = 0 ; // 记录 bloom过滤器认为存在的数量
        for (int j = 0; j <= 9999 ; j++) {
            if (bloomFilter.mightContain("XXXXXXXX" + j)) {  // 故意从Bloom过滤器取1万个根本不存在的元素
                exist_count++;
            }
        }

        System.out.println("误判（Bloom认为存在的）的数量：" + exist_count);
    }
}
