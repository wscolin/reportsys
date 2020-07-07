package com.zxd.report.mapper;

import com.zxd.report.model.StDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StDeptMapper {

    int insertSelective(StDept record);

    StDept selectByPrimaryKey(String DEPTID);

    int updateByPrimaryKeySelective(StDept record);


    List<Map> selectDepttree(@Param(value = "id") String id, @Param(value = "deptid") String deptid);
    Map getdeptbyid(@Param(value = "id") String id);
    List<StDept> selectbyfatherId(@Param(value = "pId") String pId);
    String seldeptByper(@Param(value = "deptid") String deptid, @Param(value = "permission") String permission);
}