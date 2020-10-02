package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service.DirectorStatsService;

@Controller
@RequestMapping("zhbg/kqgl/statistics/directorStats")
public class DirectorStatsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	 DirectorStatsService service;
	
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "查询统计查询列表")
	public PageImpl getList(PageImpl pageImpl,String orgId,String timeRange,String deptid,String flg){
		
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl=service.getList(pageable,timeRange,deptid,flg);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			//json.put("flag", "0");
		}
		return pageImpl;
	}
}
