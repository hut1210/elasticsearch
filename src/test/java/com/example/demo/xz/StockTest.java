package com.example.demo.xz;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.condition.WarehouseCondition;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/18 20:22
 */
@SpringBootTest
@Slf4j
public class StockTest {

    @Resource
    private EsQueryService esQueryService;

    private String wmsStockIndex="wms_stock_index";

    private String wmsStockType="_doc";

    @Test
    public void test(){
        Map<String, Long> map = new HashMap<>();
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(new WarehouseCondition(), termsAggregationCondition);
        log.info("CommonDistributionManager.getGoodsStock ssb = {}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(wmsStockIndex, wmsStockType, ssb);
        Map<String, Map<String, String>> stringMapMap = ReportUtils.analySearchResponse2Map(sr, termsAggregationCondition);
        System.out.println(stringMapMap);
        for (String key:stringMapMap.keySet()){
            Map<String, String> map1 = stringMapMap.get(key);
            System.out.println(map1+"    "+map1.get("warehouse_no")+"    "+map1.get("warehouse_no_doc_count"));
        }
    }
}
