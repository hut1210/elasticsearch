package com.example.demo.zq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.condition.WarehouseCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.dto.*;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.DateUtils;
import com.example.demo.util.RSAUtils;
import com.example.demo.util.StringUtil;
import com.example.demo.util.XZReportUtils;
import com.mysql.cj.xdevapi.JsonArray;
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
import java.security.PrivateKey;
import java.security.PublicKey;
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

    @Test
    public void test() {
        List<UnCompleteDto> unCompleteDtoList = new ArrayList<>();
        WarehouseCondition condition = new WarehouseCondition();
        condition.setOdoNotInStatus("60000");
        condition.setIsDelete(new Byte("0"));
        /*List<String> warehouseNos = new ArrayList<>();
        warehouseNos.add("SWHS0001032216439");
        warehouseNos.add("SWHS0001032212848");
        warehouseNos.add("SWHS0001032212963");
        condition.setWarehouseNos(warehouseNos);*/

        SearchResponse searchResponse = commonGroupProcess(condition, "real_qty", "odo_weight");
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
        if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            if (CollectionUtils.isNotEmpty(teams.getBuckets())) {

                for (Terms.Bucket bucket : teams.getBuckets()) {
                    String key = bucket.getKey() == null ? "" : bucket.getKey().toString();
                    if (StringUtils.isNotBlank(key)) {
                        System.out.println("key-----" + key);
                        String warehouseName = null;
                        Optional<SearchHit> searchHitOptional = searchHits.stream().filter(b -> key.equals(b.getSourceAsMap().get("warehouse_no"))).findAny();
                        if (searchHitOptional.isPresent()) {
                            SearchHit searchHit = searchHitOptional.get();
                            warehouseName = searchHit.getSourceAsMap().get("warehouse_name").toString();
                        }
                        System.out.println("warehouseName ==== " + warehouseName);
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        ParsedSum real_qty = (ParsedSum) asMap.get("real_qty");
                        ParsedSum odo_weight = (ParsedSum) asMap.get("odo_weight");

                        unCompleteDtoList.add(UnCompleteDto.builder()
                                .warehouseNo(key)
                                .warehouseName(warehouseName)
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

    public SearchResponse commonGroupProcess(WarehouseCondition condition, String... args) {
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        termsAggregationCondition.size(ReportConstant.PAGE_MAX_SIZE);

        for (int i = 0; i < args.length; i++) {
            termsAggregationCondition.sum(args[i], args[i]);
        }
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(condition, termsAggregationCondition);
        log.info("ssb ----> " + ssb);
        SearchResponse searchResponse = esQueryService.queryByIndexAndSourceBuilder(zq_odo_report_index, type, ssb);
        log.info("searchResponse ----> " + searchResponse);
        return searchResponse;
    }

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
        condition.setEnterpriseId("62");
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

                for (Terms.Bucket bucket : teams.getBuckets()) {
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

    private List<CompletionTrendDto> getCompletionTrend(WarehouseCondition condition) {

        List<CompletionTrendDto> completionTrendDtoList = new ArrayList<>();


        return completionTrendDtoList;

    }


    @Test
    public void test4() throws Exception {
        //String key = StringUtil.encodeStringParameter(content, "ISO-8859-1", "UTF-8", "123");
        String prikey = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100ad7235961483cf1e6b15a61d315de28e80b92bd209641abab738d735490cdff2bb523391efaa92ed9ee743d649752489634572c" +
                "ddad73b2ed6c817d76080d7252420f78f3247cec06151d3a445721601da5ac965bc40384f343f48869e1cdea0dca1529b176adf9258b1110631213a51fe54dc0452bc98d161d653c38afb557b020301000102818100a343d081374a639a6" +
                "47e7a611c691dabba2b93be202ce4a44177f5cc28b93fcd02dafbc4316fc4e27c23814da2d3ebd28f697e099c27e183d395822bb0a156e2081181bee5869a848930d695ed1f464708b18ff2e73e4e87999853d3a32feab16f7e949967a71" +
                "789acd586642651a38576b6ecc8ea1f25c83ae06cc3178a72b1024100dd67a87fde508934217c85f2c10c34cc129e43a93295cd88ca7b5d53ee1c6bc2356fc63b39da46555ac81358c78077101faf3c3b6a513f0299b698b7001f981f024" +
                "100c88c2b14608cd460eb25a708f88b9cade770326c6ce93667bb314f331708cc6ed952a587cf6b9a3d43a13832eeda7a81cefe0cbdfb5df289f3630887048e87250240313113c5fd3e058df896b328216e35d8d59626505475e629ab5b4" +
                "77c1b5e632ece329c67924e05ec76f41720558b1c690fb11e7fd0fa66368cb27e31c2ac425f02401bd9833e002e6e6bcb1bde775b749fdc72905d203b7ba367825255aa459f9e2c2297dc8ee09a13c0403d0a" +
                "3de0778c9dac2e117cec845e6313de83d7a4255ad502403c2aaa48767c3b898fdbeffa1508a44362bd2effe1fffe215a2171aff13e021757f78602a0d402787a1ccee6fd167cc821d894c106cb3b61a1d4703926291061";

        String pubkey = "30819f300d06092a864886f70d010101050003818d0030818902818100ad7235961483cf1e6b15a61d315de28e80b92bd209641abab738d735490cdff2bb523391efaa92ed9ee743d649752489634572cddad73b2ed6c817d76080" +
                "d7252420f78f3247cec06151d3a445721601da5ac965bc40384f343f48869e1cdea0dca1529b176adf9258b1110631213a51fe54dc0452bc98d161d653c38afb557b0203010001";
        System.out.println("prikey === " + prikey);
        PublicKey publicKey = RSAUtils.getPublicKey(pubkey);
        System.out.println(publicKey);

        String content = "{\n" +
                "\t\"tenantId\": \"SLES002807\",\n" +
                "\t\"timestamp\": 1618556400000,\n" +
                "\t\"warehouseInfo\": [{\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032212776\",\n" +
                "\t\t\"warehouseName\": \"电商仓库L\"\n" +
                "\t}, {\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032216439\",\n" +
                "\t\t\"warehouseName\": \"本地化仓库\"\n" +
                "\t}, {\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032212842\",\n" +
                "\t\t\"warehouseName\": \"测试仓库电商-NI\"\n" +
                "\t}, {\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032213716\",\n" +
                "\t\t\"warehouseName\": \"测试仓库委外\"\n" +
                "\t}, {\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032216367\",\n" +
                "\t\t\"warehouseName\": \"执行系统无\"\n" +
                "\t}, {\n" +
                "\t\t\"warehouseNo\": \"SWHS0001032213688\",\n" +
                "\t\t\"warehouseName\": \"原油储备仓\"\n" +
                "\t}]\n" +
                "}";
        String token = RSAUtils.encrypt(publicKey, content, "UTF-8");
        System.out.println("token === " + token);

        PrivateKey privateKey = RSAUtils.getPrivateKey(prikey);
        System.out.println(privateKey);

        /*String[] strings = RSAUtils.genRSAKeyPair();
        for (int i=0;i<strings.length;i++){
            System.out.println(strings[i]);
        }*/

        String str = RSAUtils.decrypt(privateKey, token, "UTF-8");
        System.out.println("str ===" + str);
        JSONObject jsonObject = JSON.parseObject(str);
        String tenantId = jsonObject.getString("tenantId");
        System.out.println(tenantId);

        JSONArray warehouseInfo = jsonObject.getJSONArray("warehouseInfo");
        System.out.println(warehouseInfo);
        List<WarehouseDto> warehouseDtoList = warehouseInfo.toJavaList(WarehouseDto.class);
        warehouseDtoList.forEach(System.out::println);

        String msg = "{\"code\": 401,\"msg\":\"缺少token\",\"data\":null}";
        JSONObject jsonObject1 = JSON.parseObject(msg);
        System.out.println(jsonObject1.get("code") + "   " + jsonObject1.get("data"));

        Long time = 1000000L;
        System.out.println("time ==" + time);
        System.out.println(time>new Long(24 * 60 * 60 * 1000));
        System.out.println(DateUtils.formatDate(new Date(),DateUtils.DATETIME_FORMAT));
    }

}
