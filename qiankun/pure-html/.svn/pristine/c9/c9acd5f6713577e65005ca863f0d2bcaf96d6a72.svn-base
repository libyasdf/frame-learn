package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.TQuestion;

/**
 * 多选题业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface AnCheckboxManager extends BaseService<AnCheckbox, String>{
	public List<AnCheckbox> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(TQuestion question);

	public List<DataCross> findStatsDataCross(TQuestion rowQuestion,
			TQuestion colQuestion);

	public List<DataCross> findStatsDataChart(TQuestion question);
}
