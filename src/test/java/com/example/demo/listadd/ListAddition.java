package com.example.demo.listadd;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/3 9:24
 * 链表相加
 * 题目描述
 * 假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
 * 给定两个这种链表，请生成代表两个整数相加值的结果链表。
 * 例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0
 *
 * 解题思路：将两个链表反转，然后逐个节点的相加，注意进位标志。然后检查没算完的链表，注意进位标志。最后还是不要忘记进位标志。在构建结果时可以从后插入构建链表，这样构建后就是结果
 */
public class ListAddition {

    /**
     *
     * @param head1 ListNode类
     * @param head2 ListNode类
     * @return ListNode类
     */
    public ListNode addInList (ListNode head1, ListNode head2) {
        // write code here
        if (head1 == null) return head2;
        if (head2 == null) return head1;
        ListNode node1 = reverse(head1);
        ListNode node2 = reverse(head2);
        ListNode res = new ListNode(-1);
        int index = 0;    // 进位数
        while (node1 != null && node2 != null) {
            // 注意要加上进位标记
            int sum = node1.val + node2.val + index;
            if (sum >= 10) {
                index = sum / 10;
            } else {    // 没有进位要清除进位标记
                index = 0;
            }
            // 从后插入节点
            insertNode(res, sum % 10);

            node1 = node1.next;
            node2 = node2.next;
        }
        // 当两个链表长度不一致时
        // 这里的index要保持更新，不然会出错
        index = whenNodeNotNull(res, node1, index);
        index = whenNodeNotNull(res, node2, index);
        // 如果最后还有进位
        if (index != 0) {
            insertNode(res, index);
        }
        return res.next;
    }
    /**
     * 反转链表
     * @param head 被反转的链表
     */
    public ListNode reverse(ListNode head) {
        ListNode res = null;
        while (head != null) {
            ListNode p = head.next;
            head.next = res;
            res = head;
            head = p;
        }
        return res;
    }

    /**
     * 计算剩余链表
     * @param res 存储结果链表
     * @param target 被计算的链表
     * @param index 进位标志
     */
    public int whenNodeNotNull(ListNode res, ListNode target, int index) {
        while (target != null) {
            int sum = target.val + index;
            if (sum >= 10) {
                index = sum / 10;
            } else {
                index = 0;
            }

            insertNode(res, sum % 10);

            target = target.next;
        }
        return index;
    }

    /**
     * 从后向前插入节点
     * @param res 存储结果链表
     * @param val 节点数值
     */
    public void insertNode(ListNode res, int val) {
        ListNode cur = new ListNode(val);
        cur.next = res.next;
        res.next = cur;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(937);
        ListNode listNode2 = new ListNode(63);
        ListAddition listAddition =new ListAddition();
        ListNode listNode = listAddition.addInList(listNode1, listNode2);
        System.out.println(listNode);
    }
}
