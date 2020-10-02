package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;

public interface XxkhTestInfoDao extends BaseRepository<XxkhTestInfo, String>{
	
	@Modifying
	@Query(" update XxkhTestInfo t set t.visible = '0' where t.id = ?1")
	int deleteLuoJi(String id);
}
