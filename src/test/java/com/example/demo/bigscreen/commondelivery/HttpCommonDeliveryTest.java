package com.example.demo.bigscreen.commondelivery;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.condition.CommonDeliveryPageCondition;
import com.example.demo.condition.WorkBillCondition;
import com.example.demo.condition.WorkBillPageCondition;
import com.example.demo.dto.CommonDeliveryDto;
import com.example.demo.util.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    //供配获取站点top5
    private static String postAction = "https://uat-proxy.jd.com/XZService/getDispatchSiteTop5";
    //配送状态饼图
    private static String postAction1 = "https://uat-proxy.jd.com/XZService/doGetDistributionStatusPieChat";
    //业务类型饼图
    private static String postAction2 = "https://uat-proxy.jd.com/XZService/doGetBusinessTypePieChat";
    //共配来源饼图
    private static String postAction3 = "https://uat-proxy.jd.com/XZService/doGetCommonDistributionSourcePieChat";
    //获取站点数量
    private static String getSiteCount = "https://uat-proxy.jd.com/XZService/getSiteCount";
    //全年12个月配送总量
    private static String getDispatchAnnualInfo = "https://uat-proxy.jd.com/XZService/getDispatchAnnualInfo";
    //共配地图区域数据
    private static String getAreaCount = "https://uat-proxy.jd.com/XZService/getAreaCount";
    //共配地图站点数据
    private static String getSiteList = "https://uat-proxy.jd.com/XZService/getSiteList";
    //共配分拣中心数量
    private static String getSortingCenter = "https://uat-proxy.jd.com/XZService/getSortingCenter";
    //共配数据汇总
    private static String getDataSummary = "https://uat-proxy.jd.com/XZService/getDataSummary";
    //共配近24h配送站点趋势
    private static String getDispatch24H = "https://uat-proxy.jd.com/XZService/getDispatch24H";

    private static String getCommonDeliveryOverviewDtoList = "https://uat-proxy.jd.com/XZService/getCommonDeliveryOverviewDtoList";

    public static void main(String[] args) throws Exception {
        Map<String, Date> dateWhitBeforeN = DateUtils.getDateWhitBeforeN(0);
        System.out.println("dateWhitBeforeN="+dateWhitBeforeN);
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getCurrYearFirst(), DateUtils.DATE_FORMAT));
        commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getCurrYearLast(), DateUtils.DATE_FORMAT));

        List<CommonDeliveryDto> commonDeliveryDtoList = new ArrayList<>();
        //commonDeliveryDtoList.add(CommonDeliveryDto.builder().key("999").amount("20").build());
        CommonDeliveryCondition commonDeliveryCondition2 = new CommonDeliveryCondition();
        commonDeliveryCondition2.setCreateTimeStart("2021-03-10");
        commonDeliveryCondition2.setCreateTimeEnd("2021-03-10");
        Map param = new HashMap<>();
        param.put("commonDeliveryDtoList",commonDeliveryDtoList);
        param.put("commonDeliveryCondition",commonDeliveryCondition2);

        String s = doPost(JSONObject.toJSONString(param), getCommonDeliveryOverviewDtoList, macUser, macKey);
        System.out.println(JSON.toJSON(s));
        System.out.println(JSON.toJSONString(s));

    }

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

    public static String doPost(String param, String url, String macUser, String macKey) {
        //请求参数对象

        logger.info("entry param:" + param +
                ",url:" + url +
                ",macUser:" + macUser +
                ",macKey:" + macKey);
        //httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            //request param
            StringEntity params = new StringEntity(param);
            String xdate = getServerTime();
            String contentMd5 = MD5(param);
            String txt = "X-Date: " + xdate + "\n" + "content-md5: " + contentMd5;
            String sign = HmacSHA1Encrypt(txt, macKey);
            HttpPost request = new HttpPost(url);
            String auth = "hmac username=\"" + macUser + "\", algorithm=\"hmac-sha1\", headers=\"X-Date content-md5\",signature=\"" + sign + "\"";
            request.addHeader("content-type", "application/json");
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
