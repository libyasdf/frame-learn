package com.sinosoft.sinoep.modules.system.notice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 通知通告反馈信息实体类
 * @author 李利广
 * @Date 2018年8月17日 下午4:13:08
 */
public class SysNoticeNoBack {

	private String noticeId;

	private String userId;

	private String userName;

	private String deptId;

	private String deptName;

	/** 未反馈处室NUM */
	private String noBackChuNum;

	private String noBackJuId;

	private String noBackJuName;

	private String noBackChuId;

	/** 反馈人处室name */
	private String noBackChuName;

	public String getNoticeId(){
		return this.noticeId;
	}

	public void setNoticeId(String noticeId){
		this.noticeId = noticeId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getDeptId(){
		return this.deptId;
	}

	public void setDeptId(String deptId){
		this.deptId = deptId;
	}

	public String getDeptName(){
		return this.deptName;
	}

	public void setDeptName(String deptName){
		this.deptName = deptName;
	}

	public String getNoBackChuNum() {
		return this.noBackChuNum;
	}

	public void setNoBackChuNum(String noBackChuNum) {
		this.noBackChuNum = noBackChuNum;
	}

	public String getNoBackJuId(){
		return this.noBackJuId;
	}

	public void setNoBackJuId(String noBackJuId){
		this.noBackJuId = noBackJuId;
	}

	public String getNoBackJuName(){
		return  this.noBackJuName;
	}

	public void setNoBackJuName(String noBackJuName){
		this.noBackJuName = noBackJuName;
	}

	public String getNoBackChuId() {
		return this.noBackChuId;
	}

	public void setNoBackChuId(String noBackChuId) {
		this.noBackChuId = noBackChuId;
	}

	public String getNoBackChuName() {
		return this.noBackChuName;
	}

	public void setNoBackChuName(String noBackChuName) {
		this.noBackChuName = noBackChuName;
	}

}