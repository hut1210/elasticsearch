package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/23 16:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetWorkDto implements Serializable {

    /**
     * 网点编号
     */
    private String netWorkCode;
    /**
     * 网点名称
     */
    private String netWorkName;
    /**
     * 网点所属区域
     */
    private String networkArea;
    /**
     * 网点业务类型
     */
    private String networkBusinessType;
}