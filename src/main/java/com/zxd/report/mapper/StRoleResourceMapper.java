package com.zxd.report.mapper;

import com.zxd.report.model.StRoleResource;
import org.apache.ibatis.annotations.Param;

public interface StRoleResourceMapper {
    int deleteByPrimaryKey(StRoleResource key);

    int insert(StRoleResource record);

    int insertSelective(StRoleResource record);
    int deletebyroleid(@Param("roleid") String roleid);
}