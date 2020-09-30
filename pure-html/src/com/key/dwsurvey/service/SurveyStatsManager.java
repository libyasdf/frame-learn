package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.entity.SurveyStats;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.SurveyDirectory;

/**
 * 统计报表
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyStatsManager extends BaseService<SurveyStats, String>{

	public SurveyStats findBySurvey(String surveyId);
	public SurveyStats findBySurvey(SurveyDirectory surveyDirectory);
	public SurveyStats findNewStatsData(SurveyDirectory survey);
	public List<TQuestion> findFrequency(SurveyDirectory survey);
	public List<TQuestion> findRowVarQus(SurveyDirectory survey);
	public List<TQuestion> findColVarQus(SurveyDirectory survey);
	public List<DataCross> findStatsDataCross(String rowQuId, String colQuId);
	public List<DataCross> findDataChart(String quId);
	public List<TQuestion> dataChart1s(SurveyDirectory survey);
	public void questionDateCross(TQuestion question) ;
}
