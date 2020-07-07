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
}