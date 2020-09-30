package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;

/**
 * TODO 外卖管理-基础配置实体类
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:01:53
 */
@Entity
@Table(name = "WMGL_CONFIG")
public class WmglConfig {

	/** 主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/** 创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/** 创建人处室ID*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	
	/** 创建人二级局ID*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/** 逻辑删除，0：删除，1：可用*/
	@Column(name = "visible")
	private String visible;
	
	/** 创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	
	/** 最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	
	/** 是否有效，0：无效，1：有效(此字段已不用)*/
	@Column(name = "isvalid")
	private String isvalid;
	
	/** 年度*/
	@Column(name = "period")
	private String period;
	
	/** 一个人在指定周期内，最多可失信多少次*/
	@Column(name = "lost_credit_limt")
	private Integer lostCreditLimt;
	
	/** 失信多少次后，用户被锁定多长时间，单位为月*/
	@Column(name = "lock_time")
	private Integer lockTime;
	
	/** 外卖的注意事项*/
	@Column(name = "attention_itme")
	private String attentionItme;

	/**
	 * 
	 */
	public WmglConfig() {
		super();
	}

	/**
	 * @param id
	 * @param creUserId
	 * @param creUserName
	 * @param creDeptId
	 * @param creDeptName
	 * @param creChushiId
	 * @param creChushiName
	 * @param creJuId
	 * @param creJuName
	 * @param visible
	 * @param creTime
	 * @param updateUserId
	 * @param updateUserName
	 * @param updateTime
	 * @param isvalid
	 * @param period
	 * @param lostCreditLimt
	 * @param lockTime
	 * @param attentionItme
	 */
	public WmglConfig(String period) {
		super();
		this.id = UUID.randomUUID().toString();
		this.creUserId = "";
		this.creUserName = "";
		this.creDeptId = "";
		this.creDeptName = "";
		this.creChushiId = "";
		this.creChushiName = "";
		this.creJuId = "";
		this.creJuName = "";
		this.visible = CommonConstants.VISIBLE[1];
		this.creTime = DateUtil.COMMON_FULL.getDateText(new Date());
		this.updateUserId = "";
		this.updateUserName = "";
		this.updateTime = "";
		this.isvalid = WmglConstants.VALID[1];
		this.period = period;
		this.lostCreditLimt = (int) WmglConstants.CONFIG_INIT_DATA[0];
		this.lockTime = (int) WmglConstants.CONFIG_INIT_DATA[1];
		this.attentionItme = (String) WmglConstants.CONFIG_INIT_DATA[2];
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

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getLockTime() {
		return lockTime;
	}

	public void setLockTime(Integer lockTime) {
		this.lockTime = lockTime;
	}

	public String getAttentionItme() {
		return attentionItme;
	}

	public void setAttentionItme(String attentionItme) {
		this.attentionItme = attentionItme;
	}

	public Integer getLostCreditLimt() {
		return lostCreditLimt;
	}

	public void setLostCreditLimt(Integer lostCreditLimt) {
		this.lostCreditLimt = lostCreditLimt;
	}

	
	
}