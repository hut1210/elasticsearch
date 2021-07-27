package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/22 14:09
 */
@Service("esQueryService")
@Slf4j
public class EsQueryServiceImpl implements EsQueryService {
    @Resource
    private RestHighLevelClient client;

    @Override
    public SearchResponse queryByIndexAndSourceBuilder(String index, String type, SearchSourceBuilder sourceBuilder) {
        return queryByIndexsAndSourceBuilder(new String[]{index},new String[]{type},sourceBuilder);
    }

    @Override
    public SearchResponse queryByIndexAndQuery(String index, String type, String query) {
        return queryByIndexsAndQuery(new String[]{index},new String[]{type},query);
    }

    @Override
    public SearchResponse queryByIndexsAndQuery(String[] indexs, String[] types, String query) {
        if (StringUtils.isBlank(query)) {
            throw new RuntimeException("查询es条件不能为空");
        }
        SearchSourceBuilder searchSourceBuilder;
        try {
            searchSourceBuilder = QueryBuilder.fromSearchSourceBuilder(query);
        } catch (RuntimeException e) {
            log.error("转换查询条件错误");
            throw new RuntimeException("转换查询条件错误", e);
        }

        return queryByIndexsAndSourceBuilder(indexs, types, searchSourceBuilder);
    }

    @Override
    public SearchResponse queryByIndexsAndSourceBuilder(String[] indexs, String[] types, SearchSourceBuilder sourceBuilder) {
        if (indexs == null || indexs.length < 1) {
            throw new RuntimeException("查询es索引不能为空");
        }

        if (types == null || types.length < 1) {
            throw new RuntimeException("查询es type不能为空");
        }

        SearchRequest searchRequest = new SearchRequest(indexs);
        searchRequest.types(types);
        searchRequest.source(sourceBuilder);
        log.error("es query searchRequest:index={},type={},sourceBuilder={}",indexs,types,sourceBuilder.toString());
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            log.error("es query searchResponse:{}", JSON.toJSONString(searchResponse.getHits()));
            return searchResponse;
        } catch (Exception e) {
            log.error("查询es出现异常", e);
            throw new RuntimeException("查询异常", e);
        }
    }

    @Override
    public List<Map<String, String>> queryByGroup(String index, String type, String query, TermsAggregationCondition... conditions) {
        SearchResponse sr = this.queryByIndexAndQuery(index,type,query);
        return ReportUtils.analySearchResponse(sr,conditions);
    }

    /**
     * 查询es内容
     *
     * @param indexs        索引
     * @param types         type
     * @param sourceBuilder 查询条件
     * @param isScroll      是否开启滚动查询，如开启使用完成后需调用
     * @return
     */
    @Override
    public SearchResponse query(String[] indexs, String[] types, SearchSourceBuilder sourceBuilder, boolean isScroll) {

        if (indexs == null || indexs.length < 1) {
            throw new RuntimeException("查询es索引不能为空");
        }

        if (types == null || types.length < 1) {
            throw new RuntimeException("查询es type不能为空");
        }

        SearchRequest searchRequest = new SearchRequest(indexs);
        searchRequest.types(types);
        searchRequest.source(sourceBuilder);
        if (isScroll) {
            searchRequest.scroll(TimeValue.timeValueMinutes(2L));
        }

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse;
        } catch (Exception e) {
            log.error("查询es出现异常", e);
            throw new RuntimeException("查询异常", e);
        }
    }

    /**
     * 滚动查询
     *
     * @param scrollId
     * @return
     */
    @Override
    public SearchResponse scroll(String scrollId) {

        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueMinutes(2L));

        try {
            SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            return searchResponse;
        } catch (IOException e) {
            log.error("滚动查询异常,{}", scrollId, e);
            throw new RuntimeException("滚动查询异常", e);
        }
    }

    /**
     * 清除滚动标识
     *
     * @param scrollId
     * @return
     */
    @Override
    public ClearScrollResponse ClearScrollRequest(String scrollId) {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
        try {
            ClearScrollResponse response = client.clearScroll(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            log.error("清除滚动标识异常,{}", scrollId, e);
            throw new RuntimeException("清除滚动标识异常", e);
        }

    }
}
