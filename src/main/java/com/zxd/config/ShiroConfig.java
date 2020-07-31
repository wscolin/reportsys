package com.zxd.config;


import com.zxd.shiro.CustomRealm;
import com.zxd.shiro.KickoutSessionControlFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private Environment env;
    //shiro redisManager
    @Bean
    public RedisManager getredisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(env.getProperty("shiro.redis.host"));
        redisManager.setPort(Integer.parseInt(env.getProperty("shiro.redis.port")));
        redisManager.setPassword(env.getProperty("shiro.redis.password"));
        redisManager.setExpire(Integer.parseInt(env.getProperty("shiro.redis.expire")));
        return redisManager;
    }
    //缓存管理器
    @Bean
    public RedisCacheManager getredisCacheManager(RedisManager redisManager){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }
    //redisSessionDAO
    @Bean
    public RedisSessionDAO getredisSessionDAO(RedisManager redisManager){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    //会话ID生成器
    @Bean
    public JavaUuidSessionIdGenerator getsessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }
    //会话DAO
    @Bean
    public EnterpriseCacheSessionDAO getsessionDAO(JavaUuidSessionIdGenerator javaUuidSessionIdGenerator){
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator);
        return enterpriseCacheSessionDAO;
    }
    //会话管理器
    //全局的会话信息设置,sessionValidationSchedulerEnabled参数就是是否开启扫描
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(EnterpriseCacheSessionDAO sessionDAO){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        //会话验证调度器  全局的会话信息检测扫描信息间隔
        QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
        quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
        defaultWebSessionManager.setSessionValidationScheduler(quartzSessionValidationScheduler);
        defaultWebSessionManager.setSessionDAO(sessionDAO);
        //设置Cookie名字
        Cookie cookie = new SimpleCookie();cookie.setName("GASUSERID");cookie.setPath("/");
        defaultWebSessionManager.setSessionIdCookie(cookie);
        return defaultWebSessionManager;
    }
    //自定义Realm
    @Bean
    public CustomRealm getCustomRealm(HashedCredentialsMatcher credentialsMatcher){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(credentialsMatcher);
        return customRealm;
    }
    //凭证匹配器
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    //securityManager安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(RedisCacheManager RedisCacheManager,DefaultWebSessionManager sessionManager,CustomRealm customRealm
    ){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(RedisCacheManager);
        return defaultWebSecurityManager;
    }
    //用户踢除 过滤器
    @Bean
    public KickoutSessionControlFilter getKickoutSessionControasdlFilter(RedisCacheManager RedisCacheManager, DefaultWebSessionManager sessionManager){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(RedisCacheManager);
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        return kickoutSessionControlFilter;
    }
    //过滤器集合
    private Map<String,Filter> filterMap = new LinkedHashMap<String,Filter>();
    //过滤规则集合
    private Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSessionManager sessionManager,DefaultWebSecurityManager securityManager,
                                                            KickoutSessionControlFilter kickout){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        //添加过滤器
        filterMap.put("kickout",kickout);
        shiroFilterFactoryBean.setFilters(filterMap);
        //过滤规则
        //资源文件
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/icon/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/javascript/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/plugins/**","anon");
        //暴露接口
        //filterChainDefinitionMap.put("/files/**","anon");//下载请求文件
       // filterChainDefinitionMap.put("/WEB-INF/views/loginByName.jsp","anon");//反馈接口
        //系统controller过滤
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/login","authc");
        filterChainDefinitionMap.put("/**","authc,user");//,kickout
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    //shiro 注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    //shiro 401
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simp = new SimpleMappingExceptionResolver();
        Properties map = new Properties();
        map.setProperty("org.apache.shiro.authz.UnauthorizedException","/401");
        simp.setExceptionMappings(map);
        return simp;
    }
}
