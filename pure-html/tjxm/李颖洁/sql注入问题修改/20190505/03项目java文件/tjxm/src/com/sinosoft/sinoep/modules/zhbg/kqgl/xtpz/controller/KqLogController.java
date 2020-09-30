package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqLog;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqLogService;

import net.sf.json.JSONObject;

/**
 * 
 * TODO 考勤日志Controller
 * @author 冯超
 * @Date 2018年5月30日 上午10:07:38
 */
@Controller
@RequestMapping("/kqgl/xtpz/kqlog")
public class KqLogController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KqLogService kqLogService;
	
	/**
	 * 
	 * TODO 获取日志列表
	 * @author 冯超
	 * @Date 2018年5月31日 上午9:30:06
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl, String timeRange) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = kqLogService.getPageList(pageable, pageImpl, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 根据id获取数据修改，只读调用
	 * @author 冯超
	 * @Date 2018年5月31日 上午10:08:22
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		KqLog kqLog = null;
		try {
			kqLog = kqLogService.getById(id);
			json.put("flag", "1");
			json.put("data", kqLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
