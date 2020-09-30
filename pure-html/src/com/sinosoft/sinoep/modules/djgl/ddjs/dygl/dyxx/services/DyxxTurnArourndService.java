package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglAroundEntity;

/**
 * 党员管理-党员信息-业务层
 * author 冯建海
 */
public interface DyxxTurnArourndService {


    public DdjsDyglAroundEntity save(DdjsDyglAroundEntity entity);

    public DdjsDyglAroundEntity getById(String id);
}
