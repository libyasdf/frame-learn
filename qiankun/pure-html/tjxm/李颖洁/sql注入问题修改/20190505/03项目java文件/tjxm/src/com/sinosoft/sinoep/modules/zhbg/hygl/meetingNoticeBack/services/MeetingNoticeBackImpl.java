package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.zhbg.hygl.common.HyglConstants;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.dao.MeetingNoticeBackDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.CountNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.service.MeetingServiceNoticeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会议通知反馈Service
 * TODO 
 * @author 张建明
 * @Date 2018年7月19日 上午10:40:02
 */
@Service
public class MeetingNoticeBackImpl implements MeetingNoticeBackService{

		@Autowired
		private SysWaitNoflowService sysWaitNoflowservice;
		@Autowired
		private MeetingNoticeBackDao meetingNoticeBackDao;
		@Autowired
		private JdbcTemplate jdbcTemplate;
		@Autowired
		private MeetingServiceNoticeService meetingServiceNoticeService;
		private NotityService notityService = (NotityService) SpringBeanUtils.getBean("notityService");
		@Autowired
		private MeetingApplyService meetingApplyService;
		/**
		 * 保存
		 * TODO 
		 * @author 张建明
		 * @Date 2018年7月23日 下午5:28:22
		 * @param meetingNoticeBack
		 * @return
		 */
		@Override
		public MeetingNoticeBack saveForm(MeetingNoticeBack meetingNoticeBack) {
			meetingNoticeBack.setVisible(CommonConstants.VISIBLE[1]);
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			// 创建人
			String userId = UserUtil.getCruUserId();
			String userName = UserUtil.getCruUserName();
			try {
				if (StringUtils.isBlank(meetingNoticeBack.getId())) {
					// 创建人部门
					String creDeptId = UserUtil.getCruDeptId();
					String creDeptName = UserUtil.getCruDeptName();
					// 创建人处室
					String creChushiId = UserUtil.getCruChushiId();
					String creChushiName = UserUtil.getCruChushiName();
					// 创建人二级局
					String creJuId = UserUtil.getCruJuId();
					String creJuName = UserUtil.getCruJuName();

					meetingNoticeBack.setCreTime(creTime);
					meetingNoticeBack.setCreUserId(userId);
					meetingNoticeBack.setCreUserName(userName);
					meetingNoticeBack.setCreDeptId(creDeptId);
					meetingNoticeBack.setCreDeptName(creDeptName);
					meetingNoticeBack.setCreChuShiId(creChushiId);
					meetingNoticeBack.setCreChuShiName(creChushiName);
					meetingNoticeBack.setCreJuId(creJuId);
					meetingNoticeBack.setCreJuName(creJuName);
					// 更新最后修改记录
					meetingNoticeBack.setUpdateTime(creTime);
					meetingNoticeBack.setUpdateUserId(userId);
					meetingNoticeBack.setUpdateUserName(userName);
					meetingNoticeBack = meetingNoticeBackDao.save(meetingNoticeBack);
				} else {
					MeetingNoticeBack oldNoticeBack = meetingNoticeBackDao.findOne(meetingNoticeBack.getId());
					//oldNoticeBack.setIsFankui(meetingNoticeBack.getIsFankui());
					//oldNoticeBack.setFankuiType(meetingNoticeBack.getFankuiType());
					oldNoticeBack.setFeedbackTime(meetingNoticeBack.getFeedbackTime());
					oldNoticeBack.setAttendPersonId(meetingNoticeBack.getAttendPersonId());
					oldNoticeBack.setAttendPersonName(meetingNoticeBack.getAttendPersonName());
					oldNoticeBack.setAttendPersonNum(meetingNoticeBack.getAttendPersonNum());
					oldNoticeBack.setRemark(meetingNoticeBack.getRemark());
					oldNoticeBack.setState(meetingNoticeBack.getState());
					oldNoticeBack.setAttendDeptType(meetingNoticeBack.getAttendDeptType());
					oldNoticeBack.setAttendPersonChuId(meetingNoticeBack.getAttendPersonChuId());
					oldNoticeBack.setAttendPersonChuName(meetingNoticeBack.getAttendPersonChuName());
					oldNoticeBack.setAttendPersonLeaveName(meetingNoticeBack.getAttendPersonLeaveName());
					oldNoticeBack.setAttendPersonLeaveId(meetingNoticeBack.getAttendPersonLeaveId());
					oldNoticeBack.setNotAttendPersonChuName(meetingNoticeBack.getNotAttendPersonChuName());
					oldNoticeBack.setNotAttendPersonChuId(meetingNoticeBack.getNotAttendPersonChuId());
					oldNoticeBack.setNotAttendPersonLeaveName(meetingNoticeBack.getNotAttendPersonLeaveName());
					oldNoticeBack.setNotAttendPersonLeaveId(meetingNoticeBack.getNotAttendPersonLeaveId());
					oldNoticeBack.setNotAttendPersonName(meetingNoticeBack.getNotAttendPersonName());
					oldNoticeBack.setNotAttendPersonId(meetingNoticeBack.getNotAttendPersonId());
					oldNoticeBack.setShouldAttendNum(meetingNoticeBack.getShouldAttendNum());
					oldNoticeBack.setRealAttendNum(meetingNoticeBack.getRealAttendNum());
					// 更新最后修改记录
					oldNoticeBack.setUpdateTime(creTime);
					oldNoticeBack.setUpdateUserId(userId);
					oldNoticeBack.setUpdateUserName(userName);
					meetingNoticeBackDao.save(oldNoticeBack);
					if("1".equals(oldNoticeBack.getState()) && "0".equals(oldNoticeBack.getAttendDeptType())){
						MeetingApplyInfo meetingApplyInfo= meetingApplyService.getById(oldNoticeBack.getMeetingApplyId());	
						oldNoticeBack.setMeetingName(meetingApplyInfo.getApplyTitle());
						sysWaitNoflowservice.deleteWaitNoflow("",oldNoticeBack.getId(),oldNoticeBack.getAttendDeptId(),"",HyglConstants.REPORT_ROLE[0]);
						sysWaitNoflowservice.deleteWaitNoflow("",oldNoticeBack.getId(),oldNoticeBack.getAttendDeptId(),"",HyglConstants.REPORT_ROLE[1]);
						deleteMessage(oldNoticeBack);
					}else if("1".equals(oldNoticeBack.getState()) && "1".equals(oldNoticeBack.getAttendDeptType())){
						MeetingApplyInfo meetingApplyInfo= meetingApplyService.getById(oldNoticeBack.getMeetingApplyId());	
						oldNoticeBack.setMeetingName(meetingApplyInfo.getApplyTitle());
						sysWaitNoflowservice.deleteWaitNoflow("",oldNoticeBack.getId(),oldNoticeBack.getAttendDeptId(),"",HyglConstants.REPORT_ROLE[2]);
						sysWaitNoflowservice.deleteWaitNoflow("",oldNoticeBack.getId(),oldNoticeBack.getAttendDeptId(),"",HyglConstants.REPORT_ROLE[3]);
						deleteMessage(oldNoticeBack);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return meetingNoticeBack;
		}

		public JSONObject deleteMessage(MeetingNoticeBack meetingNoticeBack){
			JSONObject json = new JSONObject();
			json.put("flag", "0");
			try {
				NotityMessage notityMessage = new NotityMessage();
				notityMessage.setAccepterId(UserUtil.getCruUserId());
				notityMessage.setContent(meetingNoticeBack.getMeetingName());
				notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);
				notityMessage.setSubject("会议通知");
				JSONObject js = meetingServiceNoticeService.queryMessageId(notityMessage);
				String id = js.getJSONArray("data").getJSONObject(0).get("id").toString();
				//NotityMessage notity = notityService.getById(id);
				/*notity.setStatus(GlobalNames.SYS_NOTIFY_STATUS_Y);
				notityService.updateStatus(notity);*/
				json.put("flag", "1");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("msg", "");
			}
			return json;
			
		}

		/**
		 * 反馈列表 TODO
		 * 
		 * @author 冯超
		 * @Date 2018年4月12日 上午10:16:59
		 * @param pageable
		 * @param pageImpl
		 * @param startDate
		 * @param endDate
		 * @param
		 * @param
		 * @param
		 * @return
		 */
		@Override
		public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
				String meetingName, String status) {
			StringBuilder querySql = new StringBuilder();
			List<Object> para = new ArrayList<>();
			querySql.append("	from MeetingNoticeBack t ");
			querySql.append(
					"	where t.creUserId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
			// 拼接条件
			para.add(UserUtil.getCruUserId());
			// 起始时间
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.startTime >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.startTime <= ? ");
				para.add(endDate);
			}
			// 会议名称
			if (StringUtils.isNotBlank(meetingName)) {
				querySql.append("   and t.meetingName = ?");
				para.add(meetingName);
			}
			// 状态
			if (StringUtils.isNotBlank(status)) {
				querySql.append("   and t.status = ?");
				para.add(status);
			}
			// 拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.creTime desc ");
			} else {
				querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}

			Page<MeetingNoticeBack> page = meetingNoticeBackDao.query(querySql.toString(), pageable, para.toArray());
			// 草稿列表，添加操作列
			List<MeetingNoticeBack> content = page.getContent();
			for (MeetingNoticeBack meetingNoticeBack : content) {
				//if (ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getSubflag())) {
				meetingNoticeBack.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
				//}
			}
			return new PageImpl((int) page.getTotalElements(), page.getContent());
		}

