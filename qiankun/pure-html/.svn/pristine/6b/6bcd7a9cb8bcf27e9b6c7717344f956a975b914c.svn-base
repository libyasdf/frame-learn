package com.sinosoft.sinoep.modules.zhbg.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 用于算两个日期的间隔时长（不包含周六日、节假日）
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月19日 下午2:53:59
 */
@Component
public class KqglUtil {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 根据外出的开始时间，和结束时间统计出外出时长
	 * @throws ParseException 
	 */
	public String getGoOutTime(String startDate, String endDate, String startAmOrPm, String endAmOrPm) throws Exception {
		double cnt = 0;
		//获取从开始日期到结束日期的节假日数据
		List<Map<String, Object>> list = getHolidaySets(startDate, endDate);
		if (startDate.equals(endDate)) {
			//如果开始日期和结束日期为同一天，只需统计当天的外出天数
			cnt = cnt + getGoOutDay(list, startDate, startAmOrPm, endAmOrPm);
		} else {
			//开始日期和结束日期不为同一天，统计从开始日期到结束日期的所有外出天数
			cnt = cnt + getGoOutDays(list, startDate, endDate,startAmOrPm, endAmOrPm);
		}
		return cnt + "";
	}
	
	/**
	 * 获取某个时间段的请假天数
	 * @param list
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getGoOutDays(List<Map<String, Object>> list, String startDate, String endDate, String startAmOrPm,
			String endAmOrPm) throws ParseException {
		double cnt=0;
		List<String> days=DateUtil.getBetweenDates(startDate, endDate);
		for (String day : days) {
			cnt = cnt + getGoOutDay(list, day);
		}
		//算开始外出哪天的外出时长
		cnt = cnt + getStartGoOutDay(list, startDate, startAmOrPm);
		//统计结束日期哪天的工作时长
		cnt = cnt + getEndGoOutDay(list, endDate, endAmOrPm);
		return cnt;
	}
	/**
	 * 算出某一天的请假时长
	 * @param list
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	private double getGoOutDay(List<Map<String, Object>> list, String day) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(day));
			if(week!=6 && week!=7){
				cnt++;
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(day.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						cnt++;
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(day));
				if(week!=6 && week!=7){
					cnt++;
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 算外出结束日期的外出时长
	 * @param list
	 * @param endDate
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getEndGoOutDay(List<Map<String, Object>> list, String endDate, String endAmOrPm) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(endDate));
			if(week!=6 && week!=7){
				if("0".equals(endAmOrPm)){
					cnt=cnt+1;
				}else{
					cnt=cnt+0.5;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(endDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if("0".equals(endAmOrPm)){
							cnt=cnt+1;
						}else{
							cnt=cnt+0.5;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(endDate));
				if(week!=6 && week!=7){
					if("0".equals(endAmOrPm)){
						cnt=cnt+1;
					}else{
						cnt=cnt+0.5;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 算开始外出那天的外出时长
	 * @param list
	 * @param startDate
	 * @param startAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getStartGoOutDay(List<Map<String, Object>> list, String startDate, String startAmOrPm) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(startDate));
			if(week!=6 && week!=7){
				if("1".equals(startAmOrPm)){
					cnt=cnt+1;
				}else{
					cnt=cnt+0.5;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(startDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if("1".equals(startAmOrPm)){
							cnt=cnt+1;
						}else{
							cnt=cnt+0.5;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(startDate));
				if(week!=6 && week!=7){
					if("1".equals(startAmOrPm)){
						cnt=cnt+1;
					}else{
						cnt=cnt+0.5;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 如果外出开始日期和结束日期为同一天，算是他的外出时长
	 * @param list
	 * @param startDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getGoOutDay(List<Map<String, Object>> list, String tempDate, String startAmOrPm, String endAmOrPm) throws ParseException {
		double cnt=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(tempDate));
			if(week!=6 && week!=7){
				if(startAmOrPm.equals(endAmOrPm)){
					cnt=0.5;
				}else{
					cnt=1;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(tempDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if(startAmOrPm.equals(endAmOrPm)){
							cnt=0.5;
						}else{
							cnt=1;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(tempDate));
				if(week!=6 && week!=7){
					if(startAmOrPm.equals(endAmOrPm)){
						cnt=0.5;
					}else{
						cnt=1;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 查询某段时间内，节假日设置里是否有数据
	 */
	public List<Map<String,Object>> getHolidaySets(String startDate,String endDate){
		StringBuilder sb=new StringBuilder("select  t.now_date date1,t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
		sb.append(" where to_date(t.NOW_DATE,'yyyy-mm-dd')>=to_date('" + startDate + "','yyyy-mm-dd') ");
		sb.append(" and to_date(t.NOW_DATE,'yyyy-mm-dd')<=to_date('" + endDate + "','yyyy-mm-dd')");
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString());
		return list;
		
	}
	/**
	 * 计算两个时间的间隔天数（含节假日）
	 * @throws ParseException 
	 */
	public int getDaysByDate(String startDate,String endDate) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1=sdf.parse(startDate);
		java.util.Date date2=sdf.parse(endDate);
		int days= (int)((date2.getTime()-date1.getTime())/(1000*60*60*24))+1;
		return days;
		
	}
	/**
	 * 根据传入的数字格式化为指定的精度
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月6日 下午2:18:51
	 * @param origiNum
	 * @param scale 精度
	 * @return
	 * BigDecimal.ROUND_HALF_UP 四舍五入
	 */
	public String getNumFormate(String origiNum,int scale ){
		BigDecimal b = new BigDecimal(origiNum);
		return b.setScale(scale,BigDecimal.ROUND_HALF_UP).toString(); 
	}
	
}
