package com.zxd.report.service.impl;

import com.zxd.report.controller.ExcelController;
import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.model.Min_11;
import com.zxd.report.service.ExcelService;
import com.zxd.report.service.StParamsService;
import com.zxd.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @description:
 * @author:
 * @create: 2020-07-02 15:35
 **/
@Service
public class ExcelServiceImpl implements ExcelService {
    private final static Logger logger= LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Autowired
    private StExcelMapper stExcelMapper;
    @Autowired
    private StParamsService stParamsService;

    @Override
    public List<Min_11> exportExcel(String year) {
        String sql = "select '公共预算支出' kmmc,\n" +
                "'' kmbm,\n" +
                "sum(p.amt202005)-sum(p.amt202004) a1,\n" +
                "(sum(p.amt202005)-sum(p.amt202004))-(sum(p.amt201905)-sum(p.amt201904))a2,\n" +
                "round(((sum(p.amt202005)-sum(p.amt202004))-(sum(p.amt201905)-sum(p.amt201904)))/(sum(p.amt201905)-sum(p.amt201904))*100,1)a3,\n" +
                "sum(p.amt202005)a4,\n" +
                "sum(p.amt202005)-sum(p.amt201905)a5,\n" +
                "round((sum(p.amt202005)-sum(p.amt201905))/sum(p.amt201905)*100,1)a6\n" +
                "from (select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,sum(a.amt) amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in (201,202,203,204,205,206,207,208,210,211,212,213,214,215,216,217,219,220,221,222,224,229,232,233) and type ='1' group by a.kmbm ,a.year) d group by d.kmmc,d.kmbm ORDER BY d.kmbm)p\n" +
                "union all\n" +
                "select '其中“民生工程”支出' kmmc,\n" +
                "''kmbm,\n" +
                "sum(p.amt202005-p.amt202004),\n" +
                "sum(p.amt202005-p.amt202004)-sum(p.amt201905-p.amt201904),\n" +
                "round((sum(p.amt202005-p.amt202004)-sum(p.amt201905-p.amt201904))/sum(p.amt201905-p.amt201904)*100,1),\n" +
                "sum(p.amt202005),\n" +
                "sum(p.amt202005)-sum(p.amt201905),\n" +
                "round((sum(p.amt202005)-sum(p.amt201905))/sum(p.amt201905)*100,1)\n" +
                "from (select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,sum(a.amt) amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in ('205','206','208','210','211','213','221','207','212','214','216','220','222') and type ='1' group by a.kmbm ,a.year) d group by d.kmmc,d.kmbm ORDER BY d.kmbm)p\n" +
                "union all\n" +
                "select p.kmmc ,\n" +
                "p.kmbm ,\n" +
                "p.amt202005-p.amt202004 ,\n" +
                "(p.amt202005-p.amt202004)-(p.amt201905-p.amt201904),\n" +
                "ROUND(((p.amt202005-p.amt202004)-(p.amt201905-p.amt201904))/(p.amt201905-p.amt201904)*100,1),\n" +
                "p.amt202005,\n" +
                "p.amt202005-p.amt201905 ,\n" +
                "round((p.amt202005-p.amt201905)/p.amt201905*100,1) \n" +
                "from(\n" +
                "select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,sum(a.amt) amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in ('205','206','208','210','211','213','221','207','212','214','216','220','222') and type ='1' group by a.kmbm ,a.year) d group by d.kmmc,d.kmbm ORDER BY d.kmbm)p\n" +
                "\n" +
                "union all\n" +
                "select '民生支出占公共预算支出比重%' kmmc,'' kmbm, ROUND(x.a2/x.a1 *100,1),\n" +
                "ROUND(x.a4/x.a3 *100,1),\n" +
                "ROUND(x.a2/x.a1 *100,1)-ROUND(x.a4/x.a3 *100,1), \n" +
                "ROUND(x.a6/x.a5 *100,1),\n" +
                "ROUND(x.a8/x.a7 *100,1),\n" +
                "ROUND(x.a6/x.a5 *100,1)-ROUND(x.a8/x.a7 *100,1)\n" +
                "from \n" +
                "(select sum(r.amt202005-r.amt202004) a1 ,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221','207','212','214','216','220','222') then r.amt202005-r.amt202004  end ) a2,\n" +
                "\n" +
                "sum(amt201905-amt201904) a3,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221','207','212','214','216','220','222') then r.amt201905-r.amt201904  end )  a4,\n" +
                "sum(r.amt202005)a5,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221','207','212','214','216','220','222') then r.amt202005  end ) a6,\n" +
                "sum(r.amt201905) a7,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221','207','212','214','216','220','222') then r.amt201905  end ) a8\n" +
                "from (\n" +
                "select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,a.amt amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in ('201','202','203','204','205','206','207','208','210','211','212','213','214','215','216','217','219','220','221','222','224','229','232','233') and type ='1') d  GROUP BY d.kmmc,d.kmbm)r\n" +
                ")x\n" +
                "\n" +
                "union all \n" +
                "select '民生支出占公共预算支出比重%（原口径1-7项）' kmmc,'' kmbm, ROUND(x.a2/x.a1 *100,1),\n" +
                "ROUND(x.a4/x.a3 *100,1),\n" +
                "ROUND(x.a2/x.a1 *100,1)-ROUND(x.a4/x.a3 *100,1), \n" +
                "ROUND(x.a6/x.a5 *100,1),\n" +
                "ROUND(x.a8/x.a7 *100,1),\n" +
                "ROUND(x.a6/x.a5 *100,1)-ROUND(x.a8/x.a7 *100,1)\n" +
                "from \n" +
                "(select sum(r.amt202005-r.amt202004) a1 ,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221') then r.amt202005-r.amt202004  end ) a2,\n" +
                "\n" +
                "sum(amt201905-amt201904) a3,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221') then r.amt201905-r.amt201904  end )  a4,\n" +
                "sum(r.amt202005)a5,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221') then r.amt202005  end ) a6,\n" +
                "sum(r.amt201905) a7,\n" +
                "sum(case when r.kmbm in('205','206','208','210','211','213','221') then r.amt201905  end ) a8\n" +
                "from (\n" +
                "select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,a.amt amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in (201,202,203,204,205,206,207,208,210,211,212,213,214,215,216,217,219,220,221,222,224,229,232,233) and type ='1') d  GROUP BY d.kmmc,d.kmbm)r\n" +
                ")x\n" +
                "union all \n" +
                "select '原口径1-7项合计数' kmmc,\n" +
                "''kmbm,\n" +
                "sum(p.amt202005-p.amt202004),\n" +
                "sum(p.amt202005-p.amt202004)-sum(p.amt201905-p.amt201904),\n" +
                "round((sum(p.amt202005-p.amt202004)-sum(p.amt201905-p.amt201904))/sum(p.amt201905-p.amt201904)*100,1),\n" +
                "sum(p.amt202005),\n" +
                "sum(p.amt202005)-sum(p.amt201905),\n" +
                "round((sum(p.amt202005)-sum(p.amt201905))/sum(p.amt201905)*100,1)\n" +
                "from (select \n" +
                "d.kmmc,\n" +
                "d.kmbm,\n" +
                "sum(case when d.year ='2020-05' then d.amt else 0 end) amt202005,\n" +
                "sum(case when d.year ='2020-04' then d.amt else 0 end) amt202004,\n" +
                "sum(case when d.year ='2019-05' then d.amt else 0 end) amt201905,\n" +
                "sum(case when d.year ='2019-04' then d.amt else 0 end) amt201904\n" +
                "from \n" +
                "(select a.kmmc ,a.kmbm,sum(a.amt) amt,a.year from t_disburse a where a.year in('2020-04','2020-05','2019-05','2019-04') and a.kmbm in ('205','206','208','210','211','213','221') and type ='1' group by a.kmbm ,a.year) d group by d.kmmc,d.kmbm ORDER BY d.kmbm)p";
        List<Min_11> list = stExcelMapper.querybysql(sql);

        return list;
    }

