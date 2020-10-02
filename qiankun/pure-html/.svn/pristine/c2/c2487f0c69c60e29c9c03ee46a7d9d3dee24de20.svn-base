package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.system.flowSend.entity.SysFlowSend;
import com.sinosoft.sinoep.modules.system.flowSend.services.SysFlowSendService;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.dao.SeatDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.Seat;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.UserSeat;
import com.sinosoft.sinoep.modules.zhbg.hygl.common.HyglConstants;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.dao.MeetingNoticeDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingNoticeInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.services.MeetingNoticeBackService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 排座service实现
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月12日 下午3:02:02
 */
@Service
public class SeatServiceImp implements SeatService{
	
	@Autowired
	private MeetingNoticeBackService meetingNoticeBackService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SeatDao dao;
	@Autowired
	private MeetingNoticeDao noticeDao;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	private SysWaitNoflowService sysWaitNoflowservice;
	@Autowired
	SysFlowSendService sendService;
	
	/**
	 * 获取某次会议的所有座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月12日 下午3:23:23
	 * @param meetingApplyId
	 * @return
	 */
	@Override
	public List<List<Seat>> getList(String meetingNoticeId,String attendDeptId) {
		Map<Integer,List<Seat>> seatMap =new HashMap<Integer,List<Seat>>();//用于存放人员的座位，key表示哪一行，list表示此行的人员座位
		//获取该会议通知的所有人的座位
		List<Seat> seatList = getSeatsByNoticeId(meetingNoticeId,attendDeptId);
		changeData(seatList,seatMap);
		//获取页面的所有座位 
		List<List<Seat>> list = getAllSeat();
		//把会议通知人的座位放到对应位置
		setPostion(list,seatMap);
		return list;
	}
	
	//把人员座位转成map形式，key表示哪一行，list表示此行的人员座位
	private void changeData(List<Seat> seatList, Map<Integer, List<Seat>> seatMap) {
		for (Seat seat : seatList) {
			int row = seat.getRow();
			if(seatMap.get(row)==null){
				List<Seat> rowData = new ArrayList<Seat>();
				rowData.add(seat);
				seatMap.put(row, rowData);
			}else{
				seatMap.get(row).add(seat);
			}
		}
		
	}

	//把会议通知人的座位放到对应位置
	private void setPostion(List<List<Seat>> list, Map<Integer,List<Seat>> seatMap) {
		int row = 1;
		int col = 1;
		for (List<Seat> tempList : list) {
			List<Seat> rowSeat = seatMap.get(row);
			for (Seat seat : tempList) {
				///设置该座位的状态
				setSeatState(seat,rowSeat);
			}
			row++;
		}
		
	}
	
	///设置该座位的状态
	private void setSeatState(Seat seat, List<Seat> rowSeat) {
		if(rowSeat!=null){
			for (Seat seat2 : rowSeat) {
				if(seat2.getRow()==seat.getRow() && seat2.getCol()==seat.getCol()){
					seat.setState(seat2.getState());
					seat.setAutoArrange(seat2.getAutoArrange());
					seat.setId(seat2.getId());
					seat.setFankuiType(seat2.getFankuiType());
					seat.setOwner(seat2.getOwner());
					seat.setOwnerid(seat2.getOwnerid());
					seat.setAttendDeptId(seat2.getAttendDeptId());
					seat.setAttendDeptName(seat2.getAttendDeptName());
					seat.setSeatNum("");
					//seat.setSeatNum(seat2.getSeatNum());
					break;
				}
			}
		}

	}
	
	///设置该座位的状态
		private void setSeatState1(Seat seat, List<Seat> rowSeat,String userid) {
			if(rowSeat!=null){
				for (Seat seat2 : rowSeat) {
					if(seat2.getRow()==seat.getRow() && seat2.getCol()==seat.getCol()){
						//该座位要么有人、要么被预留
						if(StringUtils.isNotBlank(userid) && seat2.getOwnerid().equals(userid)) {
							//该座位图是个人的座位图
							seat.setState(3);
						}else {
							seat.setState(seat2.getState());
						}
						seat.setAutoArrange(seat2.getAutoArrange());
						seat.setId(seat2.getId());
						seat.setFankuiType(seat2.getFankuiType());
						seat.setOwner(seat2.getOwner());
						seat.setOwnerid(seat2.getOwnerid());
						seat.setAttendDeptId(seat2.getAttendDeptId());
						seat.setAttendDeptName(seat2.getAttendDeptName());
						seat.setSeatNum("");
						//seat.setSeatNum(seat2.getSeatNum());
						break;
					}
				}
			}

		}
	//获取页面的所有座位 
	private List<List<Seat>> getAllSeat() {
		List<List<Seat>> list = new ArrayList<List<Seat>>();
		int rows=14;
		int cols=27;
		for(int i=1;i<=rows;i++){
			List<Seat> rowData = new ArrayList<Seat>();
			for(int j=1;j<=cols;j++){
				Seat seat = new Seat();
				seat.setRow(i);
				seat.setCol(j);
				seat.setState(0);
				rowData.add(seat);
			}
			list.add(rowData);
		}
		return list;
	}
	
	/**
	 * 根据会议通知的id获取座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月13日 下午2:45:53
	 * @param meetingNoticeId
	 * @return
	 */
	public List<Seat> getSeatsByNoticeId(String meetingNoticeId,String attendDeptId){
		StringBuilder sb = new StringBuilder("select t.id,t.seat_num,t.row1,t.col,t.owner,t.ownerid,t.state,t.auto_arrange,t.owner,t.attend_dept,t.attend_dept_id,t.fankui_type from hygl_seat t where t.noticeid='" + meetingNoticeId + "'");
		if(StringUtils.isNotBlank(attendDeptId)) {
			sb.append(" and t.attend_dept_id='" + attendDeptId + "' ");
		}
		List<Seat> list = jdbcTemplate.query(sb.toString(), new RowMapper<Seat>(){
			@Override
			public Seat mapRow(ResultSet result, int arg1) throws SQLException {
				Seat seat = new Seat();
				seat.setId(result.getString("id"));
				seat.setRow(result.getInt("row1"));
				seat.setCol(result.getInt("col"));
				seat.setOwner(result.getString("owner"));
				seat.setOwnerid(result.getString("ownerid"));
				seat.setState(result.getInt("state"));
				seat.setAutoArrange(result.getString("auto_arrange"));
				seat.setAttendDeptId(result.getString("attend_dept_id"));
				seat.setAttendDeptName(result.getString("attend_dept"));
				seat.setFankuiType(result.getString("fankui_type"));
				seat.setSeatNum(result.getString("seat_num"));
				return seat;
			}
			
		});
		return list;
	}
	
	/**
	 * 重置所有座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:34:24
	 * @param meetingNoticeId
	 */
	@Override
	public void deleteByNoticeId(String flag,String meetingNoticeId,String seatListJson) {
		if("0".equals(flag)){
			//重置所有座位
			jdbcTemplate.execute("delete from hygl_seat where noticeId='" + meetingNoticeId + "'");
		}else{
			//重置某些座位
			cancelReserve(meetingNoticeId,seatListJson);
		}
		
		
	}
	
	/**
	 * 保存所有的预留座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午2:29:05
	 * @param seatList
	 */
	@Override
	public void saveAll(String meetingNoticeId,String seatListJson,Integer state) {
		List<Seat> list = new ArrayList<Seat>();//存放所有排座的list
		
		JSONArray jsonArry = JSONArray.fromObject(seatListJson);
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String deptId = UserUtil.getCruDeptId();
		String deptName = UserUtil.getCruDeptName();
		String chuShiId = UserUtil.getCruChushiId();
		String chuShiName = UserUtil.getCruChushiName();
		String juId = UserUtil.getCruJuId();
		String juName = UserUtil.getCruJuName();
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			Seat seat = new Seat();
			if(StringUtils.isNotBlank(jsonObj.getString("id"))){
				seat.setId(jsonObj.getString("id"));
				seat.setOwnerid("");
				seat.setOwner("");
			}
			seat.setCreUserId(userId);
			seat.setCreUserName(userName);
			seat.setPublishState("0");
			seat.setCreDeptId(deptId);
			seat.setCreDeptName(deptName);
			seat.setCreChushiId(chuShiId);
			seat.setCreChushiName(chuShiName);
			seat.setCreJuId(juId);
			seat.setCreJuName(juName);
			
			seat.setNoticeId(meetingNoticeId);
			seat.setRow(jsonObj.getInt("row"));
			seat.setCol(jsonObj.getInt("col"));
			seat.setState(state);
			if(state==4){
				//预留
				seat.setAutoArrange("2");
			}else{
				//占用（手动排座）
				seat.setAutoArrange("1");
			}
			
			list.add(seat);
		}

