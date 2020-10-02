package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;

public interface XxkhPaperInfoDao extends BaseRepository<XxkhPaperInfo, String> ,  XxkhPaperInfoRepository {

	@Query(" from XxkhPaperInfo t  where  t.visible='1' and t.id = ?1 ")
	public List<XxkhPaperInfo> getById(String id);

	@Modifying
	@Query("update XxkhPaperInfo t set t.visible = '0',t.state = '0' where t.id = ?1")
	public int delVisible(String id);
}
