package com.sinosoft.sinoep.message.alert.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.message.alert.entity.MessageAlert;

public interface MessageAlertDao extends BaseRepository<MessageAlert, String> {
	@Modifying
	@Query(" update MessageAlert m set m.settingPath = ?1 where m.userId = ?2")
	public int updatePath(String path,String userId);
	
	@Query(" from MessageAlert m where m.userId = ?1")
	public List<MessageAlert> getByUserId(String userId);

	@Modifying
	@Query(" update MessageAlert m set m.themeClass = ?1 where m.userId = ?2")
	public int updateTheme(String themeClass, String userId);
	
	@Modifying
	@Query(" update MessageAlert m set m.classify = ?1 where m.userId = ?2")
	public int updateType(String classify, String userId);
}
