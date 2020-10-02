package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglDegreeEntity;

/**
 * 党员管理-学位学历-数据层
 * author 冯建海
 */
public interface DegreeDao extends BaseRepository<DdjsDyglDegreeEntity, String> {
    public DdjsDyglDegreeEntity findByDegreeSuperId(String degreeSuperId);
    public DdjsDyglDegreeEntity findFirstByDegreeSuperIdOrderByDegreeAwardTimeDesc(String degreeSuperId);
    public DdjsDyglDegreeEntity findFirstByDegreeSuperIdAndVisibleOrderByEnrolmentTimeDesc(String degreeSuperId,String visible);
}
