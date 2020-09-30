package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;

import java.util.List;


public interface DwxtLdcyDao extends BaseRepository<DwxtLdcy, String> {

    List<DwxtLdcy> findByHjxjIdAndVisible(String HjxjId, String visible);
    public List<DwxtLdcy> findByHjxjIdAndLeaderIdAndIsTenureAndVisible (String hjxjId, String leaderId,String isTenure,String visible);

}
