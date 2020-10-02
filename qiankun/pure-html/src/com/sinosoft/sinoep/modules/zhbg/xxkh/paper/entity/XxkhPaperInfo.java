package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;

/**
 * 
 * @author 颜振兴
 * 时间：2018年7月30日
 *	XxkhPaperInfo
 */
@Entity
@Table(name = "XXKH_PAPER_INFO")
public class XxkhPaperInfo{
	
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 创建人id */
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人姓名 */
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	
	/** 创建人部门ID */
	@Column(name = "CRE_DEPT_ID", length = 50)
	private String creDeptId;
	
	/** 创建人部门名 */
	@Column(name = "CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	
	/** 创建人处室ID */
	@Column(name = "CRE_CHUSHI_ID", length = 50)
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "CRE_CHUSHI_NAME", length = 50)
	private String creChushiName;
	
	/** 创建人二级局ID */
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;
	
	/** 创建人二级局名 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
	
	/** 创建时间 */
	@Column(name = "CRE_TIME", length = 50)
	private String creTime;
	
	/** 最后更新人姓名 */
	@Column(name = "UPDATE_USER_NAME", length = 50)
	private String updateUserName;
	
	/** 最后更新人id */
	@Column(name = "UPDATE_USER_ID", length = 50)
	private String updateUserId;
	
	/** 最后更新时间 */
	@Column(name = "UPDATE_TIME", length = 50)
	private String updateTime;
	
	/** 逻辑删除*/
	@Column(name = "VISIBLE", length = 50)
	private String visible;
	
	/** 试卷名称 */
	@Column(name = "NAME", length = 50)
	private String name;
	
	/** 试卷所属大类:法制、保密等  */
	@Column(name = "TYPE", length = 50)
	private String type;
	
	/**试题所属具体类别：法制-物权法、法制-劳动法等，对应树的唯一标识*/
	@Column(name = "NODE_ID", length = 50)
	private String nodeId;
	
	/** 1：简单；2：一般；3：困难 */
	@Column(name = "DIFFICULTY_LEVEL", length = 50)
	private String difficultyLevel;
	
	
	/** 备注 */
	@Column(name = "REMARK", length = 50)
	private String remark;
	
	/** 是否共享试卷，0：不共享，1共享 */
	@Column(name = "IS_SHARE", length = 50)
	private String isShare;
	
	/** 组卷方式，0：人工组卷，1：自动组卷 */
	@Column(name = "CREATE_TYPE", length = 50)
	private String createType;
	
	/** 试卷总分 */
	@Column(name = "FULL_SCORE", length = 50)
	private String fullScore;
	
	/** 0：草稿；1：已发布 */
	@Column(name = "STATE", length = 50)
	private String state;
	
	/** 部门自动组卷类型*/
	@Column(name = "DEPT_AUTO_TYPE", length = 50)
	private String deptType;
	//@OneToMany(cascade=CascadeType.MERGE,mappedBy="paperId")
	@Transient
	private  List<XxkhQuestionGroup> group = new ArrayList<>();
	
	@Transient
	private String isDetp;
	
	@Transient
	private String cz;

	/** 试卷主观题得分 **/
	@Transient
	private String paperSubjectiveScore;

	/** 试卷客观题得分 **/
	@Transient
	private String paperObjectiveScore;

	/** 人工评卷状态 **/
	@Transient
	private String artificialMarkingState;

	public XxkhPaperInfo() {
	}

	public XxkhPaperInfo(String id) {
		this.id = id;
	}

	public XxkhPaperInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creChushiId, String creChushiName, String creJuId, String creJuName, String creTime,
			String updateUserName, String updateUserId, String updateTime, String visible, String name, String type,
			String nodeId, String difficultyLevel, String remark, String isShare, String createType, String fullScore,
			String state) {
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChushiId = creChushiId;
		this.creChushiName = creChushiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.creTime = creTime;
		this.updateUserName = updateUserName;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
		this.visible = visible;
		this.name = name;
		this.type = type;
		this.nodeId = nodeId;
		this.difficultyLevel = difficultyLevel;
		this.remark = remark;
		this.isShare = isShare;
		this.createType = createType;
		this.fullScore = fullScore;
		this.state = state;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreUserId() {
		return this.creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return this.creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return this.creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return this.creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreChushiId() {
		return this.creChushiId;
	}

	public void setCreChushiId(String creChushiId) {
		this.creChushiId = creChushiId;
	}

	public String getCreChushiName() {
		return this.creChushiName;
	}

	public void setCreChushiName(String creChushiName) {
		this.creChushiName = creChushiName;
	}

	public String getCreJuId() {
		return this.creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreJuName() {
		return this.creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getCreTime() {
		return this.creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDifficultyLevel() {
		return this.difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsShare() {
		return this.isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public String getCreateType() {
		return this.createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getFullScore() {
		return this.fullScore;
	}

	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public List<XxkhQuestionGroup> getGroup() {
		return group;
	}

	public void setGroup(List<XxkhQuestionGroup> group) {
		this.group = group;
	}
	public XxkhPaperInfo(String id, String name, String type, String nodeId, String difficultyLevel,String fullScore) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.nodeId = nodeId;
		this.difficultyLevel = difficultyLevel;
		this.fullScore = fullScore;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getIsDetp() {
		return isDetp;
	}

	public void setIsDetp(String isDetp) {
		this.isDetp = isDetp;
	}

	public String getPaperSubjectiveScore() {
		return paperSubjectiveScore;
	}

	public void setPaperSubjectiveScore(String paperSubjectiveScore) {
		this.paperSubjectiveScore = paperSubjectiveScore;
	}

	public String getPaperObjectiveScore() {
		return paperObjectiveScore;
	}

	public void setPaperObjectiveScore(String paperObjectiveScore) {
		this.paperObjectiveScore = paperObjectiveScore;
	}

	public String getArtificialMarkingState() {
		return artificialMarkingState;
	}

	public void setArtificialMarkingState(String artificialMarkingState) {
		this.artificialMarkingState = artificialMarkingState;
	}
}
