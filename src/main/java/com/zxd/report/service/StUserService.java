package com.zxd.report.service;

import com.zxd.report.model.ImIcome;
import com.zxd.commonmodel.ImUser;
import com.zxd.commonmodel.Page;
import com.zxd.report.model.StUser;

import java.util.List;
import java.util.Map;


public interface StUserService {
    /**
     * 获取用户角色
     * @param operatorid 用户id
     * @return
     */
    List<Map> getuserrole(String operatorid, String bm);
    /**
     * 分页总数
     * @param model
     * @param deptid
     * @return
     */
    int selectByCount(Page model, String deptid);
    List<Map> selectByList(Page model, String deptid);
    /**
     * 新增用户
     * @param record  用户实体
     */
    void insertuser(StUser record);
    /**
     * 导入收入表
     * @param imIcome  用户实体
     */
    void insertincome(ImIcome imIcome);
    /**
     * 修改用户
     * @param record  用户实体
     */
    void updateuser(StUser record);
    int updateByPrimaryKeySelective(StUser record);
    /**
     * 保存用户角色
     * @param operatorid
     * @param Roles
     */
    void saveuserrole(String operatorid, List<Map> Roles, String deptusers);
    //判断警号是否存在
    int useridisone(String USERID);
    /**
     * 导入用户
     * @param list 导入实体集合
     * @param userid 导入人
     * @return
     */
    List<ImUser> insertusers(List<ImUser> list, String userid);
}
