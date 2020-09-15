package com.zxd.test.GsonTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonTest {
    private static Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static void main(String[] args) {
        List list =  new ArrayList<>();
        list.add("语文");
        list.add("数学");
        list.add("生物");
        list.add("体育");
        list.add("物理");
        Map map = new HashMap<>();
        map.put("学科",list);
        String str = gson.toJson(map);
        System.out.println(str);
    }
}

