package com.sinosoft.sinoep.modules.video.background.entity;

/**
 * 用于视频播放左边内容下的视频信息
 * TODO 
 * @author 马秋霞
 * @Date 2018年12月3日 下午3:49:43
 */
public class VideoInfo {
	/** 视频id */
	private String videoId;
	/** 内容标题 */
	private String series;
	/** 视频名称 */
	private String name;
	/** 视频序号，用于自动播放下一集 */
	private int number;
	/** 视频uuid */
	private String uuid;
	/** 状态(0表示未看过，1,表示正在看，2表示已看完) */
	private String state;
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
}
