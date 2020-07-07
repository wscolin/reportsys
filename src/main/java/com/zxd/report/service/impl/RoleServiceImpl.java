package com.zxd.report.service.impl;

import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StRoleMapper;
import com.zxd.report.mapper.StRoleResourceMapper;
import com.zxd.report.model.StRole;
import com.zxd.report.model.StRoleResource;
import com.zxd.report.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private StRoleMapper stRoleMapper;
    @Resource
    private StRoleResourceMapper stroleresourcemapper;

    @Override
    public int insert(StRole record) {
        return stRoleMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(StRole record) {
        return stRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<StRole> selectByList(Page model, String userid) {
        return stRoleMapper.selectByList(model,userid);
    }

    @Override
    public int selectByCount(Page page,String userid) {
        return stRoleMapper.selectByCount(page,userid);
    }

    @Override
    public void insertresource(Collection<StRoleResource> list, String roleid) {
        stroleresourcemapper.deletebyroleid(roleid);
        if(list.size()>0){stRoleMapper.insertresource(list);}
    }

    @Override
	public int updateByEnabled(String ROLEID) {
		return stRoleMapper.updateByEnabled(ROLEID);
	}
}
