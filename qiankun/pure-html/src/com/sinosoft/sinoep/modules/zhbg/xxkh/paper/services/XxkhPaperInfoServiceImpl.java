package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao.XxkhPaperInfoDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.OptionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.QuestionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao.XxkhQuestionGroupDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 
 * @author 颜振兴
 * 时间：2018年8月21日
 *	XxkhPaperInfoServiceImpl
 */
@Service
@Transactional
public class XxkhPaperInfoServiceImpl implements XxkhPaperInfoService {
	
	@Autowired
	private XxkhPaperInfoDao dao;
	
	@Autowired
	private XxkhQuestionGroupDao xxkhQuestionGroupDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Override
	public Page<XxkhPaperInfo> list(final XxkhPaperInfo info,final PageImpl pageImpl) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		return dao.findAll(new Specification<XxkhPaperInfo>() {
			@Override
			public Predicate toPredicate(Root<XxkhPaperInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType =  cb.equal(root.get("type").as(String.class), info.getType());
				
				predicates.add(pVisible);
				predicates.add(pType);
				
				Predicate pNodeId = null,pName=null,pDifficultyLevel=null,pState=null,pIsShare=null;
				Order desc=null;
				if (StringUtils.isBlank(info.getIsDetp())) {
					Predicate pcreJuId =  cb.equal(root.get("creJuId").as(String.class), info.getCreJuId());
					predicates.add(pcreJuId);
				}
				if(StringUtils.isNotBlank(info.getNodeId())) {
					pNodeId = cb.equal(root.get("nodeId").as(String.class), info.getNodeId());
					predicates.add(pNodeId);		
				}
				if(StringUtils.isNotBlank(info.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%"+info.getName()+"%");
					predicates.add(pName);		
				}
				if(StringUtils.isNotBlank(info.getDifficultyLevel())) {
					pDifficultyLevel = cb.equal(root.get("difficultyLevel").as(String.class), info.getDifficultyLevel());
					predicates.add(pDifficultyLevel);		
				}
				if(StringUtils.isNotBlank(info.getState())) {
					pState = cb.equal(root.get("state").as(String.class), info.getState());
					predicates.add(pState);		
				}
				if(StringUtils.isNotBlank(info.getIsShare())) {
					pIsShare = cb.equal(root.get("isShare").as(String.class), info.getIsShare());
					predicates.add(pIsShare);		
				}
				if (StringUtils.isBlank(pageImpl.getSortName())) {
					desc = cb.desc(root.get("creTime").as(String.class));	
				}else{
					if (pageImpl.getSortOrder().equals("desc")) {
						desc = cb.desc(root.get(pageImpl.getSortName()).as(String.class));	
					}
					if (pageImpl.getSortOrder().equals("asc")) {
						desc = cb.asc(root.get(pageImpl.getSortName()).as(String.class));	
					}
				}
				
				query.where(cb.and(predicates.toArray(new Predicate[0])));
				query.orderBy(desc);
				return query.getRestriction();
			};
		}, pageable) ;
	}

	@Override
	public XxkhPaperInfo save(XxkhPaperInfo info) {
		info.setCreUserId(UserUtil.getCruUserId());
		info.setCreUserName(UserUtil.getCruUserName());
		info.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		info.setCreJuId(UserUtil.getCruJuId());
		info.setCreJuName(UserUtil.getCruJuName());
		info.setVisible(CommonConstants.VISIBLE[1]);
		info.setFullScore("0");
		return dao.saveAndFlush(info);
	}

	@Override
	public XxkhPaperInfo update(XxkhPaperInfo info) {
		XxkhPaperInfo old = getOne(info);
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		old.setRemark(info.getRemark());
		old.setDeptType(info.getDeptType());
		if(StringUtils.isNotBlank(info.getDifficultyLevel()))old.setDifficultyLevel(info.getDifficultyLevel());
		if(StringUtils.isNotBlank(info.getName()))old.setName(info.getName());
		if(StringUtils.isNotBlank(info.getIsShare()))old.setIsShare(info.getIsShare());
		if(StringUtils.isNotBlank(info.getFullScore()))old.setFullScore(info.getFullScore());
		if(StringUtils.isNotBlank(info.getState()))old.setState(info.getState());
		if(StringUtils.isNotBlank(info.getCreateType()))old.setCreateType(info.getCreateType());
		return dao.saveAndFlush(old);
	}
	
	
	@Override
	public int delete(XxkhPaperInfo info) {
		return dao.delVisible(info.getId());
	}
	
	@Override
	public XxkhPaperInfo updateFenShu(XxkhPaperInfo info) {
		XxkhPaperInfo old = getOne(info);
		old.setFullScore(info.getFullScore());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dao.saveAndFlush(old);
	}

	@Override
	public XxkhPaperInfo getOne(XxkhPaperInfo info) {
		XxkhPaperInfo findOne = dao.getById(info.getId()).get(0);
		List<XxkhQuestionGroup> byList = xxkhQuestionGroupDao.getList(findOne.getId());//byList(findOne.getId());
		findOne.setGroup(byList);
		for(XxkhQuestionGroup group : byList) {
			List<Question> questions = questionDao.getList(group.getId());
			if (questions!=null&&questions.size()>0) {
				group.setQuestion(questions);
				for(Question question : questions) {
					List<Option> options = questionDao.getOptionList(question.getId());
					if(options!=null&&options.size()>0) {
						question.setList(options);
					}
				}
			}
		}
		return findOne;
	}

	@Override
	public XxkhPaperInfo getAutoOne(XxkhPaperInfo info) {
		XxkhPaperInfo findOne = dao.getById(info.getId()).get(0);
		List<XxkhQuestionGroup> byList = xxkhQuestionGroupDao.byList(findOne.getId());
		findOne.setGroup(byList);
		for(XxkhQuestionGroup group : byList) {
			List<Question> questions = questionDao.getList(group.getId());
			if (questions!=null&&questions.size()>0) {
				group.setQuestion(questions);
				for(Question question : questions) {
					List<Option> options = questionDao.getOptionList(question.getId());
					if(options!=null&&options.size()>0) {
						question.setList(options);
					}
				}
			}
		}
		return findOne;
	}

	@Override
	public int isCanUpdate(String id) {
		List<XxkhTestInfo> canUpdata = dao.isCanUpdata(id,1);
		if (canUpdata.size()>0) {
			return 2;
		}
		List<XxkhTestInfo> canUpdata2 = dao.isCanUpdata(id,0);
		if (canUpdata2.size()>0) {
			return 1;
		}
		return 0;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段
	 * @Date 2018/9/17 20:13
	 * @Param [paperInfo, testId, userId]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public XxkhPaperInfo getTestResult(XxkhPaperInfo paperInfo, String testId, String userId){
		XxkhPaperInfo testResult = dao.getTestResult(paperInfo,testId,userId);
		return testResult;
	}

	@Override
	public List<XxkhTestInfo> isCanDelete(XxkhPaperInfo paperInfo) {
		List<XxkhTestInfo> canDelete = dao.isCanDelete(paperInfo.getId());
		return canDelete;
	}
}
