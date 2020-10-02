package com.sinosoft.sinoep.modules.dagl.daly.urge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 借阅催还记录
 * @author 王磊
 * @Date 2019年2月11日 上午10:51:30
 */
@Entity
@Table(name = "DAGL_URGE_RETURN")
public class BorrowUrge {

	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	
	@Column(name = "CRE_USER_ID")
	private String creUserId;

    @Column(name = "CRE_USER_NAME")
    private String creUserName;

    @Column(name = "CRE_DEPT_ID")
    private String creDeptId;

    @Column(name = "CRE_DEPT_NAME")
    private String creDeptName;

    @Column(name = "CRE_CHUSHI_ID")
    private String creChushiId;

    @Column(name = "CRE_CHUSHI_NAME")
    private String creChushiName;

    @Column(name = "CRE_JU_ID")
    private String creJuId;

    @Column(name = "CRE_JU_NAME")
    private String creJuName;

    @Column(name = "CRE_TIME")
    private String creTime;
    
    //借阅人id
    @Column(name = "BORROW_USER_ID")
    private String borrowUserId;
    
    //借阅人name
    @Column(name = "BORROW_USER_NAME")
    private String borrowUserName;
    
    //借阅id
    @Column(name = "BORROW_ID")
    private String borrowId;
    
    //催还内容
    @Column(name = "URGE_CONTENT")
    private String urgeContent;
    
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

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(String borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getUrgeContent() {
		return urgeContent;
	}

	public void setUrgeContent(String urgeContent) {
		this.urgeContent = urgeContent;
	}
}
