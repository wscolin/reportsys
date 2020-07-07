package com.zxd.report.service.impl;

import com.zxd.report.mapper.StDeptMapper;
import com.zxd.report.model.StDept;
import com.zxd.report.service.StDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StDeptServiceImpl implements StDeptService {

    @Resource
    private StDeptMapper itemMapper;

    @Override
    public List<Map> selectDepttree(String id,String deptid) {
        return itemMapper.selectDepttree(id,deptid);
    }

    @Override
    public Map getdeptbyid(String id) {
        return itemMapper.getdeptbyid(id);
    }

    @Override
    public int insertSelective(StDept record) {
        return itemMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(StDept record) {
        return itemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public StDept selectByPrimaryKey(String DEPTID) {
        return itemMapper.selectByPrimaryKey(DEPTID);
    }

    @Override
    public List<StDept> selectbyfatherId(String pId) {
        return itemMapper.selectbyfatherId(pId);
    }

    @Override
    public String seldeptByper(String deptid, String permission) {
        return itemMapper.seldeptByper(deptid,permission);
    }
}
