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
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service.SectionStatsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("zhbg/kqgl/statistics/sectionlStats")
public class SectionStatsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SectionStatsService service;
	
	/**
	 * 处长和科长可以看到的一些人员的统计情况的数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午3:03:34
	 * @param orgId
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "领导查询统计查询列表")
	public JSONObject getList(String orgId,String timeRange,String userids,String deptid,String deptname){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<PersonalStats>list=new ArrayList<PersonalStats>();
		try {
			long beginTime=System.currentTimeMillis();
			list=service.getList(timeRange,userids,deptid,deptname);
			long endTime=System.currentTimeMillis();
			System.out.println("\n"+"领导查询统计所需要时间："+(endTime-beginTime)/1000+"s");
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
