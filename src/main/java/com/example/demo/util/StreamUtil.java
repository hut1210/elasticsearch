package com.example.demo.util;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.LPreconditions;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:25
 */
public class StreamUtil {

    /**
     * @Description: 将List<Object>中的对象某一属性，单独提取成List,并进行去重和空值过滤，主要用于in的查询条件
     * list：待转换的对象集合
     * keyMapper：指定某一属性进行转换，例如 GoodsDto::getGoodsNo
     * @Author: hanpeng
     * @Date: 08/03/2022 10:12
     */
    public static <T, K> List<K> getQueryList(List<T> list, Function<? super T, ? extends K> keyMapper) {
        //获得指定字段的去重集合
        Set<K> querySet = list.stream().map(keyMapper).collect(Collectors.toSet());
        List<K> queryList = new ArrayList<>();
        //去除null值
        for (K entry : querySet) {
            if (entry == null) {
                continue;
            }
            queryList.add(entry);
        }
        //再转换为List
        return queryList;
    }

    /**
     * @Description: 该方法和上面的getQueryList()配合使用，用于将查询到的批量数据，根据某一属性转换为Map
     * queryList： getQueryList()的返回结果
     * resultList： 业务查询结果
     * keyMapper：指定某一属性，例如 GoodsDto::getGoodsNo
     * @Author: hanpeng
     * @Date: 08/03/2022 10:17
     */
    public static <T, K> Map<K, T> listToMap(List<K> queryList, List<T> resultList, Function<? super T, ? extends K> keyMapper){
        LPreconditions.checkParameterNotNull(queryList, "queryList集合为空！");
        LPreconditions.checkParameterNotNull(resultList, "resultList集合为空！");
        Map<K, T> resultMap = resultList.stream().collect(Collectors.toMap(keyMapper, s->s, (k1, k2) -> k2));

        if(resultList.size() == queryList.size()){
            return resultMap;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (K key : queryList) {
            if(!resultMap.containsKey(key)){
                stringBuilder.append(key).append(";");
            }
        }
        throw new BusinessException("未获取到部分信息: " + stringBuilder);
    }

    public static <T, K> Map<K, T> listToMap(List<T> resultList, Function<? super T, ? extends K> keyMapper){
        if(CollectionUtils.isEmpty(resultList)){
            return null;
        }
        return resultList.stream().collect(Collectors.toMap(keyMapper, s->s, (k1, k2) -> k2));
    }

    public static <T, K> void toGroup(Map<K, List<T>> map, T data, K key) {
        if(map == null){
            map = new HashMap<>();
        }
        List<T> list = map.get(key);
        if (list == null) {
            map.put(key, Lists.newArrayList(data));
            return;
        }
        list.add(data);
    }

    public static <T, K> Map<K, List<T>> toGroup(List<T> resultList, Function<? super T, ? extends K> keyMapper) {
        if(CollectionUtils.isEmpty(resultList)){
            return null;
        }
        return resultList.stream().collect(Collectors.groupingBy(keyMapper));
    }

    public static <T, K> void toGroupByOrder(Map<K, List<T>> map, T data, K key) {
        if(map == null){
            map = new LinkedHashMap<>();
        }
        List<T> list = map.get(key);
        if (list == null) {
            map.put(key, Lists.newArrayList(data));
            return;
        }
        list.add(data);
    }

    public static <T, K> List<T> mapToList(Map<K, List<T>> map) {
        if(CollectionUtils.isEmpty(map)){
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Map.Entry<K, List<T>> entry : map.entrySet()) {
            list.addAll(entry.getValue());
        }
        return list;
    }

    public static <T, K> List<K> fieldToList(List<T> list,  Function<? super T, ? extends K> keyMapper) {
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.stream().map(keyMapper).collect(Collectors.toList());
    }
}

