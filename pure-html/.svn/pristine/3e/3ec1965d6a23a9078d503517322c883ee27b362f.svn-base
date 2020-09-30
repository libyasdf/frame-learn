package com.sinosoft.sinoep.modules.system.flowSend.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.flowSend.entity.SysFlowSend;
import com.sinosoft.sinoep.modules.system.flowSend.services.SysFlowSendService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 待阅控制类
 * TODO 
 * @author 李利广
 * @Date 2018年10月26日 下午1:54:57
 */
@Controller
@RequestMapping("/system/flowSend")
public class SysFlowSendController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysFlowSendService service;
	
	/**
	 * 获取待阅列表
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月26日 下午1:58:38
	 * @param pageImpl
	 * @param flowSend
	 * @param timeRange
	 * @return
	 */

	@RequestMapping("getSendList")
	@ResponseBody
	public PageImpl getSendList(PageImpl pageImpl,SysFlowSend flowSend,String timeRange){
		pageImpl.setFlag("0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			pageImpl = service.getFlowSendList(pageImpl, flowSend, startTime, endTime);
			pageImpl.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			pageImpl.setMsg("获取待阅列表异常！");
		}
		return pageImpl;
	}
	/**
	 * TODO 阅毕方法，根据ID更新待阅状态为已阅
	 * @author 李利广
	 * @Date 2019年03月22日 14:55:41
	 * @param send
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateByReadTime")
	public JSONObject updateByReadTime(SysFlowSend send) {
		JSONObject json = new JSONObject();
		try {
			json= service.readDone(send);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

}
