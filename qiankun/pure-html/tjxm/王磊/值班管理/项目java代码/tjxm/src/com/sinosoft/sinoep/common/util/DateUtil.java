package com.sinosoft.sinoep.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * 
 * 日期工具类
 * 
 */

public class DateUtil {
	
	private final SimpleDateFormat format;

    public DateUtil(SimpleDateFormat format) {
        this.format = format;
    }
    
    public SimpleDateFormat getFormat() {
        return format;
    }
    
    /**
     * 紧凑型日期格式，也就是纯数字类型yyyyMMdd
     */
	public static final DateUtil COMPAT = new DateUtil(new SimpleDateFormat("yyyyMMdd"));
	
	/**
	 * 常用日期格式，yyyy-MM-dd
	 */
	public static final DateUtil COMMON = new DateUtil(new SimpleDateFormat("yyyy-MM-dd"));
	
	/**
	 * 常用日期格式，yyyy-MM-dd HH:mm:ss
	 */
	public static final DateUtil COMMON_FULL = new DateUtil(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	
	/**
	 * 使用斜线分隔的，西方多采用，yyyy/MM/dd
	 */
	public static final DateUtil SLASH = new DateUtil(new SimpleDateFormat("yyyy/MM/dd"));
	
	/**
	 * 中文日期格式常用，yyyy年MM月dd日
	 */
	public static final DateUtil CHINESE = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日"));
	
	/**
	 * 中文日期格式常用，yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static final DateUtil CHINESE_FULL = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"));
	
	/**
	 * 日期获取字符串
	 */
	public String getDateText(Date date){
		return getFormat().format(date);
	}
	
	/**
	 * 字符串获取日期
	 * @throws ParseException 
	 */
	public Date getTextDate(String text) throws ParseException{
		return getFormat().parse(text);
	}
	
	/**
	 * 日期获取字符串
	 */
	public static String getDateText(Date date ,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 字符串获取日期
	 * @throws ParseException 
	 */
	public static Date getTextDate(String dateText ,String format) throws ParseException{
		return new SimpleDateFormat(format).parse(dateText);
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
	 * 根据某天获取该天所在周的开始时间和结束时间，比如2018-08-02，开始时间为2018-07-29结束时间2018-08-04
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月2日 下午3:37:59
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static JSONObject getByDate(String date) throws ParseException{
		JSONObject jsonObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tempDate = sdf.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tempDate);
		int week = getWeekDay(tempDate);
		if(week==1){
			jsonObj.put("startDate", date);
			calendar.add(Calendar.DAY_OF_MONTH, 7-week);
			jsonObj.put("endDate", sdf.format(calendar.getTime()));
		}else if(week==7){
			jsonObj.put("endDate", date);
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			jsonObj.put("startDate", sdf.format(calendar.getTime()));
		}else{
			calendar.add(Calendar.DAY_OF_MONTH, -week+1);
			jsonObj.put("startDate", sdf.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, 6);
			jsonObj.put("endDate", sdf.format(calendar.getTime()));
		}
		return jsonObj;
	}
	
	/**
	 * 日期格式加上天数yyyy-MM-dd
	 * @param date
	 * @param num
	 * @return
	 */
	public static String getAfterDayTime(String date,int num){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.roll(Calendar.DAY_OF_YEAR, num);
		String afterTime=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return afterTime;
	}
	
	/**
	 * 当月最后一天
	 * fxx
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//按你的要求设置时间
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), maxDay, 23, 59, 59);
		return calendar.getTime();
	}
	
	/**
	 * 返回一段时间内的所有日期(包含开始、结束时间)
	 * @param startTime yyyy-MM-dd
	 * @param ecdTime yyyy-MM-dd
	 * @return
	 */
	public static List<String> getAllDate(String startTime, String endTime) {
		List<String> list = new ArrayList<String>();
		// 判断开始时间是周几
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cale.setTime(format.parse(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = 0;
		try {
			boolean boo = true;
			while (boo) {
				cale = Calendar.getInstance();
				cale.setTime(format.parse(startTime));
				cale.set(Calendar.HOUR_OF_DAY, 24 * day);
				if (cale.getTime().getTime() > format.parse(endTime).getTime()) {
					boo = false;
				} else {
					list.add(format.format(cale.getTime()));
					day++;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}

