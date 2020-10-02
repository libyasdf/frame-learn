package com.sinosoft.sinoep.modules.system.notice.entity;

import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 通知通告发布人、审核人实体类
 * @author 杜建松
 * @Date 2019年1月8日 下午4:12:52
 */
@Entity
@Table(name="SYS_NOTICE_VERIFY_USER")
public class SysNoticeVerifyUser {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /** 审批表ID */
    @Column(name="VERIFYID")
    private String verifyId;

    /** 创建人ID */
    @Column(name="CRE_USER_ID")
    private String creUserId;

    /** 创建人name */
    @Column(name="CRE_USER_NAME")
    private String creUserName;

    //逻辑删除
    @Column(name = "VISIBLE")
    private String visible;

    /** 创建时间 */
    @Column(name="CRE_TIME")
    private String creTime;

    //通知通告发布人id
    @Column(name="FB_USER_ID")
    private String fbUserId;

    //通知通告发布人name
    @Column(name="FB_USER_NAME")
    private String fbUserName;

    //通知通告发布人部门id
    @Column(name="FB_USER_DEPTID")
    private String fbUserDeptId;

    //通知通告审核人id
    @Column(name="SH_USER_ID")
    private String shUserId;

    //通知通告审核人name
    @Column(name="SH_USER_NAME")
    private String shUserName;

    //通知通告审核人部门id
    @Column(name="SH_USER_DEPTID")
    private String shUserDeptId;

    //通知通告发布人业务角色编号
    @Column(name="FB_USER_ROLE")
    private String fbUserRole;

    //通知通告审核人业务角色编号
    @Column(name="SH_USER_ROLE")
    private String shUserRole;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
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

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public String getFbUserName() {
        return fbUserName;
    }

    public void setFbUserName(String fbUserName) {
        this.fbUserName = fbUserName;
    }

    public String getShUserId() {
        return shUserId;
    }

    public void setShUserId(String shUserId) {
        this.shUserId = shUserId;
    }

    public String getShUserName() {
        return shUserName;
    }

    public void setShUserName(String shUserName) {
        this.shUserName = shUserName;
    }

    public String getFbUserDeptId() {
        return fbUserDeptId;
    }

    public void setFbUserDeptId(String fbUserDeptId) {
        this.fbUserDeptId = fbUserDeptId;
    }

    public String getShUserDeptId() {
        return shUserDeptId;
    }

    public void setShUserDeptId(String shUserDeptId) {
        this.shUserDeptId = shUserDeptId;
    }

    public String getFbUserRole() {
        return fbUserRole;
    }

    public void setFbUserRole(String fbUserRole) {
        this.fbUserRole = fbUserRole;
    }

    public String getShUserRole() {
        return shUserRole;
    }

    public void setShUserRole(String shUserRole) {
        this.shUserRole = shUserRole;
    }
}
