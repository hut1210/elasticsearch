package com.example.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 网关接口调用工具类
 *
 * @author huteng5
 * @version 1.0
 * @date 2021/2/25 20:25
 */
@Component
@PropertySource({"classpath:application.properties"})
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static int OK = 200;
    /**
     * user (测试环境，线上需提前申请)
     */
    @Value("${user}")
    private String user;

    /**
     * secret (测试环境，线上需提前申请)
     */
    @Value("${secret}")
    private String secret;

    /**
     * lopDN 服务域
     */
    @Value("${lopDN}")
    private String lopDN;

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    private String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
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

    private String MD5(String md5) {
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

    public String doPost(String param, String url,String user, String secret,String lopDN) {
        //请求参数对象
        logger.info("entry param:" + param +
                ",url:" + url +
                ",user:" + user +
                ",secret:" + secret +
                ",lopDN:" + lopDN);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            StringEntity params = new StringEntity(param);
            String xDate = getServerTime();
            String contentMd5 = MD5(param);
            String txt = "X-Date: " + xDate + "\n" + "content-md5: " + contentMd5;
            String sign = HmacSHA1Encrypt(txt, secret);

            HttpPost request = new HttpPost(url);
            String auth = "hmac username=\"" + user + "\", algorithm=\"hmac-sha1\", headers=\"X-Date content-md5\",signature=\"" + sign + "\"";
            request.addHeader("content-type", "application/json");
            request.addHeader("X-Date", xDate);
            request.addHeader("content-md5", contentMd5);
            request.addHeader("Authorization", auth);
            request.addHeader("LOP-DN", lopDN);
            if (param != null) {
                request.setEntity(params);
            }
            CloseableHttpResponse response = httpClient.execute(request);
            byte[] bytes;
            try {
                HttpEntity entity = response.getEntity();
                bytes = EntityUtils.toByteArray(entity);
                int rCode = response.getStatusLine().getStatusCode();
                if (rCode != OK) {
                    new String(bytes, "UTF-8");
                    logger.error("http请求网关接口失败,返回值:" + rCode + " " + response.getStatusLine().toString());
                }
            } finally {
                response.close();
            }
            String result = new String(bytes, "UTF-8");
            if (null != result && result.length() > 0) {
                return result;
            }
            return "";
        } catch (Exception ex) {
            logger.error("http请求网关接口异常" + ex.getMessage(), ex);
            return "";
        } finally {
            try {
                httpClient.close();
            } catch (Exception var30) {
                logger.error(var30.getMessage());
            }
        }
    }
    public String doPost(String param, String url) {
        //请求参数对象
        logger.info("entry param:" + param +
                ",url:" + url +
                ",user:" + user +
                ",secret:" + secret +
                ",lopDN:" + lopDN);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            StringEntity params = new StringEntity(param);
            String xDate = getServerTime();
            String contentMd5 = MD5(param);
            String txt = "X-Date: " + xDate + "\n" + "content-md5: " + contentMd5;
            String sign = HmacSHA1Encrypt(txt, secret);

            HttpPost request = new HttpPost(url);
            String auth = "hmac username=\"" + user + "\", algorithm=\"hmac-sha1\", headers=\"X-Date content-md5\",signature=\"" + sign + "\"";
            request.addHeader("content-type", "application/json");
            request.addHeader("X-Date", xDate);
            request.addHeader("content-md5", contentMd5);
            request.addHeader("Authorization", auth);
            request.addHeader("LOP-DN", lopDN);
            if (param != null) {
                request.setEntity(params);
            }
            CloseableHttpResponse response = httpClient.execute(request);
            byte[] bytes;
            try {
                HttpEntity entity = response.getEntity();
                bytes = EntityUtils.toByteArray(entity);
                int rCode = response.getStatusLine().getStatusCode();
                if (rCode != OK) {
                    new String(bytes, "UTF-8");
                    logger.error("http请求网关接口失败,返回值:" + rCode + " " + response.getStatusLine().toString());
                }
            } finally {
                response.close();
            }
            String result = new String(bytes, "UTF-8");
            if (null != result && result.length() > 0) {
                return result;
            }
            return "";
        } catch (Exception ex) {
            logger.error("http请求网关接口异常" + ex.getMessage(), ex);
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
