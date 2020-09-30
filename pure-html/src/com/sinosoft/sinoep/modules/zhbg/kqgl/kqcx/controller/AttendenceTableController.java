package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.AttendenceTableService;

/**
 * 
 * 考勤表的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年8月27日 下午5:17:25
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/attendenceTable")
public class AttendenceTableController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AttendenceTableService service;
	
	/**
	 * 考勤表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月27日 下午5:21:51
	 * @param orgId
	 * @param timeRange
	 * @param userids
	 * @param deptid
	 * @param deptname
	 * @param isAll是否查询所有的，1表示查询所有的，0表示不是
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "考勤表查询")
	public PageImpl getList(PageImpl pageImpl,String month,String userids,String  isAll,Integer total){
		
		try {
			pageImpl=service.getList(pageImpl,month,userids,isAll,total);
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			//json.put("flag", "0");
		}
		return pageImpl;
	}
}
