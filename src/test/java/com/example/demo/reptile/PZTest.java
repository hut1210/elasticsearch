package com.example.demo.reptile;

import org.apache.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hut
 * @date 2023/5/16 2:20 下午
 */
public class PZTest {

    public static void main(String[] args) {
        String accountUrl = "https://17dz.com/xqy-portal-web/finance/account/session/accountSet?_=1684217886410";
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.ACCEPT,"application/json, text/javascript, */*; q=0.01");
        header.put(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
        header.put("Cookie", "appId=19060100010000; appKey=yqdz; DEVICEID=02A1bd32-43CD-4816-b2c4-916949635594; 19060100010000_pc_uid=PLACEHOLDER_TAG; sec19060100010000_pc_uid=PLACEHOLDER_TAG; FrontEnd.CultureName=zh-CN; FrontEnd.TenantId=8df2d413-6a5b-4c5f-8596-9f56c8b5080b; FrontEnd.TenancyName=official; 19060100010000_account_id=20230302042937350018010000078; sec19060100010000_account_id=20230302042937350018010000078; ucAccountId=20230302042937350018010000078; sec35557083004463202008051008766_pc_uid=PLACEHOLDER_TAG; sec35557083004463202008051008766_account_id=20230302042937350018010000078; ASP.NET_SessionId=ncqfkk1ynv5omgiecdsahmdt; xsrf-token=2B0CB44271E04C8A8031ADA896BDAE19; WDSESSIONID=5203D1b0-548A-421F-9784-3a58cb401655; ucSsoToken=WJa4k3huDf; secSsoToken=WJa4k3huDf; uc_session_id=ntCykxQ2vS19060100010000; secuc_session_id=ntCykxQ2vS19060100010000; ucSessionId=ntCykxQ2vS19060100010000; secucSessionId=ntCykxQ2vS19060100010000; setInfo=true");
        header.put("AccountSetToken","1H0HJAT2O369770A600A00000C88B38C80");
        header.put(HttpHeaders.CONTENT_TYPE,"application/json");
        String accountResult = HttpUtil.sendGet(accountUrl,header);
        System.out.println(accountResult);
    }
}
