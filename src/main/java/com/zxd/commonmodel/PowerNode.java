package com.zxd.commonmodel;

/**
 * Created by Think on 2016/7/29.
 */
public class PowerNode {

    private String ID;

    private String NAME;

    private String RES_KEY;

    private String ACTION;

    private String ICONCLS;

    private String PARENTID;

    private Integer THESORT;

    private PowerChildren children = new PowerChildren();


    public String toString() {
        String result = "{\"RES_KEY\":\"" + getRES_KEY() + "\",\"NAME\":\"" + getNAME() + "\",\"ICONCLS\":\"" + getICONCLS() + "\",\"ACTION\":\"" + getACTION() + "\"";
        if (children != null && children.getSize() > 0) {
            result += ",\"VALUES\":" + children.toString();
        }
        result += "}";
        return result;
    }

    //添加子节点
    public void addChildren(PowerNode node) {
        this.children.addChildren(node);
    }

    //获取子节点
    public String getChildren() {
        return this.children.toString();
    }

    public void sortChildren() {
        if (children != null && children.getSize() > 0) {
            children.sortCildren();
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPARENTID() {
        return PARENTID;
    }

    public void setPARENTID(String PARENTID) {
        this.PARENTID = PARENTID;
    }

    public Integer getTHESORT() {
        return THESORT;
    }

    public void setTHESORT(Integer THESORT) {
        this.THESORT = THESORT;
    }

    public String getRES_KEY() {
        return RES_KEY;
    }

    public void setRES_KEY(String RES_KEY) {
        this.RES_KEY = RES_KEY;
    }

    public String getACTION() {
        return ACTION;
    }

    public void setACTION(String ACTION) {
        this.ACTION = ACTION;
    }

    public String getICONCLS() {
        return ICONCLS;
    }

    public void setICONCLS(String ICONCLS) {
        this.ICONCLS = ICONCLS;
    }
}