package com.sinosoft.sinoep.user.entity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_USER_DPRB_UIAS", schema = "DEMO")
public class SysUserDprb {
    private long id;
    private String userCode;
    private String usdrName;
    private Long orderNo;
    private String note;
    private String auther;
    private String status;
    private String time;

    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_CODE", nullable = true, length = 50)
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "USDR_NAME", nullable = true, length = 50)
    public String getUsdrName() {
        return usdrName;
    }

    public void setUsdrName(String usdrName) {
        this.usdrName = usdrName;
    }

    @Basic
    @Column(name = "ORDER_NO", nullable = true, precision = 0)
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
    @Column(name = "AUTHER", nullable = true, length = 30)
    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "TIME", nullable = true, length = 30)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUserDprb that = (SysUserDprb) o;

        if (id != that.id) return false;
        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (usdrName != null ? !usdrName.equals(that.usdrName) : that.usdrName != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (auther != null ? !auther.equals(that.auther) : that.auther != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (usdrName != null ? usdrName.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (auther != null ? auther.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
