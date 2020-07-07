package com.zxd.report.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zxd.report.service.StParamsService;
import com.zxd.util.Base64Util;
import com.zxd.util.XmlUtil;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/")
public class QueryController {
	@Autowired
	private StParamsService stParamsService;
	private static final Logger log = LoggerFactory.getLogger(QueryController.class);
	private static final Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	//@RequestMapping(value = "/request_query",method = RequestMethod.GET,produces = "application/xml;charset=UTF-8")
    //@ResponseBody
	@RequestMapping(value = "/request_query",method = RequestMethod.GET,produces = "application/xml;charset=UTF-8")
	@ResponseBody
	//@SystemControllerLog(description = "请求接口",params = 1)
    //@SystemControllerLog(description = "数据接口访问",params = 1)   暴露接口无登录信息 无法记录日志
	public String request_ckext(String QUERY_XML, String CXYWLB,String TOKEN, HttpServletRequest request){
		log.info("QUERY_XML:"+QUERY_XML);
		String Req_QUERY_XML= QUERY_XML;//此请求参数放入fmq传入公安
		//获取是否需要加密
		String sfjm = stParamsService.getParamConf("sfjm");
		log.info("sfjm:"+sfjm);
		if("1".equals(sfjm)){//需要加密解密
			//对参数解密
			QUERY_XML = Base64Util.xmldecoding(QUERY_XML);
			CXYWLB = Base64Util.decodeData(CXYWLB);
			//TOKEN = Base64Util.decodeData(TOKEN);
		}
		//返回字符串
		String returnxml = "";
		//取出param_key
		String yhjgdm = stParamsService.getParamConf("YHJGDM");
		try {
			if(TOKEN.equals(Base64Util.encodeData(yhjgdm))){//token验证通过
				//暴露接口 无登录信息
				//UserVO userVO = (UserVO) SecurityUtils.getSubject().getPrincipal();
				//String url = stParamsService.getParamConf("B2P001");
				String url = environment.getProperty("jkdz");
				Map conditionmap = new HashMap<>();//带查询字段条件map
				Map requiredCols = new HashMap<>();//返回数据项集合map
				//clientInfo.put("loginName",userVO.getUSERID());clientInfo.put("userName",userVO.getOPERATORNAME());
				//clientInfo.put("userCardId",userVO.getOPERATORID());clientInfo.put("userDept",userVO.getDEPTID());
		     	Map clientInfo = new HashMap<>();//客户端信息map
		 		clientInfo.put("loginName",environment.getProperty("loginName"));
				String ip =request.getRemoteAddr();
				clientInfo.put("ip",request.getRemoteAddr());
			    clientInfo.put("userName",environment.getProperty("userAccout"));//
			    clientInfo.put("userCardId",environment.getProperty("userCardId"));//是要变的
				clientInfo.put("userDept",environment.getProperty("userDept"));
				clientInfo.put("ip",environment.getProperty("ip"));
				//String qnamespace="http://webservice.web.ws.service.iflytek.com";
				String qnamespace = environment.getProperty("qnamespace");
				//请求方法名
				String qmethed = environment.getProperty("qmethed");
				//String qmethed="requestQuery";
				//服务授权密码加密
				String passworld = DigestUtils.md5Hex(environment.getProperty("password"));
				if(CXYWLB.equals("B2P001")){//银行身份证查询
					log.info("sfzcx start.....");
					//conditionmap_dataInfo数据(银行操作员信息)
					Map conditionmap_dataInfo = (Map)((Map)XmlUtil.xmlBody2map(QUERY_XML,"QUERY_XML")).get("ITEM");
					conditionmap_dataInfo.put("CXYWLB",CXYWLB);
					//conditionmap_item数据
					Map conditionmap_zjhm=new HashMap<>();
					conditionmap_zjhm.put("ZJHM",conditionmap_dataInfo.get("SFZHM"));
					//conditionmap数据
					conditionmap.put("item",conditionmap_zjhm);
					//将dataInfo转换为json
					conditionmap.put("dataInfo",gson.toJson(conditionmap_dataInfo));
					//接口返回列
					Map requiredCols_item = new HashMap<>();
					requiredCols_item.put("ZJHM","");
					requiredCols_item.put("XM","");
					requiredCols_item.put("XB","");
					requiredCols_item.put("MZ","");
					requiredCols_item.put("CSRQ","");
					requiredCols_item.put("HKSZDXZ","");
					requiredCols_item.put("FZRQ","");
					requiredCols_item.put("YXQX","");
					requiredCols.put("item",requiredCols_item);
					String condition1 = XmlUtil.map2xmlBody(conditionmap,"condition",true);
					String requiredCols1 = XmlUtil.map2xmlBody(requiredCols,"requiredItems",true);
					String clientInfo1= XmlUtil.map2xmlBody(clientInfo,"clientInfo",true);
					Object[] entryArgs = { "wbdw_yhxh",
							passworld,
							environment.getProperty("sfzfbbm"),
							condition1,
							requiredCols1,
							clientInfo1
					};
					//请求webservice
					returnxml =querybywebservice(url,entryArgs,qnamespace,qmethed);
				}else if(CXYWLB.equals("B2P002")){//银行居住证查询
					//获取item内容，转换为map对象
					Map contion_dataInfo = (Map) ((Map)XmlUtil.xmlBody2map(QUERY_XML,"QUERY_XML")).get("ITEM");
					contion_dataInfo.put("CXYWLB",CXYWLB);
					Map contion_zjhm = new HashMap();
					contion_zjhm.put("ZJHM",contion_dataInfo.get("SFZHM"));
					conditionmap.put("item",contion_zjhm);
					conditionmap.put("dataInfo",gson.toJson(contion_dataInfo));
					String condition = XmlUtil.map2xmlBody(conditionmap,"contion",true);
					//返回列
					Map require_cols_item = new HashMap();
					require_cols_item.put("XM","");
					require_cols_item.put("ZJHM ","");
					require_cols_item.put("XB","");
					require_cols_item.put("CSRQ","");
					require_cols_item.put("MZ","");
					require_cols_item.put("XZZ","");
					require_cols_item.put("FZRQ ","");
					require_cols_item.put("YXQZ","");
					Map require_cols =  new HashMap();
					require_cols.put("item",require_cols_item);
					String requireCols = XmlUtil.map2xmlBody(require_cols,"requiredItems",true);
					String clientInfos =XmlUtil.map2xmlBody(clientInfo,"clientInfo",true);
					Object[] zzzArgs = { "wbdw_yhxh",
							passworld,
							environment.getProperty("jzzfbbm"),
							condition,
							requireCols,
							clientInfos
					};
					returnxml =querybywebservice(url,zzzArgs,qnamespace,qmethed);
				}else if(CXYWLB.equals("B2P003")){//银行护照签证查询
					Map contion_dataInfo_hz = (Map) ((Map)XmlUtil.xmlBody2map(QUERY_XML,"QUERY_XML")).get("ITEM");
					//获取证件类型
					String zjlx = (String)contion_dataInfo_hz.get("ZJLX");
					contion_dataInfo_hz.put("CXYWLB",CXYWLB);
					Map contion_zjhm_hz = new HashMap();
					if("105".equals(zjlx)){//如果是身份证号查询按身份证查询
						contion_zjhm_hz.put("SFZH",contion_dataInfo_hz.get("ZJHM"));
					}else {//否则就按其他证件类型查询
						contion_zjhm_hz.put("ZJHM",contion_dataInfo_hz.get("ZJHM"));
					}
					Map conditon_map_hz = new HashMap<>();
					conditon_map_hz.put("item",contion_zjhm_hz);
					conditon_map_hz.put("dataInfo",gson.toJson(contion_dataInfo_hz));
					String condition_hz = XmlUtil.map2xmlBody(conditon_map_hz,"contion",true);
					Map require_cols_item_hz = new HashMap();
					require_cols_item_hz.put("ZWXM","");
					require_cols_item_hz.put("SFZH","");
					require_cols_item_hz.put("ZJHM","");
					require_cols_item_hz.put("XB_MC","");
					require_cols_item_hz.put("SQLB_MC","");
					require_cols_item_hz.put("QWD_MC","");
					require_cols_item_hz.put("XCZJYXQZ","");
					require_cols_item_hz.put("SLRQ","");
					Map require_item_hz = new HashMap();
					require_item_hz.put("item",require_cols_item_hz);
					String requireCols_hz = XmlUtil.map2xmlBody(require_item_hz,"requiredItems",true);
					String clientInfo_hz =XmlUtil.map2xmlBody(clientInfo,"clientInfo",true);
					Object[] crjArgs = { "wbdw_yhxh",
							passworld,
							environment.getProperty("crjfbbm"),
							condition_hz,
							requireCols_hz,
							clientInfo_hz
					};
					returnxml =querybywebservice(url,crjArgs,qnamespace,qmethed);
				}else if(CXYWLB.equals("B2P004")){//银行车辆登记信息查询
					Map contion_dataInfo_jdc = (Map) ((Map)XmlUtil.xmlBody2map(QUERY_XML,"QUERY_XML")).get("ITEM");
					contion_dataInfo_jdc.put("CXYWLB",CXYWLB);
					Map contion_hpzl_hz = new HashMap();
					contion_hpzl_hz.put("HPHM",contion_dataInfo_jdc.get("CPH"));
					contion_hpzl_hz.put("ZJHM",contion_dataInfo_jdc.get("ZJHM"));
					Map conditon_map_jdc = new HashMap<>();
					conditon_map_jdc.put("item",contion_hpzl_hz);
					conditon_map_jdc.put("dataInfo",gson.toJson(contion_dataInfo_jdc));
					String condition_jdc = XmlUtil.map2xmlBody(conditon_map_jdc,"contion",true);
					//返回列
					Map require_cols_item_jdc = new HashMap();
					require_cols_item_jdc.put("HPZL","");
					require_cols_item_jdc.put("HPHM ","");
					require_cols_item_jdc.put("ZJHM","");
					require_cols_item_jdc.put("XM","");
					require_cols_item_jdc.put("ZWPP","");
					require_cols_item_jdc.put("YWPP ","");
					require_cols_item_jdc.put("CLSBDH","");
					require_cols_item_jdc.put("FDJH","");
					require_cols_item_jdc.put("CLLX","");
					require_cols_item_jdc.put("CCDJRQ","");
					Map require_cols_jdc =  new HashMap();
					require_cols_jdc.put("item",require_cols_item_jdc);
					String requireCols_jdc = XmlUtil.map2xmlBody(require_cols_jdc,"requiredItems",true);
					String clientInfos_jdc =XmlUtil.map2xmlBody(clientInfo,"clientInfo",true);
					Object[] jdcArgs = { "wbdw_yhxh",
							passworld,
							environment.getProperty("jdcfwbm"),
							condition_jdc,
							requireCols_jdc,
							clientInfos_jdc
					};
					returnxml =querybywebservice(url,jdcArgs,qnamespace,qmethed);
				}else if(CXYWLB.equals("B2P005")){//银行涉案情况查询
					// TODO: 2017/10/17
				}
				log.info("webservice_backxml:"+returnxml);
				//截取feedbackxml
				String feedbackxml = XmlUtil.returnxml(returnxml,CXYWLB);
				if("1".equals(sfjm)){
					try {
						XmlUtil.feedxmlencoding(feedbackxml);
					} catch (Exception e) {
						e.printStackTrace();
						log.info("feedbackxml 解析出错！");


					}
				}
				log.info("Feedback_xml:"+feedbackxml);
				//获取返回结果
				Document document = DocumentHelper.parseText(feedbackxml);
				Element RETURN_CODE = document.getRootElement().element("RETURN_CODE");
				Attribute attr =  RETURN_CODE.attribute("RETURN_CODE");
				String result_code =  attr.getValue();
				//将请求参数写入fmq文件中
				try {
					yhcgafmq(Req_QUERY_XML,result_code,CXYWLB);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return feedbackxml;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	/**
	 *调用webserice方法
	 * @param wsurl webservice地址
	 * @param entryArgs 参数集合
	 * @param qnamespace 方法命名空间
	 * @param qmethod  方法名
	 * @return
	 */
	private static String querybywebservice(String wsurl,Object[] entryArgs ,String qnamespace, String qmethod){
		try {
			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(wsurl);
			options.setTo(targetEPR);
			// 调用方法的参数值
			/**身份证*/
		    /*Object[] entryArgs = { "wbdw_yhxh", "d39bdc262e78ed7bf030de05456dd183","500100000000_01_0000001090-3781",
					"<condition><item><ZJHM>500221198903287119</ZJHM><XM>陈国飞</XM></item><dataInfo>{}</dataInfo></condition>",
					"<requiredItems><item><SFZHM>身份证号码</SFZHM><XM>姓名</XM><XB>性别</XB><MZ>民族</MZ><CSRQ>出生日期</CSRQ><HJDZ>户籍地址</HJDZ><FZRQ>发证日期</FZRQ><YXRQ>有效期止</YXRQ></item></requiredItems>",
					"<clientInfo><loginName>nbdw_yhxh</loginName><userName>admin</userName><userCardId>admin</userCardId><userDept>500000000000</userDept><ip>127.0.0.1</ip></clientInfo>"
			};*/
			// 调用方法返回值的数据类型的Class对象
			Class[] classes = {String.class};
			// 调用方法名及WSDL文件的命名空间
			QName opName = new QName(qnamespace,qmethod);
			// 执行方法获取返回值
			// 没有返回值的方法使用serviceClient.invokeRobust(opName, entryArgs)
			Object result = serviceClient.invokeBlocking(opName, entryArgs, classes)[0];
			return result.toString();
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
			return "error";
		}
	}

	private static String test(String wsurl,String qnamespace,String qmethod,String cxywlb){
		try {
			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(wsurl);
			options.setTo(targetEPR);
			Object[] params = null;
			// 调用方法的参数值
			/**身份证*/
			Object[] sfzArgs = { "wbdw_yhxh",
					DigestUtils.md5Hex("wbdw_yhxh") ,
					"500100000000_01_0000002231-3801",
					"<condition><item><ZJHM>500221198903287119</ZJHM></item><dataInfo>{\"CZRZJHM\":\"500221198903287119\",\"BLYWLB\":\"1\",\"YHSZD\":\"重庆\",\"YHLXDH\":\"13212112111\",\"KHLXDH\":\"13212112111\",\"CZRXM\":\"正式\",\"YHMC\":\"重庆\",\"YHJGDM\":\"11\",\"SFZHM\":\"500221198903287119\",\"CXYWLB\":\"B2P001\"}</dataInfo></condition>",
					"<requiredItems><item> <ZJHM></ZJHM> <XM></XM> <CYM></CYM> <XB></XB> <MZ></MZ> <CSRQ></CSRQ> <HKSZDXZ></HKSZDXZ> <FZJG></FZJG> <FZRQ></FZRQ> <YXQX></YXQX> </item></requiredItems>",
					"<clientInfo><loginName>nbdw_yhxh</loginName><userName>银行协会</userName><userCardId>12222222</userCardId><userDept>990000000000</userDept><ip>10.154.61.168</ip></clientInfo>"
			};
			/**
			 * 机动车基本信息查询服务
			 */
			Object[] carArgs = { "wbdw_yhxh",
					"d39bdc262e78ed7bf030de05456dd183",
					"500100000000_01_0000002230-3783",
					"<condition><item> <HPHM>渝BTV364</HPHM> </item> <ZJHM>500221198903287119</ZJHM><dataInfo> { \"CPH\":\"渝BTV364\" , \"ZJHM\":\"500221198903287119\" , \"KHLXDH\":\"132433232121\" , \"BLYWLB\":\"转账\" , \"CXYWLB\":\"B2P004\" , \"YHJGDM\":\"E002H101110101001\" , \"YHMC\":\"重庆银行\" , \"YHSZD\":\"重庆\" , \"YHLXDH\":\"1312121111\" , \"CZRXM\":\"cgf\" , \"CZRZJHM\":\"500221198903287119\" } </dataInfo> </condition>",
					"<requiredItems><item> <HPZL></HPZL> <HPHM></HPHM> <ZJHM></ZJHM> <XM></XM> <ZWPP></ZWPP> <YWPP></YWPP> <CLSBDH></CLSBDH> <FDJH></FDJH> <CLLX></CLLX> <CCDJRQ></CCDJRQ> </item></requiredItems>",
					" <clientInfo><loginName>nbdw_yhxh</loginName><userName>银行协会</userName><userCardId>12222222</userCardId><userDept>990000000000</userDept><ip>10.154.61.168</ip></clientInfo>"
			};
			//E78312494
			//510227198302013032
			Object[] crjArgs = { "wbdw_yhxh",
					"d39bdc262e78ed7bf030de05456dd183",
					"500100000000_01_0000002210-3782",
					"<contion><item><ZJHM>W03088290</ZJHM></item>\n" +
							"\t<dataInfo>\n" +
							"\t{\"CZRZJHM\":\"500221198903287118\",\"ZJLX\":\"101\",\"BLYWLB\":\"1\",\"YHSZD\":\"重庆\",\"YHLXDH\":\"13565654565\",\"KHLXDH\":\"13212112111\",\"CZRXM\":\"张三\",\"YHMC\":\"重庆银行\",\n" +
							"\t\"YHJGDM\":\"E00011111\",\"ZJHM\":\"W03088290\",\"CXYWLB\":\"B2P003\"}\n" +
							"\t</dataInfo>\n" +
							"</contion>",
					"<requiredItems><item> <ZWXM></ZWXM> <BM></BM> <SFZH></SFZH> <ZJHM></ZJHM> <XB_MC></XB_MC> <CSRQ></CSRQ> <QWD_MC></QWD_MC> <XCZJQFRQ></XCZJQFRQ> <XCZJYXQZ></XCZJYXQZ> <SLRQ></SLRQ> <SPRQ></SPRQ> </item></requiredItems>",
					"<clientInfo><loginName>nbdw_yhxh</loginName><userName>银行协会</userName><userCardId>12222222</userCardId><userDept>990000000000</userDept><ip>10.154.61.168</ip></clientInfo>"
			};
			Object[] zzzArgs = { "wbdw_yhxh",
					"d39bdc262e78ed7bf030de05456dd183",
					"500100000000_01_0000001090-3781",
					"<condition><item><ZJHM>500235199705017268</ZJHM></item> <dataInfo> { \"SFZHM\":\"500235199705017268\" , \"KHLXDH\":\"13254545787\" , \"BLYWLB\":\"11\" , \"CXYWLB\":\"1\" , \"YHJGDM\":\"123223\" , \"YHMC\":\"重庆\" , \"YHSZD\":\"重庆\" , \"YHLXDH\":\"13212321213\" , \"CZRXM\":\"陈主任\" , \"CZRZJHM\":\"500332236598523114\" } </dataInfo> </condition> ",
					"<requiredItems><item> <XM></XM> <ZJHM></ZJHM> <XB></XB> <CSRQ></CSRQ> <MZ></MZ> <XZZ></XZZ> <FZRQ></FZRQ> <YXQZ></YXQZ> </item></requiredItems>",
					"<clientInfo><loginName>nbdw_yhxh</loginName><userName>银行协会</userName><userCardId>12222222</userCardId><userDept>990000000000</userDept><ip>10.154.61.168</ip></clientInfo>"
			};
			if(cxywlb.equals("B2P004")){
				params = carArgs;
			}else if("B2P001".equals(cxywlb)){
				params = sfzArgs;
			}else if("B2P003".equals(cxywlb)) {
				params = crjArgs;
			}else if("B2P002".equals(cxywlb)) {
				params = zzzArgs;
			}
			//


			// 调用方法返回值的数据类型的Class对象
			Class[] classes = {String.class};
			// 调用方法名及WSDL文件的命名空间
			QName opName = new QName(qnamespace,qmethod);
			// 执行方法获取返回值
			// 没有返回值的方法使用serviceClient.invokeRobust(opName, entryArgs)
			Object result = serviceClient.invokeBlocking(opName, params, classes)[0];
			return result.toString();
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
			return "error";
		}
	}
/*	public static void main(String[] args)throws Exception {
	//	System.out.println( DigestUtils.md5Hex("wbdw_yhxh"));
	//	System.out.println("陈国飞");
	//	log.info("陈国飞");
		System.out.println(test("http://10.154.3.57/dcws/RequestService?wsdl","http://webservice.web.ws.service.iflytek.com","requestQuery","B2P003"));
	}*/

	/**
	 * 获取请求ip地址
	 * @param name
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
     */
	@RequestMapping(value = "/sms",method = RequestMethod.GET,produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	public String sms(String name,HttpServletRequest request) throws UnsupportedEncodingException {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println(ip);
		}
		if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println(ip);
		}
		if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
			System.out.println(ip);
		}
		return name;
	}


	@Autowired
	private Environment environment;

	/**
	 * @param xml 请求参数xml
	 * @throws Exception
	 * @param returncode 请求返回结构代码
     */
	public void yhcgafmq(String xml,String returncode,String cxywlb) throws Exception{
		//获取请求路径
		//String qqpath = environment.getProperty("yhcxqqpath");
		String qqpath =  stParamsService.getParamConf("yhcxga_qqcs");
		Document d =  DocumentHelper.parseText(xml);
		Element element = d.getRootElement().addElement("RETURN_CODE");
		//returncode加密
		//element.addAttribute("RETURN_CODE",Base64Util.encodeData(returncode,false));
		element.addAttribute("RETURN_CODE",returncode);
		OutputStream outputStream = new FileOutputStream(qqpath+"/"+new Date().getTime()+"_"+cxywlb+"_qqcs.xml");
		XMLWriter xmlWriter =  new XMLWriter(outputStream);
		xmlWriter.write(d);
		xmlWriter.close();
	}

}
