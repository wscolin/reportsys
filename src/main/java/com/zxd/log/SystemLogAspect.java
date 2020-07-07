package com.zxd.log;/**
 * Created by Think on 2016/10/14.
 */


import com.zxd.commonmodel.UserVO;
import com.zxd.report.mapper.StLogMapper;
import com.zxd.report.model.StLogWithBLOBs;
import com.zxd.report.service.StParamsService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;

/**
 * 版权声明 中科金审（北京）有限公司 版权所有 违者必究
 * <br> Company：中科金审
 * <br> @author 杜波
 * <br> 2016/10/14
 * <br> @version 1.0
 * 切点类
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final ThreadLocal<Long> startTime = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Autowired
    private StLogMapper stLogMapper;
    @Resource
    private StParamsService stParamsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.zxd.log.SystemControllerLog)")
    public void controllerAspect(){

    }

    /**
     * 前置通知 用户拦截Controller层记录用户操作计算执行时间
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        long beginTime = System.currentTimeMillis();
        startTime.set(beginTime);
        System.out.println("方法名:"+joinPoint.getSignature());
        System.out.println("执行开始时间："+beginTime);
    }

    /**
     * 后置通知 用户拦截Controller层记录用户操作
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        String USERID = userVO.getUSERID();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String classDesc = "";
        String controllerParams = "";
        try {
            String descTmp = getControllerDesc(joinPoint);
            String[] desc = descTmp.split("<");
            classDesc = desc[0];
            String checkParams = desc[1];
            if(checkParams.equals("1")) {
                if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                    controllerParams = JSONObject.fromObject(request.getParameterMap()).toString();
                }

            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("执行结束时间："+System.currentTimeMillis());
        StLogWithBLOBs stLogWithBLOBs = new StLogWithBLOBs();
        stLogWithBLOBs.setUSERID(USERID);
        stLogWithBLOBs.setUSERNAME(userVO.getOPERATORNAME());
        stLogWithBLOBs.setIP_ADDRESS(ip);
        stLogWithBLOBs.setMAC(getmac(ip));
        stLogWithBLOBs.setCREATETIME(new Date());
        stLogWithBLOBs.setCLASS_NAME(joinPoint.getTarget().getClass().getName());
        stLogWithBLOBs.setMETHOD_NAME(joinPoint.getSignature().toString());
        stLogWithBLOBs.setPARAMS(controllerParams);
        stLogWithBLOBs.setREMARKS(classDesc);
        stLogWithBLOBs.setDEPTID(userVO.getDEPTID());
        stLogWithBLOBs.setDEPTNAME(userVO.getDEPTNAME());
        stLogWithBLOBs.setLOG_LEVEL("DEBUG");
      //  stLogWithBLOBs.setYHJGDM(stParamsService.getParamConf("YHJGDM"));
        stLogMapper.insertSelective(stLogWithBLOBs);
    }

    /**
     * 后置异常通知 用户拦截Controller层记录用户操作
     * @param joinPoint 切点
     */
    @AfterThrowing(value = "controllerAspect()" ,throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = (UserVO) subject.getPrincipal();
        String USERID = userVO.getUSERID();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String classDesc = "";
        String controllerParams = "";
        try {
            String descTmp = getControllerDesc(joinPoint);
            String[] desc = descTmp.split("<");
            classDesc = desc[0];
            String checkParams = desc[1];
            if(checkParams.equals("1")) {
                if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                    for (int i = 0; i < joinPoint.getArgs().length; i++) {
                        controllerParams = JSONObject.fromObject(request.getParameterMap()).toString();
                    }
                }
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        StLogWithBLOBs stLogWithBLOBs = new StLogWithBLOBs();
        stLogWithBLOBs.setUSERID(USERID);
        stLogWithBLOBs.setUSERNAME(userVO.getOPERATORNAME());
        stLogWithBLOBs.setIP_ADDRESS(ip);
        stLogWithBLOBs.setMAC(getmac(ip));
        stLogWithBLOBs.setCREATETIME(new Date());
        stLogWithBLOBs.setCLASS_NAME(joinPoint.getTarget().getClass().getName());
        stLogWithBLOBs.setMETHOD_NAME(joinPoint.getSignature().toString());
        stLogWithBLOBs.setPARAMS(controllerParams);
        stLogWithBLOBs.setREMARKS(classDesc);
        stLogWithBLOBs.setDEPTID(userVO.getDEPTID());
        stLogWithBLOBs.setDEPTNAME(userVO.getDEPTNAME());
        stLogWithBLOBs.setLOG_LEVEL("DEBUG");
       // stLogWithBLOBs.setYHJGDM(stParamsService.getParamConf("YHJGDM"));
        stLogMapper.insertSelective(stLogWithBLOBs);
    }

    public String getmac(String ip){
        String mac="";
        String str="";
        if((!ip.equals("127.0.0.1")) && (!ip.equals("0:0:0:0:0:0:0:1"))){
            try {
                Process p = Runtime.getRuntime().exec("nbtstat -A "+ip);
                InputStreamReader ir = new InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                while ((str=br.readLine())!=null){
                    if(str.indexOf("MAC")>1){
                        mac = str.substring(str.indexOf("MAC")+9,str.length()).trim();
                        break;
                    }
                }
                p.destroy();
                br.close();
                ir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                byte[] macb = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<macb.length;i++){
                    if(i!=0){sb.append("-");}
                    String s = Integer.toHexString(macb[i] & 0xFF);
                    sb.append(s.length()==1?0+s:s);
                    mac=sb.toString().trim().toUpperCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mac;
    }

    public static String getControllerDesc(JoinPoint joinPoint) throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String desc = "";
        for(Method method : methods){
            if(method.getName().equals(methodName)){
                Class[] classes = method.getParameterTypes();
                if(classes.length==arguments.length){
                    desc = method.getAnnotation(SystemControllerLog.class).description()+"<"+method.getAnnotation(SystemControllerLog.class).params();
                    break;
                }
            }
        }
        return desc;
    }

}
