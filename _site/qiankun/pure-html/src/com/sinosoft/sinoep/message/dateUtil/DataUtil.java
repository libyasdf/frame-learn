package com.sinosoft.sinoep.message.dateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataUtil {

	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
	}

	
	public static String getToday() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());

	}
}
