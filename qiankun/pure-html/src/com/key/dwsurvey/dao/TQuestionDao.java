package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.TQuestion;

/**
 * 题基础 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface TQuestionDao extends BaseDao<TQuestion, String>{
	
	public void update(TQuestion entity);
	public void quOrderByIdDel1(String belongId,Integer orderById);
}
