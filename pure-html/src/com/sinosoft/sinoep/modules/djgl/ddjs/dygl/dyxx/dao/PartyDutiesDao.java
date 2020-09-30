package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglPartyDutiesEntity;

/**
 * 党员管理-党内职务-数据层
 * author 冯建海
 */
public interface PartyDutiesDao extends BaseRepository<DdjsDyglPartyDutiesEntity, String> {
    public DdjsDyglPartyDutiesEntity findByPartyDutiesSuperId(String partyDutiesSuperId);

    public DdjsDyglPartyDutiesEntity findFirstByPartyDutiesSuperIdOrderByTenureTimeDesc(String partyDutiesSuperId);
}
