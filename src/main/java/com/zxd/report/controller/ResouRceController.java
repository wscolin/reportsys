package com.zxd.report.controller;


import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StResource;
import com.zxd.report.service.DictService;
import com.zxd.report.service.ResouRceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/resource")
public class ResouRceController {

	@Autowired
	private ResouRceService resouRceService;
	@Autowired
	private DictService dictService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	//@RequiresPermissions("st_resource_index")
	//@SystemControllerLog(description = "资源管理页面",params = 0)
	public String index(Map<String,Object> map) {
		String strdict = dictService.getDictionary("DATA_AUTHORITY");
		map.put("dict", strdict);
		return "/sys/resource";
	}
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理树",params = 0)
	public List<Map> tree(String type,String enabled,HttpServletRequest request) throws Exception {
		if(enabled==null){
			enabled="1";
		}
		return resouRceService.selresourcetree(type,enabled,request.getContextPath());
	}
	//新增
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理新增",params = 0)
	public String insert(StResource record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setISLEAF(record.getPARENTID().equals(0)?"N":"Y");
		record.setCREATOR(userVO.getUSERID());
		record.setCREATE_TIME(new Date());
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATE_TIME(new Date());
		record.setICONCLS(record.getICONCLS().replaceAll("\"","\'"));
		int result = resouRceService.insertSelective(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}
	}

	//更新
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理修改",params = 0)
	public String update(StResource record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setISLEAF(record.getPARENTID().equals(0)?"N":"Y");
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATE_TIME(new Date());
		record.setICONCLS(record.getICONCLS().replaceAll("\"","\'"));
		int result = resouRceService.updateByPrimaryKeySelective(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}
	}
	@RequestMapping(value = "/sortup", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理排序上移",params = 0)
	public String sortup(String id,String pId,String appid) throws Exception {
		String data="";
		List<StResource> stdepts = resouRceService.selectbyfatherId(pId);
		for (int i=0;i<stdepts.size();i++){
			if(stdepts.get(i).getRESOURCEID().toString().equals(id)){
				if(i==0){
					data="nomove";
				}else{
					StResource yhtype = new StResource();
					yhtype.setRESOURCEID(stdepts.get(i).getRESOURCEID());
					yhtype.setTHESORT(stdepts.get(i-1).getTHESORT());
					resouRceService.updateByPrimaryKeySelective(yhtype);
					StResource yhtypeT = new StResource();
					yhtypeT.setRESOURCEID(stdepts.get(i-1).getRESOURCEID());
					yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
					resouRceService.updateByPrimaryKeySelective(yhtypeT);
					data="success";
				}
			}
		}
		return data;
	}
	@RequestMapping(value = "/sortdown", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理排序下移",params = 0)
	public String sortdown(String id,String pId,String appid) throws Exception {
		String data="";
		List<StResource> stdepts = resouRceService.selectbyfatherId(pId);
		for (int i=0;i<stdepts.size();i++){
			if(stdepts.get(i).getRESOURCEID().toString().equals(id)){
				if(i==stdepts.size()-1){
					data="nomove";
				}else{
					StResource yhtype = new StResource();
					yhtype.setRESOURCEID(stdepts.get(i).getRESOURCEID());
					yhtype.setTHESORT(stdepts.get(i+1).getTHESORT());
					resouRceService.updateByPrimaryKeySelective(yhtype);
					StResource yhtypeT = new StResource();
					yhtypeT.setRESOURCEID(stdepts.get(i+1).getRESOURCEID());
					yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
					resouRceService.updateByPrimaryKeySelective(yhtypeT);
					data="success";
				}
			}
		}
		return data;
	}
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "资源管理设置状态",params = 0)
	public String set(Integer id,String enabled) throws Exception {
		StResource temp = new StResource();temp.setRESOURCEID(id);temp.setENABLED(enabled.equals("1")?"0":"1");
		resouRceService.updateByPrimaryKeySelective(temp);
		return "success";
	}
}
