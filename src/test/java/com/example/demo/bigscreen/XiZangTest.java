package com.example.demo.bigscreen;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.PostSaleEventCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.condition.WorkBillCondition;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.constant.ReportConstant;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.BeanUtil;
import com.example.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/2 10:08
 */
@SpringBootTest
@Slf4j
public class XiZangTest {

    @Resource
    private EsQueryService esQueryService;

    private static final String workOrderIndex = "work_order_xizang";

    private static final String workOrderType = "_doc";

    @Test
    public void testBillCount() {
        // 获取时间
        Map<String, Date> dateWhitBeforeN = DateUtils.getDateWhitBeforeN(7);

        WorkBillCondition workBillCondition = new WorkBillCondition();
        workBillCondition.setCreate_time_start(DateUtils.formatDate(dateWhitBeforeN.get("start"), DateUtils.DATE_FORMAT) + " 00:00:00");
        workBillCondition.setCreate_time_end(DateUtils.formatDate(dateWhitBeforeN.get("end"), DateUtils.DATE_FORMAT) + " 23:59:59");
        workBillCondition.setCreate_time_start("2021-12-10");
        workBillCondition.setCreate_time_end("2021-12-10");
        Map<String, String> params = new HashMap<>();
        params.put("groupField", "mainid");
        params.put("cardinality", "mainid");

        //workBillCondition.setTargetNetworkCode(CommonDeliveryConstant.siteList);
        // 待处理
        //workBillCondition.setStatusList(Arrays.asList("20", "60", "80", "100", "180", "200"));
        Long pending = getBillCountNew(workBillCondition);

        // 已处理
        workBillCondition.setStatusList(Arrays.asList("220", "160"));
        Long complete = getBillCount(workBillCondition);

        System.out.println("pending = " + pending + ",complete = " + complete);
    }

