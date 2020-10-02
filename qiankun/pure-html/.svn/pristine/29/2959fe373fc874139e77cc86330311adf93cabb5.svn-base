package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao;

import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;

import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;


public interface XxkhPaperInfoRepository {

	List<String> getLevelCount(String id);
	/**
	 *
	 * @Title: XxkhPaperInfoRepository.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao
	 * @Description: TODO是否可以删除
	 * @author 颜振兴
	 * @date 2018年9月17日
	 * @param paperID
	 * @return
	 */
	List<XxkhTestInfo> isCanDelete(String paperID);

	/**
	 *
	 * @Title: XxkhPaperInfoRepository.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao
	 * @Description: TODO是否可以编辑
	 * @author 颜振兴
	 * @date 2018年9月17日
	 * @param paperID
	 * @return
	 */
	List<XxkhTestInfo> isCanUpdata(String paperID, int i);

	/**
	 * @Author 王富康
	 * @Description //TODO 考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段
	 * @Date 2018/9/17 20:13
	 * @Param [paperInfo, testId, userId]
	 * @return net.sf.json.JSONObject
	 **/
	XxkhPaperInfo getTestResult(XxkhPaperInfo paperInfo, String testId, String userId);
}
