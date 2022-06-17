package com.example.demo.yl;

public class TransportHttpWLNServiceImpl{

    private static final String CONTENT_FORMAT = "_app=%s&_s=&_t=%s";


    public String transport(String url) throws Exception {
        String dateRes = null;
        try {
            dateRes = HttpUtil.sendPost(url);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return dateRes;
    }

    public String transportHttpRequest(String url,String param) throws Exception {
        String dateRes = null;
        try {
            dateRes = HttpClientUtil.httpPostWithjson(url,param);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return dateRes;
    }

}
