package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sendflow_time_base")
public class SendFlowTimeBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;
    //模板id
    @Column(name = "template_id")
    private String templateId;
    //报送类型（00:年报，01:月报）
    @Column(name = "rule_type")
    private String ruleType;
    //提醒功能是否开启(1:开，0:关)
    @Column(name = "is_remind")
    private String isRemind;
    //提前催报时间（天）
    @Column(name = "remind_tqcb")
    private String remindTqcb;
    //提醒类型
    @Column(name = "remind_type")
    private String remindType;
    //规则表主键id
    @Column(name = "rule_id")
    private String ruleId;

    public SendFlowTimeBase() {
    }

    public SendFlowTimeBase(String templateId, String ruleType, String isRemind, String remindTqcb, String remindType, String ruleId) {
        this.templateId = templateId;
        this.ruleType = ruleType;
        this.isRemind = isRemind;
        this.remindTqcb = remindTqcb;
        this.remindType = remindType;
        this.ruleId = ruleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    public String getRemindTqcb() {
        return remindTqcb;
    }

    public void setRemindTqcb(String remindTqcb) {
        this.remindTqcb = remindTqcb;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
}
