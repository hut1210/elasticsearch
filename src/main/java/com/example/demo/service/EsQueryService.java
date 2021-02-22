package com.example.demo.service;

import com.example.demo.condition.TermsAggregationCondition;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/22 14:08
 */
public interface EsQueryService {
    /**
     * 查询es内容
     *
     * @param index   索引
     * @param type    type
     * @param sourceBuilder    查询条件
     * @return
     */
    public SearchResponse queryByIndexAndSourceBuilder(String index, String type, SearchSourceBuilder sourceBuilder);

    /**
     * 查询es内容
     *
     * @param index   索引
     * @param type    type
     * @param query    查询条件
     * @return
     */
    public SearchResponse queryByIndexAndQuery(String index, String type, String query);

    /**
     * 查询es内容
     *
     * @param indexs   索引
     * @param types    type
     * @param query    查询条件
     * @return
     */
    public SearchResponse queryByIndexsAndQuery(String[] indexs, String[] types, String query);

    /**
     * 查询es内容
     *
     * @param indexs        索引
     * @param types         type
     * @param sourceBuilder 查询条件
     * @return
     */
    public SearchResponse queryByIndexsAndSourceBuilder(String[] indexs, String[] types, SearchSourceBuilder sourceBuilder);


    public List<Map<String, String>> queryByGroup(String index, String type, String query, TermsAggregationCondition... conditions);
}
