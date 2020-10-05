package com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.controller;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.service.MeetingServiceNoticeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 会务服务通知controller
 * TODO 
 * @author 张建明
 * @Date 2018年8月29日 上午11:37:36
 */
 
@Controller
@RequestMapping("zhbg/hygl/meetingServiceNotice")
public class MeetingServiceNoticeController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingServiceNoticeService meetingServiceNoticeService;

	@Autowired
	private NotifyController notifyController;
	
	@Autowired
	private MeetingApplyService meetingApplyService;
	
	/**
	 * 起草会议管理时加载页面
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午9:29:05
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public JSONObject add() {
		JSONObject json = new JSONObject();
		MeetingServiceNotice meetingServiceNotice = new MeetingServiceNotice();
		try {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//新增会议申请时添加起草人，起草人单位
			meetingServiceNotice.setCreUserId(UserUtil.getCruUserId());
			meetingServiceNotice.setCreUserName(UserUtil.getCruUserName());
			meetingServiceNotice.setCreDeptId(UserUtil.getCruDeptId());
			meetingServiceNotice.setCreDeptName(UserUtil.getCruDeptName());
			meetingServiceNotice.setCreTime(creTime);

			json.put("flag", "1");
			json.put("data", meetingServiceNotice);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 获取列表
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:26:22
	 * @param pageImpl
	 * @param
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "HQL语句查询")
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl,String meetingRoom,String meetingName,String timeRange,String meetingServices,String subflag){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = meetingServiceNoticeService.getPageListDraft(pageable,pageImpl,meetingRoom,meetingName,startDate,endDate,meetingServices,subflag);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 获取列表
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:26:22
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "HQL语句查询")
	@ResponseBody
	@RequestMapping("getBackList")
	public PageImpl getBackList(PageImpl pageImpl,String title,String timeRange,String subflag,String meetingRoom,String meetingName,String meetingServices){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = meetingServiceNoticeService.getPageBackList(pageable,pageImpl,title,startDate,endDate,subflag,meetingRoom,meetingName,meetingServices);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 获取已反馈未反馈列表
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:26:22
	 * @param pageImpl
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "HQL语句查询")
	@ResponseBody
	@RequestMapping("getAllList")
	public PageImpl getAllList(PageImpl pageImpl,String meetingroomApplyId,String status){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
				pageImpl = meetingServiceNoticeService.getAllList(pageable,pageImpl,meetingroomApplyId,status);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 获取会务服务项目列表不分页
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询会务服务项目信息列表")
	@RequestMapping("getByMeetingApplyId")
	@ResponseBody
	public JSONObject getByBeetingApplyId(MeetingServiceNotice meetingServiceNotice,String fankunUserId) {
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isNotBlank(fankunUserId)) {
				
				meetingServiceNotice.setContactId(fankunUserId);
			}else {
				meetingServiceNotice.setContactId(UserUtil.getCruUserId());
			}
			
			meetingServiceNotice.setMeetingroomApplyId(meetingServiceNotice.getMeetingroomApplyId());
			List<MeetingServiceNotice> list = meetingServiceNoticeService.getByMeetingApplyId(meetingServiceNotice);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			//JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			//array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", list);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	/**
	 * 保存表单
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:27:59
	 * @param
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(@RequestBody List<MeetingServiceNotice> meetingServiceNotices){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingServiceNotices = meetingServiceNoticeService.saveForm(meetingServiceNotices);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", meetingServiceNotices.size());
			//JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			//array = JSONArray.fromObject(meetingServiceNotices, jsonConfig);
			data.put("rows", meetingServiceNotices);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
	}
	/**
	 * 保存表单
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:27:59
	 * @param
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("fankuiForm")
	public JSONObject fankuiForm(@RequestBody List<MeetingServiceNotice> meetingServiceNotices){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingServiceNotices = meetingServiceNoticeService.fankuiForm(meetingServiceNotices);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", meetingServiceNotices.size());
			//JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			//array = JSONArray.fromObject(meetingServiceNotices, jsonConfig);
			data.put("rows", meetingServiceNotices);
			json.put("data", data);
			MeetingApplyInfo meetingApplyInfo= meetingApplyService.getById(meetingServiceNotices.get(0).getMeetingroomApplyId());
			/*NotityMessage notityMessage = new NotityMessage();
			notityMessage.setAccepterId(meetingApplyInfo.getCreUserId());
			notityMessage.setContent(meetingApplyInfo.getApplyTitle()+"会议需提要供会务服务");
			notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);
			notityMessage.setSubject("会务服务通知");
			updateMessage(notityMessage);*/
			String cruUserid = UserUtil.getCruUserId();
			//发送消息
			String msgUrl="/modules/zhbg/hygl/meetingServiceNotice/meetingServiceNoticeReadOnlyForm.html?id="+meetingApplyInfo.getId()+"&fankunUserId="+UserUtil.getCruUserId()+"&flag=1";
			/*String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			 String param = "sendType=3&sendContent="+meetingApplyInfo.getApplyTitle()+"会议会务服务提供情况&uids="+meetingApplyInfo.getCreUserId()+"&subId=" +ConfigConsts.SYSTEM_ID+
	                    "&msgUrl="+msgUrl+"&title=会务服务通知&appSecret=af2fff3bda2043a991018689848793b4";
	            String resJson = HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL+"/sendMsgsByUid",param);*/
			
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//张三已反馈关于**次会议的会议服务情况
			sendMsgsByUid(meetingApplyInfo.getCreUserId(), "会务服务通知", UserUtil.getCruUserName()+"已反馈了关于"+meetingApplyInfo.getApplyTitle()+"次会议的会务服务提供情况", msgUrl,creTime,null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
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
	/**
	 * TODO 查询消息id
	 * @author 李颖洁  
	 * @date 2018年8月3日上午9:00:07
	 * @param notityMessage
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "更改消息状态和URL")
	@ResponseBody
	@RequestMapping(value="updateMessage")
	public JSONObject updateMessage(NotityMessage notityMessage){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			JSONObject js = meetingServiceNoticeService.queryMessageId(notityMessage);
			String id = js.getJSONArray("data").getJSONObject(0).get("id").toString();
			notifyController.edit(id);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
	} 
	
	/**
	 * TODO 删除数据
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:53:53
	 * @param
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = meetingServiceNoticeService.delete(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:54:07
	 * @param
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		MeetingServiceNotice meetingServiceNotice = null;
		try {
			meetingServiceNotice = meetingServiceNoticeService.getById(id);
			json.put("flag", "1");
			json.put("data", meetingServiceNotice);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 更新业务表流程状态
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:54:17
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingServiceNoticeService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return json;
	}

}
