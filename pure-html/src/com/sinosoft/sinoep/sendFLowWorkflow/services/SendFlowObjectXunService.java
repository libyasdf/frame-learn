package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;

import java.util.List;

public interface SendFlowObjectXunService {

    public List<SendFlowObject> addXunSendFlowObject(String templateId);

    /**
     * 旬报最大期数
     * @param templateId
     * @param ruleYear
     * @param ruleMonth
     * @return
     */
    public String getMaxPhase(String templateId, String ruleYear, String ruleMonth);
}
