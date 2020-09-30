package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.TQuestion;

import java.util.List;


/**
 * 多选题数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnCheckboxDao extends BaseDao<AnCheckbox, String>{

	public void findGroupStats(TQuestion question);

	public List<DataCross> findStatsDataCross(TQuestion rowQuestion,
                                              TQuestion colQuestion);

	public List<DataCross> findStatsDataChart(TQuestion question);

}
