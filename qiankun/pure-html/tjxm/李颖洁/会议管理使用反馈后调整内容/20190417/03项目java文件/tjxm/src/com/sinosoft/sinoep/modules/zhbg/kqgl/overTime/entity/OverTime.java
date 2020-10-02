package com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.common.constant.ConfigConsts;

/**
 * 
 * TODO 加班实体类
 * 
 * @author 张建明
 * @Date 2018年4月10日 下午2:13:14
 */

@Entity
@Table(name = "KQGL_OVER_TIME_INFO")
public class OverTime {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/*@Column(name = "TITLE")
	private String title;*/

	@Column(name = "FILETYPE")
	private String workFlowId;

	@Column(name = "SUBFLAG")
	private String subflag;

	@Column(name = "OVER_TIME_TITLE")
	private String overTimeTitle;

	@Column(name = "APPLICANT_UNIT_ID")
	private String applicationUnitId;

	@Column(name = "APPLICANT_UNIT_NAME")
	private String applicationUnitName;

	@Column(name = "APPLICATION_TIME")
	private String applicationTime;

	@Column(name = "START_STOP_TIME")
	private String startStopTime;
	
	@Column(name = "OVER_TIME_TYPE")
	private String overTimeType;

	@Column(name = "OVER_TIME_DATE")
	private String overTimeDate;

	@Column(name = "OVER_TIME_LONG_TIMEH")
	private String longTimeh;
	
	@Column(name = "OVER_TIME_LONG_TIMED")
	private String longTimed;

	@Column(name = "OVER_TIME_REASON")
	private String reason;

	@Column(name = "CRE_TIME")
	private String creTime;

	@Column(name = "CRE_USER_ID")
	private String creUserId;

	@Column(name = "CRE_USER_NAME")
	private String creUserName;

	@Column(name = "CRE_DEPT_ID")
	private String creDeptId;

	@Column(name = "CRE_DEPT_NAME")
	private String creDeptName;

	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;

	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;

	@Column(name = "CRE_JU_ID")
	private String creJuId;

	@Column(name = "CRE_JU_NAME")
	private String creJuName;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;

