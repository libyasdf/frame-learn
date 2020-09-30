package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.AdministrativeDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglAdministrativeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class DyxxAdministrativeServiceImp implements DyxxAdministrativeService{
	@Autowired
	private AdministrativeDao administrativeDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public DdjsDyglAdministrativeEntity save(DdjsDyglAdministrativeEntity entity) {
		String id = entity.getAdministrativeId();
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
			entity.setAdministrativeSuperId(entity.getAdministrativeSuperId());
			entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			return administrativeDao.save(entity);
		} else {
			DdjsDyglAdministrativeEntity oldEntity = administrativeDao.findOne(entity.getAdministrativeId());
			oldEntity.setXzTenureTime(entity.getXzTenureTime());
			oldEntity.setXzOutgoingTime(entity.getXzOutgoingTime());
			oldEntity.setTenureUnit(entity.getTenureUnit());
			oldEntity.setPersonalPositionOrder(entity.getPersonalPositionOrder());
			oldEntity.setAdministrativeDutiesName(entity.getAdministrativeDutiesName());
			return administrativeDao.save(oldEntity);
		}
	}

	@Override
	public DdjsDyglAdministrativeEntity getById(String id) {
		return administrativeDao.findOne(id);
	}

	@Override
	public int delete(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update DDJS_DYGL_ADMINISTRATIVE set visible = '"+CommonConstants.VISIBLE[0]+"' where administrative_id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}
}
