package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DyxxTurnAroundDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglAroundEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DyxxTurnAroundServiceImp implements DyxxTurnArourndService{
	@Autowired
	private DyxxTurnAroundDao turnAroundDao;


	@Override
	public DdjsDyglAroundEntity save(DdjsDyglAroundEntity entity) {
		String id = entity.getId();
		if (StringUtils.isBlank(id)) {
			entity.setCreUserId(UserUtil.getCruUserId());
			entity.setCreUserName(UserUtil.getCruUserName());
			entity.setCreDeptId(UserUtil.getCruDeptId());
			entity.setCreDeptName(UserUtil.getCruDeptName());
			entity.setCreChushiId(UserUtil.getCruChushiId());
			entity.setCreChushiName(UserUtil.getCruChushiName());
			entity.setCreJuId(UserUtil.getCruJuId());
			entity.setCreJuName(UserUtil.getCruJuName());
			entity.setVisible(CommonConstants.VISIBLE[1]);
			entity.setOriginalIndividualStatus(entity.getOriginalIndividualStatus());
			entity.setReceiveParty(entity.getReceiveParty());
			entity.setTurnAroundParty(entity.getTurnAroundParty());
			entity.setTurnAroundTime(entity.getTurnAroundTime());
			entity.setTurnAroundType(entity.getTurnAroundType());
			entity.setSuperId(entity.getSuperId());
			return turnAroundDao.save(entity);
		} else {
			DdjsDyglAroundEntity oldEntity = turnAroundDao.findOne(entity.getId());
			oldEntity.setOriginalIndividualStatus(entity.getOriginalIndividualStatus());
			oldEntity.setReceiveParty(entity.getReceiveParty());
			oldEntity.setTurnAroundParty(entity.getTurnAroundParty());
			oldEntity.setTurnAroundTime(entity.getTurnAroundTime());
			oldEntity.setTurnAroundType(entity.getTurnAroundType());
			oldEntity.setSuperId(entity.getSuperId());
			return turnAroundDao.save(entity);
		}
	}

	@Override
	public DdjsDyglAroundEntity getById(String id) {
		return turnAroundDao.findOne(id);
	}
}
