package com.zxd.test;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.zxd.commonmodel.ImUser;
import com.zxd.report.model.ImIcome;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Testeasypoi {
    public static void main(String[] args) throws Exception{

        ImportParams importParams = new ImportParams();
        importParams.setSheetNum(1);
        importParams.setStartSheetIndex(0);
        importParams.setTitleRows(1);

        File file = new File("C:\\Users\\Administrator\\Desktop\\r\\13.xlsx");
        List list = ExcelImportUtil.importExcel(file,ImUser.class,importParams);
        System.out.println(list.size());

    }
}
