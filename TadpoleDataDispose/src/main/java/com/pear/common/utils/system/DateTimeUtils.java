package com.pear.common.utils.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具
 * */
public class DateTimeUtils { 
	
	/**
	 * 把毫秒数转换成时间格式的字符串
	 * */
	public static String convert2Time(long milliseconds)
	{
		 int ss = 1000;
         int mi = ss * 60;
         int hh = mi * 60;
  
         long hour = (milliseconds) / hh;
         long minute = (milliseconds - hour * hh) / mi;
         long second = (milliseconds - hour * hh - minute * mi) / ss;
  
         String strHour = hour < 10 ? "0" + hour : "" + hour;
         String strMinute = minute < 10 ? "0" + minute : "" + minute;
         String strSecond = second < 10 ? "0" + second : "" + second;
         return strHour + ":" + strMinute + ":" + strSecond;
	}
	
	/**
	 * 把时间格式的字符串转换成毫秒数
	 * @throws ParseException 
	 * */
	public static long convert2Ms(String time) throws ParseException
	{ 
		String[] ts =time.split(":");  
		int hour =Integer.parseInt(ts[0]);  
		int min =Integer.parseInt(ts[1]);  
		int sec =Integer.parseInt(ts[2]);  
		  
		return (hour*3600+min*60+sec)*1000l;  
	}
	
}
