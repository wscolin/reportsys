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

    /**
     * 批量插入省级基础数据
     * @param list
     * @return
     */
    int insertSelective_batch_incomeBypro_map(List<Map> list);
    int insertSelective_batch_incomearea_map(List<Map<String,String>> list);

    /**
     * 根据地区批量插入省级基础数据
     * @param list
     * @return
     */
    int insertSelective_batch_incomeByproarea_map(List<Map<String,String>> list);
    int insertSelective_batch_disbursearea_map(List<Map<String,String>> list);
    int insertSelective_batch_disburse_map(List<Map> list);
    //删除收入数据
    int deleteincome(@Param(value = "date") String date);
    //删除支出数据
    int deletedisburse_area(@Param(value = "date") String date);
    //删除收入数据
    int deleteincome_area(@Param(value = "date") String date);
    //删除支出数据
    int deletedisburse(@Param(value = "date") String date);
    List<Min_11> querybysql(@Param(value = "sql") String sql);
    List<Map> selectBysql(@Param(value = "sql") String sql);
    List<Map<String,String>> selectBysql2(@Param(value = "sql") String sql);
    int selectByList_count(@Param("map") Map map);
    List<Map> selectByList(@Param("page") Page page,@Param("map") Map map);
    int selectByList_count_13(@Param("map") Map map);
    List<Map> selectByList_13(@Param("page") Page page,@Param("map") Map map);
    int selectdisburseByList_count(@Param("map") Map map);
    List<Map> selectdisburseByList(@Param("page") Page page,@Param("map") Map map);
    int selectdisburseByList_count_13(@Param("map") Map map);
    List<Map> selectdisburseByList_13(@Param("page") Page page,@Param("map") Map map);

    /**
     * 已导入的年份列表
     * @param page
     * @param map
     * @return
     */
    List<Map> selectByYear_ydr(@Param("page") Page page,@Param("map") Map map);

    /**
     * 已导入的年份列表13
     * @param page
     * @param map
     * @return
     */
    List<Map> selectByYear_ydr_13(@Param("page") Page page,@Param("map") Map map);
    /**
     * 执行sql
     */
    void excutesql(@Param("sql") String sql);
    /**
     * 清除导入数据
     */
    void cleardata();

}