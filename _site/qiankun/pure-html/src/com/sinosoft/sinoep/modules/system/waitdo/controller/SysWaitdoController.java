package com.sinosoft.sinoep.modules.system.waitdo.controller;

import com.sinosoft.sinoep.modules.system.waitdo.entity.SysWaitdo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.waitdo.services.SysWaitdoService;

/**
 * TODO 系统待办controller控制层
 * @author 李利广
 * @Date 2018年5月25日 上午10:17:18
 */
@Controller
@RequestMapping("/system/waitdo")
public class SysWaitdoController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysWaitdoService waitdoService;
	
	/**
	 * TODO 查询待办列表
	 * @author 李利广
	 * @Date 2018年5月25日 下午2:54:14
	 * @param request
	 * @param workFlowId
	 * @param fileTypeId
	 * @param pageImpl
	 * @param timeRange
	 * @param title
	 * @return
	 */
	@RequestMapping("/getWaitdoList")
	@ResponseBody
	public PageImpl getWaitdoList(String workFlowId,String fileTypeId, PageImpl pageImpl,String timeRange,String title){
		String startDate = "";
		String endDate = "";
		pageImpl.setFlag("0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			pageImpl = waitdoService.getWaitdoList(pageImpl,workFlowId,fileTypeId,startDate,endDate,title);
			pageImpl.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	/**
	 * 根据待办ID查询一条待办
	 * @param id
	 * @return
	 */
	@RequestMapping("/getWaitdoById")
	@ResponseBody
	public JSONObject getWaitdoById(String id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			if (StringUtils.isNotBlank(id)){
				SysWaitdo waitdo = waitdoService.getById(id);
                json.put("flag","1");
				json.put("data",waitdo);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag","-1");
		}
		return json;
	}

}
