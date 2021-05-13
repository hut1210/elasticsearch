package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.WaybillWide;
import com.example.demo.mapper.WaybillWideMapper;
import com.example.demo.service.WaybillWideService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/8 14:37
 */
@Service("waybillWideService")
public class WaybillWideServiceImpl extends ServiceImpl<WaybillWideMapper, WaybillWide> implements WaybillWideService {
    @Override
    public void saveBatch(List<WaybillWide> waybillWideList) {
        super.saveOrUpdateBatch(waybillWideList);
    }
}
