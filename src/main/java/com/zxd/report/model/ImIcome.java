package com.zxd.report.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 *
 * @description:
 * @author:
 * @create: 2020-06-24 16:06
 **/
public class ImIcome {
    private static final long serialVersionUID = 1L;
    @Excel(name = "科目编码", orderNum = "1")
    private String KMBM;

    @Excel(name = "科目名称", orderNum = "2")
    private String KMMC;

    @Excel(name = "金额", orderNum = "3")
    private String AMT;

    @Excel(name = "市直", orderNum = "4")
    private String SZ;
    @Excel(name = "高新区", orderNum = "5")
    private String GXQ;
    @Excel(name = "临川区", orderNum = "6")
    private String LCQ;
    @Excel(name = "东乡区", orderNum = "7")
    private String DXQ;
    @Excel(name = "南城县", orderNum = "8")
    private String NCX;
    @Excel(name = "南丰县", orderNum = "9")

    private String NFX;
    @Excel(name = "黎川县", orderNum = "10")

    private String LCX;
    @Excel(name = "崇仁县", orderNum = "11")

    private String CRX;
    @Excel(name = "宜黄县", orderNum = "12")

    private String YHX;
    @Excel(name = "乐安县", orderNum = "13")

    private String LAX;
    @Excel(name = "金溪县", orderNum = "14")

    private String JXX;
    @Excel(name = "资溪县", orderNum = "15")

    private String ZXX;
    @Excel(name = "广昌县", orderNum = "16")
    private String GCX;
    private String  TYPE;
    @Excel(name = "年份", orderNum = "17")
    private String YEAR;

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKMBM() {
        return KMBM;
    }

    public void setKMBM(String KMBM) {
        this.KMBM = KMBM;
    }

    public String getKMMC() {
        return KMMC;
    }

    public void setKMMC(String KMMC) {
        this.KMMC = KMMC;
    }

    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    public String getSZ() {
        return SZ;
    }

    public void setSZ(String SZ) {
        this.SZ = SZ;
    }

    public String getGXQ() {
        return GXQ;
    }

    public void setGXQ(String GXQ) {
        this.GXQ = GXQ;
    }

    public String getLCQ() {
        return LCQ;
    }

    public void setLCQ(String LCQ) {
        this.LCQ = LCQ;
    }

    public String getDXQ() {
        return DXQ;
    }

    public void setDXQ(String DXQ) {
        this.DXQ = DXQ;
    }

    public String getNCX() {
        return NCX;
    }

    public void setNCX(String NCX) {
        this.NCX = NCX;
    }

    public String getNFX() {
        return NFX;
    }

    public void setNFX(String NFX) {
        this.NFX = NFX;
    }

    public String getLCX() {
        return LCX;
    }

    public void setLCX(String LCX) {
        this.LCX = LCX;
    }

    public String getCRX() {
        return CRX;
    }

    public void setCRX(String CRX) {
        this.CRX = CRX;
    }

    public String getYHX() {
        return YHX;
    }

    public void setYHX(String YHX) {
        this.YHX = YHX;
    }

    public String getLAX() {
        return LAX;
    }

    public void setLAX(String LAX) {
        this.LAX = LAX;
    }

    public String getJXX() {
        return JXX;
    }

    public void setJXX(String JXX) {
        this.JXX = JXX;
    }

    public String getZXX() {
        return ZXX;
    }

    public void setZXX(String ZXX) {
        this.ZXX = ZXX;
    }

    public String getGCX() {
        return GCX;
    }

    public void setGCX(String GCX) {
        this.GCX = GCX;
    }
}
