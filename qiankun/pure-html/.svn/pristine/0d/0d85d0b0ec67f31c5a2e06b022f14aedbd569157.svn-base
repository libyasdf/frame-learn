package com.sinosoft.sinoep.modules.zhbg.salary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 工资的实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月28日 上午11:33:56
 */
@Entity
@Table(name = "GZGL_SALARY")
public class Salary {
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	/*创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	/*创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	/*创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	/*创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*处室id*/
	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;
	/*处室名称*/
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;
	/*二级局id*/
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	/*二级局名*/
	@Column(name = "CRE_JU_NAME")
	private String creJuName;
	
	/*发放月份*/
	@Column(name = "sa_month")
	private String saMonth;
	/*部门名称*/
	@Column(name = "sa_dept_name")
	private String saDeptName;
	/*职员姓名*/
	@Column(name = "sa_user_name")
	private String saUserName;
	/*职务工资*/
	@Column(name = "job_salary")
	private String jobSalary;
	/*级别工资*/
	@Column(name = "level_salary")
	private String levelSalary;
	/*警衔工资*/
	@Column(name = "police_rank_salary")
	private String policeRankSalary;
	
	/*工作津贴*/
	@Column(name = "work_salary")
	private String workSalary;
	/*生活补贴*/
	@Column(name = "life_salary")
	private String lifeSalary;
	/*执勤津贴*/
	@Column(name = "duty_salary")
	private String dutySalary;
	
	/*副食补贴*/
	@Column(name = "food_salary")
	private String fooSalary;
	/*回民补贴*/
	@Column(name = "huis_salary")
	private String huiSalary;
	/*房屋补贴*/
	@Column(name = "house_salary")
	private String houseSalary;
	/*独子费*/
	@Column(name = "only_child_salary")
	private String onlyChildSalary;
	/*值班费*/
	@Column(name = "duty_fee")
	private String dutyFee;
	/*警察加班费*/
	@Column(name = "police_overtime_pay")
	private String policeOvertimePay;
	/*应发工资*/
	@Column(name = "should_salary")
	private String shouldSalary;
	/*公积金*/
	@Column(name = "accumulation_salary")
	private String accumulationSalary;
	
	/*医保*/
	@Column(name = "medical_insurance")
	private String medicalInsurance;
	/*养老年金*/
	@Column(name = "pension_annuity")
	private String pensionAnnuity;
	/*房租*/
	@Column(name = "rent_salary")
	private String rentSalary;
	/*扣款*/
	@Column(name = "buckle_salary")
	private String buckleSalary;
	/*会费*/
	@Column(name = "dues_salary")
	private String duesSalary;
	/*扣贷款*/
	@Column(name = "buckle_loan")
	private String buckleLoan;
	/*贷款利息*/
	@Column(name = "loan_interest")
	private String loanInterest;
	/*个人所得税s*/
	@Column(name = "personal_income_tax")
	private String personalIncomeTax;
	/*实发工资*/
	@Column(name = "actual_salary")
	private String actualSalary;
	/*身份证号码*/
	@Column(name = "id_car_no")
	private String idCarNo;
	/*上报时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*更新人姓名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/*更新人姓名*/
	@Column(name = "sort")
	private Integer sort;
	
	/*物业费*/
	@Column(name = "property_fee")
	private String propertyFee;
	
	/*采暖补贴*/
	@Column(name = "heating_subsidies")
	private String heatingSubsidies;
	
	/*交通补贴*/
	@Column(name = "travel_allowance")
	private String travelAllowance;
	
	/*通讯补贴*/
	@Column(name = "phone_allowance")
	private String phoneAllowance;
	
	/*公务交通*/
	@Column(name = "public_transportation")
	private String publicTransportation;
	
	/*未休假补贴*/
	@Column(name = "unpaid_leave_allowance")
	private String unpaidLeaveAllowance;
	
	/*双薪*/
	@Column(name = "double_pay")
	private String doublePay;
	
