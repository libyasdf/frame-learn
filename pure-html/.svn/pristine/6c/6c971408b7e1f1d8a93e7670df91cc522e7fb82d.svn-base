package com.sinosoft.sinoep.modules.zhbg.hygl.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.DateUtil;

import net.sf.json.JSONObject;
/**
 * 会议统计Service
 * TODO 
 * @author 张建明
 * @Date 2018年7月19日 上午10:50:49
 */
@Service
public class MeetingStatsServiceImpl implements MeetingStatsService {
	
	/**
	 * 获取当前日期所在周上周周一
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年2月22日 上午10:06:24
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public String getWeekDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar cal = Calendar.getInstance();  
		   Date time=sdf.parse(date);
		   cal.setTime(time);  
		   
		 int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		   if(1 == dayWeek) {  
		      cal.add(Calendar.DAY_OF_MONTH, -1);  
		   }  
		  cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
		  int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		  cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
		  String startDate = DateUtil.getAfterDayTime(sdf.format(cal.getTime()), -7);
		  
		 return startDate;
	}

}
