package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.dao.DwxtHjxjDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.dao.DwxtLdcyDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtHjxj;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class DwxtHjxjServiceImpl implements DwxtHjxjService {

	@Autowired
	private DwxtHjxjDao dwxtHjxjDao;

	@Autowired
	private DwxtLdcyDao dwxtLdcyDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public PageImpl getPageList(PageImpl pageImpl, DwxtHjxj dwxtHjxj,String timeRange) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		List<Object> para = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();
		querySql.append("from DwxtHjxj t ");
		querySql.append("where  t.dwxtOrgId = '"+dwxtHjxj.getDwxtOrgId()+"' and t.visible = '" + CommonConstants.VISIBLE[1] + "' ");
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("  and t.changeTime >= ? and t.changeTime <= ? ");
			para.add(startDate);
			para.add(endDate);
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.hjxjOrder desc ");
		}
	/*	else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}*/
		Page<DwxtHjxj> page = dwxtHjxjDao.query(querySql.toString(), pageable, para.toArray());
/*		if(page.getContent().size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append("select max(t.hjxj_order) from ddjs_dzz_hjxj t where dwxt_org_id = '"+ dwxtHjxj.getDwxtOrgId() +"'and t.visible = '" + CommonConstants.VISIBLE[1]+"'");
			Integer maxOrder = DzzUtil.getMaxOrder(sb.toString());
			if(page.getContent().get(0).getHjxjOrder().equals(maxOrder - 1)){
				page.getContent().get(0).setMaxHj(true);
			}
			if(page.getContent().size() > 1){
				if(page.getContent().get(1).getHjxjOrder().equals(maxOrder - 2)){
					page.getContent().get(1).setMaxHj(true);
				}
			}
		}*/
		for (int i=0;i< page.getContent().size();i++) {
			page.getContent().get(i).setDelHj("0");
			if(i==0){
				page.getContent().get(i).setDelHj("1");
				page.getContent().get(i).setMaxHj(true);
			}else if(i==1){
				page.getContent().get(i).setMaxHj(true);
			}


		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public DwxtHjxj findOne(String id) {
		return dwxtHjxjDao.findOne(id);
	}

	@Override
	public DwxtHjxj save(DwxtHjxj dwxtHjxj) {
		dwxtHjxj.setCreDeptId(UserUtil.getCruDeptId());
		dwxtHjxj.setCreDeptName(UserUtil.getCruDeptName());
		dwxtHjxj.setCreChushiId(UserUtil.getCruChushiId());
		dwxtHjxj.setCreJuId(UserUtil.getCruJuId());
		dwxtHjxj.setCreJuName(UserUtil.getCruJuName());
		dwxtHjxj.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtHjxj.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtHjxj.setCreUserId(UserUtil.getCruUserId());
		dwxtHjxj.setCreUserName(UserUtil.getCruUserName());
		dwxtHjxj.setUpdateUserId(UserUtil.getCruUserId());
		dwxtHjxj.setUpdateUserName(UserUtil.getCruUserName());
		dwxtHjxj.setVisible("1");
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.hjxj_order) from ddjs_dzz_hjxj t where t.visible='"+ CommonConstants.VISIBLE[1]+"' and t.dwxt_org_id = '"+ dwxtHjxj.getDwxtOrgId() +"'  ");
		dwxtHjxj.setHjxjOrder(DzzUtil.getMaxOrder(sb.toString()));// 初始顺序号
		dwxtHjxj = dwxtHjxjDao.saveAndFlush(dwxtHjxj);
	/*	//新增换届选举 上一届领导成员全部自动离任
		DwxtHjxj dwxtHjxj1 = dwxtHjxjDao.findByHjxjOrder(dwxtHjxj.getHjxjOrder()-1)==null?new DwxtHjxj():dwxtHjxjDao.findByHjxjOrder(dwxtHjxj.getHjxjOrder()-1);
		List<DwxtLdcy> ldcyList	= dwxtLdcyDao.findByHjxjIdAndVisible(dwxtHjxj1.getId(),CommonConstants.VISIBLE[1]);
		for(DwxtLdcy ldcy : ldcyList){
			ldcy.setIsTenure("0");
		}
		dwxtLdcyDao.save(ldcyList);*/
		return  dwxtHjxj;
	}

	@Override
	public DwxtHjxj update(DwxtHjxj dwxtHjxj) {
		DwxtHjxj old = dwxtHjxjDao.findOne(dwxtHjxj.getId());
		old.setSessionNumber(dwxtHjxj.getSessionNumber());
		old.setLimitYear(dwxtHjxj.getLimitYear());
		old.setChangeTime(dwxtHjxj.getChangeTime());
		old.setModality(dwxtHjxj.getModality());
		old.setShouldNumber(dwxtHjxj.getShouldNumber());
		old.setActualNumber(dwxtHjxj.getActualNumber());
		old.setSituation(dwxtHjxj.getSituation());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dwxtHjxjDao.saveAndFlush(old);
	}

	@Override
	public int delete(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_hjxj set visible = '"+CommonConstants.VISIBLE[0]+"' where id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

	@Override
	public PageImpl ldcyList(DwxtLdcy dwxtLdcy,PageImpl pageImpl) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		StringBuilder sb = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sb.append(" from DwxtLdcy t where hjxjId = '"+dwxtLdcy.getHjxjId()+"' and visible = '"+CommonConstants.VISIBLE[1]+"'");
		if (StringUtils.isNotBlank(dwxtLdcy.getIsTenure()) ) {
			sb.append(" and t.isTenure = "+"0");
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sb.append(" order by t.isTenure,t.duties asc ");
		} else {
			sb.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<DwxtLdcy> page = dwxtLdcyDao.query(sb.toString(), pageable);
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public DwxtLdcy saveLdcy(DwxtLdcy dwxtLdcy) {
		dwxtLdcy.setCreDeptId(UserUtil.getCruDeptId());
		dwxtLdcy.setCreDeptName(UserUtil.getCruDeptName());
		dwxtLdcy.setCreChushiId(UserUtil.getCruChushiId());
		dwxtLdcy.setCreJuId(UserUtil.getCruJuId());
		dwxtLdcy.setCreJuName(UserUtil.getCruJuName());
		dwxtLdcy.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtLdcy.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtLdcy.setCreUserId(UserUtil.getCruUserId());
		dwxtLdcy.setCreUserName(UserUtil.getCruUserName());
		dwxtLdcy.setUpdateUserId(UserUtil.getCruUserId());
		dwxtLdcy.setUpdateUserName(UserUtil.getCruUserName());
		dwxtLdcy.setVisible("1");
		dwxtLdcy.setIsTenure("0");
		return dwxtLdcyDao.saveAndFlush(dwxtLdcy);
	}

	@Override
	public DwxtLdcy updateLdcy(DwxtLdcy dwxtLdcy) {
		DwxtLdcy old = dwxtLdcyDao.findOne(dwxtLdcy.getId());
		old.setLeaderId(dwxtLdcy.getLeaderId());
		old.setLeaderName(dwxtLdcy.getLeaderName());
		old.setDuties(dwxtLdcy.getDuties());
		old.setTenureDate(dwxtLdcy.getTenureDate());
		old.setTenureFileCode(dwxtLdcy.getTenureMode());
		old.setTenureFileCode(dwxtLdcy.getTenureFileCode());
		old.setRemark(dwxtLdcy.getRemark());
		old.setTenureMode(dwxtLdcy.getTenureMode());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dwxtLdcyDao.saveAndFlush(old);
	}

	@Override
	public DwxtLdcy findOneLdcy(String id) {
		return dwxtLdcyDao.findOne(id);
	}

	@Override
	public int deleteLdcy(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_ldcy set visible = '"+CommonConstants.VISIBLE[0]+"' where id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

	@Override
	public int outTenure(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_ldcy set is_tenure = '"+CommonConstants.VISIBLE[0]+"' where id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

	@Override
	public List<DwxtLdcy> findByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select h.session_number,h.limit_year,o.org_name,l.* from ddjs_dzz_ldcy l,ddjs_dzz_hjxj h,ddjs_dzz_org o where l.hjxj_id = h.id and o.id = h.dwxt_org_id and leader_id='" + userId + "' and o.visible='1' and h.visible='1' and l.visible='1' order by h.hjxj_order desc");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
		List<DwxtLdcy> ldcyList = new ArrayList<DwxtLdcy>();
		if(list.size() > 0) {
			for (Map<String, Object> map : list) {
				DwxtLdcy dwxtLdcy = new DwxtLdcy();
				dwxtLdcy.setLeaderName(map.get("leader_name") == null ? "": map.get("leader_name").toString());
				dwxtLdcy.setDuties(map.get("duties").toString() == null ? "" :map.get("duties").toString());
				dwxtLdcy.setOrgName(map.get("org_name") == null ? "" : map.get("org_name").toString());
				dwxtLdcy.setTenureDate(map.get("tenure_date") == null ? "" : map.get("tenure_date").toString());
				dwxtLdcy.setTenureFileCode(map.get("tenure_file_code") == null ? "" : map.get("tenure_file_code").toString());
				dwxtLdcy.setSessionNumber(map.get("session_number") == null ? "" : map.get("session_number").toString());
				dwxtLdcy.setLimitYear(map.get("limit_year") == null ? "": map.get("limit_year").toString());
				dwxtLdcy.setLeaveDate(map.get("LEAVE_DATE") == null ? "" : map.get("LEAVE_DATE").toString());
				dwxtLdcy.setCauseDeparture(map.get("CAUSE_DEPARTURE") == null ? "" : map.get("CAUSE_DEPARTURE").toString());
				dwxtLdcy.setLeavingNumber(map.get("LEAVING_NUMBER") == null ? "" : map.get("LEAVING_NUMBER").toString());
				dwxtLdcy.setLeavingMode(map.get("LEAVING_MODE") == null ? "": map.get("LEAVING_MODE").toString());
				ldcyList.add(dwxtLdcy);
			}
		}
		return ldcyList;
	}

	/**
	 * 新增换届选举时判断该人员是否已经为领导人
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 */
	public DwxtLdcy  queryLdcy(String hjxjId, String leaderId){
		List<DwxtLdcy> userList = dwxtLdcyDao.findByHjxjIdAndLeaderIdAndIsTenureAndVisible(hjxjId,leaderId,"0",CommonConstants.VISIBLE[1]);
		DwxtLdcy  entity = new DwxtLdcy();
		if(userList.size()>0){
			entity = userList.get(0)==null? new DwxtLdcy():userList.get(0);
		}
		return entity;
	}
	/**
	 * 对领导成员进行离任
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 */
	@Override
	public DwxtLdcy updateLeave(DwxtLdcy dwxtLdcy) {
		DwxtLdcy old = dwxtLdcyDao.findOne(dwxtLdcy.getId());
		old.setLeaveDate(dwxtLdcy.getLeaveDate());
		old.setCauseDeparture(dwxtLdcy.getCauseDeparture());
		old.setLeavingNumber(dwxtLdcy.getLeavingNumber());
		old.setLeavingMode(dwxtLdcy.getLeavingMode());
		old.setIsTenure(CommonConstants.VISIBLE[1]);
		return dwxtLdcyDao.saveAndFlush(old);
	}

	/**
	 * 查询本届是否有领导成员
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 */
	@Override
	public  DwxtLdcy queryLeave(String hjxjId) {
		List< DwxtLdcy> dwxtLdcyList = dwxtLdcyDao.findByHjxjIdAndVisible(hjxjId,CommonConstants.VISIBLE[1]);
		DwxtLdcy dwxtLdcy = null;
		if(dwxtLdcyList.size()>0){
			dwxtLdcy =dwxtLdcyList.get(0)==null? new DwxtLdcy():dwxtLdcyList.get(0);
		}else{
			dwxtLdcy=new DwxtLdcy();
		}

		return dwxtLdcy;
	}
}