	/*绩效奖*/
	@Column(name = "merit_pay")
	private String meritPay;
	
	/*防暑降温费*/
	@Column(name = "heatstroke_prevention_subsidy")
	private String heatstrokePreventionSubsidy;
	
	/*冬季采暖补贴*/
	@Column(name = "winter_heating_subsidy")
	private String winterHeatingSubsidy;

	/*大额医保*/
	@Column(name = "BIG_MEDICAL_INSURANCE")
	private String bigMedicalInsurance;

	/*专项附加扣除*/
	@Column(name = "DEDUCT_SURCHARGES")
	private String deductSurcharges;

	
	/*操作*/
	@Transient
	private String cz = "";
	
		
	public String getPropertyFee() {
		return propertyFee;
	}
	public void setPropertyFee(String propertyFee) {
		this.propertyFee = propertyFee;
	}
	public String getHeatingSubsidies() {
		return heatingSubsidies;
	}
	public void setHeatingSubsidies(String heatingSubsidies) {
		this.heatingSubsidies = heatingSubsidies;
	}
	public String getTravelAllowance() {
		return travelAllowance;
	}
	public void setTravelAllowance(String travelAllowance) {
		this.travelAllowance = travelAllowance;
	}
	public String getPhoneAllowance() {
		return phoneAllowance;
	}
	public void setPhoneAllowance(String phoneAllowance) {
		this.phoneAllowance = phoneAllowance;
	}
	public String getPublicTransportation() {
		return publicTransportation;
	}
	public void setPublicTransportation(String publicTransportation) {
		this.publicTransportation = publicTransportation;
	}
	public String getUnpaidLeaveAllowance() {
		return unpaidLeaveAllowance;
	}
	public void setUnpaidLeaveAllowance(String unpaidLeaveAllowance) {
		this.unpaidLeaveAllowance = unpaidLeaveAllowance;
	}
	public String getDoublePay() {
		return doublePay;
	}
	public void setDoublePay(String doublePay) {
		this.doublePay = doublePay;
	}
	public String getMeritPay() {
		return meritPay;
	}
	public void setMeritPay(String meritPay) {
		this.meritPay = meritPay;
	}
	public String getHeatstrokePreventionSubsidy() {
		return heatstrokePreventionSubsidy;
	}
	public void setHeatstrokePreventionSubsidy(String heatstrokePreventionSubsidy) {
		this.heatstrokePreventionSubsidy = heatstrokePreventionSubsidy;
	}
	
