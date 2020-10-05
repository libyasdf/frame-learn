package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglAdministrativeEntity;

/**
 * 党员管理--数据层
 * author 冯建海
 */
public interface AdministrativeDao extends BaseRepository<DdjsDyglAdministrativeEntity, String> {

    public DdjsDyglAdministrativeEntity findByAdministrativeSuperId(String administrativeSuperId);

    public DdjsDyglAdministrativeEntity findFirstByAdministrativeSuperIdOrderByXzTenureTimeDesc(String administrativeSuperId);
    public DdjsDyglAdministrativeEntity findFirstByAdministrativeSuperIdAndVisibleOrderByXzTenureTimeDesc(String administrativeSuperId,String visible);

}
