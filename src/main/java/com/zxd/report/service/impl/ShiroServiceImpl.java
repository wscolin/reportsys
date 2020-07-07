package com.zxd.report.service.impl;

import com.zxd.commonmodel.MenuNode;
import com.zxd.commonmodel.PowerNode;
import com.zxd.commonmodel.UserVO;
import com.zxd.report.mapper.StDeptMapper;
import com.zxd.report.mapper.StIpConfingMapper;
import com.zxd.report.mapper.StResourceMapper;
import com.zxd.report.mapper.StUserMapper;
import com.zxd.report.model.StResource;
import com.zxd.report.model.StUser;
import com.zxd.report.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class ShiroServiceImpl implements ShiroService {
    @Resource
    private StUserMapper stUserMapper;
    @Autowired
    private StIpConfingMapper stIpConfingMapper;
    @Autowired
    private StDeptMapper stDeptMapper;
    @Autowired
    private StResourceMapper stResourceMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public StUser selectByuserid(String userid) {
        StUser sysUser = null;
        try {
            sysUser = stUserMapper.selectByPrimaryKey(userid);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if(sysUser == null) {
            return null;//用户不存在
        }
        return sysUser;
    }

    @Override
    public int getipbyuser(String IP) {
        return stIpConfingMapper.getipbyuser(IP);
    }

    @Override
    public UserVO getUserVo(String userid) {
        UserVO userVo = new UserVO();
        StUser stUser = selectByuserid(userid);
        if(stUser!=null){
            userVo.setOPERATORID(stUser.getOPERATORID());
            userVo.setOPERATORNAME(stUser.getOPERATORNAME());
            userVo.setDEPTID(stUser.getDEPTID());
            userVo.setDEPTNAME(stDeptMapper.selectByPrimaryKey(stUser.getDEPTID()).getNAME());
            userVo.setPASSWORD(stUser.getPASSWORD());
            userVo.setUSERID(stUser.getUSERID());
            userVo.setIDENTITY(stUser.getIDENTITY());
            userVo.setPOSITION(stUser.getPOSITION());
            userVo.setSTATUS(stUser.getSTATUS());
            userVo.setMENUS(getUserRoleForMenu(userid));
            userVo.setPERMISSIONS(getUserRoleForCZ(userid));
            //userVo.setDATA_PERMISSIONS(getUserRoleForDATA(userid));
            userVo.setTELNO(stUser.getTELNO());
            return userVo;
        }else{
            return null;
        }
    }
    //菜单权限
    private String getUserRoleForMenu(String USERID) {
        StringBuffer result = new StringBuffer();
        List<StResource> resourcesList = stResourceMapper.selectListByUser(USERID,"0");
        HashMap nodeList = new HashMap();
        MenuNode root = null;
        for(Iterator it = resourcesList.iterator(); it.hasNext();){
            StResource resource = (StResource)it.next();
            MenuNode node = new MenuNode();
            node.setID(resource.getRESOURCEID().toString());
            node.setACTION(resource.getACTION());
            node.setICONCLS(resource.getICONCLS());
            node.setNAME(resource.getNAME());
            node.setPARENTID(resource.getPARENTID().toString());
            node.setTHESORT(resource.getTHESORT());
            nodeList.put(node.getID(),node);
        }

        MenuNode tmpNode = new MenuNode();
        tmpNode.setID("0");
        tmpNode.setNAME("gas_bank");
        tmpNode.setPARENTID("");
        nodeList.put("0",tmpNode);
        Set entrySet = nodeList.entrySet();
        for(Iterator it = entrySet.iterator();it.hasNext();){
            MenuNode node = (MenuNode)((Map.Entry)it.next()).getValue();
            if(node.getPARENTID()==null || node.getPARENTID().equals("")){
                root=node;
            }else{
                ((MenuNode)nodeList.get(node.getPARENTID())).addChildren(node);
            }
        }
        root.sortChildren();
        return root.getChildren();
    }
    //操作权限
    private String getUserRoleForCZ(String USERID) {
        List<StResource> resourcesList = stResourceMapper.selectListByUser(USERID,"1");
        List<StResource> resourcesList1 = stResourceMapper.selectListByUser(USERID,"0");
        resourcesList.addAll(resourcesList1);
        StringBuffer resultBuffer = new StringBuffer("[");
        for(StResource st:resourcesList){
            if(st.getRES_KEY()!=null && !"".equals(st.getRES_KEY())){
                resultBuffer.append("{\"RES_KEY\":'").append(st.getRES_KEY()).append("'},");
            }
        }
        String result = null;
        if(resultBuffer.toString().equals("[")){
            result = resultBuffer.toString()+"]";
        }else{
            result = resultBuffer.toString().substring(0,resultBuffer.toString().length()-1)+"]";
        }

        return result;
    }
    //数据权限
    private String getUserRoleForDATA(String USERID) {
        List<StResource> resourcesList = stResourceMapper.selectListByUser(USERID,"2");
        HashMap nodeList = new HashMap();
        PowerNode root = null;
        for(Iterator it = resourcesList.iterator();it.hasNext();){
            StResource resource = (StResource)it.next();
            PowerNode node = new PowerNode();
            node.setID(resource.getRESOURCEID().toString());
            node.setNAME(resource.getNAME());
            node.setRES_KEY(resource.getRES_KEY());
            node.setACTION(resource.getACTION());
            node.setICONCLS(resource.getICONCLS());
            node.setPARENTID(resource.getPARENTID().toString());
            node.setTHESORT(resource.getTHESORT());
            nodeList.put(node.getID(),node);
        }

        PowerNode tmpNode = new PowerNode();
        tmpNode.setID("0");
        tmpNode.setNAME("gas_bank");
        tmpNode.setPARENTID("");
        nodeList.put(tmpNode.getID(),tmpNode);

        Set entrySet = nodeList.entrySet();
        for(Iterator it = entrySet.iterator();it.hasNext();){
            PowerNode node = (PowerNode)((Map.Entry)it.next()).getValue();
            if(node.getPARENTID()==null || node.getPARENTID().equals("")){
                root=node;
            }else{
                ((PowerNode)nodeList.get(node.getPARENTID())).addChildren(node);
            }
        }
        root.sortChildren();
        return root.getChildren();
    }
}
