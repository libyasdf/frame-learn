package com.key.dwsurvey.service;

import com.key.dwsurvey.entity.SurveyEntiy;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODO 
 * @author 杜建松
 * @Date 2018年8月17日 下午2:27:07
 */
public interface SurveyEntiyService {

	/**
	 * TODO 保存通知通告
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:29:06
	 * @return
	 */
	SurveyEntiy saveSurvey(SurveyEntiy surveyEntiy) throws Exception;

	/**
	 * TODO 更新通知通告状态位
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:30:44
	 * @param id
	 * @param flag
	 * @return
	 */
	SurveyEntiy updateFlag(String id, String flag) throws Exception;


	/**
	 * TODO 删除通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:47:41
	 * @return
	 */
	Boolean delSurvey(String surveyId) throws Exception;
	
	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:05:48
	 * @return
	 */
	SurveyEntiy getView(String surveyId) throws Exception;

	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:05:48
	 * @return
	 */
	SurveyEntiy getIdView(String id) throws Exception;

	
}
