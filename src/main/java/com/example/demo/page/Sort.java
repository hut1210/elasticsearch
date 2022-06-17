package com.example.demo.page;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:38
 */
public class Sort implements Serializable {

    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private static final Pattern orderPattern = Pattern.compile("^(asc|desc|ASC|DESC)");
    private static final Pattern sortFieldPattern = Pattern.compile("^[\\w+]+");
    /** 下划线 */
    private static final char SEPARATOR = '_';


    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序升降  asc or desc
     */
    private String order;

    public static Sort createSort(String column,boolean asc) {
        if(asc){
            return asc(column);
        }else{
            return desc(column);
        }
    }

    public static Sort asc(String column) {
        return new Sort(column, ASC);
    }

    public static Sort desc(String column) {
        return new Sort(column, DESC);
    }

    public boolean isAsc(){
        if(this.order != null && this.order.toLowerCase().equals(DESC)){
            return false;
        }
        return true;
    }

    public Sort(String field, String order) {
        //如果排序字段不合规，则默认id排序
        if (field != null && sortFieldPattern.matcher(field).matches()) {
            this.field = field;
        } else {
            this.field = "id";
        }
        if (order != null && orderPattern.matcher(order.toLowerCase()).matches()) {
            this.order = order;
        } else {
            this.order = ASC;
        }

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        if (field != null && sortFieldPattern.matcher(field).matches()) {
            this.field = field;
        } else {
            this.field = "id";
        }
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        if (order != null && orderPattern.matcher(order.toLowerCase()).matches()) {
            this.order = order;
        } else {
            this.order = ASC;
        }
    }

    public Sort() {
    }


    public String toUnderScoreCaseField() {
        return toUnderScoreCase(this.field);
    }

    /**
     * 驼峰转下划线命名
     */
    private    String toUnderScoreCase(String str) {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
