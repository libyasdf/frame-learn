package com.sinosoft.sinoep.modules.zhbg.hygl.statistics.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.modules.zhbg.hygl.statistics.service.MeetingStatsService;
import net.sf.json.JSONObject;

/**
 * 会议统计controller
 * TODO 
 * @author 张建明
 * @Date 2018年7月19日 上午10:48:04
 */
@Controller
@RequestMapping("zhbg/hygl/statistics")
public class MeetingStatsController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingStatsService service;
	
	@Autowired
	private MeetingApplyService meetingApplyService;
	
	/**
	 * 获取会议室使用次数
	 * TODO 
	 * @author 张建明
	 * @Date 2018年7月31日 下午2:56:06
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "修改页面")
	@ResponseBody
	@RequestMapping("getCount")
	public JSONObject edit(String flag,String yearDate,String monthdate, String weekDate,String subflag){
		String startDate = "";
		String endDate = "";
		if("0".equals(flag)) {
			//按年
			String[] temp = yearDate.split("-");
			startDate=temp[0];
			endDate=temp[1];
		}else if("1".equals(flag)) {
			//按月
			startDate = monthdate.substring(0, (monthdate.length() + 1) / 2 - 1).trim();
			endDate = monthdate.substring((monthdate.length() + 1) / 2, monthdate.length()).trim();
		}else if("2".equals(flag)) {
			//按周
			startDate = weekDate.substring(0, (weekDate.length() + 1) / 2 - 1).trim();
			endDate = weekDate.substring((weekDate.length() + 1) / 2, weekDate.length()).trim();
		}
		
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			Map<String,Object> count = meetingApplyService.getCount(flag,startDate,endDate,subflag);
			json.put("flag", "1");
			json.put("data", count);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取当前日期所在周的上周周一和当前日志
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年2月22日 上午10:06:24
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWeekDate")
	public JSONObject getWeekDate(){
		JSONObject jsonObj = new JSONObject();
		String startDate="";
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		try {
			startDate= service.getWeekDate(curDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		jsonObj.put("startDate", startDate);
		jsonObj.put("endDate", curDate);
		return jsonObj;
	}
}
