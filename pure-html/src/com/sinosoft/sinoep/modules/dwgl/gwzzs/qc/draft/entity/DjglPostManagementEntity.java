package com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @Title: Entity
 * @Description: djgl_post_management
 * @author onlineGenerator
 * @date 2018-12-25 16:40:55
 * @version V1.0
 *
 */
@Entity
@Table(name = "djgl_post_management")
@SuppressWarnings("serial")
public class DjglPostManagementEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**创建人id*/
	private String creUserId;
	/**创建人名*/
	private String creUserName;
	/**创建人部门id*/
	private String creDeptId;
	/**创建人部门名*/
	private String creDeptName;
	/**创建人处室id*/
	private String creChushiId;
	/**创建人处室名*/
	private String creChushiName;
	/**创建人二级局id*/
	private String creJuId;
	/**创建人二级局名*/
	private String creJuName;
	/**逻辑删除(0:删除;1:存在)*/
	private String visible;
	/**创建时间(精确到时分秒)*/
	private String creTime;
	/**姓名*/
	private String zzsName;
	/**性别*/
	private String zzsSex;
	/**年龄*/
	private String zzsAge;
	/**政治面貌*/
	private String politicsStatus;
	/**单位*/
	private String workUnit;
	/**职位职级*/
	private String positionRank;
	/**党内职务*/
	private String partyPosts;
	/**人员类型*/
	private String jobTitle;
	/**岗位类型*/
	private String postType;
	/**岗位职责*/
	private String responsibilities;
	/**目标任务*/
	private String aimTask;
	/**履行情况*/
	private String degreePerformance;
	/**奖惩情况*/
	private String rewardsPunishment;
	/**考评结果*/
	private String evaluationResults;
	/**本人意见*/
	private String myOpinion;
	/**主管意见*/
	private String competentAdvice;
	/**备注*/
	private String remark;
	/**起草流程状态*/
	private String subflag;
	/**年底流程状态(起草流程状态完结的,再走此)*/
	private String yearSubflag;
	/**职责书年份*/
	private String zzsYear;
	/**版本号,变更后版本+1*/
	private String versionNumber;
	private String cz = "";
	/**业务角色编号 */
	private String businessRoleNum = "";
	@Column(name ="BUSINESS_ROLE_NUM",nullable=true,length=10)
	public String getBusinessRoleNum() {
		return businessRoleNum;
	}
	public void setBusinessRoleNum(String businessRoleNum) {
		this.businessRoleNum = businessRoleNum;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=1000)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人id
	 */

	@Column(name ="CRE_USER_ID",nullable=true,length=1000)
	public String getCreUserId(){
		return this.creUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人id
	 */
	public void setCreUserId(String creUserId){
		this.creUserId = creUserId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名
	 */

	@Column(name ="CRE_USER_NAME",nullable=true,length=1000)
	public String getCreUserName(){
		return this.creUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名
	 */
	public void setCreUserName(String creUserName){
		this.creUserName = creUserName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人部门id
	 */

	@Column(name ="CRE_DEPT_ID",nullable=true,length=1000)
	public String getCreDeptId(){
		return this.creDeptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人部门id
	 */
	public void setCreDeptId(String creDeptId){
		this.creDeptId = creDeptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人部门名
	 */

	@Column(name ="CRE_DEPT_NAME",nullable=true,length=1000)
	public String getCreDeptName(){
		return this.creDeptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人部门名
	 */
	public void setCreDeptName(String creDeptName){
		this.creDeptName = creDeptName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人处室id
	 */

	@Column(name ="CRE_CHUSHI_ID",nullable=true,length=1000)
	public String getCreChushiId(){
		return this.creChushiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人处室id
	 */
	public void setCreChushiId(String creChushiId){
		this.creChushiId = creChushiId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人处室名
	 */

	@Column(name ="CRE_CHUSHI_NAME",nullable=true,length=1000)
	public String getCreChushiName(){
		return this.creChushiName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人处室名
	 */
	public void setCreChushiName(String creChushiName){
		this.creChushiName = creChushiName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人二级局id
	 */

	@Column(name ="CRE_JU_ID",nullable=true,length=1000)
	public String getCreJuId(){
		return this.creJuId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人二级局id
	 */
	public void setCreJuId(String creJuId){
		this.creJuId = creJuId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人二级局名
	 */

	@Column(name ="CRE_JU_NAME",nullable=true,length=1000)
	public String getCreJuName(){
		return this.creJuName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人二级局名
	 */
	public void setCreJuName(String creJuName){
		this.creJuName = creJuName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  逻辑删除(0:删除;1:存在)
	 */

	@Column(name ="VISIBLE",nullable=true,length=1000)
	public String getVisible(){
		return this.visible;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  逻辑删除(0:删除;1:存在)
	 */
	public void setVisible(String visible){
		this.visible = visible;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建时间(要为YYYY-MM-DD形式)
	 */

	@Column(name ="CRE_TIME",nullable=true,length=1000)
	public String getCreTime(){
		return this.creTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建时间(精确到时分秒)
	 */
	public void setCreTime(String creTime){
		this.creTime = creTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */

	@Column(name ="ZZS_NAME",nullable=true,length=1000)
	public String getZzsName(){
		return this.zzsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setZzsName(String zzsName){
		this.zzsName = zzsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */

	@Column(name ="ZZS_SEX",nullable=true,length=1000)
	public String getZzsSex(){
		return this.zzsSex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setZzsSex(String zzsSex){
		this.zzsSex = zzsSex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年龄
	 */

	@Column(name ="ZZS_AGE",nullable=true,length=1000)
	public String getZzsAge(){
		return this.zzsAge;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年龄
	 */
	public void setZzsAge(String zzsAge){
		this.zzsAge = zzsAge;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  政治面貌
	 */

	@Column(name ="POLITICS_STATUS",nullable=true,length=1000)
	public String getPoliticsStatus(){
		return this.politicsStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  政治面貌
	 */
	public void setPoliticsStatus(String politicsStatus){
		this.politicsStatus = politicsStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */

	@Column(name ="WORK_UNIT",nullable=true,length=1000)
	public String getWorkUnit(){
		return this.workUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setWorkUnit(String workUnit){
		this.workUnit = workUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职位职级
	 */

	@Column(name ="POSITION_RANK",nullable=true,length=1000)
	public String getPositionRank(){
		return this.positionRank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职位职级
	 */
	public void setPositionRank(String positionRank){
		this.positionRank = positionRank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  党内职务
	 */

	@Column(name ="PARTY_POSTS",nullable=true,length=1000)
	public String getPartyPosts(){
		return this.partyPosts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  党内职务
	 */
	public void setPartyPosts(String partyPosts){
		this.partyPosts = partyPosts;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人员类型
	 */

	@Column(name ="JOB_TITLE",nullable=true,length=1000)
	public String getJobTitle(){
		return this.jobTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人员类型
	 */
	public void setJobTitle(String jobTitle){
		this.jobTitle = jobTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  岗位类型
	 */

	@Column(name ="POST_TYPE",nullable=true,length=1000)
	public String getPostType(){
		return this.postType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  岗位类型
	 */
	public void setPostType(String postType){
		this.postType = postType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  岗位职责
	 */

	@Column(name ="RESPONSIBILITIES",nullable=true,length=1000)
	public String getResponsibilities(){
		return this.responsibilities;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  岗位职责
	 */
	public void setResponsibilities(String responsibilities){
		this.responsibilities = responsibilities;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目标任务
	 */

	@Column(name ="AIM_TASK",nullable=true,length=1000)
	public String getAimTask(){
		return this.aimTask;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目标任务
	 */
	public void setAimTask(String aimTask){
		this.aimTask = aimTask;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  履行情况
	 */

	@Column(name ="DEGREE_PERFORMANCE",nullable=true,length=1000)
	public String getDegreePerformance(){
		return this.degreePerformance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  履行情况
	 */
	public void setDegreePerformance(String degreePerformance){
		this.degreePerformance = degreePerformance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  奖惩情况
	 */

	@Column(name ="REWARDS_PUNISHMENT",nullable=true,length=1000)
	public String getRewardsPunishment(){
		return this.rewardsPunishment;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  奖惩情况
	 */
	public void setRewardsPunishment(String rewardsPunishment){
		this.rewardsPunishment = rewardsPunishment;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  考评结果
	 */

	@Column(name ="EVALUATION_RESULTS",nullable=true,length=1000)
	public String getEvaluationResults(){
		return this.evaluationResults;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  考评结果
	 */
	public void setEvaluationResults(String evaluationResults){
		this.evaluationResults = evaluationResults;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  本人意见
	 */

	@Column(name ="MY_OPINION",nullable=true,length=1000)
	public String getMyOpinion(){
		return this.myOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  本人意见
	 */
	public void setMyOpinion(String myOpinion){
		this.myOpinion = myOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主管意见
	 */

	@Column(name ="COMPETENT_ADVICE",nullable=true,length=1000)
	public String getCompetentAdvice(){
		return this.competentAdvice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主管意见
	 */
	public void setCompetentAdvice(String competentAdvice){
		this.competentAdvice = competentAdvice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=1000)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起草流程状态
	 */

	@Column(name ="SUBFLAG",nullable=true,length=1000)
	public String getSubflag(){
		return this.subflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起草流程状态
	 */
	public void setSubflag(String subflag){
		this.subflag = subflag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年底流程状态(起草流程状态完结的,再走此)
	 */

	@Column(name ="YEAR_SUBFLAG",nullable=true,length=1000)
	public String getYearSubflag(){
		return this.yearSubflag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年底流程状态(起草流程状态完结的,再走此)
	 */
	public void setYearSubflag(String yearSubflag){
		this.yearSubflag = yearSubflag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职责书年份
	 */

	@Column(name ="ZZS_YEAR",nullable=true,length=1000)
	public String getZzsYear(){
		return this.zzsYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职责书年份
	 */
	public void setZzsYear(String zzsYear){
		this.zzsYear = zzsYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本号,变更后版本+1
	 */

	@Column(name ="VERSION_NUMBER",nullable=true,length=1000)
	public String getVersionNumber(){
		return this.versionNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本号,变更后版本+1
	 */
	public void setVersionNumber(String versionNumber){
		this.versionNumber = versionNumber;
	}

	@Transient
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public DjglPostManagementEntity(){ super();}
}