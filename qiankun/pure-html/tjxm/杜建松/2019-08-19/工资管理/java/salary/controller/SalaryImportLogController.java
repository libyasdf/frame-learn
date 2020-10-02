package com.sinosoft.sinoep.modules.zhbg.salary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.SalaryImportLog;
import com.sinosoft.sinoep.modules.zhbg.salary.services.SalaryImportLogService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhbg/salary/importLog")
public class SalaryImportLogController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SalaryImportLogService service;
	
	/**
	 * 获取工资导入日志的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 上午11:32:38
	 * @param pageImpl
	 * @param name
	 * @param yearMonth
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getlistBootHql(PageImpl pageImpl,String name,String yearMonth,String personId){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getlistBootHql(pageable,pageImpl,name,yearMonth, personId);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 修改
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 上午11:33:01
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		SalaryImportLog importlog = null;
		try {
			importlog = service.getById(id);
			json.put("flag", "1");
			json.put("data", importlog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
