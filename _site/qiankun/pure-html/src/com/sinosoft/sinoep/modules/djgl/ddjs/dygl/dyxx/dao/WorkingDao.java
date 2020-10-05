package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglWorkingEntity;

/**
 * 党员管理-工作岗位-数据层
 * author 冯建海
 */
public interface WorkingDao extends BaseRepository<DdjsDyglWorkingEntity, String> {
    public DdjsDyglWorkingEntity findByWorkingSuperId(String workingSuperId);

    public DdjsDyglWorkingEntity findFirstByWorkingSuperIdAndVisibleOrderByBeforeTimeDesc(String workingSuperId,String visible);
}
