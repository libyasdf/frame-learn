package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity;

import org.apache.commons.lang.StringUtils;

public class PersonalStats {
	private int sequenceNum;//序号
	private String id;//用户id
	private String deptid;
	private String deptName;
	private String name;  //用户姓名
	private String cardNum; //考勤卡号
	private String wccnt="-"; //外出次数
	private String bqcnt="-"; //补签次数
	private String cccnt="0"; //出差次数
	private String qjcnt="-";//请假次数
	private String bjcnt="-";//病假总次数
	private String sjcnt="-";//事假总次数
	private String totalOverTime="-"; //总的加班次数
	private String holidayOverCnt="-";//节假日加班次数
	private String workOverCnt="-";//工作日加班次数
	private String comeGoCnt="-";//出入记录次数
	private String noComeGoCnt="-";//无出入记录次数
	private int noComeGoCnt1;
	private String overTimed="-"; //加班零散天累计天数
	private String overTimeh="-"; //加班零散小时累计小时数
	private int lateNum;    //迟到次数
	private String lateNum1="-";    //迟到次数
	private int earlyLeaveNum; //早退次数
	private String earlyLeaveNum1="-";
	private int absentNum; //旷工次数
	private double sickDays;//病假天数
	private double casualDays;//事假天数
	private Integer order;//人员的顺序
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public double getSickDays() {
		return sickDays;
	}

	public void setSickDays(double sickDays) {
		this.sickDays = sickDays;
	}

	public double getCasualDays() {
		return casualDays;
	}

	public void setCasualDays(double casualDays) {
		this.casualDays = casualDays;
	}

	public String getNoComeGoCnt() {
		return noComeGoCnt;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getId() {
		return id;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNoComeGoCnt1() {
		return noComeGoCnt1;
	}

	public void setNoComeGoCnt1(int noComeGoCnt1) {
		this.noComeGoCnt1 = noComeGoCnt1;
	}

	public String getEarlyLeaveNum1() {
		if(StringUtils.isNotBlank(earlyLeaveNum1))
			return earlyLeaveNum1;
		else 
			return "-";
	}

	public void setEarlyLeaveNum1(String earlyLeaveNum1) {
		this.earlyLeaveNum1 = earlyLeaveNum1;
	}

	public String getLateNum1() {
		if(StringUtils.isNotBlank(lateNum1)){
			return lateNum1;
		}else{
			return "-";
		}
		
	}

	public void setLateNum1(String lateNum1) {
		this.lateNum1 = lateNum1;
	}

	public void setNoComeGoCnt(String noComeGoCnt) {
		this.noComeGoCnt = noComeGoCnt;
	}
	public String getBjcnt() {
		return bjcnt;
	}
	public void setBjcnt(String bjcnt) {
		this.bjcnt = bjcnt;
	}
	public String getSjcnt() {
		return sjcnt;
	}
	public void setSjcnt(String sjcnt) {
		this.sjcnt = sjcnt;
	}
	public String getHolidayOverCnt() {
		return holidayOverCnt;
	}
	public void setHolidayOverCnt(String holidayOverCnt) {
		this.holidayOverCnt = holidayOverCnt;
	}
	public String getWorkOverCnt() {
		return workOverCnt;
	}
	public void setWorkOverCnt(String workOverCnt) {
		this.workOverCnt = workOverCnt;
	}
	public String getComeGoCnt() {
		return comeGoCnt;
	}
	public void setComeGoCnt(String comeGoCnt) {
		this.comeGoCnt = comeGoCnt;
	}
	
	public String getQjcnt() {
		return qjcnt;
	}
	public void setQjcnt(String qjcnt) {
		this.qjcnt = qjcnt;
	}
	public String getTotalOverTime() {
		return totalOverTime;
	}
	public void setTotalOverTime(String totalOverTime) {
		this.totalOverTime = totalOverTime;
	}
	public int getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWccnt() {
		return wccnt;
	}
	public void setWccnt(String wccnt) {
		this.wccnt = wccnt;
	}
	public String getBqcnt() {
		return bqcnt;
	}
	public void setBqcnt(String bqcnt) {
		this.bqcnt = bqcnt;
	}
	public String getCccnt() {
		return cccnt;
	}
	public void setCccnt(String cccnt) {
		this.cccnt = cccnt;
	}
	public String getOverTimed() {
		return overTimed;
	}
	public void setOverTimed(String overTimed) {
		this.overTimed = overTimed;
	}
	public String getOverTimeh() {
		return overTimeh;
	}
	public void setOverTimeh(String overTimeh) {
		this.overTimeh = overTimeh;
	}
	public int getLateNum() {
		return lateNum;
	}
	public void setLateNum(int lateNum) {
		this.lateNum = lateNum;
	}
	public int getEarlyLeaveNum() {
		return earlyLeaveNum;
	}
	public void setEarlyLeaveNum(int earlyLeaveNum) {
		this.earlyLeaveNum = earlyLeaveNum;
	}
	public int getAbsentNum() {
		return absentNum;
	}
	public void setAbsentNum(int absentNum) {
		this.absentNum = absentNum;
	}
	
	
	
	
}
