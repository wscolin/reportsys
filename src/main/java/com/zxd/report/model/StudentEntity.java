package com.zxd.report.model;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: gas_bank
 * @description:
 * @author:
 * @create: 2020-06-21 12:20
 **/
public class StudentEntity {

    private String id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", height = 5, width = 20, isImportField = "true_st")
    private String name;

    public String getEnzf() {
        return enzf;
    }

    public void setEnzf(String enzf) {
        this.enzf = enzf;
    }

    /**

     * 学生性别
     */
    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生", isImportField = "true_st")
    private int sex;
    @Excel(name = "英语",groupName = "科目", height = 5, width = 10, orderNum = "1")


    private String en;
    @Excel(name = "英语总分",groupName = "科目", height = 5, width = 10)

    private String enzf;
    @Excel(name = "英语听力",groupName = "英语总分", height = 5, width = 10, orderNum = "2")

    private String china;
    @Excel(name = "英语阅读",groupName = "英语总分", height = 5, width = 10, orderNum = "3")
    private String yd;

    public String getYd() {
        return yd;
    }

    public void setYd(String yd) {
        this.yd = yd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getChina() {
        return china;
    }

    public void setChina(String china) {
        this.china = china;
    }

    public static void main(String[] args) throws Exception {
        StudentEntity studentEntity = new StudentEntity() ;
        studentEntity.setId("1");
        studentEntity.setName("丽水");

        studentEntity.setSex(2);
        studentEntity.setEn("120");
        studentEntity.setChina("130");
        StudentEntity studentEntity1 = new StudentEntity() ;
        studentEntity1.setId("1");
        studentEntity1.setName("丽水");

        studentEntity1.setSex(2);
        studentEntity1.setEn("120");
        studentEntity1.setChina("130");
        List list=  new ArrayList();
        list.add(studentEntity);
        list.add(studentEntity1);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
                StudentEntity.class, list);
        FileOutputStream fos = new FileOutputStream("emp.xls");

        workbook.write(fos);

        fos.close();
    }
}
