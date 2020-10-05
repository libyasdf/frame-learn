package com.sinosoft.sinoep.modules.dagl.daly.borrow.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglFile;

import java.util.List;

public interface DaglFileDao  extends BaseRepository<DaglFile, String> {

    public List<DaglFile> findByRecidAndVisible (String recid, String visible);
    //public List<DaglFile> findByIdIn(String[] idArr);
    //public List<DaglFile> findByVisibleAndReturnDate(String visible,String returnDate);
    public List<DaglFile> findByVisibleAndOldId(String visible,String oldId);
    public List<DaglFile> findByborrowIdContainingAndVisibleOrderByOrderNumAsc (String borrowId, String visible);
    //public List<DaglFile> findByShouldReturnDateAndVisibleAndReturnDateIsNull (String shouldReturnDate, String visible);
    //public List<DaglFile> findByVisibleAndInRenewAndReturnDateIsNotNullAndUseResultIsNull (String visible, String inRenew);
    //public List<DaglFile> findByborrowIdContainingAndVisible (String borrowId, String visible);
}
