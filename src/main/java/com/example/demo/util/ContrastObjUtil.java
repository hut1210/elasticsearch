package com.example.demo.util;

import com.example.demo.annotation.PropertyName;
import com.example.demo.domain.PackingStrategy;
import com.example.demo.enums.CompareObjectEnum;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 16:22
 */
@Slf4j
public class ContrastObjUtil {

    public static void main(String[] args) {
        List<String> ignoreList = new ArrayList(Arrays.asList("serialVersionUID", "createTime", "updateTime", "createUser", "updateUser", "ts"));
        PackingStrategy packingStrategy1 = new PackingStrategy();
        packingStrategy1.setSellerName("1234");
        packingStrategy1.setPrimaryUnit(Byte.valueOf("1"));
        packingStrategy1.setBoxPacking(Byte.valueOf("1"));
        packingStrategy1.setUpdateUser("zs");
        packingStrategy1.setStatus(Byte.valueOf("0"));
        packingStrategy1.setDeptNo("CBU4398046511123");

        PackingStrategy packingStrategy2 = new PackingStrategy();
        packingStrategy2.setSellerName("12345");
        packingStrategy2.setPrimaryUnit(Byte.valueOf("0"));
        packingStrategy2.setBoxPacking(Byte.valueOf("0"));
        packingStrategy2.setUpdateUser("ls");
        packingStrategy2.setStatus(Byte.valueOf("1"));
        packingStrategy2.setDeptNo("CBU4398046511123");

        System.out.println(compareObj(packingStrategy1, packingStrategy2, ignoreList));
        compareObj3(packingStrategy1, packingStrategy2, ignoreList);
    }

    public static String compareObj(PackingStrategy oldPackingStrategy, PackingStrategy newPackingStrategy, List ignoreList) {
        List<String> list = new ArrayList<>();
        try {
            Class clazz = oldPackingStrategy.getClass();
            Field[] fields = newPackingStrategy.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (ignoreList.contains(field.getName())) {
                    continue;
                }
                PropertyName annotation = field.getAnnotation(PropertyName.class);
                if(annotation == null){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldPackingStrategy);
                Object o2 = getMethod.invoke(newPackingStrategy);
                if (o1 == null || o2 == null) {
                    continue;
                }
                Object beforeValue = null;
                Object afterValue = null;
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    if ("status".equals(field.getName())) {
                        beforeValue = "0".equals(String.valueOf(o1)) ? "停用" : "启用";
                        afterValue = "0".equals(String.valueOf(o2)) ? "停用" : "启用";
                    } else {
                        beforeValue = translate(o1);
                        afterValue = translate(o2);
                    }
                    list.add("修改了事业部【"+newPackingStrategy.getDeptNo()+"】的" + (annotation != null && annotation.name() != null ? annotation.name() : field.getName()) + ",【" + beforeValue + "】 改为 【" + afterValue + "】");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list.toString();
    }

    public static String compareObj2(PackingStrategy oldPackingStrategy, PackingStrategy newPackingStrategy, List ignoreList) {
        List<String> list = new ArrayList<>();
        try {
            Class clazz = oldPackingStrategy.getClass();
            Field[] fields = newPackingStrategy.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (ignoreList.contains(field.getName())) {
                    continue;
                }
                PropertyName annotation = field.getAnnotation(PropertyName.class);
                if(annotation == null){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldPackingStrategy);
                Object o2 = getMethod.invoke(newPackingStrategy);
                if (o1 == null || o2 == null) {
                    continue;
                }
                Object beforeValue = null;
                Object afterValue = null;
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    CompareObjectEnum.values();
                    if ("status".equals(field.getName())) {
                        beforeValue = "0".equals(String.valueOf(o1)) ? "停用" : "启用";
                        afterValue = "0".equals(String.valueOf(o2)) ? "停用" : "启用";
                    } else {
                        beforeValue = translate(o1);
                        afterValue = translate(o2);
                    }
                    list.add("修改了事业部【"+newPackingStrategy.getDeptNo()+"】的" + (annotation != null && annotation.name() != null ? annotation.name() : field.getName()) + ",【" + beforeValue + "】 改为 【" + afterValue + "】");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list.toString();
    }

    public static void compareObj3(PackingStrategy oldPackingStrategy, PackingStrategy newPackingStrategy, List ignoreList) {
        log.info("差异比较开始");
        CompareObjectHelper compareObjectHelper = CompareObjectHelper.getInstance();
        List<String> list = new ArrayList<>();
        try {
            compareObjectHelper.doAround(oldPackingStrategy, newPackingStrategy, new CompareObjectProcessor() {
                @Override
                public void doProcess(Field field, PropertyName annotation, Object o1, Object o2) {
                    Object beforeValue = null;
                    Object afterValue = null;
                    if ("status".equals(field.getName())) {
                        beforeValue = "0".equals(String.valueOf(o1)) ? "停用" : "启用";
                        afterValue = "0".equals(String.valueOf(o2)) ? "停用" : "启用";
                    } else {
                        beforeValue = translate(o1);
                        afterValue = translate(o2);
                    }
                    list.add("修改了事业部【"+newPackingStrategy.getDeptNo()+"】的" + (annotation != null && annotation.name() != null ? annotation.name() : field.getName()) + ",【" + beforeValue + "】 改为 【" + afterValue + "】");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);
    }
    private static Object translate(Object o) {
        if (o instanceof Integer || o instanceof Byte) {
            if ("0".equals(String.valueOf(o))) {
                return "否";
            }
            return "是";
        }
        return o;
    }
}
