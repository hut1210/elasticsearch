package com.example.demo.builder.helper;

import com.example.demo.annotation.SortField;
import com.example.demo.builder.BuilderHelper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-16 16:14
 */
public class SortHelper implements BuilderHelper<SearchSourceBuilder> {

    private String name;

    private SortOrder sortOrder = SortOrder.ASC;

    private boolean operate = Boolean.FALSE;

    @Override
    public boolean isOperate() {
        return operate;
    }

    @Override
    public void fill(Field field, Annotation annotation, Object value) {
        if (annotation.annotationType() == com.example.demo.annotation.SortOrder.class) {
            if (SortOrder.DESC.toString().equals(value.toString().toLowerCase())) {
                sortOrder = SortOrder.DESC;
            } else {
                sortOrder = SortOrder.ASC;
            }
            operate = Boolean.TRUE;
        } else if (annotation.annotationType() == SortField.class) {
            name = String.valueOf(value);
            operate = Boolean.TRUE;
        }
    }

    @Override
    public void build(SearchSourceBuilder searchSource) {
        if (!isOperate()) {
            return;
        }

        if (name!=null&&name.length()>0) {
            searchSource.sort(name, sortOrder);
        }
    }
}
