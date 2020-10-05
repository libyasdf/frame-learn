package com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service.MeetingApplyService;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.entity.MeetingSignInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.service.MeetingSignService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 会议签到controller
 * TODO 
 * @author 郝灵涛
 * @Date 2018年7月13日 上午11:16:02
 */
@Controller
@RequestMapping("/sign/hygl/meetingSign")
public class MeetingSignController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingSignService meetingSignService;
	
	@Autowired
	private MeetingApplyService meetingApplyService;
	
	/**
	 * 保存表单
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:27:59
	 * @param meetingServiceNotice
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(@RequestBody List<MeetingSignInfo> meetingSignInfos){
		//response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if(!meetingSignInfos.isEmpty()&&meetingSignInfos.size()>0){
				meetingSignInfos = meetingSignService.saveForm(meetingSignInfos);
				json.put("flag", "1");
				if(meetingSignInfos.isEmpty()){
				  json.put("msg", "没有数据可同步！");
				}else{
				  json.put("msg", "数据同步成功！同步"+meetingSignInfos.size()+"条数据");	
				}
			}else{
				json.put("flag", "1");
				json.put("msg", "没有当前日期的签到数据");
				return json;
			}
			//data.put("total", MeetingSignInfos.size());
			//JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			//array = JSONArray.fromObject(meetingServiceNotices, jsonConfig);
			//data.put("rows", MeetingSignInfos);
			//json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", "0");
			json.put("msg", "数据同步失败");
		}
		return json;
	}
	
	/**
	 * 获取会议通知列表
	 * TODO 
	 * @author 张建明
	 * @Date 2018年9月11日 上午11:23:36
	 * @param pageImpl
	 * @param timeRange
	 * @param meetingName
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询签到列表")
	@ResponseBody
	@RequestMapping("getList")
	public List<MeetingApplyInfo> getList(PageImpl pageImpl,String title,String timeRange,String subflag,String noticeFlag,String applyTitle,String serviceDeptName,String serviceDeptId) throws Exception {
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		return meetingApplyService.getPageListToSign(startDate,endDate);
	}
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:54:07
	 * @param
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String start,String end,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("远程调用进入edit...");
		JSONObject json = new JSONObject();
		String callback = request.getParameter("callback");
		json.put("flag", "0");
		log.info("callback "+callback);
		//MeetingApplyInfo MeetingApplyInfo = null;
		try {
			List<MeetingApplyInfo> list = meetingApplyService.getPageListToSign(start,end);
			log.info("请求标题service完成。。。");
			JSONArray array = JSONArray.fromObject(list);
			json.put("flag", "1");
			json.put("data", array);
		} catch (Exception e) {
			log.info("Exception 报错了。。。");
			e.printStackTrace();
			log.error(e.getMessage(),e);
						
		}
		 response.setContentType("text/html");
		    response.setCharacterEncoding("utf-8");
		    response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
		    PrintWriter out = response.getWriter();
		    out.print(callback + "(" + json.toString() + ")");
		    return null;
		//return json;
	}
	
	@ResponseBody
	@RequestMapping("editTest")
	public JSONObject editTest(String start,String end,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("远程调用进入edit...");
		JSONObject json = new JSONObject();
		String callback = request.getParameter("callback");
		json.put("flag", "0");
		log.info("callback "+callback);
		//MeetingApplyInfo MeetingApplyInfo = null;
		try {
			List<MeetingApplyInfo> list = meetingApplyService.getPageListToSign(start,end);
			log.info("请求标题service完成。。。");
			JSONArray array = JSONArray.fromObject(list);
			json.put("flag", "1");
			json.put("data", array);
		} catch (Exception e) {
			log.info("Exception 报错了。。。");
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", "0");
			json.put("data", new JSONArray());
		}
		/* response.setContentType("text/html");
		    response.setCharacterEncoding("utf-8");
		    response.addHeader("Access-Control-Allow-Origin","*");//允许所有来源访问
		    PrintWriter out = response.getWriter();
		    out.print(json);*/
		    return json;
		//return json;
	}
}
