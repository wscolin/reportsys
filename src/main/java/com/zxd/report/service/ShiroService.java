package com.zxd.report.service;

import com.zxd.commonmodel.UserVO;
import com.zxd.report.model.StUser;

/**
 *
 */
public interface ShiroService {
    StUser selectByuserid(String userid);
    int getipbyuser(String IP);
    UserVO getUserVo(String userid);
}
