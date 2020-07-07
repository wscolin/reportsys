package com.zxd.report.model;

import java.io.Serializable;
import java.util.Date;

public class StUser implements Serializable {
    private Long OPERATORID;//人员id

    private String USERID;//登录账号

    private String PASSWORD;//登录密码

    private String OPERATORNAME;//人员姓名

    private String DEPTID;//部门id

    private String IDENTITY;//身份证号码

    private String TELNO;//手机号

    private String POSITION;//职位

    private String EMAIL;//email

    private String STATUS;//'删除状态 0 ：正常  1 ：已删除 '

    private Date LASTLOGIN;//最近登陆时间

    private String MACCODE;//MAC地址

    private String IPADDRESS;//登陆IP

    private String CREATOR;//创建人

    private Date CREATETIME;//创建时间

    private String UPDATOR;//修改人

    private Date UPDATETIME;//修改时间

    public Long getOPERATORID() {
        return OPERATORID;
    }

    public void setOPERATORID(Long OPERATORID) {
        this.OPERATORID = OPERATORID;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID == null ? null : USERID.trim();
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD == null ? null : PASSWORD.trim();
    }

    public String getOPERATORNAME() {
        return OPERATORNAME;
    }

    public void setOPERATORNAME(String OPERATORNAME) {
        this.OPERATORNAME = OPERATORNAME == null ? null : OPERATORNAME.trim();
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public String getIDENTITY() {
        return IDENTITY;
    }

    public void setIDENTITY(String IDENTITY) {
        this.IDENTITY = IDENTITY == null ? null : IDENTITY.trim();
    }

    public String getTELNO() {
        return TELNO;
    }

    public void setTELNO(String TELNO) {
        this.TELNO = TELNO;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION == null ? null : POSITION.trim();
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL == null ? null : EMAIL.trim();
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    public Date getLASTLOGIN() {
        return LASTLOGIN;
    }

    public void setLASTLOGIN(Date LASTLOGIN) {
        this.LASTLOGIN = LASTLOGIN;
    }

    public String getMACCODE() {
        return MACCODE;
    }

    public void setMACCODE(String MACCODE) {
        this.MACCODE = MACCODE == null ? null : MACCODE.trim();
    }

    public String getIPADDRESS() {
        return IPADDRESS;
    }

    public void setIPADDRESS(String IPADDRESS) {
        this.IPADDRESS = IPADDRESS == null ? null : IPADDRESS.trim();
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR == null ? null : CREATOR.trim();
    }

    public Date getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(Date CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getUPDATOR() {
        return UPDATOR;
    }

    public void setUPDATOR(String UPDATOR) {
        this.UPDATOR = UPDATOR == null ? null : UPDATOR.trim();
    }

    public Date getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(Date UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }
}