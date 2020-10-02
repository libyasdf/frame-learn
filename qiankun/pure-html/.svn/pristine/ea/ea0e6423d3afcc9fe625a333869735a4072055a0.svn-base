package com.sinosoft.sinoep.sendFLowWorkflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sendflow_time")
public class SendFlowTime {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;
    //sendflow_time_base外键
    @Column(name = "base_id")
    private String baseId;
    //设置月份
    @Column(name = "set_month")
    private String setMonth;
    //填报开始的年份（1：今年，0：明年）
    @Column(name = "TB_BEGIN_YEAR_STATUS")
    private String tbBeginYearStatus;
    //填报开始月
    @Column(name = "TB_BEGIN_MONTH")
    private int tbBeginMonth;
    //填报开始日
    @Column(name = "TB_BEGIN_DAY")
    private int tbBeginDay;
    //填报截至的月份（1：今年，0：明年）
    @Column(name = "TB_END_YEAR_STATUS")
    private String tbEndYearStaus;
    //填报截至月
    @Column(name = "TB_END_MONTH")
    private int tbEndMonth;
    //填报截至日
    @Column(name = "TB_END_DAY")
    private int tbEndDay;
    //催报得年份（1：今年，0：明年）
    @Column(name = "SEND_DOC_YEAR_STATUS")
    private String sendDocYearStatus;
    //催报月份
    @Column(name = "SEND_DOC_MONTH")
    private int sendDocMonth;
    //催报日
    @Column(name = "SEND_DOC_DAY")
    private int sendDocDay;
    //排序字段
    @Column(name = "ORDER_BY")
    private int orderBy;
    //填报开始小时
    @Column(name = "TB_BEGIN_HOUR")
    private int tbBeginHour;
    //填报开始分钟
    @Column(name = "TB_BEGIN_MIN")
    private int tbBeginMin;
    //填报截至小时
    @Column(name = "TB_END_HOUR")
    private int tbEndHour;
    //填报截至分钟
    @Column(name = "TB_END_MIN")
    private int tbEndMin;
    //填报期数（目前是季度报和半年报设置）
    @Column(name = "SET_PHASE")
    private String setPhase;

    public SendFlowTime() {
    }

    public SendFlowTime(String baseId, String setMonth, String tbBeginYearStatus, int tbBeginMonth, int tbBeginDay, String tbEndYearStaus, int tbEndMonth, int tbEndDay, String sendDocYearStatus, int sendDocMonth, int sendDocDay, int orderBy, int tbBeginHour, int tbBeginMin, int tbEndHour, int tbEndMin, String setPhase) {
        this.baseId = baseId;
        this.setMonth = setMonth;
        this.tbBeginYearStatus = tbBeginYearStatus;
        this.tbBeginMonth = tbBeginMonth;
        this.tbBeginDay = tbBeginDay;
        this.tbEndYearStaus = tbEndYearStaus;
        this.tbEndMonth = tbEndMonth;
        this.tbEndDay = tbEndDay;
        this.sendDocYearStatus = sendDocYearStatus;
        this.sendDocMonth = sendDocMonth;
        this.sendDocDay = sendDocDay;
        this.orderBy = orderBy;
        this.tbBeginHour = tbBeginHour;
        this.tbBeginMin = tbBeginMin;
        this.tbEndHour = tbEndHour;
        this.tbEndMin = tbEndMin;
        this.setPhase = setPhase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getSetMonth() {
        return setMonth;
    }

    public void setSetMonth(String setMonth) {
        this.setMonth = setMonth;
    }

    public String getTbBeginYearStatus() {
        return tbBeginYearStatus;
    }

    public void setTbBeginYearStatus(String tbBeginYearStatus) {
        this.tbBeginYearStatus = tbBeginYearStatus;
    }

    public int getTbBeginMonth() {
        return tbBeginMonth;
    }

    public void setTbBeginMonth(int tbBeginMonth) {
        this.tbBeginMonth = tbBeginMonth;
    }

    public int getTbBeginDay() {
        return tbBeginDay;
    }

    public void setTbBeginDay(int tbBeginDay) {
        this.tbBeginDay = tbBeginDay;
    }

    public String getTbEndYearStaus() {
        return tbEndYearStaus;
    }

    public void setTbEndYearStaus(String tbEndYearStaus) {
        this.tbEndYearStaus = tbEndYearStaus;
    }

    public int getTbEndMonth() {
        return tbEndMonth;
    }

    public void setTbEndMonth(int tbEndMonth) {
        this.tbEndMonth = tbEndMonth;
    }

    public int getTbEndDay() {
        return tbEndDay;
    }

    public void setTbEndDay(int tbEndDay) {
        this.tbEndDay = tbEndDay;
    }

    public String getSendDocYearStatus() {
        return sendDocYearStatus;
    }

    public void setSendDocYearStatus(String sendDocYearStatus) {
        this.sendDocYearStatus = sendDocYearStatus;
    }

    public int getSendDocMonth() {
        return sendDocMonth;
    }

    public void setSendDocMonth(int sendDocMonth) {
        this.sendDocMonth = sendDocMonth;
    }

    public int getSendDocDay() {
        return sendDocDay;
    }

    public void setSendDocDay(int sendDocDay) {
        this.sendDocDay = sendDocDay;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getTbBeginHour() {
        return tbBeginHour;
    }

    public void setTbBeginHour(int tbBeginHour) {
        this.tbBeginHour = tbBeginHour;
    }

    public int getTbBeginMin() {
        return tbBeginMin;
    }

    public void setTbBeginMin(int tbBeginMin) {
        this.tbBeginMin = tbBeginMin;
    }

    public int getTbEndHour() {
        return tbEndHour;
    }

    public void setTbEndHour(int tbEndHour) {
        this.tbEndHour = tbEndHour;
    }

    public int getTbEndMin() {
        return tbEndMin;
    }

    public void setTbEndMin(int tbEndMin) {
        this.tbEndMin = tbEndMin;
    }

    public String getSetPhase() {
        return setPhase;
    }

    public void setSetPhase(String setPhase) {
        this.setPhase = setPhase;
    }
}