    @Override
    public List<Map> exportExcel_Min11(String year) {
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");

        String sql = stParamsService.getParamConf("11minsheng").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }


    @Override
    public List<Map> exportExcel_Tb13(String year) {
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String sql = stParamsService.getParamConf("tongbao13").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                     replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).replaceAll("\\$\\{year\\}",thisyear);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
    @Override
    /**
     * 查询1总报表数据
     * @param year
     * @return
     */
   public List<Map> exportExcel_1zong(String year){
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");

        String sql = stParamsService.getParamConf("1zong").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }

    /**
     * 查询02级报表数据
     * @param year
     * @return
     */
    @Override
    public List<Map> exportExcel_02ji(String year){
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("02ji").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month).replaceAll("\\$\\{year\\}",thisyear);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
    /**
     * 查询04基金收支报表数据
     * @param year
     * @return
     */
    @Override
    public List<Map> exportExcel_04jijin(String year){
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("04jijin").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month).replaceAll("\\$\\{year\\}",thisyear);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }

    /**
     * 查询03部报表数据
     * @param year
     * @return
     */
    @Override
    public List<Map> exportExcel_03bu(String year){
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("03bu").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }


    /**
     * 查询05税报表数据
     * @param year
     * @return
     */
    @Override
    public List<Map>   exportExcel_05shui(String year){
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("05shui").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
    /**
     * 查询06税报表数据
     * @param year
     * @return
     */
    @Override
   public List<Map>   exportExcel_06cai(String year){
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("06cai").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
    /**
     * 查询07税比报表数据
     * @param year
     * @return
     */
    @Override
   public List<Map>   exportExcel_07shuibi(String year){
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("07shuibi").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month).replaceAll("\\$\\{year\\}",thisyear);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
    /**
     * 查询10支出报表数据
     * @param year
     * @return
     */
    @Override
   public List<Map>   exportExcel_10zhichu(String year){
        String thisyear = year.split("-")[0];
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("10zhichu").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month).replaceAll("\\$\\{year\\}",thisyear);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }

    @Override
    public List<Map> exportExcel_sheng12(String year) {
        Date date = DateUtil.getStr2date(year,"yyyy-MM");
        String this_year_this_month = DateUtil.getDate2str(date,"yyyy-MM");
        String this_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = DateUtil.getDate2str( DateUtil.getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = DateUtil.getDate2str(DateUtil.getAddTime(DateUtil.getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        String sql = stParamsService.getParamConf("12sheng").replaceAll("\\$\\{this_year_this_month\\}",this_year_this_month).
                replaceAll("\\$\\{this_year_last_month\\}",this_year_last_month).replaceAll("\\$\\{last_year_this_month\\}",last_year_this_month).
                replaceAll("\\$\\{last_year_last_month\\}",last_year_last_month);
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
}
