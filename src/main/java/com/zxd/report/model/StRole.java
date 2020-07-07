package com.zxd.report.model;

import java.util.Date;

public class StRole {
    private Integer ROLEID;

    private String NAME;

    private String DESCN;

    private String ENABLED;

    private String CREATOR;

    private Date CREATETIME;

    private String UPDATOR;

    private Date UPDATETIME;

    public Integer getROLEID() {
        return ROLEID;
    }

    public void setROLEID(Integer ROLEID) {
        this.ROLEID = ROLEID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME == null ? null : NAME.trim();
    }

    public String getDESCN() {
        return DESCN;
    }

    public void setDESCN(String DESCN) {
        this.DESCN = DESCN == null ? null : DESCN.trim();
    }

    public String getENABLED() {
        return ENABLED;
    }

    public void setENABLED(String ENABLED) {
        this.ENABLED = ENABLED == null ? null : ENABLED.trim();
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