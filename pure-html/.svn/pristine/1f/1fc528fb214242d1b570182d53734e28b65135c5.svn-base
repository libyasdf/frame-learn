package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DegreeDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglDegreeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class DyxxDrgeeServiceImp implements DyxxDrgeeService{
	@Autowired
	private DegreeDao drgeeDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public DdjsDyglDegreeEntity save(DdjsDyglDegreeEntity entity) {
		String id = entity.getDegreeId();
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
			entity.setDegreeSuperId(entity.getDegreeSuperId());
			entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			return drgeeDao.save(entity);
		} else {
			DdjsDyglDegreeEntity oldEntity = drgeeDao.findOne(entity.getDegreeId());
			oldEntity.setMajor(entity.getMajor());
			oldEntity.setGraduationTime(entity.getGraduationTime());
			oldEntity.setGraduationAcademy(entity.getGraduationAcademy());
			oldEntity.setEnrolmentTime(entity.getEnrolmentTime());
			oldEntity.setEducationSector(entity.getEducationSector());
			oldEntity.setDegreeAwardTime(entity.getDegreeAwardTime());
			oldEntity.setEducation(entity.getEducation());
			oldEntity.setDegree(entity.getDegree());
			return drgeeDao.save(oldEntity);
		}
	}

	@Override
	public DdjsDyglDegreeEntity getById(String id) {
		return drgeeDao.findOne(id);
	}

	@Override
	public int delete(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update DDJS_DYGL_DEGREE set visible = '"+CommonConstants.VISIBLE[0]+"' where degree_id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

}
