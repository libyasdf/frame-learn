package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;

import java.util.List;

/**
 * 党员管理-党员信息-数据层
 * author 冯建海
 */
public interface DyxxDao extends BaseRepository<DdjsDyglUserbasicinfoEntity, String> {

    public int countByVisibleAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String typeOfPersonnel1,String typeOfPersonnel2);



    //public int countByIsHistorypartyAndSex(String isHistroyStatus, String sexType);
    public int countByVisibleAndSexAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String sexType, String typeOfPersonnel1,String typeOfPersonnel2);

    //public int countByIsHistorypartyAndNationNot(String isHistroyStatus, String nation);
    public int countByVisibleAndNationNotAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String nation, String typeOfPersonnel1,String typeOfPersonnel2);

   // public int countByIsHistorypartyAndRyEducationNot(String isHistroyStatus, String education);
   public int countByVisibleAndRyEducationNotAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String education, String typeOfPersonnel1,String typeOfPersonnel2);

    //public int countByIsHistorypartyAndCreTimeLike(String isHistroyStatus, String likeFiledVal);
    public int countByVisibleAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String likeFiledVal, String typeOfPersonnel1,String typeOfPersonnel2);

    //public int countByIsHistorypartyAndSexAndCreTimeLike(String isHistroyStatus, String sexType,String likeFiledVal);
    public int countByVisibleAndSexAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String sexType, String likeFiledVal, String typeOfPersonnel1,String typeOfPersonnel2);

    //public int countByIsHistorypartyAndNationNotAndCreTimeLike(String isHistroyStatus, String nation, String likeFiledVal);
    public int countByVisibleAndNationNotAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String nation, String likeFiledVal, String typeOfPersonnel1,String typeOfPersonnel2);


    //public int countByIsHistorypartyAndRyEducationNotAndCreTimeLike(String isHistroyStatus, String education, String likeFiledVal);
    public int countByVisibleAndRyEducationNotAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(String visible, String education, String likeFiledVal, String typeOfPersonnel1,String typeOfPersonnel2);

    public List<DdjsDyglUserbasicinfoEntity> findByPartyOrganizationIdAndVisible (String PartyOrganizationId, String visible);

    public List<DdjsDyglUserbasicinfoEntity> findByUserIdAndVisible (String userId, String visible);
}
