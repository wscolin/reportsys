package com.zxd.shiro;

import com.zxd.report.model.Info;
import com.zxd.report.service.InfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private InfoService infoService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权 doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpInfo = new SimpleAuthorizationInfo();
        //获取当前用户的对象
        Subject subject=SecurityUtils.getSubject();
        Info user = (Info)subject.getPrincipal();//获取用户信息
        simpInfo.addStringPermission(user.getPerm());//获取数据库权限
        return simpInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证 doGetAuthorizationInfo");
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken userToken=(UsernamePasswordToken)authenticationToken;//获取登录的信息
        //获取用户名 密码  数据库取
        System.out.println(userToken.getUsername());
        Info query = infoService.queryByName(userToken.getUsername());
        System.out.println(query);
        if(query==null){//没有这个用户
            return null;
        }
        Session session=subject.getSession();//获取用户的session
        session.setAttribute("loginuser",query);

        if(!userToken.getUsername().equals(query.getUsername())){//判断登录的用户名密码 匹配数据库是否正确
            return null;//抛出异常
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(query,query.getPassword(),"");
    }
}
