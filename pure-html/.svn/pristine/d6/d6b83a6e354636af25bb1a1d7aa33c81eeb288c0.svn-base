package com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;

/**
 * 试题选项dao层
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:13:44
 */
public interface OptionDao extends BaseRepository<Option,String> {
	
	/**
	 * 自定义查询sql：根据试题id查询对应的试题选项
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月26日 下午6:36:15
	 * @param questionId
	 * @return
	 */
	@Query("select u from Option u where u.visible='1' and u.questionId=?1 order by u.sequence asc")
	public List<Option> getOptionsByQuestionId(String questionId);

	/**
	 * 试卷管理查询试题对应的全部答案
	 * @author 颜振兴
	 * @Date 2018年9月8日 下午3:50:11
	 * @param questionId
	 * @return
	 */
	@Query("from Option u where u.visible='1' and u.questionId=?1 order by u.sequence asc")
	public List<Option> getOptionsByQuestionId1(String questionId);
	
	/**
	 * 根据试题id查询所有正确的选项
	 * @author 王磊
	 * @Date 2018年9月8日 下午3:51:05
	 * @param questionId
	 * @return
	 */
	@Query("from Option u where u.visible='1' and u.isRight = '1' and u.questionId=?1 order by u.sequence asc")
	public List<Option> getRightOptionsByQuestionId(String questionId);
}
