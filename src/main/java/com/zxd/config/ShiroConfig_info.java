package com.zxd.config;


import com.zxd.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig_info {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        /**
         * shiro的内置过滤器
         anon：无需认证就可以访问 默认
         authc：必须认证了才能访问
         user：必须拥有记住我功能才能访问
         perms：必须拥有对某个的权限才能访问
         role：拥有某个角色权限才能访问
         */
        Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();//拦截
        filterChainDefinitionMap.put("/add","authc");// /add请求必须认证才能访问
        filterChainDefinitionMap.put("/del","authc");//del必须认证才能访问
        // filterChainDefinitionMap.put("user/**","authc");//支持通配符
        //授权
        filterChainDefinitionMap.put("/add","perms[user:add]");//没有这个user:add权限的会被拦截下来
        filterChainDefinitionMap.put("/del","perms[user:delete]");//没有这个user:delete权限的会被拦截下来
        //未授权的跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/Unauthorized");
        //设置注销的url
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);//把设置好的过滤设置到ShiroFilterFactoryBean
        return shiroFilterFactoryBean;

    }
    //2. DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm对象  userRealm
        securityManager.setRealm(userRealm);
        return  securityManager;
    }
    //1. 创建realm对象 自定义的·类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合shiroDialect：用来整合shiro-thymeleaf
  /*  @Bean
    public ShiroDialect getshiroDialect(){
        return new  ShiroDialect();
    }*/
}
