package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * TODO 考试人员信息封装
 * @author 李颖洁  
 * @date 2018年8月23日  下午3:55:38
 */
public class SysTestUserVo {
	
	/** 用户ID */
	@Id
	@Column(name="userid")
	private String userid;
	
	/** 用户姓名 */
	@Column(name="username")
	private String username;
	
	/** 性别 0男 1女 */
	@Column(name="user_sex")
	private String sex;
	
	/** 电话 */
	@Column(name="phone")
	private String phone;
	
	/** 状态 1 正常 0 删除 */
	@Column(name="status")
	private String status;
	
	/** 职务 */
	@Column(name="position_real_level")
	private String positionreallevel;
	
	/** 职务全称 */
	@Column(name="position_name_full")
	private String positionnamefull;
	
	/** 职级 */
	@Column(name="position_level")
	private String positionlevel;
	
	/** 职级名称*/
	@Column(name="position_level_name")
	private String positionlevelname;
	
	/** 部门ID */
    private  String userdeptid;

    /** 部门名称 */
    private  String userdeptname;

    /** 用户的处室ID */
    private  String userchushiid;

    /** 用户的处室名称 */
    private  String userchushiname;

    /** 用户的二级局id */
    private  String userjuid;

    /** 用户的二级局名称 */
    private  String userjuname;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPositionnamefull() {
		return positionnamefull;
	}

	public void setPositionnamefull(String positionnamefull) {
		this.positionnamefull = positionnamefull;
	}

	public String getPositionlevel() {
		return positionlevel;
	}

	public void setPositionlevel(String positionlevel) {
		this.positionlevel = positionlevel;
	}

	public String getPositionlevelname() {
		return positionlevelname;
	}

	public void setPositionlevelname(String positionlevelname) {
		this.positionlevelname = positionlevelname;
	}

	public String getUserdeptid() {
		return userdeptid;
	}

	public void setUserdeptid(String userdeptid) {
		this.userdeptid = userdeptid;
	}

	public String getUserdeptname() {
		return userdeptname;
	}

	public void setUserdeptname(String userdeptname) {
		this.userdeptname = userdeptname;
	}

	public String getUserchushiid() {
		return userchushiid;
	}

	public void setUserchushiid(String userchushiid) {
		this.userchushiid = userchushiid;
	}

	public String getUserchushiname() {
		return userchushiname;
	}

	public void setUserchushiname(String userchushiname) {
		this.userchushiname = userchushiname;
	}

	public String getUserjuid() {
		return userjuid;
	}

	public void setUserjuid(String userjuid) {
		this.userjuid = userjuid;
	}

	public String getUserjuname() {
		return userjuname;
	}

	public void setUserjuname(String userjuname) {
		this.userjuname = userjuname;
	}

	public String getPositionreallevel() {
		return positionreallevel;
	}

	public void setPositionreallevel(String positionreallevel) {
		this.positionreallevel = positionreallevel;
	}
    
    

   

	
	
	
	
}
