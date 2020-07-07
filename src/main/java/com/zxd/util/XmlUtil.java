package com.zxd.util;


import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;

import java.io.FileOutputStream;
import java.util.*;

/**
 *
 */
public class XmlUtil {
    public static String gamaptoxml(Map map){
        String xml = "<FEEDBACK_XML><ITEMS><ITEM ";
        for (Object key: map.keySet()) {
            xml+=key+"="+"\""+map.get(key)+"\" ";
        }
        xml+="/></ITEMS></FEEDBACK_XML>";
        return xml;
    }


    /**
     * 将返回的结果集list<map>转换为xml文件
     * @param list 返回结果集
     * @param result 返回结果代码
     * @return 返回stringxml
     */
    public static String gamaptoxml_file_path(List<Map> list,Map result){
        String xml = "<FEEDBACK_XML><ITEMS>";
        for(Map map:list){
            xml += "<ITEM ";
            for (Object key: map.keySet()) {
                xml+="FILE_PATH="+"\""+map.get(key)+"\" ";
            }
            xml +="/>";
        }
        xml+="</ITEMS><RETURN_CODE ";
        for (Object key: result.keySet()) {
            xml+=key+"="+"\""+result.get(key)+"\" />";
        }
        xml+="</FEEDBACK_XML>";
        return xml;
    }
    public static String gamaptoxml(Map map,Map result){
        String xml = "<FEEDBACK_XML><ITEMS><ITEM ";
        for (Object key: map.keySet()) {
            xml+=key+"="+"\""+map.get(key)+"\" ";
        }
        xml+="/></ITEMS><RETURN_CODE ";

        for (Object key: result.keySet()) {
            xml+=key+"="+"\""+result.get(key)+"\" ";
        }
        xml+="/></FEEDBACK_XML>";
        return xml;
    }

    /**
     * 将返回的结果集list<map>转换为xml文件
     * @param list 返回结果集
     * @param result 返回结果代码
     * @return 返回stringxml
     */
    public static String gamaptoxml(List<Map> list,Map result){
        String xml = "<FEEDBACK_XML><ITEMS>";
        for(Map map:list){
            xml += "<ITEM ";
            for (Object key: map.keySet()) {
                xml+=key+"="+"\""+map.get(key)+"\" ";
            }
            xml +="/>";
        }
        xml+="</ITEMS><RETURN_CODE ";
        for (Object key: result.keySet()) {
            xml+=key+"="+"\""+result.get(key)+"\" />";
        }
        xml+="</FEEDBACK_XML>";
        return xml;
    }

    /**
     * 如果 反馈结果无item时调用此方法
     * @param returncodemap
     * @return
     */
    public static String gamaptoxmlreturncode(Map returncodemap){
        String xml = "<FEEDBACK_XML>";
        for (Object key: returncodemap.keySet()) {
            xml+="<"+key+" "+key+"="+"\""+returncodemap.get(key)+"\" />";
        }
        xml+="</FEEDBACK_XML>";
        return xml;
    }



    public static String yhmaptoxml(Map map){
        String xml = "<QUERY_XML><ITEM ";
        for (Object key: map.keySet()) {
            xml+=key+"="+"\""+map.get(key)+"\" ";
        }
        xml+="/></QUERY_XML>";
        return xml;
    }


    /**
     * 根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，以及包含元素为上述支持数据类型的Map
     *
     * @param vo
     * @param rootElement
     * @return
     */
    public static String map2xmlBody(Map<String, Object> vo, String rootElement,boolean nothead) {
        org.dom4j.Document doc = DocumentHelper.createDocument();
        Element body = DocumentHelper.createElement(rootElement);
        doc.add(body);
        __buildMap2xmlBody(body, vo);
        if(nothead){
            return doc.getRootElement().asXML();
        }else{
            return doc.asXML();
        }

    }
    public static String map2xmlBody(Map<String, String> map, String rootElement) {
        org.dom4j.Document doc = DocumentHelper.createDocument();
        Element body = DocumentHelper.createElement(rootElement);
        doc.add(body);
        Element item = body.addElement("item");
        Element ZJHM = item.addElement("ZJHM");
        ZJHM.setText(map.get("SFZHM"));
        Element dataInfo = body.addElement("dataInfo");
        String jsonstr =  map2jsonStr(map);
        dataInfo.setText(jsonstr);
        return "";
    }

