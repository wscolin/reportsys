package com.zxd.report.mapper;/**
 * Created by Think on 2016/8/18.
 */


import com.zxd.commonmodel.Page;
import com.zxd.report.model.StParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StParamsMapper {

    int deleteByPrimaryKey(String PARM_KEY);

    int insert(StParams record);

    int insertSelective(StParams record);

    StParams getParamsValue(String PARM_KEY);

    int updateByPrimaryKeySelective(StParams record);

    int updateByPrimaryKey(StParams record);

    List<StParams> selectByList(@Param("model")Page page);

    int selectByCount();
    List<StParams> selectForKey(@Param("pkey") String pkey);
}
