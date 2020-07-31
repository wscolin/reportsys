package com.zxd.report.controller;

import com.zxd.commonmodel.Page;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StDictionary;
import com.zxd.report.service.DictService;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/dict")
public class DictController {

	@Autowired
	private DictService itemService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@RequiresPermissions("st_dict_index")
	//@SystemControllerLog(description = "字典管理页面",params = 0)
	public String index() {
		return "/sys/dict";
	}

	//列表
	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "字典管理数据列表",params = 0)
	public String dataList(Page page, HttpServletRequest request, HttpServletResponse response, String pId, String FLAG) throws Exception {
		Map map = new HashMap<>();
		map.put("FLAG",FLAG==null?"0":FLAG);
		map.put("pId",pId);
		List<StDictionary> record = new ArrayList<StDictionary>();
		int totalCount = (int) itemService.selectByCount(page,map);
		record = itemService.selectByList(page,map);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";
		return result;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	//@SystemControllerLog(description = "字典管理部门树",params = 0)
	public List<Map> tree() throws Exception {
		return itemService.selectDepttree();
	}
	//新增
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_dict_add")
	//@SystemControllerLog(description = "字典管理新增",params = 0)
	public String insert(StDictionary record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setCREATOR(userVO.getUSERID());
		record.setUPDATOR(userVO.getUSERID());
		record.setCREATE_TIME(new Date());
		record.setUPDATE_TIME(new Date());
		int result = itemService.insertSelective(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}
	}

	//更新
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "字典管理修改",params = 0)
	public String update(StDictionary record) throws Exception {
		StDictionary olddic = itemService.selectByPrimaryKey(record.getID());
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATE_TIME(new Date());
		int result = itemService.updateByPrimaryKeySelective(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}
	}
	@RequestMapping(value = "/itemcodeisone", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "字典管理编码是否唯一",params = 0)
	public String itemcodeisone(String itemcode,String pId) throws Exception {
		if(itemService.selectByitemcode(itemcode,pId)==0){
			return "success";
		}else{
			return "failure";
		}
	}
	@RequestMapping(value = "/sortup", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "字典管理排序上移",params = 0)
	public String sortup(Integer id,String pId) throws Exception {
		String data="";
		List<StDictionary> stdepts = itemService.selectbyfatherId(pId);
		for (int i=0;i<stdepts.size();i++){
			if(stdepts.get(i).getID().equals(id)){
				if(i==0){
					data="nomove";
				}else{
					StDictionary yhtype = new StDictionary();
					yhtype.setID(stdepts.get(i).getID());
					yhtype.setTHESORT(stdepts.get(i-1).getTHESORT());
					itemService.updateByPrimaryKeySelective(yhtype);
					StDictionary yhtypeT = new StDictionary();
					yhtypeT.setID(stdepts.get(i-1).getID());
					yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
					itemService.updateByPrimaryKeySelective(yhtypeT);
					data="success";
				}
			}
		}
		return data;
	}
	@RequestMapping(value = "/sortdown", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "字典管理排序下移",params = 0)
	public String sortdown(Integer id,String pId) throws Exception {
		String data="";
		List<StDictionary> stdepts = itemService.selectbyfatherId(pId);
		for (int i=0;i<stdepts.size();i++){
			if(stdepts.get(i).getID().equals(id)){
				if(i==stdepts.size()-1){
					data="nomove";
				}else{
					StDictionary yhtype = new StDictionary();
					yhtype.setID(stdepts.get(i).getID());
					yhtype.setTHESORT(stdepts.get(i+1).getTHESORT());
					itemService.updateByPrimaryKeySelective(yhtype);
					StDictionary yhtypeT = new StDictionary();
					yhtypeT.setID(stdepts.get(i+1).getID());
					yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
					itemService.updateByPrimaryKeySelective(yhtypeT);
					data="success";
				}
			}
		}
		return data;
	}
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "字典管理状态设置",params = 0)
	public String set(Integer id,String enabled) throws Exception {
		StDictionary temp = new StDictionary();temp.setID(id);temp.setENABLED(enabled.equals("1")?"0":"1");
		itemService.updateByPrimaryKeySelective(temp);
		return "success";
	}
}
