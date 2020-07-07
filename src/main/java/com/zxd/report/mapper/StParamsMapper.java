package com.zxd.report.mapper;/**
 * Created by Think on 2016/8/18.
 */


import com.zxd.commonmodel.Page;
import com.zxd.report.model.StParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版权声明 中科金审（北京）有限公司 版权所有 违者必究
 * <br> Company：中科金审
 * <br> @author 杜波
 * <br> 2016/8/18
 * <br> @version 1.0
 */
public interface StParamsMapper {

    int deleteByPrimaryKey(String PARM_KEY);

    int insert(StParams record);

    int insertSelective(StParams record);

    StParams getParamsValue(String PARM_KEY);

    int updateByPrimaryKeySelective(StParams record);

    int updateByPrimaryKey(StParams record);

    List<StParams> selectByList(Page page);

    int selectByCount();
    List<StParams> selectForKey(@Param("pkey") String pkey);
}
