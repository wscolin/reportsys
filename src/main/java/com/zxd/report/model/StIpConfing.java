package com.zxd.report.model;

import java.io.Serializable;
import java.util.Date;

public class StIpConfing implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer ID;

    private String IPADDRESS;

    private String USERNAME;

    private String STATE;

    private String REMARKS;

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

    public String getIPADDRESS() {
        return IPADDRESS;
    }

    public void setIPADDRESS(String IPADDRESS) {
        this.IPADDRESS = IPADDRESS == null ? null : IPADDRESS.trim();
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME == null ? null : USERNAME.trim();
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE == null ? null : STATE.trim();
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