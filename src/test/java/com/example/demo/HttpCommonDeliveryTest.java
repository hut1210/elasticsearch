package com.example.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.CommonDeliveryPageCondition;
import com.example.demo.condition.PostSalePageCondition;
import com.example.demo.condition.WaybillReportPageCondition;
import com.example.demo.dto.CommonDeliveryDto;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.PostSaleOverviewDto;
import com.example.demo.enums.PostUrlEnum;
import com.example.demo.util.DateUtils;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: dispatch
 * @description:
 **/
public class HttpCommonDeliveryTest {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private final static Logger logger = LoggerFactory.getLogger(HttpCommonDeliveryTest.class);

    private static String queryCommonDeliveryPage = "https://uat-proxy.jd.com/XZService/queryCommonDeliveryPage";

    private static String queryCommonDeliveryPageNew = "https://uat-proxy.jd.com/XZService/queryCommonDeliveryPageNew";

    private static String getCommonDeliveryOverviewDtoList = "https://uat-proxy.jd.com/XZService/getCommonDeliveryOverviewDtoList";

    private static String queryCommonDeliveryIndexOverview = "https://uat-proxy.jd.com/XZService/queryCommonDeliveryIndexOverview";

    private static String queryWaybillReportPage = "https://uat-proxy.jd.com/XZService/queryWaybillReportPage";

    private static String queryWaybillReportIndexOverview = "https://uat-proxy.jd.com/XZService/queryWaybillReportIndexOverview";

    private static String queryPostSaleEventPage = "https://uat-proxy.jd.com/XZService/queryPostSaleEventPage";
    //postUrl
//    private static String postUrl = new StringBuilder().append(postAction).toString();
//
//    private static String postUrl1 = new StringBuilder().append(postAction1).toString();
    //user (测试环境，线上需提前申请)
    private static String macUser = "lop://403/XZBJ";
    //secret (测试环境，线上需提前申请)
    private static String macKey = "c943ad69ae300001474a1d801d15139e";

    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes("UTF-8");
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes("UTF-8");
        //完成 Mac 操作
        byte[] digest = mac.doFinal(text);
        return Base64.getEncoder().encodeToString(digest);
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String qrCode = "{\"createTimeStart\":\"2021-02-17\",\"createTimeEnd\":\"2021-02-24\",\"pageIndex\":1,\"pageSize\":10}";
        String str="{\"createTimeEnd\":\"2021-03-01\",\"createTimeStart\":\"2021-03-01\",\"networkCode\":\"1455600\",\"networkCompany\":\"1\",\"pageIndex\":1,\"pageSize\":10}";
        String qrCode1 = "{\"createTimeStart\":\"2021-02-18\",\"createTimeEnd\":\"2021-02-25\"}";
        String qrCode2 = "{\"createTimeStart\":\"2021-02-18\",\"createTimeEnd\":\"2021-02-25\",\"pageIndex\":1,\"pageSize\":10}";
        String qrCode4 = "{\"createTime\":\"2021-02-26\",\"pageIndex\":1,\"pageSize\":10}";

        /*WaybillReportPageCondition waybillReportPageCondition = new WaybillReportPageCondition();
        waybillReportPageCondition.setPageIndex(1);
        waybillReportPageCondition.setPageSize(10);*/
        /*waybillReportPageCondition.setCreateTimeStart("2021-03-02");
        waybillReportPageCondition.setCreateTimeEnd("2021-03-02");
        waybillReportPageCondition.setNetworkArea("拉萨市");*/


