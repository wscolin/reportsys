package com.zxd.report.controller;

import com.zxd.report.model.Info;
import com.zxd.report.service.InfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    public InfoService infoService;
    @RequestMapping("/hello")
    public String  Hello() {
        log.debug("hello");
        return "hello";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/showUser")
    @ResponseBody
    public void showUser(){
        Info info =  infoService.queryByName("zs");
        System.out.println(info.getUsername());
    }

    @RequestMapping("/login1")
    public String login(String username,String password){
        try {
            //获取当前的用户
            Subject subject = SecurityUtils.getSubject();
            //封装用户的登录数据
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            subject.login(usernamePasswordToken);//执行登录的方法 没有异常就成功了
            return "index";
        } catch (UnknownAccountException e) {
            /**
             * 异常信息
             * UnknownAccountException ：用户名不存在
             * IncorrectCredentialsException：密码错误
             */
            e.printStackTrace();
            System.out.println("用户名不存在");
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
        }
        return "login";
    }

    @RequestMapping("/add")
    public String add(){//跳转页面
        return "add";
    }
    @RequestMapping("/del")
    public String delete(){//跳转页面
        return "delete";
    }
    @RequestMapping("/Unauthorized")
    public String Unauthorized(){//没有权限跳转的url
        return "Unauthorized";
    }
    //注销
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginuser",null);//清空session
        return "login";
    }
}
