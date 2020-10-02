package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.base.Strings;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowObjectDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 添加期报实例
 */
@Service
public class SendFlowObjectPhaseServiceImpl implements SendFlowObjectPhaseService {

    private static Log log = LogFactory.getLog(SendFlowObjectPhaseServiceImpl.class);

    @Autowired
    private SendFlowObjectDao sendFlowObjectDao;

    @Autowired
    private SendFlowTemplateService sendFlowTemplateService;

    public List<SendFlowObject> addPhaseSendFlowObject(String templateId) {
        List<SendFlowObject> flowObjects = new ArrayList<SendFlowObject>();
        String allPhase = this.sendFlowTemplateService.getAllPhaseByTemplateId(templateId);//获取期数
        if(Strings.isNullOrEmpty(allPhase)) {
            log.info("请您填写全年期数");
        }
        Calendar ca = Calendar.getInstance();
        Integer ruleYearNum = ca.get(Calendar.YEAR);
        SendFlowObject flowObject = this.addSendFlowObject(templateId, allPhase, ruleYearNum);//生成当前期数（也就是当前季度object实例）
        if(flowObject != null) {
            flowObjects.add(flowObject);
        }
        SendFlowObject nextFlowObject = this.addSendFlowObject(templateId, allPhase, ruleYearNum);//生成下一期数（也就是下个季度object实例）
        if(nextFlowObject != null) {
            flowObjects.add(nextFlowObject);
        }
        return flowObjects;
    }
    /**
     * 添加期报实例
     * @param templateId
     * @param allPhase
     * @param ruleYearNum
     * @return
     */
    private SendFlowObject addSendFlowObject(String templateId, String allPhase, Integer ruleYearNum) {
        SendFlowObject flowObject = null;
        String maxPhase = this.getMaxPhase(templateId, String.valueOf(ruleYearNum),allPhase);//获取当前最大期数
        int maxPhaseNum = Integer.valueOf(maxPhase);
        int allPhaseNum = Integer.valueOf(allPhase);
        if(maxPhaseNum <allPhaseNum) {
            if(!this.haveSendFlowObject(templateId,String.valueOf(ruleYearNum),String.valueOf(maxPhaseNum + 1))){//如果存在则不再次生成
                flowObject = this.addPhaseSendFlowObject(templateId, String.valueOf(ruleYearNum), allPhase, String.valueOf(maxPhaseNum + 1));
            }
        } else {
            ruleYearNum++;
            if(!this.haveSendFlowObject(templateId,String.valueOf(ruleYearNum),"1")){//如果存在则不再次生成
                flowObject = this.addPhaseSendFlowObject(templateId, String.valueOf(ruleYearNum), allPhase, "1");
            }
        }
        return flowObject;
    }

    /**
     * 根据模板、年份、期数获取对应的实体
     * @param templateId
     * @param year
     * @param phase
     * @return
     */
    public boolean haveSendFlowObject(String templateId,String year,String phase){
        SendFlowObject flowObject = sendFlowObjectDao.getByTemplateIdAndRuleYearAndPhase(templateId,year,phase);
        boolean flag = false;
        if(flowObject != null){
            flag = true;
        }
        return flag;
    }

    /**
     * 根据模板和年份获取最大期数
     * @param templateId
     * @param ruleYear
     * @return
     */
    @Override
    public String getMaxPhase(String templateId, String ruleYear,String allPhase) {
        String maxPhase = this.sendFlowObjectDao.getMaxPhase(templateId, ruleYear);
        if(Strings.isNullOrEmpty(maxPhase)) {
            String month = JDateToolkit.getNowMonth();
            int monthInt = Integer.parseInt(month);
            if("4".equals(allPhase)){
                if(0<monthInt && monthInt<=3){
                    maxPhase = "0";
                }else if(3<monthInt && monthInt<=6){
                    maxPhase = "1";
                }else if (6<monthInt && monthInt<=9){
                    maxPhase = "2";
                }else if(9<monthInt && monthInt<=12){
                    maxPhase = "3";
                }
            }else if("2".equals(allPhase)){
                if(0<monthInt && monthInt<=6){
                    maxPhase ="0";
                }else if(6<monthInt && monthInt<=12){
                    maxPhase ="1";
                }
            }else{
                maxPhase ="0";
            }
        }
        return maxPhase;
    }

    /**
     * 生成SendFlowObject实例（半年报和季度报）
     * @param templateId
     * @param ruleYear
     * @param allPhase
     * @param phase
     * @return
     */
    private SendFlowObject addPhaseSendFlowObject(String templateId, String ruleYear, String allPhase, String phase) {
        SendFlowObject sendFlowObject = new SendFlowObject();
        sendFlowObject.setTemplateId(templateId);
        sendFlowObject.setRuleType(Constant.RULE_TYPE_PHASE);//获取报送规则类型
        sendFlowObject.setRuleYear(ruleYear);
        sendFlowObject.setObjectName(this.getPhaseObjectName(ruleYear, allPhase, phase));
        sendFlowObject.setPhase(phase);
        return this.sendFlowObjectDao.save(sendFlowObject);
    }

    /**
     * 生成期报实例name(半年报，季度报和期报)
     * @param ruleYear
     * @param allPhase
     * @param phase
     * @return
     */
    private String getPhaseObjectName(String ruleYear, String allPhase, String phase) {
        String objectName = ruleYear;

        if(!Strings.isNullOrEmpty(allPhase)) {
            int allPhaseNum = Integer.valueOf(allPhase);
            int phaseNum = Integer.valueOf(phase);
            if(allPhaseNum == 2) {
                if(phaseNum == 1) {
                    objectName += "上半年";
                } else if(phaseNum == 2) {
                    objectName += "下半年";
                }
            } else if(allPhaseNum==4) {//add by wangxidong  科技服务改成季度报
                if(phaseNum == 1) {
                    objectName += "第一季度";
                } else if(phaseNum == 2) {
                    objectName += "第二季度";
                } else if(phaseNum == 3) {
                    objectName += "第三季度";
                } else if(phaseNum == 4) {
                    objectName += "第四季度";
                }
            } else if(allPhaseNum==3 || allPhaseNum>4){
                objectName += "第" + phaseNum + "期";
            }
        } else {
            log.info("请您填写全年期数");
        }
        return objectName;
    }

}
