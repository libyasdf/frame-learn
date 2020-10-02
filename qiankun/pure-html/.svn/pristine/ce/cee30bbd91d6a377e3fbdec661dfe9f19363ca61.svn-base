package com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity.MeetingRoomInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service.MeetingRoomInfoService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.dao.MeetingRoomUserInfoDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseState;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议室的使用情况
 * TODO 
 * @author 马秋霞
 * @Date 2019年3月5日 下午1:51:46
 */
@Service
public class MeetingRoomUseInfoServiceImp implements MeetingRoomUseInfoService {
	
	@Autowired
	MeetingRoomInfoService meetingRoomInfoService;
	@Autowired
	MeetingRoomUserInfoDao dao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取某天所有可用会议室的使用情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午3:00:42
	 * @param date
	 * @return
	 */
	@Override
	public List<MeetingRoomUseState> getMeetingRoomUseList(String date) {
		List<MeetingRoomUseState> result = new ArrayList<MeetingRoomUseState>();//存放页面最终所需要的数据
		//存放的是每个会议室被占用的情况，key为会议室id,value是会议室某天被占用的的信息
		Map<String,List<MeetingRoomUseInfo>> MeetingRoomUseMap = new HashMap<String,List<MeetingRoomUseInfo>>();
		//从会议室预约情况进入 只显示可用的会议室
		MeetingRoomInfo meetRoomInfo=new MeetingRoomInfo();
		meetRoomInfo.setCz("1");
		List<MeetingRoomInfo> meetingRoomInfoList = meetingRoomInfoService.getList(meetRoomInfo);
		//查询某天所有可用会议室的被占用的情况,并转成map放到MeetingRoomUseMap中
		 findByUseDate(date,meetingRoomInfoList,MeetingRoomUseMap);
		//根据会议室信息及会议室的占用情况，设置页面所需的数据，即某天所有可用会议室每个时间段的使用情况
		 changeToListByRoomUseMapAndRoomInfo(meetingRoomInfoList,MeetingRoomUseMap,result);
		return result;
	}
	
	/**
	 * 根据会议室信息及会议室的占用情况，设置页面所需的数据，即某天所有可用会议室每个时间段的使用情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午4:06:59
	 * @param meetingRoomInfoList
	 * @param meetingRoomUseMap
	 * @param result
	 */
	private void changeToListByRoomUseMapAndRoomInfo(List<MeetingRoomInfo> meetingRoomInfoList,
			Map<String, List<MeetingRoomUseInfo>> meetingRoomUseMap, List<MeetingRoomUseState> result) {
		for (MeetingRoomInfo roomInfo : meetingRoomInfoList) {
			MeetingRoomUseState roomUseState = new MeetingRoomUseState();
			roomUseState.setMeetingRoomId(roomInfo.getId());
			roomUseState.setMeetingRoomName(roomInfo.getMeetingRoomName());
			roomUseState.setContent(roomInfo.getContent());
			roomUseState.setEquipment(roomInfo.getEquipment());
			//根据会议室的占用情况，设置该会议室每个小时的状态
			setRoomStatus(roomUseState,meetingRoomUseMap.get(roomInfo.getId()));
			result.add(roomUseState);
		}
		
	}
	
	/**
	 * 根据会议室的占用情况，设置该会议室每个小时的状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午4:17:32
	 * @param roomUseState
	 * @param list
	 */
	private void setRoomStatus(MeetingRoomUseState roomUseState, List<MeetingRoomUseInfo> list) {
		if(list!=null && list.size()>0) {
			for (MeetingRoomUseInfo meetingRoomUseInfo : list) {
				String userTime = meetingRoomUseInfo.getUseTime();//占用时间
				String startTime = userTime.substring(0, userTime.indexOf(":"));
				String endTime = userTime.substring(userTime.indexOf("-")+1, userTime.lastIndexOf(":"));
				Integer start = Integer.valueOf(startTime);
				Integer end = Integer.valueOf(endTime);
				while(start<end) {
					if(start==9) roomUseState.setNoneToTenState(meetingRoomUseInfo.getSubflag());
					else if(start==10) roomUseState.setTenToElevenState(meetingRoomUseInfo.getSubflag());
					else if(start==11) roomUseState.setElevenToTwelveState(meetingRoomUseInfo.getSubflag());
					else if(start==12) roomUseState.setTwelveToThirteenState(meetingRoomUseInfo.getSubflag());
					else if(start==13) roomUseState.setThirteenToFourteenState(meetingRoomUseInfo.getSubflag());
					else if(start==14) roomUseState.setFourteenToFifteenState(meetingRoomUseInfo.getSubflag());
					else if(start==15) roomUseState.setFifteenToSixteenState(meetingRoomUseInfo.getSubflag());
					else if(start==16) roomUseState.setSixteenToSeventeenState(meetingRoomUseInfo.getSubflag());
					else if(start==17) roomUseState.setSeventeenToEighteenState(meetingRoomUseInfo.getSubflag());
					start++;
				}
			}
		}
		
		
	}

