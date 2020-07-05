package com.zxd.shiro;


import com.zxd.commonmodel.UserVO;
import com.zxd.report.model.StUser;
import com.zxd.report.service.ShiroService;
import com.zxd.util.Base64Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
	@Autowired
	private ShiroService shiroService;


	/**
	 * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {	
		UserVO userVO = (UserVO) principals.getPrimaryPrincipal();
		//用户查控权限
		List<String> permissions = new ArrayList<String>();
		JSONArray jsonArray = JSONArray.fromObject(userVO.getPERMISSIONS());
		for(int i = 0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			permissions.add(jsonObject.get("RES_KEY").toString());
		}
		//查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;

	}

	/**
	 * 认证回调函数,登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		String username = authcToken.getUsername();
		String IP = authcToken.getHost();
		StUser sysUser = null;
		try {
			sysUser = shiroService.selectByuserid(username);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        if(sysUser == null) {
        	return null;
        }
        if(sysUser.getSTATUS().equals("1")) {
        	throw new DisabledAccountException(); //用户已禁用
        }
		//非数字证书验证ip
		if(shiroService.getipbyuser(IP)==0 && (!IP.equals("127.0.0.1")) && (!IP.equals("0:0:0:0:0:0:0:1"))){
			throw new LockedAccountException();//IP未配置
		}
		String password = sysUser.getPASSWORD();
		authcToken.setPassword(Base64Util.decodeData(String.valueOf(authcToken.getPassword())).toCharArray());
		//查询用户缓存信息
		UserVO userVO  = shiroService.getUserVo(sysUser.getUSERID());
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userVO, password, this.getName());
		return simpleAuthenticationInfo;
	}
	
	//更新用户授权信息缓存.
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	//更新用户信息缓存.
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	//清除用户授权信息缓存.
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	//清除用户信息缓存.
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}
	
	//清空所有缓存
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	//清空所有认证缓存
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}