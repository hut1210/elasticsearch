package com.example.demo.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.exception.BusinessExceptionHelper;
import com.example.demo.param.ImportDataDto;
import com.example.demo.util.ExcelUtil;
import com.example.demo.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:17
 */
@Slf4j
public abstract class UploadDataListener<T> implements ReadListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private List<List<Object>> failDataList;

    private ImportDataDto importDataDto;

    private Integer successCount;

    private Boolean isOverCount = true;

    private Class<T> aClass;

    private List<Field> valueFieldList;

    private Map<Integer, List<ReadCellData>> readCellDataMap = new LinkedHashMap<>();

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        String lastValue = null;
        for (Map.Entry<Integer, ReadCellData<?>> entry: headMap.entrySet()) {
            String value = entry.getValue().getStringValue();
            if(Objects.isNull(value)){
                entry.getValue().setStringValue(lastValue);
            }
            StreamUtil.toGroupByOrder(readCellDataMap, entry.getValue(), entry.getKey());
            lastValue = entry.getValue().getStringValue();
        }
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param importDataDto
     */
    public UploadDataListener(ImportDataDto importDataDto) {
        this.importDataDto = importDataDto;
        this.failDataList = new ArrayList<>();
        this.successCount = 0;
        this.aClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.valueFieldList = ExcelUtil.getValueFieldList(aClass);
    }

    public UploadDataListener() {
        this.aClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.valueFieldList = ExcelUtil.getValueFieldList(aClass);
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        validRowCount(context);
        //数据校验
        try {
            validData(data);
            //数据落库
            saveDataByCache(data);
        } catch (Exception e) {
            addExceptionData(data, e.getMessage());
            log.error(e.getMessage(), e);
        }
    }

    private void validRowCount(AnalysisContext context) {
        if (!isOverCount) {
            return;
        }
        Integer rowCountLimit = importDataDto.getRowCountLimit();
        if (Objects.isNull(rowCountLimit)) {
            isOverCount = false;
            return;
        }
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        Integer rowCount = readSheetHolder.getApproximateTotalRowNumber() - readSheetHolder.getHeadRowNumber();
        BusinessExceptionHelper.checkArgument(rowCount <= rowCountLimit,
                MessageFormat.format("导入数据有效行数【{0}】,超过限制行数【{1}】，请调整后重新导入！", rowCount, rowCountLimit));
        isOverCount = false;
    }

    private void saveDataByCache(T data) {
        cachedDataList.add(data);
        if (cachedDataList.size() < BATCH_COUNT) {
            return;
        }
        // 达到BATCH_COUNT，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        saveDataAndExceptionRecord();
        //存储完成清理List
        cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    }

    /**
     * 解析完成后调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 确保最后遗留的数据也存储到数据库
        if (!CollectionUtils.isEmpty(cachedDataList)) {
            saveDataAndExceptionRecord();
        }

        if(needHeadListFlag()){
            importDataDto.setHeadList(getHeadList(context));
        }

        if (failDataList.size() > 0) {
            importDataDto.setFailNum(failDataList.size());
            importDataDto.setFailMessageExcelUrl(createExceptionRecordExcel(context));
            if(needBackFailDataFlag()){
                importDataDto.setFailDataList(failDataList);
            }
        }
        importDataDto.setTotalNum(successCount + importDataDto.getFailNum());
        importDataDto.setSuccessNum(successCount);
        log.info("所有数据解析完成！");
    }

    private List<List<String>> getHeadList(AnalysisContext context){
        List<List<String>> headList = new ArrayList<>();
        List<String> exceptionList = new ArrayList<>();
        for (int i = 0; i < context.readSheetHolder().getHeadRowNumber(); i++) {
            exceptionList.add("异常信息");
        }
        headList.add(exceptionList);
        for (List<ReadCellData> readCellDataList : readCellDataMap.values()) {
            List<String> list = new ArrayList<>();
            for (ReadCellData readCellData : readCellDataList) {
                list.add(readCellData.getStringValue());
            }
            headList.add(list);
        }
        return headList;
    }

    /**
     * @Description: 保存数据并记录异常
     * @Author: hanpeng
     * @Date: 22/02/2022 09:52
     */
    private void saveDataAndExceptionRecord() {
        try {
            saveData(cachedDataList);
            successCount += cachedDataList.size();
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.length() > 200) {
                message = message.substring(0, 200);
            }
            for (T data : cachedDataList) {
                addExceptionData(data, message);
            }
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @Description: 生成异常信息的excel并上传到对象存储
     * @Author: hanpeng
     * @Date: 22/02/2022 09:56
     * @Param []
     * @Return java.lang.String
     * @param context
     */
    private String createExceptionRecordExcel(AnalysisContext context) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        List<List<String>> currentHeadList = importDataDto.getHeadList();
        if(CollectionUtils.isEmpty(currentHeadList)){
            currentHeadList = getHeadList(context);
        }
        EasyExcel.write(os).sheet("模板").head(currentHeadList)
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .doWrite(failDataList);
        //return JfsUtil.save(Base64.getEncoder().encodeToString(os.toByteArray()));
        return null;
    }

    public boolean needBackFailDataFlag(){
        return false;
    }

    public boolean needHeadListFlag(){
        return false;
    }

    private void addExceptionData(T data, String exceptionMessage) {
        List<Object> valueList = new ArrayList<>();
        valueList.add(exceptionMessage);
        for (Field field : valueFieldList) {
            field.setAccessible(true);
            try {
                valueList.add(field.get(data));
            } catch (Exception e) {
                log.error("获取字段"+field.getName()+"异常，"+e.getMessage(), e);
            }
        }
        failDataList.add(valueList);
    }

    /**
     * 数据校验,返回值为null则为校验成功，否则为失败，内容是失败信息
     *
     * @param data
     */
    public abstract void validData(T data);

    /**
     * 存储数据库
     *
     * @param cachedDataList
     */
    public void saveData(List<T> cachedDataList){

    }
}
