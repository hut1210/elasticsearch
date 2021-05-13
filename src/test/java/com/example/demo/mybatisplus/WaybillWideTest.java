package com.example.demo.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.example.demo.DemoApplication;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.WaybillWideCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.domain.WaybillWide;
import com.example.demo.mapper.WaybillWideMapper;
import com.example.demo.service.EsQueryService;
import com.example.demo.service.WaybillWideService;
import com.example.demo.service.impl.WaybillWideServiceImpl;
import com.example.demo.util.DateUtil;
import com.example.demo.util.DateUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/8 14:03
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class WaybillWideTest {

    @Resource
    private WaybillWideMapper waybillWideMapper;

    @Resource
    private EsQueryService esQueryService;

    private static final String commonDeliveryMonitorIndex = "waybill_wide_new";

    private static final String commonDeliveryMonitorType = "_doc";

    @Resource
    private WaybillWideService waybillWideService;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() throws IOException {
        List<WaybillWide> waybillWideList = new ArrayList<>();
        WaybillWideCondition condition = new WaybillWideCondition();
        //condition.setCreateTimeStart("2021-03-02");
        condition.setWaybillCode("JD0038417202331");
        SearchSourceBuilder searchSourceBuilder = com.example.demo.builder.QueryBuilder.build(condition);
        //searchSourceBuilder.sort("first_time");
        searchSourceBuilder.size(ReportConstant.PAGE_MAX_SIZE);
        System.out.println(searchSourceBuilder);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(commonDeliveryMonitorIndex, commonDeliveryMonitorType, searchSourceBuilder);
        System.out.println(searchResponse);
        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                waybillWideList.add(WaybillWide.builder()
                        .waybillCode(sourceAsMap.get("waybill_code") != null ? sourceAsMap.get("waybill_code").toString() : "")
                        .cId(sourceAsMap.get("c_id") != null ? sourceAsMap.get("c_id").toString() : "")
                        .firstTime(sourceAsMap.get("first_time") != null ? DateUtil.parseDate(sourceAsMap.get("first_time").toString(), DateUtil.DATETIME_FORMAT) : null)
                        .firstTimeDate(sourceAsMap.get("first_time_date") != null ? DateUtils.parseDate(sourceAsMap.get("first_time_date").toString(), DateUtil.DATE_FORMAT) : null)
                        .arriveAreaId(sourceAsMap.get("arrive_area_id") != null ? sourceAsMap.get("arrive_area_id").toString() : "")
                        .arriveArea(sourceAsMap.get("arrive_area") != null ? sourceAsMap.get("arrive_area").toString() : "")
                        .fromProvinceId(sourceAsMap.get("from_province_id") != null ? sourceAsMap.get("from_province_id").toString() : "")
                        .fromProvinceName(sourceAsMap.get("from_province_name") != null ? sourceAsMap.get("from_province_name").toString() : "")
                        .fromCityId(sourceAsMap.get("from_city_id") != null ? sourceAsMap.get("from_city_id").toString() : "")
                        .fromCityName(sourceAsMap.get("from_city_name") != null ? sourceAsMap.get("from_city_name").toString() : "")
                        .fromCountyId(sourceAsMap.get("from_county_id") != null ? sourceAsMap.get("from_county_id").toString() : "")
                        .fromCountyName(sourceAsMap.get("from_county_name") != null ? sourceAsMap.get("from_county_name").toString() : "")
                        .toProvinceId(sourceAsMap.get("to_province_id") != null ? sourceAsMap.get("to_province_id").toString() : "")
                        .toProvinceName(sourceAsMap.get("to_province_name") != null ? sourceAsMap.get("to_province_name").toString() : "")
                        .toCityId(sourceAsMap.get("to_city_id") != null ? sourceAsMap.get("to_city_id").toString() : "")
                        .toCityName(sourceAsMap.get("to_city_name") != null ? sourceAsMap.get("to_city_name").toString() : "")
                        .toCountyId(sourceAsMap.get("to_county_id") != null ? sourceAsMap.get("to_county_id").toString() : "")
                        .toCountyName(sourceAsMap.get("to_county_name") != null ? sourceAsMap.get("to_county_name").toString() : "")
                        .oldSiteId(sourceAsMap.get("old_site_id") != null ? sourceAsMap.get("old_site_id").toString() : "")
                        .oldSiteName(sourceAsMap.get("old_site_name") != null ? sourceAsMap.get("old_site_name").toString() : "")
                        .oldSiteArea(sourceAsMap.get("old_site_area") != null ? sourceAsMap.get("old_site_area").toString() : "")
                        .pickupSiteId(sourceAsMap.get("pickup_site_id") != null ? sourceAsMap.get("pickup_site_id").toString() : "")
                        .pickupSiteName(sourceAsMap.get("pickup_site_name") != null ? sourceAsMap.get("pickup_site_name").toString() : "")
                        .pickupSiteArea(sourceAsMap.get("pickup_site_area") != null ? sourceAsMap.get("pickup_site_area").toString() : "")
                        .pickingStoreId(sourceAsMap.get("picking_store_id") != null ? sourceAsMap.get("picking_store_id").toString() : "")
                        .pickingStoreName(sourceAsMap.get("picking_store_name") != null ? sourceAsMap.get("picking_store_name").toString() : "")
                        .businessType(sourceAsMap.get("business_type") != null ? sourceAsMap.get("business_type").toString() : "")
                        .distributeStoreId(sourceAsMap.get("distribute_store_id") != null ? sourceAsMap.get("distribute_store_id").toString() : "")
                        .distributeStoreName(sourceAsMap.get("distribute_store_name") != null ? sourceAsMap.get("distribute_store_name").toString() : "")
                        .distributionType(sourceAsMap.get("distribution_type") != null ? sourceAsMap.get("distribution_type").toString() : "")
                        .goodVolume(sourceAsMap.get("good_volume") != null ? sourceAsMap.get("good_volume").toString() : "")
                        .goodWeight(sourceAsMap.get("good_weight") != null ? sourceAsMap.get("good_weight").toString() : "")
                        .codMoney(sourceAsMap.get("cod_money") != null ? sourceAsMap.get("cod_money").toString() : "")
                        .receivedMoney(sourceAsMap.get("received_money") != null ? sourceAsMap.get("received_money").toString() : "")
                        .payment(sourceAsMap.get("payment") != null ? sourceAsMap.get("payment").toString() : "")
                        .goodNumber(sourceAsMap.get("good_number") != null ? sourceAsMap.get("good_number").toString() : "")
                        .relWaybillCode(sourceAsMap.get("rel_waybill_code") != null ? sourceAsMap.get("rel_waybill_code").toString() : "")
                        .distributeType(sourceAsMap.get("distribute_type") != null ? sourceAsMap.get("distribute_type").toString() : "")
                        .thirdWaybillCode(sourceAsMap.get("third_waybill_code") != null ? sourceAsMap.get("third_waybill_code").toString() : "")
                        .busiName(sourceAsMap.get("busi_name") != null ? sourceAsMap.get("busi_name").toString() : "")
                        .storeProvinceId(sourceAsMap.get("store_province_id") != null ? sourceAsMap.get("store_province_id").toString() : "")
                        .storeProvinceName(sourceAsMap.get("store_province_name") != null ? sourceAsMap.get("store_province_name").toString() : "")
                        .storeCityId(sourceAsMap.get("store_city_id") != null ? sourceAsMap.get("store_city_id").toString() : "")
                        .storeCityName(sourceAsMap.get("store_city_name") != null ? sourceAsMap.get("store_city_name").toString() : "")
                        .storeCountyId(sourceAsMap.get("store_county_id") != null ? sourceAsMap.get("store_county_id").toString() : "")
                        .storeCountyName(sourceAsMap.get("store_county_name") != null ? sourceAsMap.get("store_county_name").toString() : "")
                        .state(sourceAsMap.get("state") != null ? sourceAsMap.get("state").toString() : "")
                        .stateCreateTime(sourceAsMap.get("state_create_time") != null ? DateUtils.parseDate(sourceAsMap.get("state_create_time").toString(), DateUtil.DATE_FORMAT) : null)
                        .realStartDate(sourceAsMap.get("real_start_date") != null ? DateUtils.parseDate(sourceAsMap.get("real_start_date").toString(), DateUtil.DATE_FORMAT) : null)
                        .realStartTime(sourceAsMap.get("real_start_time") != null ? DateUtils.parseDate(sourceAsMap.get("real_start_time").toString(), DateUtil.DATE_FORMAT) : null)
                        .requireStartTime(sourceAsMap.get("require_start_time") != null ? DateUtils.parseDate(sourceAsMap.get("require_start_time").toString(), DateUtil.DATE_FORMAT) : null)
                        .operatorSite(sourceAsMap.get("operator_site") != null ? sourceAsMap.get("operator_site").toString() : "")
                        .operatorSiteId(sourceAsMap.get("operator_site_id") != null ? sourceAsMap.get("operator_site_id").toString() : "")
                        .updateTime(sourceAsMap.get("update_time") != null ? DateUtils.parseDate(sourceAsMap.get("update_time").toString(), DateUtil.DATE_FORMAT) : null)
                        .build());
            }
        }

        waybillWideList.forEach(System.out::println);
        /*for (WaybillWide waybillWide : waybillWideList) {
            waybillWideMapper.insert(waybillWide);
        }*/
        //waybillWideService.saveBatch(waybillWideList);


        //1.创建批量导入数据
        BulkRequest bulkRequest = new BulkRequest();
        //设置多长时间导入一次
        bulkRequest.timeout("10s");
        //2.定义一个集合

        //3.将数据批量添加
        for (int i = 0; i < waybillWideList.size(); i++) {
            //如果需要做批量删除或者批量更新，修改这里请求即可
            bulkRequest.add(
                    new IndexRequest("waybill_wide_new_1")
                            //不填id时将会生成随机id
                            .id(waybillWideList.get(i).getWaybillCode())
                            .source(JSON.toJSONString(waybillWideList.get(i)), XContentType.JSON)
            );
        }
        //4.执行请求
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        //5.响应 判断是否执行成功
        RestStatus status = bulkResponse.status();
        System.out.println(status.getStatus());

    }
}
