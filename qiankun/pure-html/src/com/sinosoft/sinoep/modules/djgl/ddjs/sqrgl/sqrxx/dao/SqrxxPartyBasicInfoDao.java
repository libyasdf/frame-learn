package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.entity.DdjsSqrglPartybasicinfoEntity;

import java.util.List;


/**
 * 申请人管理-申请人员情况-Dao
 * author 李帅
 */
public interface SqrxxPartyBasicInfoDao extends BaseRepository<DdjsSqrglPartybasicinfoEntity, String> {
    public DdjsSqrglPartybasicinfoEntity findBySuperId(String superId);


}
