package com.example.demo;

import com.example.demo.dto.SalesItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 14:56
 */
public class Demo {
    public static void main(String[] args) {

        Demo demo = new Demo();

        //获取string类型
        List<String> array = new ArrayList<String>();
        array.add("test");
        array.add("doub");
        String str = demo.getListFisrt(array);
        System.out.println(str);

        //获取nums类型
        List<Integer> nums = new ArrayList<Integer>();
        nums.add(12);
        nums.add(13);

        Integer num = demo.getListFisrt(nums);
        System.out.println(num);

        List<SalesItemDto> list = new ArrayList<>();
        SalesItemDto salesItemDto = new SalesItemDto();
        salesItemDto.setSpShopGoodsNo("1234");
        list.add(salesItemDto);

        SalesItemDto salesItemDto1 = new SalesItemDto();
        salesItemDto1.setSpShopGoodsNo("1111");
        list.add(salesItemDto1);

        String collect = list.stream().map(SalesItemDto::getSpShopGoodsNo).collect(Collectors.joining(","));
        System.out.println(collect);
    }

    /**
     * 这个<T> T 可以传入任何类型的List
     * 参数T
     *     第一个 表示是泛型
     *     第二个 表示返回的是T类型的数据
     *     第三个 限制参数类型为T
     * @param data
     * @return
     */
    private <T> T getListFisrt(List<T> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        return data.get(0);
    }
}
