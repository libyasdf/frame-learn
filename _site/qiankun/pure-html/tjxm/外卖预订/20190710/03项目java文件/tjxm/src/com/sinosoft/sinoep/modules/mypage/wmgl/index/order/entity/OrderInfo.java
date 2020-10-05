package com.sinosoft.sinoep.modules.mypage.wmgl.index.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 订单信息表
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月14日 下午6:13:45
 */
@Entity
@Table(name = "WMGL_ORDER_INFO")
public class OrderInfo {
	
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
	
	/**创建人二级局ID*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
		
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
	
	/** 外卖id */
	@Column(name = "TAKE_OUT_ID")
	private String takeOutId;
	
	/** 总价钱 */
	@Column(name = "TOTAL_PRICE")
	private Double totalPrice=0.0;
	
	/** 订单号 */
	@Column(name = "ORDER_NUM")
	private String orderNum;
	
	/** 是否被统计过失信 */
	@Column(name = "LOSE_CREDIT")
	private String loseCredit;
	
	/** 状态(1:已下单；2：已取消；3：已领取；4：未领取 */
	@Column(name = "STATUS")
	private String status;
	
	/** 卡号 */
	@Column(name = "CARD_NO")
	private String cardNo;
	
	/** 订单序号 */
	@Column(name = "ORDER_NO")
	private Integer orderNo; 
	
	/** 下单人联系方式 */
	@Column(name = "PHONE")
	private String phone; 
	
	@Column(name = "FULLDETPNAME")
	private String fullDeptName; 
	
	/** 删除 ：0：删除；1：可用*/
	@Column(name = "VISIBLE")
	private String visible; 
	
	/** 订单中的商品名称 */
	@Transient
	private String goodsName; 
	
	/** 订单所属外卖的最晚下单时间 */
	@Transient
	private String deadlineTime;
	
	/** 订单所属外卖主题 */
	@Transient
	private String title; 
		
	public String getFullDeptName() {
		return fullDeptName;
	}

	public void setFullDeptName(String fullDeptName) {
		this.fullDeptName = fullDeptName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getTakeOutId() {
		return takeOutId;
	}

	public void setTakeOutId(String takeOutId) {
		this.takeOutId = takeOutId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getLoseCredit() {
		return loseCredit;
	}

	public void setLoseCredit(String loseCredit) {
		this.loseCredit = loseCredit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
