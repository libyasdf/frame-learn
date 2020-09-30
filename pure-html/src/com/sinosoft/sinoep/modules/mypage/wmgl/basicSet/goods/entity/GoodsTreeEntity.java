package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity;

import java.util.List;

public class GoodsTreeEntity {
    

	private String id;
    private String pId;
    private String name;//商品名称
    private Double goodsPrice;//商品单价
    private String valuationUnit;//计价单位
    private String buyUnit;//购买单价
    private String flg;//0表示是门类1，1表示是商品
    private Integer categorySort;//门类的顺序
    private String amountLimit; //商品是否有数量限制
    private Integer goodsNum; //商品是否有数量限制
    private String buyLimit; //商品是否有购买的数量限制
    private Integer buyNum; //最多购买数量
        
    public Integer getCategorySort() {
		return categorySort;
	}

	public void setCategorySort(Integer categorySort) {
		this.categorySort = categorySort;
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

	public GoodsTreeEntity() {
		
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

	public GoodsTreeEntity(String id, String pId, String name, String orderNo, String nodeLevel) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    
	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getValuationUnit() {
		return valuationUnit;
	}

	public void setValuationUnit(String valuationUnit) {
		this.valuationUnit = valuationUnit;
	}

	public String getBuyUnit() {
		return buyUnit;
	}

	public void setBuyUnit(String buyUnit) {
		this.buyUnit = buyUnit;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    

}
