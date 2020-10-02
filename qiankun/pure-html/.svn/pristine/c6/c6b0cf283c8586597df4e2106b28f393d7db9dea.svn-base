package com.key.dwsurvey.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.sinosoft.sinoep.common.util.PageImpl;

/**
 * 问卷处理
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyDirectoryManager extends BaseService<SurveyDirectory, String>{

	/**
	 * 根据 最底层对象，得到此对象所在的目录结构
	 * @param surveyDirectory
	 * @return
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory);
	
	public SurveyDirectory getSurvey(String id);
	
	public SurveyDirectory getSurveyBySid(String sId);

	public SurveyDirectory getSurveyByUser(String id,String userId); 
	
	public void getSurveyDetail(String id,SurveyDirectory directory);
	
	public void upSurveyData(SurveyDirectory entity);

	public void executeSurvey(SurveyDirectory entity);

	public void closeSurvey(SurveyDirectory entity);

	public SurveyDirectory findByNameUn(String id,String parentId, String surveyName);

	public void backDesign(SurveyDirectory entity);

//	public void save(SurveyDirectory entity, String[] surGroupIds);

//	public void saveUserSurvey(SurveyDirectory entity, String[] surGroupIds);
	
	public void saveUser(SurveyDirectory t);
	
	public void saveUserSurvey(SurveyDirectory entity);

	public SurveyDirectory findByNameUserUn(String id, String surveyName);

	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,
			SurveyDirectory entity);

	public List<SurveyDirectory> newSurveyList();

	public void upSuveyText(SurveyDirectory entity);

	public void checkUp(SurveyDirectory surveyDirectory);

	public SurveyDirectory findNext(SurveyDirectory directory);
	
	public void saveAll(SurveyDirectory directory);

	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page,SurveyDirectory surveyDirectory);
	
	public Page<SurveyDirectory> findByGroup(String groupId1,String groupId2,Page<SurveyDirectory> page);

	public List<SurveyDirectory> findByIndex();
	
	public List<SurveyDirectory> findByT1();
	
	public void saveByAdmin(SurveyDirectory t);

	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
			SurveyDirectory entity);

	public SurveyDirectory createBySurvey(String fromBankId, String surveyName,
										  String tag);
	/**
	 * 调查问卷的分页列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午7:00:39
	 * @param pageable
	 * @param pageImpl
	 * @param wenjuanType
	 * @return
	 */
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String wenjuanType,Integer surveyState,String startDate,String endDate,String surveyName)throws Exception ;


	/**
	 * 调查问卷的分页列表
	 * TODO
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午7:00:39
	 * @param pageable
	 * @param pageImpl
	 * @param wenjuanType
	 * @return
	 */
	public PageImpl getGenalsListDraft(Pageable pageable, PageImpl pageImpl, String wenjuanType,Integer surveyState,String startDate,String endDate,String surveyName)throws Exception ;

	/**
	 * 根据id查询调查问卷
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午8:35:10
	 * @param surveyId
	 * @return
	 */
	public SurveyDirectory findById(String surveyId);

	public void deleteById(String id);

	/**
	 * 查询当前用户收到的调查问卷
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月27日 下午9:25:38
	 * @param pageImpl
	 * @param type
	 * @param timeRange
	 * @return
	 */
	public PageImpl getSurveyList(PageImpl pageImpl,String surveyName, String type, String startDate, String endDate) throws Exception;

	public void stopCollect(String id);

	/**
	 * 重新收集
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月27日 下午5:07:50
	 * @param id
	 */
	public void startCollect(String id);

	public boolean getIsComplete(String surveyId) throws Exception;
	
}
