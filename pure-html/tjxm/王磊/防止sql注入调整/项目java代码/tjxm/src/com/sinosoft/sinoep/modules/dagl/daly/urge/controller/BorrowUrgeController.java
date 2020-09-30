package com.sinosoft.sinoep.modules.dagl.daly.urge.controller;

import com.sinosoft.sinoep.modules.dagl.daly.urge.entity.BorrowUrge;
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
import com.sinosoft.sinoep.modules.dagl.daly.urge.services.BorrowUrgeService;

/**
 * 档案催还控制层
 * @author 王磊
 * @Date 2019年2月11日 上午10:24:25
 */
@Controller
@RequestMapping("dagl/urge")
public class BorrowUrgeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BorrowUrgeService borrowUrgeService;
	
	/**
	 * 根据借阅id查询催还记录列表
	 * @author 王磊
	 * @Date 2019年2月11日 上午10:53:40
	 * @param pageImpl
	 * @param borrowId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询借阅催还列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, BorrowUrge borrowUrge) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		PageImpl pageList = borrowUrgeService.getPageList(pageable, pageImpl, borrowUrge);
		return pageList;
	}
}
