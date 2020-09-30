package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service.BusinessTripService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service.KqglLeaveInfoService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.services.GoOutInfoService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.salary.services.SalaryService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 个人统计的serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月19日 下午5:49:58
 */
@Service
public class PersonalStatsServiceImpl implements PersonalStatsService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	SalaryService service;
	
	@Autowired
	HolidaySetService holidaySetService;
	
	@Autowired
	KqglLeaveInfoService kqglLeaveInfoService;
	
	@Autowired
	BusinessTripService  businessTripService;
	
	@Autowired
	GoOutInfoService  goOutInfoService;
	
	@Autowired
	KqglUtil kqglUtil;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<PersonalStats> getList(String timeRange) throws Exception {
		/**
		 * select 
       			(select count(id)   from KQGL_BUSINESS_TRIP_INFO where cre_user_id='97588' ) cccnt,
       			( select count(id)    from kqgl_go_out_info where cre_user_id='97588' ) wccnt,
        		( select count(id)  bqcnt  from KQGL_SUPPLEMENT_SIGN_INFO where cre_user_id='97588' ) bqcnt
 			from dual;
		 */
		//存放个人统计统计信息的entity
		PersonalStats personalStats = new PersonalStats();
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		List<PersonalStats> list=new ArrayList<PersonalStats>();
		//获取当前登陆用户的id
		String cruUserId = UserUtil.getCruUserId();
		//当前用户某段时间的请假数据
		List<Map<String,Object>> leaveData = getLeaveData(startDate,endDate,cruUserId);
		//统计当前登陆用户的外出次数、出差次数、补签次数、加班时长
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(cruUserId);
		String cardNo = map.get(cruUserId).getCardNumber();
		List<Object> param = new ArrayList<>();
		StringBuilder sb = getTongJiInfo(startDate, endDate, cruUserId,cardNo,param);
		Map<String,Object> personMap = jdbcTemplate.queryForMap(sb.toString(),param.toArray());
		//把查出来的数据放到实体类里
		setData(personalStats, personMap);
		//获取当前登陆用户的考勤信息
		List<KqglAttendenceHandle> kqInfo = getKqInfo(startDate, endDate, cruUserId,personalStats);
		Map<String,KqglAttendenceHandle> kqdata = changKqData(kqInfo);
		//获取某段时间的节假日信息，判断从开始日期到结束日期每一天是否是工作日
		Map<Object, Object> holidayInfo = holidaySetService.getHolidaySets(startDate, endDate);		
		//根据节假日信息和考勤数据，设置该用户的未出入次数
		 //setKqInfo(personalStats,holidayInfo,kqdata,startDate,endDate);
		if(StringUtils.isNotBlank(personalStats.getCardNum())){
			setKqInfo1(personalStats,holidayInfo,kqdata,startDate,endDate);
		}
		
		//根据当前用户某段时间的请假数据，统计当前用户的病假及事假天数
		setSickCasualDays(leaveData,personalStats,startDate,endDate);
		list.add(personalStats);
		return list;
	}
	//根据当前用户某段时间的请假数据，统计当前用户的病假及事假天数
	private void setSickCasualDays(List<Map<String, Object>> leaveData, PersonalStats personalStats,String startDate,String endDate) throws Exception {
		for (Map<String, Object> map : leaveData) {
			String startDate1 = map.get("STARTDATE").toString();
			String endDate1 = map.get("ENDDATE").toString();
			String startAmOrPm = map.get("STARTAMORPM").toString();
			String endAmOrPm = map.get("ENDAMORPM").toString();
			String leaveType = map.get("LEAVETYPE").toString();
			String leaveLongTime = map.get("LEAVELONGTIME").toString();
			if(startDate1.compareTo(startDate)>=0 && endDate1.compareTo(endDate)<=0){
				//请假的开始时间>=要查询的开始时间，并且请假的结束时间<=要查询的结束时间
					if("1".equals(leaveType)){
						//事假
						personalStats.setCasualDays(personalStats.getCasualDays() + Double.valueOf(leaveLongTime));
					}else if("2".equals(leaveType)){
						//病假
						personalStats.setSickDays(personalStats.getSickDays() + Double.valueOf(leaveLongTime));
					}
			}else if(startDate.compareTo(startDate1)>0 && endDate1.compareTo(endDate)<=0  && endDate1.compareTo(startDate)>=0){
				//请假的开始时间<要查询的开始时间，请假的结束时间<=要查询的结束时间,请假的结束时间>=查询的开始时间,统计事假和病假的请假时长
				overTimeCnt(personalStats, startDate, endDate, endDate1, endAmOrPm, leaveType);
			}else if(startDate1.compareTo(startDate)>=0 && endDate.compareTo(startDate1)>=0 && endDate1.compareTo(endDate)>=0){
				//查询的开始时间<=请假的开始时间<=查询的结束时间，请假的结束时间>查询的结束时间时，统计事假和病假的请假时长
				overTimeCnt1(personalStats, startDate, endDate, startDate1, startAmOrPm, leaveType);
			}else{
				//查询的开始时间>请假的开始时间，查询的结束时间<请假的结束时间
				double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate, "1", "0"));
				if("1".equals(leaveType)){
					//事假
					personalStats.setCasualDays(personalStats.getCasualDays() + time);
				}else if("2".equals(leaveType)){
					//病假
					personalStats.setSickDays(personalStats.getSickDays() + time);
				}
			}
				
		}
			
	}
	private void overTimeCnt1(PersonalStats personalStats, String startDate, String endDate, String startDate1,
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
	private void overTimeCnt(PersonalStats personalStats, String startDate, String endDate, String endDate1,
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
		
	

	//无出入记录数
	private void setKqInfo1(PersonalStats personalStats, Map<Object, Object> holidayInfo,
			Map<String, KqglAttendenceHandle> kqdata, String startDate, String endDate) throws Exception {
		List<String> days = DateUtil.getBetweenDates1(startDate, endDate);
		for (String day : days) {
			String isHoliday = isHoliday(day,holidayInfo);
			if("0".equals(isHoliday)){
				//工作日
				KqglAttendenceHandle kqday = kqdata.get(day);
				if(kqday == null){
					//该天没有考勤数据
					personalStats.setNoComeGoCnt1(personalStats.getNoComeGoCnt1()+2);
				}else{
					String qrTime = kqday.getQrTime();
					String qcTime = kqday.getQcTime();
					/*String shouldQrTime = kqday.getImportDate() + " " + CommonConstants.QR_TIME;
					String shouldQcTime = kqday.getImportDate() + " " + CommonConstants.QC_TIME;
					long qrhourOff = DateUtil.getMinuteOff(shouldQrTime, qrTime);
					long qchourOff = DateUtil.getMinuteOff(qcTime, shouldQcTime);*/
					if(!(StringUtils.isNotBlank(qrTime) || StringUtils.isNotBlank(qcTime))){
						//签入和签出都不为为空
						personalStats.setNoComeGoCnt1(personalStats.getNoComeGoCnt1()+2);
					}else if(StringUtils.isNotBlank(qrTime) && StringUtils.isNotBlank(qcTime)){
						//签入、签出时间都不为空，需要计算迟到、早退次数
						
						/*if(qrhourOff >120){
							personalStats.setLateNum(personalStats.getLateNum()+1);
						}
						if(qchourOff >120){
							personalStats.setEarlyLeaveNum(personalStats.getEarlyLeaveNum()+1);
						}*/
					}
					else if(StringUtils.isNotBlank(qrTime) ){
						personalStats.setNoComeGoCnt1(personalStats.getNoComeGoCnt1()+1);
						/*if(qrhourOff >120){
							personalStats.setLateNum(personalStats.getLateNum()+1);
						}*/
					}else if(StringUtils.isNotBlank(qcTime)){
						personalStats.setNoComeGoCnt1(personalStats.getNoComeGoCnt1()+1);
						/*if(qchourOff >120){
							personalStats.setEarlyLeaveNum(personalStats.getEarlyLeaveNum()+1);
						}*/
					}
				}
			}
		}
	}

	//把查出来的数据放入到实体类里
	private void setData(PersonalStats personalStats, Map<String, Object> personMap) {
		personalStats.setSequenceNum(1);
		personalStats.setName(UserUtil.getCruUserName());
		personalStats.setWccnt(personMap.get("WCCNT").toString());
		personalStats.setCccnt(personMap.get("CCCNT").toString());
		personalStats.setOverTimed(personMap.get("OVERTIMED").toString());
		personalStats.setOverTimeh(personMap.get("OVERTIMEH").toString());
		personalStats.setBjcnt(personMap.get("BJCNT").toString());
		personalStats.setSjcnt(personMap.get("SJCNT").toString());
		//personalStats.setSickLeaveLongTimed(personMap.get("SICKLEAVELONGTIMED").toString());
		//personalStats.setSickLeaveLongTimeh(personMap.get("SICKLEAVELONGTIMEH").toString());
		//personalStats.setCasualLeaveLongTimed(personMap.get("CASUALLEAVELONGTIMED").toString());
		//personalStats.setCasualLeaveLongTimeh(personMap.get("CASUALLEAVELONGTIMEH").toString());
		personalStats.setComeGoCnt(personMap.get("COMEGOCNT").toString());
		personalStats.setBqcnt(personMap.get("BQCNT").toString());
		personalStats.setEarlyLeaveNum1(personMap.get("EARLYLEAVENUM1").toString());
		/*personalStats.setTotalOverTime(personMap.get("TOTALOVERTIME").toString());*/
		//personalStats.setQjcnt(personMap.get("QJCNT").toString());
		personalStats.setLateNum1(personMap.get("LATENUM1").toString());
		/*personalStats.setHolidayOverCnt(personMap.get("HOLIDAYOVERCNT").toString());
		personalStats.setWorkOverCnt(personMap.get("WORKOVERCNT").toString());*/
	}
	
	/**
	 * 把list格式的考勤数据变成map格式的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月23日 下午7:06:51
	 * @param kqInfo
	 * @return
	 */
	private Map<String, KqglAttendenceHandle> changKqData(List<KqglAttendenceHandle> kqInfo) {
		Map<String,KqglAttendenceHandle> map = new HashMap<String,KqglAttendenceHandle>();
		for (KqglAttendenceHandle kqinfo : kqInfo) {
			map.put(kqinfo.getImportDate(), kqinfo);
			
		}
		return map;
	}

	/**
	 * 根据当前用户的考勤数据，(迟到早退已通过sql计算 此方法暂时不用)
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 下午3:52:24
	 * @param personalStats
	 * @param holidayInfo
	 * @param kqInfo
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void setKqInfo(PersonalStats personalStats, Map<Object, Object> holidayInfo,
			Map<String,KqglAttendenceHandle> kqdata,String startDate,String endDate) throws Exception {
		List<String> days = DateUtil.getBetweenDates1(startDate, endDate);
		String cruUserId = UserUtil.getCruUserId();
		for (String day : days) {
			String isHoliday = isHoliday(day,holidayInfo);
			if("0".equals(isHoliday)){
				//工作日
				KqglAttendenceHandle kqday = kqdata.get(day);
				if(kqday == null){
					//该天没有考勤数据，计算该天是否是旷工，如果是旷工则absentNum加1（统计旷工次数）
					//setAbsentInfo(personalStats, cruUserId, day);
					//现在是无出入记录+2
					personalStats.setNoComeGoCnt(personalStats.getNoComeGoCnt()+2);
				}else{
					//有考勤数据（该天至少有一个签到或签退数据），但是有迟到或早退的情况（统计迟到或早退的次数）
					String qrTime = kqday.getQrTime();
					String qcTime = kqday.getQcTime();
					String shouldQrTime=kqday.getImportDate() + " " + CommonConstants.QR_TIME;
					String shouldQcTime=kqday.getImportDate() + " " + CommonConstants.QC_TIME;
					if("".equals(qrTime) || "".equals(qcTime)){
						//没有签到或没签退，计为旷工(现在改为无出入记录数+1）
						//personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
						personalStats.setNoComeGoCnt(personalStats.getNoComeGoCnt()+2);
					}else{
						//同时有签到和签退时间，
						long qrhourOff = DateUtil.getMinuteOff(shouldQrTime, qrTime);
						long qchourOff = DateUtil.getMinuteOff(qcTime, shouldQcTime);
						/*if(qrhourOff >2 && qchourOff>2){
							//迟到并早退(查询该天是否在请假、出差、外出中是否有数据，如果都没有迟到、早退各加一次）
							boolean flag =hasQjCcWcInfo(cruUserId, day);
							if(!flag){
								personalStats.setLateNum(personalStats.getLateNum()+1);
								personalStats.setEarlyLeaveNum(personalStats.getEarlyLeaveNum()+1);
							}
							
							
						}else*/ if(qrhourOff >120){
							//迟到(超过2个小时签到，(查询该天是否在请假、出差、外出中是否有数据，如果都没有迟到加一次））
							boolean flag =hasQjCcWcInfo(cruUserId, day);
							if(!flag){
								personalStats.setLateNum(personalStats.getLateNum()+1);
							}
							
						}else if(qchourOff>120){
							//早退(超过2个小时签到，(查询该天是否在请假、出差、外出中是否有数据，如果都没有迟到加一次））
							boolean flag =hasQjCcWcInfo(cruUserId, day);
							if(!flag){
								personalStats.setEarlyLeaveNum(personalStats.getEarlyLeaveNum()+1);
							}
							
						}
					}
					
				}
			}
			
		}
		
	}
	
	//查询当前用户，该天是否有请假、外出、出差信息，true表示有数据，false表示没有数据
	private boolean hasQjCcWcInfo(String cruUserId, String day) {
		boolean flag = true;
		List<Map<String,Object>> list = kqglLeaveInfoService.getDataByUseridAndDay(cruUserId,day);
		if(list.size() == 0){
			//请假中没有数据，查询该天在出差中是否有数据
			List<Map<String,Object>> chuChaiData = businessTripService.getDataByUseridAndDay(cruUserId, day);
			if(chuChaiData.size() == 0){
				//请假出差中没数据、查询外出中是否有数据，如果都没数据表示迟到并早退都加1
				List<Map<String,Object>> waiChuData = goOutInfoService.getDataByUseridAndDay(cruUserId, day);
				if(waiChuData.size() == 0){
					flag =false;
				}
			}
			
		}
		return flag;
		
	}
	
	/**
	 * 设置当前用户该天是否是旷工情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午1:38:16
	 * @param personalStats
	 * @param cruUserId
	 * @param day
	 */
	@SuppressWarnings("unused")
	private void setAbsentInfo(PersonalStats personalStats, String cruUserId, String day) {
		//没有该天的考勤数据，判断改天是否是旷工
		//查询该天，当前用户有没有请假
		List<Map<String,Object>> list = kqglLeaveInfoService.getDataByUseridAndDay(cruUserId,day);
		if(list.size()>0){
			//有请假信息
			if(list.size()==1){
				//该天只有一条请假信息
				Map<String,Object> qjData = list.get(0);
				if(day.equals(qjData.get("STARTDATE")) || day.equals(qjData.get("ENDDATE"))){
					if(qjData.get("STARTDATE").equals(qjData.get("ENDDATE"))){
						//开始日期和结束日期相等，有可能该天请了一上午、或一下午
						if(qjData.get("STARTAMORPM").equals(qjData.get("ENDAMORPM"))){
							//请了半天的假，但一整天没来（一上午或一下午），计为旷工
							personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
						}
						
					}else if(day.equals(qjData.get("STARTDATE"))){
						//该天正好是在请假的开始日期相等,和结束时间不相等，判断开始时间的上下午是选的上午还是下午，如果是上午那就是该天一天都请假，如果是下午，该天就计为旷工
						if("0".equals(qjData.get("STARTAMORPM"))){
							//下午
							personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
						}
					}else{
						//该天跟请假的结束日期相等,和开始时间不相等，如果是下午那就是该天一天都请假，如果是上午，该天就计为旷工
						if("1".equals(qjData.get("ENDAMORPM"))){
							//上午
							personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
						}
					}
				}
			}/*else{
				//涉及到该天有两条请假信息,第一条的结束日期和第二条的开始日期相等，结束日期的上下午和开始日期的上下午不相等的情况
				Map<String,Object> qjData = list.get(0);
				Map<String,Object> qjData1 = list.get(1);
				if(day.equals(qjData.get("ENDDATE")) && day.equals(qjData1.get("STARTDATE"))){
					if("1".equals(qjData.get("ENDAMORPM")) && "0".equals(qjData1.get("STARTAMORPM"))){
						
					}
					
				}
				
			}*/
		}else{
			//没有请假信息，需要查询是否有出差和外出情况
			List<Map<String,Object>> chuChaiData = businessTripService.getDataByUseridAndDay(cruUserId, day);
			if(chuChaiData.size() == 0  || chuChaiData == null){
				//没有出差信息,查询是否有外出情况
				List<Map<String,Object>> waiChuData = goOutInfoService.getDataByUseridAndDay(cruUserId, day);
				
				if(waiChuData.size() == 0 || waiChuData == null){
					//没有请假，没有出差、也没有外出记录，则计为旷工
					personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
				}else if(waiChuData.size()==1){
					Map<String,Object> waiChuInfo = waiChuData.get(0);
					
					//有一条外出情况，并且该天恰好是外出的开始时间或结束时间
					if(day.equals(waiChuInfo.get("STARTDATE")) || day.equals(waiChuInfo.get("ENDDATE"))){
						if(waiChuInfo.get("STARTDATE").equals(waiChuInfo.get("ENDDATE"))){
							//开始日期和结束日期相等，有可能该天请了一上午、或一下午
							if(waiChuInfo.get("STARTAMORPM").equals(waiChuInfo.get("ENDAMORPM"))){
								//请了半天的假，但一整天没来（一上午或一下午），计为旷工
								personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
							}
							
						}else if(day.equals(waiChuInfo.get("STARTDATE"))){
							//该天正好是在请假的开始日期相等,和结束时间不相等，判断开始时间的上下午是选的上午还是下午，如果是上午那就是该天一天都请假，如果是下午，该天就计为旷工
							if("0".equals(waiChuInfo.get("STARTAMORPM"))){
								//下午
								personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
							}
						}else{
							//该天跟请假的结束日期相等,和开始时间不相等，如果是下午那就是该天一天都请假，如果是上午，该天就计为旷工
							if("1".equals(waiChuInfo.get("ENDAMORPM"))){
								//上午
								personalStats.setAbsentNum(personalStats.getAbsentNum()+1);
							}
						}
					}
					
					
					
				}
			
			}
		}
	}
	
	/**
	 * 判断某天是否是节假日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月23日 下午3:46:47
	 * @param day
	 * @param holidayInfo
	 * @return
	 * @throws Exception 
	 */
	private String isHoliday(String day, Map<Object, Object> holidayInfo) throws Exception {
		String isHoliday = "";
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
		return isHoliday;
	}

	/**
	 * 获取当前登录用户某段时间内的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 下午3:19:47
	 * @param startDate
	 * @param endDate
	 * @param cruUserId
	 */
	private List<KqglAttendenceHandle> getKqInfo(String startDate, String endDate, String cruUserId,PersonalStats personalStats) {
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(cruUserId);
		List<Object> para = new ArrayList<>();
		String cardNo = map.get(cruUserId).getCardNumber();
	    personalStats.setCardNum(cardNo);
		StringBuilder kqSql = new StringBuilder("select t.* from kqgl_attendence_handle t where t.card_number='" + cardNo + "'");
		if(StringUtils.isNotBlank(startDate)){
			kqSql.append(" and t.import_date >= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			kqSql.append("  and  t.import_date <= ?");
			para.add(endDate);
		}
		List<KqglAttendenceHandle> kqData = jdbcTemplate.query(kqSql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class),para.toArray());
		return kqData;
	}
	
	/**
	 * 当前登陆用户的外出次数、出差次数、补签次数、加班时长
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午7:21:27
	 * @param startDate
	 * @param endDate
	 * @param cruUserId
	 * @return
	 */
	private StringBuilder getTongJiInfo(String startDate, String endDate, String cruUserId,String cardNo,List<Object> para) {
		
		StringBuilder sb = new StringBuilder("select ");
		//事假总次数
		sb.append("(select count(id) from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "' and leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= ?  and leave_end_date >= ?");
			sb.append("   or leave_start_date <= ?  and leave_end_date >= ?");
			sb.append("	or leave_start_date >= ? and leave_end_date<= ?)");
			para.add(startDate);
			para.add(startDate);
			para.add(endDate);
			para.add(endDate);
			para.add(startDate);
			para.add(endDate);
		}
		sb.append(" ) sjcnt, ");
		//病假总次数
		sb.append("(select count(id) from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "' and leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
					sb.append("   and (leave_start_date <= ?  and leave_end_date >= ?");
					sb.append("   or leave_start_date <= ?  and leave_end_date >= ?");
					sb.append("	or leave_start_date >= ?  and leave_end_date<= ?)");
					para.add(startDate);
					para.add(startDate);
					para.add(endDate);
					para.add(endDate);
					para.add(startDate);
					para.add(endDate);
		}
		sb.append(" ) bjcnt, ");
		//病假零散天累计天数
		/*sb.append("(select case when sum(leave_long_time) is null then 0 else sum(leave_long_time) end leaveLongTimed from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and  leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" ) sickLeaveLongTimed, ");*/
		//病假零散小时累计小时数
		/*sb.append("(select case when sum(leave_long_time) is null then 0 else sum(leave_long_time) end leaveLongTimed from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and   leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" ) sickLeaveLongTimeh, ");*/
		//事假零散天累计天数
		/*sb.append("(select case when sum(leave_long_time) is null then 0 else sum(leave_long_time) end leaveLongTimed from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and  leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" ) casualLeaveLongTimed, ");*/
		//事假零散小时累计小时数
		/*sb.append("(select case when sum(leave_long_time) is null then 0 else sum(leave_long_time) end leaveLongTimed from KQGL_LEAVE_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and   leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" ) casualLeaveLongTimeh, ");*/
		//加班总次数
		sb.append(" (select count(id) from KQGL_OVER_TIME_INFO  where cre_user_id='" + cruUserId + "' and subflag='" + ConfigConsts.APPROVAL_FLAG + "'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<= ?");
			para.add(endDate);
		}
		sb.append(" ) totalOverTime ,  ");
		//求加班天数
		sb.append("  (select case when sum( over_time_long_timed) is null then 0 else sum( over_time_long_timed) end overTimed  from KQGL_OVER_TIME_INFO where cre_user_id='" + cruUserId + "'  and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and over_time_type='0'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<= ?");
			para.add(endDate);
		}
		sb.append("   ) overTimed,");
		//工作日加班小时数
		sb.append("  (select case when sum( OVER_TIME_LONG_TIMEH) is null then 0 else sum( OVER_TIME_LONG_TIMEH) end overTimeh  from KQGL_OVER_TIME_INFO where cre_user_id='" + cruUserId + "'  and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and over_time_type='1'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<= ?");
			para.add(endDate);
		}
		sb.append("   ) overTimeh,");
		//迟到次数
		sb.append("  (select count(id) chiDaoCnt from KQGL_COMELATE_LEAVEEARLY_INFO  where cre_user_id='" + cruUserId + "' and subflag='5' and state='0' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<= ?");
			para.add(endDate);
		}
		sb.append(" ) lateNum1,");
		//早退次数
		sb.append("  (select count(id) zaoTuiCnt from KQGL_COMELATE_LEAVEEARLY_INFO  where cre_user_id='" + cruUserId + "' and subflag='5' and state='1' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<= ?");
			para.add(endDate);
		}
		sb.append(" ) earlyLeaveNum1,");
		//求节假日加班总次数
		/*sb.append("  (select  count(id) from KQGL_OVER_TIME_INFO where cre_user_id='" + cruUserId + "'  and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and over_time_type='" + ConfigConsts.HOLIDAY_OVERTIME_CODE + "'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<='" + endDate + "'");
		}
		sb.append("   ) holidayOverCnt,");
		//工作日加班总次数
		sb.append("  (select count(id)  from KQGL_OVER_TIME_INFO where cre_user_id='" + cruUserId + "'  and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  and over_time_type='" + ConfigConsts.WORK_OVERTIME_CODE + "'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<='" + endDate + "'");
		}
		sb.append("   ) workOverCnt,");*/
		//求出入次数
		if(StringUtils.isNotBlank(cardNo)){
			sb.append(" ( select count(id) from kqgl_attendance t where t.card_number='' ");
			if(StringUtils.isNotBlank(startDate)){
				sb.append(" and  t.import_date >= ?");
				para.add(startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				sb.append("  and  t.import_date <= ?");
				para.add(endDate);
			}
			sb.append(" ) comeGoCnt,");
		}else{
			sb.append(" 0 comeGoCnt,");
		}
		
		//求出差次数
		sb.append("  (select count(id)   from KQGL_BUSINESS_TRIP_INFO where (cre_user_id='" + cruUserId + "'  or trip_colleague_ids like '%" + cruUserId + "%')  and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (start_time <= ?  and end_time >= ?");
			sb.append("   or start_time <= ?  and end_time >= ?");
			sb.append("	or start_time >= ?  and end_time<= ?)");
			para.add(startDate);
			para.add(startDate);
			para.add(endDate);
			para.add(endDate);
			para.add(startDate);
			para.add(endDate);
		}
		sb.append("   ) cccnt,");
		//求外出次数
		sb.append("  ( select count(id)    from kqgl_go_out_info where cre_user_id='" + cruUserId  + "'   and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  go_out_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  go_out_date<= ?");
			para.add(endDate);
		}
		sb.append("   ) wccnt , ");
		//求补签
		sb.append("  ( select count(id)  bqcnt  from KQGL_SUPPLEMENT_SIGN_INFO where cre_user_id='" + cruUserId + "'   and  subflag='" + ConfigConsts.APPROVAL_FLAG + "'  ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  supplement_sign_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  supplement_sign_date<= ?");
			para.add(endDate);
		}
		sb.append("  ) bqcnt "); 
		
		
		sb.append(" from dual ");
		return sb;
	}
	
	/**
	 * 获取当前用户某段时间内的请假数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月23日 上午11:39:16
	 * @param startDate
	 * @param endDate
	 * @param cruUserId
	 * @return
	 */
	private List<Map<String,Object>>  getLeaveData(String startDate, String endDate, String cruUserId) {
		List<Object> para = new ArrayList<>();
		StringBuilder sb = new StringBuilder("select t.leave_start_date startDate,t.leave_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm,t.leave_type leaveType,t.leave_long_time leaveLongTime ");
		sb.append(" from KQGL_LEAVE_INFO t where t.visible='1' and t.subflag='5' and t.cre_user_id='" + cruUserId + "'  and t.leave_type in ('1','2')");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= ?  and leave_end_date >= ?");
			sb.append("   or leave_start_date <= ?  and leave_end_date >= ?");
			sb.append("	or leave_start_date >= ?  and leave_end_date<= ?)");
			para.add(startDate);
			para.add(startDate);
			para.add(endDate);
			para.add(endDate);
			para.add(startDate);
			para.add(endDate);
		}
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		return list;
	}
	/**
	 * 获取某一天是否是休息日
	 * @param list
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unused")
	private double getGoOutDay(List<Map<String, Object>> list, String day) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(day));
			if(week!=6 && week!=7){
				//工作日
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
}
