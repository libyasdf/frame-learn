package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.TestResultVo;
import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

	/**
 	*   	试题组表
 	* 		@author 颜振兴
 	*   	时间：2018年7月30日
 	*		XxkhQuestionGroup
 	*/
@Entity
@Table(name = "XXKH_QUESTION_GROUP")
public class XxkhQuestionGroup{

	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 试卷id */
	@Column(name = "PAPER_ID", length = 50)
	private String paperId;
	
	/** 创建人id */
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人姓名 */
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	
	/** 创建人部门ID */
	@Column(name = "CRE_DEPT_ID", length = 50)
	private String creDeptId;
	
	/** 创建人部门名*/
	@Column(name = "CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	
	/** 创建人处室ID */
	@Column(name = "CRE_CHUSHI_ID", length = 50)
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "CRE_CHUSHI_NAME", length = 50)
	private String creChushiName;
	
	/** 创建人二级局ID*/
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;

	/** 创建人二级局名 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
	
	/** 逻辑删除*/
	@Column(name = "VISIBLE", length = 50)
	private String visible;
	
	/** 创建时间 */
	@Column(name = "CRE_TIME", length = 50)
	private String creTime;
	
	/** 0:人工组卷，1：自动组卷 */
	@Column(name = "CREATE_TYPE", length = 50)
	private String createType;
	
	/** 试题组在试卷中顺序 */
	@Column(name = "SEQUENCE", length = 50)
	private String sequence;
	
	/** 人工组卷时该题型数量 */
	@Column(name = "QUESTION_COUNT", length = 50)
	private String questionCount;
	
	/** 该题型每题分数 */
	@Column(name = "EVERY_SCORE", length = 50)
	private String everyScore;
	
	/** 该题型总分 */
	@Column(name = "FULL_SCORE", length = 50)
	private String fullScore;
	
	/** 自动组卷时，该试题组中简单题多少个 */
	@Column(name = "SIMPLE_COUNT", length = 50)
	private String simpleCount;
	
	/** 自动组卷时，该试题组中一般题多少个 */
	@Column(name = "NORMAL_COUNT", length = 50)
	private String normalCount;
	
	/** 自动组卷时，该试题组中困难题数量*/
	@Column(name = "HARD_COUNT", length = 50)
	private String hardCount;
	
	/** 自动组卷时，改试题组的组卷状态；0：未组卷；1：已组卷*/
	@Column(name = "ZUJUAN_STATUS", length = 1)
	private String zujuanStatus;
	
	/** 试题类型：1：单选；2：多选；3：判断；4：填空；5：简答 */
	@Column(name = "QUESTION_TYPE", length = 50)
	private String questionType;
	
//	/** 主键id */
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "PAPER_ID",insertable = false, updatable = false)
//	private XxkhPaperInfo infos;
	@Transient
	private List<Question> question = new ArrayList<>();

	/**
	 * @Author 王富康
	 * @Description //TODO 交卷后显示试题及正确答案的数据Vo
	 * @Date 2018/9/12 9:16
	 **/
	@Transient
	private List<TestResultVo> TestResultList = new ArrayList<>();

	public List<TestResultVo> getTestResultList() {
		return TestResultList;
	}

	public void setTestResultList(List<TestResultVo> testResultList) {
		TestResultList = testResultList;
	}

	public XxkhQuestionGroup() {
	}

	public XxkhQuestionGroup(String id) {
		this.id = id;
	}

	public XxkhQuestionGroup(String id, String paperId, String creUserId, String creUserName, String creDeptId,
			String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName,
			String visible, String creTime, String createType, String sequence, String questionCount, String everyScore,
			String fullScore, String simpleCount, String normalCount, String hardCount, String zujuanStatus,
			String questionType) {
		this.id = id;
		this.paperId = paperId;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChushiId = creChushiId;
		this.creChushiName = creChushiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.visible = visible;
		this.creTime = creTime;
		this.createType = createType;
		this.sequence = sequence;
		this.questionCount = questionCount;
		this.everyScore = everyScore;
		this.fullScore = fullScore;
		this.simpleCount = simpleCount;
		this.normalCount = normalCount;
		this.hardCount = hardCount;
		this.zujuanStatus = zujuanStatus;
		this.questionType = questionType;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaperId() {
		return this.paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
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

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getCreTime() {
		return this.creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getCreateType() {
		return this.createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getQuestionCount() {
		return this.questionCount;
	}

	public void setQuestionCount(String questionCount) {
		this.questionCount = questionCount;
	}

	public String getEveryScore() {
		return this.everyScore;
	}

	public void setEveryScore(String everyScore) {
		this.everyScore = everyScore;
	}

	public String getFullScore() {
		return this.fullScore;
	}

	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}

	public String getSimpleCount() {
		return this.simpleCount;
	}

	public void setSimpleCount(String simpleCount) {
		this.simpleCount = simpleCount;
	}

	public String getNormalCount() {
		return this.normalCount;
	}

	public void setNormalCount(String normalCount) {
		this.normalCount = normalCount;
	}

	public String getHardCount() {
		return this.hardCount;
	}

	public void setHardCount(String hardCount) {
		this.hardCount = hardCount;
	}

	public String getZujuanStatus() {
		return this.zujuanStatus;
	}

	public void setZujuanStatus(String zujuanStatus) {
		this.zujuanStatus = zujuanStatus;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}













}
