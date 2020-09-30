package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 试题service
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:15:55
 */
public interface QuestionService {
	
	/**
	 * 试题保存
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:12
	 * @param questionInfo
	 * @return
	 */
	Question saveForm(Question questionInfo);

	/**
	 * 试题列表查询（带分页）
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:17:46
	 * @param pageable
	 * @param pageImpl
	 * @param questionInfo
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, Question questionInfo);

	/**
	 * 根据id获得一个试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:58
	 * @param questionInfo
	 * @return
	 */
	Question getById(Question questionInfo);

	/**
	 * 根据id逻辑删除一个试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:20:48
	 * @param questionInfo
	 * @return
	 */
	int delete(Question questionInfo);

	/**
	 * 更新试题状态为1：已提交
	 * 提交后将不能操作
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:21:42
	 * @param id
	 * @param subflag
	 * @return
	 */
	int commitQuestionById(String qId);
	
	/**
	 * 
	 * @Title: QuestionService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services
	 * @Description: TODO 根据id查一条试题
	 * @author 颜振兴
	 * @date 2018年8月9日
	 * @param id
	 * @return
	 */
	Question getOne(String id);
	
	/**
	 * 
	 * @Title: QuestionService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services
	 * @Description: TODO随机获取数据库的几条数据
	 * @author 颜振兴
	 * @date 2018年8月16日
	 * @param num
	 * @return
	 */
	List<Question> radomList(Question question,String num);
	
	/**
	 * 
	 * @Title: QuestionService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services
	 * @Description: TODO获取试题数量
	 * @author 颜振兴
	 * @date 2018年8月16日
	 * @param difficultyLevel
	 * @param questionType
	 * @param type
	 * @return
	 */
	int getQuestionCount(Question question);

	int getQuestionJuCount(Question question);
	
	/**
	 * 试题列表批量提交试题
	 * @author 王磊
	 * @Date 2018年9月20日 下午4:28:21
	 * @param ids 从前台传过来的试题id串
	 * @return 更新条数
	 */
	int commitSelected(String ids);
}
