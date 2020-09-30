package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.DymzpyEntity;

import java.util.List;

/**
 * TODO 党组织管理 党员民主评议Dao
 * @Author: 李帅
 * @Date: 2018/9/9 15:06
 */
public interface DymzpyDao extends BaseRepository<DymzpyEntity,String> {
    public DymzpyEntity findFirstByReviewidAndPartyIdAndVisible(String reviewid ,String partyId,String visible);

    List<DymzpyEntity> findByReviewidAndVisible(String reviewid, String visible);

}
