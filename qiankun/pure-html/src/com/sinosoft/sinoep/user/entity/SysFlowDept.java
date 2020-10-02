package com.sinosoft.sinoep.user.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SYS_FLOW_DEPT_UIAS", schema = "DEMO")
public class SysFlowDept {
    private Long deptid;
    private String treeId;
    private Long peakDeptid;
    private Long deptLevel;
    private String deptnumber;
    private String deptname;
    private String abbreviation;
    private String dptmMail;
    private Long superId;
    private Long orderNo;
    private String dptmManager;
    private String fromUnit;
    private String dptmAdmin;
    private String note;
    private String auther;
    private String status;
    private String time;
    private String diqvId;
    private String diqvName;
    private String id;
    private String unitType;
    private String isZhc;
    //用户集合
    private Set<SysFlowUser> sysFlowUsers = new HashSet<>();
    //子部门
    private List<SysFlowDept> childSysFlowDepts = new ArrayList<>();

    @Id
    @Column(name = "DEPTID", nullable = false)
    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    @Basic
    @Column(name = "DEPTLEVEL", nullable = true, length = 22)
    public Long getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Long deptLevel) {
        this.deptLevel = deptLevel;
    }

    @Basic
    @Column(name = "TREE_ID", nullable = true, length = 50)
    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    @Basic
    @Column(name = "PEAK_DEPTID", nullable = true, precision = 0)
    public Long getPeakDeptid() {
        return peakDeptid;
    }

    public void setPeakDeptid(Long peakDeptid) {
        this.peakDeptid = peakDeptid;
    }

    @Basic
    @Column(name = "DEPTNUMBER", nullable = true, length = 50)
    public String getDeptnumber() {
        return deptnumber;
    }

    public void setDeptnumber(String deptnumber) {
        this.deptnumber = deptnumber;
    }

    @Basic
    @Column(name = "DEPTNAME", nullable = true, length = 100)
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Basic
    @Column(name = "ABBREVIATION", nullable = true, length = 50)
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Basic
    @Column(name = "DPTM_MAIL", nullable = true, length = 100)
    public String getDptmMail() {
        return dptmMail;
    }

    public void setDptmMail(String dptmMail) {
        this.dptmMail = dptmMail;
    }

    @Basic
    @Column(name = "SUPER_ID", nullable = true, precision = 0)
    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    @Basic
    @Column(name = "ORDER_NO", nullable = true, precision = 0)
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "DPTM_MANAGER", nullable = true, length = 400)
    public String getDptmManager() {
        return dptmManager;
    }

    public void setDptmManager(String dptmManager) {
        this.dptmManager = dptmManager;
    }

    @Basic
    @Column(name = "FROM_UNIT", nullable = true, length = 1)
    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    @Basic
    @Column(name = "DPTM_ADMIN", nullable = true, length = 400)
    public String getDptmAdmin() {
        return dptmAdmin;
    }

    public void setDptmAdmin(String dptmAdmin) {
        this.dptmAdmin = dptmAdmin;
    }

    @Basic
    @Column(name = "NOTE", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "AUTHER", nullable = true, length = 30)
    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "TIME", nullable = true, length = 30)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "DIQV_ID", nullable = true, length = 50)
    public String getDiqvId() {
        return diqvId;
    }

    public void setDiqvId(String diqvId) {
        this.diqvId = diqvId;
    }

    @Basic
    @Column(name = "DIQV_NAME", nullable = true, length = 100)
    public String getDiqvName() {
        return diqvName;
    }

    public void setDiqvName(String diqvName) {
        this.diqvName = diqvName;
    }

    @Basic
    @Column(name = "ID", nullable = true, length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UNIT_TYPE", nullable = true, length = 2)
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Basic
    @Column(name = "IS_ZHC", nullable = true, length = 2)
    public String getIsZhc() {
        return isZhc;
    }

    public void setIsZhc(String isZhc) {
        this.isZhc = isZhc;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_dprb_uias",
            joinColumns = {@JoinColumn(name = "deptid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")})
    public Set<SysFlowUser> getSysFlowUsers() {
        return sysFlowUsers;
    }

    public void setSysFlowUsers(Set<SysFlowUser> sysFlowUsers) {
        this.sysFlowUsers = sysFlowUsers;
    }

    @Transient
    public List<SysFlowDept> getChildSysFlowDepts() {
        return childSysFlowDepts;
    }

    public void setChildSysFlowDepts(List<SysFlowDept> childSysFlowDepts) {
        this.childSysFlowDepts = childSysFlowDepts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysFlowDept that = (SysFlowDept) o;

        if (deptid != that.deptid) return false;
        if (treeId != null ? !treeId.equals(that.treeId) : that.treeId != null) return false;
        if (peakDeptid != null ? !peakDeptid.equals(that.peakDeptid) : that.peakDeptid != null) return false;
        if (deptnumber != null ? !deptnumber.equals(that.deptnumber) : that.deptnumber != null) return false;
        if (deptname != null ? !deptname.equals(that.deptname) : that.deptname != null) return false;
        if (abbreviation != null ? !abbreviation.equals(that.abbreviation) : that.abbreviation != null) return false;
        if (dptmMail != null ? !dptmMail.equals(that.dptmMail) : that.dptmMail != null) return false;
        if (superId != null ? !superId.equals(that.superId) : that.superId != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (dptmManager != null ? !dptmManager.equals(that.dptmManager) : that.dptmManager != null) return false;
        if (fromUnit != null ? !fromUnit.equals(that.fromUnit) : that.fromUnit != null) return false;
        if (dptmAdmin != null ? !dptmAdmin.equals(that.dptmAdmin) : that.dptmAdmin != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (auther != null ? !auther.equals(that.auther) : that.auther != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (diqvId != null ? !diqvId.equals(that.diqvId) : that.diqvId != null) return false;
        if (diqvName != null ? !diqvName.equals(that.diqvName) : that.diqvName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (unitType != null ? !unitType.equals(that.unitType) : that.unitType != null) return false;
        if (isZhc != null ? !isZhc.equals(that.isZhc) : that.isZhc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (deptid ^ (deptid >>> 32));
        result = 31 * result + (treeId != null ? treeId.hashCode() : 0);
        result = 31 * result + (peakDeptid != null ? peakDeptid.hashCode() : 0);
        result = 31 * result + (deptnumber != null ? deptnumber.hashCode() : 0);
        result = 31 * result + (deptname != null ? deptname.hashCode() : 0);
        result = 31 * result + (abbreviation != null ? abbreviation.hashCode() : 0);
        result = 31 * result + (dptmMail != null ? dptmMail.hashCode() : 0);
        result = 31 * result + (superId != null ? superId.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (dptmManager != null ? dptmManager.hashCode() : 0);
        result = 31 * result + (fromUnit != null ? fromUnit.hashCode() : 0);
        result = 31 * result + (dptmAdmin != null ? dptmAdmin.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (auther != null ? auther.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (diqvId != null ? diqvId.hashCode() : 0);
        result = 31 * result + (diqvName != null ? diqvName.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (unitType != null ? unitType.hashCode() : 0);
        result = 31 * result + (isZhc != null ? isZhc.hashCode() : 0);
        return result;
    }
}
