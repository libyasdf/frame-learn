package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.dao.XxkhQuestionQgroupDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao.XxkhQuestionGroupDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
@Transactional
public class XxkhQuestionGroupServiceImpl implements XxkhQuestionGroupService {
	@Autowired
	private XxkhQuestionGroupDao dao;
	
	@Autowired
	private XxkhQuestionQgroupDao xxkhQuestionQgroupdao;

	@Override
	public XxkhQuestionGroup save(XxkhQuestionGroup group) {
		group.setCreUserId(UserUtil.getCruUserId());
		group.setCreUserName(UserUtil.getCruUserName());
		group.setCreJuId(UserUtil.getCruJuId());
		group.setCreJuName(UserUtil.getCruJuName());
		group.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		return dao.saveAndFlush(group);
	}

	@Override
	public XxkhQuestionGroup update(XxkhQuestionGroup group) {
		XxkhQuestionGroup old = getbyId(group);
		if (StringUtils.isNotBlank(group.getEveryScore())) {
			old.setEveryScore(group.getEveryScore());
		}
		if (StringUtils.isNotBlank(group.getQuestionCount())) {
			old.setQuestionCount(group.getQuestionCount());
		}
		if (StringUtils.isNotBlank(group.getFullScore())) {
			old.setFullScore(group.getFullScore());
		}
		return dao.saveAndFlush(old);
	}

	@Override
	public XxkhQuestionGroup getbyId(XxkhQuestionGroup group) {
		return dao.byId(group.getId()).get(0);
	}

	@Override
	public int delete(XxkhQuestionGroup group) {
		xxkhQuestionQgroupdao.deleteByZuId(group.getId());
		dao.delete(group.getId());
		return 1;
	}

	@Override
	public XxkhQuestionGroup autosave(XxkhQuestionGroup group) {
		group.setCreUserId(UserUtil.getCruUserId());
		group.setCreUserName(UserUtil.getCruUserName());
		group.setCreJuId(UserUtil.getCruJuId());
		group.setCreJuName(UserUtil.getCruJuName());
		group.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		group.setZujuanStatus(XXKHCommonConstants.ZUJUAN_NO_START);
		group.setSimpleCount("0");
		group.setNormalCount("0");
		group.setHardCount("0");
		group.setFullScore("0");
		group.setEveryScore("0");
		return dao.saveAndFlush(group);
	}

	@Override
	public XxkhQuestionGroup update(String groupId, String jiandan, String yiban, String kunnan,String everyScore,String fullScore,String isZuJuan) {
		XxkhQuestionGroup one = dao.byId(groupId).get(0);
		one.setSimpleCount(jiandan);
		one.setNormalCount(yiban);
		one.setHardCount(kunnan);
		if(!everyScore.equals("")) {
			one.setEveryScore(everyScore);
		}
		if(!fullScore.equals("")) {
			one.setFullScore(fullScore);
		}
		if(isZuJuan.equals("1")) {
			one.setZujuanStatus(XXKHCommonConstants.ZUJUAN_YES_START);
		}
		return dao.saveAndFlush(one);
	}

	@Override
	public XxkhQuestionGroup getLevelCount(XxkhQuestionGroup group) {
		
		return dao.getLevelCount(group.getId());
	}

	@Override
	public XxkhQuestionGroup getEveryScore(String questionGroupId, String questionId) {
		return dao.getEverScore(questionGroupId, questionId);
	}
}
