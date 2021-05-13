package com.example.demo.service;

import com.example.demo.domain.WaybillWide;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/8 15:33
 */
public interface WaybillWideService {
    void saveBatch(List<WaybillWide> waybillWideList);
}
