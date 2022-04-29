package com.example.demo.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.DemoApplication;
import com.example.demo.dto.PackingDto;
import com.example.demo.entity.Packing;
import com.example.demo.entity.PackingItem;
import com.example.demo.mapper.PackingItemMapper;
import com.example.demo.mapper.PackingMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/15 15:10
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class PackingTest {

    @Resource
    private PackingMapper packingMapper;

    @Resource
    private PackingItemMapper packingItemMapper;

    @Test
    //@Transactional
    public void testInsert() {
        Packing packing = new Packing();
        packing.setEnterpriseNo("E001");
        packing.setEnterpriseName("测试企业名称");
        packing.setPackNo("SGPU20400000004");
        packing.setOutPackNo("1*2*3");
        packing.setPackName("商家企业包装策略");
        int insert = packingMapper.insert(packing);

        PackingItem packingItem = new PackingItem();
        packingItem.setPackingId(packing.getId());
        packingItem.setUomLevel(1);
        packingItem.setUomNo("EA");
        packingItem.setOutUomNo("EA");
        packingItem.setOutUomName("箱");
        packingItem.setMainUnitQty(1);
        packingItemMapper.insert(packingItem);

        PackingItem packingItem2 = new PackingItem();
        packingItem2.setPackingId(packing.getId());
        packingItem2.setUomLevel(2);
        packingItem2.setUomNo("IP");
        packingItem2.setOutUomNo("IP");
        packingItem2.setOutUomName("包");
        packingItem2.setMainUnitQty(1);
        packingItemMapper.insert(packingItem2);
    }

    @Test
    public void testQuery() {
        List<Packing> packingList = packingMapper.selectList(new QueryWrapper<>());
        packingList.forEach(System.out::println);
    }

    @Test
    public void testQueryDetail() {
        Packing packing = packingMapper.selectById(1494129580973539330L);
        System.out.println(packing);
        LambdaQueryWrapper<PackingItem> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(PackingItem::getPackingId, 1494129580973539330L);
        List packingItemList = packingItemMapper.selectList(lambdaQueryWrapper);
        packingItemList.forEach(System.out::println);

        PackingDto packingDto = new PackingDto();
        BeanUtils.copyProperties(packing, packingDto);
        packingDto.setPackingItemList(packingItemList);
        System.out.println(packingDto);

        String s = JSON.toJSONString(packingDto);
        System.out.println(s);

    }

    @Test
    public void testUpdate() {
        /*Packing packing = new Packing();
        packing.setId(1493487256706805761L);
        packing.setStatus(Byte.valueOf("1"));
        packingMapper.updateById(packing);*/

        PackingItem packingItem = new PackingItem();
        packingItem.setId(1494129581292306433L);
        packingItem.setLength(new BigDecimal("1"));
        packingItem.setWidth(new BigDecimal("1"));
        packingItem.setHeight(new BigDecimal("1"));
        //packingItemMapper.updateById(packingItem);

        List<PackingItem> packingItemList = new ArrayList<>();
        packingItemList.add(packingItem);

        LambdaQueryWrapper<PackingItem> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(PackingItem::getPackingId, 1494129580973539330L);
        List<PackingItem> packingItemList1 = packingItemMapper.selectList(lambdaQueryWrapper);

        List<PackingItem> collect = packingItemList1.stream().filter(pi -> packingItemList.stream().map(PackingItem::getId).noneMatch(
                id -> Objects.equals(pi.getId(), id))).collect(Collectors.toList());

        collect.forEach(System.out::println);

    }

    @Test
    public void testDelete() {
        packingMapper.deleteById(1494129580973539330L);
    }

}
