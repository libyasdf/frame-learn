package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.entity.OtherFlowDep;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.services.OtherFlowDepService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service.PersonalStatsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("zhbg/kqgl/statistics/personalStats")
public class PersonalStatsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PersonalStatsService service;
	
	/**
	 * 统计个人的考勤情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午5:42:04
	 * @param orgId
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "个人统计查询列表")
	public JSONObject getList(String orgId,String timeRange){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<PersonalStats>list=new ArrayList<PersonalStats>();
		try {
			list=service.getList(timeRange);
			JSONObject data = new JSONObject();
			data.put("total",list.size());
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			data.put("rows", array);
			json.put("data",data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			json.put("flag", "0");
		}
		return json;
	}
}
