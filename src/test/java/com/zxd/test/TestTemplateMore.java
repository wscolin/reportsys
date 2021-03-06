package com.zxd.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.zxd.report.model.Min_11;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestTemplateMore {
    @Test
    public void testTempMore(){
        List<Min_11>  liststu =  new ArrayList();
        for(int i =0;i<2;i++){
            Min_11 min_11 = new Min_11();
            min_11.setKmmc("住房公积金"+i);
            min_11.setKmbm(11);
            min_11.setA1("1"+i);
            min_11.setA2("2"+i);
            min_11.setA3("3"+i);
            min_11.setA4("4"+i);
            min_11.setA5("5"+i);
            min_11.setA6("6"+i);
            liststu.add(min_11);
        }
        List<Map> list2 =  new ArrayList<>();
        for(int i =0;i<2;i++){
            Map map = new HashMap();
            map.put("a1","a1"+i);
            map.put("a2","a2"+i);
            map.put("a3","a3"+i);
            map.put("a4","a4"+i);
            map.put("a5","a5"+i);
            map.put("a6","a6"+i);
            list2.add(map);
        }
        HashMap map =  new HashMap();
        map.put("ss",liststu);
        map.put("aa",list2);
        TemplateExportParams params = new TemplateExportParams("C:/Users/Administrator/Desktop/报表/templateExport/导出_模板.xlsx",true);
        String[] sheetNameArray = {"11民生","13通报"} ;
        params.setSheetName(sheetNameArray);
        Workbook workbook = ExcelExportUtil.exportExcel(params,map);
        File savefile = new File("d:/excel/1.xlsx");
        try {
            FileOutputStream fos = new FileOutputStream(savefile);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (Exception e){

        }

    }
    @Test
    public void testPath()throws Exception{
        //第一种
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) path = new File("");
        System.out.println(path.getAbsolutePath());
        //第二种
        System.out.println(System.getProperty("user.dir"));
        //第三种
        String path1 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(URLDecoder.decode(path1, "utf-8"));
        //第四种
        String path2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path2);
        //第五种
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        System.out.println(jarF.getParentFile().toString());
        System.out.println( ResourceUtils.getFile("classpath:").getPath());;
    }

    public static void main(String[] args) {
        String a = "t.0";
        System.out.println(a.split("\\.")[0]);
    }

}
