package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DYGL_DEGREE")
public class DdjsDyglDegreeEntity{
    private String degreeId;
    private String creUserId;
    private String creUserName;
    private String creDeptId;
    private String creDeptName;
    private String creChushiId;
    private String creChushiName;
    private String creJuId;
    private String creJuName;
    private String visible;
    private String creTime;
    private String degreeSuperId;
    private String educationSector;
    private String education;
    private String enrolmentTime;
    private String graduationTime;
    private String major;
    private String graduationAcademy;
    private String degree;
    private String degreeAwardTime;


    @Basic
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "degree_Id", length = 50)

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }
    @Basic
    @Column(name = "CRE_USER_ID")
    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    @Basic
    @Column(name = "CRE_USER_NAME")
    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    @Basic
    @Column(name = "CRE_DEPT_ID")
    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    @Basic
    @Column(name = "CRE_DEPT_NAME")
    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_ID")
    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_NAME")
    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    @Basic
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    @Basic
    @Column(name = "CRE_JU_NAME")
    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    @Basic
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }


    @Basic
    @Column(name = "degree_SUPER_ID")
    public String getDegreeSuperId() {
        return degreeSuperId;
    }

    public void setDegreeSuperId(String degreeSuperId) {
        this.degreeSuperId = degreeSuperId;
    }


    @Basic
    @Column(name = "EDUCATION_SECTOR")
    public String getEducationSector() {
        return educationSector;
    }

    public void setEducationSector(String educationSector) {
        this.educationSector = educationSector;
    }

    @Basic
    @Column(name = "EDUCATION")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "ENROLMENT_TIME")
    public String getEnrolmentTime() {
        return enrolmentTime;
    }

    public void setEnrolmentTime(String enrolmentTime) {
        this.enrolmentTime = enrolmentTime;
    }

    @Basic
    @Column(name = "GRADUATION_TIME")
    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    @Basic
    @Column(name = "MAJOR")
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "GRADUATION_ACADEMY")
    public String getGraduationAcademy() {
        return graduationAcademy;
    }

    public void setGraduationAcademy(String graduationAcademy) {
        this.graduationAcademy = graduationAcademy;
    }

    @Basic
    @Column(name = "DEGREE")
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Basic
    @Column(name = "DEGREE_AWARD_TIME")
    public String getDegreeAwardTime() {
        return degreeAwardTime;
    }

    public void setDegreeAwardTime(String degreeAwardTime) {
        this.degreeAwardTime = degreeAwardTime;
    }

    public DdjsDyglDegreeEntity() {
        super();
    }

    public DdjsDyglDegreeEntity(String degreeId, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String degreeSuperId, String educationSector, String education, String enrolmentTime, String graduationTime, String major, String graduationAcademy, String degree, String degreeAwardTime) {
        this.degreeId = degreeId;
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
        this.degreeSuperId = degreeSuperId;
        this.educationSector = educationSector;
        this.education = education;
        this.enrolmentTime = enrolmentTime;
        this.graduationTime = graduationTime;
        this.major = major;
        this.graduationAcademy = graduationAcademy;
        this.degree = degree;
        this.degreeAwardTime = degreeAwardTime;
    }
}
