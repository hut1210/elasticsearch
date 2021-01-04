package com.example.demo.builder.helper;


import com.example.demo.annotation.*;
import com.example.demo.builder.BuilderHelper;
import com.example.demo.util.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.joor.Reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-16 15:44
 */
@Slf4j
public class QueryHelper implements BuilderHelper<BoolQueryBuilder> {

    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static final ThreadLocal<DateFormat> DTF = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private List<QueryBuilder> must;
    private List<QueryBuilder> notMust;
    private List<QueryBuilder> should;
    private List<QueryBuilder> filterMust;
    private List<QueryBuilder> filterNotMust;
    private List<QueryBuilder> filterShould;
    private List<QueryBuilder> filterScript;

    private boolean operate = Boolean.FALSE;

    @Override
    public boolean isOperate() {
        return operate;
    }

    public QueryHelper() {
        this.must = new ArrayList<>();
        this.notMust = new ArrayList<>();
        this.filterMust = new ArrayList<>();
        this.filterNotMust = new ArrayList<>();
        this.should = new ArrayList<>();
        this.filterShould = new ArrayList<>();
        this.filterScript= new ArrayList<>();
    }

    @Override
    public void fill(Field field, Annotation annotation, Object value) {

        QueryBuilder builder = createQueryBuilder(field, annotation, value);
        if (builder == null) {
            return;
        }
        if (null != annotation.annotationType().getAnnotation(Must.class)) {
            must.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(MustNot.class)) {
            notMust.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(FilterMust.class)) {
            filterMust.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(FilterMustNot.class)) {
            filterNotMust.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(Should.class)) {
            should.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(FilterShould.class)) {
            filterShould.add(builder);
            operate = Boolean.TRUE;
        } else if (null != annotation.annotationType().getAnnotation(FilterScript.class)) {
            filterScript.add(builder);
            operate = Boolean.TRUE;
        } else {
            log.warn("{}注解无匹配规则", annotation.annotationType().getName());
        }

    }

    public QueryBuilder createQueryBuilder(Field field, Annotation annotation, Object value) {

        if (annotation.annotationType().getSimpleName().contains("MatchPhrase")) {
            Reflect matchPhrase = Reflect.on(annotation);
            String fieldName = getFieldName(matchPhrase.call("fieldName").get(), field.getName());
            MatchPhraseQueryBuilder query = QueryBuilders.matchPhraseQuery(fieldName, value);
            String analyzer = matchPhrase.call("analyzer").get();
            if (analyzer.trim().length() > 0) {
                query.analyzer(analyzer.trim());
            }
            int slop = matchPhrase.call("slop").get();
            if (slop != -1) {
                query.slop(slop);
            }
            return query;
        } else if (annotation.annotationType().getSimpleName().contains("Match")) {
            Reflect match = Reflect.on(annotation);
            String fieldName = getFieldName(match.call("fieldName").get(), field.getName());
            return QueryBuilders.matchQuery(fieldName, value).operator(Operator.fromString(match.call("operator").call("value").get()));
        } else if (annotation.annotationType().getSimpleName().contains("Terms")) {
            Reflect terms = Reflect.on(annotation);
            List<Object> values;
            if (value instanceof Collection) {
                values = new ArrayList<>();
                for (Object o : ((Collection) value)) {
                    values.add(o);
                }
            } else {
                String split = terms.call("split").get();
                if (StringUtils.isNotBlank(split)) {
                    values = Arrays.asList(value.toString().split(split));
                } else {
                    values = Arrays.asList(value);
                }

            }


            String[] fieldNames = terms.call("fieldName").get();
            if (fieldNames == null || fieldNames.length == 0) {
                String fieldName = getFieldName(null, field.getName());
                return QueryBuilders.termsQuery(fieldName, values);
            } else if (fieldNames.length == 1) {
                String fieldName = getFieldName(fieldNames[0], field.getName());
                return QueryBuilders.termsQuery(fieldName, values);
            } else {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                for (int i = 0; i < fieldNames.length; i++) {
                    String fieldName = getFieldName(fieldNames[i], field.getName());
                    boolQueryBuilder.should(QueryBuilders.termsQuery(fieldName, values));
                }
                return boolQueryBuilder;
            }

        } else if (annotation.annotationType().getSimpleName().contains("Term")) {
            Reflect term = Reflect.on(annotation);
            String[] fieldNames = term.call("fieldName").get();
            if (fieldNames == null || fieldNames.length == 0) {
                String fieldName = getFieldName(null, field.getName());
                return QueryBuilders.termQuery(fieldName, value);
            } else if (fieldNames.length == 1) {
                String fieldName = getFieldName(fieldNames[0], field.getName());
                return QueryBuilders.termQuery(fieldName, value);
            } else {

                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

                for (int i = 0; i < fieldNames.length; i++) {
                    String fieldName = getFieldName(fieldNames[i], field.getName());
                    boolQueryBuilder.should(QueryBuilders.termQuery(fieldName, value));
                }
                return boolQueryBuilder;
            }
        } else if (annotation.annotationType().getSimpleName().contains("Wildcard")) {
            Reflect wildcard = Reflect.on(annotation);
            String fieldName = getFieldName(wildcard.call("fieldName").get(), field.getName());
            return QueryBuilders.wildcardQuery(fieldName, value.toString());
        } else if (annotation.annotationType().getSimpleName().contains("Exists")) {
            Reflect exists = Reflect.on(annotation);
            String fieldName = getFieldName(exists.call("fieldName").get(), field.getName());
            return QueryBuilders.existsQuery(fieldName);
        } else if (annotation.annotationType().getSimpleName().contains("Range")) {
            Reflect exists = Reflect.on(annotation);
            String fieldName = getFieldName(exists.call("fieldName").get(), field.getName());
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(fieldName);

            String format = exists.call("format").call("toString").get();
            if ("DATE".equals(format)) {
                try {
                    if (value instanceof Date) {
                        value = ((Date) value).getTime();
                    } else {
                        value = DF.get().parse(value.toString()).getTime();
                    }
                } catch (Exception e) {
                    log.error("{}转换时间错误", value);
                }
            } else if ("DATETIME".equals(format)) {
                try {
                    if (value instanceof Date) {
                        value = ((Date) value).getTime();
                    } else {
                        value = DTF.get().parse(value.toString()).getTime();
                    }
                } catch (Exception e) {
                    log.error("{}转换时间错误", value);
                }
            }

            String type = exists.call("type").call("toString").get();
            if ("GT".equals(type)) {
                rangeQuery.gt(value);
            } else if ("GTE".equals(type)) {
                rangeQuery.gte(value);
            } else if ("LT".equals(type)) {
                rangeQuery.lt(value);
            } else if ("LTE".equals(type)) {
                rangeQuery.lte(value);
            }
            return rangeQuery;
        } else if (annotation.annotationType().getSimpleName().contains("Script")) {
            Reflect script = Reflect.on(annotation);
            String paramCode = script.call("paramCode").get();
            String source = script.call("source").get();
            String lang = script.call("lang").get();

            Map<String, Object> params = new HashMap<>();
            if (StringUtils.isNotBlank(paramCode)) {
                params.put(paramCode, value);
            }
            ScriptParams[] scriptParams = script.call("params").get();
            if (scriptParams.length > 0) {
                for (ScriptParams scriptParam : scriptParams) {
                    String paramValue = scriptParam.value();
                    if (scriptParam.type() == ScriptParams.Type.INTEGER) {
                        params.put(scriptParam.key(), Long.valueOf(paramValue));
                    } else {
                        params.put(scriptParam.key(), paramValue);
                    }
                }
            }

            Script esScript = new Script(ScriptType.INLINE, lang, source, params);
            return QueryBuilders.scriptQuery(esScript);

        } else {
            log.warn("{}注解无匹配规则", annotation.annotationType().getName());
        }
        return null;

    }

