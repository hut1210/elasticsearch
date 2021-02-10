package com.example.demo.util;


import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.constant.ReportConstant;
import com.example.demo.entity.Trend;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.*;

import java.math.BigDecimal;
import java.util.*;

public class ReportUtils {

    public static String getDayCount(Long day,Long dayCount){
        if(isNull(day) || isNull(dayCount)){
            return "0%";
        }else{

            return toPercent(day-dayCount,dayCount);
        }
    }

    public static boolean isNull(Long n){
        return n == null || n == 0;
    }

    public static boolean isNull(BigDecimal n){
        return n == null || n.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 计算比率，保留两位小数
     * @return
     */
    public static String toPercent(BigDecimal b1 ,BigDecimal b2){
        if(isNull(b2) || b1 == null){
            return "--";
        }
        String rate = divide(b1,b2).toString();
        String rateStr = rate+"%";
        return rateStr;
    }

    /**
     * 环比
     * @param day
     * @param dayCount
     * @return
     */
    public static String getDayCount(BigDecimal day,BigDecimal dayCount){
        if(isNull(day) || isNull(dayCount)){
            return "---";
        }else{
            return toPercent(day.subtract(dayCount),dayCount);
        }
    }

    /**
     * 计算比率，保留两位小数
     * @return
     */
    public static String toPercent(Long d1 ,Long d2){
        if(isNull(d1) || isNull(d2)){
            return "0%";
        }
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        String rate = divide(b1,b2).toString();
        String rateStr = rate+"%";
        return rateStr;
    }

    public static BigDecimal getBigDecimal(Object obj){
        if(obj != null && StringUtils.isNotBlank(obj.toString())){
            return new BigDecimal(obj.toString()).setScale(0,BigDecimal.ROUND_DOWN);
        }else{
            return BigDecimal.ZERO.setScale(0);
        }
    }

    public static BigDecimal getBigDecimal(Object obj,int scale){
        if(obj != null && StringUtils.isNotBlank(obj.toString())){
            return new BigDecimal(obj.toString()).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }else{
            return BigDecimal.ZERO.setScale(scale);
        }
    }

    public static Long getLong(Object obj){
        if(obj != null  && StringUtils.isNotBlank(obj.toString())){
            return new Long(obj.toString());
        }else{
            return new Long("0");
        }
    }

    public static BigDecimal divide(BigDecimal b1,BigDecimal b2){
        BigDecimal result = (b1.divide(b2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        return result;
    }

    public static BigDecimal baseDivide(BigDecimal b1,BigDecimal b2){
        BigDecimal result = (b1.divide(b2,4,BigDecimal.ROUND_HALF_DOWN)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        return result;
    }

    public static Map<String, Trend> trend2Map(List<Trend> trendList){
        Map<String, Trend> result = new LinkedHashMap<String, Trend>();
        if(CollectionUtils.isNotEmpty(trendList)){
            for(Trend trend:trendList){
                result.put(trend.getName(),trend);
            }
        }
        return result;
    }


    public static List<Map<String,String>> analySearchResponse(SearchResponse sr, TermsAggregationCondition... conditions){
        List<Map<String,String>> list = new ArrayList<>();
        Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();
        if(aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms terms = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            for(Terms.Bucket bucket : terms.getBuckets()) {
                HashMap<String,String> map = new HashMap<>();
                analySearchResponse1(bucket,list,null,0,conditions);
            }
        }
        return list;
    }


    private static void analySearchResponse1(Terms.Bucket bucket, List<Map<String,String>> list, HashMap<String,String> preMap, int index, TermsAggregationCondition... conditions){
        TermsAggregationCondition condition = conditions[index];
        if(preMap == null){
            preMap = new HashMap<>();
        }
        HashMap<String,String> map = new HashMap<>();
        map.putAll(preMap);
        map.put(condition.getGroupField(),bucket.getKeyAsString());
        map.put(condition.getGroupField()+"_doc_count",ReportUtils.getBigDecimal(bucket.getDocCount()).toString());

        for(Map.Entry<String,String> entry : condition.getSum().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedSum sum = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(sum.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getMin().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedMin min = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(min.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getMax().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedMax max = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(max.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getAvg().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedAvg avg = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(avg.getValue(),2).toString());
            }
        }

        if(StringUtils.isNotBlank(condition.getCardinality())){
            if(bucket != null && bucket.getAggregations().get(AggregationHelper.AGG_CARDINALITY_TERM) != null){
                ParsedCardinality cardinality = bucket.getAggregations().get(AggregationHelper.AGG_CARDINALITY_TERM);
                map.put(condition.getCardinality()+"_cardinality",ReportUtils.getBigDecimal(cardinality.getValue(), 2).toString());
            }
        }

        ParsedTerms terms = (ParsedTerms)bucket.getAggregations().getAsMap().get(AggregationHelper.AGG_GROUP_TERM);
        if(terms != null && CollectionUtils.isNotEmpty(terms.getBuckets())){
            index+=1;
            for(Terms.Bucket bucket1 : terms.getBuckets()){
                analySearchResponse1(bucket1,list,map,index,conditions);
            }
        }else{
            list.add(map);
            return ;
        }
    }

    public static Map<String,Map<String,String>> analySearchResponse2Map(SearchResponse sr, TermsAggregationCondition... conditions){
        Map<String,Map<String,String>> result = new LinkedHashMap<>();
        Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();
        if(aggMap.containsKey(AggregationHelper.AGG_GROUP_TERM)) {
            ParsedStringTerms terms = (ParsedStringTerms) aggMap.get(AggregationHelper.AGG_GROUP_TERM);
            for(Terms.Bucket bucket : terms.getBuckets()) {
                HashMap<String,String> map = new HashMap<>();
                analySearchResponse2Map1(bucket,result,null,0,conditions);
            }
        }
        return result;
    }

    private static void analySearchResponse2Map1(Terms.Bucket bucket, Map<String,Map<String,String>> result, HashMap<String,String> preMap, int index, TermsAggregationCondition... conditions){
        TermsAggregationCondition condition = conditions[index];
        if(preMap == null){
            preMap = new HashMap<>();
        }
        HashMap<String,String> map = new HashMap<>();
        map.putAll(preMap);
        map.put(condition.getGroupField(),bucket.getKeyAsString());
        map.put(condition.getGroupField()+"_doc_count",ReportUtils.getBigDecimal(bucket.getDocCount()).toString());

        for(Map.Entry<String,String> entry : condition.getSum().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedSum sum = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(sum.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getMin().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedMin min = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(min.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getMax().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedMax max = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(max.getValue(),2).toString());
            }
        }

        for(Map.Entry<String,String> entry : condition.getAvg().entrySet()){
            String name = entry.getKey();
            if(bucket != null && bucket.getAggregations().get(name) != null){
                ParsedAvg avg = bucket.getAggregations().get(name);
                map.put(condition.getGroupField()+"_"+name,ReportUtils.getBigDecimal(avg.getValue(),2).toString());
            }
        }

        if(StringUtils.isNotBlank(condition.getCardinality())){
            if(bucket != null && bucket.getAggregations().get(AggregationHelper.AGG_CARDINALITY_TERM) != null){
                ParsedCardinality cardinality = bucket.getAggregations().get(AggregationHelper.AGG_CARDINALITY_TERM);
                map.put(condition.getCardinality()+"_cardinality",ReportUtils.getBigDecimal(cardinality.getValue(), 2).toString());
            }
        }

        ParsedTerms terms = (ParsedTerms)bucket.getAggregations().getAsMap().get(AggregationHelper.AGG_GROUP_TERM);
//        if(bucket.getAggregations().getAsMap().get(AggregationHelper.AGG_GROUP_TERM) instanceof ParsedStringTerms){
//
//        }else if(bucket.getAggregations().getAsMap().get(AggregationHelper.AGG_GROUP_TERM) instanceof ParsedLongTerms){
//
//        }else{
//            throw new RuntimeException("Terms type is not matching !");
//        }
        if(terms != null && CollectionUtils.isNotEmpty(terms.getBuckets())){
            index+=1;
            for(Terms.Bucket bucket1 : terms.getBuckets()){
                analySearchResponse2Map1(bucket1,result,map,index,conditions);
            }
        }else{
            List<String> keyList = new ArrayList<>();
            for(TermsAggregationCondition condition1 : conditions){
                keyList.add(map.get(condition1.getGroupField()));
            }
            String key = Joiner.on(ReportConstant.SPLIT).join(keyList).trim();
            if(StringUtils.isNotBlank(key)){
               result.put(key,map);
            }
            return ;
        }
    }

    public static int getFrom(int pageSize,int pageIndex){
       return pageSize*(pageIndex-1);
    }

    public static int getTo(int pageSize,int pageIndex){
        return pageSize*(pageIndex);
    }

    public static String getString(Object obj){
        if(obj == null){
            return "";
        }else{
            return obj.toString();
        }
    }

    /**
     * 把数组分成size份。
     * @param collection
     * @param size
     * @param <T>
     * @return
     */
    public <T>  List<List<T>> subList(Collection<T> collection , int size){
        List<List<T>> result = new ArrayList<>();
        List<T> list = new ArrayList<>(collection);
        if(CollectionUtils.isNotEmpty(list) && size > 0){
            int length = list.size();
            //先计算要分解成多少页
            int page = length % size == 0 ? length/size : length/size + 1;
            for(int i = 1 ; i <= page ; i++){
                int start = (i-1) * size;
                int end = i * size > length ? length : i * size;
                result.add(list.subList(start,end));
            }
        }
        return result;
    }
}
