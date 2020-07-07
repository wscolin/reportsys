package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StRole;
import com.zxd.report.model.StRoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface StRoleMapper {
    int deleteByPrimaryKey(Integer ROLEID);

    int insert(StRole record);

    int insertSelective(StRole record);

    StRole selectByPrimaryKey(Integer ROLEID);

    int updateByPrimaryKeySelective(StRole record);

    int updateByPrimaryKey(StRole record);
    //全部信息
    List<StRole> selectByList(@Param("model") Page model, @Param("userid") String userid);

    //总记录数
    int selectByCount(@Param("model") Page model, @Param("userid") String userid);
    //批量插入资源
    int insertresource(@Param("list") Collection<StRoleResource> list);
    int updateByEnabled(String ROLEID);
}