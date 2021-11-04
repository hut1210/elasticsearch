package com.example.demo.menu.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/10/20 17:25
 */
@Data
public class Menu {
    private Long id;
    private String name;
    private String code;
    private String url;
    private Long parentId;
    private int level;
    private int seq;
    private List<Menu> children;
    private int type;
}
