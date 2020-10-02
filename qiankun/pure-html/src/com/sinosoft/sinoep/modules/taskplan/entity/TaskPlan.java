package com.sinosoft.sinoep.modules.taskplan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 实体类
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:45:48
 */
@Entity
@Table(name = "TASK_PLAN")
public class TaskPlan {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "FILETYPE")
	private String workFlowId;
	
	@Column(name = "SUBFLAG")
	private String subflag;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "ZHUB_DEPT")
	private String zhubDept;
	
	@Column(name = "ZHUB_PERSON")
	private String zhubPerson;
	
	@Column(name = "ZHUB_DEPT_NM")
	private String zhubDeptNm;
	
	@Column(name = "ZHUB_PERSON_NM")
	private String zhubPersonNm;
	
	@Column(name = "XIEB_DEPT")
	private String xiebDept;
	
	@Column(name = "XIEB_PERSON")
	private String xiebPerson;
	
	@Column(name = "CRE_TIME")
	private String creTime;
	
	@Column(name = "CRE_USERID")
	private String creUserId;
	
	@Column(name = "CRE_USERNM")
	private String creUserName;
	
	@Column(name = "IDEA")
	private String idea;
	
	@Column(name = "VISIBLE")
	private String visible;
	
	@Column(name = "SEX")
	private String sex;
	
	@Column(name = "WEEK")
	private String week;
	
	@Column(name = "AREA")
	private String area;
	
	@Transient
	private String workitemid;
	
	@Transient
	private String cz = "";
	
	@Transient
	private String yibanid;
	
	public TaskPlan() {
		super();
	}
	
	/**
	 * 为hql连表查询，创建有参构造
	 * @param yibanid
	 * @param id
	 * @param title
	 * @param workFlowId
	 * @param subflag
	 * @param note
	 * @param zhubDept
	 * @param zhubPerson
	 * @param zhubDeptNm
	 * @param zhubPersonNm
	 * @param xiebDept
	 * @param xiebPerson
	 * @param creTime
	 * @param creUserId
	 * @param creUserName
	 * @param idea
	 */
	public TaskPlan(String yibanid,String id, String title, String workFlowId, String subflag, String note, String zhubDept,
			String zhubPerson, String zhubDeptNm, String zhubPersonNm, String xiebDept, String xiebPerson,
			String creTime, String creUserId, String creUserName, String idea ) {
		super();
		this.yibanid = yibanid;
		this.id = id;
		this.title = title;
		this.workFlowId = workFlowId;
		this.subflag = subflag;
		this.note = note;
		this.zhubDept = zhubDept;
		this.zhubPerson = zhubPerson;
		this.zhubDeptNm = zhubDeptNm;
		this.zhubPersonNm = zhubPersonNm;
		this.xiebDept = xiebDept;
		this.xiebPerson = xiebPerson;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.idea = idea;
	}

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getZhubDept() {
		return zhubDept;
	}

	public void setZhubDept(String zhubDept) {
		this.zhubDept = zhubDept;
	}

	public String getZhubPerson() {
		return zhubPerson;
	}

	public void setZhubPerson(String zhubPerson) {
		this.zhubPerson = zhubPerson;
	}

	public String getZhubDeptNm() {
		return zhubDeptNm;
	}

	public void setZhubDeptNm(String zhubDeptNm) {
		this.zhubDeptNm = zhubDeptNm;
	}

	public String getZhubPersonNm() {
		return zhubPersonNm;
	}

	public void setZhubPersonNm(String zhubPersonNm) {
		this.zhubPersonNm = zhubPersonNm;
	}

	public String getXiebDept() {
		return xiebDept;
	}

	public void setXiebDept(String xiebDept) {
		this.xiebDept = xiebDept;
	}

	public String getXiebPerson() {
		return xiebPerson;
	}

	public void setXiebPerson(String xiebPerson) {
		this.xiebPerson = xiebPerson;
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
}
