package com.zxd.report.service.impl;

import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;
import com.zxd.report.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: gas_bank
 * @description:
 * @author:
 * @create: 2020-07-02 15:35
 **/
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private StExcelMapper stExcelMapper;

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
        List<Map> list = stExcelMapper.selectBysql(sql);

        return list;
    }


    @Override
    public List<Map> exportExcel_Tb13(String year) {
        String sql = "\tselect t.area a1,\n" +
                "\tt.zs202005 a2,\n" +
                "\tb.target_amt a3,\n" +
                "\tROUND(t.zs202005/b.target_amt*100,2)a4,\n" +
                "\tround((t.zs202005/t.zs201904-1)*100,1)a5,\n" +
                "  round(t.ss202005/t.zs202005 *100,2)a6\tfrom (\n" +
                "\tselect '高新区' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.gxq end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.gxq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.gxq end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.gxq end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '临川区' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.lcq end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.lcq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.lcq end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.lcq end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '东乡区' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.dxq end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.dxq end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.dxq end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.dxq end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '南城县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.ncx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.ncx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.ncx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.ncx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\t\tunion all\n" +
                "\tselect '南丰县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.nfx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.nfx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.nfx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.nfx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '黎川县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.lcx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.lcx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.lcx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.lcx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '崇仁县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.crx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.crx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.crx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.crx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '宜黄县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.yhx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.yhx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.yhx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.yhx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\tunion all\n" +
                "\tselect '乐安县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.lax end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.lax end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.lax end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.lax end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\t\tunion all\n" +
                "\tselect '金溪县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.jxx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.jxx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.jxx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.jxx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\t\tunion all\n" +
                "\tselect '资溪县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.zxx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.zxx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.zxx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.zxx end)ss202005\n" +
                "\tfrom t_income a \n" +
                "\t\tunion all\n" +
                "\tselect '广昌县' area,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2020-05'  and a.type ='1' then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2020-05'   then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2020-05'  then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2020-05'  then a.gcx end) zs202005,\n" +
                "\tsum(case when a.kmmc ='一般公共预算收入合计' and a.year='2019-05'  and a.type ='1' then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='上划中央\"四税\"' and a.year='2019-05'   then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='入中央库所得税' and a.year='2019-05'  then a.gcx end)+\n" +
                "\tsum(case when a.kmmc ='上划省级收入' and a.year='2019-05'  then a.gcx end) zs201904,\n" +
                "\tsum(case when a.kmmc ='税务部门组织收入' and a.year='2020-05'  then a.gcx end)ss202005\n" +
                "\tfrom t_income a \n" +
                ")t ,t_expectgoal b where t.area = b.area \n" +
                "\t\n" +
                "\t\n" +
                "\n" +
                "\t\n" +
                "\n" +
                "\t";
        List<Map> list = stExcelMapper.selectBysql(sql);
        return list;
    }
}
