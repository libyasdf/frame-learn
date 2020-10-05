package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;

public interface XxkhQuestionGroupRepository {
	List<XxkhQuestionGroup> getList(String id);

	/**
	 * @Author 王富康
	 * @Description //TODO 只获取简答题的试题组
	 * @Date 2018/9/18 19:45
	 * @Param [id, questionType]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup>
	 **/
    List<XxkhQuestionGroup> getSortAnswerList(String id,String questionType);
	
	XxkhQuestionGroup getLevelCount(String id);
	
	XxkhQuestionGroup getEverScore(String tizuId,String questionId);
}
