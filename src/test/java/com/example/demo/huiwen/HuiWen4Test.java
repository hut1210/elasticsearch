package com.example.demo.huiwen;

import java.util.HashSet;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 21:36
 */
public class HuiWen4Test {
    public static int getLonestLength(String s){
        int count = 0;
        char[] chars = s.toCharArray();
        HashSet set = new HashSet();
        for(int i = 0;i < chars.length; i++){
            char b = chars[i];
            if(set.contains(b)){
                count += 2;
                set.remove(b);
            }else{
                set.add(b);
            }
        }
        if(count < s.length()){
            count ++;
        }
        return count;
    }

    public static void main(String[] args) {
        String s = "assdsdgggggaa";
        System.out.println(getLonestLength(s));
    }
}
