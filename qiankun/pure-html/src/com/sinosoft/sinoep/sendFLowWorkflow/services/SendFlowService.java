package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.CountInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.WaitDoParamBean;

import java.util.List;
import java.util.Map;

public interface SendFlowService {
    public Map<String,List<SendFlowInfo>> getWaitDoList(WaitDoParamBean param);

    public List<CountInfo> getCountInfo(WaitDoParamBean param);
}
