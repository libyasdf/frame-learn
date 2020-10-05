package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 外卖详情实体类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月6日 下午5:51:13
 */
@Entity
@Table(name = "WMGL_TAKE_OUT_DETAIL")
public class TakeOutDetail {
	
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
	
	/** 逻辑删除*/
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
	
	/** 门类id */
	@Column(name = "CLASS_ID")
	private String classId;
	
	/** 外卖id  */
	@Column(name = "TAKE_OUT_ID")
	private String takeOutId;
	
	/** 商品id  */
	@Column(name = "GOODS_ID")
	private String goodsId;
	
	/** 商品名 */
	@Column(name = "GOODS_NAME")
	private String goodsName;
	
	/** 商品单价 */
	@Column(name = "GOODS_PRICE")
	private Double goodsPrice;
	
	/** 购买单位 */
	@Column(name = "BUY_UNIT")
	private String buyUnit;
	
	/** 计价单位 */
	@Column(name = "VALUATION_UNIT")
	private String valuationUnit;
	
	/** 商品顺序号 */
	@Column(name = "sort")
	private Integer sort;
	
	/** 门类的顺序号 */
	@Column(name = "CATEGORY_SORT")
	private Integer categorySort;
	
	
	/**商品数量  */
	@Column(name = "GOODS_NUM")
	private Integer goodsNum;
		
	/** 门类名称 */
	@Column(name = "CLASS_NAME")
	private String className;
	
	/** 是否有数量限制，0：没有，1：有 */
	@Column(name = "amount_limit")
	private  String amountLimit;
	
	/** 是否有购买限制，0：没有，1：有 */
	@Column(name = "buy_limit")
	private  String buyLimit;
	
	/** 最大购买数量 */
	@Column(name = "buy_num")
	private  Integer buyNum;
	
	@Transient
	private String cz = "";
	
	/**是否被组合进菜单,0表示为被组合进去;1.表示被组合进去  */
	@Transient
	private String combination;
	
	
	public String getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(String buyLimit) {
		this.buyLimit = buyLimit;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getCategorySort() {
		return categorySort;
	}

	public void setCategorySort(Integer categorySort) {
		this.categorySort = categorySort;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	
	public String getCz() {
		return cz;
	}
	
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getTakeOutId() {
		return takeOutId;
	}

	public void setTakeOutId(String takeOutId) {
		this.takeOutId = takeOutId;
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

	

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getBuyUnit() {
		return buyUnit;
	}

	public void setBuyUnit(String buyUnit) {
		this.buyUnit = buyUnit;
	}

	public String getValuationUnit() {
		return valuationUnit;
	}

	public void setValuationUnit(String valuationUnit) {
		this.valuationUnit = valuationUnit;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	
	
	

}
