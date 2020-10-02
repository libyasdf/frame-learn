package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.entity.DataCross;

/**
 * 单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnRadioManager extends BaseService<AnRadio, String>{
	public AnRadio findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(TQuestion question);

	public List<DataCross> findStatsDataCross(TQuestion rowQuestion,
			TQuestion colQuestion);

	public List<DataCross> findStatsDataChart(TQuestion question);
}
