package com.sinosoft.sinoep.modules.system.config.honor.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.system.config.honor.entity.HonorDetails;
import java.util.List;

public interface HonorDetailsDao extends BaseRepository<HonorDetails, String>{

    List<HonorDetails> findByVisibleAndHonor_IdOrderByOrderNumberAsc(String visible, String honorId);

}
