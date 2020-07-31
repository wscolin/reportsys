package com.zxd.commonmodel;


public class MenuNode {

    private String ID;

    private String NAME;

    private String ACTION;

    private String ICONCLS;

    private String PARENTID;

    private Integer THESORT;

    private MenuChildren children = new MenuChildren();


    public String toString(){
        String result = "{\"NAME\":\""+getNAME()+"\"," +
                "\"ACTION\":\""+getACTION()+"\",\"ICONCLS\":\""+getICONCLS()+"\"";
        if(children!=null && children.getSize()>0){
            result+=",\"VALUES\":"+children.toString();
        }
        result+="}";
        return result;
    }

    //添加子节点
    public void addChildren(MenuNode node){
        this.children.addChildren(node);
    }

    //获取子节点
    public String getChildren(){
        return this.children.toString();
    }

    public void sortChildren(){
        if(children!=null && children.getSize()>0){
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
}
