package com.zxd.report.controller;

import com.zxd.commonmodel.Page;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StRole;
import com.zxd.report.model.StRoleResource;
import com.zxd.report.service.ResouRceService;
import com.zxd.report.service.RoleService;
import com.zxd.report.service.StUserService;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping(value = "/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResouRceService resourceservice;
	@Autowired
	private StUserService stUserService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@RequiresPermissions("st_role_index")
	//@SystemControllerLog(description = "角色管理页面",params = 0)
	public String index(ModelMap map) {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		if(userVO.getUSERID().equals("admin")){
			map.addAttribute("maxrole","-1");
		}else{
			String roles="";
			for (Map temp:stUserService.getuserrole(userVO.getOPERATORID().toString(),userVO.getOPERATORID().toString())) {
				if(temp.get("setROLEID")!=null){
					roles+=temp.get("LEVEL");
					break;
				}
			}
			map.addAttribute("maxrole",roles);
		}
		return "/sys/role";
	}	

	//列表
	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "角色管理列表",params = 0)
	public String dataList(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		List<StRole> record = new ArrayList<StRole>();
	    int totalCount = (int) roleService.selectByCount(page,userVO.getUSERID());
		record = roleService.selectByList(page,userVO.getUSERID());
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";

		return result;
	}

	//新增
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_role_add")
	//@SystemControllerLog(description = "角色管理新增",params = 0)
	public String insert(StRole record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();		
		record.setCREATOR(userVO.getUSERID());
		record.setCREATETIME(new Date());
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATETIME(new Date());
		int result = roleService.insert(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}		
	}	

	//更新
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_role_edit")
	//@SystemControllerLog(description = "角色管理修改",params = 0)
	public String update(StRole record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();	
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATETIME(new Date());
		int result = roleService.updateByPrimaryKey(record);
		if (result>0) {
			return "success";
		}else{
			return "failure";
		}	
	}	
	
	//状态
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_role_set")
	//@SystemControllerLog(description = "角色管理设置状态",params = 0)
	public String updateByState(String ROLEID) throws Exception {
		roleService.updateByEnabled(ROLEID);
		return "success";
	}
	//选中节点
	@RequestMapping(value = "/getresource", method = RequestMethod.POST)
	@ResponseBody
	//@SystemControllerLog(description = "角色管理查询角色资源数据",params = 0)
	public List<Map> getresource(String roleid) throws Exception {
		return resourceservice.selectbyappid(roleid);
	}
	//选中节点
	@RequestMapping(value = "/saveresource", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_role_saveresource")
	//@SystemControllerLog(description = "角色管理页面保存角色资源数据",params = 0)
	public String saveresource(String StRoleResources,String roleid) {
		JSONArray array =JSONArray.fromObject(StRoleResources);
		Collection<StRoleResource> list = JSONArray.toCollection(array,StRoleResource.class);
		roleService.insertresource(list,roleid);
		return  "success";
	}
}
