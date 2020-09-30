package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.entity.NdzjEntity;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 李帅
 * @Date: 2018/9/8 8:07
 */
public interface NdzjDao extends BaseRepository<NdzjEntity,String> {
    @Query(value="select n from NdzjEntity n where n.reportingTime like %?1% and n.partyOrganizationId=?2 and n.visible=?3")
    public NdzjEntity findByReportingTime(String reportingTime, String partyOrganizationId,String visible);
}
