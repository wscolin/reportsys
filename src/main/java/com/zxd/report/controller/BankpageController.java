package com.zxd.report.controller;


import com.zxd.report.service.DictService;
import com.zxd.report.service.StParamsService;
import net.sf.json.JSONArray;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping(value = "/bank")
public class BankpageController {
    @Autowired
    private Environment env;
    @Autowired
    private QueryController queryController;
    @Autowired
    private StParamsService stParamsService;
    @Autowired
    private DictService dictService;
    @RequestMapping("/identity")
    public ModelAndView identity(Model model, HttpServletRequest request, String identity) throws DocumentException {
       /* if(identity!=null) {
            UserVO userVO = (UserVO) SecurityUtils.getSubject().getPrincipal();
            String yhjgdm = stParamsService.getParamConf("YHJGDM");
            Map map = new HashMap<>();
            map.put("SFZHM", identity);
            map.put("KHLXDH", "18888888888");
            map.put("BLYWLB", "B2P001");
            map.put("YHJGDM", yhjgdm);
            map.put("YHMC", "银行名称");
            map.put("YHSZD", "银行所在地");
            map.put("YHLXDH", "银行联系电话");
            map.put("CZRXM", userVO.getOPERATORNAME());
            map.put("CZRZJHM", userVO.getIDENTITY());
            String xml = queryController.request_ckext(XmlUtil.yhmaptoxml(map), "B2P001", Base64Util.encodeData(yhjgdm), request);
            model.addAttribute("map", XmlUtil.xmlBody2map(xml, "FEEDBACK_XML"));
            model.addAttribute("identity",identity);
        }*/
        String yhjgdm = stParamsService.getParamConf("YHJGDM");
        ModelAndView mv =  new ModelAndView("bank/identity");
        mv.addObject("yhjgdm",yhjgdm);
        return mv;
    }
    @RequestMapping("/sfzcx")
    @ResponseBody
    public List<Map> sfzcx(@RequestParam("QUERY_XML") String QUERY_XML, @RequestParam("CXYWLB")String CXYWLB, @RequestParam("TOKEN") String TOKEN, HttpServletRequest request ) throws Exception {
        /*List list = new ArrayList<>();
        String xml = queryController.request_ckext(QUERY_XML,CXYWLB,TOKEN,request);
        String xml = "<FEEDBACK_XML>\n" +
                "\t<ITEMS>\n" +
               // "\t\t<ITEM XM=\"CGF\" SFZHM=\"500221198903287119\" XB=\"NAN\" MZ=\"HAN\" CSRQ=\"19890328\" HJDZ=\"CQCS\" FZRQ=\"20151101\" YXRQ=\"10N\"/>\n" +
                //"\t\t<ITEM XM=\"CGF\" SFZHM=\"5002211989032871191111\" XB=\"NAN\" MZ=\"HAN\" CSRQ=\"19890328\" HJDZ=\"CQCS\" FZRQ=\"20151101\" YXRQ=\"10N\"/>\n" +
                "\t\t<ITEM XM=\"CGF\" SFZHM=\"500221198903287119\" XB=\"NAN\" MZ=\"HAN\" CSRQ=\"19890328\" HJDZ=\"CQCS\" FZRQ=\"20151101\" YXRQ=\"10N\"/>\n" +
                "\t</ITEMS>\n" +
                "</FEEDBACK_XML>";
        Document document = DocumentHelper.parseText(xml);
        List<Element> items =document.getRootElement().element("ITEMS").elements("ITEM");
        for(Element eitem:items){
            List<Attribute> attrs =  eitem.attributes();
            Map map = new  HashMap();
            for(Attribute a: attrs){
                map.put(a.getName(),a.getValue());
            }
            list.add(map);
        }
      Map map = new HashMap<>();
        map.put("XM","CGF");
        map.put("SFZHM","500221198903287119");
        map.put("XB","男");
        map.put("MZ","汉");
        map.put("CSRQ","19890328");
        map.put("HJDZ","重庆长寿");
        map.put("FZRQ","20150811");
        map.put("YXRQ","10年");
        list.add(map);*/
        String xml = queryController.request_ckext(QUERY_XML,CXYWLB,TOKEN,request);
        List list = jxfkxml(xml);
        return list;
    }
    @RequestMapping("/jzz")
    public String initjzzcx(Map<String,String> map){
        String yhjgdm = stParamsService.getParamConf("YHJGDM");
        map.put("yhjgdm",yhjgdm);
        return  "bank/jzzcx";
    }
    /**
     * 居住证查询
     */
    @RequestMapping("/jzzcx")
    @ResponseBody
    public List jzzcx(@RequestParam("QUERY_XML") String QUERY_XML, @RequestParam("CXYWLB")String CXYWLB, @RequestParam("TOKEN") String TOKEN, HttpServletRequest request )throws Exception{
        String xml = queryController.request_ckext(QUERY_XML,CXYWLB,TOKEN,request);
        List list = jxfkxml(xml);
        return list;
    }

    /**
     * 车辆登记查询
     * @return
     */
    @RequestMapping("/cldj")
    public String initcldj(Map<String,Object> map){
        //获取银行机构代码
        String yhjgdm = stParamsService.getParamConf("YHJGDM");
        //获取字典项 号牌种类
        String  jsonstr = dictService.getDictionary("hpzl");
        map.put("hpzl",JSONArray.fromObject(jsonstr) );
        map.put("yhjgdm",yhjgdm);
        return  "bank/cldjcx";

    }

    /**
     * 涉案查询
     * @return
     */
    @RequestMapping("/saqk")
    public String initsaqk(){
        return "bank/saqkcx";
    }

    /**
     * 机动车查询
     * @return
     */
    @RequestMapping("/cldjcx")
    @ResponseBody
    public List jdccx(@RequestParam("QUERY_XML") String QUERY_XML, @RequestParam("CXYWLB")String CXYWLB, @RequestParam("TOKEN") String TOKEN, HttpServletRequest request)throws Exception{
        String xml = queryController.request_ckext(QUERY_XML,CXYWLB,TOKEN,request);
        List list = jxfkxml(xml);
        return list;
    }

    /**
     * 护照查询
     * @param map
     * @return
     */
    @RequestMapping("/hz")
    public String hzcx(Map<String,Object> map){
        //获取银行机构代码
        String yhjgdm = stParamsService.getParamConf("YHJGDM");
        //获取字典项 证件类型
        String  jsonstr = dictService.getDictionary("zjlx");
        map.put("zjlx",JSONArray.fromObject(jsonstr) );
        map.put("yhjgdm",yhjgdm);
       return "bank/hzcx" ;
    }

    /**
     * 解析反馈xml文件
     * @param xml
     * @return
     * @throws Exception
     */
    public List jxfkxml(String xml)throws Exception{
        List list =  new ArrayList<>();
        Document document = DocumentHelper.parseText(xml);
        List<Element> items =document.getRootElement().element("ITEMS").elements("ITEM");
        //遍历元素属性
        for(Element eitem:items){
            List<Attribute> attrs =  eitem.attributes();
            Map map = new  HashMap();
            for(Attribute a: attrs){
                map.put(a.getName(),a.getValue());
            }
            list.add(map);
        }
        return list;
    }
}
