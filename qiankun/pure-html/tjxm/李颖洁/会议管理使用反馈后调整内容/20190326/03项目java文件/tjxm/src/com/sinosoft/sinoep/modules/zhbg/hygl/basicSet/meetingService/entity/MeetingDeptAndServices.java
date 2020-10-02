package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity;

import java.util.List;


/**
 * 会务服务信息列表和会务服务单位
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:39:16
 */
public class MeetingDeptAndServices {
	
	/** 服务单位主键id*/
	private String pid;
	
	/** 服务单位主键name*/
	private String serviceDeptName;
	
	/** 服务单位id*/
	private String serviceDeptId;
	
	/** 服务项目*/
	private List<MeetingServiceInfo> meetingServiceList;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getServiceDeptName() {
		return serviceDeptName;
	}

	public void setServiceDeptName(String serviceDeptName) {
		this.serviceDeptName = serviceDeptName;
	}

	public String getServiceDeptId() {
		return serviceDeptId;
	}

	public void setServiceDeptId(String serviceDeptId) {
		this.serviceDeptId = serviceDeptId;
	}

	public List<MeetingServiceInfo> getMeetingServiceList() {
		return meetingServiceList;
	}

	public void setMeetingServiceList(List<MeetingServiceInfo> meetingServiceList) {
		this.meetingServiceList = meetingServiceList;
	}
	
	
	


}
