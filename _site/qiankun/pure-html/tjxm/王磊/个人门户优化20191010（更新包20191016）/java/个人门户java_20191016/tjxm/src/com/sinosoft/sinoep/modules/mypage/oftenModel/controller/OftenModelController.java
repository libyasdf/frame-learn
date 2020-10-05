package com.sinosoft.sinoep.modules.mypage.oftenModel.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sinosoft.sinoep.modules.mypage.oftenModel.entity.OftenModule;
import com.sinosoft.sinoep.modules.mypage.oftenModel.services.OftenModelService;
import net.sf.json.JSONObject;

/**
 * 
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月18日 上午9:46:44
 */
@RestController
@RequestMapping("/mypage/oftenModel")
public class OftenModelController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OftenModelService service;
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 上午9:56:30
	 * @param model
	 * @return
	 */
	@RequestMapping("save")
	public JSONObject save(String model){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		try {
			service.save(model);
		} catch (Exception e) {
			json.put("flag", "0");
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}


	/**
	 * 获取常用模块数据
	 * @return
	 */
	@RequestMapping("getData")
	public List<OftenModule> getData(){
		try{
			return service.findByUserId();
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@RequestMapping("delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(id)) {
			try {
				service.delete(id);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}


	@RequestMapping(value = "sort", method = RequestMethod.POST)
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



}
