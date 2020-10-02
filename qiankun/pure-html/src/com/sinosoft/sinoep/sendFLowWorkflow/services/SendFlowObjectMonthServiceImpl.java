package com.sinosoft.sinoep.sendFLowWorkflow.services;

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

/**
 * 添加月实例
 */
@Service
public class SendFlowObjectMonthServiceImpl implements SendFlowObjectMonthService {

    @Autowired
    private SendFlowObjectService sendFlowObjectService;
    @Autowired
    private SendFlowObjectDao sendFlowObjectDao;

    private static Log log = LogFactory.getLog(SendFlowObjectMonthServiceImpl.class);

    @Override
    public List<SendFlowObject> addMonthSendFlowObject(String templateId) {
        List<SendFlowObject> flowObject = new ArrayList<SendFlowObject>();
        SendFlowObject cruSendFlowObject = this.addCruSendFlowObject(templateId);
        if(cruSendFlowObject != null) {
            flowObject.add(cruSendFlowObject);
        }
        SendFlowObject nextSendFlowObject = this.addNextSendFlowObject(templateId);
        if(nextSendFlowObject != null) {
            flowObject.add(nextSendFlowObject);
        }
        return flowObject;
    }

    private SendFlowObject addCruSendFlowObject(String templateId) {
        Calendar ca = Calendar.getInstance();
        String ruleYear = String.valueOf(ca.get(Calendar.YEAR));
        int ruleMonthInt = ca.get(Calendar.MONTH) + 1;
        String ruleMonth = this.getRuleMonth(ruleMonthInt);
        return this.addSendFlowObject(templateId, ruleYear, ruleMonth);
    }

    private SendFlowObject addNextSendFlowObject(String templateId) {
        Calendar ca = Calendar.getInstance();
        String ruleYear = "";
        String ruleMonth = "";
        int cruYear = ca.get(Calendar.YEAR);
        int cruMonth = ca.get(Calendar.MONTH) + 2;
        if(12 == cruMonth) {
            ruleYear = String.valueOf(cruYear + 1);
            ruleMonth = "01";
        } else {
            ruleYear = String.valueOf(cruYear);
            ruleMonth = getRuleMonth(cruMonth);
        }
        return this.addSendFlowObject(templateId, ruleYear, ruleMonth);
    }

    public String getRuleMonth(int ruleMonthInt) {
        String ruleMonth = "" + ruleMonthInt;
        if(ruleMonthInt < 10) {
            ruleMonth = "0" + ruleMonthInt;
        }
        return ruleMonth;
    }

    private SendFlowObject addSendFlowObject(String templateId,  String ruleYear, String ruleMonth) {
        SendFlowObject sendFlowObject = new SendFlowObject();
        boolean haveflag = this.sendFlowObjectService.haveSendFlowObject(templateId, ruleYear, ruleMonth);
        if(haveflag) {
            this.log.info("规则已经生成过了");
            return null;
        } else {
            sendFlowObject.setTemplateId(templateId);
            sendFlowObject.setRuleType(Constant.RULE_TYPE_MONTH);
            sendFlowObject.setRuleYear(ruleYear);
            sendFlowObject.setRuleMonth(ruleMonth);
            sendFlowObject.setObjectName(ruleYear + "-" +ruleMonth);
            this.log.info("生成月份实例============"+ruleYear+"-"+ruleMonth);
        }
        return this.sendFlowObjectDao.save(sendFlowObject);
    }

}
