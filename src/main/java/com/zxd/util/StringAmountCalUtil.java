package com.zxd.util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StringAmountCalUtil {
    /**
     * @Description: 整数相除结果转换成指定位数的百分数
     * @param dividend : 被除数(分子)
     * @param divisor : 除数(分母)
     * @param digit : 保留几位小数
     * @return String
     */
    public static String getPercent(double dividend,double divisor,int digit) {
        Float result = (float)dividend/(float)divisor;
        if (result.isNaN()) {
            return "--";
        }else {
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度,即保留几位小数
            nt.setMinimumFractionDigits(digit);
            String finalResult = nt.format(result);
            return finalResult.substring(0,finalResult.lastIndexOf("%"));
        }
    }

    /**
     * 两个数相加
     * @param str1
     * @param str2
     * @return
     */
    public static String add(String str1,String str2) {
        try {
            return new BigDecimal(str1).add(new BigDecimal(str2)).toPlainString();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 两个数相乘
     * @param str1
     * @param str2
     * @return
     */
    public static String multiply(String str1,String str2) {
        try {
            return new BigDecimal(str1).multiply(new BigDecimal(str2)).toPlainString();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 两个数相减
     * @param str1
     * @param str2
     * @return
     */
    public static String subtract(String str1,String str2) {
        try {
            return new BigDecimal(str1).subtract(new BigDecimal(str2)).toPlainString();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 两个数相除
     * @param str1
     * @param str2
     * @return
     */
    public static String divide(String str1,String str2) {
        try {
            return new BigDecimal(str1).divide(new BigDecimal(str2),1).toPlainString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 四舍六入五五成双
     * @param str
     */
    public static String FourUpSixInto(String str){
        BigDecimal b1 = new BigDecimal(str);
        BigDecimal b2 = b1.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return b2.toString();
    }

    /**
     * TODO 除法运算，保留小数
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    public static String txfloat(double a,double b) {
        // TODO 自动生成的方法存根

        DecimalFormat df=new DecimalFormat("0.0");//设置保留位数

        return df.format((float)a/b);

    }

    public static void main(String[] args) {
      //  System.out.println(txfloat(27253,88130));
      //  System.out.println(divide("1","2"));
        System.out.println(getPercent(27253,-88130,1));
    }
}