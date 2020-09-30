package com.sinosoft.sinoep.modules.dagl.bmgl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DAGL_VIRTUAL_CLASS")
public class VirtualClass {

    //** 主键id //*
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;

 

    //** 创建人id //*
    @Column(name = "cre_user_id", length = 50)
    private String creUserId;//创建人id

    //** 创建人姓名 //*
    @Column(name = "cre_user_name", length = 50)
    private String creUserName;//创建人姓名

    //** 创建人部门id //*
    @Column(name = "cre_dept_id", length = 50)
    private String creDeptId;//创建人部门id

    //** 创建人部门名 //*
    @Column(name = "cre_dept_name", length = 50)
    private String creDeptName;//创建人部门名

    //** 创建人处室id //*
    @Column(name = "cre_chushi_id", length = 50)
    private String creChushiId;//创建人处室id

    //** 创建人处室名 //*
    @Column(name = "cre_chushi_name", length = 50)
    private String creChushiName;//创建人处室名

    //** 创建人局id //*
    @Column(name = "cre_ju_id", length = 50)
    private String creJuId;//创建人局id

    //** 创建人局name //*
    @Column(name = "cre_ju_name", length = 50)
    private String creJuName;//创建人局id
    
    public String getCreJuName() {
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	//** 创建时间 //*
    @Column(name = "CRE_TIME", length = 50)
    private String creTime;
    
    //** 字段英文名 //*
    @Column(name = "COLUMN_NAME", length = 50)
    private String columnName;
    //** 字段分类值 //*
    @Column(name = "COLUMN_VALUE", length = 50)
    private String columnValue;
    
    //** 门类代号 //*
    @Column(name = "CATEGORY_CODE", length = 50)
    private String categoryCode;
    
    //**父虚拟分类id //*
    @Column(name = "PID", length = 50)
    private String pId;
    
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	/** 虚拟分类名称、 */
	@Column(name = "NAME", length = 50)
	private String name;
	
	/** 是否管理员1是2不是 */
	@Column(name = "ISADMIN", length = 50)
	private String isAdmin;
	
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}


    
    
  
}
