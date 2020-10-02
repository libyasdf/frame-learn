package com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * @Author 王富康
 * @Description //TODO 基础信息配置实体类
 * @Date 2018/7/13 15:44
 * @Param
 * @return
 **/
@Entity
@Table(name = "zbgl_duty_config")
public class Config {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;//值班表id

    /** 创建人id */
    @Column(name = "cre_user_id", length = 50)
    private String creUserId;//创建人id

    /** 创建人姓名 */
    @Column(name = "cre_user_name", length = 50)
    private String creUserName;//创建人姓名

    /** 创建人部门id */
    @Column(name = "cre_dept_id", length = 50)
    private String creDeptId;//创建人部门id

    /** 创建人部门名 */
    @Column(name = "cre_dept_name", length = 50)
    private String creDeptName;//创建人部门名

    /** 创建人处室id */
    @Column(name = "cre_chushi_id", length = 50)
    private String creChushiId;//创建人处室id

    /** 创建人处室名 */
    @Column(name = "cre_chushi_name", length = 50)
    private String creChushiName;//创建人处室名

    /** 创建人局id */
    @Column(name = "cre_ju_id", length = 50)
    private String creJuId;//创建人局id

    /** 创建人局名 */
    @Column(name = "cre_ju_name", length = 50)
    private String creJuName;//创建人局名

    /** 逻辑删除标识 */
    @Column(name = "visible", length = 1)
    private String visible;//逻辑删除标识

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;//创建时间

    /** 上报截止时间 */
    @Column(name = "report_over_date", length = 30)
    private String reportOverDate;

    /** 催办即使内容 */
    @Column(name = "prompt_message_content", length = 500)
    private String promptMessageContent;

    /** 单位id */
    @Column(name = "unit_id", length = 30)
    private String unitId;

    /** 单位名称 */
    @Column(name = "unit_name", length = 30)
    private String unitName;

    /** 最后更新人id */
    @Column(name = "update_user_id", length = 30)
    private String updateUserId;

    /** 最后更新人姓名 */
    @Column(name = "update_user_name", length = 30)
    private String updateUserName;

    /** 最后更新时间 */
    @Column(name = "update_time", length = 30)
    private String updateTime;

    /** 值班月份 */
    @Column(name = "duty_month", length = 30)
    private String dutyMonth;

    /** 值班月份 */
    @Transient
    private String bl;

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

    public String getReportOverDate() {
        return reportOverDate;
    }

    public void setReportOverDate(String reportOverTime) {
        this.reportOverDate = reportOverTime;
    }

    public String getPromptMessageContent() {
        return promptMessageContent;
    }

    public void setPromptMessageContent(String promptMessageContent) {
        this.promptMessageContent = promptMessageContent;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDutyMonth() {
        return dutyMonth;
    }

    public void setDutyMonth(String dutyMonth) {
        this.dutyMonth = dutyMonth;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public Config() {
        super();
    }

    public Config(String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String reportOverDate, String promptMessageContent, String unitId, String unitName, String updateUserId, String updateUserName, String updateTime, String dutyMonth) {
        super();
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.visible = visible;
        this.creTime = creTime;
        this.reportOverDate = reportOverDate;
        this.promptMessageContent = promptMessageContent;
        this.unitId = unitId;
        this.unitName = unitName;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.dutyMonth = dutyMonth;
    }
}
