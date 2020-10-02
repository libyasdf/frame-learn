package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao.DwxtHistoryOrgDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao.DwxtOrgDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao.DwxtRevokeDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.dao.DwxtUnitDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtHistoryOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtRevoke;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtUnit;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class DwxtOrgServiceImpl implements DwxtOrgService {

	@Autowired
	private DwxtOrgDao dwxtOrgDao;

	@Autowired
	private DwxtUnitDao dwxtUnitDao;

	@Autowired
	private DwxtRevokeDao dwxtRevokeDao;

	@Autowired
	private DwxtHistoryOrgDao dwxtHistoryOrgDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public List<DwxtOrg> findChild(DwxtOrg dwxtOrg){
		String sql = " select * from (select * from (select * from ddjs_dzz_org where visible = '"+ CommonConstants.VISIBLE[1]+"') t ";
				sql += " start with t.id = '" + dwxtOrg.getId() + "' connect by prior t.id = t.super_id order siblings by t.org_order) ";
		if(StringUtils.isNotBlank(dwxtOrg.getOrgName())){
			sql += " where org_name like '%"+dwxtOrg.getOrgName()+"%'";
		}
		if(StringUtils.isNotBlank(dwxtOrg.getOrgName())){
			sql += " and org_type like '%"+dwxtOrg.getOrgType()+"%'";
		}
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DwxtOrg.class));
	}
	@Override
	public List<DwxtOrg> findChildVal(DwxtOrg dwxtOrg,PageImpl pageImpl){
		String sql = " select * from (select * from (select * from ddjs_dzz_org where visible = '"+ CommonConstants.VISIBLE[1]+"') t ";
				sql += " start with t.id = '" + dwxtOrg.getId() + "' connect by prior t.id = t.super_id order siblings by t.org_order) where 1=1";
		if(StringUtils.isNotBlank(dwxtOrg.getOrgName())){
			sql += " and org_name like '%"+dwxtOrg.getOrgName()+"%'";
		}
		if(StringUtils.isNotBlank(dwxtOrg.getOrgType())){
			sql += " and org_type like '%"+dwxtOrg.getOrgType()+"%'";
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sql +=" order by TO_NUMBER (ORG_ID) asc ";
		} else {
			String orderName ="";
			if(pageImpl.getSortName().equals("orgName")){
				orderName ="ORG_NAME";
			}else if(pageImpl.getSortName().equals("orgType")){
				orderName ="ORG_TYPE";
			}else if(pageImpl.getSortName().equals("orgId")){
				orderName ="ORG_ID";
			}
			sql +=" order by " +orderName + " " + pageImpl.getSortOrder()  ;
		}
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DwxtOrg.class));
	}

	@Override
	public List<Map<String,Object>> getTree(DwxtOrg dwxtOrg) {
		String sql = "select t.*, ";
		sql += "(select count(1) from ddjs_org_user_view t2 where (t2.partytype like '%04%' or  t2.partytype like '%05%')   and t2.partytype not like '%06%' connect by  t2.pid = prior id start with t2.pid = t.id) as count ";
		sql += "from  (select * from ddjs_org_user_view where partytype = 'partyType' or  partytype like '%04%' or partytype like '%05%' and partytype not like '%06%')t ";
		sql += "start with t.id = '" + dwxtOrg.getId() + "' connect by prior t.id = t.pid  order siblings by type desc, t.org_order";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOrgUserTree(String partyBranchId,String id) {
		String sql = "select t.* from ddjs_org_user_view t where id = '"+partyBranchId+"' or (pid = '"+partyBranchId+"' and (t.partytype like '%04%' or  t.partytype like '%05%'))";
		if(StringUtils.isNotBlank(id)) {
			sql += " union select t.* from ddjs_org_user_view  t  where t.partytype = 'partyType' or t.partytype like '%04%' or t.partytype like '%05%' start with t.id = '" + id + "' connect by prior t.id = t.pid order  by 5 desc";
		}
		return jdbcTemplate.queryForList(sql);
	}


	@Override
	public DwxtOrg findOne(String id){
		return dwxtOrgDao.findOne(id);
	}

	@Override
	public DwxtOrg save(DwxtOrg dwxtOrg,String jcOrgId) {
		DwxtOrg superDwxtOrg = dwxtOrgDao.findOne(dwxtOrg.getSuperId());
		dwxtOrg.setOrgId(superDwxtOrg.getOrgId() + jcOrgId);
		dwxtOrg.setCreDeptId(UserUtil.getCruDeptId());
		dwxtOrg.setCreDeptName(UserUtil.getCruDeptName());
		dwxtOrg.setCreChushiId(UserUtil.getCruChushiId());
		dwxtOrg.setCreChushiName(UserUtil.getCruChushiName());
		dwxtOrg.setCreJuId(UserUtil.getCruJuId());
		dwxtOrg.setCreJuName(UserUtil.getCruJuName());
		dwxtOrg.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtOrg.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtOrg.setCreUserId(UserUtil.getCruUserId());
		dwxtOrg.setCreUserName(UserUtil.getCruUserName());
		dwxtOrg.setUpdateUserId(UserUtil.getCruUserId());
		dwxtOrg.setUpdateUserName(UserUtil.getCruUserName());
		dwxtOrg.setVisible("1");
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.org_order) from ddjs_dzz_org t where ");
		sb.append(" t.super_id = '"+dwxtOrg.getSuperId()+"' and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		dwxtOrg.setOrgOrder(DzzUtil.getMaxOrder(sb.toString()));// 初始顺序号
		return dwxtOrgDao.saveAndFlush(dwxtOrg);
	}

	@Override
	public DwxtOrg saveDxz(DwxtOrg dwxtOrg, String selectIds) {
		DwxtOrg superDwxtOrg = dwxtOrgDao.findOne(dwxtOrg.getSuperId());
		dwxtOrg.setOrgId(superDwxtOrg.getOrgId() + DzzUtil.getOrgId(superDwxtOrg.getId()));
		dwxtOrg.setCreDeptId(UserUtil.getCruDeptId());
		dwxtOrg.setCreDeptName(UserUtil.getCruDeptName());
		dwxtOrg.setCreChushiId(UserUtil.getCruChushiId());
		dwxtOrg.setCreChushiName(UserUtil.getCruChushiName());
		dwxtOrg.setCreJuId(UserUtil.getCruJuId());
		dwxtOrg.setCreJuName(UserUtil.getCruJuName());
		dwxtOrg.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtOrg.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtOrg.setCreUserId(UserUtil.getCruUserId());
		dwxtOrg.setCreUserName(UserUtil.getCruUserName());
		dwxtOrg.setUpdateUserId(UserUtil.getCruUserId());
		dwxtOrg.setUpdateUserName(UserUtil.getCruUserName());
		dwxtOrg.setVisible("1");
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.org_order) from ddjs_dzz_org t where ");
		sb.append(" t.super_id = '"+dwxtOrg.getSuperId()+"' and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		dwxtOrg.setOrgOrder(DzzUtil.getMaxOrder(sb.toString()));// 初始顺序号
		dwxtOrg = dwxtOrgDao.saveAndFlush(dwxtOrg);
		if(StringUtils.isNotBlank(selectIds)) {
			selectIds = DzzUtil.spiltString(selectIds);
			if (StringUtils.isNotBlank(selectIds)) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append("update ddjs_dygl_userbasicinfo set party_organization_id = '" + dwxtOrg.getId() + "',party_organization_name='" + dwxtOrg.getOrgName() + "' where id in (" + selectIds + ")");
				jdbcTemplate.update(sb1.toString());
			}
		}
		return dwxtOrg;
	}


	@Override
	public DwxtOrg update(DwxtOrg dwxtOrg,String jcOrgId) {
		DwxtOrg old = dwxtOrgDao.findOne(dwxtOrg.getId());
		String orgId = dwxtOrg.getOrgId().substring(0,dwxtOrg.getOrgId().length()-3)+jcOrgId;
		int count =  DzzUtil.edit(old.getOrgId(),orgId);
		old.setOrgId(orgId);
		old.setOrgName(dwxtOrg.getOrgName());
		old.setOrgType(dwxtOrg.getOrgType());
		old.setOrgFullName(dwxtOrg.getOrgFullName());
		old.setCreDate(dwxtOrg.getCreDate());
		old.setFileNumber(dwxtOrg.getFileNumber());
		old.setContactMan(dwxtOrg.getContactMan());
		old.setContactNumber(dwxtOrg.getContactNumber());
		old.setPostalCode(dwxtOrg.getPostalCode());
		old.setBelongAddress(dwxtOrg.getBelongAddress());
		old.setPostalAddress(dwxtOrg.getPostalAddress());
		old.setReason(dwxtOrg.getReason());
		old.setAssociativeUnitId(dwxtOrg.getAssociativeUnitId());
		old.setAssociativeUnitName(dwxtOrg.getAssociativeUnitName());
		old.setAssociativeUserId(dwxtOrg.getAssociativeUserId());
		old.setAssociativeUserName(dwxtOrg.getAssociativeUserName());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dwxtOrgDao.saveAndFlush(old);
	}

	@Override
	public DwxtOrg updateDxz(DwxtOrg dwxtOrg,String selectIds) {
		DwxtOrg old = dwxtOrgDao.findOne(dwxtOrg.getId());
		old.setOrgName(dwxtOrg.getOrgName());
		old.setCreDate(dwxtOrg.getCreDate());
		old.setReason(dwxtOrg.getReason());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		dwxtOrg = dwxtOrgDao.saveAndFlush(old);
		StringBuilder sb = new StringBuilder();
		sb.append("select id from ddjs_dygl_userbasicinfo t where t.party_organization_id = '"+dwxtOrg.getId()+"' and t .visible = '1'");
		String deleteIds = "";
		String dbIds = "";
		//查询数据库党小组下的人
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		for(int i=0;i<list.size();i++){
			 //页面ID 不包含在数据库存的ID
			if(!selectIds.contains(list.get(i).get("id").toString())){
				deleteIds += list.get(i).get("id").toString() + ",";
			}
			//数据库的id 转字符串
			dbIds += list.get(i).get("id").toString() + ",";
		}
		String[] sIds = selectIds.split(",");
		String addIds = "";
		for(int i=0 ; i<sIds.length; i++){
			if(!dbIds.contains(sIds[i])){
				addIds += sIds[i] + ",";
			}
		}
		if(StringUtils.isNotBlank(deleteIds)) {
			deleteIds = DzzUtil.spiltString(deleteIds);
			StringBuilder sb1 = new StringBuilder();
			sb1.append("update ddjs_dygl_userbasicinfo set party_organization_id = '" + dwxtOrg.getPartyBranchId() + "',party_organization_name='" + dwxtOrg.getOrgName() + "' where id in (" + deleteIds + ")");
			jdbcTemplate.update(sb1.toString());
		}
		if(StringUtils.isNotBlank(addIds)){
			addIds = DzzUtil.spiltString(addIds);
			StringBuilder sb2 = new StringBuilder();
			sb2.append("update ddjs_dygl_userbasicinfo set party_organization_id = '" + dwxtOrg.getId() + "',party_organization_name='" + dwxtOrg.getOrgName() + "' where id in (" + addIds + ")");
			jdbcTemplate.update(sb2.toString());
		}
		return dwxtOrg;
	}


	@Override
	public Boolean check(String orgId){
		String sql = "select t.id from ddjs_dzz_org t where t.org_id = '"+orgId+"' and t.visible  = '"+ CommonConstants.VISIBLE[1]+"'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size() > 0){
			return false;
		}
		return true;
	}


	@Override
	public void recursiveDwxtOrg(String orgId) {
		List<DwxtOrg> list =  dwxtOrgDao.findBySuperIdAndVisible(orgId,CommonConstants.VISIBLE[1]);
		for(DwxtOrg dwxtOrg : list){
			recursiveDwxtOrg(dwxtOrg.getOrgId());
		}
	}

	@Override
	public int deleteOrg(DwxtOrg dwxtOrg,String revokeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_org set visible = '"+CommonConstants.VISIBLE[0]+"',revoke_id='"+revokeId+"' where org_id like '" +dwxtOrg.getOrgId()+ "%' and visible = '1'");
		List<DwxtOrg> list = findChild(dwxtOrg);
		String ids = "";
		for(DwxtOrg dwxtOrg1 : list){
			ids += dwxtOrg1.getId()+",";
		}
		ids = ids.substring(0,ids.length()-1);
		cascadeDeletUnit(ids);
		return jdbcTemplate.update(sb.toString());
	}

	public void cascadeDeletUnit(String ids) {
		ids = DzzUtil.spiltString(ids);
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_unit set visible = '"+CommonConstants.VISIBLE[0]+"' where dwxt_org_id in (" +ids+ ")");
		jdbcTemplate.update(sb.toString());
	}

	@Override
	public List<DwxtUnit> unitList(DwxtOrg dwxtOrg) {
		return dwxtUnitDao.findByDwxtOrgIdAndVisibleOrderByCreTimeAsc(dwxtOrg.getId(),CommonConstants.VISIBLE[1]);
	}

	@Override
	public DwxtUnit saveUnit(DwxtUnit dwxtUnit) {
		dwxtUnit.setCreDeptId(UserUtil.getCruDeptId());
		dwxtUnit.setCreDeptName(UserUtil.getCruDeptName());
		dwxtUnit.setCreChushiId(UserUtil.getCruChushiId());
		dwxtUnit.setCreChushiName(UserUtil.getCruChushiName());
		dwxtUnit.setCreJuId(UserUtil.getCruJuId());
		dwxtUnit.setCreJuName(UserUtil.getCruJuName());
		dwxtUnit.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtUnit.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtUnit.setCreUserId(UserUtil.getCruUserId());
		dwxtUnit.setCreUserName(UserUtil.getCruUserName());
		dwxtUnit.setUpdateUserId(UserUtil.getCruUserId());
		dwxtUnit.setUpdateUserName(UserUtil.getCruUserName());
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.unit_order) from ddjs_dzz_unit t where t.visible='"+ CommonConstants.VISIBLE[1]+"' and t.dwxt_org_id = '"+ dwxtUnit.getDwxtOrgId() +"'  ");
		dwxtUnit.setUnitOrder(DzzUtil.getMaxOrder(sb.toString()));
		dwxtUnit.setVisible("1");
		return dwxtUnitDao.saveAndFlush(dwxtUnit);
	}

	@Override
	public DwxtUnit updateUnit(DwxtUnit dwxtUnit) {
		DwxtUnit old = dwxtUnitDao.findOne(dwxtUnit.getId());
		old.setUnitName(dwxtUnit.getUnitName());
		old.setGrassrootOrg(dwxtUnit.getGrassrootOrg());
		old.setOrgUnitCode(dwxtUnit.getOrgUnitCode());
		old.setYoungWorkersNumber(dwxtUnit.getYoungWorkersNumber());
		old.setWorkersNumber(dwxtUnit.getWorkersNumber());
		old.setSkillWorkers(dwxtUnit.getSkillWorkers());
		old.setMajorWorkers(dwxtUnit.getMajorWorkers());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dwxtUnitDao.saveAndFlush(old);
	}

	@Override
	public DwxtUnit findOneUnit(String id){
		return dwxtUnitDao.findOne(id);
	}

	@Override
	public int deleteUnit(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_unit set visible = '"+CommonConstants.VISIBLE[0]+"' where id = '" +id+ "'");
		return jdbcTemplate.update(sb.toString());
	}

	@Override
	public DwxtOrg changeDzzOrg(String id,String targetId,String code) {
		DwxtOrg old = dwxtOrgDao.findOne(id);//更新的党组织
		DwxtOrg target = dwxtOrgDao.findOne(targetId);
		old.setSuperId(targetId);
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.org_order) from ddjs_dzz_org t where ");
		sb.append(" t.super_id = '"+ targetId +"' and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		old.setOrgOrder(DzzUtil.getMaxOrder(sb.toString()));// 初始顺序号
		DwxtOrg dwxtOrg = dwxtOrgDao.saveAndFlush(old);
		int conunt = DzzUtil.edit(old.getOrgId(),target.getOrgId() + code);
		return dwxtOrg;
	}

	@Override
	public DwxtRevoke saveRevoke(DwxtRevoke dwxtRevoke,String dwxtOrgId) {
		dwxtRevoke.setCreUserId(UserUtil.getCruUserId());
		dwxtRevoke.setCreUserName(UserUtil.getCruUserName());
		dwxtRevoke.setCreChushiId(UserUtil.getCruChushiId());
		dwxtRevoke.setCreChushiName(UserUtil.getCruChushiName());
		dwxtRevoke.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtRevoke = dwxtRevokeDao.saveAndFlush(dwxtRevoke);
		DwxtOrg dwxtOrg = dwxtOrgDao.findOne(dwxtOrgId);
		deleteOrg(dwxtOrg,dwxtRevoke.getId());
		List<DwxtOrg> orgList = findOrgListByDwxtRevoke(dwxtOrg.getOrgId(),dwxtRevoke.getId());
		List<DwxtHistoryOrg> historyList = new ArrayList<DwxtHistoryOrg>();
		for(DwxtOrg dwxtOrg1: orgList){
			DwxtHistoryOrg historyOrg = new DwxtHistoryOrg();
			historyOrg.setDwxtOrgId(dwxtOrg1.getId());
			historyOrg.setHistoryOrgId(dwxtOrg1.getOrgId());
			historyOrg.setCreUserId(UserUtil.getCruUserId());
			historyOrg.setCreUserName(UserUtil.getCruUserName());
			historyOrg.setCreChushiId(UserUtil.getCruChushiId());
			historyOrg.setCreChushiName(UserUtil.getCruChushiName());
			historyOrg.setRevokeId(dwxtRevoke.getId());
			historyOrg.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			historyList.add(historyOrg);
		}
		dwxtHistoryOrgDao.save(historyList);
		return dwxtRevoke;
	}

	@Override
	public List<DwxtOrg> findOrgListByDwxtRevoke(String orgId,String revokeId){
		StringBuilder sb = new StringBuilder();
		sb.append("select t.id,t.org_id from ddjs_dzz_org t where visible = '"+CommonConstants.VISIBLE[0]+"' and revoke_id = '"+revokeId+"' and org_id like '"+ orgId +"%' ");
		return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<>(DwxtOrg.class));

	}



	@Override
	public Map<String, Object> orgBasicSituation(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sum(dw)+sum(dzz)+sum(dzb) as basic_org, sum(dw) as org_dw,sum(dzz) as org_dzz,sum(dzb) as org_dzb from(select * from (select * from ddjs_dzz_org t where t.visible = '"+ CommonConstants.VISIBLE[1] +"' )a start with a.id = '"+ id +"' connect by prior a.id = a.super_id) p ");
		sb.append("pivot(count(org_type) for org_type in('631' as dzb,'621' as dzz ,'611' as dw)) t");
		return jdbcTemplate.queryForMap(sb.toString());
	}

	@Override
	public Map unitBasicSituation(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select jg.jgdwjl_org, round(jg.jgdwjl_org/dzz,2)*100 as jgdwjl_org_b1, sy.sydwjl_org,round(sy.sydwjl_org/dzz,2)*100 as sydwjl_org_b1 from ");
		sb.append("(select count(1) as jgdwjl_org from ddjs_dzz_unit u,(select * from (select * from ddjs_dzz_org t where t.visible = '1') a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) o  where u.dwxt_org_id = o.id and u.unit_order = '0' and u.visible = '1' ");
		sb.append("and o.visible = '1' and u.unit_attr = '11') jg, ");
		sb.append("(select count(1) as sydwjl_org from ddjs_dzz_unit u,ddjs_dzz_org o  where u.dwxt_org_id = o.id and u.unit_order = '0' and u.visible = '1' ");
		sb.append("and o.visible = '1' and u.unit_attr = '21') sy, ");
		sb.append("(select count(1) as dzz from ddjs_dzz_org t where t.visible = '1' and t.org_Type != '666') zz ");
		return jdbcTemplate.queryForMap(sb.toString());
	}

	@Override
	public Map<String, Object> orgCommendation(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) as sbzdxjjc_org from (select count(1) from (select * from (select * from ddjs_dzz_org t where t.visible = '1') a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) o , ddjs_dzz_commendation c where o.id  = c.party_organization_id and o.visible = '1' and c.visible = '1' ");
		sb.append("and substr(c.grant_time,0,4) = '"+ DateUtil.COMMON.getDateText(new Date()).substring(0,4) +"'  group by party_organization_id )");
		return jdbcTemplate.queryForMap(sb.toString());
	}

	@Override
	public Map<String, Object> orgChangeSituation(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append("(select count(1) as zj_org ");
		sb.append("from (select * from ddjs_dzz_org a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) t where  substr(t.cre_date,0,4) = '"+ DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"' and t.org_type <> '666' and (t.visible = '1' or t.revoke_id is not null)) a, ");
		sb.append("(select count(1) as zj_org_dw ");
		sb.append("from (select * from ddjs_dzz_org a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) t where  substr(t.cre_date,0,4) = '"+DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"' and t.org_type <> '666' and (t.visible = '1' or t.revoke_id is not null) and t.org_type = '611') b, ");
		sb.append("(select count(1) as js_org ");
		sb.append("from (select * from ddjs_dzz_org a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) o,ddjs_dzz_revoke r where o.revoke_id = r.id and substr(o.cre_date,0,4) != '"+DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"' and o.org_type <> '666' and substr(r.revoke_date,0,4) = '"+DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"') c, ");
		sb.append("(select count(1) as js_org_dw ");
		sb.append("   from (select * from ddjs_dzz_org a start with a.id = '"+ id +"'connect by prior a.id = a.super_id) o,ddjs_dzz_revoke r where o.revoke_id = r.id and substr(o.cre_date,0,4) != '"+DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"' and o.org_type <> '666' and substr(r.revoke_date,0,4) = '"+DateUtil.COMMON.getDateText(new Date()).substring(0,4)+"' and o.org_type = '611') d ");
		return jdbcTemplate.queryForMap(sb.toString());
	}

	@Override
	public String getOrgId() {
		String userId = UserUtil.getCruUserId();
		List<DwxtOrg> list = dwxtOrgDao.findByAssociativeUserIdAndVisible(userId,"1");
		if(list.size() > 0){
			return list.get(0).getId();
		}else{
			return null;
		}
	}

//	@Override
//	public String findByOrgId(String id) {
//		return dwxtOrgDao.findByOrgIdAndVisible(orgId,CommonConstants.VISIBLE[1]).getAssociativeUnitId();
//	}


	@Override
	public List<DwxtOrg> lsList(DwxtOrg dwxtOrg){
		String sql = " select t.*,r.REVOKE_DATE,r.REVOKE_CODE,r.REVOKE_REASON from (select a.*,h.history_org_id from (select * from (select *  from ddjs_dzz_org t  start with t.id = '"+dwxtOrg.getId()+"'  connect by prior t.id = t.super_id order siblings by t.org_order) where visible = '0' and revoke_id is not null)a, ddjs_dzz_history_org h where a.id = h.dwxt_org_id)t LEFT JOIN DDJS_DZZ_REVOKE r ON   t.revoke_id = r.id";
		if(StringUtils.isNotBlank(dwxtOrg.getOrgName())){
			sql += " and org_name like '%"+dwxtOrg.getOrgName()+"%'";
		}
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DwxtOrg.class));
	}

}
