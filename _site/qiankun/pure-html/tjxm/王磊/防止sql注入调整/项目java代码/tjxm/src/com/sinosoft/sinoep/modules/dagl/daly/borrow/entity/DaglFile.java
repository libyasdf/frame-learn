package com.sinosoft.sinoep.modules.dagl.daly.borrow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DAGL_FILE")
public class DaglFile {
    private String id;
    private String borrowId;
    private String oldId;
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
    private String useWay;
    private String borrowStatus;
    private String borrowDate;
    private String returnDate;
    private String shouldReturnDate;
    private String borrowUserId;
    private String borrowUserName;
    private String approveUserId;
    private String approveUserName;
    private String handleUserId;
    private String handleUserName;
    private String returnHandleUserId;
    private String returnHandleUserName;
    private String reason;
    private String useResult;
    private String returnTime;
    private String inRenew;
    private String borrowDeptId;
    private String borrowDeptName;
    private String borrowUnitId;
    private String borrowUnitName;
    private String recid;
    private String mainTitle;
    private String archiveNo;
    private String basefolderUnit;
    private String ljdwMark;
    private String categoryNo;
    private String categoryName;
    private String tableName;
    private String usePurpose;
    private String phone;
    private String approveUnitId;
    private String approveUnitName;
    private String orderNum;
    private String isReminder;
    //添加当前数据对应的案卷数、分卷数、卷内数 王磊 2019-03-05
    private String anJuan;
    private String fenJuan;
    private String juanNei;
    //添加当前数据对应的底层数 王富康 2019-03-26
    private String borrowNum;
    private String cz = "";
    private String isBorrow = "";

    @Transient
    public String getIsBorrow() {
        return isBorrow;
    }

    public void setIsBorrow(String isBorrow) {
        this.isBorrow = isBorrow;
    }

    @Basic
    @Column(name = "anjuan")
    public String getAnJuan() {
		return anJuan;
	}

	public void setAnJuan(String anJuan) {
		this.anJuan = anJuan;
	}

	@Basic
    @Column(name = "fenjuan")
	public String getFenJuan() {
		return fenJuan;
	}

	public void setFenJuan(String fenJuan) {
		this.fenJuan = fenJuan;
	}

	@Basic
    @Column(name = "juannei")
	public String getJuanNei() {
		return juanNei;
	}

	public void setJuanNei(String juanNei) {
		this.juanNei = juanNei;
	}

	@Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
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
    @Column(name = "is_Reminder")
    public String getIsReminder() {
        return isReminder;
    }

    public void setIsReminder(String isReminder) {
        this.isReminder = isReminder;
    }

    @Basic
    @Column(name = "BORROW_ID")
    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    @Basic
    @Column(name = "OLD_ID")
    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
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
    @Column(name = "USE_WAY")
    public String getUseWay() {
        return useWay;
    }

    public void setUseWay(String useWay) {
        this.useWay = useWay;
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
    @Column(name = "BORROW_DATE")
    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    @Basic
    @Column(name = "RETURN_DATE")
    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Basic
    @Column(name = "SHOULD_RETURN_DATE")
    public String getShouldReturnDate() {
        return shouldReturnDate;
    }

    public void setShouldReturnDate(String shouldReturnDate) {
        this.shouldReturnDate = shouldReturnDate;
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
    @Column(name = "BORROW_USER_NAME")
    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    @Basic
    @Column(name = "APPROVE_USER_ID")
    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    @Basic
    @Column(name = "APPROVE_USER_NAME")
    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    @Basic
    @Column(name = "HANDLE_USER_ID")
    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    @Basic
    @Column(name = "HANDLE_USER_NAME")
    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    @Basic
    @Column(name = "RETURN_HANDLE_USER_ID")
    public String getReturnHandleUserId() {
        return returnHandleUserId;
    }

    public void setReturnHandleUserId(String returnHandleUserId) {
        this.returnHandleUserId = returnHandleUserId;
    }

    @Basic
    @Column(name = "RETURN_HANDLE_USER_NAME")
    public String getReturnHandleUserName() {
        return returnHandleUserName;
    }

    public void setReturnHandleUserName(String returnHandleUserName) {
        this.returnHandleUserName = returnHandleUserName;
    }

    @Basic
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "USE_RESULT")
    public String getUseResult() {
        return useResult;
    }

    public void setUseResult(String useResult) {
        this.useResult = useResult;
    }

    @Basic
    @Column(name = "RETURN_TIME")
    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "IN_RENEW")
    public String getInRenew() {
        return inRenew;
    }

    public void setInRenew(String inRenew) {
        this.inRenew = inRenew;
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
    @Column(name = "RECID")
    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    @Basic
    @Column(name = "MAIN_TITLE")
    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    @Basic
    @Column(name = "archive_no")
    public String getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
    }
    @Basic
    @Column(name = "BASEFOLDER_UNIT")
    public String getBasefolderUnit() {
        return basefolderUnit;
    }

    public void setBasefolderUnit(String basefolderUnit) {
        this.basefolderUnit = basefolderUnit;
    }

    @Basic
    @Column(name = "LJDW_MARK")
    public String getLjdwMark() {
        return ljdwMark;
    }

    public void setLjdwMark(String ljdwMark) {
        this.ljdwMark = ljdwMark;
    }

    @Basic
    @Column(name = "CATEGORY_NO")
    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    @Basic
    @Column(name = "CATEGORY_NAME")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "TABLE_NAME")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
    @Column(name = "order_num")
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "borrow_num")
    public String getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(String borrowNum) {
        this.borrowNum = borrowNum;
    }
}