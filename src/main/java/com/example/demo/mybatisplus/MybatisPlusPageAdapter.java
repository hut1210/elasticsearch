package com.example.demo.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.demo.page.Page;
import com.example.demo.page.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:34
 */
public class MybatisPlusPageAdapter<T> implements IPage<T> {

    private Page innerPage;

    private static final Integer MAX_LIST_SIZE = 5000;

    public MybatisPlusPageAdapter(Page page){
        this.innerPage = page;
    }

    @Override
    public List<OrderItem> orders() {
        List<OrderItem> orderItemList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(this.innerPage.getSorts())){
            for(Sort sort : (List<Sort>)this.innerPage.getSorts()) {
                if(sort.isAsc()){
                    orderItemList.add(OrderItem.asc(sort.toUnderScoreCaseField()));
                }else{
                    orderItemList.add(OrderItem.desc(sort.toUnderScoreCaseField()));
                }
            }
        }
        return orderItemList;
    }

    @Override
    public List<T> getRecords() {
        return this.innerPage.getRows();
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.innerPage.setRows(records);
        return this;
    }

    @Override
    public long getTotal() {
        return this.innerPage.getTotal();
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.innerPage.setTotal((int)total);
        return this;
    }

    @Override
    public long getSize() {
        return this.innerPage.getPageSize();
    }

    @Override
    public IPage<T> setSize(long size) {
        this.innerPage.setPageSize((int)size);
        return this;
    }

    @Override
    public long getCurrent() {
        return this.innerPage.getPageIndex();
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.innerPage.setPageIndex((int)current);
        return this;
    }

    public Page toPage(){
        return this.innerPage;
    }

    public static MybatisPlusPageAdapter newInstance(Page page){
        return new MybatisPlusPageAdapter(page);
    }

    public static MybatisPlusPageAdapter newMaxInstance(){
        return new MybatisPlusPageAdapter(new Page(1,MAX_LIST_SIZE));
    }

    public void CheckMaxListSize(){
        if(this.getTotal() > MAX_LIST_SIZE){
            throw new IllegalArgumentException("MAX_LIST_SIZE more then 5000!");
        }
    }
}
