package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 考试与试卷关联实体类
 * @author 李颖洁  
 * @date 2018年8月14日  下午8:24:08
 */
@Entity
@Table(name = "XXKH_Test_PAPER")
public class XxkhTestPaper {

	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", length = 50)
	private String id;
	
	/** 考试id */
	@Column(name="TEST_ID", length = 50)
	private String testId;
	
	/** 试卷id */
	@Column(name="PAPER_ID", length = 50)
	private String paperId;
	
	/** 创建人id */
	@Column(name="CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人姓名 */
	@Column(name="CRE_USER_NAME", length = 50)
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
	
	/** 试卷顺序：1、2、3、4... */
	@Column(name="SORT", length = 2)
	private String sort;

	/** 试卷名称   */ 
	@Transient
	private String name;
	
	/** 试卷总分  */ 
	@Transient
	private String fullScore;
	
	/** 操作（试卷列表中显示）   */ 
	@Transient
	private String cz;
	
	/** 试卷状态：0不可用；1：可用  */ 
	@Transient
	private String paperState;
	
	/** 试卷状态：0：不共享；1：共享  */ 
	@Transient
	private String paperShared;
	
	/** 考试类别  */ 
	@Transient
	private String type;
	
	public XxkhTestPaper() {
	}

	public XxkhTestPaper(String id, String testId, String paperId, String creUserId, String creUserName,
			String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId,
			String creJuName, String visible, String creTime, String sort) {
		this.id = id;
		this.testId = testId;
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
		this.sort = sort;
	}

	/**  封装试卷信息 */
	public XxkhTestPaper(String id, String testId, String paperId,String sort,String name,String fullScore,String paperState,String paperShared,String type) {
		this.id = id;
		this.testId = testId;
		this.paperId = paperId;
		this.sort = sort;
		this.name = name;
		this.fullScore = fullScore;
		this.paperState = paperState;
		this.paperShared = paperShared;
		this.type = type;
	}
	
	public String getFullScore() {
		return fullScore;
	}

	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getPaperState() {
		return paperState;
	}

	public void setPaperState(String paperState) {
		this.paperState = paperState;
	}

	public String getPaperShared() {
		return paperShared;
	}

	public void setPaperShared(String paperShared) {
		this.paperShared = paperShared;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
