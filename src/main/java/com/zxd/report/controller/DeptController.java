package com.zxd.report.controller;

import com.zxd.commonmodel.PermissionBean;
import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.model.StDept;
import com.zxd.report.service.StDeptService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    private StDeptService itemService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @RequiresPermissions("st_dept_index")
    @SystemControllerLog(description = "部门管理页面",params = 0)
    public String index(ModelMap map) {
        return "/sys/dept";
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理树",params = 0)
    public List<Map> tree(String id) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        return itemService.selectDepttree(id, itemService.seldeptByper(userVO.getDEPTID(), PermissionBean.getEcuserPermission(userVO)));
    }

    @RequestMapping(value = "/getdeptbyid", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理查询部门信息 返回map包含父部门名称 PARENTNAME",params = 0)
    public Map getdeptbyid(String id) throws Exception {
        return itemService.getdeptbyid(id);
    }

    //新增
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理新增",params = 0)
    public String insert(StDept record) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        record.setCREATOR(userVO.getUSERID());
        record.setCREATE_TIME(new Date());
        record.setUPDATOR(userVO.getUSERID());
        record.setUPDATE_TIME(new Date());
        int result = itemService.insertSelective(record);
        if (result > 0) {
            return "success";
        } else {
            return "failure";
        }
    }

    //更新
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理修改",params = 0)
    public String update(StDept record) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        record.setUPDATOR(userVO.getUSERID());
        record.setUPDATE_TIME(new Date());
        int result = itemService.updateByPrimaryKeySelective(record);
        if (result > 0) {
            return "success";
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "/bankcodeisone", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理是否唯一",params = 0)
    public String bankcodeisone(String deptid) throws Exception {
        if (itemService.selectByPrimaryKey(deptid) == null) {
            return "success";
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "/sortup", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理排序上移",params = 0)
    public String sortup(String id, String pId) throws Exception {
        String data = "";
        List<StDept> stdepts = itemService.selectbyfatherId(pId);
        for (int i = 0; i < stdepts.size(); i++) {
            if (stdepts.get(i).getDEPTID().equals(id)) {
                if (i == 0) {
                    data = "nomove";
                } else {
                    StDept yhtype = new StDept();
                    yhtype.setDEPTID(stdepts.get(i).getDEPTID());
                    yhtype.setTHESORT(stdepts.get(i - 1).getTHESORT());
                    itemService.updateByPrimaryKeySelective(yhtype);
                    StDept yhtypeT = new StDept();
                    yhtypeT.setDEPTID(stdepts.get(i - 1).getDEPTID());
                    yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
                    itemService.updateByPrimaryKeySelective(yhtypeT);
                    data = "success";
                }
            }
        }
        return data;
    }

    @RequestMapping(value = "/sortdown", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理排序下移",params = 0)
    public String sortdown(String id, String pId) throws Exception {
        String data = "";
        List<StDept> stdepts = itemService.selectbyfatherId(pId);
        for (int i = 0; i < stdepts.size(); i++) {
            if (stdepts.get(i).getDEPTID().equals(id)) {
                if (i == stdepts.size() - 1) {
                    data = "nomove";
                } else {
                    StDept yhtype = new StDept();
                    yhtype.setDEPTID(stdepts.get(i).getDEPTID());
                    yhtype.setTHESORT(stdepts.get(i + 1).getTHESORT());
                    itemService.updateByPrimaryKeySelective(yhtype);
                    StDept yhtypeT = new StDept();
                    yhtypeT.setDEPTID(stdepts.get(i + 1).getDEPTID());
                    yhtypeT.setTHESORT(stdepts.get(i).getTHESORT());
                    itemService.updateByPrimaryKeySelective(yhtypeT);
                    data = "success";
                }
            }
        }
        return data;
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "部门管理设置状态",params = 0)
    public String set(String id, String enabled) throws Exception {
        StDept temp = new StDept();
        temp.setDEPTID(id);
        temp.setSTATUS(enabled.equals("1") ? "0" : "1");
        itemService.updateByPrimaryKeySelective(temp);
        return "success";
    }
}
