package com.example.demo.huiwen;

import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/2 21:38
 */
public class HuiWen5Test {
    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(System.in);
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            String string = st.sval;
            int max = 0;
            char[] a = string.toCharArray();
            for (int i = 0; i < string.length(); i++) {
                for (int j = 0; i + j < string.length() && i - j >= 0; j++) {
                    if (a[i - j] != a[i + j]) {
                        break;
                    }
                    if (2 * j + 1 > max) {
                        max = 2 * j + 1;
                    }
                }
                for (int j = 0; i + j + 1 < a.length && i - j >= 0; j++) {
                    if (a[i - j] != a[i + j + 1]) {
                        break;
                    }
                    if (j * 2 + 2 > max) {
                        max = j * 2 + 2;
                    }
                }
            }
            System.out.println(max);
        }
    }
}
