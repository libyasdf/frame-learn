package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service.BusinessTripService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.DeptCount;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqglAttendenceHandleService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class DirectorStatsServiceImpl implements DirectorStatsService {
	@Autowired
	private TreeService service;
	 
	@Autowired
	JdbcTemplate jdbcTemplate;
	 
	@Autowired
	KqglUtil kqglUtil;
	 
	@Autowired
	UserInfoService userInfoService;
	 
	@Autowired
	HolidaySetService holidaySetService;
	 
	@Autowired
	private KqglAttendenceHandleService kqglAttendenceHandleService;
	@Autowired
	private BusinessTripService businessTripService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	 
	@Override
	public PageImpl getList(Pageable pageable,String timeRange, String deptid,String flg) throws Exception {
		int total=0;
		int end = pageable.getOffset()+pageable.getPageSize();
		int start = pageable.getOffset()+1;
		String tempDeptid = deptid;
		if("1".equals(flg)) {//flg为1表示考勤管理员登陆，否则不是
			tempDeptid="";
		}
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		//获取该用户下的所有处级子部门信息
		Map<String,StringBuilder> deptidsMap = new HashMap<String,StringBuilder>();//存放每个局部门下所有处级部门的部门id，key为局id,value为该局所有的处室id
		StringBuilder deptidsb = new StringBuilder();//存放所要查询的所有局部门下所有处级部门及科级部门的部门id
		StringBuilder chudeptIdsb = new StringBuilder();//存放所有的处级部门
		Map<String,StringBuilder> userIdsMap = new HashMap<String,StringBuilder>();//存放每个处部门下，所有的人员id(包括处室，和科的）
		StringBuilder userids = new StringBuilder();//所要查询的所有局的人员id
		List<DeptCount> list = new ArrayList<DeptCount>();
		if( (!StringUtils.isNotBlank(deptid))) {
			return new PageImpl(total,list);
			//return list;
		}
		Map<String,List<KqglAttendenceHandle>> kqMap = new HashMap<String,List<KqglAttendenceHandle>>();//用于用户考勤数据，key表示考勤卡号,value表示考勤数据
		
		Map<String,DeptCount> map = new HashMap<String,DeptCount> ();//key表示处级部门id,value表示处级部门的统计信息,用于和sql查出是数据做对比，最终结果会在此处
		Map<String,List<Map<String,Object>>> userOverTimeInfo = new HashMap<String,List<Map<String,Object>>>();//key表示用户id,value表示该用户的一些加班数据
		//找出要查询的部门id即deptidsb，设置该处级部门人数、人员id及子部门id
		getChildDeptInfo(deptidsb,chudeptIdsb, map,userIdsMap,userids,deptid,flg);
		if(userids.length()>0) {
			userids.deleteCharAt(userids.length()-1);
		}
		String chuDeptid = chudeptIdsb.toString();
		if("1".equals(flg) && StringUtils.isNotBlank(chuDeptid)) {
			//当前登录用户为考勤管理员，查询个个局或某些局的考勤数据，需要把处室进行分页
			deptid=chudeptIdsb.substring(0, chudeptIdsb.length()-1);
			
		}else if(!StringUtils.isNotBlank(deptid) && "0".equals(flg)){
			//当前用户不是考勤管理员，查询局的所有处
			deptid=chudeptIdsb.substring(0, chudeptIdsb.length()-1);
			//deptid = deptidsb.substring(0, deptidsb.length()-1);
		}
		
		//把各个处室进行分页（一页显示几个）
		String[] chuDeptids = deptid.split(",");
		deptid="";
		total=chuDeptids.length;
		userids.setLength(0);
		for(int i=start;i<=end;i++) {
			int index =i-1;
			if(chuDeptids.length>index) {
				deptid=deptid+chuDeptids[index]+",";
				//该页所要查询的人员
				StringBuilder chuUserIds = userIdsMap.get(chuDeptids[index]);
				if(chuUserIds!=null && StringUtils.isNotBlank(chuUserIds.toString())) {
					userids.append(chuUserIds);
				}
				
			}
		}
		//获取某些用户的考勤卡号，并根据用户属于哪个部门，在该部门下设置卡号
		String cardNum = "";
		if(StringUtils.isNotBlank(userids.toString())){
			 cardNum = getCardNums(userids.substring(0, userids.length()-1),map);
		}
		
		//根据考勤卡号获取考勤数据
		if(StringUtils.isNotBlank(cardNum)){
			cardNum = cardNum.substring(0, cardNum.length()-1);
		}
		List<KqglAttendenceHandle> kqData = new ArrayList<KqglAttendenceHandle>();
		if(StringUtils.isNotBlank(cardNum)) {
			kqData = kqglAttendenceHandleService.findByCondition(startDate,endDate,CommonUtils.commomSplit(cardNum));
		}
		changKqData(kqData,kqMap,cardNum);
		//查询某段时间某些人做为同行人的出差记录
		List<Map<String,Object>> BusinessTrips = new ArrayList<Map<String,Object>>();
		String tempUserids="";
		if(StringUtils.isNotBlank(userids.toString())) {
			tempUserids = userids.substring(0, userids.length()-1);
			String[] users = tempUserids.split(",");
			 BusinessTrips = businessTripService.findTripColleagueByUserId(startDate,endDate,users);
		}

		//获取某段时间的节假日信息，判断从开始日期到结束日期每一天是否是工作日
		Map<Object, Object> holidayInfo = holidaySetService.getHolidaySets(startDate, endDate);		
		//统计每个处级部门下总的未出入记录总数
		setSumNoComeGoCnt(map,kqMap,holidayInfo,startDate,endDate);
		
		//把考勤数据转成map格式
		//查询该用户子部门下某段时间内请假数据..
		if(StringUtils.isNotBlank(tempUserids)) {
			List<Map<String,Object>> overTimeData = getOverTimeData(startDate,endDate,tempUserids);
			changeOverTimeData(overTimeData,userOverTimeInfo);
		}
		
		//统计该用户子部门下某段时间内总的病假次数、事假总次数、出差次数..
		List<Map<String,Object>> totalInfo = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(tempUserids)) {
			 totalInfo = getTotalInfo(startDate,endDate,tempUserids);
		}
		//设置每个处级部门总的出差次数，加班总天数....................
		setDeptSumInfo(totalInfo,map);
		//设置每个处级部门总的病假天数和事假天数
		setSumSickCasualDays(map,userOverTimeInfo,startDate,endDate);
		//把每个部门下作为出差同行人的次数累积进去
		setSumColleague(map,BusinessTrips);
		//把转成页面所需的格式，并设置平均
		String[] deptids = deptid.split(",");
		Integer index=start;
		for(String chuDetpid:deptids) {
			DeptCount deptCount = map.get(chuDetpid);
			if(deptCount!=null) {
				deptCount.setIndex(index);
				index++;
				//统计每个处级部门的平均出差、加班天数、加班小时数、平均病假、事假天数、平均未出入记录数....................
				setDeptAvgInfo(deptCount);
				//统计每个处级部门的平均病假、事假天数....................
				//setAvgSickCasualDays(deptCount);
				//统计每个处级部门平均未出入记录数
				//setAvgNoComeGoCnt(deptCount);
				list.add(deptCount);
			}
			
		}
		
		return  new PageImpl(total,list);
	}
	
	//把某些用户作为同行人的也累积进去
	private void setSumColleague(Map<String, DeptCount> map, List<Map<String, Object>> businessTrips) {
		Set<Entry<String, DeptCount>> entrySet = map.entrySet();
		for (Entry<String, DeptCount> entry : entrySet) {
			DeptCount deptCount = entry.getValue();
			String userids = deptCount.getUserids().toString();
			String[] useridAry = userids.split(",");
			for (String tempUserid : useridAry) {
				for (Map<String, Object> map2 : businessTrips) {
					String creuserid = map2.get("CREUSERID").toString();
					if(map2.get("USERIDS").toString().contains(tempUserid) && (!creuserid.equals(tempUserid))){
						deptCount.setBusinessCnt(deptCount.getBusinessCnt()+1);
					}
				}
			}
		}
		
	}


	//统计每个处级部门下总的未出入记录总数
	private void setSumNoComeGoCnt(Map<String, DeptCount> map, Map<String, List<KqglAttendenceHandle>> kqMap,Map<Object, Object> holidayInfo,String startDate, String endDate) throws Exception {
		Set<Entry<String, List<KqglAttendenceHandle>>> entrySet = kqMap.entrySet();
		for (Entry<String, List<KqglAttendenceHandle>> entry : entrySet) {
			String cardNum = entry.getKey();
			List<KqglAttendenceHandle> list = entry.getValue();
			Set<Entry<String, DeptCount>> deptCountEntry = map.entrySet();
			for (Entry<String, DeptCount> entry2 : deptCountEntry) {
				DeptCount deptCount = entry2.getValue();
				if(deptCount.getCardNum().indexOf(cardNum)!=-1){
					//统计该部门的未出入记录数
					setDeptSumNoComeGoCnt(deptCount,list,holidayInfo,startDate,endDate);
				}
			}
		}
	}
	
	//统计某个部门的未出入记录数
	private void setDeptSumNoComeGoCnt(DeptCount deptCount, List<KqglAttendenceHandle> list,Map<Object, Object> holidayInfo,String startDate, String endDate) throws Exception {
		List<String> days = DateUtil.getBetweenDates1(startDate, endDate);
		Map<String,KqglAttendenceHandle> kqdata = changKqData(list);//把考勤数据转成map格式，key表示日期，value表示该天的数据
		for (String day : days) {
			String isHoliday = isHoliday(day,holidayInfo);
			if("0".equals(isHoliday)){
				//工作日
				KqglAttendenceHandle kqday = kqdata.get(day);
				if(kqday == null){
					//该天没有考勤数据
					deptCount.setNoComeGoCnt(deptCount.getNoComeGoCnt()+2);
				}else{
					String qrTime = kqday.getQrTime();
					String qcTime = kqday.getQcTime();
					if(!(StringUtils.isNotBlank(qrTime) || StringUtils.isNotBlank(qcTime))){
						//签入和签出都不为为空
						deptCount.setNoComeGoCnt(deptCount.getNoComeGoCnt()+2);
					}else if(StringUtils.isNotBlank(qrTime) && StringUtils.isNotBlank(qcTime)){
						//签入、签出时间都不为空，需要计算迟到、早退次数

					}
					else if(StringUtils.isNotBlank(qrTime) ){
						deptCount.setNoComeGoCnt(deptCount.getNoComeGoCnt()+1);
					}else if(StringUtils.isNotBlank(qcTime)){
						deptCount.setNoComeGoCnt(deptCount.getNoComeGoCnt()+1);
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
	//把某个卡号对应的考勤数据转成map格式，key为日期，value对应的为遮天的考勤数据
	private Map<String, KqglAttendenceHandle> changKqData(List<KqglAttendenceHandle> list) {
		Map<String,KqglAttendenceHandle> map = new HashMap<String,KqglAttendenceHandle>();
		if(list!=null){
			for (KqglAttendenceHandle kqinfo : list) {
				map.put(kqinfo.getImportDate(), kqinfo);
				
			}
		}
		return map;
	}

	//把考勤数据转成卡号对应的考勤数据，key表示考勤卡号,value表示考勤数据
	private void changKqData(List<KqglAttendenceHandle> kqData, Map<String, List<KqglAttendenceHandle>> kqMap,String tempcardNum) {
		for (KqglAttendenceHandle kqglAttendenceHandle : kqData) {
			String cardNum = kqglAttendenceHandle.getCardNumber();
			List<KqglAttendenceHandle> list = kqMap.get(cardNum);
			if(list==null){
				List<KqglAttendenceHandle> kqlist = new ArrayList<KqglAttendenceHandle>();
				kqlist.add(kqglAttendenceHandle);
				kqMap.put(cardNum, kqlist);
			}else{
				list.add(kqglAttendenceHandle);
			}
		}
		if(StringUtils.isNotBlank(tempcardNum)) {
			String[] cards=tempcardNum.split(",");
			for(int i=0;i<cards.length;i++){
				List<KqglAttendenceHandle> list = kqMap.get(cards[i]);
				if(list==null){
					kqMap.put(cards[i], list);
				}
			}
		}
		
	}

	//获取某些用户的考勤卡号，并根据用户属于哪个部门，在该部门下设置卡号
	private String getCardNums(String uerid,Map<String,DeptCount> map) {
		StringBuilder cardNum = new StringBuilder();
		String[] userids1 = uerid.split(",");
		List<String> useridList = new ArrayList(Arrays.asList(userids1));
		System.out.println("所要查询的人为1"+userids1.length);
		StringBuilder sql = new StringBuilder("select t.userid,t.card_number cardnumber from sys_flow_user_ext t   ");
		sql.append(" where  t.card_number is not null ");
		useridIntoSql1(useridList,sql);
		JSONObject jsonObj = userInfoService.getDateBySql(sql.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		for (Map<String, Object> tempMap : data) {
			Object cardNumberObj = tempMap.get("cardnumber");
			String classStr = cardNumberObj.getClass().toString();
			if(cardNumberObj!=null && (!"null".equals(cardNumberObj))) {
				String cardNumber = cardNumberObj.toString();
				String userid = tempMap.get("userid").toString();
				cardNum.append(cardNumber+",");
				Set<Entry<String, DeptCount>> DeptCountEntrys = map.entrySet();
				for (Entry<String, DeptCount> entry2 : DeptCountEntrys) {
					DeptCount deptCount = entry2.getValue();
					int index = deptCount.getUserids().indexOf(userid);
					if(index!=-1){
						deptCount.getCardNum().append(cardNumber+",");
						break;
					}
				}
			}
		}

		
		return cardNum.toString();
	}
	private void useridIntoSql1(List<String> useridList, StringBuilder sb) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		if(useridList.size()>0){
			sb.append("  and (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" t.userid in (");
				}else{
					sb.append(" or t.userid in (");
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
	//统计每个处级部门的平均病假、事假天数....................
	@SuppressWarnings("unused")
	private void setAvgSickCasualDays(DeptCount deptCount) {
		int peopleNum = deptCount.getPeopleNum();
		DecimalFormat df = new DecimalFormat("#0.0");
		if(peopleNum!=0){
			double avgsickdays =deptCount.getSickDays()/peopleNum;
			deptCount.setAvgSickDay(df.format(avgsickdays));
			double avgCasualDays = deptCount.getCasualDays()/peopleNum;
			deptCount.setAvgCasualDay(df.format(avgCasualDays));
		}
		
		
	}

	//设置每个处级部门总的病假天数和事假天数
	private void setSumSickCasualDays(Map<String, DeptCount> map,Map<String, List<Map<String, Object>>> userOverTimeInfo,String startDate,String endDate) throws Exception {
		Set<Entry<String,  List<Map<String, Object>>>> entrySet = userOverTimeInfo.entrySet();
		for (Entry<String, List<Map<String, Object>>> entry : entrySet) {
			String userid = entry.getKey();
			 List<Map<String, Object>> overTimeData = entry.getValue();
			Set<Entry<String,  DeptCount>> deptCountentry = map.entrySet();
			for (Entry<String, DeptCount> entry2 : deptCountentry) {
				DeptCount deptCount = entry2.getValue();
				String deptUserids = deptCount.getUserids().toString();//该部门下是所有人员id
				if(deptUserids.contains(userid)){
					//如果该部门下包含此人员，便把事假、病假的天数加到该部门下总的事假、病假天数
					setSumSickCasualDaysIntoDept(deptCount,overTimeData,startDate,endDate);
				}
			}
		}
		
	}
	
	//把某个人的请假数据统计到某个部门下
	private void setSumSickCasualDaysIntoDept(DeptCount deptCount, List<Map<String, Object>> overTimeData,String startDate,String endDate) throws Exception {
		if(overTimeData!=null){
			for (Map<String, Object> map : overTimeData) {
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
							deptCount.setCasualDays(deptCount.getCasualDays() + Double.valueOf(leaveLongTime));
						}else if("2".equals(leaveType)){
							//病假
							deptCount.setSickDays(deptCount.getSickDays() + Double.valueOf(leaveLongTime));
						}
				}else if(startDate.compareTo(startDate1)>0 && endDate1.compareTo(endDate)<=0  && endDate1.compareTo(startDate)>=0){
					//请假的开始时间<要查询的开始时间，请假的结束时间<=要查询的结束时间,请假的结束时间>=查询的开始时间,统计事假和病假的请假时长
					overTimeCnt(deptCount, startDate, endDate, endDate1, endAmOrPm, leaveType);
				}else if(startDate1.compareTo(startDate)>=0 && endDate.compareTo(startDate1)>=0 && endDate1.compareTo(endDate)>=0){
					//查询的开始时间<=请假的开始时间<=查询的结束时间，请假的结束时间>查询的结束时间时，统计事假和病假的请假时长
					overTimeCnt1(deptCount, startDate, endDate, startDate1, startAmOrPm, leaveType);
				}else{
					//查询的开始时间>请假的开始时间，查询的结束时间<请假的结束时间
					double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate, "1", "0"));
					if("1".equals(leaveType)){
						//事假
						deptCount.setCasualDays(deptCount.getCasualDays() + time);
					}else if("2".equals(leaveType)){
						//病假
						deptCount.setSickDays(deptCount.getSickDays() + time);
					}
				}
					
			}
		}
		
	}
	
	private void overTimeCnt(DeptCount deptCount, String startDate, String endDate, String endDate1,
			String endAmOrPm, String leaveType) throws Exception {
		if(endDate1.equals(startDate)){
			//请假的结束时间==查询的开始时间
			if("1".equals(endAmOrPm)){
				//请假结束时间是上午
				if("1".equals(leaveType)){
					//事假
					deptCount.setCasualDays(deptCount.getCasualDays() + 0.5);
				}else if("2".equals(leaveType)){
					//病假
					deptCount.setSickDays(deptCount.getSickDays() + 0.5);
				}
			}else{
				//请假结束时间是下午
				if("1".equals(leaveType)){
					//事假
					deptCount.setCasualDays(deptCount.getCasualDays() + 1);
				}else if("2".equals(leaveType)){
					//病假
					deptCount.setSickDays(deptCount.getSickDays() + 1);
				}
			}
		}else if(endDate1.equals(endDate)){
			double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
			if("1".equals(leaveType)){
				//事假
				deptCount.setCasualDays(deptCount.getCasualDays() + time);
			}else if("2".equals(leaveType)){
				//病假
				deptCount.setSickDays(deptCount.getSickDays() + time);
			}
			
		}else{
			double time = Double.valueOf(kqglUtil.getGoOutTime(startDate, endDate1, "1", endAmOrPm));
			if("1".equals(leaveType)){
				//事假
				deptCount.setCasualDays(deptCount.getCasualDays() + time);
			}else if("2".equals(leaveType)){
				//病假
				deptCount.setSickDays(deptCount.getSickDays() + time);
			}
		}
	}

	
	@SuppressWarnings("unused")
	private void overTimeCnt1(DeptCount deptCount, String startDate, String endDate, String startDate1,
			String startAmOrPm, String leaveType) throws Exception {
		if(startDate1.equals(startDate)){
			//请假的开始时间==查询的开始时间
			double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, startAmOrPm, "0"));
			if("1".equals(leaveType)){
				//事假
				deptCount.setCasualDays(deptCount.getCasualDays() + time);
			}else if("2".equals(leaveType)){
				//病假
				deptCount.setSickDays(deptCount.getSickDays() + time);
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
				deptCount.setCasualDays(deptCount.getCasualDays() + 0.5);
			}else if("2".equals(leaveType)){
				//病假
				deptCount.setSickDays(deptCount.getSickDays() + 0.5);
			}
		}else{
			double time = Double.valueOf(kqglUtil.getGoOutTime(startDate1, endDate, "1", "0"));
			if("1".equals(leaveType)){
				//事假
				deptCount.setCasualDays(deptCount.getCasualDays() + time);
			}else if("2".equals(leaveType)){
				//病假
				deptCount.setSickDays(deptCount.getSickDays() + time);
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
	//查询该用户子部门下某段时间内请假数据..
	private List<Map<String, Object>> getOverTimeData(String startDate, String endDate, String userids) {
		List<Object> para = new ArrayList<>();
		StringBuilder sb = new StringBuilder("select t.cre_user_id userId,t.leave_start_date startDate,t.leave_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm,t.leave_type leaveType,t.leave_long_time leaveLongTime ");
		sb.append(" from KQGL_LEAVE_INFO t where t.visible='1' and t.subflag='5' " );
		useridIntoSql(sb,userids,para);
		sb.append(" and t.leave_type in ('1','2')");
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
	
	private void useridIntoSql(StringBuilder sb,String userids,List<Object> para) {
		List<String> useridList = new ArrayList(Arrays.asList(userids.split(",")));
		
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		String inString = "";
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" cre_user_id in (");
				}else{
					sb.append(" or cre_user_id in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				inString = CommonUtils.solveSqlInjectionOfIn(useridSb.toString(), para);
				sb.append(inString + " )");
			}
			sb.append(" )");
		}
	}
	/**
	 * 设置每个处级部门总的出差次数，加班总天数....................
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月16日 下午3:36:37
	 * @param totalInfo
	 * @param map
	 */
	private void setDeptSumInfo(List<Map<String, Object>> list, Map<String, DeptCount> deptCntInfo) {
		Set<Entry<String,DeptCount>> entrySet = deptCntInfo.entrySet();
		for (Map<String, Object> map : list) {
			String userid = map.get("CRE_USER_ID").toString();
			for (Entry<String, DeptCount> entry : entrySet) {
				DeptCount deptCount = entry.getValue();
				if(deptCount.getUserids().indexOf(userid)!=-1){
					if(map.get("SJCNT")!=null){
						deptCount.setCasualCnt(deptCount.getCasualCnt()+Double.valueOf(map.get("SJCNT").toString()));
					}
					if(map.get("BJCNT")!=null){
						deptCount.setSickCnt(deptCount.getSickCnt()+Double.valueOf(map.get("BJCNT").toString()));
					}
				
					if(map.get("CCCNT")!=null){
						deptCount.setBusinessCnt(deptCount.getBusinessCnt()+Double.valueOf(map.get("CCCNT").toString()));
					}
					
					
					if(map.get("OVERTIMED")!=null){
						deptCount.setOverTimed(deptCount.getOverTimed()+Double.valueOf(map.get("OVERTIMED").toString()));
						
					}
					if(map.get("OVERTIMEH")!=null){
						deptCount.setOverTimeh(deptCount.getOverTimeh()+Double.valueOf(map.get("OVERTIMEH").toString()));
					}
					if(map.get("LATENUM1")!=null){
						deptCount.setLateNum(deptCount.getLateNum()+Double.valueOf(map.get("LATENUM1").toString()));
					}
					if(map.get("EARLYLEAVENUM1")!=null){
						deptCount.setEarlyLeaveNum(deptCount.getEarlyLeaveNum()+Double.valueOf(map.get("EARLYLEAVENUM1").toString()));
					}
				}
			}
		}
		/*for (Map<String, Object> userInfo : list) {
			String deptid = deptInfo.get("DEPTID").toString();
			DeptCount deptCount = deptCntInfo.get(deptid);
			if(deptCount==null){
				//该信息属于某个处级部门下的子部门的统计信息
				//统计处级下子部门的出差总数，总加班天数..
				setChildDeptCntInfo(deptInfo,deptCntInfo);
			}else{
				//该记录就是处级部门下的总统计信息
				setData(deptInfo,deptCount);
			}
			
		}*/
		
	}
	
	//统计某条记录是属于哪个处级部门下，那就在哪个处级部门下增加该条统计的总个数
	@SuppressWarnings("unused")
	private void setChildDeptCntInfo(Map<String, Object> deptInfo, Map<String, DeptCount> deptCount) {
		String deptid = deptInfo.get("DEPTID").toString();
		Set<Entry<String, DeptCount>> entrySet = deptCount.entrySet();
		for (Entry<String, DeptCount> entry : entrySet) {
			DeptCount tempDeptCnt = entry.getValue();
			if(tempDeptCnt.getChidDeptid().indexOf(deptid)!=-1){
				//该记录属于该处级部门的子部门
				setData(deptInfo,tempDeptCnt);
				break;
			}
		}
		
		
	}

	//把数据库查出的信息放到统计信息中
	private void setData(Map<String, Object> deptInfo, DeptCount deptCntInfo) {
		if(deptInfo.get("SICKCNT")!=null){
			deptCntInfo.setSickCnt(deptCntInfo.getSickCnt()+Double.valueOf(deptInfo.get("SICKCNT").toString()));
		}
		if(deptInfo.get("CASUALCNT")!=null){
			deptCntInfo.setCasualCnt(deptCntInfo.getCasualCnt()+Double.valueOf(deptInfo.get("CASUALCNT").toString()));
		}
		/*if(deptInfo.get("SICKDAYS")!=null){
			deptCntInfo.setSickDays(deptCntInfo.getSickDays()+Double.valueOf(deptInfo.get("SICKDAYS").toString()));
		}
		if(deptInfo.get("CASUALDAYS")!=null){
			deptCntInfo.setCasualDays(deptCntInfo.getCasualDays()+Double.valueOf(deptInfo.get("CASUALDAYS").toString()));
		}*/
		if(deptInfo.get("CCCNT")!=null){
			deptCntInfo.setBusinessCnt(deptCntInfo.getBusinessCnt() + Double.valueOf(deptInfo.get("CCCNT").toString()));
		}
		if(deptInfo.get("OVERTIMED")!=null){
			deptCntInfo.setOverTimed(deptCntInfo.getOverTimed() + Double.valueOf(deptInfo.get("OVERTIMED").toString()));
		}
		if(deptInfo.get("OVERTIMEH")!=null){
			deptCntInfo.setOverTimeh(deptCntInfo.getOverTimeh() + Double.valueOf(deptInfo.get("OVERTIMEH").toString()));
		}
		if(deptInfo.get("LATENUM")!=null){
			deptCntInfo.setLateNum(deptCntInfo.getLateNum() + Double.valueOf(deptInfo.get("LATENUM").toString()));
		}
		if(deptInfo.get("EARLYLEAVENUM")!=null){
			deptCntInfo.setEarlyLeaveNum(deptCntInfo.getEarlyLeaveNum() + Double.valueOf(deptInfo.get("EARLYLEAVENUM").toString()));
		}
		
	}

	//根据部们总的病假次数、出差总次数....及deptCount里的人数求平均
	private void setDeptAvgInfo(DeptCount deptCount) {
		int peopleNum = deptCount.getPeopleNum();
		DecimalFormat df = new DecimalFormat("#0.0");
		if(peopleNum!=0){
			double avgsickcnt = deptCount.getSickCnt()/peopleNum;
			deptCount.setAvgSickCnt(String.format("%.2f", avgsickcnt));
			double avgCasualCnt = deptCount.getCasualCnt()/peopleNum;
			deptCount.setAvgCasualCnt(String.format("%.2f", avgCasualCnt));
			double avgBusinessCnt = deptCount.getBusinessCnt()/peopleNum;
			deptCount.setAvgBusinessCnt(String.format("%.2f", avgBusinessCnt));
			double avgOverTimed = deptCount.getOverTimed()/peopleNum;
			deptCount.setAvgOverTimed(String.format("%.2f", avgOverTimed));
			double avgOverTimeh = deptCount.getOverTimeh()/peopleNum;
			deptCount.setAvgOverTimeh(String.format("%.2f", avgOverTimeh));
			double avgLateNum = deptCount.getLateNum()/peopleNum;
			deptCount.setAvgLateNum(String.format("%.2f", avgLateNum));
			double avgEarlyLeaveNum = deptCount.getEarlyLeaveNum()/peopleNum;
			deptCount.setAvgEarlyLeaveNum(String.format("%.2f", avgEarlyLeaveNum));
			double avgsickdays =deptCount.getSickDays()/peopleNum;
			deptCount.setAvgSickDay(String.format("%.2f", avgsickdays));
			double avgCasualDays = deptCount.getCasualDays()/peopleNum;
			deptCount.setAvgCasualDay(String.format("%.2f", avgCasualDays));
			double avgNoComeGoCnt = deptCount.getNoComeGoCnt()/peopleNum;
			deptCount.setAvgNoComeGoCnt(String.format("%.2f", avgNoComeGoCnt));
			
		}
		
	}

	//获取该用户下的所有处级子部门信息
	private void getChildDeptInfo(StringBuilder deptidsb,StringBuilder chudeptIdsb,Map<String,DeptCount> map,Map<String,StringBuilder> useridMap,StringBuilder userids,String tempdeptid,String flg) {
		String juId = UserUtil.getCruJuId();
		if("1".equals(flg) && StringUtils.isNotBlank(tempdeptid)) {
			//当前用户是考勤管理员，查看 的是所有部门的统计数据
			juId=tempdeptid;
			
		}
		String[] deptids = juId.split(",");
		for (String judeptId : deptids) {
			Map<String,List<JSONObject>> deptUserInfo = new HashMap<String,List<JSONObject>>();//用于存放每个局底下的的所有部门信息和用户信息，key为局id,value为部门信息或用户信息
			JSONArray jsonArray = service.getDeptAndUserTreeByDeptId(judeptId);
						
			setDeptUserInfo(deptidsb,jsonArray,deptUserInfo);
			List<JSONObject> chuDept = deptUserInfo.get(judeptId);
			if(chuDept!=null) {
				for (JSONObject jsonObject : chuDept) {
					if("dept".equals(jsonObject.getString("type"))){
						
						DeptCount peptCount = new DeptCount();
						String deptid = jsonObject.getString("uuid");
						chudeptIdsb.append(deptid+",");
						//deptidsb.append(deptid + ",");
						peptCount.setDeptid(deptid);
						peptCount.setDeptName(jsonObject.getString("name"));
						StringBuilder usersb1 = new StringBuilder();//存放处部门下所有的人员id
						useridMap.put(deptid, usersb1);//
						//设置该部门下子部门的人数及子部门的部门id
						setPeopleNumAndChidDeptIds(peptCount,deptid,deptUserInfo,userids,usersb1);
						map.put(deptid, peptCount);
					}
				}
			}
		
		}
		
		

	}
	
	//设置该部门下子部门的人数及子部门的部门id
	private void setPeopleNumAndChidDeptIds(DeptCount peptCount, String deptid,
			Map<String, List<JSONObject>> deptUserInfo,StringBuilder userids,StringBuilder usersb1) {
		List<JSONObject> list = deptUserInfo.get(deptid);
		if(list!=null && list.size()>0){
			for (JSONObject jsonObject : list) {
				if("dept".equals(jsonObject.getString("type"))){
					//部门
					peptCount.getChidDeptid().append(jsonObject.getString("uuid") + ",");
					setPeopleNumAndChidDeptIds(peptCount,jsonObject.getString("uuid"),deptUserInfo,userids,usersb1);
				}else{
					//人员
					if(peptCount.getUserids().indexOf(jsonObject.getString("uuid"))==-1){
						userids.append(jsonObject.getString("uuid") + ",");
						usersb1.append(jsonObject.getString("uuid") + ",");
						peptCount.getUserids().append(jsonObject.getString("uuid") + ",");
						peptCount.setPeopleNum(peptCount.getPeopleNum()+1);
					}
					
				}
			}
		}
		
		
	}

	//把jsonArray里的数据转成map格式（key为pid,value为部门信息或用户信息)
	private void setDeptUserInfo(StringBuilder deptidsb,JSONArray jsonArray, Map<String, List<JSONObject>> deptUserInfo) {
		for (Object object : jsonArray) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			deptidsb.append(jsonObj.getString("uupid") + ",");
			List<JSONObject>  list = deptUserInfo.get(jsonObj.getString("uupid"));
			if(list==null){
				List<JSONObject> templist =new ArrayList<JSONObject>();
				templist.add(jsonObj);
				deptUserInfo.put(jsonObj.getString("uupid"), templist);
			}else{
				deptUserInfo.get(jsonObj.getString("uupid")).add(jsonObj);
			}
		}
		
	}

	private List<Map<String, Object>> getTotalInfo(String startDate,String endDate,String userids) {
		StringBuilder sb = new StringBuilder("");
		List<Object> para = new ArrayList<>();
		//统计在请假、外出、加班、出差都有的用户id,做为主表
		sb.append("select t.cre_user_id,t1.sjcnt,t2.bjcnt,t7.cccnt,t8.wccnt,t9.bqcnt,t10.overTimed,t11.overTimeh,t12.lateNum1,t13.earlyLeaveNum1 from (");
		sb.append("(select  distinct cre_user_id from KQGL_LEAVE_INFO where subflag='5' ");
		useridIntoSql(sb,userids,para);
		sb.append(" )");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_BUSINESS_TRIP_INFO where subflag='5' ");
		useridIntoSql(sb,userids,para);
		sb.append(" )");
		sb.append("union");
	/*	sb.append("(select  distinct cre_user_id from kqgl_go_out_info where subflag='5' and cre_user_id in(" + userids + "))");
		sb.append("union");*/
		sb.append("(select  distinct cre_user_id from KQGL_SUPPLEMENT_SIGN_INFO where subflag='5' ");
		useridIntoSql(sb,userids,para);
		sb.append(" )");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_COMELATE_LEAVEEARLY_INFO where subflag='5' ");
		useridIntoSql(sb,userids,para);
		sb.append(" )");
		sb.append("union");
		sb.append("(select  distinct cre_user_id from KQGL_OVER_TIME_INFO where subflag='5' ");
		useridIntoSql(sb,userids,para);
		sb.append(" )");
		sb.append(") t ");
		/**每个人事假总次数*/
		sb.append(" left outer join(select cre_user_id,count(id) sjcnt from KQGL_LEAVE_INFO t where  t.subflag='5' and t.leave_type='1' ");
		
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
		sb.append(" group by cre_user_id)t1 on t.cre_user_id=t1.cre_user_id ");
		/**每个人病假总次数*/
		sb.append(" left outer join (select cre_user_id,count(id) bjcnt from KQGL_LEAVE_INFO t where  t.subflag='5' and t.leave_type='2' ");
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
		sb.append(" group by cre_user_id");
		sb.append(")t7 on t.cre_user_id=t7.cre_user_id ");
		 /**外出次数*/
		sb.append(" left outer join (select t.cre_user_id,count(id) wccnt from kqgl_go_out_info t  where t.subflag='5'  ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  go_out_date>= ?");
			para.add(startDate); 
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  go_out_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by cre_user_id) t8 on t.cre_user_id=t8.cre_user_id ");
		 /**补签次数*/
		sb.append(" left outer join(select t.cre_user_id,count(id) bqcnt from KQGL_SUPPLEMENT_SIGN_INFO t  where t.subflag='5' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  supplement_sign_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  supplement_sign_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by cre_user_id ");
		sb.append(")t9 on t.cre_user_id=t9.cre_user_id ");
		/**加班天数*/
		sb.append("     left outer join (select t.cre_user_id, sum( over_time_long_timed)  overTimed from KQGL_OVER_TIME_INFO t  where t.subflag='5' and  over_time_type='0'");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by cre_user_id ");
		sb.append(")t10 on t.cre_user_id=t10.cre_user_id ");
		
		 /**加班小时数*/
		sb.append("  left outer join( select t.cre_user_id, sum( over_time_long_timeh)  overTimeh from KQGL_OVER_TIME_INFO t  where t.subflag='5' and   over_time_type='1' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  over_time_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  over_time_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by cre_user_id");
		sb.append(")t11 on t.cre_user_id=t11.cre_user_id");
		/** 迟到次数*/
		sb.append("  left outer join (select t.cre_user_id,count(t.id) lateNum1 from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.subflag='5' and t.state='0' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by t.cre_user_id)t12 on t.cre_user_id = t12.cre_user_id ");
		/**早退次数*/
		sb.append("  left outer join (select t.cre_user_id,count(t.id) earlyLeaveNum1 from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.subflag='5' and t.state='1' ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and  cdzt_date>= ?");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append("  and  cdzt_date<= ?");
			para.add(endDate);
		}
		sb.append(" group by t.cre_user_id)t13 on t.cre_user_id = t13.cre_user_id ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		return list;
	}

}
