package com.example.demo.merge;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 21:31
 *
 * 给出两个有序的整数数组 和 ，请将数组 合并到数组 中，变成一个有序的数组
 */
public class Merge {
    public static void main(String[] args) {
        int[] num1 = {1,3,4,6,0,0,0,0};
        int[] num2 = {2,5,7,8};
        merge(num1,4,num2,4);
    }
    public static void merge(int A[], int m, int B[], int n) {
        int j = n -1;
        //index是数组A和B长度之和，因为题目规定A的长度一定能容纳B，所以直接相加作为合并后的坐标。
        int index = m + n - 1;
        int i = m -1;
        while(i >=0 && j >=0){
            A[index--] = A[i] > B[j] ? A[i--] : B[j--];
        }
        //如果A的数字比B多，则不会进入后续处理；如果B的数字比A多，则进入后续处理，将B剩余数字添加到数组A中。
        while(j >=0){
            A[index--] = B[j--];
        }
        System.out.println(Arrays.toString(A));
    }
}
