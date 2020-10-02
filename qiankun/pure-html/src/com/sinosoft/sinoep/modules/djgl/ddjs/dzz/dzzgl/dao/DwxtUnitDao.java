package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtUnit;

import java.util.List;

public interface DwxtUnitDao extends BaseRepository<DwxtUnit, String> {

    List<DwxtUnit> findByDwxtOrgIdAndVisibleOrderByCreTimeAsc(String dwxtOrgId,String visible);

}
