package com.zxd.report.service;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StIpConfing;

import java.util.List;

public interface IpconfigService {

	//新增
	int insert(StIpConfing record);

	//修改
	int updateByPrimaryKey(StIpConfing record);

	//状态
	int updateByEnabled(String ID);
	
	//全部
	List<StIpConfing> selectByList(Page model);

	//总记录数
	int selectByCount();
	int getipbyuser(String ip);
}
