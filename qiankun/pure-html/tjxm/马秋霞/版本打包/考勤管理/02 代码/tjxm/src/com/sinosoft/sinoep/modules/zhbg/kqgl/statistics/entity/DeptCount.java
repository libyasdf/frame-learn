package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity;

public class DeptCount {
	private String deptid;
	private String deptName;
	private String avgSickCnt;//人均病假总次数
	private String avgCasualCnt;//人均事假总次数
	private String avgCasualDay;//人均事假总天数
	private String avgSickDay;  //病假总天数
	private String avgLateNum;  //人均迟到次数
	private String avgEarlyLeaveNum;  //人均早退次数
	private String avgNoComeGoCnt;//人均无出入记录
	private String avgBusinessCnt;//人均出差次数
	private String avgOverTimed;//人均加班天数
	private String avgOverTimeh;//人均加班小时数
	private int peopleNum;//人数
	private StringBuilder userids = new StringBuilder();//存放的是用户id
	private StringBuilder chidDeptid = new StringBuilder();//存放的是子部门的 部门id
	private StringBuilder cardNum = new StringBuilder();//存放的是子部门人员的卡号
	private double sickCnt;//病假总次数
	private double casualCnt;//事假总次数
	private double casualDays;//事假总天数
	private double sickDays;  //总天数
	private double lateNum;  //迟到次数
	private double earlyLeaveNum;  //早退次数
	private double noComeGoCnt;//无出入记录
	private double businessCnt;//出差次数
	private double overTimed;//加班天数
	private double overTimeh;//加班小时数
	public StringBuilder getCardNum() {
		return cardNum;
	}
	public void setCardNum(StringBuilder cardNum) {
		this.cardNum = cardNum;
	}
	public double getSickCnt() {
		return sickCnt;
	}
	public void setSickCnt(double sickCnt) {
		this.sickCnt = sickCnt;
	}
	public double getCasualCnt() {
		return casualCnt;
	}
	public void setCasualCnt(double casualCnt) {
		this.casualCnt = casualCnt;
	}
	public double getCasualDays() {
		return casualDays;
	}
	public void setCasualDays(double casualDays) {
		this.casualDays = casualDays;
	}
	public double getSickDays() {
		return sickDays;
	}
	public void setSickDays(double sickDays) {
		this.sickDays = sickDays;
	}
	public double getLateNum() {
		return lateNum;
	}
	public void setLateNum(double lateNum) {
		this.lateNum = lateNum;
	}
	public double getEarlyLeaveNum() {
		return earlyLeaveNum;
	}
	public void setEarlyLeaveNum(double earlyLeaveNum) {
		this.earlyLeaveNum = earlyLeaveNum;
	}
	public double getNoComeGoCnt() {
		return noComeGoCnt;
	}
	public void setNoComeGoCnt(double noComeGoCnt) {
		this.noComeGoCnt = noComeGoCnt;
	}
	public double getBusinessCnt() {
		return businessCnt;
	}
	public void setBusinessCnt(double businessCnt) {
		this.businessCnt = businessCnt;
	}
	public double getOverTimed() {
		return overTimed;
	}
	public void setOverTimed(double overTimed) {
		this.overTimed = overTimed;
	}
	public double getOverTimeh() {
		return overTimeh;
	}
	public void setOverTimeh(double overTimeh) {
		this.overTimeh = overTimeh;
	}
	
	public StringBuilder getChidDeptid() {
		return chidDeptid;
	}
	public void setChidDeptid(StringBuilder chidDeptid) {
		this.chidDeptid = chidDeptid;
	}
	public StringBuilder getUserids() {
		return userids;
	}
	public void setUserids(StringBuilder userids) {
		this.userids = userids;
	}
	
	
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getAvgSickCnt() {
		return avgSickCnt;
	}
	public void setAvgSickCnt(String avgSickCnt) {
		this.avgSickCnt = avgSickCnt;
	}
	public String getAvgCasualCnt() {
		return avgCasualCnt;
	}
	public void setAvgCasualCnt(String avgCasualCnt) {
		this.avgCasualCnt = avgCasualCnt;
	}
	public String getAvgCasualDay() {
		return avgCasualDay;
	}
	public void setAvgCasualDay(String avgCasualDay) {
		this.avgCasualDay = avgCasualDay;
	}
	public String getAvgSickDay() {
		return avgSickDay;
	}
	public void setAvgSickDay(String avgSickDay) {
		this.avgSickDay = avgSickDay;
	}
	public String getAvgLateNum() {
		return avgLateNum;
	}
	public void setAvgLateNum(String avgLateNum) {
		this.avgLateNum = avgLateNum;
	}
	public String getAvgEarlyLeaveNum() {
		return avgEarlyLeaveNum;
	}
	public void setAvgEarlyLeaveNum(String avgEarlyLeaveNum) {
		this.avgEarlyLeaveNum = avgEarlyLeaveNum;
	}
	public String getAvgNoComeGoCnt() {
		return avgNoComeGoCnt;
	}
	public void setAvgNoComeGoCnt(String avgNoComeGoCnt) {
		this.avgNoComeGoCnt = avgNoComeGoCnt;
	}
	public String getAvgBusinessCnt() {
		return avgBusinessCnt;
	}
	public void setAvgBusinessCnt(String avgBusinessCnt) {
		this.avgBusinessCnt = avgBusinessCnt;
	}
	public String getAvgOverTimed() {
		return avgOverTimed;
	}
	public void setAvgOverTimed(String avgOverTimed) {
		this.avgOverTimed = avgOverTimed;
	}
	public String getAvgOverTimeh() {
		return avgOverTimeh;
	}
	public void setAvgOverTimeh(String avgOverTimeh) {
		this.avgOverTimeh = avgOverTimeh;
	}

	
}
