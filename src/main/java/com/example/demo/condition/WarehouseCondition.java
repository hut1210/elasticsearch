package com.example.demo.condition;

import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.mustnot.Filter_Not_Term;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 14:08
 */
@Data
public class WarehouseCondition implements Serializable {

    @Filter_Not_Term(fieldName = "odo_status")
    private String odoNotInStatus;

    @Filter_Term(fieldName = "is_delete")
    private Byte isDelete;

    @Filter_Term(fieldName = "odo_status")
    private String odoInStatus;
}
