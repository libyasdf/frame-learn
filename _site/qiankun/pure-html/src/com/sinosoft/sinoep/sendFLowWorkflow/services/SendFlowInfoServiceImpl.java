package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.collect.Lists;
import com.sinosoft.authorization.common.extinterface.DeptInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowInfoDao;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowInfoDaoExt;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowTemplateDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.WaitUnit;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.Const;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SendFlowInfoServiceImpl implements SendFlowInfoService {

    private static Log log = LogFactory.getLog(SendFlowTemplateServiceImpl.class);
    @Autowired
    private SendFlowObjectService sendFlowObjectService;

    @Autowired
    private SendFlowTemplateDao sendFlowTemplateDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SendFlowInfoDao sendFlowInfoDao;

    private JdbcTemplate jdbcTemplate;

    @Override
    public void generateFlowInfo(String templateId) {
        List<SendFlowObject> sendFlowObjects = this.sendFlowObjectService.addSendFlowObject(templateId);//生成实例
        //以下代码是生成sendflow_info流程信息
        List<SendFlowInfo> sendFlowInfoList = new ArrayList<SendFlowInfo>();
        for(SendFlowObject sendFlowobject:sendFlowObjects){
            String id = sendFlowobject.getId();
            SendFlowTemplate templateEntity = sendFlowTemplateDao.findOne(templateId);
            String tbDeptJb = templateEntity.getTbDeptJb();
            String tbDeptIds = templateEntity.getTbDeptIds();
            List<Map<String,Object>> DeptList = this.getTbDeptInfo(tbDeptJb,tbDeptIds);
            int n = 0;
            for(Map<String,Object> map :DeptList){
                SendFlowInfo sendflowinfo = new SendFlowInfo();
                sendflowinfo.setUnitId(map.get("deptid").toString());
                sendflowinfo.setUnitName(map.get("deptname").toString());
                sendflowinfo.setSuperUnitId(map.get("super_id").toString());
                sendflowinfo.setStatus(Constant.INFO_STATUS_NOT_REPORT);
                sendflowinfo.setId(null);
                sendflowinfo.setObjectId(id);
                sendflowinfo.setTemplateId(templateId);
                sendflowinfo.setIsHaveSubUnit("0");
                sendflowinfo.setStatus(Constant.INFO_STATUS_NOT_REPORT);
                sendflowinfo.setOrderNo(String.valueOf(n));
                sendFlowInfoList.add(sendflowinfo);
                sendflowinfo.setIsDel("0");
                log.info("给单位创建流程实例"+sendflowinfo.getUnitName()+sendflowinfo.getObjectId());
                n++;
            }
        }
        this.sendFlowInfoDao.batchInsert(sendFlowInfoList);
    }

    /**
     * 获取填报单位
     * @param tbDeptJb
     * @param tbDeptIds
     * @return
     */
    private List<Map<String,Object>> getTbDeptInfo(String tbDeptJb, String tbDeptIds){
        String deptIds = "";
        if(StringUtils.isNotEmpty(tbDeptIds)) {
            for (String s : tbDeptIds.split(",")) {
                deptIds += "'" + s + "',";
            }
            deptIds = deptIds.substring(0,deptIds.length()-1);
        }
        StringBuilder sql = new StringBuilder("select * from sys_flow_dept t where status = '1'");
        if(StringUtils.isNotEmpty(tbDeptJb) && StringUtils.isNotEmpty(deptIds)){
            sql.append(" and deptlevel='").append(tbDeptJb).append("' or deptid in ('").append(deptIds).append("')");
        }
        if(StringUtils.isNotEmpty(tbDeptJb) && StringUtils.isEmpty(deptIds)){
            sql.append(" and deptlevel='").append(tbDeptJb).append("'");
        }
        if(StringUtils.isNotEmpty(deptIds) && StringUtils.isEmpty(tbDeptJb)){
            sql.append(" and deptid in('").append(deptIds).append("') ");
        }
        sql.append(" start with deptid ='441' connect by prior deptid = super_id order siblings by order_no ") ;
        JSONObject json = userInfoService.getDateBySql(sql.toString());
        List<Map<String,Object>> list = json.getJSONArray("data");
        return list;
    }

    /*审批未审批通过数据public List<SendFlowInfo> getSubSendFlowInfoList(String objectId,String unitId, String templateId, String businessTableName,
                                                     String businessTableId) {
        List<Map<String, Object>> benJiDatas = Lists.newArrayList();
        List<SendFlowInfo> infos = new ArrayList<SendFlowInfo>();
        if(StringUtils.isNotBlank(objectId) && StringUtils.isNotBlank(unitId)){
            StringBuilder sql = new StringBuilder("");
            sql.append(" select t.* from sendflow_info t where t.object_Id = '").append(objectId).append("'");
            sql.append(" and t.super_unit_id = '").append(unitId).append("'  order by t.time desc");
            List<Map<String, Object>> serchDatas = this.jdbcTemplate.queryForList(sql.toString());
            for(Map<String,Object> shiJuData : serchDatas){
                StringBuilder sql2 = new StringBuilder();
                sql2.append(" select t.* from sendflow_info t where t.object_Id = '").append(objectId).append("'");
                sql2.append(" and t.super_unit_id = '").append(shiJuData.get("UNIT_ID")).append("'");
                List<Map<String,Object>> list2 = this.jdbcTemplate.queryForList(sql2.toString());
                int len = list2.size();
                if(list2!=null && len>0){
                    for(Map<String,Object> mapData : list2){
                        String businessId = (String)mapData.get("BUSINESS_ID");
                        String superNnitId = (String)mapData.get("SUPER_UNIT_ID");
                        String serchuUnitId = (String)mapData.get("UNIT_ID");
                        if(StringUtils.isNotBlank(superNnitId)&& StringUtils.isNotBlank(serchuUnitId)&& superNnitId.equals(serchuUnitId)){
                            shiJuData.put("CRU_BUSINESS_ID", businessId);
                        }else{
                            shiJuData.put("CRU_BUSINESS_ID", "");
                        }
                    }
                }
                benJiDatas.add(shiJuData);
            }
            if(Collections.EMPTY_LIST.equals(benJiDatas)){
                return infos;
            }else{
                infos = this.getSendFlowInfoList(benJiDatas);
                return infos;
            }
        }
        return infos;
    }
*
   /* private List<SendFlowInfo> getSendFlowInfoList(List<Map<String, Object>> list) {
        List<SendFlowInfo> sendFlowInfoList = new ArrayList<SendFlowInfo>();
        if(list == null || list.size() <= 0) {
            sendFlowInfoList = null;
        } else {
            for(Map<String, Object> sendFlowInfoMap: list) {

                SendFlowInfo sendFlowInfo = new SendFlowInfo(
                        (String)sendFlowInfoMap.get("ID"),
                        (String)sendFlowInfoMap.get("TEMPLATE_ID"),
                        (String)sendFlowInfoMap.get("OBJECT_ID"),
                        (String)sendFlowInfoMap.get("BUSINESS_ID"),
                        (String)sendFlowInfoMap.get("UNIT_ID"),
                        (String)sendFlowInfoMap.get("UNIT_NAME"),
                        (String)sendFlowInfoMap.get("SUPER_UNIT_ID"),
                        (String)sendFlowInfoMap.get("STATUS"),
                        (String)sendFlowInfoMap.get("CRU_USER_ID"),
                        (String)sendFlowInfoMap.get("CRU_USER_NAME"),
                        (String)sendFlowInfoMap.get("PHONE"),
                        (String) sendFlowInfoMap.get("TIME"),
                        (String)sendFlowInfoMap.get("IS_HAVE_SUB_UNIT"),
                        (String)sendFlowInfoMap.get("ORDER_NO"),
                        (String)sendFlowInfoMap.get("SEND_WAITDO_TIME"),
                        (String)sendFlowInfoMap.get("IS_DEL"),
                        (String)sendFlowInfoMap.get("ATTR1"),
                        (String)sendFlowInfoMap.get("ATTR2"),
                        (String)sendFlowInfoMap.get("ATTR3")
                );
                sendFlowInfoList.add(sendFlowInfo);
            }
        }
        return sendFlowInfoList;
    }*/

    @Override
    public List<SendFlowInfo> getSubInfo(String objectId, String status,String deptType,String deptId) {
        List<SendFlowInfo> list = new ArrayList<SendFlowInfo>();
        if(deptType.equals(Constant.DEPTTYPE_JU)){//全局
            list =  this.sendFlowInfoDao.getByObjectIdAndStatus(objectId,status);
        }else if(deptType.equals(Constant.DEPTTYPE_ERJIJU)){//二级局
            list =  this.sendFlowInfoDao.getByObjectIdAndStatusAndSuperUnitId(objectId,status,deptId);
        }else if(deptType.equals(Constant.DEPTTYPE_CHUSHI)){//处室
            list =  this.sendFlowInfoDao.getByObjectIdAndStatusAndUnitId(objectId,status,deptId);
        }
        return list;
    }



    @Override
    public boolean updateSendFlowInfo(String status,String tempalteId,String objectId,String deptId) {
        boolean flag = false;
        try{
            this.sendFlowInfoDao.updateSendFlowInfo(status,tempalteId,objectId,deptId);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int getCountInfoSum(String templateId, String objectId) {
        return sendFlowInfoDao.countAllByTemplateIdAndObjectId(templateId,objectId);
    }

    @Override
    public int getCountInfo(String status,String templateId,String objectid) {
        return sendFlowInfoDao.countAllByStatusAndTemplateIdAndObjectId(status,templateId,objectid);
    }

    @Override
    public int getCountInfoBySuperIdSum(String templateId, String objectId, String superUnitId) {
        return sendFlowInfoDao.countAllByTemplateIdAndObjectIdAndSuperUnitId(templateId,objectId,superUnitId);
    }

    @Override
    public int getCountInfoBySuperId(String status,String templateId,String objectId,String superUnitId) {
        return sendFlowInfoDao.countAllByStatusAndTemplateIdAndObjectIdAndSuperUnitId(status,templateId,objectId,superUnitId);
    }
}
