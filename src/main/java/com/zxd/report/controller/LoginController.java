package com.zxd.report.controller;

import com.zxd.report.model.StUser;
import com.zxd.util.Base64Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class LoginController {
	/***
	 *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String login() {
        return "/loginByName";
    }
	/***
	 *
	 * @param username  用户名
	 * @param password  密码
	 * @param request
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String username, String password, HttpServletRequest request) throws Exception{
		//当用户已经登录手动注销然后到前台重新登陆（防止后退点登录还是之前用户）
		if(SecurityUtils.getSubject().isAuthenticated()){
			SecurityUtils.getSubject().logout();
			StUser stUser = new StUser();stUser.setUSERID(username);stUser.setPASSWORD(Base64Util.decodeData(password));
			request.setAttribute("hasuser",stUser);
		}
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		//根据shiro返回的异常类路径判断，抛出指定异常信息

		if(exceptionClassName!=null){
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				request.setAttribute("error", "用户不存在！");
			}else if (DisabledAccountException.class.getName().equals( exceptionClassName)) {
				request.setAttribute("error", "用户已禁用！");
			}else if (LockedAccountException .class.getName().equals( exceptionClassName)) {
				//request.setAttribute("error", "密码已过期！");
				request.setAttribute("error", "IP未配置,请联系管理员！");
			}else if (IncorrectCredentialsException.class.getName().equals( exceptionClassName)) {
				request.setAttribute("error", "用户或密码不正确！");
			}else{
				request.setAttribute("error", "系统错误！");
			}
		}
		return "loginByName";
	}

}
