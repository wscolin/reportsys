package com.zxd.report.controller;

import com.zxd.commonmodel.Page;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StLogWithBLOBs;
import com.zxd.report.service.LogService;
import com.zxd.report.service.StParamsService;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/log")
public class LogController {
	@Autowired
	private LogService logService;
	@Autowired
	private StParamsService stParamsService;
	@RequiresPermissions("st_log_index")
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	//@SystemControllerLog(description = "日志查询页面",params = 0)
	public String index(boolean portal, ModelMap modelMap) {
		return "/sys/log";
	}	

	@RequestMapping(value = "/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "日志查询列表",params = 0)
	public String dataList(HttpServletRequest request, Page page, @RequestParam Map map) throws Exception {
		map.put("search_end",map.get("search_end")+" 23:59:59");
		String orderColumn = "0";
		//定义列名
		String[] cols = {"","USERID", "USERNAME","DEPTNAME", "CLASS_NAME", "REMARKS", "IP_ADDRESS", "CREATETIME"};
		orderColumn = request.getParameter("order[0][column]");
		orderColumn = cols[Integer.parseInt(orderColumn)];
		//获取排序方式 默认为asc
		String orderDir = "DESC";
		orderDir = request.getParameter("order[0][dir]");
		page.setOrderColumn(orderColumn);
		page.setOrderDir(orderDir);

		int totalCount = logService.selectByCount(map);
		List<StLogWithBLOBs> record = logService.selectByList(page,map);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";

		return result;
	}
	@RequiresPermissions("ck_log")
	@RequestMapping(value = "/cklog",method = RequestMethod.GET)
	//@SystemControllerLog(description = "查控请求日志查询页面",params = 0)
	public String cklog(ModelMap modelMap) {
		modelMap.addAttribute("danger_date",stParamsService.getParamConf("danger_date"));
		return "/sys/cklog";
	}
	@RequestMapping(value = "/cklist", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "查控请求日志查询列表",params = 0)
	public String ckdataList(HttpServletRequest request, Page page,@RequestParam Map map) throws Exception {
		map.put("search_end",map.get("search_end")+" 23:59:59");
		String orderColumn = "0";
		//定义列名
		String[] cols = {"","QQDH", "STATE","FLAG", "CREATE_TIME", "UPDATE_TIME", "FK"};
		orderColumn = request.getParameter("order[0][column]");
		orderColumn = cols[Integer.parseInt(orderColumn)];
		//获取排序方式 默认为asc
		String orderDir = "DESC";
		orderDir = request.getParameter("order[0][dir]");
		page.setOrderColumn(orderColumn);
		page.setOrderDir(orderDir);

		int totalCount = logService.selectByCount_ck(map);
		List<Map> record = logService.selectByList_ck(page,map);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";

		return result;
	}
	@RequestMapping(value = "/ckcfqq", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "查控请求重发",params = 0)
	public String ckcfqq(HttpServletRequest request,String qqdh){
		return logService.ckcfqq(qqdh);
	}
	@RequestMapping(value = "/getfks", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "查控请求获取反馈数据",params = 0)
	public String getfks(HttpServletRequest request,Page page,String FKS){
		String orderColumn = "0";
		//定义列名
		String[] cols = {"","QQDH", "STATE","FLAG", "CREATE_TIME", "UPDATE_TIME"};
		orderColumn = request.getParameter("order[0][column]");
		orderColumn = cols[Integer.parseInt(orderColumn)];
		//获取排序方式 默认为asc
		String orderDir = "DESC";
		orderDir = request.getParameter("order[0][dir]");
		page.setOrderColumn(orderColumn);
		page.setOrderDir(orderDir);

		int totalCount = FKS.split(",").length;
		List<Map> record = logService.getfks(page,FKS);
		String result = "{\"recordsTotal\":" + totalCount;
		result += ",\"recordsFiltered\":" + totalCount;
		result += ",\"data\":" + JSONArray.fromObject(record) + "}";

		return result;
	}
	@RequestMapping(value = "/fkpage",method = RequestMethod.GET)
	@SystemControllerLog(description = "日志反馈页面",params = 1)
	public String fkpage(String FKS,ModelMap map) {
		map.addAttribute("FKS",FKS);
		return "/sys/ckfklog";
	}
	/**
	 * 人员查询
	 * @return
	 */
	@RequestMapping("/rycx")
	public String rycx(){
		return "sys/rycx" ;
	}
}
