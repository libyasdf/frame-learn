package com.sinosoft.sinoep.modules.system.notice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 通知通告审批实体类
 * @author 杜建松
 * @Date 2019年1月8日 下午4:12:52
 */
@Entity
@Table(name="SYS_NOTICE_VERIFY")
public class SysNoticeVerify {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    /** 创建人处室ID */
    @Column(name="CRE_CHUSHI_ID")
    private String creChushiId;

    /** 创建人处室name */
    @Column(name="CRE_CHUSHI_NAME")
    private String creChushiName;

    /** 创建人部门ID */
    @Column(name="CRE_DEPT_ID")
    private String creDeptId;

    /** 创建人部门name */
    @Column(name="CRE_DEPT_NAME")
    private String creDeptName;

    /** 创建人局ID */
    @Column(name="CRE_JU_ID")
    private String creJuId;

    /** 创建人局name */
    @Column(name="CRE_JU_NAME")
    private String creJuName;

    /** 创建时间 */
    @Column(name="CRE_TIME")
    private String creTime;

    /** 创建人ID */
    @Column(name="CRE_USER_ID")
    private String creUserId;

    /** 创建人name */
    @Column(name="CRE_USER_NAME")
    private String creUserName;

    @Column(name = "VISIBLE")
    private String visible;

    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    //指定部门管理员id
    @Column(name = "NOTICE_GL_USER_IDS")
    private String noticeGlUserIds;

    //指定部门管理姓名
    @Column(name = "NOTICE_GL_USER_NAMES")
    private String noticeGlUserNames;

    //是否审批
    @Column(name = "IS_SP")
    private String isSP;

    //局级或部门id
    @Column(name = "NOTICE_DEPTID")
    private String noticeDeptId;

    //指定部门管理员 部门id
    @Column(name = "DEPT_GL_USERDEPTID")
    private String deptGLUserDeptId;

    @Transient
    private List<SysNoticeVerifyUser> noticeUserList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
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

    public String getNoticeGlUserIds() {
        return noticeGlUserIds;
    }

    public void setNoticeGlUserIds(String noticeGlUserIds) {
        this.noticeGlUserIds = noticeGlUserIds;
    }

    public String getNoticeGlUserNames() {
        return noticeGlUserNames;
    }

    public void setNoticeGlUserNames(String noticeGlUserNames) {
        this.noticeGlUserNames = noticeGlUserNames;
    }

    public String getIsSP() {
        return isSP;
    }

    public void setIsSP(String isSP) {
        this.isSP = isSP;
    }

    public List<SysNoticeVerifyUser> getNoticeUserList() {
        return noticeUserList;
    }

    public void setNoticeUserList(List<SysNoticeVerifyUser> noticeUserList) {
        this.noticeUserList = noticeUserList;
    }

    public String getNoticeDeptId() {
        return noticeDeptId;
    }

    public void setNoticeDeptId(String noticeDeptId) {
        this.noticeDeptId = noticeDeptId;
    }

    public String getDeptGLUserDeptId() {
        return deptGLUserDeptId;
    }

    public void setDeptGLUserDeptId(String deptGLUserDeptId) {
        this.deptGLUserDeptId = deptGLUserDeptId;
    }
}
