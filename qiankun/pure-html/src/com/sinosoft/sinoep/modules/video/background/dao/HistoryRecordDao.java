package com.sinosoft.sinoep.modules.video.background.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.video.background.entity.HistoryRecord;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HistoryRecordDao extends BaseRepository<HistoryRecord,String> {

  
}
