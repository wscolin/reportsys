package com.zxd.report.service;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StLogWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface LogService {
    //全部
    List<StLogWithBLOBs> selectByList(Page model, Map map);

    //总记录数
    int selectByCount(Map map);
    //ck全部
    List<Map> selectByList_ck(Page model, Map map);

    //ck总记录数
    int selectByCount_ck(Map map);
    String  ckcfqq(String qqdh);
    List<Map> getfks(Page model, String FKS);
}
