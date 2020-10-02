package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 商品表
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月6日 下午5:51:13
 */
@Entity
@Table(name = "Wmgl_Goods")
public class Goods {
	
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
	
	/** 所属门类*/
	@Column(name = "BELONG_ID")
	private String belongId;
	
	/** 商品名称 */
	@Column(name = "GOODS_NAME")
	private String goodsName;
	
	/** 单价 */
	@Column(name = "PRICE")
	private Double price;
	
	/** 购买单位 */
	@Column(name = "buy_unit")
	private String buyUnit;
	
	/** 是否可用 */
	@Column(name = "is_use")
	private String isUse;
	
	/** 图片id */
	@Column(name = "image_id")
	private String imageId;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/**计价单位 */
	@Column(name = "VALUATION_UNIT")
	private String valuationUnit;
	
	/**排序 */
	@Column(name = "ORDER_NO")
	private Integer orderNo;
	
	/** 备注 */
	@Column(name = "mark")
	private  String mark;
	
	/** 描述 */
	@Column(name = "describe")
	private  String describe;
	
	/** 是否有数量限制，0：没有，1：有 */
	@Column(name = "amount_limit")
	private  String amountLimit;
		
	/** 限制发布商品的数量 */
	@Column(name = "amount_num")
	private  String amountNum;
		
	/** 是否有购买限制，0：没有，1：有 */
	@Column(name = "buy_limit")
	private  String buyLimit;
	
	@Transient
	private  String buyLimitLabel;
	
	@Transient
	private  String amountLimitLable;
	
	public String getAmountLimitLable() {
		if("0".equals(amountLimit))
			return "无";
		else return "有";
	}

	public void setAmountLimitLable(String amountLimitLable) {
		this.amountLimitLable = amountLimitLable;
	}


	/** 购买数量 */
	@Column(name = "buy_num")
	private  Integer buyNum;
	
	@Transient
	private String cz = "";
	
	/**门类名称 */
	@Transient
	private String categoryName;
	
	/**总数，在首页商品展示时使用,购物车中的数量 */
	@Transient
	private Integer goodsNum = 0;
	
	/**总数，在首页商品展示时使用,购物车中的数量 */
	@Transient
	private Integer repertory = 0;
	
	/**图片路径*/
	@Transient
	private String imgPath;
	
	/**套餐名称*/
	@Transient
	private String mealName;
	
	/**套餐id*/
	@Transient
	private String mealId;
	
	/**套餐中的商品ids*/
	@Transient
	private String mealGoodsIds;

	/**套餐中的商品数目*/
	@Transient
	private Integer mealGoodsNum;
	
	/**套餐中可选商品数目*/
	@Transient
	private Integer mealOptNum;
	
	/**套餐的规则 */
	@Transient
	private String mealRule;
	
	
	
	public String getBuyLimitLabel() {
		if("0".equals(buyLimit)) {
			return "无";
		}else {
			return "有";
		}
	}

	public void setBuyLimitLabel(String buyLimitLabel) {
		this.buyLimitLabel = buyLimitLabel;
	}

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

	public String getMealGoodsIds() {
		return mealGoodsIds;
	}


	public void setMealGoodsIds(String mealGoodsIds) {
		this.mealGoodsIds = mealGoodsIds;
	}


	public String getMealRule() {
		
		return mealGoodsNum + "选" + mealOptNum;
	}


	public void setMealRule(String mealRule) {
		this.mealRule = mealRule;
	}


	public String getMealName() {
		return mealName;
	}


	public void setMealName(String mealName) {
		this.mealName = mealName;
	}


	public String getMealId() {
		return mealId;
	}


	public void setMealId(String mealId) {
		this.mealId = mealId;
	}


	public Integer getMealGoodsNum() {
		return mealGoodsNum;
	}


	public void setMealGoodsNum(Integer mealGoodsNum) {
		this.mealGoodsNum = mealGoodsNum;
	}


	public Integer getMealOptNum() {
		return mealOptNum;
	}


	public void setMealOptNum(Integer mealOptNum) {
		this.mealOptNum = mealOptNum;
	}


	public Integer getRepertory() {
		return repertory;
	}


	public void setRepertory(Integer repertory) {
		this.repertory = repertory;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}


	public Integer getOrderNo() {
		return orderNo;
	}
	
	public Integer getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}


		


	public String getStatus() {
		return status;
	}

	

	public String getValuationUnit() {
		return valuationUnit;
	}

	public void setValuationUnit(String valuationUnit) {
		this.valuationUnit = valuationUnit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBelongId() {
		return belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}



	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getBuyUnit() {
		return buyUnit;
	}

	public void setBuyUnit(String buyUnit) {
		this.buyUnit = buyUnit;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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

	
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}


	public String getAmountLimit() {
		return amountLimit;
	}


	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}


	public String getAmountNum() {
		return amountNum;
	}


	public void setAmountNum(String amountNum) {
		this.amountNum = amountNum;
	}

	
	
	

}
