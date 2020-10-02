package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces;

import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;

public interface XxkhQuestionGroupService {
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO添加一个试题组
	 * @author 颜振兴
	 * @date 2018年8月14日
	 * @param group
	 * @return
	 */
	XxkhQuestionGroup save(XxkhQuestionGroup group);
	
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO修改一个试题组
	 * @author 颜振兴
	 * @date 2018年8月14日
	 * @param group
	 * @return
	 */
	XxkhQuestionGroup update(XxkhQuestionGroup group);
	
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO查询一个试题组
	 * @author 颜振兴
	 * @date 2018年8月14日
	 * @param group
	 * @return
	 */
	XxkhQuestionGroup getbyId(XxkhQuestionGroup group);
	
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO删除一个试题组
	 * @author 颜振兴
	 * @date 2018年8月14日
	 * @param group
	 * @return
	 */
	int delete(XxkhQuestionGroup group);

	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO添加一个自动的题组
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param group
	 * @return
	 */
	XxkhQuestionGroup autosave(XxkhQuestionGroup group);

	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO修改自动组卷题的数量
	 * @author 颜振兴
	 * @date 2018年8月20日
	 * @param groupId
	 * @param jiandan
	 * @param yiban
	 * @param kunnan
	 * @param everyScore
	 * @param isZujuan 传0或1 1表示组卷 0 不组卷
	 * @return
	 */
	XxkhQuestionGroup update(String groupId, String jiandan, String yiban, String kunnan, String everyScore,String fullScore,String isZuJuan);
	
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO获取三种难度级别的数量
	 * @author 颜振兴
	 * @date 2018年8月21日
	 * @param group
	 * @return
	 */
	XxkhQuestionGroup getLevelCount(XxkhQuestionGroup group);
	/**
	 * 
	 * @Title: XxkhQuestionGroupService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces
	 * @Description: TODO根据题型id和试题id查题的分数
	 * @author 颜振兴
	 * @date 2018年9月7日
	 * @param questionGroupId
	 * @param questionId
	 * @return
	 */
	XxkhQuestionGroup getEveryScore(String questionGroupId,String questionId);
}
