package com.zxd.report.controller;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StAutoMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/word")
public class WordController {
    @Autowired
    private StAutoMapper stAutoMapper;
    @RequestMapping("/autoinput")
    public  ResponseEntity<byte[]>  autoinput(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String date = request.getParameter("date");
        String year = date.split("-")[0];//年
        String month=date.split("-")[1].substring(1);//月
        String querysql = "select * from t_autoinputconfig where typecode='100'";
        List<Map> list =  stAutoMapper.selectBysql(querysql);
        Map root = new HashMap();
        root.put("year",year);
        root.put("month",month);
        for(Map m: list){
            String sql = m.get("sql").toString();
            String num = stAutoMapper.selectBysql(sql).get(0).get("num").toString();
            if(num!=null && !"".equals(num)){
                Double d =  new Double(num);
                //百分比如果正数，增长多少，否则为降低多少
                if(m.get("code").toString().endsWith("tb_bfb")){
                    root.put(m.get("code"),d>=0?"增长"+d : "下降"+Math.abs(d));
                }else {
                    root.put(m.get("code"),d);
                }
            }
        }
/*        Template t = null;
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\Desktop\\autoinput\\input\\"));
        t = cfg.getTemplate("财政运行202005（定）.ftl","UTF-8");
        t.setEncoding("utf-8");

        File outFile = new File("C:\\Users\\Administrator\\Desktop\\autoinput\\out\\财政运行202005（定）.docx");//临时存放路径
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
        t.process(root, out); // 往模板里写数据
        out.flush();
        out.close();*/
        String templatepath = System.getProperty("user.dir")+File.separator+"templateExport"+File.separator+"word导出模板.docx";
        XWPFDocument doc = WordExportUtil.exportWord07(templatepath, root);
        String fileName = "财政运行"+date;
        File savefile = new File(System.getProperty("user.dir")+File.separator+"excelExport"+File.separator+fileName+".docx");
        FileOutputStream fos = new FileOutputStream(savefile);
        doc.write(fos);
        fos.close();
        return download(savefile);
    }
    /**
     * 下载
     * @param file
     * @return
     * @throws IOException
     */
    public ResponseEntity<byte[]> download(File file) throws IOException {
        // 需要下载的文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("gbk"),"iso-8859-1"));
        ResponseEntity<byte[]> entity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
        file.delete();
        return entity;
    }

}
