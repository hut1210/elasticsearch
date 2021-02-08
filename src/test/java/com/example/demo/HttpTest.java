package com.example.demo;


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
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @program: dispatch
 * @description:
 **/
public class HttpTest {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private final static Logger logger = LoggerFactory.getLogger(HttpTest.class);


    //postAction （固定不变）
    // {"startTime":"2020-12-12 00:00:00","endTime":"2020-12-12 23:59:00","page":1,"limit":1}

    private static String postAction = "https://uat-proxy.jd.com/MNService/getOrder"; //3
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getOrderDetail";// 133
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getOrderWater";//143

//    private static String postAction = "https://uat-proxy.jd.com/MNService/getWaybill";//1777
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getWaybillWater";//2,112,1998

//    private static String postAction = "https://uat-proxy.jd.com/MNService/getWaybillPackage";//0,0
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getWaybillPackageDetail";//
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getDispatch";//0,0

//    private static String postAction = "https://uat-proxy.jd.com/MNService/getPoMain";// 1,1
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getPoMainStatus";// 1
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getPoItem";// 10


//    private static String postAction = "https://uat-proxy.jd.com/MNService/getWarehouse";// 43
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getCustomer";// 10
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getShipper";// 125

//    private static String postAction = "https://uat-proxy.jd.com/MNService/getGoods";//
//    private static String postAction = "https://uat-proxy.jd.com/MNService/getStock";//


    //postUrl
    private static String postUrl = new StringBuilder().append(postAction).toString();
    //user (测试环境，线上需提前申请)
    private static String macUser = "lop://9/MNDJ";
    //secret (测试环境，线上需提前申请)
    private static String macKey = "c92cdc00357000013efc19a919d02fb0";

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

        String param = "[{\"source\":\"ISV\",\"salePlat\":\"0010001\",\"customerCode\":\"010K101\",\"customerName\":\"ec139\",\"orderId\":\"676555512\",\"thrOrderId\":\"676555512\",\"senderName\":\"MrGao\",\"senderAddress\":\"Pergudangan Marunda Center, Sagara Makmur\",\"senderMobile\":\"18811700000\",\"senderProvince\":\"California\",\"senderCity\":\"Fullerton\",\"senderPostcode\":\"92831\",\"receiveName\":\"GaoYaofei\",\"receiveAddress\":\"Beijingdaxin\",\"receiveTel\":\"1321313213\",\"receiveMobile\":\"18811766317\",\"province\":\"Arizona\",\"city\":\"CasaGrande\",\"postcode\":\"85193\",\"packageCount\":1,\"collectionValue\":1,\"collectionMoney\":100.00,\"country\":\"US\"}]";
        //String param2="[{\"appendPropertiesDto\":{\"easyPollution\":false,\"industry\":2,\"muslim\":false,\"quarantineTicket\":true,\"receipt\":0,\"sendToStore\":false,\"temperatureLayer\":-1},\"clientCode\":\"1\",\"clientName\":\"客户端名字\",\"merchantCode\":\"10K20902\",\"merchantName\":\"大运单\",\"orderNo\":\"VA85349534531\",\"orderType\":2,\"paymentType\":1,\"pickupType\":0,\"receiveTel\":\"18333333333\",\"recipientAddress\":\"末端转运中心地址\",\"recipientName\":\"收件人电话\",\"sendAddress\":\"起始转运地址\",\"sendType\":1,\"senderName\":\"发送人员信息\",\"senderTel\":\"18333333333\",\"source\":\"QL\",\"sumNum\":1,\"sumVolume\":11.00,\"sumWeight\":22.00,\"tenantCode\":\"石景山营业部\",\"tenantName\":\"21\",\"transType\":1,\"waybill\":\"VA85349534534\"}]";
        String msg = "[{\"subOrderId\":\"BWX4PL20200206003\",\"weight\":12.2,\"volume\":12.2,\"packageNum\":3,\"ownerNo\":\"SOWE0001032215353\",\"source\":\"6\",\"pin\":\"LCWDGJ00000000022\"}]";
        /* Map<String,Object> map = new HashedMap();
        map.put("orderId","201026127");
        map.put("customerId",179);
        JSONArray jsonArray = JSONArray.fromObject(map);*/
        // String qrCode="[{\"subOrderId\":\"1222\",\"vendorId\":\"11\",\"weight\":8,\"volume\":0,\"packageNum\":8}]";
        String qrCode = "{\"startT\":\"2020-11-13 00:00:00\",\"endT\":\"2020-11-13 23:59:00\",\"page\":1,\"limit\":1}";
        String s = doPost(qrCode, postUrl, macUser, macKey);
        System.out.println(s);

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

            //request
            HttpPost request = new HttpPost(url);
//            HttpGet request = new HttpGet(url);


            String auth = "hmac username=\"" + macUser + "\", algorithm=\"hmac-sha1\", headers=\"X-Date content-md5\",signature=\"" + sign + "\"";
            request.addHeader("content-type", "application/json");
            request.addHeader("X-Date", xdate);
            request.addHeader("content-md5", contentMd5);
            request.addHeader("Authorization", auth);
            request.addHeader("LOP-DN", "clps.api.com");
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
