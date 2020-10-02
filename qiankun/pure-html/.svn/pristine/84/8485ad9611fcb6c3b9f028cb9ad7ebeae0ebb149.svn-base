package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.WorkingDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglWorkingEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class DyxxWorkServiceImp implements DyxxWorkService{
	@Autowired
	private WorkingDao workDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public DdjsDyglWorkingEntity save(DdjsDyglWorkingEntity entity) {
		String id = entity.getWorkingId();
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
			entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			entity.setWorkingSuperId(entity.getWorkingSuperId());
			return workDao.save(entity);
		} else {
			DdjsDyglWorkingEntity oldEntity = workDao.findOne(entity.getWorkingId());
			oldEntity.setWorkFrontlineSituation(entity.getWorkFrontlineSituation());
			oldEntity.setNewStratum(entity.getNewStratum());
			oldEntity.setIdentity(entity.getIdentity());
			oldEntity.setEndTime(entity.getEndTime());
			oldEntity.setBeforeTime(entity.getBeforeTime());
			return workDao.save(oldEntity);
		}
	}

	@Override
	public DdjsDyglWorkingEntity getById(String id) {
		return workDao.findOne(id);
	}

	@Override
	public int delete(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update DDJS_DYGL_WORKING set visible = '"+CommonConstants.VISIBLE[0]+"' where working_id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

}
