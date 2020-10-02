package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyDetailInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;

import net.sf.json.JSONObject;
/**
 * 会议申请Controller
 * TODO 
 * @author 冯超
 * @Date 2018年8月21日 上午11:22:28
 */
 
@Controller
@RequestMapping("zhbg/hygl/meetingApplyDetail")
public class MeetingApplyDetailController {
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingApplyService meetingApplyService;

	/**
	 * 根据当前时间获取本周周一和周日的日期
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月23日 下午4:53:46
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWeek")
	public JSONObject getWeek() {
		JSONObject json = new JSONObject();
		try {
			String creTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			//新增会议申请时添加起草人，起草人单位
			JSONObject date = DateUtil.getByDate(creTime);
			json.put("flag", "1");
			json.put("startDate", date.get("startDate"));
			json.put("endDate", date.get("endDate"));
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 根据周一-周日时间范围获取中间的所有日期
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月24日 上午9:46:47
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDateByWeek")
	public JSONObject getDateByWeek(String timeRange) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				List<String> list = DateUtil.getBetweenDates1(startDate, endDate);
				json.put("flag", "1");
				json.put("dateList", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	
	/**
	 * 获取会议室预约情况
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月23日 下午4:54:41
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "语句查询")
	@ResponseBody
	@RequestMapping("getlist")
	public JSONObject getList(String timeRange){
		JSONObject json = new JSONObject();
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				List<MeetingApplyDetailInfo> meetingApplyDetailInfoList = meetingApplyService.getMeetingApplyDetail(startDate, endDate);
				json.put("flag", "1");
				JSONObject data = new JSONObject();
				data.put("total", meetingApplyDetailInfoList.size());
				data.put("rows", meetingApplyDetailInfoList);
				json.put("data", data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
