package com.example.demo.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:35
 */
public class Page<T> implements Serializable {

    public static final int SUCCESS_CODE = 200; //成功

    /**
     * 页数大小
     */
    public Integer pageSize = 10;
    /**
     * 当前页
     */
    private Integer pageIndex = 1;

    /**
     * 排序字段，支持多字段排序
     */
    private List<Sort> sorts  = new ArrayList<>();

    /**
     * 总页数
     */
    private Integer pages = 0;
    /**
     * 总记录数
     */
    private Integer total = 0;
    /**
     * 数据内容
     */
    public List<T> rows;
    /**
     * 仓库权限列表
     */
    public Object dynamicLotattr;

    /**
     * 仓库权限列表
     */
    public List<String> warehousePermissionList;

    /**
     * 调用结果码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 子错误码
     */
    private Integer subCode;

    /**
     * 子错误信息
     */
    private String subMsg;



    public int getPageIndex() {
        return pageIndex;
    }

    public Page() {
        this.code = SUCCESS_CODE;
        this.msg = "成功";

    }

    public Page(int pageIndex,int pageSize) {
        this.code = SUCCESS_CODE;
        this.msg = "成功";
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }


    public Object getDynamicLotattr() {
        return dynamicLotattr;
    }

    public void setDynamicLotattr(Object dynamicLotattr) {
        this.dynamicLotattr = dynamicLotattr;
    }

    public List<String> getWarehousePermissionList() {
        return warehousePermissionList;
    }

    public void setWarehousePermissionList(List<String> warehousePermissionList) {
        this.warehousePermissionList = warehousePermissionList;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSubCode() {
        return subCode;
    }

    public void setSubCode(Integer subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public Page<T> addSorts(Sort... items) {
        sorts.addAll(Arrays.asList(items));
        return this;
    }

    public Page<T> addSorts(List<Sort> items) {
        sorts.addAll(items);
        return this;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public static Page buildPage(int pageIndex, int pageSize){
        Page comPage = new Page();
        comPage.setPageIndex(pageIndex);
        comPage.setPageSize(pageSize);
        return comPage;
    }

    public PageRequest toRequest(T req){
        PageRequest<T> pageRequest = new PageRequest<T>();
        pageRequest.setPage(this);
        pageRequest.setData(req);
        return pageRequest;
    }

}
