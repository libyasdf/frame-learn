package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

import com.sinosoft.sinoep.common.util.DateUtil;

/**
 * 
 * @author 颜振兴
 * 时间：2018年7月26日
 *	XxkhLearnTime
 */
@Entity
@Table(name = "XXKH_LEARN_TIME")
public class XxkhLearnTime {
	
	/** 主键id */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人ID（也是申请人ID字段） */
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名称（也是申请人名称字段） */
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建时间 */
	@Column(name = "cre_time")
	private String creTime;
	
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
	
	/** 学习开始时间 */
	@Column(name = "start_time")
	private String startTime;
	
	/** 学习结束时间 */
	@Column(name = "OVER_TIME")
	private String overTime;
	
	/** 本次学习时长（小时） */
	@Column(name = "learn_time_h")
	private float learnTimeH;

	/** 本次学习日期：yyyy-mm-dd */
	@Column(name = "learn_date")
	private String learnDate;
	
	/** 资料id */
	@Column(name = "info_id")
	private String infoId;
	
	/** 资料名称 */
	@Column(name = "info_name")
	private String infoName;
	
	/** 操作 */
	@Transient
	private String cz;
	
	public XxkhLearnTime(float learnTimeH){
		this.learnTimeH=learnTimeH;
	}
	
	public XxkhLearnTime(String id, String creUserId, String creUserName, String creTime, String creDeptId,
			String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName,
			String visible, String startTime, String overTime, float learnTimeH, String learnDate, String infoId,
			String infoName) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creTime = creTime;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChushiId = creChushiId;
		this.creChushiName = creChushiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.visible = visible;
		this.startTime = startTime;
		this.overTime = overTime;
		this.learnTimeH = learnTimeH;
		this.learnDate = learnDate;
		this.infoId = infoId;
		this.infoName = infoName;
	}

	public void setLearnTimeH(float learnTimeH) {
		this.learnTimeH = learnTimeH;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public float getLearnTimeH() {
		return learnTimeH;
	}

	public void setLearnTimeH() {
		DecimalFormat df=new DecimalFormat("0.00");
		long nh = 1000 * 60 * 60;
		try {
			long over = DateUtil.COMMON_FULL.getTextDate(this.overTime).getTime();
			long start = DateUtil.COMMON_FULL.getTextDate(this.startTime).getTime();
			long diff = over - start;
			this.learnTimeH =Float.parseFloat(df.format((float)diff/nh));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public XxkhLearnTime() {
		super();
	}

	public XxkhLearnTime(String id) {
		this.id = id;
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
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getOverTime() {
		return this.overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getCz() {
		return cz;
	}


	public void setCz(String cz) {
		this.cz = cz;
	}
	
	public String getLearnDate() {
		return learnDate;
	}

	public void setLearnDate(String learnDate) {
		this.learnDate = learnDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	
}
