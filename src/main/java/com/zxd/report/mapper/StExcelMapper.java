package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import com.zxd.report.model.StIpConfing;
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
    int insertSelective_batch_income_map(List<Map> list);
    int insertSelective_batch_disburse_map(List<Map> list);
    //删除收入数据
    int deleteincome(@Param(value = "date") String date);
    //删除支出数据
    int deletedisburse(@Param(value = "date") String date);
    List<Min_11> querybysql(@Param(value = "sql") String sql);
    List<Map> selectBysql(@Param(value = "sql") String sql);
    List<Map> selectByList(@Param("page") Page page,@Param("map") Map map);
    List<Map> selectdisburseByList(@Param("page") Page page,@Param("map") Map map);
    /**
     * 执行sql
     */
    void excutesql(@Param("sql") String sql);

}