package com.sinosoft.sinoep.waitNoflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 非流程待办表
 * @author 李利广
 * @Date 2018年7月24日 上午11:16:15
 */
@Entity
@Table(name = "SYS_WAITDO_NOFLOW")
public class SysWaitNoflow {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    /**
     * 起草人ID
     */
    @Column(name = "draft_user_id")
    private String  draftUserId;
    
    /**
     * 起草人name
     */
    @Column(name = "draft_user_name")
    private String draftUserName;
    
    /**
     * 起草人部门id
     */
    @Column(name = "draft_dept_id")
    private String draftDeptId;
    
    /**
     * 起草人部门name
     */
    @Column(name = "draft_dept_name")
    private String draftDeptName;
    
    /**
     * 起草人处室id
     */
    @Column(name = "draft_chushi_id")
    private String draftChushiId;
    
    /**
     * 起草人处室name
     */
    @Column(name = "draft_chushi_name")
    private String draftChushiName;
    
    /**
     * 起草人二级局id
     */
    @Column(name = "draft_ju_id")
    private String draftJuId;
    
    /**
     * 起草人二级局name
     */
    @Column(name = "draft_ju_name")
    private String draftJuName;
    
    /**
     * 逻辑删除(0删除；1可见)
     */
    @Column(name = "visible")
    private String visible;
    
    /**
     * 起草时间（yyyy-MM-dd HH:mm:ss）
     */
    @Column(name = "draft_Time")
    private String draftTime;
    
    /**
     * 接收人id
     */
    @Column(name = "receive_user_id")
    private String receiveUserId;
    
    /**
     * 接收人name
     */
    @Column(name = "receive_user_name")
    private String receiveUserName;
    
    /**
     * 接收部门id
     */
    @Column(name = "receive_dept_id")
    private String receiveDeptId;
    
    /**
     * 接收部门name
     */
    @Column(name = "receive_dept_name")
    private String receiveDeptName;
    
    /**
     * 业务角色编号
     */
    @Column(name = "roles_no")
    private String rolesNo;
    
    /**
     * 业务表名称
     */
    @Column(name = "table_name")
    private String tableName;
    
    /**
     * 业务表主键
     */
    @Column(name = "table_id")
    private String tableId;
    
    /**
     * 业务id
     */
    @Column(name = "source_id")
    private String sourceId;
    
    /**
     * 操作名称
     */
    @Column(name = "op_name")
    private String opName;
    
    /**
     * 待办url
     */
    @Column(name = "daiban_url")
    private String daibanUrl;
    
    /**
     * 待办数量
     */
    @Column(name = "daiban_num")
    private String daibanNum;
    
    /**
     * 待办标题
     */
    @Column(name = "title")
    private String title;
    
    /**
     * 待办类型（0：只有部门，1：有人员和部门，2：有部门和业务角色）
     */
    @Column(name = "type")
    private String type;

    /**
     * 备用字段1
     */
    @Column(name = "attr1")
    private String attr1;
    
    /**
     * 备用字段2
     */
    @Column(name = "attr2")
    private String attr2;
    
    /**
     * 备用字段3
     */
    @Column(name = "attr3")
    private String attr3;
    
    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private String updateTime;

    public SysWaitNoflow() {
        super();
    }

    public SysWaitNoflow(String draftUserId, String draftUserName, String draftDeptId, String draftDeptName, String draftChushiId, String draftChushiName, String draftJuId, String draftJuName, String visible, String draftTime, String receiveUserId, String receiveUserName, String receiveDeptId, String receiveDeptName, String rolesNo, String tableName, String tableId, String sourceId, String opName, String daibanUrl, String daibanNum, String title, String type, String attr1, String attr2, String attr3, String updateTime) {
        this.draftUserId = draftUserId;
        this.draftUserName = draftUserName;
        this.draftDeptId = draftDeptId;
        this.draftDeptName = draftDeptName;
        this.draftChushiId = draftChushiId;
        this.draftChushiName = draftChushiName;
        this.draftJuId = draftJuId;
        this.draftJuName = draftJuName;
        this.visible = visible;
        this.draftTime = draftTime;
        this.receiveUserId = receiveUserId;
        this.receiveUserName = receiveUserName;
        this.receiveDeptId = receiveDeptId;
        this.receiveDeptName = receiveDeptName;
        this.rolesNo = rolesNo;
        this.tableName = tableName;
        this.tableId = tableId;
        this.sourceId = sourceId;
        this.opName = opName;
        this.daibanUrl = daibanUrl;
        this.daibanNum = daibanNum;
        this.title = title;
        this.type = type;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(String draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getDraftUserName() {
        return draftUserName;
    }

    public void setDraftUserName(String draftUserName) {
        this.draftUserName = draftUserName;
    }

    public String getDraftDeptId() {
        return draftDeptId;
    }

    public void setDraftDeptId(String draftDeptId) {
        this.draftDeptId = draftDeptId;
    }

    public String getDraftDeptName() {
        return draftDeptName;
    }

    public void setDraftDeptName(String draftDeptName) {
        this.draftDeptName = draftDeptName;
    }

    public String getDraftChushiId() {
        return draftChushiId;
    }

    public void setDraftChushiId(String draftChushiId) {
        this.draftChushiId = draftChushiId;
    }

    public String getDraftChushiName() {
        return draftChushiName;
    }

    public void setDraftChushiName(String draftChushiName) {
        this.draftChushiName = draftChushiName;
    }

    public String getDraftJuId() {
        return draftJuId;
    }

    public void setDraftJuId(String draftJuId) {
        this.draftJuId = draftJuId;
    }

    public String getDraftJuName() {
        return draftJuName;
    }

    public void setDraftJuName(String draftJuName) {
        this.draftJuName = draftJuName;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getDraftTime() {
        return draftTime;
    }

    public void setDraftTime(String draftTime) {
        this.draftTime = draftTime;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveDeptId() {
        return receiveDeptId;
    }

    public void setReceiveDeptId(String receiveDeptId) {
        this.receiveDeptId = receiveDeptId;
    }

    public String getReceiveDeptName() {
        return receiveDeptName;
    }

    public void setReceiveDeptName(String receiveDeptName) {
        this.receiveDeptName = receiveDeptName;
    }

    public String getRolesNo() {
        return rolesNo;
    }

    public void setRolesNo(String rolesNo) {
        this.rolesNo = rolesNo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getDaibanUrl() {
        return daibanUrl;
    }

    public void setDaibanUrl(String daibanUrl) {
        this.daibanUrl = daibanUrl;
    }

    public String getDaibanNum() {
        return daibanNum;
    }

    public void setDaibanNum(String daibanNum) {
        this.daibanNum = daibanNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
