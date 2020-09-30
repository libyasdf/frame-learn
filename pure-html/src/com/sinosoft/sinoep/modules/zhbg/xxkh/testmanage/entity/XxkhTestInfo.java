package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 考试信息实体类
 * @author 李颖洁  
 * @date 2018年9月5日  上午9:31:45
 */
@Entity
@Table(name = "XXKH_TEST_INFO")
public class XxkhTestInfo  {
	
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 创建人姓名 */
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	
	/** 创建人id */
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人部门名称 */
	@Column(name = "CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	
	/** 创建人部门id */
	@Column(name = "CRE_DEPT_ID", length = 50)
	private String creDeptId;
	
	/** 创建人二级局ID */
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;
	
	/** 创建人二级局名 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
	
	/** 创建时间 */
	@Column(name = "CRE_TIME", length = 30)
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
	
	/** 逻辑删除 0：已“删除；1：未删除 */
	@Column(name = "VISIBLE", length = 1)
	private String visible;
	
	/** 考试名称 */
	@Column(name = "NAME", length = 500)
	private String name;
	
	/** 考试大类：法制、保密、政治理论、部门 */
	@Column(name = "TYPE", length = 50)
	private String type;
	
	/** 所属小类的唯一标识 */
	@Column(name = "NODE_ID", length = 50)
	private String nodeId;
	
	/** 当前试卷要求的答题时长(分钟) */
	@Column(name = "ANSWER_TIME", length = 50)
	private String answerTime;
	
	/** 考试难易程度 1：简单；2：一般；3：困难 */
	@Column(name = "DIFFICULTY_LEVEL", length = 1)
	private String difficultyLevel;
	
	/** 满分分数 */
	@Column(name = "FULL_SCORE", length = 50)
	private String fullScore;
	
	/** 及格分数 */
	@Column(name = "PASS_SCORE", length = 50)
	private String passScore;
	
	/** 考试开始时间(YYYY-MM-DD) */
	@Column(name = "START_TIME", length = 30)
	private String startTime;
	
	/** 考试结束时间(YYYY-MM-DD) */
	@Column(name = "END_TIME", length = 30)
	private String endTime;
	
	/** 是否上报考试人员 ，0：否；1：是 */
	@Column(name = "TEST_TO_DEPARTMENT", length = 1)
	private String testToDepartment;
	
	/** 是否多次考试 ，0：否；1：是 */
	@Column(name = "IS_MORE_CHANCE", length = 1)
	private String isMoreChance;
	
	/** 考试后显示答案，0：否；1：是 */
	@Column(name = "IS_SHOW_ANSWER", length = 1)
	private String isShowAnswer;
	
	/** 是否人工评卷，0：否；1：是 */
	@Column(name = "ARTIFICIAL_MARKING", length = 1)
	private String artificialMarking;
	
	/** 是否答案随机，0：否；1：是 */
	@Column(name = "ANSWER_RANDOM", length = 1)
	private String answerRandom;
	
	/** 考试须知 */
	@Column(name = "TEST_NOTICE", length = 1000)
	private String testNotice;
	
	/** 参加考试处室名称，逗号分隔 */
	@Column(name = "TEST_CHU_SHI_NAMES", length = 2000)
	private String testChuShiNames;
	
	/** 参加考试处室id，逗号分隔 */
	@Column(name = "TEST_CHU_SHI_IDS", length = 4000)
	private String testChuShiIds;
	
	/** 职务   不需要上报考试人员时，需要选择处室，并勾选人员职务 */
	@Column(name = "DUTY_IDS", length = 100)
	private String dutyIds;
	
	/** 职级   不需要上报考试人员时，需要选择人员职级 */
	@Column(name = "LEVEL_IDS", length = 100)
	private String levelIds;
	
	/** 人员要求 */
	@Column(name = "REQUIREMENT", length = 1000)
	private String requirement;
	
	/** 评卷状态 0：未评卷；1：评卷中；2：已评卷 */
	@Column(name = "MARK_STATUS", length = 1)
	private String markStatus;
	
	/** 考试状态 0：草稿；1：已提交；2：已发布 */
	@Column(name = "STATE", length = 1)
	private String state;
	
	/** 个人考试分数 :用于个人成绩查询*/
	@Transient
	private String score;

	/** 个人考试总数量 :用于个人成绩查询*/
	@Transient
	private Integer total;
	
	/**成绩查询最大值 */
	@Transient
	private String maxScore;
	
	/**成绩查询最小值 */
	@Transient
	private String minScore;
	
	/**考试总人数 */
	@Transient
	private String sumPeople;
	
	/**实际考试人数 */
	@Transient
	private String submitPeople;
	
	/**平均分 */
	@Transient
	private String avgSvore;
	
	/**及格人数 */
	@Transient
	private String passPeople;
	
	/** 主观题分数 */
	@Transient
	private String subjectiveScore;
	
	/** 客观题分数 */
	@Transient
	private String objectiveScore;
	
	/** 试卷id */
	@Transient
	private String paperId;
	
	/** 试卷开始时间 */
	@Transient
	private String beginTestTime;

	/** 一场考试的未考试次数 */
	@Transient
	private String wkcs;

	/** 一场考试的已考试次数 */
	@Transient
	private String ykcs;

	/**
	 * 无参构造
	 */
	public XxkhTestInfo() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param fullScore
	 * @param passScore
	 * @param startTime
	 * @param endTime
	 * @param state
	 */
	public XxkhTestInfo(String id, String name, String type, String fullScore, String passScore, String startTime,
			String endTime, String state) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.fullScore = fullScore;
		this.passScore = passScore;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

	/**
	 * @param id
	 */
	public XxkhTestInfo(String id) {
		super();
		this.id = id;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
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

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getFullScore() {
		return fullScore;
	}

	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}

	public String getPassScore() {
		return passScore;
	}

	public void setPassScore(String passScore) {
		this.passScore = passScore;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTestToDepartment() {
		return testToDepartment;
	}

	public void setTestToDepartment(String testToDepartment) {
		this.testToDepartment = testToDepartment;
	}

	public String getIsMoreChance() {
		return isMoreChance;
	}

	public void setIsMoreChance(String isMoreChance) {
		this.isMoreChance = isMoreChance;
	}

	public String getIsShowAnswer() {
		return isShowAnswer;
	}

	public void setIsShowAnswer(String isShowAnswer) {
		this.isShowAnswer = isShowAnswer;
	}

	public String getArtificialMarking() {
		return artificialMarking;
	}

	public void setArtificialMarking(String artificialMarking) {
		this.artificialMarking = artificialMarking;
	}

	public String getAnswerRandom() {
		return answerRandom;
	}

	public void setAnswerRandom(String answerRandom) {
		this.answerRandom = answerRandom;
	}

	public String getTestNotice() {
		return testNotice;
	}

	public void setTestNotice(String testNotice) {
		this.testNotice = testNotice;
	}

	public String getTestChuShiNames() {
		return testChuShiNames;
	}

	public void setTestChuShiNames(String testChuShiNames) {
		this.testChuShiNames = testChuShiNames;
	}

	public String getTestChuShiIds() {
		return testChuShiIds;
	}

	public void setTestChuShiIds(String testChuShiIds) {
		this.testChuShiIds = testChuShiIds;
	}

	public String getDutyIds() {
		return dutyIds;
	}

	public void setDutyIds(String dutyIds) {
		this.dutyIds = dutyIds;
	}

	public String getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(String levelIds) {
		this.levelIds = levelIds;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getMarkStatus() {
		return markStatus;
	}

	public void setMarkStatus(String markStatus) {
		this.markStatus = markStatus;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	public String getMaxScore() {
		return maxScore;
	}



	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}



	public String getMinScore() {
		return minScore;
	}



	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}



	public String getSumPeople() {
		return sumPeople;
	}



	public void setSumPeople(String sumPeople) {
		this.sumPeople = sumPeople;
	}



	public String getSubmitPeople() {
		return submitPeople;
	}



	public void setSubmitPeople(String submitPeople) {
		this.submitPeople = submitPeople;
	}



	public String getAvgSvore() {
		return avgSvore;
	}



	public void setAvgSvore(String avgSvore) {
		this.avgSvore = avgSvore;
	}



	public String getPassPeople() {
		return passPeople;
	}



	public void setPassPeople(String passPeople) {
		this.passPeople = passPeople;
	}

	public String getSubjectiveScore() {
		return subjectiveScore;
	}

	public void setSubjectiveScore(String subjectiveScore) {
		this.subjectiveScore = subjectiveScore;
	}

	public String getObjectiveScore() {
		return objectiveScore;
	}

	public void setObjectiveScore(String objectiveScore) {
		this.objectiveScore = objectiveScore;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getBeginTestTime() {
		return beginTestTime;
	}

	public void setBeginTestTime(String beginTestTime) {
		this.beginTestTime = beginTestTime;
	}

	public String getWkcs() {
		return wkcs;
	}

	public void setWkcs(String wkcs) {
		this.wkcs = wkcs;
	}

	public String getYkcs() {
		return ykcs;
	}

	public void setYkcs(String ykcs) {
		this.ykcs = ykcs;
	}
}