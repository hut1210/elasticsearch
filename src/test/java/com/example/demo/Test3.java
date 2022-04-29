package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.Collator;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/11 17:57
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) {
        String s = "123";
        if (s.equals("123")) {
            System.out.println(123);
        } else if (s.equals("456")) {
            System.out.println(456);
        } else {
            System.out.println(0);
        }

        Map map = new HashMap<>();
        map.put("a", 123);

        System.out.println(map.toString());

        Student student = new Student();
        Student student2 = new Student();
        List list = new ArrayList<>();
        list.add(student);
        list.add(student2);
        log.info("list=" + list);
        System.out.println(StringUtils.isBlank("PL"));
        System.out.println(StringUtils.isBlank(null));

        if (!(StringUtils.isBlank("") && StringUtils.isBlank(""))) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
        Map map1 = new HashMap();
        map1.put("name", "zhangsan");
        map1.put("name1", "zhangsan");
        map1.put("name2", "zhangsan");
        map1.put("name3", "zhangsan");
        log.info("map1=" + map1.toString());
        String SecretKey = "DEDddads23232";
        System.out.println(Sign(map1, SecretKey));

        String ss = "2";
        if (ss.equals("3") || ss.equals("2")) {
            System.out.println(true);
        }

        Byte a = new Byte("2");

        System.out.println("123" + (a.intValue() == 2));

        Integer i1 = Integer.valueOf(127);
        Integer i2 = Integer.valueOf(127);
        Integer i3 = Integer.valueOf(200);
        Integer i4 = Integer.valueOf(200);
        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }

    public static String getServerIp() {
        String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                SERVER_IP = ip.getHostAddress();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                && ip.getHostAddress().indexOf(":") == -1){
                    SERVER_IP = ip.getHostAddress();
                    break;
                } else{
                    ip = null;
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return SERVER_IP;
    }

    public static String Sign(Map<String, String> map, String SecretKey) {
        String pingjie = "";
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        String[] arrStrings = new String[map.size()];
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> vo : map.entrySet()) {
            list.add(vo.getKey() + vo.getValue());
        }
        for (int i = 0; i < list.size(); i++) {
            arrStrings[i] = list.get(i);
        }
        Arrays.sort(arrStrings, comparator);
        for (int i = 0; i < arrStrings.length; i++) {
            pingjie = pingjie + arrStrings[i];
        }
        //参数拼接
        String sign = pingjie.trim().toLowerCase();
        // System.out.println(sign);
        //加密
        String NEWsign = MD5(sign + SecretKey);
        return NEWsign;
    }

    /**
     * 27.	 * @param str
     * 28.	 * @return
     * 29.
     */
    public static String MD5(String str) {
        return DigestUtils.md5Hex(str).toLowerCase();
    }

}
