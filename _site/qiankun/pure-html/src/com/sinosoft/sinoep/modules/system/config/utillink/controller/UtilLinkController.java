package com.sinosoft.sinoep.modules.system.config.utillink.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.modules.system.config.utillink.entity.UtilLink;
import com.sinosoft.sinoep.modules.system.config.utillink.service.UtilLinkService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/system/config/utilLink")
public class UtilLinkController {
	
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UtilLinkService utilLinkService;
	
	/**
	 * 
	 * TODO 修改常用工具
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
		UtilLink utilLink = null;
		try {
			utilLink = utilLinkService.getById(id);
			json.put("flag", "1");
			json.put("data", utilLink);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 保存常用工具
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:14:18
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(UtilLink utilLink){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//判断有没有标题重复的
			List<UtilLink> list = utilLinkService.getListExcludeId(utilLink);
			if(list.size()>0){
				json.put("flag", "3");//3:代表有重名
				json.put("data", utilLink);
			}else{
				json.put("flag", "1");//1:保存成功
				utilLink = utilLinkService.saveForm(utilLink);
				json.put("data", utilLink);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 获取常用工具列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param application
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JSONObject getList(UtilLink utilLink) {
		JSONObject json = new JSONObject();
		try {
			List<UtilLink> list = utilLinkService.getList(utilLink);
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
	 * TODO 删除常用工具
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
			int result = utilLinkService.delete(id);
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
	 * TODO 常用工具排序
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
			utilLinkService.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
