package com.key.dwsurvey.dao;

import java.util.List;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnYesno;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.TQuestion;

/**
 * 是非题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnYesnoDao extends BaseDao<AnYesno, String>{

	public void findGroupStats(TQuestion question);

	public List<DataCross> findStatsDataCross(TQuestion rowQuestion, TQuestion colQuestion);

	public List<DataCross> findStatsDataChart(TQuestion question);

}
