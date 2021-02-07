package com.example.demo.listadd;

import java.util.Stack;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/3 9:25
 * 链表相加
 * 题目描述
 * 假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
 * 给定两个这种链表，请生成代表两个整数相加值的结果链表。
 * 例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0
 *
 * 利用栈结构求解：
 * 1、 将两个链表分别从左到右遍历，遍历过程中将值压栈，这样就生成了两个链表节点的逆序栈s1 和 s2。
 *
 * 2、将s1和s2同步弹出，这样相当于两个链表从低位到高位一次弹出，在这个过程中生成相加链表即可，同时关注下是否有进位。
 *
 * 当s1 和 s2都为空时，还要关注一下进位信息是否为1，如果为1 还要生成一个节点值为1的新节点
 *
 * 最后返回新生成的结果链表即可
 */
public class ListAddition2 {
    public  class Node{

        public int value;
        public Node next;

        public Node(int data){
            this.value=data;
        }

    }


    public Node addLists1(Node head1 ,Node head2){

        Stack<Integer> s1= new Stack<Integer>();
        Stack<Integer> s2 =new Stack<Integer>();
        while(head1!= null){
            s1.push(head1.value);
            head1=head1.next;
        }
        while(head1!=null){
            s2.push(head2.value);
            head2=head2.next;
        }

        int ca=0;
        int n1=0;
        int n2=0;
        int n=0;
        Node node = null;
        Node pre = null;

        while(!s1.isEmpty() || !s2.isEmpty()){

            n1 = s1.isEmpty() ? 0:s1.pop();
            n2 = s2.isEmpty() ? 0 :s2.pop();

            n=n1+n2+ca;
            pre=node;

            node=new Node(n%10);
            node.next=pre;
            ca=n/10;

        }

        if(ca==1){
            pre=node;
            node=new Node(1);
            node.next=pre;
        }

        return node;

    }
}
