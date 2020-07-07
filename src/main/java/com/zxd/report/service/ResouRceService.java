package com.zxd.report.service;


import com.zxd.report.model.StResource;

import java.util.List;
import java.util.Map;

public interface ResouRceService {

	/**
	 * 获取资源树
	 * @param type 功能:0菜单，1按钮，2数据
	 * @param enabled 是否启用
	 * @param ctx 项目路径 用于展示图片
     * @return
     */
	List<Map> selresourcetree(String type, String enabled, String ctx);
	int insertSelective(StResource record);
	int updateByPrimaryKeySelective(StResource record);
	List<StResource> selectbyfatherId(String pId);
	List<Map> selectbyappid(String roleid);
}
