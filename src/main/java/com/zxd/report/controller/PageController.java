package com.zxd.report.controller;


import com.zxd.commonmodel.UserVO;
import com.zxd.log.SystemControllerLog;
import com.zxd.report.service.StUserService;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class PageController implements ErrorController{
    @Autowired
    private StUserService stUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @RequestMapping("/")
    public String index(Model model){
        UserVO userVO = (UserVO) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("menus",JSONArray.fromObject(userVO.getMENUS()));
        return "welcome";
    }
    @RequestMapping("/home")
    //@SystemControllerLog(description = "首页",params = 0)
    public String gohome(Model model) {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        model.addAttribute("userVO", userVO);
        String roles="";
        for (Map map:stUserService.getuserrole(userVO.getOPERATORID().toString(),userVO.getOPERATORID().toString())) {
            if(map.get("setROLEID")!=null){
                roles+=map.get("NAME");
                break;
            }
        }
        model.addAttribute("roles", roles);
        model.addAttribute("userount",  stUserService.selectByCount(null,userVO.getDEPTID()));
        return "/sys/home";
    }
    @RequestMapping({"cleanredis"})
    @ResponseBody
    public String cleanredis(String redisname) {
        try {
            redisTemplate.delete("CACHE:"+redisname);
            return "success"; }
        catch (Exception e) {
        }
        return "error";
    }
    /**
     * error页
     */
    @RequestMapping("/error")
    public String error404(HttpServletRequest request) {
        String statusCode = request.getAttribute("javax.servlet.error.status_code").toString();
        if(statusCode == null) {
            if(statusCode == "400"){
                return "400";
            }else if(statusCode == "403"){
                return "403";
            }else if(statusCode == "404"){
                return "404";
            }else{
                return "500";
            }

        }
        return statusCode;
    }
    @Override
    public String getErrorPath() {
        return "error";
    }
}
