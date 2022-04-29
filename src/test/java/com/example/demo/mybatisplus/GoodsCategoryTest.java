package com.example.demo.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.DemoApplication;
import com.example.demo.dto.GoodsCategoryDto;
import com.example.demo.entity.GoodsCategory;
import com.example.demo.mapper.GoodsCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/17 11:56
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class GoodsCategoryTest {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
    public void testInsert() {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryName("通讯");
        goodsCategory.setPid(0L);
        goodsCategory.setLevel(1);
        goodsCategoryMapper.insert(goodsCategory);

        GoodsCategory goodsCategory2 = new GoodsCategory();
        goodsCategory2.setCategoryName("电脑");
        goodsCategory2.setPid(goodsCategory.getId());
        goodsCategory2.setLevel(2);
        goodsCategoryMapper.insert(goodsCategory2);

        GoodsCategory goodsCategory3 = new GoodsCategory();
        goodsCategory3.setCategoryName("主机");
        goodsCategory3.setPid(goodsCategory2.getId());
        goodsCategory3.setLevel(3);
        goodsCategoryMapper.insert(goodsCategory3);
    }

    @Test
    public void testList() {
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(new QueryWrapper<>());
        //根节点集合
        List<GoodsCategoryDto> rootMenu = new ArrayList<>();
        for (GoodsCategory goodsCategory : goodsCategories) {
            if (goodsCategory.getPid() == 0) {
                GoodsCategoryDto goodsCategoryDto = new GoodsCategoryDto();
                BeanUtils.copyProperties(goodsCategory,goodsCategoryDto);
                rootMenu.add(goodsCategoryDto);
            }
        }
        for (GoodsCategoryDto goodsCategory : rootMenu){
            List<GoodsCategoryDto> childList = getChild(goodsCategory.getId(),goodsCategories);
            goodsCategory.setChildren(childList);
        }
        String s = JSON.toJSONString(rootMenu);
        System.out.println(s);
    }

    private List<GoodsCategoryDto> getChild(Long id, List<GoodsCategory> goodsCategories) {
        List<GoodsCategoryDto> childList = new ArrayList<>();
        for (GoodsCategory goodsCategory : goodsCategories){
            if(goodsCategory.getPid().equals(id)){
                GoodsCategoryDto goodsCategoryDto = new GoodsCategoryDto();
                BeanUtils.copyProperties(goodsCategory,goodsCategoryDto);
                childList.add(goodsCategoryDto);
            }
        }
        for (GoodsCategoryDto goodsCategory : childList){
            goodsCategory.setChildren(getChild(goodsCategory.getId(),goodsCategories));
        }
        if(childList.size() == 0){
            return new ArrayList<>();
        }
        return childList;
    }

    @Test
    public void testList2() {
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(new QueryWrapper<>());
        //根节点集合
        List<GoodsCategory> rootMenu = new ArrayList<>();
        for (GoodsCategory goodsCategory : goodsCategories) {
            if (goodsCategory.getPid() == 0) {
                rootMenu.add(goodsCategory);
            }
        }
        for (GoodsCategory goodsCategory : rootMenu){
            List<GoodsCategory> childList = getChild2(goodsCategory.getId(),goodsCategories);
            goodsCategory.setChildren(childList);
        }
        String s = JSON.toJSONString(rootMenu);
        System.out.println(s);
    }

    private List<GoodsCategory> getChild2(Long id, List<GoodsCategory> goodsCategories) {
        List<GoodsCategory> childList = new ArrayList<>();
        for (GoodsCategory goodsCategory : goodsCategories){
            if(goodsCategory.getPid().equals(id)){
                childList.add(goodsCategory);
            }
        }
        for (GoodsCategory goodsCategory : childList){
            goodsCategory.setChildren(getChild2(goodsCategory.getId(),goodsCategories));
        }
        if(childList.size() == 0){
            return new ArrayList<>();
        }
        return childList;
    }
}
