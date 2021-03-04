package com.example.demo.condition;

import com.example.demo.annotation.Filter_Range;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Terms;
import com.example.demo.enums.RangeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkBillCondition implements Serializable {

    @Filter_Term(fieldName = "mainid")
    private String mainid;

    @Filter_Range(fieldName = "create_time",type= RangeTypeEnum.GTE)
    private String create_time_start;

    @Filter_Range(fieldName = "create_time",type= RangeTypeEnum.LTE)
    private String create_time_end;

    @Filter_Terms(fieldName = "status",split = ",")
    private List<String> statusList;

    @Filter_Term(fieldName = "create_time")
    private String create_time;

    @Filter_Term(fieldName = "create_name")
    private String create_name;

    @Filter_Term(fieldName = "originate_clue_type")
    private String originate_clue_type;

    @Filter_Term(fieldName = "originate_clue_value")
    private String originate_clue_value;

    @Filter_Term(fieldName = "work_order_type")
    private String work_order_type;

    @Filter_Term(fieldName = "status")
    private String status;

    @Filter_Term(fieldName = "process_dept_id_level1")
    private String process_dept_id_level1;

    @Filter_Term(fieldName = "process_dept_id_level2")
    private String process_dept_id_level2;

    @Filter_Term(fieldName = "process_dept_id_level3")
    private String process_dept_id_level3;

    @Filter_Term(fieldName = "process_dept_name_level1")
    private String process_dept_name_level1;

    @Filter_Term(fieldName = "process_dept_name_level2")
    private String process_dept_name_level2;

    @Filter_Term(fieldName = "process_dept_name_level3")
    private String process_dept_name_level3;

    @Filter_Term(fieldName = "biztype_first_id")
    private String biztype_first_id;

    @Filter_Term(fieldName = "biztype_second_id")
    private String biztype_second_id;

    @Filter_Term(fieldName = "biztype_first_name")
    private String biztype_first_name;

    @Filter_Term(fieldName = "biztype_second_name")
    private String biztype_second_name;

    @Filter_Term(fieldName = "locate_type")
    private String locate_type;

    @Filter_Term(fieldName = "locate_attribute_id")
    private String locate_attribute_id;

    @Filter_Term(fieldName = "branches_name")
    private String branches_name;

    @Filter_Term(fieldName = "upgrade_status")
    private String upgrade_status;

    @Filter_Term(fieldName = "oper_time")
    private Long oper_time;

    /**
     * 目的网点
     */
    @Filter_Terms(fieldName = "locate_attribute_id", split = ",")
    private List<String> targetNetworkCode;

    private String groupField;
    private String cardinality;
}
