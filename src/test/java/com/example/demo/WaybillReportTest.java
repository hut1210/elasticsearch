package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.*;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.IndexOverviewDto;
import com.example.demo.dto.OwnerAndWarehouseDto;
import com.example.demo.dto.WaybillReportOverviewDto;
import com.example.demo.enums.CommonDeliveryEnum;
import com.example.demo.enums.WaybillReportEnum;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/20 14:23
 */
@SpringBootTest
@Slf4j
public class WaybillReportTest {

    @Resource
    private RestHighLevelClient client;

    private static final String index = "common_delivery_monitor_index";

    /**
     * 获取运单总量、配送总量折线图
     *
     * @return
     */
    @Test
    public void test() throws IOException {
        WaybillReportCondition waybillReportCondition = new WaybillReportCondition();

        Map<String, String> map = new HashMap<>();
        //根据日期分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition(CommonDeliveryConstant.FIRST_TIME);
        termsAggregationCondition.order(CommonDeliveryConstant.FIRST_TIME, false);
        //根据目的网点分组查询
        TermsAggregationCondition termsAggregationCondition2 = new TermsAggregationCondition("old_site_id");
        termsAggregationCondition.order(CommonDeliveryConstant.FIRST_TIME, false);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(waybillReportCondition, termsAggregationCondition, termsAggregationCondition2);
        log.info("getCommonDeliveryLineChart ssb------->{}", ssb);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(ssb);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        log.info("getCommonDeliveryLineChart searchResponse------->{}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("time-----" + key);
                        map.put(key, String.valueOf(bucket.getDocCount()));
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        if (asMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                            ParsedLongTerms aggregation = (ParsedLongTerms) asMap.get(AggregationHelper.AGG_GROUP_TERM);
                            if (CollectionUtils.isNotEmpty(aggregation.getBuckets())) {
                                aggregation.getBuckets().forEach(bucket1 -> {
                                    String key1 = bucket1.getKey() == null ? "" : bucket1.getKey().toString();
                                    if (StringUtils.isNotBlank(key1)) {
                                        System.out.println("oldSiteId-----" + key1 + ",-----count-----" + bucket1.getDocCount());
                                        getWaybillReportOverviewDto(waybillReportCondition, key, key1);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    private WaybillReportOverviewDto getWaybillReportOverviewDto(WaybillReportCondition waybillReportCondition, String date, String oldSiteId) {
        WaybillReportOverviewDto waybillReportOverviewDto = new WaybillReportOverviewDto();

        WaybillReportCondition waybillReportCondition1 = new WaybillReportCondition();
        BeanUtils.copyProperties(waybillReportCondition, waybillReportCondition1);

        List list = new ArrayList<>();
        list.add(oldSiteId);
        waybillReportCondition1.setTargetNetworkCode(list);
        waybillReportCondition1.setCreateTimeStart(date);
        waybillReportCondition1.setCreateTimeEnd(date);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(waybillReportCondition1);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                Map<String, Object> map = searchHits[0].getSourceAsMap();
            }
        } catch (IOException e) {
            log.error("运单业务指标概览获取单量异常", e);
        }
        return waybillReportOverviewDto;
    }

    @Test
    public void getDistributionAmount(){
        //配送量
        WaybillReportDistributionCondition waybillReportDistributionCondition = new WaybillReportDistributionCondition();
        //始发网点不是西藏网点
        waybillReportDistributionCondition.setBeginNetworkCode(CommonDeliveryConstant.siteList);
        /*List<String> list = new ArrayList<>();
        list.add("572997");*/
        //目的网点是西藏网点
        waybillReportDistributionCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);
        waybillReportDistributionCondition.setCreateTimeStart("2020-01-03");
        waybillReportDistributionCondition.setCreateTimeEnd("2020-01-11");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(waybillReportDistributionCondition);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            log.info("配送量="+result.getHits().getTotalHits().value);
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                Map<String, Object> map = searchHits[0].getSourceAsMap();
            }
        } catch (IOException e) {
            log.error("运单业务指标概览获取单量异常", e);
        }
    }

    @Test
    public void getCollectAmount(){
        //揽收量
        WaybillReportCollectCondition waybillReportCollectCondition = new WaybillReportCollectCondition();
        //所有始发网点为西藏网点
        waybillReportCollectCondition.setBeginNetworkCode(CommonDeliveryConstant.siteList);
        waybillReportCollectCondition.setCreateTimeStart("2020-01-03");
        waybillReportCollectCondition.setCreateTimeEnd("2020-01-11");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(waybillReportCollectCondition);
        //sourceBuilder.size(10000);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            log.info("揽收量="+result.getHits().getTotalHits().value);
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                Map<String, Object> map = searchHits[0].getSourceAsMap();
            }
        } catch (IOException e) {
            log.error("运单业务指标概览获取单量异常", e);
        }
    }

