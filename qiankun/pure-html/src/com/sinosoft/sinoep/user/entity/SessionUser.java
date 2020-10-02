package com.sinosoft.sinoep.user.entity;

/**
 * @Auther: 周俊林
 * @Date: 2018/5/7 17:00
 * @Description: 将Session中的用户信息封装成对象
 */
public class SessionUser {
    /** 用户名 */
    private  String userName;

    /** 用户ID */
    private  String userId ;

    /** 组织机构ID，顶级部门ID */
    private  String userOrgId;

    /** 组织机构名称，顶级部门名称 */
    private  String userOrgName;

    /** 部门ID */
    private  String userDeptId;

    /** 部门名称 */
    private  String userDeptName;

    /** 父部门ID */
    private  String superDeptId;

    /** 父部门名称 */
    private  String superDeptName;

    /** 登录用户的系统角色ID */
    private  String userSysroleId;

    /** 登录用户的业务角色ID */
    private  String userRole;

    /** 登录用户的处室ID */
    private  String userChushiId;

    /** 登录用户的处室名称 */
    private  String userChushiName;

    /** 登录用户的二级局id */
    private  String userJuId;

    /** 登录用户的二级局名称 */
    private  String userJuName;
    
    /** 登录用户的系统角色ID */
    private  String userSysroleNo;

    /** 登录用户的业务角色ID */
    private  String userRoleNo;
    

    public String getUserSysroleNo() {
		return userSysroleNo;
	}

	public void setUserSysroleNo(String userSysroleNo) {
		this.userSysroleNo = userSysroleNo;
	}

	public String getUserRoleNo() {
		return userRoleNo;
	}

	public void setUserRoleNo(String userRoleNo) {
		this.userRoleNo = userRoleNo;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(String userOrgId) {
        this.userOrgId = userOrgId;
    }

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
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
}
