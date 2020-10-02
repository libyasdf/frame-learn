package com.sinosoft.sinoep.message.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.message.alert.entity.MessageAlert;
import com.sinosoft.sinoep.message.alert.services.MessageAlertService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/message/alert")
public class MessageAlertController {

	@Autowired
	private MessageAlertService messageAlertService;
	
	@RequestMapping("save")
	@ResponseBody
	public String save(MessageAlert alert) {
		messageAlertService.save(alert);
		return "ok";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String update(MessageAlert alert) {
		MessageAlert update = messageAlertService.update(alert);
		if (update!=null) {
			return "ok";
		}
		return "no";
	}
	
	@RequestMapping("updateTheme")
	@ResponseBody
	public String updateTheme(MessageAlert alert) {
		int updateTheme = messageAlertService.updateTheme(alert);
		if (updateTheme!=0) {
			return "ok";
		}
		return "no";
	}
	
	@RequestMapping("updateType")
	@ResponseBody
	public String updateType(MessageAlert alert) {
		int updateType = messageAlertService.updateType(alert);
		if (updateType!=0) {
			return "ok";
		}
		return "no";
	}
	
	@RequestMapping("getOne")
	@ResponseBody
	public JSONObject getOne(MessageAlert alert) {
		JSONObject json = new JSONObject();
		try {
			MessageAlert byUserId = messageAlertService.getByUserId(alert);
			if (byUserId!=null&&byUserId.getId()!="") {
				json.put("data", byUserId);
			}else {
				json.put("data", "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println(e);
			
			e.printStackTrace();
		}
			
		return json;
		
	}
	
}
