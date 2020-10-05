package com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.QuestionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.dao.XxkhQuestionQgroupDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
@Transactional
public class XxkhQuestionQgroupServiceImpl implements XxkhQuestionQgroupService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private XxkhQuestionQgroupDao dao;

	@Autowired
	private QuestionDao questionDao;

	@Override
	public PageImpl list(PageImpl pageImpl, XxkhQuestionQgroup xxkhQuestionQgroup, Pageable pageable) {
		StringBuilder jpql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		jpql.append(
				" select new  com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question(t.id,q.nodeId,q.difficultyLevel,q.questionType,q.describe,t.creUserName,t.creTime)");
		jpql.append(" from XxkhQuestionQgroup t,Question q");
		jpql.append(" where t.questionId=q.id and t.questionGroupId =? ");
		jpql.append(" order by t.creTime desc ");
		params.add(xxkhQuestionQgroup.getQuestionGroupId());
		Page<Question> query = questionDao.query(jpql.toString(), pageable, params.toArray());
		pageImpl.getData().setRows(query.getContent());
		pageImpl.getData().setTotal((int) query.getTotalElements());
		return pageImpl;
	}

	@Override
	public XxkhQuestionQgroup save(XxkhQuestionQgroup xxkhQuestionQgroup) {
		String[] questionIds = xxkhQuestionQgroup.getQuestionId().split(",");
		if (questionIds.length > 1) {
			delete(xxkhQuestionQgroup);
			for (String questionId : questionIds) {
				XxkhQuestionQgroup qgroup = new XxkhQuestionQgroup();
				qgroup.setQuestionId(questionId);
				qgroup.setQuestionGroupId(xxkhQuestionQgroup.getQuestionGroupId());
				qgroup.setCreJuId(UserUtil.getCruJuId());
				qgroup.setCreJuName(UserUtil.getCruJuName());
				qgroup.setCreUserId(UserUtil.getCruUserId());
				qgroup.setCreUserName(UserUtil.getCruUserName());
				qgroup.setCreTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
				dao.save(qgroup);
			}
		}else {
			xxkhQuestionQgroup.setCreJuId(UserUtil.getCruJuId());
			xxkhQuestionQgroup.setCreJuName(UserUtil.getCruJuName());
			xxkhQuestionQgroup.setCreUserId(UserUtil.getCruUserId());
			xxkhQuestionQgroup.setCreUserName(UserUtil.getCruUserName());
			xxkhQuestionQgroup.setCreTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
			dao.save(xxkhQuestionQgroup);
		}
		return xxkhQuestionQgroup;
	}

	@Override
	public int delete(XxkhQuestionQgroup xxkhQuestionQgroup) {
		String[] questionIds = xxkhQuestionQgroup.getQuestionId().split(",");
		for (String questionId : questionIds) {
			try {
				dao.deleteByTiIdAndZuId(questionId, xxkhQuestionQgroup.getQuestionGroupId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	@Override
	public XxkhQuestionQgroup isCheck(String tizhu, String ti) {
		List<XxkhQuestionQgroup> check = dao.isCheck(tizhu, ti);
		if (check.size() > 0) {
			return check.get(0);
		} else {
			return null;
		}

	}

	@Override
	public int deleteti(XxkhQuestionQgroup xxkhQuestionQgroup) {
		try {
			dao.delete(xxkhQuestionQgroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return 1;
	}

	@Override
	public XxkhQuestionQgroup autoSave(XxkhQuestionQgroup qgroup) {
		qgroup.setCreJuId(UserUtil.getCruJuId());
		qgroup.setCreJuName(UserUtil.getCruJuName());
		qgroup.setCreUserId(UserUtil.getCruUserId());
		qgroup.setCreUserName(UserUtil.getCruUserName());
		qgroup.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		return dao.save(qgroup);
	}


}
