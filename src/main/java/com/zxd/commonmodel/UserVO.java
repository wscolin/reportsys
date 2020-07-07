package com.zxd.commonmodel;

import java.io.Serializable;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long OPERATORID;

	private String OPERATORNAME;

	private String DEPTID;

	private String DEPTNAME;

	private String PASSWORD;

	private String USERID;

	private String IDENTITY;

	private String POSITION;

	private String TELNO;

	private String STATUS;

	private String MENUS;// 菜单

	private String PERMISSIONS;// 操作权限

	private String DATA_PERMISSIONS;// 数据权限    解析实体类  PermissionBean

	public Long getOPERATORID() {
		return OPERATORID;
	}

	public void setOPERATORID(Long oPERATORID) {
		OPERATORID = oPERATORID;
	}

	public String getOPERATORNAME() {
		return OPERATORNAME;
	}

	public void setOPERATORNAME(String oPERATORNAME) {
		OPERATORNAME = oPERATORNAME;
	}

	public String getDEPTID() {
		return DEPTID;
	}

	public void setDEPTID(String dEPTID) {
		DEPTID = dEPTID;
	}

	public String getDEPTNAME() {
		return DEPTNAME;
	}

	public void setDEPTNAME(String dEPTNAME) {
		DEPTNAME = dEPTNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getIDENTITY() {
		return IDENTITY;
	}

	public void setIDENTITY(String iDENTITY) {
		IDENTITY = iDENTITY;
	}

	public String getPOSITION() {
		return POSITION;
	}

	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getMENUS() {
		return MENUS;
	}

	public void setMENUS(String mENUS) {
		MENUS = mENUS;
	}

	public String getPERMISSIONS() {
		return PERMISSIONS;
	}

	public void setPERMISSIONS(String pERMISSIONS) {
		PERMISSIONS = pERMISSIONS;
	}

	public String getTELNO() {
		return TELNO;
	}

	public void setTELNO(String TELNO) {
		this.TELNO = TELNO;
	}

	public String getDATA_PERMISSIONS() {
		return DATA_PERMISSIONS;
	}

	public void setDATA_PERMISSIONS(String DATA_PERMISSIONS) {
		this.DATA_PERMISSIONS = DATA_PERMISSIONS;
	}
}