    @Test
    public void getIndexOverview(){
        WaybillReportCondition waybillReportCondition = new WaybillReportCondition();
        waybillReportCondition.setCreateTimeStart("2020-01-03");
        waybillReportCondition.setCreateTimeEnd("2020-01-11");

        List<Map> distributionList = new ArrayList<>();
        //配送量+揽收量
        String distributionAmount = getDistributionAmountNew(waybillReportCondition);
        String collectAmount = getCollectAmountNew(waybillReportCondition);
        //运单总量
        String totalAmount = String.valueOf(new BigDecimal(distributionAmount).add(new BigDecimal(collectAmount)));
        //环比运单总量
        //计算开始时间和结束时间的差
        int difference = DateUtils.diffDate(DateUtils.parseDate(waybillReportCondition.getCreateTimeStart(),DateUtils.DATE_FORMAT),DateUtils.parseDate(waybillReportCondition.getCreateTimeEnd(),DateUtils.DATE_FORMAT));
        Date startTime = DateUtils.getDateForBegin(DateUtils.parseDate(waybillReportCondition.getCreateTimeStart(),DateUtils.DATE_FORMAT),difference-1);
        Date endTime = DateUtils.getDateForEnd(DateUtils.parseDate(waybillReportCondition.getCreateTimeEnd(),DateUtils.DATE_FORMAT),difference-1);

        //获取环比数量
        waybillReportCondition.setCreateTimeStart(DateUtils.formatDate(startTime,DateUtils.DATE_FORMAT));
        waybillReportCondition.setCreateTimeEnd(DateUtils.formatDate(endTime,DateUtils.DATE_FORMAT));

        String linkRelativeRatioDistributionAmount = getDistributionAmountNew(waybillReportCondition);
        String linkRelativeRatioCollectAmount = getCollectAmountNew(waybillReportCondition);

        String linkRelativeRatioAmount = String.valueOf(new BigDecimal(linkRelativeRatioDistributionAmount).add(new BigDecimal(linkRelativeRatioCollectAmount)));
        //环比计算
        String linkRelativeRatio = ReportUtils.getDayCount(new BigDecimal(totalAmount), new BigDecimal(linkRelativeRatioAmount));
        IndexOverviewDto totalAmountDto = IndexOverviewDto.builder()
                .title("运单总量")
                .num(totalAmount)
                .note("以选择时间条件为基础，配送量+揽收量")
                .percent(linkRelativeRatio)
                .build();
        distributionList.add(JSONObject.parseObject(JSON.toJSONString(totalAmountDto)));

        //配送量
        //计算占比
        String percent = ReportUtils.toPercent(new BigDecimal(distributionAmount), new BigDecimal(totalAmount));
        distributionList.add(JSONObject.parseObject(JSON.toJSONString(
                IndexOverviewDto.builder()
                        .title(WaybillReportEnum.DISTRIBUTION_AMOUNT.getName())
                        .num(distributionAmount)
                        .note(WaybillReportEnum.DISTRIBUTION_AMOUNT.getNote())
                        .percent(percent)
                        .build())));

        //揽收量
        //计算占比
        String percent2 = ReportUtils.toPercent(new BigDecimal(collectAmount), new BigDecimal(totalAmount));
        distributionList.add(JSONObject.parseObject(JSON.toJSONString(
                IndexOverviewDto.builder()
                        .title(WaybillReportEnum.COLLECT_AMOUNT.getName())
                        .num(collectAmount)
                        .note(WaybillReportEnum.COLLECT_AMOUNT.getNote())
                        .percent(percent2)
                        .build())));

        distributionList.forEach(System.out::println);
    }

    private String getDistributionAmountNew(WaybillReportCondition waybillReportCondition){
        //配送量
        WaybillReportDistributionCondition waybillReportDistributionCondition = new WaybillReportDistributionCondition();
        BeanUtils.copyProperties(waybillReportCondition,waybillReportDistributionCondition);
        //始发网点不是西藏网点
        waybillReportDistributionCondition.setBeginNetworkCode(CommonDeliveryConstant.siteList);
        //目的网点是西藏网点
        waybillReportDistributionCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(waybillReportDistributionCondition);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            log.info("配送量="+result.getHits().getTotalHits().value);
            return String.valueOf(result.getHits().getTotalHits().value);
        } catch (IOException e) {
            log.error("运单业务指标概览获取单量异常", e);
        }
        return "0";
    }

    private String getCollectAmountNew(WaybillReportCondition waybillReportCondition){
        //揽收量
        WaybillReportCollectCondition waybillReportCollectCondition = new WaybillReportCollectCondition();
        BeanUtils.copyProperties(waybillReportCondition,waybillReportCollectCondition);
        //所有始发网点为西藏网点
        waybillReportCollectCondition.setBeginNetworkCode(CommonDeliveryConstant.siteList);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(waybillReportCollectCondition);
        //sourceBuilder.size(10000);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            log.info("揽收量="+result.getHits().getTotalHits().value);
            return String.valueOf(result.getHits().getTotalHits().value);
        } catch (IOException e) {
            log.error("运单业务指标概览获取单量异常", e);
        }
        return "0";
    }
}
