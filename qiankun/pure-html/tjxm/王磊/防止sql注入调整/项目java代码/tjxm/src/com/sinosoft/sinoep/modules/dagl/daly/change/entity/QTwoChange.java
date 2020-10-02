package com.sinosoft.sinoep.modules.dagl.daly.change.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;

/**
 * Q2变更审批实体
 * TODO 
 * @author 王磊
 * @Date 2018年11月23日 下午2:50:47
 */
@Entity
@Table(name = "DAGL_Q2_CHANGE")
public class QTwoChange {

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

    @Column(name = "VISIBLE")
    private String visible;

    @Column(name = "CRE_TIME")
    private String creTime;
    
    //批量处理唯一编号（uuid），用于找到某一次所有变更的记录
    @Column(name = "pid")
    private String pid;
    
    //原系统档案唯一值
    @Column(name = "recid")
    private String recid;
    
    //案卷级档号
    @Column(name = "FOLDER_NO")
    private String folderNo;
    
    //原主管人名称
    @Column(name = "OLD_MAINTITLE")
    private String oldMaintitle;
    
    //原主管单位id
    @Column(name = "OLD_BASEFOLDER_UNIT_ID")
    private String oldBasefolderUnitId;
    
    //原主管单位name
    @Column(name = "OLD_BASEFOLDER_UNIT_NAME")
    private String oldBasefolderUnitName;
    
    //新主管人名称
    @Column(name = "NEW_MAINTITLE")
    private String newMaintitle;
    
    //新主管单位id
    @Column(name = "NEW_BASEFOLDER_UNIT_ID")
    private String newBasefolderUnitId;
    
    //新主管单位name
    @Column(name = "NEW_BASEFOLDER_UNIT_NAME")
    private String newBasefolderUnitName;
    
    //变更单号
    @Column(name = "CHANGE_NO")
    private String changeNo;
    
    //变更日期
    @Column(name = "CHANGE_DATE")
    private String changeDate;
    
    //登记日期
    @Column(name = "REGISTER_DATE")
    private String registerDate;
    
    //变更事由
    @Column(name = "REMARK")
    private String remark;
    
    //表明
    @Transient
    private String tName;
    
    public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 变更记录*/
	@Transient
	List<QTwoChange> list = new ArrayList<QTwoChange>();
	
	public List<QTwoChange> getList() {
		return list;
	}

	public void setList(List<QTwoChange> list) {
		this.list = list;
	}

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getFolderNo() {
		return folderNo;
	}

	public void setFolderNo(String folderNo) {
		this.folderNo = folderNo;
	}

	public String getOldMaintitle() {
		return oldMaintitle;
	}

	public void setOldMaintitle(String oldMaintitle) {
		this.oldMaintitle = oldMaintitle;
	}

	public String getOldBasefolderUnitId() {
		return oldBasefolderUnitId;
	}

	public void setOldBasefolderUnitId(String oldBasefolderUnitId) {
		this.oldBasefolderUnitId = oldBasefolderUnitId;
	}

	public String getOldBasefolderUnitName() {
		return oldBasefolderUnitName;
	}

	public void setOldBasefolderUnitName(String oldBasefolderUnitName) {
		this.oldBasefolderUnitName = oldBasefolderUnitName;
	}

	public String getNewMaintitle() {
		return newMaintitle;
	}

	public void setNewMaintitle(String newMaintitle) {
		this.newMaintitle = newMaintitle;
	}

	public String getNewBasefolderUnitId() {
		return newBasefolderUnitId;
	}

	public void setNewBasefolderUnitId(String newBasefolderUnitId) {
		this.newBasefolderUnitId = newBasefolderUnitId;
	}

	public String getNewBasefolderUnitName() {
		return newBasefolderUnitName;
	}

	public void setNewBasefolderUnitName(String newBasefolderUnitName) {
		this.newBasefolderUnitName = newBasefolderUnitName;
	}

	public String getChangeNo() {
		return changeNo;
	}

	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
}
