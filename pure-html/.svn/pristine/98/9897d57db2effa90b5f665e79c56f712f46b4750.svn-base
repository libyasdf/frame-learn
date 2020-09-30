package com.sinosoft.sinoep.sendFLowWorkflow.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import org.springframework.data.jpa.repository.Query;

public interface SendFlowObjectDao extends BaseRepository<SendFlowObject,String> {

    @Query(" select max(s.phase) from SendFlowObject s where s.templateId = ?1 and s.ruleYear = ?2 ")
    public String getMaxPhase(String templateId, String ruleYear);

    @Query(" select max(s.phase) from SendFlowObject s where s.templateId = ?1 and s.ruleYear = ?2 and s.ruleMonth = ?3")
    public String getMaxPhase(String templateId, String ruleYear, String ruleMonth);

    @Query(" select count(s.id) from SendFlowObject s where s.templateId = ?1 and s.ruleYear = ?2 ")
    public long getNumByTemplateIdAndRuleYear(String templateId, String ruleYear);

    @Query(" select count(s.id) from SendFlowObject s where s.templateId = ?1 " +
            " and s.ruleYear = ?2 and s.ruleMonth = ?3")
    public long getNumByTemplateIdAndRuleYearAndRuleMonth(String templateId, String ruleYear, String ruleMonth);

    public SendFlowObject getByTemplateIdAndRuleYear(String templateId, String ruleYear);

    public SendFlowObject getByTemplateIdAndRuleYearAndRuleMonth(String templateId, String ruleYear, String ruleMonth);

    public SendFlowObject getByTemplateIdAndRuleYearAndPhase(String templated,String ruleYear,String phase);

}
