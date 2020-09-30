package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity;

/**
 * 
 * TODO 会议反馈情况实体类
 * @author 张建明
 * @Date 2018年9月18日 下午8:03:06
 */

public class CountNoticeBack {
	
	private String totalCount;
	
	private String backedCount;
	
	private String unBackedCount;
	
	private String remarkCount;
	
	private String attendNum;
	
	private String shouldAttendNum;
	
	private String realAttendNum;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getBackedCount() {
		return backedCount;
	}

	public void setBackedCount(String backedCount) {
		this.backedCount = backedCount;
	}

	public String getUnBackedCount() {
		return unBackedCount;
	}

	public void setUnBackedCount(String unBackedCount) {
		this.unBackedCount = unBackedCount;
	}

	public String getRemarkCount() {
		return remarkCount;
	}

	public void setRemarkCount(String remarkCount) {
		this.remarkCount = remarkCount;
	}

	public CountNoticeBack() {
		super();
		
	}
	public CountNoticeBack(String totalCount, String backedCount, String unBackedCount, String remarkCount) {
		super();
		this.totalCount = totalCount;
		this.backedCount = backedCount;
		this.unBackedCount = unBackedCount;
		this.remarkCount = remarkCount;
	}
	
	

	/**反馈参会人数和的构造器
	 * @param attendNum
	 * @param shouldAttendNum
	 * @param realAttendNum
	 */
	public CountNoticeBack(String attendNum, String shouldAttendNum, String realAttendNum) {
		super();
		this.attendNum = attendNum;
		this.shouldAttendNum = shouldAttendNum;
		this.realAttendNum = realAttendNum;
	}

	public String getAttendNum() {
		return attendNum;
	}

	public void setAttendNum(String attendNum) {
		this.attendNum = attendNum;
	}

	public String getShouldAttendNum() {
		return shouldAttendNum;
	}

	public void setShouldAttendNum(String shouldAttendNum) {
		this.shouldAttendNum = shouldAttendNum;
	}

	public String getRealAttendNum() {
		return realAttendNum;
	}

	public void setRealAttendNum(String realAttendNum) {
		this.realAttendNum = realAttendNum;
	}
	
	
	
}