	/**
	 * 查询某天所有可用会议室的被占用详情数据,转成map形式，放到MeetingRoomUseInfoMap中
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午3:16:38
	 * @param date
	 * @param meetingRoomInfoList
	 * @return
	 */
	private void findByUseDate(String date, List<MeetingRoomInfo> meetingRoomInfoList,Map<String,List<MeetingRoomUseInfo>> MeetingRoomUseInfoMap) {
		StringBuilder roomIds = new StringBuilder();//存放所有可用会议室的id
		List<Object> param = new ArrayList<Object>();
		String roomIdStr = "";
		//把可用会议室的id放到roomIds中
		for (MeetingRoomInfo meetingRoomInfo : meetingRoomInfoList) {
			roomIds.append(meetingRoomInfo.getId()+",");
		}
		if(roomIds.length()>0) {
			roomIds.delete(roomIds.length()-1, roomIds.length());
			roomIdStr = CommonUtils.commomSplit(roomIds.toString());
		}
		//查询某天所有可用会议室的被占用数据
		StringBuilder sql = new StringBuilder(" select t.meetingroom_id meetingroomId,t.apply_id applyId,t.use_time useTime,t1.subflag from hygl_meetingroom_useinfo t ");
		sql.append(" left outer join hygl_meeting_apply t1 on t.apply_id=t1.id  ");
		sql.append(" where    (t1.subflag='1' or t1.subflag='5') and t.use_date = ? ");
		param.add(date);
		if(StringUtils.isNotBlank(roomIdStr)) {
			sql.append(" and  t.meetingroom_id in ( " + CommonUtils.solveSqlInjectionOfIn(roomIdStr,param) + ") order by t.meetingroom_id,t.use_time ");
		}
		List<MeetingRoomUseInfo> meetingRoomUseList = jdbcTemplate.query(sql.toString(), new RowMapper<MeetingRoomUseInfo>() {
			@Override
			public MeetingRoomUseInfo mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingRoomUseInfo roomUseInfo = new MeetingRoomUseInfo();
				roomUseInfo.setApplyId(result.getString("applyId"));
				roomUseInfo.setMeetingroomId(result.getString("meetingroomId"));
				roomUseInfo.setUseTime(result.getString("useTime"));
				
				if("1".equals(result.getString("subflag"))) {
					roomUseInfo.setSubflag("1");
				}else {
					roomUseInfo.setSubflag("2");
				}
				
				return roomUseInfo;
			}
			
		},param.toArray());
		
