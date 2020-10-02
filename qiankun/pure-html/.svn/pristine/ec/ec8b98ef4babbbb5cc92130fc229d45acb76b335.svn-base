package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowInfo;

import java.util.List;

public interface SendFlowInfoService {

    public void generateFlowInfo(String templateId);

   /* public List<SendFlowInfo> getSubSendFlowInfoList(String objectId, String unitId, String templateId, String businessTableName, String businessTableId);
*/
   public List<SendFlowInfo> getSubInfo(String objectId, String status,String deptType,String deptId);

    public boolean updateSendFlowInfo(String status,String tempalteId,String objectId,String deptId);

    public  int getCountInfoSum(String templateId,String objectId);

    public int getCountInfo(String status,String templateId,String objectId);

    public int getCountInfoBySuperIdSum(String templateId,String objectId,String superUnitId);

    public int getCountInfoBySuperId(String status,String templateId,String objectId,String superUnitId);

}
