package com.zxd.report.mapper;

import com.zxd.report.model.StResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StResourceMapper {
    int deleteByPrimaryKey(Integer RESOURCEID);

    int insert(StResource record);

    int insertSelective(StResource record);

    StResource selectByPrimaryKey(Integer RESOURCEID);

    int updateByPrimaryKeySelective(StResource record);

    int updateByPrimaryKey(StResource record);
    List<StResource> selectbyfatherId(@Param("pId") String pId);
    List<StResource> selectListByUser(@Param("userId") String userId, @Param("type") String type);
    List<Map> selResourcetree(@Param("type") String type, @Param("enabled") String enabled, @Param("ctx") String ctx);
    List<Map> selectbyappid(@Param("roleid") String roleid);
}