    /**
     * 待处理、已处理工单数量
     *
     * @param workBillCondition
     * @return
     */
    public long getBillCount(WorkBillCondition workBillCondition) {
        List<String> datesBetween2Date = DateUtils.getDatesBetween2Date(workBillCondition.getCreate_time_start(), workBillCondition.getCreate_time_end());
        AtomicLong amount = new AtomicLong(0L);
        for (String s : datesBetween2Date) {
            WorkBillCondition tempWorkBillCondition = new WorkBillCondition();
            BeanUtils.copyProperties(workBillCondition, tempWorkBillCondition);
            tempWorkBillCondition.setCreate_time_start(s + " 00:00:00");
            tempWorkBillCondition.setCreate_time_end(s + " 23:59:59");
            TermsAggregationCondition condition1 = new TermsAggregationCondition("mainid");
            condition1.cardinality("mainid");
            condition1.size(ReportConstant.PAGE_MAX_SIZE);
            condition1.order("_count", false);
            SearchSourceBuilder ssb = QueryBuilder.buildGroup(tempWorkBillCondition, condition1);
            log.info("getBillCount ssb------->{}", ssb);
            SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(workOrderIndex, workOrderType, ssb);
            log.info("getBillCount sr------->{}", sr);

            Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();
            if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
                System.out.println("teams.getSumOfOtherDocCounts()=" + teams.getSumOfOtherDocCounts());
                if (teams.getSumOfOtherDocCounts() > 0) {
                    //按小时分组查询
                } else {
                    if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                        teams.getBuckets().forEach(bucket -> {
                            String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                            if (StringUtils.isNotBlank(key)) {
                                Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                                ParsedCardinality cardinality = (ParsedCardinality) asMap.get(AggregationHelper.AGG_CARDINALITY_TERM);
                                amount.addAndGet(cardinality.getValue());
                            }
                        });
                    }
                }

            }
        }
        return amount.get();
    }

    public long getBillCountNew(WorkBillCondition workBillCondition) {
        //1.按天查询，求和
        List<String> datesBetween2Date = DateUtils.getDatesBetween2Date(workBillCondition.getCreate_time_start(), workBillCondition.getCreate_time_end());
        long count = 0L;
        for (String s : datesBetween2Date) {
            WorkBillCondition tempWorkBillCondition = new WorkBillCondition();
            BeanUtils.copyProperties(workBillCondition, tempWorkBillCondition);
            tempWorkBillCondition.setCreate_time_start(s + " 00:00:00");
            tempWorkBillCondition.setCreate_time_end(s + " 23:59:59");
            log.info("tempWorkBillCondition ={}", tempWorkBillCondition);
            ParsedStringTerms teams = getAggregation(tempWorkBillCondition);
            log.info("tempWorkBillCondition teams.getSumOfOtherDocCounts()={}", teams.getSumOfOtherDocCounts());
            if (teams != null && teams.getSumOfOtherDocCounts() > 0) {
                //2.按小时分组查询
                for (int i = 0; i < CommonDeliveryConstant.postSaleEventTime.keySet().size(); i++) {
                    //查询各个时间段的数量
                    String timeSlot = CommonDeliveryConstant.postSaleEventTime.get(i);
                    if (StringUtils.isNotEmpty(timeSlot)) {
                        String[] times = timeSlot.split("-");
                        WorkBillCondition hourWorkBillCondition = new WorkBillCondition();
                        BeanUtils.copyProperties(workBillCondition, hourWorkBillCondition);
                        hourWorkBillCondition.setCreate_time_start(s + " " + times[0]);
                        hourWorkBillCondition.setCreate_time_end(s + " " + times[1]);
                        log.info("hourWorkBillCondition = {},i = {}", hourWorkBillCondition, i);
                        ParsedStringTerms hourTeams = getAggregation(hourWorkBillCondition);
                        log.info("hourWorkBillCondition teams.getSumOfOtherDocCounts()={}", hourTeams.getSumOfOtherDocCounts());
                        if (hourTeams != null && hourTeams.getSumOfOtherDocCounts() > 0) {
                            //3.按照分钟查询
                            for (int j = 0; j < CommonDeliveryConstant.postSaleEventHourTime.keySet().size(); j++) {
                                //查询各个时间段的数量
                                String minuteTimeSlot = CommonDeliveryConstant.postSaleEventHourTime.get(j);
                                if (StringUtils.isNotEmpty(minuteTimeSlot)) {
                                    String[] minuteTimes = minuteTimeSlot.split("-");
                                    WorkBillCondition minuteWorkBillCondition = new WorkBillCondition();
                                    BeanUtils.copyProperties(workBillCondition, minuteWorkBillCondition);
                                    String hour = new StringBuilder(String.valueOf(i)).toString();
                                    if (i < 10) {
                                        hour = new StringBuilder(String.valueOf(0)).append(i).toString();
                                    }
                                    minuteWorkBillCondition.setCreate_time_start(s + " " + hour + minuteTimes[0]);
                                    minuteWorkBillCondition.setCreate_time_end(s + " " + hour + minuteTimes[1]);
                                    log.info("minuteWorkBillCondition = {} ,j = {}", minuteWorkBillCondition, j);
                                    ParsedStringTerms minuteTeams = getAggregation(minuteWorkBillCondition);
                                    count += getAmount(minuteTeams.getBuckets());
                                }
                            }

                        } else {
                            count += getAmount(teams.getBuckets());
                        }
                    }
                }
            } else {
                count += getAmount(teams.getBuckets());
            }
        }
        return count;
    }

    public ParsedStringTerms getAggregation(WorkBillCondition tempWorkBillCondition) {
        TermsAggregationCondition condition1 = new TermsAggregationCondition("mainid");
        //condition1.cardinality("mainid");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        condition1.order("_count", false);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(tempWorkBillCondition, condition1);
        log.info("getAggregation ssb------->{}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(workOrderIndex, workOrderType, ssb);
        log.info("getAggregation sr------->{}", sr);
        ParsedStringTerms teams = null;
        Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
        }
        return teams;
    }

    private long getAmount(List<? extends Terms.Bucket> list) {
        AtomicLong amount = new AtomicLong(0L);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(bucket -> {
                String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                if (StringUtils.isNotBlank(key)) {
                    Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                    ParsedCardinality cardinality = (ParsedCardinality) asMap.get(AggregationHelper.AGG_CARDINALITY_TERM);
                    amount.addAndGet(cardinality.getValue());
                }
            });
        }
        return amount.get();
    }

    /**
     * 待处理、已处理工单数量
     *
     * @param workBillCondition
     * @return
     */
    public long getBillCount2(WorkBillCondition workBillCondition) {
        TermsAggregationCondition condition1 = new TermsAggregationCondition("mainid");
        condition1.cardinality("mainid");
        condition1.size(ReportConstant.PAGE_MAX_SIZE);
        condition1.order("_count", false);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(workBillCondition, condition1);
        log.info("getBillCount ssb------->{}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(workOrderIndex, workOrderType, ssb);
        log.info("getBillCount sr------->{}", sr);
        AtomicLong amount = new AtomicLong(0L);
        Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            System.out.println("teams.getSumOfOtherDocCounts()=" + teams.getSumOfOtherDocCounts());
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedCardinality cardinality = (ParsedCardinality) asMap.get(AggregationHelper.AGG_CARDINALITY_TERM);
                        amount.addAndGet(cardinality.getValue());
                    }
                });
            }
        }
        return amount.get();
    }
}
