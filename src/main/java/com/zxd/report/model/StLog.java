package com.zxd.report.model;

import java.util.Date;

public class StLog {
    private Integer ID;

    private String YHJGDM;

    private String USERID;

    private String USERNAME;

    private String DEPTID;

    private String DEPTNAME;

    private String CLASS_NAME;

    private String METHOD_NAME;

    private String IP_ADDRESS;

    private String MAC;

    private String LOG_LEVEL;

    private Date CREATETIME;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getYHJGDM() {
        return YHJGDM;
    }

    public void setYHJGDM(String YHJGDM) {
        this.YHJGDM = YHJGDM == null ? null : YHJGDM.trim();
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID == null ? null : USERID.trim();
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME == null ? null : USERNAME.trim();
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID == null ? null : DEPTID.trim();
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME == null ? null : DEPTNAME.trim();
    }

    public String getCLASS_NAME() {
        return CLASS_NAME;
    }

    public void setCLASS_NAME(String CLASS_NAME) {
        this.CLASS_NAME = CLASS_NAME == null ? null : CLASS_NAME.trim();
    }

    public String getMETHOD_NAME() {
        return METHOD_NAME;
    }

    public void setMETHOD_NAME(String METHOD_NAME) {
        this.METHOD_NAME = METHOD_NAME == null ? null : METHOD_NAME.trim();
    }

    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public void setIP_ADDRESS(String IP_ADDRESS) {
        this.IP_ADDRESS = IP_ADDRESS == null ? null : IP_ADDRESS.trim();
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC == null ? null : MAC.trim();
    }

    public String getLOG_LEVEL() {
        return LOG_LEVEL;
    }

    public void setLOG_LEVEL(String LOG_LEVEL) {
        this.LOG_LEVEL = LOG_LEVEL == null ? null : LOG_LEVEL.trim();
    }

    public Date getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(Date CREATETIME) {
        this.CREATETIME = CREATETIME;
    }
}