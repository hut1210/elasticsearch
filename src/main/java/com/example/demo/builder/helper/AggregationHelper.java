package com.example.demo.builder.helper;

import com.example.demo.condition.TermsAggregationCondition;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AggregationHelper {

    public final static String AGG_GROUP_TERM = "agg_group";

    public final static String AGG_CARDINALITY_TERM = "agg_cardinality";

    public static void setTermsAggregation(SearchSourceBuilder searchSource, List<TermsAggregationCondition> aggs) {

        TermsAggregationBuilder rootTermsAggregation = null; //分组root
        TermsAggregationBuilder preTermsAggregation = null;  //上一个分组

        for(TermsAggregationCondition termsAggregationCondition : aggs){
            TermsAggregationBuilder termsAggregation = AggregationBuilders.terms(AGG_GROUP_TERM).field(termsAggregationCondition.getGroupField());
            List<BucketOrder> orders = new ArrayList<BucketOrder>();
            for(Map.Entry<String,Boolean> entry : termsAggregationCondition.getOrders().entrySet()){
                String key = entry.getKey();
                Boolean value = entry.getValue();
                if("_count".equals(key)) {
                    orders.add(BucketOrder.count(value));
                }else if("_key".equals(key)){
                    orders.add(BucketOrder.key(value));
                }
            }
            if(orders!=null&&orders.size()>0){
                termsAggregation.order(orders);
            }else{
                termsAggregation.order(BucketOrder.count(true));
            }
            int size = termsAggregationCondition.getSize();
            termsAggregation.size(size);
            String executionHint = termsAggregationCondition.getExecutionHint();
            if (executionHint!=null&&executionHint.length()>0) {
                termsAggregation.executionHint(executionHint);
            }

            if(rootTermsAggregation == null){ //第一次
                rootTermsAggregation = termsAggregation;
            }else{
                preTermsAggregation.subAggregation(termsAggregation);
            }
            preTermsAggregation = termsAggregation;
        }

        TermsAggregationCondition termsAggregationCondition = aggs.get(aggs.size() - 1);

        for(Map.Entry<String,String> sum : termsAggregationCondition.getSum().entrySet()){
            preTermsAggregation.subAggregation(AggregationBuilders.sum(sum.getKey()).field(sum.getValue()));
        }

        if(termsAggregationCondition.getCardinality()!=null&&termsAggregationCondition.getCardinality().length()>0) {
            preTermsAggregation.subAggregation(AggregationBuilders.cardinality(AGG_CARDINALITY_TERM).field(termsAggregationCondition.getCardinality()));
        }

        for(Map.Entry<String,String> max :  termsAggregationCondition.getMax().entrySet()){
            preTermsAggregation.subAggregation(AggregationBuilders.max(max.getKey()).field(max.getValue()));
        }

        for(Map.Entry<String,String> min : termsAggregationCondition.getMin().entrySet()){
            preTermsAggregation.subAggregation(AggregationBuilders.min(min.getKey()).field(min.getValue()));
        }

        for(Map.Entry<String,String> avg : termsAggregationCondition.getAvg().entrySet()){
            preTermsAggregation.subAggregation(AggregationBuilders.avg(avg.getKey()).field(avg.getValue()));
        }

        searchSource.aggregation(rootTermsAggregation);
    }

}
