package com.sinosoft.sinoep.modules.video.background.entity;

/**
 * 个人门户视频发布提醒用到的数据封装
 * @author 王磊
 * @Date 2019年10月12日 上午11:04:18
 */
public class MenHuContentVo {
	
	/**
	 * 内容所述栏目名称
	 */
	private String columeName;
	
	/**
	 * 内容名称
	 */
	private String contentTitle;
	
	/**
	 * 内容id
	 */
	private String contentId;
	
	/**
	 * 内容发布时间
	 */
	private String time;
	
	/**
	 * 第一个视频在音视频服务器的uuid
	 */
	private String firstVideoUuid;
	
	/**
	 * 第一个视频在业务系统里的id
	 */
	private String firstVideoId;
	
	/**
	 * 第一个视频的文件名（带后缀）
	 */
	private String firstVideoName;

	private String isTop;
	
	/**
	 * @return the contentFbTime
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param contentFbTime the contentFbTime to set
	 */
	public void setTime(String contentFbTime) {
		this.time = contentFbTime;
	}

	/**
	 * @return the columeName
	 */
	public String getColumeName() {
		return columeName;
	}

	/**
	 * @param columeName the columeName to set
	 */
	public void setColumeName(String columeName) {
		this.columeName = columeName;
	}

	/**
	 * @return the contentTitle
	 */
	public String getContentTitle() {
		return contentTitle;
	}

	/**
	 * @param contentTitle the contentTitle to set
	 */
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the firstVideoUuid
	 */
	public String getFirstVideoUuid() {
		return firstVideoUuid;
	}

	/**
	 * @param firstVideoUuid the firstVideoUuid to set
	 */
	public void setFirstVideoUuid(String firstVideoUuid) {
		this.firstVideoUuid = firstVideoUuid;
	}

	/**
	 * @return the firstVideoId
	 */
	public String getFirstVideoId() {
		return firstVideoId;
	}

	/**
	 * @param firstVideoId the firstVideoId to set
	 */
	public void setFirstVideoId(String firstVideoId) {
		this.firstVideoId = firstVideoId;
	}

	/**
	 * @return the firstVideoName
	 */
	public String getFirstVideoName() {
		return firstVideoName;
	}

	/**
	 * @param firstVideoName the firstVideoName to set
	 */
	public void setFirstVideoName(String firstVideoName) {
		this.firstVideoName = firstVideoName;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
}
