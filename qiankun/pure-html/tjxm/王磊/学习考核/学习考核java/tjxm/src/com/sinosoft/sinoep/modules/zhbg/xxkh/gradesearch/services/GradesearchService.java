package com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.services;

import java.util.List;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;

public interface GradesearchService {
	
	/**
	 * 
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO成绩查询
	 * @author 颜振兴
	 * @date 2018年9月10日
	 * @param pageImpl
	 * @param testInfo
	 * @param timeRange 
	 * @return
	 */
	List<XxkhTestInfo> getScoreInquiry(PageImpl pageImpl,XxkhTestInfo testInfo, String timeRange);
	
	/**
	 * 
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO获取考试所有的人员
	 * @author 颜振兴
	 * @date 2018年9月10日
	 * @param pageImpl
	 * @param testInfo
	 * @param isJiGe 
	 * @return
	 */
	List<XxkhUserPaper> getbyTestContent(PageImpl pageImpl,XxkhTestInfo testInfo, String isJiGe);
	
	/**
	 *  TODO 查询个人考试成绩列表
	 * @author 李颖洁  
	 * @date 2018年9月8日下午2:53:41
	 * @param pageImpl
	 * @param info
	 * @param isPass
	 * @return List<XxkhTestInfo>
	 */
	List<XxkhTestInfo> getPersonalTestScore(PageImpl pageImpl, XxkhTestInfo info, String isPass);
}
