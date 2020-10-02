package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.collect.Lists;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowObjectDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * 添加年实例
 */
@Service
public class SendFlowObjectYearServiceImpl implements SendFlowObjectYearService {
    private static Log log = LogFactory.getLog(SendFlowObjectYearServiceImpl.class);

    @Autowired
    private SendFlowObjectDao sendFlowObjectDao;
    @Autowired
    private SendFlowObjectService sendFlowObjectService;

    /**
     * 生成年实例
     * @param templateId
     * @return
     */
    @Override
    public List<SendFlowObject> addYearSendFlowObject(String templateId) {
        List<SendFlowObject> flowObjects = Lists.newArrayList();
        Calendar ca = Calendar.getInstance();
        String ruleYear = String.valueOf(ca.get(Calendar.YEAR)); // 当前年
        String nextRuleYear = String.valueOf(ca.get(Calendar.YEAR)+1); //明年

        return this.getYearSendFlowObjects(templateId,ruleYear,nextRuleYear);
    }

    private List<SendFlowObject> getYearSendFlowObjects(String templateId, String...  ruleYears) {
        List<SendFlowObject> flowObjects = Lists.newArrayList();
        if(ruleYears == null) {
            return flowObjects;
        }
        for(String ruleYear: ruleYears) {
            SendFlowObject flowObject = this.addSendFlowObject(templateId, ruleYear);
            if(flowObject != null) {
                flowObjects.add(flowObject);
            }
        }
        return flowObjects;
    }
    private SendFlowObject addSendFlowObject(String templateId, String ruleYear) {
        SendFlowObject sendFlowObject = new SendFlowObject();
        boolean haveflag = this.sendFlowObjectService.haveSendFlowObject(templateId, ruleYear);
        if(haveflag) {
            log.info(ruleYear + "规则已经生成过了");
            return null;
        }
        sendFlowObject.setTemplateId(templateId);
        sendFlowObject.setRuleType(Constant.RULE_TYPE_YEAR);
        sendFlowObject.setRuleYear(ruleYear);
        sendFlowObject.setObjectName(ruleYear);
        return this.sendFlowObjectDao.save(sendFlowObject);
    }
}
