package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.entity.AnEnumqu;

/**
 * 枚举题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnEnumquDao extends BaseDao<AnEnumqu, String>{

	public void findGroupStats(TQuestion question);

}
