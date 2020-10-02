package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.base.Strings;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowObjectDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SendFlowObjectXunServiceImpl implements SendFlowObjectXunService {

    @Autowired
    private SendFlowObjectDao sendFlowObjectDao;

    @Autowired
    private SendFlowTemplateService sendFlowTemplateService;

    private static Log log = LogFactory.getLog(SendFlowObjectXunServiceImpl.class);

    @Override
    public List<SendFlowObject> addXunSendFlowObject(String templateId) {
        List<SendFlowObject> flowObjects = new ArrayList<SendFlowObject>();
        String allPhase = this.sendFlowTemplateService.getAllPhaseByTemplateId(templateId);
        if(Strings.isNullOrEmpty(allPhase)) {
            log.info("请您填写每月期数");
        }
        Calendar ca = Calendar.getInstance();
        Integer ruleYear = ca.get(Calendar.YEAR);
        Integer ruleMonth = ca.get(Calendar.MONTH) + 1;
        SendFlowObject flowObject = this.addSendFlowObject(templateId, allPhase, ruleYear, ruleMonth);
        if(flowObject != null) {
            flowObjects.add(flowObject);
        }
        SendFlowObject nextFlowObject = this.addSendFlowObject(templateId, allPhase, ruleYear, ruleMonth);
        if(nextFlowObject != null) {
            flowObjects.add(nextFlowObject);
        }
        return flowObjects;
    }

    public String getRuleMonth(int ruleMonthInt) {
        String ruleMonth = "" + ruleMonthInt;
        if(ruleMonthInt < 10) {
            ruleMonth = "0" + ruleMonthInt;
        }
        return ruleMonth;
    }

    private SendFlowObject addSendFlowObject(String templateId, String allPhase, Integer ruleYear, Integer ruleMonth) {
        SendFlowObject flowObject = null;
        String maxPhase = this.getMaxPhase(templateId, String.valueOf(ruleYear),this.getRuleMonth(ruleMonth));
        int maxPhaseNum = Integer.valueOf(maxPhase);
        int allPhaseNum = Integer.valueOf(allPhase);
        if(maxPhaseNum < allPhaseNum) {
            flowObject = this.addSendFlowObject(templateId, ruleYear, ruleMonth, allPhase, String.valueOf(maxPhaseNum + 1));
        } else {
            ruleMonth++;
            if(13 == ruleMonth){
                ruleYear = ruleYear + 1;
                ruleMonth = 1;
            }
            flowObject = this.addSendFlowObject(templateId, allPhase, ruleYear, ruleMonth);
        }
        return flowObject;
    }

    @Override
    public String getMaxPhase(String templateId, String ruleYear, String ruleMonth) {
        String maxPhase = this.sendFlowObjectDao.getMaxPhase(templateId, ruleYear, ruleMonth);
        if(Strings.isNullOrEmpty(maxPhase)) {
            maxPhase = "0";
        }

        return maxPhase;
    }

    private SendFlowObject addSendFlowObject(String templateId, Integer ruleYear, Integer ruleMonth, String allPhase, String phase) {
        String ruleYearStr = String.valueOf(ruleYear);
        String ruleMontehStr = this.getRuleMonth(ruleMonth);
        SendFlowObject sendFlowObject = new SendFlowObject();
        sendFlowObject.setTemplateId(templateId);
        sendFlowObject.setRuleType(Constant.RULE_TYPE_XUN);
        sendFlowObject.setRuleYear(ruleYearStr);
        sendFlowObject.setRuleMonth(ruleMontehStr);
        sendFlowObject.setObjectName(this.getObjectName(ruleYearStr, ruleMontehStr, allPhase, phase));
        sendFlowObject.setPhase(phase);
        return this.sendFlowObjectDao.save(sendFlowObject);
    }

    private String getObjectName(String ruleYear, String ruleMonth, String allPhase, String phase) {
        String objectName = ruleYear + "-" +ruleMonth;
        if(!Strings.isNullOrEmpty(allPhase)) {
            int allPhaseNum = Integer.valueOf(allPhase);
            int phaseNum = Integer.valueOf(phase);
            if(allPhaseNum == 3) {
                if(phaseNum == 1) {
                    objectName += "上旬";
                } else if(phaseNum == 2) {
                    objectName += "中旬";
                } else if(phaseNum == 3) {
                    objectName += "下旬";
                }
            } else {
                objectName += "第" + phaseNum + "期";
            }
        } else {
           log.info("请您填写每月期数");
        }
        return objectName;
    }
}
