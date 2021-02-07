package com.example.demo.huiwen;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 21:05
 */
public class HuiWen2Test {
    public static void main(String[] args) {
        String str = "sgfsasaopoiuydfghgfdtrewqirgabnirweir";
        System.out.println("答案是："+searchMaxOddStr(str));
    }

    /**
     * 查询最长回文字符串。@author YYM
     * 只能查询到如"asdsa"类型的字符串（总长度为奇数）。
     * 而无法查询"asddsa"类型的（总长度为偶数）。
     * 若要查询asdsa类型的，请对原字符串数据进行处理。
     * @param str 待分析的字符串
     * @return 结果字符串
     */
    public static String searchMaxOddStr(String str) {
        if (str.equals("")) {
            return "";
        }
        String answer;//答案字符串
        int maxLen = 0;//存放当前回文字符串的"半径".
        int maxCenter = 0;//存放当前回文字符串的中心。
        int nowCenter=0;
        int nowLen =0;
        boolean flag = true;
        char[] list = str.toCharArray();
        for(nowCenter = 0;nowCenter< list.length;) {
            while(flag) {//开始执行一个中心点的判断
                if((nowCenter-nowLen)>=0 && nowCenter+nowLen<list.length) {//防止越界
                    if(list[nowCenter-nowLen]==list[nowCenter+nowLen]) {
                        if(maxLen<nowLen) {
                            maxLen = nowLen;
                            maxCenter = nowCenter;
                        }
                        nowLen++;
                    }else flag=false;
                }else flag =false;
            }
            nowCenter++;//判断下一个
            flag = true;//恢复初始状态
            nowLen=1;//恢复初始长度
        }
        answer = str.substring(maxCenter-maxLen,maxCenter+maxLen+1);//截取
        return answer;
    }
}
