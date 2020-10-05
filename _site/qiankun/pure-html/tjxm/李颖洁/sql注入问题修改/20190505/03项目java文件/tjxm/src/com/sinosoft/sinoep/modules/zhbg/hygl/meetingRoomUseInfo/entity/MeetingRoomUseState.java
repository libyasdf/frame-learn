package com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity;

/**
 * 会议室每天的使用状态
 * TODO 
 * @author 马秋霞
 * @Date 2019年3月5日 下午2:16:31
 */
public class MeetingRoomUseState {
	private String applyId;//会议申请id
	private String meetingRoomId;//会议室id
	private String meetingRoomName;//会议室名称
	private String content;//容纳人数
	private String equipment;//设备
	private String noneToTenState="0";//该会议室9点到10点的使用状态；0:表示为未被占用，1表示申请中；2表示被占用
	private String tenToElevenState="0";//该会议室10点到11点的使用状态
	private String elevenToTwelveState="0";//该会议室11点到12点的使用状态
	private String twelveToThirteenState="0";//该会议室12点到13点的使用状态
	private String thirteenToFourteenState="0";//该会议室13点到14点的使用状态
	private String fourteenToFifteenState="0";//该会议室14点到15点的使用状态
	private String fifteenToSixteenState="0";//该会议室15点到16点的使用状态
	private String sixteenToSeventeenState="0";//该会议室16点到17点的使用状态
	private String seventeenToEighteenState="0";//该会议室17点到18点的使用状态
	public String getMeetingRoomId() {
		return meetingRoomId;
	}
	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}
	public String getMeetingRoomName() {
		return meetingRoomName;
	}
	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getNoneToTenState() {
		return noneToTenState;
	}
	public void setNoneToTenState(String noneToTenState) {
		this.noneToTenState = noneToTenState;
	}
	public String getTenToElevenState() {
		return tenToElevenState;
	}
	public void setTenToElevenState(String tenToElevenState) {
		this.tenToElevenState = tenToElevenState;
	}
	public String getElevenToTwelveState() {
		return elevenToTwelveState;
	}
	public void setElevenToTwelveState(String elevenToTwelveState) {
		this.elevenToTwelveState = elevenToTwelveState;
	}
	public String getTwelveToThirteenState() {
		return twelveToThirteenState;
	}
	public void setTwelveToThirteenState(String twelveToThirteenState) {
		this.twelveToThirteenState = twelveToThirteenState;
	}
	public String getThirteenToFourteenState() {
		return thirteenToFourteenState;
	}
	public void setThirteenToFourteenState(String thirteenToFourteenState) {
		this.thirteenToFourteenState = thirteenToFourteenState;
	}
	public String getFourteenToFifteenState() {
		return fourteenToFifteenState;
	}
	public void setFourteenToFifteenState(String fourteenToFifteenState) {
		this.fourteenToFifteenState = fourteenToFifteenState;
	}
	public String getFifteenToSixteenState() {
		return fifteenToSixteenState;
	}
	public void setFifteenToSixteenState(String fifteenToSixteenState) {
		this.fifteenToSixteenState = fifteenToSixteenState;
	}
	public String getSixteenToSeventeenState() {
		return sixteenToSeventeenState;
	}
	public void setSixteenToSeventeenState(String sixteenToSeventeenState) {
		this.sixteenToSeventeenState = sixteenToSeventeenState;
	}
	public String getSeventeenToEighteenState() {
		return seventeenToEighteenState;
	}
	public void setSeventeenToEighteenState(String seventeenToEighteenState) {
		this.seventeenToEighteenState = seventeenToEighteenState;
	}
	
}
