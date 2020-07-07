package com.zxd.report.service;


import com.zxd.commonmodel.Page;
import com.zxd.report.model.StDictionary;

import java.util.List;
import java.util.Map;

public interface DictService {

	//树查询
	List<Map> selectDepttree();
	int insertSelective(StDictionary record);
	int updateByPrimaryKeySelective(StDictionary record);
	List<StDictionary> selectbyfatherId(String pId);
	int selectByitemcode(String itemcode, String pId);
	int selectByCount(Page page, Map map);
	List<StDictionary> selectByList(Page page, Map map);
	StDictionary selectBypandcode(String itemcode, String pitemcode);
	StDictionary selectByPrimaryKey(Integer ID);
	String getDictionary(String key);
}
