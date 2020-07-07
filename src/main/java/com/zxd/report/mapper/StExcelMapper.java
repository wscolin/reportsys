package com.zxd.report.mapper;

import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.TongBao_13;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/6/24 0024.
 */
public interface StExcelMapper {
    int insertSelective(ImIcome imIcome);
    int insertSelective_batch_income(List<ImIcome> list);
    int insertSelective_batch_disburse(List<ImIcome> list);
    List<Min_11> querybysql(@Param(value = "sql") String sql);
    List<Map> selectBysql(@Param(value = "sql") String sql);
}