    @Override
    public void build(BoolQueryBuilder boolQueryBuilder) {

        if (!isOperate()) {
            return;
        }

        if (CollectionUtils.isNotEmpty(must)) {
            for (QueryBuilder queryBuilder : must) {
                boolQueryBuilder.must(queryBuilder);
            }
        }
        if (CollectionUtils.isNotEmpty(notMust)) {
            for (QueryBuilder queryBuilder : notMust) {
                boolQueryBuilder.mustNot(queryBuilder);
            }
        }
        if (CollectionUtils.isNotEmpty(should)) {
            for (QueryBuilder queryBuilder : should) {
                boolQueryBuilder.should(queryBuilder);
            }
        }

        if (CollectionUtils.isNotEmpty(filterMust) || CollectionUtils.isNotEmpty(filterNotMust)
                || CollectionUtils.isNotEmpty(filterShould) || CollectionUtils.isNotEmpty(filterScript)) {
            BoolQueryBuilder filterBoolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.filter(filterBoolQueryBuilder);
            if (CollectionUtils.isNotEmpty(filterMust)) {
                for (QueryBuilder queryBuilder : filterMust) {
                    filterBoolQueryBuilder.must(queryBuilder);
                }
            }
            if (CollectionUtils.isNotEmpty(filterNotMust)) {
                for (QueryBuilder queryBuilder : filterNotMust) {
                    filterBoolQueryBuilder.mustNot(queryBuilder);
                }
            }
            if (CollectionUtils.isNotEmpty(filterShould)) {
                for (QueryBuilder queryBuilder : filterShould) {
                    filterBoolQueryBuilder.should(queryBuilder);
                }
            }
            if (CollectionUtils.isNotEmpty(filterScript)){
                for (QueryBuilder queryBuilder : filterScript) {
                    boolQueryBuilder.filter(queryBuilder);
                }
            }
        }

    }

    private String getFieldName(String annotationFieldName, String fieldName) {
        if (StringUtils.isNotBlank(annotationFieldName)) {
            return CaseFormat.toUnderscore(annotationFieldName);
        } else {
            return CaseFormat.toUnderscore(fieldName);
        }
    }
}
