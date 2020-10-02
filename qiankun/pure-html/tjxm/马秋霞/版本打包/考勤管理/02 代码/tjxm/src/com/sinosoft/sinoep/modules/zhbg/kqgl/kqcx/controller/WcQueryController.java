package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.WcQueryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.services.GoOutInfoService;
import net.sf.json.JSONObject;

/**
 * 外出的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午9:36:32
 */
@Controller
@RequestMapping("/zhbg/kqgl/kqcx/wcquery")
public class WcQueryController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcQueryService service;
	
	/**
	 *HQL语句查询，获取外出的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 上午10:14:26
	 * @param pageImpl
	 * @param creUserName
	 * @param creDeptName
	 * @param goOutTime
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(value = "query",opName = "查询列表")
	public PageImpl getList(PageImpl pageImpl,String userId,String deptId,String goOutTime, String subflag,String flag){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(goOutTime)) {
				startDate = goOutTime.substring(0, (goOutTime.length() + 1) / 2 - 1).trim();
				endDate = goOutTime.substring((goOutTime.length() + 1) / 2, goOutTime.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getPageListDraft(pageable,pageImpl,userId,deptId,startDate,endDate,subflag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	
}
