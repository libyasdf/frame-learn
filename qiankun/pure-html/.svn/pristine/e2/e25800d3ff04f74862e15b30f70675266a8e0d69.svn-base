package com.sinosoft.sinoep.modules.system.config.userdefinesetting.controller;

import com.sinosoft.sinoep.modules.system.config.userdefinesetting.entity.UserSetting;
import com.sinosoft.sinoep.modules.system.config.userdefinesetting.services.UserSettingService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户设置CONTROLLER
 */
@RestController
@RequestMapping("/system/config/userdefinesetting")
public class UserSettingController {
	
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserSettingService userSettingService;

	/**
	 * 保存用户设置
	 * @param userSetting
	 * @return
	 */
	@RequestMapping(value = "/save")
	public JSONObject save(UserSetting userSetting){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		json.put("msg","设置失败");
		try {
			userSettingService.save(userSetting);
			json.put("flag","1");
			json.put("msg","设置成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 查询登录用户自定义设置
	 * @return
	 */
	@RequestMapping(value = "/query")
	public JSONObject query(){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		json.put("msg","用户还未自定义设置");
		try {
			String userId = UserUtil.getCruUserId();
			UserSetting userSetting = userSettingService.findByUserId(userId);
			json.put("flag","1");
			json.put("msg","查询成功");
			json.put("personalPortalAddress",userSetting.getPersonalPortalAddress() == null ? "" : userSetting.getPersonalPortalAddress());
			json.put("themeSetting",userSetting.getThemeSetting() == null ? "" : userSetting.getThemeSetting());
		} catch (Exception e) {
			json.put("msg","系统错误");
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}


}
