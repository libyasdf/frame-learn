package com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao;

import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

public interface QuestionRepository {
	/*
	 * 随机组题 返回试题
	 */
	List<Question> radomList(Question question,String num);
	
	/*获取所有试题
	 * 
	 */
	List<Question> getList(String id);
	
	/*
	 * 获取一条试题的选项
	 */
	List<Option> getOptionList(String id);
	
	int getQuestionJuCount(String difficultyLevel,String questionType,String type);
}
