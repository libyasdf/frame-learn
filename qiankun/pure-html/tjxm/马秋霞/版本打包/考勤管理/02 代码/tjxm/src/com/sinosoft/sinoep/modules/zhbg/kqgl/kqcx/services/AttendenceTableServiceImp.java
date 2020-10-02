package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.service.AbsenteeismInfoService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service.BusinessTripService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.service.ComeLateLeaveEarlyService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.entity.PersionInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.service.OverTimeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service.KqglLeaveInfoService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqglAttendenceHandleDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqglAttendenceHandleService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 考勤表
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月27日 下午8:08:53
 */
@Service
public class AttendenceTableServiceImp implements AttendenceTableService {
	
	@Autowired
	private KqglAttendenceHandleService kqglAttendenceHandleService;
	@Autowired
	private ComeLateLeaveEarlyService comeLateLeaveEarlyService;
	@Autowired
	private KqglLeaveInfoService kqglLeaveInfoService;
	@Autowired
	private AbsenteeismInfoService absenteeismInfoService;
	@Autowired
	private BusinessTripService businessTripService;
	@Autowired
	private OverTimeService overTimeService;
	@Autowired
	 HolidaySetService holidaySetService;
	 @Autowired
	KqglUtil kqglUtil;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	 @Autowired
	 private TreeService service;
	
	@Override
	public List<PersionInfo> getList(String month, String userids) throws Exception {
		List<PersionInfo> result = new ArrayList<PersionInfo>();//最终返回结果
		//Map<String,SysFlowUserVo> userInfo = new HashMap<String,SysFlowUserVo>();//用于存放一个map的key为用户id，value为用户信息
		Map<String,List<KqglAttendenceHandle>> map = new HashMap<String,List<KqglAttendenceHandle>>();//key为卡号、value为一个list是考勤数据
		Map<String,List<Map<String,Object>>> vacationmap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是公休假的请假数据
		Map<String,List<Map<String,Object>>> sickCausemap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是事假病假的请假数据
		Map<String,List<Map<String,Object>>> leaveEarlymap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是迟到/早退数据
		Map<String,List<Map<String,Object>>> absenteeismap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是旷工数据
		Map<String,List<Map<String,Object>>> businessTripmap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是出差数据
		Map<String,List<Map<String,Object>>> overTimemap = new HashMap<String,List<Map<String,Object>>>();//key为用户id、value为一个list是加班数据
		//JSONObject cardNumAndUserInfo = new JSONObject();
		if(!StringUtils.isNotBlank(userids)){
			//获取当前用户部门及子部门下的人员
			userids = getUserIds(UserUtil.getCruDeptId());
		}
		
		String startDate = month + "-1";
		String[] dateData = month.split("-");
		String endDate =DateUtil.getLastDayOfMonth(Integer.valueOf(dateData[0]),Integer.valueOf(dateData[1]));
		List<String> days = DateUtil.getBetweenDates1(startDate, endDate);
		//获取某段时间的节假日信息，判断从开始日期到结束日期每一天是否是工作日
		Map<Object, Object> holidayInfo = holidaySetService.getHolidaySets(startDate, endDate);
		//根据用户id把对象信息放到考勤表的entry，并获取里面所有的卡号放入cardNumAndUserInfo,在考勤表实体类里设置哪些天为公休
		setUserInfo(userids,result,days,holidayInfo);
		//查询某些用户某个月份的考勤数据
		//List<KqglAttendenceHandle> kqInfo = kqglAttendenceHandleService.getKqInfo(startDate, endDate, CommonUtils.commomSplit(cardNumAndUserInfo.getString("cardNum")));
		//把考勤数据转成key、value的形式、key为卡号、value为一个list是考勤数据
		 //changeKqData(kqInfo,map);
		//查询某些用户某个月的请假数据,并根据请假数据设置某个月每一天是否有请假状态
		ArrayList<Map<String,Object>> vacationList = new ArrayList<Map<String,Object>>();//存放的是公休假
		ArrayList<Map<String,Object>> sickCauseList = new ArrayList<Map<String,Object>>(); //病假、事假数据
		List<Map<String,Object>> list = kqglLeaveInfoService.findData(startDate,endDate,CommonUtils.commomSplit(userids));
		divideLeaveData(list,vacationList,sickCauseList);
		 changekqglLeaveInfoData(vacationList,vacationmap);
		 changekqglLeaveInfoData(sickCauseList,sickCausemap);
		 //setLeaveDatas(result,kqglLeaveInfomap,days);
		//查询某些用户某个月的迟到/早退数据
		List<Map<String,Object>> leaveEarlylist = comeLateLeaveEarlyService.findUserData(startDate,endDate,CommonUtils.commomSplit(userids));
		 changeleaveEarlyData(leaveEarlylist,leaveEarlymap);
		//查询某些用户某个月的旷工数据
		List<Map<String,Object>> absenteeismList = absenteeismInfoService.findUserData(startDate,endDate,CommonUtils.commomSplit(userids));
		 changeAbsenteeismData(absenteeismList,absenteeismap);
		//查询某写用户某个月的出差数据
		List<Map<String,Object>> businessTripList = businessTripService.findUserData(startDate,endDate,CommonUtils.commomSplit(userids));
		 changeBusinessTripData(businessTripList,businessTripmap);
		//查询某些用户某段时间的加班数据
		List<Map<String,Object>> overTimeList = overTimeService.findUserData(startDate,endDate,CommonUtils.commomSplit(userids));
		changeOverTimeData(overTimeList,overTimemap);
		//设置每个人每天的考勤状态 
		setDayState(days,holidayInfo,result,vacationmap,sickCausemap,leaveEarlymap,absenteeismap,businessTripmap,overTimemap,startDate,endDate);
		return result;
	}
	
