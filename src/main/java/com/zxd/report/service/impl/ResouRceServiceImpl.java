package com.zxd.report.service.impl;

import com.zxd.report.mapper.StResourceMapper;
import com.zxd.report.model.StResource;
import com.zxd.report.service.ResouRceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ResouRceServiceImpl implements ResouRceService {

    @Resource
    private StResourceMapper stresourcemapper;

    @Override
    public List<Map> selresourcetree(String type,String enabled,String ctx) {
        if(type.equals("2")){
            return null;
        }else{
            return stresourcemapper.selResourcetree(type,enabled,ctx);
        }
    }

    @Override
    public int insertSelective(StResource record) {
        return stresourcemapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(StResource record) {
        return stresourcemapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<StResource> selectbyfatherId(String pId) {
        return stresourcemapper.selectbyfatherId(pId);
    }
    @Override
    public List<Map> selectbyappid(String roleid) {
        return stresourcemapper.selectbyappid(roleid);
    }
}
