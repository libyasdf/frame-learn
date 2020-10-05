package com.sinosoft.sinoep.sendFLowWorkflow.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SendFlowInfoDao extends SendFlowInfoDaoExt<SendFlowInfo>,BaseRepository<SendFlowInfo,String> {

    public List<SendFlowInfo> getByObjectIdAndStatus(String objectId, String status);

    public List<SendFlowInfo> getByObjectIdAndStatusAndUnitId(String objectId, String status,String unitId);

    public List<SendFlowInfo> getByObjectIdAndStatusAndSuperUnitId(String objectId, String status,String superUnitId);

    @Transactional
    @Modifying
    @Query(" update SendFlowInfo t set t.status = ?1 where t.templateId = ?2 and t.objectId = ?3 and t.unitId = ?4")
    public void updateSendFlowInfo(String status,String templateId,String objectId,String unitId);

    public int countAllByTemplateIdAndObjectId(String templateId,String objectId);

    public int countAllByStatusAndTemplateIdAndObjectId(String status,String templateId,String objectId);

    public int countAllByTemplateIdAndObjectIdAndSuperUnitId(String templateId,String objectId,String superUnitId);

    public int countAllByStatusAndTemplateIdAndObjectIdAndSuperUnitId(String status,String templateId,String objectId,String superId);
}
