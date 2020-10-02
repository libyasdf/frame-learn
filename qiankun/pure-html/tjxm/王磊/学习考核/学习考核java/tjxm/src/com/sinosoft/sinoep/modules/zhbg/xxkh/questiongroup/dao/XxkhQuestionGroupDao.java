package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
public interface XxkhQuestionGroupDao extends BaseRepository<XxkhQuestionGroup, String> ,  XxkhQuestionGroupRepository {
	
@Query("from XxkhQuestionGroup t where t.paperId = ?1 order by t.creTime")
public List<XxkhQuestionGroup> byList(String paperId);

@Query("from XxkhQuestionGroup t where t.id = ?1")
public List<XxkhQuestionGroup> byId(String id);
}
