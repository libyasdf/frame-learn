package com.sinosoft.sinoep.modules.system.online.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysOnlineTimeDao extends BaseRepository<SysOnlineTime,String> {

    @Query(" from SysOnlineTime where userId = ?1 and ticket = ?2")
    SysOnlineTime getTimeByUserIdAndTic(String userId,String ticket);

    @Query(" from SysOnlineTime where userId = ?1 order by loginTime desc")
    List<SysOnlineTime> getOnlineByUserId(String userId);

}
