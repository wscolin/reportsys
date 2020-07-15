package com.zxd.test;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.common.base.CharMatcher;
import com.zxd.commonmodel.ImUser;
import com.zxd.report.model.ImIcome;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testeasypoi {
    public static void main(String[] args) throws Exception{

        /*ImportParams importParams = new ImportParams();
        importParams.setSheetNum(1);
        importParams.setStartSheetIndex(0);
        importParams.setTitleRows(1);

        File file = new File("C:\\Users\\Administrator\\Desktop\\r\\13.xlsx");
        List list = ExcelImportUtil.importExcel(file,ImUser.class,importParams);
        System.out.println(list.size());*/
        File file = new File("./log");

        System.out.println( file.getCanonicalPath());
        System.out.println(System.getProperty("user.dir"));
        String str = "  string  ";

        str = str.trim();

        System.out.println(str);

        String str3 = replaceBlank("      　      国有企业增值税  ");
        System.out.println(str3);
    }
    public static String replaceBlank(String str) {
        if(null != str) {
            // String phone = "‭15800000000"; 该字符串实际的字符长度是 12, 其中15前面有一个看不见的字符, 既不是中文空格也不是英文空格.
            // System.out.println(TrimUtils.trimAnyBlank(phone).length());
            return CharMatcher.anyOf("\r\n\t \u00A0　‭").trimFrom(str);
        }
        return str;
    }

}