	//把请假数据划分成病假、事假和公休假
	private void divideLeaveData(List<Map<String, Object>> list, ArrayList<Map<String, Object>> vacationList,
			ArrayList<Map<String, Object>> sickCauseList) {
		for (Map<String, Object> map : list) {
			String type = map.get("TYPE").toString();
			if("1".equals(type) || "2".equals(type)){
				//事假或病假
				sickCauseList.add(map);
			}else{
				vacationList.add(map);
			}
			
		}
		
	}

	//把每个人每天的状态都设置进去
	private void setDayState(List<String> days, Map<Object, Object> holidayInfo, List<PersionInfo> result,Map<String, List<Map<String, Object>>> vacationmap,Map<String, List<Map<String, Object>>> sickCausemap,Map<String, List<Map<String, Object>>> leaveEarlymap, Map<String, List<Map<String, Object>>> absenteeismap,
			Map<String, List<Map<String, Object>>> businessTripmap,
			Map<String, List<Map<String, Object>>> overTimemap,String startDate,String endDate) throws Exception {
		for (PersionInfo person : result) {
			List<Map<String, Object>> vacationList = vacationmap.get(person.getUserId());
			List<Map<String, Object>> sickCauseList = sickCausemap.get(person.getUserId());
			List<Map<String, Object>> leaveEarlyList = leaveEarlymap.get(person.getUserId());
			List<Map<String, Object>> absenteeisList = absenteeismap.get(person.getUserId());
			List<Map<String, Object>> businessTripList = businessTripmap.get(person.getUserId());
			List<Map<String, Object>> overTimeList = overTimemap.get(person.getUserId());
			setPersonState(days,holidayInfo,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeList);
			//设置某个用户病假事假天数
			setSickCasualDays(sickCauseList,person,startDate,endDate);
			//设置某个用户公休假天数
			setVacationDays(vacationList,person,startDate,endDate);
		}
		
	}
	
