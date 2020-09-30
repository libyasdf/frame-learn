package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 人员试卷关联实体类
 * @author 李颖洁  
 * @date 2018年8月14日  下午8:48:20
 */
@Entity
@Table(name = "XXKH_USER_PAPER")
public class XxkhUserPaper{
	
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", length = 50)
	private String id;
	
	/** 考试id */
	@Column(name = "TEST_ID", length = 50)
	private String testId;
	
	/** 试卷id */
	@Column(name = "PAPER_ID", length = 50)
	private String paperId;
	
	/** 考试名称 */
	@Column(name = "TEST_NAME", length = 50)
	private String testName;
	
	/** 创建人id */
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人姓名 */
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	
	/** 创建人部门ID */
	@Column(name="CRE_DEPT_ID", length = 50)
	private String creDeptId;
	
	/** 创建人部门名 */
	@Column(name="CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	
	/** 创建人处室ID */
	@Column(name="CRE_CHUSHI_ID", length = 50)
	private String creChushiId;
	
	/** 创建人处室名 */
	@Column(name="CRE_CHUSHI_NAME", length = 50)
	private String creChushiName;
	
	/** 创建人二级局ID */
	@Column(name="CRE_JU_ID", length = 50)
	private String creJuId;
	
	/** 创建人二级局名 */
	@Column(name="CRE_JU_NAME", length = 50)
	private String creJuName;
	
	/** 逻辑删除 ,0:不可见，1：可见 */
	@Column(name="VISIBLE", length = 1)
	private String visible;
	
	/** 创建时间 */
	@Column(name="CRE_TIME", length = 30)
	private String creTime;
	
	/** 用户id */
	@Column(name = "USER_ID", length = 50)
	private String userId;
	
	/** 用户姓名 */
	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	/** 试卷顺序：1、2、3... */
	@Column(name = "PAPER_ORDER", length = 2)
	private String paperOrder;
	
	/** 是否已交卷，0：未交卷，1：已交卷 */
	@Column(name = "IS_SUBMIT", length = 1)
	private String isSubmit;
	
	/** 交卷时间 */
	@Column(name = "SUBMIT_TIME", length = 50)
	private String submitTime;
	
	/** 首次进入答题时间，用于判断 */
	@Column(name = "BEGIN_TEST_TIME", length = 50)
	private String beginTestTime;
	
	/** 当前试卷要求的答题时长，用于判断是否还能再次答题 */
	@Column(name = "ANSWER_TIME", length = 50)
	private String answerTime;
	
	/** 考生所在部门id */
	@Column(name = "CANDIDATE_DEPT_ID", length = 50)
	private String candidateDeptId;
	
	/** 考生所在部门name */
	@Column(name = "CANDIDATE_DEPT_NAME", length = 50)
	private String candidateDeptName;
	
	/** 考生所在处室id */
	@Column(name = "CANDIDATE_CHUSHI_ID", length = 50)
	private String candidateChushiId;
	
	/** 考生所在处室name */
	@Column(name = "CANDIDATE_CHUSHI_NAME", length = 50)
	private String candidateChushiName;
	
	/** 考生所在二级局id */
	@Column(name = "CANDIDATE_JU_ID", length = 50)
	private String candidateJuId;
	
	/** 考生所在二级局name */
	@Column(name = "CANDIDATE_JU_NAME", length = 50)
	private String candidateJuName;
	
	/** 考生职务 */
	@Column(name = "CANDIDATE_DUTY", length = 50)
	private String candidateDuty;
	
	/** 考生职务名称 */
	@Column(name = "CANDIDATE_DUTY_NAME", length = 50)
	private String candidateDutyName;
	
	/** 考生职级 */
	@Column(name = "CANDIDATE_LEVEL", length = 50)
	private String candidateLevel;
	
	/** 考生职级 名称*/
	@Column(name = "CANDIDATE_LEVEL_NAME", length = 50)
	private String candidateLevelName;
	
	/** 性别*/
	@Column(name = "USER_SEX", length = 10)
	private String userSex;
	
	/** 电话*/
	@Column(name = "PHONE", length = 50)
	private String phone;
	
	/** 试卷主观题总得分（简答题）*/
	@Column(name = "PAPER_SUBJECTIVE_SCORE", length = 50)
	private String paperSubjectiveScore;
	
	/** 试卷客观题总得分*/
	@Column(name = "PAPER_OBJECTIVE_SCORE", length = 50)
	private String paperObjectiveScore;
	
	/** 人工评卷状态，0：暂存或未评卷，1：已人工评卷完毕；人工评卷只评主观题得分*/
	@Column(name = "ARTIFICIAL_MARKING_STATE", length = 1)
	private String artificialMarkingState;
	
	/** 人员数量   */ 
	@Transient
	private Long num;
	
	/** 列表数量（分页查询获取的总记录数）   */ 
	@Transient
	private Integer total;
	
	/** 是否及格*/
	@Transient
	private String isJiGe;
	
	/** 总分*/
	@Transient
	private String sumScore;
	
	/** 总分*/
	@Transient
	private String passScore;
	
	/** 区分人工评卷 */
	@Transient
	private String isUser;
	
	/** 是否有主观题*/
	@Transient
	private String isZhuGuan;
	public String getIsZhuGuan() {
		return isZhuGuan;
	}



	public void setIsZhuGuan(String isZhuGuan) {
		this.isZhuGuan = isZhuGuan;
	}



	/**
	 * 无参构造
	 */
	public XxkhUserPaper() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * ToDo 封装部门考试人员数量信息
	 * @param candidateChushiId
	 * @param candidateChushiName
	 * @param candidateJuId
	 * @param candidateJuName
	 * @param num
	 */
	public XxkhUserPaper(String candidateChushiId, String candidateChushiName, String candidateJuId,
			String candidateJuName, Long num) {
		this.candidateChushiId = candidateChushiId;
		this.candidateChushiName = candidateChushiName;
		this.candidateJuId = candidateJuId;
		this.candidateJuName = candidateJuName;
		this.num = num;
	}
	
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getCandidateDutyName() {
		return candidateDutyName;
	}

	public void setCandidateDutyName(String candidateDutyName) {
		this.candidateDutyName = candidateDutyName;
	}

	public String getCandidateLevelName() {
		return candidateLevelName;
	}

	public void setCandidateLevelName(String candidateLevelName) {
		this.candidateLevelName = candidateLevelName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestId() {
		return this.testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getPaperId() {
		return this.paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPaperOrder() {
		return this.paperOrder;
	}

	public void setPaperOrder(String paperOrder) {
		this.paperOrder = paperOrder;
	}

	public String getIsSubmit() {
		return this.isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getBeginTestTime() {
		return this.beginTestTime;
	}

	public void setBeginTestTime(String beginTestTime) {
		this.beginTestTime = beginTestTime;
	}

	public String getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public String getCandidateDeptId() {
		return this.candidateDeptId;
	}

	public void setCandidateDeptId(String candidateDeptId) {
		this.candidateDeptId = candidateDeptId;
	}

	public String getCandidateDeptName() {
		return this.candidateDeptName;
	}

	public void setCandidateDeptName(String candidateDeptName) {
		this.candidateDeptName = candidateDeptName;
	}

	public String getCandidateChushiId() {
		return this.candidateChushiId;
	}

	public void setCandidateChushiId(String candidateChushiId) {
		this.candidateChushiId = candidateChushiId;
	}

	public String getCandidateChushiName() {
		return this.candidateChushiName;
	}

	public void setCandidateChushiName(String candidateChushiName) {
		this.candidateChushiName = candidateChushiName;
	}

	public String getCandidateJuId() {
		return this.candidateJuId;
	}

	public void setCandidateJuId(String candidateJuId) {
		this.candidateJuId = candidateJuId;
	}

	public String getCandidateJuName() {
		return this.candidateJuName;
	}

	public void setCandidateJuName(String candidateJuName) {
		this.candidateJuName = candidateJuName;
	}

	public String getCandidateDuty() {
		return this.candidateDuty;
	}

	public void setCandidateDuty(String candidateDuty) {
		this.candidateDuty = candidateDuty;
	}

	public String getCandidateLevel() {
		return this.candidateLevel;
	}

	public void setCandidateLevel(String candidateLevel) {
		this.candidateLevel = candidateLevel;
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



	public String getIsJiGe() {
		return isJiGe;
	}



	public void setIsJiGe(String isJiGe) {
		this.isJiGe = isJiGe;
	}



	public String getSumScore() {
		return sumScore;
	}



	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}



	public String getPassScore() {
		return passScore;
	}



	public void setPassScore(String passScore) {
		this.passScore = passScore;
	}



	public String getIsUser() {
		return isUser;
	}



	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}

	
}
