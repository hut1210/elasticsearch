package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.util.UserLog;
import com.xkzhangsan.xkbeancomparator.CompareResult;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 11:05
 */
public class UserLogTest {

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("aa");
        u1.setAge(23);

        User u2 = new User();
        u2.setId(1L);
        u2.setName("aa2");
        u2.setAge(24);
        CompareResult compareResult = UserLog.getCompareResult(u1, u2);
        if (compareResult.isChanged()) {
            System.out.println(compareResult.getChangeContent());
        }
    }
}
