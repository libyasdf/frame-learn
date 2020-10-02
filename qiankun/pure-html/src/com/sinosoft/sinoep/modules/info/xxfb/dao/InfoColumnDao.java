package com.sinosoft.sinoep.modules.info.xxfb.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InfoColumnDao extends BaseRepository<InfoColumn,String> {

    @Transactional
    @Modifying
    @Query("update InfoColumn t set t.visible = '0' where t.id = ?1")
    public int delVisible(String id);

	public InfoColumn findByColumnCode(String columnCode);

	public InfoColumn findByColumnCodeAndVisible(String columnCode,String visible);
}
