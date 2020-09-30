package com.sinosoft.sinoep.modules.video.background.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;
import com.sinosoft.sinoep.modules.video.background.entity.VideoContent;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoContentDao extends BaseRepository<VideoContent,String> {

    @Transactional
    @Modifying
    @Query("update VideoContent t set t.visible='0' where t.id =?1")
    public int delVisible(String id);

    @Transactional
    @Modifying
    @Query("update VideoContent t set t.isZd =?1 where t.id = ?2")
    public int updateZdById(String isZd,String id);

    public List<VideoContent> findByVisibleAndColumnIdAndIsZdIsNotNullOrderByIsZdAsc(String visible,String columnId);

}
