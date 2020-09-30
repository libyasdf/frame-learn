package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * @Author 王富康
 * @Description //TODO 值班表实体类
 * @Date 2018/7/11 18:06
 * @Param
 * @return
 **/
@Entity
@Table(name = "zbgl_duty_detail")
public class DutyTable {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;//值班表id

    /** 创建人id */
    @Column(name = "cre_user_id", length = 50)
    private String creUserId;//创建人id

    /** 创建人姓名 */
    @Column(name = "cre_user_name", length = 50)
    private String creUserName;//创建人姓名

    /** 创建人部门id */
    @Column(name = "cre_dept_id", length = 50)
    private String creDeptId;//创建人部门id

    /** 创建人部门名 */
    @Column(name = "cre_dept_name", length = 50)
    private String creDeptName;//创建人部门名

    /** 创建人处室id */
    @Column(name = "cre_chushi_id", length = 50)
    private String creChushiId;//创建人处室id

    /** 创建人处室名 */
    @Column(name = "cre_chushi_name", length = 50)
    private String creChushiName;//创建人处室名

    /** 创建人局id */
    @Column(name = "cre_ju_id", length = 50)
    private String creJuId;//创建人局id

    /** 创建人局名 */
    @Column(name = "cre_ju_name", length = 50)
    private String creJuName;//创建人局名

    /** 逻辑删除标识 */
    @Column(name = "visible", length = 1)
    private String visible;//逻辑删除标识

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;//创建时间

    /** 上报表的id或者是值班工作通知的id */
    @Column(name = "report_id", length = 50)
    private String reportId;//上报表的id或者是值班工作通知的id

    /** 值班日期 */
    @Column(name = "watch_date", length = 30)
    private String watchDate;//值班日期

    /** 星期几 */
    @Column(name = "weekday", length = 30)
    private String weekday;//周几
    /** 值班人id */
    @Column(name = "watch_id", length = 50)
    private String watchId;//值班人id

    /** 值班人姓名 */
    @Column(name = "watch_name", length = 20)
    private String watchName;//值班人姓名

    /** 值班班次：0.昼;1.夜;2.早;3.中;4.晚 */
    @Column(name = "classes", length = 1)
    private String classes;//值班班次：0.昼;1.夜;2.早;3.中;4.晚

    /** 值班人联系电话 */
    @Column(name = "phone", length = 20)
    private String phone;//值班人联系电话

    /** 值班人普网电话 */
    @Column(name = "common_phone", length = 20)
    private String commonPhone;//值班人普网电话

    /** 值班人专网电话 */
    @Column(name = "private_phone", length = 20)
    private String privatePhone;//值班人专网电话

    /** 带班领导id */
    @Column(name = "shift_leader_id", length = 50)
    private String shiftLeaderId;//带班领导id

    /** 带班领导姓名 */
    @Column(name = "shift_leader_name", length = 50)
    private String shiftLeaderName;//带班领导姓名

    /** 带班领导电话 */
    @Column(name = "shift_leader_phone", length = 20)
    private String shiftLeaderPhone;//带班领导电话

    /** 是否安保值班 0：不是；1：是； */
    @Column(name = "is_security", length = 1)
    private  String isSecurity;

    /** 值班工作通知id */
    @Column(name = "zb_notice_id", length = 50)
    private String zbNoticeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getWatchName() {
        return watchName;
    }

    public void setWatchName(String watchName) {
        this.watchName = watchName;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommonPhone() {
        return commonPhone;
    }

    public void setCommonPhone(String commonPhone) {
        this.commonPhone = commonPhone;
    }

    public String getPrivatePhone() {
        return privatePhone;
    }

    public void setPrivatePhone(String privatePhone) {
        this.privatePhone = privatePhone;
    }

    public String getShiftLeaderId() {
        return shiftLeaderId;
    }

    public void setShiftLeaderId(String shiftLeaderId) {
        this.shiftLeaderId = shiftLeaderId;
    }

    public String getShiftLeaderName() {
        return shiftLeaderName;
    }

    public void setShiftLeaderName(String shiftLeaderName) {
        this.shiftLeaderName = shiftLeaderName;
    }

    public String getShiftLeaderPhone() {
        return shiftLeaderPhone;
    }

    public void setShiftLeaderPhone(String shiftLeaderPhone) {
        this.shiftLeaderPhone = shiftLeaderPhone;
    }

    public String getIsSecurity() {
        return isSecurity;
    }

    public void setIsSecurity(String isSecurity) {
        this.isSecurity = isSecurity;
    }

    public String getZbNoticeId() {
        return zbNoticeId;
    }

    public void setZbNoticeId(String zbNoticeId) {
        this.zbNoticeId = zbNoticeId;
    }

    /**无参构造器**/
    public DutyTable() {
        super();
    }
    /**全部参数构造器**/
    public DutyTable(String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String reportId, String watchDate, String weekday, String watchId, String watchName, String classes, String phone, String commonPhone, String privatePhone, String shiftLeaderId, String shiftLeaderName, String shiftLeaderPhone, String isSecurity, String zbNoticeId) {
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.visible = visible;
        this.creTime = creTime;
        this.reportId = reportId;
        this.watchDate = watchDate;
        this.weekday = weekday;
        this.watchId = watchId;
        this.watchName = watchName;
        this.classes = classes;
        this.phone = phone;
        this.commonPhone = commonPhone;
        this.privatePhone = privatePhone;
        this.shiftLeaderId = shiftLeaderId;
        this.shiftLeaderName = shiftLeaderName;
        this.shiftLeaderPhone = shiftLeaderPhone;
        this.isSecurity = isSecurity;
        this.zbNoticeId = zbNoticeId;
    }

    /**
     * @Author 王富康
     * @Description //TODO 值班查询构造器
     * @Date 2018/12/19 11:08
     **/
    public DutyTable(String creJuName, String creChushiName,  String commonPhone, String privatePhone) {
        super();
        this.creJuName = creJuName;
        this.creChushiName = creChushiName;
        this.commonPhone = commonPhone;
        this.privatePhone = privatePhone;
    }

    /**导出新建虚拟数据用的构造器**/
    public DutyTable(String reportId, String watchDate, String weekday, String watchName, String classes, String phone, String shiftLeaderName, String shiftLeaderPhone, String commonPhone, String privatePhone) {
        this.reportId = reportId;
        this.watchDate = watchDate;
        this.weekday = weekday;
        this.watchName = watchName;
        this.classes = classes;
        this.phone = phone;
        this.shiftLeaderName = shiftLeaderName;
        this.shiftLeaderPhone = shiftLeaderPhone;
        this.commonPhone = commonPhone;
        this.privatePhone = privatePhone;
    }
}
