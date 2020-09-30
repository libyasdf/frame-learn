package com.sinosoft.sinoep.modules.zhbg.xxkh.question.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.OptionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.QuestionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 试题service实现类
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:22:47
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//试题dao
	@Autowired
	private QuestionDao questionDao;
	
	//试题选项dao
	@Autowired
	private OptionDao optionDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 试题保存
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:12
	 * @param questionInfo
	 * @return
	 */
	@Override
	public Question saveForm(Question questionInfo) {
		if(StringUtils.isBlank(questionInfo.getId())){
			//新增
			//设置常用字段开始
			questionInfo.setCreJuId(UserUtil.getCruJuId());
			questionInfo.setCreJuName(UserUtil.getCruJuName());
			questionInfo.setCreChushiId(UserUtil.getCruChushiId());
			questionInfo.setCreChushiName(UserUtil.getCruChushiName());
			questionInfo.setCreDeptId(UserUtil.getCruDeptId());
			questionInfo.setCreDeptName(UserUtil.getCruDeptName());
			questionInfo.setCreUserId(UserUtil.getCruUserId());
			questionInfo.setCreUserName(UserUtil.getCruUserName());
			questionInfo.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			questionInfo.setUpdateUserId(UserUtil.getCruJuId());
			questionInfo.setUpdateUserName(UserUtil.getCruJuName());
			questionInfo.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			questionInfo.setVisible(CommonConstants.VISIBLE[1]);
			//设置常用字段结束
			questionInfo.setState(XXKHCommonConstants.RECORD_STATE[0]);
			//保存试题
			Question  oldQuestion = questionDao.save(questionInfo);
			//保存试题对应的选项
			for(Option op : questionInfo.getList()){
				//设置常用字段开始
				op.setCreJuId(UserUtil.getCruJuId());
				op.setCreJuName(UserUtil.getCruJuName());
				op.setCreChushiId(UserUtil.getCruChushiId());
				op.setCreChushiName(UserUtil.getCruChushiName());
				op.setCreDeptId(UserUtil.getCruDeptId());
				op.setCreDeptName(UserUtil.getCruDeptName());
				op.setCreUserId(UserUtil.getCruUserId());
				op.setCreUserName(UserUtil.getCruUserName());
				op.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				op.setUpdateUserId(UserUtil.getCruJuId());
				op.setUpdateUserName(UserUtil.getCruJuName());
				op.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				op.setVisible(CommonConstants.VISIBLE[1]);
				//设置常用字段结束
				//设置关联
				op.setQuestionId(oldQuestion.getId());
			}
			optionDao.save(questionInfo.getList());
			return oldQuestion;
		}else{
			//修改
			//更新试题内容
			Question oldQuestion = questionDao.findOne(questionInfo.getId());
			//更新修改人和修改时间开始
			oldQuestion.setUpdateUserId(UserUtil.getCruJuId());
			oldQuestion.setUpdateUserName(UserUtil.getCruJuName());
			oldQuestion.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			//更新修改人和修改时间结束
			//修改试题分析、题干内容、难易程度、题型
			oldQuestion.setAnalysis(questionInfo.getAnalysis());
			oldQuestion.setDescribe(questionInfo.getDescribe());
			oldQuestion.setDifficultyLevel(questionInfo.getDifficultyLevel());
			oldQuestion.setQuestionType(questionInfo.getQuestionType());
			oldQuestion = questionDao.save(oldQuestion);
			
			//更新试题选项内容：1、逻辑删除之前的选项；2、插入新的选项
			String delOption = "update Option o set o.visible="+CommonConstants.VISIBLE[0]+" where o.questionId='"+oldQuestion.getId()+"'";
			optionDao.update(delOption);
			//保存试题对应的选项
			for(Option op : questionInfo.getList()){
				//设置常用字段开始
				op.setCreJuId(UserUtil.getCruJuId());
				op.setCreJuName(UserUtil.getCruJuName());
				op.setCreChushiId(UserUtil.getCruChushiId());
				op.setCreChushiName(UserUtil.getCruDeptName());
				op.setCreDeptId(UserUtil.getCruDeptId());
				op.setCreDeptName(UserUtil.getCruDeptName());
				op.setCreUserId(UserUtil.getCruUserId());
				op.setCreUserName(UserUtil.getCruUserName());
				op.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				op.setUpdateUserId(UserUtil.getCruJuId());
				op.setUpdateUserName(UserUtil.getCruJuName());
				op.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				op.setVisible(CommonConstants.VISIBLE[1]);
				//设置常用字段结束
				//设置关联
				op.setQuestionId(oldQuestion.getId());
			}
			optionDao.save(questionInfo.getList());
			return oldQuestion;
		}
	}

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
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, Question questionInfo) {
		
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" from Question t ");
		//querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		querySql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		//para.add(UserUtil.getCruChushiId());
		
		// 题干
		if (StringUtils.isNotBlank(questionInfo.getDescribe())) {
			querySql.append(" and t.describe like ? ");
			para.add("%"+questionInfo.getDescribe()+"%");
		}
		
		// 难是否可用
		if (StringUtils.isNotBlank(questionInfo.getState()) && !"0".equals(questionInfo.getState())) {
			querySql.append(" and t.state = ?");
			para.add(questionInfo.getState());
		}
		
		
		// 题型
		if (StringUtils.isNotBlank(questionInfo.getQuestionType()) && !"0".equals(questionInfo.getQuestionType())) {
			querySql.append(" and t.questionType = ?");
			para.add(questionInfo.getQuestionType());
		}
		
		// 难易程度
		if (StringUtils.isNotBlank(questionInfo.getDifficultyLevel()) && !"0".equals(questionInfo.getDifficultyLevel())) {
			querySql.append(" and t.difficultyLevel = ?");
			para.add(questionInfo.getDifficultyLevel());
		}
				
		// 试题所在小类
		if (StringUtils.isNotBlank(questionInfo.getNodeId())) {
			querySql.append(" and t.nodeId = ?");
			para.add(questionInfo.getNodeId());
		}else{
			querySql.append(" and t.nodeId = null");
		}
		
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append(" order by t.creTime desc ");
		} else {
			querySql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<Question> page = questionDao.query(querySql.toString(), pageable, para.toArray());
		// 添加列表操作
		List<Question> content = page.getContent();
		for (Question question : content) {
			if (XXKHCommonConstants.RECORD_STATE[0].equals(question.getState())) {
				question.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id获得一个试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:18:58
	 * @param questionInfo
	 * @return
	 */
	@Override
	public Question getById(Question questionInfo) {
		if(StringUtils.isNotBlank(questionInfo.getId())){
			questionInfo = questionDao.findOne(questionInfo.getId());
			//设置该试题所有的选项
			questionInfo.setList(optionDao.getOptionsByQuestionId(questionInfo.getId()));
		}
		return questionInfo;
	}

	/**
	 * 根据id逻辑删除一个试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:20:48
	 * @param questionInfo
	 * @return
	 */
	@Override
	public int delete(Question questionInfo) {
		int n = 0;
		if(StringUtils.isNotBlank(questionInfo.getId())){
			try {
				//逻辑删除试题
				//防止sql注入 王磊20190426
				String delQuestion = "update Question q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id= ? ";
				n = questionDao.update(delQuestion,questionInfo.getId());
				//逻辑删除试题对应的选项
				String delOption = "update Option o set o.visible='"+CommonConstants.VISIBLE[0]+"' where o.questionId= ? ";
				optionDao.update(delOption,questionInfo.getId());
			} catch (Exception e) {
				log.info(e.getMessage());
				log.info("删除试题出现异常！");
				n=0;
			}
		}
		return n;
	}

	/**
	 * 更新试题状态为1：已提交
	 * @author 王磊
	 * @Date 2018年7月25日 下午4:21:42
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public int commitQuestionById(String qId) {
		int n = 0;
		try {
			//防止sql注入 王磊 20190426
			String commitSql = "update Question q set q.state='"+XXKHCommonConstants.RECORD_STATE[1]+"' where q.id= ?";
			n = questionDao.update(commitSql,qId);
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("提交试题发生异常");
		}
		return n;
	}

	@Override
	public Question getOne(String id) {
		Question one = questionDao.getById(id).get(0);
		one.setList(null);
		return one;
		
	}

	@Override
	public List<Question> radomList(Question question,String num) {
		return questionDao.radomList(question,num);
	}

	@Override
	public int getQuestionCount(Question question) {
		return questionDao.getQuestionCount(question.getDifficultyLevel(), question.getQuestionType(), question.getType());
	}

	//获取部门所对应的试题数
	@Override
	public int getQuestionJuCount(Question question) {
		return questionDao.getQuestionJuCount(question.getDifficultyLevel(), question.getQuestionType(), question.getType());
	}

	/**
	 * 试题列表批量提交试题
	 * @author 王磊
	 * @Date 2018年9月20日 下午4:29:03
	 * @param ids 从前台传过来的试题id串
	 * @return 更新条数
	 */
	@Override
	public int commitSelected(String ids) {
		int n = 0;
		try {
			List<Object> list = new ArrayList<>();
			String commitSql = "update Question q set q.state='"+XXKHCommonConstants.RECORD_STATE[1]+"' where q.id in("+CommonUtils.solveSqlInjectionOfIn(ids, list)+")";
			log.info("批量提交试题sql==="+commitSql);
			n = questionDao.update(commitSql,list.toArray());
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("提交试题发生异常");
		}
		return n;
	}

}
