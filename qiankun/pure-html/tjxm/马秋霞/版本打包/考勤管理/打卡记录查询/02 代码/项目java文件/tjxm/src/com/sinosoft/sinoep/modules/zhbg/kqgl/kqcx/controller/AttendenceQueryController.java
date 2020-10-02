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
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.AttendenceQueryService;

/**
 * 领导考勤查询
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月27日 下午7:53:41
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/attendenceQuery")
public class AttendenceQueryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AttendenceQueryService service;
	
	
	@ResponseBody
	@RequestMapping("getlist")
	@LogAnnotation(value = "query",opName = "查询列表")
	public PageImpl getList(PageImpl pageImpl, String timeRange,String cardNumber,String userId,String flag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = service.getPageList(pageable, pageImpl, startDate,endDate ,cardNumber,userId,flag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}
}
