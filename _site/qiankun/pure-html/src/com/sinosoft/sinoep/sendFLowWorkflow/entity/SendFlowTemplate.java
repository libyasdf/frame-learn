package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 报送模板表
 */
@Entity
@Table(name = "sendflow_template")
public class SendFlowTemplate {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id ;
    //模板name
    @Column(name = "template_name")
    private String templateName;
    //规则类型id
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "RULE_TYPE_ID")
    private RuleTypeTable ruleTypeTable;
    //业务实体表name
    @Column(name = "business_table_name")
    private String businessTableName;
    //业务实体表主键id
    @Column(name = "business_table_id")
    private String businessTableId;
    //业务实体文件状态
    @Column(name = "business_table_file_status")
    private String businessTableFileStatus;
    //业务实体上报状态
    @Column(name = "business_table_sb_status")
    private String businessTableSbStatus;
    //汇总url
    @Column(name = "sum_url")
    private String sumUrl;
    //业务url
    @Column(name = "business_url")
    private String businessUrl;
    //是否支持多次上报
    @Column(name = "is_report_many")
    private String isReportMany;
    //填报单位级别
    @Column(name = "tb_dept_jb")
    private String tbDeptJb;
    //填报单位ids
    @Column(name = "tb_dept_ids")
    private String tbDeptIds;
    //备用字段1
    @Column(name = "attr1")
    private String attr1;
    //备用字段2
    @Column(name = "attr2")
    private String attr2;
    //备用字段3
    @Column(name = "attr3")
    private String attr3;


    public SendFlowTemplate() {
    }

    public SendFlowTemplate(String templateName, RuleTypeTable ruleTypeTable, String businessTableName, String businessTableId, String businessTableFileStatus, String businessTableSbStatus, String sumUrl, String businessUrl, String isReportMany, String tbDeptJb, String tbDeptIds, String attr1, String attr2, String attr3) {
        this.templateName = templateName;
        this.ruleTypeTable = ruleTypeTable;
        this.businessTableName = businessTableName;
        this.businessTableId = businessTableId;
        this.businessTableFileStatus = businessTableFileStatus;
        this.businessTableSbStatus = businessTableSbStatus;
        this.sumUrl = sumUrl;
        this.businessUrl = businessUrl;
        this.isReportMany = isReportMany;
        this.tbDeptJb = tbDeptJb;
        this.tbDeptIds = tbDeptIds;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public RuleTypeTable getRuleTypeTable() {
        return ruleTypeTable;
    }

    public void setRuleTypeTable(RuleTypeTable ruleTypeTable) {
        this.ruleTypeTable = ruleTypeTable;
    }

    public String getBusinessTableName() {
        return businessTableName;
    }

    public void setBusinessTableName(String businessTableName) {
        this.businessTableName = businessTableName;
    }

    public String getBusinessTableId() {
        return businessTableId;
    }

    public void setBusinessTableId(String businessTableId) {
        this.businessTableId = businessTableId;
    }

    public String getBusinessTableFileStatus() {
        return businessTableFileStatus;
    }

    public void setBusinessTableFileStatus(String businessTableFileStatus) {
        this.businessTableFileStatus = businessTableFileStatus;
    }

    public String getBusinessTableSbStatus() {
        return businessTableSbStatus;
    }

    public void setBusinessTableSbStatus(String businessTableSbStatus) {
        this.businessTableSbStatus = businessTableSbStatus;
    }

    public String getSumUrl() {
        return sumUrl;
    }

    public void setSumUrl(String sumUrl) {
        this.sumUrl = sumUrl;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public String getIsReportMany() {
        return isReportMany;
    }

    public void setIsReportMany(String isReportMany) {
        this.isReportMany = isReportMany;
    }

    public String getTbDeptJb() {
        return tbDeptJb;
    }

    public void setTbDeptJb(String tbDeptJb) {
        this.tbDeptJb = tbDeptJb;
    }

    public String getTbDeptIds() {
        return tbDeptIds;
    }

    public void setTbDeptIds(String tbDeptIds) {
        this.tbDeptIds = tbDeptIds;
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
