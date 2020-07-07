package com.zxd.report.controller;

import com.zxd.commonmodel.UserVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cgf on 2017/8/14.
 */
@Controller
@RequestMapping(value = "/police")
public class PolicepageController {
    /**
     * 电话查询
     * @param model
     * @param request
     * @param identity
     * @return
     */
    @RequestMapping("/photo")
    public String queryByPhoto(Model model, HttpServletRequest request, String identity){
        if(null != identity){
             UserVO us = (UserVO)SecurityUtils.getSubject().getPrincipal();
             Map map = new HashMap();
            map.put("khsjhm","13212223211");
            //生成请求包




        }
        return "police/queryByPhoto";
    }

    /**
     *  证件照查询
     * @param model
     * @param request
     * @param identity
     * @return
     */
    @RequestMapping("/certificate")
    public String queryCertificate(Model model, HttpServletRequest request, String identity){
        if(null != identity){
            SecurityUtils.getSubject().getPrincipal();
        }
        return "police/queryByZj";
    }
}