		//把list转成key-value形式放到MeetingRoomUseInfoMap
		for (MeetingRoomUseInfo meetingRoomUseInfo : meetingRoomUseList) {
			if(MeetingRoomUseInfoMap.get(meetingRoomUseInfo.getMeetingroomId())==null) {
				List<MeetingRoomUseInfo> list = new ArrayList<MeetingRoomUseInfo>();
				list.add(meetingRoomUseInfo);
				MeetingRoomUseInfoMap.put(meetingRoomUseInfo.getMeetingroomId(), list);
			}else {
				
				MeetingRoomUseInfoMap.get(meetingRoomUseInfo.getMeetingroomId()).add(meetingRoomUseInfo);
			}
		}
	}
	
	/**
	 * 保存所有的会议室申请数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月8日 下午3:50:20
	 * @param data
	 * @return
	 */
	@Override
	public List<MeetingRoomUseInfo> saveAll(String data) {
		JSONObject jsonObj = JSONObject.fromObject(data);
		String applyId = jsonObj.getString("applyId");
		//删除该会议申请之前设置的会议室
		String deleSql = " delete from hygl_meetingroom_useinfo where apply_id=? ";
		jdbcTemplate.update(deleSql,applyId);
		//保存数据
		String cruDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<MeetingRoomUseInfo> list = new ArrayList<MeetingRoomUseInfo>();
		JSONArray jsonArry = jsonObj.getJSONArray("resultList");
		for (Object object : jsonArry) {
			JSONObject tempjson = JSONObject.fromObject(object);
			MeetingRoomUseInfo info = new MeetingRoomUseInfo();
			info.setCreUserId(UserUtil.getCruUserId());
			info.setCreUserName(UserUtil.getCruUserName());
			info.setCreTime(cruDate);
			info.setUpdateTime(cruDate);
			info.setUpdateUserId(UserUtil.getCruUserId());
			info.setUpdateUserName(UserUtil.getCruUserName());
			info.setApplyId(applyId);
			info.setMeetingroomName(tempjson.getString("meetingroomName"));
			info.setMeetingroomId(tempjson.getString("meetingRoomid"));
			info.setUseDate(tempjson.getString("useDate"));
			info.setUseTime(tempjson.getString("useTime"));
			info.setWeekDate(tempjson.getString("weekDay"));
			list.add(info);
		}
		list = dao.save(list);
		return list;
	}
	
	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月9日 上午9:57:33
	 * @param id
	 */
	@Override
	public void deletById(String id) {
		dao.delete(id);
		
	}
	
	/**
	 * 检查会议室是否被占用
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月9日 上午11:19:48
	 * @param data
	 */
	@Override
	public JSONObject checkMeetingRoom(String data) {
		List<Object> params = new ArrayList<Object>();
		JSONObject result = new JSONObject();
		//解析传过来每个会议室的信息
		JSONObject jsonObj = JSONObject.fromObject(data);
		String applyId = jsonObj.getString("applyId");
		JSONArray jsonArry = jsonObj.getJSONArray("paramArry");
		StringBuilder dates = new StringBuilder();
		StringBuilder meetingRoomIds = new StringBuilder();
		for (Object object : jsonArry) {
			JSONObject tempObj = JSONObject.fromObject(object);
			dates.append(tempObj.getString("useDate") + ",");
			meetingRoomIds.append(tempObj.getString("meetingRoomid") + ",");
		}
		if(dates.length()>0) {
			dates.deleteCharAt(dates.length()-1);
		}
		if(meetingRoomIds.length()>0) {
			meetingRoomIds.deleteCharAt(meetingRoomIds.length()-1);
		}
		//查询除本次申请，别的会议室申请，某些天某些会议室的占用情况
		//拼sql
		StringBuilder sql = new StringBuilder("select t.meetingroom_id meetingroomId,t.use_date useDate,t.use_time useTime  from hygl_meetingroom_useInfo t  ");
		sql.append(" left outer join hygl_meeting_apply t1 on t.apply_id=t1.id where (t1.subflag='1' or t1.subflag='5') ");
		sql.append(" and t.apply_id!= ? ");
		params.add(applyId);
		if(dates.length()>0) {
			sql.append(" and t.use_date in (" + CommonUtils.solveSqlInjectionOfIn(dates.toString(),params) + ") ");
		}
		if(meetingRoomIds.length()>0) {
			sql.append(" and t.meetingroom_id in (" + CommonUtils.solveSqlInjectionOfIn(meetingRoomIds.toString(),params)  + ") ");
		}
		List<MeetingRoomUseInfo> list = jdbcTemplate.query(sql.toString(), new RowMapper<MeetingRoomUseInfo>() {
			@Override
			public MeetingRoomUseInfo mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingRoomUseInfo roomUseInfo = new MeetingRoomUseInfo();
				roomUseInfo.setMeetingroomId(result.getString("meetingroomId"));
				roomUseInfo.setUseTime(result.getString("useTime"));
				roomUseInfo.setUseDate(result.getString("useDate"));
				return roomUseInfo;
			}
		},params.toArray());
		
		//存放每个会议室 每天的会议申请的数据,是一个map,key为会议室id,value也为一个map,map中的key为某一天，value，使用的数据
		Map<String,Map<String,List<MeetingRoomUseInfo>>> meetingRoomUseMap = new HashMap<String,Map<String,List<MeetingRoomUseInfo>>>();
		for (MeetingRoomUseInfo meetingRoomUseInfo : list) {
			Map<String,List<MeetingRoomUseInfo>> tempMap = meetingRoomUseMap.get(meetingRoomUseInfo.getMeetingroomId());
			if(tempMap==null) {
				tempMap = new HashMap<String,List<MeetingRoomUseInfo>>();
				meetingRoomUseMap.put(meetingRoomUseInfo.getMeetingroomId(), tempMap);
			}
			List<MeetingRoomUseInfo> templist = tempMap.get(meetingRoomUseInfo.getUseDate());
			if(templist==null) {
				templist=new ArrayList<MeetingRoomUseInfo>();
				tempMap.put(meetingRoomUseInfo.getUseDate(), templist);
			}
			templist.add(meetingRoomUseInfo);
		}
		//判断每个设置的会议室，在已经处在流程中或流程结束的申请中是否有设置过
		String flg="1";
		int i=0;
	    for (Object object : jsonArry) {
			 i++;
			JSONObject tempObj = JSONObject.fromObject(object);
			//判断是否可设置该会议室，即tempObj是否可保存到数据库；1表示可设置，0表示不可设置
			String flag = hasSet(tempObj,meetingRoomUseMap);
			if("0".equals(flag)) {
				flg="0";
				break;
			}
			
		}
	    result.put("flag", flg);
	    result.put("index", i);
	    return result;
	}

	//判断每个设置的会议室，在已经处在流程中或流程结束的申请中是否有设置过
	private String hasSet(JSONObject tempObj, Map<String, Map<String, List<MeetingRoomUseInfo>>> meetingRoomUseMap) {
		String meetingRoomid = tempObj.getString("meetingRoomid");
		String useDate = tempObj.getString("useDate");
		String useTime = tempObj.getString("useTime");
		String[] usetimeArry = useTime.split("-");
		Map<String, List<MeetingRoomUseInfo>> map = meetingRoomUseMap.get(meetingRoomid);
		String flg="1";
		List<MeetingRoomUseInfo> list = map.get(useDate);
		//有该天该会议室的设置信息
		for (MeetingRoomUseInfo meetingRoomUseInfo : list) {
				String tempuseTime = meetingRoomUseInfo.getUseTime();
				String[] tempuseTimeArry = tempuseTime.split("-");
				Integer newStartTime = Integer.valueOf(usetimeArry[0].split(":")[0]);
				Integer newEndTime = Integer.valueOf(usetimeArry[1].split(":")[0]);
				Integer oldStartTime = Integer.valueOf(tempuseTimeArry[0].split(":")[0]);
				Integer oldEndTime = Integer.valueOf(tempuseTimeArry[1].split(":")[0]);
				if(newStartTime.compareTo(oldStartTime)>0 &&  newStartTime.compareTo(oldEndTime)<0) {
					//新设置的这条会议室的开始时段，在某个会议申请的时间段中间
					flg="0";
					break;
				}else if(newEndTime.compareTo(oldStartTime)>0 &&  newEndTime.compareTo(oldEndTime)<0) {
					//新设置的这条会议室的结束时间段，在某个会议申请的时间段中间
					flg="0";
					break;
				}else if(newStartTime.compareTo(oldStartTime)<=0 && newEndTime.compareTo(oldEndTime)>=0) {
					//新设置的这条会议室的使用时间段，正好包含了时间段内（某个会议室申请的时间段被包含在该条的时间段内）
					
					flg="0";
					break;
				}else if(newStartTime.compareTo(oldStartTime)>=0 && newEndTime.compareTo(oldEndTime)<=0) {
					
					//新设置的这条会议室的使用时间段，正好包含在了时间段内（某个会议室申请的时间段包含了该条的时间段内）
					flg="0";
					break;
				}
					
		}
				
		return flg;
	}
	
	/**
	 * 根据会议室申请获取所有的会议室使用信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月11日 上午9:42:37
	 * @param applyId
	 */
	@Override
	public List<MeetingRoomUseInfo> getByApplyId(String applyId) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select t.id,t.meetingroom_id meetingroomId,t.use_date useDate,t.use_time useTime,t.week_date weekDate,t1.meetingroom_name meetingRoomName,t1.location location  from hygl_meetingroom_useInfo t  ");
		sql.append(" left outer join hygl_meetingroom t1 on t.meetingroom_id=t1.id ");
		sql.append(" where t.apply_id= ? order by t.use_date, t.use_time ");
		param.add(applyId);
		List<MeetingRoomUseInfo> list = jdbcTemplate.query(sql.toString(), new RowMapper<MeetingRoomUseInfo>() {
			@Override
			public MeetingRoomUseInfo mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingRoomUseInfo roomUseInfo = new MeetingRoomUseInfo();
				roomUseInfo.setId(result.getString("id"));
				roomUseInfo.setMeetingroomId(result.getString("meetingroomId"));
				roomUseInfo.setUseTime(result.getString("useTime"));
				roomUseInfo.setUseDate(result.getString("useDate"));
				roomUseInfo.setWeekDate(result.getString("weekDate"));
				roomUseInfo.setMeetingroomName(result.getString("meetingRoomName"));
				roomUseInfo.setLocation(result.getString("location"));
				return roomUseInfo;
			}
			
		},param.toArray());
		return list;
	}

}
