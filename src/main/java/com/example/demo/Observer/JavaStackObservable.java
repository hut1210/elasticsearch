package com.example.demo.Observer;

import lombok.Getter;

import java.util.Observable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/13 15:31
 */
@Getter
public class JavaStackObservable extends Observable {

    private String article;

    /**
     * 发表文章
     * @param article
     */
    public void publish(String article){
        //发表文章
        this.article=article;
        //改变状态
        this.setChanged();
        //通知所有观察者
        this.notifyObservers();
    }
}
