package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;

import java.util.List;

public interface SendFlowObjectService {

    /**
     *  根据templateId生成实例
     * @param TemplateId
     * @return
     */
    public List<SendFlowObject> addSendFlowObject(String TemplateId);

    /**
     * 判断年是否存在实例
     * @param templateId
     * @param ruleYear
     * @return
     */
    public boolean haveSendFlowObject(String templateId, String ruleYear);

    /**
     * 判断月是否存在实例
     * @param templateId
     * @param ruleYear
     * @param ruleMonth
     * @return
     */
    public boolean haveSendFlowObject(String templateId, String ruleYear, String ruleMonth);

    /**
     * 获取上个月的实例
     * @param templateId
     * @param ruleType
     * @return
     */
    public String getObjectIdCruYearOrMonthByTemplateId(String templateId,String ruleType);

    public String getObjectId(String templateId);

    public SendFlowObject getCruMonthByTemplateId(String templateId);
    /**
     * 获取月实例
     * @param templateId
     * @param year
     * @param month
     * @return
     */
    public SendFlowObject getByTemplateIdAndYearAndMonth(String templateId, String year, String month);

    /**
     * 获取期数实例
     * @param templateId
     * @param year
     * @param phase
     * @return
     */
    public SendFlowObject getByTemplateIdAndYearAndPhase(String templateId, String year, String phase);

    /**
     * 根据templateId查询实例
     * @param templateId
     * @return
     */
    public List<SendFlowObject> objectNameList(String templateId);
    /**
     * 获取年实例
     * @param templateId
     * @param year
     * @return
     */
    public SendFlowObject getByTemplateIdAndYear(String templateId, String year);
}