  //设置某个人每天的状态
	private void setPersonState(List<String> days, Map<Object, Object> holidayInfo, PersionInfo person,
			 List<Map<String, Object>> vacationList, List<Map<String, Object>> sickCauseList,
			List<Map<String, Object>> leaveEarlyList,  List<Map<String, Object>> absenteeisList,
			List<Map<String, Object>> businessTripList,
			 List<Map<String, Object>> overTimeList) throws Exception {
		Map<String,Map<String, Object>> overTimeData = new HashMap<String,Map<String, Object>>();//某一天的加班数据,key表示某一天，value表示该天的加班数据
		
		changPesionOverTimeData(overTimeList,overTimeData);
		
		//1.只有工作日，请假、迟到、早退、旷工有任何一种情况没有才算出勤，否则都不算出勤，如果是出差、加班也要有出勤状态..........
		//2.休息日（公休），需要判断是否有出差、加班情况
		//3无论是否工作日都需要判断
		//某一天如果是工作日需要判断是否有请假情况；如果是休息日，需要判断是否有出差、加班；工作日和休息日都需要判断是否有出勤
		for (String day : days) {
			person.setDay(day);
			String isHoliday = isHoliday(day,holidayInfo);
			String tempDay = day.substring(day.lastIndexOf("-")+1);
			if("01".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"oneState");
				}else{
					//休息日
					
					setHolidayState(day,person,businessTripList,overTimeData,"oneState","");
				}
				
			}else if("02".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twoState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twoState","");
				}
				
			}else if("03".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"thirdState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"thirdState","");
				}
				
			}else if("04".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"fourthState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"fourthState","");
				}
				
			}else if("05".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"fiveState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"fiveState","");
				}
				
			}else if("06".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"sixState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"sixState","");
				}
				
			}else if("07".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"sevenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"sevenState","");
				}
				
			}else if("08".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"eightState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"eightState","");
				}
				
			}else if("09".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"nineState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"nineState","");
				}
				
			}else if("10".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"tenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"tenState","");
				}
				
			}else if("11".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"elevenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"elevenState","");
				}
				
			}else if("12".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twelveState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twelveState","");
				}
				
			}else if("13".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"thirteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"thirteenState","");
				}
				
			}else if("14".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"fourteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"fourteenState","");
				}
				
			}else if("15".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"fifteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"fifteenState","");
				}
				
			}else if("16".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"sixteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"sixteenState","");
				}
				
			}else if("17".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"seventeenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"seventeenState","");
				}
				
			}else if("18".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"eightteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"eightteenState","");
				}
				
			}else if("19".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"nineteenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"nineteenState","");
				}
				
			}else if("20".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyState","");
				}
				
			}else if("21".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyOneState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyOneState","");
				}
				
			}else if("22".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyTwoState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyTwoState","");
				}
				
			}else if("23".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyThirdState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyThirdState","");
				}
				
			}else if("24".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyFourthState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyFourthState","");
				}
				
			}else if("25".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyFiveState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyFiveState","");
				}
				
			}else if("26".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentySixState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentySixState","");
				}
				
			}else if("27".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentySevenState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentySevenState","");
				}
				
			}else if("28".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyEightState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyEightState","");
				}
				
			}else if("29".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"twentyNineState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"twentyNineState","");
				}
				
			}else if("30".equals(tempDay)){
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"thirtyState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"thirtyState","");
				}
				
			}else{
				if("0".equals(isHoliday)){
					//工作日
					setWorkState(day,person,vacationList,sickCauseList,leaveEarlyList,absenteeisList,businessTripList,overTimeData,"thirtyOneState");
				}else{
					//休息日
					setHolidayState(day,person,businessTripList,overTimeData,"thirtyOneState","");
				}
			}
			
		}
		
	}
	
	//把每个人加班数据转成map形式，key为某天，value为该天的加班数据
	private void changPesionOverTimeData(List<Map<String, Object>> overTimeList,
			Map<String, Map<String, Object>> overTimeData) {
		if(overTimeList!=null){
			for (Map<String, Object> map : overTimeList) {
				String day = map.get("OVERTIME").toString();
				overTimeData.put(day, map);
			}
		}
		
		
	}
	//根据某个人某段时间的公休请假数据，统计该用户的公休天数
	private void setVacationDays(List<Map<String, Object>> leaveData, PersionInfo person,String startDate,String endDate) throws Exception {
		if(leaveData!=null){
			for (Map<String, Object> map : leaveData) {
				String startDate1 = map.get("STARTDATE").toString();
				String endDate1 = map.get("ENDDATE").toString();
				String startAmOrPm = map.get("STARTAMORPM").toString();
				String endAmOrPm = map.get("ENDAMORPM").toString();
				String leaveType = map.get("TYPE").toString();
				String leaveLongTime = map.get("LEAVELONGTIME").toString();
				long leaveStartDate = sdf.parse(startDate1).getTime();
				long leaveEndDate = sdf.parse(endDate1).getTime();
				long cxStartDate = sdf.parse(startDate).getTime();
				long cxEndDate = sdf.parse(endDate).getTime();
				if(leaveStartDate>=cxStartDate &&  leaveEndDate<= cxEndDate ){
					//请假的开始时间>=要查询的开始时间，并且请假的结束时间<=要查询的结束时间
					person.setHolidays(person.getHolidays() + Double.valueOf(leaveLongTime));
				}else if( cxStartDate>leaveStartDate &&  cxEndDate>= leaveEndDate  &&  leaveEndDate>=cxStartDate ){
					//请假的开始时间<要查询的开始时间，请假的结束时间<=要查询的结束时间,请假的结束时间>=查询的开始时间,统计事假和病假的请假时长
					vacationTimeCnt(person, startDate, endDate, endDate1, endAmOrPm, leaveType);
				}else if( leaveStartDate>=cxStartDate  &&    cxEndDate>= leaveStartDate  &&   leaveEndDate>=cxEndDate  ){
					//查询的开始时间<=请假的开始时间<=查询的结束时间，请假的结束时间>查询的结束时间时，统计事假和病假的请假时长
					leaveTimeCnt1(person, startDate, endDate, startDate1, startAmOrPm, leaveType);
				}else{
					//查询的开始时间>请假的开始时间，查询的结束时间<请假的结束时间
					double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate, "1", "0"));
					person.setHolidays(person.getHolidays() + time);
					
				}
					
			}
		}
		
			
	}
	
	//根据某个人某段时间的请假数据，统计该用户的病假及事假天数
		private void setSickCasualDays(List<Map<String, Object>> leaveData, PersionInfo person,String startDate,String endDate) throws Exception {
			if(leaveData!=null){
				for (Map<String, Object> map : leaveData) {
					String startDate1 = map.get("STARTDATE").toString();
					String endDate1 = map.get("ENDDATE").toString();
					String startAmOrPm = map.get("STARTAMORPM").toString();
					String endAmOrPm = map.get("ENDAMORPM").toString();
					String leaveType = map.get("TYPE").toString();
					String leaveLongTime = map.get("LEAVELONGTIME").toString();
					long leaveStartDate = sdf.parse(startDate1).getTime();
					long leaveEndDate = sdf.parse(endDate1).getTime();
					long cxStartDate = sdf.parse(startDate).getTime();
					long cxEndDate = sdf.parse(endDate).getTime();
					if(leaveStartDate>=cxStartDate &&  leaveEndDate<= cxEndDate ){
						//请假的开始时间>=要查询的开始时间，并且请假的结束时间<=要查询的结束时间
							if("1".equals(leaveType)){
								//事假
								person.setCasualDays(person.getCasualDays() + Double.valueOf(leaveLongTime));
							}else if("2".equals(leaveType)){
								//病假
								person.setSickDays(person.getSickDays() + Double.valueOf(leaveLongTime));
							}
					}else if( cxStartDate>leaveStartDate &&  cxEndDate>= leaveEndDate  &&  leaveEndDate>=cxStartDate ){
						//请假的开始时间<要查询的开始时间，请假的结束时间<=要查询的结束时间,请假的结束时间>=查询的开始时间,统计事假和病假的请假时长
						leaveTimeCnt(person, startDate, endDate, endDate1, endAmOrPm, leaveType);
					}else if( leaveStartDate>=cxStartDate  &&    cxEndDate>= leaveStartDate  &&   leaveEndDate>=cxEndDate  ){
						//查询的开始时间<=请假的开始时间<=查询的结束时间，请假的结束时间>查询的结束时间时，统计事假和病假的请假时长
						leaveTimeCnt1(person, startDate, endDate, startDate1, startAmOrPm, leaveType);
					}else{
						//查询的开始时间>请假的开始时间，查询的结束时间<请假的结束时间
						double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate, "1", "0"));
						if("1".equals(leaveType)){
							//事假
							person.setCasualDays(person.getCasualDays() + time);
						}else if("2".equals(leaveType)){
							//病假
							person.setSickDays(person.getSickDays() + time);
						}
					}
						
				}
			}
			
				
		}
		private void vacationTimeCnt(PersionInfo person, String startDate, String endDate, String endDate1,
				String endAmOrPm, String leaveType) throws Exception {
			if(endDate1.equals(startDate)){
				//请假的结束时间==查询的开始时间
				if("1".equals(endAmOrPm)){
					//请假结束时间是上午
					person.setHolidays(person.getHolidays() + 0.5);
					
				}else{
					//请假结束时间是下午
					person.setHolidays(person.getHolidays() + 1);
					
				}
			}else if(endDate1.equals(endDate)){
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
				person.setHolidays(person.getHolidays() + time);
			}else{
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
				person.setHolidays(person.getHolidays() + time);
			}
		}
		private void vacationCnt1(PersionInfo person, String startDate, String endDate, String startDate1,
				String startAmOrPm, String leaveType) throws Exception {
			if(startDate1.equals(startDate)){
				//请假的开始时间==查询的开始时间
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, startAmOrPm, "0"));
				person.setHolidays(person.getHolidays() + time);
				
			}else if(startDate1.equals(endDate)){
				double temptime = 0;
				if("1".equals(startDate1)){
					//上午
					temptime=1;
				}else{
					//下午
					temptime=0.5;
				}
				person.setHolidays(person.getHolidays() + temptime);
			}else{
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, "1", "0"));
				person.setHolidays(person.getHolidays() + time);
				
			}
		}
		
		private void leaveTimeCnt(PersionInfo personalStats, String startDate, String endDate, String endDate1,
				String endAmOrPm, String leaveType) throws Exception {
			if(endDate1.equals(startDate)){
				//请假的结束时间==查询的开始时间
				if("1".equals(endAmOrPm)){
					//请假结束时间是上午
					if("1".equals(leaveType)){
						//事假
						personalStats.setCasualDays(personalStats.getCasualDays() + 0.5);
					}else if("2".equals(leaveType)){
						//病假
						personalStats.setSickDays(personalStats.getSickDays() + 0.5);
					}
				}else{
					//请假结束时间是下午
					if("1".equals(leaveType)){
						//事假
						personalStats.setCasualDays(personalStats.getCasualDays() + 1);
					}else if("2".equals(leaveType)){
						//病假
						personalStats.setSickDays(personalStats.getSickDays() + 1);
					}
				}
			}else if(endDate1.equals(endDate)){
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + time);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + time);
				}
				
			}else{
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + time);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + time);
				}
			}
		}
		private void leaveTimeCnt1(PersionInfo personalStats, String startDate, String endDate, String startDate1,
				String startAmOrPm, String leaveType) throws Exception {
			if(startDate1.equals(startDate)){
				//请假的开始时间==查询的开始时间
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, startAmOrPm, "0"));
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + time);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + time);
				}
			}else if(startDate1.equals(endDate)){
				double temptime = 0;
				if("1".equals(startDate1)){
					//上午
					temptime=1;
				}else{
					//下午
					temptime=0.5;
				}
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + temptime);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + temptime);
				}
			}else{
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, "1", "0"));
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + time);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + time);
				}
			}
		}
	/**
	 * day为工作日时设置该天状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 下午8:01:30
	 * @param day
	 * @param person
	 * @param kqglLeaveList
	 * @param leaveEarlyList
	 * @param absenteeisList
	 * @param businessTripList
	 * @param overTimeList
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void setWorkState(String day, PersionInfo person, List<Map<String, Object>> vacationList,List<Map<String, Object>> sickCauseList,
			List<Map<String, Object>> leaveEarlyList, List<Map<String, Object>> absenteeisList,
			List<Map<String, Object>> businessTripList,
			Map<String,Map<String, Object>> overTimeList,String field) throws Exception {
		Map<String, Object> map = overTimeList.get(day);
		Class<PersionInfo> clazz = PersionInfo.class;
		String titleField = field + "Title";
		PropertyDescriptor propertyDescriptor1 = new PropertyDescriptor(titleField,clazz);
		  Method getStateTitleMethod = propertyDescriptor1.getReadMethod();
		  Method setStateTitleMethod = propertyDescriptor1.getWriteMethod();
		  
		 PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field,clazz);
		  Method getStateMethod = propertyDescriptor.getReadMethod();
		  Method setStateMethod = propertyDescriptor.getWriteMethod();
		  String flag = "0" ;
		  //判断该天是否属于公休
		  if(vacationList!=null){
			  for (Map<String, Object> vacationDay : vacationList) {
				  String startDate =  vacationDay.get("STARTDATE").toString();
				  String endDate =  vacationDay.get("ENDDATE").toString();
				  String startAmOrPm =  vacationDay.get("STARTAMORPM").toString();
				  String endAmOrPm =  vacationDay.get("ENDAMORPM").toString();
				  long tempday = sdf.parse(day).getTime();
				  long tempStartDate = sdf.parse(startDate).getTime();
					long tempEndDate = sdf.parse(endDate).getTime();
				  if(tempStartDate<=tempday  && tempday<=tempEndDate){
					  setHolidayState(day,person,businessTripList,overTimeList,field,"1");
					  flag="1";
					  break;
				  }
			  }
		  }
	
		  if("0".equals(flag)){
			  //该天不属于公休，事假、病假、迟到早退、旷工都没有记为出勤,
			  if((sickCauseList==null || sickCauseList.size()==0) && (leaveEarlyList==null || leaveEarlyList.size()==0) && (absenteeisList==null || absenteeisList.size()==0)){
				  //出勤，需要统计一下加班、出差
				  setStateMethod.invoke(person, "Q");
			  }else{
				  String chuQinFlag="1";
				  //未出勤,需要查看是否有病假、事假、迟到早退或旷工情况
				  //统计请假
				  long tempDay = sdf.parse(day).getTime();
					
				  if(sickCauseList!=null){
					  for (Map<String, Object> sickCauseMap : sickCauseList) {
						  String leaveLongTime = sickCauseMap.get("LEAVELONGTIME").toString();
						  String leaveStartDate = sickCauseMap.get("STARTDATE").toString();
						  String leaveEndDate = sickCauseMap.get("ENDDATE").toString();
						  long endDate = sdf.parse(leaveEndDate).getTime();
							long startDate = sdf.parse(leaveStartDate).getTime();
						  if( tempDay>=startDate   &&  endDate>=tempDay ){
							  chuQinFlag="0";
							  String daystate = getStateMethod.invoke(person).toString();
							  String daystateTitle = getStateTitleMethod.invoke(person).toString();
							 String type =  sickCauseMap.get("TYPE").toString();
							  if(StringUtils.isNotBlank(daystate)){
								  if("1".equals(type)){
									  //事假
									  setStateMethod.invoke(person, daystate+",S");
									  if(StringUtils.isNotBlank(daystateTitle)){
										  setStateTitleMethod.invoke(person, daystateTitle+";S:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }else{
										  setStateTitleMethod.invoke(person,"S:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime  + "天");
									  }
									 
								  }else{//病假
									  setStateMethod.invoke(person, daystate+",B");
									  if(StringUtils.isNotBlank(daystateTitle)){
										  setStateTitleMethod.invoke(person, daystateTitle+";B:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }else{
										  setStateTitleMethod.invoke(person,"B:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }
									 
								  }
								  
							  }else{
								  if("1".equals(type)){
									  //事假
									  setStateMethod.invoke(person, "S");
									  if(StringUtils.isNotBlank(daystateTitle)){
										  setStateTitleMethod.invoke(person, daystateTitle+";S:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }else{
										  setStateTitleMethod.invoke(person,"S:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime  + "天");
									  }
									 
								  }else{//病假
									  setStateMethod.invoke(person, "B");
									  if(StringUtils.isNotBlank(daystateTitle)){
										  setStateTitleMethod.invoke(person, daystateTitle+";B:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }else{
										  setStateTitleMethod.invoke(person,"B:从"+leaveStartDate+"到"+leaveEndDate+"请假"+leaveLongTime + "天");
									  }
									  
								  }
							  }
							  break;
						  }
					  }
				  }
				 
				  //该天是否有迟到早退情况
				  if(leaveEarlyList!=null){
					  for (Map<String, Object> leaveEarlyMap : leaveEarlyList) {
						  String cdztDate = leaveEarlyMap.get("DATE1").toString();
							if(day.compareTo(cdztDate)==0){
								 chuQinFlag="0";
								String state = leaveEarlyMap.get("STATE").toString();
								 String daystate = getStateMethod.invoke(person).toString();
								 String daystateTitle = getStateTitleMethod.invoke(person).toString();
								if(StringUtils.isNotBlank(daystate)){
									if("0".equals(state)){
										//迟到
										  setStateMethod.invoke(person, daystate+",C");
										  person.setLateNum(person.getLateNum()+1);
										  if(StringUtils.isNotBlank(daystateTitle)){
											  setStateTitleMethod.invoke(person, daystateTitle+";C:"+cdztDate+"迟到了");
										  }else{
											  setStateTitleMethod.invoke(person,"C:"+cdztDate+"迟到了");
										  }
									  }else{//早退
										  setStateMethod.invoke(person, daystate+",Z");
										  person.setEarlyLeaveNum(person.getEarlyLeaveNum()+1);
										  if(StringUtils.isNotBlank(daystateTitle)){
											  setStateTitleMethod.invoke(person, daystateTitle+";Z:"+cdztDate+"早退了");
										  }else{
											  setStateTitleMethod.invoke(person,"Z:"+cdztDate+"早退了");
										  }
									  }
									  
								  }else{
									    if("0".equals(state)){
											//迟到
									    	setStateMethod.invoke(person, "C");
											  person.setLateNum(person.getLateNum()+1);
											  if(StringUtils.isNotBlank(daystateTitle)){
												  setStateTitleMethod.invoke(person, daystateTitle+";C:"+cdztDate+"迟到了");
											  }else{
												  setStateTitleMethod.invoke(person,"C:"+cdztDate+"迟到了");
											  }
										  }else{//早退
											  setStateMethod.invoke(person, "Z");
											  person.setEarlyLeaveNum(person.getEarlyLeaveNum()+1);
											  if(StringUtils.isNotBlank(daystateTitle)){
												  setStateTitleMethod.invoke(person, daystateTitle+";Z:"+cdztDate+"早退了");
											  }else{
												  setStateTitleMethod.invoke(person,"Z:"+cdztDate+"早退了");
											  }
										  }
								  }
								break;
							}
						  }
				  }
				  
				  //该天是否有旷工情况
				  if(absenteeisList!=null){
					  for (Map<String, Object> absenteeismap : absenteeisList) {
						  String absenteeisDate = absenteeismap.get("DATE1").toString();
						  if(day.compareTo(absenteeisDate)==0){
							  String daystateTitle = getStateTitleMethod.invoke(person).toString();
							  chuQinFlag="0";
							  String daystate = getStateMethod.invoke(person).toString();
							  if(StringUtils.isNotBlank(daystate)){
								  setStateMethod.invoke(person, daystate+",X");
								  person.setAbsentNum(person.getAbsentNum()+1);
								  if(StringUtils.isNotBlank(daystateTitle)){
									  setStateTitleMethod.invoke(person, daystateTitle+";X:"+absenteeisDate+"旷工了");
								  }else{
									  setStateTitleMethod.invoke(person,"X:"+absenteeisDate+"旷工了");
								  }
							  }else{
								  setStateMethod.invoke(person, "X");
								  person.setAbsentNum(person.getAbsentNum()+1);
								  if(StringUtils.isNotBlank(daystateTitle)){
									  setStateTitleMethod.invoke(person, daystateTitle+";X:"+absenteeisDate+"旷工了");
								  }else{
									  setStateTitleMethod.invoke(person,"X:"+absenteeisDate+"旷工了");
								  }
							  }
							  break;
						  }
					  }
				  }
				//判断是否出勤
				  if("1".endsWith(chuQinFlag)){
					  setStateMethod.invoke(person, "Q");
				  }
				  
			  }
			  setOvertimeAndBusinessData(day, person, businessTripList, getStateMethod, setStateMethod, setStateTitleMethod,getStateTitleMethod,map);
		  }
		  
		
		
	}
	
	/**
	 * day为休息日时设置该天状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 下午8:01:30
	 * @param day
	 * @param person
	 * @param kqglLeaveList
	 * @param leaveEarlyList
	 * @param absenteeisList
	 * @param businessTripList
	 * @param overTimeList
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void setHolidayState(String day, PersionInfo person, List<Map<String, Object>> businessTripList,Map<String,Map<String, Object>> overTimeList,String field,String flag) throws Exception {
		//person.setOneState("J");
		if(!"1".equals(flag)){
			//如果不是请假里的公休，公休天数就加1
			person.setHolidays(person.getHolidays() + 1);
		}
		  Class<PersionInfo> clazz = PersionInfo.class;
		  String titleField = field + "Title";
			PropertyDescriptor propertyDescriptor1 = new PropertyDescriptor(titleField,clazz);
			  Method getStateTitleMethod = propertyDescriptor1.getReadMethod();
			  Method setStateTitleMethod = propertyDescriptor1.getWriteMethod();
			  
		  PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field,clazz);
		  Method getStateMethod = propertyDescriptor.getReadMethod();
		  Method setStateMethod = propertyDescriptor.getWriteMethod();
		  String daystate =getStateMethod.invoke(person).toString();
		  if(!StringUtils.isNotBlank(daystate)){
			  setStateMethod.invoke(person,"J");
		  }
		  //person.setHolidays(person.getHolidays()+1);
		  Map<String, Object> map = overTimeList.get(day);
		  setOvertimeAndBusinessData(day, person, businessTripList, getStateMethod, setStateMethod,setStateTitleMethod,getStateTitleMethod, map);
		
	}
	
	//设置某天某用户的加班、出差数据
	private void setOvertimeAndBusinessData(String day, PersionInfo person, List<Map<String, Object>> businessTripList,
			Method getStateMethod, Method setStateMethod,Method setStateTitleMethod, Method getStateTitleMethod, Map<String, Object> overTimeDataMap)
					throws IllegalAccessException, InvocationTargetException, Exception {
		if(overTimeDataMap!=null){//有该天的加班数据
			  String daystateTitle = getStateTitleMethod.invoke(person).toString();
			  String daystate = getStateMethod.invoke(person).toString();
			  if(StringUtils.isNotBlank(daystate)){
				  setStateMethod.invoke(person, daystate+",A");
				 
			  }else{
				  setStateMethod.invoke(person, "A");
			  }
			  String type = overTimeDataMap.get("TYPE").toString();
			  String[] startStopTime = overTimeDataMap.get("STARTSTOPTIME").toString().split("-");
			  if("0".equals(type)){
				  //按天
				 String  titleTime;
				  double days = Double.valueOf(overTimeDataMap.get("DAYS").toString());
				  if(days==1){
					  titleTime="1天";
				  }else{
					  titleTime="半天";
				  }
				  person.setOverTimeDays(person.getOverTimeDays() + days);
				  if(StringUtils.isNotBlank(daystateTitle)){
					  setStateTitleMethod.invoke(person, daystateTitle+";A:从" + startStopTime[0] + "加班到" + startStopTime[0] + "加班了" + titleTime);
				  }else{
					  setStateTitleMethod.invoke(person,"A:从" + startStopTime[0] + "加班到" + startStopTime[0] + "加班了" + titleTime);
				  }
				  
			  }else{//按小时
				  long hours = Long.valueOf(overTimeDataMap.get("HOURS").toString());
				  person.setOverTimeHours(person.getOverTimeHours() +  hours );
				  if(StringUtils.isNotBlank(daystateTitle)){
					  setStateTitleMethod.invoke(person, daystateTitle+";A:从" + startStopTime[0] + "加班到" + startStopTime[0] + "加班了" + hours +"小时");
				  }else{
					  setStateTitleMethod.invoke(person,"A:从" + startStopTime[0] + "加班到" + startStopTime[0] + "加班了" + hours +"小时");
				  }
			  }
		  }
		  if(businessTripList!=null){
			  long tempDay = sdf.parse(day).getTime();
			  for (Map<String, Object> businessTrip : businessTripList) {
				  String businessTripStartTime = businessTrip.get("STARTTIME").toString();
				  String businessTripEndTime = businessTrip.get("ENDTIME").toString();
				long endDate = sdf.parse(businessTripEndTime).getTime();
				long startDate = sdf.parse(businessTripStartTime).getTime();
				if(startDate<=tempDay   && tempDay<=endDate){
					String longTime = businessTrip.get("LONGTIME").toString();
					String daystateTitle = getStateTitleMethod.invoke(person).toString();
					 String daystate = getStateMethod.invoke(person).toString();
					  if(StringUtils.isNotBlank(daystate)){
						  setStateMethod.invoke(person, daystate+",O");
						  if(StringUtils.isNotBlank(daystateTitle)){
							  setStateTitleMethod.invoke(person, daystateTitle+";O:从" + businessTripStartTime + "到" + businessTripEndTime + "出差了" + longTime +"天");
						  }else{
							  setStateTitleMethod.invoke(person,"O:从" + businessTripStartTime + "到" + businessTripEndTime + "出差了" + longTime +"天");
						  }
					  }
					  break;
				}
			}
		  }
	}

	/**
	 * 根据请假数据，设置每一天是否有请假数据,并统计总的事假、病假天数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 下午3:27:55
	 * @param result
	 * @param kqglLeaveInfomap
	 * @param days
	 */
   private void setLeaveDatas(List<PersionInfo> result,
			Map<String, List<Map<String, Object>>> kqglLeaveInfomap, List<String> days) {
	   for (PersionInfo entry : result) {
		   List<Map<String, Object>> list = kqglLeaveInfomap.get(entry.getUserId());
		   
		   for (String day : days) {
			   for (Map<String, Object> map : list) {
				  String startDate =  map.get("STARTDATE").toString();
				  String endDate =  map.get("ENDDATE").toString();
				  String startAmOrPm =  map.get("STARTAMORPM").toString();
				  String endAmOrPm =  map.get("ENDAMORPM").toString();
				  String leaveType =  map.get("LEAVETYPE").toString();
				  if(day.compareTo(startDate)>0){
					  
				  }
			   }
		   }
	   }
		
		
	}

	////把数据转成key、value的形式、key为卡号、value为一个list是出差数据
	private void changeBusinessTripData(List<Map<String, Object>> businessTripList,Map<String, List<Map<String, Object>>> businessTripmap) {
		 Set<String> userids = businessTripmap.keySet();
			for (Map<String, Object> map : businessTripList) {
				if(userids.contains(map.get("USERID"))){
					businessTripmap.get(map.get("USERID")).add(map);
				}else{
					List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
					templist.add(map);
					businessTripmap.put(map.get("USERID").toString(), templist);
				}
			}
	}

	////把数据转成key、value的形式、key为卡号、value为一个list是加班数据
	private void changeOverTimeData(List<Map<String, Object>> overTimeList,Map<String, List<Map<String, Object>>> overTimemap) {
		     Set<String> userids = overTimemap.keySet();
				for (Map<String, Object> map : overTimeList) {
					if(userids.contains(map.get("USERID"))){
						overTimemap.get(map.get("USERID")).add(map);
					}else{
						List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
						templist.add(map);
						overTimemap.put(map.get("USERID").toString(), templist);
					}
				}
	}

	////把数据转成key、value的形式、key为卡号、value为一个list是旷工数据
	private void changeAbsenteeismData(List<Map<String, Object>> absenteeismList,
			Map<String, List<Map<String, Object>>> absenteeismap) {
		Set<String> userids = absenteeismap.keySet();
		for (Map<String, Object> map : absenteeismList) {
			if(userids.contains(map.get("USERID"))){
				absenteeismap.get(map.get("USERID")).add(map);
			}else{
				List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
				templist.add(map);
				absenteeismap.put(map.get("USERID").toString(), templist);
			}
		}
		
	}

	////把数据转成key、value的形式、key为卡号、value为一个list是迟到/早退数据
	private void changeleaveEarlyData(List<Map<String, Object>> leaveEarlylist,
			Map<String, List<Map<String, Object>>> leaveEarlymap) {
		Set<String> userids = leaveEarlymap.keySet();
		for (Map<String, Object> map : leaveEarlylist) {
			if(userids.contains(map.get("USERID"))){
				leaveEarlymap.get(map.get("USERID")).add(map);
			}else{
				List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
				templist.add(map);
				leaveEarlymap.put(map.get("USERID").toString(), templist);
			}
			
		}
		
	}

	//把考勤数据转成key、value的形式、key为卡号、value为一个list是请假数据
	private void changekqglLeaveInfoData(List<Map<String, Object>> list,Map<String, List<Map<String, Object>>> kqglLeaveInfomap) {
		Set<String> userids = kqglLeaveInfomap.keySet();
		for (Map<String, Object> map : list) {
			if(userids.contains(map.get("USERID"))){
				kqglLeaveInfomap.get(map.get("USERID")).add(map);
			}else{
				List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
				templist.add(map);
				kqglLeaveInfomap.put(map.get("USERID").toString(), templist);
			}
			
		}
		
	}

	//把考勤数据转成key、value的形式、key为卡号、value为一个list是考勤数据
	private void changeKqData(List<KqglAttendenceHandle> kqInfo, Map<String, List<KqglAttendenceHandle>> map) {
		Set<String> cardNum = map.keySet();
		for (KqglAttendenceHandle kq : kqInfo) {
			if(cardNum.contains(kq.getCardNumber())){
				map.get(kq.getCardNumber()).add(kq);
			}else{
				List<KqglAttendenceHandle> list = new ArrayList<KqglAttendenceHandle>();
				list.add(kq);
				map.put(kq.getCardNumber(), list);
			}
		}
		
	}

	//把考勤信息转成考勤map数据，key为卡号,value为考勤数据
	private Map<String, KqglAttendenceHandle> getKqMap(List<KqglAttendenceHandle> kqInfo) {
		Map<String, KqglAttendenceHandle> map = new HashMap<String, KqglAttendenceHandle>();
		for (KqglAttendenceHandle kqglAttendenceHandle : kqInfo) {
			map.put(kqglAttendenceHandle.getCardNumber(), kqglAttendenceHandle);
		}
		return map;
	}
	/**
	 * 根据某些用户id获取该用户的一些卡号，及用户信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午6:38:41
	 * @param userId
	 */
	private void getCardNoms(String userIds,JSONObject json) {
		Map<String,SysFlowUserVo> userInfo = new HashMap<String,SysFlowUserVo>();//key为卡号，value为用户
		StringBuilder cardNoms = new StringBuilder();//所有的卡号
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userIds);
		String[] userids = userIds.split(",");
		for (String userid : userids) {
			SysFlowUserVo userVo = map.get(userid);
			if(StringUtils.isNotBlank(userVo.getCardNumber())){
				cardNoms.append(userVo.getCardNumber()+",");
			}
			
		}
		if(StringUtils.isNotBlank(cardNoms.toString())){
			json.put("cardNum", cardNoms.substring(0,cardNoms.length()-1));
		}else{
			json.put("cardNum", "");
		}
		
	}
	/**
	 * 获取当前部门及子部门的人员id
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月30日 上午10:26:22
	 * @param cruDeptId
	 * @return
	 */
	private String getUserIds(String cruDeptId) {
		StringBuilder sb = new StringBuilder();
		JSONArray jsonArry = service.getDeptAndUserBydeptId(cruDeptId);
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if (!StringUtils.isNotBlank(jsonObj.getString("deptlevel"))) {
				//该json数据为人员
				sb.append(jsonObj.getString("uuid") + ",");
				
			}
		}
		String userids = sb.toString();
		return userids.substring(0, userids.length()-1);
	}
	//根据用户id设置用户信息
		private void setUserInfo(String userids, List<PersionInfo> list,List<String> days,Map<Object, Object> holidayInfo) {
			StringBuilder cardNoms = new StringBuilder();//所有的卡号
			Map<String, SysFlowUserVo> map1 = UserUtil.getUserVo(userids);
			String[] userid =userids.split(",");
			for(int i=0;i<userid.length;i++){
				SysFlowUserVo userVo = map1.get(userid[i]);
				if(StringUtils.isNotBlank(userVo.getCardNumber())){
					cardNoms.append(userVo.getCardNumber()+",");
				}
				PersionInfo entry = new PersionInfo();
				entry.setUserId(userid[i]);
				entry.setUserName(userVo.getUserNameFull());
				entry.setDeptid(userVo.getUserDeptId());
				entry.setDeptName(userVo.getUserDeptName());
				entry.setCard(userVo.getCardNumber());
				list.add(entry);
			}
		}
		
		
		
		//判断某天是否是工作日
		private String isHoliday(String day, Map<Object, Object> holidayInfo) {
			String isHoliday = "";
			try {
				if(holidayInfo.get(day) != null){
					isHoliday = holidayInfo.get(day).toString();
				}else{
					int weekDay = DateUtil.getWeekDay(sdf.parse(day));
					if(weekDay == 6 || weekDay ==7){
						//休息日
						isHoliday = "1";
					}else{
						//工作日
						isHoliday = "0";
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return isHoliday;
		}
}
