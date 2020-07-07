package com.zxd.report.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.zxd.commonmodel.ImUser;
import com.zxd.commonmodel.Page;
import com.zxd.commonmodel.PermissionBean;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StUser;
import com.zxd.report.model.StudentEntity;
import com.zxd.report.service.DictService;
import com.zxd.report.service.StDeptService;
import com.zxd.report.service.StUserService;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private StUserService stUserService;
	@Autowired
	private StDeptService stDeptService;
	@Autowired
	private DictService dictService;
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@RequiresPermissions("st_user_index")
	@SystemControllerLog(description = "用户管理首页",params = 0)
	public String index(ModelMap map) {
		map.addAttribute("ZW", JSONArray.fromObject(dictService.getDictionary("ZW")));
		return "/sys/user";
	}	

	//列表
	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "用户管理列表",params = 0)
	public String dataList(Page page, HttpServletRequest request, HttpServletResponse response, String deptid) throws Exception {
		List<Map> record = new ArrayList<Map>();
		if(deptid==null){
			Subject subject = SecurityUtils.getSubject();
			UserVO userVO = (UserVO) subject.getPrincipal();
			deptid = stDeptService.seldeptByper(userVO.getDEPTID(), PermissionBean.getEcuserPermission(userVO));
		}
	    int totalCount = (int) stUserService.selectByCount(page,deptid);
		record = stUserService.selectByList(page,deptid);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";
		return result;
	}

	/**
	 * 新增用户
	 * @param record 用户信息
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_user_add")
	@SystemControllerLog(description = "用户管理新增",params = 0)
	public String insert(StUser record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setCREATOR(userVO.getUSERID());
		record.setCREATETIME(new Date());
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATETIME(new Date());
		record.setPASSWORD("670b14728ad9902aecba32e22fa4f6bd");
		stUserService.insertuser(record);
		return "success";
	}
	/**
	 * 修改用户
	 * @param record 用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_user_edit")
	@SystemControllerLog(description = "用户管理修改",params = 0)
	public String update(StUser record) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		record.setUPDATOR(userVO.getUSERID());
		record.setUPDATETIME(new Date());
		stUserService.updateuser(record);
		return "success";
	}

	//状态
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_user_set")
	@SystemControllerLog(description = "用户管理用户状态",params = 0)
	public String updateByState(Long operatorid,String status) throws Exception {
		StUser stUser = new StUser();stUser.setOPERATORID(operatorid);
		stUser.setSTATUS(status.equals("0")?"1":"0");
		stUserService.updateByPrimaryKeySelective(stUser);
		return "success";
	}
	//获取用户权限
	@RequestMapping(value = "/getuserrole", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "用户管理用户权限",params = 0)
	public List<Map> getuserrole(String operatorid) throws Exception {
		String bm=operatorid;
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		if(operatorid.equals("admin")){operatorid=userVO.getOPERATORID().toString();}else {
			bm=userVO.getOPERATORID().toString();
		}
		return stUserService.getuserrole(operatorid,bm);
	}
	//保存权限
	@RequestMapping(value = "/saveuserrole", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("st_user_setrole")
	@SystemControllerLog(description = "用户管理保存权限",params = 0)
	public String saveuserrole(String operatorid,String Roles,String deptusers) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		List<Map> l = new ArrayList<>();
		if(!Roles.equals("")){
			for (String Role: Roles.split(",")) {
				Map m = new HashMap<>();
				m.put("OPERATORID",operatorid);
				m.put("ROLEID",Role);
				m.put("CREATOR",userVO.getUSERID());
				m.put("CREATETIME",new Date());
				l.add(m);
			}
		}
		stUserService.saveuserrole(operatorid,l,deptusers);
		return "success";
	}
	//用户唯一
	@RequestMapping(value = "/useridisone", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "用户管理用户唯一",params = 0)
	public String useridisone(String USERID,String IDENTITY) throws Exception {
		int jhycz = stUserService.useridisone(USERID);
		int sfzycz = stUserService.useridisone(IDENTITY);
		return jhycz==0?sfzycz==0?"success":"sfzycz":"jhycz";
	}

	/**
	 * 导入用户
	 * @param request
	 * @param file 模板文件
     * @return
     */
	@RequestMapping("/importfile")
	@ResponseBody
	@RequiresPermissions("st_user_import")
	@SystemControllerLog(description = "用户管理导入用户",params = 0)
	public Object importfile(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file){
		Subject subject = SecurityUtils.getSubject();
		UserVO userVO = (UserVO) subject.getPrincipal();
		HttpHeaders headers = new HttpHeaders();
		List<ImUser> error = new ArrayList<>();
		try {
			ImportParams param = new ImportParams();
			param.setSheetNum(1);
			param.setTitleRows(1);
			List<ImUser> list = ExcelImportUtil.importExcel(file.getInputStream(), ImUser.class, param);
			error = stUserService.insertusers(list,userVO.getUSERID());
			if(error.size()>0){
				return error;
			}else{
				return "success";
			}
		}catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping("/exportExcel")
	@ResponseBody
	@RequiresPermissions("st_user_import")
	@SystemControllerLog(description = "Excel导出",params = 0)
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response){
		StudentEntity studentEntity = new StudentEntity() ;
		studentEntity.setId("1");
		studentEntity.setName("丽水");

		studentEntity.setSex(2);
		studentEntity.setEn("120");
		studentEntity.setChina("130");
		studentEntity.setYd("122");
		StudentEntity studentEntity1 = new StudentEntity() ;
		studentEntity1.setId("1");
		studentEntity1.setName("丽水");

		studentEntity1.setSex(2);
		studentEntity1.setEn("120");
		studentEntity1.setChina("130");
		studentEntity1.setYd("122");
		List list=  new ArrayList();
		list.add(studentEntity);
		list.add(studentEntity1);
		try {

			Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
					StudentEntity.class, list);
			FileOutputStream fos = new FileOutputStream("f:\\emp.xls");
			workbook.write(fos);
		/*	OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
			out.flush();*/
			return "success";
		}catch (Exception e) {
			return "error";
		}
	}



	@RequestMapping(value = "/dwloadmb", method = RequestMethod.GET)
	@SystemControllerLog(description = "用户信息导入模板下载",params = 0)
	public ResponseEntity<byte[]> dwloadmb(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 下载文件名
		String fileName = "模板.xlsx";
		// 页面下载设置
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gbk"),"iso-8859-1"));
		// 项目路径
		String path = request.getRealPath("/");
		ResponseEntity<byte[]> bEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path+"plugins/down.xlsx")),headers, HttpStatus.OK);
		return bEntity;
	}
}
