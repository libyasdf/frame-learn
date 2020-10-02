package com.sinosoft.sinoep.user.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SYS_FLOW_USER_UIAS", schema = "DEMO")
public class SysFlowUser {
    private long userid;
    private String userCode;
    private String username;
    private String userPassword;
    private String userNm;
    private String usernamefull;
    private String userOnline;
    private String note;
    private String status;
    private String auther;
    private String time;
    private String userStatus;
    private String userSex;
    private String userTitle;
    private String userState;
    private String userjid;
    private String phone;
    private String userEmail;
    private String userEmailPsw;
    private String usermode;
    private String wrongLoginNumber;
    private String lastWrongLoginTime;
    private String initpwd;
    private String mtime;
    private String phoneimei;
    private String unionid;
    //部门列表
    private Set<SysFlowDept> sysFlowDepts = new HashSet<>();

    @Id
    @Column(name = "USERID", nullable = false, precision = 0)
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "USER_CODE", nullable = true, length = 20)
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "USER_PASSWORD", nullable = true, length = 50)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "USER_NM", nullable = true, length = 50)
    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    @Basic
    @Column(name = "USERNAMEFULL", nullable = true, length = 50)
    public String getUsernamefull() {
        return usernamefull;
    }

    public void setUsernamefull(String usernamefull) {
        this.usernamefull = usernamefull;
    }

    @Basic
    @Column(name = "USER_ONLINE", nullable = true, length = 1)
    public String getUserOnline() {
        return userOnline;
    }

    public void setUserOnline(String userOnline) {
        this.userOnline = userOnline;
    }

    @Basic
    @Column(name = "NOTE", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "STATUS", nullable = false, length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "AUTHER", nullable = true, length = 30)
    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    @Basic
    @Column(name = "TIME", nullable = true, length = 30)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "USER_STATUS", nullable = true, length = 1)
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Basic
    @Column(name = "USER_SEX", nullable = true, length = 10)
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Basic
    @Column(name = "USER_TITLE", nullable = true, length = 50)
    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    @Basic
    @Column(name = "USER_STATE", nullable = true, length = 50)
    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Basic
    @Column(name = "USERJID", nullable = true, length = 50)
    public String getUserjid() {
        return userjid;
    }

    public void setUserjid(String userjid) {
        this.userjid = userjid;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "USER_EMAIL", nullable = true, length = 50)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "USER_EMAIL_PSW", nullable = true, length = 50)
    public String getUserEmailPsw() {
        return userEmailPsw;
    }

    public void setUserEmailPsw(String userEmailPsw) {
        this.userEmailPsw = userEmailPsw;
    }

    @Basic
    @Column(name = "USERMODE", nullable = true, length = 10)
    public String getUsermode() {
        return usermode;
    }

    public void setUsermode(String usermode) {
        this.usermode = usermode;
    }

    @Basic
    @Column(name = "WRONG_LOGIN_NUMBER", nullable = true, length = 10)
    public String getWrongLoginNumber() {
        return wrongLoginNumber;
    }

    public void setWrongLoginNumber(String wrongLoginNumber) {
        this.wrongLoginNumber = wrongLoginNumber;
    }

    @Basic
    @Column(name = "LAST_WRONG_LOGIN_TIME", nullable = true, length = 10)
    public String getLastWrongLoginTime() {
        return lastWrongLoginTime;
    }

    public void setLastWrongLoginTime(String lastWrongLoginTime) {
        this.lastWrongLoginTime = lastWrongLoginTime;
    }

    @Basic
    @Column(name = "INITPWD", nullable = true, length = 255)
    public String getInitpwd() {
        return initpwd;
    }

    public void setInitpwd(String initpwd) {
        this.initpwd = initpwd;
    }

    @Basic
    @Column(name = "MTIME", nullable = true, length = 255)
    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    @Basic
    @Column(name = "PHONEIMEI", nullable = true, length = 255)
    public String getPhoneimei() {
        return phoneimei;
    }

    public void setPhoneimei(String phoneimei) {
        this.phoneimei = phoneimei;
    }

    @Basic
    @Column(name = "UNIONID", nullable = true, length = 255)
    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "sysFlowUsers")
    public Set<SysFlowDept> getSysFlowDepts() {
        return sysFlowDepts;
    }

    public void setSysFlowDepts(Set<SysFlowDept> sysFlowDepts) {
        this.sysFlowDepts = sysFlowDepts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysFlowUser that = (SysFlowUser) o;

        if (userid != that.userid) return false;
        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (userNm != null ? !userNm.equals(that.userNm) : that.userNm != null) return false;
        if (usernamefull != null ? !usernamefull.equals(that.usernamefull) : that.usernamefull != null) return false;
        if (userOnline != null ? !userOnline.equals(that.userOnline) : that.userOnline != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (auther != null ? !auther.equals(that.auther) : that.auther != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (userStatus != null ? !userStatus.equals(that.userStatus) : that.userStatus != null) return false;
        if (userSex != null ? !userSex.equals(that.userSex) : that.userSex != null) return false;
        if (userTitle != null ? !userTitle.equals(that.userTitle) : that.userTitle != null) return false;
        if (userState != null ? !userState.equals(that.userState) : that.userState != null) return false;
        if (userjid != null ? !userjid.equals(that.userjid) : that.userjid != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;
        if (userEmailPsw != null ? !userEmailPsw.equals(that.userEmailPsw) : that.userEmailPsw != null) return false;
        if (usermode != null ? !usermode.equals(that.usermode) : that.usermode != null) return false;
        if (wrongLoginNumber != null ? !wrongLoginNumber.equals(that.wrongLoginNumber) : that.wrongLoginNumber != null)
            return false;
        if (lastWrongLoginTime != null ? !lastWrongLoginTime.equals(that.lastWrongLoginTime) : that.lastWrongLoginTime != null)
            return false;
        if (initpwd != null ? !initpwd.equals(that.initpwd) : that.initpwd != null) return false;
        if (mtime != null ? !mtime.equals(that.mtime) : that.mtime != null) return false;
        if (phoneimei != null ? !phoneimei.equals(that.phoneimei) : that.phoneimei != null) return false;
        if (unionid != null ? !unionid.equals(that.unionid) : that.unionid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userid ^ (userid >>> 32));
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userNm != null ? userNm.hashCode() : 0);
        result = 31 * result + (usernamefull != null ? usernamefull.hashCode() : 0);
        result = 31 * result + (userOnline != null ? userOnline.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (auther != null ? auther.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (userSex != null ? userSex.hashCode() : 0);
        result = 31 * result + (userTitle != null ? userTitle.hashCode() : 0);
        result = 31 * result + (userState != null ? userState.hashCode() : 0);
        result = 31 * result + (userjid != null ? userjid.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userEmailPsw != null ? userEmailPsw.hashCode() : 0);
        result = 31 * result + (usermode != null ? usermode.hashCode() : 0);
        result = 31 * result + (wrongLoginNumber != null ? wrongLoginNumber.hashCode() : 0);
        result = 31 * result + (lastWrongLoginTime != null ? lastWrongLoginTime.hashCode() : 0);
        result = 31 * result + (initpwd != null ? initpwd.hashCode() : 0);
        result = 31 * result + (mtime != null ? mtime.hashCode() : 0);
        result = 31 * result + (phoneimei != null ? phoneimei.hashCode() : 0);
        result = 31 * result + (unionid != null ? unionid.hashCode() : 0);
        return result;
    }
}
