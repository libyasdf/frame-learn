package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.entity.PersionInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.AttendenceTableService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 考勤表的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年8月27日 下午5:17:25
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/attendenceTable")
public class AttendenceTableController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	AttendenceTableService service;
	
	/**
	 * 考勤表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月27日 下午5:21:51
	 * @param orgId
	 * @param timeRange
	 * @param userids
	 * @param deptid
	 * @param deptname
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "考勤表查询")
	public JSONObject getList(String month,String userids){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<PersionInfo>list=new ArrayList<PersionInfo>();
		try {
			list=service.getList(month,userids);
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
