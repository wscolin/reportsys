package com.zxd.report.controller;


import com.zxd.commonmodel.Page;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StParams;
import com.zxd.report.service.StParamsService;
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
@RequestMapping(value = "/params")
public class ParamsController {
	@Autowired
	private StParamsService itemService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@RequiresPermissions("st_params_index")
	@SystemControllerLog(description = "参数管理页面",params = 0)
	public String index() {
		return "/sys/params";
	}	

	//列表
	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "参数管理列表",params = 0)
	public String dataList(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<StParams> record = new ArrayList<StParams>();
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
	@RequiresPermissions("st_params_add")
	@SystemControllerLog(description = "参数管理新增",params = 0)
	public String insert(StParams record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setCREATOR(userVO.getUSERID());
		record.setCREATOR_NAME(userVO.getOPERATORNAME());
		record.setCREATE_TIME(new Date());
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATOR_NAME(userVO.getOPERATORNAME());
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
	@RequiresPermissions("st_params_edit")
	@SystemControllerLog(description = "参数管理修改",params = 0)
	public String update(StParams record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATOR_NAME(userVO.getOPERATORNAME());
		record.setUPDATE_TIME(new Date());
		int result = itemService.updateByPrimaryKey(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}	
	}
	@RequestMapping(value = "/isone", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "参数管理是否唯一",params = 0)
	public String isone(String key) throws Exception {
		StParams record = itemService.getParamsValue(key);
		if (record!=null) {
			return "failure";
		}else{
			return "success";
		}
	}
}
