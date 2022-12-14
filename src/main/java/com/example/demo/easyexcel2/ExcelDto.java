package com.example.demo.easyexcel2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hut
 * @date 2022/12/13 9:38 下午
 */
public class ExcelDto {

    private List<List<String>> head        = new ArrayList<>();//表头
    private List<List<Object>> tmpContent  = new ArrayList<>();//临时数据
    private List<List<Object>> content     = new ArrayList<>();//保存全量数据  高并发或者数据量较大时不建议使用  [如需开启  重写 enableContent()方法 返回true]
    private boolean            isHaveError;//默认false
    private int                count       = 3000;//三千条数据 提交一次
    private String             url;//文件上传后的地址

    public List<List<String>> getHead() {
        return head;
    }
    public void setHead(List<List<String>> head) {
        this.head = head;
    }
    public List<List<Object>> getTmpContent() {
        return tmpContent;
    }
    public void setTmpContent(List<List<Object>> tmpContent) {
        this.tmpContent = tmpContent;
    }
    public boolean isHaveError() {
        return isHaveError;
    }
    public void setHaveError(boolean haveError) {
        isHaveError = haveError;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<List<Object>> getContent() {
        return content;
    }
    public void setContent(List<List<Object>> content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "ExcelDto{" +
                "head=" + head +
                ", tmpContent=" + tmpContent +
                ", saveContent=" + content +
                ", isHaveError=" + isHaveError +
                ", count=" + count +
                ", url='" + url + '\'' +
                '}';
    }
}
