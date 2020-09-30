package com.sinosoft.sinoep.modules.video.background.entity;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "history_record", schema = "TJXM")
public class HistoryRecord {
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	/*用户id*/
	@Column(name = "user_id")
    private String userId;
	/*用户名*/
	@Column(name = "user_name")
    private String userName;
	/*部门id*/
	@Column(name = "dept_id")
    private String deptId;
	/*部门名称*/
	@Column(name = "dept_name")
    private String deptName;
	/*处室id*/
	@Column(name = "chushi_id")
    private String chushiId;
	/*处室名*/
	@Column(name = "chushi_name")
    private String chushiName;
	/*二级局id*/
	@Column(name = "ju_id")
    private String juId;
	/*二级局名*/
	@Column(name = "ju_name")
    private String juName;
	/*是否删除*/
	@Column(name = "visible")
    private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
    private String creTime;
	/*内容id*/
	@Column(name = "content_id")
    private String contentId;
	/*视频id*/
	@Column(name = "video_id")
    private String videoId;
	/*剩余秒数（暂时不用）*/
	@Column(name = "surplus_time")
    private Long surplusTime;
	/*剩余分钟数(暂时不用)*/
	@Column(name = "surplus_minute")
    private String surplusMinute;
	/*是否看完，0表示未看完，1表示已看完*/
	@Column(name = "state")
    private String state;
	/*内容标题*/
	@Transient
	private String contentTitle;
	/*视频的文件名*/
	@Transient
	private String fileName;
	/*视频的uuid*/
	@Transient
	private String uuid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getChushiId() {
		return chushiId;
	}
	public void setChushiId(String chushiId) {
		this.chushiId = chushiId;
	}
	public String getChushiName() {
		return chushiName;
	}
	public void setChushiName(String chushiName) {
		this.chushiName = chushiName;
	}
	public String getJuId() {
		return juId;
	}
	public void setJuId(String juId) {
		this.juId = juId;
	}
	public String getJuName() {
		return juName;
	}
	public void setJuName(String juName) {
		this.juName = juName;
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
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public Long getSurplusTime() {
		return surplusTime;
	}
	public void setSurplusTime(Long surplusTime) {
		this.surplusTime = surplusTime;
	}
	public String getSurplusMinute() {
		return surplusMinute;
	}
	public void setSurplusMinute(String surplusMinute) {
		this.surplusMinute = surplusMinute;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
}
