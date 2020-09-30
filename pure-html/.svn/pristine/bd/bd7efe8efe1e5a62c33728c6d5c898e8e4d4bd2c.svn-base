package com.sinosoft.sinoep.modules.system.date.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.DateUtil;

import net.sf.json.JSONObject;

/**
 * 日期
 * TODO 
 * @author 冯超
 * @Date 2018年5月4日 下午4:39:47
 */
@Controller
@RequestMapping("/system/date")
public class DateController {
	
	/**
	 * 
	 * TODO 更具日期格式获取当前日期并返回
	 * @author 冯超
	 * @Date 2018年5月4日 下午4:38:29
	 * @param format 日期格式
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="/getCurrentDate",method = RequestMethod.POST)
	public JSONObject getCurrentDate(String format){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			json.put("flag", "1");
			String text = DateUtil.getDateText(new Date(),format);
			json.put("data", text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 
	 * TODO 获取1970年1月1号0时0分0秒所差的毫秒数
	 * @author 冯超 
	 * @Date 2018年5月15日 下午5:44:25
	 * @param format
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="/getCurrentTime",method = RequestMethod.GET)
	public JSONObject getCurrentTime(){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			json.put("flag", "1");
			long currentTime = System.currentTimeMillis();
			json.put("data", currentTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

}
