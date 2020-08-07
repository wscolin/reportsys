package com.zxd.util;

import org.apache.commons.lang.StringUtils;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by panpengfei on 2017/8/8.
 */
public class DateUtil {
    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfHours = new SimpleDateFormat("yyyyMMddHHmm");

    private final static SimpleDateFormat sdfHms = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat ymdDays = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat ymdDays_1 = new SimpleDateFormat("yyyy/MM/dd");

    private final static SimpleDateFormat ymdDays_2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");

    private final static SimpleDateFormat ymdDays_3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static int getYear(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int year = c.get(Calendar.YEAR);
        return year;
    }
    //2017-01-01转date
    public static Date dateStringDToDate(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        try{
            return ymdDays.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //2017-01-01 01:01:01转date
    public static Date dateStringSToDate(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        try {
            return sdfDate.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Date dateStringSToDateyy(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        try {
            return sdfHms.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //2017/01/01 转 date
    public static Date dateStringToDate_1(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        try{
            return ymdDays_1.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 20170101转date
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date dateStringToDate_2(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        try{
            return sdfDays.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //date 转2017/01/01
    public static String getStringByDate_1(Date date){
        if(date == null){
            return null;
        }

        return ymdDays_1.format(date);
    }

    //date 转20170101
    public static String getSdfDays(Date date)throws Exception{
        if(date == null){
            return null;
        }

        return sdfDays.format(date);
    }

    /**
     * date 转201701010101
     * @param date
     * @return
     */
    public static String getSdfHours(Date date)throws Exception{
        if(date == null){
            return null;
        }
        return sdfHours.format(date);
    }

    /**
     * date 转2017-01-01 01:01:01
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateConverString_1(Date date)throws Exception{
        if(date == null){
            return null;
        }
        return sdfDate.format(date);
    }

    /**
     * date转01/01/2017 01:01:01:992
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateConverString_2(Date date)throws Exception{
        if(date == null){
            return null;
        }
        return ymdDays_2.format(date);
    }

    /**
     * date转2017-01-01
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateConverString_3(Date date)throws Exception{
        if(date == null){
            return null;
        }
        return ymdDays.format(date);
    }

    /**
     * date转01/01/2017 01:01:01
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateConverString_4(Date date)throws Exception{
        if(date == null){
            return null;
        }

        return ymdDays_3.format(date);
    }

    public static Map<String,String> dateConverString_5(Date date)throws Exception{
        if(date == null){
            return null;
        }
        Map<String,String> map = new HashMap<String,String>();
        Date start = DateUtil.dateStringDToDate(DateUtil.dateConverString_3(date));
        map.put("start",DateUtil.getSdfDays(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date_2 = calendar.getTime();
        map.put("end",DateUtil.getSdfDays(date_2));
        return map;
    }

    /**
     * 20170101转2017-01-01 01:01:01
     * @param dateString
     * @return
     */
    public static String stringConverString_1(String dateString)throws Exception{
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        return dateConverString_1(dateStringToDate_2(dateString));
    }

    /**
     * 20170101转2017-01-01
     * @param dateString
     * @return
     * @throws Exception
     */
    public static String stringConverString_2(String dateString)throws Exception{
        if(StringUtils.isNotEmpty(dateString)){
            return null;
        }

        return dateConverString_3(dateStringToDate_2(dateString));
    }



    /**
     * 强转时间
     * @param time
     * @return
     */
    public static String getTimeByString(String time)throws Exception{
        if(StringUtils.isEmpty(time)){
            return null;
        }
        String[] strings = time.split("-");
        StringBuffer timeNew = new StringBuffer("");
        for(int i = 0 ; i < strings.length ; i++){

            if(i == strings.length - 1){
                String[] s1 = strings[i].split(" ");
                timeNew.append(s1[0]);
            }else {
                timeNew.append(strings[i]);
            }
        }
        return timeNew.toString();
    }

    /**
     * 2017-01-01 01:01:01,2017/01/01或者2017-01-01 转20170101
     * @param time
     * @return
     */
    public static String converCjsjQz(String time)throws Exception{

        if(StringUtils.isEmpty(time)){
            return null;
        }

        try{
            sdfDate.parse(time);
        }catch (Exception e){
            try {
                ymdDays_1.parse(time);
            }catch (Exception e1){
                try{
                    ymdDays.parse(time);
                }catch (Exception e2){
                    return null;
                }
                return getSdfDays(dateStringDToDate(time));
            }
            return getSdfDays(dateStringToDate_1(time));
        }

        return getSdfDays(dateStringSToDate(time));
    }


    /**
     * 2017-01-01 01:01:01,2017/01/01或者2017-01-01 转日期
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getDate(String time)throws Exception{
        try{
            return sdfDate.parse(time);
        }catch (Exception e){
            try {
                return ymdDays_1.parse(time);
            }catch (Exception e1){
                try{
                    return ymdDays.parse(time);
                }catch (Exception e2){
                    return null;
                }
            }
        }
    }

    public static String converFarq(String time)throws Exception{

        try{
            sdfDate.parse(time);
        }catch (Exception e){
            try{
                ymdDays.parse(time);
            }catch (Exception e1){
                return null;
            }
            return getSdfDays(dateStringDToDate(time))+"0000";
        }
        return getSdfHours(dateStringSToDate(time));
    }

    /**
     * 2017-01-01 01:01:01,2017/01/01或者2017-01-01 转201701010101
     * @param time
     * @return
     */
    public static String converCjsj(String time)throws Exception{
        try{
            sdfDate.parse(time);
        }catch (Exception e){
            try {
                ymdDays_1.parse(time);
            }catch (Exception e1){
                try{
                    ymdDays.parse(time);
                }catch (Exception e2){
                    return null;
                }
                return getSdfHours(dateStringDToDate(time));
            }
            return getSdfHours(dateStringToDate_1(time));
        }
        return getSdfHours(dateStringSToDate(time));
    }


    /**
     * 2017-01-01 转 20170101
     * @return
     */
    public static String getDay(String time)throws Exception{
        return getSdfDays(dateStringDToDate(time));
    }

    //获取前一天
    public static String getYesterday(int day)throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,day);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     *
     * 把字符串时间格式转为yyyy-mm-dd hh:mm:ss
     * @param x
     * @return
     */
    public static String getStrtime (String x){
        x = x.trim();
        if(null !=x && !"".equals(x)){
            Date date  =  null;
            try {
                date = sdfDate.parse(x);
                return sdfDate.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 将date转为string  yyyy-mm-dd hh:mm:ss
     * @param date
     * @return
     */

    public static String datetostr(Date date){
        return sdfDate.format(date);
    }

    /**
     * 指定时间减一秒
     * @param strdate
     * @return
     */
    public static String datetostrjian(String strdate) {
        try {
            Date date = sdfDate.parse(strdate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.SECOND, -1);
            date = c.getTime();
            return sdfDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 指定时间减一秒
     * @param strdate
     * @return
     */
    public static String datetostrjianyy(String strdate) {
        try {
            Date date = sdfDate.parse(strdate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.SECOND, -1);
            date = c.getTime();
            return sdfHms.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void main(String[] args)throws Exception{
//        DateUtil dateUtil = new DateUtil();
//        System.out.println(dateUtil.getSdfDays("2017-01-01 01:01:01"));
//        System.out.println(new Date("2017-01-01 01:01:01"));
//        System.out.println(DateUtil.fomatTimeSdfDays("2017-01-01 01:01:01"));
//        System.out.println(DateUtil.getSdfDays(DateUtil.fomatTimeSdfDays("2017-01-01 01:01:01")));
//        System.out.println(DateUtil.fomatTimeSdfHours(""));
//        System.out.println(DateUtil.getSdfDays(null));
//        System.out.println(DateUtil.dateStringSToDate("2017-01-01 01:01:01"));2017-08-01 16:36:00.0
//        System.out.println(DateUtil.getSdfHours(DateUtil.dateStringSToDate("2016-11-01")));
//        System.out.println(DateUtil.getSdfDays(DateUtil.dateStringSToDate("2016-11-01")));
//        System.out.println(DateUtil.dateStringDToDate("2016-11-01"));
//        System.out.println(DateUtil.dateStringSToDate("2017-01-01 01:01:01.0"));

//        System.out.println(ymdDays_2.format(new Date()));
        //System.out.println(dateConverString_5(new Date()));

       // System.out.println(converCjsj("2013/2/1"));
//        System.out.println(getYesterday(0));
//
//        System.out.println(DateUtil.getStrtime(null));
//        System.out.println(DateUtil.datetostr(new Date()));
//        //System.out.println("".equals(null));
//        System.out.println(null+"111");
        System.out.println(getStartDayOfWeek("2018-12-21")); //本周开始时间
        System.out.println(getEndDayOfWeek("2018-12-21")); //本周结束时间
       //System.out.println("111"+getAddOneDay(DateUtil.getStr2date(getStartDayOfWeek("2018-12-21"),"yyyy-MM-dd"))); //本周开始时间
        System.out.println(getSzStartDayOfWeek("2018-12-21")); // 上周开始时间
        System.out.println(getSzEndDayOfWeek("2018-12-21")); // 上周结束时间
       // System.out.println(getBeginDayOfYesterDay());
        //System.out.println(getEndDayOfYesterDay());
        //System.out.println("本期开始"+getBqstartTime("2018-12-21"));
        //System.out.println("本期结束"+getBqEndTime("2018-12-21"));
       // System.out.println(getSqstartTime("2018-12-21"));
       // System.out.println(getSqEndTime("2018-12-21"));
       // System.out.println(getLastYearTime("2019-01-16","yyy-MM-dd"));
        Date date = getStr2date("2020-05","yyyy-MM");
        String this_year_this_month = getDate2str(date,"yyyy-MM");
        String this_year_last_month = getDate2str( getAddTime(date,-1,"MONTH"),"yyyy-MM");
        String  last_year_this_month = getDate2str( getAddTime(date,-1,"YEAR"),"yyyy-MM");
        String  last_year_last_month = getDate2str(getAddTime(getStr2date(last_year_this_month,"yyyy-MM"),-1,"MONTH"),"yyyy-MM");
        System.out.println(this_year_this_month);
        System.out.println(this_year_last_month);
        System.out.println(last_year_this_month);
        System.out.println(last_year_last_month);
        System.out.println(getDateBystr("2020年1月基础信息表"));
    }

    public static Date getDateFormat(Date date,String format){
        try {
            String strdate =null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            strdate=simpleDateFormat.format(date);
            date = simpleDateFormat.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 本周开始时间
     * @return
     */
    public static String getStartDayOfWeek(String strdate){
        Date date = null;
        try{
            if(strdate != null && !"".equals(strdate)){
                date= DateUtil.getStr2date(strdate,"yyyy-MM-dd");
            }else {
                date = DateUtil.getDateFormat(new Date(),"yyyy-MM-dd");
            }
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if(week == 1){
                week +=7;
            }
            calendar.add(Calendar.DATE,2-week);
            return getDate2str(calendar.getTime(),"yyyy-MM-dd");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 上周开始时间
     * @return
     */
    public static String getSzStartDayOfWeek(String strdate){
        try{
            Date date= getStr2date(getStartDayOfWeek(strdate),"yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            return getDate2str(calendar.getTime(),"yyyy-MM-dd");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上周结束时间
     * @return
     */
    public static String getSzEndDayOfWeek(String strdate){
        try{
            Date date= getStr2date(getSzStartDayOfWeek(strdate),"yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,7);
            return getDate2str(calendar.getTime(),"yyyy-MM-dd");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 本周结束时间
     * @return
     */
    public static String  getEndDayOfWeek(String strdate){
        try{
            //本周开始时间
            Date  date =  getStr2date(getStartDayOfWeek(strdate),"yyyy-MM-dd");
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,7);
            return getDate2str(calendar.getTime(),"yyyy-MM-dd");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 昨天开始时间
     * @return
     */
    public static String getBeginDayOfYesterDay(){
        try {
            Date date = new Date();
            date = getDate(getStringByDate_1(date));
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            return getDate2str(calendar.getTime(),"yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 昨天结束时间
     * @return
     */
    public static String getEndDayOfYesterDay(){
        try {
/*            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            calendar.add(Calendar.DAY_OF_MONTH,-1);*/
            Date date= getDateFormat(new Date(),"yyyy-MM-dd");
           return getDate2str(date,"yyyy-MM-dd" );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将字符串转换为date
     * @param date
     * @param format
     * @return
     */
    public static Date getStr2date(String date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(StringUtils.isEmpty(date)){
            return null;
        }
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将date转换为字符串
     * @param date
     * @param format
     * @return
     */
    public static String getDate2str(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if( date ==null){
            return null;
        }
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 本月开始时间
     * @return
     */
    public static  String getBqstartTime(String strdate){
        Date date = null;
        if(strdate != null && !"".equals(strdate)){
            date = DateUtil.getStr2date(strdate,"yyyy-MM-dd");
        }else {
            date = new Date();
        }
        Calendar calendar =Calendar.getInstance();
            calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return  getDate2str(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * 本月结束时间
     * @return
     */
    public static  String getBqEndTime(String strdate){
        Date date = null;
        if(strdate != null && !"".equals(strdate)){
            date = DateUtil.getStr2date(strdate,"yyyy-MM-dd");
        }else {
            date = new Date();
        }
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH,1);
        return  getDate2str(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * s上期开始时间
     * @return
     */
    public static  String getSqstartTime(String strdate){
        Date date = null;
        if(strdate != null && !"".equals(strdate)){
            date= DateUtil.getStr2date(strdate,"yyyy-MM-dd");
        }else {
            date = new Date();
        }
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH,-1);
        return  getDate2str(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * 上期结束时间
     * @return
     */
    public static  String getSqEndTime(String strdate){
        Date date = null;
        if(strdate != null && !"".equals(strdate)){
            date= DateUtil.getStr2date(strdate,"yyyy-MM-dd");
        }else {
            date = new Date();
        }
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return  getDate2str(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * 获取指定时间的去年时间
     * @param strdate
     * @param format
     * @return
     */
    public static String getLastYearTime(String strdate,String format){
        Date date = getStr2date(strdate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.YEAR,-1);
        return getDate2str(calendar.getTime(),format);
    }

    /**
     * 加一天
     * @return
     */
   public static Date getAddOneDay(Date date){
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.add(Calendar.DAY_OF_MONTH,1);
       return calendar.getTime();
   }
    /**
     * 加一天
     * @return
     */
    public static Date getAddTime(Date date,int ts,String type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (type){
            case "DAY_OF_MONTH":
                calendar.add(Calendar.DAY_OF_MONTH,ts);
                break;
            case "MONTH":
                calendar.add(Calendar.MONTH,ts);
                break;
            case "YEAR":
                calendar.add(Calendar.YEAR,ts);
        }
        return calendar.getTime();
    }

    /**
     * 获取指定时间的去年时间
     * @param strdate
     * @param format
     * @return
     */
    public static String getjianoneYear(String strdate,String format){
        Date date = getStr2date(strdate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,-1);
        return getDate2str(calendar.getTime(),format);
    }

    /**
     * 截取制定字符串中时间
     */
    public static String getDateBystr(String str){
        Pattern p = Pattern.compile("(\\d{4})|(\\d{1,2})");
        Matcher matcher = p.matcher(str);
        List<String> list =  new ArrayList();
        while(matcher.find()){
            list.add(matcher.group());
        }
        String year = list.get(0);
        String month = Integer.parseInt(list.get(1))<10 &&list.get(1).length()==1 ?"0"+list.get(1):list.get(1);

        return year+"-"+month;
    }

}
