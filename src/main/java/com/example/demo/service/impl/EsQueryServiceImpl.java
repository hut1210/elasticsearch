package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
