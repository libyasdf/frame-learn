package com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.common.constant.ConfigConsts;

/**
 * 实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午8:59:25
 */
@Entity
@Table(name = "kqgl_go_out_info")
public class GoOutInfo {
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	/*创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	/*创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	/*创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	/*创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	/*创建人处室id*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	/*创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	/*创建人二级局id*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	/*创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*流程状态*/
	@Column(name = "subflag")
	private String subflag;
	
	/*最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	/*最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*标题*/
	@Column(name = "go_out_title")
	private String goOutTitle;
	/*单位id*/
	@Column(name = "applicant_unit_id")
	private String applicantUnitId;
	/*单位名称*/
	@Column(name = "applicant_unit_name")
	private String applicantUnitName;
	/*申请时间*/
	@Column(name = "application_time")
	private String applicationTime;
	/*外出日期*/
	@Column(name = "go_out_date")
	private String goOutDate;
	/*外出起止时间*/
	@Column(name = "START_STOP_TIME")
	private String startStopTime;
	/*目的地*/
	@Column(name = "destination")
	private String destination;
	/*外出时长*/
	@Column(name = "go_out_long_time")
	private String goOutLongTime;
	/*外出事由*/
	@Column(name = "go_out_reason")
	private String goOutReason;
	/*操作*/
	@Transient
	private String cz = "";

	/*状态名称*/
	@Transient
	private String subflagName="";
	
	
	public GoOutInfo() {
		
	}
	public GoOutInfo(String id, String goOutTitle, String creUserName,  String creDeptName,String applicantUnitName,
			String goOutStartDate,String goOutEndDate,String startAmOrPm,String endAmOrPm,String goOutLongTime, String destination, String subflag) {
		this.id = id;
		this.goOutTitle = goOutTitle;
		this.creUserName = creUserName;
		this.creDeptName = creDeptName;
		this.goOutLongTime = goOutLongTime;
		this.destination = destination;
		this.subflag = subflag;
		this.applicantUnitName=applicantUnitName;
		
	}
	
	public String getSubflagName() {
		if(ConfigConsts.START_FLAG.equals(subflag)){
			return "草稿";
		}else if(ConfigConsts.SUB_FLAG.equals(subflag)){
			return "流程中";
		}else if(ConfigConsts.APPROVAL_FLAG.equals(subflag)){
			return "审批通过";
		}else if(ConfigConsts.NO_APPROVAL_FLAG.equals(subflag)){
			return "审批未通过";
		}
		return subflagName;
	}
	public void setSubflagName(String subflagName) {
		this.subflagName = subflagName;
	}
	

	public String getGoOutDate() {
		return goOutDate;
	}
	public void setGoOutDate(String goOutDate) {
		this.goOutDate = goOutDate;
	}
	public String getStartStopTime() {
		return startStopTime;
	}
	public void setStartStopTime(String startStopTime) {
		this.startStopTime = startStopTime;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
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
	
	public String getSubflag() {
		return subflag;
	}
	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getGoOutTitle() {
		return goOutTitle;
	}
	public void setGoOutTitle(String goOutTitle) {
		this.goOutTitle = goOutTitle;
	}
	public String getApplicantUnitId() {
		return applicantUnitId;
	}
	public void setApplicantUnitId(String applicantUnitId) {
		this.applicantUnitId = applicantUnitId;
	}
	public String getApplicantUnitName() {
		return applicantUnitName;
	}
	public void setApplicantUnitName(String applicantUnitName) {
		this.applicantUnitName = applicantUnitName;
	}
	public String getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getGoOutLongTime() {
		return goOutLongTime;
	}
	public void setGoOutLongTime(String goOutLongTime) {
		this.goOutLongTime = goOutLongTime;
	}
	public String getGoOutReason() {
		return goOutReason;
	}
	public void setGoOutReason(String goOutReason) {
		this.goOutReason = goOutReason;
	}
}
