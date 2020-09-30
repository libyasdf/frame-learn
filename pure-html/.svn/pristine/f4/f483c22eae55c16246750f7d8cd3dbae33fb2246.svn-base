package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.DeptCount;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service.DirectorStatsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("zhbg/kqgl/statistics/directorStats")
public class DirectorStatsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	 DirectorStatsService service;
	
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "查询统计查询列表")
	public JSONObject getList(String orgId,String timeRange,String deptid){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<DeptCount>list=new ArrayList<DeptCount>();
		try {
			list=service.getList(timeRange,deptid);
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
