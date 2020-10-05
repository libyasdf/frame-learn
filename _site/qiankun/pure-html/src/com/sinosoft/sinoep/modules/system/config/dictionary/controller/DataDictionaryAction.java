package com.sinosoft.sinoep.modules.system.config.dictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * TODO 数据字典
 * @author 周俊林
 * @Date 2018年4月9日 上午9:30:57
 */
@Controller
@RequestMapping(value = "/system/config/dictionary")
public class DataDictionaryAction {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataDictionaryService service;
	
	/**
	 * TODO 根据类别，字典类型查询列表，不分页
	 * @author 周俊林
	 * @Date 2018年4月9日 下午1:51:01
	 * @param dic
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(DataDictionary dic) {
		JSONObject json = new JSONObject();
		if (!StringUtils.isBlank(dic.getType())) {
			try {
				List<DataDictionary> list = service.list(dic);
				System.out.println(JSON.toJSONString(list));
				json.put("flag", "1");
				JSONObject data = new JSONObject();
				data.put("total",list.size());
				JSONArray array = new JSONArray();
				JsonConfig jsonConfig  = new JsonConfig();
				jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
				jsonConfig.setExcludes(new String[] {"visible","type",
						"creTime", "updateTime", "creUserId", "creUserName", "updateUserId", "updateUserName",
						"creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
				array = JSONArray.fromObject(list, jsonConfig);
				System.out.println(JSON.toJSONString(array));
				data.put("rows", array);
				json.put("data",data);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				json.put("flag", 0);
			}
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * TODO 保存
	 * @author 周俊林
	 * @Date 2018年4月9日 下午3:26:00
	 * @param dic
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject save(DataDictionary dic) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dic.getId())) {
				dic = service.save(dic);
			} else {
				dic = service.update(dic);
			}
			json.put("flag", 1);
			json.put("data", dic);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 删除字典类型
	 * @param id
	 * @param mark
	 * @return
	 */
	@RequestMapping(value = "deleteType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delete(String id, String mark) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(mark)) {
			try {
				if (service.canDel(id,mark)) {
					service.deleteType(id);
					json.put("flag", "1");
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return json;
	}

	/**
	 * 删除字典项
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteItem", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(String ids) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(ids)) {
			try {
				service.deleteDic(ids);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	/**
	 * 根据ID获取一条字典数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view")
	@ResponseBody
	public JSONObject view(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			DataDictionary dic = service.getDictById(id);
			json.put("flag","1");
			json.put("data",dic);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 添加字典时，校验字典名、字典值不能重复
	 * @param dic
	 * @param checkItem
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject check(DataDictionary dic,String checkItem){
		JSONObject json = new JSONObject();
		boolean valid = service.checkDic(dic,checkItem);
		json.put("valid",valid);
		return json;
	}

	/**
	 * 根据唯一码值查找字典项/字典类型
	 * @param mark
	 * @return
	 */
	@RequestMapping(value = "getListByMark", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getListByMark(String mark,String type){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			List<DataDictionary> dicts = service.getListByMark(mark,type);
			json.put("flag","1");
			json.put("data",dicts);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 数据字典项排序
	 * @author 李利广
	 * @Date 2018年5月15日 下午7:22:52
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sort(String[] id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			service.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 根据唯一码值查询字典项(类型)
	 * @author 李利广
	 * @Date 2018年5月15日 下午7:22:52
	 * @param mark
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "getMapByMark", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getMapByMark(String mark,String type){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			List<DataDictionary> dicts = service.getListByMark(mark,type);
			Map<String, String> map = new HashMap<>();
			for (DataDictionary dic : dicts) {
				map.put(dic.getCode(), dic.getName());
			}
			json.put("flag","1");
			json.put("data",map);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 数据字典左侧数排序
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月23日 上午10:54:41
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updatetreeSort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatetreeSort(String[] id,String dropId,String pId){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			service.updatetreeSort(id,dropId,pId);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
