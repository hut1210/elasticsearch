package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.CommonDeliveryPageCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.IndexOverviewDto;
import com.example.demo.enums.CommonDeliveryEnum;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import com.example.demo.util.TimeTool;
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
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/19 17:35
 */
@SpringBootTest
@Slf4j
public class CommonDeliveryNewTest {

    @Resource
    private RestHighLevelClient client;

    private static final String commonDeliveryMonitorIndex = "waybill_wide";

    private static final String commonDeliveryMonitorType = "_doc";

    @Resource
    private EsQueryService esQueryService;

    @Test
    public void test1() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2021-02-22 00:00:00");
        commonDeliveryCondition.setCreateTimeEnd("2021-02-22 23:59:59");*/
        TermsAggregationCondition condition1 = new TermsAggregationCondition("old_site_id");
        condition1.cardinality("waybill_code");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition, condition1);
        System.out.println("ssb --->" + ssb);

        List<CommonDeliveryOverviewDto> commonDeliveryOverviewDtoList = new ArrayList<>();
        try {
            SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
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
            System.out.println("分页前列表长度=" + commonDeliveryOverviewDtoList.size());
            int pageSize = 1;
            int pageIndex = 1;
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
            System.out.println("分页后列表长度=" + commonDeliveryOverviewDtoList.size());
            commonDeliveryOverviewDtoList.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    @Test
    public void getAvgDistributionDuration() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart("2021-02-21");
        commonDeliveryCondition.setCreateTimeEnd("2021-02-21");
        commonDeliveryCondition.setNetworkCode("999");
        commonDeliveryCondition.setStatus(CommonDeliveryConstant.signedInStatusList);
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition);
        sourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        log.info("getAvgDistributionDuration sourceBuilder------->{}", sourceBuilder);
        SearchResponse result = null;
        result = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("getAvgDistributionDuration result------->{}", result);
        SearchHit[] searchHits = result.getHits().getHits();
        long totalTime = 0L;
        if (searchHits != null && searchHits.length > 0) {
            for (SearchHit searchHit : searchHits) {
                Map<String, Object> map = searchHit.getSourceAsMap();
                String stateCreateTime = map.get("state_create_time") != null ? map.get("state_create_time").toString() : "";
                String requireStartTime = map.get("require_start_time") != null ? map.get("require_start_time").toString() : "";
                System.out.println(map.get("state_create_time") + "    " + map.get("require_start_time"));
                if (!StringUtils.isEmpty(stateCreateTime) && !StringUtils.isEmpty(requireStartTime)) {
                    try {
                        long time = TimeTool.dateDiff(requireStartTime, stateCreateTime, DateUtils.DATETIME_FORMAT);
                        totalTime += time;
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                }
            }
            System.out.println(new BigDecimal("0").compareTo(BigDecimal.ZERO));
            System.out.println(new BigDecimal("0").compareTo(BigDecimal.ZERO));
            System.out.println(new BigDecimal("0").compareTo(BigDecimal.ZERO));
            System.out.println(ReportUtils.baseDivide(new BigDecimal(String.valueOf(totalTime)), new BigDecimal("2")));
            System.out.println("totalTime----》" + totalTime);
        }
    }

    private CommonDeliveryOverviewDto getAmount(CommonDeliveryCondition commonDeliveryCondition, String oldSiteId) {
        CommonDeliveryOverviewDto commonDeliveryOverviewDto = new CommonDeliveryOverviewDto();
        for (CommonDeliveryEnum commonDeliveryEnum : CommonDeliveryEnum.values()) {
            CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);

            SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition1);
            log.info("getAmount sourceBuilder------->{}", sourceBuilder);
            SearchResponse result = null;
            result = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
            log.info("getAmount result------->{}", result);
            SearchHit[] searchHits = result.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                commonDeliveryOverviewDto = modifyCommonDeliveryOverviewDto(commonDeliveryOverviewDto, commonDeliveryEnum, String.valueOf(result.getHits().getTotalHits().value));
                Map<String, Object> map = searchHits[0].getSourceAsMap();
                commonDeliveryOverviewDto.setBizType(map.get("business_type").toString());
                commonDeliveryOverviewDto.setNetworkName(map.get("old_site_name").toString());
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
     *
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        CommonDeliveryOverviewDto commonDeliveryOverviewDto = null;

        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2020-01-03");
        commonDeliveryCondition.setCreateTimeEnd("2020-01-03");*/

        //统计在途单量、配送、已签收、取消、拒收单量
        commonDeliveryOverviewDto = getAmount(commonDeliveryCondition, null);

        //查询运单量
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition);
        sourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        SearchResponse result = null;
        result = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("getAmount result------->{}", result);
        long value = result.getHits().getTotalHits().value;
        commonDeliveryOverviewDto.setWaybillAmount(String.valueOf(value));
        SearchHit[] searchHits = result.getHits().getHits();
        if (searchHits != null && searchHits.length > 0) {
            log.info("searchHits length------->{}", searchHits.length);
        }

        System.out.println(commonDeliveryOverviewDto);
    }

    /**
     * 共配业务指标概览
     *
     * @return
     */
    @Test
    public void queryCommonDeliveryIndexOverview() {

        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        Map responseMap = new HashMap();
        //概览数据集合
        responseMap.put("distributionList", doGetIndexOverview(commonDeliveryCondition));
        //运单总量折线图
        responseMap.put("waybillAmountLineChart", doGetCommonDeliveryLineChart(commonDeliveryCondition, null));
        System.out.println(responseMap);
    }

    /**
     * 获取概览数据集合
     *
     * @return
     */
    @Test
    public List doGetIndexOverview(CommonDeliveryCondition commonDeliveryCondition) {
        commonDeliveryCondition.setCreateTimeStart("2021-02-22");
        commonDeliveryCondition.setCreateTimeEnd("2020-02-22");
        List<Map> distributionList = new ArrayList<>();
        log.info("doGetIndexOverview 取运单总量时commonDeliveryCondition={}", commonDeliveryCondition);
        //运单总量
        String totalAmount = doGetAmount(commonDeliveryCondition, null);
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
            String num = doGetAmount(commonDeliveryCondition, commonDeliveryEnum);
            //计算占比
            String percent = ReportUtils.toPercent(new BigDecimal(num), new BigDecimal(totalAmount));
            distributionList.add(JSONObject.parseObject(JSON.toJSONString(IndexOverviewDto.builder()
                    .title(commonDeliveryEnum.getName())
                    .num(num)
                    .note(commonDeliveryEnum.getNote())
                    .percent(percent)
                    .build())));
        }
        return distributionList;
    }

    /**
     * 获取运单总量、配送总量折线图
     *
     * @return
     */
    @Test
    public Map doGetCommonDeliveryLineChart(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        log.info("doGetCommonDeliveryLineChart 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChart(commonDeliveryCondition, null);
        lineChartMap.keySet().forEach(key -> {
            seriesList.add(key);
            xdataList.add(lineChartMap.get(key));
        });
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    /**
     * 获取概览数据集合
     *
     * @return
     */
    @Test
    public void doGetIndexOverview() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart("2021-02-22");
        commonDeliveryCondition.setCreateTimeEnd("2020-02-22");
        List<Map> distributionList = new ArrayList<>();
        log.info("doGetIndexOverview 取运单总量时commonDeliveryCondition={}", commonDeliveryCondition);
        //运单总量
        String totalAmount = doGetAmount(commonDeliveryCondition, null);
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
            String num = doGetAmount(commonDeliveryCondition, commonDeliveryEnum);
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

    private String doGetAmount(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        if (commonDeliveryEnum != null) {
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
        }

        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition1);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        SearchResponse result = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("getAmount result------->{}", result);
        SearchHit[] searchHits = result.getHits().getHits();
        if (searchHits != null && searchHits.length > 0) {
            System.out.println("searchHits.length====" + searchHits.length);
        }
        return String.valueOf(result.getHits().getTotalHits().value);
    }

    /**
     * 获取运单总量、配送总量折线图
     *
     * @return
     */
    @Test
    public void doGetCommonDeliveryLineChart() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2021-02-22");
        commonDeliveryCondition.setCreateTimeEnd("2021-02-22");*/
        //commonDeliveryCondition.setStatus(CommonDeliveryConstant.distributionStatusList);
        log.info("doGetCommonDeliveryLineChart 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChart(commonDeliveryCondition, null);
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
     * @return
     */
    @Test
    public void doGetCommonDeliveryLineChart2() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2021-02-22");
        commonDeliveryCondition.setCreateTimeEnd("2021-02-22");*/
        log.info("doGetCommonDeliveryLineChart 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChart(commonDeliveryCondition, CommonDeliveryEnum.DISTRIBUTION_AMOUNT);
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
    private Map getCommonDeliveryLineChart(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeStart())) {
            commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATE_FORMAT));
        }
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeEnd())) {
            commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATE_FORMAT));
        }
        List<String> datesBetween2Date = DateUtils.getDatesBetween2Date(commonDeliveryCondition.getCreateTimeStart(), commonDeliveryCondition.getCreateTimeEnd());

        datesBetween2Date.forEach(System.out::println);

        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        if (commonDeliveryEnum != null) {
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
        }

        Map<String, String> map = new HashMap<>();
        //根据日期分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition(CommonDeliveryConstant.FIRST_TIME);
        termsAggregationCondition.order(CommonDeliveryConstant.FIRST_TIME, false);
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition1, termsAggregationCondition);
        log.info("getCommonDeliveryLineChart ssb------->{}", ssb);

        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
        System.out.println("searchResponse --->" + searchResponse);
        log.info("getCommonDeliveryLineChart searchResponse------->{}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedLongTerms teams = (ParsedLongTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKeyAsString() == null ? "" : bucket.getKeyAsString().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        map.put(key, String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
        return map;
    }


    /**
     * 获取运单总量、配送总量折线图
     *
     * @return
     */
    @Test
    public void doGetCommonDeliveryLineChartNew() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        /*commonDeliveryCondition.setCreateTimeStart("2021-02-22");
        commonDeliveryCondition.setCreateTimeEnd("2021-02-22");*/
        //commonDeliveryCondition.setStatus(CommonDeliveryConstant.distributionStatusList);
        log.info("doGetCommonDeliveryLineChartNew 获取运单总量、配送总量折线图commonDeliveryCondition={}", commonDeliveryCondition);
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = getCommonDeliveryLineChartNew(commonDeliveryCondition, null);
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
    private Map getCommonDeliveryLineChartNew(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) throws IOException {
        commonDeliveryCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeStart())) {
            commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATE_FORMAT));
        }
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeEnd())) {
            commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATE_FORMAT));
        }
        List<String> datesBetween2Date = DateUtils.getDatesBetween2Date(commonDeliveryCondition.getCreateTimeStart(), commonDeliveryCondition.getCreateTimeEnd());

        Map<String, String> map = new HashMap<>();
        datesBetween2Date.forEach(time -> {
            CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
            commonDeliveryCondition1.setCreateTimeStart(time);
            commonDeliveryCondition1.setCreateTimeEnd(time);
            if (commonDeliveryEnum != null) {
                commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
            }

            //根据日期分组查询
            TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition(CommonDeliveryConstant.FIRST_TIME);
            termsAggregationCondition.order(CommonDeliveryConstant.FIRST_TIME, false);
            termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
            SearchSourceBuilder ssb = QueryBuilder.build(commonDeliveryCondition1);
            log.info("getCommonDeliveryLineChart ssb------->{}", ssb);

            SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
            System.out.println("searchResponse --->" + searchResponse);
            log.info("getCommonDeliveryLineChart searchResponse------->{}", searchResponse);
            map.put(time, String.valueOf(searchResponse.getHits().getTotalHits().value));
        });

        /*for (String time : datesBetween2Date){
            CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
            commonDeliveryCondition1.setCreateTimeStart(time);
            commonDeliveryCondition1.setCreateTimeEnd(time);
            if (commonDeliveryEnum != null) {
                commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
            }

            //根据日期分组查询
            SearchSourceBuilder ssb = QueryBuilder.build(commonDeliveryCondition1);
            log.info("getCommonDeliveryLineChart ssb------->{}", ssb);

            SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
            System.out.println("searchResponse --->" + searchResponse);
            log.info("getCommonDeliveryLineChart searchResponse------->{}", searchResponse);
            map.put(time,String.valueOf(searchResponse.getHits().getTotalHits().value));
        }*/

        return map;
    }

    @Test
    public void getCommonDeliveryPieChat() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        Map<String, String> map = new HashMap<>();
        //根据业务类型分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("business_type");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition, termsAggregationCondition);
        log.info("getCommonDeliveryPieChat ssb = {}", ssb);

        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
        log.info("getCommonDeliveryPieChat searchResponse = {}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        map.put(key, String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
    }

    @Test
    public void getCommonDeliveryPieChat2() throws IOException {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        Map<String, String> map = new HashMap<>();
        //根据业务类型分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("distribution_type");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition, termsAggregationCondition);
        log.info("getCommonDeliveryPieChat ssb = {}", ssb);

        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
        log.info("getCommonDeliveryPieChat searchResponse = {}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        map.put(key, String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
    }

    @Test
    public void groupList() {
        List<String> list = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10"));

        int listSize = list.size();
        int toIndex = 3;
        int keyToken = 0;
        for (int i = 0; i < list.size(); i += 3) {
            // 作用为toIndex最后没有100条数据则剩余几条newList中就装几条
            if (i + 3 > listSize) {
                toIndex = listSize - i;
            }
            List<String> newList = list.subList(i, i + toIndex);
            System.out.println(newList);
            for (String n : newList) {
                System.out.println(n);
            }
        }
    }

    @Test
    public void testQueryCommonDeliveryPageNew() {
        CommonDeliveryPageCondition commonDeliveryPageCondition = new CommonDeliveryPageCondition();
        commonDeliveryPageCondition.setPageSize(1);
        commonDeliveryPageCondition.setPageSize(10);
        /*commonDeliveryPageCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATE_FORMAT));
        commonDeliveryPageCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATE_FORMAT));*/
        queryCommonDeliveryPageNew(commonDeliveryPageCondition);
    }

    public List<CommonDeliveryOverviewDto> queryCommonDeliveryPageNew(CommonDeliveryPageCondition commonDeliveryPageCondition) {

        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryPageCondition, commonDeliveryCondition);
        //获取西藏所有网点编号
        List<String> netWorkList = CommonDeliveryConstant.siteList;
        commonDeliveryCondition.setTargetNetworkCode(netWorkList);
        log.info("queryCommonDeliveryPage commonDeliveryPageCondition --->{}，---commonDeliveryCondition---->{}", commonDeliveryPageCondition, commonDeliveryCondition);
        //1.先按照站点分组查询
        TermsAggregationCondition condition1 = new TermsAggregationCondition("old_site_id");
        condition1.cardinality("waybill_code");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);

        TermsAggregationCondition condition2 = new TermsAggregationCondition("state");
        condition2.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition, condition1, condition2);
        log.info("queryCommonDeliveryPage ssb --->" + ssb);
        List<CommonDeliveryOverviewDto> commonDeliveryOverviewDtoList = new ArrayList<>();
        List list = new ArrayList();
        try {
            SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
            log.info("queryCommonDeliveryPage searchResponse --->" + searchResponse);
            List<Map<String, String>> maps = ReportUtils.analySearchResponse(searchResponse, condition1, condition2);
            System.out.println("maps = " + maps);
            /*list = maps.stream().collect(Collectors.groupingBy(d -> d.get("old_site_id"))).values().stream().map(d -> {
                Map<String, String> sampleData = d.get(0);
                Map<String, BigDecimal> map = new HashMap<>();
                map.put("state_doc_count", d.stream().map(s -> new BigDecimal(s.get("state_doc_count").toString())).reduce(BigDecimal.ZERO, BigDecimal::add));
                return map;
            }).collect(Collectors.toList());*/
            Function<Map<String,String>, String> s = new Function<Map<String,String>, String>() {
                @Override
                public String apply(Map<String, String> t) {
                    String string = t.get("old_site_id");
                    return string;
                }
            };
            Object collect = maps.stream().collect(Collectors.groupingBy(s));

            Map<Object,List<Map>> result = maps.stream().collect(Collectors.groupingBy(it -> it.get("old_site_id")));
            System.out.println("result = "+result);
            for (Object o : result.keySet()) {
                CommonDeliveryOverviewDto commonDeliveryOverviewDto = new CommonDeliveryOverviewDto();
                String oldSiteId = o.toString();
                List<Map> list1 = result.get(oldSiteId);
                int waybillAmount = list1.stream().mapToInt(b -> Integer.parseInt(String.valueOf(b.get("old_site_id_doc_count")))).sum();
                //总量
                commonDeliveryOverviewDto.setWaybillAmount(String.valueOf(waybillAmount));
                int distributionAmount = 0;
                int signedInAmount = 0;
                int cancelAmount = 0;
                int rejectionAmount = 0;
                Integer avgDistributionDuration = null;
                for (Map map : list1) {
                    if(CommonDeliveryConstant.distributionStatusList.contains(map.get("state"))){
                        distributionAmount += Integer.parseInt(String.valueOf(map.get("state_doc_count")));
                    }
                    if(CommonDeliveryConstant.signedInStatusList.contains(map.get("state"))){
                        signedInAmount += Integer.parseInt(String.valueOf(map.get("state_doc_count")));
                    }
                    if(CommonDeliveryConstant.cancelStatusList.contains(map.get("state"))){
                        cancelAmount += Integer.parseInt(String.valueOf(map.get("state_doc_count")));
                    }
                    if(CommonDeliveryConstant.rejectionStatusList.contains(map.get("state"))){
                        rejectionAmount += Integer.parseInt(String.valueOf(map.get("state_doc_count")));
                    }
                }
                commonDeliveryOverviewDto.setDistributionAmount(String.valueOf(distributionAmount));
                commonDeliveryOverviewDto.setSignedInAmount(String.valueOf(signedInAmount));
                commonDeliveryOverviewDto.setCancelAmount(String.valueOf(cancelAmount));
                commonDeliveryOverviewDto.setRejectionAmount(String.valueOf(rejectionAmount));

                //分母 配送总量
                BigDecimal denominator = new BigDecimal(waybillAmount).subtract(new BigDecimal(cancelAmount).add(new BigDecimal(rejectionAmount)));
                //分子 已签收单量
                BigDecimal molecule = new BigDecimal(signedInAmount);
                //计算配送完成率
                String percent = ReportUtils.toPercent(molecule, denominator);

                commonDeliveryOverviewDto.setDeliveryCompletionRate(percent);

                commonDeliveryOverviewDtoList.add(commonDeliveryOverviewDto);
            }
            System.out.println("commonDeliveryOverviewDtoList = "+commonDeliveryOverviewDtoList);
            System.out.println("分组后:"+result);
            System.out.println("分组后:"+collect);
            System.out.println("list = "+list);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }

        return commonDeliveryOverviewDtoList;
    }

    @Test
    public void fun1() {
        List<Map<String,Object>> lsl = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "zhangSan");
        lsl.add(map);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("id", "2");
        map2.put("name", "lisi");
        lsl.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("id", "1");
        map3.put("name", "wangwu");

        lsl.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("id", "2");
        map4.put("name", "zhaoliu");

        lsl.add(map4);

        Map<String, List<Map<String, Object>>> collect = lsl.stream().collect(Collectors.groupingBy(s));

        System.out.println(collect);
    }

    Function<Map<String,Object>, String>  s = new Function<Map<String,Object>, String>() {

        @Override
        public String apply(Map<String, Object> t) {
            Object object = t.get("id");
            String string = object.toString();
            return string;
        }
    };
}
