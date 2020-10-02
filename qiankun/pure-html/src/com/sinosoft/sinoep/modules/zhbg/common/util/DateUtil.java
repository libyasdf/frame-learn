package com.sinosoft.sinoep.modules.zhbg.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

/**
 * 日期工具类
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月19日 下午3:02:42
 */
public class DateUtil {
	public static SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	/**
	 * 获取两个日期之间的中间日期（不包含开始日期和结束日期本身）
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 日期集合
	 * @throws ParseException 
	 */
	public static List<String> getBetweenDates(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date start=sdf.parse(startDate);
		Date end=sdf.parse(endDate);
	    List<String> result = new ArrayList<String>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(sdf.format(tempStart.getTime()));
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	
	/**
	 * 获取两个日期之间的中间日期（包含开始日期和结束日期本身）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月23日 下午6:02:51
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenDates1(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date start=sdf.parse(startDate);
		Date end=sdf.parse(endDate);
	    List<String> result = new ArrayList<String>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    //tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(sdf.format(tempStart.getTime()));
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    result.add(endDate);
	    return result;
	}
	/**
	 * 根据日期，返回其星期数，周一为1，周日为7
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		int ret;
		if (w == Calendar.SUNDAY)
			ret = 7;
		else
			ret = w - 1;
		return ret;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO  获取当月天数
	 * @Date 2018/7/18 13:56 
	 * @Param []
	 * @return int
	 **/
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据年 月 获取对应的月份 天数
	 * @Date 2018/7/18 14:03
	 * @Param [year, month]
	 * @return int
	 **/
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取指定年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getMinimum(Calendar.DATE);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH,firstDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取指定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * @param pTime 修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public  static  int  dayForWeek(String pTime) throws  Exception {
		SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd" );
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int  dayForWeek = 0 ;
		if (c.get(Calendar.DAY_OF_WEEK) == 1 ){
			dayForWeek = 7 ;
		}else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1 ;
		}
		return  dayForWeek;
	}
	/**
	 * 算出两个时间相差多少小时
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午2:26:22
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception 
	 */
	public static long getMinuteOff(String date1,String date2) throws Exception {
		   Date begin=dfs.parse(date1); 
		   Date end = dfs.parse(date2); 
		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒 
		   long hour=between/60; 
		   return hour;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 使用当前月份,得到上一个月的月份:月份的格式是:yyyy-MM
	 * @Date 2018/8/3 11:54
	 * @Param [currentDate]
	 * @return java.lang.String
	 **/
	public static String getLastDate(String currentDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(currentDate + "-" + "01");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		String year = ""+c.get(Calendar.YEAR);
		String month = ""+(c.get(Calendar.MONTH) + 1);
		if((c.get(Calendar.MONTH) + 1)<10){
			month = "0"+(c.get(Calendar.MONTH) + 1);
		}
		String lastDate = year + "-" + month;

		return lastDate;

	}

	/**
	 * @Author 王富康
	 * @Description //TODO 判断日期的合法性
	 * @Date 2018/8/3 14:22
	 * @Param [dateString]
	 * @return boolean
	 **/
	public static boolean validate(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;}

		//得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);

		if(month<1 || month>12){	return false;}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		}else{
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if(day<1 || day>monthLength){
			return false;
		}
		return true;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 判断年份是否为闰年
	 * @Date 2018/8/3 14:22
	 * @Param [year]
	 * @return boolean
	 **/
	private static boolean isLeapYear(int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ;
	}
	
	/**
	 * 根据某天获取该天所在周的开始时间和结束时间，比如2018-08-02，开始时间为2018-07-29结束时间2018-08-04
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月2日 下午3:37:59
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public static JSONObject getByDate(String date) throws ParseException{
		
		JSONObject jsonObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tempDate = sdf.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tempDate);
		int week = getWeekDay(tempDate);
		if(week==1){
			jsonObj.put("startDate", date);
			calendar.add(calendar.DAY_OF_MONTH, 7-week);
			jsonObj.put("endDate", sdf.format(calendar.getTime()));
		}else if(week==7){
			jsonObj.put("endDate", date);
			calendar.add(calendar.DAY_OF_MONTH, -6);
			jsonObj.put("startDate", sdf.format(calendar.getTime()));
		}else{
			calendar.add(calendar.DAY_OF_MONTH, -week+1);
			jsonObj.put("startDate", sdf.format(calendar.getTime()));
			calendar.add(calendar.DAY_OF_MONTH, 6);
			jsonObj.put("endDate", sdf.format(calendar.getTime()));
		}
		return jsonObj;
	}
	
}

