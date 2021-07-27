package com.example.demo.esScroll;

import com.alibaba.fastjson.JSON;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.StudentCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/7/9 14:31
 */
@SpringBootTest
@Slf4j
public class ScrollTest {

    @Resource
    private EsQueryService esQueryService;

    @Test
    public void testStudent(){
        StudentCondition condition = new StudentCondition();
        SearchSourceBuilder searchSourceBuilder = com.example.demo.builder.QueryBuilder.build(condition);
        searchSourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        System.out.println(searchSourceBuilder);
        //SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder("student_index", "_doc", searchSourceBuilder);
        List<SearchHit> temp = new ArrayList<>();
        SearchResponse searchResponse = esQueryService.query(new String[]{"student_index"},new String[]{"_doc"},searchSourceBuilder,true);
        do {
            temp.addAll(Arrays.asList(searchResponse.getHits().getHits()));
            searchResponse = esQueryService.scroll(searchResponse.getScrollId());
        } while (searchResponse.getHits().getHits().length > 0);
        System.out.println("searchResponse=====2"+searchResponse);
        System.out.println("searchResponse====="+searchResponse.getHits().getTotalHits().value);
        System.out.println(temp.size());

        esQueryService.ClearScrollRequest(searchResponse.getScrollId());
    }

    @Test
    public void testStudent2(){
        StudentCondition condition = new StudentCondition();
        TermsAggregationCondition condition1 = new TermsAggregationCondition("name");
        condition1.sum("sumage", "age");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder searchSourceBuilder = com.example.demo.builder.QueryBuilder.buildGroup(condition, condition1);
        //SearchSourceBuilder searchSourceBuilder = com.example.demo.builder.QueryBuilder.build(condition);
        searchSourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        System.out.println(searchSourceBuilder);
        //SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder("student_index", "_doc", searchSourceBuilder);
        List<SearchHit> temp = new ArrayList<>();
        SearchResponse searchResponse = esQueryService.query(new String[]{"student_index"},new String[]{"_doc"},searchSourceBuilder,true);
        do {
            if(searchResponse!=null && searchResponse.getAggregations()!=null&& searchResponse.getAggregations().getAsMap()!=null){
                Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
                if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                    ParsedTerms terms = (ParsedTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
                    if (CollectionUtils.isNotEmpty(terms.getBuckets())) {
                        for (Terms.Bucket bucket : terms.getBuckets()) {
                            String pk = bucket.getKeyAsString();
                            if (bucket != null) {
                                ParsedSum parsedSum = bucket.getAggregations().get("sumage");
                                System.out.println(String.valueOf(parsedSum.getValue()));
                            }
                        }
                    }
                }
            }
            searchResponse = esQueryService.scroll(searchResponse.getScrollId());
        } while (searchResponse.getHits().getHits().length > 0);
        System.out.println("searchResponse=====2"+searchResponse);
        System.out.println("searchResponse====="+searchResponse.getHits().getTotalHits().value);
        System.out.println(temp.size());

        esQueryService.ClearScrollRequest(searchResponse.getScrollId());
        log.error("es query searchResponse:{}", JSON.toJSONString(searchResponse.getHits()));
    }

    @Test
    public void testStudent3(){
        StudentCondition condition = new StudentCondition();
        TermsAggregationCondition condition1 = new TermsAggregationCondition("name");
        condition1.sum("sumage", "age");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder searchSourceBuilder = com.example.demo.builder.QueryBuilder.buildGroup(condition, condition1);
        searchSourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        System.out.println(searchSourceBuilder);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder("student_index", "_doc", searchSourceBuilder);

        //System.out.println("searchResponse=====2"+searchResponse);
        System.out.println("searchResponse====="+searchResponse.getHits().getTotalHits().value);

        log.error("es query searchResponse:{}", JSON.toJSONString(searchResponse.getHits()));
    }
}
