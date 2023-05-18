package com.example.demo.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpHeaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hut
 * @date 2023/5/12 5:13 下午
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) {
        String kmyeUrl = "https://17dz.com/xqy-portal-web/finance/accountBalanceSheet/query?_rnd_=0.6680684184025578";

        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.ACCEPT,"application/json, text/javascript, */*; q=0.01");
        header.put(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
        header.put("Cookie", "appId=19060100010000; appKey=yqdz; DEVICEID=02A1bd32-43CD-4816-b2c4-916949635594; 19060100010000_pc_uid=PLACEHOLDER_TAG; sec19060100010000_pc_uid=PLACEHOLDER_TAG; FrontEnd.CultureName=zh-CN; FrontEnd.TenantId=8df2d413-6a5b-4c5f-8596-9f56c8b5080b; FrontEnd.TenancyName=official; 19060100010000_account_id=20230302042937350018010000078; sec19060100010000_account_id=20230302042937350018010000078; ucAccountId=20230302042937350018010000078; sec35557083004463202008051008766_pc_uid=PLACEHOLDER_TAG; sec35557083004463202008051008766_account_id=20230302042937350018010000078; ASP.NET_SessionId=fcxk4xcw1gcivo4fhrxnzvgu; xsrf-token=ABA5D262A0C747E4B0503DD535201A85; ucSsoToken=n7JsYGE8bR; secSsoToken=n7JsYGE8bR; uc_session_id=kcpGKdVz3H19060100010000; secuc_session_id=kcpGKdVz3H19060100010000; ucSessionId=kcpGKdVz3H19060100010000; secucSessionId=kcpGKdVz3H19060100010000; setInfo=true");
        header.put("AccountSetToken","1H07BJDETVSN8B16600A0000057DEBAA40");
        header.put(HttpHeaders.CONTENT_TYPE,"application/json");
        Map<String, String> kmyemap = new HashMap<>();
        kmyemap.put("assistantId","");
        kmyemap.put("assistantType","");
        kmyemap.put("beginPeriod","202212");
        kmyemap.put("beginTitleCode","");
        kmyemap.put("endPeriod","202212");
        kmyemap.put("endTitleCode","");
        kmyemap.put("fcurCode","");
        kmyemap.put("firstAccountTitle","false");
        kmyemap.put("pageNo","0");
        kmyemap.put("pageSize","100");
        kmyemap.put("showAssistant","true");
        kmyemap.put("showEndBalance0","true");
        kmyemap.put("showQuantity","false");
        kmyemap.put("showYearAccumulated","true");
        kmyemap.put("titleLevel","6");
        kmyemap.put("titleName","");
        String kmye = HttpUtil.sendPost(kmyeUrl, header,kmyemap);
        log.info("科目余额表获取结果 = {}",kmye);

        List<Map<Integer, String>> kmyeList = new ArrayList<>();
        if(!kmye.startsWith("<script>")){
            String body = JSON.parseObject(kmye).get("body").toString();
            List<JSONObject> optionsList = new ArrayList<>();
            findOptions(JSON.parseObject(body),optionsList);
            System.out.println("kmye = "+optionsList.size());
            optionsList.forEach(item->{
                System.out.println(item.toJSONString());
                if(item.get("titleCode") != null){
                    Map<Integer, String> map = new HashMap<>();
                    map.put(0,item.get("titleCode").toString());
                    map.put(1,item.get("titleName").toString());
                    kmyeList.add(map);
                }
            });
        }
    }

    public static List<JSONObject> findOptions(JSONObject obj, List<JSONObject> optionsList) {
        for (String key : obj.keySet()) {
            if(key.equals("head")){
                continue;
            }
            if (obj.get(key) instanceof JSONObject) {
                optionsList.add(obj.getJSONObject(key));
                findOptions(obj.getJSONObject(key),optionsList);
            } else if (obj.get(key) instanceof JSONArray) {
                JSONArray array = obj.getJSONArray(key);
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i) instanceof JSONObject) {
                        optionsList.add(array.getJSONObject(i));
                        findOptions(array.getJSONObject(i),optionsList);
                    }
                }
            }
        }
        return optionsList;
    }
}
