package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DyxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.RewardDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglRewardEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardServiceImp implements RewardService{

	@Autowired
	private RewardDao rewardDao;

	@Autowired
	private DyxxDao dyxxDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public DdjsDyglRewardEntity saveForm(DdjsDyglRewardEntity entity) {
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
			entity = rewardDao.save(entity);
			return entity;
		} else {
			DdjsDyglRewardEntity oldEntity = rewardDao.findOne(entity.getId());
			oldEntity.setPartyOrganization(entity.getPartyOrganization());
			oldEntity.setPartyOrganizationId(entity.getPartyOrganizationId());
			oldEntity.setPartyMember(entity.getPartyMember());
			oldEntity.setPartyMemberId(entity.getPartyMemberId());
			oldEntity.setRewardReason(entity.getRewardReason());
			oldEntity.setRewardContent(entity.getRewardContent());
			oldEntity.setRewardRevokeTime(entity.getRewardRevokeTime());
			oldEntity.setRewardTime(entity.getRewardTime());
			oldEntity.setRewardUnit(entity.getRewardUnit());
			oldEntity.setRemarks(entity.getRemarks());
			oldEntity.setMainDeeds(entity.getMainDeeds());
			oldEntity.setApprovalOrganLevel(entity.getApprovalOrganLevel());
			entity =  rewardDao.save(oldEntity);
			return entity;
		}
	}

	@Override
	public DdjsDyglRewardEntity getBySuperId(String superId) {
		DdjsDyglUserbasicinfoEntity superEntity =  dyxxDao.findOne(superId);
		DdjsDyglRewardEntity entity = new DdjsDyglRewardEntity();
		entity.setSuperId(superId);
		entity.setPartyMember(superEntity.getName());
		entity.setPartyOrganization(superEntity.getPartyOrganizationName());
		return  entity;
	}

	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglRewardEntity entity, String startTime, String endTime,String id, String type) {
		StringBuilder sql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sql.append(" from DdjsDyglRewardEntity t ");
		//querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		if(type.equals("org")){
			id = DzzUtil.spiltString(id);
			sql.append(" and partyOrganizationId in ("+id+")");
		}else{
			sql.append(" and partyMemberId = '"+id+"'");
		}
		// 时间
		if (StringUtils.isNotBlank(entity.getRewardTime())) {
			sql.append(" and substr(t.rewardTime,1,10) between ? and ?");
			para.add(startTime);
			para.add(endTime);
		}

		// 地点
		if (StringUtils.isNotBlank(entity.getPartyMember()) ) {
			sql.append(" and t.partyMember = ?");
			para.add(entity.getPartyMember());
		}

		// 主持人
		if (StringUtils.isNotBlank(entity.getRewardContent())) {
			sql.append(" and t.rewardContent = ?");
			para.add(entity.getRewardContent());
		}

		// 记录人
		if (StringUtils.isNotBlank(entity.getRewardReason())) {
			sql.append(" and t.rewardReason = ?");
			para.add(entity.getRewardReason());
		}
//
//		if ("dept".equals(type)) {
//			sql.append(" and t.partyOrganizationId like ?");
//			para.add(orgId + "%");
//		}else{
//			sql.append(" and t.partyMemberId like ?");
//			para.add(orgId + "%");
//		}
		Page<DdjsDyglUserbasicinfoEntity> page = rewardDao.query(sql.toString(), pageable, para.toArray());
		// 添加列表操作
		List<DdjsDyglUserbasicinfoEntity> content = page.getContent();
		/*for (DdjsDyglUserbasicinfoEntity entity : content) {
			entity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}*/
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public int delete(String id) {
		StringBuilder sql = new StringBuilder("update DDJS_DYGL_REWARD set visible ='");
		sql.append(CommonConstants.VISIBLE[0]);
		sql.append("' where id ='").append(id).append("' ");
		return jdbcTemplate.update(sql.toString());
	}

	@Override
	public DdjsDyglRewardEntity getById(String id) {
		return rewardDao.findOne(id);
	}


}
