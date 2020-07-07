package com.zxd.report.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;
import com.zxd.report.service.ExcelService;
import com.zxd.report.service.StUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: gas_bank
 * @description:
 * @author:
 * @create: 2020-06-21 17:46
 **/
@Controller
@RequestMapping(value = "/excel")
public class ExcelController {
    @Autowired
    private StUserService stUserService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private StExcelMapper stExcelMapper;
    @RequestMapping("/import")
    public String excelIm(Map<String,String> map){
        return  "excel/excelimport";
    }
    /**
     * 导入excel
     * @param request
     * @param file 模板文件
     * @return
     */
    @RequestMapping("/importfile")
    @ResponseBody
    @RequiresPermissions("st_user_import")
   // @SystemControllerLog(description = "excel导入",params = 0)
    public Object importfile(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file){
        try {
            ImportParams in_param = new ImportParams();
            in_param.setSheetNum(1);
            in_param.setTitleRows(1);
            List<ImIcome> income_list = ExcelImportUtil.importExcel(file.getInputStream(), ImIcome.class, in_param);
            String type = "";
            for(ImIcome imIcome:income_list){
                if(imIcome.getKMMC() != null ) {
                    if (imIcome.getKMMC().contains("一般公共预算收入合计")) {
                        type = "1";
                    } else if (imIcome.getKMMC().contains("国有资本经营预算收入合计")) {
                        type = "2";
                    } else if (imIcome.getKMMC().contains("国有资本经营预算收入合计")) {
                        type = "3";
                    } else if (imIcome.getKMMC().contains("社会保险基金预算收入合计")) {
                        type = "4";
                    }
                }
                imIcome.setTYPE(type);
                //stExcelMapper.insertSelective(imIcome);

            }
            stExcelMapper.insertSelective_batch_income(income_list);
            ImportParams zc_param = new ImportParams();
            String zc_type = "";
            zc_param.setStartSheetIndex(1);
            zc_param.setTitleRows(1);
            List<ImIcome> zc_list = ExcelImportUtil.importExcel(file.getInputStream(), ImIcome.class, zc_param);
            for(ImIcome zcIcome:zc_list){
               //
                if(zcIcome.getKMMC() != null ){
                    if(zcIcome.getKMMC().contains("一般公共预算支出合计")){
                        zc_type="1";

                    }else if(zcIcome.getKMMC().contains("政府性基金预算支出合计")){
                        zc_type="2";
                    }else if(zcIcome.getKMMC().contains("国有资本经营预算支出合计")){
                        zc_type="3";
                    }else if(zcIcome.getKMMC().contains("社会保险基金预算支出合计")){
                        zc_type="4";
                    }
                }


                zcIcome.setTYPE(zc_type);
                //stExcelMapper.insertSelective(zcIcome);
            }

            stExcelMapper.insertSelective_batch_disburse(zc_list);
           return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 导入excel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exportExcel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Min_11> min_11List_list = excelService.exportExcel(null);
       /*     Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("表十二：抚州市2019年1-5月民生工程支出表","11民生"),
                    Min_11.class, min_11List_list);*/


            ExportParams exportParams1 = new ExportParams();
            // 设置sheet得名称
            exportParams1.setTitle("表十二：抚州市2019年1-5月民生工程支出表");
            exportParams1.setSheetName("11民生");
            ExportParams exportParams2 = new ExportParams();
            exportParams2.setTitle("抚州市财政收入分县区执行情况及排名表（2020年5月）");
            exportParams2.setSheetName("13通报");
            // 创建sheet1使用得map
            Map<String, Object> min_11_Map = new HashMap<>();
            // title的参数为ExportParams类型
            min_11_Map.put("title", exportParams1);
            // 模版导出对应得实体类型
            min_11_Map.put("entity", Min_11.class);
            // sheet中要填充得数据
            min_11_Map.put("data", min_11List_list);
            // 创建sheet2使用得map
            Map<String, Object> tongBao_13_Map = new HashMap<>();
            tongBao_13_Map.put("title", exportParams2);
            tongBao_13_Map.put("entity", TongBao_13.class);
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            sheetsList.add(min_11_Map);
            sheetsList.add(tongBao_13_Map);
            Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
            FileOutputStream fos = new FileOutputStream("f:\\emp.xls");
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据模板导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/excelExportByTemplate")
    public String excelExportByTemplate(HttpServletRequest request,HttpServletResponse response)throws Exception{
     /*    List<Min_11> reportBodyList =new ArrayList<>();
       reportBodyList = excelService.exportExcel(null);
      for(int i = 0 ;i < 2; i++){
           Map<String,Object> values = new HashMap<String,Object>();
           values.put("kmmc","sss");
           values.put("kmbm","1111");
           values.put("a1","总计");
           values.put("a2",10+i);
           values.put("a3",5+i);
           values.put("a4",8);
           values.put("a5",5);
           values.put("a6",8);
           values.put("a7",6);
           values.put("a8",32+i);
           reportBodyList.add(values);
       }*/

       /*  Min_11 values = new Min_11();
        values.setKmmc("单独");
        values.setKmbm(11);
        values.setA1("12");
        values.setA2("13");
        values.setA3("14");
        values.setA4("15");
        values.setA5("15");
        values.setA6("15");
        Min_11 values1 = new Min_11();
        values1.setKmmc("民生工程");
        values1.setKmbm(22);
        values1.setA1("11212");
        values1.setA2("12");
        values1.setA3("1112");
        values1.setA4("2");
        values1.setA5("1");
        values1.setA6("-78%");

        TemplateExportParams params = new TemplateExportParams();
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        params.setHeadingRows(2);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        params.setSheetName("11民生");
        params.setTemplateUrl("C:\\Users\\Administrator\\Desktop\\报表\\templateExport\\11民生_模板.xlsx");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list",reportBodyList);
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/11民生.xlsx");
        workbook.write(fos);
        fos.close();*/
        /**
         * 11民
         */
        String year = request.getParameter("year");
        List<Map> min_11List  = excelService.exportExcel_Min11(year);
        String filename = "11民生";
        TemplateExportParams min_11_params = new TemplateExportParams();
        // 标题开始行
        min_11_params.setHeadingStartRow(0);
        // 标题行数
        min_11_params.setHeadingRows(2);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        min_11_params.setSheetName(filename);
        min_11_params.setTemplateUrl("C:\\Users\\Administrator\\Desktop\\报表\\templateExport\\"+filename+"_模板.xlsx");
        Map<String, Object> min_11_map = new HashMap<String, Object>();
        min_11_map.put("list",min_11List);
        excelExport(min_11_params,min_11_map,filename);

        /**
         * 13通报
         */
        List<Map> tongBao_13List  = excelService.exportExcel_Tb13(year);
        filename = "13通报";
        TemplateExportParams tongBao_13_params = new TemplateExportParams();
        // 标题开始行
        tongBao_13_params.setHeadingStartRow(0);
        // 标题行数
        tongBao_13_params.setHeadingRows(2);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        tongBao_13_params.setSheetName(filename);
        tongBao_13_params.setTemplateUrl("C:\\Users\\Administrator\\Desktop\\报表\\templateExport\\"+filename+"_模板.xlsx");
        Map<String, Object> map_tongbao13 = new HashMap<String, Object>();
        map_tongbao13.put("list",tongBao_13List);
        excelExport(tongBao_13_params,map_tongbao13,filename);
        return  null;
    }

    /**
     *  导出excel
     * @param params
     * @param map
     * @param fileName
     * @throws IOException
     */
    private void excelExport(TemplateExportParams params,Map map,String fileName) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/"+fileName+".xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
