package com.zxd.report.service.impl;

import com.zxd.report.mapper.InterfaceMapper;
import com.zxd.report.service.InterfaceService;
import com.zxd.util.XmlUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {
    @Autowired
    private InterfaceMapper interfaceMapper;
    @Override
    public String getqqfiles() {
        Map map = new HashMap<>();
        //返回查控请求列表
        List<Map> ck_request = interfaceMapper.getqqfiles();
 /*       Document d =  DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("FEEDBACK_XML");
        //设置FEEDBACK_XML为根元素
        d.setRootElement(root);
        //添加ITEMS元素
        Element ITEMS = root.addElement("ITEMS");
        for(Map m: ck_request){
            for(Object key:m.keySet()){
                ITEMS.addElement((String)key).addAttribute("FILE_PATH",(String)(m.get(key)));
            }
        }
        root.addElement("RETURN_CODE").addAttribute("RETURN_CODE","00");
        return d.asXML();
        */
        Map resultCodeMap =  new HashedMap();
        //获取返回结果集
        resultCodeMap.put("RETURN_CODE","00");
        return XmlUtil.gamaptoxml_file_path(ck_request,resultCodeMap);
    }

    @Override
    public void SetqqYtq(String qqdh) {
        interfaceMapper.SetqqYtq(qqdh);
    }

    @Override
    @Transactional
    public void savefeedback(String xml,String yhjgdm) throws Exception {
        Map map = (Map)XmlUtil.xmlBody2map(xml,"QUERY_XML");

        Object items = map.get("ITEMS");
        if(items instanceof Map){
            Map item = (Map)map.get("ITEMS");
            Map feedback = new HashMap<>();
            feedback.put("YHJGDM",yhjgdm);
            //获取filepath
            String sitem = ((Map)item.get("ITEM")).get("FILE_PATH").toString();
            //获取请求单号
            String qqdh =  hqqqdh(sitem);
            feedback.put("QQDH",qqdh);
            feedback.put("FILE_PATH",sitem);
            interfaceMapper.savefeedback(feedback);
        } else if(items instanceof List) {
            List<Map> ITEMS =  (List<Map>) map.get("ITEMS");
            for (Map item:ITEMS) {
                Map feedback = new HashMap<>();
                feedback.put("YHJGDM",yhjgdm);
                //获取file_path
                String sitem = ((Map)item.get("ITEM")).get("FILE_PATH").toString();
                //获取请求单号
                String qqdh =  hqqqdh(sitem);
                feedback.put("QQDH",qqdh);
                feedback.put("FILE_PATH",sitem);
                interfaceMapper.savefeedback(feedback);
            }
        }
    }
    /**
     * 反馈时，从file_path获取请求单号
     */
    public String hqqqdh(String filePath){
        String filename =   filePath.substring(filePath.lastIndexOf("/")+1);
        //获取请求单号
        String qqdh =  filename.substring(23,45);
        return qqdh;
    }

}
