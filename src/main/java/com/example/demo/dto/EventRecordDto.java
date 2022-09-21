package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hut
 * @date 2022/9/21 11:27 上午
 */
@Data
public class EventRecordDto implements Serializable{
    private Integer pageIndex;
    private Integer pageSize;
}
