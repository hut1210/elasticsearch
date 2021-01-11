package com.example.demo.strategy;

import com.example.demo.domain.WarnRules;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:30
 */
@Slf4j
public class WarnTriggerFactory {

    private static Map<Integer, IWarnTrigger> warnTriggerMap = new HashMap<Integer, IWarnTrigger>();

    public static IWarnTrigger getWarnTriggerByTarget(Integer warnTarget) {
        //此处判空，没有可以抛异常
        if (warnTarget == null || !warnTriggerMap.containsKey(warnTarget)) {
            log.error("warnTarget is error.");
            return null;
        }
        return warnTriggerMap.get(warnTarget);
    }

    /**
     * 策略注册方法
     *
     * @param warnTarget
     * @param warnTrigger
     */
    public static void registerService(Integer warnTarget, IWarnTrigger warnTrigger) {
        warnTriggerMap.put(warnTarget, warnTrigger);
    }

}
