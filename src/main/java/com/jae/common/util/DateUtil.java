package com.jae.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jae
 * @time 2017-07-26
 */
public class DateUtil {

	
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static final String YMD_TMS = "yyyy-MM-dd HH:mm:ss";

	public static final String YMD = "yyyy-MM-dd";
	
	public static final String YMD_TMS_NUM = "yyyyMMddHHmmss";
	
	public static final String YMD_NUM = "yyyyMMdd";

	/**
	 *	date 转 str
	 *  @param date
	 * @param pattern
	 * @return
	 */
	public  static String date2str(Date date, String pattern){
		if (date == null) {
			throw new IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = YMD_TMS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 根据日期Date获取yyyyMMddHHmmss时间戳
	 * @param date
	 * @return
	 */
	public static String getYmdTmsNum(Date date){
		return date2str(new Date(), YMD_TMS_NUM);
	}

	/**
	 * 将日期转换成字符串(yyyy-MM-dd HH:mm:ss)格式
	 * @param date
	 * @return
	 */
	public static String getYmdTms(Date date){
		return  date2str(date,YMD_TMS);
	}

	/**
	 * 根据日期Date获取yyyyMMdd间戳
	 * @param date
	 * @return
	 */
	public static String getYmdNum(Date date){
		return date2str(new Date(), YMD_NUM);
	}

	/**
	 * 将日期转换成字符串(yyyy-MM-dd)格式
	 * @param date
	 * @return
	 */
	public static String getYmdStr(Date date){
		return  date2str(date,YMD);
	}

	public static Date str2date(String strDate,String pattern){
        if(StringUtils.isEmpty(strDate)){
            throw new RuntimeException();
        }
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;

		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	/**
	 * 根据yyyyMMddHHmmss时间戳获取日期Date
	 * @param timestamp
	 * @return
	 */
	public static Date getYmdTmsNum(String timestamp){
		return str2date(timestamp, YMD_TMS_NUM);
	}

	/**
	 * 将字符串(yyyy-MM-dd HH:mm:ss)转换成日期格式
	 * @param datStr
	 * @return
	 */
	public static Date getYmdTms(String datStr){
		return  str2date(datStr,YMD_TMS);
	}

    /**
     * 根据yyyyMMdd时间戳获取日期Date
     * @param timestamp
     * @return
     */
	public static Date getYmdNum(String timestamp){
		return str2date(timestamp, YMD_NUM);
	}

    /**
     * 根据yyyy-MM-dd时间戳获取日期Date
     * @param timestamp
     * @return
     */
	public static Date getYmd(String timestamp){
		return str2date(timestamp, YMD);
	}



	/**
	 * 计算两个日期之间的时间相差秒数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long dateDiff(Date startTime, Date endTime) {
		long diff = 0, sec = 0;
		// 获得两个时间的毫秒时间差异
		diff = endTime.getTime() - startTime.getTime();
		sec = diff / 1000;// 计算差多少秒
		return sec;
	}

	/**
	 * 描述：间隔天数
	 * @param date1
	 * @param date2
	 * @return 天数
	 */
	public static int getDateDiff(Date date1, Date date2)
	{
		if ((date1 == null) || (date2 == null))
		{
			return 0;
		}
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		
		long diff = time1 - time2;
		
		Long longValue = new Long(diff / 86400000L);
		return longValue.intValue();
	}
	
	
	/**
	 * 描述：获取指定日期之前多少天的日期
	 * @param date 指定日期
	 * @param day 天数
	 * @return 日期
	 */
	public static Date getDataDiff(Date date, int day)
	{
		if (date == null)
		{
			return null;
		}
		long time = date.getTime();
		time -= 86400000L * day;
		return new Date(time);
	}
	
	
	/**
	 * 时间计算，获取yyyy-mm-dd(datestr) 之后day天的日期
	 * @param datestr
	 * @param day
	 * @return
	 */
	public static Date getBeforeAfterDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date olddate = null;  
        try {  
            df.setLenient(false);  
            olddate = new Date(df.parse(datestr).getTime());  
        } catch (ParseException e) {  
            throw new RuntimeException("日期转换错误");  
        }  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(olddate);  
  
        int Year = cal.get(Calendar.YEAR);  
        int Month = cal.get(Calendar.MONTH);  
        int Day = cal.get(Calendar.DAY_OF_MONTH);  
  
        int NewDay = Day + day;  
  
        cal.set(Calendar.YEAR, Year);  
        cal.set(Calendar.MONTH, Month);  
        cal.set(Calendar.DAY_OF_MONTH, NewDay);  
  
        return new Date(cal.getTimeInMillis());  
    }
	
	public static String timeDiff(Date begin,Date end){
		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		   long day1=between/(24*3600);
		   long hour1=between%(24*3600)/3600;
		   long minute1=between%3600/60;
		   long second1=between%60/60;
		   StringBuffer result = new StringBuffer(); 
		   if(day1>0){
			   result.append(day1+"天");
		   }
		   if(hour1>0){
			   result.append(hour1+"小时");
		   }
		   if(hour1>0){
			   result.append(minute1+"分");
		   }
		   if(hour1>0){
			   result.append(second1+"秒");
		   }
		   //System.out.println(""+day1+"天"+hour1+"小时"+minute1+"分"+second1+"秒");
		   return result.toString();
	}
	
	public static int  mimuteDiff(Date begin,Date end){
		long between=(end.getTime()-begin.getTime())/1000;
		return (int)between%3600/60;
	}
	
	/**
	 * 描述：比较日期大小,Date
	 * @author yj
	 * @created 2017年7月11日 上午10:22:55
	 * @since 
	 * @param startDate
	 * @param endDate
	 * @return 0:相等  大于0:大于  小于0:小与
	 */
	public static int compareDate(Date startDate,Date endDate){
		return startDate.compareTo(endDate);
	}
	
	/**
	 * 描述：比较日期大小,String
	 * @author yj
	 * @created 2017年7月11日 上午10:23:12
	 * @since 
	 * @param startDateStr
	 * @param endDateStr
	 * @return  0:相等  1:大于  2:小与
	 */
	public static int compareDate(String startDateStr,String endDateStr,String formatStr){
		if(StringUtils.isEmpty(formatStr)){
			formatStr = YMD;
		}
		Date startDate = str2date(startDateStr, formatStr);
		Date endDate = str2date(endDateStr, formatStr);
		return startDate.compareTo(endDate);
	}
	
	/**
	 * 描述：字符串型日期加X天
	 * @author yj
	 * @created 2017年7月11日 上午10:38:33
	 * @since 
	 * @param addDateStr
	 * @param addNum
	 * @param formatStr
	 * @return
	 */
	public static String addDate(String addDateStr,int addNum,String formatStr){
		if(StringUtils.isEmpty(formatStr)){
			formatStr = YMD;
		}
		Date addDate = str2date(addDateStr, formatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(addDate);
        calendar.add(Calendar.DAY_OF_MONTH, addNum);
        return date2str(calendar.getTime(), formatStr);
	}
	
	/**
	 * 描述：日期加天数
	 * @author yj
	 * @created 2017年7月14日 上午10:02:09
	 * @since 
	 * @param addDate
	 * @param addNum
	 * @return
	 */
	public static Date addDate(Date addDate,int addNum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(addDate);
        calendar.add(Calendar.DAY_OF_MONTH, addNum);
        return calendar.getTime();
	}
	
	public static Date addHours(Date addDate,int addNum){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(addDate);
        calendar.add(Calendar.HOUR_OF_DAY, addNum);
        return calendar.getTime();
	}
	
	/**
	 * 描述：获取当前周
	 * @return
	 */
	public static int getCurrentWeek()
	{
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(7);
		--week;
		if (week == 0)
		{
			week = 7;
		}
		return week;
	}
	
	
	/**
	 * 描述：获取中文当前周
	 * @return
	 */
	public static String getCurrentWeekStr()
	{
		return getWeekStr(new Date());
	}
	

	/**
	 * 描述：获取指定日期的中文星期数
	 * @param date 指定日期
	 * @return 星期数，如：星期一
	 */
	public static String getWeekStr(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(7);
		--week;
		String weekStr = "";
		switch (week)
		{
			case 0:
				weekStr = "星期日";
				break;
			case 1:
				weekStr = "星期一";
				break;
			case 2:
				weekStr = "星期二";
				break;
			case 3:
				weekStr = "星期三";
				break;
			case 4:
				weekStr = "星期四";
				break;
			case 5:
				weekStr = "星期五";
				break;
			case 6:
				weekStr = "星期六";
		}
		return weekStr;
	}
	
	
	/**
	 * 描述：获取本年
	 * @return
	 */
	public static int getCurrentYear()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(1);
	}
	
	
	/**
	 * 描述：获取本月
	 * @return
	 */
	public static int getCurrentMonth()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(2) + 1;
	}
	
	
	/**
	 * 描述：获取本月的当前日期数
	 * @return
	 */
	public static int getCurrentDay()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(5);
	}
	
	
	/**
	 * 描述：当前时间与指定时间的差
	 * @param str 秒数
	 * @return 时间差，单位：秒
	 */
	public static int getUnixTime(String str)
	{
		if ((str == null) || ("".equals(str)))
		{
			return 0;
		}
		try
		{
			Date date1 = new Date(Long.parseLong(str));
			Date date = new Date();
			long nowtime = (date.getTime() - date1.getTime()) / 1000L;
			return (int) nowtime;
		}
		catch (Exception e)
		{
			logger.error("日期转换出错", e);
		}
		return 0;
	}
	
	
	/**
	 * 描述：去除日期字串中原“-”和“:”
	 * @param dateTime日期字串
	 * @return
	 */
	public static String formatString(String dateTime)
	{
		if ((dateTime != null) && (dateTime.length() >= 8))
		{
			String formatDateTime = dateTime.replaceAll("-", "");
			formatDateTime = formatDateTime.replaceAll(":", "");
			String date = formatDateTime.substring(0, 8);
			return date;
		}
		
		return "";
	}
	
	
	   
}