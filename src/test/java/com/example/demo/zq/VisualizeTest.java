package com.example.demo.zq;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.condition.WarehouseCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.CompleteProgressDto;
import com.example.demo.dto.CompletionTrendDto;
import com.example.demo.dto.UnCompleteDto;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.XZReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 14:02
 */
@SpringBootTest
@Slf4j
public class VisualizeTest {

    @Resource
    private EsQueryService esQueryService;

    private static final String zq_odo_report_index = "zq_odo_report_index";

    private static final String type = "_doc";

    /**
     * 累计未完成数量Top5
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {

        List<UnCompleteDto> unCompleteDtoList = new ArrayList<>();
        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoNotInStatus("60000");
        condition.setIsDelete(new Byte("0"));
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        //未完成数量
        termsAggregationCondition.sum("real_qty", "real_qty");
        //未完成重量
        termsAggregationCondition.sum("odo_weight", "odo_weight");
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);

        /*List<Map<String, String>> maps = ReportUtils.analySearchResponse(searchResponse, termsAggregationCondition);
        System.out.println("排序前 = " + maps);*/

        /*Collections.sort(maps, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return new BigDecimal(o2.get("warehouse_no_realQtyNum")).compareTo(new BigDecimal(o1.get("warehouse_no_realQtyNum")));
            }
        });
        System.out.println("排序后 = "+maps);*/

