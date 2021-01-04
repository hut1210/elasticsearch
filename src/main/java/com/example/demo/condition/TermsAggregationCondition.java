package com.example.demo.condition;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/4 14:31
 */
public class TermsAggregationCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupField;
    private int size;
    private Map<String, Boolean> orders;
    private Map<String, String> sum;
    private Map<String, String> avg;
    private Map<String, String> max;
    private Map<String, String> min;
    private String executionHint;
    private String cardinality;

    public TermsAggregationCondition(String groupField) {
        this.groupField = groupField;
        this.size = 10;
        this.orders = new LinkedHashMap();
        this.sum = new LinkedHashMap();
        this.avg = new LinkedHashMap();
        this.max = new LinkedHashMap();
        this.min = new LinkedHashMap();
    }

    public TermsAggregationCondition sum(String sumName, String field) {
        this.sum.put(sumName, field);
        return this;
    }

    public TermsAggregationCondition avg(String sumName, String field) {
        this.avg.put(sumName, field);
        return this;
    }

    public TermsAggregationCondition max(String sumName, String field) {
        this.max.put(sumName, field);
        return this;
    }

    public TermsAggregationCondition min(String sumName, String field) {
        this.min.put(sumName, field);
        return this;
    }

    public TermsAggregationCondition order(String field, Boolean asc) {
        this.orders.put(field, asc);
        return this;
    }

    public TermsAggregationCondition cardinality(String fieldName) {
        this.cardinality = fieldName;
        return this;
    }

    public TermsAggregationCondition executionHint(String executionHint) {
        this.executionHint = executionHint;
        return this;
    }

    public void size(int size) {
        this.size = size;
    }

    public String getGroupField() {
        return this.groupField;
    }

    public int getSize() {
        return this.size;
    }

    public Map<String, Boolean> getOrders() {
        return this.orders;
    }

    public Map<String, String> getSum() {
        return this.sum;
    }

    public Map<String, String> getAvg() {
        return this.avg;
    }

    public Map<String, String> getMax() {
        return this.max;
    }

    public Map<String, String> getMin() {
        return this.min;
    }

    public String getExecutionHint() {
        return this.executionHint;
    }

    public String getCardinality() {
        return this.cardinality;
    }
}
