package com.example.demo.huiwen;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 20:22
 */
public class HuiWenTest {

    static String hws = "";
    static int num = 0;
    static String[] hw;

    public static void main(String[] args) {
        String text = "123321qwwqaartyuuytr1";
        //System.out.println(isHuiWen(text));
        char[] c = text.toCharArray();
        System.out.println("--------输出所有的回文------");
        outPalindrome(c);
        System.out.println("--------输出最长的回文------");
        longestPalindromes();
    }

    public static boolean isHuiWen(String text) {
        int length = text.length();
        for (int i = 0; i < length / 2; i++) {
            if (text.toCharArray()[i] != text.toCharArray()[length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 创建数组保存所有的回文
     * @return 返回一个String类型的数组
     */
    public static String[] createHw() {
        return new String[num];
    }

    /**
     * 将hws字符串更改为空字符
     */
   public static void hwsClose() {
       hws = "";
   }

    /**
     * 判断该字符串中存在的回文的数量
     * @param c  数组c ，c是用户输入的字符串转换为单个字符组成的数组
     * @return
     */
    public static int judgeNumber(char[] c) {
        for (int i = 0; i < c.length - 1; i++) {
            if (c[i] == c[i + 1]) {
                num++;
            }
        }
        return num;
    }

    /**
     2      * 第一次判断字符串中前后的数是否存在相同
     3      *
     4      * @param c
     5      *            数组c ，c是用户输入的字符串转换为单个字符组成的数组
     6      */
    public static void judge(char[] c) {
        judgeNumber(c);
        if (num != 0) {
            hw = createHw();
            for (int i = 0; i < c.length - 1; i++) {
                if (c[i] == c[i + 1]) {
                    hws = hws + c[i];
                    judge2(c, i, i + 1);
                    hw[--num] = hws;
                    hwsClose();
                }
            }
        } else {
            System.out.println("该字符串没有回文");
        }
    }

    /**
     2      * 进行二次判断以当前相同的字符为起点，分别以前下标向前和该后下标向后进行比较()
     3      *
     4      * @param c
     5      *            数组c ，c是用户输入的字符串转换为单个字符组成的数组
     6      * @param i
     7      *            数组前一个下标，该下标和后一个进行比较并且相同
     8      * @param k
     9      *            数组后一个下标，该下标和前一个进行比较并且相同
     10      */
    public static void judge2(char[] c, int i, int k) {
        if (i - 1 >= 0 && k + 1 < c.length) {
            if (c[i - 1] == c[k + 1]) {
                hws = hws + c[i - 1];
                judge2(c, i - 1, k + 1);
            }
        }
    }
        /**
  2      * 获取所得的回文
  3      *
  4      * @param c
  5      *            数组c ，c是用户输入的字符串转换为单个字符组成的数组
  6      */
    public static void outPalindrome(char[] c) {
        judge(c);
        if (hw!=null) {
            for (int i = 0; i < hw.length; i++) {
                System.out.println(reverse(hw[i])+hw[i]);
            }
        } else {
            System.out.println("没有回文2");
        }
    }

    /**
     2      * 将最长的回文输出
     3      *
     4      */
    public static void longestPalindromes() {
        String longest = null;
        if (hw!=null) {
            if (hw.length == 1) {
                System.out.println(reverse(hw[0])+hw[0]);
            } else {
                for (int i = 0; i < hw.length - 1; i++) {
                    for (int j = 0; j < hw.length - 1 - i; j++) {
                        if (hw[j].length() > hw[j + 1].length()) {
                            longest = hw[j + 1];
                            hw[j + 1] = hw[j];
                            hw[j] = longest;
                        }
                    }
                }
                for (int i = 0; i < hw.length - 1; i++) {
                    if (hw[hw.length - 1].length() == hw[hw.length - i - 1].length()) {
                        System.out.println(reverse(hw[hw.length - i - 1])+hw[hw.length - i - 1]);
                    }
                }
            }
        } else {
            System.out.println("没有回文3");
        }
    }

    /**
     2      * 将字符串进行倒序
     3      * @param string 将要被倒序的字符串
     4      * @return 返回倒序后的字符串
     5      */
    public static StringBuffer reverse(String string){
        StringBuffer sb=new StringBuffer();
        char a;
        for(int i=0;i<string.length();i++){
            a=string.charAt(string.length()-1-i);
            sb.append(a);
        }
        return sb;
    }
}
