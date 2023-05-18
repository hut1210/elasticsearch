package com.example.demo.reptile;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hut
 * @date 2023/5/10 8:49 上午
 */
public class Test2 {

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        String cookie = "appId=19060100010000; appKey=yqdz; DEVICEID=02A1bd32-43CD-4816-b2c4-916949635594; 19060100010000_pc_uid=PLACEHOLDER_TAG; sec19060100010000_pc_uid=PLACEHOLDER_TAG; FrontEnd.CultureName=zh-CN; FrontEnd.TenantId=8df2d413-6a5b-4c5f-8596-9f56c8b5080b; FrontEnd.TenancyName=official; 19060100010000_account_id=20230302042937350018010000078; sec19060100010000_account_id=20230302042937350018010000078; ASP.NET_SessionId=32kapiywyd4solobhzssluxb; xsrf-token=D9A16A4297894737B5F65C2D2325D6EB; adt_user_name=wj; ucAccountId=20230302042937350018010000078; ucSsoToken=fJPZ1rYtVl; secSsoToken=fJPZ1rYtVl; uc_session_id=NkFypdDmwn19060100010000; secuc_session_id=NkFypdDmwn19060100010000; ucSessionId=NkFypdDmwn19060100010000; secucSessionId=NkFypdDmwn19060100010000; setInfo=true";
        String url = "https://17dz.com/xqy-portal-web/finance/customerAccountTitles/listByType?customerId=1906327004300&subjectType=ass&_=1683680579975";
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.ACCEPT,"application/json, text/javascript, */*; q=0.01");
        header.put(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
        header.put("Cookie",cookie);
        String s = HttpUtil.sendGet(url, header);
        System.out.println("科目结果 = "+s);

        String qcUrl="https://17dz.com/xqy-portal-web/finance/openingBalance/all?customerId=1906327004300&period=201901&_=1683699158624";
        Map<String, String> qcheader = new HashMap<>();
        qcheader.put(HttpHeaders.ACCEPT,"application/json, text/javascript, */*; q=0.01");
        qcheader.put(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
        qcheader.put("Cookie",cookie);
        String qcResult = HttpUtil.sendGet(qcUrl, qcheader);
        System.out.println("期初结果 = "+qcResult);

        String pzUrl = "https://17dz.com/xqy-portal-web/finance/accDocs/list";
        Map<String, String> pzheader = new HashMap<>();
        pzheader.put(HttpHeaders.ACCEPT,"application/json, text/javascript, */*; q=0.01");
        pzheader.put(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
        pzheader.put(HttpHeaders.CONTENT_TYPE,"application/json");
        pzheader.put("Cookie",cookie);
        Map<String, String> pzmap = new HashMap<>();
        pzmap.put("beginMoney","");
        pzmap.put("beginNumber","");
        pzmap.put("beginPeriod","202203");
        pzmap.put("endMoney","");
        pzmap.put("endNumber","");
        pzmap.put("endPeriod","202203");
        pzmap.put("pageNo","0");
        pzmap.put("pageSize","100");
        pzmap.put("summary","");
        pzmap.put("titleCode","");
        pzmap.put("type","");

        String pzResult = HttpUtil.sendPost(pzUrl, pzheader,pzmap);
        System.out.println("凭证结果 = "+pzResult);

        /*String loginUrl = "https://17dz.com/ucapi/auth/v1/oauth2.0/doLogin";
        Map<String,String> map = new HashMap<>();
        map.put("appId","19060100010000");
        map.put("username","17390604457");
        map.put("password","4546f9e427ad9f7c595226dd8bc74f1d");
        map.put("autoLogin","true");
        map.put("redirectUri","https://17dz.com/xqy-portal-web/login/v2/yqdz/callback?loginParams=%7B%22scope%22:%22top%22,%22line%22:%22hd%22,%22host%22:%22https://17dz.com%22%7D&_=1683681103789");
        map.put("ucFrontUrlEnv","prod");
        String s1 = HttpUtil.sendPost(loginUrl, map,null);
        System.out.println(s1);*/


        //待请求的地址
        String url1 = "https://www.17dz.com/";
        //请求参数
        List<NameValuePair> loginNV = new ArrayList<>();
        loginNV.add(new BasicNameValuePair("username", "17390604457"));
        loginNV.add(new BasicNameValuePair("password", "w17390604457"));
        //构造请求资源地址
        URI uri = new URIBuilder(url1).addParameters(loginNV).build();
        //创建一个HttpContext对象，用来保存Cookie
        HttpClientContext httpClientContext = HttpClientContext.create();
        //构造自定义Header信息
        List<Header> headerList = Lists.newArrayList();
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8"));
        headerList.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));
        headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
        headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2," +
                "de;q=0.2"));
        //构造自定义的HttpClient对象
        HttpClient httpClient = HttpClients.custom().setDefaultHeaders(headerList).build();
        //构造请求对象
        HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(uri).build();
        //执行请求，传入HttpContext，将会得到请求结果的信息
        httpClient.execute(httpUriRequest, httpClientContext);
        //获取请求结果中Cookie，此时的Cookie已经带有登录信息了
        CookieStore cookieStore = httpClientContext.getCookieStore();
        System.out.println(cookieStore);
        //这个CookieStore保存了我们的登录信息，我们可以先将它保存到某个本地文件，后面直接读取使用
        /*saveCookieStore(cookieStore,"/Users/huteng/Desktop/cookie");
        //使用Cookie来请求，首先读取之前的Cookie
        CookieStore cookieStore1 = readCookieStore("/Users/huteng/Desktop/cookie");
        //构造一个带这个Cookie的HttpClient
        HttpClient newHttpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore1).build();
        //使用新的HttpClient请求。此时HttpClient已经带有了之前的登录信息，再爬取就不用登录了
        HttpResponse execute = newHttpClient.execute(httpUriRequest, httpClientContext);
        HttpEntity entity = execute.getEntity();
        if (entity != null) {
            System.out.println("result = "+EntityUtils.toString(entity));;
        }*/
    }

    //使用序列化的方式保存CookieStore到本地文件，方便后续的读取使用
    private static void saveCookieStore( CookieStore cookieStore, String savePath ) throws IOException {
        FileOutputStream fs = new FileOutputStream(savePath);
        ObjectOutputStream os =  new ObjectOutputStream(fs);
        os.writeObject(cookieStore);
        os.close();
    }
    //读取Cookie的序列化文件，读取后可以直接使用
    private static CookieStore readCookieStore( String savePath ) throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(savePath);//("foo.ser");
        ObjectInputStream ois = new ObjectInputStream(fs);
        CookieStore cookieStore = (CookieStore) ois.readObject();
        ois.close();
        return cookieStore;

    }
}
