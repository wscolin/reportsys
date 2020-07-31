package com.zxd.report.controller;


import com.zxd.commonmodel.Page;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StIpConfing;
import com.zxd.report.service.IpconfigService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/ipconfig")
public class IpconfigController {
	@Autowired
	private IpconfigService itemService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@RequiresPermissions("st_ipconfig_index")
	//@SystemControllerLog(description = "IP管理页面",params = 0)
	public String index() {
		return "/sys/ipconfig";
	}	

	//列表
	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "IP管理列表数据",params = 0)
	public String dataList(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<StIpConfing> record = new ArrayList<StIpConfing>();
	    int totalCount = (int) itemService.selectByCount();
	    
		record = itemService.selectByList(page);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";

		return result;
	}

	//新增
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_ipconfig_add")
	//@SystemControllerLog(description = "IP管理新增",params = 0)
	public String insert(StIpConfing record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setCREATOR(userVO.getUSERID());
		record.setCREATE_TIME(new Date());
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATE_TIME(new Date());
		int result = itemService.insert(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}		
	}	

	//更新
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_ipconfig_edit")
	//@SystemControllerLog(description = "IP管理修改",params = 0)
	public String update(StIpConfing record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();	
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATE_TIME(new Date());
		int result = itemService.updateByPrimaryKey(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}	
	}	
	
	//状态
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_ipconfig_set")
	//@SystemControllerLog(description = "IP管理状态设置",params = 0)
	public String updateByState(String ID) throws Exception {
		itemService.updateByEnabled(ID);
		return "success";
	}		
}
