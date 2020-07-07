package com.zxd.commonmodel;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.io.Serializable;

/**
 *
 */
@ExcelTarget("ImUser")
public class ImUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Excel(name = "机构编码", orderNum = "1")
    private String DEPTID;

    @Excel(name = "机构名称", orderNum = "2")
    private String DEPTNAME;

    @Excel(name = "管理员姓名", orderNum = "3")
    private String OPERATORNAME;

    @Excel(name = "用户名", orderNum = "4")
    private String USERID;

    @Excel(name = "身份证", orderNum = "5")
    private String IDENTITY;

    @Excel(name = "电话号码", orderNum = "6")
    private String TELNO;

    public String getTELNO() {
        return TELNO;
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public String getOPERATORNAME() {
        return OPERATORNAME;
    }

    public String getUSERID() {
        return USERID;
    }

    public String getIDENTITY() {
        return IDENTITY;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public void setOPERATORNAME(String OPERATORNAME) {
        this.OPERATORNAME = OPERATORNAME;
    }

    public void setUSERID(String USERID) {
        //科学计数转字符串
//        BigDecimal bd = new BigDecimal(USERID);
//        this.USERID = bd.toPlainString();
        this.USERID = USERID;
    }

    public void setIDENTITY(String IDENTITY) {
        this.IDENTITY = IDENTITY;
    }

    public void setTELNO(String TELNO) {
        this.TELNO=TELNO;
    }
}
