package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
 * @Author 王富康
 * @Description //TODO 门类实体类
 * @Date 2018/11/13 14:14
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_category_table")
public class DaglCategoryTable {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 36)
    private String id;

    /** 门类名 */
    @Column(name = "category_name", length = 50)
    private String categoryName;

    /** 门类代号 */
    @Column(name = "category_code", length = 50)
    private String categoryCode;

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

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;//创建时间

    /** 	最近更新id*/
    @Column(name = "update_user_id", length = 50)
    private String updateUserId;

    /** 	最近更新人名*/
    @Column(name = "update_user_name", length = 50)
    private String updateUserName;

    /** 最近更新时间*/
    @Column(name = "update_time", length = 50)
    private String updateTime;

    /** 逻辑删除标识 */
    @Column(name = "visible", length = 1)
    private String visible;//逻辑删除标识

    /** 全宗代号 */
    @Column(name = "general_archive_code", length = 50)
    private String generalArchiveCode;//全宗代号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
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

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getGeneralArchiveCode() {
        return generalArchiveCode;
    }

    public void setGeneralArchiveCode(String generalArchiveCode) {
        this.generalArchiveCode = generalArchiveCode;
    }

    public DaglCategoryTable() {
        super();
    }

    public DaglCategoryTable(String categoryName, String categoryCode, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String creTime, String updateUserId, String updateUserName, String updateTime, String visible, String generalArchiveCode) {
        super();
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.creTime = creTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.visible = visible;
        this.generalArchiveCode = generalArchiveCode;
    }
}
