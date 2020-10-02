package com.sinosoft.sinoep.modules.system.config.msgVersion.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * TODO 消息客户端版本管理实体类
 * @author 李利广
 * @Date 2019年03月08日 19:45:44
 */
@Entity
@Table(name = "message_version")
public class MsgVersion {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;

    /** 版本号 */
    @Column(name = "version")
    private String version;

    /** 版本更新内容 */
    @Column(name = "content")
    private String content;

    /** 版本号第一位 */
    @Column(name = "first")
    private String first;

    /** 版本号第二位 */
    @Column(name = "second")
    private String second;

    /** 版本号第三位 */
    @Column(name = "three")
    private String three;

    /** 更新时间 */
    @Column(name = "cre_time")
    private String creTime;

    /** 创建人ID */
    @Column(name = "cre_user_id")
    private String creUserId;

    /**  */
    @Column(name = "cre_user_name")
    private String creUserName;

    /**  */
    @Column(name = "cre_dept_id")
    private String creDeptId;

    /**  */
    @Column(name = "cre_dept_name")
    private String creDeptName;

    /**  */
    @Column(name = "cre_ju_id")
    private String creJuId;

    /**  */
    @Column(name = "cre_ju_name")
    private String creJuName;

    /** 逻辑删除 */
    @Column(name = "status")
    private String status;

    /** 附件ID */
    @Transient
    private String affixId;

    /** 附件大小 */
    @Transient
    private String affixSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAffixId() {
        return affixId;
    }

    public void setAffixId(String affixId) {
        this.affixId = affixId;
    }

    public String getAffixSize() {
        return affixSize;
    }

    public void setAffixSize(String affixSize) {
        this.affixSize = affixSize;
    }
}
