package com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao.XxkhPaperInfoRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 试题dao层
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:13:44
 */
public interface QuestionDao extends BaseRepository<Question,String> ,  QuestionRepository {
	
	@Query(" from Question t where t.id = ?1")
	List<Question> getById(String id);
	
	@Query(" select count(*) from Question t where t.difficultyLevel = ?1 and t.questionType = ?2 and t.type = ?3 and t.state='1' and t.visible = '1'" )
	int getQuestionCount(String difficultyLevel,String questionType,String type);
}
