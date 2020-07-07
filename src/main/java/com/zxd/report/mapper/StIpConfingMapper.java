package com.zxd.report.mapper;



import com.zxd.commonmodel.Page;
import com.zxd.report.model.StIpConfing;

import java.util.List;

public interface StIpConfingMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(StIpConfing record);

    int insertSelective(StIpConfing record);

    StIpConfing selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(StIpConfing record);

    int updateByPrimaryKey(StIpConfing record);
    int updateByEnabled(String ID);

    //全部信息
    List<StIpConfing> selectByList(Page model);

    //总记录数
    int selectByCount();
    int getipbyuser(String ip);
}