package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.dao.MeetingNoticeDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingNoticeInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.services.MeetingNoticeBackService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DeptVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议通知Service
 * TODO 
 * @author 张建明
 * @Date 2018年7月19日 上午10:40:02
 */
@Service
public class MeetingNoticeServiceImpl implements MeetingNoticeService {

	private ObjectMapper mapper = new ObjectMapper();
	
    @Autowired
    private SysWaitNoflowService sysWaitNoflowservice;
    
    @Autowired
    MeetingNoticeBackService meetingNoticeBackService;
    
	@Autowired
	private MeetingNoticeDao meetingNoticeDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MeetingApplyService meetingApplyService;
	
	/**
	 * 保存
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月26日 下午4:21:22
	 * @param meetingNotice
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "static-access", "unused" })
	@Override
	public MeetingNoticeInfo saveForm(MeetingNoticeInfo meetingNotice)throws Exception {
		meetingNotice.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(meetingNotice.getId())) {
			// 创建人部门
			String creDeptId = UserUtil.getCruDeptId();
			String creDeptName = UserUtil.getCruDeptName();
			// 创建人处室
			String creChushiId = UserUtil.getCruChushiId();
			String creChushiName = UserUtil.getCruChushiName();
			// 创建人二级局
			String creJuId = UserUtil.getCruJuId();
			String creJuName = UserUtil.getCruJuName();

			meetingNotice.setCreTime(creTime);
			meetingNotice.setCreUserId(userId);
			meetingNotice.setCreUserName(userName);
			meetingNotice.setCreDeptId(creDeptId);
			meetingNotice.setCreDeptName(creDeptName);
			meetingNotice.setCreChuShiId(creChushiId);
			meetingNotice.setCreChuShiName(creChushiName);
			meetingNotice.setCreJuId(creJuId);
			meetingNotice.setCreJuName(creJuName);
			
			// 更新最后修改记录
			meetingNotice.setUpdateTime(creTime);
			meetingNotice.setUpdateUserId(userId);
			meetingNotice.setUpdateUserName(userName);
			meetingNotice = meetingNoticeDao.save(meetingNotice);
		} else {
			MeetingNoticeInfo oldHyglNoticeInfo = meetingNoticeDao.findOne(meetingNotice.getId());
			oldHyglNoticeInfo.setIsFankui(meetingNotice.getIsFankui());
			oldHyglNoticeInfo.setFankuiType(meetingNotice.getFankuiType());
			oldHyglNoticeInfo.setResponseTime(meetingNotice.getResponseTime());
			oldHyglNoticeInfo.setAttendDept(meetingNotice.getAttendDept());
			oldHyglNoticeInfo.setAttendDeptId(meetingNotice.getAttendDeptId());
			oldHyglNoticeInfo.setAttendDeptJu(meetingNotice.getAttendDeptJu());
			oldHyglNoticeInfo.setAttendDeptJuId(meetingNotice.getAttendDeptJuId());
			oldHyglNoticeInfo.setContents(meetingNotice.getContents());
			oldHyglNoticeInfo.setRemark(meetingNotice.getRemark());
			oldHyglNoticeInfo.setNoticeName(meetingNotice.getNoticeName());
			oldHyglNoticeInfo.setApplyId(meetingNotice.getApplyId());
			oldHyglNoticeInfo.setMeetingName(meetingNotice.getMeetingName());
			oldHyglNoticeInfo.setStatus(meetingNotice.getStatus());
			oldHyglNoticeInfo.setTreeId(meetingNotice.getTreeId());
			// 更新最后修改记录
			oldHyglNoticeInfo.setUpdateTime(creTime);
			oldHyglNoticeInfo.setUpdateUserId(userId);
			oldHyglNoticeInfo.setUpdateUserName(userName);
			meetingNotice = meetingNoticeDao.save(oldHyglNoticeInfo);
		}
		if("1".equals(meetingNotice.getStatus())){
			//改变会议申请的通知状态 
			MeetingApplyInfo meetingApplyInfo = new MeetingApplyInfo();
			meetingApplyInfo.setId(meetingNotice.getApplyId());
			meetingApplyInfo = meetingApplyService.getById(meetingApplyInfo.getId());
			meetingApplyInfo.setNoticeFlag("1");
			meetingApplyService.save(meetingApplyInfo);
			
		}
		if("1".equals(meetingNotice.getIsFankui())){
		if("1".equals(meetingNotice.getStatus())){
			String [] deptIDs = meetingNotice.getAttendDeptId().split(",");
			String [] depts = meetingNotice.getAttendDept().split(",");
			String [] deptJuIDs = meetingNotice.getAttendDeptJuId().split(",");
			String [] deptJus = meetingNotice.getAttendDeptJu().split(",");
			if(!"".equals(deptJuIDs[0])&&deptJuIDs[0]!=null){
			for(int i=0;i<deptJuIDs.length;i++){
				MeetingNoticeBack meetingNoticeBack = new MeetingNoticeBack();
				meetingNoticeBack.setAttendDept(deptJus[i]);
				meetingNoticeBack.setAttendDeptId(deptJuIDs[i]);
				meetingNoticeBack.setCreTime(creTime);
				meetingNoticeBack.setFeedbackTime(meetingNotice.getResponseTime());
				meetingNoticeBack.setFeedbackType(meetingNotice.getFankuiType());
				
				meetingNoticeBack.setIsFeedback(meetingNotice.getIsFankui());
				meetingNoticeBack.setMeetingName(meetingNotice.getNoticeName());
				meetingNoticeBack.setMeetingApplyId(meetingNotice.getApplyId());
				meetingNoticeBack.setMeetingBeginTime(meetingNotice.getStartTime());
				meetingNoticeBack.setMeetingContent(meetingNotice.getContents());
				meetingNoticeBack.setMeetingEndTime(meetingNotice.getEndTime());
				meetingNoticeBack.setMeetingRoom(meetingNotice.getMeetingRoom());
				meetingNoticeBack.setMeetingRoomPlace(meetingNotice.getMeetingPlace());
				meetingNoticeBack.setNoticeId(meetingNotice.getId());
				meetingNoticeBack.setAttendDeptType("0");
				if("0".equals(meetingNotice.getIsFankui())){
					meetingNoticeBack.setState("2");
				}else{
					meetingNoticeBack.setState("0");
				}
				meetingNoticeBack = meetingNoticeBackService.saveForm(meetingNoticeBack);
					SysWaitNoflow waitNoflwJu = new SysWaitNoflow();
					waitNoflwJu.setReceiveDeptName(deptJus[i]);//接收人部门
					waitNoflwJu.setReceiveDeptId(deptJuIDs[i]);//接收人部门id 必填
					waitNoflwJu.setReceiveUserId("");//接受人id
					waitNoflwJu.setReceiveUserName("");//接受人name
					waitNoflwJu.setRolesNo("C011");//接受业务角色
					waitNoflwJu.setOpName("会议通知");//操作类型
					waitNoflwJu.setDaibanUrl("/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html");//待办url?id="+meetingNoticeBack.getId()+"&resId="+ConfigConsts.SYSTEM_ID  必填
					waitNoflwJu.setTitle(meetingNotice.getNoticeName());//待办显示标题
					waitNoflwJu.setTableId(meetingNoticeBack.getId());//业务表id
					waitNoflwJu.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu.setSourceId(meetingNoticeBack.getId());//业务id
					waitNoflwJu.setAttr1("");//预留字段1
					waitNoflwJu.setAttr2("");//预留字段2
					waitNoflwJu.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu, "会议通知",meetingNoticeBack.getMeetingName(),"/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
					SysWaitNoflow waitNoflwJu2 = new SysWaitNoflow();
					waitNoflwJu2.setReceiveDeptName(deptJus[i]);//接收人部门
					waitNoflwJu2.setReceiveDeptId(deptJuIDs[i]);//接收人部门id 必填
					waitNoflwJu2.setReceiveUserId("");//接受人id
					waitNoflwJu2.setReceiveUserName("");//接受人name
					waitNoflwJu2.setRolesNo("D012");//接受业务角色
					waitNoflwJu2.setOpName("会议通知");//操作类型
					waitNoflwJu2.setDaibanUrl("/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html");//待办url  必填
					waitNoflwJu2.setTitle(meetingNotice.getNoticeName());//待办显示标题
					waitNoflwJu2.setTableId(meetingNoticeBack.getId());//业务表id
					waitNoflwJu2.setTableName("HYGL_MEETING_NOTICE");//业务表名
					waitNoflwJu2.setSourceId(meetingNoticeBack.getId());//业务id
					waitNoflwJu2.setAttr1("");//预留字段1
					waitNoflwJu2.setAttr2("");//预留字段2
					waitNoflwJu2.setAttr3("");//预留字段3
					sysWaitNoflowservice.saveWaitNoflow(waitNoflwJu2, "会议通知",meetingNoticeBack.getMeetingName(),"/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
			}
			}
			if(!"".equals(deptIDs[0])&&deptIDs[0]!=null){
			for(int i=0;i<deptIDs.length;i++){
				MeetingNoticeBack meetingNoticeBack2 = new MeetingNoticeBack();
				meetingNoticeBack2.setAttendDept(depts[i]);
				meetingNoticeBack2.setAttendDeptId(deptIDs[i]);
				meetingNoticeBack2.setCreTime(creTime);
				meetingNoticeBack2.setFeedbackTime(meetingNotice.getResponseTime());
				meetingNoticeBack2.setFeedbackType(meetingNotice.getFankuiType());
				meetingNoticeBack2.setIsFeedback(meetingNotice.getIsFankui());
				meetingNoticeBack2.setMeetingName(meetingNotice.getNoticeName());
				meetingNoticeBack2.setMeetingApplyId(meetingNotice.getApplyId());
				meetingNoticeBack2.setMeetingBeginTime(meetingNotice.getStartTime());
				meetingNoticeBack2.setMeetingContent(meetingNotice.getContents());
				meetingNoticeBack2.setMeetingEndTime(meetingNotice.getEndTime());
				meetingNoticeBack2.setMeetingRoom(meetingNotice.getMeetingRoom());
				meetingNoticeBack2.setMeetingRoomPlace(meetingNotice.getMeetingPlace());
				meetingNoticeBack2.setNoticeId(meetingNotice.getId());
				meetingNoticeBack2.setAttendDeptType("1");
				if("0".equals(meetingNotice.getIsFankui())){
					meetingNoticeBack2.setState("2");
				}else{
					meetingNoticeBack2.setState("0");
				}
				meetingNoticeBack2 = meetingNoticeBackService.saveForm(meetingNoticeBack2);
				//发送非流程待办
				SysWaitNoflow waitNoflw = new SysWaitNoflow();
				waitNoflw.setReceiveDeptName(depts[i]);//接收人部门
				waitNoflw.setReceiveDeptId(deptIDs[i]);//接收人部门id 必填
				waitNoflw.setReceiveUserId("");//接受人id
				waitNoflw.setReceiveUserName("");//接受人name
				waitNoflw.setRolesNo("D011");//接受业务角色
				waitNoflw.setOpName("会议通知");//操作类型
				waitNoflw.setDaibanUrl("/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html");//待办url  必填
				waitNoflw.setTitle(meetingNotice.getNoticeName());//待办显示标题
				waitNoflw.setTableId(meetingNoticeBack2.getId());//业务表id
				waitNoflw.setTableName("HYGL_MEETING_NOTICE");//业务表名
				waitNoflw.setSourceId(meetingNoticeBack2.getId());//业务id
				waitNoflw.setAttr1("");//预留字段1
				waitNoflw.setAttr2("");//预留字段2
				waitNoflw.setAttr3("");//预留字段3
				sysWaitNoflowservice.saveWaitNoflow(waitNoflw, "会议通知",meetingNoticeBack2.getMeetingName(),"/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack2.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
				SysWaitNoflow waitNoflw2 = new SysWaitNoflow();
				waitNoflw2.setReceiveDeptName(depts[i]);//接收人部门
				waitNoflw2.setReceiveDeptId(deptIDs[i]);//接收人部门id 必填
				waitNoflw2.setReceiveUserId("");//接受人id
				waitNoflw2.setReceiveUserName("");//接受人name
				waitNoflw2.setRolesNo("C010");//接受业务角色
				waitNoflw2.setOpName("会议通知");//操作类型
				waitNoflw2.setDaibanUrl("/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html");//待办url  必填
				waitNoflw2.setTitle(meetingNotice.getNoticeName());//待办显示标题
				waitNoflw2.setTableId(meetingNoticeBack2.getId());//业务表id
				waitNoflw2.setTableName("HYGL_MEETING_NOTICE");//业务表名
				waitNoflw2.setSourceId(meetingNoticeBack2.getId());//业务id
				waitNoflw2.setAttr1("");//预留字段1
				waitNoflw2.setAttr2("");//预留字段2
				waitNoflw2.setAttr3("");//预留字段3
				sysWaitNoflowservice.saveWaitNoflow(waitNoflw2, "会议通知",meetingNoticeBack2.getMeetingName(),"/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack2.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
				}
			}
		}
		}else{
			if("1".equals(meetingNotice.getStatus())){
				String [] deptIDs = meetingNotice.getAttendDeptId().split(",");
				String [] depts = meetingNotice.getAttendDept().split(",");
				String [] deptJuIDs = meetingNotice.getAttendDeptJuId().split(",");
				String [] deptJus = meetingNotice.getAttendDeptJu().split(",");
				System.out.println("".equals(deptJuIDs[0]));
				if(!"".equals(deptJuIDs[0])&&deptJuIDs[0]!=null){
				for(int i=0;i<deptJuIDs.length;i++){
					MeetingNoticeBack meetingNoticeBack = new MeetingNoticeBack();
					meetingNoticeBack.setAttendDept(deptJus[i]);
					meetingNoticeBack.setAttendDeptId(deptJuIDs[i]);
					meetingNoticeBack.setCreTime(creTime);
					meetingNoticeBack.setFeedbackTime(meetingNotice.getResponseTime());
					meetingNoticeBack.setFeedbackType(meetingNotice.getFankuiType());
					meetingNoticeBack.setIsFeedback(meetingNotice.getIsFankui());
					meetingNoticeBack.setMeetingName(meetingNotice.getNoticeName());
					meetingNoticeBack.setMeetingApplyId(meetingNotice.getApplyId());
					meetingNoticeBack.setMeetingBeginTime(meetingNotice.getStartTime());
					meetingNoticeBack.setMeetingContent(meetingNotice.getContents());
					meetingNoticeBack.setMeetingEndTime(meetingNotice.getEndTime());
					meetingNoticeBack.setMeetingRoom(meetingNotice.getMeetingRoom());
					meetingNoticeBack.setMeetingRoomPlace(meetingNotice.getMeetingPlace());
					meetingNoticeBack.setNoticeId(meetingNotice.getId());
					meetingNoticeBack.setAttendDeptType("0");
					if("0".equals(meetingNotice.getIsFankui())){
						meetingNoticeBack.setState("2");
					}else{
						meetingNoticeBack.setState("0");
					}
					meetingNoticeBack = meetingNoticeBackService.saveForm(meetingNoticeBack);
					List<UserInfo> userListC = new ArrayList<UserInfo>();
					List<UserInfo> userListD = new ArrayList<UserInfo>();
					String userids = "";
					//部门下业务角色
			        String ReceiveDeptIds = "";
			        JSONObject deptJSON = userInfoService.getAllDeptBySuperId(deptJuIDs[i]);
			        JSONArray jsonArray = deptJSON.getJSONArray("data");
			        List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
			        for(Dept item :list){
			            ReceiveDeptIds +=item.getDeptid()+",";
			        }
			        userListC = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,"C011");
			        userListD = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,"D012");
			        for (UserInfo user:userListC){
			            userids += String.valueOf(user.getUid())+",";
			        }
			        for (UserInfo user:userListD){
			            userids += String.valueOf(user.getUid())+",";
			        }
			        String[] userIdArray = userids.split(",");
			        boolean flag = false;
	                for (String userid : userIdArray) {
	                    flag = sendMsgsByUid(userid, "会议通知", "【"+meetingNoticeBack.getMeetingName()+"】会议无需反馈，请按时参会。", "/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
	                }
				}
				}
				if(!"".equals(deptIDs[0])&&deptIDs[0]!=null){
				for(int i=0;i<deptIDs.length;i++){
					MeetingNoticeBack meetingNoticeBack = new MeetingNoticeBack();
					meetingNoticeBack.setAttendDept(depts[i]);
					meetingNoticeBack.setAttendDeptId(deptIDs[i]);
					meetingNoticeBack.setCreTime(creTime);
					meetingNoticeBack.setFeedbackTime(meetingNotice.getResponseTime());
					meetingNoticeBack.setFeedbackType(meetingNotice.getFankuiType());
					meetingNoticeBack.setIsFeedback(meetingNotice.getIsFankui());
					meetingNoticeBack.setMeetingName(meetingNotice.getNoticeName());
					meetingNoticeBack.setMeetingApplyId(meetingNotice.getApplyId());
					meetingNoticeBack.setMeetingBeginTime(meetingNotice.getStartTime());
					meetingNoticeBack.setMeetingContent(meetingNotice.getContents());
					meetingNoticeBack.setMeetingEndTime(meetingNotice.getEndTime());
					meetingNoticeBack.setMeetingRoom(meetingNotice.getMeetingRoom());
					meetingNoticeBack.setMeetingRoomPlace(meetingNotice.getMeetingPlace());
					meetingNoticeBack.setNoticeId(meetingNotice.getId());
					meetingNoticeBack.setAttendDeptType("1");
					if("0".equals(meetingNotice.getIsFankui())){
						meetingNoticeBack.setState("2");
					}else{
						meetingNoticeBack.setState("0");
					}
					meetingNoticeBack = meetingNoticeBackService.saveForm(meetingNoticeBack);
					List<UserInfo> userListC = new ArrayList<UserInfo>();
					List<UserInfo> userListD = new ArrayList<UserInfo>();
					String userids = "";
					//部门下业务角色
			        String ReceiveDeptIds = "";
			        JSONObject deptJSON = userInfoService.getAllDeptBySuperId(deptIDs[i]);
			        JSONArray jsonArray = deptJSON.getJSONArray("data");
			        List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
			        for(Dept item :list){
			            ReceiveDeptIds +=item.getDeptid()+",";
			        }
			        userListC = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,"D011");
			        userListD = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,"C010");
			        for (UserInfo user:userListC){
			            userids += String.valueOf(user.getUid())+",";
			        }
			        for (UserInfo user:userListD){
			            userids += String.valueOf(user.getUid())+",";
			        }
			        String[] userIdArray = userids.split(",");
			        boolean flag = false;
	                for (String userid : userIdArray) {
	                    flag = sendMsgsByUid(userid, "会议通知", "【"+meetingNoticeBack.getMeetingName()+"】会议无需反馈，请按时参会。", "/modules/zhbg/hygl/meetingNoticeBack/meetingFeedbackEditForm.html?id="+meetingNoticeBack.getId()+"&resId="+ConfigConsts.SYSTEM_ID);
	                }
				}
				}
			}
			
		}
		return meetingNotice;
	}

	@SuppressWarnings("unused")
	private static boolean sendMsgsByUid(String userId,String subject,String content,String messageURL){
        boolean flag = false;
        if(StringUtils.isNotBlank(userId)){
           if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
                NotityMessage message = new NotityMessage();
                message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
                message.setSubject(subject);//标题
                message.setContent(content);//内容
                message.setSendTime(new Date());//发送时间
                message.setAcceptTime(new Date());//接收时间
                message.setAccepterId(userId);//接收人id
                message.setStatus("0");//状态
                message.setType("3");//类型  1手机    2邮箱   3栈内
                message.setMsgUrl(messageURL);//消息链接
                NotityService notityService =  (NotityService) SpringBeanUtils.getBean("notityService");
                notityService.add(message);
                flag = true;
           }else{
                String param = "sendType=3&sendContent="+content+"&uids="+userId+"&subId=" +ConfigConsts.SYSTEM_ID+
                        "&msgUrl="+messageURL+"&title="+subject+"&appSecret=af2fff3bda2043a991018689848793b4";
                String resJson = HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL+"/sendMsgsByUid",param);
                flag = true;
           }
        }
        return flag;
    }
    
	/**
	 * 查询通知列表
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
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status) {
		//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	querySql(sb,startDate,endDate,meetingName,status);
    	sb.append(")");
    	Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
    	//查询分页数据
		 String pageSql = pageSql(pageable,startDate,endDate,meetingName,status);
		List<MeetingNoticeInfo> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<MeetingNoticeInfo>(){
			@Override
			public MeetingNoticeInfo mapRow(ResultSet result, int arg1) throws SQLException {
				MeetingNoticeInfo info = new MeetingNoticeInfo();
				info.setId(result.getString("id"));//backNum
				String backNumStr = result.getString("backNum");
				if(StringUtils.isNotBlank(backNumStr)) {
					info.setActualAttendDeptNum(Integer.valueOf(backNumStr));
				}else {
					info.setActualAttendDeptNum(0);
				}
				
				info.setAttentionItem(result.getString("attentionItem"));
				info.setMeetingRoom(result.getString("meetingRoom"));//会议室名称
				info.setMeetingName(result.getString("meetingName"));//会议室标题
				info.setPublishState(result.getString("publishState"));//座位的发布状态
				info.setStatus(result.getString("status"));//状态
				info.setMeetingTime(result.getString("meetingTime"));
				info.setAttendDeptId(result.getString("attendDeptId"));
				info.setAttendDeptJuId(result.getString("attendDeptJuId"));
			
				
				info.setIsFankui(result.getString("isFankui"));
				info.setFankuiType(result.getString("fankuiType"));;
				info.setMeetingroomId(result.getString("meetingroomId"));
				if (ConfigConsts.START_FLAG.equals(result.getString("status"))) {
					info.setCz(CommonConstants.OPTION_UPDATE);
				}else{
					if(ConfigConsts.START_FLAG.equals(result.getString("isFankui"))){
						//不需要反馈的用通知，也不需要反馈
						info.setCz(CommonConstants.OPTION_VIEW);
					}else{
						info.setCz(CommonConstants.OPTION_ADD);
					}
				}
				
				//查询会议通知有几条会议反馈
				//info.setActualAttendDeptNum(Integer.valueOf(result.getString("backNum")));
				//info.setShouldAttendDeptNum(Integer.valueOf(result.getString("totalNum")));
				
				return info;
			}
			
		});
		return new PageImpl(total,listData);
	}
	
	
	private String pageSql(Pageable pageable, String startDate, String endDate, String meetingName, String status) {
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,startDate,endDate,meetingName,status);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}

	private void querySql(StringBuilder sb, String startDate, String endDate, String meetingName,String status) {
		sb.append(" select t.id,t1.apply_title meetingName, t.status,t1.meeting_time meetingTime, t4.meetingRoom,t5.meetingroomId,t.ATTENTION_ITEM attentionItem, ");
		sb.append(" t.IS_FANKUI isFankui,t2.seatNum,t3.backNum,t.publish_state publishState,t.fankui_type fankuiType,t.attend_dept_id attendDeptId,t.attend_dept_ju_id attendDeptJuId ");
		sb.append(" from HYGL_MEETING_NOTICE t ");
		sb.append(" left outer join hygl_meeting_apply t1  on t.apply_id = t1.id ");
		sb.append("  left outer join (select t.noticeid,count(1) seatNum from hygl_seat t where t.state !=4 group by t.noticeid )t2 on t.id=t2.noticeid ");
		sb.append(" left outer join (select t.NOTICE_ID,count(1) backNum from hygl_notice_back t where  t.state='1' group by t.NOTICE_ID)t3 on t.id=t3.NOTICE_ID ");
		sb.append(" left outer join (select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRoom,t2.id  from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id ) t4 on t4.id = t1.id ");
		sb.append(" left outer join (select to_char(wm_concat(distinct t1.id))  meetingroomId,t2.id from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id)t5 on t5.id = t1.id ");
		//sb.append(" left outer join (select t.NOTICE_ID,count(1) totalNum from hygl_notice_back t where  t.visible='1' group by t.NOTICE_ID)t6 on t.id=t6.NOTICE_ID ");
		sb.append(" where  t.visible='1' and t.cre_user_id='" + UserUtil.getCruUserId()+"'" );
		// 起始时间
		if (StringUtils.isNotBlank(startDate)) {
			sb.append(" and SUBSTR(t1.meeting_time,23,10)>='" + startDate + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sb.append(" and SUBSTR(t1.meeting_time,0,10)<='" + endDate + "'");
		}
		// 会议名称
		if (StringUtils.isNotBlank(meetingName)) {
			sb.append(" and t1.apply_title like '%" + meetingName + "%'");
		}
		// 状态
		if (StringUtils.isNotBlank(status)) {
			sb.append(" and  t.status = '" + status + "'");
		}
		sb.append(" order by t.cre_time desc ");
		
	}

	public PageImpl getPageListDraft1(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" select new com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingNoticeInfo(t.attentionItem,f.meetingroomId,f.applyTitle as meetingName,t.publishState,"
				+ "f.meetingroomName as meetingRoom,f.meetingStartTime as startTime,"
				+ "f.meetingEndTime as endTime,f.meetingStartDate as startDate,f.meetingEndDate as endDate,"
				+ " t.id, t.applyId,t.attendDept,t.isFankui,t.fankuiType,t.responseTime,t.actualFankuiTime, t.attendDeptId,t.attendDeptJu,t.attendDeptJuId,t.noticeName,");
		querySql.append(" t.status, t.contents, t.remark ,t.treeId,m.location as meetingPlace)");
		querySql.append(" from MeetingNoticeInfo t ,MeetingApplyInfo f,MeetingRoomInfo m");
		querySql.append("	where t.applyId = f.id and m.id = f.meetingroomId and t.creUserId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		// 起始时间
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and f.meetingStartDate <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and f.meetingEndDate >= ? ");
			para.add(startDate);
		}
		// 会议名称
		if (StringUtils.isNotBlank(meetingName)) {
			querySql.append("   and t.noticeName like ?");
			para.add("%"+meetingName+"%");
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

		Page<MeetingNoticeInfo> page = meetingNoticeDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<MeetingNoticeInfo> content = page.getContent();
		for (MeetingNoticeInfo kqglLeaveInfo : content) {
			StringBuilder sb = new StringBuilder(" select  ");
			sb.append(" (select count(1) from hygl_seat t where t.noticeid='" + kqglLeaveInfo.getId() + "' and  t.state !=4) seatNum, ");
			sb.append(" (select count(1) from hygl_notice_back t where  t.state='1' and t.NOTICE_ID='" + kqglLeaveInfo.getId() + "' ) backNum ");
			sb.append("  from dual ");
			Map<String,Object> map = jdbcTemplate.queryForMap(sb.toString());
			//查询会议通知有几条会议反馈
			kqglLeaveInfo.setActualAttendDeptNum(Integer.valueOf(map.get("BACKNUM").toString()));
			kqglLeaveInfo.setSeatNum(Integer.valueOf(map.get("SEATNUM").toString()));
			//会议通知起草保存后  是反馈的可以修改 不是反馈的不可以修改  根据需求修改成  只要是草稿状态都可以修改
//			if(ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getIsFankui())){
//				kqglLeaveInfo.setCz(CommonConstants.OPTION_VIEW);
//			}else{
				if (ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getStatus())) {
					kqglLeaveInfo.setCz(CommonConstants.OPTION_UPDATE);
					
				}else{
					if(ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getIsFankui())){
						//不需要反馈的用通知，也不需要反馈
						kqglLeaveInfo.setCz(CommonConstants.OPTION_VIEW);
					}else{
						kqglLeaveInfo.setCz(CommonConstants.OPTION_ADD);
					/*if(cnt==kqglLeaveInfo.getShouldAttendDeptNum()){
							//反馈完成，显示排座按钮
							kqglLeaveInfo.setCz(CommonConstants.OPTION_ADD+","+CommonConstants.OPTION_SEE_BACK);
							if("1".equals(kqglLeaveInfo.getPublishState())){
								//已发布，显示打印按钮
								kqglLeaveInfo.setCz(CommonConstants.OPTION_ADD+","+CommonConstants.OPTION_SEE_BACK+","+CommonConstants.OPTION_PUBLISH);
							}
						}else{
							kqglLeaveInfo.setCz(CommonConstants.OPTION_ADD);
						}*/
					}
				}
			//}
			
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id获取通知信息
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月26日 下午4:22:14
	 * @param id
	 * @return
	 */
	@Override
	public MeetingNoticeInfo getById(String id) {
		StringBuilder querySql = new StringBuilder();
		//3.9日修改前的sql如下：
		/*querySql.append("select f.apply_title as MEETING_NAME,"
				+ "m.MEETINGROOM_NAME as MEETING_ROOM,f.MEETING_TIME as MEETING_TIME,"
				+ " t.id, t.APPLY_ID,t.ATTEND_DEPT,t.IS_FANKUI,t.FANKUI_TYPE,t.RESPONSE_TIME,t.ACTUAL_FANKUI_TIME, t.ATTEND_DEPT_ID,t.ATTEND_DEPT_JU,t.ATTEND_DEPT_JU_ID,t.notice_name,");
		querySql.append(" t.status, t.contents, t.remark,t.tree_id,m.location as meeting_place");
		querySql.append(" from HYGL_MEETING_NOTICE t ,HYGL_MEETING_APPLY f,HYGL_MEETINGROOM m,HYGL_MEETINGROOM_USEINFO u");
		querySql.append(" where t.APPLY_ID = f.id and f.id = u.apply_id and m.id = u.meetingroom_id and t.id = ?");*/
		//3.9日更改的sql如下：
		querySql.append("select f.apply_title as MEETING_NAME,f.MEETING_TIME as MEETING_TIME,"
				+ "	t.id, t.APPLY_ID,t.ATTEND_DEPT,t.IS_FANKUI,t.FANKUI_TYPE,t.RESPONSE_TIME,t.ACTUAL_FANKUI_TIME, t.ATTEND_DEPT_ID,t.ATTEND_DEPT_JU,t.ATTEND_DEPT_JU_ID,t.notice_name,");
		querySql.append(" t.status, t.contents, t.remark,t.tree_id");
		querySql.append(" from HYGL_MEETING_NOTICE t ,HYGL_MEETING_APPLY f");
		querySql.append(" where t.APPLY_ID = f.id and t.id = ?");
		MeetingNoticeInfo meetingNoticeInfo = jdbcTemplate.queryForObject(querySql.toString(),new Object[]{id},new BeanPropertyRowMapper<MeetingNoticeInfo>(MeetingNoticeInfo.class));
		return meetingNoticeInfo;
	}
	/**
	 * 删除通知信息
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月26日 下午4:22:31
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String id)throws Exception {
		int result = 0;
		String jpql = "update MeetingNoticeInfo t set t.visible = ? where t.id = ?";
		try {
			result = meetingNoticeDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新通知状态
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月26日 下午4:22:48
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public MeetingNoticeInfo updateSubFlag(String id, String subflag) {
		MeetingNoticeInfo meetingNotice = getById(id);
		meetingNotice.setSubflag(subflag);
		return meetingNoticeDao.save(meetingNotice);
	}
	
	/**
	 * 判断是否处室或部门收发人员
	 * TODO 
	 * @author 张建明
	 * @Date 2018年8月24日 下午1:53:45
	 * @param deptIds
	 * @param deptNames
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public JSONObject hasConfidentiUser(String deptIds,String deptNames) throws Exception {
		JSONObject json = new JSONObject();
		boolean f = false;
		json.put("flag", "0");
		StringBuilder  deptNoUser = new StringBuilder();
		try {
			String[] deptId = mapper.readValue(deptIds, String[].class);
			String[] deptName = mapper.readValue(deptNames, String[].class);
			String ids = StringUtils.join(deptId,",");
			ids = CommonUtils.commomSplit(ids);
			String sql = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,12) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 12) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+ids+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in('C010','D011'))and d.status = '"+ZBGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";
			/*String sql = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,12) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 12) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+ids+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in('C010','D011'))and d.status = '"+ZBGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";*/
			JSONObject result = userInfoService.getDateBySql(sql);
			List<DeptVo> list = new ArrayList<>();
			JSONArray arr = result.getJSONArray("data");
			list = JSONArray.toList(arr, DeptVo.class);
			String sqlJu = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,8) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 8) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+ids+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in('C011','D012'))and d.status = '"+ZBGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";
			/*String sqlJu = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,8) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 8) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+ids+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in('C011','D012'))and d.status = '"+ZBGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";*/
			JSONObject resultJu = userInfoService.getDateBySql(sqlJu);
			List<DeptVo> listJu = new ArrayList<>();
			JSONArray arrJu = resultJu.getJSONArray("data");
			listJu = JSONArray.toList(arrJu, DeptVo.class);
			list.addAll(listJu);
				for (String n : deptName) {
					n = n.replace("'", "").trim();
					for (DeptVo deptVo : list) {
						if(n.equals(deptVo.getDeptname())){
							f = true;
							break;
						}
					}
				if(!f){
					deptNoUser.append(n+",");
				}
				f = false;
			}
			json.put("flag", "1");
			json.put("data", JSONArray.fromObject(list));
			if(deptNoUser.length()!=0){
				json.put("deptNames",deptNoUser.toString());
				json.put("flag", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "解析异常");
		}
		return json;
	}
	
	/**
	 * 更改通知的注意事项
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年11月7日 上午9:40:18
	 * @param meetingNoticeId
	 * @param attentionItem
	 * @return
	 */
	@Override
	public void updateAttentionItem(String meetingNoticeId, String attentionItem) {
			String updateSql = " update HYGL_MEETING_NOTICE set ATTENTION_ITEM='" + attentionItem + "' where id='" + meetingNoticeId +"' ";
			jdbcTemplate.execute(updateSql);
		
		
	}

	/**
	 * TODO 获取已经通过的会议的全部信息（带分页）
	 * @author 李颖洁  
	 * @date 2019年3月21日下午2:00:02
	 * @param pageable
	 * @param pageImpl
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param meeetingTitle 会议标题
	 * @param meetingType 会议类型
	 * @param hostDeptId 主办单位id
	 * @param meetingRoomId 会议室id
	 * @return JSONObject
	 */
	@Override
	public JSONObject getPageListMeetingAllInfo(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			 String meeetingTitle,String meetingType,String hostDeptId,String meetingRoomId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		querySql.append("select a.id,a.host_dept_name hostDeptName,a.host_dept_id hostDeptId,a.apply_title applyTitle,a.meeting_type meetingType,t1.meetingRoom meetingRoomName,t1.meetingRoomId,");
		querySql.append(" a.meeting_time meetingTime,st.meeting_service_name meetingServiceName,st.un_backed_count remark,st.backed_count status,");
		querySql.append(" sn.notice_id noticeId,sn.attend_feedback_count attendFeedbackNum,sn.attend_count attendNum,sn.not_feedback notFeedback,a.cre_user_id,nt.fankui_type fankuiType");
		querySql.append(" from HYGL_MEETING_APPLY a ");
		querySql.append(" 	left join (select t.meetingroom_apply_id,to_char(str_sum(t.meeting_service_name || ',')) meeting_service_name,");
		querySql.append(" sum(case when t.status = '1' then 1 else 0 end) as un_backed_count,");
		querySql.append(" count( t.id) as backed_count");
		querySql.append(" from hygl_meeting_service_task t where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append(" group by t.meetingroom_apply_id) st on st.meetingroom_apply_id = a.id");
		querySql.append(" 	left join (select to_char(wm_concat(distinct t1.meetingroom_name)) meetingRoom,to_char(str_sum( distinct t1.id || ','))  meetingRoomId,t2.id  from hygl_meetingroom_useInfo t,HYGL_MEETINGROOM t1,hygl_meeting_apply t2 "
				+ "where  t.meetingroom_id=t1.id and t.apply_id=t2.id group by t2.id) t1 on t1.id =a.id");
		querySql.append(" 	left join (select t.notice_id,t.meeting_apply_id,sum(case when t.state = '1' then 1 else 0 end) as attend_feedback_count,count( t.notice_id) as attend_count,sum(case when t.state = '2' then 2 else 0 end) as not_feedback	"
				+ "from hygl_notice_back t   group by t.notice_id,t.meeting_apply_id ) sn on sn.meeting_apply_id = a.id");
		querySql.append("   left join hygl_meeting_notice nt on nt.apply_id = a.id");
		querySql.append("   where  a.visible = '1' and a.subflag = '5'");
		String roleNo = UserUtil.getCruUserRoleNo();
		if(roleNo.indexOf("D101")==-1){
			querySql.append("   and a.cre_user_id = '"+UserUtil.getCruUserId()+"'");
		}
		//会议室标题
		if (StringUtils.isNotBlank(meeetingTitle)) {
			querySql.append("   and a.apply_title like '%"+meeetingTitle+"%'");
		}
		// 主办单位
		if (StringUtils.isNotBlank(hostDeptId)) {
			querySql.append("   and a.host_dept_id ='"+hostDeptId+"'");
		}
		//日期
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and substr(a.meeting_time,0,10) <= '"+endDate+"'");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and substr(a.meeting_time,23,10) >= '"+startDate+"'");
		}
		// 会议类型
		if (StringUtils.isNotBlank(meetingType)) {
			querySql.append("   and a.meeting_type = '"+meetingType+"'");
		}
		// 会议室id
		if (StringUtils.isNotBlank(meetingRoomId)) {
			querySql.append("   and t1.meetingRoomId like '%"+meetingRoomId+"%'");
		}
		//排序
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by substr(a.meeting_time,0,10) desc,a.cre_time desc");
		}else{
			querySql.append("  order by a."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+" ");
		}
		
		//分页
		String sql = "";
		String sql1 = "";
		sql1 = "select s.*,rownum rn from ("+querySql.toString()+") s";
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		sql = "select s.* from ("+sql1+"  where rownum<="+after+") s where rn>="+pre;
		List<MeetingInfo> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(MeetingInfo.class));
		if(list!=null&&!list.isEmpty()) {
			StringBuilder totalSql = new StringBuilder();
			totalSql.append("select count(1) total from ("+querySql.toString()+")");
			Map<String,Object> map = jdbcTemplate.queryForMap(totalSql.toString());
			data.put("total", map.get("total"));
		}else{
			data.put("total", "0");
		}
		data.put("rows", list);
		json.put("flag", "1");
		json.put("data", data);
        return json;
	}


}
