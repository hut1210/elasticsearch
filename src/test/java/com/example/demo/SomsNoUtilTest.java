package com.example.demo;

import com.example.demo.enums.PkTable;
import com.example.demo.util.SomsNoUtil;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 16:34
 */
public class SomsNoUtilTest {

    public static void main(String[] args) {
        long purchaseId = SomsNoUtil.reverseNo2Id("SCPO48597631484889", PkTable.TENANT_PURCHASEMAIN);
        System.out.println(purchaseId);
    }
}
