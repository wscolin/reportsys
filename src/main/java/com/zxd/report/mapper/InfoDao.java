package com.zxd.report.mapper;

import com.zxd.report.model.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InfoDao {
    @Select("select * from Info where username = #{name}")
    Info queryByName(String name);

}
