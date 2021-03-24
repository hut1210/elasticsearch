package com.example.demo.bigscreen.workorder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.condition.WorkBillCondition;
import com.example.demo.condition.WorkBillPageCondition;
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
public class HttpWorkOrderTest {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private final static Logger logger = LoggerFactory.getLogger(HttpWorkOrderTest.class);
    //热点工单top5  区域
    private static String getGroupCount = "https://uat-proxy.jd.com/XZService/getGroupCount";
    //指标单量
    private static String getBillCount = "https://uat-proxy.jd.com/XZService/getBillCount";
    //工单类型、工单状态
    private static String getBillGeneralCount = "https://uat-proxy.jd.com/XZService/getBillGeneralCount";
    //工单报表
    private static String getBillReport = "https://uat-proxy.jd.com/XZService/getBillReport";

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
        String qrCode = "{\"createTimeStart\":\"2021-02-28\",\"createTimeEnd\":\"2021-02-28\"}";
        String param = "{\"workBillCondition\":{\"create_time_end\":\"2021-03-02 23:59:59\",\"create_time_start\":\"2021-02-23 00:00:00\"},{\"groupField\":\"mainid\",\"cardinality\":\"mainid\"}}";
        Map<String, Date> dateWhitBeforeN = DateUtils.getDateWhitBeforeN(0);
        System.out.println("dateWhitBeforeN="+dateWhitBeforeN);

        //postAction1
        WorkBillCondition workBillCondition = new WorkBillCondition();
        workBillCondition.setCreate_time_start(DateUtils.formatDate(dateWhitBeforeN.get("start"), DateUtils.DATE_FORMAT));
        workBillCondition.setCreate_time_end(DateUtils.formatDate(dateWhitBeforeN.get("end"), DateUtils.DATE_FORMAT));
        workBillCondition.setGroupField("mainid");
        workBillCondition.setCardinality("mainid");
        workBillCondition.setStatusList(Arrays.asList("20", "60", "80", "100","160","220","180", "200"));

        Map<String, String> params = new HashMap<>();
        params.put("groupField", "mainid");
        params.put("cardinality", "mainid");

        List<Object> list = new ArrayList<>();
        list.add(workBillCondition);
        list.add(params);
        System.out.println(JSONObject.toJSONString(list));
        Map<String, Object> map = new HashMap<>();
        map.put("workBillCondition", workBillCondition);
        map.put("params", params);
        System.out.println(JSONObject.toJSONString(map));
        System.out.println("workBillCondition = " + JSONObject.toJSONString(workBillCondition));

        //postAction
        WorkBillCondition workBillCondition2 = new WorkBillCondition();
        workBillCondition2.setGroupField("locate_attribute_id");
        workBillCondition2.setCardinality("mainid");
        workBillCondition2.setCreate_time_start("2021-03-02 00:00:00");
        workBillCondition2.setCreate_time_end("2021-03-02 23:59:59");

        //postAction2
        WorkBillCondition workBillCondition3 = new WorkBillCondition();
        workBillCondition3.setCreate_time_start(DateUtils.formatDate(dateWhitBeforeN.get("start"), DateUtils.DATE_FORMAT));
        workBillCondition3.setCreate_time_end(DateUtils.formatDate(dateWhitBeforeN.get("end"), DateUtils.DATE_FORMAT));
        workBillCondition3.setGroupField("work_order_type");
        workBillCondition3.setCardinality("mainid");
        workBillCondition3.setWork_order_type("0");

        //getBillGeneralCount
        WorkBillCondition workBillCondition4 = new WorkBillCondition();
        workBillCondition4.setCreate_time_start("2021-03-17 00:00:00");
        workBillCondition4.setCreate_time_end("2021-03-17 23:59:59");
        workBillCondition4.setGroupField("status");
        workBillCondition4.setCardinality("mainid");
        // 待审核
        workBillCondition4.setStatus("20");
        // 待领取
        workBillCondition4.setStatus("60");
        // 待回复
        workBillCondition4.setStatus("80");

        //postAction3
        Map<String, Date> dateWhitBeforeN1 = DateUtils.getDateWhitBeforeN(0);

        WorkBillPageCondition workBillPageCondition = new WorkBillPageCondition();
        workBillPageCondition.setCreate_time_start(DateUtils.formatDate(dateWhitBeforeN1.get("start"), DateUtils.DATE_FORMAT) + " 00:00:00");
        workBillPageCondition.setCreate_time_end(DateUtils.formatDate(dateWhitBeforeN1.get("end"), DateUtils.DATE_FORMAT) + " 23:59:59");
        workBillPageCondition.setPageIndex(1);
        workBillPageCondition.setPageSize(10);

        String s = doPost(JSONObject.toJSONString(workBillCondition4), getBillGeneralCount, macUser, macKey);
        System.out.println(JSON.toJSON(s));
        System.out.println(JSON.toJSONString(s));

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
