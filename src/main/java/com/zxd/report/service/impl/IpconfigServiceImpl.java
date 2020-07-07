package com.zxd.report.service.impl;

import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StIpConfingMapper;
import com.zxd.report.model.StIpConfing;
import com.zxd.report.service.IpconfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IpconfigServiceImpl implements IpconfigService {

	@Resource
	private StIpConfingMapper itemMapper;

    @Override
    public int insert(StIpConfing record) {
        return itemMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(StIpConfing record) {
        return itemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<StIpConfing> selectByList(Page model) {
        return itemMapper.selectByList(model);
    }

    @Override
    public int selectByCount() {
        return itemMapper.selectByCount();
    }

    @Override
    public int getipbyuser(String ip) {
        return itemMapper.getipbyuser(ip);
    }

    @Override
	public int updateByEnabled(String ID) {
		return itemMapper.updateByEnabled(ID);
	}
}
