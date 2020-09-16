package com.zxd.report.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zxd.report.mapper.StAutoMapper;
import com.zxd.report.service.StParamsService;
import com.zxd.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 出图controller
 */
@Controller
@RequestMapping("/plot")
public class PlotController {
    private  Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private StParamsService stParamsService;
    @Autowired
    private StAutoMapper stAutoMapper;
    @RequestMapping("/index")
    public String index(){
        return "plot/index";
    }


    /**
     * 查询统计数据
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/canvos")
    @ResponseBody
    public String canvos(HttpServletRequest request)throws Exception{
        String  kmbm =   request.getParameter("kmbm");
        String index  = request.getParameter("index");
        String table = kmbm.equals("A024")?"t_disbursebyarea":"t_incomebyarea ";
        //get当前年份
        int year = DateUtil.getYear();
        //get昨年
        int last_year = year -1;
        Map returnMap =  new HashMap();
        if(index.equals("11")){
            getLjdata("A001", table, returnMap, year, last_year);
            getDydataFor11("A027", table, returnMap, year, last_year);
        }else{
            getLjdata(kmbm, table, returnMap, year, last_year);
            getDydata(kmbm, table, returnMap, year, last_year);
        }



        String returnData = gson.toJson(returnMap);

        return returnData;
    }

    private void getDydata(String kmbm, String table, Map returnMap, int year, int last_year) {
        //当年本月
        String this_year_dy_sql = stParamsService.getParamConf("plot_dy").replaceAll("\\$\\{year\\}",year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> this_year_dy_list = stAutoMapper.selectBysqlList(this_year_dy_sql);

        String last_year_dy_sql = stParamsService.getParamConf("plot_lj").replaceAll("\\$\\{year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        //去年本月
        List<Map> last_year_dy_list = stAutoMapper.selectBysqlList(last_year_dy_sql);

        //当年当月同比百分比
        String this_year_dy_tb_sql = stParamsService.getParamConf("plot_dy_tb_bfb").replaceAll("\\$\\{this_year\\}",year+"").
                replaceAll("\\$\\{last_year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> this_year_dy_tb_list = stAutoMapper.selectBysqlList(this_year_dy_tb_sql);

        //去年当月同比百分比
        String last_year_dy_tb_sql = stParamsService.getParamConf("plot_dy_tb_bfb").replaceAll("\\$\\{this_year\\}",last_year+"").
                replaceAll("\\$\\{last_year\\}",last_year-1+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> last_year_dy_tb_list = stAutoMapper.selectBysqlList(last_year_dy_tb_sql);

        List this_year_dy_Array = new ArrayList();
        this.map2list(this_year_dy_list.get(0),this_year_dy_Array);
        List last_year_dy_Array = new ArrayList();
        this.map2list(last_year_dy_list.get(0),last_year_dy_Array);
        List this_year_dy_tb_Array = new ArrayList();
        this.map2list(this_year_dy_tb_list.get(0),this_year_dy_tb_Array);
        List last_year_dy_tb_Array = new ArrayList();


        this.map2list(last_year_dy_tb_list.get(0),last_year_dy_tb_Array);
        returnMap.put("this_year_dy_Array",this_year_dy_Array);
        returnMap.put("last_year_dy_Array",last_year_dy_Array);
        returnMap.put("this_year_dy_tb_Array",this_year_dy_tb_Array);
        returnMap.put("last_year_dy_tb_Array",last_year_dy_tb_Array);
    }
    private void getDydataFor11(String kmbm, String table, Map returnMap, int year, int last_year) {
        //当年本月
        String this_year_dy_sql = stParamsService.getParamConf("plot_lj").replaceAll("\\$\\{year\\}",year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> this_year_dy_list = stAutoMapper.selectBysqlList(this_year_dy_sql);

        String last_year_dy_sql = stParamsService.getParamConf("plot_lj").replaceAll("\\$\\{year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        //去年本月
        List<Map> last_year_dy_list = stAutoMapper.selectBysqlList(last_year_dy_sql);

        //当年当月同比百分比
        String this_year_dy_tb_sql = stParamsService.getParamConf("plot_bfb").replaceAll("\\$\\{this_year\\}",year+"").
                replaceAll("\\$\\{last_year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> this_year_dy_tb_list = stAutoMapper.selectBysqlList(this_year_dy_tb_sql);

        //去年当月同比百分比
        String last_year_dy_tb_sql = stParamsService.getParamConf("plot_bfb").replaceAll("\\$\\{this_year\\}",last_year+"").
                replaceAll("\\$\\{last_year\\}",last_year-1+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> last_year_dy_tb_list = stAutoMapper.selectBysqlList(last_year_dy_tb_sql);

        List this_year_dy_Array = new ArrayList();
        this.map2list(this_year_dy_list.get(0),this_year_dy_Array);
        List last_year_dy_Array = new ArrayList();
        this.map2list(last_year_dy_list.get(0),last_year_dy_Array);
        List this_year_dy_tb_Array = new ArrayList();
        this.map2list(this_year_dy_tb_list.get(0),this_year_dy_tb_Array);
        List last_year_dy_tb_Array = new ArrayList();


        this.map2list(last_year_dy_tb_list.get(0),last_year_dy_tb_Array);
        returnMap.put("this_year_dy_Array",this_year_dy_Array);
        returnMap.put("last_year_dy_Array",last_year_dy_Array);
        returnMap.put("this_year_dy_tb_Array",this_year_dy_tb_Array);
        returnMap.put("last_year_dy_tb_Array",last_year_dy_tb_Array);
    }

    private void getLjdata(String kmbm, String table, Map returnMap, int year, int last_year) {
        String lj_sql = stParamsService.getParamConf("plot_lj").replaceAll("\\$\\{year\\}",year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        //当年累计
        List<Map> this_year_list = stAutoMapper.selectBysqlList(lj_sql);
        String last_year_sql = stParamsService.getParamConf("plot_lj").replaceAll("\\$\\{year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        //去年累计
        List<Map> last_year_list = stAutoMapper.selectBysqlList(last_year_sql);
        //当年同比百分比
        String this_year_tb_sql = stParamsService.getParamConf("plot_bfb").replaceAll("\\$\\{this_year\\}",year+"").
                replaceAll("\\$\\{last_year\\}",last_year+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> this_year_tb_list = stAutoMapper.selectBysqlList(this_year_tb_sql);
        //去年同比百分比
        String last_year_tb_sql = stParamsService.getParamConf("plot_bfb").replaceAll("\\$\\{this_year\\}",last_year+"").
                replaceAll("\\$\\{last_year\\}",last_year-1+"").replaceAll("\\$\\{kmbm\\}",kmbm+"").replaceAll("\\$\\{table\\}",table+"");
        List<Map> last_year_tb_list = stAutoMapper.selectBysqlList(last_year_tb_sql);
        List this_year_Array = new ArrayList();
        this.map2list(this_year_list.get(0),this_year_Array);
        List last_year_Array = new ArrayList();
        this.map2list(last_year_list.get(0),last_year_Array);
        List this_year_tb_Array = new ArrayList();
        this.map2list(this_year_tb_list.get(0),this_year_tb_Array);
        List last_year_tb_Array = new ArrayList();
        this.map2list(last_year_tb_list.get(0),last_year_tb_Array);
        returnMap.put("this_year",year);
        returnMap.put("last_year",last_year);
        returnMap.put("this_year_Array",this_year_Array);
        returnMap.put("last_year_Array",last_year_Array);
        returnMap.put("this_year_tb_Array",this_year_tb_Array);
        returnMap.put("last_year_tb_Array",last_year_tb_Array);
        returnMap.put("result","success");
    }


    public void map2list(Map map,List list){
       Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
       while (iterator.hasNext()){
           Map.Entry entry =  iterator.next();
           list.add( entry.getValue());
       }
    }
}