        /*CommonDeliveryPageCondition commonDeliveryPageCondition = new CommonDeliveryPageCondition();
        commonDeliveryPageCondition.setPageIndex(1);
        commonDeliveryPageCondition.setPageSize(10);
        commonDeliveryPageCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATE_FORMAT));
        commonDeliveryPageCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATE_FORMAT));
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        BeanUtils.copyProperties(commonDeliveryPageCondition,commonDeliveryCondition);
        String result = doPost(JSONObject.toJSONString(commonDeliveryPageCondition), queryCommonDeliveryPageNew, macUser, macKey);
        System.out.println("result = "+JSON.toJSON(result));*/
        /*List<CommonDeliveryDto> commonDeliveryDtoList = new ArrayList<>();
        List<CommonDeliveryOverviewDto> commonDeliveryOverviewDtoList = new ArrayList<>();
        if (!StringUtils.isEmpty(result)) {
            try{
                commonDeliveryDtoList = JSONArray.parseArray(result).toJavaList(CommonDeliveryDto.class);
                if(CollectionUtils.isNotEmpty(commonDeliveryDtoList)){
                    Map param = new HashMap<>();
                    param.put("commonDeliveryDtoList",commonDeliveryDtoList);
                    param.put("commonDeliveryCondition",commonDeliveryCondition);
                    String listResult = doPost(JSONObject.toJSONString(param), getCommonDeliveryOverviewDtoList, macUser, macKey);
                    System.out.println("listResult = "+listResult);
                    if(!StringUtils.isEmpty(listResult)){
                        commonDeliveryOverviewDtoList = JSONArray.parseArray(listResult).toJavaList(CommonDeliveryOverviewDto.class);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("commonDeliveryOverviewDtoList = "+commonDeliveryOverviewDtoList);*/
        /*System.out.println(JSON.toJSONString(s));
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println("jsonObject------>"+jsonObject);
        Object data = jsonObject.get("data");
        System.out.println("data------>"+data);
        JSONObject l = JSON.parseObject(JSON.toJSONString(data));
        JSONArray jsonArray= JSONArray.parseArray(JSON.toJSONString(l.get("rows")));
        System.out.println("jsonArray------>"+jsonArray);

        JSONArray jsonArray2= l.getJSONArray("rows");
        System.out.println("jsonArray2------>"+jsonArray2);
        List<PostSaleOverviewDto> postSaleOverviewDtoList = jsonArray2.toJavaList(PostSaleOverviewDto.class);
        postSaleOverviewDtoList.forEach(System.out::println);*/

        PostSalePageCondition postSalePageCondition = new PostSalePageCondition();
        postSalePageCondition.setPageIndex(1);
        postSalePageCondition.setPageSize(5);
        String result = doPost(JSONObject.toJSONString(postSalePageCondition), queryPostSaleEventPage, macUser, macKey);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject);
            JSONObject data = JSON.parseObject(JSON.toJSONString(jsonObject.get("data")));
            System.out.println(data.get("rowTotal"));
            JSONArray jsonArray = data.getJSONArray("rows");
            if (jsonArray.size() > 0) {
                List<PostSaleOverviewDto> postSaleOverviewDtoList = jsonArray.toJavaList(PostSaleOverviewDto.class);
                postSaleOverviewDtoList.forEach(System.out::println);
            }
        }
    }

    public static String doPost(String param, String url, String macUser, String macKey) {
        //请求参数对象

        logger.info("entry param:" + param +
                ",url:" + url +
                ",macUser:" + macUser +
                ",macKey:" + macKey);
        //httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            param = new String(param.getBytes("iso-8859-1"),"utf-8");
            //request param
            StringEntity params = new StringEntity(param);
            String xdate = getServerTime();
            String contentMd5 = MD5(param);
            String txt = "X-Date: " + xdate + "\n" + "content-md5: " + contentMd5;
            String sign = HmacSHA1Encrypt(txt, macKey);

            //request
            HttpPost request = new HttpPost(url);
//            HttpGet request = new HttpGet(url);


            String auth = "hmac username=\"" + macUser + "\", algorithm=\"hmac-sha1\", headers=\"X-Date content-md5\",signature=\"" + sign + "\"";
            request.addHeader("content-type", "application/json;charset=UTF-8\"");
            request.addHeader("X-Date", xdate);
            request.addHeader("content-md5", contentMd5);
            request.addHeader("Authorization", auth);
            request.addHeader("LOP-DN", "soms.report.xz.api.com");
            if (param != null) {
                request.setEntity(params);
            }

            CloseableHttpResponse response = httpClient.execute(request);
            byte[] bytes;
            try {
                HttpEntity entity = response.getEntity();
                bytes = EntityUtils.toByteArray(entity);
                int rCode = response.getStatusLine().getStatusCode();
                if (rCode != 200) {
                    new String(bytes, "UTF-8");
                    logger.error("请求失败,返回值:" + rCode + " " + response.getStatusLine().toString());
                    return "";
                }
            } finally {
                response.close();
            }

            String result = new String(bytes, "UTF-8");
            //logger.info("返回内容:" + result);

            if (null != result && result.length() > 0) {
                return result;
            }

            return "";
        } catch (Exception ex) {
            logger.error(ex.toString(), ex);
            return "";
        } finally {
            try {
                httpClient.close();
            } catch (Exception var30) {
                logger.error(var30.getMessage());
            }
        }
    }
}