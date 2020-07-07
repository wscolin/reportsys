package com.zxd.report.model;

import java.io.Serializable;
import java.util.Date;

public class StDictionary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer ID;

    private String ITEM_CODE;

    private String ITEM_NAME;

    private String DESCN;

    private Integer PARENT_ID;

    private String FLAG;

    private String ENABLED;

    private Integer THESORT;

    private String CREATOR;

    private Date CREATE_TIME;

    private String UPDATOR;

    private Date UPDATE_TIME;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE == null ? null : ITEM_CODE.trim();
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME == null ? null : ITEM_NAME.trim();
    }

    public String getDESCN() {
        return DESCN;
    }

    public void setDESCN(String DESCN) {
        this.DESCN = DESCN == null ? null : DESCN.trim();
    }

    public Integer getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(Integer PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG == null ? null : FLAG.trim();
    }

    public String getENABLED() {
        return ENABLED;
    }

    public void setENABLED(String ENABLED) {
        this.ENABLED = ENABLED == null ? null : ENABLED.trim();
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