package com.sinosoft.sinoep.modules.video.background.entity;

/**
 * 轮播图封装类
 * TODO 
 * @author 李利广
 * @Date 2018年9月26日 上午10:14:28
 */
public class BannerImgVo {
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 副标题
	 */
	private String subTitle;
	
	/**
	 * 业务ID
	 */
	private String infoId;
	
	/**
	 * 图片路径
	 */
	private String imgPath;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
