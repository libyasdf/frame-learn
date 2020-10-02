package com.sinosoft.sinoep.modules.video.background.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoColumnFbUserDao extends BaseRepository<VideoColumnFbUser,String> {

    public List<VideoColumnFbUser> findByColumnIdAndVisible(String columnId,String visible);

    @Transactional
    @Modifying
    @Query(" update VideoColumnFbUser t set t.visible='0' where t.columnId=?1")
    public int deleteAllVisible(String columnId);

    @Transactional
    @Modifying
    @Query(" update VideoColumnFbUser t set t.visible='0' where t.id=?1")
    public int delVisible(String id);

    public List<VideoColumnFbUser> findByVisibleAndColumnIdAndFbUserId(String visible,String columnId,String fbUserId);
   
    public List<VideoColumnFbUser> findByVisibleAndFbUserId(String visible,String fbUserId);
    
    @Transactional
    @Modifying
    @Query(" from VideoColumnFbUser t where t.visible=?1 and t.shUserId=?2")
    public List<VideoColumnFbUser> findByVisibleAndShUserId(String visible,String shUserId);
    
}
