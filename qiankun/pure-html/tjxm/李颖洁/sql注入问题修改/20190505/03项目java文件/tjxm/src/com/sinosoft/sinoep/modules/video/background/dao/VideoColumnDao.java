package com.sinosoft.sinoep.modules.video.background.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VideoColumnDao extends BaseRepository<VideoColumn,String> {

    @Transactional
    @Modifying
    @Query("update VideoColumn t set t.visible = '0' where t.id = ?1")
    public int delVisible(String id);

	public VideoColumn findByColumnCode(String columnCode);
	
	public VideoColumn findByColumnCodeAndVisible(String columnCode,String visible);
	
	@Transactional
    @Modifying
    @Query("update VideoColumn t set t.orderNo =?1 where t.id = ?2")
	public void updateZdById(String iszd, String id);
}
