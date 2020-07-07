package com.zxd.report.model;/**
 * Created by Think on 2016/8/18.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * 版权声明 中科金审（北京）有限公司 版权所有 违者必究
 * <br> Company：中科金审
 * <br> @author 杜波
 * <br> 2016/8/18
 * <br> @version 1.0
 * <br> 系统参数表model
 */
public class StParams implements Serializable{

    private String PARM_KEY;

    private String PARM_VALUE;

    private String REMARKS;

    private String CREATOR;

    private String CREATOR_NAME;

    private Date CREATE_TIME;

    private String UPDATOR;

    private String UPDATOR_NAME;

    private Date UPDATE_TIME;

    public String getPARM_KEY() {
        return PARM_KEY;
    }

    public void setPARM_KEY(String PARM_KEY) {
        this.PARM_KEY = PARM_KEY == null ? null : PARM_KEY.trim();
    }

    public String getPARM_VALUE() {
        return PARM_VALUE;
    }

    public void setPARM_VALUE(String PARM_VALUE) {
        this.PARM_VALUE = PARM_VALUE == null ? null : PARM_VALUE.trim();
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS == null ? null : REMARKS.trim();
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR == null ? null : CREATOR.trim();
    }

    public String getCREATOR_NAME() {
        return CREATOR_NAME;
    }

    public void setCREATOR_NAME(String CREATOR_NAME) {
        this.CREATOR_NAME = CREATOR_NAME == null ? null : CREATOR_NAME.trim();
    }

    public Date getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(Date CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getUPDATOR() {
        return UPDATOR;
    }

    public void setUPDATOR(String UPDATOR) {
        this.UPDATOR = UPDATOR == null ? null : UPDATOR.trim();
    }

    public String getUPDATOR_NAME() {
        return UPDATOR_NAME;
    }

    public void setUPDATOR_NAME(String UPDATOR_NAME) {
        this.UPDATOR_NAME = UPDATOR_NAME == null ? null : UPDATOR_NAME.trim();
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
