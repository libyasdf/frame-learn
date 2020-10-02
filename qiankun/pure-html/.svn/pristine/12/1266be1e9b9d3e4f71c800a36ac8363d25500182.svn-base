package com.sinosoft.sinoep.sendFLowWorkflow.services;

import com.google.common.base.Strings;
import com.sinosoft.sinoep.sendFLowWorkflow.common.Constant;
import com.sinosoft.sinoep.sendFLowWorkflow.dao.SendFlowObjectDao;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject;
import com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SendFlowObjectServiceImpl implements SendFlowObjectService {

    private static Log log = LogFactory.getLog(SendFlowObjectServiceImpl.class);
    @Autowired
    private SendFlowTemplateService sendFlowTemplateService;
    @Autowired
    private SendFlowObjectDao sendFlowObjectDao;
    @Autowired
    private SendFlowObjectYearService yearObjectService;//年报
    @Autowired
    private SendFlowObjectMonthService sendFlowObjectMonthService;//月报
    @Autowired
    private SendFlowObjectPhaseService phaseObjectService;//期报
    @Autowired
    private SendFlowObjectXunService sendFlowObjectXunService;//旬报
    @Autowired
    private SendFlowInfoService sendFlowInfoService;

    /**
     * 根据templateId生成实例
     * @param templateId
     * @return
     */
    @Override
    public List<SendFlowObject> addSendFlowObject(String templateId) {
        List<SendFlowObject> flowObjects = new ArrayList<SendFlowObject>();
        String RuleTypeTable = this.sendFlowTemplateService.getRuleTypeByTemplateId(templateId);//获取规则类型
        if(Constant.RULE_TYPE_YEAR.equals(RuleTypeTable)){//年报
            flowObjects = this.yearObjectService.addYearSendFlowObject(templateId);
        }else if(Constant.RULE_TYPE_MONTH.equals(RuleTypeTable)){//月报
            flowObjects = this.sendFlowObjectMonthService.addMonthSendFlowObject(templateId);
        }else if(Constant.RULE_TYPE_PHASE.equals(RuleTypeTable)){//期数（半年报，季度报）
            flowObjects = this.phaseObjectService.addPhaseSendFlowObject(templateId);
        }else if(Constant.RULE_TYPE_XUN.equals(RuleTypeTable)){//旬报
            flowObjects = this.sendFlowObjectXunService.addXunSendFlowObject(templateId);
        }
        return flowObjects;
    }

    /**
     * 判断年实例
     * @param templateId
     * @param ruleYear
     * @return
     */
    @Override
    public boolean haveSendFlowObject(String templateId, String ruleYear) {
        boolean resultFlag = false;
        long l = this.sendFlowObjectDao.getNumByTemplateIdAndRuleYear(templateId, ruleYear);
        if(l > 0) {
            resultFlag = true;
        }
        return resultFlag;
    }

    /**
     * 判断月实例
     * @param templateId
     * @param ruleYear
     * @param ruleMonth
     * @return
     */
    @Override
    public boolean haveSendFlowObject(String templateId, String ruleYear, String ruleMonth) {
        boolean resultFlag = false;
        long l = this.sendFlowObjectDao.getNumByTemplateIdAndRuleYearAndRuleMonth(templateId, ruleYear, ruleMonth);
        if(l > 0) {
            resultFlag = true;
        }
        return resultFlag;
    }

    @Override
    public String getObjectIdCruYearOrMonthByTemplateId(String templateId,String ruleType) {
        String objectId = "";
        if(Constant.RULE_TYPE_YEAR.equals(ruleType)) {
            objectId = this.getCruYearByTemplateId(templateId).getId();
        } else if(Constant.RULE_TYPE_MONTH.equals(ruleType)) {
            objectId = this.getCruMonthByTemplateId(templateId).getId();
        } else if (Constant.RULE_TYPE_PHASE.equals(ruleType)){
            objectId = this.getCruPhaseByTemplateId(templateId).getId();
        }
        return objectId;
    }

    @Override
    public String getObjectId(String templateId) {
        String objectId = "";
        if(StringUtils.isNotEmpty(templateId)){
            objectId = this.getObjectIdByTemplateId(templateId);//先查询是否存在今年的实例
            if(StringUtils.isEmpty(objectId)){//如果不存在则生成
                sendFlowInfoService.generateFlowInfo(templateId);
                objectId = this.getObjectIdByTemplateId(templateId);//生成之后在查询今年的实例
            }
        }
        return objectId;
    }

    private String getObjectIdByTemplateId(String templateId){
        String objectId = "";
        SendFlowTemplate template = this.sendFlowTemplateService.getSendFlowTemplateById(templateId);
        String ruleType = template.getRuleTypeTable().getRuleType();
        if (Constant.RULE_TYPE_YEAR.equals(ruleType)) {
            objectId = this.getCruYearByTemplateId(templateId).getId();
        } else if (Constant.RULE_TYPE_MONTH.equals(ruleType)) {
            objectId = this.getNowCruMonthByTemplateId(templateId).getId();
        } else if (Constant.RULE_TYPE_PHASE.equals(ruleType)) {
            objectId = this.getNowCruPhaseByTemplateId(templateId).getId();
        }
        return objectId;
    }

    public SendFlowObject getCruYearByTemplateId(String templateId) {
        Calendar ca = Calendar.getInstance();
        int cruYear = ca.get(Calendar.YEAR);
        SendFlowObject sendFlowObject = this.sendFlowObjectDao.getByTemplateIdAndRuleYear(templateId,
                String.valueOf(cruYear));
        if(sendFlowObject == null) {
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }

    @Override
    public SendFlowObject getCruMonthByTemplateId(String templateId) {
        Calendar ca = Calendar.getInstance();
        int cruYear = ca.get(Calendar.YEAR);
        int cruMonth = ca.get(Calendar.MONTH);
        if(cruMonth==0){
            cruYear = cruYear-1;
            cruMonth = 12;
        }
        return this.getByTemplateIdAndYearAndMonth(templateId, String.valueOf(cruYear),
                this.getRuleMonth(String.valueOf(cruMonth)));
    }

    public SendFlowObject getNowCruMonthByTemplateId(String templateId) {
        Calendar ca = Calendar.getInstance();
        int cruYear = ca.get(Calendar.YEAR);
        int cruMonth = ca.get(Calendar.MONTH)+1;
        return this.getByTemplateIdAndYearAndMonth(templateId, String.valueOf(cruYear),
                this.getRuleMonth(String.valueOf(cruMonth)));
    }

    //根据templateid来获取半年报的默认时间
    public SendFlowObject getCruPhaseByTemplateId(String templateId) {
        Calendar ca = Calendar.getInstance();
        int cruYear = ca.get(Calendar.YEAR);
        int cruMonth = ca.get(Calendar.MONTH);
        String phase="";
        String maxPash = this.sendFlowTemplateService.getAllPhaseByTemplateId(templateId);
        if ("2".equals(maxPash)) {
            if (cruMonth <= 6) {
                cruYear = cruYear - 1;
                phase = "2";
            } else {
                phase = "1";
            }
        }
        if ("4".equals(maxPash)) {
            if (cruMonth >= 0 && cruMonth <= 3) {
                cruYear = cruYear - 1;
                phase = "4";
            }
            if (cruMonth >= 3 && cruMonth <= 6) {
                phase = "1";
            }
            if (cruMonth >= 6 && cruMonth <= 9) {
                phase = "2";
            }
            if (cruMonth >= 9 && cruMonth <= 12) {
                phase = "3";
            }
        }
        SendFlowObject sendFlowObject =this.sendFlowObjectDao.getByTemplateIdAndRuleYearAndPhase(templateId, String.valueOf(cruYear), phase);
        if(sendFlowObject==null){
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }

    public SendFlowObject getNowCruPhaseByTemplateId(String templateId) {
        Calendar ca = Calendar.getInstance();
        int cruYear = ca.get(Calendar.YEAR);
        int cruMonth = ca.get(Calendar.MONTH)+1;
        String phase="";
        String maxPash = this.sendFlowTemplateService.getAllPhaseByTemplateId(templateId);
        if ("2".equals(maxPash)) {
            if (cruMonth <= 6) {
                cruYear = cruYear;
                phase = "1";
            } else {
                phase = "2";
            }
        }
        if ("4".equals(maxPash)) {
            if (cruMonth >= 0 && cruMonth <= 3) {
                cruYear = cruYear;
                phase = "1";
            }
            if (cruMonth >= 3 && cruMonth <= 6) {
                phase = "2";
            }
            if (cruMonth >= 6 && cruMonth <= 9) {
                phase = "3";
            }
            if (cruMonth >= 9 && cruMonth <= 12) {
                phase = "4";
            }
        }
        SendFlowObject sendFlowObject =this.sendFlowObjectDao.getByTemplateIdAndRuleYearAndPhase(templateId, String.valueOf(cruYear), phase);
        if(sendFlowObject==null){
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }
    @Override
    public SendFlowObject getByTemplateIdAndYearAndMonth(String templateId, String year, String month) {
        SendFlowObject sendFlowObject = this.sendFlowObjectDao.getByTemplateIdAndRuleYearAndRuleMonth(templateId,
                year, getRuleMonth(month));
        if(sendFlowObject == null) {
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }

    @Override
    public SendFlowObject getByTemplateIdAndYearAndPhase(String templateId, String year, String phase) {
        SendFlowObject sendFlowObject = this.sendFlowObjectDao.getByTemplateIdAndRuleYearAndPhase(templateId,
                year, phase);
        if(sendFlowObject == null) {
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }

    @Override
    public SendFlowObject getByTemplateIdAndYear(String templateId, String year) {
        SendFlowObject sendFlowObject = this.sendFlowObjectDao.getByTemplateIdAndRuleYear(templateId,
                year);
        if(sendFlowObject == null) {
            sendFlowObject = new SendFlowObject();
        }
        return sendFlowObject;
    }

    public String getRuleMonth(String ruleMonth) {
        String result = "";
        if(Strings.isNullOrEmpty(ruleMonth)) {
            return result;
        }
        ruleMonth.length();
        if(Integer.valueOf(ruleMonth) < 10) {
            result = "0" + Integer.valueOf(ruleMonth);
        }else{
            result = ruleMonth;
        }
        return result;
    }

    @Override
    public List<SendFlowObject> objectNameList(String templateId) {
        StringBuilder querySql = new StringBuilder();
        List listzwf=new ArrayList();
        querySql.append("from SendFlowObject t where 1=1");
        if(templateId != null && !templateId.equals("")){
            querySql.append(" and t.templateId =? ");
            listzwf.add(templateId.trim());
        }
        String ruleType = sendFlowTemplateService.getRuleTypeByTemplateId(templateId);
        if(Constant.RULE_TYPE_PHASE.equals(ruleType)){
            querySql.append(" order by t.ruleYear,t.phase desc) ");
        }else{
            querySql.append(" order by t.ruleYear desc) ");
        }
        Query query = sendFlowObjectDao.getEntityManager().createQuery(querySql.toString());
        for(int i=0;i<listzwf.size();i++){
            query.setParameter((i+1),listzwf.get(i));//为问号赋占位符
        }
        List<SendFlowObject> list = new ArrayList<SendFlowObject>();
        list = (List<SendFlowObject>)query.getResultList();
        return list;
    }
}
