package com.zxd.report.service;


import com.zxd.commonmodel.Page;
import com.zxd.report.model.StRole;
import com.zxd.report.model.StRoleResource;

import java.util.Collection;
import java.util.List;

public interface RoleService {

	//新增
	int insert(StRole record);

	//修改
	int updateByPrimaryKey(StRole record);

	//状态
	int updateByEnabled(String ROLEID);
	
	//全部
	List<StRole> selectByList(Page model, String userid);

	//总记录数
	int selectByCount(Page model, String userid);

	/**
	 * 批量插入资源
	 * @param list 资源集合
	 * @param roleid 角色id
     */
	void insertresource(Collection<StRoleResource> list, String roleid);
}
