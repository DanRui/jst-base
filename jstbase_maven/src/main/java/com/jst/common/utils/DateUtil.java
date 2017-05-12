package com.jst.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jst.util.StringUtil;

/**
 * 保留以前的DateUtil，与现有DateUtil合并
 * @author Administrator
 *
 */
public class DateUtil {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String TIMESTAMPS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	//每天、每小时、每分钟、每秒的毫秒数
	public static final long dayMs = 24 * 60 * 60 * 1000;
	public static final long hourMs = 60 * 60 * 1000;
	public static final long minuteMs = 60 * 1000;
	public static final long secondMs = 1000;
	
	/**
	 * @see 获取当前系统时间
	 * @return
	 */
	public static Date getCurrentDate(){
		return new Date();
	}
	
	/**
	 * @see 简单写了下，严格点还应该用正则表达式校验dateStr、datePattern
	 * @param dateStr
	 * @param datePattern
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateStr, String datePattern) throws Exception{
		Date date = null;
		SimpleDateFormat sdf = null;
		
		if(StringUtil.isEmpty(dateStr)){
			throw new Exception("日期不可为空");
		}
		
		if(StringUtil.isEmpty(datePattern)){
			throw new Exception("日期格式不可为空");
		}
		
		sdf = new SimpleDateFormat(datePattern);
		
		try{
			date = sdf.parse(dateStr);
		}catch(Exception e){
			throw new Exception("日期与日期格式不匹配");
		}
		
		return date;
	}
	
	/**
	 * @see 简单写了下，严格点还应该用正则表达式校验datePattern
	 * @param date
	 * @param datePattern
	 * @return
	 * @throws Exception
	 */
	public static String format(Date date, String datePattern) throws Exception{
		String dateStr = null;
		SimpleDateFormat sdf = null;
		
		if(null == date){
			throw new Exception("日期不可为空");
		}
		
		if(StringUtil.isEmpty(datePattern)){
			throw new Exception("日期格式不可为空");
		}
		
		sdf = new SimpleDateFormat(datePattern);
		
		try{
			dateStr = sdf.format(date);
		}catch(Exception e){
			throw new Exception("日期与日期格式不匹配");
		}
		
		return dateStr;
	}
	
	/**
	 * @see 获取你想要的那一天，由amount控制
	 * @param date
	 * @param pattern
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static Object getNeedDay(Object date, String datePattern, int amount) throws Exception{
		Calendar c = null;
		
		c = Calendar.getInstance();
		
		if(date instanceof Date){
			c.setTime((Date) date);
			c.add(Calendar.DAY_OF_MONTH, amount);
			
			return c.getTime();
		}else if(date instanceof String){
			c.setTime(parse((String)date,datePattern));
			c.add(Calendar.DAY_OF_MONTH,amount);
			
			return format(c.getTime(),datePattern);
		}
		
		return null;
	}
	
	/**
	 * @see 获取两个日期的天数差
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDateDifference(String returnType, Date startDate, Date endDate){
		
		if("day".equals(returnType.toLowerCase())){
			return ( endDate.getTime() - startDate.getTime() ) / dayMs;
		}else if("hour".equals(returnType.toLowerCase())){
			return ( endDate.getTime() - startDate.getTime() ) / hourMs;
		}else if("minute".equals(returnType.toLowerCase())){
			return ( endDate.getTime() - startDate.getTime() ) / minuteMs;
		}else if("second".equals(returnType.toLowerCase())){
			return ( endDate.getTime() - startDate.getTime() ) / secondMs;
		}
		
		return 0;
	}
	
	/**
	 * @see 获取两个日期 期间的每一天,包含开始/结束日期
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static String[] getDateDifferArray(String startDate, String endDate, String datePattern) throws Exception{
		int differ = 0;
		String[] str = null;
		Date tempDate = null;
		Calendar c = Calendar.getInstance();
		
		differ = (int)getDateDifference("day",parse(startDate,datePattern), parse(endDate,datePattern));
		
		str = new String[differ+1];
		
		tempDate = parse(startDate,datePattern);
		
		for(int i=0;i<differ-1;i++){
			c.setTime(tempDate);
			c.add(Calendar.DAY_OF_MONTH, 1);
			
			tempDate = c.getTime();
			
			str[i+1] = format(tempDate,datePattern);
		}
		
		str[0]= startDate;
		str[str.length-1] = endDate;
		
		return str;
	}
	
	
	/**
	 * @see 判断一个日期是否包含时间部分，即时、分、秒
	 * @param date
	 * @return
	 */
	public static boolean containsTime(Date date){
		Calendar c = Calendar.getInstance();
		
		c.setTime(date);
		
		return 0 != c.get(Calendar.HOUR_OF_DAY) && 0 != c.get(Calendar.MINUTE) && 0 != c.get(Calendar.SECOND);
	}
	
	/**
	 * @see 判断一个日期是否包含时间部分，即时、分、秒
	 * @param date
	 * @return
	 */
	public static boolean containsTime(String dateStr){
		try{
			parse(dateStr, TIMESTAMPS_PATTERN);
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static Date getDate(String dateStr){
		Date date = null ;
		try{
			if(dateStr==null ||dateStr.equals("")){
				return null ;
			}
			SimpleDateFormat sdf  = null ;
			if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){ //yyyy-MM-dd
				 sdf = new SimpleDateFormat("yyyy-MM-dd");
			}else if(dateStr.matches("\\d{2}-\\d{1,2}-\\d{1,2}")){ //yy-MM-dd
				 sdf = new SimpleDateFormat("yy-MM-dd");
			}else if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:d{1,2}")){ //yyyy-MM-dd hh:mm
				 sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			}else if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:d{1,2}:\\d{1,2}")){ // yyyy-MM-dd hh:mm:ss
				 sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			}else if(dateStr.matches("\\d{2}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:d{1,2}:\\d{1,2}")){ //yy-MM-dd hh:MM:ss
				 sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
			}else if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.0")){//yyyy-MM-dd hh:mm:ss.0
				dateStr = dateStr.substring(0,dateStr.lastIndexOf("."));
				sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			}else{
				System.out.println("日期转换错误，暂时不支持该格式");
				 return null ; 
			}
			date = sdf.parse(dateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date ;
	}

}
