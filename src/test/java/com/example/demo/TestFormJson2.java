package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hut
 * @date 2023/5/11 3:02 下午
 */
public class TestFormJson2 {
    public List<JSONObject> optionsList;

    public List<JSONObject> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<JSONObject> optionsList) {
        this.optionsList = optionsList;
    }

    public static void main(String[] args) {
        TestFormJson2 testFormJson=new TestFormJson2();
        testFormJson.setOptionsList(new ArrayList<>());
        JSONObject jsonObject = JSONObject.parseObject(json);
        testFormJson.findOptions(jsonObject);
        testFormJson.getOptionsList().forEach(System.out::println);
        System.out.println(testFormJson.getOptionsList().size());
        System.out.println("123");
    }
    public void findOptions(JSONObject obj) {
        for (String key : obj.keySet()) {
            if (obj.get(key) instanceof JSONObject) {
                optionsList.add(obj.getJSONObject(key));
                findOptions(obj.getJSONObject(key));
            } else if (obj.get(key) instanceof JSONArray) {
                JSONArray array = obj.getJSONArray(key);
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i) instanceof JSONObject) {
                        optionsList.add(array.getJSONObject(i));
                        findOptions(array.getJSONObject(i));
                    }
                }
            }
        }
    }

    public final static  String  json="{\n" +
            "\t\"body\": [{\n" +
            "\t\t\"openingBalance\": null,\n" +
            "\t\t\"id\": 2083529176,\n" +
            "\t\t\"type\": \"lia\",\n" +
            "\t\t\"code\": \"2221\",\n" +
            "\t\t\"name\": \"应交税费\",\n" +
            "\t\t\"fullName\": \"应交税费\",\n" +
            "\t\t\"direction\": -1,\n" +
            "\t\t\"pCode\": null,\n" +
            "\t\t\"pId\": null,\n" +
            "\t\t\"level\": 1,\n" +
            "\t\t\"last\": false,\n" +
            "\t\t\"assistantType\": null,\n" +
            "\t\t\"status\": \"1\",\n" +
            "\t\t\"source\": \"s\",\n" +
            "\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\"unit\": null,\n" +
            "\t\t\"fcurCode\": null,\n" +
            "\t\t\"useAssistant\": false,\n" +
            "\t\t\"useQuantity\": false,\n" +
            "\t\t\"useFcur\": false,\n" +
            "\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\"remark\": null,\n" +
            "\t\t\"children\": [{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529177,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222101\",\n" +
            "\t\t\t\t\"name\": \"应交增值税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交增值税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": false,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": [{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529178,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210101\",\n" +
            "\t\t\t\t\t\t\"name\": \"进项税额\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-进项税额\",\n" +
            "\t\t\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529179,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210102\",\n" +
            "\t\t\t\t\t\t\"name\": \"已交税金\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-已交税金\",\n" +
            "\t\t\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529180,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210103\",\n" +
            "\t\t\t\t\t\t\"name\": \"转出未交增值税\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-转出未交增值税\",\n" +
            "\t\t\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529181,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210104\",\n" +
            "\t\t\t\t\t\t\"name\": \"减免税款\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-减免税款\",\n" +
            "\t\t\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529182,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210105\",\n" +
            "\t\t\t\t\t\t\"name\": \"销项税额\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-销项税额\",\n" +
            "\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529185,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210108\",\n" +
            "\t\t\t\t\t\t\"name\": \"转出多交增值税\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-转出多交增值税\",\n" +
            "\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529186,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210109\",\n" +
            "\t\t\t\t\t\t\"name\": \"进项税额转出\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-进项税额转出\",\n" +
            "\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529187,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210110\",\n" +
            "\t\t\t\t\t\t\"name\": \"销项税额抵减\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-销项税额抵减\",\n" +
            "\t\t\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": {\n" +
            "\t\t\t\t\t\t\t\"id\": 995774958,\n" +
            "\t\t\t\t\t\t\t\"accountTitleId\": 2083529188,\n" +
            "\t\t\t\t\t\t\t\"accountTitleCode\": \"22210111\",\n" +
            "\t\t\t\t\t\t\t\"accountTitleName\": null,\n" +
            "\t\t\t\t\t\t\t\"titleType\": null,\n" +
            "\t\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\t\"pTitleId\": null,\n" +
            "\t\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\t\"assistantTypeName\": null,\n" +
            "\t\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\t\"type\": \"b\",\n" +
            "\t\t\t\t\t\t\t\"openingBalance\": 332.040000,\n" +
            "\t\t\t\t\t\t\t\"debitTotal\": 0,\n" +
            "\t\t\t\t\t\t\t\"creditTotal\": 0,\n" +
            "\t\t\t\t\t\t\t\"yearBeginDebit\": 0.000000,\n" +
            "\t\t\t\t\t\t\t\"yearBeginCredit\": 332.040000\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"id\": 2083529188,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210111\",\n" +
            "\t\t\t\t\t\t\"name\": \"小规模增值税\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-小规模增值税\",\n" +
            "\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\t\t\"id\": 2083529189,\n" +
            "\t\t\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\t\t\"code\": \"22210112\",\n" +
            "\t\t\t\t\t\t\"name\": \"增值税检查调整\",\n" +
            "\t\t\t\t\t\t\"fullName\": \"应交税费-应交增值税-增值税检查调整\",\n" +
            "\t\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\t\"pCode\": \"222101\",\n" +
            "\t\t\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\t\t\"level\": 3,\n" +
            "\t\t\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529191,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222103\",\n" +
            "\t\t\t\t\"name\": \"应交所得税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交所得税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": {\n" +
            "\t\t\t\t\t\"id\": 995775014,\n" +
            "\t\t\t\t\t\"accountTitleId\": 2083529192,\n" +
            "\t\t\t\t\t\"accountTitleCode\": \"222104\",\n" +
            "\t\t\t\t\t\"accountTitleName\": null,\n" +
            "\t\t\t\t\t\"titleType\": null,\n" +
            "\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\"pTitleId\": null,\n" +
            "\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\"assistantTypeName\": null,\n" +
            "\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\"type\": \"b\",\n" +
            "\t\t\t\t\t\"openingBalance\": 0.000000,\n" +
            "\t\t\t\t\t\"debitTotal\": 0,\n" +
            "\t\t\t\t\t\"creditTotal\": 0,\n" +
            "\t\t\t\t\t\"yearBeginDebit\": 0.000000,\n" +
            "\t\t\t\t\t\"yearBeginCredit\": 0.000000\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"id\": 2083529192,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222104\",\n" +
            "\t\t\t\t\"name\": \"应交印花税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交印花税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529193,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222105\",\n" +
            "\t\t\t\t\"name\": \"应交营业税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交营业税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": {\n" +
            "\t\t\t\t\t\"id\": 995774947,\n" +
            "\t\t\t\t\t\"accountTitleId\": 2083529195,\n" +
            "\t\t\t\t\t\"accountTitleCode\": \"222107\",\n" +
            "\t\t\t\t\t\"accountTitleName\": null,\n" +
            "\t\t\t\t\t\"titleType\": null,\n" +
            "\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\"pTitleId\": null,\n" +
            "\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\"assistantTypeName\": null,\n" +
            "\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\"type\": \"b\",\n" +
            "\t\t\t\t\t\"openingBalance\": 23.250000,\n" +
            "\t\t\t\t\t\"debitTotal\": 0,\n" +
            "\t\t\t\t\t\"creditTotal\": 0,\n" +
            "\t\t\t\t\t\"yearBeginDebit\": 0.000000,\n" +
            "\t\t\t\t\t\"yearBeginCredit\": 23.250000\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"id\": 2083529195,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222107\",\n" +
            "\t\t\t\t\"name\": \"应交城市维护建设税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交城市维护建设税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529196,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222108\",\n" +
            "\t\t\t\t\"name\": \"应交个人所得税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交个人所得税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": {\n" +
            "\t\t\t\t\t\"id\": 995774992,\n" +
            "\t\t\t\t\t\"accountTitleId\": 2083529197,\n" +
            "\t\t\t\t\t\"accountTitleCode\": \"222109\",\n" +
            "\t\t\t\t\t\"accountTitleName\": null,\n" +
            "\t\t\t\t\t\"titleType\": null,\n" +
            "\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\"pTitleId\": null,\n" +
            "\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\"assistantTypeName\": null,\n" +
            "\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\"type\": \"b\",\n" +
            "\t\t\t\t\t\"openingBalance\": 0.000000,\n" +
            "\t\t\t\t\t\"debitTotal\": 0,\n" +
            "\t\t\t\t\t\"creditTotal\": 0,\n" +
            "\t\t\t\t\t\"yearBeginDebit\": 0.000000,\n" +
            "\t\t\t\t\t\"yearBeginCredit\": 0.000000\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"id\": 2083529197,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222109\",\n" +
            "\t\t\t\t\"name\": \"应交教育费附加\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交教育费附加\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": {\n" +
            "\t\t\t\t\t\"id\": 995774991,\n" +
            "\t\t\t\t\t\"accountTitleId\": 2083529198,\n" +
            "\t\t\t\t\t\"accountTitleCode\": \"222110\",\n" +
            "\t\t\t\t\t\"accountTitleName\": null,\n" +
            "\t\t\t\t\t\"titleType\": null,\n" +
            "\t\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\t\"pTitleId\": null,\n" +
            "\t\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\t\"assistantTypeName\": null,\n" +
            "\t\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\t\"type\": \"b\",\n" +
            "\t\t\t\t\t\"openingBalance\": 0.000000,\n" +
            "\t\t\t\t\t\"debitTotal\": 0,\n" +
            "\t\t\t\t\t\"creditTotal\": 0,\n" +
            "\t\t\t\t\t\"yearBeginDebit\": 0.000000,\n" +
            "\t\t\t\t\t\"yearBeginCredit\": 0.000000\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"id\": 2083529198,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222110\",\n" +
            "\t\t\t\t\"name\": \"应交地方教育费附加\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交地方教育费附加\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529209,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222121\",\n" +
            "\t\t\t\t\"name\": \"应交残疾人就业保障金\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-应交残疾人就业保障金\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529210,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222122\",\n" +
            "\t\t\t\t\"name\": \"预交增值税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-预交增值税\",\n" +
            "\t\t\t\t\"direction\": 1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"openingBalance\": null,\n" +
            "\t\t\t\t\"id\": 2083529211,\n" +
            "\t\t\t\t\"type\": \"lia\",\n" +
            "\t\t\t\t\"code\": \"222123\",\n" +
            "\t\t\t\t\"name\": \"简易计税\",\n" +
            "\t\t\t\t\"fullName\": \"应交税费-简易计税\",\n" +
            "\t\t\t\t\"direction\": -1,\n" +
            "\t\t\t\t\"pCode\": \"2221\",\n" +
            "\t\t\t\t\"pId\": null,\n" +
            "\t\t\t\t\"level\": 2,\n" +
            "\t\t\t\t\"last\": true,\n" +
            "\t\t\t\t\"assistantType\": null,\n" +
            "\t\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\t\"source\": \"s\",\n" +
            "\t\t\t\t\"accountSetId\": 15865512,\n" +
            "\t\t\t\t\"unit\": null,\n" +
            "\t\t\t\t\"fcurCode\": null,\n" +
            "\t\t\t\t\"useAssistant\": false,\n" +
            "\t\t\t\t\"useQuantity\": false,\n" +
            "\t\t\t\t\"useFcur\": false,\n" +
            "\t\t\t\t\"customerId\": 1906327004300,\n" +
            "\t\t\t\t\"remark\": null,\n" +
            "\t\t\t\t\"children\": null,\n" +
            "\t\t\t\t\"assistants\": null,\n" +
            "\t\t\t\t\"pinYinInitial\": null,\n" +
            "\t\t\t\t\"logicalUseQuantity\": false\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"assistants\": null,\n" +
            "\t\t\"pinYinInitial\": null,\n" +
            "\t\t\"logicalUseQuantity\": false\n" +
            "\t}]\n" +
            "}";
}
