package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;
/**
 * 会议申请Controller
 * TODO 
 * @author 冯超
 * @Date 2018年8月21日 上午11:22:28
 */
 
@Controller
@RequestMapping("zhbg/hygl/meetingApply")
public class MeetingApplyController {
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingApplyService meetingApplyService;

	/**
	 * 起草会议管理时加载页面
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午9:29:05
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "起草会议管理时加载页面")
	@ResponseBody
	@RequestMapping("add")
	public JSONObject add() {
		JSONObject json = new JSONObject();
		MeetingApplyInfo meetingApplyInfo = new MeetingApplyInfo();
		try {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//新增会议申请时添加起草人，起草人单位
			meetingApplyInfo.setCreUserId(UserUtil.getCruUserId());
			meetingApplyInfo.setCreUserName(UserUtil.getCruUserName());
			meetingApplyInfo.setCreDeptId(UserUtil.getCruDeptId());
			meetingApplyInfo.setCreDeptName(UserUtil.getCruDeptName());
			meetingApplyInfo.setCreTime(creTime);

			json.put("flag", "1");
			json.put("data", meetingApplyInfo);
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
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "HQL语句查询")
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl,String title,String timeRange,String subflag,String noticeFlag,String applyTitle,String serviceDeptName,String serviceDeptId){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
				pageImpl = meetingApplyService.getPageListDraft(pageable,pageImpl,title,startDate,endDate,subflag,noticeFlag,applyTitle,serviceDeptName,serviceDeptId);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 获取需要起草会议通知的申请列表
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
	@RequestMapping("getNoticeList")
	public PageImpl getNoticeList(PageImpl pageImpl,String title,String timeRange,String subflag,String noticeFlag,String meetingName,String serviceDeptName,String serviceDeptId){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			//if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl = meetingApplyService.getNoticeList(pageable,pageImpl,title,startDate,endDate,subflag,noticeFlag,meetingName,serviceDeptName,serviceDeptId);
			//}else{
				//pageImpl = meetingApplyService.getPageList(pageable,pageImpl,title,startDate,endDate,subflag);
			//}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 保存表单
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:27:59
	 * @param meetingApplyInfo
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(MeetingApplyInfo meetingApplyInfo){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingApplyInfo.setNoticeFlag("0");
			meetingApplyInfo = meetingApplyService.saveForm(meetingApplyInfo);
			json.put("flag", "1");
			json.put("data", meetingApplyInfo);
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
	@LogAnnotation(opType = OpType.DELETE,opName = "删除一条数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = meetingApplyService.delete(id);
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
	@LogAnnotation(opType = OpType.QUERY,opName = "获取修改页面数据")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		MeetingApplyInfo MeetingApplyInfo = null;
		try {
			MeetingApplyInfo = meetingApplyService.getById(id);
			json.put("flag", "1");
			json.put("data", MeetingApplyInfo);
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
			meetingApplyService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return json;
	}
	
	/**
	 * 检查会务服务的负责人是否已不具备"会务服务人员"
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年4月11日 下午5:55:59
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "检查服务的负责人不具备会务服务的角色")
	@ResponseBody
	@RequestMapping("checkServiceInfo")
	public JSONObject checkServiceInfo(String meetingServiceIds){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		List<MeetingServiceInfo> list= new ArrayList<MeetingServiceInfo>();
		  try {
			list= meetingApplyService.checkServiceInfo(meetingServiceIds);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		json.put("data", list);
		return json;
	}
	
	/**
	 * 会议管理员撤办
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2019年4月10日 上午11:10:35
	 * @param id
	 * @param ifRemovedByadmin
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "会议管理员撤办")
	@ResponseBody
	@RequestMapping("removedByMeetingAdmin")
	public JSONObject removedByMeetingAdmin(MeetingApplyInfo info,String ifRemovedByadmin,String subflag){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			meetingApplyService.removedByMeetingAdmin(info, ifRemovedByadmin,subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return json;
	}

}
