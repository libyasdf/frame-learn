package com.sinosoft.sinoep.modules.dagl.daly.borrow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "DAGL_BORROW")
public class DaglBorrow {
    private String id;
    private String creUserId;
    private String creUserName;
    private String creDeptId;
    private String creDeptName;
    private String creChushiId;
    private String creChushiName;
    private String creJuId;
    private String creJuName;
    private String visible;
    private String creTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String title;
    private String borrowUserName;
    private String borrowUserId;
    private String borrowUnitId;
    private String borrowUnitName;
    private String usePurpose;
    private String phone;
    private String remark;
    private String subflag;
    private String flowType;
    private String year;
    private String borrowStatus;
    private String approveUnitId;
    private String approveUnitName;
    private String borrowDeptId;
    private String borrowDeptName;
    private String borrowDate;
    private String shouldReturnDate ;
    private String approveUserId;
    private String approveUserName;
    private String handleUserId ;
    private String handleUserName;
    private String serialNumber;
    private String isInnewOrReborrow;
    private String cz = "";
    List<DaglFile> fileList = new ArrayList<DaglFile>();
    @Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
    @Transient
    public List<DaglFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<DaglFile> fileList) {
        this.fileList = fileList;
    }



    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "is_innew_or_reborrow")
    public String getIsInnewOrReborrow() {
        return isInnewOrReborrow;
    }

    public void setIsInnewOrReborrow(String isInnewOrReborrow) {
        this.isInnewOrReborrow = isInnewOrReborrow;
    }

    @Basic
    @Column(name = "CRE_USER_ID")
    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    @Basic
    @Column(name = "CRE_USER_NAME")
    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    @Basic
    @Column(name = "CRE_DEPT_ID")
    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    @Basic
    @Column(name = "CRE_DEPT_NAME")
    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_ID")
    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_NAME")
    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    @Basic
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    @Basic
    @Column(name = "CRE_JU_NAME")
    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    @Basic
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    @Basic
    @Column(name = "UPDATE_USER_ID")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Basic
    @Column(name = "UPDATE_USER_NAME")
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "BORROW_USER_NAME")
    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    @Basic
    @Column(name = "BORROW_USER_ID")
    public String getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(String borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    @Basic
    @Column(name = "BORROW_UNIT_ID")
    public String getBorrowUnitId() {
        return borrowUnitId;
    }

    public void setBorrowUnitId(String borrowUnitId) {
        this.borrowUnitId = borrowUnitId;
    }

    @Basic
    @Column(name = "BORROW_UNIT_NAME")
    public String getBorrowUnitName() {
        return borrowUnitName;
    }

    public void setBorrowUnitName(String borrowUnitName) {
        this.borrowUnitName = borrowUnitName;
    }

    @Basic
    @Column(name = "USE_PURPOSE")
    public String getUsePurpose() {
        return usePurpose;
    }

    public void setUsePurpose(String usePurpose) {
        this.usePurpose = usePurpose;
    }

    @Basic
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "SUBFLAG")
    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    @Basic
    @Column(name = "FLOW_TYPE")
    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    @Basic
    @Column(name = "YEAR")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    @Column(name = "BORROW_STATUS")
    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    @Basic
    @Column(name = "APPROVE_UNIT_ID")
    public String getApproveUnitId() {
        return approveUnitId;
    }

    public void setApproveUnitId(String approveUnitId) {
        this.approveUnitId = approveUnitId;
    }

    @Basic
    @Column(name = "APPROVE_UNIT_NAME")
    public String getApproveUnitName() {
        return approveUnitName;
    }

    public void setApproveUnitName(String approveUnitName) {
        this.approveUnitName = approveUnitName;
    }

    @Basic
    @Column(name = "BORROW_DEPT_ID")
    public String getBorrowDeptId() {
        return borrowDeptId;
    }

    public void setBorrowDeptId(String borrowDeptId) {
        this.borrowDeptId = borrowDeptId;
    }

    @Basic
    @Column(name = "BORROW_DEPT_NAME")
    public String getBorrowDeptName() {
        return borrowDeptName;
    }

    public void setBorrowDeptName(String borrowDeptName) {
        this.borrowDeptName = borrowDeptName;
    }

    @Basic
    @Column(name = "borrow_Date")
    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    @Basic
    @Column(name = "should_Return_Date")
    public String getShouldReturnDate() {
        return shouldReturnDate;
    }

    public void setShouldReturnDate(String shouldReturnDate) {
        this.shouldReturnDate = shouldReturnDate;
    }

    @Basic
    @Column(name = "approve_User_Id")
    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    @Basic
    @Column(name = "approve_User_Name")
    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    @Basic
    @Column(name = "handle_User_Id")
    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    @Basic
    @Column(name = "handle_User_Name")
    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    @Basic
    @Column(name = "Serial_Number")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}