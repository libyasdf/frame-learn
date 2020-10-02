package com.key.authority.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key.authority.entity.AuthModel;
import com.key.authority.services.AuthZwqxService;

import net.sf.json.JSONObject;

/**
 * TODO 职务权限控制类
 * @author 李利广
 * @Date 2018年9月15日 下午5:41:32
 */
@Controller
@RequestMapping("/dwsurvey/authority")
public class AuthZwqxController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthZwqxService infoService;

	/**
	 * TODO 保存职务权限
	 * @author 李利广
	 * @Date 2018年9月15日 下午5:42:35
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public JSONObject saveInfo(@RequestBody AuthModel model){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			model = infoService.saveInfoZwqx(model);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		json.put("data", model);
		return json;
	}
	
	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAuthority")
	public JSONObject getAuthority(String surveyId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			AuthModel model = infoService.getAuthority(surveyId);
			json.put("data", model);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
