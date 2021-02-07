package com.example.demo;

import java.util.HashMap;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 21:52
 */
public class Solution {
    /**
     *
     * @param numbers int整型一维数组
     * @param target int整型
     * @return int整型一维数组
     */
    public static int[] twoSum (int[] numbers, int target) {
        int[] result=new int[2];
        for(int i=0;i<numbers.length;i++){
            for(int j=i+1;j<numbers.length;j++){
                if(numbers[i]+numbers[j]==target){
                    result[1]=j+1;
                    result[0]=i+1;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 前提是元素已经升序排列好的数组
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum2 (int[] numbers, int target) {
        int[] result=new int[2];
        int i=0,j=numbers.length-1;
        while (i<j){
            int sum = numbers[i]+numbers[j];
            if(sum==target){
                result[1]=j+1;
                result[0]=i+1;
                return result;
            }else if(sum<target){
                i++;
            }else{
                j--;
            }
        }
        return null;
    }

    public int[] twoSum3 (int[] numbers, int target) {
        int len = numbers.length;
        int[] result = new int[2];
        //map里面放 键为target-每个数的结果 值为下标
        //每次放入的时候看是否包含 当前值
        //有的话说明当前值和已包含的值下标的那个元素为需要的结果
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0 ; i < len ; i++){
            if(map.containsKey(numbers[i])){
                result[0] = map.get(numbers[i])+1;
                result[1] = i+1;
                break;
            }else{
                map.put(target-numbers[i],i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i : twoSum(new int[]{1, 3, 7}, 10)) {
            System.out.println(i);
        }

        for (int i : twoSum2(new int[]{2,3,4}, 6)) {
            System.out.println(i);
        }
    }
}
