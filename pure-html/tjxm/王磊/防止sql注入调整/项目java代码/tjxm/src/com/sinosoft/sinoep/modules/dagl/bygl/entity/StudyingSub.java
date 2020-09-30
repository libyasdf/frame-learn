package com.sinosoft.sinoep.modules.dagl.bygl.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @Author 王富康
 * @Description //TODO 编研管理分发信息实体类
 * @Date 2018/12/21 9:39
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_bygl_sub")
public class StudyingSub {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 36)
    private String id;

    /** 编研id */
    @Column(name = "pid", length = 50)
    private String pid;

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

    /** 删除标识 */
    @Column(name = "visible", length = 1)
    private String visible;//删除标识

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;//创建时间

    /** 编研的年份 */
    @Column(name = "year", length = 4)
    private String year;

    /** 编研的标题 */
    @Column(name = "title", length = 500)
    private String title;

    /** 在编研管理中序号 */
    @Column(name = "sequence", length = 30)
    private String sequence;

    /** 接收处室name */
    @Column(name = "rec_chushi_name", length = 50)
    private String recChushiName;

    /** 接收处室id */
    @Column(name = "rec_chushi_id", length = 50)
    private String recChushiId;

    /** 确认状态，0未确认，1已确认 */
    @Column(name = "status", length = 1)
    private String status;

    /** 反馈状态，0未反馈，1已反馈 */
    @Column(name = "is_back", length = 1)
    private String isBack;

    @Transient
    private String fjId ;

    @Transient
    private String filelistid ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getRecChushiName() {
        return recChushiName;
    }

    public void setRecChushiName(String recChushiName) {
        this.recChushiName = recChushiName;
    }

    public String getRecChushiId() {
        return recChushiId;
    }

    public void setRecChushiId(String recChushiId) {
        this.recChushiId = recChushiId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFjId() {
        return fjId;
    }

    public void setFjId(String fjId) {
        this.fjId = fjId;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack;
    }

    public String getFilelistid() {
        return filelistid;
    }

    public void setFilelistid(String filelistid) {
        this.filelistid = filelistid;
    }
}
