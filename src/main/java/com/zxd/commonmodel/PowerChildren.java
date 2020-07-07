package com.zxd.commonmodel;

import java.util.*;

/**
 * Created by Think on 2016/7/29.
 */
public class PowerChildren {


    private List list = new ArrayList();

    public int getSize(){
        return list.size();
    }
    public void addChildren(PowerNode node){
        list.add(node);
    }

    public String toString(){
        String result = "[";
        for(Iterator it = list.iterator(); it.hasNext();){
            result+=((PowerNode)it.next()).toString();
            result+=",";
        }
        if(list.size()>0){
            result = result.substring(0,result.length()-1);
        }

        result+="]";
        return result;
    }

    public void sortCildren(){
        //根据THESORT进行排序
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int j1 = ((PowerNode)o1).getTHESORT();
                int j2 = ((PowerNode)o2).getTHESORT();
                return (j1<j2?-1:(j1==j2?0:1));
            }
        });
        for(Iterator it = list.iterator();it.hasNext();){
            ((PowerNode)it.next()).sortChildren();
        }
    }
}
