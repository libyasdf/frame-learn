package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity;

/**
 * 会议室预约情况表
 * TODO 
 * @author 冯超
 * @Date 2018年8月24日 上午8:55:38
 */
public class MeetingApplyDetailInfo {
	
	/** 会议室ID*/
	private String meetingroomId;
	
	/** 会议室名称*/
	private String meetingroomName;
	
	/** 会议室地点*/
	private String meetingPlace;
	
	/** 时间*/
	private String meetingDate;
	
	/** 上下午 0：上午  1 下午*/
	private String meetingTime;
	
	/** 会议室周一  0可用   1 已预约  2已占用*/
	private String detailOne;
	
	/** 会议室周二  0可用   1 已预约  2已占用*/
	private String detailTwo;
	
	/** 会议室周三  0可用   1 已预约  2已占用*/
	private String detailThree;
	
	/** 会议室周一四  0可用   1 已预约  2已占用*/
	private String detailFour;
	
	/** 会议室周五  0可用   1 已预约  2已占用*/
	private String detailFive;
	
	/** 会议室周六  0可用   1 已预约  2已占用*/
	private String detailSix;
	
	/** 会议室周日  0可用   1 已预约  2已占用*/
	private String detailSeven;

	public String getMeetingroomId() {
		return meetingroomId;
	}

	public void setMeetingroomId(String meetingroomId) {
		this.meetingroomId = meetingroomId;
	}

	public String getMeetingroomName() {
		return meetingroomName;
	}

	public void setMeetingroomName(String meetingroomName) {
		this.meetingroomName = meetingroomName;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getDetailOne() {
		return detailOne;
	}

	public void setDetailOne(String detailOne) {
		this.detailOne = detailOne;
	}

	public String getDetailTwo() {
		return detailTwo;
	}

	public void setDetailTwo(String detailTwo) {
		this.detailTwo = detailTwo;
	}

	public String getDetailThree() {
		return detailThree;
	}

	public void setDetailThree(String detailThree) {
		this.detailThree = detailThree;
	}

	public String getDetailFour() {
		return detailFour;
	}

	public void setDetailFour(String detailFour) {
		this.detailFour = detailFour;
	}

	public String getDetailFive() {
		return detailFive;
	}

	public void setDetailFive(String detailFive) {
		this.detailFive = detailFive;
	}

	public String getDetailSix() {
		return detailSix;
	}

	public void setDetailSix(String detailSix) {
		this.detailSix = detailSix;
	}

	public String getDetailSeven() {
		return detailSeven;
	}

	public void setDetailSeven(String detailSeven) {
		this.detailSeven = detailSeven;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

}
