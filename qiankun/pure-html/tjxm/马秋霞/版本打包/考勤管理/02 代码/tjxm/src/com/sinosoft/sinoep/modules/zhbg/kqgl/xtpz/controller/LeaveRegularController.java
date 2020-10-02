package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.controller;


import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.LeaveRegularService;

import net.sf.json.JSONObject;

/**
 * 年休假规则设置的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月20日 下午4:03:01
 */
@Controller
@RequestMapping("/zhbg/kqgl/xtpz/njgz")
public class LeaveRegularController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LeaveRegularService service;
	
	/**
	 * 查询年休假规则的所有数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月23日 上午10:53:56
	 * @param pageImpl
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql1")
	public Map<String,Object> getList1(PageImpl pageImpl){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = service.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return map;
	}
	
	/**
	 * 保存年休假规则列表数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午4:20:43
	 * @param data
	 */
	@ResponseBody
	@RequestMapping("saveAll")
	public JSONObject saveAll(String data,String dayNumbers) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		try {
			service.saveAll(data, dayNumbers);
		} catch (Exception e) {
			json.put("flag", "0");
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
