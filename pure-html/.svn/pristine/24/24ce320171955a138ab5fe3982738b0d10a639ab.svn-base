package com.sinosoft.sinoep.sendFLowWorkflow.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;
import org.springframework.data.jpa.repository.Query;

public interface SendFlowTemplateDao extends BaseRepository<SendFlowTemplate,String>{

    @Query(" select s.ruleTypeTable.ruleType from SendFlowTemplate s where s.id = ?1 ")
    public String getRuleTypeById(String id);

    @Query(" select s.ruleTypeTable.allPhase from SendFlowTemplate s where s.id =?1 ")
    public String getAllPhaseById(String id);




}
