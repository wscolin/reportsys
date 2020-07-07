package com.zxd.report.service;


import com.zxd.report.model.StDept;

import java.util.List;
import java.util.Map;

public interface StDeptService {

	//树查询
	List<Map> selectDepttree(String id, String deptid);
	//查询部门信息 返回map包含父部门名称 PARENTNAME
	Map getdeptbyid(String id);
	//新增部门信息
	int insertSelective(StDept record);
	//更新部门信息
	int updateByPrimaryKeySelective(StDept record);
	StDept selectByPrimaryKey(String DEPTID);
	//查询部门下的其余部门
	List<StDept> selectbyfatherId(String pId);
	//根据数据权限查询使用部门
	String seldeptByper(String deptid, String permission);
}
