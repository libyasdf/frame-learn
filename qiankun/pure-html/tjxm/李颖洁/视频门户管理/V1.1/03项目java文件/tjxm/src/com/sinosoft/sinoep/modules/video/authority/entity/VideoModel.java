package com.sinosoft.sinoep.modules.video.authority.entity;

import java.util.List;

/**
 * 权限实体封装类
 * TODO 
 * @author 李利广
 * @Date 2018年9月16日 上午10:11:42
 */
public class VideoModel {

	private String contentId;

	private List<VideoFbDept> deptList;
	
	private List<VideoFbZwqx> zwqxList;
	
	private List<VideoFbGroup> groupList;
	
	private List<VideoFbDeptZwqxList> deptZwqxList;
	
	private List<VideoFbDeptZwqx> deptZwqxListList;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public List<VideoFbDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<VideoFbDept> deptList) {
		this.deptList = deptList;
	}

	public List<VideoFbZwqx> getZwqxList() {
		return zwqxList;
	}

	public void setZwqxList(List<VideoFbZwqx> zwqxList) {
		this.zwqxList = zwqxList;
	}

	public List<VideoFbDeptZwqxList> getDeptZwqxList() {
		return deptZwqxList;
	}

	public void setDeptZwqxList(List<VideoFbDeptZwqxList> deptZwqxList) {
		this.deptZwqxList = deptZwqxList;
	}

	public List<VideoFbDeptZwqx> getDeptZwqxListList() {
		return deptZwqxListList;
	}

	public void setDeptZwqxListList(List<VideoFbDeptZwqx> deptZwqxListList) {
		this.deptZwqxListList = deptZwqxListList;
	}

	public List<VideoFbGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<VideoFbGroup> groupList) {
		this.groupList = groupList;
	}
	
	

}
