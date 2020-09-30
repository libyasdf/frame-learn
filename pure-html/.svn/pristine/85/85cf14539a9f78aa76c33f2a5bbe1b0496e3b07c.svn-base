package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.base.Strings;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowTemplateDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendFlowTemplateServiceImpl implements SendFlowTemplateService {

    private static Log log = LogFactory.getLog(SendFlowTemplateServiceImpl.class);

    @Autowired
    private SendFlowTemplateDao templateDao;

    @Override
    public String getRuleTypeByTemplateId(String templateId) {
        String ruleType = this.templateDao.getRuleTypeById(templateId);
        if(Strings.isNullOrEmpty(ruleType)){
            log.info("请你联系管理员，没有配置流程类型");
        }
        return ruleType;
    }

    @Override
    public String getAllPhaseByTemplateId(String templateId) {
        String allPhase = this.templateDao.getAllPhaseById(templateId);
        return Strings.isNullOrEmpty(allPhase)?"0":allPhase;
    }
    @Override
    public SendFlowTemplate getSendFlowTemplateById(String id) {
        SendFlowTemplate template = templateDao.findOne(id);
        if(template == null) {
            log.info("获取规则信息的时候，发现模版信息错误");
        }
        return template;
    }
}
