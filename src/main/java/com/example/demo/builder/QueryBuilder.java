package com.example.demo.builder;

import com.example.demo.annotation.Factor;
import com.example.demo.annotation.Page;
import com.example.demo.annotation.Query;
import com.example.demo.annotation.Sort;
import com.example.demo.builder.helper.*;
import com.example.demo.condition.TermsAggregationCondition;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.LoggingDeprecationHandler;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/4 14:33
 */
@Slf4j
public class QueryBuilder {

    private static final String annotationPackage = "com.example.demo.annotation";


    public static String buildString(Object object) {
        return build(object).toString();
    }

    public static SearchSourceBuilder build(Object object) {
        SearchSourceBuilder searchSource = new SearchSourceBuilder();
        searchSource.query(setSearchSource(searchSource, object));
        return searchSource;
    }

    public static SearchSourceBuilder buildGroup(Object object, TermsAggregationCondition... conditions) {
        SearchSourceBuilder searchSource = new SearchSourceBuilder();
        searchSource.query(setSearchSource(searchSource, object));
        setGroupSearchSource(searchSource, conditions);

        return searchSource;
    }

    public static SearchSourceBuilder fromSearchSourceBuilder(String query) {
        try {
            SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.fromXContent(createParser(JsonXContent.jsonXContent, query));
            return searchSourceBuilder;
        } catch (Exception e) {
            log.error("from查询条件异常,query:{}", query, e);
            throw new RuntimeException("from查询条件异常", e);
        }
    }


    public static BoolQueryBuilder setSearchSource(SearchSourceBuilder searchSource, Object object) {

        if (isBasic(object)) {
            return null;
        }

        List<Field> fields = getAllFields(object);

        PageHelper pageHelper = new PageHelper();
        QueryHelper queryHelper = new QueryHelper();
        SortHelper sortHelper = new SortHelper();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException ignore) {
                continue;
            }
            if (nullOrEmpty(value)) {
                continue;
            }

            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                try {
                    if (Factor.class == annotation.annotationType()) {
                        if (isBasic(value)) {
                            continue;
                        } else if (value instanceof Collection) {
                            FactorHelper factorHelper = new FactorHelper();
                            for (Object o : ((Collection) value)) {
                                BoolQueryBuilder factorBoolQueryBuilder = setSearchSource(searchSource, o);
                                factorHelper.fill(field, annotation, factorBoolQueryBuilder);
                            }
                            factorHelper.build(boolQueryBuilder);
                        } else {
                            FactorHelper factorHelper = new FactorHelper();
                            BoolQueryBuilder factorBoolQueryBuilder = setSearchSource(searchSource, value);
                            factorHelper.fill(field, annotation, factorBoolQueryBuilder);
                            factorHelper.build(boolQueryBuilder);

                        }
                    } else if (getTopAnnotation(annotation).annotationType() == Page.class) {
                        pageHelper.fill(field, annotation, value);
                        break;
                    } else if (getTopAnnotation(annotation).annotationType() == Sort.class) {
                        sortHelper.fill(field, annotation, value);
                        break;
                    } else if (getTopAnnotation(annotation).annotationType() == Query.class) {
                        queryHelper.fill(field, annotation, value);
                        break;
                    }
                } catch (Exception e) {
                    log.warn("analyse annotation failed, field: {}, annotation: {}, value:{}, cause: {}",
                            field.getName(), annotation.getClass(), value, e.getStackTrace());
                }
            }
        }
        pageHelper.build(searchSource);
        sortHelper.build(searchSource);
        queryHelper.build(boolQueryBuilder);
        return boolQueryBuilder;
    }

    private static void setGroupSearchSource(SearchSourceBuilder searchSource,TermsAggregationCondition... conditions) {
        if (conditions != null && conditions.length > 0) {
            List<TermsAggregationCondition> aggs = Arrays.asList(conditions);
            AggregationHelper.setTermsAggregation(searchSource, aggs);
        }
    }

    private static List<Field> getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    private static boolean nullOrEmpty(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }
        try {
            if (value instanceof Collection) {
                return ((Collection) value).isEmpty();
            } else {
                return value.toString().isEmpty();
            }
        } catch (Exception ignored) {
            return true;
        }
    }

    private static boolean isBasic(Object o) {
        if (o instanceof String || o instanceof Long || o instanceof Integer || o instanceof Byte ||
                o instanceof Short || o instanceof Float || o instanceof Double || o instanceof Boolean || o instanceof Character) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private static Annotation getTopAnnotation(Annotation annotation) {

        Annotation[] annotations = annotation.annotationType().getAnnotations();
        if (annotation != null && annotations.length > 0) {
            for (Annotation annotation1 : annotations) {
                if (annotation1.annotationType().getPackage().getName().startsWith(annotationPackage)) {
                    return getTopAnnotation(annotation1);
                }
            }
        }
        return annotation;
    }

    private static XContentParser createParser(XContent xContent, String data) throws IOException {
        return xContent.createParser(xContentRegistry(), LoggingDeprecationHandler.INSTANCE, data);
    }

    private static NamedXContentRegistry xContentRegistry() {
        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
        return new NamedXContentRegistry(searchModule.getNamedXContents());
    }
}
