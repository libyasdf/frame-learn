package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 外卖管理-失信人员实体类
 * @author 李颖洁  
 * @date 2019年5月9日  下午3:26:21
 */
@Entity
@Table(name = "WMGL_LOSECREDIT")
public class WmgLosecredit {

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
	
	/** 失信人id*/
	@Column(name = "lose_user_id")
	private String loseUserId;
	
	/** 失信人姓名*/
	@Column(name = "lose_user_name")
	private String loseUserName;
	
	/** 失信人单位id*/
	@Column(name = "lose_user_dept_id")
	private String loseUserDeptId;
	
	/** 失信人单位名称*/
	@Column(name = "lose_user_dept_name")
	private String loseUserDeptName;
	
	/** 失信人处室id*/
	@Column(name = "lose_chushi_id")
	private String loseChushiId;
	
	/** 失信人处室名称*/
	@Column(name = "lose_chushi_name")
	private String loseChushiName;
	
	/** 失信人局id*/
	@Column(name = "lose_ju_id")
	private String loseJuId;
	
	/** 失信人局名称*/
	@Column(name = "lose_ju_name")
	private String loseJuName;
	
	/** 被锁定时间*/
	@Column(name = "lose_time")
	private String loseTime;
	
	/** 解除时间*/
	@Column(name = "relieve_time")
	private String relieveTime;
	
	/** 0表示自动解除；1表示是手动解除*/
	@Column(name = "relieve_type")
	private String relieveType;
	
	/** 门禁卡号 */
	@Column(name = "CARD_NUM")
	private String cardNum;
	
	/** 外卖id,是因为哪几次外卖的订单，而被锁定 */
	@Column(name = "TAKE_OUT_IDS")
	private String takeOutIds;
	
	/** 锁定状态：0:锁定，1：解除*/
	@Transient
	private String state;
	
	public WmgLosecredit() {
		super();
	}
	
	public WmgLosecredit(String loseUserId,String loseUserName, String loseUserDeptId, String loseUserDeptName, 
			String loseChushiId,String loseChushiName, String loseJuId, String loseJuName, String loseTime, String relieveTime) {
		super();
		this.creUserId = UserUtil.getCruUserId();
		this.creUserName = UserUtil.getCruUserName();
		this.creDeptId = UserUtil.getCruDeptId();
		this.creDeptName = UserUtil.getCruDeptName();
		this.creChushiId = UserUtil.getCruChushiId();
		this.creChushiName = UserUtil.getCruChushiName();
		this.creJuId = UserUtil.getCruJuId();
		this.creJuName = UserUtil.getCruJuName();
		this.visible = CommonConstants.VISIBLE[1];
		this.creTime = DateUtil.COMMON_FULL.getDateText(new Date());
		this.loseUserId = loseUserId;
		this.loseUserName = loseUserName;
		this.loseUserDeptId = loseUserDeptId;
		this.loseUserDeptName = loseUserDeptName;
		this.loseChushiId = loseChushiId;
		this.loseChushiName = loseChushiName;
		this.loseJuId = loseJuId;
		this.loseJuName = loseJuName;
		this.loseTime = loseTime;
		this.relieveTime = relieveTime;
		this.relieveType = WmglConstants.RELIEVE_TYPE[0];
	}
		
	public String getTakeOutIds() {
		return takeOutIds;
	}

	public void setTakeOutIds(String takeOutIds) {
		this.takeOutIds = takeOutIds;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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

	public String getLoseUserId() {
		return loseUserId;
	}

	public void setLoseUserId(String loseUserId) {
		this.loseUserId = loseUserId;
	}

	public String getLoseUserName() {
		return loseUserName;
	}

	public void setLoseUserName(String loseUserName) {
		this.loseUserName = loseUserName;
	}

	public String getLoseUserDeptId() {
		return loseUserDeptId;
	}

	public void setLoseUserDeptId(String loseUserDeptId) {
		this.loseUserDeptId = loseUserDeptId;
	}

	public String getLoseUserDeptName() {
		return loseUserDeptName;
	}

	public void setLoseUserDeptName(String loseUserDeptName) {
		this.loseUserDeptName = loseUserDeptName;
	}

	public String getLoseChushiId() {
		return loseChushiId;
	}

	public void setLoseChushiId(String loseChushiId) {
		this.loseChushiId = loseChushiId;
	}

	public String getLoseChushiName() {
		return loseChushiName;
	}

	public void setLoseChushiName(String loseChushiName) {
		this.loseChushiName = loseChushiName;
	}

	public String getLoseJuId() {
		return loseJuId;
	}

	public void setLoseJuId(String loseJuId) {
		this.loseJuId = loseJuId;
	}

	public String getLoseJuName() {
		return loseJuName;
	}

	public void setLoseJuName(String loseJuName) {
		this.loseJuName = loseJuName;
	}

	public String getLoseTime() {
		return loseTime;
	}

	public void setLoseTime(String loseTime) {
		this.loseTime = loseTime;
	}

	public String getRelieveTime() {
		return relieveTime;
	}

	public void setRelieveTime(String relieveTime) {
		this.relieveTime = relieveTime;
	}

	public String getRelieveType() {
		return relieveType;
	}

	public void setRelieveType(String relieveType) {
		this.relieveType = relieveType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}