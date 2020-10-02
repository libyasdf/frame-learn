package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Map;

/**
 * 报送实例表
 */
@Entity
@Table(name = "sendflow_info")
public class SendFlowInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    //模板id
    @Column(name = "template_id")
    private String templateId;
    //实例id
    @Column(name = "object_id")
    private String objectId;
    //业务id
    @Column(name = "business_id")
    private String businessId;
    //单位id
    @Column(name = "unit_id")
    private String unitId;
    //单位name
    @Column(name = "unit_name")
    private String unitName;
    //上级单位id
    @Column(name = "super_unit_id")
    private String superUnitId;
    //报送状态（00：未报：01：已上报，02：退回）
    @Column(name = "status")
    private String status;
    //报送人ID
    @Column(name = "cru_user_id")
    private String cruUserId;
    //报送人NAME
    @Column(name = "cru_user_name")
    private String cruUserName;
    //联系电话
    @Column(name = "phone")
    private String phone;
    //报送时间
    @Column(name = "time")
    private String time;
    //是否拥有下级单位
    @Column(name = "is_have_sub_unit")
    private String isHaveSubUnit;
    //排序
    @Column(name = "order_no")
    private String orderNo;
    //最后一次催报的时间
    @Column(name = "send_waitdo_time")
    private String sendWaitdoTime;
    //是否删除
    @Column(name = "is_del")
    private String isDel;
    //备用字段1
    @Column(name = "attr1")
    private String attr1;
    //备用字段2
    @Column(name = "attr2")
    private String attr2;
    //备用字段3
    @Column(name = "attr3")
    private String attr3;

    @Transient
    private Map<String,Object> businessEntity;


    public SendFlowInfo() {
    }

    public SendFlowInfo(String templateId, String objectId, String businessId, String unitId, String unitName, String superUnitId, String status, String cruUserId, String cruUserName, String phone, String time, String isHaveSubUnit, String orderNo, String sendWaitdoTime, String isDel, String attr1, String attr2, String attr3, Map<String,Object> businessEntity) {
        this.templateId = templateId;
        this.objectId = objectId;
        this.businessId = businessId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.superUnitId = superUnitId;
        this.status = status;
        this.cruUserId = cruUserId;
        this.cruUserName = cruUserName;
        this.phone = phone;
        this.time = time;
        this.isHaveSubUnit = isHaveSubUnit;
        this.orderNo = orderNo;
        this.sendWaitdoTime = sendWaitdoTime;
        this.isDel = isDel;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.businessEntity = businessEntity;
    }

    public Map<String,Object> getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(Map<String,Object> businessEntity) {
        this.businessEntity = businessEntity;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCruUserId() {
        return cruUserId;
    }

    public void setCruUserId(String cruUserId) {
        this.cruUserId = cruUserId;
    }

    public String getCruUserName() {
        return cruUserName;
    }

    public void setCruUserName(String cruUserName) {
        this.cruUserName = cruUserName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsHaveSubUnit() {
        return isHaveSubUnit;
    }

    public void setIsHaveSubUnit(String isHaveSubUnit) {
        this.isHaveSubUnit = isHaveSubUnit;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSendWaitdoTime() {
        return sendWaitdoTime;
    }

    public void setSendWaitdoTime(String sendWaitdoTime) {
        this.sendWaitdoTime = sendWaitdoTime;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
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
