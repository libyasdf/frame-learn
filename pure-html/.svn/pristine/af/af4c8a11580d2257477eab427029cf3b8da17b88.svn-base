package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity.MeetingRoomInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service.MeetingRoomInfoService;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.service.MeetingServiceService;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.service.ServiceDeptService;
import com.sinosoft.sinoep.modules.zhbg.hygl.common.HyglConstants;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.dao.MeetingApplyDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyDetailInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.dao.MeetingRoomUserInfoDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.service.MeetingServiceNoticeService;
import com.sinosoft.sinoep.user.entity.SysFlowUser;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议申请
 * TODO 
 * @author 冯超
 * @Date 2018年8月21日 上午11:13:14
 */
@Service
public class MeetingApplyServiceImpl implements MeetingApplyService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MeetingApplyDao meetingApplyDao;
	@Autowired
	MeetingRoomInfoService meetingRoomInfoService;
	@Autowired
	private MeetingServiceService meetingServiceService;
	@Autowired
	MeetingServiceNoticeService meetingServiceNoticeService ;
	@Autowired
	private SysWaitNoflowService sysWaitNoflowservice;
	@Autowired
	private ServiceDeptService serviceDeptService;
	@Autowired
	MeetingRoomUserInfoDao meetingUsedao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 保存
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:12:57
	 * @param meetingApplyInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public MeetingApplyInfo saveForm(MeetingApplyInfo meetingApplyInfo) throws Exception {
		meetingApplyInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String creUserId = UserUtil.getCruUserId();
		String creUserName = UserUtil.getCruUserName();
		String creDeptId = UserUtil.getCruDeptId();
		String creDeptName = UserUtil.getCruDeptName();
		
		String meetingRoomInfo = meetingApplyInfo.getMeetingRoomInfo();//获取设置的会议室信息
		if (StringUtils.isBlank(meetingApplyInfo.getId())) {
			//新建保存
			meetingApplyInfo.setCreTime(creTime);
			meetingApplyInfo.setCreUserId(creUserId);
			meetingApplyInfo.setCreUserName(creUserName);
			meetingApplyInfo.setCreDeptId(creDeptId);
			meetingApplyInfo.setCreDeptName(creDeptName);
			meetingApplyInfo.setCreChushiId(UserUtil.getCruChushiId());
			meetingApplyInfo.setCreChushiName(UserUtil.getCruChushiName());
			meetingApplyInfo.setCreJuId(UserUtil.getCruJuId());
			meetingApplyInfo.setCreJuName(UserUtil.getCruJuName());
			
			//临时数据
			//meetingApplyInfo.setMeetingroomId("402894826521dacb016521ff8d1f0000");
			//meetingApplyInfo.setMeetingroomName("三楼1310");
			//meetingApplyInfo.setMeetingStartDate("2018-08-23");
			//meetingApplyInfo.setMeetingStartTime("0");//0 上午 8:00 1下午 12:00
			//meetingApplyInfo.setMeetingEndDate("2018-08-23");
			//meetingApplyInfo.setMeetingEndTime("0");//0:上午 12:00 1下午18:00
			//拼接为一个新的日期范围，方便会议室预约情况的统计
			/*if("0".equals(meetingApplyInfo.getMeetingStartTime())){
				meetingApplyInfo.setMeetingStart(meetingApplyInfo.getMeetingStartDate()+" 08:00");
			}else{
				meetingApplyInfo.setMeetingStart(meetingApplyInfo.getMeetingStartDate()+" 12:00");
			}
			if("0".equals(meetingApplyInfo.getMeetingEndTime())){
				meetingApplyInfo.setMeetingEnd(meetingApplyInfo.getMeetingEndDate()+" 12:00");
			}else{
				meetingApplyInfo.setMeetingEnd(meetingApplyInfo.getMeetingEndDate()+" 18:00");
			}*/
			//meetingApplyInfo.setMeetingStart("2018-08-23 08:00");
			//meetingApplyInfo.setMeetingEnd("2018-08-23 12:00");
			
			
			meetingApplyInfo = meetingApplyDao.save(meetingApplyInfo);
			//updateSubFlag(meetingApplyInfo.getId(),"5");
		}else{
			//更新
			MeetingApplyInfo oldMeetingApplyInfo = meetingApplyDao.findOne(meetingApplyInfo.getId());
			//更新人数据
			oldMeetingApplyInfo.setUpdateTime(creTime);
			oldMeetingApplyInfo.setUpdateUserId(creUserId);
			oldMeetingApplyInfo.setUpdateUserName(creUserName);
			//业务数据
			oldMeetingApplyInfo.setTitle(meetingApplyInfo.getTitle());
			oldMeetingApplyInfo.setApplyTitle(meetingApplyInfo.getApplyTitle());
			oldMeetingApplyInfo.setMeetingType(meetingApplyInfo.getMeetingType());
			oldMeetingApplyInfo.setHostDeptId(meetingApplyInfo.getHostDeptId());
			oldMeetingApplyInfo.setHostDeptName(meetingApplyInfo.getHostDeptName());
			oldMeetingApplyInfo.setConvenor(meetingApplyInfo.getConvenor());
			oldMeetingApplyInfo.setMeetingTime(meetingApplyInfo.getMeetingTime());
			oldMeetingApplyInfo.setMeetingServices(meetingApplyInfo.getMeetingServices());
			oldMeetingApplyInfo.setMeetingServicesId(meetingApplyInfo.getMeetingServicesId());
			oldMeetingApplyInfo.setNoticeFlag(meetingApplyInfo.getNoticeFlag());
			oldMeetingApplyInfo.setRemark(meetingApplyInfo.getRemark());
		
			meetingApplyInfo = meetingApplyDao.save(oldMeetingApplyInfo);
		}
		//保存会议室信息
		String delSql = "delete from hygl_meetingroom_useinfo where apply_id='" + meetingApplyInfo.getId() + "' ";
		jdbcTemplate.execute(delSql);
		JSONArray jsonArry = JSONArray.fromObject(meetingRoomInfo);
		List<MeetingRoomUseInfo> list = new ArrayList<MeetingRoomUseInfo>();
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object) ;
			MeetingRoomUseInfo roomUse = new MeetingRoomUseInfo();
			
			roomUse.setUpdateTime(creTime);
			roomUse.setUpdateUserId(creUserId);
			roomUse.setUpdateUserName(creUserName);
			roomUse.setCreTime(creTime);
			roomUse.setCreUserId(creUserId);
			roomUse.setCreUserName(creUserName);
			roomUse.setCreDeptId(creDeptId);
			roomUse.setCreDeptName(creDeptName);
			roomUse.setCreChushiId(UserUtil.getCruChushiId());
			roomUse.setCreChushiName(UserUtil.getCruChushiName());
			roomUse.setCreJuId(UserUtil.getCruJuId());
			roomUse.setCreJuName(UserUtil.getCruJuName());
			roomUse.setMeetingroomId(jsonObj.getString("meetingRoomid"));
			roomUse.setMeetingroomName(jsonObj.getString("meetingroomName"));
			roomUse.setUseDate(jsonObj.getString("useDate"));
			roomUse.setUseTime(jsonObj.getString("useTime"));
			roomUse.setWeekDate(jsonObj.getString("weekDate"));
			roomUse.setApplyId(meetingApplyInfo.getId());
			list.add(roomUse);
		}
		if(list.size()>0) {
			meetingUsedao.save(list);
		}
		
		return meetingApplyInfo;
	}
	
	/**
	 * 删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:14:20
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String id)  throws Exception {
		String jpql = "update MeetingApplyInfo t set t.visible = ? where t.id = ?";
		return meetingApplyDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}

	/**
	 * 根据id获取数据
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:14:49
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public MeetingApplyInfo getById(String id) throws Exception {
		return meetingApplyDao.findOne(id);
	}

	/**
	 * 更新状态
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:15:59
	 * @param id
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	@Override
	public MeetingApplyInfo updateSubFlag(String id, String subflag) throws Exception {
		MeetingApplyInfo meetingApplyInfo = getById(id);
		meetingApplyInfo.setSubflag(subflag);
		meetingApplyInfo = meetingApplyDao.save(meetingApplyInfo);
		if ("5".equals(subflag)&&meetingApplyInfo.getMeetingServices()!=null&&!"".equals(meetingApplyInfo.getMeetingServices())){
			String [] meetingServicess = meetingApplyInfo.getMeetingServices().split(",");
			List<MeetingServiceInfo> templist = new ArrayList<MeetingServiceInfo>();
			for(int i=0;i<meetingServicess.length;i++){
				MeetingServiceInfo meetingServiceInfo = meetingServiceService.getById(meetingServicess[i]);
				MeetingServiceNotice meetingServiceNotice = new MeetingServiceNotice();
				meetingServiceNotice.setContactId(meetingServiceInfo.getResponsibleUserId());
				meetingServiceNotice.setMeetingroomApplyId(meetingApplyInfo.getId());
				meetingServiceNotice.setMeetingServiceId(meetingServiceInfo.getId());
				meetingServiceNotice.setMeetingServiceName(meetingServiceInfo.getMeetingService());
				meetingServiceNotice.setStatus("0");
				meetingServiceNotice.setVisible("1");
				meetingServiceNoticeService.saveForm(meetingServiceNotice);
				//serviceDeptService.getById(meetingServiceInfo.getServiceDeptId());
				meetingServiceInfo.setServiceDeptId(serviceDeptService.getById(meetingServiceInfo.getServiceDeptId()).getServiceDeptId());
				templist.add(meetingServiceInfo);
			}
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < templist.size() ; i++){
	            map.put(templist.get(i).getResponsibleUserId(), templist.get(i).getResponsibleDeptId());
			}
			String now = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
			for (Map.Entry<String, String> entry : map.entrySet()) { 
				  SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
					waitNoflwJu.setReceiveUserId(entry.getKey());//接受人id
					waitNoflwJu.setReceiveDeptId(entry.getValue());
					//waitNoflwJu.setReceiveUserName(entry.getValue());//接受人name
					//waitNoflwJu.setRolesNo("D103");//接受业务角色
					waitNoflwJu.setOpName("会务服务通知");//操作类型
					waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingServiceNotice/meetingServiceNoticeEditForm.html?flg=1");//待办url  必填
					waitNoflwJu.setTitle(now+meetingApplyInfo.getCreChushiName()+"会务服务申请");//待办显示标题
					waitNoflwJu.setTableId(meetingApplyInfo.getId());//业务表id
					waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu.setSourceId(meetingApplyInfo.getId());//业务id
					waitNoflwJu.setAttr1("");//预留字段1
					waitNoflwJu.setAttr2("");//预留字段2
					waitNoflwJu.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会务服务通知",meetingApplyInfo.getApplyTitle()+"需要提供会务服务","/modules/zhbg/hygl/meetingServiceNotice/meetingServiceNoticeEditForm.html?flg=1&id="+meetingApplyInfo.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
			}
		}
		return meetingApplyInfo;
	}
	
	/**
	 * 会议室申请列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月6日 下午4:51:41
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @param noticeFlag
	 * @param applyTitle
	 * @param serviceDeptName
	 * @param serviceDeptId
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String title,String startDate, String endDate, String subflag,String noticeFlag, String applyTitle, String serviceDeptName, String serviceDeptId) throws Exception {
		//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	querySql(sb,title,startDate,endDate,subflag,noticeFlag,applyTitle,serviceDeptName,serviceDeptId);
    	sb.append(")");
    	Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
    	//查询分页数据
		 String pageSql = pageSql(pageable,title,startDate,endDate,subflag,noticeFlag,applyTitle,serviceDeptName,serviceDeptId);
		List<MeetingApplyInfo> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<MeetingApplyInfo>(){
			@Override
			public MeetingApplyInfo mapRow(ResultSet result, int arg1) throws SQLException {
				
				MeetingApplyInfo info = new MeetingApplyInfo();
				info.setId(result.getString("id"));
				info.setApplyTitle(result.getString("applyTitle"));
				info.setMeetingRoomName(result.getString("meetingRomName"));
				info.setMeetingTime(result.getString("meetingTime"));
				info.setHostDeptName(result.getString("hostDeptName"));
				return info;
			}
			
		});
		return new PageImpl(total,listData);
	}
	private void querySql(StringBuilder sb, String title,String startDate, String endDate, String subflag,String noticeFlag, String applyTitle, String serviceDeptName, String serviceDeptId) {
		sb.append(" select t.id,t.apply_title applyTitle,t.meeting_time meetingTime,t.host_dept_name hostDeptName,t1.meetingRomName meetingRomName");
		//sb.append(" (select to_char(wm_concat(t1.meetingroom_name)) from hygl_meetingroom_useinfo t,hygl_meetingroom t1,hygl_meeting_apply t2 where t.meetingroom_id=t1.id and t.apply_id=t2.id) meetingRomName ");
		sb.append("  from hygl_meeting_apply t  ");
		sb.append(" left outer join ( ");
		sb.append(" select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRomName, t2.id from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM  t1, hygl_meeting_apply  t2 where t.meetingroom_id = t1.id  and t.apply_id = t2.id  group by t2.id ");
		sb.append(" )t1 on t.id=t1.id ");
		sb.append(" where t.cre_user_id = '" + UserUtil.getCruUserId() +"' and t.visible='1' and t.subflag = '" + subflag + "'");
		if (StringUtils.isNotBlank(title)) {
			sb.append(" and t.title like  '%" + title + "%'");
		}
		if (StringUtils.isNotBlank(noticeFlag)) {
			sb.append(" and t.notice_flag ='" + noticeFlag + "'");
		}
		if (StringUtils.isNotBlank(applyTitle)) {
			sb.append(" and t.apply_title like '%" + applyTitle + "%'");
		}
		if (StringUtils.isNotBlank(serviceDeptId)) {
			sb.append(" and t.host_dept_id='" + serviceDeptId + "'");
		}
		if (StringUtils.isNotBlank(startDate)) {
			sb.append(" and SUBSTR(t.meeting_time,23,10)>='" + startDate + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sb.append(" and SUBSTR(t.meeting_time,0,10)<='" + endDate + "'");
		}
		sb.append(" order by t.cre_time desc ");
		
		
		
	}
	private String pageSql(Pageable pageable,  String title,String startDate, String endDate, String subflag,String noticeFlag, String applyTitle, String serviceDeptName, String serviceDeptId) {
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,title,startDate,endDate,subflag,noticeFlag,applyTitle,serviceDeptName,serviceDeptId);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}
	/**
	 * 查询草稿列表
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月14日 下午5:11:01
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	public PageImpl getPageListDraft1(Pageable pageable, PageImpl pageImpl, String title,
			String startDate, String endDate, String subflag,String noticeFlag, String applyTitle, String serviceDeptName, String serviceDeptId) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from MeetingApplyInfo t ");
		querySql.append("	where t.creUserId = ? and t.subflag = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		para.add(UserUtil.getCruUserId());
		para.add(subflag);
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.title like ?");
			para.add("%"+title+"%");
		}
		if (StringUtils.isNotBlank(noticeFlag)) {
			querySql.append("   and t.noticeFlag = ?");
			para.add(noticeFlag);
		}
		if (StringUtils.isNotBlank(applyTitle)) {
			querySql.append("   and t.applyTitle like ?");
			para.add("%"+applyTitle+"%");
		}
		if (StringUtils.isNotBlank(serviceDeptId)) {
			querySql.append("   and t.hostDeptId = ?");
			para.add(serviceDeptId);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.meetingStartDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.meetingEndDate <= ?");
			para.add(endDate);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<MeetingApplyInfo> page = meetingApplyDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<MeetingApplyInfo> content = page.getContent();
        for (MeetingApplyInfo meetingApplyInfo : content) {
        	if (ConfigConsts.START_FLAG.equals(meetingApplyInfo.getSubflag())) {
        		meetingApplyInfo.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			}
		}
        pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)page.getTotalElements());
        return pageImpl;
	}
	
	/**
	 * 获取一周内会议的预约情况
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月23日 下午5:30:06
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws throws ParseException 
	 */
	@Override
	public List<MeetingApplyDetailInfo> getMeetingApplyDetail(String startDate, String endDate) throws ParseException{
		List<MeetingApplyDetailInfo> meetingApplyDetailInfoList = new ArrayList<MeetingApplyDetailInfo>();
		//改时间端的所有数据
		List<MeetingApplyInfo> meetingApplyInfoList = meetingApplyDao.getMeetingApplyInfoByDate(startDate, endDate);
		//所有的会议室
		MeetingRoomInfo meetRoomInfo=new MeetingRoomInfo();
		//从会议室预约情况进入 只显示可用的会议室
		meetRoomInfo.setCz("1");
		List<MeetingRoomInfo> meetingRoomInfoList = meetingRoomInfoService.getList(meetRoomInfo);
		// 所有的天
		List<String> DateList = DateUtil.getBetweenDates1(startDate, endDate);
		// 循环会议室
		for(MeetingRoomInfo meetingRoomInfo : meetingRoomInfoList){
			
			// 新建预约情况 
			MeetingApplyDetailInfo meetingApplyDetailInfo = new MeetingApplyDetailInfo();
			meetingApplyDetailInfo.setMeetingroomId(meetingRoomInfo.getId());// 会议室id
			meetingApplyDetailInfo.setMeetingroomName(meetingRoomInfo.getMeetingRoomName());// 会议室name
			meetingApplyDetailInfo.setMeetingPlace(meetingRoomInfo.getLocation());
			//上午
			meetingApplyDetailInfo.setMeetingTime("0");// 上午
			meetingApplyDetailInfo.setDetailOne("0");//可用
			meetingApplyDetailInfo.setDetailTwo("0");
			meetingApplyDetailInfo.setDetailThree("0");
			meetingApplyDetailInfo.setDetailFour("0");
			meetingApplyDetailInfo.setDetailFive("0");
			meetingApplyDetailInfo.setDetailSix("0");
			meetingApplyDetailInfo.setDetailSeven("0");
			
			// 循环时间
			for(int i=0;i<DateList.size();i++){
				//上午
				//meetingApplyDetailInfo.setMeetingDate(DateList.get(i));// 日期
				// 判断改日期上午有没有改数据
				for(MeetingApplyInfo meetingApplyInfo : meetingApplyInfoList){
					long start =  Long.valueOf(meetingApplyInfo.getMeetingStart().replaceAll("[-\\s:]",""));
					long end = Long.valueOf(meetingApplyInfo.getMeetingEnd().replaceAll("[-\\s:]",""));
					long thisDate = Long.valueOf((DateList.get(i)+" 09:00").replaceAll("[-\\s:]",""));
					if(thisDate <= end && thisDate >=start &&  "5".equals(meetingApplyInfo.getSubflag())/*&&meetingApplyInfo.getMeetingroomId().equals(meetingRoomInfo.getId())*/){
						if(i==0){
							meetingApplyDetailInfo.setDetailOne("2");// 占用
						}else if(i==1){
							meetingApplyDetailInfo.setDetailTwo("2");// 占用
						}else if(i==2){
							meetingApplyDetailInfo.setDetailThree("2");// 占用
						}else if(i==3){
							meetingApplyDetailInfo.setDetailFour("2");// 占用
						}else if(i==4){
							meetingApplyDetailInfo.setDetailFive("2");// 占用
						}else if(i==5){
							meetingApplyDetailInfo.setDetailSix("2");// 占用
						}else if(i==6){
							meetingApplyDetailInfo.setDetailSeven("2");// 占用
						}
						break;
					}
					if(thisDate <= end && thisDate >=start &&  "1".equals(meetingApplyInfo.getSubflag())/*&&meetingApplyInfo.getMeetingroomId().equals(meetingRoomInfo.getId())*/){
						if(i==0){
							meetingApplyDetailInfo.setDetailOne("1");// 已预约
						}else if(i==1){
							meetingApplyDetailInfo.setDetailTwo("1");// 已预约
						}else if(i==2){
							meetingApplyDetailInfo.setDetailThree("1");// 已预约
						}else if(i==3){
							meetingApplyDetailInfo.setDetailFour("1");// 已预约
						}else if(i==4){
							meetingApplyDetailInfo.setDetailFive("1");// 已预约
						}else if(i==5){
							meetingApplyDetailInfo.setDetailSix("1");// 已预约
						}else if(i==6){
							meetingApplyDetailInfo.setDetailSeven("1");// 已预约
						}
						break;
					}
				}
			}
			meetingApplyDetailInfoList.add(meetingApplyDetailInfo);
			
			
			// 新建预约情况 
			MeetingApplyDetailInfo meetingApplyDetailInfo1 = new MeetingApplyDetailInfo();
			meetingApplyDetailInfo1.setMeetingroomId(meetingRoomInfo.getId());// 会议室id
			meetingApplyDetailInfo1.setMeetingroomName(meetingRoomInfo.getMeetingRoomName());// 会议室name
			meetingApplyDetailInfo1.setMeetingPlace(meetingRoomInfo.getLocation());
			//下午
			meetingApplyDetailInfo1.setMeetingTime("1");// 下午
			meetingApplyDetailInfo1.setDetailOne("0");//可用
			meetingApplyDetailInfo1.setDetailTwo("0");
			meetingApplyDetailInfo1.setDetailThree("0");
			meetingApplyDetailInfo1.setDetailFour("0");
			meetingApplyDetailInfo1.setDetailFive("0");
			meetingApplyDetailInfo1.setDetailSix("0");
			meetingApplyDetailInfo1.setDetailSeven("0");
			
			// 循环时间
			for(int i=0;i<DateList.size();i++){
				//上午
				//meetingApplyDetailInfo.setMeetingDate(DateList.get(i));// 日期
				// 判断改日期上午有没有改数据
				for(MeetingApplyInfo meetingApplyInfo : meetingApplyInfoList){
					long start =  Long.valueOf(meetingApplyInfo.getMeetingStart().replaceAll("[-\\s:]",""));
					long end = Long.valueOf(meetingApplyInfo.getMeetingEnd().replaceAll("[-\\s:]",""));
					long thisDate = Long.valueOf((DateList.get(i)+" 13:00").replaceAll("[-\\s:]",""));
					if(thisDate <= end && thisDate >=start &&  "5".equals(meetingApplyInfo.getSubflag())/*&&meetingApplyInfo.getMeetingroomId().equals(meetingRoomInfo.getId())*/){
						if(i==0){
							meetingApplyDetailInfo1.setDetailOne("2");// 占用
						}else if(i==1){
							meetingApplyDetailInfo1.setDetailTwo("2");// 占用
						}else if(i==2){
							meetingApplyDetailInfo1.setDetailThree("2");// 占用
						}else if(i==3){
							meetingApplyDetailInfo1.setDetailFour("2");// 占用
						}else if(i==4){
							meetingApplyDetailInfo1.setDetailFive("2");// 占用
						}else if(i==5){
							meetingApplyDetailInfo1.setDetailSix("2");// 占用
						}else if(i==6){
							meetingApplyDetailInfo1.setDetailSeven("2");// 占用
						}
						break;
					}
					if(thisDate <= end && thisDate >=start &&  "1".equals(meetingApplyInfo.getSubflag())/*&&meetingApplyInfo.getMeetingroomId().equals(meetingRoomInfo.getId())*/){
						if(i==0){
							meetingApplyDetailInfo1.setDetailOne("1");// 已预约
						}else if(i==1){
							meetingApplyDetailInfo1.setDetailTwo("1");// 已预约
						}else if(i==2){
							meetingApplyDetailInfo1.setDetailThree("1");// 已预约
						}else if(i==3){
							meetingApplyDetailInfo1.setDetailFour("1");// 已预约
						}else if(i==4){
							meetingApplyDetailInfo1.setDetailFive("1");// 已预约
						}else if(i==5){
							meetingApplyDetailInfo1.setDetailSix("1");// 已预约
						}else if(i==6){
							meetingApplyDetailInfo1.setDetailSeven("1");// 已预约
						}
						break;
					}
				}
			}
			meetingApplyDetailInfoList.add(meetingApplyDetailInfo1);
		}
		return meetingApplyDetailInfoList;
	}

	@Override
	public Map<String,Object> getCount(String flag,String startDate, String endDate, String subflag) throws ParseException{
		startDate = startDate.trim();
		endDate = endDate.trim();
		//按照会议类型获取会议次数
		StringBuilder sb = new StringBuilder("select count(meeting_type) meetCount ,t.meeting_type meetType from hygl_meeting_apply t " );	
		sb.append(" where  t.visible = '"+CommonConstants.VISIBLE[1]+"'  and t.subflag = "+"'"+subflag+"'");
		if("0".equals(flag)) {
			//按年
			//sb.append("  and substr(t.meeting_time, 0, 4) >= '" + startDate + "' and  substr(t.meeting_time,23,4) < ='" + endDate + "'" );
			sb.append("  and substr(t.meeting_time, 0, 4) >= '" + startDate + "' and  substr(t.meeting_time,0,4) < ='" + endDate + "'" );

		}else if("1".equals(flag)) {
			//按月
			//sb.append("  and substr(t.meeting_time, 0, 7) >= '" + startDate + "' and  substr(t.meeting_time,23,7) < ='" + endDate + "'" );
			sb.append("  and substr(t.meeting_time, 0, 7) >= '" + startDate + "' and  substr(t.meeting_time,0,7) < ='" + endDate + "'" );

		}else {
			//按周
			sb.append("  and substr(t.meeting_time, 0, 10) >= '" + startDate + "' and  substr(t.meeting_time,0,10) < ='" + endDate + "'" );

			//sb.append("  and substr(t.meeting_time, 0, 10) >= '" + startDate + "' and  substr(t.meeting_time,23,10) < ='" + endDate + "'" );
		}
		if(UserUtil.getCruUserRoleNo().indexOf("D101")<0){
			sb.append("  and t.cre_ju_id = '"+UserUtil.getCruJuId()+"'");
		}
		sb.append("  group by t.meeting_type order by t.meeting_type asc");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		
		//按照月份获取会议次数
		List<String> listMonth = getMonth(startDate,endDate,flag);
	
		StringBuilder sb1 = new StringBuilder("select count(id) meetCount   " );
		if("0".equals(flag)) {
			//按年
			sb1.append("  ,substr(t.meeting_time, 0, 4) meetType " );
		}else if("1".equals(flag)) {
			//按月
			sb1.append("  ,substr(t.meeting_time, 0, 7) meetType " );
		}else {
			//按周
			sb1.append("  ,substr(t.meeting_time, 0, 10) meetType" );
		}
		sb1.append(" from hygl_meeting_apply t where   t.visible = '"+CommonConstants.VISIBLE[1]+"'  and t.subflag = "+"'"+subflag+"'");
		if("0".equals(flag)) {
			//按年
			sb1.append("  and substr(t.meeting_time, 0, 4) >= '" + startDate + "' and  substr(t.meeting_time,0,4) < ='" + endDate + "'" );
		}else if("1".equals(flag)) {
			//按月
			sb1.append("  and substr(t.meeting_time, 0, 7) >= '" + startDate + "' and  substr(t.meeting_time,0,7) < ='" + endDate + "'" );
		}else {
			//按周
			sb1.append("  and substr(t.meeting_time, 0, 10) >= '" + startDate + "' and  substr(t.meeting_time,0,10) < ='" + endDate + "'" );
		}
	
		if(UserUtil.getCruUserRoleNo().indexOf("D101")<0){
			sb1.append("  and t.cre_ju_id = '"+UserUtil.getCruJuId()+"'");
		}
		sb1.append("  group by ");
		if("0".equals(flag)) {
			//按年
			sb1.append("  substr(t.meeting_time, 0, 4) order by substr(t.meeting_time, 0, 4) asc " );
		}else if("1".equals(flag)) {
			//按月
			sb1.append("  substr(t.meeting_time, 0, 7) order by substr(t.meeting_time, 0, 7) asc " );
		}else {
			//按周
			sb1.append("  substr(t.meeting_time, 0, 10) order by substr(t.meeting_time, 0, 10) asc " );
		}
		List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sb1.toString());
		
		//按照主办单位获取会议次数
		StringBuilder sb2 = new StringBuilder("select count(id) meetCount ,t.host_dept_name meetType from hygl_meeting_apply t " );
		sb2.append("  where  t.visible = '"+CommonConstants.VISIBLE[1]+"'  and t.subflag = "+"'"+subflag+"'");
		if("0".equals(flag)) {
			//按年
			sb2.append("  and substr(t.meeting_time, 0, 4) >= '" + startDate + "' and  substr(t.meeting_time,0,4) < ='" + endDate + "'" );
		}else if("1".equals(flag)) {
			//按月
			sb2.append("  and substr(t.meeting_time, 0, 7) >= '" + startDate + "' and  substr(t.meeting_time,0,7) < ='" + endDate + "'" );
		}else {
			//按周
			sb2.append("  and substr(t.meeting_time, 0, 10) >= '" + startDate + "' and  substr(t.meeting_time,0,10) < ='" + endDate + "'" );
		}
		if(UserUtil.getCruUserRoleNo().indexOf("D101")<0){
			sb2.append("  and t.cre_ju_id = '"+UserUtil.getCruJuId()+"'");
		}
		sb2.append("  group by t.host_dept_name");
		List<Map<String,Object>> list2 = jdbcTemplate.queryForList(sb2.toString());
		
		//按照会议室获取会议次数
		StringBuilder sb3 = new StringBuilder(" select t.meetCount,t1.meetingroom_name meetType from ( " );
		sb3.append(" select count(t.id) meetCount, t.meetingroom_id meetingroomId from hygl_meetingroom_useinfo t  ");
		sb3.append("   left outer join hygl_meeting_apply t1  on t.apply_id = t1.id ");
		//StringBuilder sb3 = new StringBuilder("select count(id) meetCount ,t.meetingroom_name meetType from hygl_meeting_apply t " );
		sb3.append("  where  t1.visible = '"+CommonConstants.VISIBLE[1]+"'  and t1.subflag = "+"'"+subflag+"'");
		if("0".equals(flag)) {
			//按年
			sb3.append("  and substr(t1.meeting_time, 0, 4) >= '" + startDate + "' and  substr(t1.meeting_time,0,4) < ='" + endDate + "'" );
		}else if("1".equals(flag)) {
			//按月
			sb3.append("  and substr(t1.meeting_time, 0, 7) >= '" + startDate + "' and  substr(t1.meeting_time,0,7) < ='" + endDate + "'" );
		}else {
			//按周
			sb3.append("  and substr(t1.meeting_time, 0, 10) >= '" + startDate + "' and  substr(t1.meeting_time,0,10) < ='" + endDate + "'" );
		}
		if(UserUtil.getCruUserRoleNo().indexOf("D101")<0){
			sb3.append("  and t.cre_ju_id = '"+UserUtil.getCruJuId()+"'");
		}
		sb3.append("  group by t.meetingroom_id  ");
		sb3.append(" )t ");
		sb3.append(" left outer join hygl_meetingroom t1 on t.meetingroomId=t1.id ");
		List<Map<String,Object>> list3 = jdbcTemplate.queryForList(sb3.toString());
		List<Map<String,Object>> listTemp = new ArrayList<Map<String,Object>>();
		if(!"2".equals(flag)) {
			for(Map<String,Object> map : list1){
				if(listMonth.contains(map.get("MEETTYPE"))){
					listMonth.remove(listMonth.indexOf(map.get("MEETTYPE")));
					listTemp.add(map);
				}
			}
			for(String str : listMonth){
				Map<String,Object> tempMap = new HashMap<String,Object>();
				tempMap.put("MEETCOUNT", 0);
				tempMap.put("MEETTYPE", str);
				listTemp.add(tempMap);	
			}
		}else {
			setWeekData(startDate,endDate,listTemp,list1,listMonth);
		}
		
		//将list按照月份进行排序，测试环境出问题
		//System.out.println("=============++++++++++");
		//System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		/*Map<String,Object> tempMap1 = new HashMap<String,Object>();
		for(int i=0;i<listTemp.size()-1;i++){
			 for(int j=0;j<listTemp.size()-1-i;j++){ 
				 if(listTemp.get(j).get("MEETTYPE").toString().compareTo(listTemp.get(j+1).get("MEETTYPE").toString())>0){
					 tempMap1 = listTemp.get(j+1);
					 listTemp.get(j+1) = listTemp.get(j);
					 listTemp.get(j) = tempMap1;
				 }
				 
			}
		}*/

		/* try {
			Collections.sort(listTemp, new Comparator<Map<String, Object>>(){
				
				 	@Override
			       public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
				 		String name1 = (String)o1.get("MEETTYPE");//name1是从你list里面拿出来的一个  
				 		String name2 = (String)o2.get("MEETTYPE"); //name2是从你list里面拿出来的第二个name  
				 		System.out.println("=============-----------------");
				 		System.out.println(name1.compareTo(name2));
				 		return name1.compareTo(name2);  
				 		
				  }  
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		 //System.out.println("=============}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//listTemp = SortUtil.getMonthsSorted(listTemp);
		Map<String,Object> meetMap = new HashMap<String,Object>();
		meetMap.put("meetmonth", listTemp);
		meetMap.put("meettype", list);
		meetMap.put("meetdept", list2);
		meetMap.put("meetroom", list3);
		return meetMap;
	}
	
	//设置周数据
	private void setWeekData(String startDate, String endDate, List<Map<String, Object>> listTemp,List<Map<String,Object>> data,List<String> listMonth) throws ParseException {
		//把list转成map格式，map中的key为哪天，value为个数
		Map<String,Integer> mapData =changeToMap(data);
		//根据开始日期和结束日期，划分出每周的时间范围
		//List<String> weekDays = divide(startDate,endDate);
		//统计每周的会议室的预约次数
		for (String week : listMonth) {
			tongjiWeekCount(week,listTemp,mapData);
		}
	}
	//把list里的数据转成map格式
	private Map<String, Integer> changeToMap(List<Map<String, Object>> data) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Map<String, Object> tempmap : data) {
			String MEETTYPE = tempmap.get("MEETTYPE").toString();
			Integer MEETCOUNT = Integer.valueOf(tempmap.get("MEETCOUNT").toString());
			map.put(MEETTYPE, MEETCOUNT);
		}
		return map;
	}

	//统计每周会议室的预约次数
	private void tongjiWeekCount(String week, List<Map<String, Object>> listTemp, Map<String,Integer> mapData) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] tempDate = week.split("~");
		String startDate = tempDate[0];
		String endDate = tempDate[1];
		Calendar calendar = Calendar.getInstance();
		int count=0;
		while(startDate.compareTo(endDate)<=0) {
			calendar.setTime(sdf.parse(startDate));
			//统计该天的会议室预约次数
			if(mapData.get(startDate)!=null) {
				count=count + mapData.get(startDate);
			}
			
			calendar.add(calendar.DAY_OF_MONTH, 1);
			startDate = sdf.format(calendar.getTime());
		}
		map.put("MEETTYPE", week);
		map.put("MEETCOUNT", count);
		listTemp.add(map);
	}

	//根据开始日期和结束日期，划分出每周的时间范围
	private List<String> divide(String startDate, String endDate) throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObj = DateUtil.getByDate(startDate);
		String weekEndDate =jsonObj.getString("endDate");
		Calendar endcalendar = Calendar.getInstance();
		Calendar startcalendar = Calendar.getInstance();
		if(weekEndDate.compareTo(endDate)>=0) {
			//跟开始日期在同一周
			list.add(startDate + "~" + endDate);
		}else {
			int index=0;
			while(weekEndDate.compareTo(endDate)<0) {
				list.add(startDate + "~" + weekEndDate);
				endcalendar.setTime(sdf.parse(weekEndDate));
				 endcalendar.add(endcalendar.DAY_OF_MONTH, 1);
				 startDate = sdf.format(endcalendar.getTime());
				endcalendar.add(endcalendar.DAY_OF_MONTH, 6);
				weekEndDate = sdf.format(endcalendar.getTime());
			}
			if(weekEndDate.compareTo(endDate)>=0) {
				//跟开始日期在同一周
				list.add(startDate + "~" + endDate);
			}
		}
		
		return list;
	}

	public List<String> getMonth(String beginDate,String endDate,String flg) throws ParseException{
		List<String> months = new ArrayList<String>();
		String patter = "yyyy-MM";
		if("2".equals(flg)) {
			//按周 
			//根据开始日期和结束日期按周划分
			months= divide(beginDate,endDate);
			//accordingWeek(beginDate,endDate,months);
		}
		if("0".equals(flg) || "1".equals(flg)) {
			if("0".equals(flg)) {
				patter = "yyyy";
			}
			 SimpleDateFormat sdf = new SimpleDateFormat(patter);  
		     Calendar cal = Calendar.getInstance(); 
		     Calendar calend = Calendar.getInstance();
				cal.setTime(sdf.parse(beginDate));
				calend.setTime(sdf.parse(endDate));  
		     while (cal.before(calend)) {
		    	 months.add(sdf.format(cal.getTime()));
		    	 if("0".equals(flg)) {
		    		 cal.add(Calendar.YEAR, 1);
		 		}else {
		 			cal.add(Calendar.MONTH, 1);
		 		}
		         
		     }
		     months.add(sdf.format(calend.getTime()));
		}
		/*if("0".equals(flg)) {
			//按年
			patter = "yyyy";
		}else if("1".equals(flg)) {
			//按月
			 SimpleDateFormat sdf = new SimpleDateFormat(patter);  
		     Calendar cal = Calendar.getInstance(); 
		     Calendar calend = Calendar.getInstance();
				cal.setTime(sdf.parse(beginDate));
				calend.setTime(sdf.parse(endDate));  
		     while (cal.before(calend)) {
		    	 months.add(sdf.format(cal.getTime()));
		    	 if("0".equals(flg)) {
		    		 cal.add(Calendar.YEAR, 1);
		 		}else {
		 			cal.add(Calendar.MONTH, 1);
		 		}
		         
		     }
		     months.add(sdf.format(calend.getTime()));
		}else {
			//按周 
			//根据开始日期和结束日期按周划分
			accordingWeek(beginDate,endDate,months);
		}*/
	    
	     return months;
	}
	//根据开始日期和结束日期，划分出每周的一个时间段
	private void accordingWeek(String beginDate, String endDate, List<String> months) throws ParseException {
		JSONObject jsonObj = DateUtil.getByDate(beginDate);
		String weekEnd = jsonObj.getString("endDate");
		if(endDate.compareTo(weekEnd)<1) {
			months.add(beginDate + "~" + weekEnd);
		}
		
	}

	@Override
	public PageImpl getNoticeList(Pageable pageable, PageImpl pageImpl, String title,
			String startDate, String endDate, String subflag,String noticeFlag, String meetingRoom, String serviceDeptName, String serviceDeptId) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from MeetingApplyInfo t ");
		querySql.append("	where t.creUserId = ? and t.subflag = ? and t.id not in ( select n.applyId from MeetingNoticeInfo n where n.applyId is not null) and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		para.add(UserUtil.getCruUserId());
		para.add(subflag);
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.title like ?");
			para.add("%"+title+"%");
		}
		if (StringUtils.isNotBlank(noticeFlag)) {
			querySql.append("   and t.noticeFlag = ?");
			para.add(noticeFlag);
		}
		if (StringUtils.isNotBlank(meetingRoom)) {
			querySql.append("   and t.applyTitle like ?");
			para.add("%"+meetingRoom+"%");
		}
		if (StringUtils.isNotBlank(serviceDeptId)) {
			querySql.append("   and t.hostDeptId = ?");
			para.add(serviceDeptId);
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("   and substr(t.meetingTime,0,10) <= ? and substr(t.meetingTime,23,10) >= ?");
			para.add(endDate);
			para.add(startDate);
		}
		/*if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.meetingEndDate <= ?");
			para.add(endDate);
		}*/
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<MeetingApplyInfo> page = meetingApplyDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<MeetingApplyInfo> content = page.getContent();
        for (MeetingApplyInfo meetingApplyInfo : content) {
        	if (ConfigConsts.START_FLAG.equals(meetingApplyInfo.getSubflag())) {
        		meetingApplyInfo.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			}
		}
        pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)page.getTotalElements());
        return pageImpl;
	}

	@Override
	public List<MeetingApplyInfo> getPageListToSign(String startDate, String endDate) {
		log.info("进入getPageListToSign。。。");
		Pageable pageable = new PageRequest(0, 50);
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from MeetingApplyInfo t ");
		querySql.append("where t.meetingroomName = '多功能厅' and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.meetingStart <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.meetingEnd >= ?");
			para.add(startDate);
		}
		//拼接排序语句
		querySql.append("  order by t.creTime desc ");
		log.info("querySql: "+querySql);
        Page<MeetingApplyInfo> page = meetingApplyDao.query(querySql.toString(), pageable,para.toArray());
        List<MeetingApplyInfo> content = page.getContent();
        return content;
	}

	@Override
	public void save(MeetingApplyInfo meetingApplyInfo) {
		meetingApplyDao.save(meetingApplyInfo);
		
	}

	@Override
	public MeetingApplyInfo removedByMeetingAdmin(String id, String ifRemovedByadmin) throws Exception {
		MeetingApplyInfo meetingApplyInfo = getById(id);
		meetingApplyInfo.setIfRemovedByadmin(ifRemovedByadmin);
		meetingApplyInfo = meetingApplyDao.save(meetingApplyInfo);
		return meetingApplyInfo;
	}
	
	/**
	 * 检查会务服务的负责人是否已不具备"会务服务人员"
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年4月11日 下午5:55:59
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<MeetingServiceInfo> checkServiceInfo(String meetingServiceIds) throws Exception {
		//查询此次会议申请使用哪些服务（主要是服务id和服务的负责人id)
		
		List<MeetingServiceInfo> list=new ArrayList<MeetingServiceInfo>();
		final StringBuilder responsibleUserIds = new StringBuilder();
		if (StringUtils.isNotBlank(meetingServiceIds)){
			StringBuilder sb = new StringBuilder("select t.id,t.responsible_user_id responsibleUserId,t.meeting_service meetingService from HYGL_MEETING_SERVICE t where t.id in(" + CommonUtils.commomSplit(meetingServiceIds) + ")");
			list = jdbcTemplate.query(sb.toString(), new RowMapper<MeetingServiceInfo>() {
				@Override
				public MeetingServiceInfo mapRow(ResultSet result, int arg1) throws SQLException {
					String responsibleUserId = result.getString("responsibleUserId");
					responsibleUserIds.append(responsibleUserId+",");
					MeetingServiceInfo serviceInfo = new MeetingServiceInfo();
					serviceInfo.setId(result.getString("id"));
					serviceInfo.setResponsibleUserId(responsibleUserId);
					serviceInfo.setMeetingService(result.getString("meetingService"));
					return serviceInfo;
				}
			});
		}
		if(responsibleUserIds.length()>0) {
			responsibleUserIds.deleteCharAt(responsibleUserIds.length()-1);
		}
		//查询服务负责人的详细信息
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(responsibleUserIds.toString());
		Set<Entry<String,SysFlowUserVo>> entrySet = map.entrySet();
		for (Entry<String, SysFlowUserVo> entry : entrySet) {
			String userId = entry.getKey();
			SysFlowUserVo userVo = entry.getValue();
			String userRole = userVo.getUserRoleNO();
			if(StringUtils.isNotBlank(userRole) && userRole.contains(HyglConstants.SERVICE_ROLE)) {
				//该用户是会务服务管理员，便把由该用户负责的服务除去
				deleteFromList(list,userId);
			}
		}
		return list;
	}
	//把由该用户提供的服务除去（表示可以正常提供）
	private void deleteFromList(List<MeetingServiceInfo> list, String userId) {
		if(list!=null) {
			Iterator<MeetingServiceInfo> iterator = list.iterator();
			while(iterator.hasNext()) {
				MeetingServiceInfo meetingServiceInfo = iterator.next();
				String responsibleUserId = meetingServiceInfo.getResponsibleUserId();
				if(userId.equals(responsibleUserId)) {
					iterator.remove();
				}
			}
			
		}
		
	}
	
}
