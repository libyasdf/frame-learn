package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.OptionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 试题选项service实现类
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:22:47
 */
@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionDao optionDao;
	
	/**
	 * 保存试题选项
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:12
	 * @param questionInfo
	 * @return
	 */
	@Override
	public Question saveForm(List<Option> optionList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据试题id获得该题全部的试题选项
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:17:46
	 * @param pageable
	 * @param pageImpl
	 * @param questionInfo
	 * @return
	 */
	@Override
	public List<Option> getOptionsById(String id) {
		List<Option> oList = new ArrayList<Option>();
		if(StringUtils.isNotBlank(id)){
			oList = optionDao.getOptionsByQuestionId(id);
		}
		return oList;
	}

	/**
	 * 根据试题id获得该试题全部正确的试题选项（参加考试判分用）
	 * 判断答题是否正确规则：
	 * 1.单选、多选、判断根据返回对象的sequence属性来判断，例如单选（选项顺序是1、2、3、4分别对应ABCD）。
	 * 2.填空题根据返回对象的content属性来判断
	 * @author 王磊
	 * @Date 2018年9月8日 下午3:34:25
	 * @param id
	 * @return
	 */
	@Override
	public List<Option> getRightOptionsById(String id) {
		List<Option> oList = new ArrayList<Option>();
		if(StringUtils.isNotBlank(id)){
			oList = optionDao.getRightOptionsByQuestionId(id);
		}
		return oList;
	}

}
