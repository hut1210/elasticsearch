package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.IndexOverviewDto;
import com.example.demo.enums.CommonDeliveryEnum;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
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
 * @date 2021/2/19 17:35
 */
@SpringBootTest
@Slf4j
public class CommonDeliveryTest {

    @Resource
    private RestHighLevelClient client;

    private static final String index = "common_delivery_monitor_index";

    @Test
    public void test1() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2020-01-03");
        commonDeliveryCondition.setCreateTimeEnd("2020-01-03");*/
        TermsAggregationCondition condition1 = new TermsAggregationCondition("old_site_id");
        condition1.cardinality("waybill_code");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = com.example.demo.builder.QueryBuilder.buildGroup(commonDeliveryCondition, condition1);
        System.out.println("ssb --->" + ssb);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(ssb);

        List<CommonDeliveryOverviewDto> commonDeliveryOverviewDtoList = new ArrayList<>();
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("searchResponse --->" + searchResponse);
            Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
            if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
                if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                    for (Terms.Bucket bucket : teams.getBuckets()) {
                        String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                        if (StringUtils.isNotBlank(key)) {
                            System.out.println("key-----" + key + "   " + bucket.getDocCount());
                            CommonDeliveryOverviewDto commonDeliveryOverviewDto = getAmount(commonDeliveryCondition, key);
                            commonDeliveryOverviewDto.setWaybillAmount(String.valueOf(bucket.getDocCount()));
                            String cancelAmount = commonDeliveryOverviewDto.getCancelAmount() != null ? commonDeliveryOverviewDto.getCancelAmount() : "0";
                            String rejectionAmount = commonDeliveryOverviewDto.getRejectionAmount() != null ? commonDeliveryOverviewDto.getRejectionAmount() : "0";

                            BigDecimal denominator = new BigDecimal(commonDeliveryOverviewDto.getWaybillAmount()).subtract(new BigDecimal(cancelAmount).add(new BigDecimal(rejectionAmount)));
                            BigDecimal molecule = new BigDecimal(commonDeliveryOverviewDto.getSignedInAmount() != null ? commonDeliveryOverviewDto.getSignedInAmount() : "0");
                            String percent = ReportUtils.toPercent(molecule, denominator);
                            commonDeliveryOverviewDtoList.add(CommonDeliveryOverviewDto.builder()
                                    .networkName(key)
                                    .waybillAmount(String.valueOf(bucket.getDocCount()))
                                    .bizType(commonDeliveryOverviewDto.getBizType())
                                    .distributionAmount(commonDeliveryOverviewDto.getDistributionAmount() != null ? commonDeliveryOverviewDto.getDistributionAmount() : "0")
                                    .signedInAmount(commonDeliveryOverviewDto.getSignedInAmount() != null ? commonDeliveryOverviewDto.getSignedInAmount() : "0")
                                    .cancelAmount(cancelAmount)
                                    .rejectionAmount(rejectionAmount)
                                    .deliveryCompletionRate(percent)
                                    .build());
                        }
                    }
                }
            }
            commonDeliveryOverviewDtoList.forEach(System.out::println);
            System.out.println("分页前列表长度="+commonDeliveryOverviewDtoList.size());
            int pageSize = 2;
            int pageIndex = 2;
            int pageFrom = ReportUtils.getFrom(pageSize, pageIndex);
            int pageTo = ReportUtils.getTo(pageSize, pageIndex);
            //size小于数据长度， 假分页开始
            if (pageSize < commonDeliveryOverviewDtoList.size()) {
                if (pageTo <= commonDeliveryOverviewDtoList.size()) {
                    commonDeliveryOverviewDtoList = commonDeliveryOverviewDtoList.subList(pageFrom, pageTo);
                } else {
                    commonDeliveryOverviewDtoList = commonDeliveryOverviewDtoList.subList(pageFrom, commonDeliveryOverviewDtoList.size());
                }
            }
            System.out.println("分页后列表长度="+commonDeliveryOverviewDtoList.size());
            commonDeliveryOverviewDtoList.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    private CommonDeliveryOverviewDto getAmount(CommonDeliveryCondition commonDeliveryCondition, String oldSiteId) {
        CommonDeliveryOverviewDto commonDeliveryOverviewDto = new CommonDeliveryOverviewDto();
        for (CommonDeliveryEnum commonDeliveryEnum : CommonDeliveryEnum.values()) {
            CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(index);
            SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition1);
            log.info("getAmount sourceBuilder------->{}", sourceBuilder);
            searchRequest.source(sourceBuilder);
            SearchResponse result = null;
            try {
                result = client.search(searchRequest, RequestOptions.DEFAULT);
                log.info("getAmount result------->{}", result);
                SearchHit[] searchHits = result.getHits().getHits();
                if (searchHits != null && searchHits.length > 0) {
                    commonDeliveryOverviewDto = modifyCommonDeliveryOverviewDto(commonDeliveryOverviewDto, commonDeliveryEnum, String.valueOf(result.getHits().getTotalHits().value));
                    Map<String, Object> map = searchHits[0].getSourceAsMap();
                    commonDeliveryOverviewDto.setBizType(map.get("business_type").toString());
                    commonDeliveryOverviewDto.setNetworkName(map.get("old_site_name").toString());
                }
            } catch (IOException e) {
                log.error("共配业务指标概览获取单量异常", e);
            }
        }
        return commonDeliveryOverviewDto;
    }

    private CommonDeliveryCondition modifyCommonDeliveryCondition(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        if (commonDeliveryEnum != null) {
            CommonDeliveryCondition tempCommonDeliveryCondition = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition, tempCommonDeliveryCondition);
            switch (commonDeliveryEnum.getCode()) {
                case 1:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.onTheWayStatusList);
                    break;
                case 2:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.distributionStatusList);
                    break;
                case 3:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.signedInStatusList);
                    break;
                case 4:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.cancelStatusList);
                    break;
                case 5:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.rejectionStatusList);
                    break;
                default:
            }
            return tempCommonDeliveryCondition;
        }
        return commonDeliveryCondition;
    }

    private CommonDeliveryOverviewDto modifyCommonDeliveryOverviewDto(CommonDeliveryOverviewDto commonDeliveryOverviewDto, CommonDeliveryEnum commonDeliveryEnum, String amount) {
        if (commonDeliveryEnum != null) {
            switch (commonDeliveryEnum.getCode()) {
                case 1:
                    commonDeliveryOverviewDto.setOnTheWayAmount(amount);
                    break;
                case 2:
                    commonDeliveryOverviewDto.setDistributionAmount(amount);
                    break;
                case 3:
                    commonDeliveryOverviewDto.setSignedInAmount(amount);
                    break;
                case 4:
                    commonDeliveryOverviewDto.setCancelAmount(amount);
                    break;
                case 5:
                    commonDeliveryOverviewDto.setRejectionAmount(amount);
                    break;
                default:
                    commonDeliveryOverviewDto.setWaybillAmount(amount);
            }
        }
        return commonDeliveryOverviewDto;
    }

    /**
     * 获取共配业务监控报表列表
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        CommonDeliveryOverviewDto commonDeliveryOverviewDto = null;

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart("2020-01-03");
        commonDeliveryCondition.setCreateTimeEnd("2020-01-03");

        //统计在途单量、配送、已签收、取消、拒收单量
        commonDeliveryOverviewDto = getAmount(commonDeliveryCondition, null);

        //查询运单量
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition);
        sourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            long value = result.getHits().getTotalHits().value;
            commonDeliveryOverviewDto.setWaybillAmount(String.valueOf(value));
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                log.info("searchHits length------->{}", searchHits.length);
            }
        } catch (IOException e) {
            log.error("共配业务指标概览获取单量异常", e);
        }

        System.out.println(commonDeliveryOverviewDto);
    }


    /**
     * 获取概览数据集合
     * @return
     */
    @Test
    public void doGetIndexOverview() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart("2020-01-03");
        commonDeliveryCondition.setCreateTimeEnd("2020-01-03");
        List<Map> distributionList = new ArrayList<>();
        log.info("doGetIndexOverview 取运单总量时commonDeliveryCondition={}", commonDeliveryCondition);
        //运单总量
        String totalAmount = doGetAmount(commonDeliveryCondition,null);
        IndexOverviewDto totalAmountDto = IndexOverviewDto.builder()
                .title("运单总量")
                .num(totalAmount)
                .note("以选择时间条件为基础，统计所有目的网点为西藏网点的运单总量")
                .build();
        distributionList.add(JSONObject.parseObject(JSON.toJSONString(totalAmountDto)));
        for (CommonDeliveryEnum commonDeliveryEnum : CommonDeliveryEnum.values()) {
            CommonDeliveryCondition tempCommonDeliveryCondition = modifyCommonDeliveryCondition(commonDeliveryCondition, commonDeliveryEnum);
            log.info("doGetIndexOverview 取其他总量修改后tempCommonDeliveryCondition={}", tempCommonDeliveryCondition);
            //获取其他数量
            String num = doGetAmount(commonDeliveryCondition,commonDeliveryEnum);
            //计算占比
            String percent = ReportUtils.toPercent(new BigDecimal(num), new BigDecimal(totalAmount));
            distributionList.add(JSONObject.parseObject(
                    JSON.toJSONString(IndexOverviewDto.builder()
                            .title(commonDeliveryEnum.getName())
                            .num(num)
                            .note(commonDeliveryEnum.getNote())
                            .percent(percent)
                            .build())));
        }
        distributionList.forEach(System.out::println);
    }

    private String doGetAmount(CommonDeliveryCondition commonDeliveryCondition,CommonDeliveryEnum commonDeliveryEnum) {
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        if(commonDeliveryEnum!=null){
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition1);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("getAmount result------->{}", result);
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                System.out.println("searchHits.length===="+searchHits.length);
            }
            return String.valueOf(result.getHits().getTotalHits().value);
        } catch (IOException e) {
            log.error("共配业务指标概览获取单量异常", e);
        }
        return "0";
    }

    /**
     * 获取运单总量、配送总量折线图
     * @return
     */
    @Test
    public void doGetCommonDeliveryLineChart() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        //commonDeliveryCondition.setStatus(CommonDeliveryConstant.distributionStatusList);
        log.info("doGetCommonDeliveryLineChart 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChart(commonDeliveryCondition,null);
        lineChartMap.keySet().forEach(key -> {
            seriesList.add(key);
            xdataList.add(lineChartMap.get(key));
        });
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        System.out.println(map);
    }

    /**
     * 获取运单总量、配送总量折线图
     * @return
     */
    @Test
    public void doGetCommonDeliveryLineChart2() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        log.info("doGetCommonDeliveryLineChart 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChart(commonDeliveryCondition,CommonDeliveryEnum.DISTRIBUTION_AMOUNT);
        lineChartMap.keySet().forEach(key -> {
            seriesList.add(key);
            xdataList.add(lineChartMap.get(key));
        });
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        System.out.println(map);
    }

    /**
     * 获取运单总量、配送总量折线图
     *
     * @param commonDeliveryCondition
     * @return
     */
    private Map getCommonDeliveryLineChart(CommonDeliveryCondition commonDeliveryCondition,CommonDeliveryEnum commonDeliveryEnum) throws IOException {
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        if(commonDeliveryEnum!=null){
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
        }

        Map<String, String> map = new HashMap<>();
        //根据日期分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition(CommonDeliveryConstant.FIRST_TIME);

        termsAggregationCondition.order(CommonDeliveryConstant.FIRST_TIME, false);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition1, termsAggregationCondition);
        log.info("getCommonDeliveryLineChart ssb------->{}", ssb);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(ssb);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse --->"+searchResponse);
        log.info("getCommonDeliveryLineChart searchResponse------->{}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if(CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket->{
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----"+key);
                        map.put(key,String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
        return map;
    }

    @Test
    public void getCommonDeliveryPieChat () throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        Map<String, String> map = new HashMap<>();
        //根据业务类型分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("business_type");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition, termsAggregationCondition);
        log.info("getCommonDeliveryPieChat ssb = {}", ssb);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(ssb);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        log.info("getCommonDeliveryPieChat searchResponse = {}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedLongTerms teams = (ParsedLongTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if(CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket->{
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----"+key);
                        map.put(key,String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
    }
}
