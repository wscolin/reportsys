package com.zxd.report.service;
import com.zxd.commonmodel.Page;
import com.zxd.report.model.StParams;
import java.util.List;
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