	public String getWinterHeatingSubsidy() {
		return winterHeatingSubsidy;
	}
	public void setWinterHeatingSubsidy(String winterHeatingSubsidy) {
		this.winterHeatingSubsidy = winterHeatingSubsidy;
	}
	public String getCreChuShiId() {
		return creChuShiId;
	}
	public void setCreChuShiId(String creChuShiId) {
		this.creChuShiId = creChuShiId;
	}
	public String getCreChuShiName() {
		return creChuShiName;
	}
	public void setCreChuShiName(String creChuShiName) {
		this.creChuShiName = creChuShiName;
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
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
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
	public String getSaMonth() {
		return saMonth;
	}
	public void setSaMonth(String saMonth) {
		this.saMonth = saMonth;
	}
	public String getSaDeptName() {
		return saDeptName;
	}
	public void setSaDeptName(String saDeptName) {
		this.saDeptName = saDeptName;
	}
	public String getSaUserName() {
		return saUserName;
	}
	public void setSaUserName(String saUserName) {
		this.saUserName = saUserName;
	}
	public String getJobSalary() {
		return jobSalary;
	}
	public void setJobSalary(String jobSalary) {
		this.jobSalary = jobSalary;
	}
	public String getLevelSalary() {
		return levelSalary;
	}
	public void setLevelSalary(String levelSalary) {
		this.levelSalary = levelSalary;
	}
	public String getPoliceRankSalary() {
		return policeRankSalary;
	}
	public void setPoliceRankSalary(String policeRankSalary) {
		this.policeRankSalary = policeRankSalary;
	}
	public String getWorkSalary() {
		return workSalary;
	}
	public void setWorkSalary(String workSalary) {
		this.workSalary = workSalary;
	}
	public String getLifeSalary() {
		return lifeSalary;
	}
	public void setLifeSalary(String lifeSalary) {
		this.lifeSalary = lifeSalary;
	}
	public String getDutySalary() {
		return dutySalary;
	}
	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}
	public String getFooSalary() {
		return fooSalary;
	}
	public void setFooSalary(String fooSalary) {
		this.fooSalary = fooSalary;
	}
	public String getHuiSalary() {
		return huiSalary;
	}
	public void setHuiSalary(String huiSalary) {
		this.huiSalary = huiSalary;
	}
	public String getHouseSalary() {
		return houseSalary;
	}
	public void setHouseSalary(String houseSalary) {
		this.houseSalary = houseSalary;
	}
	public String getOnlyChildSalary() {
		return onlyChildSalary;
	}
	public void setOnlyChildSalary(String onlyChildSalary) {
		this.onlyChildSalary = onlyChildSalary;
	}
	public String getShouldSalary() {
		return shouldSalary;
	}
	public void setShouldSalary(String shouldSalary) {
		this.shouldSalary = shouldSalary;
	}
	public String getAccumulationSalary() {
		return accumulationSalary;
	}
	public void setAccumulationSalary(String accumulationSalary) {
		this.accumulationSalary = accumulationSalary;
	}
	public String getMedicalInsurance() {
		return medicalInsurance;
	}
	public void setMedicalInsurance(String medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}
	public String getPensionAnnuity() {
		return pensionAnnuity;
	}
	public void setPensionAnnuity(String pensionAnnuity) {
		this.pensionAnnuity = pensionAnnuity;
	}
	public String getRentSalary() {
		return rentSalary;
	}
	public void setRentSalary(String rentSalary) {
		this.rentSalary = rentSalary;
	}
	public String getBuckleSalary() {
		return buckleSalary;
	}
	public void setBuckleSalary(String buckleSalary) {
		this.buckleSalary = buckleSalary;
	}
	public String getDuesSalary() {
		return duesSalary;
	}
	public void setDuesSalary(String duesSalary) {
		this.duesSalary = duesSalary;
	}
	public String getBuckleLoan() {
		return buckleLoan;
	}
	public void setBuckleLoan(String buckleLoan) {
		this.buckleLoan = buckleLoan;
	}
	public String getLoanInterest() {
		return loanInterest;
	}
	public void setLoanInterest(String loanInterest) {
		this.loanInterest = loanInterest;
	}
	public String getPersonalIncomeTax() {
		return personalIncomeTax;
	}
	public void setPersonalIncomeTax(String personalIncomeTax) {
		this.personalIncomeTax = personalIncomeTax;
	}
	public String getActualSalary() {
		return actualSalary;
	}
	public void setActualSalary(String actualSalary) {
		this.actualSalary = actualSalary;
	}
	public String getIdCarNo() {
		return idCarNo;
	}
	public void setIdCarNo(String idCarNo) {
		this.idCarNo = idCarNo;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getDutyFee() {
		return dutyFee;
	}
	public void setDutyFee(String dutyFee) {
		this.dutyFee = dutyFee;
	}
	public String getPoliceOvertimePay() {
		return policeOvertimePay;
	}
	public void setPoliceOvertimePay(String policeOvertimePay) {
		this.policeOvertimePay = policeOvertimePay;
	}

	public String getBigMedicalInsurance() {
		return bigMedicalInsurance;
	}

	public void setBigMedicalInsurance(String bigMedicalInsurance) {
		this.bigMedicalInsurance = bigMedicalInsurance;
	}

	public String getDeductSurcharges() {
		return deductSurcharges;
	}

	public void setDeductSurcharges(String deductSurcharges) {
		this.deductSurcharges = deductSurcharges;
	}
}
