package com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;

/**
 * 试题组与试题关联表
 * @author 颜振兴
 * 时间：2018年7月31日
 *	XxkhQuestionQgroup
 */
@Entity
@Table(name = "XXKH_QUESTION_QGROUP")
public class XxkhQuestionQgroup {
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 试题ID */
	@Column(name = "QUESTION_ID", length = 50)
	private String questionId;
	
	/** 试题组id */
	@Column(name = "QUESTION_GROUP_ID", length = 50)
	private String questionGroupId;

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
	

	

	
	/** 逻辑删除*/
	@Column(name = "VISIBLE", length = 50)
	private String visible;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="id")
	private List<XxkhQuestionGroup> groups = new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="id")
	private List<Question> questions  = new ArrayList<>();
	public XxkhQuestionQgroup() {
	}

	public XxkhQuestionQgroup(String id) {
		this.id = id;
	}

	public XxkhQuestionQgroup(String id, String questionId, String questionGroupId, String creUserId,
			String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName,
			String creJuId, String creJuName, String visible, String creTime) {
		this.id = id;
		this.questionId = questionId;
		this.questionGroupId = questionGroupId;
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
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionGroupId() {
		return this.questionGroupId;
	}

	public void setQuestionGroupId(String questionGroupId) {
		this.questionGroupId = questionGroupId;
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



}
