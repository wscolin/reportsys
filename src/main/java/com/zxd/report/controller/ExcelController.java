package com.zxd.report.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;
import com.zxd.report.service.ExcelService;
import com.zxd.report.service.StUserService;
import com.zxd.util.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @RequestMapping(value = "/dwloadmb", method = RequestMethod.GET)
    @SystemControllerLog(description = "excel收入-支出模板下载",params = 0)
    public ResponseEntity<byte[]> dwloadmb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 下载文件名
        String fileName = "收入-支出表导入模板.xlsx";
        // 页面下载设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gbk"),"iso-8859-1"));
        // 项目路径
        String path = request.getRealPath("/");
        ResponseEntity<byte[]> bEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path+"plugins/templateImport/收入-支出表导入模板.xlsx")),headers, HttpStatus.OK);
        return bEntity;
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
        String date = request.getParameter("date");
        //删除对应时间的收入-支出数据
        stExcelMapper.deleteincome(date);
        stExcelMapper.deletedisburse(date);
        try {
            //新增收入表
            ImportParams in_param = new ImportParams();
            in_param.setSheetNum(1);
            in_param.setStartSheetIndex(0);
            in_param.setTitleRows(1);
            List<ImIcome> income_list = ExcelImportUtil.importExcel(file.getInputStream(), ImIcome.class, in_param);
            String type = "";
            for(ImIcome imIcome:income_list){
                imIcome.setYEAR(date);
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
            //新增支出表
            ImportParams zc_param = new ImportParams();
            String zc_type = "";
            zc_param.setStartSheetIndex(1);
            zc_param.setTitleRows(1);
            List<ImIcome> zc_list = ExcelImportUtil.importExcel(file.getInputStream(), ImIcome.class, zc_param);
            for(ImIcome zcIcome:zc_list){
                zcIcome.setYEAR(date);
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
        String date = request.getParameter("date");
        try {
            List<Min_11> min_11List_list = excelService.exportExcel(date);
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
     *
     */



    /**
     * 根据模板导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/excelExportByTemplate", method = RequestMethod.GET)
    public  ResponseEntity<byte[]>  excelExportByTemplate(HttpServletRequest request,HttpServletResponse response)throws Exception{
        try {
            //2019年5月报全市公式版
            String path =  request.getServletContext().getRealPath("/");
            String date = request.getParameter("date");
            String fileName = date+"月报全市报表";
            /**
             * 11民
             */
            List<Map> min_11List  = excelService.exportExcel_Min11(date);
            /**
             * 13通报
             */
            List<Map> tongBao_13List  = excelService.exportExcel_Tb13(date);
            HashMap map =  new HashMap();
            map.put("list_11min",min_11List);
            map.put("list_13tongbao",tongBao_13List);
            return excelExport(map,path,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/downexcel", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downexcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 下载文件名
        String fileName = "11民生.xlsx";
        // 页面下载设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gbk"),"iso-8859-1"));
        // 项目路径
        String path = request.getRealPath("/");
        ResponseEntity<byte[]> bEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path+"excelExport/11民生.xlsx")),headers, HttpStatus.OK);
        return bEntity;
    }

    /**
     *  导出excel
     * @param map
     *
     * @throws IOException
     */
    private  ResponseEntity<byte[]>  excelExport(Map map,String path,String fileName) throws IOException {
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setScanAllsheet(true);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        templateExportParams.setTemplateUrl(path+"/templateExport/导出_模板.xlsx");
        String[] sheetNameArray = {"11民生","13通报"};
        templateExportParams.setSheetName(sheetNameArray);
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams,map);
        File savefile = new File(path+"/excelExport/"+fileName+".xlsx");
        FileOutputStream fos = new FileOutputStream(savefile);
        workbook.write(fos);
        fos.close();
        workbook.close();
        return   download(savefile);
    }

    /**
     * 下载
     * @param file
     * @return
     * @throws IOException
     */
    public ResponseEntity<byte[]> download(File file) throws IOException {
        // 需要下载的文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("gbk"),"iso-8859-1"));
        ResponseEntity<byte[]> entity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
        file.delete();
        return entity;

    }


}
