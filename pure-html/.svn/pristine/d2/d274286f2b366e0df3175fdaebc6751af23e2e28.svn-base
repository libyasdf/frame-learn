package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 年度计划主表Dao
 * @Author: 李帅
 * @Date: 2018/8/30 9:32
 */
public interface NdjhDao extends BaseRepository<NdjhEntity,String> {
    @Query(value="select n from NdjhEntity n where n.annual=?1 and n.partyOrganizationId=?2 and n.visible=?3")
    public List<NdjhEntity> findByAnnual(String annual, String partyOrganizationId, String visible);
    public NdjhEntity findOne(String id);
}
