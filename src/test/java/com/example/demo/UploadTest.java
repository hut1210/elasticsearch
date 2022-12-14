package com.example.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.example.demo.listener.UploadDataListener;
import com.example.demo.param.ImportDataDto;
import org.junit.jupiter.api.Test;

/**
 * @author hut
 * @date 2022/12/11 9:46 下午
 */
public class UploadTest {

    @Test
    public void test(){
        ImportDataDto importDataDto = new ImportDataDto();
        UploadDataListener uploadDataListener = new UploadDataListener() {
            @Override
            public void validData(Object data) {

            }
        };
        uploadDataListener.saveData(null);
    }
}
