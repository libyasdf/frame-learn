package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 三会一课 党课实体类
 * @Author: 李帅
 * @Date: 2018/8/28 17:21
 */
@Entity
@Table(name = "DDJS_SHYK_REMIND")
public class ShykRemindEntity {
    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;


    /** 创建人id */
    @Column(name = "CRE_USER_ID", length = 50)
    private String creUserId;

    /** 创建人姓名 */
    @Column(name = "CRE_USER_NAME", length = 50)
    private String creUserName;

    /** 创建人部门ID */
    @Column(name = "CRE_DEPT_ID", length = 50)
    private String creDeptId;

    /** 创建人部门名*/
    @Column(name = "CRE_DEPT_NAME", length = 50)
    private String creDeptName;

    /** 创建人处室ID */
    @Column(name = "CRE_CHUSHI_ID", length = 50)
    private String creChushiId;

    /** 创建人处室名*/
    @Column(name = "CRE_CHUSHI_NAME", length = 50)
    private String creChushiName;

    /** 创建人二级局ID*/
    @Column(name = "CRE_JU_ID", length = 50)
    private String creJuId;

    /** 创建人二级局名 */
    @Column(name = "CRE_JU_NAME", length = 50)
    private String creJuName;

    /** 逻辑删除*/
    @Column(name = "VISIBLE", length = 50)
    private String visible;

    /** 创建时间 */
    @Column(name = "CRE_TIME", length = 50)
    private String creTime;

    @Column(name = "BACK_CONTENT")
    private String backContent;

    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "ADVANCE_DAYS")
    private String advanceDays;

    @Column(name = "ORGAN_ID")
    private String organId;

    @Column(name = "BACK_CYCLE ")
    private String backCycle;

    @Column(name = "TITLE")
    private String title;

    public ShykRemindEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAdvanceDays() {
        return advanceDays;
    }

    public void setAdvanceDays(String advanceDays) {
        this.advanceDays = advanceDays;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getBackCycle() {
        return backCycle;
    }

    public void setBackCycle(String backCycle) {
        this.backCycle = backCycle;
    }
}
