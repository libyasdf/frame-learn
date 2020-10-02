package com.sinosoft.sinoep.modules.info.authority.entity;

import com.sinosoft.sinoep.modules.system.notice.entity.InfoFBUserGroup;

import java.util.List;

/**
 * 权限实体封装类
 * TODO 
 * @author 李利广
 * @Date 2018年9月16日 上午10:11:42
 */
public class InfoModel {

	private String contentId;

	private List<InfoFbDept> deptList;

	private List<InfoFbGroup> groupList;
	
	private List<InfoFbZwqx> zwqxList;
	
	private List<InfoFbDeptZwqxList> deptZwqxList;
	
	private List<InfoFbDeptZwqx> deptZwqxListList;

	//通知人群
	private List<InfoFBUserGroup> userGroupList;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public List<InfoFbGroup> getGroupList(){
		return groupList;
	}

	public void setGroupList(List<InfoFbGroup> groupList){
		this.groupList = groupList;
	}

	public List<InfoFbDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<InfoFbDept> deptList) {
		this.deptList = deptList;
	}

	public List<InfoFbZwqx> getZwqxList() {
		return zwqxList;
	}

	public void setZwqxList(List<InfoFbZwqx> zwqxList) {
		this.zwqxList = zwqxList;
	}

	public List<InfoFbDeptZwqxList> getDeptZwqxList() {
		return deptZwqxList;
	}

	public void setDeptZwqxList(List<InfoFbDeptZwqxList> deptZwqxList) {
		this.deptZwqxList = deptZwqxList;
	}

	public List<InfoFbDeptZwqx> getDeptZwqxListList() {
		return deptZwqxListList;
	}

	public void setDeptZwqxListList(List<InfoFbDeptZwqx> deptZwqxListList) {
		this.deptZwqxListList = deptZwqxListList;
	}

	public List<InfoFBUserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<InfoFBUserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}
}
