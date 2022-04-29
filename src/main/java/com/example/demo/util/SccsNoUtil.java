package com.example.demo.util;


import com.example.demo.enums.PkMixEnum;
import com.example.demo.enums.PkTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * 公共方法:clps单号生成器
 * User: zhuhua
 * Date: 13-5-10Time: 下午1:51
 * version:1.0
 */
public class SccsNoUtil {

    private static long coefficient = 11L;
    private static long longSpace = 1032214646L;
    private static long shortSpace = 1626L;

    /**
     * 生成clps号
     *
     * @return
     */
    public static String generateNo(final PkTable bizType, final Long id) {
        if (PkMixEnum.LONG.getCode() == bizType.getMix()) {
            return bizType.getPrefix() + String.format(bizType.getFormat(), (id * coefficient) ^ longSpace);
        }
        if (PkMixEnum.SHORT.getCode() == bizType.getMix()) {
            return bizType.getPrefix() + String.format(bizType.getFormat(), (id * coefficient) ^ shortSpace);
        } else {
            return bizType.getPrefix() + String.format(bizType.getFormat(), id);
        }

    }

    public static long reverseNo2Id(String no, final PkTable bizType) {
        if (null == no || no.equals("")) {
            return -1L;
        }
        if (no.startsWith(bizType.getPrefix())) {
            try {
                if (PkMixEnum.LONG.getCode() == bizType.getMix()) {
                    Long mixId = Long.valueOf(no.substring(bizType.getPrefix().length())) ^ longSpace;
                    if (mixId % coefficient == 0) {
                        return mixId / coefficient;
                    } else {
                        return -1;
                    }
                }
                if (PkMixEnum.SHORT.getCode() == bizType.getMix()) {
                    Long mixId = Long.valueOf(no.substring(bizType.getPrefix().length())) ^ shortSpace;
                    if (mixId % coefficient == 0) {
                        return mixId / coefficient;
                    } else {
                        return -1;
                    }
                } else {
                    return Long.valueOf(no.substring(bizType.getPrefix().length()));
                }
            } catch (Exception e) {
                return -1L;
            }
        } else {
            try {
                return Long.valueOf(no);
            } catch (Exception e) {
                return -1L;
            }
        }
    }

    /**
     * 批量操作
     *
     * @param noCollection
     * @param bizType
     * @return
     */
    public static Set<Long> batchReverseNo2Id(Collection<String> noCollection, final PkTable bizType) {
        Set<Long> idSet = new HashSet<Long>();
        if (noCollection != null && !noCollection.isEmpty()) {
            for (String no : noCollection) {
                long id = reverseNo2Id(no, bizType);
                if (id != -1L) {
                    idSet.add(id);
                }
            }
        }
        return idSet;

    }

    /**
     * 编号转化为ID
     * 换另一个传参使用
     *
     * @param no       编号
     * @param noPrefix 编号前缀
     * @return 返回ID
     */
    @Deprecated
    public static long reverseNo2Id(String no, String noPrefix) {
        if (null == no || no.equals("")) {
            return -1L;
        }
        if (no.startsWith(noPrefix)) {
            try {
                return Long.valueOf(no.substring(noPrefix.length()));
            } catch (Exception e) {
                return -1L;
            }
        } else {
            try {
                return Long.valueOf(no);
            } catch (Exception e) {
                return -1L;
            }
        }
    }
}