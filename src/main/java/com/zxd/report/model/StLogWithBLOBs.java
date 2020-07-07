package com.zxd.report.model;

public class StLogWithBLOBs extends StLog {
    private String PARAMS;

    private String REMARKS;

    public String getPARAMS() {
        return PARAMS;
    }

    public void setPARAMS(String PARAMS) {
        this.PARAMS = PARAMS == null ? null : PARAMS.trim();
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS == null ? null : REMARKS.trim();
    }
}