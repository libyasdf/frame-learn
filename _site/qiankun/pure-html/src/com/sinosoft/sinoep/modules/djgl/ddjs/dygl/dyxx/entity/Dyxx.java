package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 党员管理-党员信息-实体
 * author 冯建海
 */
@Entity
@Table(name = "zbgl_duty_info")
public class Dyxx {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;

    /** 单位id */
    @Column(name = "unit_id", length = 50)
    private String unitId;

    /** 单位名 */
    @Column(name = "unit_name", length = 50)
    private String unitName;

    /** 考勤表月份 */
    @Column(name = "month", length = 30)
    private String month;

    /** 是否上报 */
    @Column(name = "is_report", length = 1)
    private String isReport;

    /** 上报人id */
    @Column(name = "reporter_id", length = 50)
    private String reporterId;

    /** 上报人姓名 */
    @Column(name = "reporter_name", length = 50)
    private String reporterName;

    /** 上报时间 */
    @Column(name = "reporter_time", length = 50)
    private String reporterTime;

    /** 是否按时上报*/
    @Column(name = "is_on_time", length = 1)
    private String isOnTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterTime() {
        return reporterTime;
    }

    public void setReporterTime(String reporterTime) {
        this.reporterTime = reporterTime;
    }

    public String getIsOnTime() {
        return isOnTime;
    }

    public void setIsOnTime(String isOnTime) {
        this.isOnTime = isOnTime;
    }

    public Dyxx() {
        super();
    }

    public Dyxx(String unitId, String unitName, String month, String isReport, String reporterId, String reporterName, String reporterTime, String isOnTime) {
        super();
        this.unitId = unitId;
        this.unitName = unitName;
        this.month = month;
        this.isReport = isReport;
        this.reporterId = reporterId;
        this.reporterName = reporterName;
        this.reporterTime = reporterTime;
        this.isOnTime = isOnTime;
    }
}
