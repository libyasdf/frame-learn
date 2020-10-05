package com.sinosoft.sinoep.modules.zhbg.xxkh.grade.controller;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.grade.services.GradeService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services.TestManageService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhbg/xxkh/grade")
public class GradeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GradeService gradeService;
	@Autowired
	private TestManageService testManageService;
	
	/**
	 * 
	 * @param pageImpl
	 * @param info
	 * @return
	 * @author 颜振兴
	 * PageImpl
	 * TODO "获取人工评卷列表
	 */
	@LogAnnotation(value = "query",opName = "获取人工评卷list")
	@RequestMapping("gradeList")
	@ResponseBody
	public PageImpl getGradeList(PageImpl pageImpl,XxkhTestInfo info) {
		pageImpl.setFlag("0");
		try {
			List<XxkhTestInfo> gradeList = gradeService.getGradeList(pageImpl, info);
			if (gradeList.size()>0) {
				for(XxkhTestInfo info2:gradeList) {
					info2.setStartTime(info2.getStartTime()+" - "+info2.getEndTime());
				}
				pageImpl.getData().setRows(gradeList);
				pageImpl.getData().setTotal(gradeList.get(0).getTotal());
				pageImpl.setFlag("1");
			}else {
				pageImpl.getData().setTotal(0);
				pageImpl.setFlag("1");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * @Title: GradeController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.grade.controller
	 * @Description: TODO修改用户交卷状态
	 * @author 颜振兴
	 * @date 2018年9月19日
	 * @param id
	 * @param all
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改用户交卷状态")
	@RequestMapping("getTestToUser")
	@ResponseBody
	public JSONObject getTestToUser(String id,String all) {
		JSONObject json = new  JSONObject();
		List<XxkhUserPaper> list = testManageService.getUserPaperByTestId(id, "");
		int userNumber = 0;
		for(XxkhUserPaper userPaper:list) {
			if (userPaper.getArtificialMarkingState().equals("1")) {
				userNumber++;
			}
		}
		if(StringUtils.isNotBlank(all)) {
			json.put("flag", "0");
			XxkhTestInfo xxkhTestInfo = new XxkhTestInfo();
			xxkhTestInfo.setId(id);
			xxkhTestInfo.setMarkStatus("2");
			int updateTestPingJuan = testManageService.updateTestPingJuan(xxkhTestInfo);
			int updateTestAllUser = testManageService.updateTestAllUser(id);
			if (updateTestPingJuan>0&&updateTestAllUser>0) {
				json.put("flag", "1");
			}
			return json;
		}
		if (userNumber==0) {
			json.put("flag", "0");
		}
		if (userNumber<list.size()&&userNumber!=0) {
			XxkhTestInfo xxkhTestInfo = new XxkhTestInfo();
			xxkhTestInfo.setId(id);
			xxkhTestInfo.setMarkStatus("1");
			int updateTestPingJuan = testManageService.updateTestPingJuan(xxkhTestInfo);
			if (updateTestPingJuan>0) {
				json.put("flag", "1");
			}
		}
		if (userNumber==list.size()) {
			XxkhTestInfo xxkhTestInfo = new XxkhTestInfo();
			xxkhTestInfo.setId(id);
			xxkhTestInfo.setMarkStatus("2");
			int updateTestPingJuan = testManageService.updateTestPingJuan(xxkhTestInfo);
			if (updateTestPingJuan>0) {
				json.put("flag", "1");
			}
		}
		return json;
	}
}
