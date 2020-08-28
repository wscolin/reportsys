package com.zxd.test;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.zxd.report.mapper.StAutoMapper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestWordExport {
    @Autowired
    private StAutoMapper stAutoMapper;
    @Test
    public void SimpleWordExport() {
        String querysql = "select * from t_autoinputconfig where typecode='100'";
        List<Map> list =  stAutoMapper.selectBysql(querysql);
        Map root = new HashMap();
        for(Map m: list){
            String sql = m.get("sql").toString();
            String num = stAutoMapper.selectBysql(sql).get(0).get("num").toString();
            if(num!=null && !"".equals(num)){
                Double d =  new Double(num);
                d = d>0?d:Math.abs(d);
                if(m.get("code").toString().endsWith("tb_bfb")){
                    if(d>=0){
                        root.put(m.get("code"),"增长"+d);
                    }else if(d<0){
                        root.put(m.get("code"),"下降"+Math.abs(d));
                    }
                }else {
                    root.put(m.get("code"),d);
                }
            }
        }
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    "C:\\Users\\Administrator\\Desktop\\autoinput\\input\\b1.docx", root);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\autoinput\\out\\a1.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
