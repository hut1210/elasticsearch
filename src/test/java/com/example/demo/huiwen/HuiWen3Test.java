package com.example.demo.huiwen;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 21:22
 */
public class HuiWen3Test {
    public static void main(String[] args) {
        String s="212123432143434";
        System.out.println(longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        String result = "";

        for (int i=0; i<s.length(); i++) {
            String s1 = expand(s, i, i);
            String s2 = expand(s, i, i+1);

            if (s1.length() > result.length()) {
                result = s1;
            }
            if (s2.length() > result.length()) {
                result = s2;
            }
        }

        return result;
    }

    public static String expand(String s, int c1, int c2) {
        int j = 0;
        for (; c2+j<s.length() && c1-j>=0; j++) {
            if (s.charAt(c2+j) != s.charAt(c1-j)) {
                break;
            }
        }

        return s.substring(c1-j+1, c2+j);
    }

}
