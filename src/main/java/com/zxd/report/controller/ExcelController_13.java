package com.zxd.report.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.gson.Gson;
import com.zxd.commonmodel.Page;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;
import com.zxd.report.service.ExcelService;
import com.zxd.util.DateUtil;
import com.zxd.util.POIUtil;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/excel_13")
public class ExcelController_13 {
    private final static Logger logger= LoggerFactory.getLogger(ExcelController_13.class);

    @Autowired
    private ExcelService excelService;
    @Autowired
    private StExcelMapper stExcelMapper;
    @RequestMapping("/import")
    public String excelIm(Map<String,String> map){
        return  "excel/excelimport_13";
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
    @RequestMapping("/importfileBypoi")
    @ResponseBody
    @RequiresPermissions("st_user_import")
   // @SystemControllerLog(description = "excel导入",params = 0)
    public Object importfileBypoi(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        String[] keys = {"kmbm","kmmc","amt","sz","gxq","lcq","dxq","ncx","nfx","lcx","crx","yhx","lax","jxx","zxx","gcx"};
        List<Map>  income_list = POIUtil.readExcel(file,"收入",keys);
        List<Map>  zc_list = POIUtil.readExcel(file,"支出",keys);
        String date = request.getParameter("date");
        stExcelMapper.deleteincome(date);
        stExcelMapper.deletedisburse(date);
        stExcelMapper.deleteincome_area(date);
        stExcelMapper.deletedisburse_area(date);
        try {
            String type = "";
            for(Map map:income_list){
                map.put("year",date);
                if(map.get("kmmc") != null ) {
                    if (map.get("kmmc").toString().contains("一般公共预算收入合计")) {
                        type = "1";
                    } else if (map.get("kmmc").toString().contains("政府性基金预算收入合计")) {
                        type = "2";
                    } else if (map.get("kmmc").toString().contains("国有资本经营预算收入合计")) {
                        type = "3";
                    } else if (map.get("kmmc").toString().contains("社会保险基金预算收入合计")) {
                        type = "4";
                    }
                }
                map.put("type",type);
            }
            stExcelMapper.insertSelective_batch_income_map(income_list);
            //插入按地区收入表
            List<Map> list_area = stExcelMapper.selectBysql("select area_code,area_name,simple_name from t_area ");
            List<Map<String,String>> list_incode_area = new ArrayList<>();
            for(Map area:list_area) {
                getBasedateByArea(income_list, list_incode_area, area);
            }
            //新增地区基本收入数据
            stExcelMapper.insertSelective_batch_incomearea_map(list_incode_area);
            //新增支出表
            String zctype = null;
            for(Map zcIcome:zc_list){
                zcIcome.put("year",date);
                if(zcIcome.get("kmmc") != null ) {
                    if (zcIcome.get("kmmc").toString().contains("一般公共预算支出合计")) {
                        zctype = "1";
                    } else if (zcIcome.get("kmmc").toString().contains("政府性基金预算支出合计")) {
                        zctype = "2";
                    } else if (zcIcome.get("kmmc").toString().contains("国有资本经营预算支出合计")) {
                        zctype = "3";
                    } else if (zcIcome.get("kmmc").toString().contains("社会保险基金预算支出合计")) {
                        zctype = "4";
                    }
                }
                zcIcome.put("type",zctype);
            }
            stExcelMapper.insertSelective_batch_disburse_map(zc_list);
            List<Map<String,String>> list_zccode_area = new ArrayList<>();
            for(Map area:list_area) {
                getBasedateByArea(zc_list, list_zccode_area, area);
            }
            //新增地区基本支出数据
            stExcelMapper.insertSelective_batch_disbursearea_map(list_zccode_area);
            //自定义科目编码更新
            String sql= "update t_income a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm='';";
            stExcelMapper.excutesql(sql);
            sql= "update t_disburse a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm=''";
            stExcelMapper.excutesql(sql);
            sql= "update t_incomebyarea a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm='';";
            stExcelMapper.excutesql(sql);
            sql= "update t_disbursebyarea a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm=''";
            stExcelMapper.excutesql(sql);
           return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void getBasedateByArea(List<Map> income_list, List<Map<String, String>> list_incode_area, Map area) {
        for (Map m : income_list) {
            Iterator<Map.Entry<String,String>> iterator =(Iterator<Map.Entry<String,String>> ) m.entrySet().iterator();
            while (iterator.hasNext()){
               Map.Entry entry =  iterator.next();
               String key =(String) entry.getKey();
               String value = (String) entry.getValue();
               if(key.equals(area.get("simple_name"))){
                   Map<String,String> map_area_kmbm = new HashMap();
                   map_area_kmbm.put("area_name", area.get("area_name").toString());
                   map_area_kmbm.put("area_code", area.get("area_code").toString());
                   map_area_kmbm.put("amt",value);
                   map_area_kmbm.put("kmbm",m.get("kmbm")==null?null:m.get("kmbm").toString());
                   map_area_kmbm.put("kmmc",m.get("kmmc")==null?null:m.get("kmmc").toString());
                   map_area_kmbm.put("type",m.get("type")==null?null:m.get("type").toString());
                   map_area_kmbm.put("year",m.get("year")==null?null:m.get("year").toString());
                   list_incode_area.add(map_area_kmbm);
               }
            }
        }
    }



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
            //String path =  request.getServletContext().getRealPath("/");
            String path = System.getProperty("user.dir")+File.separator+"templateExport";
            String date = request.getParameter("date");
            String fileName = date+"月报全市报表";
            /**
             * 1总
             */
            List<Map>zong1_List  = excelService.exportExcel_1zong(date);
            /**
             * 02级
             */
            List<Map>ji02_list  = excelService.exportExcel_02ji(date);
            /**
             * 03部
             */
            List<Map>bu03_list  = excelService.exportExcel_03bu(date);
            /**
             * 04级
             */
            List<Map>jijin04_list  = excelService.exportExcel_04jijin(date);
            /**
             * 05税
             */
            List<Map>shui05_list  = excelService.exportExcel_05shui(date);
            /**
             * 06财
             */
            List<Map>cai06_list  = excelService.exportExcel_06cai(date);
            /**
             * 07税比
             */
            List<Map>shuibi07_list  = excelService.exportExcel_07shuibi(date);
            /**
             * 10支出
             */
            List<Map> zhichu10_list  = excelService.exportExcel_10zhichu(date);
            /**
             * 11民
             */
            List<Map> min_11List  = excelService.exportExcel_Min11(date);
            /**
             * 12省
             */

            List<Map> sheng_12List  = excelService.exportExcel_sheng12(date);
            /**
             * 13通报
             */
            List<Map> tongBao_13List  = excelService.exportExcel_Tb13(date);
            String year = date.split("-")[0];
            String month = date.split("-")[1];
            HashMap map =  new HashMap();
            map.put("list_1zong",zong1_List);
            map.put("list_2ji",ji02_list);
            map.put("list_3bu",bu03_list);
            map.put("list_4jijin",jijin04_list);
            map.put("list_5shui",shui05_list);
            map.put("list_6cai",cai06_list);
            map.put("list_7shuibi",shuibi07_list);
            map.put("list_10zhichu",zhichu10_list);
            map.put("list_11min",min_11List);
            map.put("list_12sheng",sheng_12List);
            map.put("list_13tongbao",tongBao_13List);
            map.put("year",year);
            map.put("month",month);
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
        String templatepath = System.getProperty("user.dir")+File.separator+"templateExport"+File.separator+"导出_模板.xlsx";
        templateExportParams.setTemplateUrl(templatepath);
        String[] sheetNameArray = {"01总","02级","03部","04基金分级收支","05税","06财","07税比","10支出","11民生","12省","13通报"};
        templateExportParams.setSheetName(sheetNameArray);
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams,map);
        //System.out.println("path:======="+path+"/excelExport/"+fileName+".xlsx");
        File savefile = new File(System.getProperty("user.dir")+File.separator+"excelExport"+File.separator+fileName+".xlsx");
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
    //列表
    @RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String dataList(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String date = request.getParameter("date");
        String index = request.getParameter("index");
        Map map =  new HashMap();
        map.put("date",date);
        List<Map> record = new ArrayList<Map>();
        int totalCount = 0;
        if("0".equals(index)){
            totalCount = stExcelMapper.selectByList_count_13(map);
            record = stExcelMapper.selectByList_13(page,map);
        }else{
            totalCount = stExcelMapper.selectdisburseByList_count_13(map);
            record = stExcelMapper.selectdisburseByList_13(page,map);
        }
        String result = "{\"recordsTotal\":" + totalCount;
        result += ",\"recordsFiltered\":" + totalCount;
        result += ",\"data\":" + JSONArray.fromObject(record) + "}";
        return result;
    }

    @RequestMapping("/ydryear")
    @ResponseBody
    public String ydryear(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String date = request.getParameter("date");
        Map map =  new HashMap();
        //map.put("date",date);
        List<Map> record = new ArrayList<Map>();
        int totalCount = 0;
        String sql = "select year from t_incomeByarea a group by year";
        List<Map> list = stExcelMapper.selectBysql(sql);
        if(null != list){
            totalCount = list.size();
        }
        record = stExcelMapper.selectByYear_ydr_13(page,map);
        String result = "{\"recordsTotal\":" + totalCount;
        result += ",\"recordsFiltered\":" + totalCount;
        result += ",\"data\":" + JSONArray.fromObject(record) + "}";

        return result;
    }


    /**
     * 清除导入数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cleardata")
    @ResponseBody
    public String cleardata(HttpServletRequest request, HttpServletResponse response)throws Exception{
        Map resultMap = new HashMap();
        Gson gson = new Gson();
        String jsonresult = null;
        try {
            stExcelMapper.cleardata();
            resultMap.put("result","success");
        } catch (Exception e) {
            resultMap.put("result","error");
            e.printStackTrace();
        }
        jsonresult = gson.toJson(resultMap);
        return jsonresult;
    }
    @RequestMapping("/import_pro")
    public String import_pro(){
        return "excel/excel_pro";
    }

    /**
     * 导入省级数据excel
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/import_prodataBypoi")
    @ResponseBody
    public String import_prodataBypoi(HttpServletRequest request, HttpResponse response ,@RequestParam(value = "file", required = false) MultipartFile file)throws Exception{
        String date = request.getParameter("date");
        String[] keys = {"kmbm","kmmc","amt","sbj","ncs","jdzs","pxs","jjs","xys","yts","gzs","ycs","srs","jas","wzs"};
        /**
         * 删除导入数据开始
         */
        String del_prodata = "delete from t_income_pro where year='"+date+"'";
        String del_proareadata = "delete from t_incomebyarea_pro where year='"+date+"'";
        stExcelMapper.excutesql(del_prodata);
        stExcelMapper.excutesql(del_proareadata);
        /**
         * 删除导入数据结束
         */
        List<Map>  income_list = POIUtil.readExcel(file,"省级",keys);
        try {
            String type = "";
            for(Map map:income_list){
                map.put("year",date);
                map.put("type",type);
            }
            stExcelMapper.insertSelective_batch_incomeBypro_map(income_list);
            //插入按地区收入表
            List<Map> list_area = stExcelMapper.selectBysql("select area_code,area_name,simple_name from t_area where level='1'");
            List<Map<String,String>> list_incode_area = new ArrayList<>();
            for(Map area:list_area) {
                getBasedateByArea(income_list, list_incode_area, area);
            }
            //新增省级地区基本收入数据
            stExcelMapper.insertSelective_batch_incomeByproarea_map(list_incode_area);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 导入13excel
     * @param request
     * @param files
     * @return
     */
    @RequestMapping("/importfileBypoi_13")
    @ResponseBody
    public Object importfileBypoi_13(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile[] files) throws Exception{
        Map resultMap = new HashMap();
        Gson gson = new Gson();
        String jsonresult = null;
        try {
            for (MultipartFile file : files){
                getYb(file);
            }
            resultMap.put("msg","success");
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("msg","error");
        }
        jsonresult = gson.toJson(resultMap);
        return jsonresult;

    }

    private void getYb(MultipartFile file) throws IOException {
        Workbook workbook = POIUtil.getWorkBook(file);
        //第一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowNum =  sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        //第一行数据
        Row firstRow= sheet.getRow(firstRowNum);
        //地区名称
        String area_name = POIUtil.replaceBlank(firstRow.getCell(0).getStringCellValue());
        if("市直".equals(area_name)){
            area_name ="高新区";
        }
        if("抚州市市本级".equals(area_name)){
            area_name ="市本级";
        }
        //第二行数据
        Row secondRow= sheet.getRow(firstRowNum+1);
        String base =  POIUtil.replaceBlank(secondRow.getCell(0).getStringCellValue());
        //获取时间
        String date =  DateUtil.getDateBystr(base);
        Row lastRow= sheet.getRow(20);
        //地区编码
        String area_code =area_name.equals("高新区")?"36100X": lastRow.getCell(2).getStringCellValue();
        //读取收入数据
        String[] keys={"kmbm","kmmc","amt"};
        List<Map<String,String>> income_list = POIUtil.readSheet(workbook.getSheetAt(1),keys,"1");
        String type = "";
        for(Iterator<Map<String,String>> iterator= income_list.iterator();iterator.hasNext();){
            Map<String,String> map = iterator.next();
            //去除科目编码和科目名称为空的数据
            if("".equals(POIUtil.replaceBlank(map.get("kmbm"))) && "".equals(POIUtil.replaceBlank(map.get("kmmc")))){
                iterator.remove();
                continue;
            }
            map.put("area_name",area_name);
            map.put("year",date);
            map.put("area_code",area_code);
            if(map.get("kmmc") != null ) {
                if (map.get("kmmc").toString().contains("一般公共预算收入合计")) {
                    type = "1";
                } else if (map.get("kmmc").toString().contains("政府性基金预算收入合计")) {
                    type = "2";
                } else if (map.get("kmmc").toString().contains("国有资本经营预算收入合计")) {
                    type = "3";
                } else if (map.get("kmmc").toString().contains("社会保险基金预算收入合计")) {
                    type = "4";
                }
            }
            map.put("type",type);
        }
        //删除数据
        String delsql =  "delete from t_incomebyarea where year='"+date+"' and area_code='"+area_code+"'";
        stExcelMapper.excutesql(delsql);
        //新增地区基本收入数据
        stExcelMapper.insertSelective_batch_incomearea_map(income_list);
        List<Map<String,String>> zc_list = POIUtil.readSheet(workbook.getSheetAt(1),keys,"2");
        String zctype = null;
        for(Iterator<Map<String,String>> iterator= zc_list.iterator();iterator.hasNext();){
            Map<String,String> zcIcome = iterator.next();
            //去除科目编码和科目名称为空的数据
            if("".equals(POIUtil.replaceBlank(zcIcome.get("kmbm"))) && "".equals(POIUtil.replaceBlank(zcIcome.get("kmmc")))){
                iterator.remove();
                continue;
            }
            zcIcome.put("area_name",area_name);
            zcIcome.put("year",date);
            zcIcome.put("area_code",area_code);
            zcIcome.put("year",date);
            if(zcIcome.get("kmmc") != null ) {
                if (zcIcome.get("kmmc").toString().contains("一般公共预算支出合计")) {
                    zctype = "1";
                } else if (zcIcome.get("kmmc").toString().contains("政府性基金预算支出合计")) {
                    zctype = "2";
                } else if (zcIcome.get("kmmc").toString().contains("国有资本经营预算支出合计")) {
                    zctype = "3";
                } else if (zcIcome.get("kmmc").toString().contains("社会保险基金预算支出合计")) {
                    zctype = "4";
                }
            }
            zcIcome.put("type",zctype);
            //新增地区基本支出数据
        }
        delsql =  "delete from t_disbursebyarea where  year='"+date+"' and area_code='"+area_code+"'";
        stExcelMapper.excutesql(delsql);
        stExcelMapper.insertSelective_batch_disbursearea_map(zc_list);
        //自定义科目编码更新
        String sql= "update t_incomebyarea a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm='' and year ='"+date+"' and area_code='"+area_code+"'" ;
        stExcelMapper.excutesql(sql);
        sql= "update t_disbursebyarea a,t_kmbmdiy b set a.kmbm = b.kmbm where a.kmmc=b.kmmc and a.kmbm='' and year ='"+date+"' and area_code='"+area_code+"'" ;
        stExcelMapper.excutesql(sql);

        workbook.close();
    }

}
