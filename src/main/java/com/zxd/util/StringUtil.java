package com.zxd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {



    public static void main(String[] args) {
        String a="2020年5月基础信息表";
        List<String> digitList = new ArrayList<String>();
        Pattern p = Pattern.compile("(\\d{4})|(\\d{1,2})");
        Matcher matcher = p.matcher(a);
        List<String> list =  new ArrayList();

        while(matcher.find()){
            list.add(matcher.group());
        }
        String month = Integer.parseInt(list.get(1))<10?"0"+list.get(1):list.get(1);
        System.out.println(list.get(0)+"-"+month);


      /*  Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}");
        Matcher matcher = pattern.matcher("2、开标时间：2018-08-02 14:00:00。2、开标时间：2018-08-02 16:00:00。2、开标时间：2018-08-02 15:00:00。");
        while(matcher.find()){
            System.out.println(matcher.group());
        }*/
    }

}

