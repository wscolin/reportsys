package com.zxd.report.model;

import java.io.Serializable;

public class StRoleResource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer ROLEID;

    private Integer RESOURCEID;

    public Integer getROLEID() {
        return ROLEID;
    }

    public void setROLEID(Integer ROLEID) {
        this.ROLEID = ROLEID;
    }

    public Integer getRESOURCEID() {
        return RESOURCEID;
    }

    public void setRESOURCEID(Integer RESOURCEID) {
        this.RESOURCEID = RESOURCEID;
    }
}