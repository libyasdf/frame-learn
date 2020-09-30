package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 报送实例表
 */
@Entity
@Table(name="sendflow_object")
public class SendFlowObject {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    //实例name
    @Column(name = "object_name")
    private String objectName;
    //模板id
    @Column(name = "template_id")
    private String templateId;
    //规则类型（00：年，01：月，02：时间段）
    @Column(name = "rule_type")
    private String ruleType;
    //规则年
    @Column(name = "rule_year")
    private String ruleYear;
    //规则月
    @Column(name = "rule_month")
    private String ruleMonth;
    //规则开始时间
    @Column(name = "rule_start_time")
    private String ruleStartTime;
    //规则结束时间
    @Column(name = "rule_end_time")
    private String ruleEndTime;
    //上半年，下半年，第几季度标识
    @Column(name = "phase")
    private String phase;
    //备用字段1
    @Column(name = "attr1")
    private String attr1;
    //备用字段2
    @Column(name = "attr2")
    private String attr2;
    //备用字段3
    @Column(name = "attr3")
    private String attr3;

    public SendFlowObject() {
    }

    public SendFlowObject(String objectName, String templateId, String ruleType, String ruleYear, String ruleMonth, String ruleStartTime, String ruleEndTime, String phase, String attr1, String attr2, String attr3) {
        this.objectName = objectName;
        this.templateId = templateId;
        this.ruleType = ruleType;
        this.ruleYear = ruleYear;
        this.ruleMonth = ruleMonth;
        this.ruleStartTime = ruleStartTime;
        this.ruleEndTime = ruleEndTime;
        this.phase = phase;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public String getRuleYear() {
        return ruleYear;
    }

    public void setRuleYear(String ruleYear) {
        this.ruleYear = ruleYear;
    }

    public String getRuleMonth() {
        return ruleMonth;
    }

    public void setRuleMonth(String ruleMonth) {
        this.ruleMonth = ruleMonth;
    }

    public String getRuleStartTime() {
        return ruleStartTime;
    }

    public void setRuleStartTime(String ruleStartTime) {
        this.ruleStartTime = ruleStartTime;
    }

    public String getRuleEndTime() {
        return ruleEndTime;
    }

    public void setRuleEndTime(String ruleEndTime) {
        this.ruleEndTime = ruleEndTime;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }
}
