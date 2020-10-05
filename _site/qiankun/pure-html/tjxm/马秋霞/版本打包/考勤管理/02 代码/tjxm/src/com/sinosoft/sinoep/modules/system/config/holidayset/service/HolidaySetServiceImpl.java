package com.sinosoft.sinoep.modules.system.config.holidayset.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.system.config.holidayset.dao.HolidaySetDao;
import com.sinosoft.sinoep.modules.system.config.holidayset.entity.DayStatus;
import com.sinosoft.sinoep.modules.system.config.holidayset.entity.HolidaySet;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 *节假日设置的service层
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:53:26
 */
@Service
public class HolidaySetServiceImpl implements HolidaySetService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	HolidaySetDao dao;

	/**
	 * 获取日历数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:28:12
	 * @param request
	 * @param year
	 * @param month
	 * @return
	 */
	@Override
	public DayStatus[][] getList(HttpServletRequest request, Integer year, Integer month) {
		//获取节假日设置里某年某月的数据
		List<Map<String,Object>> list = getHolidaySetInfo(year, month!=12?(month+1):1);
		Calendar ca = new GregorianCalendar(year, month, 1);
		   int month_count; //一个月的天数
		    DayStatus[][] arr = new DayStatus[6][7];
		   
	        int before = ca.get(Calendar.DAY_OF_WEEK); //得到月历前的空白天数
	        if(ca.get(Calendar.MONTH)==Calendar.DECEMBER){
	            ca.add(Calendar.YEAR,1);
	        }
	        ca.roll(Calendar.MONTH,1); //滚动一个月后
		       /* int s=ca.get(Calendar.MONTH);
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        System.out.println(sdf.format(ca.getTime()));*/
	        int next_before = ca.get(Calendar.DAY_OF_WEEK);

	        int after;
	        if (next_before == 1) {
	            after = 7;
	        } else {
	            after = next_before - 1;
	        } //得到当前时间

	        /* 获取月天数 ,考虑闰年的2月 */
	        if (before <= after) {
	            int temp = after - before + 1;
	            if (temp < 7) {
	                month_count = temp + 28;
	            } else {
	                month_count = 28;
	            }
	        } else {
	            int temp = 8 - before + after;
	            if (temp < 7) {
	                month_count = temp + 28;
	            } else {
	                month_count = 28;
	            }
	        }

	        //         System.out.println ("月天数="+month_count); // 检查月天数
	        //         开始赋值
	        
	        int temp_day = 9 - before; //第二行 起始的日期数

	        //         System.out.println ("before="+before); // test
	        //         System.out.println ("after="+after);
	        //        第一行的处理
	        //获取某个月的最后几天
	        List<Integer> beforeDays= getLastDaysByDate(year, month,before);
	        for (int k = 0; k < before - 1; k++) {
	        		DayStatus dayStatus=new DayStatus(request,year, (month-1), beforeDays.get(k));
	        		dayStatus.setValidate(false);
	        		setIsWorkDay(dayStatus,list);
	        		arr[0][k] = dayStatus;
	        } //为前面的几个赋值空格

	        for (int m = 1; m < 9 - before; m++) {
	        	DayStatus dayStatus=new DayStatus(request,year, month, m);
	        	//设置该天是否为工作日
	        	setIsWorkDay(dayStatus,list);
	        	arr[0][before - 2 + m] = dayStatus;
	        }

	        /* 检查第一行 完成 无误 */
	        //获取某个月的前几天
	       // List<Integer> lastDays=getBeforeDaysByDate(year, month,before);
	        Integer day;
	        //        接下来的处理
	        int cnt=0;
	        for (int i = 1; i < 6; i++) {
	            for (int j = 0; j < 7; j++) {
	                if (temp_day <= month_count) {
	                	DayStatus dayStatus=new DayStatus(request,year,month,temp_day);
	                	setIsWorkDay(dayStatus,list);
	                	arr[i][j] = dayStatus;
	                    temp_day++;
	                } else {
	                	 day= getBeforeDaysByDate(year, month,cnt);
	                	DayStatus dayStatus=new DayStatus(request,year,(month+1),day);
	                	dayStatus.setValidate(false);
	                	setIsWorkDay(dayStatus,list);
	                		arr[i][j] = dayStatus;
	                		cnt++;
	                }
	            }
	        }

	        /*
	         * System.out.println ("检查全部数组"); for(int t=0;t <6;t++){ for(int i=0;i
	         * <7;i++){ DayStatus d=arr[t][i]; System.out.println (d.getDay()); } }
	         */
	        return arr; //返回 日历数组
	}
	




	/**
	 * 获取某个月第几天
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月9日 下午4:49:24
	 * @param year
	 * @param month
	 * @param before
	 * @return
	 */
	private Integer getBeforeDaysByDate(Integer year, Integer month, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, day);
		String day1 = sdf.format(cal.getTime());
		return  Integer.valueOf(day1.substring(day1.lastIndexOf("-")+1));
	}




	/**
	 * 根据节假日设置信息设置该天是否为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 下午5:35:17
	 * @param dayStatus
	 * @param list
	 */
	private void setIsWorkDay(DayStatus dayStatus, List<Map<String, Object>> list) {
		//获取该天的年月日
		String fullDay = dayStatus.getFullDay();
		if(list.size()>0){
			for (Map<String, Object> map : list) {
				if(fullDay.equals(map.get("NOWDATE"))){
					dayStatus.setHasData("1");
					if("0".equals(map.get("ISHOLIDAY"))){
						//设置了工作日日
						dayStatus.setIsHoliday("0");
					}else if("1".equals(map.get("ISHOLIDAY"))){
						dayStatus.setIsHoliday("1");
					}
					break;
				}
			}
		}
		
	}

	/**
	 * 根据年份和月份获取节假日设置信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 下午5:32:49
	 * @param year
	 * @param month
	 * @return
	 */
	private List<Map<String, Object>> getHolidaySetInfo(Integer year, Integer month) {
		ArrayList<String>yearMonths=new ArrayList<String>();
		//设置当月的年月
		String yearMonth;
		if(month<10){
			 yearMonth = year+"-0"+month;
		}else{
			yearMonth = year+"-"+month;
		}
		yearMonths.add(yearMonth);
		//设置上个月的年月
		Integer previousMonth=month-1;
		String previousYearMonth;
		if(previousMonth<10 && previousMonth>0){
			previousYearMonth = year+"-0"+previousMonth;
		}else if(previousMonth==0){
			previousYearMonth = (year-1)+"-"+12;
		}else{
			previousYearMonth = year+"-"+previousMonth;
		}
		yearMonths.add(previousYearMonth);
		//设置下个月的年月
		Integer nextMonth=month+1;
		String nextYearMonth;
		if(month==12){
			nextYearMonth = (year+1)+"-01";
		}else if(nextMonth<10){
			nextYearMonth = year+"-0"+nextMonth;
		}else{
			nextYearMonth = year+"-"+nextMonth;
		}
		yearMonths.add(nextYearMonth);
		String condition=StringUtils.join(yearMonths, ",");
		StringBuilder sb = new StringBuilder("select t.now_date nowDate,t.is_holiday isHoliday from KQGL_HOLIDAY_SET_INFO t where t.year_month in (" + CommonUtils.commomSplit(condition) + ")");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
	
	/**
	 * 获取某个月的最后几天
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月9日 下午3:18:34
	 * @param year
	 * @param month
	 * @param before
	 * @return
	 */
	public  List<Integer> getLastDaysByDate(Integer year, Integer month, int before) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,year);
			  cal.set(Calendar.MONTH, month);
			  cal.set(Calendar.DAY_OF_MONTH, 1);
			  cal.add(Calendar.DAY_OF_MONTH, -1);
		for(int i=0;i<before-1;i++){
			  Date lastDate = cal.getTime();
			  String temp = sdf.format(lastDate);
			 list.add(Integer.valueOf(temp.substring(temp.lastIndexOf("-")+1)));
			 cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		Collections.sort(list, new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
			
		});
		return list;
	}

	/**
	 * 把一些天设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:28:35
	 * @param dates
	 */
	@Override
	public void setWorkDay(String dates) {
		//在节假日设置数据库中删除这些日期的数据
		jdbcTemplate.execute("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" + CommonUtils.commomSplit(dates) + ")");
		//保存
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<HolidaySet> list=new ArrayList<HolidaySet>();
		String time = sdf.format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String deptId = UserUtil.getCruDeptId();
		String deptName = UserUtil.getCruDeptName();
		String[] tempDates = dates.split(",");
		for(int i = 0;i<tempDates.length;i++){
			HolidaySet holidaySet=new HolidaySet();
			holidaySet.setCreUserId(userId);
			holidaySet.setCreDeptName(userName);
			holidaySet.setCreDeptId(deptId);
			holidaySet.setCreDeptName(deptName);
			holidaySet.setUpdateUserId(userId);
			holidaySet.setUpdateUserName(userName);
			holidaySet.setUpdateTime(userName);
			holidaySet.setVisible("1");
			holidaySet.setCreTime(time);
			holidaySet.setYearMonth(tempDates[i].substring(0, tempDates[i].lastIndexOf("-")));
			holidaySet.setIsHoliday("0");
			holidaySet.setNowDate(tempDates[i]);
			list.add(holidaySet);
		}
		dao.save(list);
	}

	/**
	 * 设置某些日期为休息日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 下午2:34:53
	 * @param dates
	 */
	@Override
	public void setFreeDay(String dates) {
		//在节假日设置数据库中删除这些日期的数据
		jdbcTemplate.execute("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" + CommonUtils.commomSplit(dates) + ")");
		//保存
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<HolidaySet> list = new ArrayList<HolidaySet>();
		String time = sdf.format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String deptId = UserUtil.getCruDeptId();
		String deptName = UserUtil.getCruDeptName();
		String[] tempDates = dates.split(",");
		for(int i = 0;i<tempDates.length;i++){
			HolidaySet holidaySet=new HolidaySet();
			holidaySet.setCreUserId(userId);
			holidaySet.setCreDeptName(userName);
			holidaySet.setCreDeptId(deptId);
			holidaySet.setCreDeptName(deptName);
			holidaySet.setUpdateUserId(userId);
			holidaySet.setUpdateUserName(userName);
			holidaySet.setUpdateTime(userName);
			holidaySet.setVisible("1");
			holidaySet.setCreTime(time);
			holidaySet.setYearMonth(tempDates[i].substring(0, tempDates[i].lastIndexOf("-")));
			holidaySet.setIsHoliday("1");
			holidaySet.setNowDate(tempDates[i]);
			list.add(holidaySet);
		}
		dao.save(list);
		
	}


	/**
	 * 恢复某些日期
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 下午2:37:48
	 * @param dates
	 */
	@Override
	public void setBack(String dates) {
		jdbcTemplate.execute("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" + CommonUtils.commomSplit(dates) + ")");
		
	}
	
	/**
	 * 查询某段时间内，节假日设置里是否有数据,map中key表示日期，value表示是否节假日
	 */
	@Override
	public Map<Object, Object> getHolidaySets(String startDate, String endDate) {
		StringBuilder sb=new StringBuilder("select  t.now_date date1,t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
		sb.append(" where to_date(t.NOW_DATE,'yyyy-mm-dd')>=to_date('" + startDate + "','yyyy-mm-dd') ");
		sb.append(" and to_date(t.NOW_DATE,'yyyy-mm-dd')<=to_date('" + endDate + "','yyyy-mm-dd')");
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString());
		Map<Object,Object> result=new HashMap<Object,Object>();
		for (Map<String, Object> map : list) {
			result.put((String) map.get("date1"), map.get("isholiday"));
		}
		return result;
	}

	/**
	 * 查询某天日期是否是节假日(1表示是休息日，0表示是工作日,空表示没有改天数据）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:44:13
	 * @param day
	 * @return
	 */
	@Override
	public String isHoliday(String day) {
		String isHoliday;
		try {
			StringBuilder sb = new StringBuilder("select t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
			sb.append("where to_date(t.NOW_DATE,'yyyy-mm-dd')=to_date('" + day + "','yyyy-mm-dd')");
			isHoliday = jdbcTemplate.queryForObject(sb.toString(), String.class);
		} catch (Exception e) {
			//没有该天信息
			isHoliday = "";
		}
		return isHoliday;
	}

}
