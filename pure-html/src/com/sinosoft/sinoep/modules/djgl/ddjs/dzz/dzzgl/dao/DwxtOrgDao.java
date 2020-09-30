package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;

import java.util.List;


public interface DwxtOrgDao extends BaseRepository<DwxtOrg, String> {

    List<DwxtOrg> findBySuperIdAndVisible(String orgId,String visible);

    DwxtOrg findByOrgIdAndVisible(String orgId,String visible);

    List<DwxtOrg> findByAssociativeUserIdAndVisible(String associativeUserId,String visible);

}