		/**
		 * 根据id获取请假信息 TODO
		 * 
		 * @author 冯超
		 * @Date 2018年4月12日 下午12:00:58
		 * @param id
		 * @return
		 */
		@Override
		public MeetingNoticeBack getById(String id) {
			MeetingNoticeBack meetingNoticeBack = meetingNoticeBackDao.findOne(id);
			return meetingNoticeBack;
		}
		/**
		 * 删除请假数据 TODO
		 * @author 冯超
		 * @Date 2018年4月12日 下午2:43:11
		 * @param id
		 * @return
		 */
		@Override
		public int delete(String id) {
			int result = 0;
			String jpql = "update MeetingNoticeBack t set t.visible = ? where t.id = ?";
			try {
				result = meetingNoticeBackDao.update(jpql, CommonConstants.VISIBLE[0], id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		/**
		 * 更新状态 TODO
		 * 
		 * @author 冯超
		 * @Date 2018年4月18日 上午8:50:56
		 * @param id
		 * @param subflag
		 * @return
		 */
		@Override
		public MeetingNoticeBack updateSubFlag(String id, String subflag) {
			MeetingNoticeBack meetingNoticeBack = getById(id);
			meetingNoticeBack.setState(subflag);
			return meetingNoticeBackDao.save(meetingNoticeBack);
		}

		/**
		 * 查询反馈列表
		 * TODO 
		 * @author 张建明
		 * @Date 2018年7月26日 下午4:21:49
		 * @param pageable
		 * @param pageImpl
		 * @param startDate
		 * @param endDate
		 * @param meetingName
		 * @param status
		 * @return
		 */
		@Override
		public PageImpl getPageListDraft1(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
				String meetingName, String status) {
			StringBuilder querySql = new StringBuilder();
			List<Object> para = new ArrayList<>();
			/*querySql.append(" select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack(t.attentionItem,f.meetingroomId,t.publishState,t.id as noticeId,b.attendDeptType,t.fankuiType as feedbackType,f.applyTitle as meetingName,"
					+ "f.meetingroomName as meetingRoom,f.meetingStartTime as meetingBeginTime,"
					+ "f.meetingEndTime as meetingEndTime,f.meetingStartDate as meetingStartDate,f.meetingEndDate as meetingEndDate,"
					+ " b.id, b.state)");
			querySql.append(" from MeetingNoticeInfo t ,MeetingApplyInfo f ,MeetingNoticeBack b");
			querySql.append("	where b.meetingApplyId = f.id and b.noticeId = t.id  and b.visible = '" + CommonConstants.VISIBLE[1] + "'");*/
			
			querySql.append(" select t.attention_item,t5.meetingroomId,t.publish_state,t.id as noticeId,b.attend_dept_type,t.fankui_type as feedbackType,f.apply_title as meetingName,"
					+ "t4.meetingRoom, b.id, b.state,f.meeting_time,t.publish_state publishState,b.attend_dept_id");
			querySql.append(" from hygl_meeting_notice t "
					+ "	left outer join hygl_meeting_apply f on f.id = t.apply_id"
					+ "	left outer join hygl_notice_back b on t.id = b.notice_id"
					+" left outer join (select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRoom,t2.id  from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id ) t4 on t4.id = f.id "
					+" left outer join (select to_char(wm_concat(distinct t1.id))  meetingroomId,t2.id from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id)t5 on t5.id = f.id ");
			querySql.append("	where b.meeting_apply_id = f.id   and b.visible = '" + CommonConstants.VISIBLE[1] + "'");
			// 拼接条件
			String roleNos = UserUtil.getCruUserRoleNo();
			System.out.println("====="+roleNos);
			if(roleNos!=null&&!"".equals(roleNos)){
				if((roleNos.indexOf("D012")>=0||roleNos.indexOf("C011")>=0)&&(roleNos.indexOf("D011")>=0||roleNos.indexOf("C010")>=0)){
					querySql.append(" and ( b.attend_dept_id like '%"+UserUtil.getCruJuId()+"%' or b.attend_dept_id like '%"+UserUtil.getCruChushiId()+"%' )");
				}else if (roleNos.indexOf("D011")>=0||roleNos.indexOf("C010")>=0){
					querySql.append(" and b.attend_dept_id like '%"+UserUtil.getCruChushiId()+"%'");
				}else if(roleNos.indexOf("D012")>=0||roleNos.indexOf("C011")>=0){
					querySql.append(" and b.attend_dept_id like '%"+UserUtil.getCruJuId()+"%'");
				}
			}
			
			// 起始时间
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				querySql.append("   and substr(f.meeting_time,0,10) <= ? and substr(f.meeting_time,23,10)>= ? ");
				para.add(endDate);
				para.add(startDate);
			}
			// 会议名称
			if (StringUtils.isNotBlank(meetingName)) {
				querySql.append("   and f.apply_title like ? ");
				para.add("%" + meetingName + "%");
			}
			// 状态
			if (StringUtils.isNotBlank(status)) {
				querySql.append("   and b.state = ? ");
				para.add(status);
			}
			// 拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by b.cre_time desc ");
			} else {
				querySql.append("  order by b." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() );
			}

			StringBuilder sb1 =new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
			sb1.append(querySql.toString());
			sb1.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
			List<MeetingNoticeBack> content = new ArrayList<>();
			content = jdbcTemplate.query(sb1.toString(),new BeanPropertyRowMapper<>(MeetingNoticeBack.class),para.toArray());
			//总个数
	    	StringBuilder sb2 = new StringBuilder("select count(1) from (");
	    	sb2.append(querySql.toString());
	    	sb2.append(")");
	    	Integer total=jdbcTemplate.queryForObject(sb2.toString(), Integer.class,para.toArray());
			//Page<MeetingNoticeBack> page = meetingNoticeBackDao.query(querySql.toString(), pageable, para.toArray());
			// 草稿列表，添加操作列
			
			//List<MeetingNoticeBack> content = page.getContent();
			for (MeetingNoticeBack kqglLeaveInfo : content) {
				StringBuilder sb = new StringBuilder();
				sb.append(" select count(1) from hygl_seat t where t.noticeid='" + kqglLeaveInfo.getNoticeId() + "' and t.state !=4 ");
				int seatNum=0;
				if(kqglLeaveInfo.getAttendDeptType()!=null){
					if(kqglLeaveInfo.getAttendDeptType().contains("0") && kqglLeaveInfo.getAttendDeptType().contains("1")){
						sb.append("  and (t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "' or t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "' )");
						//sb.append("  or t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "'");
					}else if(kqglLeaveInfo.getAttendDeptType().contains("0")){
						//上报的为用户所在局部门的用户
						sb.append("  and t.ATTEND_DEPT_ID = '" + UserUtil.getCruJuId() + "'");
					}else if(kqglLeaveInfo.getAttendDeptType().contains("1")){
						//上报的为用户所在处部门的用户
						sb.append("  and t.ATTEND_DEPT_ID = '" + UserUtil.getCruChushiId() + "'");
					}
					 seatNum = jdbcTemplate.queryForObject(sb.toString(), Integer.class);
				}
				
				 
				kqglLeaveInfo.setSeatNum(seatNum);
				//已反馈和无需反馈
				if("1".equals(kqglLeaveInfo.getState()) ||"2".equals(kqglLeaveInfo.getState())){
					kqglLeaveInfo.setCz(CommonConstants.OPTION_VIEW);
				}
				//未反馈
				if("0".equals(kqglLeaveInfo.getState())){
					kqglLeaveInfo.setCz(CommonConstants.OPTION_UPDATE);
				}
				//if (ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getSubflag())) {
				//	kqglLeaveInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
				//}
			}
			return new PageImpl(total, content);
		}
		/**
		 * 查询未反馈列表
		 * TODO 
		 * @author 张建明
		 * @Date 2018年7月26日 下午4:21:49
		 * @param pageable
		 * @param pageImpl
		 * @param startDate
		 * @param endDate
		 * @param meetingName
		 * @param status
		 * @return
		 */
		@Override
		public PageImpl getunBackedPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
				String meetingName, String status, String id) {
			StringBuilder querySql = new StringBuilder();
			List<Object> para = new ArrayList<>();
			querySql.append(" select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack(f.meetingName as meetingName,"
					+ "f.meetingroomName as meetingRoom,f.meetingBegainTime as meetingBeginTime,"
					+ "f.meetingEndTime as meetingEndTime,f.meetingroomPlace as meetingRoomPlace,"
					+ " b.id, b.state)");
			querySql.append(" from MeetingNoticeInfo t ,MeetingApplyInfo f ,MeetingNoticeBack b");
			querySql.append("	where b.meetingApplyId = f.id and b.noticeId = t.id and b.noticeId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
			// 拼接条件
			para.add(UserUtil.getCruUserId());
			// 起始时间
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.startTime >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.startTime <= ? ");
				para.add(endDate);
			}
			// 会议名称
			if (StringUtils.isNotBlank(meetingName)) {
				querySql.append("   and t.meetingName = ? ");
				para.add(meetingName);
			}
			// 状态
			if (StringUtils.isNotBlank(status)) {
				querySql.append("   and t.status = ? ");
				para.add(status);
			}
			// 拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.creTime desc ");
			} else {
				querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}

