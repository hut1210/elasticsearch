package com.example.demo.param;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:25
 */
@Data
public class ImportDataDto {

    /**
     * 导入总数
     */
    private Integer totalNum = 0;

    /**
     * "成功数
     */
    private Integer successNum = 0;

    /**
     * "失败数
     */
    private Integer failNum = 0;

    /**
     * 失败原因说明excel地址
     */
    private String failMessageExcelUrl;

    /**
     * "开始时间
     */
    private LocalDateTime startTime;

    /**
     * "结束时间
     */
    private LocalDateTime endTime;

    /**
     * 导入最大行数限制
     */
    private Integer rowCountLimit;

    /**
     * 异常数据
     */
    private List failDataList = new ArrayList();

    /**
     * 异常数据表头列表
     */
    private List<List<String>> headList;
}

