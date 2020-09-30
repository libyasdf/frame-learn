package com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;

public interface XxkhQuestionQgroupDao extends BaseRepository<XxkhQuestionQgroup, String> ,  XxkhQuestionQgroupRepository  {

	//根据id删除
	@Query(value = "delete from XxkhQuestionQgroup t where t.questionId=?1 and t.questionGroupId=?2 ")
	@Modifying
	public int deleteByTiIdAndZuId(String tid,String zid);
	
	//根据id删除
	@Query(value = "delete from XxkhQuestionQgroup t where  t.questionGroupId=?1 ")
	@Modifying
	public int deleteByZuId(String zuid);
	
	@Query(" from XxkhQuestionQgroup t where t.questionGroupId=?1 and t.questionId=?2 ")
	public List<XxkhQuestionQgroup> isCheck(String tizud,String tiid);
	
	@Query(" from XxkhQuestionQgroup t where t.questionGroupId=?1")
	public List<XxkhQuestionQgroup> getByIdList(String id);
}
