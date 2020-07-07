package com.zxd.report.controller;


import com.zxd.report.service.InterfaceService;
import com.zxd.report.service.StParamsService;
import com.zxd.util.Base64Util;
import com.zxd.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/")
public class InterfaceController {
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private StParamsService stParamsService;

	private static final Logger log = LoggerFactory.getLogger(InterfaceController.class);
	@RequestMapping(value = "/request_ckext",method = RequestMethod.GET,produces = "application/xml;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "请求接口",params = 1)
	public String request_ckext(String YHJGDM,String TOKEN){
		String yhjgdm = stParamsService.getParamConf("YHJGDM");
		try {
			if(YHJGDM.equals(yhjgdm) && TOKEN.equals(Base64Util.encodeData(yhjgdm))){
                return interfaceService.getqqfiles();
            }else{
                //返回其他类型的return_code
                log.error("token error");
                Map returnmap =  new HashMap();
                returnmap.put("RETURN_CODE","99");
                return XmlUtil.gamaptoxmlreturncode(returnmap);
            }
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "/feedback_ckext",method = RequestMethod.POST)
	@ResponseBody
	//@SystemControllerLog(description = "反馈接口",params = 1)
	public String feedback_ckext(String YHJGDM,String TOKEN,String QUERY_XML){
		String yhjgdm = stParamsService.getParamConf("YHJGDM");
		Map returnmap =  new HashMap();
		if(YHJGDM.equals(yhjgdm) && TOKEN.equals(Base64Util.encodeData(yhjgdm))){
			try {
				interfaceService.savefeedback(QUERY_XML,yhjgdm);
				returnmap.put("RETURN_CODE","00");
				return XmlUtil.gamaptoxmlreturncode(returnmap);
			}catch (Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
				returnmap.put("RETURN_CODE","99");
				return XmlUtil.gamaptoxmlreturncode(returnmap);
			}
		}else {
			log.error("token error");
			System.out.println("token验证不通过");
			returnmap.put("RETURN_CODE","07");
			return XmlUtil.gamaptoxmlreturncode(returnmap);
		}

	}
}
