package com.example.demo.condition;

import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Terms;
import com.example.demo.annotation.mustnot.Filter_Not_Term;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 14:08
 */
@Data
public class WarehouseCondition implements Serializable {

    @Filter_Term(fieldName = "enterprise_id")
    private String enterpriseId;
    /**
     * 仓库编号
     */
    @Filter_Term(fieldName = "warehouse_no")
    private String warehouseNo;

    @Filter_Terms(fieldName = "warehouse_no", split = ",")
    private List<String> warehouseNos;

    @Filter_Not_Term(fieldName = "odo_status")
    private String odoNotInStatus;

    @Filter_Term(fieldName = "is_delete")
    private Byte isDelete;

    @Filter_Term(fieldName = "odo_status")
    private String odoInStatus;
}
