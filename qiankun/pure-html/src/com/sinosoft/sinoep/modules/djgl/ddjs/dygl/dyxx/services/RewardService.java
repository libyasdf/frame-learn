package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglRewardEntity;
import org.springframework.data.domain.Pageable;

/**
 * 党员管理-党员信息-业务层
 * author 冯建海
 */
public interface RewardService {


    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglRewardEntity entity, String startTime, String endTime, String id, String type);

    public int delete(String id);

    public DdjsDyglRewardEntity getById(String id);


    public DdjsDyglRewardEntity saveForm(DdjsDyglRewardEntity entity);

    public DdjsDyglRewardEntity getBySuperId(String superId);
}
