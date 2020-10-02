package com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service.MeetingRoomInfoService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.dao.MeetingServiceNoticeDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONObject;

/**
 * 会务服务Service
 * TODO 
 * @author 张建明
 * @Date 2018年8月29日 上午11:38:35
 */
@Service
public class MeetingServiceNoticeServiceImpl implements MeetingServiceNoticeService {
	
	@Autowired
	private MeetingServiceNoticeDao meetingServiceNoticeDao;
	@Autowired
	MeetingRoomInfoService meetingRoomInfoService;
	@Autowired
	private SysWaitNoflowService sysWaitNoflowservice;
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 保存
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:12:57
	 * @param meetingServiceNotice
	 * @return
	 * @throws Exception
	 */
	@Override
	public MeetingServiceNotice saveForm(MeetingServiceNotice meetingServiceNotice) throws Exception {
		meetingServiceNotice.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String creUserId = UserUtil.getCruUserId();
		String creUserName = UserUtil.getCruUserName();
		String creDeptId = UserUtil.getCruDeptId();
		String creDeptName = UserUtil.getCruDeptName();
		if (StringUtils.isBlank(meetingServiceNotice.getId())) {
			//新建保存
			meetingServiceNotice.setCreTime(creTime);
			meetingServiceNotice.setCreUserId(creUserId);
			meetingServiceNotice.setCreUserName(creUserName);
			meetingServiceNotice.setCreDeptId(creDeptId);
			meetingServiceNotice.setCreDeptName(creDeptName);
			meetingServiceNotice.setCreChushiId(UserUtil.getCruChushiId());
			meetingServiceNotice.setCreChushiName(UserUtil.getCruChushiName());
			meetingServiceNotice.setCreJuId(UserUtil.getCruJuId());
			meetingServiceNotice.setCreJuName(UserUtil.getCruJuName());
			
			//临时数据
			//meetingServiceNotice.setMeetingroomId("402894826521dacb016521ff8d1f0000");
			//meetingServiceNotice.setMeetingroomName("三楼1310");
			//meetingServiceNotice.setMeetingStartDate("2018-08-23");
			//meetingServiceNotice.setMeetingStartTime("0");//0 上午 8:00 1下午 12:00
			//meetingServiceNotice.setMeetingEndDate("2018-08-23");
			//meetingServiceNotice.setMeetingEndTime("0");//0:上午 12:00 1下午18:00
			//拼接为一个新的日期范围，方便会议室预约情况的统计
			
			meetingServiceNotice = meetingServiceNoticeDao.save(meetingServiceNotice);
		}else{
			//更新
			MeetingServiceNotice oldMeetingServiceNotice = meetingServiceNoticeDao.findOne(meetingServiceNotice.getId());
			//更新人数据
			oldMeetingServiceNotice.setUpdateTime(creTime);
			oldMeetingServiceNotice.setUpdateUserId(creUserId);
			oldMeetingServiceNotice.setUpdateUserName(creUserName);
			//业务数据
			oldMeetingServiceNotice.setStatus(meetingServiceNotice.getStatus());
			oldMeetingServiceNotice.setIsOrNot(meetingServiceNotice.getIsOrNot());
			oldMeetingServiceNotice.setRemark(meetingServiceNotice.getRemark());
			
			meetingServiceNotice = meetingServiceNoticeDao.save(oldMeetingServiceNotice);
		}
		return meetingServiceNotice;
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
		String jpql = "update MeetingServiceNotice t set t.visible = ? where t.id = ?";
		return meetingServiceNoticeDao.update(jpql, CommonConstants.VISIBLE[0], id);
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
	public MeetingServiceNotice getById(String id) throws Exception {
		return meetingServiceNoticeDao.findOne(id);
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
	public MeetingServiceNotice updateSubFlag(String id, String subflag) throws Exception {
		MeetingServiceNotice meetingServiceNotice = getById(id);
		return meetingServiceNoticeDao.save(meetingServiceNotice);
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
	@SuppressWarnings("unused")
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl,String meetingRoom,String meetingName,
			String startDate, String endDate,String meetingServices,String status) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		/*querySql.append("select st.meetingroom_apply_id,a.host_dept_name,a.apply_title,a.meetingroom_name,a.meeting_place,a.meeting_start_date,a.meeting_start_time,");
		querySql.append(" a.meeting_end_date,a.meeting_end_time,st.meeting_service_name,st.status from HYGL_MEETING_APPLY a ,");
		querySql.append(" (select t.status,t.meetingroom_apply_id, t.contact_id, to_char(str_sum(t.meeting_service_name || ',')) meeting_service_name ");
		querySql.append(" from HYGL_MEETING_SERVICE_TASK t where t.contact_id = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");*/
		querySql.append("select st.meetingroom_apply_id,a.host_dept_name,a.apply_title,t1.meetingRoom meetingroom_name,a.meeting_time,");
		querySql.append(" st.meeting_service_name,st.status,st.meeting_service_id from HYGL_MEETING_APPLY a ,");
		querySql.append(" (select t.status,t.meetingroom_apply_id, t.contact_id, to_char(str_sum(t.meeting_service_name || ',')) meeting_service_name,"
				+ "to_char(str_sum(t.meeting_service_id || ',')) meeting_service_id ");
		querySql.append(" from HYGL_MEETING_SERVICE_TASK t where t.contact_id = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		
		
		/*querySql.append("select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice( t.meetingroomApplyId,a.hostDeptName,a.applyTitle,a.meetingroomName,a.meetingPlace,a.meetingStartDate,a.meetingStartTime,");
		querySql.append(" a.meetingEndDate,a.meetingEndTime,t.meetingServiceName,t.status ) from MeetingApplyInfo a ,");
		querySql.append(" MeetingServiceNotice t where t.contactId = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append(" and t.meetingroomApplyId = a.id ");*/
		//拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(status)) {
			querySql.append("   and t.status = ?");
			para.add(status);
		}
		//拼接排序语句
//		if (StringUtils.isNotBlank(meetingServices)) {
//			querySql.append("   and t.MEETING_SERVICE_ID in("+CommonUtils.commomSplit(meetingServices)+")");
//		}
		querySql.append(" group by t.contact_id,t.meetingroom_apply_id,t.status) st,");
		querySql.append(" (select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRoom,t2.id  from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id) "
				+ "t1 where st.meetingroom_apply_id = a.id and t1.id =a.id");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) ) {
			querySql.append("   and substr(a.meeting_time,0,10) <= ?");
			para.add(endDate);
			querySql.append("   and substr(a.meeting_time,23,10) >= ?");
			para.add(startDate);
		}
		
		
		if (StringUtils.isNotBlank(meetingRoom)) {
			querySql.append("   and t1.meetingRoom like ?");
			para.add("%"+meetingRoom+"%");
		}
		if (StringUtils.isNotBlank(meetingName)) {
			querySql.append("   and a.apply_title like ?");
			para.add("%"+meetingName+"%");
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by a.cre_time desc ");
		}else{
			querySql.append("  order by a."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+" ");
		}
		String resSql="select * from ("+querySql+") b ";
		if(StringUtils.isNotBlank(meetingServices)){
			String[] mtServices;
			if(meetingServices.indexOf(",")!=-1){
				mtServices=meetingServices.split(",");
				int tt=mtServices.length;
				resSql+="where 1=1 and ((b.meeting_service_id like '%"+mtServices[0]+"%')";
				for(int i=1;i<mtServices.length;i++){
					resSql+=" or (b.meeting_service_id like '%"+mtServices[i]+"%')";
					if(i==mtServices.length-1){
						resSql+=")";	
					}
				}
				
			}else{
				resSql+="where b.meeting_service_id like '%"+meetingServices+"%'";	
			}
		}
       // Page<MeetingServiceNotice> page = meetingServiceNoticeDao.query(querySql.toString(), pageable,para.toArray());
        Page<Object[]> page1 = meetingServiceNoticeDao.nativeQuery(resSql.toString(), pageable, para.toArray());
        //草稿列表，添加操作列
        List<Object[]> content1 = page1.getContent();
        List<Object> newContent1 = new ArrayList<>();
        for (Object[] object1  : content1) {
        	MeetingServiceNotice meetingServiceNotice = new MeetingServiceNotice();
        	meetingServiceNotice.setMeetingroomApplyId(object1[0].toString());
        	meetingServiceNotice.setHostDeptName(object1[1].toString());
        	meetingServiceNotice.setApplyTitle(object1[2].toString());
        	meetingServiceNotice.setMeetingroomName(object1[3].toString());
        	//meetingServiceNotice.setMeetingPlace(object1[4].toString());
        	meetingServiceNotice.setMeetingTime(object1[4].toString());
        	meetingServiceNotice.setMeetingServiceName(object1[5].toString());
        	meetingServiceNotice.setStatus(object1[6].toString());
        	if (ConfigConsts.START_FLAG.equals(meetingServiceNotice.getStatus())) {
        		meetingServiceNotice.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			}
        	newContent1.add(meetingServiceNotice);
		}
        pageImpl.setFlag("1");
		pageImpl.getData().setRows(newContent1);
		pageImpl.getData().setTotal((int)page1.getTotalElements());
        return pageImpl;
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
	@SuppressWarnings("unused")
	@Override
	public PageImpl getPageBackList(Pageable pageable, PageImpl pageImpl, String title,
			String startDate, String endDate, String status,String meetingRoom,String meetingName,String meetingServices) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("select st.meetingroom_apply_id,a.host_dept_name,a.apply_title,t1.meetingRoom meetingroom_name,");
		querySql.append(" a.meeting_time,st.meeting_service_name meeting_service_name,st.un_backed_count remark,st.backed_count status ,st.meeting_service_id meeting_service_id from HYGL_MEETING_APPLY a ,");
		querySql.append(" (select t.meetingroom_apply_id,to_char(str_sum(t.meeting_service_name || ',')) meeting_service_name,to_char(str_sum(t.meeting_service_id || ',')) meeting_service_id,");
		querySql.append(" sum(case when t.status = '1' then 1 else 0 end) as un_backed_count,");
		querySql.append(" count( t.id) as backed_count");
		querySql.append(" from HYGL_MEETING_SERVICE_TASK t where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		/*querySql.append("select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice( t.meetingroomApplyId,a.hostDeptName,a.applyTitle,a.meetingroomName,a.meetingPlace,a.meetingStartDate,a.meetingStartTime,");
		querySql.append(" a.meetingEndDate,a.meetingEndTime,t.meetingServiceName,t.status ) from MeetingApplyInfo a ,");
		querySql.append(" MeetingServiceNotice t where t.contactId = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append(" and t.meetingroomApplyId = a.id ");*/
		//拼接条件

//		if (StringUtils.isNotBlank(meetingServices)) {
//			querySql.append("   and t.MEETING_SERVICE_ID in("+CommonUtils.commomSplit(meetingServices)+")");
//		}
		//拼接排序语句
		
		querySql.append(" group by t.meetingroom_apply_id) st,");
		querySql.append(" (select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRoom,t2.id  from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id) "
				+ "t1 where st.meetingroom_apply_id = a.id and t1.id =a.id");
		querySql.append("   and a.cre_user_id = ?");
		para.add(UserUtil.getCruUserId());
		//会议室名称
		if (StringUtils.isNotBlank(meetingRoom)) {
			querySql.append("   and t1.meetingRoom like ?");
			para.add("%"+meetingRoom+"%");
		}
		// 会议标题
		if (StringUtils.isNotBlank(meetingName)) {
			querySql.append("   and a.apply_title like ?");
			para.add("%"+meetingName+"%");
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and substr(a.meeting_time,0,10) <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and substr(a.meeting_time,23,10) >= ?");
			para.add(startDate);
		}
		
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by a.cre_time desc");
		}else{
			querySql.append("  order by a."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+" ");
		}
		String resSql="select * from ("+querySql+") b ";
		if(StringUtils.isNotBlank(meetingServices)){
			String[] mtServices;
			if(meetingServices.indexOf(",")!=-1){
				mtServices=meetingServices.split(",");
				int tt=mtServices.length;
				resSql+="where 1=1 and ((b.meeting_service_id like '%"+mtServices[0]+"%')";
				for(int i=1;i<mtServices.length;i++){
					resSql+=" or (b.meeting_service_id like '%"+mtServices[i]+"%')";
					if(i==mtServices.length-1){
						resSql+=")";	
					}
				}
				
			}else{
				resSql+="where b.meeting_service_id like '%"+meetingServices+"%'";	
			}
		}
		
       // Page<MeetingServiceNotice> page = meetingServiceNoticeDao.query(querySql.toString(), pageable,para.toArray());
        Page<Object[]> page = meetingServiceNoticeDao.nativeQuery(resSql.toString(), pageable, para.toArray());
        //草稿列表，添加操作列
        List<Object[]> content = page.getContent();
        List<Object> newContent = new ArrayList<>();
        for (Object[] object  : content) {
        	MeetingServiceNotice meetingServiceNotice = new MeetingServiceNotice();
        	meetingServiceNotice.setMeetingroomApplyId(object[0].toString());
        	meetingServiceNotice.setHostDeptName(object[1].toString());
        	meetingServiceNotice.setApplyTitle(object[2].toString());
        	meetingServiceNotice.setMeetingroomName(object[3].toString());
        	//meetingServiceNotice.setMeetingPlace(object[4].toString());
        	meetingServiceNotice.setMeetingTime(object[4].toString());
        	meetingServiceNotice.setMeetingServiceName(object[5].toString());
        	meetingServiceNotice.setRemark(object[6].toString());
        	meetingServiceNotice.setStatus(object[7].toString());
        	if (ConfigConsts.START_FLAG.equals(meetingServiceNotice.getStatus())) {
        		meetingServiceNotice.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			}
        	newContent.add(meetingServiceNotice);
		}
        pageImpl.setFlag("1");
		pageImpl.getData().setRows(newContent);
		pageImpl.getData().setTotal((int)page.getTotalElements());
        return pageImpl;
	}
	/**
	 * 
	 * TODO 查询会务服务项列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public List<MeetingServiceNotice> getByMeetingApplyId(final MeetingServiceNotice meetingServiceNotice) {
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		Sort sort = new Sort(list);
		List<MeetingServiceNotice> meetingServiceInfoList =  meetingServiceNoticeDao.findAll(new Specification<MeetingServiceNotice>() {
			@Override
			public Predicate toPredicate(Root<MeetingServiceNotice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
					pName = cb.equal(root.get("contactId").as(String.class), meetingServiceNotice.getContactId());
					predicates.add(pName);
					Predicate paId = null;
					paId = cb.equal(root.get("meetingroomApplyId").as(String.class), meetingServiceNotice.getMeetingroomApplyId());
					predicates.add(paId);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
		//将负责人部门放进列表
		//for(MeetingServiceNotice meetingService : meetingServiceInfoList){
		//	meetingService.setResponsibleUserDeptId(meetingServiceNotice.getResponsibleUserDeptId());
		//}
		return meetingServiceInfoList;
	}

	@Override
	public List<MeetingServiceNotice> saveForm(List<MeetingServiceNotice> meetingServiceNotices) throws Exception {
		List<MeetingServiceNotice> newMeetingServiceNotices = new ArrayList<MeetingServiceNotice>();
		for(MeetingServiceNotice meetingServiceNotice : meetingServiceNotices){
			meetingServiceNotice.setStatus("0");
			newMeetingServiceNotices.add(saveForm(meetingServiceNotice));
		}
		return newMeetingServiceNotices;
	}
	@Override
	public List<MeetingServiceNotice> fankuiForm(List<MeetingServiceNotice> meetingServiceNotices) throws Exception {
		List<MeetingServiceNotice> newMeetingServiceNotices = new ArrayList<MeetingServiceNotice>();
		for(MeetingServiceNotice meetingServiceNotice : meetingServiceNotices){
			meetingServiceNotice.setStatus("1");
			newMeetingServiceNotices.add(saveForm(meetingServiceNotice));
		}
		sysWaitNoflowservice.deleteWaitNoflow("",newMeetingServiceNotices.get(0).getMeetingroomApplyId(),UserUtil.getCruDeptId(),UserUtil.getCruUserId(),"");
		return newMeetingServiceNotices;
	}
	@Override
	public PageImpl getAllList(Pageable pageable, PageImpl pageImpl, String meetingroomApplyId, String status) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice(a.id ,"
				+ "a.meetingroomApplyId, a.meetingServiceName,a.isOrNot,a.remark,"
				+ " b.responsibleUserName as responsibleUserName, b.responsibleUserTelephone as responsibleUserTelephone, c.serviceDeptName as serviceDeptId )");
		querySql.append("	from MeetingServiceNotice a, MeetingServiceInfo b, ServiceDeptInfo c ");
		querySql.append("	where a.meetingServiceId = b.id ");
		querySql.append("	and b.serviceDeptId = c.id and a.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		querySql.append("   and a.meetingroomApplyId = ?");
		para.add(meetingroomApplyId);
		if (StringUtils.isNotBlank(status) ){
			querySql.append("   and a.status = ?");
			para.add(status);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by a.creTime desc) ");
		}else{
			querySql.append("  order by a."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<TaskPlan> page = meetingServiceNoticeDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
	}
	@Override
	public JSONObject queryMessageId(NotityMessage notityMessage) {
		String sql ="select * from message t where t.senderid ='"+notityMessage.getSenderId()+"' and t.accepterid='"+notityMessage.getAccepterId()+"' and t.subject='"+notityMessage.getSubject()+"' and t.content='"+notityMessage.getContent()+"'";
		JSONObject result = userInfoService.getDateBySql(sql);
		return result;
	}
}
