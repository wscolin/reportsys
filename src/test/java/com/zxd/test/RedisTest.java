package com.zxd.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testSet(){
        this.redisTemplate.opsForValue().set("key", "陈国飞");
    }
    @Test
    public void fe_map() throws Exception {
        TemplateExportParams params = new TemplateExportParams();
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
       // params.setHeadingRows();
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
       // params.setSheetName("数据统计");
        TemplateExportParams params1 = new TemplateExportParams();
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        // params.setHeadingRows();
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        //params.setSheetName("数据统计");
        // 获取报表内容
        // 因为表数据是根据存储过程来实现的，不同的报表有不同的配置，
        // 所以使用Map<String,Object>格式来接收
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> reportBodyList =  new ArrayList<>();
        Map<String,Object> values = new HashMap<String,Object>();
        values.put("c1","总计");
        values.put("c1",10);
        values.put("c3",5);
        values.put("c4",8);
        values.put("c5",5);
        values.put("c6",8);
        values.put("c7",6);
        values.put("c8",3);
        reportBodyList.add(values);
        Map<String,Object> values1 = new HashMap<String,Object>();
        values.put("c1","总计");
        values.put("c1",10);
        values.put("c3",5);
        values.put("c4",8);
        values.put("c5",5);
        values.put("c6",8);
        values.put("c7",6);
        values.put("c8",32);
        reportBodyList.add(values1);
        data.put("list", reportBodyList);
        // 获取模板文件路径
        // 这里有个很坑的地方，就是easypoi的API只能接收文件路径，无法读取文件流
        // 设置模板路径
        params.setTemplateUrl("C:\\Users\\Administrator\\Desktop\\报表\\模板导出.xlsx");
        // 获取workbook
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);
     //   Workbook workbook = ExcelExportUtil.exportExcel(params, data);



        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/模板导出.xlsx");
        workbook.write(fos);

        fos.close();
        workbook.close();
    }
}
