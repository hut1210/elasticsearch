package com.example.demo;

import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.StudentCondition;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.dao.StudentRepository;
import com.example.demo.domain.Student;
import com.example.demo.dto.OwnerAndWarehouseDto;
import com.example.demo.util.ReportUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.matrix.stats.MatrixStats;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Resource
    private RestHighLevelClient client;

    /*@Resource
    private TransportClient transportClient;*/

    @Test
    void contextLoads() {
    }

    @Test
    void testElasticSearch(){
        List scores= new ArrayList<>();
        scores.add(67.2);
        scores.add(27.2);
        scores.add(56.2);
        studentRepository.save(new Student(UUID.randomUUID().toString(), "刘伯", 22, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "刘思想", 35, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "王皮皮", 45, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "王二丫", 23, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "李四", 22, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "张三", 51, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "狗蛋", 23, scores ));
        studentRepository.save(new Student(UUID.randomUUID().toString(), "铁柱", 45, scores ));
    }

    @Test
    public void testSimpleQuery() throws IOException {

        //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词.
        QueryBuilder queryBuilder= QueryBuilders.termsQuery("age",new int[]{21, 22, 23, 35});
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);

        Page<Student> search = studentRepository.search(nativeSearchQueryBuilder.build());
        List<Student> content = search.getContent();
        content.forEach(System.out::println);
        System.out.println(search.getTotalPages());
        System.out.println(search.getTotalElements());

        //1. 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //1.1）指定索引
        searchRequest.indices("student_index");
        //1.2）构造检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termsQuery("age",new int[]{21, 22, 23, 35}));
        System.out.println("检索条件："+sourceBuilder);
        searchRequest.source(sourceBuilder);
        //2. 执行检索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("检索结果："+searchResponse);
        SearchHit[] hits = searchResponse.getHits().getHits();
        Arrays.asList(hits).forEach(item->{
            System.out.println(item.toString());
        });
    }

    /**
     * 聚合
     */
    @Test
    public void testAggs() throws IOException {

        /*TermsAggregationBuilder termsAggregationBuilder = new TermsAggregationBuilder("age");

        TermsAggregationBuilder field = AggregationBuilders.terms("group_age").field("age");

        //1.创建查询条件，也就是QueryBuild
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();//设置查询所有，相当于不设置查询条件
        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //2.0 设置QueryBuilder
        //nativeSearchQueryBuilder.withQuery(matchAllQuery);
        //2.1设置搜索类型，默认值就是QUERY_THEN_FETCH，参考https://blog.csdn.net/wulex/article/details/71081042
        nativeSearchQueryBuilder.withSearchType(SearchType.QUERY_THEN_FETCH);//指定索引的类型，只先从各分片中查询匹配的文档，再重新排序和排名，取前size个文档
        //2.3重点来了！！！指定聚合函数,本例中以某个字段分组聚合为例（可根据你自己的聚合查询需求设置）
        //该聚合函数解释：计算该字段(假设为username)在所有文档中的出现频次，并按照降序排名（常用于某个字段的热度排名）
        nativeSearchQueryBuilder.addAggregation(field);
        //2.4构建查询对象
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        //3.执行查询
        //3.1方法1,通过reporitory执行查询,获得有Page包装了的结果集
        Page<Student> search = studentRepository.search(nativeSearchQuery);
        System.out.println(search);
        System.out.println(search.getTotalElements());
        List<Student> content = search.getContent();
        content.forEach(System.out::println);*/

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("student_index");
        //指定分组字段,terms指定别名,field指定字段名
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_age")
                //聚合字段名
                .field("age").order(InternalOrder.CompoundOrder.key(false));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregation);
        //执行查询
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
        System.out.println(searchRequest);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("response==>"+response);
        Terms byAgeAggregation = response.getAggregations().get("group_age");
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        System.out.println("--------------------------------");
        List<? extends Terms.Bucket> buckets = byAgeAggregation.getBuckets();
        for (Terms.Bucket buck: buckets) {
            System.out.println(buck.getKeyAsString()+"    "+buck.getDocCount());
        }
    }

    @Test
    public void testAggs2() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("student_index");
        //指定分组字段,terms指定别名,field指定字段名
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_age")
                //聚合字段名
                .field("age")
                .subAggregation(AggregationBuilders.avg("avg_age").field("age"))
                .order(InternalOrder.CompoundOrder.count(false));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregation);
        //执行查询
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
        System.out.println(searchRequest);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        Terms byAgeAggregation = response.getAggregations().get("group_age");
        List<? extends Terms.Bucket> buckets = byAgeAggregation.getBuckets();
        Avg terms2 = null;
        for (Terms.Bucket buck: buckets) {
            terms2 = buck.getAggregations().get("avg_age");
            System.out.println(buck.getKeyAsString()+"    "+buck.getDocCount()+" 平均年龄"+terms2.getValue());
        }
    }

    @Test
    public void testAggs3() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("student_index");
        //指定分组字段,terms指定别名,field指定字段名
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_name")
                //聚合字段名
                .field("name")
                .subAggregation(AggregationBuilders.avg("avg_age").field("age"));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort("age", SortOrder.DESC);
        searchSourceBuilder.aggregation(aggregation);
        //执行查询
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
        System.out.println(searchRequest);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        Terms byAgeAggregation = response.getAggregations().get("group_name");
        List<? extends Terms.Bucket> buckets = byAgeAggregation.getBuckets();
        Avg terms2 = null;
        for (Terms.Bucket buck: buckets) {
            terms2 = buck.getAggregations().get("avg_age");
            System.out.println(buck.getKeyAsString()+"    "+buck.getDocCount()+" 平均年龄"+terms2.getValue());
        }
    }

    @Test
    public void testAggs4() throws IOException {
        StudentCondition condition = new StudentCondition();
        condition.setName("王皮皮");

        QueryBuilders.termQuery("name","王皮皮");

        TermsAggregationCondition condition1 = new TermsAggregationCondition("name");
        condition1.order("_key",false);

        condition1.avg("avgAge","age");
        condition1.sum("sumAge","age");
        SearchSourceBuilder ssb = com.example.demo.builder.QueryBuilder.buildGroup(condition,condition1);
        System.out.println("ssb --->"+ssb);
        SearchRequest searchRequest = new SearchRequest("student_index");
        searchRequest.source(ssb);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("searchResponse --->"+searchResponse);
            List<Map<String,String>> list = ReportUtils.analySearchResponse(searchResponse,condition1);
            System.out.println(list);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    @Test
    public void testAggs5() throws IOException {
        StudentCondition condition = new StudentCondition();
        condition.setName("王皮皮");

        SearchSourceBuilder ssb = com.example.demo.builder.QueryBuilder.build(condition);
        //ssb.query(QueryBuilders.termQuery("name","王皮皮"));
        System.out.println("ssb --->"+ssb);
        SearchRequest searchRequest = new SearchRequest("student_index");
        searchRequest.source(ssb);

        /*SearchResponse response = transportClient.prepareSearch("student_index")
                .addAggregation(AggregationBuilders.stats("ageAgg").field("age"))
                .get();
        Stats ageAgg = response.getAggregations().get("ageAgg");
        System.out.println("总数："+ageAgg.getCount());
        System.out.println("最小值："+ageAgg.getMin());
        System.out.println("最大值："+ageAgg.getMax());
        System.out.println("平均值："+ageAgg.getAvg());
        System.out.println("和："+ageAgg.getSum());*/

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("searchResponse --->"+searchResponse);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    @Test
    public void testAggs6() throws IOException {
        StudentCondition condition = new StudentCondition();
        condition.setName("王皮皮");

        QueryBuilders.termQuery("name","王皮皮");

        TermsAggregationCondition condition1 = new TermsAggregationCondition("name");
        condition1.order("_key",false);

        condition1.avg("avgAge","age");

        TermsAggregationCondition condition2 = new TermsAggregationCondition("age");
        condition2.sum("sumAge","age");

        SearchSourceBuilder ssb = com.example.demo.builder.QueryBuilder.buildGroup(new StudentCondition(),condition1,condition2);
        System.out.println("ssb --->"+ssb);
        SearchRequest searchRequest = new SearchRequest("student_index");
        searchRequest.source(ssb);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("searchResponse --->"+searchResponse);

            List<Map<String,String>> list = ReportUtils.analySearchResponse(searchResponse,condition1,condition2);

            Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
            if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
                if(CollectionUtils.isNotEmpty(teams.getBuckets())) {
                    for (Terms.Bucket bucket : teams.getBuckets()) {
                        String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                        if (StringUtils.isNotBlank(key)) {
                            System.out.println("key-----"+key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    @Test
    public void testAggs7() throws IOException {
        StudentCondition condition = new StudentCondition();
        //condition.setName("王皮皮");
        //类似于in
        List<String> list1 = new ArrayList<>();
        list1.add("20");
        list1.add("18");
        list1.add("45");
        condition.setAge(list1);

        QueryBuilders.termQuery("name","王皮皮");

        TermsAggregationCondition condition1 = new TermsAggregationCondition("name");

        TermsAggregationCondition condition2 = new TermsAggregationCondition("age");

        SearchSourceBuilder ssb = com.example.demo.builder.QueryBuilder.buildGroup(condition,condition1,condition2);
        System.out.println("ssb --->"+ssb);
        SearchRequest searchRequest = new SearchRequest("student_index");
        searchRequest.source(ssb);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("searchResponse --->"+searchResponse);
            SearchHit[] hits = searchResponse.getHits().getHits();

            List<OwnerAndWarehouseDto> list = new ArrayList<>();

            Map<String, Aggregation> aggMap = searchResponse.getAggregations().getAsMap();
            if (aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                ParsedStringTerms teams = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
                if(CollectionUtils.isNotEmpty(teams.getBuckets())) {
                    teams.getBuckets().forEach(bucket->{
                        String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                        if (StringUtils.isNotBlank(key)) {
                            System.out.println("key-----"+key);
                        }
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        if (asMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                            ParsedLongTerms aggregation = (ParsedLongTerms) asMap.get(AggregationHelper.AGG_GROUP_TERM);
                            if(CollectionUtils.isNotEmpty(aggregation.getBuckets())){
                                aggregation.getBuckets().forEach(bucket1->{
                                    String key1 = bucket1.getKey() == null ? "" : bucket1.getKey().toString() ;
                                    if (StringUtils.isNotBlank(key1)) {
                                        System.out.println("key1-----"+key1);
                                        //组装编号和名称，返回
                                        OwnerAndWarehouseDto ownerAndWarehouseDto = new OwnerAndWarehouseDto();
                                        ownerAndWarehouseDto.setNo(key);
                                        ownerAndWarehouseDto.setName(key1);
                                        list.add(ownerAndWarehouseDto);
                                    }
                                });
                            }
                        }
                    });
                    /*for (Terms.Bucket bucket : teams.getBuckets()) {
                        String key = bucket.getKey() == null ? "" : bucket.getKey().toString() ;
                        if (StringUtils.isNotBlank(key)) {
                            System.out.println("key-----"+key);
                        }
                        Map<String, Aggregation> asMap = bucket.getAggregations().getAsMap();
                        if (asMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
                            ParsedLongTerms aggregation = (ParsedLongTerms) asMap.get(AggregationHelper.AGG_GROUP_TERM);
                            if(CollectionUtils.isNotEmpty(aggregation.getBuckets())){
                                for (Terms.Bucket bucket1 : aggregation.getBuckets()){
                                    String key1 = bucket1.getKey() == null ? "" : bucket1.getKey().toString() ;
                                    if (StringUtils.isNotBlank(key1)) {
                                        System.out.println("key1-----"+key1);
                                    }
                                }
                            }
                        }
                    }*/
                }
            }
            list.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        }
    }

    @Test
    public void testAggs8() throws IOException {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("student_index");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            AggregationBuilder avg = AggregationBuilders.stats("sumAgg").field("age");    // @1
            sourceBuilder.aggregation(avg);
            sourceBuilder.size(0);

            /*SearchSourceBuilder sourceBuilder2 = com.example.demo.builder.QueryBuilder.build(CommonDeliveryCondition.builder()
            .createTimeStart("2021-02-06")
            .createTimeEnd("2021-02-10")
            .networkCode("1").build());
            sourceBuilder2.aggregation(avg).size(0);
            System.out.println("sourceBuilder2 ----"+sourceBuilder2);*/

            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.rangeQuery("age").gte(20).lte(50));
                    if(1>0){
                        queryBuilder.must(QueryBuilders.termQuery("age",20));
                    }
            sourceBuilder.query(
                    queryBuilder
            );

            System.out.println("sourceBuilder---->"+sourceBuilder);
            searchRequest.source(sourceBuilder);
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(result);
            Aggregation aggregation = result.getAggregations().get("sumAgg");
            ParsedStats parsedStats = (ParsedStats)aggregation;
            System.out.println(parsedStats.getSum());

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSuggestCompletionProc() {
        String suggestField="name";//指定在哪个字段搜索
        String suggestValue="王二";//输入的信息
        Integer suggestMaxCount=10;//获得最大suggest条数

        CompletionSuggestionBuilder suggestionBuilderDistrict = new CompletionSuggestionBuilder(suggestField).prefix(suggestValue).size(suggestMaxCount);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("student_suggest", suggestionBuilderDistrict);//添加suggest

        //设置查询builder的index,type,以及建议
        //SearchRequestBuilder requestBuilder = this.elasticsearchRestTemplate..prepareSearch("student_index").setTypes("student").suggest(suggestBuilder);
        SearchResponse response  =this.elasticsearchRestTemplate.suggest(suggestBuilder,Student.class);
        //SearchResponse response  =this.elasticsearchRestTemplate.suggest(suggestBuilder,IndexCoordinates.of("student_index"));

        System.out.println("response---->"+response.toString());

        Suggest suggest = response.getSuggest();//suggest实体

        Set<String> suggestSet = new HashSet<>();//set
        int maxSuggest = 0;
        if (suggest != null) {
            Suggest.Suggestion result = suggest.getSuggestion("student_suggest");//获取suggest,name任意string
            for (Object term : result.getEntries()) {

                if (term instanceof CompletionSuggestion.Entry) {
                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()) {
                        //若item的option不为空,循环遍历
                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                            String tip = option.getText().toString();
                            if (!suggestSet.contains(tip)) {
                                suggestSet.add(tip);
                                ++maxSuggest;
                            }
                        }
                    }
                }
                if (maxSuggest >= suggestMaxCount) {
                    break;
                }
            }
        }

        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));

        suggests.forEach((s)->{
            System.out.println("suggests.forEach--->"+s);
        });

//		return	 suggests;

    }

}
