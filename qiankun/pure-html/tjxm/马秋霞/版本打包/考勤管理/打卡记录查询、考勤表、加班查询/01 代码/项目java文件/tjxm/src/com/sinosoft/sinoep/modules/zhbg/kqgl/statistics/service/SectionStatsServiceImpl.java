package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service.BusinessTripService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处长和科长统计的serviceimp
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月29日 下午3:01:46
 */
@Service
public class SectionStatsServiceImpl implements SectionStatsService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	 @Autowired
	 private TreeService service;
	 @Autowired
	 HolidaySetService holidaySetService;
	 @Autowired
	 private BusinessTripService businessTripService;
	 @Autowired
	 private MyUserUtil myUserUtil;
	 @Autowired
	KqglUtil kqglUtil;
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<PersonalStats> getList(String timeRange, String userids,String deptid,String deptname) throws Exception {
		String startDate = "";
		String endDate = "";
		//JSONObject jsonObj = new JSONObject();//用于存放一个map的key为用户id，value为用户名
		Map<String,SysFlowUserVo> userInfo = new HashMap<String,SysFlowUserVo>();//用于存放一个map的key为用户id，value为用户信息
		Map<String,List<Map<String,Object>>> userLeaveInfo = new HashMap<String,List<Map<String,Object>>>();//key表示用户id,value表示该用户的一些请假数据
		Map<String,Integer> userOrder= new HashMap<String,Integer>();
		JSONObject cardNumAndUserInfo = new JSONObject();
		Map<String,List<KqglAttendenceHandle>> map = new HashMap<String,List<KqglAttendenceHandle>>();//key为卡号、value为一个list是考勤数据
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		if(StringUtils.isNotBlank(deptid)){
			//说明此处是点击部门领导查询统计跳转过来
			if(!StringUtils.isNotBlank(userids)){
				userids = getUserIds(deptid,userOrder);
			}
			
		}else{
			if(!StringUtils.isNotBlank(userids)){
				//获取当前部门及子部门下的所有用户id
				userids = getUserIds(UserUtil.getCruDeptId(),userOrder);
			}
		}
		//某些用户某段时间的请假数据
		List<Map<String,Object>> leaveData = getOverTimeData(startDate,endDate,userids);
		changeOverTimeData(leaveData,userLeaveInfo);
		//查询某段时间某些人做为同行人的出差记录
		String[] users = userids.split(",");
		List<Map<String,Object>> BusinessTrips = businessTripService.findTripColleagueByUserId(startDate,endDate,users);
		//根据用户id获取用户id对应的用户信息
		setUserInfo(userids,userInfo,cardNumAndUserInfo);
		//统计外出、出差、加班次数.....
		String sql = getSql(startDate,endDate, CommonUtils.commomSplit(userids ));
		System.out.print(sql);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		List<PersonalStats> personalStatsList = setEntity(list,userInfo,BusinessTrips,userOrder);
		//查询某些人的考勤卡号
		//getCardNoms(userids,cardNumAndUserInfo);
		//查询某段时间某些人的考勤数据
		List<KqglAttendenceHandle> kqInfo = getKqInfo(startDate, endDate, CommonUtils.commomSplit(cardNumAndUserInfo.getString("cardNum")));
		//把考勤数据转成key、value的形式、key为卡号、value为一个list是考勤数据
		 changeKqData(kqInfo,map);
		//获取某段时间的节假日信息，判断从开始日期到结束日期每一天是否是工作日
		Map<Object, Object> holidayInfo = holidaySetService.getHolidaySets(startDate, endDate);
		List<String> days = DateUtil.getBetweenDates1(startDate, endDate);
		 //统计人的无出入记录数
		 setData(personalStatsList,map,holidayInfo,days);
		// 根据用户某段时间的请假数据，统计其病假及事假天数
		 setSickCasualDays(userLeaveInfo,personalStatsList,startDate,endDate);
		return personalStatsList;
	} 
	
	// 根据用户某段时间的请假数据，统计其病假及事假天数
	private void setSickCasualDays(Map<String, List<Map<String, Object>>> userLeaveInfo,List<PersonalStats> personalStatsList, String startDate, String endDate) throws Exception {
		for (PersonalStats personalStats : personalStatsList) {
			List<Map<String, Object>> leaveData = userLeaveInfo.get(personalStats.getId());
			setSickCasualDays(leaveData,personalStats,startDate,endDate);
		}
		
	}
	
	//根据某个人某段时间的请假数据，统计该用户的病假及事假天数
	private void setSickCasualDays(List<Map<String, Object>> leaveData, PersonalStats personalStats,String startDate,String endDate) throws Exception {
		if(leaveData!=null){
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
					leaveTimeCnt(personalStats, startDate, endDate, endDate1, endAmOrPm, leaveType);
				}else if(startDate1.compareTo(startDate)>=0 && endDate.compareTo(startDate1)>=0 && endDate1.compareTo(endDate)>=0){
					//查询的开始时间<=请假的开始时间<=查询的结束时间，请假的结束时间>查询的结束时间时，统计事假和病假的请假时长
					leaveTimeCnt1(personalStats, startDate, endDate, startDate1, startAmOrPm, leaveType);
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
		
			
	}

	private void leaveTimeCnt(PersonalStats personalStats, String startDate, String endDate, String endDate1,
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
	@SuppressWarnings("unused")
	private void leaveTimeCnt1(PersonalStats personalStats, String startDate, String endDate, String startDate1,
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
				personalStats.setCasualDays(personalStats.getCasualDays() + 0.5);
			}else if("2".equals(leaveType)){
				//病假
				personalStats.setSickDays(personalStats.getSickDays() + 0.5);
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
	//把加班数据转成一个map,即某个用户对应的一些加班数据
	private void changeOverTimeData(List<Map<String, Object>> overTimeData,Map<String, List<Map<String, Object>>> userOverTimeInfo) {
		for (Map<String, Object> map : overTimeData) {
			List<Map<String, Object>> userOverTimeData = userOverTimeInfo.get(map.get("USERID"));
			if(userOverTimeData==null){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list.add(map);
				userOverTimeInfo.put(map.get("USERID").toString(), list);
			}else{
				userOverTimeData.add(map);
			}
		}
		
	}

	/**
	 * 设置每个人迟到早退无出入记录数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月8日 下午3:51:43
	 * @param personalStatsList
	 * @param map
	 */
	private void setData(List<PersonalStats> personalStatsList, Map<String, List<KqglAttendenceHandle>> map,Map<Object, Object> holidayInfo,List<String> days) {
		for (PersonalStats personalStats : personalStatsList) {
			if(StringUtils.isNotBlank(personalStats.getCardNum())){
				List<KqglAttendenceHandle> list = map.get(personalStats.getCardNum());
				Map<String,KqglAttendenceHandle> kqdata = changKqData(list);
				setKqCnt(personalStats,kqdata,holidayInfo,days);
			}
		}
	}
	/**
	 * 把list格式的考勤数据变成map格式的考勤数据,key为哪天，value为哪天的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月23日 下午7:06:51
	 * @param kqInfo
	 * @return
	 */
	private Map<String, KqglAttendenceHandle> changKqData(List<KqglAttendenceHandle> kqInfo) {
		Map<String,KqglAttendenceHandle> map = new HashMap<String,KqglAttendenceHandle>();
		if(kqInfo!=null){
			for (KqglAttendenceHandle kqinfo : kqInfo) {
				map.put(kqinfo.getImportDate(), kqinfo);
				
			}
		}
		return map;
	}

	/**
	 * 根据节假日信息和考勤数据设置该用的迟到、早退、无出入记录次数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月8日 下午4:57:25
	 * @param personalStats
	 * @param map
	 * @param holidayInfo
	 */
	private void setKqCnt(PersonalStats personalStats, Map<String,KqglAttendenceHandle> kqdata,
			Map<Object, Object> holidayInfo,List<String> days) {
		try {
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
						/*
						String shouldQrTime = kqday.getImportDate() + " " + CommonConstants.QR_TIME;
						String shouldQcTime = kqday.getImportDate() + " " + CommonConstants.QC_TIME;
						long qrhourOff = DateUtil.getMinuteOff(shouldQrTime, qrTime);
						long qchourOff = DateUtil.getMinuteOff(qcTime, shouldQcTime);*/
						if(!(StringUtils.isNotBlank(qrTime) || StringUtils.isNotBlank(qcTime))){
							//签入和签出都为空,未出入记录数+2
							personalStats.setNoComeGoCnt1(personalStats.getNoComeGoCnt1()+2);
						}else if(StringUtils.isNotBlank(qrTime) && StringUtils.isNotBlank(qcTime)){
							//签入、签出时间都不为空，需要计算迟到、早退次数
							
							/*if(qrhourOff >120){
								personalStats.setLateNum(personalStats.getLateNum()+1);
							}
							if(qchourOff >120){
								personalStats.setEarlyLeaveNum(personalStats.getEarlyLeaveNum()+1);
							}*/
						}else if(StringUtils.isNotBlank(qrTime) ){
							//签入时间不为空
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	//根据用户id设置用户信息
	private void setUserInfo(String userids, Map<String,SysFlowUserVo> map,JSONObject json) {
		StringBuilder cardNoms = new StringBuilder();//所有的卡号
		//Map<String, SysFlowUserVo> map1 = UserUtil.getUserVo(userids);
		Map<String, SysFlowUserVo> map1 = myUserUtil.getUserVo(userids);
		String[] userid =userids.split(",");
		for(int i=0;i<userid.length;i++){
			SysFlowUserVo userVo = map1.get(userid[i]);
			if(StringUtils.isNotBlank(userVo.getCardNumber())){
				cardNoms.append(userVo.getCardNumber()+",");
			}
			map.put(userid[i], userVo);
		}
		if(StringUtils.isNotBlank(cardNoms.toString())){
			json.put("cardNum", cardNoms.substring(0,cardNoms.length()-1));
		}else{
			json.put("cardNum", "");
		}
	}
	//把考勤信息转成考勤map数据，key为卡号,value为考勤数据
	@SuppressWarnings("unused")
	private Map<String, KqglAttendenceHandle> getKqMap(List<KqglAttendenceHandle> kqInfo) {
		Map<String, KqglAttendenceHandle> map = new HashMap<String, KqglAttendenceHandle>();
		for (KqglAttendenceHandle kqglAttendenceHandle : kqInfo) {
			map.put(kqglAttendenceHandle.getCardNumber(), kqglAttendenceHandle);
		}
		return map;
	}

	//把考勤数据转成key为卡号，value为考勤数据的形式
	private void changeKqData(List<KqglAttendenceHandle> kqinfo,
			Map<String, List<KqglAttendenceHandle>> map) {

		Set<String> cardSet1 = map.keySet();
		for (KqglAttendenceHandle kqglAttendenceHandle : kqinfo) {
			if(cardSet1.contains(kqglAttendenceHandle.getCardNumber())){
				map.get(kqglAttendenceHandle.getCardNumber()).add(kqglAttendenceHandle);
			}else{
				List<KqglAttendenceHandle> list = new ArrayList<KqglAttendenceHandle>();
				list.add(kqglAttendenceHandle);
				map.put(kqglAttendenceHandle.getCardNumber(), list);
			}
		}
	
	}

	/**
	 * 查询某段时间内某些考勤卡号的一些考勤信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月8日 上午9:03:09
	 * @param startDate
	 * @param endDate
	 * @param cardNum
	 * @return
	 */
	private List<KqglAttendenceHandle> getKqInfo(String startDate, String endDate, String cardNo) {
		
		StringBuilder kqSql = new StringBuilder("select t.* from kqgl_attendence_handle t where t.card_number in (" + cardNo + ")");
		if(StringUtils.isNotBlank(startDate)){
			kqSql.append(" and t.import_date >= '" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			kqSql.append("  and  t.import_date <= '" + endDate + "'");
		}
		List<KqglAttendenceHandle> kqData = jdbcTemplate.query(kqSql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class));
		return kqData;
	}

	/**
	 * 获取当前部门及子部门的人员id
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月30日 上午10:26:22
	 * @param cruDeptId
	 * @return
	 */
	private String getUserIds(String cruDeptId,Map<String,Integer> userOrder) {
		StringBuilder sb = new StringBuilder();
		JSONArray deptUserList = service.getDeptUserTreeByDeptId(cruDeptId, 20);
		Integer i=0;
		for (Object object : deptUserList) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			String type = jsonObj.getString("type");
			if("user".equals(type)){
				sb.append(jsonObj.getString("uuid") + ",");
				if(userOrder.get(jsonObj.getString("uuid"))==null){
					userOrder.put(jsonObj.getString("uuid"), i);
					i++;
				}
			}
		}
		String userids = sb.toString();
		return userids.substring(0, userids.length()-1);
		
	}
	

	/**
	 * 把查出来的数据放到实体类里
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午4:26:37
	 * @param list
	 * @return
	 */
	private List<PersonalStats> setEntity(List<Map<String, Object>> list,Map<String,SysFlowUserVo>  userInfo,List<Map<String,Object>> BusinessTrips,Map<String,Integer> userOrder) {
		List<PersonalStats> personalStatsList = new ArrayList<PersonalStats>();
		for (Map<String, Object> map : list) {
			PersonalStats PersonalStats = new PersonalStats();
			//PersonalStats.setName(map.get("NAME").toString());
			PersonalStats.setId(map.get("CRE_USER_ID").toString());
			if(userInfo.containsKey(map.get("CRE_USER_ID").toString())){
				SysFlowUserVo user = userInfo.get(map.get("CRE_USER_ID").toString());
				PersonalStats.setName(user.getUserNameFull());
				PersonalStats.setDeptName(user.getUserDeptName());
				PersonalStats.setDeptid(user.getUserDeptId());
				PersonalStats.setCardNum(user.getCardNumber());
				userInfo.remove(map.get("CRE_USER_ID").toString());
			
			}
			PersonalStats.setOrder(userOrder.get(PersonalStats.getId()));
			if(map.get("SJCNT")==null){
				PersonalStats.setSjcnt("0");
			}else{
				PersonalStats.setSjcnt(map.get("SJCNT").toString());
			}
			if(map.get("BJCNT")==null){
				PersonalStats.setBjcnt("0");
			}else{
				PersonalStats.setBjcnt(map.get("BJCNT").toString());
			}
		
			if(map.get("CCCNT")==null){
				PersonalStats.setCccnt("0");
			}else{
				PersonalStats.setCccnt(map.get("CCCNT").toString());
			}
			if(map.get("WCCNT")==null){
				PersonalStats.setWccnt("0");
			}else{
				PersonalStats.setWccnt(map.get("WCCNT").toString());
			}
			if(map.get("BQCNT")==null){
				PersonalStats.setBqcnt("0");
			}else{
				PersonalStats.setBqcnt(map.get("BQCNT").toString());
			}
			if(map.get("OVERTIMED")==null){
				PersonalStats.setOverTimed("0");
			}else{
				PersonalStats.setOverTimed(map.get("OVERTIMED").toString());
			}
			if(map.get("OVERTIMEH")==null){
				PersonalStats.setOverTimeh("0");
			}else{
				PersonalStats.setOverTimeh(map.get("OVERTIMEH").toString());
			}
			if(map.get("LATENUM1")==null){
				PersonalStats.setLateNum1("0");
			}else{
				PersonalStats.setLateNum1(map.get("LATENUM1").toString());
			}
			if(map.get("EARLYLEAVENUM1")==null){
				PersonalStats.setEarlyLeaveNum1("0");
			}else{
				PersonalStats.setEarlyLeaveNum1(map.get("EARLYLEAVENUM1").toString());
			}
			//统计该用户在出差记录中作为同行人的出差次数
			tongjiBusinessTripCnt(PersonalStats,BusinessTrips);
			personalStatsList.add(PersonalStats);
		}
		
		Set<String> userids = userInfo.keySet();
		for (String userid : userids) {
			SysFlowUserVo user = userInfo.get(userid);
			PersonalStats PersonalStats = new PersonalStats();
			PersonalStats.setDeptid(user.getUserDeptId());
			PersonalStats.setDeptName(user.getUserDeptName());
			PersonalStats.setId(userid);
			PersonalStats.setName(user.getUserNameFull());
			PersonalStats.setCardNum(user.getCardNumber());
			PersonalStats.setOrder(userOrder.get(userid));
			//统计该用户在出差记录中作为同行人的出差次数
			tongjiBusinessTripCnt(PersonalStats,BusinessTrips);
			personalStatsList.add(PersonalStats);
		}
		personalStatsList.sort(new Comparator<PersonalStats>(){
			@Override
			public int compare(PersonalStats o1,PersonalStats o2) {
				if(o1.getOrder()!=null && o2.getOrder()!=null){
					return o1.getOrder().compareTo(o2.getOrder());
				}else{
					return 1;
				}
				
			}
		});
		return personalStatsList;
	}
	
	//统计该用户在出差记录中作为同行人的出差次数
	private void tongjiBusinessTripCnt(PersonalStats personalStats, List<Map<String, Object>> businessTrips) {
		for (Map<String, Object> map : businessTrips) {
			if( map.get("USERIDS")!=null){
				String userids = map.get("USERIDS").toString();
				String creuserid = map.get("CREUSERID").toString();
				if(userids.contains(personalStats.getId()) && (!creuserid.equals(personalStats.getId()))){
					personalStats.setCccnt((Long.valueOf(personalStats.getCccnt())+1)+"");
				}
			}
			
		}
		
	}

	private String getSql(String startDate,String endDate,String userids) {
		StringBuilder sb = new StringBuilder("");
		//统计在请假、外出、加班、出差都有的用户id,做为主表
		sb.append("select t.cre_user_id,t1.sjcnt,t2.bjcnt,t7.cccnt,t8.wccnt,t9.bqcnt,t10.overTimed,t11.overTimeh,t12.lateNum1,t13.earlyLeaveNum1 from (");
		sb.append("(select  distinct cre_user_id from KQGL_LEAVE_INFO where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_BUSINESS_TRIP_INFO where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from kqgl_go_out_info where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_SUPPLEMENT_SIGN_INFO where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_COMELATE_LEAVEEARLY_INFO where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_OVER_TIME_INFO where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append(") t ");
		/**每个人事假总次数*/
		sb.append(" left outer join(select cre_user_id,count(id) sjcnt from KQGL_LEAVE_INFO t where  t.subflag='5' and t.leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id)t1 on t.cre_user_id=t1.cre_user_id ");
		/**每个人病假总次数*/
		sb.append(" left outer join (select cre_user_id,count(id) bjcnt from KQGL_LEAVE_INFO t where  t.subflag='5' and t.leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id)t2  on t.cre_user_id=t2.cre_user_id  ");
		/**每个人事假零散天累计天数*/
		/*sb.append(" left outer join (select cre_user_id, sum(leave_long_time)  casualLeaveLongTimed from KQGL_LEAVE_INFO t where  t.subflag='5' and t.leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id)t3 on t.cre_user_id=t3.cre_user_id ");*/
		/**每个人病假零散天累计天数*/
		/*sb.append(" left outer join (select cre_user_id, sum(leave_long_time)  sickLeaveLongTimed from KQGL_LEAVE_INFO t where   t.subflag='5' and t.leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append("group by cre_user_id");
		sb.append(")t4 on t.cre_user_id=t4.cre_user_id ");*/
		/**每个人事假零散小时累计小时数*/
		/*sb.append(" left outer join (select cre_user_id, sum(leave_long_time)  casualLeaveLongTimeh from KQGL_LEAVE_INFO t where t.subflag='5' and t.leave_type='1' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id");
		sb.append(")t5 on t.cre_user_id=t5.cre_user_id ");*/
		/**每个人病假零散小时累计小时数*/
		/*sb.append(" left outer join (select cre_user_id, sum(leave_long_time)  sickLeaveLongTimeh from KQGL_LEAVE_INFO t where t.subflag='5' and t.leave_type='2' ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id");
		sb.append(")t6 on t.cre_user_id=t6.cre_user_id ");*/
		/**出差次数*/
		sb.append("left outer join (select t.cre_user_id,count(id) cccnt from KQGL_BUSINESS_TRIP_INFO t  where t.subflag='5'");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (start_time <= '" + startDate + "'  and end_time >='" + startDate +"'");
			sb.append("   or start_time <= '" + endDate + "'  and end_time >='" + endDate +"'");
			sb.append("	or start_time >='" + startDate + "'  and end_time<='" + endDate + "')");
		}
		sb.append(" group by cre_user_id");
		sb.append(")t7 on t.cre_user_id=t7.cre_user_id ");
		 /**外出次数*/
		sb.append(" left outer join (select t.cre_user_id,count(id) wccnt from kqgl_go_out_info t  where t.subflag='5'  ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  go_out_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  go_out_date<='" + endDate + "'");
		}
		sb.append(" group by cre_user_id) t8 on t.cre_user_id=t8.cre_user_id ");
		 /**补签次数*/
		sb.append(" left outer join(select t.cre_user_id,count(id) bqcnt from KQGL_SUPPLEMENT_SIGN_INFO t  where t.subflag='5' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  supplement_sign_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  supplement_sign_date<='" + endDate + "'");
		}
		sb.append(" group by cre_user_id ");
		sb.append(")t9 on t.cre_user_id=t9.cre_user_id ");
		/**加班天数*/
		sb.append("     left outer join (select t.cre_user_id, sum( over_time_long_timed)  overTimed from KQGL_OVER_TIME_INFO t  where t.subflag='5' and  over_time_type='0'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<='" + endDate + "'");
		}
		sb.append(" group by cre_user_id ");
		sb.append(")t10 on t.cre_user_id=t10.cre_user_id ");
		
		 /**加班小时数*/
		sb.append("  left outer join( select t.cre_user_id, sum( over_time_long_timeh)  overTimeh from KQGL_OVER_TIME_INFO t  where t.subflag='5' and   over_time_type='1' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<='" + endDate + "'");
		}
		sb.append(" group by cre_user_id");
		sb.append(")t11 on t.cre_user_id=t11.cre_user_id");
		/** 迟到次数*/
		sb.append("  left outer join (select t.cre_user_id,count(t.id) lateNum1 from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.subflag='5' and t.state='0' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<='" + endDate + "'");
		}
		sb.append(" group by t.cre_user_id)t12 on t.cre_user_id = t12.cre_user_id ");
		/**早退次数*/
		sb.append("  left outer join (select t.cre_user_id,count(t.id) earlyLeaveNum1 from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.subflag='5' and t.state='1' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>='" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<='" + endDate + "'");
		}
		sb.append(" group by t.cre_user_id)t13 on t.cre_user_id = t13.cre_user_id ");
		return sb.toString();
	}
	
	/**
	 *查询某些用户某段时间内的请假数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月23日 上午11:39:16
	 * @param startDate
	 * @param endDate
	 * @param cruUserId
	 * @return
	 */
	private List<Map<String,Object>>  getOverTimeData(String startDate, String endDate, String userids) {
		ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userids.split(",")));
		StringBuilder sb = new StringBuilder("select t.cre_user_id userId,t.leave_start_date startDate,t.leave_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm,t.leave_type leaveType,t.leave_long_time leaveLongTime ");
		sb.append(" from KQGL_LEAVE_INFO t where t.visible='1' and t.subflag='5'  and t.leave_type in ('1','2')");
		useridIntoSql(useridList, sb);
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   and (leave_start_date <= '" + startDate + "'  and leave_end_date >='" + startDate +"'");
			sb.append("   or leave_start_date <= '" + endDate + "'  and leave_end_date >='" + endDate +"'");
			sb.append("	or leave_start_date >='" + startDate + "'  and leave_end_date<='" + endDate + "')");
		}
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" t.cre_user_id in (");
				}else{
					sb.append(" or t.cre_user_id in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				sb.append(CommonUtils.commomSplit(useridSb.substring(0, useridSb.length()-1)) + " )");
			}
			sb.append(" )");
		}
	}

}
