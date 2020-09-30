package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtHjxj;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.services.DwxtHjxjService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 换届选举控制层
 * @author 胡石阳
 * @Date 2018年4月9日 上午9:30:57
 */
@RestController
@RequestMapping(value = "/djgl/ddjs/dzz/hjxj")
public class DwxtHjxjController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DwxtHjxjService dwxtHjxjService;

	@LogAnnotation(value = "query",opName = "查询列表")
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, DwxtHjxj dwxtHjxj, String timeRange) {
		return dwxtHjxjService.getPageList(pageImpl,dwxtHjxj,timeRange);
	}

	/**
	 * 查询换届信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "查询换届信息")
	@RequestMapping(value = "findOne", method = RequestMethod.GET)
	public JSONObject findOne(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtHjxj dwxtHjxj = null;
		try {
			dwxtHjxj = dwxtHjxjService.findOne(id);
			json.put("flag", "1");
			json.put("data", dwxtHjxj);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 保存换届信息
	 * @param dwxtHjxj
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存换届信息")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public JSONObject save(DwxtHjxj dwxtHjxj) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtHjxj.getId())) {
				dwxtHjxj = dwxtHjxjService.save(dwxtHjxj);
			} else {
				dwxtHjxj = dwxtHjxjService.update(dwxtHjxj);
			}
			json.put("flag", 1);
			json.put("data", dwxtHjxj);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 删除换届信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除换届信息")
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(id)) {
			try {
				dwxtHjxjService.delete(id);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	@LogAnnotation(value = "query",opName = "查询领导成员")
	@RequestMapping(value = "ldcyList", method = RequestMethod.GET)
	public PageImpl ldcyList(DwxtLdcy dwxtLdcy,PageImpl pageImpl){
		return dwxtHjxjService.ldcyList(dwxtLdcy,pageImpl);
	}

	/**
	 * 保存换届信息
	 * @param dwxtLdcy
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存领导成员")
	@RequestMapping(value = "saveLdcy", method = RequestMethod.POST)
	public JSONObject saveLdcy(DwxtLdcy dwxtLdcy) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtLdcy.getId())) {
				dwxtLdcy = dwxtHjxjService.saveLdcy(dwxtLdcy);
			} else {
				dwxtLdcy = dwxtHjxjService.updateLdcy(dwxtLdcy);
			}
			json.put("flag", 1);
			json.put("data", dwxtLdcy);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 查询换届信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "查询领导成员信息")
	@RequestMapping(value = "findOneLdcy", method = RequestMethod.GET)
	public JSONObject findOneLdcy(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtLdcy dwxtLdcy = null;
		try {
			dwxtLdcy = dwxtHjxjService.findOneLdcy(id);
			json.put("flag", "1");
			json.put("data", dwxtLdcy);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 删除领导成员
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除领导成员")
	@RequestMapping(value = "deleteLdcy", method = RequestMethod.GET)
	public JSONObject deleteLdcy(String id) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(id)) {
			try {
				dwxtHjxjService.deleteLdcy(id);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	/**
	 * 离任
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "删除领导成员")
	@RequestMapping(value = "outTenure", method = RequestMethod.GET)
	public JSONObject outTenure(String id) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(id)) {
			try {
				dwxtHjxjService.outTenure(id);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	/**
	 * 新增换届选举时判断该人员是否已经为领导人
	 * TODO
	 * @author 李帅
	 * @Date 2018年8月30日
	 * @param
	 * @param
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询人员是否已经为领导人")
	@ResponseBody
	@RequestMapping("queryLdcy")
	public JSONObject queryLdcy(String hjxjId, String leaderId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtLdcy dwxtLdcy  =null;
		try {
			dwxtLdcy = dwxtHjxjService.queryLdcy(hjxjId,leaderId)==null?new DwxtLdcy():dwxtHjxjService.queryLdcy(hjxjId,leaderId);
			json.put("flag", "1");
			json.put("data", dwxtLdcy);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

/**
 * 对领导成员进行离任
 * TODO
 * @author 李帅
 * @Date 2018年11月16日
 * @param
 * @param
 * @return
 * */
	@LogAnnotation(value = "save",opName = "保存领导成员离任信息保存")
	@RequestMapping(value = "updateLdcy", method = RequestMethod.POST)
	public JSONObject updateLdcy(DwxtLdcy dwxtLdcy) {
		JSONObject json = new JSONObject();
		try {
			dwxtLdcy = dwxtHjxjService.updateLeave(dwxtLdcy);
			json.put("flag", 1);
			json.put("data", dwxtLdcy);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}
	/**
	 * 查询本届是否有领导成员
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 * */
	@LogAnnotation(value = "query",opName = "查询本届是否有领导成员")
	@ResponseBody
	@RequestMapping("queryLeave")
	public JSONObject queryLeave(String hjxjId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtLdcy dwxtLdcy  =null;
		try {
			dwxtLdcy = dwxtHjxjService.queryLeave(hjxjId)==null?new DwxtLdcy():dwxtHjxjService.queryLeave(hjxjId);
			json.put("flag", "1");
			json.put("data", dwxtLdcy);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
