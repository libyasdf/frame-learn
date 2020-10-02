package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.base.Strings;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.CountInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowInfo;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.WaitDoParamBean;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SendFlowServiceImpl implements SendFlowService {

    private static Log log = LogFactory.getLog(SendFlowServiceImpl.class);

    @Autowired
    private SendFlowTemplateService sendFlowTemplateService;
    @Autowired
    private SendFlowInfoService sendFlowInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String,List<SendFlowInfo>> getWaitDoList(final WaitDoParamBean param) {
        Map<String, List<SendFlowInfo>> waitInfos = new HashMap<String, List<SendFlowInfo>>();
        String templateId = param.getTemplateId();
        if(Strings.isNullOrEmpty(templateId)) {
            return waitInfos;
        }
        SendFlowTemplate template = this.sendFlowTemplateService.getSendFlowTemplateById(templateId);
        List<SendFlowInfo> subSendFlowInfos = new ArrayList<SendFlowInfo>();//已上报列表
        List<SendFlowInfo> sendFlowInfos = new ArrayList<SendFlowInfo>();//未上报列表
        List<SendFlowInfo> backFlowInfos = new ArrayList<SendFlowInfo>();//退回列表
        if(StringUtils.isEmpty(param.getDeptType())){
            param.setDeptType(Constant.DEPTTYPE_JU);
        }
        //获取已上报数据
        subSendFlowInfos = this.sendFlowInfoService.getSubInfo(param.getObjectId(),Constant.INFO_STATUS_REPORT,param.getDeptType(),param.getUnitId());
        subSendFlowInfos = this.getBusinessInfo(subSendFlowInfos,template,param.getSortName(),param.getOrderBy());
        if(subSendFlowInfos.size()>1 && StringUtils.isNotBlank(param.getSortName())) {
            subSendFlowInfos.sort(new Comparator<SendFlowInfo>() {
                @Override
                public int compare(SendFlowInfo o1, SendFlowInfo o2) {
                    String s1 = (String) o1.getBusinessEntity().get(param.getSortName());
                    String s2 = (String) o2.getBusinessEntity().get(param.getSortName());
                    if ("asc".equals(param.getOrderBy())) {
                        return s1.compareTo(s2);
                    } else {
                        return s2.compareTo(s1);
                    }
                }
            });
        }
        waitInfos.put(Constant.WAIT_DO_TYPE_REPORT,subSendFlowInfos);
        //获取未上报列表
        sendFlowInfos = this.sendFlowInfoService.getSubInfo(param.getObjectId(), Constant.INFO_STATUS_NOT_REPORT,param.getDeptType(),param.getUnitId());
        sendFlowInfos = this.getBusinessInfo(sendFlowInfos,template,param.getSortName(),param.getOrderBy());
        waitInfos.put(Constant.WAIT_DO_TYPE_NOT_REPORT, sendFlowInfos);
        //获取退回列表
        backFlowInfos =  this.sendFlowInfoService.getSubInfo(param.getObjectId(), Constant.INFO_STATUS_BACK,param.getDeptType(),param.getUnitId());
        backFlowInfos = this.getBusinessInfo(backFlowInfos,template,param.getSortName(),param.getOrderBy());
        waitInfos.put(Constant.WAIT_DO_TYPE_BACK, backFlowInfos);
        return waitInfos;
    }

    private List<SendFlowInfo> getBusinessInfo(List<SendFlowInfo> list,SendFlowTemplate template,String sortName,String orderBy){
        String businessTableName =template.getBusinessTableName();
        if(StringUtils.isNotEmpty(businessTableName)) {
            for (SendFlowInfo sendFlowInfo : list) {
                List listzwf=new ArrayList();
                StringBuilder sql = new StringBuilder();
                sql.append(" select * from ").append(businessTableName).append(" t where 1=1");
                sql.append(" and object_id =? ");
                listzwf.add(sendFlowInfo.getObjectId());
                sql.append(" and dept_id =? ");
                listzwf.add(sendFlowInfo.getUnitId());
                sql.append(" and visible =? ");
                listzwf.add(CommonConstants.VISIBLE[1]);
                if(StringUtils.isNotEmpty(sortName)){
                    sql.append(" order by ").append(sortName).append(" ");
                    if(StringUtils.isNotEmpty(orderBy)){
                        sql.append(orderBy);
                    }else{
                        sql.append("desc");
                    }
                }
                List<Map<String, Object>> businessInfo = jdbcTemplate.queryForList(sql.toString(),listzwf.toArray());
                Map<String, Object> entity = new HashMap<String, Object>();
                if (businessInfo.size() > 0) {
                    entity = businessInfo.get(0);
                }
                sendFlowInfo.setBusinessEntity(entity);
            }
        }
        return list;
    }

    @Override
    public List<CountInfo> getCountInfo(WaitDoParamBean param) {
        List<CountInfo> list = new ArrayList<CountInfo>();
        String templateId = param.getTemplateId();
        if(Strings.isNullOrEmpty(templateId)) {
            return list;
        }
        if(StringUtils.isEmpty(param.getDeptlevel())){
            param.setDeptlevel(Constant.DEPTLEVEL_ERJIJU);
        }
        CountInfo juJiEntity = new CountInfo();
        juJiEntity.setDeptid("441");
        juJiEntity.setDeptName("天津局");
        juJiEntity.setSum(sendFlowInfoService.getCountInfoSum(param.getTemplateId(),param.getObjectId()));
        juJiEntity.setSub(sendFlowInfoService.getCountInfo(Constant.INFO_STATUS_REPORT,param.getTemplateId(),param.getObjectId()));
        juJiEntity.setNoSub(sendFlowInfoService.getCountInfo(Constant.INFO_STATUS_NOT_REPORT,param.getTemplateId(),param.getObjectId()));
        juJiEntity.setBack(sendFlowInfoService.getCountInfo(Constant.INFO_STATUS_BACK,param.getTemplateId(),param.getObjectId()));
        list.add(juJiEntity);
        String getDeptSql = "select * from sys_flow_dept where status ='1' and deptlevel='"+param.getDeptlevel()+"' order by order_no ";
        JSONObject deptJson = userInfoService.getDateBySql(getDeptSql);
        if(ConfigConsts.SUCESS_STARUS.equals(deptJson.get("flag"))){
            List<Map<String,Object>> deptList = deptJson.getJSONArray("data");
            for (Map<String,Object> map :deptList){
                Object deptids = map.get("deptid");
                Object deptNames = map.get("deptname");
                String deptId = deptids.toString();
                String deptName = deptNames.toString();
                CountInfo entity = new CountInfo();
                entity.setDeptid(deptId);
                entity.setDeptName(deptName);
                entity.setSum(sendFlowInfoService.getCountInfoBySuperIdSum(param.getTemplateId(),param.getObjectId(),deptId));
                entity.setSub(sendFlowInfoService.getCountInfoBySuperId(Constant.INFO_STATUS_REPORT,param.getTemplateId(),param.getObjectId(),deptId));
                entity.setNoSub(sendFlowInfoService.getCountInfoBySuperId(Constant.INFO_STATUS_NOT_REPORT,param.getTemplateId(),param.getObjectId(),deptId));
                entity.setBack(sendFlowInfoService.getCountInfoBySuperId(Constant.INFO_STATUS_BACK,param.getTemplateId(),param.getObjectId(),deptId));
                list.add(entity);
            }
        }else {
            log.info("获取部门错误！！！");
        }
        return list;
    }
}
