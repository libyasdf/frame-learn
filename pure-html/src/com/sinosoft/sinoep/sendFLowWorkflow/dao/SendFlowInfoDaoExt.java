package com.sinosoft.sinoep.sendFLowWorkflow.dao;

import java.util.List;

public interface SendFlowInfoDaoExt<T>{
    public void batchInsert(List<T> list);
}
