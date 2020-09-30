package com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 试题实体类
 * TODO 
 * @author 王磊
 * @Date 2018年7月23日 下午9:11:29
 */
@Entity
@Table(name = "xxkh_question_info")
public class Question {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人ID（也是申请人ID字段） */
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名称（也是申请人名称字段） */
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建部门ID */
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/** 创建部门名 */
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/** 创建人处室ID */
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	
	/** 创建人二级局ID */
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名 */
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/** 逻辑删除 */
	@Column(name = "visible")
	private String visible;
	
	/** 创建时间 */
	@Column(name = "cre_time")
	private String creTime;
	
	/** 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;
	
	/** 试题所属大类*/
	@Column(name = "type")
	private String type;
	
	/** 试题所属小类*/
	@Column(name = "node_id")
	private String nodeId;
	
	/** 难易程度  1：简单；2：一般；3：困难*/
	@Column(name = "difficulty_level")
	private String difficultyLevel;
	
	/** 题型 1：单选；2：多选；3：判断；4：填空；5：问答*/
	@Column(name = "question_type")
	private String questionType;
	
	/** 题干,试题描述*/
	@Column(name = "describe")
	private String describe;
	
	/** 答案解析*/
	@Column(name = "analysis")
	private String analysis;
	
	/** 试题状态 0：草稿；1：发布*/
	@Column(name = "state")
	private String state;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	/** 是否被选中*/
	@Transient
	private  String isCheck;
	
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}
	

	/** 试题对应的选项*/
	@Transient
	List<Option> list = new ArrayList<Option>();
	
	public List<Option> getList() {
		return list;
	}

	public void setList(List<Option> list) {
		this.list = list;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getAnalysis() {
		return analysis;
	}

	

	public Question(String id,String nodeId, String difficultyLevel, String questionType, String describe,String creUserName,String creTime) {
		super();
		this.id = id;
		this.nodeId = nodeId;
		this.difficultyLevel = difficultyLevel;
		this.questionType = questionType;
		this.describe = describe;
		this.creUserName = creUserName;
		this.creTime=creTime;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public Question() {
		super();
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

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}




}
