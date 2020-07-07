package com.zxd.report.model;

import java.util.Date;

public class StResource {
    private Integer RESOURCEID;

    private Integer PARENTID;

    private String NAME;

    private String RES_KEY;

    private String ACTION;

    private String DESCN;

    private String ENABLED;

    private String TYPE;

    private String ICONCLS;

    private String ISLEAF;

    private Integer THESORT;

    private String CREATOR;

    private Date CREATE_TIME;

    private String UPDATOR;

    private Date UPDATE_TIME;

    public Integer getRESOURCEID() {
        return RESOURCEID;
    }

    public void setRESOURCEID(Integer RESOURCEID) {
        this.RESOURCEID = RESOURCEID;
    }

    public Integer getPARENTID() {
        return PARENTID;
    }

    public void setPARENTID(Integer PARENTID) {
        this.PARENTID = PARENTID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME == null ? null : NAME.trim();
    }

    public String getRES_KEY() {
        return RES_KEY;
    }

    public void setRES_KEY(String RES_KEY) {
        this.RES_KEY = RES_KEY == null ? null : RES_KEY.trim();
    }

    public String getACTION() {
        return ACTION;
    }

    public void setACTION(String ACTION) {
        this.ACTION = ACTION == null ? null : ACTION.trim();
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

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE == null ? null : TYPE.trim();
    }

    public String getICONCLS() {
        return ICONCLS;
    }

    public void setICONCLS(String ICONCLS) {
        this.ICONCLS = ICONCLS == null ? null : ICONCLS.trim();
    }

    public String getISLEAF() {
        return ISLEAF;
    }

    public void setISLEAF(String ISLEAF) {
        this.ISLEAF = ISLEAF == null ? null : ISLEAF.trim();
    }

    public Integer getTHESORT() {
        return THESORT;
    }

    public void setTHESORT(Integer THESORT) {
        this.THESORT = THESORT;
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