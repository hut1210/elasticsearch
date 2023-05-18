package com.example.demo;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hut
 * @date 2023/3/14 2:12 下午
 */
public class KmbmTest3 {
    public static void main(String[] args) {
        String str = "余额表\n" +
                "编制单位：郑州金坤门业有限公司\n" +
                "科目代码\n" +
                "\n" +
                "1001\n" +
                "1002\n" +
                "1122\n" +
                "1122-WL019\n" +
                "1122-WL040\n" +
                "1122-WL067\n" +
                "1122-WL071\n" +
                "1122-WL078\n" +
                "1122-WL090\n" +
                "1122-WL091\n" +
                "1122-WL097\n" +
                "1122-WL099\n" +
                "1122-WL105\n" +
                "1122-WL106\n" +
                "1122-WL108\n" +
                "1122-WL110\n" +
                "1122-WL114\n" +
                "1122-WL117\n" +
                "1122-WL118\n" +
                "1122-WL119\n" +
                "1122-WL120\n" +
                "1122-WL121\n" +
                "1122-WL126\n" +
                "1122-WL127\n" +
                "1122-WL128\n" +
                "1122-WL129\n" +
                "1122-WL130\n" +
                "1122-WL131\n" +
                "1122-WL132\n" +
                "1122-WL133\n" +
                "1122-WL134\n" +
                "1122-WL139\n" +
                "1122-WL140\n" +
                "1122-WL141\n" +
                "1122-WL142\n" +
                "1122-WL143\n" +
                "1122-WL144\n" +
                "1122-WL145\n" +
                "1122-WL146\n" +
                "1122-WL147\n" +
                "1122-WL148\n" +
                "1122-WL149\n" +
                "1122-WL150\n" +
                "1122-WL151\n" +
                "1122-WL152\n" +
                "1122-WL153\n" +
                "1122-WL154\n" +
                "1122-WL155\n" +
                "1122-WL156\n" +
                "1122-WL162\n" +
                "1122-WL163\n" +
                "1122-WL164\n" +
                "1122-WL165\n" +
                "1122-WL166\n" +
                "1122-WL167\n" +
                "1122-WL171\n" +
                "1122-WL173\n" +
                "1122-WL174\n" +
                "1122-WL175\n" +
                "1122-WL176\n" +
                "1122-WL177\n" +
                "1122-WL178\n" +
                "1122-WL179\n" +
                "1122-WL180\n" +
                "1122-WL181\n" +
                "1122-WL182\n" +
                "1122-WL183\n" +
                "1122-WL184\n" +
                "1122-WL186\n" +
                "1122-WL187\n" +
                "1122-WL188\n" +
                "1122-WL189\n" +
                "1122-WL190\n" +
                "1122-WL191\n" +
                "1122-WL192\n" +
                "1122-WL193\n" +
                "1122-WL194\n" +
                "1122-WL195\n" +
                "1122-WL196\n" +
                "1122-WL197\n" +
                "1122-WL198\n" +
                "1122-WL201\n" +
                "1122-WL202\n" +
                "1122-WL204\n" +
                "1122-WL205\n" +
                "1122-WL206\n" +
                "1122-WL207\n" +
                "1122-WL208\n" +
                "1122-WL209\n" +
                "1122-WL210\n" +
                "1122-WL211\n" +
                "1122-WL212\n" +
                "1122-WL213\n" +
                "1122-WL214\n" +
                "1122-WL215\n" +
                "1122-WL216\n" +
                "1122-WL217\n" +
                "1122-WL218\n" +
                "1122-WL219\n" +
                "1122-WL220\n" +
                "1122-WL221\n" +
                "1122-WL222\n" +
                "1122-WL223\n" +
                "1122-WL224\n" +
                "1122-WL225\n" +
                "1122-WL226\n" +
                "1122-WL227\n" +
                "1122-WL228\n" +
                "1122-WL229\n" +
                "1122-WL230\n" +
                "1122-WL231\n" +
                "1122-WL232\n" +
                "1122-WL234\n" +
                "1122-WL235\n" +
                "1122-WL236\n" +
                "1122-WL239\n" +
                "1122-WL240\n" +
                "1122-WL241\n" +
                "1122-WL242\n" +
                "1122-WL243\n" +
                "1122-WL245\n" +
                "1122-WL246\n" +
                "1122-WL247\n" +
                "1122-WL248\n" +
                "1122-WL249\n" +
                "1122-WL250\n" +
                "1122-WL251\n" +
                "1122-WL254\n" +
                "1122-WL255\n" +
                "1122-WL256\n" +
                "1122-WL257\n" +
                "1122-WL258\n" +
                "1122-WL259\n" +
                "1122-WL260\n" +
                "1122-WL261\n" +
                "1122-WL262\n" +
                "1122-WL263\n" +
                "1122-WL264\n" +
                "1122-WL265\n" +
                "1122-WL266\n" +
                "1122-WL267\n" +
                "1122-WL268\n" +
                "1122-WL273\n" +
                "1122-WL274\n" +
                "1122-WL275\n" +
                "1122-WL276\n" +
                "1122-WL277\n" +
                "1122-WL278\n" +
                "1122-WL282\n" +
                "1122-WL283\n" +
                "1122-WL284\n" +
                "1122-WL285\n" +
                "1122-WL286\n" +
                "1122-WL287\n" +
                "1122-WL291\n" +
                "1122-WL292\n" +
                "1122-WL293\n" +
                "1122-WL294\n" +
                "1122-WL295\n" +
                "1122-WL296\n" +
                "1122-WL297\n" +
                "1122-WL299\n" +
                "1122-WL300\n" +
                "1122-WL301\n" +
                "1122-WL302\n" +
                "1122-WL303\n" +
                "1122-WL304\n" +
                "1122-WL305\n" +
                "1122-WL306\n" +
                "1122-WL307\n" +
                "1122-WL309\n" +
                "1122-WL310\n" +
                "1122-WL311\n" +
                "1122-WL312\n" +
                "1122-WL314\n" +
                "1122-WL315\n" +
                "1122-WL316\n" +
                "1122-WL317\n" +
                "1122-WL318\n" +
                "1122-WL319\n" +
                "1122-WL320\n" +
                "1122-WL321\n" +
                "1122-WL322\n" +
                "1122-WL323\n" +
                "1122-WL324\n" +
                "1122-WL328\n" +
                "1122-WL329\n" +
                "1122-WL330\n" +
                "1122-WL331\n" +
                "1122-WL332\n" +
                "1122-WL333\n" +
                "1122-WL334\n" +
                "1122-WL335\n" +
                "1122-WL336\n" +
                "1122-WL337\n" +
                "1221\n" +
                "1221-WL059\n" +
                "1403\n" +
                "1405\n" +
                "1601\n" +
                "1602\n" +
                "2202\n" +
                "2202-WL006\n" +
                "2202-WL009\n" +
                "2202-WL047\n" +
                "2202-WL050\n" +
                "2202-WL107\n" +
                "2202-WL109\n" +
                "2202-WL115\n" +
                "2202-WL122\n" +
                "2202-WL123\n" +
                "2202-WL124\n" +
                "2202-WL135\n" +
                "2202-WL136\n" +
                "2202-WL137\n" +
                "2202-WL138\n" +
                "2202-WL157\n" +
                "2202-WL158\n" +
                "2202-WL159\n" +
                "2202-WL170\n" +
                "2202-WL185\n" +
                "2202-WL199\n" +
                "2202-WL200\n" +
                "2202-WL203\n" +
                "2202-WL237\n" +
                "2202-WL238\n" +
                "2202-WL244\n" +
                "2202-WL252\n" +
                "2202-WL253\n" +
                "2202-WL269\n" +
                "2202-WL270\n" +
                "2202-WL271\n" +
                "2202-WL272\n" +
                "2202-WL279\n" +
                "2202-WL280\n" +
                "2202-WL281\n" +
                "2202-WL288\n" +
                "2202-WL289\n" +
                "2202-WL290\n" +
                "2202-WL298\n" +
                "2202-WL308\n" +
                "2202-WL313\n" +
                "2202-WL325\n" +
                "2202-WL326\n" +
                "2202-WL327\n" +
                "2211\n" +
                "221101\n" +
                "221104\n" +
                "22110401\n" +
                "22110402\n" +
                "22110403\n" +
                "22110404\n" +
                "22110405\n" +
                "2221\n" +
                "222101\n" +
                "22210101\n" +
                "22210102\n" +
                "22210107\n" +
                "22210108\n" +
                "222104\n" +
                "222105\n" +
                "222111\n" +
                "222115\n" +
                "222118\n" +
                "222121\n" +
                "222129\n" +
                "22212901\n" +
                "2241\n" +
                "2241-WL002\n" +
                "2241-WL059\n" +
                "2241-WL116\n" +
                "2241-WL125\n" +
                "2241-WL160\n" +
                "2241-WL161\n" +
                "2241-WL172\n" +
                "2241-WL233\n" +
                "2241-WL325\n" +
                "2251\n" +
                "3103\n" +
                "3104\n" +
                "310402\n" +
                "4001\n" +
                "400101\n" +
                "40010101\n" +
                "40010102\n" +
                "5001\n" +
                "5401\n" +
                "5403\n" +
                "540303\n" +
                "540309\n" +
                "540310\n" +
                "540313\n" +
                "5602\n" +
                "560202\n" +
                "560203\n" +
                "560207\n" +
                "56020701\n" +
                "56020703\n" +
                "560223\n" +
                "560225\n" +
                "560229\n" +
                "5801\n";

        String[] split = str.split("\n");
        Map<String, Integer> map = new HashMap();
        //存放当前使用的英文字母
        Map<String, String> englishLetterMap = new HashMap();
        List<String> list = Arrays.asList("A", "B", "C", "D", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W");
        int englishLetter = 0;
        for (int i = 0; i < split.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder temp = new StringBuilder(split[i]);
            if (temp.indexOf("-") > -1) {
                String substring = temp.substring(0, temp.indexOf("-"));
                stringBuilder.append(substring);
                if (map.get(substring) == null) {
                    stringBuilder.append("01");
                    map.put(substring, 1);
                } else {
                    if ((map.get(substring) + 1) > 99) {
                        //英文字母计数 map.get("num")
                        if (map.get("num") == null) {
                            map.put("num", 1);
                        } else {
                            if (map.get("num") > 8) {
                                map.put("num", 1);
                                englishLetter++;
                            } else {
                                map.put("num", map.get("num") + 1);
                            }
                        }
                        englishLetterMap.put("englishLetter", list.get(englishLetter));
                        stringBuilder.append(englishLetterMap.get("englishLetter"));
                        stringBuilder.append(map.get("num"));
                    } else {
                        String s = StringUtils.leftPad((map.get(substring) + 1) + "", 2, "0");
                        stringBuilder.append(s);
                        map.put(substring, map.get(substring) + 1);
                    }
                }
                System.out.println(stringBuilder.toString());
            } else {
                System.out.println(temp);
            }
        }
    }
}
