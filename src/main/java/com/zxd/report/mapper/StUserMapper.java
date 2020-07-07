package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface StUserMapper {
    int insertSelective(StUser record);
    int updateByPrimaryKeySelective(StUser record);
    StUser selectByPrimaryKey(String USERID);
    List<Map> getuserrole(@Param("operatorid") String operatorid, @Param("bm") String bm);
    //分页查询
    List<Map> selectByList(@Param("model") Page model, @Param("deptids") String deptids);
    //总记录数
    int selectByCount(@Param("model") Page model, @Param("deptids") String deptids);
    /**
     * 递归查询部门下的所有部门
     * @param deptid 部门id
     * @return
     */
    String whileIdsbyPid(@Param("deptid") String deptid);
    /**
     * 递归查询部门所有上级部门
     * @param deptid 部门id
     * @return
     */
    String whilePidsbyId(@Param("deptid") String deptid);
    void deleteuserrole(@Param("operatorid") String operatorid);
    void  saveuserrole(@Param("roles") List<Map> roles);
    void  saveuserrolebydeptid(@Param("roles") List<Map> roles, @Param("deptid") String deptid);
    //判断警号是否存在
    int useridisone(String USERID);
}