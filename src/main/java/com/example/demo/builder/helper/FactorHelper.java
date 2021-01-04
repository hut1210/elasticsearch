package com.example.demo.builder.helper;

import com.example.demo.annotation.Factor;
import com.example.demo.builder.BuilderHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-20 21:07
 */
public class FactorHelper implements BuilderHelper<BoolQueryBuilder> {

    private boolean operate = Boolean.FALSE;

    private List<BoolQueryBuilder> queryBuilders;


    private boolean child;

    private Factor.Type type;

    public FactorHelper() {
        this.queryBuilders = new ArrayList<>();
    }

    @Override
    public boolean isOperate() {
        return operate;
    }

    @Override
    public void fill(Field field, Annotation annotation, Object value) {
        Factor factor = (Factor) annotation;
        type = factor.type();
        operate = true;
        if (value != null) {
            queryBuilders.add((BoolQueryBuilder) value);
        }
    }

    @Override
    public void build(BoolQueryBuilder boolQueryBuilder) {

        if (!operate || queryBuilders.isEmpty()) {
            return;
        }

        if (Factor.Type.NONE.equals(type)) {
            for (BoolQueryBuilder queryBuilder : queryBuilders) {

                if (CollectionUtils.isNotEmpty(queryBuilder.must())) {
                    boolQueryBuilder.must().addAll(queryBuilder.must());
                }
                if (CollectionUtils.isNotEmpty(queryBuilder.mustNot())) {
                    boolQueryBuilder.mustNot().addAll(queryBuilder.mustNot());
                }
                if (CollectionUtils.isNotEmpty(queryBuilder.should())) {
                    boolQueryBuilder.should().addAll(queryBuilder.should());
                }

                if (CollectionUtils.isNotEmpty(queryBuilder.filter())) {
                    boolQueryBuilder.filter().addAll(queryBuilder.filter());
                }
            }

        } else if (Factor.Type.MUST.equals(type)) {
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                boolQueryBuilder.must(queryBuilder);
            }

        } else if (Factor.Type.MUSTNOT.equals(type)) {
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                boolQueryBuilder.mustNot(queryBuilder);
            }
        } else if (Factor.Type.SHOULD.equals(type)) {
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                boolQueryBuilder.should(queryBuilder);
            }
        } else if (Factor.Type.FILTERMUST.equals(type)) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                builder.must(queryBuilder);
            }
            boolQueryBuilder.filter(builder);

        } else if (Factor.Type.FILTERMUSTNOT.equals(type)) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                builder.mustNot(queryBuilder);
            }
            boolQueryBuilder.filter(builder);

        } else if (Factor.Type.FILTERSHOULD.equals(type)) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            for (BoolQueryBuilder queryBuilder : queryBuilders) {
                builder.should(queryBuilder);
            }
            boolQueryBuilder.filter(builder);

        }
    }


}