		dao.save(list);
		
	}
	
	/**
	 * 取消某些座位的预留
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午3:33:33
	 * @param seatList
	 */
	@Override
	public void cancelReserve(String meetingNoticeId,String seatListJson) {
		JSONArray jsonArry = JSONArray.fromObject(seatListJson);
		StringBuilder sb = new StringBuilder("delete from hygl_seat where noticeId = '" + meetingNoticeId + "' and (");
		int i=1;
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if(i==1){
				sb.append("  row1='" + jsonObj.getString("row") + "'  and col='" + jsonObj.getString("col") + "' ");
			}else{
				sb.append("  or row1='" + jsonObj.getString("row") + "'  and col='" + jsonObj.getString("col") + "' ");
			}
			i++;
		}
		sb.append(" )");
		jdbcTemplate.execute(sb.toString());
	}
	
	/**
	 * 自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午5:26:08
	 * @param meetingNoticeId
	 */
	@Override
	public void autoArrangeSeat(String meetingNoticeId,String fankuiType) {
		if("1".equals(fankuiType)){
			//按人数进行反馈的通知进行自动排座
			feedbackByNum(meetingNoticeId,fankuiType);
		}else{
			feedbackByUserName(meetingNoticeId,fankuiType);
		}
		
        
	}
	
	/**
	 * 按人数进行反馈的通知进行自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月15日 下午2:17:54
	 * @param meetingNoticeId
	 */
	private void feedbackByNum(String meetingNoticeId,String fankuiType) {
		//存放业务部门信息
				Set<Map<String,Object>> businessDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
								if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
									if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
										return 1;
									}else{
										return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
									}
									
								}else{
									return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
								}						
							}else{
								return 0;
							}
						}
				});
				//存放综合部门信息
				Set<Map<String,Object>>  synthesizeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
								if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
									if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
										return 1;
									}else{
										return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
									}
									
								}else{
									return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
								}						
							}else{
								return 0;
							}
						}
				});
				//存放未设置部门类型的部门
				Set<Map<String,Object>> noTypeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
								if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
									if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
										return 1;
									}else{
										return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
									}
									
								}else{
									return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
								}						
							}else{
								return 0;
							}
						}
				});
				//存放人员的座位信息，最终保存到数据库中 
				Set<Seat> result = new TreeSet<Seat>(new Comparator<Seat>() {
					@Override
					public int compare(Seat o1, Seat o2) {
						return 1;
					}
					
			   });
		Map<String,Map<String,Object>> deptInfo = new HashMap<String,Map<String,Object>>();//存放的是部门的详细信息,key为部门id,value为该部门的详细信息
		Map<String,Object> seatmap = new HashMap<String,Object>();//存放已设置的座位信息，及已被安排座位的人员id
		Map<String,Integer> juDeptNum = new HashMap<String,Integer>();//存放局的部门人员数，key为部门id，value为部门人员数
		Map<String,Integer> chuDeptNum = new HashMap<String,Integer>();//存放处的部门人员数，key为部门id，value为部门人员数
		//根据会议通知id删除自动排座的那些座位
		String deleteSql = "delete from hygl_seat where state=2 and auto_arrange='0' and noticeId='" + meetingNoticeId + "'";
		jdbcTemplate.execute(deleteSql);
		 StringBuilder judeptid = new StringBuilder();
		 StringBuilder chudeptid = new StringBuilder();
		//获取会议反馈的信息,key为部门id，value为一个map,map中的key为0表示是部门领导，为1表示为处级或科级人员
		Map<String,Map<String,List<MeetingNoticeBack>>> noticeBackInfo = new HashMap<String,Map<String,List<MeetingNoticeBack>>>();
		setBackInfoByNoticeId(meetingNoticeId, judeptid,chudeptid, noticeBackInfo);
		//查询已经手动设置的座位及预存座位,及设置的座位信息，及第一排是否要
		getOWnerIds(meetingNoticeId,seatmap);
		//查询部门的详细信息
		StringBuilder depSql = new StringBuilder();
		if(judeptid.length()!=0 || chudeptid.length()!=0){
			if(judeptid.length()!=0 && chudeptid.length()!=0){
				depSql.append(" select t.deptid,t.deptname,t.unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no from sys_flow_dept t where t.status='1' and t.deptid in (" + CommonUtils.commomSplit(judeptid.substring(0,judeptid.length()-1)) + ") ");
				depSql.append("  union ");
				depSql.append(" select t.deptid,t.deptname,(select t2.unit_type from sys_flow_dept t2 where t2.deptid = t.super_id) unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no  from sys_flow_dept t where t.status = '1' and t.deptid in (" + CommonUtils.commomSplit(chudeptid.substring(0,chudeptid.length()-1)) + ") ");
			}else if(judeptid.length()!=0){
				depSql.append("select t.deptid,t.deptname,t.unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no from sys_flow_dept t where t.status='1' and t.deptid in (" + CommonUtils.commomSplit(judeptid.substring(0,judeptid.length()-1)) + ")");
			}else{
				depSql.append(" select t.deptid,t.deptname,(select t2.unit_type from sys_flow_dept t2 where t2.deptid = t.super_id) unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no  from sys_flow_dept t where t.status = '1' and t.deptid in (" + CommonUtils.commomSplit(chudeptid.substring(0,chudeptid.length()-1)) + ") ");

			}
			JSONObject jsonObj = userInfoService.getDateBySql(depSql.toString());
			List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
			for (Map<String, Object> map : data) {
				if("02".equals(map.get("unit_type").toString())){//业务部门
					//businessDept.add(map);
					if(!businessDept.contains(map)){
						businessDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						businessDept.add(map);
					}
				}else if("01".equals(map.get("unit_type").toString())){//综合部门
					//synthesizeDept.add(map);
					if(!synthesizeDept.contains(map)){
						synthesizeDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						synthesizeDept.add(map);
					}
				}else{
					//部门没有类型
					//.add(map);noTypeDept
					if(!noTypeDept.contains(map)){
						noTypeDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						noTypeDept.add(map);
					}
					
				}
			}
		}
		//把业务部门的反馈信息分到局级和处级的map中，即juDeptNum和chuDeptNum中
		List<String> juDeptid = new ArrayList<String>();
		List<String> commonDeptid = new ArrayList<String>();
		for (Map<String, Object> dept : businessDept) {
			deptInfo.put(dept.get("deptid").toString(), dept);
			 Map<String, List<MeetingNoticeBack>> backInfo = noticeBackInfo.get(dept.get("deptid").toString());
		
			 List<MeetingNoticeBack> juInfo = backInfo.get("0");//反馈的是局级用户几个人参加
			 if(juInfo!=null){
				 juDeptid.add(dept.get("deptid").toString());
				 for (MeetingNoticeBack meetingNoticeBack : juInfo) {
					 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
						//存放的是局部门的人员数
						Integer deptNum = juDeptNum.get(dept.get("deptid").toString());
						if(deptNum==null){
							juDeptNum.put(dept.get("deptid").toString(), personNum);
						}else{
							juDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
						}
				}
			 }
			 
			 List<MeetingNoticeBack> commonInfo = backInfo.get("1");//反馈的是普通用户几个人参加
			 if(commonInfo!=null){
				 commonDeptid.add(dept.get("deptid").toString());
				 for (MeetingNoticeBack meetingNoticeBack : commonInfo) {
					 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
						//存放的是处级和科级的人员数
						Integer deptNum = chuDeptNum.get(dept.get("deptid").toString());
						if(deptNum==null){
							chuDeptNum.put(dept.get("deptid").toString(), personNum);
						}else{
							chuDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
						}
				}
			 }
			
			 
		}
		//把综合部门的反馈信息分到局级和处级的map中，即juDeptNum和chuDeptNum中
			for (Map<String, Object> dept : synthesizeDept) {
				deptInfo.put(dept.get("deptid").toString(), dept);
					 Map<String, List<MeetingNoticeBack>> backInfo = noticeBackInfo.get(dept.get("deptid").toString());
					 List<MeetingNoticeBack> juInfo = backInfo.get("0");//反馈的是局级用户几个人参加
					 if(juInfo!=null){
						 juDeptid.add(dept.get("deptid").toString());
						 for (MeetingNoticeBack meetingNoticeBack : juInfo) {
							 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
								//存放的是局部门的人员数
								Integer deptNum = juDeptNum.get(dept.get("deptid").toString());
								if(deptNum==null){
									juDeptNum.put(dept.get("deptid").toString(), personNum);
								}else{
									juDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
								}
						}
					 }
					 List<MeetingNoticeBack> commonInfo = backInfo.get("1");//反馈的是普通用户几个人参加
					 if(commonInfo!=null){
						 commonDeptid.add(dept.get("deptid").toString());
						 for (MeetingNoticeBack meetingNoticeBack : commonInfo) {
							 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
								//存放的是处级和科级的人员数
								Integer deptNum = chuDeptNum.get(dept.get("deptid").toString());
								if(deptNum==null){
									chuDeptNum.put(dept.get("deptid").toString(), personNum);
								}else{
									chuDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
								}
						}
					 }
			
			}
			//把未设置部门类型的反馈信息分到局级和处级的map中，即juDeptNum和chuDeptNum中
			for (Map<String, Object> dept : noTypeDept) {
				    deptInfo.put(dept.get("deptid").toString(), dept);
					 Map<String, List<MeetingNoticeBack>> backInfo = noticeBackInfo.get(dept.get("deptid").toString());
					 List<MeetingNoticeBack> juInfo = new ArrayList<MeetingNoticeBack>();
					 if(backInfo!=null) {
						 juInfo = backInfo.get("0");//反馈的是局级用户几个人参加
					 }
					 
					 if(juInfo!=null){
						 juDeptid.add(dept.get("deptid").toString());
						 for (MeetingNoticeBack meetingNoticeBack : juInfo) {
							 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
								//存放的是局部门的人员数
								Integer deptNum = juDeptNum.get(dept.get("deptid").toString());
								if(deptNum==null){
									juDeptNum.put(dept.get("deptid").toString(), personNum);
								}else{
									juDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
								}
						}
					 }
					 List<MeetingNoticeBack> commonInfo = new ArrayList<MeetingNoticeBack>();
					 if(backInfo!=null) {
						 commonInfo = backInfo.get("1");//反馈的是普通用户几个人参加
					 }
					
					 if(commonInfo!=null){
						 commonDeptid.add(dept.get("deptid").toString());
						 for (MeetingNoticeBack meetingNoticeBack : commonInfo) {
							 int personNum = Integer.parseInt(meetingNoticeBack.getAttendPersonNum());
								//存放的是处级和科级的人员数
								Integer deptNum = chuDeptNum.get(dept.get("deptid").toString());
								if(deptNum==null){
									chuDeptNum.put(dept.get("deptid").toString(), personNum);
								}else{
									chuDeptNum.put(dept.get("deptid").toString(), deptNum + personNum);
								}
						}
					 }
			}
		//按人数进行自动排座
		autoArrangeSeatByNum(juDeptid,commonDeptid,meetingNoticeId,juDeptNum,chuDeptNum,seatmap,noticeBackInfo,deptInfo,fankuiType);
		
	}
	

	//按人数进行自动排座
	private void autoArrangeSeatByNum(List<String> juDeptid,List<String> commonDeptid,String meetingNoticeId,Map<String, Integer> juDeptNum, Map<String, Integer> commonDeptNum,
			Map<String, Object> seatmap,Map<String,Map<String,List<MeetingNoticeBack>>> noticeBackInfo,Map<String,Map<String,Object>> deptInfo,String fankuiType) {
		List<Seat> result = new ArrayList<Seat>();
		//List<String> juDeptid = new ArrayList<String>();//存放局级的部门id
		//List<String> commonDeptid = new ArrayList<String>();//存放处级的部门id
		Map<Integer,List<Seat>> map = new HashMap<Integer,List<Seat>>();//存放每一排的座位,key为第几排，value为此排的座位
		Set<String> ownerids = (Set<String>)seatmap.get("ownerids");//已经排座的人员id
		List<Seat> seats = (List<Seat>)seatmap.get("seats");//已经被占用的座位信息
		changeSeatData(seats,map);
		int rowNum = CommonConstants.HYGL_MEETINGROM_ROWNUM;
		int colNum = CommonConstants.HYGL_MEETINGROM_COLNUM;
		
		//先排部门领导
		String isGetSeat="1";//是否要重新获取此排的座位，1表示要重新获取，0表示不需要
		Map<Integer,Seat> tempmap = new HashMap<Integer,Seat>();//存放此排的每一个的座位,
		JSONObject jsonObj = new JSONObject();//用于存放第一排是否要安排座位
		int rowflag=1;//用于记录排到第几行
		int colflag=11;//用于记录排到第几列
		//先排部门领导（中间位置）
	
	    Set<String> deptids = juDeptNum.keySet();//获取局级部门id
	    //juDeptid.addAll(deptids);
	    Integer personNum=0;
	    String judeptid ="";
	    String op1="0";
	    int seatNum=1;
	    while(juDeptid.size()>0){//排完一个部门在排下个部门
	    	//处级/科级部门人员未被排完
	    	if(personNum<=0){
	    		//该部门的人未排完
	    		judeptid = juDeptid.remove(0);
	    		seatNum=1;
	    		 personNum = juDeptNum.get(judeptid);
	    	}
	    	while(personNum>0){
	    		if("1".equals(isGetSeat)){
		    		List<Seat> seatsList = map.get(rowflag);
					changColSeat(seatsList,tempmap,rowflag,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
					isGetSeat="0";
		    	}
              if("1".equals(jsonObj.getString("flag")) && rowflag==1 && colflag<=11 && colflag>=1){
					//第一排中间位置不可进行排座
            	    rowflag++;
					isGetSeat="1";
					colflag=11;
					op1="0";
					continue;//第一排中间不能排座，就排下一行，重新获取下一行已经排好的座位
				}
	    		
	    		Seat seat = tempmap.get(colflag);
	    		if(seat==null){
	    			//该座位未被占用
	    			Seat tempseat = new Seat();
					tempseat.setAttendDeptId(judeptid);
					tempseat.setAttendDeptName(deptInfo.get(judeptid).get("deptname").toString());
					tempseat.setAutoArrange("0");
					tempseat.setRow(rowflag);
					//tempseat.setSeatNum(seatNum+"座");
					tempseat.setCol(colflag);
					tempseat.setState(2);
					tempseat.setPublishState("0");
					tempseat.setFankuiType(fankuiType);
					tempseat.setNoticeId(meetingNoticeId);
					tempseat.setFankuiType("0");
					
					tempseat.setCreUserId(UserUtil.getCruUserId());
					tempseat.setCreUserName(UserUtil.getCruUserName());
					tempseat.setCreChushiId(UserUtil.getCruChushiId());
					tempseat.setCreChushiName(UserUtil.getCruChushiName());
					tempseat.setCreDeptId(UserUtil.getCruDeptId());
					tempseat.setCreDeptName(UserUtil.getCruDeptName());
					tempseat.setCreJuId(UserUtil.getCruJuId());
					result.add(tempseat);
					seatNum++;
					personNum--;
	    		}
	    		
	    			//从左到右S形排列
	    			if("0".equals(op1)){
						colflag=colflag-2;
						if(colflag==-1){//从左往右排，左边排完了
							colflag=2;
							op1="1";
						}else if(colflag==0){//从右往左排，排到了中间位置
							colflag=1;
							op1="1";
						}
						
					}else{
						colflag=colflag+2;
						if(colflag==12){////从左往右排，右边排完了
							rowflag++;
							isGetSeat="1";
							colflag=10;
							op1="0";
							continue;
						}else if(colflag==13){//从右往左排，左边排完了
							rowflag++;
							isGetSeat="1";
							colflag=11;
							op1="0";
							continue;
						}
					}
	    		
	    	}
	    	
	    }
	   
	   /* while(juDeptid.size()>0){
	    	
	    	//还有人员未被排完
	    	if(personNum<=0){
	    		judeptid = juDeptid.remove(0);
		    	 personNum =juDeptNum.get(judeptid);
	    	}
	    	while(personNum>0){
	    		if("1".equals(isGetSeat)){
		    		List<Seat> tempseats = map.get(rowflag);
					changColSeat(tempseats,tempmap,rowflag,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
					isGetSeat="0";
		    	}
	    		if("1".equals(jsonObj.getString("flag")) && rowflag==1){
					//第一排不可进行排座
	    			rowflag++;
	    			isGetSeat="1";
					continue;
				}
	    		Seat seat = tempmap.get(colflag);
	    		if(seat==null){//该座位未被占用
	    			Seat tempseat = new Seat();
		    		tempseat.setAttendDeptId(judeptid);
					tempseat.setAttendDeptName(deptInfo.get(judeptid).get("deptname").toString());
					tempseat.setAutoArrange("0");
					tempseat.setFankuiType(fankuiType);
					tempseat.setRow(rowflag);
					tempseat.setCol(colflag);
					tempseat.setState(2);
					tempseat.setPublishState("0");
					tempseat.setNoticeId(meetingNoticeId);
					tempseat.setFankuiType("0");
					
					tempseat.setCreUserId(UserUtil.getCruUserId());
					tempseat.setCreUserName(UserUtil.getCruUserName());
					tempseat.setCreChushiId(UserUtil.getCruChushiId());
					tempseat.setCreChushiName(UserUtil.getCruChushiName());
					tempseat.setCreDeptId(UserUtil.getCruDeptId());
					tempseat.setCreDeptName(UserUtil.getCruDeptName());
					tempseat.setCreJuId(UserUtil.getCruJuId());
					result.add(tempseat);
					colflag++;
					
		    		personNum--;
		    		if(colflag>11){
		    			rowflag++;
		    			colflag=1;
		    			isGetSeat="1";
		    			continue;
		    		}
	    		}else{
	    			colflag++;
	    			if(colflag>11){
		    			rowflag++;
		    			colflag=1;
		    			isGetSeat="1";
		    			continue;
		    		}
	    		}
	    	
	    	}
	    }*/
	    

		//排普通人员
	    isGetSeat="1";
		String from="0";//默认是从左边往右排
		int rowflag1=1;//用于记录排到第几行
		int colflag1=27;//用于记录排到第几列
		List<Seat> tempseats = map.get(rowflag1);//获取此排被占用的座位
		changColSeat(tempseats,tempmap,rowflag1,jsonObj,"1");//把此排的座位转成map形式，
	    Set<String> deptids1 = commonDeptNum.keySet();//获取局级部门id
	    //commonDeptid.addAll(deptids1);
	    String op = "0";//0表示座位号是减2;1表示座位号加2
	    String deptid ="";
	    Integer personNum1=0;
	    Map<Integer,Seat> hasArrangeSeat = new HashMap<Integer,Seat>();
	    while(commonDeptid.size()>0){//排完一个部门在排下个部门
	    	//处级/科级部门人员未被排完
	    	if(personNum1<=0){
	    		//该部门的人未排完
	    		 deptid = commonDeptid.remove(0);
	    		 seatNum=1;
	    		 personNum1 = commonDeptNum.get(deptid);
	    	}
	    	while(personNum1>0){
	    		if("1".equals(isGetSeat)){
		    		List<Seat> seatsList = map.get(rowflag1);
					changColSeat(seatsList,tempmap,rowflag1,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
					isGetSeat="0";
					if(rowflag==rowflag1){
						//获取该行已经排座的座位,转成map格式,key为该行的纵坐标，value为座位
						getRowSeate(hasArrangeSeat,result,rowflag1);
						
					}else{
						hasArrangeSeat.clear();
					}
		    	}
	    		
	    		Seat seat = tempmap.get(colflag1);
	    		if(seat==null){
	    			//该座位未被占用
	    			Seat tempseat = new Seat();
					tempseat.setAttendDeptId(deptid);
					tempseat.setAttendDeptName(deptInfo.get(deptid).get("deptname").toString());
					tempseat.setAutoArrange("0");
					tempseat.setRow(rowflag1);
					tempseat.setCol(colflag1);
					tempseat.setState(2);
					tempseat.setPublishState("0");
					tempseat.setFankuiType(fankuiType);
					tempseat.setNoticeId(meetingNoticeId);
					tempseat.setFankuiType("0");
					//tempseat.setSeatNum(seatNum+"座");
					tempseat.setCreUserId(UserUtil.getCruUserId());
					tempseat.setCreUserName(UserUtil.getCruUserName());
					tempseat.setCreChushiId(UserUtil.getCruChushiId());
					tempseat.setCreChushiName(UserUtil.getCruChushiName());
					tempseat.setCreDeptId(UserUtil.getCruDeptId());
					tempseat.setCreDeptName(UserUtil.getCruDeptName());
					tempseat.setCreJuId(UserUtil.getCruJuId());
					if(hasArrangeSeat.get(colflag1)==null){
						result.add(tempseat);
						seatNum++;
						personNum1--;
					}else{
						System.out.println("dddjdkdjkjd");
					}
					
	    		}
	    		
	    		if(rowflag>rowflag1){
					//此排的中间位置已排满，排两边即可 
	    			if("0".equals(op)){
	    				colflag1=colflag1-2;
	    				if(colflag1==11){//从左往右排，左边排完
	    					colflag1=12;
	    					op="1";
	    				}else if(colflag1==10){//从右往左中间位置排完 
	    					colflag1=13;
	    					op="1";
	    				}
	    			}else{
	    				colflag1=colflag1+2;
	    				if(colflag1==28){//从左往右排，右边排完了
	    					rowflag1++;
	    					if("0".equals(from)){
	    						from="1";
	    					}else{
	    						from="0";
	    					}
	    					
							isGetSeat="1";
							colflag1=26;
							op="0";
							continue;
	    				}else if(colflag1==29){//从右往左排，左边排完，排下行
	    					rowflag1++;
							isGetSeat="1";
							if("0".equals(from)){
	    						from="1";
	    					}else{
	    						from="0";
	    					}
							colflag1=27;
							op="0";
							continue;
	    				}
	    			}
	    			
	    		}/*else if((rowflag==rowflag1 && deptids.size()>0)){
					//中间位置排了几个
	    			if("0".equals(op)){
	    				colflag1=colflag1-2;
	    				if(colflag1<colflag && "1".equals(from)){//从右往左排
	    					if(colflag%2==0){
	    						colflag1=colflag-1;
	    					}else{
	    						colflag1=colflag;
	    					}
	    					op="1";
	    				}else if(colflag1<colflag && "0".equals(from)){//从左往右排
	    					if(colflag%2!=0){
	    						colflag1=colflag;
	    					}else{
	    						colflag1=colflag1+1;
	    					}
	    					op="1";
	    				}
	    			}else{
	    				colflag1=colflag1+2;
	    				if(colflag1==29){//从右往左排，左边排完
	    					from="0";
	    					rowflag1++;
							isGetSeat="1";
							colflag1=27;
							op="0";
							continue;
	    				}else if(colflag1==28){
	    					from="1";
	    					rowflag1++;
							isGetSeat="1";
							colflag1=26;
							op="0";
							continue;
	    				}
	    			}
	    		}*/else{
	    			//从左到右S形排列
	    			if("0".equals(op)){
						colflag1=colflag1-2;
						if(colflag1==-1){//从左往右排，左边排完了
							colflag1=2;
							op="1";
						}else if(colflag1==0){//从右往左排，排到了中间位置
							colflag1=1;
							op="1";
						}
						
					}else{
						colflag1=colflag1+2;
						if(colflag1==28){////从左往右排，右边排完了
							rowflag1++;
							isGetSeat="1";
							colflag1=26;
							op="0";
							continue;
						}else if(colflag1==29){//从右往左排，左边排完了
							rowflag1++;
							isGetSeat="1";
							colflag1=27;
							op="0";
							continue;
						}
					}
	    		}
				
	    		if("1".equals(jsonObj.getString("flag")) && rowflag1==1 && colflag1<=11 && colflag1>=1){
	    			
					//第一排中间位置不可进行排座
	    			if("0".equals(op)){
	    				colflag1=12;
	    				op="1";
	    			}else{
	    				colflag1=13;
	    				op="0";
	    			}
				}
				
				
	    	}
	    	
	    }
	    
	    dao.save(result);
		
	}
	//把某行的座位转成map形式
	private void getRowSeate(Map<Integer, Seat> hasArrangeSeat, List<Seat> result, int rowflag1) {
		hasArrangeSeat.clear();
		for (Seat seat : result) {
			if(seat.getRow()==rowflag1){
				hasArrangeSeat.put(seat.getCol(), seat);
			}
		}
		
	}

	/**
	 * 根据会议通知的id设置每个部门的反馈信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月15日 下午7:57:42
	 * @param meetingNoticeId
	 * @param judeptid
	 * @param noticeBackInfo
	 */
	private void setBackInfoByNoticeId(String meetingNoticeId, final StringBuilder judeptid,final StringBuilder chudeptid,
			Map<String, Map<String, List<MeetingNoticeBack>>> noticeBackInfo) {
		String noticeBackSql = " select t.attend_person_num,t.attend_dept_id,t.attend_dept_type,t.attend_dept from  hygl_notice_back t where t.visible='1' and t.STATE='1' and t.notice_id='" + meetingNoticeId + "'  ";
		List<MeetingNoticeBack> noticeBackList = jdbcTemplate.query(noticeBackSql, new RowMapper<MeetingNoticeBack>(){
			@Override
			public MeetingNoticeBack mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingNoticeBack noticeBack = new MeetingNoticeBack();
				noticeBack.setAttendPersonNum(result.getString("attend_person_num"));
				noticeBack.setAttendDeptId(result.getString("attend_dept_id"));
				if("0".equals(result.getString("attend_dept_type"))){
					judeptid.append(result.getString("attend_dept_id")+",");
				}else{
					chudeptid.append(result.getString("attend_dept_id")+",");
				}
				noticeBack.setAttendDept(result.getString("attend_dept"));
				noticeBack.setAttendDeptType(result.getString("attend_dept_type"));
				return noticeBack;
			}
			
		});
		for (MeetingNoticeBack meetingNoticeBack : noticeBackList) {
			Map<String,List<MeetingNoticeBack>> tempMap = noticeBackInfo.get(meetingNoticeBack.getAttendDeptId());
			if(tempMap==null){
				tempMap = new HashMap<String,List<MeetingNoticeBack>>();
				noticeBackInfo.put(meetingNoticeBack.getAttendDeptId(), tempMap);
			}
			if("0".equals(meetingNoticeBack.getAttendDeptType())){
				//反馈的是局级部门的人数
				List<MeetingNoticeBack> list = tempMap.get("0");
				if(list==null){
						List<MeetingNoticeBack> backlist = new ArrayList<MeetingNoticeBack>();
						backlist.add(meetingNoticeBack);
						tempMap.put("0", backlist);
				}else{
					tempMap.get("0").add(meetingNoticeBack);
				}
			}else{
				//反馈的是处级部门的人数
				List<MeetingNoticeBack> list = tempMap.get("1");
				if(list==null){
						List<MeetingNoticeBack> backlist = new ArrayList<MeetingNoticeBack>();
						backlist.add(meetingNoticeBack);
						tempMap.put("1", backlist);
				}else{
					tempMap.get("1").add(meetingNoticeBack);
				}
			}
			
		}
	}

	/**
	 * 按人名进行反馈的通知进行自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月15日 下午2:15:38
	 * @param meetingNoticeId
	 */
	private void feedbackByUserName(String meetingNoticeId,String fankuiType) {
		//根据会议通知id删除自动排座的那些座位
		String deleteSql = "delete from hygl_seat where state=2 and auto_arrange='0' and noticeId='" + meetingNoticeId + "'";
		jdbcTemplate.execute(deleteSql);
		StringBuilder judeptids = new StringBuilder();//存放所有局参会部门id
		StringBuilder chudeptids = new StringBuilder();//存放所有处参会部门id
		//List<String> attendeePersionId = new ArrayList<String>();//存放所有的参会人员id
		List<String> juPersonId = new ArrayList<String>();//存放即部门领导id
		List<String> chuPersonId = new ArrayList<String>();//存放处长id
		List<String> commonPersonId = new ArrayList<String>();//存放第三部门的人员id(即处员、科员id)
		//存放每个参会部门的参会人员,key为参会部门id,value为一个map,map中key为flag当为0时表示存放的是部门领导；flag为1时表示存放的是处长；flag为3时存放的是第三部分人员（处员、科员、副处长）等
		Map<String,Map<String,List<String>>> persionMap = new HashMap<String,Map<String,List<String>>>();
		Map<String,String> deptMap = new HashMap<String,String>();//key为参会的部门id,value为参会的部门名称
		//JSONObject jsonObj = new JSONObject();//存放已设置的座位信息，及已被安排座位的人员id
		Map<String,Object> seatmap = new HashMap<String,Object>();//存放已设置的座位信息，及已被安排座位的人员id
		//存放业务部门信息
		Set<Map<String,Object>> businessDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
						if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
							if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
								return 1;
							}else{
								return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
							}
							
						}else{
							return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
						}						
					}else{
						return 0;
					}
					
				}
		});
		//存放综合部门信息
		Set<Map<String,Object>>  synthesizeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
						if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
							if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
								return 1;
							}else{
								return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
							}
							
						}else{
							return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
						}						
					}else{
						return 0;
					}
				}
		});
		//存放未设置部门类型的部门
		Set<Map<String,Object>> noTypeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
						if(Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()))==0){
							if(Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()))==0){
								return 1;
							}else{
								return Integer.valueOf(o1.get("order_no").toString()).compareTo(Integer.valueOf(o2.get("order_no").toString()));
							}
							
						}else{
							return Integer.valueOf(o1.get("superorderno").toString()).compareTo(Integer.valueOf(o2.get("superorderno").toString()));
						}						
					}else{
						return 0;
					}
				}
		});
		//存放人员的座位信息，最终保存到数据库中 
		Set<Seat> result = new TreeSet<Seat>(new Comparator<Seat>() {
			@Override
			public int compare(Seat o1, Seat o2) {
				return o1.getOwnerid().compareTo(o2.getOwnerid());
			}
			
	   });
		//根据会议通知id所有的参会人及参会部门
		List<MeetingNoticeBack> list = attendeePersion(meetingNoticeId);
		//查询已经手动设置的座位及预存座位,及设置的座位信息，及第一排是否要
		getOWnerIds(meetingNoticeId,seatmap);
		//1.查询所有的参会部门信息，是业务部门还是综合部门,放入到businessDept、synthesizeDept、noTypeDept；2.并把每个参会部门人员进行分类放入不同的map中，即放到persionMap；
		getDeptPersion(judeptids, chudeptids,persionMap, businessDept, synthesizeDept, noTypeDept, list);
		//把业务部门的人员进行分类，分到第二类和第三类
		for (Map<String,Object> map : businessDept) {
			deptMap.put(map.get("deptid").toString(), map.get("deptname").toString());
			Map<String,List<String>> personMap = persionMap.get(map.get("deptid").toString());
			if(personMap.get("0")!=null){
				juPersonId.addAll(personMap.get("0"));
			}
			if(personMap.get("1")!=null){
				chuPersonId.addAll(personMap.get("1"));			
		    }
			if(personMap.get("2")!=null){
				 commonPersonId.addAll(personMap.get("2"));
			}
			
		}
		//给综合部门的人员进行分类，分到第二类和第三类
        for (Map<String,Object> map : synthesizeDept) {
        	deptMap.put(map.get("deptid").toString(), map.get("deptname").toString());
        	Map<String,List<String>> personMap = persionMap.get(map.get("deptid").toString());
        	if(personMap.get("0")!=null){
				juPersonId.addAll(personMap.get("0"));
			}
        	if(personMap.get("1")!=null){
        		chuPersonId.addAll(personMap.get("1"));
        	}
        	if(personMap.get("2")!=null){
        		commonPersonId.addAll(personMap.get("2"));
        	}
			 
			 
		}
		//给未设置部门类型的人员进行进行分类，分到第二类和第三类
        for (Map<String,Object> map : noTypeDept) {
        	deptMap.put(map.get("deptid").toString(), map.get("deptname").toString());
        	Map<String,List<String>> personMap = persionMap.get(map.get("deptid").toString());
        	if(personMap.get("0")!=null){
				juPersonId.addAll(personMap.get("0"));
			}
        	if(personMap.get("1")!=null){
        		chuPersonId.addAll(personMap.get("1"));
        	}
        	if(personMap.get("2")!=null){
        		commonPersonId.addAll(personMap.get("2"));
        	}
		}
        //进行自动排座
        autoArrangeSeat(meetingNoticeId,juPersonId,chuPersonId,commonPersonId,result,seatmap,persionMap,deptMap,fankuiType);
	}
	
	/**
	 * 根据已经排座的座位信息及人员信息进行自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月11日 上午10:23:55
	 * @param juPersonId
	 * @param commonPersonId
	 * @param result
	 * @param jsonObj
	 */
	private void autoArrangeSeat(String meetingNoticeId,List<String> juPersonId, List<String> chuPersonId,List<String> commonPersonId, Set<Seat> result,
			Map<String,Object>  seatMap,Map<String,Map<String,List<String>>> persionMap,Map<String,String> deptMap,String fankuiType) {
		JSONObject jsonObj = new JSONObject();//用于存放第一排是否要安排座位
		List<String> personIdList = new ArrayList<String>();//存放要排座人员的id
		//List<String> juPersonIdList = new ArrayList<String>();//存放部门领导的id
		//List<String> chuPersonIdList = new ArrayList<String>();//存放处长的id
		//List<String> commonPersonIdList = new ArrayList<String>();//存放第三部分人员id,即（副处长、处员、科长）的id
		Map<Integer,List<Seat>> map = new HashMap<Integer,List<Seat>>();//存放每一排的座位,key为第几排，value为此排的座位
		Set<String> ownerids = (Set<String>)seatMap.get("ownerids");//已经排座的人员id
		List<Seat> seats = (List<Seat>)seatMap.get("seats");//已经被占用的座位信息
		changeSeatData(seats,map);
		chuPersonId.removeAll(ownerids);
		juPersonId.removeAll(ownerids);
		int juPersonSize = juPersonId.size();
		commonPersonId.removeAll(ownerids);//排除已经排座的人员
		//chuPersonIdList.addAll(chuPersonId);
		//juPersonIdList.addAll(juPersonId);
		//commonPersonId.removeAll(juPersonId);
		commonPersonId.removeAll(chuPersonId);//避免一人岗时（即是处长，有是普通人员），会出现一个人两个位置的情况
		//commonPersonIdList.addAll(commonPersonId);
		juPersonId.addAll(chuPersonId);
		personIdList.addAll(juPersonId);
		personIdList.addAll(chuPersonId);
		personIdList.addAll(commonPersonId);
		//查询要排座人员的详细信息
		String persionid = StringUtils.join(personIdList, ",");
		Map<String, SysFlowUserVo> userInfo = UserUtil.getUserVo(persionid);
		int rowNum = CommonConstants.HYGL_MEETINGROM_ROWNUM;
		int colNum = CommonConstants.HYGL_MEETINGROM_COLNUM;
		
		int rowflag=0;//用于记录排到第几行
		int colflag=1;//用于记录排到第几列
		int colIndex=1;
		String from1="0";
		//给部门领导排座
		String isGetSeat="1";//是否要重新获取此排的座位，1表示要重新获取，0表示不需要
		Map<Integer,Seat> tempmap = new HashMap<Integer,Seat>();//存放此排的每一个的座位,
		for(int i=1;i<=rowNum;i++){
			if(juPersonId.size()<=0){
				//部门领导排完了
				break;
			}
			if("1".equals(isGetSeat)){
				List<Seat> tempseats = map.get(i);
				changColSeat(tempseats,tempmap,i,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
			}
			if("1".equals(jsonObj.getString("flag")) && i==1){
				//第一排不可进行排座
				continue;
			}
			if("0".equals(from1)){
				rowflag=i;
				juPersonSize=leftArrangeToRightLeader(juPersonSize,meetingNoticeId, juPersonId, result, persionMap, deptMap, userInfo, tempmap, i);
				from1="1";
			}else{
				rowflag=i;
				juPersonSize=rightArrangeToLeftLeader(juPersonSize,meetingNoticeId, juPersonId, result, persionMap, deptMap, userInfo, tempmap,i);
				
				from1="0";
			}
		}
/*		for(int i=1;i<=rowNum;i++){
			if(juPersonId.size()<=0){
				//部门领导排完了
				break;
			}
			if(colIndex>11){
				colIndex=1;
				continue;
			}
			if("1".equals(isGetSeat)){
				List<Seat> tempseats = map.get(i);
				changColSeat(tempseats,tempmap,i,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
			}
			
			if("1".equals(jsonObj.getString("flag")) && i==1){
				//第一排不可进行排座
				continue;
			}
			Seat seat = tempmap.get(colIndex);
			if(seat==null){
				//该座位未被占用，在此座位上进行排座
				String persionId = juPersonId.remove(0);
				Seat tempseat = new Seat();
				SysFlowUserVo userVo = userInfo.get(persionId);
				tempseat.setOwner(userVo.getUserNameFull());
				tempseat.setAttendDeptId(userVo.getUserDeptId());
				tempseat.setAttendDeptName(userVo.getUserDeptName());
				tempseat.setAutoArrange("0");
				tempseat.setPublishState("0");
				tempseat.setOwnerid(persionId);
				tempseat.setRow(i);
				tempseat.setFankuiType(fankuiType);
				tempseat.setCol(colIndex);
				tempseat.setState(2);
				tempseat.setNoticeId(meetingNoticeId);
				tempseat.setFankuiType("0");
				//设置座位所属的参会部门
				setAttendDept(tempseat,persionId,persionMap,"0",deptMap);
				
				tempseat.setCreUserId(UserUtil.getCruUserId());
				tempseat.setCreUserName(UserUtil.getCruUserName());
				tempseat.setCreChushiId(UserUtil.getCruChushiId());
				tempseat.setCreChushiName(UserUtil.getCruChushiName());
				tempseat.setCreDeptId(UserUtil.getCruDeptId());
				tempseat.setCreDeptName(UserUtil.getCruDeptName());
				tempseat.setCreJuId(UserUtil.getCruJuId());
				
				result.add(tempseat);
				//用于记录下个位置要从哪开始排
				if(colIndex==12){
					rowflag=i+1;
					colflag=1;
				}else{
					rowflag=i;
					colflag=colIndex+1;
				}
				colIndex++;
			}else{
				colIndex++;
			}
			
			if(colIndex<12){
				i--;
				isGetSeat="0";//不需要重新获取此排座位
			}else{
				colIndex=1;
				isGetSeat="1";//需要重新获取此排座位
			}
		}*/
		//给处长进行排座
		isGetSeat="1";
/*		for(int i=rowflag;i<=rowNum;i++){
			if("1".equals(isGetSeat)){
				List<Seat> tempseats = map.get(i);
				changColSeat(tempseats,tempmap,i,jsonObj,"1");//把此排的座位转成map形式， 并判断第一行是否要安排座位
			}
			if(chuPersonId.size()==0){
				//处长座位已排完
				break;
			}else{
				Seat seat = tempmap.get(colIndex);
				if(seat==null){
					//该座位没有预存或占用情况，就进行排座
					Seat tempseat = new Seat();
					tempseat.setAutoArrange("0");
					String persionId = chuPersonId.remove(0);
					tempseat.setOwnerid(persionId);
					SysFlowUserVo userVo = userInfo.get(persionId);
					tempseat.setOwner(userVo.getUserNameFull());
					tempseat.setPublishState("0");
					tempseat.setFankuiType(fankuiType);
					tempseat.setAttendDeptId(userVo.getUserDeptId());
					tempseat.setAttendDeptName(userVo.getUserDeptName());
					tempseat.setRow(i);
					tempseat.setCol(colIndex);
					tempseat.setState(2);
					tempseat.setNoticeId(meetingNoticeId);
					tempseat.setFankuiType("0");
					//设置座位所属的参会部门
					setAttendDept(tempseat,persionId,persionMap,"1",deptMap);
					
					tempseat.setCreUserId(UserUtil.getCruUserId());
					tempseat.setCreUserName(UserUtil.getCruUserName());
					tempseat.setCreChushiId(UserUtil.getCruChushiId());
					tempseat.setCreChushiName(UserUtil.getCruChushiName());
					tempseat.setCreDeptId(UserUtil.getCruDeptId());
					tempseat.setCreDeptName(UserUtil.getCruDeptName());
					tempseat.setCreJuId(UserUtil.getCruJuId());
					result.add(tempseat);
					//用于记录下个位置要从哪开始排
					if(colIndex==12){
						rowflag=i+1;
						colflag=1;
					}else{
						rowflag=i;
						colflag=colIndex+1;
					}
					colIndex++;
				}else{
					colIndex++;
				}
			
			}
			if(colIndex<12){
				i--;
				isGetSeat="0";//不需要重新获取此排座位
			}else{
				colIndex=1;
				isGetSeat="1";//需要重新获取此排座位
			}
		}*/
		
		//给普通人员（副处长、处员、科员）排座
		int colindex=1;
		String from="0";//默认是从左边往右排
		String flag="0";//表示第二部分人那一行安排了第三部分人员
		for(int i=1;i<=rowNum;i++){
			List<Seat> tempseats = map.get(i);
			changColSeat(tempseats,tempmap,i,jsonObj,"1");
			if(commonPersonId.size()==0){
				//普通人员位置已排完
				break;
			}
		
			if(rowflag>i){
				//此排的中间位置已排满，排两边即可
				if("0".equals(from)){
					//从左边往右排
					leftArrangeToRight1(meetingNoticeId, commonPersonId, result, persionMap, deptMap, userInfo, tempmap, i);
					from="1";
				}else{
					//从右往左排
					rightArrangeToLeft1(meetingNoticeId, commonPersonId, result, persionMap, deptMap, userInfo, tempmap,i);
					from="0";
					
				}
				
				
			}else if(rowflag==i){
				//从左中右开始排
				if("0".equals(from)){
					leftArrangeToRight2(meetingNoticeId, commonPersonId, result, persionMap, deptMap, userInfo, colIndex,tempmap, i);
					from="1";
				}else{
					//从右往左边开始排
					rightArrangeToLeft2(meetingNoticeId, commonPersonId, result, persionMap, deptMap, userInfo,colIndex, tempmap, i);
					from="0";
				}
				
			}else{
				//中间两边位置都可进行排座,
				if("0".equals(from)){
					leftArrangeToRight3(meetingNoticeId,jsonObj, commonPersonId, result, persionMap, deptMap, userInfo, tempmap, i);
					from="1";
				}else{
					rightArrangeToLeft3(meetingNoticeId,jsonObj, commonPersonId, result, persionMap, deptMap, userInfo, tempmap,i);
					from="0";
				}
				
			}
		}

		
		dao.save(result);
	}

	private int rightArrangeToLeftLeader(int juPersonSize,String meetingNoticeId, List<String> juPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		//从右边到中间开始排
				for(int j=10;j>=2;j=j-2){
					if(juPersonId.size()>0){
						Seat ownerseat = tempmap.get(j);
						if(ownerseat==null){
							//该座位未被占用
							Seat seat = new Seat();
							seat.setAutoArrange("0");
							seat.setState(2);
							seat.setRow(i);
							seat.setCol(j);
							seat.setFankuiType("0");
							seat.setPublishState("0");
							String persionId = juPersonId.remove(0);
							seat.setOwnerid(persionId);
							//设置座位所属的参会部门
							if(juPersonSize>0){
								setAttendDept(seat,persionId,persionMap,"0",deptMap);
							}else{
								setAttendDept(seat,persionId,persionMap,"1",deptMap);
							}
							juPersonSize--;
							SysFlowUserVo userVo = userInfo.get(persionId);
							seat.setOwner(userVo.getUserNameFull());
							/*seat.setAttendDeptId(userVo.getUserDeptId());
							seat.setAttendDeptName(userVo.getUserDeptName());*/
							seat.setNoticeId(meetingNoticeId);
							if(!result.contains(seat)){
								result.add(seat);
							}else{
								j=j+2;
							}
						}else{
							//该座位被占用
							continue;
						}
					}else{
						//普通人员已被排完
						 break;
					}
				}
				//从中间往左边开始排
				for(int j=1;j<=11;j=j+2){
					if(juPersonId.size()>0){
						//普通人员未被排完
						Seat ownerseat = tempmap.get(j);
						if(ownerseat==null){
							//该座位未被占用
							Seat seat = new Seat();
							seat.setAutoArrange("0");
							seat.setState(2);
							seat.setRow(i);
							seat.setCol(j);
							seat.setPublishState("0");
							seat.setFankuiType("0");
							String persionId = juPersonId.remove(0);
							seat.setOwnerid(persionId);
							//设置座位所属的参会部门
							if(juPersonSize>0){
								setAttendDept(seat,persionId,persionMap,"0",deptMap);
							}else{
								setAttendDept(seat,persionId,persionMap,"1",deptMap);
							}
							juPersonSize--;
							SysFlowUserVo userVo = userInfo.get(persionId);
							seat.setOwner(userVo.getUserNameFull());
							/*seat.setAttendDeptId(userVo.getUserDeptId());
							seat.setAttendDeptName(userVo.getUserDeptName());*/
							seat.setNoticeId(meetingNoticeId);
							if(!result.contains(seat)){
								result.add(seat);
							}else{
								j=j-2;
							}
						}else{
							//该座位被占用
							continue;
						}
					}else{
						//普通人员已被排完
						 break;
					}
				}
				return juPersonSize;
		
	}

	private int leftArrangeToRightLeader(int juPersonSize,String meetingNoticeId, List<String> juPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		for(int j=11;j>=1;j=j-2){
			if(juPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					String persionId = juPersonId.remove(0);
					if(!StringUtils.isNotBlank(persionId)) {
						continue;
					}
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setPublishState("0");
					seat.setFankuiType("0");
					
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					if(juPersonSize>0){
						setAttendDept(seat,persionId,persionMap,"0",deptMap);
					}else{
						setAttendDept(seat,persionId,persionMap,"1",deptMap);
					}
					juPersonSize--;
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j+2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
		//从中间到右边开始排
		for(int j=2;j<=10;j=j+2){
			if(juPersonId.size()>0){
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = juPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					if(juPersonSize>0){
						setAttendDept(seat,persionId,persionMap,"0",deptMap);
					}else{
						setAttendDept(seat,persionId,persionMap,"1",deptMap);
					}
					juPersonSize--;
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j-2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
		return juPersonSize;
		
	}

	private void rightArrangeToLeft3(String meetingNoticeId,JSONObject jsonObj, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		int rowIndex=2;
		int rowIndex1=1;
		if("1".equals(jsonObj.getString("flag")) && i==1){
			//第一排中间不可进行排座
			rowIndex=12;
			rowIndex1=13;
		}
		//从右边到中间开始排
		for(int j=26;j>=rowIndex;j=j-2){
			if(commonPersonId.size()>0){
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j+2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
		//从中间往左边开始排
		for(int j=rowIndex1;j<=27;j=j+2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setPublishState("0");
					seat.setFankuiType("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j-2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
	}

	private void leftArrangeToRight3(String meetingNoticeId,JSONObject jsonObj, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		int rowIndex=1;
		int rowIndex1=2;
		if("1".equals(jsonObj.getString("flag")) && i==1){
			//第一排不可进行排座
			rowIndex=13;
			rowIndex1=12;
		}
		for(int j=27;j>=rowIndex;j=j-2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setPublishState("0");
					seat.setFankuiType("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j+2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
		//从中间到右边开始排
		for(int j=rowIndex1;j<=26;j=j+2){
			if(commonPersonId.size()>0){
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j-2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
		}
	}

	private void rightArrangeToLeft2(String meetingNoticeId, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, int colIndex, Map<Integer, Seat> tempmap, int i) {
		//获取此行已经排好的座位
		Map<Integer,Seat> hasArrrangeSeat = getRowSeat(result,i);
		//从右往中间排
		for(int j=26;j>=colIndex;j=j-2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					String persionId = commonPersonId.remove(0);
					seat.setPublishState("0");
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						Seat tempSeat =hasArrrangeSeat.get(seat.getCol());
						if(tempSeat==null){
							//没有安排好座位
							result.add(seat);
							
						}else{
							commonPersonId.add(0,persionId);
							//j=j-2;
						}
					}else{
						j=j+2;
					}
					//result.add(seat);
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
			
		}
		//先从中间位置到左开始排
		/*if(colIndex%2==0){
			colIndex=colIndex+1;
		}*/
		for(int j=colIndex;j<=27;j=j+2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						Seat tempSeat =hasArrrangeSeat.get(seat.getCol());
						if(tempSeat==null){
							//没有安排好座位
							result.add(seat);
						}else{
							commonPersonId.add(0,persionId);
							//j=j+2;
						}
					}else{
						j=j-2;
					}
					//result.add(seat);
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				break;
			}
		}
	}

	private void leftArrangeToRight2(String meetingNoticeId, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, int colIndex, Map<Integer, Seat> tempmap, int i) {
		//获取此行已经排好的座位
		Map<Integer,Seat> hasArrrangeSeat = getRowSeat(result,i);
		//先从左到中间位置开始排
		for(int j=27;j>=colIndex;j=j-2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						Seat tempSeat =hasArrrangeSeat.get(seat.getCol());
						if(tempSeat==null){
							//没有安排好座位
							result.add(seat);
						}else{
							commonPersonId.add(0,persionId);
							//j=j-2;
						}
						
					}else{
						j=j+2;
					}
					//result.add(seat);
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				break;
			}
		}
		
		//从中间往右排
		/*if(colIndex%2!=0){
			colIndex=colIndex+1;
		}*/
		for(int j=colIndex;j<=26;j=j+2){
			if(j%2!=0){
				//该位置是基数
				j++;
			}
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					seat.setPublishState("0");
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						Seat tempSeat =hasArrrangeSeat.get(seat.getCol());
						if(tempSeat==null){
							//没有安排好座位
							result.add(seat);
						}else{
							commonPersonId.add(0,persionId);
							//j=j+2;
						}
					}else{
						j=j-2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				 break;
			}
			
		}
	}
	
	//获取某行已经排好的座位
	private Map<Integer, Seat> getRowSeat(Set<Seat> result, int i) {
		Map<Integer, Seat> map = new HashMap<Integer, Seat>();
		for (Seat seat : result) {
			if(i==seat.getRow()){
				map.put(seat.getCol(), seat);
			}
		}
		return map;
	}

	private void rightArrangeToLeft1(String meetingNoticeId, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		for(int j=26;j>=12;j=j-2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j+2;
					}
				}else{
					//该座位被占用
					continue;
				}
				
			}else{
				//普通人员已被排完
				break;
			}
		}
		//先排左边
		for(int j=13;j<=27;j=j+2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j-2;
					}
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				break;
			}
			
		}
	}
	
	//中间位置坐满，从左往右排座
	private void leftArrangeToRight1(String meetingNoticeId, List<String> commonPersonId, Set<Seat> result,
			Map<String, Map<String, List<String>>> persionMap, Map<String, String> deptMap,
			Map<String, SysFlowUserVo> userInfo, Map<Integer, Seat> tempmap, int i) {
		//先排左边
		for(int j=27;j>=13;j=j-2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j+2;
					}
					
				}else{
					//该座位被占用
					continue;
				}
			}else{
				//普通人员已被排完
				break;
			}
			
		}
		//排右边座位
		for(int j=12;j<=26;j=j+2){
			if(commonPersonId.size()>0){
				//普通人员未被排完
				Seat ownerseat = tempmap.get(j);
				if(ownerseat==null){
					//该座位未被占用
					Seat seat = new Seat();
					seat.setAutoArrange("0");
					seat.setState(2);
					seat.setRow(i);
					seat.setCol(j);
					seat.setFankuiType("0");
					seat.setPublishState("0");
					String persionId = commonPersonId.remove(0);
					seat.setOwnerid(persionId);
					//设置座位所属的参会部门
					setAttendDept(seat,persionId,persionMap,"2",deptMap);
					SysFlowUserVo userVo = userInfo.get(persionId);
					seat.setOwner(userVo.getUserNameFull());
					/*seat.setAttendDeptId(userVo.getUserDeptId());
					seat.setAttendDeptName(userVo.getUserDeptName());*/
					seat.setNoticeId(meetingNoticeId);
					if(!result.contains(seat)){
						result.add(seat);
					}else{
						j=j-2;
					}
					
				}else{
					//该座位被占用
					continue;
				}
				
			}else{
				//普通人员已被排完
				break;
			}
		}
	}
	
	/**
	 * //设置座位所属的参会部门
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月15日 下午3:37:49
	 * @param tempseat
	 * @param persionId
	 * @param persionMap
	 */
	private void setAttendDept(Seat tempseat, String persionId, Map<String, Map<String, List<String>>> persionMap,String flag,Map<String,String> deptMap) {
		Set<Entry<String, Map<String, List<String>>>> entrySet = persionMap.entrySet();
		for (Entry<String, Map<String, List<String>>> entry : entrySet) {
			String deptid = entry.getKey();
			Map<String, List<String>> map = entry.getValue();
			
			 List<String> personid = map.get(flag);
			
			if(personid!=null){
				if(personid.contains(persionId)){
					tempseat.setAttendDeptId(deptid);
					tempseat.setAttendDeptName(deptMap.get(deptid));
					break;
				}
			}
			
		}
		
	}

	/*
	 * 把某一排已经被占用的座位转成map形式，key为第几列，value为座位
	 */
	private void changColSeat(List<Seat> tempseats, Map<Integer, Seat> tempmap,int index,JSONObject jsonObj,String setJsonObjValue) {
		String flag="0";
		tempmap.clear();
		if(tempseats!=null){
			for (Seat seat : tempseats) {
				tempmap.put(seat.getCol(), seat);
			}
			//如果第一排有预存座位，就不进行排座
			if(index==1 && "1".equals(setJsonObjValue)){
				for(int j=1;j<=11;j++){
					Seat seat = tempmap.get(j);
					if(seat!=null && seat.getState()==4){
						flag="1";//第一排不可进行排座
						break;
					}
				}
			}
		}else{
			tempmap.clear();
		}
		jsonObj.put("flag", flag);
	}

	/**
	 * 把已经排座的座位信息转成map形式
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月11日 上午11:35:06
	 * @param seats
	 * @param map
	 */
	private void changeSeatData(List<Seat> seats, Map<Integer, List<Seat>> map) {
		for (Seat seat : seats) {
			if(map.get(seat.getRow())==null){
				List<Seat> list = new ArrayList<Seat>();
				list.add(seat);
				map.put(seat.getRow(), list);
			}else{
				map.get(seat.getRow()).add(seat);
			}
		}
		
	}

	/**
	 * 1.查询所有的参会部门信息，是业务部门还是综合部门,放入到businessDept、synthesizeDept、noTypeDept；2.并把每个参会部门人员进行分类放入不同的map中，即放到persionMap；
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月11日 上午9:53:18
	 * @param judeptids
	 * @param persionMap
	 * @param businessDept
	 * @param synthesizeDept
	 * @param noTypeDept
	 * @param list
	 */
	private void getDeptPersion(StringBuilder judeptids,StringBuilder chudeptids, Map<String,Map<String,List<String>>> persionMap,
			Set<Map<String, Object>> businessDept, Set<Map<String, Object>> synthesizeDept,
			Set<Map<String, Object>> noTypeDept, List<MeetingNoticeBack> list) {
		for (MeetingNoticeBack meetingNoticeBack : list) {
			/*if(meetingNoticeBack.getAttendPersonId()!=null && StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
				attendeePersionId.add(meetingNoticeBack.getAttendPersonId());
			}*/
			if("0".equals(meetingNoticeBack.getFlag())){
				//反馈的为 局用户
				if(!judeptids.toString().contains(meetingNoticeBack.getAttendDeptId())){
					judeptids.append(meetingNoticeBack.getAttendDeptId()+",");
				}
			}else{
				//反馈的是处或科用户
				if(!chudeptids.toString().contains(meetingNoticeBack.getAttendDeptId())){
					chudeptids.append(meetingNoticeBack.getAttendDeptId()+",");
				}
			}
			
			if(persionMap.get(meetingNoticeBack.getAttendDeptId())==null){
				Map<String,List<String>> map = new HashMap<String,List<String>>();
				persionMap.put(meetingNoticeBack.getAttendDeptId(), map);
			}
			
			Map<String,List<String>> map = persionMap.get(meetingNoticeBack.getAttendDeptId());
			//该反馈信息反馈的是部门领导
			if("0".equals(meetingNoticeBack.getFlag())){
				if(map.get("0")==null){
					List<String> list1 = new ArrayList<String>();
					//Set<String> set = new TreeSet<String>();
					if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
						 list1 = Arrays.asList(meetingNoticeBack.getAttendPersonId().split(","));
						 list1 = new ArrayList(list1);
					}
					
					map.put("0", new ArrayList(list1));
				}else{
					
					map.get("0").addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
				}
			}else if("1".equals(meetingNoticeBack.getFlag())){
				//该反馈信息反馈的是处长
				if(map.get("1")==null){
					List<String> list1 = new ArrayList<String>();
					//Set<String> set = new TreeSet<String>();
					if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
						 list1 = Arrays.asList(meetingNoticeBack.getAttendPersonId().split(","));
						 list1 = new ArrayList(list1);
					}
					//set.addAll(list1);
					map.put("1", list1);
				}else{
					map.get("1").addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
				}
			}else{
				//该反馈信息反馈的是第三部分人（副处长、处员、或科员）
				if(map.get("2")==null){
					List<String> list1 = new ArrayList<String>();
					//Set<String> set = new TreeSet<String>();
					if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
						 list1 = Arrays.asList(meetingNoticeBack.getAttendPersonId().split(","));
						 list1 = new ArrayList(list1);
					}
					//set.addAll(list1);
					map.put("2", list1);
				}else{
					map.get("2").addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
				}
			}
			/*if(persionMap.get(meetingNoticeBack.getAttendDeptId())==null){
				Set<String> set = new TreeSet<String>();
				set.add(meetingNoticeBack.getAttendPersonId());
				persionMap.put(meetingNoticeBack.getAttendDeptId(), set);
			}else{
				persionMap.get(meetingNoticeBack.getAttendDeptId()).add(meetingNoticeBack.getAttendPersonId());
			}*/
		}
		StringBuilder depSql = new StringBuilder();
		if(judeptids.length()!=0 || chudeptids.length()!=0){
			if(judeptids.length()!=0 && chudeptids.length()!=0){
				depSql.append(" select t.deptid,t.deptname,t.unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no from sys_flow_dept t where t.status='1' and t.deptid in (" + CommonUtils.commomSplit(judeptids.substring(0,judeptids.length()-1)) + ") ");
				depSql.append("  union ");
				depSql.append(" select t.deptid,t.deptname,(select t2.unit_type from sys_flow_dept t2 where t2.deptid = t.super_id) unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo, t.order_no order_no  from sys_flow_dept t where t.status = '1' and t.deptid in (" + CommonUtils.commomSplit(chudeptids.substring(0,chudeptids.length()-1)) + ") ");

			}else if(judeptids.length()!=0){
				depSql.append(" select t.deptid,t.deptname,t.unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo,t.order_no order_no from sys_flow_dept t where t.status='1' and t.deptid in (" + CommonUtils.commomSplit(judeptids.substring(0,judeptids.length()-1)) + ") ");
			}else{
				depSql.append(" select t.deptid,t.deptname,(select t2.unit_type from sys_flow_dept t2 where t2.deptid = t.super_id) unit_type,(select t2.order_no from sys_flow_dept t2 where t2.deptid = t.super_id) superOrderNo, t.order_no order_no  from sys_flow_dept t where t.status = '1' and t.deptid in (" + CommonUtils.commomSplit(chudeptids.substring(0,chudeptids.length()-1)) + ") ");
			}
			JSONObject jsonObj = userInfoService.getDateBySql(depSql.toString());
			List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
			for (Map<String, Object> map : data) {
				if("02".equals(map.get("unit_type").toString())){//业务部门
					if(!businessDept.contains(map)){
						businessDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						businessDept.add(map);
					}
					
				}else if("01".equals(map.get("unit_type").toString())){//综合部门
					if(!synthesizeDept.contains(map)){
						synthesizeDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						synthesizeDept.add(map);
					}
					
				}else{
					//部门没有类型
					if(!noTypeDept.contains(map)){
						noTypeDept.add(map);
					}else{
						String newOrderNo = map.get("order_no").toString()+"01";
						map.put("order_no", newOrderNo);
						noTypeDept.add(map);
					}
					//noTypeDept.add(map);
				}
			}
		}
	}

	//根据会议通知id所有的参会人及参会部门
	private List<MeetingNoticeBack> attendeePersion(String meetingNoticeId) {
		/*StringBuilder sb = new StringBuilder(" select t.id,t.attend_dept_type,t.attend_dept_id,t.attend_dept,t.flag, LISTAGG( to_char(attend_person_id), ',') WITHIN GROUP(ORDER BY attend_person_id) AS attend_person_id from (");	
		sb.append(" select t.id,t.attend_person_id attend_person_id,t.attend_dept_id,t.attend_dept,'0' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='0' and t.notice_id = '"+meetingNoticeId+"' ");
		sb.append(" union ");
		sb.append(" select t.id,t.ATTEND_PERSON_LEAVE_ID,t.attend_dept_id,t.attend_dept,'0' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='0' and t.notice_id = '"+meetingNoticeId+"' ");
		sb.append(" union ");
		sb.append(" select t.id,t.attend_person_chu_id,t.attend_dept_id,t.attend_dept,'0' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='0' and t.notice_id = '"+meetingNoticeId+"'  ");
		sb.append(" union ");
		
		sb.append(" select t.id,t.attend_person_chu_id,t.attend_dept_id,t.attend_dept,'1' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='1' and t.notice_id = '" + meetingNoticeId + "'");
		sb.append(" union ");
		sb.append("  select t.id,t.attend_person_id,t.attend_dept_id,t.attend_dept,'2' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='1' and t.notice_id = '" + meetingNoticeId+ "' ");
		sb.append(" union ");
		sb.append(" select t.id,t.ATTEND_PERSON_LEAVE_ID,t.attend_dept_id,t.attend_dept,'2' flag,t.attend_dept_type from hygl_notice_back t where t.visible = '1' and t.state='1' and t.ATTEND_DEPT_TYPE='1' and t.notice_id = '" + meetingNoticeId+ "' ");
		sb.append(" ) t where t.attend_person_id is not null group by t.id,t.attend_dept_id,t.attend_dept,t.flag,t.attend_dept_type order by t.flag ");*/
		StringBuilder sb = new StringBuilder(" select t.id,t.attend_dept_id,t.attend_dept,t.flag, ");
		sb.append("case when substr(replace(t.attend_person_id,',,',','),0,1)=',' then substr(replace(t.attend_person_id,',,',','),2,length(replace(t.attend_person_id,',,',','))-1) else replace(t.attend_person_id,',,',',') end attend_person_id, ");
		sb.append(" t.attend_dept_type  from( ");
		sb.append(" select t.id,(t.attend_person_chu_id||','||t.ATTEND_PERSON_LEAVE_ID||','||t.attend_person_id)attend_person_id,t.attend_dept_id,t.attend_dept,'0' flag,t.attend_dept_type  from hygl_notice_back t ");
		sb.append(" where t.notice_id='" + meetingNoticeId + "' and t.visible = '1' and t.state = '1' and  t.ATTEND_DEPT_TYPE = '0' ");
		sb.append("  union ");
		sb.append(" select t.id,t.attend_person_chu_id attend_person_id,t.attend_dept_id,t.attend_dept,'1' flag,t.attend_dept_type  from hygl_notice_back t ");
		sb.append("  where t.notice_id='" + meetingNoticeId+ "' and t.visible = '1' and t.state = '1' and  t.ATTEND_DEPT_TYPE = '1' ");
		sb.append("  union ");
		sb.append(" select t.id,(t.ATTEND_PERSON_LEAVE_ID||','||t.attend_person_id)attend_person_id ,t.attend_dept_id,t.attend_dept,'2' flag,t.attend_dept_type  from hygl_notice_back t ");
		sb.append("  where t.notice_id='" + meetingNoticeId + "' and t.visible = '1' and t.state = '1' and  t.ATTEND_DEPT_TYPE = '1' ");
		sb.append(" )t  ");
		List<MeetingNoticeBack> list = jdbcTemplate.query(sb.toString(), new RowMapper<MeetingNoticeBack>(){
			@Override
			public MeetingNoticeBack mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingNoticeBack noticeBack = new MeetingNoticeBack();
				noticeBack.setId(result.getString("id"));
				noticeBack.setAttendPersonId(result.getString("attend_person_id"));
				noticeBack.setAttendDeptId(result.getString("attend_dept_id"));
				noticeBack.setAttendDept(result.getString("attend_dept"));
				noticeBack.setFlag(result.getString("flag"));
				noticeBack.setAttendDeptType(result.getString("attend_dept_type"));
				//noticeBack.setAttendDeptType(result.getString("attend_dept_type"));
				return noticeBack;
			}
			
		});
		return list;
	}
	
	/**
	 * /查询手动已经安排好座位的人员id和预存座位，即非自动安排的座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月10日 下午4:40:47
	 * @param meetingNoticeId
	 * @return
	 */
	private void getOWnerIds(String meetingNoticeId,Map<String,Object> jsonMap) {
		final Set<String> ownerids = new TreeSet<String>();
		StringBuilder sb = new StringBuilder("select t.ownerid,t.row1,t.col,t.state,t.auto_arrange from hygl_seat t where t.auto_arrange!='0' and t.noticeid='" + meetingNoticeId + "'  ");
		List<Seat> seats = jdbcTemplate.query(sb.toString(), new RowMapper<Seat>(){
			@Override
			public Seat mapRow(ResultSet result, int arg1) throws SQLException {
				Seat seat = new Seat();
				seat.setOwnerid(result.getString("ownerid"));
				seat.setRow(result.getInt("row1"));
				seat.setCol(result.getInt("col"));
				seat.setState(result.getInt("state"));
				seat.setAutoArrange(result.getString("auto_arrange"));
				return seat;
			}
		});
		for (Seat seat : seats) {
			if("1".equals(seat.getAutoArrange())){
				if(seat.getOwnerid()!=null){
					ownerids.add(seat.getOwnerid());
				}
				
			}
		}
		jsonMap.put("ownerids", ownerids);
		jsonMap.put("seats", seats);
	}

	/**
	 * 获取此次会议二级局和处长的参会人员信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月30日 上午10:33:26
	 * @param meetingNoticeId
	 * @return
	 */
	@Override
	public List<UserSeat> getAllAttendees(String meetingNoticeId,String isJu,String isChuZhang) {
		StringBuilder personIds = new StringBuilder();//存放参会人员id
		//根据会议通知获取会议二级局和处长的参会人员
		//List<UserSeat> list =  getAllAttendeesInfo(meetingNoticeId,isJu,isChuZhang,personIds);
		List<UserSeat> list =  getAllAttendeesInfo1(meetingNoticeId,isJu,isChuZhang,personIds);
		//给二级局和处长设置座位信息
		getPeopleSeatInfo(meetingNoticeId,list);
		return list;
	}
	
	
	
	//根据会议通知id查询某些用户的的座位信息,并给二级局和处长设置座位信息
	private Map<String,Seat> getPeopleSeatInfo(String meetingNoticeId, List<UserSeat> list) {
		//查询此次会议通知的人员座位信息
		StringBuilder ownerids = new StringBuilder();
        for (UserSeat userSeat : list) {
        	ownerids.append(userSeat.getUserId()+",");
		}
		StringBuilder sql = new StringBuilder("select t.id,t.ownerid,t.row1,t.col from hygl_seat t where t.NOTICEID ='" + meetingNoticeId + "'  ");
		if(StringUtils.isNotBlank(ownerids.toString())){
			sql.append(" and t.OWNERID in (" + CommonUtils.commomSplit(ownerids.substring(0, ownerids.length()-1)) + ") ");
		}
		List<Seat> seats =  jdbcTemplate.query(sql.toString(), new RowMapper<Seat>(){
			@Override
			public Seat mapRow(ResultSet result, int arg1) throws SQLException {
				Seat seat = new Seat();
				seat.setId(result.getString("id"));
				seat.setOwnerid(result.getString("ownerid"));
				seat.setRow(result.getInt("row1"));
				seat.setCol(result.getInt("col"));
				return seat;
			}
			
		});
		
		Map<String,Seat> map = new HashMap<String,Seat>();//key表示人员id，value表示座位信息
		for (Seat seat : seats) {
			map.put(seat.getOwnerid(), seat);
		}
		//给可手动 排座的人员设置座位信息
	    for (UserSeat userSeat : list) {
	    	Seat seat = map.get(userSeat.getUserId());
	    	if(seat!=null){
	    		//有该人员的座位信息
	    		userSeat.setId(seat.getId());
	    		userSeat.setRow(seat.getRow());
	    		userSeat.setCol(seat.getCol());
	    	}
		}
		return map;
	}
	
	
	
	
	/**
	 * 根据会议通知获取会议二级局和处长的参会人员
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月9日 下午2:09:31
	 * @param meetingNoticeId
	 * @return
	 */
	private List<UserSeat> getAllAttendeesInfo1(String meetingNoticeId,String isJu,String isChuZhang,StringBuilder personIds) {
		Map<String,String> attendDeptMap = new HashMap<String,String>();//存放的是参会部门信息,key为部门id,value为部门名称 
		Map<String,List<MeetingNoticeBack>> backInfoList = meetingNoticeBackService.findByNoticeId(meetingNoticeId,isJu,isChuZhang,attendDeptMap);//key为参会部门id,value为反馈信息
		List<UserSeat> juPersionList = new ArrayList<UserSeat>();// 存放的是局级用户，
		List<UserSeat> chuPersionList = new ArrayList<UserSeat>();// 存放的是处级用户，
		//存放每个部门局级和处级的用户id,key为部门id,value为一个map,当key为0表示存放的是局级的用户id,为处时，表示存放的是处级的用户id
		Map<String,Map<String,Set<String>>> persionMap = new HashMap<String,Map<String,Set<String>>>();
		StringBuilder deptid = new StringBuilder();
		List<UserSeat> result = new ArrayList<UserSeat>();// 最终返回人员及其座位信息，为最终返回结果
		//存放业务部门
		Set<Map<String,Object>> businessDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return o1.get("deptid").toString().compareTo(o2.get("deptid").toString());
				}
		});
				//存放综合部门
		Set<Map<String,Object>>  synthesizeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return o1.get("deptid").toString().compareTo(o2.get("deptid").toString());
				}
		});
				//存放未设置部门类型的部门
		Set<Map<String,Object>> noTypeDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						return o1.get("deptid").toString().compareTo(o2.get("deptid").toString());
				}
		});
		
		Set<Entry<String,List<MeetingNoticeBack>>> entrySet = backInfoList.entrySet();
		for (Entry<String, List<MeetingNoticeBack>> entry : entrySet) {
			deptid.append(entry.getKey()+",");
			List<MeetingNoticeBack> tempList = entry.getValue();
			Map<String,Set<String>> deptPersionMap = persionMap.get(entry.getKey());
			if(deptPersionMap==null){
				Map<String,Set<String>> tempMap = new HashMap<String,Set<String>>();
				persionMap.put(entry.getKey(), tempMap);
			}
			for (MeetingNoticeBack meetingNoticeBack : tempList) {
				if(meetingNoticeBack.getAttendPersonId()!=null && StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
					personIds.append(meetingNoticeBack.getAttendPersonId()+",");
				}
				if("0".equals(meetingNoticeBack.getFlag())){
					//该反馈，反馈的是部门领导
					Set<String> set = persionMap.get(entry.getKey()).get("0");
					if(set==null){
						Set<String> tempJuPesionId = new TreeSet<String>();
						if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
							tempJuPesionId.addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
						}
						persionMap.get(entry.getKey()).put("0", tempJuPesionId);
					}else{
						Set<String> set1 = persionMap.get(entry.getKey()).get("0");
						if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())) {
							set1.addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
						}
						
					}
					
					
				}else{
					//反馈的是处级用户
					Set<String> set = persionMap.get(entry.getKey()).get("1");
					if(set==null){
						Set<String> tempJuPesionId = new TreeSet<String>();
						if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())){
							tempJuPesionId.addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
						}
						
						persionMap.get(entry.getKey()).put("1", tempJuPesionId);
					}else{
						
						Set<String> attendPersonIds = persionMap.get(entry.getKey()).get("1");
						if(StringUtils.isNotBlank(meetingNoticeBack.getAttendPersonId())) {
							attendPersonIds.addAll(Arrays.asList(meetingNoticeBack.getAttendPersonId().split(",")));
						}
					}
				}
				
				
			}
		}
		//获取参会人员的详细信息
		Map<String, SysFlowUserVo> userinfo;
		if(personIds.length()!=0){
				userinfo= UserUtil.getUserVo(personIds.substring(0, personIds.length()-1));
		}else{
				userinfo = new HashMap<String, SysFlowUserVo>();
		}
		
		//获取部门的详细信息，区分哪些属于业务部门，哪些数据综合部门
		if(deptid.length()!=0){
					String depSql="select t.deptid,t.deptname,t.unit_type,t.order_no from sys_flow_dept t where t.status='1' and t.deptid in (" + CommonUtils.commomSplit(deptid.substring(0,deptid.length()-1)) + ")";
					JSONObject jsonObj = userInfoService.getDateBySql(depSql);
					List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
					for (Map<String, Object> map : data) {
						if("02".equals(map.get("unit_type").toString())){//业务部门
							businessDept.add(map);
						}else if("01".equals(map.get("unit_type").toString())){//综合部门
							synthesizeDept.add(map);
						}else{
							//部门没有类型
							noTypeDept.add(map);
						}
					}
					//根据不同的部门类型，把  各个部门的人员信息按顺序放到result中
					int index=1;
					//存放业务部门的人员信息
					for (Map<String,Object> tempdept : businessDept) {
						index = setUserSeat(index,juPersionList, chuPersionList,  tempdept, userinfo,persionMap,backInfoList,attendDeptMap);
					}
					//存放综合部门的人员信息
					for (Map<String,Object> tempdept: synthesizeDept) {
						index = setUserSeat(index,juPersionList, chuPersionList, tempdept, userinfo,persionMap,backInfoList,attendDeptMap);
					}
					//存放部门没有部门类型的人员信息
					for (Map<String,Object> tempdept : noTypeDept) {
						index = setUserSeat(index,juPersionList, chuPersionList, tempdept, userinfo,persionMap,backInfoList,attendDeptMap);
					}
		}
		result.addAll(juPersionList);
		result.addAll(chuPersionList);
		
		return result;
	}
	


	/**
	 * 
	 * 把每个部门的用户信息分类放到juPersionList和chuPersionList中
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月12日 下午6:14:10
	 * @param juPersionList
	 * @param chuPersionList
	 * @param tempdept
	 * @param userinfo
	 * @param persionMap
	 * @return
	 */
	private int setUserSeat(int index,List<UserSeat> juPersionList, List<UserSeat> chuPersionList,
			Map<String, Object> tempdept, Map<String, SysFlowUserVo> userinfo, Map<String, Map<String, Set<String>>> persionMap,Map<String,List<MeetingNoticeBack>> backInfoList,Map<String,String> attendDeptMap) {
		String deptid = tempdept.get("deptid").toString();
		Map<String, Set<String>> map = persionMap.get(deptid);
		Set<String> juPersionset = map.get("0");//为局级用户
		Set<String> chuPersionset = map.get("1");//为处级用户
		//添加的为局级用户
		if(juPersionset!=null){
			for (String persionId : juPersionset) {
				SysFlowUserVo userVo = userinfo.get(persionId);
				UserSeat userSeat = new UserSeat();
				userSeat.setAttendDeptId(deptid);
				userSeat.setAttendDeptName(attendDeptMap.get(deptid));
				BeanUtils.copyProperties(userVo, userSeat);
				userSeat.setOrder(index);
				juPersionList.add(userSeat);
				index++;
			}
		}
		
		//添加的为局级用户
		if(chuPersionset!=null){
			for (String persionId : chuPersionset) {
				SysFlowUserVo userVo = userinfo.get(persionId);
				UserSeat userSeat = new UserSeat();
				userSeat.setAttendDeptId(deptid);
				userSeat.setAttendDeptName(attendDeptMap.get(deptid));
				BeanUtils.copyProperties(userVo, userSeat);
				userSeat.setOrder(index);
				chuPersionList.add(userSeat);
				index++;
			}
		}
		
		return index;
	}


	
	/**
	 * 手动排座的保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月10日 下午2:27:14
	 * @param seatDataJson
	 */
	@Override
	public List<Seat> manualSeatSave(String meetingNoticeId,String seatDataJson,String fankuiType) {
		JSONArray jSONArray = JSONArray.fromObject(seatDataJson);
		//查询已排座的座位
		StringBuilder ownerSeatIds = new StringBuilder();
		for (Object object : jSONArray) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if(jsonObj.get("id")!=null && (!"".equals(jsonObj.get("id")))){
				ownerSeatIds.append(jsonObj.getString("id")+",");
			}
		}
		List<Seat> ownerList = new ArrayList<Seat>();
		if(ownerSeatIds.length()>0){
			ownerSeatIds.deleteCharAt(ownerSeatIds.length()-1);
			ownerList=getBySeatId(meetingNoticeId,CommonUtils.commomSplit(ownerSeatIds.toString()));
		}
		Map<String,Seat> owerMap = new HashMap<String,Seat>();
		listToMap(ownerList,owerMap);
		StringBuilder delSql = new StringBuilder(" delete from hygl_seat where noticeid='" + meetingNoticeId + "' ");
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//overTime.setOverTimeTitle("测试加班流程标题");
		StringBuilder ownerid = new StringBuilder();
		List<Seat> list =new ArrayList<Seat>();
		int i=0;
		StringBuilder deleteSql = new StringBuilder();
		for (Object object : jSONArray) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if(jsonObj.getString("ownerid")!=null && StringUtils.isNotBlank(jsonObj.getString("ownerid"))){
				ownerid.append(jsonObj.getString("ownerid")+",");
				Seat seat = new Seat();
				seat.setCreUserId(UserUtil.getCruUserId());
				seat.setCreUserName(UserUtil.getCruUserName());
				seat.setCreChushiId(UserUtil.getCruChushiId());
				seat.setCreChushiName(UserUtil.getCruChushiName());
				seat.setCreDeptId(UserUtil.getCruDeptId());
				seat.setCreDeptName(UserUtil.getCruDeptName());
				seat.setFankuiType(fankuiType);
				seat.setCreJuId(UserUtil.getCruJuId());
				if(jsonObj.get("id")!=null && (!"".equals(jsonObj.get("id")))){
					seat.setId(jsonObj.getString("id"));
					seat.setAutoArrange(owerMap.get(jsonObj.getString("id")).getAutoArrange());
					
				}else{
					seat.setAutoArrange("1");
					
				}
				seat.setState(2);
				seat.setOwnerid(jsonObj.getString("ownerid"));
				seat.setOwner(jsonObj.getString("owner"));
				seat.setRow(jsonObj.getInt("row"));
				seat.setCol(jsonObj.getInt("col"));
				seat.setAttendDeptId(jsonObj.getString("attendDeptId"));
				seat.setAttendDeptName(jsonObj.getString("attendDeptName"));
				seat.setNoticeId(meetingNoticeId);
				list.add(seat);
			}else{
				i++;
				if(i==1){
					delSql.append("  and ( row1= " + jsonObj.getInt("row") + " and col = " + jsonObj.getInt("col") + "  ");
					
				}else{
					delSql.append("  or row1= " + jsonObj.getInt("row") + " and col = " + jsonObj.getInt("col") + " ");
				}
			}
			
		}
		delSql.append(" )");
		//先把这些人之前的座位给清空掉
		if(i>0){
			jdbcTemplate.execute(delSql.toString());
		}
		dao.save(list);
		return list;
		
	}
	
	private void listToMap(List<Seat> ownerList, Map<String,Seat> owerMap) {
		for (Seat seat : ownerList) {
			owerMap.put(seat.getId(), seat);
		}
		
	}

	private List<Seat> getBySeatId(String meetingNoticeId,String commomSplit) {
		String sql = "  select t.id,t.row1,t.col,t.auto_arrange from hygl_seat t where t.noticeId = '" + meetingNoticeId + "' and id in (" + commomSplit + ") ";
		List<Seat> list= jdbcTemplate.query(sql, new RowMapper<Seat>(){
			@Override
			public Seat mapRow(ResultSet result, int arg1) throws SQLException {
				Seat seat = new Seat();
				seat.setId(result.getString("id"));
				seat.setRow(result.getInt("row1"));
				seat.setCol(result.getInt("col"));
				seat.setAutoArrange(result.getString("auto_arrange"));
				return seat;
			}
			
		});
		return list;
	}

	/**
	 * 发布
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月18日 下午5:40:54
	 * @param meetingNoticeId
	 * @return
	 */
	@Override
	public void publish(String meetingNoticeId,String fankuiType) {
		Map<String,MeetingNoticeBack> juDeptmap = new HashMap<String,MeetingNoticeBack>();//存放局的反馈信息，key为部门id,value为反馈
		Map<String,MeetingNoticeBack> chuDeptmap = new HashMap<String,MeetingNoticeBack>();//存放处级部门的反馈信息，key为部门id,value为反馈信息
		//存放的的是部门 和角色用户信息，key为部门id,value为一个map，map中的key为业务角色，map中的value为用户id
		Map<String,Map<String,List<String>>> deptRoleUserMap = new HashMap<String,Map<String,List<String>>>();
		//存放的是角色用户信息，key为业务角色id,value为用户id
		Map<String,List<String>> roleUserMap = new HashMap<String,List<String>>();
		StringBuilder deptids = new StringBuilder();//存放所有参会的部门id
		if("0".equals(fankuiType)){
			//反馈的是人名
			publishByPersonName(meetingNoticeId, juDeptmap, chuDeptmap, deptRoleUserMap, deptids);
		}else{
			//反馈的是人数
			publishByPersonNum(meetingNoticeId, juDeptmap, chuDeptmap, deptRoleUserMap, deptids);
		}
	
		
		//修改会议通知座位的状态
		StringBuilder noticeSql = new StringBuilder(" update HYGL_MEETING_NOTICE set publish_state='1' where id='" + meetingNoticeId + "' ");
		StringBuilder seatSql = new StringBuilder(" update hygl_seat set publish_state='1' where  noticeid='" + meetingNoticeId + "' ");
		jdbcTemplate.execute(noticeSql.toString());
		jdbcTemplate.execute(seatSql.toString());
	}

	/** TODO 
	 * @author 李颖洁  
	 * @date 2019年3月25日上午8:53:39
	 * @param meetingNoticeId
	 * @param juDeptmap
	 * @param chuDeptmap
	 * @param deptRoleUserMap
	 * @param deptids
	 * @return void
	 */
	private void publishByPersonNum(String meetingNoticeId, Map<String, MeetingNoticeBack> juDeptmap,
			Map<String, MeetingNoticeBack> chuDeptmap, Map<String, Map<String, List<String>>> deptRoleUserMap,
			StringBuilder deptids) {
		//用于存放所有参会的部门，key表示存放的是局的部门id,还是处的部门id,key为0时表示value(也就是后面的list)存放的是局的部门id;为1时表示存放的处的部门id
		Map<String,List<String>> deptmap = new HashMap<String,List<String>>();
		//根据通知id获取已反馈的信息
		List<MeetingNoticeBack> backList = attendeeInfo(meetingNoticeId);
		MeetingNoticeInfo notice = noticeDao.findOne(meetingNoticeId);
		//获取所有所通知的部门,放到deptids，并按局和处进行分类，放到map中
		for (MeetingNoticeBack meetingNoticeBack : backList) {
			deptids.append(meetingNoticeBack.getAttendDeptId()+",");
			if("0".equals(meetingNoticeBack.getAttendDeptType())) {
				//局部门
				juDeptmap.put(meetingNoticeBack.getAttendDeptId(), meetingNoticeBack);
			}else {
				//处部门
				chuDeptmap.put(meetingNoticeBack.getAttendDeptId(), meetingNoticeBack);
			}
		}
		deptids.deleteCharAt(deptids.length()-1);
		//查询所有此次会议通知的所有机要交换员
		StringBuilder deptUserSql = new StringBuilder("select t.roleNo,t.userid,t.deptid, t.usdr_name,t.deptname,t.treeId from (");
		deptUserSql.append(" select t1.ROLE_NO roleNo,t.userid,t.usdr_name,t2.deptid,t2.tree_id treeId,t2.deptname from sys_user_dprb t right outer join(  ");
		deptUserSql.append("  select t.*,t1.t1.ROLE_NO from sys_user_frole t left outer join sys_flow_role t1 on t.roleid=t1.roleid where t1.ROLE_NO in ('C011','D012','C010','D011') ");
		deptUserSql.append("  )t1 on t.id=t1.u_dept_id right outer join  ( ");
		deptUserSql.append("  select t.* from sys_flow_dept t start with t.deptid in (" + CommonUtils.commomSplit(deptids.toString()) + ") connect by prior deptid = super_id  ");
		deptUserSql.append(" )t2 on t.deptid=t2.deptid  order by nvl(length(trim(t2.tree_id)),0) asc ");
		deptUserSql.append("  ) t   group by t.roleNo,t.userid,t.deptid, t.usdr_name,t.deptname,t.treeId order by nvl(length(trim(t.treeId)), 0) ");
		JSONObject jsonObj = userInfoService.getDateBySql(deptUserSql.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		//去重，先把获取到的，业务角色一样、用户id，部门id一样的记录去掉
		//TreeSet<Map<String, Object>> set = Distinct(data);
		//把通知的所有局级部门的部门收发人员，转成map格式放到deptRoleUserMap中
		if(juDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= juDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//设置某个局部门的部门收发人员有哪些，转成map格式，放到deptRoleUserMap中
				setJuSendAndReceive1(deptRoleUserMap,data,entry.getKey(),"1");
			}
			
		}
		//把通知的所有处级部门的收发人员,转成map格式放到deptRoleUserMap中
		if(chuDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= chuDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//设置某个处部门的部门收发人员有哪些，转成map格式，放到deptRoleUserMap中
				setJuSendAndReceive1(deptRoleUserMap,data,entry.getKey(),"0");
			}
			
		}
		
		//给所有参会的部门收发人员和收发人员发送待办（座位图）
		//sendDeptDaiban(deptRoleUserMap, notice, juDeptmap,chuDeptmap);
		sendDeptDaiban1(deptRoleUserMap, notice, juDeptmap,chuDeptmap);
		
		//给所有参会人员发送个人座位图和座位票（发消息）
		//sendPersonDaiban(notice,meetingNoticeId);
		//sendPersonDaiban(notice,backList);
		
	}

	/** TODO 
	 * @author 李颖洁  
	 * @date 2019年3月25日上午8:55:00
	 * @param meetingNoticeId
	 * @return
	 * @return List<MeetingNoticeBack>
	 */
	private List<MeetingNoticeBack> attendeeInfo(String meetingNoticeId) {
		StringBuilder sb = new StringBuilder("select id,attend_dept,attend_dept_id,attend_dept_type  from hygl_notice_back where visible = '1' and state='1'  and notice_id='" + meetingNoticeId + "'  ");
		List<MeetingNoticeBack> backList = jdbcTemplate.query(sb.toString(), new RowMapper<MeetingNoticeBack>(){

			@Override
			public MeetingNoticeBack mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingNoticeBack back=new MeetingNoticeBack();
				back.setId(result.getString("id"));
				back.setAttendDept(result.getString("attend_dept"));
				back.setAttendDeptId(result.getString("attend_dept_id"));
				back.setAttendDeptType(result.getString("attend_dept_type"));
				return back;
			}
			
		});
		return backList;
	}

	/** TODO 
	 * @author 李颖洁  
	 * @date 2019年3月25日上午8:52:48
	 * @param meetingNoticeId
	 * @param juDeptmap
	 * @param chuDeptmap
	 * @param deptRoleUserMap
	 * @param deptids
	 * @return void
	 */
	private void publishByPersonName(String meetingNoticeId, Map<String, MeetingNoticeBack> juDeptmap,
			Map<String, MeetingNoticeBack> chuDeptmap, Map<String, Map<String, List<String>>> deptRoleUserMap,
			StringBuilder deptids) {
		//用于存放所有参会的部门，key表示存放的是局的部门id,还是处的部门id,key为0时表示value(也就是后面的list)存放的是局的部门id;为1时表示存放的处的部门id
		Map<String,List<String>> deptmap = new HashMap<String,List<String>>();
		//根据通知id获取已反馈的人员信息
		List<MeetingNoticeBack> backList = attendeePersion(meetingNoticeId);
		MeetingNoticeInfo notice = noticeDao.findOne(meetingNoticeId);
		//获取所有所通知的部门,放到deptids，并按局和处进行分类，放到map中
		for (MeetingNoticeBack meetingNoticeBack : backList) {
			deptids.append(meetingNoticeBack.getAttendDeptId()+",");
			if("0".equals(meetingNoticeBack.getAttendDeptType())) {
				//局部门
				juDeptmap.put(meetingNoticeBack.getAttendDeptId(), meetingNoticeBack);
			}else {
				//处部门
				chuDeptmap.put(meetingNoticeBack.getAttendDeptId(), meetingNoticeBack);
			}
		}
		deptids.deleteCharAt(deptids.length()-1);
		//查询所有此次会议通知的所有机要交换员
		StringBuilder deptUserSql = new StringBuilder("select t.roleNo,t.userid,t.deptid, t.usdr_name,t.deptname,t.treeId from (");
		deptUserSql.append(" select t1.ROLE_NO roleNo,t.userid,t.usdr_name,t2.deptid,t2.tree_id treeId,t2.deptname from sys_user_dprb t right outer join(  ");
		deptUserSql.append("  select t.*,t1.t1.ROLE_NO from sys_user_frole t left outer join sys_flow_role t1 on t.roleid=t1.roleid where t1.ROLE_NO in ('C011','D012','C010','D011') ");
		deptUserSql.append("  )t1 on t.id=t1.u_dept_id right outer join  ( ");
		deptUserSql.append("  select t.* from sys_flow_dept t start with t.deptid in (" + CommonUtils.commomSplit(deptids.toString()) + ") connect by prior deptid = super_id  ");
		deptUserSql.append(" )t2 on t.deptid=t2.deptid  order by nvl(length(trim(t2.tree_id)),0) asc ");
		deptUserSql.append("  ) t   group by t.roleNo,t.userid,t.deptid, t.usdr_name,t.deptname,t.treeId order by nvl(length(trim(t.treeId)), 0) ");
		JSONObject jsonObj = userInfoService.getDateBySql(deptUserSql.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		//去重，先把获取到的，业务角色一样、用户id，部门id一样的记录去掉
		//TreeSet<Map<String, Object>> set = Distinct(data);
		//把通知的所有局级部门的部门收发人员，转成map格式放到deptRoleUserMap中
		if(juDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= juDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//设置某个局部门的部门收发人员有哪些，转成map格式，放到deptRoleUserMap中
				setJuSendAndReceive1(deptRoleUserMap,data,entry.getKey(),"1");
			}
			
		}
		//把通知的所有处级部门的收发人员,转成map格式放到deptRoleUserMap中
		if(chuDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= chuDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//设置某个处部门的部门收发人员有哪些，转成map格式，放到deptRoleUserMap中
				setJuSendAndReceive1(deptRoleUserMap,data,entry.getKey(),"0");
			}
			
		}
		
		//给所有参会的部门收发人员和收发人员发送待办（座位图）
		//sendDeptDaiban(deptRoleUserMap, notice, juDeptmap,chuDeptmap);
		sendDeptDaiban1(deptRoleUserMap, notice, juDeptmap,chuDeptmap);
		
		//给所有参会人员发送个人座位图和座位票（发消息）
		//sendPersonDaiban(notice,meetingNoticeId);
		sendPersonDaiban(notice,backList);
	}
	
	//给所有参会人员发送个人座位图
	private void sendPersonDaiban(MeetingNoticeInfo notice, List<MeetingNoticeBack> backList) {
		String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		for (MeetingNoticeBack meetingNoticeBack : backList) {
			String userids = meetingNoticeBack.getAttendPersonId();
			if(StringUtils.isNotBlank(userids)) {
				String[] useridArry =userids.split(",");
				for (String userid : useridArry) {
					/*SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
					waitNoflwJu.setReceiveUserId(userid);//接受人id
					waitNoflwJu.setReceiveDeptId(meetingNoticeBack.getAttendDeptId());
					//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
					//waitNoflwJu.setRolesNo("D103");//接受业务角色
					waitNoflwJu.setOpName("会议的座位通知");//操作类型
					
					waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+meetingNoticeBack.getAttendDeptId()+"&userid="+userid+"&backId="+meetingNoticeBack.getId()+"&remark="+notice.getAttentionItem());//待办url  必填
					waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议的个人座位图和座位票");//待办显示标题
					waitNoflwJu.setTableId(notice.getId());//业务表id
					waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu.setSourceId(notice.getId());//业务id
					waitNoflwJu.setAttr1("");//预留字段1
					waitNoflwJu.setAttr2("");//预留字段2
					waitNoflwJu.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议的个人座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?attendDetpTyp=0&meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+meetingNoticeBack.getAttendDeptId()+"&userid="+userid+"&backId="+meetingNoticeBack.getId()+"&remark="+notice.getAttentionItem());*/
					/*String cruUserid = UserUtil.getCruUserId();
					//发送消息
					String msgUrl="/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+meetingNoticeBack.getAttendDeptId()+"&userid="+userid+"&backId="+meetingNoticeBack.getId();
					
					String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					sendMsgsByUid(userid, "会议的座位通知", "关于" + notice.getNoticeName() + "次会议的个人座位图和座位票", msgUrl,creTime,null);*/
					SysFlowSend flowSend = new SysFlowSend();
					flowSend.setSendUserId(UserUtil.getCruUserId());
					flowSend.setSendUserName(UserUtil.getCruUserName());
					flowSend.setSendDeptId(UserUtil.getCruDeptId());
					flowSend.setSendDeptName(UserUtil.getCruDeptName());
					flowSend.setSendChuShiId(UserUtil.getCruChushiId());
					flowSend.setSendChuShiName(UserUtil.getCruChushiName());
					flowSend.setSendJuId(UserUtil.getCruJuId());
					flowSend.setSendJuName(UserUtil.getCruJuName());
					flowSend.setSendTime(dateTime);
					flowSend.setStatus("0");
					flowSend.setReceiveUserId(userid);
					flowSend.setReceiveDeptId(meetingNoticeBack.getAttendDeptId());
					flowSend.setReceiveDeptName(meetingNoticeBack.getAttendDept());
					flowSend.setTitle("关于" + notice.getNoticeName() + "次会议的个人座位图和座位票");
					flowSend.setRecordId(notice.getId());
					flowSend.setSendUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+meetingNoticeBack.getAttendDeptId()+"&userid="+userid+"&backId="+meetingNoticeBack.getId());
					flowSend.setSysId(ConfigConsts.SYSTEM_ID);
					flowSend.setWorkflowName("通知的座位图和座位票");
					sendService.sendFlowSend(flowSend);	
				}
			}
		}
		
	}

	
	   public boolean sendMsgsByUid(String userId, String subject, String content, String messageURL, String creTime, SysWaitNoflow entity) {
	        boolean flag = false;
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date data = new Date();
	        try {
	            data = format.parse(creTime);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        if (StringUtils.isNotBlank(userId)) {
	          /*  if (StringUtils.isNotBlank(messageURL)) {
	                if (messageURL.contains("?")) {
	                    messageURL = messageURL += "&workItemId=" + entity.getId();
	                } else {
	                    messageURL = messageURL += "?workItemId=" + entity.getId();
	                }
	            }*/
	            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
	                NotityMessage message = new NotityMessage();
	                message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
	                message.setSubject(subject);//标题
	                message.setContent(content);//内容
	                message.setSendTime(data);//发送时间
	                message.setAcceptTime(data);//接收时间
	                message.setAccepterId(userId);//接收人id
	                message.setStatus("0");//状态
	                message.setType("3");//类型  1手机    2邮箱   3栈内
	                message.setMsgUrl(messageURL);//消息链接
	                NotityService notityService = (NotityService) SpringBeanUtils.getBean("notityService");
	                notityService.add(message);
	                flag = true;
	            } else {
	                String param = "sendType=3&sendContent=" + content + "&uids=" + userId + "&subId=" + ConfigConsts.SYSTEM_ID +
	                        "&msgUrl=" + messageURL + "&title=" + subject + "&appSecret=af2fff3bda2043a991018689848793b4";
	                HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL + "/sendMsgsByUid", param);
	                flag = true;
	            }
	        }
	        return flag;
	    }
	//去重，先把获取到的，业务角色一样、用户id，部门id一样的记录去掉
	private TreeSet<Map<String, Object>> Distinct(List<Map<String, Object>> data) {
		
		TreeSet<Map<String, Object>> set = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Object deptid1 = o1.get("deptid").toString();
				Object roleNo1 = o1.get("roleno").toString();
				Object userid1 = o1.get("userid").toString();
				Object deptid2 = o2.get("deptid").toString();
				Object roleNo2 = o2.get("roleno").toString();
				Object userid2 = o2.get("userid").toString();
				int flg=1;
				if("null".equals(deptid1) && "null".equals(roleNo1) && "null".equals(userid1) && "null".equals(deptid2) && "null".equals(roleNo2) && "null".equals(userid2) ) {
					if(deptid1.equals(deptid2) && roleNo1.equals(roleNo2) && userid1.equals(userid2)) {
						flg=0;
					}
				}
				
				return flg;
			}
		});
       for (Map<String, Object> map : data) {
    	   set.add(map);
		}
		return set;
		
	}

	//给所有参会人员发送个人座位图
	private void sendPersonDaiban(MeetingNoticeInfo notice,String meetingNoticeId) {
		//查询此次参会的所有人的座位
		String sql = "select t.ownerid,t.attend_dept_id attendDeptId from hygl_seat t where t.noticeid='" + meetingNoticeId + "' and  t.state='2' and t.owner is not null";
		List<Seat> list =jdbcTemplate.query(sql, new RowMapper<Seat>() {
					@Override
					public Seat mapRow(ResultSet result, int arg1) throws SQLException {
						Seat seat = new Seat();
						seat.setOwnerid(result.getString("ownerid"));
						seat.setAttendDeptId(result.getString("attendDeptId"));
						return seat;
					}
			
		});
		//给所有人发送座位图
		for (Seat seat : list) {
			SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
			waitNoflwJu.setReceiveUserId(seat.getOwnerid());//接受人id
			waitNoflwJu.setReceiveDeptId(seat.getAttendDeptId());
			//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
			//waitNoflwJu.setRolesNo("D103");//接受业务角色
			waitNoflwJu.setOpName("会议的座位通知");//操作类型
			
			waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+seat.getAttendDeptId()+"&userid="+seat.getOwnerid());//待办url  必填
			waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议的个人座位图");//待办显示标题
			waitNoflwJu.setTableId(notice.getId());//业务表id
			waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
			waitNoflwJu.setSourceId(notice.getId());//业务id
			waitNoflwJu.setAttr1("");//预留字段1
			waitNoflwJu.setAttr2("");//预留字段2
			waitNoflwJu.setAttr3("");//预留字段3
			sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议的个人座位图","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?attendDetpTyp=0&meetingNoticeId=" + notice.getId() + "&publishState=1&attendDeptId="+seat.getAttendDeptId()+"&userid="+seat.getOwnerid());
		
		}
	}

	//给所有参会的部门收发人员和收发人员发送待办（座位图）
	private void sendDeptDaiban(Map<String, Map<String, List<String>>> deptRoleUserMap, MeetingNoticeInfo notice,
			Map<String,MeetingNoticeBack> juDeptmap,Map<String,MeetingNoticeBack> chuDeptmap) {
		String atetenddeptName = notice.getAttendDeptJu();
		//给通知的所有局部门的部门收发人员发送待办（座位图）
		if(juDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= juDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//给该部门的部门收发人(处)发送待办
				String deptid = entry.getKey();
				MeetingNoticeBack backinfo = entry.getValue();
				List<String> chuSendAndRecUserid = deptRoleUserMap.get(deptid).get("C011");
				List<String> keSendAndRecUserid = deptRoleUserMap.get(deptid).get("D012");
				//给该部门的部门收发人(处)发送待办
				if(chuSendAndRecUserid!=null && chuSendAndRecUserid.size()>0) {
					for (String userid : chuSendAndRecUserid) {
						SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
						waitNoflwJu.setReceiveUserId(userid);//接受人id
						waitNoflwJu.setReceiveDeptId(deptid);
						//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
						//waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[0]);//接受业务角色
						waitNoflwJu.setOpName("会议的座位通知");//操作类型
						
						waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() + "&attendDeptId="+deptid+"&backId="+backinfo.getId());//待办url  必填
						waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议" +  backinfo.getAttendDept() + "上报的人员座位图和座位票");//待办显示标题
						waitNoflwJu.setTableId(backinfo.getId());//业务表id
						waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
						waitNoflwJu.setSourceId(backinfo.getId());//业务id
						waitNoflwJu.setAttr1("");//预留字段1
						waitNoflwJu.setAttr2("");//预留字段2
						waitNoflwJu.setAttr3("");//预留字段3
						sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" +  backinfo.getAttendDept()  + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() + "&attendDeptId="+deptid+"&backId="+backinfo.getId());
					}
				}
				
				//给该部门的部门收发人(科)发送待办
				if(keSendAndRecUserid!=null && keSendAndRecUserid.size()>0) {
					for (String userid : keSendAndRecUserid) {
						SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
						waitNoflwJu.setReceiveUserId(userid);//接受人id
						waitNoflwJu.setReceiveDeptId(deptid);
						//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
						//waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[1]);//接受业务角色
						waitNoflwJu.setOpName("会议的座位通知");//操作类型
						waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backinfo.getId());//待办url  必填
						waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议" + backinfo.getAttendDept()   + "上报的人员座位图和座位票");//待办显示标题
						waitNoflwJu.setTableId(backinfo.getId());//业务表id
						waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
						waitNoflwJu.setSourceId(backinfo.getId());//业务id
						waitNoflwJu.setAttr1("");//预留字段1
						waitNoflwJu.setAttr2("");//预留字段2
						waitNoflwJu.setAttr3("");//预留字段3
						sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议"+backinfo.getAttendDept() +"上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backinfo.getId());
					}
				}
				
			}
					
		}

	
		//给通知的所有处级部门的部门收发人员发送待办（座位图）
		if(chuDeptmap.size()>0) {
			Set<Entry<String,MeetingNoticeBack>> entrySet= chuDeptmap.entrySet();
			for (Entry<String, MeetingNoticeBack> entry : entrySet) {
				//给该部门的收发人(处)发送待办
				String deptid = entry.getKey();
				MeetingNoticeBack backInfo = entry.getValue();
				List<String> chuSendAndRecUserid = deptRoleUserMap.get(deptid).get("C010");
				List<String> keSendAndRecUserid = deptRoleUserMap.get(deptid).get("D011");
				if(chuSendAndRecUserid!=null && chuSendAndRecUserid.size()>0) {
					for (String userid : chuSendAndRecUserid) {
						SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
						waitNoflwJu.setReceiveUserId(userid);//接受人id
						waitNoflwJu.setReceiveDeptId(deptid);
						//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
						//waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[2]);//接受业务角色
						waitNoflwJu.setOpName("会议的座位通知");//操作类型
						
						waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());//待办url  必填
						waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议上" + backInfo.getAttendDept() + "报的人员座位图和座位票");//待办显示标题
						waitNoflwJu.setTableId(backInfo.getId());//业务表id
						waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
						waitNoflwJu.setSourceId(backInfo.getId());//业务id
						waitNoflwJu.setAttr1("");//预留字段1
						waitNoflwJu.setAttr2("");//预留字段2
						waitNoflwJu.setAttr3("");//预留字段3
						sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" + backInfo.getAttendDept() + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());
					}
				}
				
				//给该部门的收发人(科)发送待办
				if(keSendAndRecUserid!=null && keSendAndRecUserid.size()>0) {
					for (String userid : keSendAndRecUserid) {
						SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
						waitNoflwJu.setReceiveUserId(userid);//接受人id
						waitNoflwJu.setReceiveDeptId(deptid);
						//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
						//waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[3]);//接受业务角色
						waitNoflwJu.setOpName("会议的座位通知");//操作类型
						waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());//待办url  必填
						waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议" +  backInfo.getAttendDept() + "上报的人员座位图和座位票");//待办显示标题
						waitNoflwJu.setTableId(backInfo.getId());//业务表id
						waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
						waitNoflwJu.setSourceId(backInfo.getId());//业务id
						waitNoflwJu.setAttr1("");//预留字段1
						waitNoflwJu.setAttr2("");//预留字段2
						waitNoflwJu.setAttr3("");//预留字段3
						sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" +  backInfo.getAttendDept() + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());
					}
				}
			}
		}
		
	}
	
	//设置该部门的部门收发人员有哪些，转成map格式，放到deptRoleUserMap中
	private void setJuSendAndReceive1(Map<String, Map<String, List<String>>> deptRoleUserMap,
			List<Map<String, Object>> data, String deptid,String flg) {
		if(deptRoleUserMap.get(deptid)==null) {
			Map<String, List<String>> map= new HashMap<String, List<String>>();
			deptRoleUserMap.put(deptid, map);
		}
		String treeId = null;
		Iterator<Map<String, Object>>  iterator= data.iterator();
		while(iterator.hasNext()) {
			Map<String, Object> map = iterator.next();
			String deptid1 = map.get("deptid").toString();
			String temptreeId = map.get("treeid").toString();
			String roleNo = map.get("roleno").toString();
			String userid = map.get("userid").toString();
			if(deptid1.equals(deptid)) {
				treeId=temptreeId;
				if(StringUtils.isNotBlank(roleNo) && StringUtils.isNotBlank(userid) && !"null".equals(roleNo) && !"null".equals(userid)){
					if(deptRoleUserMap.get(deptid).get(roleNo)==null) {
						List<String> list= new ArrayList<String>();
						list.add(userid);
						deptRoleUserMap.get(deptid).put(roleNo, list);
					}else {
						deptRoleUserMap.get(deptid).get(roleNo).add(userid);
					}
					//iterator.remove();
				}
				
			}else {
				if(StringUtils.isNotBlank(treeId)&&temptreeId.contains(treeId) && !"null".equals(treeId)) {
					//该部门是其子部门
					String role1="C011";//部门收发人（处）
					String role2="D012";//部门收发人（科）
					if("0".equals(flg)) {
						 role1="C010";//收发人（处）
						 role2="D011";//收发人（科）
					}
					if(StringUtils.isNotBlank(roleNo) && !"null".equals(roleNo) &&(role1.equals(roleNo) || role2.equals(roleNo))) {
						if(deptRoleUserMap.get(deptid).get(roleNo)==null) {
							List<String> list= new ArrayList<String>();
							list.add(userid);
							deptRoleUserMap.get(deptid).put(roleNo, list);
						}else {
							deptRoleUserMap.get(deptid).get(roleNo).add(userid);
						}
						//iterator.remove();
					}else if(!StringUtils.isNotBlank(roleNo)) {
						//iterator.remove();
					}
					
				}
			}
		}
	}

	

	/**
	 * 查询该会议所有的座位票
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月25日 上午11:33:42
	 * @param meetingNoticeId
	 * @return
	 */
	@Override
	public JSONObject ticketList(String meetingNoticeId,String attendDeptType,String attentionItem,String attendDeptId,String userid) {
		JSONObject jsonObj = new JSONObject();
		//更新通知里的注意事项
			String selectSql = " select t.attention_item from HYGL_MEETING_NOTICE t where t.id='" + meetingNoticeId + "' ";
			attentionItem = jdbcTemplate.queryForObject(selectSql, String.class);
			jdbcTemplate.execute(selectSql);
		//获取座位信息
		StringBuilder sb = new StringBuilder("select t.owner,t.state,t.attend_dept attendDept,t.row1,t.col,t1.notice_name noticeName,t1.useTime from hygl_seat t left outer join ( ");
		sb.append(" select t.*,t1.id noticeId,t1.notice_name  from (");
		sb.append("  select t.apply_id,min(use_date || ' ' ||use_time) useTime from hygl_meetingroom_useinfo t where t.meetingroom_id='" + HyglConstants.multifunctionalMeetingRoomId + "' group by t.apply_id  ");
		sb.append(" ) t right outer join hygl_meeting_notice t1 on t.apply_id=t1.apply_id where t1.id is not null ");
		sb.append(" )t1 on t.noticeid=t1.noticeId ");
		sb.append(" where t.noticeid='" + meetingNoticeId + "' and t.state!=4 ");
		if(StringUtils.isNotBlank(attendDeptId)) {
			sb.append(" and t.ATTEND_DEPT_ID = '" + attendDeptId + "'");
		}
		if(StringUtils.isNotBlank(userid)) {
			sb.append(" and t.ownerid = '" + userid + "'");
		}
		if(attendDeptType!=null){
			if(attendDeptType.contains("0") && attendDeptType.contains("1")){
				sb.append("  and (t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "' or t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "' )");
				//sb.append("  or t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "'");
			}else if(attendDeptType.contains("0")){
				//上报的为用户所在局部门的用户
				sb.append("  and t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "'");
			}else if(attendDeptType.contains("1")){
				//上报的为用户所在处部门的用户
				sb.append("  and t.ATTEND_DEPT_ID = '" + UserUtil.getCruChushiId() + "'");
			}
		}
		
		sb.append(" order by t.row1,t.col ");
		List<Seat> list = jdbcTemplate.query(sb.toString(), new RowMapper<Seat>(){
			@Override
			public Seat mapRow(ResultSet result, int arg1) throws SQLException {
				Seat seat = new Seat();
				seat.setRow(result.getInt("row1"));
				seat.setCol(result.getInt("col"));
				seat.setNoticeName(result.getString("noticeName"));
				String startDate = result.getString("useTime");
				seat.setStartDate(startDate);
				seat.setMeetingRoom("多功能厅");
				seat.setOwner(result.getString("owner"));
				seat.setAttendDeptName(result.getString("attendDept"));
				seat.setState(result.getInt("state"));
				
				return seat;
			}
			
		});
		jsonObj.put("data", list);
		if(StringUtils.isNotBlank(attentionItem)){
			jsonObj.put("attentionItem", attentionItem);
		}else{
			jsonObj.put("attentionItem", "");
		}
		
		return jsonObj;
	}

	@Override
	public List<List<Seat>> getList1(String meetingNoticeId, String attendDeptId, String userid) {
		Map<Integer,List<Seat>> seatMap =new HashMap<Integer,List<Seat>>();//用于存放人员的座位，key表示哪一行，list表示此行的人员座位
		//获取该会议通知的所有人的座位
		List<Seat> seatList = getSeatsByNoticeId(meetingNoticeId,attendDeptId);
		changeData(seatList,seatMap);
		//获取页面的所有座位 
		List<List<Seat>> list = getAllSeat();
		//把会议通知人的座位放到对应位置
		setPostion1(list,seatMap,userid);
		return list;
	}

	private void setPostion1(List<List<Seat>> list, Map<Integer, List<Seat>> seatMap, String userid) {
		int row = 1;
		int col = 1;
		for (List<Seat> tempList : list) {
			List<Seat> rowSeat = seatMap.get(row);
			for (Seat seat : tempList) {
				///设置该座位的状态
				setSeatState1(seat,rowSeat,userid);
			}
			row++;
		}
		
	}
	
		//给所有参会的部门收发人员和处室收发人员发送待办（座位图）20190402李颖洁新增:按照角色发送待办
		private void sendDeptDaiban1(Map<String, Map<String, List<String>>> deptRoleUserMap, MeetingNoticeInfo notice,
				Map<String,MeetingNoticeBack> juDeptmap,Map<String,MeetingNoticeBack> chuDeptmap) {
			//给通知的所有局部门的部门收发人员发送待办（座位图）
			if(juDeptmap.size()>0) {
				Set<Entry<String,MeetingNoticeBack>> entrySet= juDeptmap.entrySet();
				for (Entry<String, MeetingNoticeBack> entry : entrySet) {
					String deptid = entry.getKey();
					MeetingNoticeBack backinfo = entry.getValue();
					//给该部门的部门收发人(处)发送待办
					SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
					waitNoflwJu.setReceiveDeptId(deptid);
					waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[0]);//接受业务角色
					waitNoflwJu.setOpName("会议的座位通知");//操作类型
					
					waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() + "&attendDeptId="+deptid+"&backId="+backinfo.getId());//待办url  必填
					waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议" +  backinfo.getAttendDept() + "上报的人员座位图和座位票");//待办显示标题
					waitNoflwJu.setTableId(backinfo.getId());//业务表id
					waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu.setSourceId(backinfo.getId());//业务id
					waitNoflwJu.setAttr1("");//预留字段1
					waitNoflwJu.setAttr2("");//预留字段2
					waitNoflwJu.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" +  backinfo.getAttendDept()  + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() + "&attendDeptId="+deptid+"&backId="+backinfo.getId());
			
					//给该部门的部门收发人(科)发送待办
					SysWaitNoflow waitNoflwJu1 = new SysWaitNoflow();
					waitNoflwJu1.setReceiveDeptId(deptid);
					waitNoflwJu1.setRolesNo(HyglConstants.REPORT_ROLE[1]);//接受业务角色
					waitNoflwJu1.setOpName("会议的座位通知");//操作类型
					waitNoflwJu1.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backinfo.getId());//待办url  必填
					waitNoflwJu1.setTitle("关于" + notice.getNoticeName() + "次会议" + backinfo.getAttendDept()   + "上报的人员座位图和座位票");//待办显示标题
					waitNoflwJu1.setTableId(backinfo.getId());//业务表id
					waitNoflwJu1.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu1.setSourceId(backinfo.getId());//业务id
					waitNoflwJu1.setAttr1("");//预留字段1
					waitNoflwJu1.setAttr2("");//预留字段2
					waitNoflwJu1.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu1, "会议的座位通知","关于" + notice.getNoticeName() + "次会议"+backinfo.getAttendDept() +"上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backinfo.getId());
					
				}
						
			}

		
			//给通知的所有处级部门的部门收发人员发送待办（座位图）
			if(chuDeptmap.size()>0) {
				Set<Entry<String,MeetingNoticeBack>> entrySet= chuDeptmap.entrySet();
				for (Entry<String, MeetingNoticeBack> entry : entrySet) {
					//给该处室收发人(处)发送待办
					String deptid = entry.getKey();
					MeetingNoticeBack backInfo = entry.getValue();
					SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
					waitNoflwJu.setReceiveDeptId(deptid);
					waitNoflwJu.setRolesNo(HyglConstants.REPORT_ROLE[2]);//接受业务角色
					waitNoflwJu.setOpName("会议的座位通知");//操作类型
					
					waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());//待办url  必填
					waitNoflwJu.setTitle("关于" + notice.getNoticeName() + "次会议上" + backInfo.getAttendDept() + "报的人员座位图和座位票");//待办显示标题
					waitNoflwJu.setTableId(backInfo.getId());//业务表id
					waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu.setSourceId(backInfo.getId());//业务id
					waitNoflwJu.setAttr1("");//预留字段1
					waitNoflwJu.setAttr2("");//预留字段2
					waitNoflwJu.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" + backInfo.getAttendDept() + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());
			
					//给该处室收发人(科)发送待办
					SysWaitNoflow waitNoflwJu1 = new SysWaitNoflow();
					waitNoflwJu1.setReceiveDeptId(deptid);
					waitNoflwJu1.setRolesNo(HyglConstants.REPORT_ROLE[3]);//接受业务角色
					waitNoflwJu1.setOpName("会议的座位通知");//操作类型
					waitNoflwJu1.setDaibanUrl("/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());//待办url  必填
					waitNoflwJu1.setTitle("关于" + notice.getNoticeName() + "次会议" +  backInfo.getAttendDept() + "上报的人员座位图和座位票");//待办显示标题
					waitNoflwJu1.setTableId(backInfo.getId());//业务表id
					waitNoflwJu1.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu1.setSourceId(backInfo.getId());//业务id
					waitNoflwJu1.setAttr1("");//预留字段1
					waitNoflwJu1.setAttr2("");//预留字段2
					waitNoflwJu1.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu1, "会议的座位通知","关于" + notice.getNoticeName() + "次会议" +  backInfo.getAttendDept() + "上报的人员座位图和座位票","/modules/zhbg/hygl/meetingNotice/arrangeSeat/daiban.html?flg=1&publishState=1&meetingNoticeId=" + notice.getId() +"&attendDeptId="+deptid+"&backId="+backInfo.getId());
				}
			}
			
		}

}
