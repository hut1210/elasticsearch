package com.example.demo.builder.helper;

import com.example.demo.annotation.PageNo;
import com.example.demo.annotation.PageSize;
import com.example.demo.builder.BuilderHelper;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-16 16:12
 */
public class PageHelper implements BuilderHelper<SearchSourceBuilder> {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private boolean operate = Boolean.FALSE;

    @Override
    public boolean isOperate() {
        return operate;
    }

    @Override
    public void fill(Field field, Annotation annotation, Object value) {
        if (annotation.annotationType() == PageNo.class) {
            if (value instanceof Integer) {
                pageNo = (Integer) value;
            } else {
                pageNo = Integer.valueOf(value.toString());
            }
            operate = Boolean.TRUE;
        } else if (annotation.annotationType() == PageSize.class) {
            if (value instanceof Integer) {
                pageSize = (Integer) value;
            } else {
                pageSize = Integer.valueOf(value.toString());
            }
            operate = Boolean.TRUE;
        }

    }

    @Override
    public void build(SearchSourceBuilder searchSource) {
        if (!isOperate()) {
            return;
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 0 || pageSize > 500) {
            pageSize = 10;
        }

        Integer form = (pageNo - 1) * pageSize;
        //如果大于1万,es是不支持的,则默认查询一万条以内的
        if (form > 10000) {
            form = 10000 - pageSize;
        }
        searchSource.from(form).size(pageSize);
    }
}
