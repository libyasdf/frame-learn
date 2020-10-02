package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglWorkingEntity;

/**
 * 党员管理-工作岗位-业务层
 * author 冯建海
 */
public interface DyxxWorkService {


    public DdjsDyglWorkingEntity save(DdjsDyglWorkingEntity entity);

    public DdjsDyglWorkingEntity getById(String id);

    int delete(String id);
}
