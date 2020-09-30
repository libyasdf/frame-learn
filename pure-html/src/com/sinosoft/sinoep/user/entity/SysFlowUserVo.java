package com.sinosoft.sinoep.user.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * TODO 用户信息封装类
 * @author 李利广
 * @Date 2018年5月22日 下午5:32:17
 */
public class SysFlowUserVo {
	
	/** 用户ID */
	@Id
	@Column(name="userid")
	private String userId;
	
	/** 用户姓名 */
	@Column(name="username")
	private String userName;
	
	/** 性别 0男 1女 */
	@Column(name="user_sex")
	private String sex;
	
	/** 用户ID */
	@Column(name="user_nm")
	private String userNm;
	
	/** 真实姓名、用户名企业全称 */
	@Column(name="usernamefull")
	private String userNameFull;
	
	/** 状态 1 正常 0 删除 */
	@Column(name="status")
	private String status;
	
	/** 备注 */
	@Column(name="note")
	private String note;
	
	/** 用户是否在线 */
	@Column(name="user_online")
	private String userOnline;
	
	/** 添加人 */
	@Column(name="auther")
	private String auther;
	
	/** 添加时间 */
	@Column(name="time")
	private String time;
	
	/**  */
	@Column(name="user_status")
	private String userStatus;
	
	/** 企业负责人姓名 */
	@Column(name="user_title")
	private String title;
	
	/**  */
	@Column(name="user_state")
	private String state;
	
	/**  */
	@Column(name="userjid")
	private String userJid;
	
	/** 电话 */
	@Column(name="phone")
	private String phone;
	
	/** 邮箱 */
	@Column(name="user_email")
	private String email;
	
	/** 邮箱密码 */
	@Column(name="user_email_psw")
	private String emailPwd;
	
	/**  */
	@Column(name="usermode")
	private String userMode;
	
	/** 密码错误次数 */
	@Column(name="wrong_login_number")
	private String wrongLoginNumber;
	
	/** 最后错误登录时间 */
	@Column(name="last_wrong_login_time")
	private String lastWrongLoginTime;
	
	/** 微信唯一标识 */
	@Column(name="unionid")
	private String unionId;
	
	/** CA G码 */
	@Column(name="ca_g_code")
	private String cagCode;
	
	/** 办公室电话号 */
	@Column(name="phone_office")
	private String phoneOffice;
	
	/** 添加时间 */
	@Column(name="mtime")
	private String mTime;
	
	/** 手机imei码 */
	@Column(name="phoneimei")
	private String phoneImei;
	
	/** 初始密码1未修改 */
	@Column(name="initpwd")
	private String initPwd;
	
	/**  */
	@Column(name="emailisable")
	private String emailIsable;
	
	/** 职务权限 */
	@Column(name="position_name")
	private String positionName;
	
	/** 职务 */
	@Column(name="position_real_level")
	private String positionRealName;
	
	/** 职务全称 */
	@Column(name="position_name_full")
	private String positionNameFull;
	
	/** 职级 */
	@Column(name="position_level")
	private String positionLevel;
	
	/** 涉密登记（1绝密；2机密；3秘密） */
	@Column(name="sensitive_mark")
	private String sensitiveMark;
	
	/**  */
	@Column(name="default_portal_site")
	private String defaultPortalSite;
	
	/**  */
	@Column(name="user_code")
	private String userCode;
	
	/** 用户地址 */
	@Column(name="address")
	private String address;
	
	/** 创建时间 */
	@Column(name="outdate")
	private String outDate;
	
	/** QQ */
	@Column(name="validatacode")
	private String validataCode;
	
	/** 公司 */
	@Column(name="company")
	private String company;
	
	/** QQ */
	@Column(name="qq")
	private String qq;
	
	/** 微信   */
	@Column(name="wechat")
	private String wechat;
	
	/** 生日   */
	@Column(name="birthdate")
	private String birthday;
	
	/** 资料完善度   */
	@Column(name="degree")
	private String degree;
	
	/** 普通用户身份证号、企业法人身份证号   */
	@Column(name="identity")
	private String identity;
	
	/** 支付账号   */
	@Column(name="account")
	private String account;
	
	/** 用户类型  0普通用户  1 企业用户   */
	@Column(name="usertype")
	private String userType;
	
	/** 注册用户来源 wx 微信   gt 国泰   dx 电信   */
	@Column(name="reg_sys")
	private String regSys;
	
	/** 用户认证状态，0：未认证；1：通过认证；2：认证中；3：认证未通过 */
	@Column(name="authentication")
	private String authentication;
	
	/** 头像路径 */
	@Column(name="imagepath")
	private String imagePath;
	
	/** 企业工商注册号/统一社会信用代码 */
	@Column(name="co_registration_code")
	private String coRegistrationCode;
	
	/** 警号 */
	@Column(name="police_number")
	private String policeNumber;
	
	/** 人员卡号 */
	@Column(name="card_number")
	private String cardNumber;
	
	/** 部门ID */
    private String userDeptId;

    /** 部门名称 */
    private String userDeptName;

    /** 父部门ID */
    private String superDeptId;

    /** 父部门名称 */
    private String superDeptName;

