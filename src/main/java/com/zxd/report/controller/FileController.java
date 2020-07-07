package com.zxd.report.controller;


import com.zxd.report.service.InterfaceService;
import com.zxd.report.service.StParamsService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping
public class FileController {
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private StParamsService stParamsService;
	@RequestMapping(value = "/files/{filename:.+}",method = RequestMethod.GET)
	//@SystemControllerLog(description = "请求文件下载",params = 1)
	public ResponseEntity<byte[]> index(@PathVariable String filename,HttpServletRequest request) throws IOException {
		String QQCFPATH = stParamsService.getParamConf("ck_receive_path_backup");
		//文件对象
		File fileobj = new File(QQCFPATH+"/"+filename);
		// 下载文件名
		String fileName = filename;
		// 页面下载设置
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gbk"),"iso-8859-1"));
		ResponseEntity<byte[]> bEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileobj),headers, HttpStatus.OK);
		interfaceService.SetqqYtq(filename.substring(filename.indexOf(".")-22,filename.indexOf(".")));
		return bEntity;
	}
	@RequestMapping(value = "/fkfiles/{filename:.+}",method = RequestMethod.GET)
	//@SystemControllerLog(description = "本地测试反馈文件下载",params = 1)
	public ResponseEntity<byte[]> fkindex(@PathVariable String filename,HttpServletRequest request) throws IOException {
		String testfk = "E:\\FMQ\\send_ck";
		//文件对象
		File fileobj = new File(testfk+"/"+filename);
		// 下载文件名
		String fileName = filename;
		// 页面下载设置
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gbk"),"iso-8859-1"));
		ResponseEntity<byte[]> bEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileobj),headers, HttpStatus.OK);
		return bEntity;
	}
}
