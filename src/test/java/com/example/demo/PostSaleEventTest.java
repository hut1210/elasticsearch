package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.condition.*;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.constant.PostSaleEventConstant;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.PostSaleOverviewDto;
import com.example.demo.enums.StateDistributionEnum;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.BeanUtil;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.util.unit.DataUnit;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/23 11:14
 */
@SpringBootTest
@Slf4j
public class PostSaleEventTest {
    @Resource
    private EsQueryService esQueryService;

    private static final String postSaleEventIndex = "work_order_xizang";

    private static final String postSaleEventType = "_doc";

    @Test
    public void queryPostSaleEventPage() {
        PostSaleCondition postSaleCondition = new PostSaleCondition();
        postSaleCondition.setCreateTime("2021-02-22");

        PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
        BeanUtils.copyProperties(postSaleCondition,postSaleEventCondition);
        postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime()+" 00:00:00");
        postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime()+" 23:59:59");
        if (StringUtils.isEmpty(postSaleCondition.getCreateTime())) {
            postSaleCondition.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT));
        }
        List<PostSaleOverviewDto> rows = new ArrayList<>();
        //查询
        SearchSourceBuilder ssb = QueryBuilder.build(postSaleEventCondition);
        ssb.size(5);
        ssb.from(ReportUtils.getFrom(5, 1));
        ssb.sort("create_time", SortOrder.DESC);
        log.info("queryPostSaleEventPage ssb------->{}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(postSaleEventIndex, postSaleEventType, ssb);
        log.info("queryPostSaleEventPage sr------->{}", sr);
        for (SearchHit hit : sr.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            rows.add(PostSaleOverviewDto.builder()
                    .workOrderNo(ReportUtils.getString(map.get("mainid")))
                    .createTime(ReportUtils.getString(map.get("create_time")))
                    .createUser(ReportUtils.getString(map.get("create_name")))
                    .clueType(ReportUtils.getString(map.get("originate_clue_type")))
                    .clueValue(ReportUtils.getString(map.get("originate_clue_value")))
                    .workOrderType(ReportUtils.getString(map.get("work_order_type")))
                    .workOrderStatus(ReportUtils.getString(map.get("status")))
                    .problemType(ReportUtils.getString(map.get("biztype_first_name"))+ReportUtils.getString(map.get("biztype_second_name")))
                    .processingParty(ReportUtils.getString(map.get("process_dept_name_level1"))+ReportUtils.getString(map.get("process_dept_name_level2"))
                            +ReportUtils.getString(map.get("process_dept_name_level3")))
                    .totalProcessingTime(ReportUtils.getString(map.get("oper_time")))
                    .upgradeStatus(ReportUtils.getString(map.get("upgrade_status")))
                    .build());
        }
        Long total = sr.getHits().getTotalHits().value;
        rows.forEach(System.out::println);
    }

    @Test
    public void testQueryPostSaleEventIndexOverview() {
        PostSaleCondition postSaleCondition = new PostSaleCondition();
        postSaleCondition.setCreateTime("2021-02-22");

        PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
        BeanUtils.copyProperties(postSaleCondition,postSaleEventCondition);
        postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime()+" 00:00:00");
        postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime()+" 23:59:59");
        if (StringUtils.isEmpty(postSaleCondition.getCreateTime())) {
            postSaleCondition.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT));
        }
        System.out.println(JSON.toJSONString(queryPostSaleEventIndexOverview(postSaleCondition,postSaleEventCondition)));
    }

    public Map queryPostSaleEventIndexOverview(PostSaleCondition postSaleCondition,PostSaleEventCondition postSaleEventCondition) {
        Map responseMap = new HashMap();
        //创建数量折线图
        responseMap.put("createAmountLineChart", doGetCreateAmountLineChart(postSaleCondition));
        //解决数量折线图
        responseMap.put("solveAmountLineChart", doGetSolveAmountLineChart(postSaleCondition));

        //状态分布柱状图
        responseMap.put("stateDistributionDiagram", doGetStateDistributionDiagram(postSaleEventCondition));
        return responseMap;
    }

    /**
     * 创建数量折线图
     *
     * @param postSaleCondition
     * @return
     */
    private Map doGetCreateAmountLineChart(PostSaleCondition postSaleCondition) {
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        for (Integer i : CommonDeliveryConstant.postSaleEventTime.keySet()) {
            //查询各个时间段的数量
            String timeSlot = CommonDeliveryConstant.postSaleEventTime.get(i);
            long l = 0L;
            if (StringUtils.isNotEmpty(timeSlot)) {
                String[] times = timeSlot.split("-");
                PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
                BeanUtils.copyProperties(postSaleCondition, postSaleEventCondition);
                postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime() + " " + times[0]);
                postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime() + " " + times[1]);
                l = getSolveAmountLineChart(postSaleEventCondition);
            }
            xdataList.add(l);
            seriesList.add(i);
        }
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    /**
     * 解决数量折线图
     *
     * @param postSaleCondition
     * @return
     */
    private Map doGetSolveAmountLineChart(PostSaleCondition postSaleCondition) {
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        for (Integer i : CommonDeliveryConstant.postSaleEventTime.keySet()) {
            //查询各个时间段的数量
            String timeSlot = CommonDeliveryConstant.postSaleEventTime.get(i);
            long l = 0L;
            if (StringUtils.isNotEmpty(timeSlot)) {
                String[] times = timeSlot.split("-");
                PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
                BeanUtils.copyProperties(postSaleCondition, postSaleEventCondition);
                postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime() + " " + times[0]);
                postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime() + " " + times[1]);
                postSaleEventCondition.setStatus(PostSaleEventConstant.solveStatusList);
                l = getSolveAmountLineChart(postSaleEventCondition);
            }
            xdataList.add(l);
            seriesList.add(i);
        }
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    private long getSolveAmountLineChart(PostSaleEventCondition postSaleEventCondition) {
        SearchSourceBuilder ssb = QueryBuilder.build(postSaleEventCondition);
        log.info("getSolveAmountLineChart ssb------->{}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(postSaleEventIndex, postSaleEventType, ssb);
        log.info("getSolveAmountLineChart sr------->{}", sr);
        long value = sr.getHits().getTotalHits().value;
        return value;
    }

    /**
     * 状态分布柱状图
     *
     * @param postSaleEventCondition
     * @return
     */
    private Map doGetStateDistributionDiagram(PostSaleEventCondition postSaleEventCondition) {
        Map map = new HashMap();
        List numList = new ArrayList();
        List statusNameList = new ArrayList();
        Map<String, String> stateDistributionDiagram = getStateDistributionDiagram(postSaleEventCondition);
        for (StateDistributionEnum stateDistributionEnum : StateDistributionEnum.values()) {
            numList.add(stateDistributionDiagram.get(stateDistributionEnum.getStatus())==null?"0" : stateDistributionDiagram.get(stateDistributionEnum.getStatus()));
            statusNameList.add(stateDistributionEnum.getName());
        }
        map.put("num", numList);
        map.put("statusName", statusNameList);
        return map;
    }

    public Map getStateDistributionDiagram(PostSaleEventCondition postSaleEventCondition) {
        Map<String, String> map = new HashMap<>();
        //根据状态分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("status");
        //根据工单号去重
        termsAggregationCondition.cardinality("mainid");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(postSaleEventCondition, termsAggregationCondition);
        log.info("getStateDistributionDiagram ssb------->{}", ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(postSaleEventIndex, postSaleEventType, ssb);
        log.info("getStateDistributionDiagram searchResponse------->{}", searchResponse);
        List<Map<String, String>> list = ReportUtils.analySearchResponse(searchResponse, termsAggregationCondition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String, String> m : list) {
                String data = m.get("status");
                String dataSum = m.get("status_doc_count") == null ? "0" : m.get("status_doc_count");
                if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(dataSum)) {
                    map.put(data, dataSum);
                }
            }
        }
        return map;
    }
}