    /** 用户的系统角色ID */
    private String userSysroleId;

    /** 用户的业务角色ID */
    private String userRole;

    /** 用户的处室ID */
    private String userChushiId;

    /** 用户的处室名称 */
    private String userChushiName;

    /** 用户的二级局id */
    private String userJuId;

    /** 用户的二级局名称 */
    private String userJuName;
	
    /** 用户的系统角色ID */
    private String userSysroleNo;

    /** 用户的业务角色ID */
    private String userRoleNO;

	/** 职务权限 名称 */
    private String positionNameMn;

	/** 职务 名称 */
    private String positionRealNameMn;

	/** 职级 名称 */
    private String positionLevelMn;


	public String getUserSysroleNo() {
		return userSysroleNo;
	}

	public void setUserSysroleNo(String userSysroleNo) {
		this.userSysroleNo = userSysroleNo;
	}

	public String getUserRoleNO() {
		return userRoleNO;
	}

	public void setUserRoleNO(String userRoleNO) {
		this.userRoleNO = userRoleNO;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}

	public String getSuperDeptId() {
		return superDeptId;
	}

	public void setSuperDeptId(String superDeptId) {
		this.superDeptId = superDeptId;
	}

	public String getSuperDeptName() {
		return superDeptName;
	}

	public void setSuperDeptName(String superDeptName) {
		this.superDeptName = superDeptName;
	}

	public String getUserSysroleId() {
		return userSysroleId;
	}

	public void setUserSysroleId(String userSysroleId) {
		this.userSysroleId = userSysroleId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserChushiId() {
		return userChushiId;
	}

	public void setUserChushiId(String userChushiId) {
		this.userChushiId = userChushiId;
	}

	public String getUserChushiName() {
		return userChushiName;
	}

	public void setUserChushiName(String userChushiName) {
		this.userChushiName = userChushiName;
	}

	public String getUserJuId() {
		return userJuId;
	}

	public void setUserJuId(String userJuId) {
		this.userJuId = userJuId;
	}

	public String getUserJuName() {
		return userJuName;
	}

	public void setUserJuName(String userJuName) {
		this.userJuName = userJuName;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserNameFull() {
		return userNameFull;
	}

	public void setUserNameFull(String userNameFull) {
		this.userNameFull = userNameFull;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserOnline() {
		return userOnline;
	}

	public void setUserOnline(String userOnline) {
		this.userOnline = userOnline;
	}

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserJid() {
		return userJid;
	}

	public void setUserJid(String userJid) {
		this.userJid = userJid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailPwd() {
		return emailPwd;
	}

	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}

	public String getUserMode() {
		return userMode;
	}

	public void setUserMode(String userMode) {
		this.userMode = userMode;
	}

	public String getWrongLoginNumber() {
		return wrongLoginNumber;
	}

	public void setWrongLoginNumber(String wrongLoginNumber) {
		this.wrongLoginNumber = wrongLoginNumber;
	}

	public String getLastWrongLoginTime() {
		return lastWrongLoginTime;
	}

	public void setLastWrongLoginTime(String lastWrongLoginTime) {
		this.lastWrongLoginTime = lastWrongLoginTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getCagCode() {
		return cagCode;
	}

	public void setCagCode(String cagCode) {
		this.cagCode = cagCode;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getPhoneImei() {
		return phoneImei;
	}

	public void setPhoneImei(String phoneImei) {
		this.phoneImei = phoneImei;
	}

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
	}

	public String getEmailIsable() {
		return emailIsable;
	}

	public void setEmailIsable(String emailIsable) {
		this.emailIsable = emailIsable;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionRealName() {
		return positionRealName;
	}

	public void setPositionRealName(String positionRealName) {
		this.positionRealName = positionRealName;
	}

	public String getPositionNameFull() {
		return positionNameFull;
	}

	public void setPositionNameFull(String positionNameFull) {
		this.positionNameFull = positionNameFull;
	}

	public String getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getSensitiveMark() {
		return sensitiveMark;
	}

	public void setSensitiveMark(String sensitiveMark) {
		this.sensitiveMark = sensitiveMark;
	}

	public String getDefaultPortalSite() {
		return defaultPortalSite;
	}

	public void setDefaultPortalSite(String defaultPortalSite) {
		this.defaultPortalSite = defaultPortalSite;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRegSys() {
		return regSys;
	}

	public void setRegSys(String regSys) {
		this.regSys = regSys;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCoRegistrationCode() {
		return coRegistrationCode;
	}

	public void setCoRegistrationCode(String coRegistrationCode) {
		this.coRegistrationCode = coRegistrationCode;
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPositionNameMn() {
		return positionNameMn;
	}

	public void setPositionNameMn(String positionNameMn) {
		this.positionNameMn = positionNameMn;
	}

	public String getPositionRealNameMn() {
		return positionRealNameMn;
	}

	public void setPositionRealNameMn(String positionRealNameMn) {
		this.positionRealNameMn = positionRealNameMn;
	}

	public String getPositionLevelMn() {
		return positionLevelMn;
	}

	public void setPositionLevelMn(String positionLevelMn) {
		this.positionLevelMn = positionLevelMn;
	}

}
