package com.example.demo.bigscreen.commondelivery;

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
import com.example.demo.dto.WaybillReportDto;
import com.example.demo.enums.CommonDeliveryEnum;
import com.example.demo.enums.DistributionStatusEnum;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import com.example.demo.util.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
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
public class CommonDeliveryNewTest {

    @Resource
    private RestHighLevelClient client;

    private static final String commonDeliveryMonitorIndex = "waybill_wide";

    private static final String commonDeliveryMonitorType = "_doc";

    @Resource
    private EsQueryService esQueryService;

    //当前站点的名称、配送数量
    //根据站点分组取配送单量
    @Test
    public void testGetAreaDistributionAmount() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart("2021-03-03");
        commonDeliveryCondition.setCreateTimeEnd("2021-03-03");
        commonDeliveryCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);
        getAreaDistributionAmount(commonDeliveryCondition);
    }
    private String getAreaDistributionAmount(CommonDeliveryCondition commonDeliveryCondition) {
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        //配送状态
        commonDeliveryCondition1.setStatus(CommonDeliveryConstant.distributionStatusList);
        commonDeliveryCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);

        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("old_site_id");
        //根据站点分组
        TermsAggregationCondition termsAggregationCondition1 = new TermsAggregationCondition("old_site_id");
        termsAggregationCondition1.order("_count", false);
        termsAggregationCondition1.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder sourceBuilder = QueryBuilder.buildGroup(commonDeliveryCondition1, termsAggregationCondition1);
        sourceBuilder.size(0);
        log.info("getAreaDistributionAmount sourceBuilder------->{}", sourceBuilder);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("getAreaDistributionAmount searchResponse------->{}", searchResponse);
        Map<String, Aggregation> asMap = searchResponse.getAggregations().getAsMap();
        if (asMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms aggregation = (ParsedStringTerms) asMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(aggregation.getBuckets())) {
                aggregation.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println(key + " " + bucket.getDocCount());
                    }
                });
            }
        }
        return null;
    }

    //24小时趋势图

    //共配站点按配送单的TOP5排名情况、配送单占比情况
    @Test
    public void testGetDistributionAmount() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT));
        commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT));
        commonDeliveryCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);
        doGetAmount(commonDeliveryCondition);
    }

    private String doGetAmount(CommonDeliveryCondition commonDeliveryCondition) {
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);

        //根据站点分组
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("old_site_id");
        termsAggregationCondition.order("_count", false);
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder sourceBuilder = QueryBuilder.buildGroup(commonDeliveryCondition1, termsAggregationCondition);
        sourceBuilder.size(0);
        log.info("doGetAmount sourceBuilder------->{}", sourceBuilder);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("doGetAmount searchResponse------->{}", searchResponse);
        Map<String, Aggregation> asMap = searchResponse.getAggregations().getAsMap();
        if (asMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms aggregation = (ParsedStringTerms) asMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(aggregation.getBuckets())) {
                aggregation.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println(key + " " + bucket.getDocCount());
                    }
                });
            }
        }
        return null;
    }
    //配送状态占比 已签收和未签收配送单的状态占比
    public Map getDistributionStatusPieChat(CommonDeliveryCondition commonDeliveryCondition) {
        //获取西藏所有网点编号
        /*List<String> netWorkList = getNetWorkList();
        commonDeliveryCondition.setTargetNetworkCode(netWorkList);*/
        log.info("getDistributionStatusPieChat commonDeliveryCondition = {}", commonDeliveryCondition);
        Map<Integer, String> map = new HashMap<>();
        //获取运单量
        String waybillAmount = getAmount(commonDeliveryCondition, null);
        //获取已签收单量
        CommonDeliveryCondition tempCommonDeliveryCondition = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, tempCommonDeliveryCondition);
        String singedInAmount = getAmount(tempCommonDeliveryCondition, CommonDeliveryEnum.DISTRIBUTION_AMOUNT);
        map.put(DistributionStatusEnum.SINGED_IN.getCode(), singedInAmount);
        map.put(DistributionStatusEnum.NOT_SIGNED.getCode(), null);
        log.info("getDistributionStatusPieChat waybillAmount = {} , singedInAmount = {}", waybillAmount, singedInAmount);
        //未签收量 = 运单量 - 已签收单量
        if (StringUtils.isNotBlank(waybillAmount) && StringUtils.isNotBlank(singedInAmount)) {
            BigDecimal subtract = new BigDecimal(waybillAmount).subtract(new BigDecimal(singedInAmount));
            log.info("getDistributionStatusPieChat subtract = {}", subtract);
            map.put(DistributionStatusEnum.NOT_SIGNED.getCode(), subtract.stripTrailingZeros().toPlainString());
        }
        return map;
    }

    public String getAmount(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        //获取西藏所有网点编号
        /*List<String> netWorkList = getNetWorkList();
        commonDeliveryCondition.setTargetNetworkCode(netWorkList);*/
        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryCondition, commonDeliveryCondition1);
        if (commonDeliveryEnum != null) {
            commonDeliveryCondition1 = modifyCommonDeliveryCondition(commonDeliveryCondition1, commonDeliveryEnum);
            log.info("getAmount commonDeliveryCondition1={}", commonDeliveryCondition1);
        }
        SearchSourceBuilder sourceBuilder = QueryBuilder.build(commonDeliveryCondition1);
        log.info("getAmount sourceBuilder------->{}", sourceBuilder);
        SearchResponse result = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, sourceBuilder);
        log.info("getAmount result------->{}", result);
        long totalHits = result.getHits().getTotalHits().value;
        return String.valueOf(totalHits);
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
    //全年站点配送趋势图 近12个月站点配送趋势

    //共配类型占比图 共配来源占比图
    @Test
    public void testCommonDeliveryPieChat() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        String column = "";
        getCommonDeliveryPieChat(commonDeliveryCondition, column);
    }

    public Map<String, String> getCommonDeliveryPieChat(CommonDeliveryCondition commonDeliveryCondition, String
            column) {
        log.info("getCommonDeliveryPieChat commonDeliveryCondition = {}，column={}", commonDeliveryCondition, column);
        Map<String, String> map = new HashMap<>();
        //根据业务类型分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition(column);

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
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(key)) {
                        map.put(key, String.valueOf(bucket.getDocCount()));
                    }
                });
            }
        }
        return map;
    }

    @Test
    public void testSortingCenter() {
        System.out.println(getSortingCenter());
    }

    public Long getSortingCenter() {
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        //根据分拣中心id分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("picking_store_id");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(commonDeliveryCondition,termsAggregationCondition);
        log.info("getSortingCenter ssb = {}", ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, ssb);
        log.info("getSortingCenter searchResponse = {}", searchResponse);
        return searchResponse.getHits().getTotalHits().value;
    }
}
