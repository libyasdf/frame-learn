package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity;

import javax.persistence.Column;

import com.sinosoft.sinoep.user.entity.SysFlowUserVo;

/**
 * 用于手动排座人员的展示
 * TODO 
 * @author 马秋霞
 * @Date 2018年10月18日 下午6:58:34
 */
public class UserSeat extends SysFlowUserVo{
	String id; //座位id
	/*行坐标*/
	private int row;
	/*纵坐标*/
	private int col;//列
	private int order;//人员顺序
	/** 参会部门*/
	private String attendDeptName;
	/** 参会部门id*/
	private String attendDeptId;
	/** 参会部门id*/
	private String owner;
	/** 参会部门id*/
	private String ownerid;
	

	public String getAttendDeptName() {
		return attendDeptName;
	}
	public void setAttendDeptName(String attendDeptName) {
		this.attendDeptName = attendDeptName;
	}
	public String getAttendDeptId() {
		return attendDeptId;
	}
	public void setAttendDeptId(String attendDeptId) {
		this.attendDeptId = attendDeptId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	
	
}
