package com.key.dwsurvey.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.key.common.base.entity.IdEntity;
import com.sinosoft.sinoep.common.util.DateUtil;

/**
 * 问卷目录及问卷
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_directory")
public class SurveyDirectory extends IdEntity{
	
	//用于短链接的ID
	private String sid;
	private String parentId="";
	private String surveyName;
	private String wenjuanType;//问卷类型：比如学习问卷、或饮食问卷
	//创建者ID
	private String userId;
	//1目录，2问卷
	private Integer dirType=1;
	//所对应的问卷详细信息表Id  当dirType=2
	private String surveyDetailId;
	//创建时间
	private Date createDate=new Date();
	private String createDate1;//用于在列表页面 的排序
	//问卷状态  0默认设计状态  1执行中 2结束 
	private Integer surveyState=0;
	//问卷下面有多少题目数 
	private Integer surveyQuNum=0;
	//可以回答的最少选项数目 
	private Integer anItemLeastNum=0;
	//回答次数
	private Integer answerNum;
	//是否显示  1显示 0不显示
	private Integer visibility=1;

	//问卷所属的问卷模块   1问卷模块
	private Integer surveyModel=1;
	//是否公开结果  0不  1公开
	private Integer viewAnswer=0;
	//是否共享问卷  0不共享 1共享 
	private Integer isShare=1;
	//引用次数
	private Integer excerptNum=0;
	//问卷标识 默认 0待审核  1审核通过  2审核未通过
	private Integer surveyTag=1;
	
	//静态HTML保存路径
	private String htmlPath;

	private String deptState;

	private String subflag;
	
	private String cz;
	
	@Transient
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getCreateDate1() {
		return createDate1;
	}
	public void setCreateDate1(String createDate1) {
		this.createDate1 = createDate1;
	}



	private String creDateLable;
	
	@Transient
	public String getCreDateLable() {
		return DateUtil.getDateText(createDate,"yyyy-MM-dd");
		
	}

	public void setCreDateLable(String creDateLable) {
		this.creDateLable = creDateLable;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public String getWenjuanType() {
		return wenjuanType;
	}

	public void setWenjuanType(String wenjuanType) {
		this.wenjuanType = wenjuanType;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public Integer getDirType() {
		return dirType;
	}

	public void setDirType(Integer dirType) {
		this.dirType = dirType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public String getSurveyDetailId() {
		return surveyDetailId;
	}

	public void setSurveyDetailId(String surveyDetailId) {
		this.surveyDetailId = surveyDetailId;
	}
	public Integer getSurveyState() {
		return surveyState;
	}

	public void setSurveyState(Integer surveyState) {
		this.surveyState = surveyState;
	}
	public Integer getSurveyQuNum() {
		return surveyQuNum;
	}

	public void setSurveyQuNum(Integer surveyQuNum) {
		this.surveyQuNum = surveyQuNum;
	}

	public Integer getAnItemLeastNum() {
		if(anItemLeastNum==null){
			return 0;
		}
		return anItemLeastNum;
	}

	public void setAnItemLeastNum(Integer anItemLeastNum) {
		this.anItemLeastNum = anItemLeastNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSurveyTag() {
		return surveyTag;
	}

	public void setSurveyTag(Integer surveyTag) {
		this.surveyTag = surveyTag;
	}

	public Integer getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public Integer getSurveyModel() {
		return surveyModel;
	}

	public void setSurveyModel(Integer surveyModel) {
		this.surveyModel = surveyModel;
	}

	public Integer getViewAnswer() {
		return viewAnswer;
	}

	public void setViewAnswer(Integer viewAnswer) {
		this.viewAnswer = viewAnswer;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}


	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}


	public String groupName;
	@Transient
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	private SurveyDetail surveyDetail=null;
	@Transient
	public SurveyDetail getSurveyDetail() {
		return surveyDetail;
	}

	public void setSurveyDetail(SurveyDetail surveyDetail) {
		this.surveyDetail = surveyDetail;
	}
	
	public Integer getExcerptNum() {
		return excerptNum;
	}

	public void setExcerptNum(Integer excerptNum) {
		this.excerptNum = excerptNum;
	}


	//用户名
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private List<TQuestion> questions=null;
	@Transient
	public List<TQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<TQuestion> questions) {
		this.questions = questions;
	}

	public String getDeptState() {
		return deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}

    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }
}
