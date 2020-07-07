package com.zxd.report.service.impl;

import com.zxd.commonmodel.ImUser;
import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StExcelMapper;
import com.zxd.report.mapper.StUserMapper;
import com.zxd.report.model.ImIcome;
import com.zxd.report.model.StUser;
import com.zxd.report.service.StUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class StUserServiceImpl implements StUserService {
    @Autowired
    private StExcelMapper stExcelMapper;

    public StExcelMapper getStExcelMapper() {
        return stExcelMapper;
    }

    public void setStExcelMapper(StExcelMapper stExcelMapper) {
        this.stExcelMapper = stExcelMapper;
    }

    @Autowired
    private StUserMapper stUserMapper;
    @Override
    public List<Map> getuserrole(String operatorid, String bm) {
        return stUserMapper.getuserrole(operatorid,bm);
    }

    @Override
    public int selectByCount(Page model, String deptid) {
        if(model!=null){
            if(model.getSearchValue()==null?false:!model.getSearchValue().equals("")){
                String deptids = stUserMapper.whileIdsbyPid(deptid);
                return stUserMapper.selectByCount(model,deptids.equals("$")?"":deptids);
            }else{
                return stUserMapper.selectByCount(model,deptid);
            }
        }else{
            return stUserMapper.selectByCount(model,deptid);
        }
    }

    @Override
    public List<Map> selectByList(Page model, String deptid) {
        if(model.getSearchValue()==null?false:!model.getSearchValue().equals("")){
            String deptids = stUserMapper.whileIdsbyPid(deptid);
            return stUserMapper.selectByList(model,deptids.equals("$")?"":deptids);
        }else{
            return stUserMapper.selectByList(model,deptid);
        }
    }

    @Override
    public void insertuser(StUser record) {
        stUserMapper.insertSelective(record);
    }



    @Override
    public void updateuser(StUser record) {
        stUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(StUser record) {
        return stUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void saveuserrole(String operatorid, List<Map> Roles, String deptusers) {
        if(deptusers==null){
            stUserMapper.deleteuserrole(operatorid);
            if(Roles.size()>0){
                stUserMapper.saveuserrole(Roles);
            }
        }else{
            //不删除用户权限增量部门增量授权
            //itemMapper.deleteuserrolebydeptid(deptusers);
            if(Roles.size()>0){
                stUserMapper.saveuserrolebydeptid(Roles,deptusers);
            }
        }
    }

    @Override
    public int useridisone(String USERID) {
        return stUserMapper.useridisone(USERID);
    }

    @Override
    public List<ImUser> insertusers(List<ImUser> list, String userid) {
        List<ImUser> error = new ArrayList<>();
        for (ImUser user:list) {
            try {
                StUser stUser = new StUser();
                stUser.setDEPTID(user.getDEPTID());
                stUser.setOPERATORNAME(user.getOPERATORNAME());
                stUser.setUSERID(user.getUSERID());
                stUser.setIDENTITY(user.getIDENTITY());
                stUser.setCREATOR(userid);
                stUser.setCREATETIME(new Date());
                stUser.setUPDATOR(userid);
                stUser.setUPDATETIME(new Date());
                stUser.setTELNO(user.getTELNO());
                stUser.setPASSWORD("670b14728ad9902aecba32e22fa4f6bd");
                stUserMapper.insertSelective(stUser);
            }catch (Exception e) {
                e.printStackTrace();
                error.add(user);
            }
        }
        return error;
    }

    @Override
    public void insertincome(ImIcome imIcome) {
        stExcelMapper.insertSelective(imIcome);
    }

}
