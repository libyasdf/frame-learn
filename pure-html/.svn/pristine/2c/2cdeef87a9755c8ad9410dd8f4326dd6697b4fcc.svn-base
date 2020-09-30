package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "wait_unit")
public class WaitUnit {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;
    //单位id
    @Column(name = "unit_id")
    private String unitId;
    //单位name
    @Column(name = "unit_name")
    private String unitName;
    //上级单位id
    @Column(name = "super_unit_id")
    private String superUnitId;
    //模板id
    @Column(name = "tempalte_id")
    private String templateId;
    //排序
    @Column(name = "order_no")
    private String orderNo;
    //备注
    @Column(name = "memo")
    private String memo;
    //是否拥有下级单位
    @Column(name = "is_have_sub_unit")
    private String isHaveSubUnit;
    //备用字段1
    @Column(name = "attr1")
    private String attr1;
    //备用字段2
    @Column(name = "attr2")
    private String attr2;
    //备用字段3
    @Column(name = "attr3")
    private String attr3;

    public WaitUnit() {
    }

    public WaitUnit(String unitId, String unitName, String superUnitId, String templateId, String orderNo, String memo, String isHaveSubUnit, String attr1, String attr2, String attr3) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.superUnitId = superUnitId;
        this.templateId = templateId;
        this.orderNo = orderNo;
        this.memo = memo;
        this.isHaveSubUnit = isHaveSubUnit;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSuperUnitId() {
        return superUnitId;
    }

    public void setSuperUnitId(String superUnitId) {
        this.superUnitId = superUnitId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIsHaveSubUnit() {
        return isHaveSubUnit;
    }

    public void setIsHaveSubUnit(String isHaveSubUnit) {
        this.isHaveSubUnit = isHaveSubUnit;
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
