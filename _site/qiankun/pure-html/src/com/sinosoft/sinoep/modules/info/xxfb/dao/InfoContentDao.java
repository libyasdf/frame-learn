package com.sinosoft.sinoep.modules.info.xxfb.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InfoContentDao extends BaseRepository<InfoContent,String> {

    @Transactional
    @Modifying
    @Query("update InfoContent t set t.visible='0' where t.id =?1")
    public int delVisible(String id);

    @Transactional
    @Modifying
    @Query("update InfoContent t set t.isZd =?1 where t.id = ?2")
    public int updateZdById(String isZd,String id);

    public List<InfoContent> findByVisibleAndColumnIdAndIsZdIsNotNullOrderByIsZdAsc(String visible,String columnId);

}
