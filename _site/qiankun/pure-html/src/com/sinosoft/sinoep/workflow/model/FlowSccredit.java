package com.sinosoft.sinoep.workflow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import net.sf.json.JSONObject;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：待办授权实体类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Entity
@Table(name = "FLOW_SCCREDIT")
public class FlowSccredit implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String sccreditid = ""; // 主键ID
    private String fsccreditid = ""; //
    private String topfsccreditid = "";//
    private String filetype = "";// 授权事项id
    private String filetypename = "";// 授权事项id
    private String userid = "";// 授权人id
    private String username = "";// 授权人name
    private String deptid = "";// 授权人部门id
    private String deptname = "";// 授权人部门name
    private String sccredituserid = "";// 被授权人id
    private String sccreditusername = "";// 被授权人name
    private String sccreditdeptid = "";// 被授权人部门id
    private String sccreditdeptname = "";// 被授权人部门name
    private String sccredittype = "";// 授权类型
    private String issccredit = "";// 是否允许再授权1允许0不允许
    private String sccreditdate = "";// 待办授权信息生成时间
    private String sccbegindate = "";// 授权生效时间
    private String sccenddate = "";// 授权失效时间
    private String z_who = "";//
    private String z_status = "";//
    private String z_when = "";//
    private String isread = "";//
    private String cre_user = "";// 当前登录人id
    private String cre_username = "";// 当前登录人name
    private String sysId = ""; // 系统ID
    private String orgId = ""; // 组织体系ID
    
    private String cz = ""; // 操作

    @Transient
    public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "uuid")
    @Column(name = "SCCREDITID", length = 100)
    public String getSccreditid() {
        return sccreditid;
    }

    public void setSccreditid(String sccreditid) {
        this.sccreditid = sccreditid;
    }

    @Column(name = "FSCCREDITID", length = 50)
    public String getFsccreditid() {
        return fsccreditid;
    }

    public void setFsccreditid(String fsccreditid) {
        this.fsccreditid = fsccreditid;
    }

    @Column(name = "TOPFSCCREDITID", length = 50)
    public String getTopfsccreditid() {
        return topfsccreditid;
    }

    public void setTopfsccreditid(String topfsccreditid) {
        this.topfsccreditid = topfsccreditid;
    }

    @Column(name = "FILETYPE", length = 50)
    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "DEPTID", length = 50)
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    @Column(name = "SCCREDITUSERID", length = 50)
    public String getSccredituserid() {
        return sccredituserid;
    }

    public void setSccredituserid(String sccredituserid) {
        this.sccredituserid = sccredituserid;
    }

    @Column(name = "SCCREDITDEPTID", length = 50)
    public String getSccreditdeptid() {
        return sccreditdeptid;
    }

    public void setSccreditdeptid(String sccreditdeptid) {
        this.sccreditdeptid = sccreditdeptid;
    }

    @Column(name = "SCCREDITTYPE", length = 50)
    public String getSccredittype() {
        return sccredittype;
    }

    public void setSccredittype(String sccredittype) {
        this.sccredittype = sccredittype;
    }

    @Column(name = "ISSCCREDIT", length = 50)
    public String getIssccredit() {
        return issccredit;
    }

    public void setIssccredit(String issccredit) {
        this.issccredit = issccredit;
    }

    @Column(name = "SCCREDITDATE", length = 50)
    public String getSccreditdate() {
        return sccreditdate;
    }

    public void setSccreditdate(String sccreditdate) {
        this.sccreditdate = sccreditdate;
    }

    @Column(name = "SCCBEGINDATE", length = 50)
    public String getSccbegindate() {
        return sccbegindate;
    }

    public void setSccbegindate(String sccbegindate) {
        this.sccbegindate = sccbegindate;
    }

    @Column(name = "SCCENDDATE", length = 50)
    public String getSccenddate() {
        return sccenddate;
    }

    public void setSccenddate(String sccenddate) {
        this.sccenddate = sccenddate;
    }

    @Column(name = "Z_WHO", length = 50)
    public String getZ_who() {
        return z_who;
    }

    public void setZ_who(String z_who) {
        this.z_who = z_who;
    }

    @Column(name = "Z_STATUS", length = 50)
    public String getZ_status() {
        return z_status;
    }

    public void setZ_status(String z_status) {
        this.z_status = z_status;
    }

    @Column(name = "Z_WHEN", length = 50)
    public String getZ_when() {
        return z_when;
    }

    public void setZ_when(String z_when) {
        this.z_when = z_when;
    }

    @Column(name = "ISREAD", length = 50)
    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    @Column(name = "CRE_USER", length = 50)
    public String getCre_user() {
        return cre_user;
    }

    public void setCre_user(String cre_user) {
        this.cre_user = cre_user;
    }

    @Column(name = "SYSID", length = 50)
    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    @Column(name = "ORGID", length = 50)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "FILETYPENAME", length = 200)
    public String getFiletypename() {
        return filetypename;
    }

    public void setFiletypename(String filetypename) {
        this.filetypename = filetypename;
    }

    @Column(name = "DEPTNAME", length = 200)
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Column(name = "USERNAME", length = 200)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "SCCREDITUSERNAME", length = 200)
    public String getSccreditusername() {
        return sccreditusername;
    }

    public void setSccreditusername(String sccreditusername) {
        this.sccreditusername = sccreditusername;
    }

    @Column(name = "SCCREDITDEPTNAME", length = 200)
    public String getSccreditdeptname() {
        return sccreditdeptname;
    }

    public void setSccreditdeptname(String sccreditdeptname) {
        this.sccreditdeptname = sccreditdeptname;
    }

    @Column(name = "CRE_USERNAME", length = 200)
    public String getCre_username() {
        return cre_username;
    }

    public void setCre_username(String cre_username) {
        this.cre_username = cre_username;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

}