			Page<MeetingNoticeBack> page = meetingNoticeBackDao.query(querySql.toString(), pageable, para.toArray());
			// 草稿列表，添加操作列
			List<MeetingNoticeBack> content = page.getContent();
			for (MeetingNoticeBack kqglLeaveInfo : content) {
				//if (ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getSubflag())) {
					kqglLeaveInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
				//}
			}
			return new PageImpl((int) page.getTotalElements(), page.getContent());
		}
		/**
		 * 查询已反馈列表
		 * TODO 
		 * @author 张建明
		 * @Date 2018年7月26日 下午4:21:49
		 * @param pageable
		 * @param pageImpl
		 * @param
		 * @param
		 * @param
		 * @param status
		 * @return
		 */
		@Override
		public PageImpl getBackedPageList(Pageable pageable, PageImpl pageImpl, String status, String id) {
			StringBuilder querySql = new StringBuilder();
			List<Object> para = new ArrayList<>();
			//3.9日修改前的sql如下：
			/*querySql.append(" select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack(f.applyTitle as meetingName,"
					+ "f.meetingroomName as meetingRoom,f.meetingStartTime as meetingBeginTime,"
					+ "f.meetingEndTime as meetingEndTime,f.meetingStartDate as meetingStartDate,f.meetingEndDate as meetingEndDate,"
					+ "b.attendDeptId,b.attendDept,b.attendPersonName,b.attendPersonId,b.attendPersonChuName,b.attendPersonChuId,b.attendPersonNum,b.remark,"
					+ " b.id, b.state)");*/
			//3.9日修改的sql如下：
			querySql.append(" select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack(f.applyTitle as meetingName,"
					+ "b.attendDeptId,b.attendDept,b.attendPersonName,b.attendPersonId,b.attendPersonChuName,b.attendPersonChuId,b.attendPersonNum,b.remark,"
					+ " b.id, b.state,b.attendPersonLeaveId,b.attendPersonLeaveName,b.shouldAttendNum,b.realAttendNum,b.notAttendPersonId,b.notAttendPersonName,"
					+ "b.notAttendPersonChuId,b.notAttendPersonChuName,b.notAttendPersonLeaveId,b.notAttendPersonLeaveName)");
			querySql.append(" from MeetingNoticeInfo t ,MeetingApplyInfo f ,MeetingNoticeBack b");
			querySql.append("	where b.meetingApplyId = f.id and b.noticeId = t.id and b.noticeId = ? and b.visible = '" + CommonConstants.VISIBLE[1] + "'");
			// 拼接条件
			para.add(id);
			// 状态
			if (StringUtils.isNotBlank(status)) {
				querySql.append("   and b.state = ?");
				para.add(status);
			}
			// 拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by b.remark asc ");
			} else {
				querySql.append("  order by b." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}

			Page<MeetingNoticeBack> page = meetingNoticeBackDao.query(querySql.toString(), pageable, para.toArray());
			String sql = " select sum(to_number(t.attend_person_num)) attendNum,sum(to_number(t.real_attend_num)) realAttendNum,sum(to_number(t.should_attend_num)) shouldAttendNum "
					+ "from hygl_notice_back t where t.notice_id = ? and t.state = ? ";
			CountNoticeBack countNoticeBack = jdbcTemplate.queryForObject(sql,new Object[]{id,status},new BeanPropertyRowMapper<CountNoticeBack>(CountNoticeBack.class));
			// 草稿列表，添加操作列
			List<MeetingNoticeBack> content = page.getContent();
			for (MeetingNoticeBack meetingNoticeBack : content) {
				meetingNoticeBack.setAttendCount(countNoticeBack.getAttendNum());
				meetingNoticeBack.setShouldAttendCount(countNoticeBack.getShouldAttendNum());
				meetingNoticeBack.setRealAttendCount(countNoticeBack.getRealAttendNum());
				if ("1".equals(meetingNoticeBack.getState())||"2".equals(meetingNoticeBack.getState())) {
					meetingNoticeBack.setCz(CommonConstants.OPTION_UPDATE );
				}else if ("0".equals(meetingNoticeBack.getState())){
					meetingNoticeBack.setCz(CommonConstants.OPTION_ADD );
				}
			}
			return new PageImpl((int) page.getTotalElements(), page.getContent());
		}
		
		/**
		 * 根据id获取反馈信息
		 * TODO 
		 * @author 张建明
		 * @Date 2018年7月26日 下午4:22:14
		 * @param id
		 * @return
		 */
		@Override
		public MeetingNoticeBack getById1(String id) {
			StringBuilder querySql = new StringBuilder();
			//3.9日前的sql.如下：
			/*querySql.append("select f.APPLY_TITLE as MEETING_NAME,"
					+ "f.MEETINGROOM_NAME as MEETING_ROOM,f.MEETING_START_TIME as meeting_begin_time,"
					+ "f.MEETING_END_TIME as meeting_end_time,f.MEETING_START_DATE as meeting_start_date,f.MEETING_END_DATE as meeting_end_date,f.MEETING_PLACE as meeting_room_place,"
					+ " b.id, b.meeting_apply_id,b.ATTEND_DEPT,t.IS_FANKUI as is_feedback,t.FANKUI_TYPE as feedback_type,t.RESPONSE_TIME as FEEDBACK_TIME,b.REAL_FEEDBACK_TIME, b.ATTEND_DEPT_ID,");
			querySql.append(" b.state,b.notice_id, t.contents as meeting_content, t.remark as notice_remark,b.remark,b.attend_person_num,b.attend_person_id,b.attend_person_name,b.attend_dept_type,b.attend_person_chu_id,b.attend_person_chu_name");
			querySql.append(" from HYGL_MEETING_NOTICE t ,hygl_meeting_apply f ,hygl_notice_back b");
			querySql.append(" where b.meeting_apply_id = f.id and b.notice_id = t.id  and b.id = ?");*/
			//3.9日修改后的sql如下：
			querySql.append("select t.attend_dept attend_chu_dept,t.attend_dept_ju attend_ju_dept,f.apply_title as meeting_name,f.host_dept_name as host_dept,f.meeting_time,f.cre_user_id,"
					+ " b.id, b.meeting_apply_id,b.attend_dept,t.is_fankui as is_feedback,t.fankui_type as feedback_type,t.RESPONSE_TIME as FEEDBACK_TIME,b.REAL_FEEDBACK_TIME, b.ATTEND_DEPT_ID,");
			querySql.append(" b.state,b.notice_id, t.contents as meeting_content, t.remark as notice_remark,b.remark,b.attend_person_num,b.attend_person_id,b.attend_person_name,b.attend_dept_type,b.attend_person_chu_id,b.attend_person_chu_name,");
			querySql.append(" b.attend_person_leave_id,b.attend_person_leave_name, b.not_attend_person_leave_id, b.not_attend_person_leave_name,b.not_attend_person_chu_id,b.not_attend_person_chu_name,b.not_attend_person_id,b.not_attend_person_name,b.should_attend_num,b.real_attend_num	");
			querySql.append(" from hygl_meeting_notice t ,hygl_meeting_apply f ,hygl_notice_back b");
			querySql.append(" where b.meeting_apply_id = f.id and b.notice_id = t.id  and b.id = ?");
			MeetingNoticeBack meetingNoticeBack = jdbcTemplate.queryForObject(querySql.toString(),new Object[]{id},new BeanPropertyRowMapper<MeetingNoticeBack>(MeetingNoticeBack.class));
			return meetingNoticeBack;
		}
		
		/**
		 * 根据会议通知id获取该会议通知二级局（或处长的）参会人员
		 * TODO 
		 * @author 马秋霞
		 * @Date 2018年9月13日 上午10:05:02
		 * @param meetingNoticeId
		 * @return
		 */
		@Override
		public Map<String,List<MeetingNoticeBack>> findByNoticeId(String meetingNoticeId,String isJu,String isChuZhang,Map<String,String> attendDeptMap) {
			List<Object> param = new ArrayList<Object>();
			Map<String,List<MeetingNoticeBack>> map = new HashMap<String,List<MeetingNoticeBack>>();//key为参会部门，value为一些反馈信息
			List<MeetingNoticeBack> list = new ArrayList<MeetingNoticeBack>();
			StringBuilder sb = new StringBuilder();
			if("0".equals(isJu) || "0".equals(isChuZhang)){
				//局长或处长至少有一个没被选中
				if("0".equals(isJu) && "0".equals(isChuZhang)){
					//都没选中
					return map;
				}else if("1".equals(isJu)){
					//局长复选框被选中
					sb.append(" select t.ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
					sb.append(" union ");
					sb.append(" select t.attend_person_chu_id ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
					sb.append(" union ");
					sb.append(" select t.ATTEND_PERSON_LEAVE_ID ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
					param.add(meetingNoticeId);
					param.add(meetingNoticeId);
					param.add(meetingNoticeId);
				}else if("1".equals(isChuZhang)){
					//处长长复选框被选中
					sb.append(" select t.ATTEND_PERSON_CHU_ID ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'1' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='1' and t.NOTICE_ID = ? ");
					param.add(meetingNoticeId);
				}
			}else{
				//局长和处长都选中了
				sb.append(" select t.ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
				sb.append(" union ");
				sb.append(" select t.attend_person_chu_id ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
				sb.append(" union ");
				sb.append(" select t.ATTEND_PERSON_LEAVE_ID ATTEND_PERSON_ID,t.attend_dept_id,t.attend_dept,'0' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='0' and t.NOTICE_ID = ? ");
				sb.append(" union ");
				sb.append(" select t.ATTEND_PERSON_CHU_ID,t.attend_dept_id,t.attend_dept,'1' flag from hygl_notice_back t where t.visible = '1' and state='1' and t.ATTEND_DEPT_TYPE='1' and t.NOTICE_ID = ? ");
				param.add(meetingNoticeId);
				param.add(meetingNoticeId);
				param.add(meetingNoticeId);
				param.add(meetingNoticeId);
			}
			list = jdbcTemplate.query(sb.toString(),param.toArray(), new BeanPropertyRowMapper<MeetingNoticeBack>(MeetingNoticeBack.class));
	        for (MeetingNoticeBack meetingNoticeBack : list) {
				if(map.get(meetingNoticeBack.getAttendDeptId())==null){
					attendDeptMap.put(meetingNoticeBack.getAttendDeptId(), meetingNoticeBack.getAttendDept());
					List<MeetingNoticeBack> templist = new ArrayList<MeetingNoticeBack>();
					templist.add(meetingNoticeBack);
					map.put(meetingNoticeBack.getAttendDeptId(), templist);
				}else{
					map.get(meetingNoticeBack.getAttendDeptId()).add(meetingNoticeBack);
				}
			}
			return map;
		}

		@Override
		public CountNoticeBack getBackCount(String id) {
			StringBuilder querySql = new StringBuilder();
			querySql.append("select count (b.notice_id) as total_count,"
					+ " sum(case when b.state = '0' then 1 else 0 end) as un_backed_count,"
 +" sum(case when b.state = '1' then 1 else 0 end) as backed_count,"
 +" sum(case when b.remark is not null then 1 else 0 end) as remark_count");
			querySql.append(" from hygl_notice_back b");
			querySql.append(" where b.notice_id = ? and b.state <> '2'");
			CountNoticeBack countNoticeBack = jdbcTemplate.queryForObject(querySql.toString(),new Object[]{id},new BeanPropertyRowMapper<CountNoticeBack>(CountNoticeBack.class));
			return countNoticeBack;
		}

}
