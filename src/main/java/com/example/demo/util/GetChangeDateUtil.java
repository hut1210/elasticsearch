package com.example.demo.util;

import com.example.demo.domain.PackingStrategy;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 15:45
 */
public class GetChangeDateUtil {

    public static void main(String[] args) {
        PackingStrategy packingStrategy1 = new PackingStrategy();
        packingStrategy1.setPrimaryUnit(Byte.valueOf("1"));
        packingStrategy1.setBoxPacking(Byte.valueOf("1"));

        PackingStrategy packingStrategy2 = new PackingStrategy();
        packingStrategy2.setPrimaryUnit(Byte.valueOf("0"));
        packingStrategy2.setBoxPacking(Byte.valueOf("1"));
        System.out.println(compareFields(packingStrategy1,packingStrategy2,null));
    }

    /**
     * <p>Title: compareFields</p>
     * <p>Description: </p>  比较两个实体属性值
     *
     * @param obj1
     * @param obj2
     * @param ignoreArr 忽略的字段
     * @return
     */
    public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] ignoreArr) {
        try {
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<String> ignoreList = null;
            if (ignoreArr != null && ignoreArr.length > 0) {
                // array转化为list
                ignoreList = Arrays.asList(ignoreArr);
            }
            if (obj1.getClass() == obj2.getClass()) {// 只有两个对象都是同一类型的才有可比性
                Class clazz = obj1.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                        Object.class).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {// 这里就是所有的属性了
                    String name = pd.getName();// 属性名
                    if (ignoreList != null && ignoreList.contains(name)) {// 如果当前属性选择忽略比较，跳到下一次循环
                        continue;
                    }
                    Method readMethod = pd.getReadMethod();// get方法
                    // 在obj1上调用get方法等同于获得obj1的属性值
                    Object o1 = readMethod.invoke(obj1);
                    // 在obj2上调用get方法等同于获得obj2的属性值
                    Object o2 = readMethod.invoke(obj2);
                    if (o1 instanceof Timestamp) {
                        o1 = new Date(((Timestamp) o1).getTime());
                    }
                    if (o2 instanceof Timestamp) {
                        o2 = new Date(((Timestamp) o2).getTime());
                    }
                    if (o1 == null && o2 == null) {                               //o1 旧     o2新    如果两个都是空就跳过
                        continue;
                    } else if (o1 == null && o2 != null) {                         //如果O1旧的没有     O2新的有
                        List<Object> list = new ArrayList<Object>();
                        list.add("旧()");
                        list.add("新" + "(" + o2+")");
                        map.put(name, list);
                        continue;
                    }
                    if (!o1.equals(o2)) {// 比较这两个值是否相等,不等就可以放入map了                             //如果旧的和新的不相等
                        List<Object> list = new ArrayList<Object>();
                        if (o1 == null) {
                            list.add("旧()");
                        } else {
                            list.add("旧" + "(" + o1+")");
                        }
                        if (o2 == null) {
                            list.add("新()");
                        } else {
                            list.add("新" + "(" + o2+")");
                        }
                        map.put(name, list);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
