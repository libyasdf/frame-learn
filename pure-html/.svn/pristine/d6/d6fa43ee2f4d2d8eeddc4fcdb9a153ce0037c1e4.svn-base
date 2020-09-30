package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglPartybasicinfoEntity;

/**
 * 党员管理-党员基本信息-数据层
 * author 冯建海
 */
public interface PartyBasicInfoDao extends BaseRepository<DdjsDyglPartybasicinfoEntity, String> {
    public DdjsDyglPartybasicinfoEntity findByPartybasicinfoSuperId(String partybasicinfoSuperId);
    public DdjsDyglPartybasicinfoEntity findByPartybasicinfoSuperIdAndVisible(String partybasicinfoSuperId,String visible);

    public int countByJoinPartyTimeLessThan(String joinTime);
    public int countByJoinPartyTimeGreaterThanAndJoinPartyTimeLessThan(String startTime,String endTime);
    public int countByJoinPartyTimeGreaterThan(String startTime);
}
