package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

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
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.OverTimeQueryService;

/**
 * 加班查询controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月19日 下午3:01:27
 */
@Controller
@RequestMapping("/zhbg/kqgl/overTimeQuery")
public class OverTimeQueryController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OverTimeQueryService service;

	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(value = "query",opName = "查询列表")
	public PageImpl getList(PageImpl pageImpl, String userId,String deptId,String overTimeType, String timeRange, String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = service.getPageListDraft(pageable, pageImpl,userId,deptId, overTimeType, startDate, endDate, subflag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	

}
