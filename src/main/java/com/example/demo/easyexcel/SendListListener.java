package com.example.demo.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hut
 * @date 2022/12/13 8:52 下午
 */
public class SendListListener extends AnalysisEventListenerAdapter<SendListExcel> {
    private List<SendListExcel> listExcels = new ArrayList<>();

    public SendListListener() {
        super();
        listExcels.clear();
        excelErrorMap.clear();
    }

    /**
     * 每一条数据解析都会调用
     */
    @Override
    public void invoke(SendListExcel sendListExcel, AnalysisContext analysisContext) {
        listExcels.add(sendListExcel);
    }

    /**
     * 所有数据解析完成都会调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        SendListExcel sle = null;
        boolean isMatch = true;
        for (int i = 0; i < listExcels.size(); i++) {
            sle = listExcels.get(i);
            isMatch = true;
            Integer accountCellIndex = EasyExcelUtil.getCellIndex(sle, "account");
            if (accountCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getAccount())) {
                    setExcelErrorMaps(i, accountCellIndex, "账号不能为空！");
                }
            }
            Integer templateCodeCellIndex = EasyExcelUtil.getCellIndex(sle, "templateCode");
            if (templateCodeCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getTemplateCode())) {
                    setExcelErrorMaps(i, templateCodeCellIndex, "模板编号不能为空！");
                }
            }
            Integer accountTypeCellIndex = EasyExcelUtil.getCellIndex(sle, "accountType");
            if (accountTypeCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getAccountType())) {
                    setExcelErrorMaps(i, accountTypeCellIndex, "类型不能为空！");
                } else {
                    if ("sms".equals(sle.getAccountType()) || "email".equals(sle.getAccountType()) || "wechat".equals(sle.getAccountType())) {
                        isMatch = false;
                    }
                    if (isMatch) {

                        setExcelErrorMaps(i, accountTypeCellIndex, "类型只允许：sms、email、wechat");
                    }
                }
            }
        }
    }

    public List<SendListExcel> getListExcels() {
        return listExcels;
    }
}
