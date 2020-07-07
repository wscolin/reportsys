package com.zxd.report.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @program: gas_bank
 * @description:
 * @author:
 * @create: 2020-07-03 18:18
 **/
public class TongBao_13 {
    @Excel(name = "县区名称", height = 5, width = 20, isImportField = "true_st")
    private String area;

    @Excel(name = "累计完成数", isImportField = "true_st")
    private int kmbm;
    @Excel(name = "2020年县区上报预期目标", height = 5, width = 10, orderNum = "1")
    private String a1;

    @Excel(name = "占预期目标数%", height = 5, width = 10, orderNum = "2")
    private String a2;

    @Excel(name = "完成预期目标", height = 5, width = 10, orderNum = "3")
    private String a3;

    @Excel(name = "同比增长%", height = 5, width = 10, orderNum = "4")
    private String a4;

    @Excel(name = "税收占总收入比重", height = 5, width = 10, orderNum = "5")
    private String a5;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getKmbm() {
        return kmbm;
    }

    public void setKmbm(int kmbm) {
        this.kmbm = kmbm;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

}