        /*maps = maps.stream().sorted((s1, s2) -> new BigDecimal(s2.get("warehouse_no_real_qty")).compareTo(new BigDecimal(s1.get("warehouse_no_real_qty")))).collect(Collectors.toList());

        System.out.println("第二次排序 = " + maps);*/

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {

                for (Terms.Bucket bucket : teams.getBuckets()){
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        SearchHit searchHit = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny().get();
                        System.out.println("searchHit ==== " + searchHit.getSourceAsMap().get("warehouse_name"));
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum real_qty = (ParsedSum) asMap.get("real_qty");
                        ParsedSum odo_weight = (ParsedSum) asMap.get("odo_weight");

                        unCompleteDtoList.add(UnCompleteDto.builder()
                                .warehouseNo(key)
                                .warehouseName(searchHit.getSourceAsMap().get("warehouse_name").toString())
                                .unCompleteQty(real_qty.getValue())
                                .unCompleteWeight(odo_weight.getValue())
                                .build());
                    }
                }
            }
        }
        unCompleteDtoList = unCompleteDtoList.stream().sorted((s1, s2) -> new BigDecimal(s2.getUnCompleteQty()).compareTo(new BigDecimal(s1.getUnCompleteQty()))).collect(Collectors.toList());
        unCompleteDtoList.forEach(System.out::println);
    }

    /**
     * 完成进度 TOP5指标
     *
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {

        List<CompleteProgressDto> completeProgressDtoList = new ArrayList<>();
        completeProgressDtoList = getApplyQtyList();
        getCompleteList(completeProgressDtoList);

    }

    /**
     * 当日下达数量
     *
     * @return
     */
    private List getApplyQtyList() {
        List<CompleteProgressDto> completeProgressDtoList = new ArrayList<>();
        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoNotInStatus("10000");
        condition.setIsDelete(new Byte("0"));
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        //当日下达数量
        termsAggregationCondition.sum("apply_qty", "apply_qty");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        SearchHit searchHit = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny().get();
                        System.out.println("searchHit ==== " + searchHit.getSourceAsMap().get("warehouse_name"));
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum parsedSum = (ParsedSum) asMap.get("apply_qty");
                        System.out.println("parsedSum  = " + parsedSum.getValue());
                        completeProgressDtoList.add(CompleteProgressDto.builder()
                                .warehouseNo(key)
                                .warehouseName(searchHit.getSourceAsMap().get("warehouse_name").toString())
                                .applyQty(parsedSum.getValue())
                                .build());
                    }
                });
            }
        }

        return completeProgressDtoList;
    }

    /**
     * 完成数量
     *
     * @return
     */
    private void getCompleteList(List<CompleteProgressDto> completeProgressDtoList) {

        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoInStatus("60000");
        condition.setIsDelete(new Byte("0"));
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        //完成数量
        termsAggregationCondition.sum("real_qty", "real_qty");
        termsAggregationCondition.sum("unit_price", "unit_price");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key1-----" + key);
                        SearchHit searchHit = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny().get();
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum real_qty = (ParsedSum) asMap.get("real_qty");
                        ParsedSum unit_price = (ParsedSum) asMap.get("unit_price");

                        double completeQty = real_qty.getValue() * unit_price.getValue();

                        Optional<CompleteProgressDto> any = completeProgressDtoList.stream().filter(dto -> key.equals(dto.getWarehouseNo())).findAny();
                        if (any.isPresent()) {
                            CompleteProgressDto completeProgressDto = any.get();
                            completeProgressDto.setCompleteQty(completeQty);
                            completeProgressDto.setPercent(XZReportUtils.toPercent(new BigDecimal(String.valueOf(completeQty)), new BigDecimal(completeProgressDto.getApplyQty())));
                        } else {
                            completeProgressDtoList.add(CompleteProgressDto.builder()
                                    .warehouseNo(key)
                                    .warehouseName(searchHit.getSourceAsMap().get("warehouse_name").toString())
                                    .completeQty(completeQty)
                                    .build());
                        }
                    }
                });
            }
        }

        /*List<Map<String, String>> maps = ReportUtils.analySearchResponse(searchResponse, termsAggregationCondition);
        System.out.println(maps);*/
        completeProgressDtoList.stream().sorted((s1, s2) -> new BigDecimal(s2.getPercent()).compareTo(new BigDecimal(s1.getPercent()))).collect(Collectors.toList());
        completeProgressDtoList.forEach(System.out::println);
    }


    /**
     * 累计未完成数量、累计未完成金额
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        List<CompletionTrendDto> unFinishedList = getUnFinishedList();
        getFinishedList(unFinishedList);
    }

    /**
     * 未完成数量、未完成金额
     *
     * @return
     */
    private List<CompletionTrendDto> getUnFinishedList() {
        List<CompletionTrendDto> completionTrendDtoList = new ArrayList<>();
        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoNotInStatus("60000");
        condition.setIsDelete(new Byte("0"));
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        //完成数量
        termsAggregationCondition.sum("real_qty", "real_qty");
        termsAggregationCondition.sum("unit_price", "unit_price");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key2-----" + key);
                        SearchHit searchHit = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny().get();
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum real_qty = (ParsedSum) asMap.get("real_qty");
                        ParsedSum unit_price = (ParsedSum) asMap.get("unit_price");

                        double unFinishedQty = real_qty.getValue() * unit_price.getValue();
                        completionTrendDtoList.add(CompletionTrendDto.builder()
                                .warehouseNo(key)
                                .warehouseName(searchHit.getSourceAsMap().get("warehouse_name").toString())
                                .unfinishedQty(real_qty.getValue())
                                .unfinishedAmount(unFinishedQty)
                                .build());
                    }
                });
            }
        }

        return completionTrendDtoList;
    }

    /**
     * 完成数量
     *
     * @return
     */
    private void getFinishedList(List<CompletionTrendDto> completionTrendDtoList) {

        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoInStatus("60000");
        condition.setIsDelete(new Byte("0"));
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);
        //完成数量
        termsAggregationCondition.sum("real_qty", "real_qty");
        termsAggregationCondition.sum("unit_price", "unit_price");

        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {
                teams.getBuckets().forEach(bucket -> {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key3-----" + key);
                        SearchHit searchHit = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny().get();
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum real_qty = (ParsedSum) asMap.get("real_qty");
                        ParsedSum unit_price = (ParsedSum) asMap.get("unit_price");

                        double finishedAmount = real_qty.getValue() * unit_price.getValue();
                        Optional<CompletionTrendDto> any = completionTrendDtoList.stream().filter(dto -> key.equals(dto.getWarehouseNo())).findAny();
                        if (any.isPresent()) {
                            CompletionTrendDto completionTrendDto = any.get();
                            completionTrendDto.setFinishedQty(real_qty.getValue());
                            completionTrendDto.setFinishedAmount(finishedAmount);
                        } else {
                            completionTrendDtoList.add(CompletionTrendDto.builder()
                                    .warehouseNo(key)
                                    .warehouseName(searchHit.getSourceAsMap().get("warehouse_name").toString())
                                    .finishedQty(real_qty.getValue())
                                    .finishedAmount(finishedAmount)
                                    .build());
                        }

                    }
                });
            }
        }
        completionTrendDtoList.forEach(System.out::println);
    }

}
