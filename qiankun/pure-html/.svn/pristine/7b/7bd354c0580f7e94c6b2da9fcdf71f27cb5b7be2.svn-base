package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingNoticeInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.service.MeetingNoticeService;
import net.sf.json.JSONObject;

/**
 * 会议管理中会议通知controller TODO
 * 
 * @author 郝灵涛
 * @Date 2018年7月9日 下午6:51:36
 */
@Controller
@RequestMapping("zhbg/hygl/notice")
public class MeetingNoticeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingNoticeService meetingNoticeService;

	/**
	 * 保存会议通知表单
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:16:34
	 * @param meetingNotice
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存会议通知")
	@ResponseBody
	@RequestMapping("saveForm")
	public MeetingNoticeInfo saveForm(MeetingNoticeInfo meetingNotice) {
		try {
				
				meetingNotice = meetingNoticeService.saveForm(meetingNotice);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return meetingNotice;
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
	@LogAnnotation(opType = OpType.QUERY,opName = "查询通知列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String meetingName, String status) {
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		return meetingNoticeService.getPageListDraft(pageable, pageImpl, startDate, endDate, meetingName, status);
	}

	/**
	 * 修改
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月20日 下午3:15:51
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "更新会议通知")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		MeetingNoticeInfo meetingNotice = null;
		try {
			meetingNotice = meetingNoticeService.getById(id);
			json.put("flag", "1");
			json.put("data", meetingNotice);
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
	@LogAnnotation(opType = OpType.DELETE,opName = "删除会议通知草稿")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = 0;
		try {
			result = meetingNoticeService.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	@LogAnnotation(opType = OpType.UPDATE,opName = "更新状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id, String subflag) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingNoticeService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 判断本部门是否有机要交换员
	 * TODO 
	 * @author 张建明
	 * @Date 2018年8月24日 下午1:52:41
	 * @param deptIds
	 * @param deptNames
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询部门是否有处室或部门收发人员")
	@ResponseBody
	@RequestMapping("hasConfidentiUser")
	public JSONObject hasConfidentiUser(String deptIds,String deptNames) {
		JSONObject json = new JSONObject();
		try {
			json = meetingNoticeService.hasConfidentiUser(deptIds,deptNames);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", "0");
		}
		return json;
	}
	
	@LogAnnotation(opType =OpType.UPDATE, opName = "")
	@ResponseBody
	@RequestMapping("updateAttentionItem")
	public JSONObject updateAttentionItem(String meetingNoticeId,String attentionItem) {
		JSONObject json = new JSONObject();
		try {
			 meetingNoticeService.updateAttentionItem(meetingNoticeId,attentionItem);
			 json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * TODO 获取已经通过的会议的全部信息（带分页）
	 * @author 李颖洁  
	 * @date 2019年3月21日下午1:45:37
	 * @param pageImpl
	 * @param timeRange 日期范围
	 * @param meeetingTitle 会议标题
	 * @param meetingType 会议类型
	 * @param hostDeptId 主办单位id
	 * @param meetingRoomId 会议室id
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询会议安排情况列表")
	@ResponseBody
	@RequestMapping("getNotifiedList")
	public JSONObject getList(PageImpl pageImpl, String timeRange, String meeetingTitle,String meetingType,String hostDeptId,String meetingRoomId) {
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		JSONObject json =  meetingNoticeService.getPageListMeetingAllInfo(pageable, pageImpl, startDate, endDate,meeetingTitle,meetingType,hostDeptId,meetingRoomId);
		return json;
	}
}
