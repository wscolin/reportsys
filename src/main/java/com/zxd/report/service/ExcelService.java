package com.zxd.report.service;

import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/7/2 0002.
 */
public interface ExcelService {
    List<Min_11> exportExcel(String year);
    List<Map> exportExcel_Min11(String year);
    List<Map> exportExcel_Tb13(String year);

    /**
     * 查询1总报表数据
     * @param year
     * @return
     */
    List<Map> exportExcel_1zong(String year);

    /**
     * 查询02级报表数据
     * @param year
     * @return
     */
    List<Map> exportExcel_02ji(String year);
    /**
     * 查询04基金分级收支报表数据
     * @param year
     * @return
     */
    List<Map>   exportExcel_04jijin(String year);


    /**
     * 查询03部报表数据
     * @param year
     * @return
     */
    List<Map>   exportExcel_03bu(String year);

}