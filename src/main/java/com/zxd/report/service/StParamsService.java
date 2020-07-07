package com.zxd.report.service;/**
 * Created by Think on 2016/8/18.
 */


import com.zxd.commonmodel.Page;
import com.zxd.report.model.StParams;

import java.util.List;

/**
 * 版权声明 中科金审（北京）有限公司 版权所有 违者必究
 * <br> Company：中科金审
 * <br> @author 杜波
 * <br> 2016/8/18
 * <br> @version 1.0
 */
public interface StParamsService {

    /**
     * 查询参数信息
     * @param paramsKey 参数表中的key
     * @return
     */
    StParams getParamsValue(String paramsKey);
    List<StParams> selectByList(Page page);
    int selectByCount();
    int insert(StParams stparams);
    int updateByPrimaryKey(StParams stparams);
    String getParamConf(String key) ;
}
