package com.example.demo.util;

import com.example.demo.annotation.PropertyName;
import com.example.demo.domain.LogData;
import com.example.demo.domain.PackUom;
import com.example.demo.domain.PackingStrategy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/9 10:56
 */
public class PackUomCompareUtil {

    public static void main(String[] args) {
        List<String> ignoreList = new ArrayList(Arrays.asList("serialVersionUID", "createTime", "updateTime", "createUser", "updateUser", "ts"));
        PackUom oldPackUom = new PackUom();
        oldPackUom.setOutUomNo("EA");
        oldPackUom.setOutUomName("瓶");
        oldPackUom.setSellerNo("CCP0000000000029");
        PackUom newPackUom = new PackUom();
        newPackUom.setOutUomNo("each");
        newPackUom.setOutUomName("个");
        newPackUom.setSellerNo("CCP0000000000029");
        System.out.println(compareObj(oldPackUom, newPackUom, ignoreList));
    }

    private static String compareObj(PackUom oldPackUom, PackUom newPackUom, List ignoreList) {
        List<String> list = new ArrayList<>();
        try {
            Class clazz = oldPackUom.getClass();
            Field[] fields = newPackUom.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (ignoreList.contains(field.getName())) {
                    continue;
                }
                PropertyName annotation = field.getAnnotation(PropertyName.class);
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldPackUom);
                Object o2 = getMethod.invoke(newPackUom);
                if (o1 == null || o2 == null) {
                    continue;
                }
                Object beforeValue = null;
                Object afterValue = null;
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    if ("status".equals(field.getName())) {
                        beforeValue = "0".equals(String.valueOf(o1)) ? "停用" : "启用";
                        afterValue = "0".equals(String.valueOf(o2)) ? "停用" : "启用";
                    }else{
                        beforeValue = String.valueOf(o1);
                        afterValue = String.valueOf(o2);
                    }
                    list.add("修改了"+newPackUom.getSellerNo()+"的" + (annotation != null && annotation.name() != null ? annotation.name() : field.getName()) + ",【" + beforeValue + "】 改为 【" + afterValue + "】");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list.toString();
    }
}
