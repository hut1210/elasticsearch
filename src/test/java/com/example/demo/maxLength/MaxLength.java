package com.example.demo.maxLength;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 21:17
 */
public class MaxLength {

    public static void main(String[] args) {
        System.out.println(maxLength(new int[]{2,2,3,4,3}));
    }

    /**
     * https://www.nowcoder.com/questionTerminal/b56799ebfd684fb394bd315e89324fb4?orderByHotValue=1&page=1&onlyReference=false
     */
    public static int maxLength (int[] arr) {
        LinkedList<Integer> list = new LinkedList<>();
        int p=0, ans=0;
        for(int i=0;i<arr.length;i++){
            if(list.contains(arr[i])){
                int j=list.indexOf(arr[i]);
                while (j-->=0){
                    list.removeFirst();
                }
            }
            list.add(arr[i]);
            ans=Math.max(ans,list.size());
        }
        return ans;
    }

    /**
     *
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public static int maxLength2 (int[] arr) {
        // write code here
        int[] rem = new int[100000];
        Arrays.fill(rem, -1);
        int start = 0, max = 0;
        //int start = 0；
        int i = 0;
        for(int a: arr){
            //1、当重复字符的上一次位置在start之前，取start
            //2、当重复字符的上一次位置在start位置或之后，取重复字符上一次位置的后一位位置
            //3、若无重复字符，则继续
            start = Math.max(start, rem[a]);
            max = Math.max(max, i - start + 1);
            rem[a] = i + 1; //指向所在位置的后一个位置
            i++;
        }

        return max;
    }
}
