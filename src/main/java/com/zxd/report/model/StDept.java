package com.zxd.report.model;

import java.util.Date;

public class StDept {
    private String DEPTID;

    private String NAME;

    private String DESCN;

    private String PARENTID;

    private Long THESORT;

    private String STATUS;

    private String CREATOR;

    private Date CREATE_TIME;

    private String UPDATOR;

    private Date UPDATE_TIME;

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
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

    public String getPARENTID() {
        return PARENTID;
    }

    public void setPARENTID(String PARENTID) {
        this.PARENTID = PARENTID == null ? null : PARENTID.trim();
    }

    public Long getTHESORT() {
        return THESORT;
    }

    public void setTHESORT(Long THESORT) {
        this.THESORT = THESORT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR == null ? null : CREATOR.trim();
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

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}