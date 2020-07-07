package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StLog;
import com.zxd.report.model.StLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StLogMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(StLogWithBLOBs record);

    int insertSelective(StLogWithBLOBs record);

    StLogWithBLOBs selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(StLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(StLogWithBLOBs record);

    int updateByPrimaryKey(StLog record);
    List<StLogWithBLOBs> selectByList(@Param("page") Page model, @Param("map") Map map);
    int selectByCount(@Param("map") Map map);
    List<Map> selectByList_ck(@Param("page") Page model, @Param("map") Map map);
    int selectByCount_ck(@Param("map") Map map);
    int ckcfqq(String qqdh);
    List<Map> getfks(@Param("page") Page model, @Param("fks") String[] fks);
}