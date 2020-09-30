package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;

/**
 * TODO 人员试卷关联dao层
 * @author 李颖洁  
 * @date 2018年8月16日  下午3:06:13
 */
public interface XxkhUserPaperDao extends BaseRepository<XxkhUserPaper, String>{
	
	@Query(" from XxkhUserPaper t where t.paperId = ?1 and t.testId= ?2 and t.userId = ?3")
	public List<XxkhUserPaper> getUserByPaperIdtestIdUserId(String paperId,String testId,String userId);
}
