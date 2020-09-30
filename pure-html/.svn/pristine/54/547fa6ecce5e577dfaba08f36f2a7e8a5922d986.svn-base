package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rule_type_table")
public class RuleTypeTable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;

    //规则NAME
    @Column(name = "rule_name")
    private String ruleName;
    //规则类型（00：年，01：月，02：时间段，03:一年分期）
    @Column(name = "rule_type")
    private String ruleType;
    //如果规则为一年分期的话，该字段记录了总共分了几期
    @Column(name = "all_phase")
    private String allPhase;
    //备注
    @Column(name = "memo")
    private String memo;
    //备用字段1
    @Column(name = "attr1")
    private String attr1;
    //备用字段2
    @Column(name = "attr2")
    private String attr2;
    //备用字段3
    @Column(name = "attr3")
    private String attr3;

    public RuleTypeTable() {
    }

    public RuleTypeTable(String ruleName, String ruleType, String allPhase, String memo, String attr1, String attr2, String attr3) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.allPhase = allPhase;
        this.memo = memo;
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

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getAllPhase() {
        return allPhase;
    }

    public void setAllPhase(String allPhase) {
        this.allPhase = allPhase;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
