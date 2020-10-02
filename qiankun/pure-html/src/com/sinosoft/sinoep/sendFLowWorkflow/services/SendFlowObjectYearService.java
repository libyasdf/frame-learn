package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;

import java.util.List;

public interface SendFlowObjectYearService {

    public List<SendFlowObject> addYearSendFlowObject(String templateId);
}
