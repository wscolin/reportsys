package com.zxd.util;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by cgf on 2020-7-5
 */
public class Base64Util {
    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/', };

    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
            60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
            -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
            -1, -1 };

    /**
     * 解密
     * @param inputdata
     * @return
     */
    public static String decodeData(String inputdata){
        try {
            if(null == inputdata){
                return null;
            }
            return new String(Base64.decodeBase64(inputdata.getBytes("utf-8")),"utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
    //加密
    public static String encodeData(String inputdata){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        inputdata+=date;
        try {
            if(null == inputdata){
                return null;
            }
            return new String(Base64.encodeBase64(inputdata.getBytes("utf-8")),"utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param inputdata
     * @param flag true需要inputdata+时间 加密，false则不需要
     * @return
     */
    public static String encodeData(String inputdata,boolean flag){
        String strdata="";
        if (flag) {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            strdata = inputdata+date;
        }else {
            strdata =  inputdata;
        }
        try {
            if(null == inputdata){
                return null;
            }
            return new String(Base64.encodeBase64(strdata.getBytes("utf-8")),"utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求参数xml参数加密
     * @param xml xml明文
     * @return  返回加密xml字符串
     * @throws Exception
     */
    public static String xmlencoding(String xml) throws Exception{
        Map map = (Map)XmlUtil.xmlBody2map(xml,"QUERY_XML");
        Map item = (Map)map.get("ITEM");
        for (Object key:item.keySet()){
            item.put(key, Base64Util.encodeData((String)item.get(key),false));
        }
        String sb_xml = XmlUtil.map2xmlBody(item,"ITEM",true);
        sb_xml ="<QUERY_XML>"+sb_xml+"</QUERY_XML>";
        return sb_xml;
    }

    /**
     * 请求参数xml参数解密
     *
     * @param xml 加密xml参数
     * @return  返回解密xml字符串
     * @throws Exception
     */
    public static String xmldecoding(String xml) {
        Map map = null;
        try {
            map = (Map) XmlUtil.xmlBody2map(xml,"QUERY_XML");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Map item = (Map)map.get("ITEM");
        for (Object key:item.keySet()){
            item.put(key, Base64Util.decodeData((String)item.get(key)));
        }
        String sb_xml = XmlUtil.map2xmlBody(item,"ITEM",true);
        sb_xml ="<QUERY_XML>"+sb_xml+"</QUERY_XML>";
        return sb_xml;
    }



}
