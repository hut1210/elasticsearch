package com.example.demo.gson;

import com.example.demo.domain.WarnRules;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/10/9 15:19
 */
public class GsonTest {

    private static Gson GSON;

    static {
        GSON = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
    }

    public static void main(String[] args) {
        WarnRules warnRules = new WarnRules();
        warnRules.setCreateTime(new Date());
        System.out.println(GSON.toJson(warnRules));
    }
}