	@Column(name = "UPDATE_TIME")
	private String updateTime;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "IDEA")
	private String idea;

	@Transient
	private String workitemid;

	@Transient
	private String cz = "";

	@Transient
	private String yibanid;
	
	@Transient
	private String overTimeTypeName;
	
	@Transient
	private String subflagName;
	
	@Transient
	private String longTime;
	
	@Transient
	private String talLongTimeH;
	
	@Transient
	private String talLongTimeD;
	
	
	public String getLongTime() {
		if("1".endsWith(overTimeType)){
			//按小时
			return longTimeh + "小时";
		}else{
			//按天
			if("0.5".equals(longTimed)){
				return "半天";
			}else{
				return "1天";
			}
		}
	}

	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}

	public OverTime() {
		super();
	}

	/**
	 * 为hql连表查询，创建有参构造
	 * 
	 * @param id
	 * @param title
	 * @param subflag
	 * @param businessTripTitle
	 * @param applicationUnitId
	 * @param applicationUnitName
	 * @param applicationTime
	 * @param startTime
	 * @param endTime
	 * @param destination
	 * @param vehicle
	 * @param isHaveReceptionFees
	 * @param receptionFees
	 * @param longTime
	 * @param reason
	 * @param creTime
	 * @param creUserId
	 * @param creUserName
	 * @param creDeptId
	 * @param creDeptName
	 * @param creChuShiId
	 * @param creChuShiName
	 * @param creJuId
	 * @param creJuName
	 * @param updateUserId
	 * @param updateUserName
	 * @param updateTime
	 * @param visible
	 * @param workitemid
	 * @param cz
	 * @param yibanid
	 */
	public OverTime(String id, String title, String workFlowId, String subflag, String overTimeTitle,
			String applicationUnitId, String applicationUnitName, String applicationTime, String startTime,
			String endTime, String overTimeType, String overTimeDate, String longTime, String reason, String creTime,
			String creUserId, String creUserName, String creDeptId, String creDeptName, String creChuShiId,
			String creChuShiName, String creJuId, String creJuName, String updateUserId, String updateUserName,
			String updateTime, String visible, String idea, String workitemid, String cz, String yibanid) {
		super();
		this.id = id;
		/*this.title = title;*/
		this.workFlowId = workFlowId;
		this.subflag = subflag;
		this.overTimeTitle = overTimeTitle;
		this.applicationUnitId = applicationUnitId;
		this.applicationUnitName = applicationUnitName;
		this.applicationTime = applicationTime;
		this.overTimeType = overTimeType;
		this.overTimeDate = overTimeDate;
		this.reason = reason;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.visible = visible;
		this.idea = idea;
		this.workitemid = workitemid;
		this.cz = cz;
		this.yibanid = yibanid;
	}

	// t.id, t.title, t.creUserId, t.subflag, t.startTime,
	// t.applicationUnitName,"
	// + "t.longTime, t.destination"
	public OverTime(String yibanid, String id, String overTimeTitle, String subflag, String applicationUnitName,
			String startTime, String endTime, String overTimeType, String overTimeDate, String longTime,
			String creUserId, String creUserName) {
		super();
		this.yibanid = yibanid;
		this.id = id;
		this.overTimeTitle = overTimeTitle;
		this.subflag = subflag;
		this.applicationUnitName = applicationUnitName;
		this.overTimeType = overTimeType;
		this.overTimeDate = overTimeDate;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
	}

	public String getOverTimeTypeName() {
		if("0".equals(overTimeType)){
			return "按天";
		}else if("1".equals(overTimeType)){
			return "按小时";
		}
		return overTimeTypeName;
	}



	public void setOverTimeTypeName(String overTimeTypeName) {
		this.overTimeTypeName = overTimeTypeName;
	}

	public String getApplicationUnitId() {
		return applicationUnitId;
	}

	public void setApplicationUnitId(String applicationUnitId) {
		this.applicationUnitId = applicationUnitId;
	}

	public String getApplicationUnitName() {
		return applicationUnitName;
	}

	public void setApplicationUnitName(String applicationUnitName) {
		this.applicationUnitName = applicationUnitName;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public String getStartStopTime() {
		return startStopTime;
	}

	public void setStartStopTime(String startStopTime) {
		this.startStopTime = startStopTime;
	}

	public String getLongTimeh() {
		return longTimeh;
	}

	public void setLongTimeh(String longTimeh) {
		this.longTimeh = longTimeh;
	}

	public String getLongTimed() {
		return longTimed;
	}

	public void setLongTimed(String longTimed) {
		this.longTimed = longTimed;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getCreChuShiId() {
		return creChuShiId;
	}

	public void setCreChuShiId(String creChuShiId) {
		this.creChuShiId = creChuShiId;
	}

	public String getCreChuShiName() {
		return creChuShiName;
	}

	public void setCreChuShiName(String creChuShiName) {
		this.creChuShiName = creChuShiName;
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

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
*/
	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
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

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getOverTimeTitle() {
		return overTimeTitle;
	}

	public void setOverTimeTitle(String overTimeTitle) {
		this.overTimeTitle = overTimeTitle;
	}

	public String getOverTimeType() {
		return overTimeType;
	}

	public void setOverTimeType(String overTimeType) {
		this.overTimeType = overTimeType;
	}

	public String getOverTimeDate() {
		return overTimeDate;
	}

	public void setOverTimeDate(String overTimeDate) {
		this.overTimeDate = overTimeDate;
	}

	public String getSubflagName() {
		
		if(ConfigConsts.START_FLAG.equals(subflag)){
			return "草稿";
		}else if(ConfigConsts.SUB_FLAG.equals(subflag)){
			return "流程中";
		}else if(ConfigConsts.APPROVAL_FLAG.equals(subflag)){
			return "审批通过";
		}else if(ConfigConsts.NO_APPROVAL_FLAG.equals(subflag)){
			return "审批不通过";
		}else if(ConfigConsts.REMOVE_FLAG.equals(subflag)){
			return "撤办";
		}
		return subflagName;
	}

	public void setSubflagName(String subflagName) {
		this.subflagName = subflagName;
	}

	public String getTalLongTimeH() {
		return talLongTimeH;
	}

	public void setTalLongTimeH(String talLongTimeH) {
		this.talLongTimeH = talLongTimeH;
	}

	public String getTalLongTimeD() {
		return talLongTimeD;
	}

	public void setTalLongTimeD(String talLongTimeD) {
		this.talLongTimeD = talLongTimeD;
	}
	
}
