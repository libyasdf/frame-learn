package com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 订单详情表
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月14日 下午6:13:45
 */
@Entity
@Table(name = "WMGL_ORDER_DETIL")
public class OrderDetil {
	
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
	
	/** 订单id */
	@Column(name = "ORDER_ID")
	private String orderId;
	
	/**门类id,暂时不用 */
	@Column(name = "category_id")
	private String categoryId;
	
	/**门类名称 ,暂时不用*/
	@Column(name = "category_name")
	private String categoryName;
		
	
	/** 商品id */
	@Column(name = "GOODS_ID")
	private String goodsId;
	
	/** 商品名*/
	@Column(name = "GOODS_NAME")
	private String goodsName;
	
	/** 计价单位 */
	@Column(name = "VALUATION_UNIT")
	private String valuationUnit;
	
	/** 购买单位 */
	@Column(name = "BUY_UNIT")
	private String bugUnit;
	
	/** 商品单价 */
	@Column(name = "GOODS_PRICE")
	private Double goodsPrice;
	
	/** 商品数量 */
	@Column(name = "GOODS_NUM")
	private Integer goodsNum;
	
	/** 商品总价,暂时不用 */
	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;
	
	/** 门类的排序号*/
	@Column(name = "CATEGORY_SORT")
	private Double categorySort;
	
	/**  商品数量,导出时使用*/
	@Transient
	private String goodsNumLable;

	
	public String getGoodsNumLable() {
		return goodsNum + "" + bugUnit;
	}

	public void setGoodsNumLable(String goodsNumLable) {
		this.goodsNumLable = goodsNumLable;
	}

	public Double getCategorySort() {
		return categorySort;
	}

	public void setCategorySort(Double categorySort) {
		this.categorySort = categorySort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getValuationUnit() {
		return valuationUnit;
	}

	public void setValuationUnit(String valuationUnit) {
		this.valuationUnit = valuationUnit;
	}

	public String getBugUnit() {
		return bugUnit;
	}

	public void setBugUnit(String bugUnit) {
		this.bugUnit = bugUnit;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}


	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	
	
	
}
