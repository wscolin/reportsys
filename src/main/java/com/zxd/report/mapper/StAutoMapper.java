package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.Min_11;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/6/24 0024.
 */
public interface StAutoMapper {
    List<Map> selectBysql(@Param(value = "sql") String sql);
    List<Map> selectBysqlList(@Param(value = "sql") String sql);
}