    /**
     *
     * @return
     * @throws Exception

    public String  strXML() throws Exception{
    Map map =  new HashedMap();
    map.put("khsjhm","18523539965");
    StringBuilder sb = new StringBuilder();
    // sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb.append("<request>"+yhmaptoxml(map));
    sb.append("<CXYWLB>EEEEEEE</CXYWLB><TOKEN>12112112</TOKEN></request>");
    Document d = DocumentHelper.parseText(sb.toString());
    return sb.toString();
    }  */
   /*//**
     * 请求生成string类型xml
     * @return
     */

    public void   strXML() throws Exception{
        Map map =  new HashedMap();
        map.put("khsjhm","18523539965");
        StringBuilder sb = new StringBuilder();
       // sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<request>"+yhmaptoxml(map));
        sb.append("<CXYWLB>EEEEEEE</CXYWLB><TOKEN>12112112</TOKEN></request>");
        Document d = DocumentHelper.parseText(sb.toString());
        XMLWriter xw = new XMLWriter(new FileOutputStream("\\\\10.154.61.167\\c:\\request.xml"));
        xw.write(d);
        xw.flush();
        xw.close();

    }




    private static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
        if (vo != null) {
            Iterator<String> it = vo.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (StringUtils.isNotEmpty(key)) {
                    Object obj = vo.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof Character || obj instanceof Boolean || obj instanceof Number
                                    || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                                //org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
                                //element.add(attr);
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof Map) {
                                //org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", java.util.Map.class.getCanonicalName());
                                //element.add(attr);
                                __buildMap2xmlBody(element, (Map<String, Object>) obj);
                            } else if (obj instanceof List){
                                //org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", java.util.List.class.getCanonicalName());
                                //element.add(attr);
                                for (Map map:(List<Map>)obj) {
                                    __buildMap2xmlBody(element,map);
                                }
                            }
                        }
                    }
                    body.add(element);
                }
            }
        }
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     */
    public static Object xmlBody2map(String xml, String rootElement) throws DocumentException {
        org.dom4j.Document doc = DocumentHelper.parseText(xml);
        Element body = (Element) doc.selectSingleNode("/" + rootElement);
        Object vo = __buildXmlBody2map(body);
        return vo;
    }

    public static Object __buildXmlBody2map(Element body) {
        Map vo = new HashMap();
        List<Map> list = new ArrayList<>();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if(vo.containsKey(key)){
                    Map temp = new HashMap<>();
                    temp.put(key,vo.get(key));
                    list.add(temp);
                    vo.clear();
                }
                if(element.elements().size()>1){
                    vo.put(key, __buildXmlBody2map(element));
                }else{
                    if (StringUtils.isNotEmpty(key)) {
                        if(element.attributeCount()>0){
                            Map temp = new HashMap<>();
                            for (Object obj:element.attributes()) {
                                DefaultAttribute att = (DefaultAttribute)obj;
                                temp.put(att.getQName().getName(),att.getValue());
                            }
                            vo.put(key, temp);
                        }else{
                            String type = element.attributeValue("type", "java.lang.String");
                            if(element.elements().size()>0){
                                type="java.util.Map";
                            }
                            String text = element.getText().trim();
                            Object value = null;
                            if (String.class.getCanonicalName().equals(type)) {
                                value = text;
                            } else if (Character.class.getCanonicalName().equals(type)) {
                                value = new Character(text.charAt(0));
                            } else if (Boolean.class.getCanonicalName().equals(type)) {
                                value = new Boolean(text);
                            } else if (Short.class.getCanonicalName().equals(type)) {
                                value = Short.parseShort(text);
                            } else if (Integer.class.getCanonicalName().equals(type)) {
                                value = Integer.parseInt(text);
                            } else if (Long.class.getCanonicalName().equals(type)) {
                                value = Long.parseLong(text);
                            } else if (Float.class.getCanonicalName().equals(type)) {
                                value = Float.parseFloat(text);
                            } else if (Double.class.getCanonicalName().equals(type)) {
                                value = Double.parseDouble(text);
                            } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                                value = new java.math.BigInteger(text);
                            } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                                value = new java.math.BigDecimal(text);
                            } else if (Map.class.getCanonicalName().equals(type)) {
                                value = __buildXmlBody2map(element);
                            } else {
                            }
                            vo.put(key, value);
                        }
                    }
                }
            }
        }
        if(list.size()>0){
            list.add(vo);
            return list;
        }else{
            return vo;
        }
    }

    /**
     * 大情报返回结果集解析
     */
    public static String returnxml(String str,String cxywlb){
        String returnxml="";
        int a = str.indexOf("<body>");
        int b = str.indexOf("</response>");
        //获取body字符串内容
        String body =  str.substring(a,b);
        //返回字段替换文档上字段一致
        if("B2P001".equals(cxywlb)){//身份证查询
            body = body.replace("ZJHM","SFZHM");
            body = body.replace("HKSZDXZ","HJDZ");
            body = body.replace("YXQX","YXRQ");
        }else if("B2P002".equals(cxywlb)){//居住证查询
            body = body.replace("ZJHM","SFZHM");
            body = body.replace("XZZ","HJDZ");
            body = body.replace("YXQZ","YXRQ");
        }else if("B2P003".equals(cxywlb)){//护照查询
            body = body.replace("SFZH","SFZHM");
            body = body.replace("XB_MC","XB");
            body = body.replace("ZWXM","XM");
            body = body.replace("SQLB_MC","ZJLX");
            body = body.replace("QWD_MC","QWGHDQ");//前往国或地区
            body = body.replace("XCZJYXQZ","ZJYXQ");//证件有效期
            body = body.replace("SLRQ","BLSJ");//办理日期
        }else if("B2P004".equals(cxywlb)){//机动车查询
            body = body.replace("HPZL","HPZL");//车牌号
            body = body.replace("HPHM","CPH");//车牌号
            body = body.replace("CLLX","CLLX");//车辆类型
            body = body.replace("ZWPP","CLPPXH");//车辆品牌类型
            body = body.replace("FDJH","FDJHM");//发动机号码
            body = body.replace("CLSBDH","CLSBDM");//车辆识别代码
            body = body.replace("XM","CLSYRXM");//车辆所有人姓名
            body = body.replace("ZJHM","CLSYSZJHM");//车辆所有证件号码
            body = body.replace("CCDJRQ","CCDJRQ");//初次登记日期
        }
        Document document = null;
        try {
            //解析body
            document = DocumentHelper.parseText(body);
            Element root =  document.getRootElement();
            Element resultList = root.element("resultList");
            List<Element> results = resultList.elements("result");
            Element resultCode =root.element("resultCode");
            Map resultCodeMap =  new HashedMap();
            //获取返回结果集
            resultCodeMap.put("RETURN_CODE",resultCode.getText());
            List<Map> list =  new ArrayList<>();
            /**
             * 循环结果集中resultlist,一般情况只有一个，不排除有多个
             */
            if(null!=results&&results.size()>0){
                for(Element result:results){
                    Map map = new HashedMap();
                    for( Iterator<Element> it = result.elementIterator();it.hasNext();){
                        Element e = it.next();
                        String key = e.getName();
                        if("B2P001".equals(cxywlb)){//身份证查询
                            if("XM".equals(key) || "SFZHM".equals(key)|| "XB".equals(key)|| "MZ".equals(key)|| "HJDZ".equals(key)|| "FZRQ".equals(key)|| "YXRQ".equals(key)|| "CSRQ".equals(key)){
                                map.put(e.getName(),e.getText());
                            }
                        }else if("B2P002".equals(cxywlb)){//居住证
                            map.put(e.getName(),e.getText());
                            map.remove("YXQX");
                        }else if("B2P003".equals(cxywlb)){//出入境
                            if("ZJYXQ".equals(key)){//截取证件有效期前八位
                                String zjyxq = "";
                                if(!"".equals(e.getText())&&null !=e.getText()){
                                    zjyxq = e.getText().substring(0,8);//截取前八位
                                }
                                map.put(e.getName(),zjyxq);
                            }else {
                                map.put(e.getName(),e.getText());
                            }

                        }else if("B2P004".equals(cxywlb)){//机动车查询
                            map.put(e.getName(),e.getText());
                            map.remove("FZJG_DM");
                            map.remove("YWPP");
                            map.remove("CLXH");
                            map.remove("ZZCMC");
                            map.remove("ZJLX");
                            map.remove("GCJK");
                        }
                    }
                    list.add(map);
                }
            }
            /**解析返回结果目录**/
            returnxml = gamaptoxml(list,resultCodeMap);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return returnxml;
    }
    /**
     * map 转换为json
     */
    public static String map2jsonStr(Map map){
        String jsonstr ="";
        for(Object key:map.keySet()){
            jsonstr +="\""+key+"\"=\""+map.get(key)+"\",";
        }
        jsonstr = "{"+jsonstr.substring(0,jsonstr.lastIndexOf(","))+"}";

        System.out.println(jsonstr);
        return jsonstr;
    }

    /**
     * 请求qury_xml 参数加密
     *
     * @param xml xml传入的请求参数
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
     * 反馈xml加密
     * @param feedbackxml 反馈xml
     * @return
     * @throws Exception
     */
    public static String feedxmlencoding(String feedbackxml) throws Exception{
        Document d =   DocumentHelper.parseText(feedbackxml);
        Element root = d.getRootElement();
        //获取items子节点
        Element items = root.element("ITEMS");
        //获取item
        List<Element> listitem= items.elements("ITEM");
        for (Element item:listitem){
             //获取item的属性值
            List<Attribute> attributes =item.attributes();
            for(Attribute a: attributes){
                a.setValue( Base64Util.encodeData(a.getValue(),false));
            }
        }
        //获取return_code
        Element RETURN_CODE = root.element("RETURN_CODE");
        Attribute attr_returncode = RETURN_CODE.attribute("RETURN_CODE");
        attr_returncode.setValue(Base64Util.encodeData(attr_returncode.getValue(),false));
        return d.asXML();
    }



    /* public static void main(String[] args)throws Exception {
      String QUERY_XML ="<QUERY_XML><ITEM SFZHM=\"500221198903287119\" CZRZJHM=\"500221198903287119\" KHLXDH=\"13212112111\" BLYWLB=\"1\" YHJGDM=\"11\" YHMC=\"重庆\" YHSZD=\"重庆\" YHLXDH=\"13565654565\" CZRXM=\"常驻\"  CXYWLB=\"B2P001\" /></QUERY_XML>";
        String Fk= "<FEEDBACK_XML>\n" +
                "\t<ITEMS>\n" +
                "\t\t<ITEM XM=\"CGF\" SFZHM=\"500221198903287119\" XB=\"NAN\" MZ=\"HAN\" CSRQ=\"19890328\" HJDZ=\"CQCS\" FZRQ=\"20151101\" YXRQ=\"10N\"/>\n" +
                "\t\t<ITEM XM=\"CGF\" SFZHM=\"500221198903287119\" XB=\"NAN\" MZ=\"HAN\" CSRQ=\"19890328\" HJDZ=\"CQCS\" FZRQ=\"20151101\" YXRQ=\"10N\"/>\n" +
                "\t</ITEMS>\n" +
                "</FEEDBACK_XML>";

        Object obj = ((Map)XmlUtil.xmlBody2map(Fk,"FEEDBACK_XML")).get("ITEMS");
        if(obj instanceof List){
           obj =(List) obj;
        }else{
            obj =(Map)obj;
        }
        System.out.println(obj);
        Map item =  (Map)conditionmap_dataInfo.get("ITEM");
        XmlUtil.map2jsonStr(conditionmap_dataInfo);
        String str = "<FEEDBACK_XML>\n" +
                "<ITEMS>\n" +
                "<ITEM XM=\"谭鉴\" BLSJ=\"20040108\" XB=\"女\" ZJHM=\"W03088290\" SFZHM=\"510212197507051623\" ZJYXQ=\"\" QWGHDQ=\"香港\" ZJLX=\"往来港澳通行证\" />\n" +

                "</ITEMS><RETURN_CODE RETURN_CODE=\"00\" />\n" +
                "</FEEDBACK_XML>";
        System.out.println(XmlUtil.feedxmlencoding(str));
    }*/
}
