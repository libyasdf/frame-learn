package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;/**
 * Created by s on 2018/11/7.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @Description :党员管理-历史党员-实体
 * @Author: 李帅
 * @Date: 2018/11/7 20:43
 */
@Entity
@Table(name = "DDJS_DYGL_HISTORY")
public class DdjsDyglHistoryEntity {
    private String id;
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
    private String isProbationary;
    private String partyOrganizationId;
    private String partyOrganizationName;
    private String goPartyid;
    private String goPartyname;
    private String name;
    private String pinCodes;
    private String registeredResidence;
    private String sex;
    private String nation;
    private String nativePlace;
    private String birthplace;
    private String healthStatus;
    private String familyOrigin;
    private String dateOfBirth;
    private String maritalStatus;
    private String workingUnit;
    private String workingTime;
    private String identity;
    private String frontlineSituation;
    private String ryEducation;
    private String ryGegree;
    private String ryMajor;
    private String ryGraduationAcademy;
    private String technicalPost;
    private String jobLevel;
    private String contactNumber;
    private String homeAddress;
    private String localPoliceStation;
    private String isPartyRepresentative;
    private String userId;
    private String userbasicinfoId;
    private String superId;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "IS_PROBATIONARY")
    public String getIsProbationary() {
        return isProbationary;
    }

    public void setIsProbationary(String isProbationary) {
        this.isProbationary = isProbationary;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION_ID")
    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION_NAME")
    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    @Basic
    @Column(name = "GO_PARTYID")
    public String getGoPartyid() {
        return goPartyid;
    }

    public void setGoPartyid(String goPartyid) {
        this.goPartyid = goPartyid;
    }

    @Basic
    @Column(name = "GO_PARTYNAME")
    public String getGoPartyname() {
        return goPartyname;
    }

    public void setGoPartyname(String goPartyname) {
        this.goPartyname = goPartyname;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PIN_CODES")
    public String getPinCodes() {
        return pinCodes;
    }

    public void setPinCodes(String pinCodes) {
        this.pinCodes = pinCodes;
    }

    @Basic
    @Column(name = "REGISTERED_RESIDENCE")
    public String getRegisteredResidence() {
        return registeredResidence;
    }

    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence;
    }

    @Basic
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "NATION")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "NATIVE_PLACE")
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @Basic
    @Column(name = "BIRTHPLACE")
    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    @Basic
    @Column(name = "HEALTH_STATUS")
    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Basic
    @Column(name = "FAMILY_ORIGIN")
    public String getFamilyOrigin() {
        return familyOrigin;
    }

    public void setFamilyOrigin(String familyOrigin) {
        this.familyOrigin = familyOrigin;
    }

    @Basic
    @Column(name = "DATE_OF_BIRTH")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "MARITAL_STATUS")
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Basic
    @Column(name = "WORKING_UNIT")
    public String getWorkingUnit() {
        return workingUnit;
    }

    public void setWorkingUnit(String workingUnit) {
        this.workingUnit = workingUnit;
    }

    @Basic
    @Column(name = "WORKING_TIME")
    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    @Basic
    @Column(name = "IDENTITY")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "FRONTLINE_SITUATION")
    public String getFrontlineSituation() {
        return frontlineSituation;
    }

    public void setFrontlineSituation(String frontlineSituation) {
        this.frontlineSituation = frontlineSituation;
    }

    @Basic
    @Column(name = "EDUCATION")
    public String getRyEducation() { return ryEducation; }

    public void setRyEducation(String ryEducation) { this.ryEducation = ryEducation; }

    @Basic
    @Column(name = "DEGREE")
    public String getRyGegree() { return ryGegree; }

    public void setRyGegree(String ryGegree) { this.ryGegree = ryGegree; }

    @Basic
    @Column(name = "MAJOR")
    public String getRyMajor() { return ryMajor; }

    public void setRyMajor(String ryMajor) { this.ryMajor = ryMajor; }

    @Basic
    @Column(name = "GRADUATION_ACADEMY")

    public String getRyGraduationAcademy() { return ryGraduationAcademy; }

    public void setRyGraduationAcademy(String ryGraduationAcademy) { this.ryGraduationAcademy = ryGraduationAcademy; }

    @Basic
    @Column(name = "TECHNICAL_POST")
    public String getTechnicalPost() {
        return technicalPost;
    }

    public void setTechnicalPost(String technicalPost) {
        this.technicalPost = technicalPost;
    }

    @Basic
    @Column(name = "JOB_LEVEL")
    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Basic
    @Column(name = "CONTACT_NUMBER")
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Basic
    @Column(name = "HOME_ADDRESS")
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Basic
    @Column(name = "LOCAL_POLICE_STATION")
    public String getLocalPoliceStation() {
        return localPoliceStation;
    }

    public void setLocalPoliceStation(String localPoliceStation) {
        this.localPoliceStation = localPoliceStation;
    }

    @Basic
    @Column(name = "IS_PARTY_REPRESENTATIVE")
    public String getIsPartyRepresentative() {
        return isPartyRepresentative;
    }

    public void setIsPartyRepresentative(String isPartyRepresentative) {
        this.isPartyRepresentative = isPartyRepresentative;
    }

    @Basic
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Basic
    @Column(name = "USERBASICINFO_ID")
    public String getUserbasicinfoId() {
        return userbasicinfoId;
    }

    public void setUserbasicinfoId(String userbasicinfoId) {
        this.userbasicinfoId = userbasicinfoId;
    }

    @Basic
    @Column(name = "SUPER_ID")
    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }


    public DdjsDyglHistoryEntity(){
        super();
    }

    public DdjsDyglHistoryEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String isProbationary, String partyOrganizationId, String partyOrganizationName, String goPartyid, String goPartyname, String name, String pinCodes, String registeredResidence, String sex, String nation, String nativePlace, String birthplace, String healthStatus, String familyOrigin, String dateOfBirth, String maritalStatus, String workingUnit, String workingTime, String identity, String frontlineSituation, String ryEducation, String ryGegree, String ryMajor, String ryGraduationAcademy, String technicalPost, String jobLevel, String contactNumber, String homeAddress, String localPoliceStation, String isPartyRepresentative, String userId, String userbasicinfoId, String superId) {
        this.id = id;
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
        this.isProbationary = isProbationary;
        this.partyOrganizationId = partyOrganizationId;
        this.partyOrganizationName = partyOrganizationName;
        this.goPartyid = goPartyid;
        this.goPartyname = goPartyname;
        this.name = name;
        this.pinCodes = pinCodes;
        this.registeredResidence = registeredResidence;
        this.sex = sex;
        this.nation = nation;
        this.nativePlace = nativePlace;
        this.birthplace = birthplace;
        this.healthStatus = healthStatus;
        this.familyOrigin = familyOrigin;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.workingUnit = workingUnit;
        this.workingTime = workingTime;
        this.identity = identity;
        this.frontlineSituation = frontlineSituation;
        this.ryEducation = ryEducation;
        this.ryGegree = ryGegree;
        this.ryMajor = ryMajor;
        this.ryGraduationAcademy = ryGraduationAcademy;
        this.technicalPost = technicalPost;
        this.jobLevel = jobLevel;
        this.contactNumber = contactNumber;
        this.homeAddress = homeAddress;
        this.localPoliceStation = localPoliceStation;
        this.isPartyRepresentative = isPartyRepresentative;
        this.userId = userId;
        this.userbasicinfoId = userbasicinfoId;
        this.superId = superId;
    }
}
