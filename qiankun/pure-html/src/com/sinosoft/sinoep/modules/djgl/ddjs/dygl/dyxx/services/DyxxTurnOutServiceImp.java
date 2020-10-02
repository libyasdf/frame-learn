package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglHistoryEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglTurnoutEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DyxxTurnOutServiceImp implements DyxxTurnOutService{
	@Autowired
	private DyxxTurnOutDao turnOutDao;

	@Autowired
	private HistoryDao historyDao;

	@Autowired
	private DyxxDao dyxxDao;
	@Autowired
	private IncreseDao increseDao;
	@Autowired
	private PartyBasicInfoDao partyBasicInfoDao;
	@Override
	public DdjsDyglTurnoutEntity save(DdjsDyglTurnoutEntity entity) {
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
			entity.setSuperId(entity.getSuperId());
			entity =turnOutDao.save(entity);
			DdjsDyglUserbasicinfoEntity userbasicinfoEntity = dyxxDao.findOne(entity.getSuperId());
			DdjsDyglHistoryEntity historyEntity = new DdjsDyglHistoryEntity();
			BeanUtils.copyProperties(userbasicinfoEntity,historyEntity);
			historyEntity.setUserbasicinfoId(userbasicinfoEntity.getId());
			historyEntity.setSuperId(entity.getId());
			historyEntity = historyDao.save(historyEntity);
			userbasicinfoEntity.setIsHistoryparty("06");
			userbasicinfoEntity.setVisible(CommonConstants.VISIBLE[0]);
			//更新党员基本情况
			String updatPartyBasic = "update DdjsDyglPartybasicinfoEntity q set q.locationpartygroup='' where q.partybasicinfoSuperId='"+userbasicinfoEntity.getId()+"'";
			int m = partyBasicInfoDao.update(updatPartyBasic);
			//删除党员增加情况
			String delIncrease = "update DdjsDyglIncreaseEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.increaseSuperId='"+userbasicinfoEntity.getId()+"'";
			int b = increseDao.update(delIncrease);
			dyxxDao.save(userbasicinfoEntity);
			return entity;
		} else {
			DdjsDyglTurnoutEntity oldEntity = turnOutDao.findOne(entity.getId());
			oldEntity.setSuperId(entity.getSuperId());
			return turnOutDao.save(entity);
		}
	}

	@Override
	public DdjsDyglTurnoutEntity getById(String id) {
		return turnOutDao.findOne(id);
	}
}
