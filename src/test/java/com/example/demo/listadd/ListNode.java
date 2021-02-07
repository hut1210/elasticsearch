package com.example.demo.listadd;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/3 9:28
 */
@Data
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int data){
        this.val=data;
    }
}
