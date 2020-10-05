package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.CountNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.services.MeetingNoticeBackService;
import com.sinosoft.sinoep.urge.controller.SysUrgeController;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议通知反馈controller
 * TODO 
 * @author 张建明
 * @Date 2018年7月19日 上午10:40:25
 */
@Controller
@RequestMapping("/zhbg/hygl/meetingNoticeBack")
public class MeetingNoticeBackController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingNoticeBackService meetingNoticeBackService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
    private NotifyController notifyController;
	
    @Autowired
    private SysUrgeController sysUrgeController;
    
	/**
	 * 保存表单
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:16:34
	 * @param meetingNotice
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存会议通知反馈")
	@ResponseBody
	@RequestMapping("saveForm")
	public MeetingNoticeBack saveForm(MeetingNoticeBack meetingNoticeBack) {
		try {
			meetingNoticeBack = meetingNoticeBackService.saveForm(meetingNoticeBack);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return meetingNoticeBack;
	}

	/**
	 * 获取会议通知列表
	 * 
	 * @author 郝灵涛
	 * @Date 2018年7月9日 下午7:31:22
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询通知反馈列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String meetingName, String status,String id) {
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		return meetingNoticeBackService.getPageListDraft1(pageable, pageImpl, startDate, endDate, meetingName, status);
	}

	/**
	 * 获取已反馈未反馈会议通知反馈列表 
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月30日 上午10:42:51
	 * @param pageImpl
	 * @param timeRange
	 * @param meetingName
	 * @param status
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询已反馈未反馈通知反馈列表")
	@ResponseBody
	@RequestMapping("getBackList")
	public PageImpl getBackPageList(PageImpl pageImpl,String status,String id) {
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl=meetingNoticeBackService.getBackedPageList(pageable, pageImpl, status,id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 获取反馈数量 
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月30日 上午10:42:51
	 * @param pageImpl
	 * @param timeRange
	 * @param meetingName
	 * @param status
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询已反馈未反馈通知反馈列表")
	@ResponseBody
	@RequestMapping("getBackCount")
	public JSONObject getBackCount(PageImpl pageImpl,String status,String id) {
		JSONObject json = new JSONObject();
		try {
			CountNoticeBack countNoticeBack = meetingNoticeBackService.getBackCount(id);
			json.put("flag", "1");
			json.put("data", countNoticeBack);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 修改
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:15:51
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "更新会议通知反馈")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		MeetingNoticeBack meetingNoticeBack = null;
		try {
			meetingNoticeBack = meetingNoticeBackService.getById1(id);
			json.put("flag", "1");
			json.put("data", meetingNoticeBack);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 删除
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:16:04
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除会议通知反馈")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = meetingNoticeBackService.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 更新状态
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:16:14
	 * @param id
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id, String subflag) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingNoticeBackService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	 /**
	  * 
	  * TODO 催办，发送消息，同时保存催催办记录
	  * @author 张建明
	  * @Date 2018年9月18日 下午5:03:59
	  * @param deptIds
	  * @param deptNames
	  * @param pressContent
	  * @param yearMonth
	  * @return
	  */
    @SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	@LogAnnotation(opType = OpType.SAVE,opName = "催办，发送消息，同时保存催催办记录")
    @RequestMapping("urge")
    @ResponseBody 
    public JSONObject press(String Id, String attendDeptId, String pressContent,String pressTitle) {
        JSONObject json = new JSONObject();
        try {
        	 
             DeptAllInfo deptAllInfo = userInfoService.getDeptInfoByDeptId(attendDeptId);
             if(deptAllInfo.getTreeId().length()==8){//参会单位为局级
            	 String userids = "";
                 List<UserInfo> userList = new ArrayList<UserInfo>();
                 String ReceiveDeptIds = "";
                 JSONObject deptJSON = userInfoService.getAllDeptBySuperId(attendDeptId);
            	 JSONArray jsonArray = deptJSON.getJSONArray("data");
     			 List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
                  for(Dept item :list){
                      ReceiveDeptIds +=item.getDeptid()+",";
                  }
                  userList = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,"D012,C011");
                 // userList.addAll(userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,""));
                  for (UserInfo user:userList){
                      userids += String.valueOf(user.getUid())+",";
                  }
                  String[] userIdArray = userids.split(",");
                  List<String> listUserIds = new ArrayList<String>();
                  for (int i=0; i<userIdArray.length; i++) {
                    if(!listUserIds.contains(userIdArray[i])) {
                    	listUserIds.add(userIdArray[i]);
                    }
                  }
                  userIdArray = listUserIds.toArray(new String[1]);
                     for(String userid : userIdArray){
                         NotityMessage notityMessage = new NotityMessage();
                         notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);  //系统ID
                         notityMessage.setSubject(pressTitle); //消息主题
                         notityMessage.setContent(pressContent); //消息内容
                         notityMessage.setAccepterId(userid);  //接收人ID
                         notityMessage.setStatus("0");  //0未读；1已读
                         notityMessage.setType("3");  //3站内消息
                         //发送消息
                         notifyController.save(notityMessage);
                     }
                     userids = userids.substring(0,userids.length()-1);  //去掉最后一个逗号
                     //催办记录
                     SysUrge sysUrge = new SysUrge();
                     sysUrge.setSubject(pressTitle);
                     sysUrge.setContent(pressContent);
                     sysUrge.setModuleType("hygl_tzfk");
                     sysUrgeController.saveUrge(sysUrge,userids);
                     json.put("flag", "1");
             }else{//参会单位为局级以下
            	 String useridsJu = "";
                 List<UserInfo> userListJu = new ArrayList<UserInfo>();
                 String ReceiveDeptIdsJu = "";
                 JSONObject deptJSONJu = userInfoService.getAllDeptBySuperId(attendDeptId);
            	 JSONArray jsonArrayJu = deptJSONJu.getJSONArray("data");
     			List<Dept> listJu = jsonArrayJu.toList(jsonArrayJu,Dept.class);
                  for(Dept item :listJu){
                      ReceiveDeptIdsJu +=item.getDeptid()+",";
                  }
                  userListJu = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIdsJu,"D011");
                  userListJu.addAll(userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIdsJu,"C010"));
                  for (UserInfo user:userListJu){
                      useridsJu += String.valueOf(user.getUid())+",";
                  }
                  String[] userIdArrayJu = useridsJu.split(",");
                  List<String> listUserIdJus = new ArrayList<String>();
                  for (int i=0; i<userIdArrayJu.length; i++) {
                    if(!listUserIdJus.contains(userIdArrayJu[i])) {
                    	listUserIdJus.add(userIdArrayJu[i]);
                    }
                  }
                  userIdArrayJu = listUserIdJus.toArray(new String[1]);
                     for(String userid : userIdArrayJu){
                         NotityMessage notityMessage = new NotityMessage();
                         notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);  //系统ID
                         notityMessage.setSubject(pressTitle); //消息主题
                         notityMessage.setContent(pressContent); //消息内容
                         notityMessage.setAccepterId(userid);  //接收人ID
                         notityMessage.setStatus("0");  //0未读；1已读
                         notityMessage.setType("3");  //3站内消息
                         //发送消息
                         notifyController.save(notityMessage);
                     }
                     useridsJu = useridsJu.substring(0,useridsJu.length()-1);  //去掉最后一个逗号
                     //催办记录
                     SysUrge sysUrge = new SysUrge();
                     sysUrge.setSubject(pressTitle);
                     sysUrge.setContent(pressContent);
                     sysUrge.setModuleType("hygl_tzfk");
                     sysUrgeController.saveUrge(sysUrge,useridsJu);
                     json.put("flag", "1");
             }
             
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", "0");
        }
        return json;
    }
    
}
