package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;

public interface SendFlowTemplateService {


    //根据模板id获取规则id
    public String getRuleTypeByTemplateId(String templateId);
    //根据模板获取期数
    public String getAllPhaseByTemplateId(String templateId);

    public SendFlowTemplate getSendFlowTemplateById(String id);
}
