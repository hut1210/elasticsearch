package com.example.demo.findMinNum;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/3 21:32
 *
 * 题目描述
 * 给定一个无序数组arr，找到数组中未出现的最小正整数
 * 例如arr = [-1, 2, 3, 4]。返回1
 * arr = [1, 2, 3, 4]。返回5
 * [要求]
 * 时间复杂度为O(n)，空间复杂度为O(1)
 *
 * 题目分析
 * 原地哈希，把数组中取值在1到n的数放到对应的位置，比如1放到0的位置，2放到1的位置，……n放到n-1的位置，然后遍历重置后的数组，
 * 若i下标位置不是i+1，则i+1就是那个最小的正整数，若1到n位置都对的上，说明最小的正整数是n+1。
 */
public class findMinNum {

    /**
     * return the min number
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public static int minNumberdisappered (int[] arr) {
        // write code here
        int n = arr.length;
        for(int i = 0 ; i < n ; i++){
            while(arr[i]>=1 && arr[i]<=n && arr[arr[i]-1]!=arr[i]){
                swap(arr,arr[i]-1,i);
            }
        }

        for(int i = 0 ; i<n; i++){
            if(arr[i] !=i+1){
                return i+1;
            }
        }
        return n+1;
    }

    private static void swap(int[] arr,int i,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        System.out.println(minNumberdisappered(new int[]{2, 4, 3, 1}));
    }
}
