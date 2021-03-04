package com.example.demo.bigscreen.workorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.PostSaleCondition;
import com.example.demo.condition.PostSaleEventCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.IndexOverviewDto;
import com.example.demo.enums.StateDistributionEnum;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/1 21:09
 */
@SpringBootTest
@Slf4j
public class WorkOrderTest {

    @Resource
    private EsQueryService esQueryService;

    private static final String postSaleEventIndex = "work_order_xizang";

    private static final String postSaleEventType = "_doc";

    @Test
    public void testWorkOrderStatus(){
        PostSaleCondition postSaleCondition = new PostSaleCondition();
        postSaleCondition.setCreateTime("2021-02-22");

        PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
        BeanUtils.copyProperties(postSaleCondition,postSaleEventCondition);
        postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime()+" 00:00:00");
        postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime()+" 23:59:59");
        System.out.println(doGetStateDistributionDiagram(postSaleEventCondition));

        System.out.println(getStateDistributionList(postSaleEventCondition));
    }

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

    /**
     * 工单状态
     * @param postSaleEventCondition
     * @return
     */
    private List getStateDistributionList(PostSaleEventCondition postSaleEventCondition){
        Map<String, String> stateDistributionDiagram = getStateDistributionDiagram(postSaleEventCondition);
        List<Map> distributionList = new ArrayList<>();
        for (StateDistributionEnum stateDistributionEnum : StateDistributionEnum.values()) {
            distributionList.add(JSONObject.parseObject(
                    JSON.toJSONString(IndexOverviewDto.builder()
                            .title(stateDistributionEnum.getName())
                            .num(stateDistributionDiagram.get(stateDistributionEnum.getStatus())==null?"0" : stateDistributionDiagram.get(stateDistributionEnum.getStatus()))
                            .percent("10%")
                            .build())));
        }
        return distributionList;
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


    /**
     * 工单类型
     */
    @Test
    public void testWorkOrderType(){
        PostSaleCondition postSaleCondition = new PostSaleCondition();
        postSaleCondition.setCreateTime("2021-02-22");

        PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
        BeanUtils.copyProperties(postSaleCondition,postSaleEventCondition);
        postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime()+" 00:00:00");
        postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime()+" 23:59:59");

        System.out.println(getWorkOrderType(postSaleEventCondition));
    }

    public Map getWorkOrderType(PostSaleEventCondition postSaleEventCondition) {
        Map<String, String> map = new HashMap<>();
        //根据状态分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("work_order_type");
        //根据工单号去重
        termsAggregationCondition.cardinality("mainid");
        termsAggregationCondition.getSum();
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(postSaleEventCondition, termsAggregationCondition);
        log.info("getStateDistributionDiagram ssb------->{}", ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(postSaleEventIndex, postSaleEventType, ssb);
        log.info("getStateDistributionDiagram searchResponse------->{}", searchResponse);
        List<Map<String, String>> list = ReportUtils.analySearchResponse(searchResponse, termsAggregationCondition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String, String> m : list) {
                String data = m.get("work_order_type");
                String dataSum = m.get("work_order_type_doc_count") == null ? "0" : m.get("work_order_type_doc_count");
                if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(dataSum)) {
                    map.put(data, dataSum);
                }
            }
        }
        return map;
    }

    /**
     * 站点工单Top5
     */
    @Test
    public void testTop5(){
        PostSaleCondition postSaleCondition = new PostSaleCondition();
        postSaleCondition.setCreateTime("2021-02-28");

        PostSaleEventCondition postSaleEventCondition = new PostSaleEventCondition();
        BeanUtils.copyProperties(postSaleCondition,postSaleEventCondition);
        postSaleEventCondition.setCreateTimeStart(postSaleCondition.getCreateTime()+" 00:00:00");
        postSaleEventCondition.setCreateTimeEnd(postSaleCondition.getCreateTime()+" 23:59:59");

        System.out.println(getTop5(postSaleEventCondition));
    }

    public List getTop5(PostSaleEventCondition postSaleEventCondition) {
        Map<String, Long> map = new HashMap<>();
        //根据状态分组查询
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("locate_attribute_id");
        //根据工单号去重
        termsAggregationCondition.cardinality("mainid");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        termsAggregationCondition.order("_count",false);
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(postSaleEventCondition, termsAggregationCondition);
        ssb.size(0);
        log.info("getStateDistributionDiagram ssb------->{}", ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(postSaleEventIndex, postSaleEventType, ssb);
        log.info("getStateDistributionDiagram searchResponse------->{}", searchResponse);
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if(CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket->{
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                    if (StringUtils.isNotBlank(key)) {
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedCardinality cardinality = (ParsedCardinality) asMap.get(AggregationHelper.AGG_CARDINALITY_TERM);
                        map.put(key,cardinality.getValue());
                    }
                });
            }
        }
        //自定义比较器
        Comparator<Map.Entry<String, Long>> valCmp = new Comparator<Map.Entry<String,Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (int) (o2.getValue()-o1.getValue());  // 降序排序，如果想升序就反过来
            }
        };
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String,Long>>(map.entrySet());
        Collections.sort(list,valCmp);
        //输出map
        for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getKey() + " = " + list.get(i).getValue());
        }

        return list;
    }
}
