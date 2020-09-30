package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglDegreeEntity;

/**
 * 党员管理-学位学历-业务层
 * author 冯建海
 */
public interface DyxxDrgeeService {


    public DdjsDyglDegreeEntity save(DdjsDyglDegreeEntity entity);

    public DdjsDyglDegreeEntity getById(String id);

    int delete(String id);
}
