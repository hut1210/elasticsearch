package com.example.demo;

import java.util.Arrays;

/**
 * @author hut
 * @date 2022/11/15 7:25 下午
 */
public class Test {

    public static void main(String[] args) {
        //System.out.println(getN(7));
        int oldArr[]={-4,-3,0,1,56,34,76,34,23,4,75,87,50,3,5,6,0};
        //int oldArr[] = {1, -1, 3, 2, 0};
        System.out.println(getIndex(oldArr));
    }

    // 数列前2项为 1 1，从第3项开始，若为奇数项，则值为前两项之和，若为偶数项，则值为前两项之和的2倍
    // 可知该数列前6项为 1 1 2 6 8 28
    // 求该数列的第n项
    // 请用递归和循环两种方式实现
    public static long getN(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else if (n % 2 == 0) {
            return (getN(n - 1) + getN(n - 2)) * 2;
        } else if (n % 2 == 1) {
            return getN(n - 1) + getN(n - 2);
        }
        return 0;
    }

    // 给定一个int型数组，找出其中大于0的数字中倒数第二小的数字的下标
    // 例如 1 -1 3 2 0，其中大于0的数字有1 3 2，倒数第二小的数字为2，其下标为3
    // 不能使用排序
    // 尽量只使用一次循环
    public static int getIndex(int[] array) {
        int arr[] = Arrays.stream(array).filter(e -> (e > 0)).toArray();
        int min = arr[0];
        int sec = arr[1];
        int temp;
        if (min > sec) {
            temp = min;
            min = sec;
            sec = temp;
        }
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] < min) {
                sec = min;
                min = arr[i];
            } else if (arr[i] > min && arr[i] < sec) {
                sec = arr[i];
            }
        }
        return sec;
    }

    public static void getSecminindex(int[] arr) { // int[] 数组

        // 冒泡排序对数组进行排列
        for (int i = 0; i < arr.length - 1; i++) { // n个数字比较n-1次
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        // 定义一个变量来接受第二小下标值
        int index = 0;
        // 第二小值的位置 arr[1]
        for (int i = 0; i < arr.length; i++) { // newarr是没有排序过的
            if (arr[i] == arr[1]) {
                index = i;
            }
        }

        System.out.println("第二小的下标索引为：" + index + ",元素为" + arr[index]);
    }

}
