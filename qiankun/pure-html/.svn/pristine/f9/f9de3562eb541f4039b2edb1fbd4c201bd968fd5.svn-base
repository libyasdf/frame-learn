package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.controller;

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
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.services.DataDictionarysService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * TODO 数据字典
 * @author 周俊林
 * @Date 2018年4月9日 上午9:30:57
 */
@Controller
@RequestMapping(value = "/zhbg/xxkh/tree")
public class DataDictionarysController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataDictionarysService service;
	

	@LogAnnotation(value = "query",opName = "查询树资料树列表")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(DataDictionarys dic) {
		JSONObject json = new JSONObject();
		if (!StringUtils.isBlank(dic.getType())) {
			try {
				dic.setCreJuId(UserUtil.getCruJuId());
				List<DataDictionarys> list = service.list(dic);
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
	 * 
	 * @Title: DataDictionarysController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.controller
	 * @Description: TODO(新加树的一条类型)
	 * @author 颜振兴
	 * @date 2018年7月30日
	 * @param dic
	 * @return
	 */
	@SameUrlData
	@LogAnnotation(value = "save",opName = "新加树的一条类型")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject save(DataDictionarys dic) {
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
	 * 根据类型id删除学习考核相关树类型及子类型
	 * @param id 类型id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除学习考核相关树类型")
	@RequestMapping(value = "deleteType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteType(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		if (StringUtils.isNotBlank(id)) {
			try {
				if (service.canDel(id)) {
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
			DataDictionarys dic = service.getDictById(id);
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
	public JSONObject check(DataDictionarys dic,String checkItem){
		JSONObject json = new JSONObject();
		boolean valid = service.checkDic(dic,checkItem);
		json.put("valid",valid);
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
			List<DataDictionarys> dicts = service.getListByMark(mark,type);
			Map<String, String> map = new HashMap<>();
			for (DataDictionarys dic : dicts) {
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
	
	@RequestMapping("ispid")
	@ResponseBody
	public JSONObject getIsPid(String id) {
		JSONObject json = new JSONObject();
		int isPid = service.getIsPid(id);
		json.put("count", isPid);
		return json;
	}
	/**
	 * 
	 * @Title: DataDictionarysController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.controller
	 * @Description: TODO 部门选题的时候 获取所有的试题树
	 * @author 颜振兴
	 * @date 2018年9月12日
	 * @param dic
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "获取所有试题树")
	@RequestMapping(value = "bumenlist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject bumenlist(DataDictionarys dic) {
		JSONObject json = new JSONObject();
		if (!StringUtils.isBlank(dic.getType())) {
			try {
				dic.setCreJuId(UserUtil.getCruJuId());
				List<DataDictionarys> list = service.bumenlist(dic);
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
	 * 
	 * @Title: DataDictionarysController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.controller
	 * @Description: TODO获取本部门的树
	 * @author 颜振兴
	 * @date 2018年9月12日
	 * @param dic
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "部门查询树资料树列表")
	@RequestMapping(value = "bumenSjList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject bumenSjList(DataDictionarys dic) {
		JSONObject json = new JSONObject();
		if (!StringUtils.isBlank(dic.getType())) {
			try {
				dic.setCreJuId(UserUtil.getCruJuId());
				List<DataDictionarys> list = service.bumenlist(dic);
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
	 * 
	 * @Title: DataDictionarysController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.controller
	 * @Description: TODO 部门获取所有的树列表
	 * @author 颜振兴
	 * @date 2018年9月12日
	 * @param dic
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "部门查询所有树资料树列表")
	@RequestMapping(value = "buMenGetAllTreeSj", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject buMenGetAllTreeSj(DataDictionarys dic) {
		JSONObject json = new JSONObject();
		if (!StringUtils.isBlank(dic.getType())) {
			try {
				dic.setCreJuId(UserUtil.getCruJuId());
				List<DataDictionarys> list = service.bumenSjList(dic);
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
	//@LogAnnotation(value = "query",opName = "学习时长树列表")
		@RequestMapping(value = "learningTimeTreeList", method = RequestMethod.GET)
		@ResponseBody
		public JSONObject learningTimeTreeList(DataDictionarys dic) {
			JSONObject json = new JSONObject();
			if (!StringUtils.isBlank(dic.getType())) {
				try {
					dic.setCreJuId(UserUtil.getCruJuId());
					List<DataDictionarys> list = service.learningTimeTreeList(dic);
					json.put("flag", "1");
					JSONObject data = new JSONObject();
					data.put("total",list.size());
					JSONArray array = new JSONArray();
					JsonConfig jsonConfig  = new JsonConfig();
					jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
					jsonConfig.setExcludes(new String[] {"visible",
							"creTime", "updateTime", "creUserId", "creUserName", "updateUserId", "updateUserName",
							"creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
					array = JSONArray.fromObject(list, jsonConfig);
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
	
	
}
