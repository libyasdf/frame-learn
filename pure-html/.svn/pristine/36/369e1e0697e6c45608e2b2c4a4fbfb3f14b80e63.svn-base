package com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.services.GradesearchService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;

@Controller
@RequestMapping("/zhbg/xxkh/gradesearch/")
public class GradesearchController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GradesearchService gradesearchService;

	/**
	 * 
	 * @Title: TestManageController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.controller
	 * @Description: TODO成绩查询
	 * @author 颜振兴
	 * @date 2018年9月10日
	 * @param pageImpl
	 * @param info
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "成绩查询")
	@ResponseBody
	@RequestMapping(value="getScoreInquiry")
	public PageImpl getScoreInquiry(PageImpl pageImpl,XxkhTestInfo info,String timeRange) {
		pageImpl.setFlag("0");
		try {
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[0])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[0]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[1])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[1]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[2])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[2]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[3])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[3]);
			}
			List<XxkhTestInfo> scoreInquiry = gradesearchService.getScoreInquiry(pageImpl, info,timeRange);
			if (scoreInquiry.size()>0) {
				pageImpl.setFlag("1");
				pageImpl.getData().setRows(scoreInquiry);
				pageImpl.getData().setTotal(scoreInquiry.get(0).getTotal());
			}else {
				pageImpl.setFlag("1");
				pageImpl.getData().setTotal(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * @Title: GradesearchController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.controller
	 * @Description: TODO成绩查询详情
	 * @author 颜振兴
	 * @date 2018年9月12日
	 * @param pageImpl
	 * @param info
	 * @param isJiGe
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "成绩查询详情")
	@ResponseBody
	@RequestMapping(value="getbyTestContent")
	public PageImpl getbyTestContent(PageImpl pageImpl,XxkhTestInfo info,String isJiGe) {
		pageImpl.setFlag("0");
		try {
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[0])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[0]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[1])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[1]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[2])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[2]);
			}
			if (info.getType().indexOf(XXKHCommonConstants.XXKH_TYPE[3])!=-1) {
				info.setType(XXKHCommonConstants.TEST_TYPE[3]);
			}
			List<XxkhUserPaper> getbyTestContent = gradesearchService.getbyTestContent(pageImpl, info,isJiGe);
			if (getbyTestContent.size()>0) {
				for(XxkhUserPaper paper:getbyTestContent) {
					if(paper.getArtificialMarkingState().equals("1")) {
						if (Integer.parseInt(paper.getPaperObjectiveScore())+Integer.parseInt(paper.getPaperSubjectiveScore())>=Integer.parseInt(paper.getPassScore())) {
							paper.setIsJiGe("是");
						}else {
							paper.setIsJiGe("否");
						}
						paper.setSumScore(Integer.parseInt(paper.getPaperObjectiveScore())+Integer.parseInt(paper.getPaperSubjectiveScore())+"");
					}else {
						if (Integer.parseInt(paper.getPaperObjectiveScore())>=Integer.parseInt(paper.getPassScore())) {
							paper.setIsJiGe("是");
						}else {
							paper.setIsJiGe("否");
						}
						paper.setPaperSubjectiveScore("-");
						paper.setSumScore(Integer.parseInt(paper.getPaperObjectiveScore())+"");
					}
					
					
				}
				pageImpl.setFlag("1");
				pageImpl.getData().setRows(getbyTestContent);
				pageImpl.getData().setTotal(getbyTestContent.get(0).getTotal());
			}else {
				pageImpl.setFlag("1");
				pageImpl.getData().setTotal(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 查询个人考试成绩信息列表
	 * @author 李颖洁  
	 * @date 2018年9月8日下午2:29:42
	 * @param pageImpl
	 * @param info 考试信息（名称或类型）
	 * @param isPass 是否及格
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询个人考试成绩信息")
	@ResponseBody
	@RequestMapping(value="getPersonalTestScore")
	public PageImpl getPersonalTestScore(PageImpl pageImpl,XxkhTestInfo info,String isPass){
		pageImpl.setFlag("0");
		try {
			List<XxkhTestInfo> list = gradesearchService.getPersonalTestScore(pageImpl,info,isPass);
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(list);
			if(list.size()>0){
				pageImpl.getData().setTotal(list.get(0).getTotal());
			}else{
				pageImpl.getData().setTotal(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
}
