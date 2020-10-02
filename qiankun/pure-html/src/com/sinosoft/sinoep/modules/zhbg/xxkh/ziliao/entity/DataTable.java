package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "xxkh_zi_liao")
public class DataTable {

	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	/** 唯一标识 */
	@Column(name = "NODE_ID", length = 50)
	private String nodeId;
	
	
	/** 资料名称*/
	@Column(name = "DATA_NAME", length = 50)
	private String dataName;
	
	/** 排序*/
	@Column(name = "SORT", length = 50)
	private String sort;
	/** 删除字段1存在0删除*/
	@Column(name = "VISIBLE", length = 50)
	private String visible;
	
	/** 是否必学（0不必学1必学）*/
	@Column(name = "IS_WILL_LEARN", length = 2)
	private String isWillLearn;
	
	/** 最少学习时长*/
	@Column(name = "MINIMUM_LEARNING_TIME", length = 50)
	private String minimumLearningTime;
	
	/** 最晚学习日期*/
	@Column(name = "LATEST_STUDY_DATE", length = 30)
	private String latestStudyDate;
	
	/** 创建人*/
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	/** 创建人id*/
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	/** 创建时间*/
	@Column(name = "CRE_TIME", length = 50)
	private String creTime;

	/** 视频*/
	@Column(name = "VIDEO", length = 50)
	private String video;

	/** 操作*/
	@Transient
	private String cz="";
	
	
	/** 归属类*/
	@Column(name = "type", length = 50)
	private String type;
	/** 创建部门id*/
	@Column(name = "CRE_DEPT_ID", length = 50)
	private String creDeptId;
	/** 创建部门名*/
	@Column(name = "CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	/** 最近更新时间*/
	@Column(name = "UPDATE_TIME", length = 50)
	private String updateTime;
	/** 	最近更新id*/
	@Column(name = "UPDATE_USER_ID", length = 50)
	private String updateUserId;
	/** 	最近更新人名*/
	@Column(name = "UPDATE_USER_NAME", length = 50)
	private String updateUserName;
	
	/** 	内容*/
	@Column(name = "CONTEXT", length = 50)
	private String context;
	
	/**     共享时间*/
	@Column(name = "SHARE_TIME", length = 50)
	private String shareTime;
	
	/**     共享部门*/
	@Column(name = "SHARE_DEPT", length = 500)
	private String shareDept;
	
	/**     共享部门id*/
	@Column(name = "SHARE_DEPT_ID", length = 500)
	private String shareDeptId;
	
	/**     保存或发布（0保存1发布）*/
	@Column(name = "SUBFLAG", length = 50)
	private String subflag;
	
	/** 备注 */
	@Column(name = "REMARK", length = 50)
	private String remark;
	/** 创建人处室ID、 */
	@Column(name = "CRE_CHUSHI_ID", length = 50)
	private String creChuShiId;
	/** 创建人处室名、 */
	@Column(name = "CRE_CHUSHI_NAME", length = 50)
	private String creChuShiName;
	/** 创建人二级局ID、 */
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;
	/** 创建人二级局名、 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getCreUserName() {
		return creUserName;
	}
	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}
	public String getCreUserId() {
		return creUserId;
	}
	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}
	public String getCreTime() {
		return creTime;
	}
	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSubflag() {
		return subflag;
	}
	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}
	public String getShareTime() {
		return shareTime;
	}
	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}
	public String getShareDept() {
		return shareDept;
	}
	public void setShareDept(String shareDept) {
		this.shareDept = shareDept;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getIsWillLearn() {
		return isWillLearn;
	}
	public void setIsWillLearn(String isWillLearn) {
		this.isWillLearn = isWillLearn;
	}
	public String getMinimumLearningTime() {
		return minimumLearningTime;
	}
	public void setMinimumLearningTime(String minimumLearningTime) {
		this.minimumLearningTime = minimumLearningTime;
	}
	public String getLatestStudyDate() {
		return latestStudyDate;
	}
	public void setLatestStudyDate(String latestStudyDate) {
		this.latestStudyDate = latestStudyDate;
	}
	public String getShareDeptId() {
		return shareDeptId;
	}
	public void setShareDeptId(String shareDeptId) {
		this.shareDeptId = shareDeptId;
	}
	
}
