package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hut
 * @date 2023/5/11 3:02 下午
 */
public class TestFormJson {
    public List<JSONObject> optionsList;

    public List<JSONObject> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<JSONObject> optionsList) {
        this.optionsList = optionsList;
    }

    public static void main(String[] args) {
        TestFormJson testFormJson=new TestFormJson();
        testFormJson.setOptionsList(new ArrayList<>());
        JSONObject jsonObject = JSONObject.parseObject(json);
        testFormJson.findOptions(jsonObject);
        System.out.println(testFormJson.getOptionsList().size());
        System.out.println("123");
    }
    public void findOptions(JSONObject obj) {
        for (String key : obj.keySet()) {
            if (key.equals("options")) {
                optionsList.add(obj.getJSONObject(key));
            } else if (obj.get(key) instanceof JSONObject) {
                findOptions(obj.getJSONObject(key));
            } else if (obj.get(key) instanceof JSONArray) {
                JSONArray array = obj.getJSONArray(key);
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i) instanceof JSONObject) {
                        findOptions(array.getJSONObject(i));
                    }
                }
            }
        }
    }

    public final static  String  json="{\n" +
            "  \"widgetList\": [\n" +
            "    {\n" +
            "      \"key\": 30323,\n" +
            "      \"type\": \"choose-department\",\n" +
            "      \"icon\": \"file-upload-field\",\n" +
            "      \"formItemFlag\": true,\n" +
            "      \"options\": {\n" +
            "        \"name\": \"caecc.iai:udka/dataschemaset/des/anonymous#3deff77f-2d97-4338-8bc4-6f358218e2d5\",\n" +
            "        \"label\": \"名称\",\n" +
            "        \"labelAlign\": \"\",\n" +
            "        \"labelWidth\": null,\n" +
            "        \"tableSequence\": null,\n" +
            "        \"labelHidden\": false,\n" +
            "        \"columnWidth\": \"200px\",\n" +
            "        \"disabled\": false,\n" +
            "        \"hidden\": false,\n" +
            "        \"required\": false,\n" +
            "        \"requiredHint\": \"\",\n" +
            "        \"customRule\": \"\",\n" +
            "        \"customRuleHint\": \"\",\n" +
            "        \"defaultValueIds\": \"\",\n" +
            "        \"defaultValue\": \"撒撒\",\n" +
            "        \"securityLevel\": \"\",\n" +
            "        \"customClass\": [],\n" +
            "        \"labelIconClass\": null,\n" +
            "        \"labelIconPosition\": \"rear\",\n" +
            "        \"labelTooltip\": null,\n" +
            "        \"onCreated\": \"\",\n" +
            "        \"onMounted\": \"\",\n" +
            "        \"onBeforeUpload\": \"\",\n" +
            "        \"onUploadSuccess\": \"\",\n" +
            "        \"onUploadError\": \"\",\n" +
            "        \"onFileRemove\": \"\",\n" +
            "        \"onValidate\": \"\"\n" +
            "      },\n" +
            "      \"id\": \"choosedepartment67005\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": 22367,\n" +
            "      \"type\": \"input\",\n" +
            "      \"icon\": \"text-field\",\n" +
            "      \"formItemFlag\": true,\n" +
            "      \"options\": {\n" +
            "        \"name\": \"caecc.iai:udka/dataschemaset/des/anonymous#6b6aeba7-60b5-49f6-b49d-c4b77a676993\",\n" +
            "        \"label\": \"部门编码\",\n" +
            "        \"labelAlign\": \"\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"defaultValue\": \"\",\n" +
            "        \"placeholder\": \"\",\n" +
            "        \"columnWidth\": \"200px\",\n" +
            "        \"size\": \"\",\n" +
            "        \"labelWidth\": null,\n" +
            "        \"tableSequence\": null,\n" +
            "        \"labelHidden\": false,\n" +
            "        \"readonly\": false,\n" +
            "        \"disabled\": false,\n" +
            "        \"hidden\": false,\n" +
            "        \"clearable\": true,\n" +
            "        \"showPassword\": false,\n" +
            "        \"required\": false,\n" +
            "        \"requiredHint\": \"\",\n" +
            "        \"validation\": \"\",\n" +
            "        \"validationHint\": \"\",\n" +
            "        \"customClass\": \"\",\n" +
            "        \"labelIconClass\": null,\n" +
            "        \"labelIconPosition\": \"rear\",\n" +
            "        \"labelTooltip\": null,\n" +
            "        \"minLength\": null,\n" +
            "        \"maxLength\": null,\n" +
            "        \"showWordLimit\": false,\n" +
            "        \"prefixIcon\": \"\",\n" +
            "        \"suffixIcon\": \"\",\n" +
            "        \"appendButton\": false,\n" +
            "        \"appendButtonDisabled\": false,\n" +
            "        \"buttonIcon\": \"custom-search\",\n" +
            "        \"onCreated\": \"\",\n" +
            "        \"onMounted\": \"\",\n" +
            "        \"onInput\": \"\",\n" +
            "        \"onChange\": \"\",\n" +
            "        \"onFocus\": \"\",\n" +
            "        \"onBlur\": \"\",\n" +
            "        \"onValidate\": \"\",\n" +
            "        \"onAppendButtonClick\": \"\"\n" +
            "      },\n" +
            "      \"id\": \"input116459\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": 22367,\n" +
            "      \"type\": \"input\",\n" +
            "      \"icon\": \"text-field\",\n" +
            "      \"formItemFlag\": true,\n" +
            "      \"options\": {\n" +
            "        \"name\": \"caecc.iai:udka/dataschemaset/des/anonymous#d785b619-192e-438b-8e56-d0e79672a58b\",\n" +
            "        \"label\": \"单位Id\",\n" +
            "        \"labelAlign\": \"\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"defaultValue\": \"\",\n" +
            "        \"placeholder\": \"\",\n" +
            "        \"columnWidth\": \"200px\",\n" +
            "        \"size\": \"\",\n" +
            "        \"labelWidth\": null,\n" +
            "        \"tableSequence\": null,\n" +
            "        \"labelHidden\": false,\n" +
            "        \"readonly\": false,\n" +
            "        \"disabled\": false,\n" +
            "        \"hidden\": false,\n" +
            "        \"clearable\": true,\n" +
            "        \"showPassword\": false,\n" +
            "        \"required\": false,\n" +
            "        \"requiredHint\": \"\",\n" +
            "        \"validation\": \"\",\n" +
            "        \"validationHint\": \"\",\n" +
            "        \"customClass\": \"\",\n" +
            "        \"labelIconClass\": null,\n" +
            "        \"labelIconPosition\": \"rear\",\n" +
            "        \"labelTooltip\": null,\n" +
            "        \"minLength\": null,\n" +
            "        \"maxLength\": null,\n" +
            "        \"showWordLimit\": false,\n" +
            "        \"prefixIcon\": \"\",\n" +
            "        \"suffixIcon\": \"\",\n" +
            "        \"appendButton\": false,\n" +
            "        \"appendButtonDisabled\": false,\n" +
            "        \"buttonIcon\": \"custom-search\",\n" +
            "        \"onCreated\": \"\",\n" +
            "        \"onMounted\": \"\",\n" +
            "        \"onInput\": \"\",\n" +
            "        \"onChange\": \"\",\n" +
            "        \"onFocus\": \"\",\n" +
            "        \"onBlur\": \"\",\n" +
            "        \"onValidate\": \"\",\n" +
            "        \"onAppendButtonClick\": \"\"\n" +
            "      },\n" +
            "      \"id\": \"input106499\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"formConfig\": {\n" +
            "    \"modelName\": \"组织机构\",\n" +
            "    \"entityId\": \"caecc#organization\",\n" +
            "    \"code\": \"CD\",\n" +
            "    \"refName\": \"撒撒\",\n" +
            "    \"rulesName\": \"rules\",\n" +
            "    \"remark\": \"撒\",\n" +
            "    \"labelWidth\": 80,\n" +
            "    \"labelPosition\": \"left\",\n" +
            "    \"size\": \"\",\n" +
            "    \"labelAlign\": \"label-left-align\",\n" +
            "    \"cssCode\": \"\",\n" +
            "    \"customClass\": [],\n" +
            "    \"functions\": \"\",\n" +
            "    \"layoutType\": \"PC\",\n" +
            "    \"jsonVersion\": 3,\n" +
            "    \"onFormCreated\": \"\",\n" +
            "    \"onFormMounted\": \"\",\n" +
            "    \"onFormDataChange\": \"\"\n" +
            "  }\n" +
            "}";
}
