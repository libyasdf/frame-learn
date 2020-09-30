package com.key.dwsurvey.dao.imp;

import com.key.dwsurvey.entity.AnFillblank;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.dao.AnFillblankDao;
import com.key.dwsurvey.entity.TQuestion;

/**
 * 填空 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnFillblankDaoImpl extends BaseDaoImpl<AnFillblank, String> implements AnFillblankDao{

	@Override
	public void findGroupStats(TQuestion question) {
		String sql="select count(case when answer='' then 1 else 0 end) emptyCount, count(case when answer!='' then 1 else 0  end) blankCount from t_an_fillblank where  visibility=1 and  qu_id=?";
		Object[] objs=(Object[]) this.getSession().createSQLQuery(sql).setString(0, question.getId()).uniqueResult();
		
		question.setRowContent(objs[0].toString());//未回答数
		question.setOptionContent(objs[1].toString());//回答的项数
		question.setAnCount(Integer.parseInt(objs[1].toString()));
	}

}
