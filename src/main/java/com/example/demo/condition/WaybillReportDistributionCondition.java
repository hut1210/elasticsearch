package com.example.demo.condition;

import com.example.demo.annotation.Filter_Terms;
import com.example.demo.annotation.mustnot.Filter_Not_Terms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/20 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaybillReportDistributionCondition extends WaybillReportCondition {

    /**
     * 始发网点
     */
    @Filter_Not_Terms(fieldName = "pick_site_id",split = ",")
    private List<String> beginNetworkCode;
}
