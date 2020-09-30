package com.sinosoft.sinoep.modules.info.xxfb.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InfoColumnFbUserDao extends BaseRepository<InfoColumnFbUser,String> {

    public List<InfoColumnFbUser> findByColumnIdAndVisible(String columnId,String visible);

    @Transactional
    @Modifying
    @Query(" update InfoColumnFbUser t set t.visible='0' where t.columnId=?1")
    public int deleteAllVisible(String columnId);

    @Transactional
    @Modifying
    @Query(" update InfoColumnFbUser t set t.visible='0' where t.id=?1")
    public int delVisible(String id);

    public List<InfoColumnFbUser> findByVisibleAndColumnIdAndFbUserId(String visible,String columnId,String fbUserId);
}
