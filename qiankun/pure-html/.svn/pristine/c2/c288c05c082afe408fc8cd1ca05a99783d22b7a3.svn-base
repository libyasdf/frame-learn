package com.sinosoft.sinoep.modules.system.config.applicationlink.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.applicationlink.entity.Application;
import com.sinosoft.sinoep.modules.system.config.applicationlink.service.ApplicationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/system/config/application")
public class ApplicationController {
	
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationService applicationService;
	
	/**
	 * 
	 * TODO 修改导航
	 * @author 冯超
	 * @Date 2018年5月7日 下午1:59:55
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		Application application = null;
		try {
			application = applicationService.getById(id);
			json.put("flag", "1");
			json.put("data", application);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 保存导航
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:14:18
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public Application saveForm(Application application){
		try {
			application = applicationService.saveForm(application);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return application;
	}
	
	/**
	 * 
	 * TODO 获取导航列表(分页)
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:33:40
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPageList")
	public PageImpl getPageList(PageImpl pageImpl,String name){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = applicationService.getPageList(pageable,pageImpl,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 获取导航列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param application
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JSONObject getList(Application application) {
		JSONObject json = new JSONObject();
		try {
			List<Application> list = applicationService.getList(application);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", array);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 
	 * TODO 删除导航
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:54:10
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = applicationService.delete(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 导航排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:16:19
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sort(String[] id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			applicationService.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
