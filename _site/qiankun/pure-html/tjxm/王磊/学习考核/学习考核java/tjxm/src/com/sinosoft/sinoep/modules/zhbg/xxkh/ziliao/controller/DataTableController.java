package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.DataTable;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services.DataTableService;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/zhbg/xxkh/tree/datatable")
public class DataTableController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataTableService service;
	
	@LogAnnotation(value = "query",opName = "查询资料列表")
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject list(DataTable dt,PageImpl pageImpl)  {
		Page<DataTable> list = null;
		//dt.setCreJuId(UserUtil.getCruJuId());
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			json.put("flag", "1");
			list = service.list(dt, pageImpl);
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject data = new JSONObject();
		data.put("total", list.getTotalElements());
		for(DataTable dTable:list.getContent()) {
			dTable.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			
		}
		data.put("rows", list.getContent());
		json.put("data", data);
		return json;
		

	} 
	@LogAnnotation(value = "save",opName = "保存一条资料")
	@SameUrlData
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(DataTable dt) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			DataTable save = service.save(dt);
			json.put("data", save);
			json.put("flag", "1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return json;
	}
	/**
	 * @TODO 查询单条消息
	 * @param dt
	 */
	@LogAnnotation(value = "query",opName = "查询单条资料")
	@RequestMapping("/getOne")
	@ResponseBody
	public JSONObject getOne(DataTable dt) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		DataTable save = service.getOne(dt.getId());
		json.put("data", save);
		return json;
	}
	/**
	 * 
	
	* @Title: DataTableController.java
	* @Package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.controller
	* @Description: TODO
	* @author Administrator
	* @date 2018年7月20日
	
	 * @param dt
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除一条资料")
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(DataTable dt) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		int delete = service.delete(dt.getId());
		if (delete>=1) {
			json.put("flag", "1");
		}
		
		return json;
				
	}
	

}
