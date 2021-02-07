package com.example.demo.spiralmatrix;

import java.util.Arrays;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 20:39
 * 螺旋矩阵的思路：
 * 1.定义上下左右四个方向
 *  左方向的初始值就是二维数组的最左一列下标（left）
 *  右方向的初始值就是二维数组的最右一列下标（right）
 *  上方向的初始值就是二维数组的最上一行下标（top）
 *  下方向的初始值就是二维数组的最下一行下标（bottom）
 * 2.按照"右->下->左->上"的方向对二维数组进行填充，初始就是往右方向进行遍历，注意的是外层循环的条件就是左方向的值要小于等于右方向并且上方向的值要小于等于下方向
 *  右方向遍历：for循环变量的初始值就是left，当达到right边界的时候，说明上面已经遍历完一行了，所以top++，准备向下遍历，需要将遍历的方向改为bottom;
 *  下方向遍历：for循环变量的初始值就是top，当达到bottom边界的时候，说明右边已经遍历完一列了，所以right- -，准备向左遍历，需要将遍历的方向改为left;
 *  左方向遍历：for循环变量的初始值就是right，当达到left边界的时候，说明下面已经遍历完一行了，所以bottom- -，准备向上遍历，需要将遍历的方向改为top;
 *  上方向遍历：for循环变量的初始值就是bottom，当达到top边界的时候，说明左边已经遍历完一列了，所以left++，准备向右遍历，需要将遍历的方向改为right;
 */
public class SpiralMatrix1 {

    public static void main(String[] args) {
        int[][] arr = spiralMatrix(4,5);
        for(int m=0;m<arr.length;m++){//控制行数
            for(int n=0;n<arr[m].length;n++){//一行中有多少个元素（即多少列）
                System.out.print(arr[m][n]+" ");
            }
            System.out.println();
        }
        System.out.println(arr[1][1]);
    }

    public static int[][] spiralMatrix(int n, int m){
        int[][] arr=new int[n][m];
        int left=0;//左方向
        int right=arr[0].length-1;//右方向
        int top=0;//上方向
        int bottom=arr.length-1;//下方向

        String direction="right";//要遍历的方向
        int temp=1;
        while(left<=right&&top<=bottom){
            if(direction.equals("right")){
                for(int i=left;i<=right;i++){
                    arr[top][i]=temp++;
                }
                //从左遍历到右边界，准备向下遍历，上面已经遍历完一行了，所以top++
                top++;
                direction="bottom";
            }else if(direction.equals("bottom")){
                for(int i=top;i<=bottom;i++){
                    arr[i][right]=temp++;
                }
                //从上遍历到下边界，准备向左遍历，右边已经遍历完一列了，所以right--
                right--;
                direction="left";
            }else if(direction.equals("left")){
                for(int i=right;i>=left;i--){
                    arr[bottom][i]=temp++;
                }
                //从右遍历到左边界，准备向上遍历，下面已经遍历完一行了，所以bottom--
                bottom--;
                direction="top";
            }else if(direction.equals("top")){
                for(int i=bottom;i>=top;i--){
                    arr[i][left]=temp++;
                }
                //从下遍历到上边界，准备向右遍历，左边已经遍历完一列了，所以left++
                left++;
                direction="right";
            }
        }
        return arr;
    }
}
