package com.key.dwsurvey.dao.imp;

import com.key.dwsurvey.entity.SurveyStats;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.common.QuType;
import com.key.dwsurvey.dao.SurveyStatsDao;
import com.key.dwsurvey.entity.TQuestion;

/**
 * 问卷统计 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class SurveyStatsDaoImpl extends BaseDaoImpl<SurveyStats, String> implements SurveyStatsDao{

	
	@Override
	public void findStatsDataCross(TQuestion rowQuestion, TQuestion colQuestion) {

		String sql="select yesno_answer,qu_item_id,count(*) from t_an_yesno t1, t_an_radio t2"+ 
							"where t1.qu_id='402880e63dd92431013dd9297ecc0000'"+ 
							"and t2.qu_id='402881c83dbff250013dbff6b2d50000'"+ 
							"and t1.belong_answer_id=t2.belong_answer_id GROUP BY yesno_answer,qu_item_id";
		
		getQuItemName(rowQuestion);
		getQuItemName(colQuestion);
		
		QuType quType=rowQuestion.getQuType();
		if(quType==QuType.RADIO){
			radioRowDataCross(rowQuestion,colQuestion);
		}else if(quType==QuType.CHECKBOX){
			checkboxRowDataCross(rowQuestion,colQuestion);
		}
		
	}
	
	private void checkboxRowDataCross(TQuestion rowQuestion, TQuestion colQuestion) {
		// TODO Auto-generated method stub
		
	}

	private void radioRowDataCross(TQuestion rowQuestion, TQuestion colQuestion) {
		// TODO Auto-generated method stub
		
	}

	private String getQuItemName(TQuestion question){
		String result=null;
		QuType quType=question.getQuType();
		if(quType==QuType.YESNO){
			result="yesno_answer";
		}else if(quType==QuType.RADIO){
			result="qu_item_id";
		}
		return result;
	}
	
}