package com.sinosoft.sinoep.modules.system.config.holidayset.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.modules.system.config.holidayset.entity.DayStatus;


public interface HolidaySetService {
	
	/**
	 * 获取日期数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:27:45
	 * @param request
	 * @param year
	 * @param month
	 * @return
	 */
	DayStatus[][] getList(HttpServletRequest request, Integer year, Integer month);
	
	/**
	 * 把某些日期设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:27:17
	 * @param dates
	 */
	void setWorkDay(String dates);
	
	/**
	 * 设置某些日期为休息日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 下午2:33:55
	 * @param dates
	 */
	void setFreeDay(String dates);
	
	/**
	 * 恢复
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 下午2:37:09
	 * @param dates
	 */
	void setBack(String dates);
	
	/**
	 * 查询某段时间内，节假日设置里是否有数据
	 */
	public Map<Object, Object> getHolidaySets(String startDate,String endDate);
	
	/**
	 * 查询某天日期是否是节假日(1表示是休息日，0表示是工作日）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:44:13
	 * @param day
	 * @return
	 */
	public String isHoliday(String day);
	
	/**
	 * 
	 * TODO 根据开始日期和天数，获得之后的一个工作日
	 * @author 王磊
	 * @Date 2019年2月27日 下午3:49:46
	 * @param startDate
	 * @param num
	 * @return
	 */
	public String getDateByStartDateAndDays(String startDate,int num);
}
