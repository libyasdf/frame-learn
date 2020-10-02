package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 试题选项service
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:15:55
 */
public interface OptionService {
	
	/**
	 * 保存试题选项
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:12
	 * @param questionInfo
	 * @return
	 */
	Question saveForm(List<Option> optionList);

	/**
	 * 根据试题id获得该题全部的试题选项
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:17:46
	 * @param pageable
	 * @param pageImpl
	 * @param questionInfo
	 * @return
	 */
	List<Option> getOptionsById(String id);
	
	/**
	 * 根据试题id获得该试题全部正确的试题选项（参加考试判分用）
	 * @author 王磊
	 * @Date 2018年9月8日 下午3:34:25
	 * @param id
	 * @return
	 */
	List<Option> getRightOptionsById(String id);

}
