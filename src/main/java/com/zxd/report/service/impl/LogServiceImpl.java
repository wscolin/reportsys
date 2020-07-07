package com.zxd.report.service.impl;

import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StLogMapper;
import com.zxd.report.model.StLogWithBLOBs;
import com.zxd.report.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private StLogMapper stLogMapper;
    @Override
    public List<StLogWithBLOBs> selectByList(Page model, Map map) {
        return stLogMapper.selectByList(model,map);
    }

    @Override
    public int selectByCount(Map map) {
        return stLogMapper.selectByCount(map);
    }

    @Override
    public List<Map> selectByList_ck(Page model, Map map) {
        return stLogMapper.selectByList_ck(model,map);
    }

    @Override
    public int selectByCount_ck(Map map) {
        return stLogMapper.selectByCount_ck(map);
    }

    @Override
    public String ckcfqq(String qqdh) {
        return String .valueOf(stLogMapper.ckcfqq(qqdh));
    }

    @Override
    public List<Map> getfks(Page model,String FKS) {
        return stLogMapper.getfks(model,FKS.split(","));
    }
}
