package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.entity.SurveyDirectory;

/**
 * 题基础
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuestionManager extends BaseService<TQuestion, String>{

	public TQuestion getDetail(String quId);
	
	public TQuestion getDetail1(String quId);
	
	public List<TQuestion> find(String belongId,String tag);
	
	public List<TQuestion> findDetails(String belongId,String tag);
	
	public void getQuestionOption(TQuestion question);
	
	public List<TQuestion> findByParentQuId(String parentQuId);
	
	public List<TQuestion> findByQuIds(String[] quIds, boolean b);
	
	public void deletes(String[] delQuIds);
	//交接排序位置--前台交换
	public boolean upsort(String prevId, String nextId);
	
	public void saveBySurvey(String belongId  ,int tag, List<TQuestion> questions);
	
	public void saveChangeQu(String belongId, int tag, String[] quIds);

	public List<TQuestion> findStatsRowVarQus(SurveyDirectory survey);

	public List<TQuestion> findStatsColVarQus(SurveyDirectory survey);
	
	public void update(TQuestion entity);

	public TQuestion findById(String quId);
	
}
