package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.vo.DyTreeVO;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.dao.DwxtLdcyDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.services.DwxtHjxjService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DyxxServiceImp implements DyxxService{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HistoryDao historyDao;
	@Autowired
	private DyxxDao dyxxDao;
	@Autowired
	private AdministrativeDao administrativeDao;
	@Autowired
	private DegreeDao degreeDao;
	@Autowired
	private IncreseDao increseDao;
	@Autowired
	private PartyBasicInfoDao partyBasicInfoDao;
	@Autowired
	private PartyDutiesDao partyDutiesDao;
	@Autowired
	private WorkingDao workingDao;
	@Autowired
	private DwxtHjxjService dwxtHjxjService;
	@Autowired
	private DwxtLdcyDao dwxtLdcyDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DyxxTurnOutDao dyxxTurnOutDao;
	@Autowired
	private ZbdydhService zbdydhService;

	@Override
	public DdjsDyglUserbasicinfoEntity saveForm(DdjsDyglUserbasicinfoEntity entity, DdjsDyglPartybasicinfoEntity partybasicinfoEntity,
												DdjsDyglIncreaseEntity increaseEntity) throws IOException {
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
			entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			entity.setIsHistoryparty("0");
			entity.setTypeOfPersonnel("05");
			entity.setPartyOrganizationName(java.net.URLDecoder.decode(entity.getPartyOrganizationName(),"UTF-8"));
			entity.setSuperId(entity.getSuperId());
			// BeanUtils.copyProperties(entity,partybasicinfoEntity);
			//			BeanUtils.copyProperties(entity,partyDutiesEntity);
			entity = dyxxDao.save(entity);
		} else {
			DdjsDyglUserbasicinfoEntity oldEntity = dyxxDao.findOne(entity.getId());
			oldEntity.setSuperId(entity.getSuperId());
			oldEntity.setVisible(CommonConstants.VISIBLE[1]);
			oldEntity.setBirthplace(entity.getBirthplace());
			oldEntity.setContactNumber(entity.getContactNumber());
			oldEntity.setDateOfBirth(entity.getDateOfBirth());
			oldEntity.setRyGegree(entity.getRyGegree());
			oldEntity.setDemocraticParties(entity.getDemocraticParties());
			oldEntity.setWorkingUnit(entity.getWorkingUnit());
			oldEntity.setWorkingTime(entity.getWorkingTime());
			oldEntity.setTechnicalPost(entity.getTechnicalPost());
			oldEntity.setSex(entity.getSex());
			oldEntity.setRegisteredResidence(entity.getRegisteredResidence());
			oldEntity.setPinCodes(entity.getPinCodes());
			oldEntity.setPartyOrganizationName(entity.getPartyOrganizationName());
			oldEntity.setPartyOrganizationId(entity.getPartyOrganizationId());
			oldEntity.setNewStratum(entity.getNewStratum());
			oldEntity.setNativePlace(entity.getNativePlace());
			oldEntity.setNation(entity.getNation());
			oldEntity.setName(entity.getName());
			oldEntity.setMaritalStatus(entity.getMaritalStatus());
			oldEntity.setRyMajor(entity.getRyMajor());
			oldEntity.setLocalPoliceStation(entity.getLocalPoliceStation());
			oldEntity.setJobLevel(entity.getJobLevel());
			oldEntity.setIsProbationary(entity.getIsProbationary());
			oldEntity.setIsPartyRepresentative(entity.getIsPartyRepresentative());
			oldEntity.setIsHistoryparty(entity.getIsHistoryparty());
			oldEntity.setRyIdentity(entity.getRyIdentity());
			oldEntity.setHomeAddress(entity.getHomeAddress());
			oldEntity.setHistoryTime(entity.getHistoryTime());
			oldEntity.setHistoryPartyname(entity.getHistoryPartyname());
			oldEntity.setHistoryPartyid(entity.getHistoryPartyid());
			oldEntity.setHealthStatus(entity.getHealthStatus());
			oldEntity.setRyGraduationAcademy(entity.getRyGraduationAcademy());
			oldEntity.setGoPartyname(entity.getGoPartyname());
			oldEntity.setGoPartyid(entity.getGoPartyid());
			oldEntity.setFrontlineSituation(entity.getFrontlineSituation());
			oldEntity.setFamilyOrigin(entity.getFamilyOrigin());
			oldEntity.setRyEducation(entity.getRyEducation());
			oldEntity.setIsHistoryparty("0");
			oldEntity.setUserId(entity.getUserId());
			oldEntity.setSuperId(entity.getSuperId());
			entity =  dyxxDao.saveAndFlush(oldEntity);
			//党员增加情况
			if(StringUtils.isNotBlank(increaseEntity.getIncreaseSuperId())){
				DdjsDyglIncreaseEntity increaseEntityOld =increseDao.findByIncreaseSuperIdAndVisible(increaseEntity.getIncreaseSuperId(),CommonConstants.VISIBLE[1]);
				if(increaseEntityOld!=null&&increaseEntityOld.getIncreaseId()!=null&&(!increaseEntityOld.getIncreaseId().equals(""))){
					increaseEntity.setIncreaseId(increaseEntityOld.getIncreaseId());
				}
				BeanUtils.copyProperties(entity,increaseEntity);
				increaseEntity = increseDao.save(increaseEntity);
			}
			String  xx = partybasicinfoEntity.getPartybasicinfoSuperId();

			if(StringUtils.isNotBlank(partybasicinfoEntity.getPartybasicinfoSuperId())) {
				DdjsDyglPartybasicinfoEntity partybasicinfoEntityOld =partyBasicInfoDao.findByPartybasicinfoSuperIdAndVisible(partybasicinfoEntity.getPartybasicinfoSuperId(),CommonConstants.VISIBLE[1]);
				BeanUtils.copyProperties(entity, partybasicinfoEntity);
				if(partybasicinfoEntityOld!=null&&partybasicinfoEntityOld.getPartybasicinfoId()!=null&&(!partybasicinfoEntityOld.getPartybasicinfoId().equals(""))){
					partybasicinfoEntity.setPartybasicinfoId(partybasicinfoEntityOld.getPartybasicinfoId());
				}
				partybasicinfoEntity = partyBasicInfoDao.save(partybasicinfoEntity);
			}
			//行政职务
//			List<DdjsDyglAdministrativeEntity> administrativeEntityList = new ArrayList<DdjsDyglAdministrativeEntity>();
//			administrativeEntity.setAdministrativeSuperId(entity.getId());
//			administrativeEntity = administrativeDao.save(administrativeEntity);
//			administrativeEntityList.add(administrativeEntity);
//			/*if(StringUtil.isBlank(administrativeEntity.getAdministrativeId())){
//				administrativeEntity.setAdministrativeSuperId(entity.getId());
//				administrativeEntity = administrativeDao.save(administrativeEntity);
//				administrativeEntityList.add(administrativeEntity);
//			}else{
//				DdjsDyglAdministrativeEntity oldAdministrativeEntity= administrativeDao.findOne(degreeEntity.getDegreeId());
//				oldAdministrativeEntity.setAdministrativeSuperId(entity.getId());
//				administrativeDao.save(oldAdministrativeEntity);
//				administrativeEntityList.add(oldAdministrativeEntity);
//			}*/
//
//			//学位学历
//			List<DdjsDyglDegreeEntity> degreeEntityList = new ArrayList<DdjsDyglDegreeEntity>();
//			degreeEntity.setDegreeSuperId(entity.getId());
//			degreeEntity = degreeDao.save(degreeEntity);
//			degreeEntityList.add(degreeEntity);
//			/*if(StringUtil.isBlank(degreeEntity.getDegreeId())){
//				degreeEntity.setDegreeSuperId(entity.getId());
//				degreeEntity = degreeDao.save(degreeEntity);
//				degreeEntityList.add(degreeEntity);
//			}else{
//				DdjsDyglDegreeEntity oldDegreeEntity= degreeDao.findOne(degreeEntity.getDegreeId());
//				BeanUtils.copyProperties(oldDegreeEntity,degreeEntity);
//				oldDegreeEntity.setDegreeSuperId(entity.getId());
//				degreeDao.save(oldDegreeEntity);
//				degreeEntityList.add(oldDegreeEntity);
//			}*/
//
////			//党员增加情况
//			List<DdjsDyglIncreaseEntity> increaseEntityList = new ArrayList<DdjsDyglIncreaseEntity>();
//			increaseEntity.setIncreaseSuperId(entity.getId());
//			increaseEntity = increseDao.save(increaseEntity);
//			increaseEntityList.add(increaseEntity);
//			/*if(StringUtil.isBlank(increaseEntity.getIncreaseId())){
//				increaseEntity.setIncreaseSuperId(entity.getId());
//				increaseEntity = increseDao.save(increaseEntity);
//				increaseEntityList.add(increaseEntity);
//			}else{
//				DdjsDyglIncreaseEntity oldIncreaseEntity= increseDao.findOne(increaseEntity.getIncreaseId());
//				BeanUtils.copyProperties(oldIncreaseEntity,increaseEntity);
//				oldIncreaseEntity.setIncreaseSuperId(entity.getId());
//				increseDao.save(oldIncreaseEntity);
//				increaseEntityList.add(oldIncreaseEntity);
//			}*/
//
//			//工作岗位
//			List<DdjsDyglWorkingEntity> workingEntityList = new ArrayList<DdjsDyglWorkingEntity>();
//			workingEntity = workingDao.save(workingEntity);
//			workingEntityList.add(workingEntity);
//			/*	if(StringUtil.isBlank(workingEntity.getWorkingId())){
//				workingEntity.setWorkingSuperId(entity.getId());
//				workingEntity = workingDao.save(workingEntity);
//				workingEntityList.add(workingEntity);
//			}else{
//				DdjsDyglWorkingEntity oldWorkingEntity= workingDao.findOne(workingEntity.getWorkingId());
//				BeanUtils.copyProperties(oldWorkingEntity,workingEntity);
//				oldWorkingEntity.setWorkingSuperId(entity.getId());
//				workingDao.save(oldWorkingEntity);
//				workingEntityList.add(oldWorkingEntity);
//			}*/
//
//			//工作岗位
//			List<DdjsDyglPartyDutiesEntity> partyDutiesEntityList = new ArrayList<DdjsDyglPartyDutiesEntity>();
//			partyDutiesEntity.setPartyDutiesSuperId(entity.getId());
//			//partyDutiesEntity = partyDutiesDao.save(partyDutiesEntity);
//			//partyDutiesEntityList.add(partyDutiesEntity);
//			/*if(StringUtil.isBlank(partyDutiesEntity.getPartyDutiesId())){
//				partyDutiesEntity.setPartyDutiesSuperId(entity.getId());
//				partyDutiesEntity = partyDutiesDao.save(partyDutiesEntity);
//				partyDutiesEntityList.add(partyDutiesEntity);
//			}else{
//				DdjsDyglPartyDutiesEntity oldPartyDutiesEntity = partyDutiesDao.findOne(partyDutiesEntity.getPartyDutiesId());
//				BeanUtils.copyProperties(oldPartyDutiesEntity,partyDutiesEntity);
//				oldPartyDutiesEntity.setPartyDutiesSuperId(entity.getId());
//				partyDutiesDao.save(oldPartyDutiesEntity);
//				partyDutiesEntityList.add(oldPartyDutiesEntity);
//			}*/
//
//			//党员基本情况
//			List<DdjsDyglPartybasicinfoEntity> partybasicinfoEntityList = new ArrayList<DdjsDyglPartybasicinfoEntity>();
//			partybasicinfoEntity.setPartybasicinfoSuperId(entity.getId());
//			partybasicinfoEntity = partyBasicInfoDao.save(partybasicinfoEntity);
//			partybasicinfoEntityList.add(partybasicinfoEntity);
//			/*if(StringUtil.isBlank(partybasicinfoEntity.getPartybasicinfoId())){
//				partybasicinfoEntity.setPartybasicinfoSuperId(entity.getId());
//				partybasicinfoEntity = partyBasicInfoDao.save(partybasicinfoEntity);
//				partybasicinfoEntityList.add(partybasicinfoEntity);
//			}else{
//				DdjsDyglPartybasicinfoEntity oldPartybasicinfoEntity = partyBasicInfoDao.findOne(partybasicinfoEntity.getPartybasicinfoId());
//				BeanUtils.copyProperties(oldPartybasicinfoEntity,partybasicinfoEntity);
//				oldPartybasicinfoEntity.setPartybasicinfoSuperId(entity.getId());
//				partyBasicInfoDao.save(oldPartybasicinfoEntity);
//				partybasicinfoEntityList.add(oldPartybasicinfoEntity);
//			}*/
//			entity.setWorkingEntityList(workingEntityList);
//			entity.setPartyDutiesEntityList(partyDutiesEntityList);
//			entity.setDegreeEntityList(degreeEntityList);
//			entity.setPartybasicinfoEntity(partybasicinfoEntityList);
//			entity.setAdministrativeEntityList(administrativeEntityList);
//			entity.setIncreaseEntityEntityList(increaseEntityList);
		}
		return entity;
	}

/*	@Override
	public PageImpl getHistroyList(Pageable pageable, PageImpl pageImpl, DdjsDyglUserbasicinfoEntity entity,String ids) {
		ids = DzzUtil.spiltString(ids);
		StringBuilder sql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sql.append(" from DdjsDyglUserbasicinfoEntity t ");
		sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		//sql.append(" and t.isHistoryparty = '1'");
		sql.append(" and t.typeOfPersonnel like '%06%'");
		sql.append(" and partyOrganizationId in ("+ids+")");
		// 姓名
		if (StringUtils.isNotBlank(entity.getName()) ) {
			sql.append(" and t.name = ?");
			para.add(entity.getName());
		}

		// 性别
		if (StringUtils.isNotBlank(entity.getSex()) ) {
			sql.append(" and t.sex = ?");
			para.add(entity.getSex());
		}

		// 民族
		if (StringUtils.isNotBlank(entity.getNation())) {
			sql.append(" and t.nation = ?");
			para.add(entity.getNation());
		}

		// 学历
		if (StringUtils.isNotBlank(entity.getRyEducation())) {
			sql.append(" and t.education = ?");
			para.add(entity.getRyEducation());
		}

		Page<DdjsDyglUserbasicinfoEntity> page = dyxxDao.query(sql.toString(), pageable, para.toArray());
		// 添加列表操作
		List<DdjsDyglUserbasicinfoEntity> content = page.getContent();
		*//*for (DdjsDyglUserbasicinfoEntity entity : content) {
			entity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}*//*
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}*/
@Override
public PageImpl getHistroyList(Pageable pageable, PageImpl pageImpl, DdjsDyglHistoryEntity entity,String ids) {
	ids = DzzUtil.spiltString(ids);
	StringBuilder sql = new StringBuilder();
	List<Object> para = new ArrayList<>();
	sql.append(" from DdjsDyglHistoryEntity t ");
	sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
	//sql.append(" and t.isHistoryparty = '1'");

	sql.append(" and partyOrganizationId in ("+ids+")");
	// 姓名
	if (StringUtils.isNotBlank(entity.getName()) ) {
		sql.append(" and t.name = ?");
		para.add(entity.getName());
	}

	// 性别
	if (StringUtils.isNotBlank(entity.getSex()) ) {
		sql.append(" and t.sex = ?");
		para.add(entity.getSex());
	}

	// 民族
	if (StringUtils.isNotBlank(entity.getNation())) {
		sql.append(" and t.nation = ?");
		para.add(entity.getNation());
	}


	if (StringUtils.isNotBlank(entity.getRyEducation())) {
		sql.append(" and t.ryEducation = ?");
		para.add(entity.getRyEducation());
	}

	Page<DdjsDyglHistoryEntity> page = historyDao.query(sql.toString(), pageable, para.toArray());
	// 添加列表操作
	List<DdjsDyglHistoryEntity> content = page.getContent();
		/*for (DdjsDyglUserbasicinfoEntity entity : content) {
			entity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}*/
	return new PageImpl((int) page.getTotalElements(), page.getContent());
}


	@Override
	public PageImpl getListForObject(Pageable pageable, PageImpl pageImpl, Object entity, String superId, String type) {
		StringBuilder sql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		Page<Object> page = null;
		String whereSql = " where t.visible = '" + CommonConstants.VISIBLE[1] + "'";
		sql.append(" from ");
		if("3".equals(type)){
			sql.append(" DdjsDyglPartyDutiesEntity t ").append(whereSql).append(" and t.partyDutiesSuperId ='").append(superId).append("' ");
			page = partyDutiesDao.query(sql.toString(),pageable,para.toArray());
		}else if("2".equals(type)){
			sql.append(" DdjsDyglAdministrativeEntity t ").append(whereSql).append(" and t.administrativeSuperId ='").append(superId).append("' ");
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				sql.append(" order by t.xzTenureTime desc ");
			} else {
				sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}
			page = administrativeDao.query(sql.toString(),pageable,para.toArray());
		}else if("5".equals(type)){
			sql.append(" DdjsDyglDegreeEntity t ").append(whereSql).append(" and t.degreeSuperId ='").append(superId).append("' ");
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				sql.append(" order by t.enrolmentTime desc ");
			} else {
				sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}
			page = degreeDao.query(sql.toString(),pageable,para.toArray());
		}else{
			sql.append(" DdjsDyglWorkingEntity t ").append(whereSql).append(" and t.workingSuperId ='").append(superId).append("' ");
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				sql.append(" order by t.beforeTime desc ");
			} else {
				sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
			}
			page = workingDao.query(sql.toString(),pageable,para.toArray());
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglUserbasicinfoEntity entity,String type,String ids) {
		ids = DzzUtil.spiltString(ids);
		StringBuilder sql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sql.append(" from DdjsDyglUserbasicinfoEntity t ");
		sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		sql.append(" and partyOrganizationId in ("+ids+")");
		//sql.append(" and t.isHistoryparty = '0'");
		if(type.equals("01")){
			String typeArry [] = entity.getTypeOfPersonnel().split(",");
			List typeVal = Arrays.asList(typeArry);
				sql.append(" and t.typeOfPersonnel like '%" + entity.getTypeOfPersonnel() + "%'");
				sql.append(" and INSTR(typeOfPersonnel,'04')<=0");
				sql.append(" and INSTR(typeOfPersonnel,'05')<=0");
		}else{
			sql.append(" and  (INSTR(typeOfPersonnel,'05')>0 ");
			sql.append(" or  INSTR(typeOfPersonnel,'04')>0) ");
			sql.append(" and typeOfPersonnel not like '%06%'");
		}
		if(StringUtils.isNotBlank(entity.getName())){
			sql.append(" and NAME like '%").append(entity.getName().replaceAll(" ", "")).append("%' ");
		}
		if(StringUtils.isNotBlank(entity.getNation())){
			sql.append(" and nation like '%").append(entity.getNation().replaceAll(" ", "")).append("%' ");
		}
		if(StringUtils.isNotBlank(entity.getRyEducation())){
			sql.append(" and education like '%").append(entity.getRyEducation().replaceAll(" ", "")).append("%' ");
		}
	/*	if(StringUtils.isNotBlank(entity.getTypeOfPersonnel())){
			sql.append(" and typeOfPersonnel like '%").append(entity.getTypeOfPersonnel().replaceAll(" ", "")).append("%' ");
		}*/
		if(StringUtils.isNotBlank(entity.getSex())){
			sql.append(" and sex = '"+entity.getSex()+"'" );
		}
//		if(StringUtils.isNotBlank(entity.getPartyOrganizationId())){
//			sql.append(" and t.partyOrganizationId like '").append(entity.getPartyOrganizationId()).append("%' ");
//		}
		if(StringUtils.isNotBlank(entity.getSex())){
			sql.append(" and t.sex = '").append(entity.getSex()).append("' ");
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sql.append(" order by t.creTime desc ");
		} else {
			sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<DdjsDyglUserbasicinfoEntity> page = dyxxDao.query(sql.toString(), pageable, para.toArray());
		// 添加列表操作
		List<DdjsDyglUserbasicinfoEntity> content = page.getContent();


		for(DdjsDyglUserbasicinfoEntity userEntity : content){
			String[] typeSplit = userEntity.getTypeOfPersonnel().split(",");
			List<String> list1 = Arrays.asList(typeSplit);
			if(list1.contains("05")){
				userEntity.setTypeOfPersonnel("05");
			}else if(list1.contains("04")){
				userEntity.setTypeOfPersonnel("04");
			}else if(list1.contains("03")){
				userEntity.setTypeOfPersonnel("03");
			}else if(list1.contains("02")){
				userEntity.setTypeOfPersonnel("02");
			}else if(list1.contains("01")){
				userEntity.setTypeOfPersonnel("01");
			}
			//根据党组织库更新查询页面党组织名称
			String id = userEntity.getPartyOrganizationId()==null?"":(String)userEntity.getPartyOrganizationId();
			String  PartyOrganizationName = "";//组织名称

			List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
			if(listOrgName.size()>0){
				PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
			}
			userEntity.setPartyOrganizationName(PartyOrganizationName);
		}
		/*for (DdjsDyglUserbasicinfoEntity entity : content) {
			entity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}*/
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public int delete(DdjsDyglUserbasicinfoEntity entity) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int n = 0;
		int m =0;
		int b =0;
		if(StringUtils.isNotBlank(entity.getId())){
			try {
				//逻辑删除试题
				String delQuestion = "update DdjsDyglUserbasicinfoEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+entity.getId()+"'";
				n = dyxxDao.update(delQuestion);
				//更新党员基本情况
				String updatPartyBasic = "update DdjsDyglPartybasicinfoEntity q set q.locationpartygroup='' where q.partybasicinfoSuperId='"+entity.getId()+"'";
				m = partyBasicInfoDao.update(updatPartyBasic);
				//删除党员增加情况
				String delIncrease = "update DdjsDyglIncreaseEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.increaseSuperId='"+entity.getId()+"'";
				b = increseDao.update(delIncrease);
			} catch (Exception e) {
				log.info(e.getMessage());
				log.info("删除人员基本情况出现异常！");
				n=0;
			}
		}
		return n;
	}

	@Override
	public Object getById(String id, String type) {
		Object obj = null;
		if("2".equals(type)){
			obj = partyBasicInfoDao.findByPartybasicinfoSuperId(id);
			if(null == obj){
				obj = new DdjsDyglPartybasicinfoEntity();
			}
		}else if("3".equals(type)){
			obj = increseDao.findByIncreaseSuperIdAndVisible(id,CommonConstants.VISIBLE[1]);;
			if(null == obj){
				obj = new DdjsDyglIncreaseEntity();
			}
		}else if("4".equals(type)){//工作岗位
			obj = workingDao.findFirstByWorkingSuperIdAndVisibleOrderByBeforeTimeDesc(id,CommonConstants.VISIBLE[1]);
			if(null == obj){
				obj = new DdjsDyglWorkingEntity();
			}
		}else if("5".equals(type)){
//			DdjsDyglUserbasicinfoEntity dyxx = dyxxDao.findOne(id);
			List<DwxtLdcy> list =  dwxtHjxjService.findByUserId(id);
			if(list.size() > 0){
				obj = list.get(0);
			}else{
				obj = new DwxtLdcy();
			}
		}else if("6".equals(type)){//行政职务
			obj = administrativeDao.findFirstByAdministrativeSuperIdAndVisibleOrderByXzTenureTimeDesc(id,CommonConstants.VISIBLE[1]);
			if(null == obj){
				obj = new DdjsDyglAdministrativeEntity();
			}
		}else if("7".equals(type)){//学位学历
			obj = degreeDao.findFirstByDegreeSuperIdAndVisibleOrderByEnrolmentTimeDesc(id,CommonConstants.VISIBLE[1]);

			if(null == obj){
				obj = new DdjsDyglDegreeEntity();
			}
		}else if("8".equals(type)){//历史党员基本信息页面
			obj = historyDao.findOne(id);

			if(null == obj){
				obj = new DdjsDyglDegreeEntity();
			}
		}else if("9".equals(type)){//党员减少情况页面
			obj = dyxxTurnOutDao.findOne(id);

			if(null == obj){
				obj = new DdjsDyglTurnoutEntity();
			}
		}else{
			obj = dyxxDao.findOne(id);
		}
		return obj;
	}


	@Override
	public Object getTeamSituation() {
		String isHistroyStatus = "0";
		String typeOfPersonnel1 = "04";
		String typeOfPersonnel2 = "05";
		String sexType = "2";
		String notNation = "01";
		String notEducatcion = "4";
		TeamSituationTotal teamSituationTotal = new TeamSituationTotal();
		int partyMemberCount = dyxxDao.countByVisibleAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],typeOfPersonnel1,typeOfPersonnel2);//获取党员总数
		teamSituationTotal.setPartyMember(transforByInt(partyMemberCount));

		int sexPrtyMemberCount = dyxxDao.countByVisibleAndSexAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],sexType,typeOfPersonnel1,typeOfPersonnel2);//获取女党员人数
		teamSituationTotal.setFemalePartyMember(transforByInt(sexPrtyMemberCount));
		teamSituationTotal.setFemalePartyMemberBl(getComponent(sexPrtyMemberCount,partyMemberCount));//获取女党员党员人数比例

		int minNationCount = dyxxDao.countByVisibleAndNationNotAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],notNation,typeOfPersonnel1,typeOfPersonnel2);//获取少数民族数
		teamSituationTotal.setMinNationPartyMember(transforByInt(minNationCount));
		teamSituationTotal.setMinNationPartyMemberBl(getComponent(minNationCount,partyMemberCount));

		int eduationCount = dyxxDao.countByVisibleAndRyEducationNotAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],notEducatcion,typeOfPersonnel1,typeOfPersonnel2);//获取大专以上党员数
		teamSituationTotal.setCollegeAndAboveDmPartyMember(transforByInt(eduationCount));
		teamSituationTotal.setCollegeAndAbovePartyMemberBl(getComponent(eduationCount,partyMemberCount));

		int thirtyFiveUnderPartyMemberCount = this.getPartyMemberCountByAgeRange("<= '35'");//获取35 岁及以下的党员数
		teamSituationTotal.setThirtyFiveUnderPartyMember(transforByInt(thirtyFiveUnderPartyMemberCount));
		teamSituationTotal.setThirtyFiveUnderPartyMemberBl(getComponent(thirtyFiveUnderPartyMemberCount,partyMemberCount));

		int thirtySixAbovePartyMemberCount = this.getPartyMemberCountByAgeRange(">= '36' and a.age <= '45'");//获取36 岁至45 岁的党员数
		teamSituationTotal.setThirtySixAbovePartyMember(transforByInt(thirtySixAbovePartyMemberCount));
		teamSituationTotal.setThirtySixAbovePartyMemberBl(getComponent(thirtySixAbovePartyMemberCount,partyMemberCount));

		int fortySixAbovePartyMemberCount = this.getPartyMemberCountByAgeRange(">= '46' and a.age <= '60'");//获取46 岁至60 岁的党员数
		teamSituationTotal.setFortySixAbovePartyMember(transforByInt(fortySixAbovePartyMemberCount));
		teamSituationTotal.setFortySixAbovePartyMemberBl(getComponent(fortySixAbovePartyMemberCount,partyMemberCount));

		int sixtyOneAbovePartyMemberCount = this.getPartyMemberCountByAgeRange(">= '61'");//获取61 岁及以上的党员数
		teamSituationTotal.setSixtyOneAbovePartyMember(transforByInt(sixtyOneAbovePartyMemberCount));
		teamSituationTotal.setSixtyOneAbovePartyMemberBl(getComponent(sixtyOneAbovePartyMemberCount,partyMemberCount));

		int beforEstablishmentPartyMemberCount  = this.getPartyMemberCountByJoinPartyTime("","1949-10-01");//建国前入党的党员数
		teamSituationTotal.setBeforEstablishmentPartyMember(transforByInt(beforEstablishmentPartyMemberCount));
		teamSituationTotal.setBeforEstablishmentPartyMemberBl(getComponent(beforEstablishmentPartyMemberCount,partyMemberCount));

		int afterEstablishmentPartyMemberCount = this.getPartyMemberCountByJoinPartyTime("1949-10-01","1966-05-01");//获取建国后至“文革”前入党的党员数
		teamSituationTotal.setAfterEstablishmentPartyMember(transforByInt(afterEstablishmentPartyMemberCount));
		teamSituationTotal.setAfterEstablishmentPartyMemberBl(getComponent(afterEstablishmentPartyMemberCount,partyMemberCount));

		int greatRevolutionPartyMemberCount = this.getPartyMemberCountByJoinPartyTime("1966-05-01","1976-10-01");//获取“文革”期间 入党的党员数
		teamSituationTotal.setGreatRevolutionPartyMember(transforByInt(greatRevolutionPartyMemberCount));

		int gangFourpartyMemberCount = this.getPartyMemberCountByJoinPartyTime("1976-10-01","1978-12-18");//获取粉碎“四人帮”至党的十一届三中全会前入党的党员数
		teamSituationTotal.setGangFourpartyMember(transforByInt(gangFourpartyMemberCount));
		teamSituationTotal.setGangFourpartyMemberBl(getComponent(gangFourpartyMemberCount,partyMemberCount));

		int eleventhThirdPartyMemberCount = this.getPartyMemberCountByJoinPartyTime("1978-12-18","2002-11-08");//获取党的十一届三中全会后 至党的十六大前入党的党员数
		teamSituationTotal.setEleventhThirdPartyMember(transforByInt(eleventhThirdPartyMemberCount));
		teamSituationTotal.setEleventhThirdPartyMemberBl(getComponent(eleventhThirdPartyMemberCount,partyMemberCount));

		int sixteenMajorMemberCount = this.getPartyMemberCountByJoinPartyTime("2002-11-08","2012-11-08");//获取党的十六大后至党的十八大前入党的党员数
		teamSituationTotal.setSixteenMajorPartyMember(transforByInt(sixteenMajorMemberCount));
		teamSituationTotal.setSixteenMajorPartyMemberBl(getComponent(sixteenMajorMemberCount,partyMemberCount));

		int eighteenthMajorPartyMemberCount = this.getPartyMemberCountByJoinPartyTime("2012-11-08","");//获取党的十八大以来入党的党员数
		teamSituationTotal.setEighteenthMajorPartyMember(transforByInt(eighteenthMajorPartyMemberCount));
		teamSituationTotal.setEighteenthMajorPartyMemberBl(getComponent(eighteenthMajorPartyMemberCount,partyMemberCount));

		//1.4   党员的职业
		teamSituationTotal.setWorkerPartyMember(transforByInt(0));
		teamSituationTotal.setWorkerPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setAdminPartyMember(transforByInt(0));
		teamSituationTotal.setAdminPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setOfficePartyMember(transforByInt(0));
		teamSituationTotal.setOfficePartyMemberBl(getComponent(0,0));
		teamSituationTotal.setRetirementPartyMember(transforByInt(0));
		teamSituationTotal.setRetirementPartyMemberBl(getComponent(0,0));

		//二、本年发展党员情况
		String nowYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		//2.1   发展党员的性别、民族、年龄和学历
		int dmPartyMemberCount = dyxxDao.countByVisibleAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],nowYear,typeOfPersonnel1,typeOfPersonnel2);//发展党员总数
		teamSituationTotal.setDmPartyMember(transforByInt(dmPartyMemberCount));

		int sexDmPrtyMemberCount = dyxxDao.countByVisibleAndSexAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],sexType,nowYear,typeOfPersonnel1,typeOfPersonnel2);//发展女党员数
		teamSituationTotal.setFemaleDmPartyMember(transforByInt(sexDmPrtyMemberCount));
		teamSituationTotal.setFemaleDmPartyMemberBl(getComponent(sexDmPrtyMemberCount,dmPartyMemberCount));

		int minNationDmCount = dyxxDao.countByVisibleAndNationNotAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],notNation,nowYear,typeOfPersonnel1,typeOfPersonnel2);//发展少数民族党员数
		teamSituationTotal.setMinNationDmPartyMember(transforByInt(minNationDmCount));
		teamSituationTotal.setMinNationDmPartyMemberBl(getComponent(minNationDmCount,dmPartyMemberCount));

		int eduationDmCount = dyxxDao.countByVisibleAndRyEducationNotAndCreTimeLikeAndTypeOfPersonnelContainingOrTypeOfPersonnelContaining(CommonConstants.VISIBLE[1],notEducatcion,nowYear,typeOfPersonnel1,typeOfPersonnel2);//获取大专以上党员数
		teamSituationTotal.setCollegeAndAboveDmPartyMember(transforByInt(eduationDmCount));
		teamSituationTotal.setCollegeAndAboveDmPartyMemberBl(getComponent(eduationDmCount,dmPartyMemberCount));

		int thirtyFiveUnderDmPartyMemberCount = this.getPartyMemberCountByAgeRange("<= '35'",nowYear);//获取35 岁及以下的党员数
		teamSituationTotal.setThirtyFiveUnderDmPartyMember(transforByInt(thirtyFiveUnderDmPartyMemberCount));
		teamSituationTotal.setThirtyFiveUnderDmPartyMemberBl(getComponent(thirtyFiveUnderDmPartyMemberCount,dmPartyMemberCount));

		//2.2   发展党员的职业
		teamSituationTotal.setWorkerDmPartyMember(transforByInt(0));
		teamSituationTotal.setWorkerDmPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setAdminDmPartyMember(transforByInt(0));
		teamSituationTotal.setAdminDmPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setOfficeDmPartyMember(transforByInt(0));
		teamSituationTotal.setOfficeDmPartyMemberBl(getComponent(0,0));


		//2.3   在生产、工作一线和新的社会阶层中发展党员
		teamSituationTotal.setWorkerFrontlineDmPartyMember(transforByInt(0));
		teamSituationTotal.setWorkerFrontlineDmPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setAdvancedDmPartyMember(transforByInt(0));
		teamSituationTotal.setAdvancedDmPartyMemberBl(getComponent(0,0));
		teamSituationTotal.setLeagueDmPartyMember(transforByInt(0));
		teamSituationTotal.setLeagueDmPartyMemberBl(getComponent(0,0));

		//3     党员表彰情况
		teamSituationTotal.setFinePartyMember(transforByInt(0));
		teamSituationTotal.setFineWorker(getComponent(0,0));

		return this.getJsonByTeamSituationTotal(teamSituationTotal);
	}

	@Override
	public List<DyTreeVO> findChild(DwxtOrg dwxtOrg,String ryType) {
		String sql3 = "select * from ddjs_org_user_view s where PARTYTYPE='partyType'" ;
		if("04".equals(ryType)){
			sql3 += "  or (PARTYTYPE like '%04%' or PARTYTYPE like '%05%') ";
		}else if("01".equals(ryType)){
			sql3 += "  OR(PARTYTYPE like '%01%' ";
			sql3 +=" and (INSTR(PARTYTYPE,'04')<=0";
			sql3 +=" and INSTR(PARTYTYPE,'05')<=0)) ";
		}else if("02".equals(ryType)){
			sql3 += " OR( PARTYTYPE like '%02%' ";
			sql3 +=" and (INSTR(PARTYTYPE,'04')<=0";
			sql3 +=" and INSTR(PARTYTYPE,'05')<=0))";
		}else if("03".equals(ryType)){
			sql3 += " OR( PARTYTYPE like '%03%' ";
			sql3 +=" and (INSTR(PARTYTYPE,'04')<=0";
			sql3 +=" and INSTR(PARTYTYPE,'05')<=0))";
		}else{
			sql3 += " OR( PARTYTYPE like '%04%' or PARTYTYPE like '%05%')" ;
		}
		List<DyTreeVO> voList = this.jdbcTemplate.query(sql3,new BeanPropertyRowMapper<>(DyTreeVO.class));


		/*String sql = " select * from (select * from ddjs_dzz_org where visible = '"+ CommonConstants.VISIBLE[1]+"') t ";
		if(StringUtils.isNotBlank(dwxtOrg.getOrgName())){
			sql += " and org_name like '%"+dwxtOrg.getOrgName()+"%'";
		}
		sql += " start with t.org_id = '" + dwxtOrg.getOrgId() + "' connect by prior t.org_id = t.super_id order siblings by t.org_order";
		List<DwxtOrg> orgList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DwxtOrg.class));
		String userSql = "select * from ddjs_dygl_userbasicinfo where visible = '1' and party_organization_id like '" + dwxtOrg.getOrgId() + "%'";
		if("04".equals(ryType)){
			userSql += " and (TYPE_OF_PERSONNEL = '04' or TYPE_OF_PERSONNEL = '05') ";
		}else if("01".equals(ryType)){
			userSql += " and TYPE_OF_PERSONNEL like '%01%' ";
			userSql +=" and (INSTR(type_of_personnel,'04')<=0";
			userSql +=" or INSTR(type_of_personnel,'05')<=0)";
		}else if("02".equals(ryType)){
			userSql += " and TYPE_OF_PERSONNEL = '02' ";
		}else if("03".equals(ryType)){
			userSql += " and TYPE_OF_PERSONNEL = '03' ";
		}
		userSql += " order by party_organization_id ";
		List<DdjsDyglUserbasicinfoEntity> userList  =jdbcTemplate.query(userSql,new BeanPropertyRowMapper<>(DdjsDyglUserbasicinfoEntity.class));
		List<DyTreeVO> userTreeList = new ArrayList<>();
		List<DyTreeVO> deptTreeList = new ArrayList<>();
		for(DwxtOrg dwxtOrg1 : orgList){
			DyTreeVO dwxtTreeVO = new DyTreeVO();
			dwxtTreeVO.setId(dwxtOrg1.getOrgId());
			dwxtTreeVO.setName(dwxtOrg1.getOrgName());
			dwxtTreeVO.setpId(dwxtOrg1.getSuperId());
			dwxtTreeVO.setZid(dwxtOrg1.getId());
			dwxtTreeVO.setType("dept");
			deptTreeList.add(dwxtTreeVO);
		}
		for(int i =0;i<userList.size();i++){
			DyTreeVO dwxtTreeVO = new DyTreeVO();
			dwxtTreeVO.setId(userList.get(i).getId());
			dwxtTreeVO.setName(userList.get(i).getName());
			dwxtTreeVO.setpId(userList.get(i).getPartyOrganizationId());
			dwxtTreeVO.setZid("");
			dwxtTreeVO.setType("user");
			userTreeList.add(dwxtTreeVO);
		}
		userTreeList.addAll(deptTreeList);*/
		return voList;
	}

	@Override
	public List<DdjsDyglUserbasicinfoEntity> findByPartyOrganizationIdAndVisible(String PartyOrganizationId, String visible) {
		return dyxxDao.findByPartyOrganizationIdAndVisible(PartyOrganizationId,visible);
	}

	@Override
	public DdjsDyglUserbasicinfoEntity findOne(String id) {
		return dyxxDao.findOne(id);
	}


	public String transforByInt(int count){
		String str = "";
		if(count != 0 && count > 0){
			str = Integer.toString(count);
		}
		return StringUtils.isNotBlank(str) ? str : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}

	/**
	 * 	获取在党员中不同类型占有比例
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @return
	 */
	public String getComponent(int dividend, int divisor){
		NumberFormat nf = NumberFormat.getPercentInstance();
		String bl = "";
		//除数不为0时进行计算
		if(divisor !=0){
			bl = nf.format(dividend / divisor);
		}

		return StringUtils.isNotBlank(bl) ? bl : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%";
	}

	/**
	 * 根据年龄范围获取符合条件的党员人数
	 * @param ageRangeSql 年龄范围
	 * @return
	 */
	public int getPartyMemberCountByAgeRange(String  ageRangeSql){
		int ageRangeCountStr = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql  = new StringBuilder(" select count(*) ");
		sql.append(" from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth")
				.append(", 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where")
				.append(this.sqlWhere())
				.append(") a where a.age ")
				.append(ageRangeSql).append(" ");
		int ageRangeCount = this.jdbcTemplate.queryForObject(sql.toString(),Integer.class);
		return ageRangeCountStr;
	}

	public String sqlWhere(){
		String wheresql = " VISIBLE ='"+ CommonConstants.VISIBLE[1];
		wheresql += "' and  (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0) ";
		return wheresql;
	}

	/**
	 * 根据年龄范围获取符合条件的本年度党员人数
	 * @param ageRangeSql 年龄范围
	 * @return
	 */
	public int getPartyMemberCountByAgeRange(String  ageRangeSql,String nowYear){
		int ageRangeCountStr = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql  = new StringBuilder(" select count(*) ");
		sql.append(" from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth")
				.append(", 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where ")
				.append(this.sqlWhere()).append(" and u.cre_time like '%")
				.append(nowYear).append("%') a where a.age ")
				.append(ageRangeSql).append(" ");
		int ageRangeCount = this.jdbcTemplate.queryForObject(sql.toString(),Integer.class);
		return ageRangeCountStr;
	}


	/*public int countByJoinPartyTimeLessThan(String joinTime);
	public int countByJoinPartyTimeGreaterThanAndJoinPartyTimeLessThan(String startTime,String endTime);
	public int countByJoinPartyTimeGreaterThan(String startTime);*/

	/**
	 * 通过入党时间获取党员数量
	 * @param joinTimeStart
	 * @param joinTimeEnd
	 * @return count
	 */
	public int getPartyMemberCountByJoinPartyTime(String joinTimeStart,String joinTimeEnd){
		StringBuilder sql = new StringBuilder(" select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where ");
		sql.append(" u.id = s.partybasicinfo_super_id and u.VISIBLE = '1' ");
		sql.append(" and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0) ");
		if(StringUtils.isNotBlank(joinTimeStart)){
			sql.append(" and s.join_party_time >= '").append(joinTimeStart).append("' ");
		}
		if(StringUtils.isNotBlank(joinTimeEnd)){
			sql.append(" and s.join_party_time < '").append(joinTimeEnd).append("' ");
		}
		int count = this.jdbcTemplate.queryForObject(sql.toString(),Integer.class);
		return  count;
	}

	/**
	 * 根据统计实体获取页面所需html代码
	 * @param teamSituationTotal
	 * @return
	 */
	private Object getJsonByTeamSituationTotal(TeamSituationTotal teamSituationTotal) {

		String teamSituationTotalHtml = "一、党员队伍情况</br>党员总数<u>" + teamSituationTotal.getPartyMember() +"</u>名。</br>";
				teamSituationTotalHtml += "党员的性别、民族和学历：女党员<u>" + teamSituationTotal.getFemalePartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getFemalePartyMemberBl() + "</u>；少数民族党员<u>" + teamSituationTotal.getMinNationPartyMember()+ "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getMinNationPartyMemberBl() + "</u>；大专及以上学历党员<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getCollegeAndAbovePartyMemberBl() + "</u>。</br>党员的年龄：35  岁及以下的党员<u>" + teamSituationTotal.getThirtyFiveUnderPartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getThirtyFiveUnderPartyMemberBl() + "</u>；36  岁至45  岁的党员<u>" + teamSituationTotal.getThirtySixAbovePartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getThirtySixAbovePartyMemberBl() + "</u>；46  岁至60  岁的党员<u>" + teamSituationTotal.getFortySixAbovePartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getFortySixAbovePartyMemberBl() + "</u>；61  岁及以上的党员<u>" + teamSituationTotal.getSixtyOneAbovePartyMember() + "</u>名，占党员总数的<u>";
				teamSituationTotalHtml += teamSituationTotal.getSixtyOneAbovePartyMemberBl() + "</u>。";
				teamSituationTotalHtml += "</br>党员的入党时间：建国前入党的党员<u>" + teamSituationTotal.getBeforEstablishmentPartyMember() + "</u>名，建国后至“文革”前入党的<u>" + teamSituationTotal.getAfterEstablishmentPartyMember();
				teamSituationTotalHtml += "</u>名，“文革”期间入党的<u>" + teamSituationTotal.getGreatRevolutionPartyMember() + "</u>名，粉碎“四人帮”至党的十一届三中全会前入党的<u>" + teamSituationTotal.getGangFourpartyMember();
				teamSituationTotalHtml += "</u>名，党的十一届三中全会后至党的十六大前入党的<u>" + teamSituationTotal.getEighteenthMajorPartyMember() + "</u>名，党的十六大后至党的十八大前入党的<u>" + teamSituationTotal.getSixteenMajorPartyMember();
				teamSituationTotalHtml += "</u>名，党的十八大以来入党的<u>" + teamSituationTotal.getEighteenthMajorPartyMember() + "</u>名。";
				teamSituationTotalHtml += "</br>党员的职业：工人<u>" + teamSituationTotal.getWorkerPartyMember() + "</u>名，企事业单位、民办非企业单位管理人员<u>" + teamSituationTotal.getAdminPartyMember();
				teamSituationTotalHtml += "</u>名，党政机关工作人员<u>" + teamSituationTotal.getOfficePartyMember() + "</u>名，离退休人员<u>" + teamSituationTotal.getRetirementPartyMember() + "</u>名。</br></br>";
				teamSituationTotalHtml += "二、本年发展党员情况</br>发展党员总数<u>" + teamSituationTotal.getDmPartyMember() + "</u>名。</br>发展党员的性别、民族、年龄和学历：发展女党员<u>" + teamSituationTotal.getFemaleDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getFemaleDmPartyMemberBl() + "</u>；发展少数民族党员<u>" + teamSituationTotal.getMinNationDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getMinNationDmPartyMemberBl() + "</u>；发展35  岁及以下党员<u>" + teamSituationTotal.getThirtyFiveUnderDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getThirtyFiveUnderDmPartyMemberBl() + "</u>；发展具有大专及以上学历的党员<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMemberBl() + "</u>。</br>发展党员的职业：工人<u>" +  teamSituationTotal.getWorkerDmPartyMember();
				teamSituationTotalHtml += "</u>名，企事业单位、民办非企业单位管理人员<u>" + teamSituationTotal.getAdminDmPartyMember() + "</u>名，党政机关工作人员<u>" + teamSituationTotal.getOfficeDmPartyMember();
				teamSituationTotalHtml += "</u>名。</br>在生产、工作一线和新的社会阶层中发展党员：在生产、工作一线发展党员<u>" + teamSituationTotal.getWorkerFrontlineDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getWorkerFrontlineDmPartyMemberBl() + "</u>。</br>发展的党员中，各行各业的先进模范人物<u>" + teamSituationTotal.getAdvancedDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getAdvancedDmPartyMemberBl() + "</u>；共青团员<u>" + teamSituationTotal.getLeagueDmPartyMember();
				teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + teamSituationTotal.getLeagueDmPartyMemberBl() + "</u>。</br></br>";
				teamSituationTotalHtml += "三、党员表彰情况</br>";
				teamSituationTotalHtml += "本年受表彰的优秀共产党员<u>" + teamSituationTotal.getFinePartyMember() + "</u>名，受表彰的优秀党务工作者<u>" + teamSituationTotal.getFineWorker() + "</u>名。";

		return teamSituationTotalHtml;
	}
	/**
	 * 党员简要信息查询
	 * TODO
	 * @author 李帅
	 * @Date 2018年10月30日
	 * @param
	 * @return
	 */
	public  Object applicantStatistics(String annual,String ids){
		ids = DzzUtil.spiltString(ids);
		String sql ="SELECT\n" +
				"distinct(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)) AS xx,\n" +
				"(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)) AS dyzs,--获取党员总数\n" +
				"(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND SEX='女'AND (INSTR (type_of_personnel, '06') <= 0)) AS ndyzs,--获取女党员人数\n" +
				"		ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				"	((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND SEX='女'AND (INSTR (type_of_personnel, '06') <= 0))\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as  ndyzsfb,--获取女党员党员人数比例\n" +
				"(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND NATION!='01'AND (INSTR (type_of_personnel, '06') <= 0)) AS ssmzdyzs,--获取少数民族数\n" +
				"	ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				"	((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND NATION!='01'AND (INSTR (type_of_personnel, '06') <= 0))\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   ssmzdyzsfb,--获取少数民族数比例\n" +
				"(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%')AND (INSTR (type_of_personnel, '06') <= 0)) AS dzxldyzs,--获取大专以上党员数\n" +
				"ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				"	((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%')AND (INSTR (type_of_personnel, '06') <= 0))\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   dzxldyzsfb, --获取大专以上数比例\n" +
				"  (select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age <= '35')  AS sswdyzs，--35岁以下\n" +
				"ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				" ((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age <= '35')\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   sswdyzsfb, --获取35以下数比例\n" +
				"   (select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where   a.age >= '36'AND a.age <= '45')  AS ssldyzs，--36岁以上，45以下\n" +
				"ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				" ((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age >= '36'AND a.age <= '45')\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   ssldyzsfb ,--36岁以上，45以下数比例\n" +
				"    (select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age >= '46'AND a.age <= '60')  AS sldyzs，--46岁以上，60以下\n" +
				"ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				" ((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where   a.age >= '46'AND a.age <= '60')\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   sldyzsfb， --46岁以上，60以下数比例\n" +
				"    (select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age >= '61')  AS lsdyzs，--60岁以上\n" +
				"ROUND(decode((	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)),0,0,\n" +
				" ((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and	 VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) )a where  a.age >= '61')\n" +
				"  /(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0)))),2) as   lsdyzsfb, --获取60岁以上数比例\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and	 u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and s.join_party_time < '1949-10-01') AS jgqdyzs,--建国前入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and s.join_party_time >= '1949-10-01' and s.join_party_time < '1966-05-01' )AS wgqdyzs,--获取建国后至“文革”前入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and  s.join_party_time >= '1966-05-01'  and s.join_party_time < '1976-10-01'  )AS wgqjdyzs,--获取“文革”期间 入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and  u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and  s.join_party_time >= '1976-10-01'  and s.join_party_time < '1978-12-18'  )AS srbqjdyzs,--获取粉碎“四人帮”至党的十一届三中全会前入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and  s.join_party_time >= '1978-12-18'  and s.join_party_time < '2002-11-08')AS syjszqhh,--获取党的十一届三中全会后 至党的十六大前入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and  s.join_party_time >= '2002-11-08'  and s.join_party_time < '2012-11-08')AS sldh,--获取党的十六大后至党的十八大前入党的党员数\n" +
				"( select count(*) from ddjs_dygl_userbasicinfo u,DDJS_DYGL_PARTYBASICINFO s where  u.id = s.partybasicinfo_super_id and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(u.type_of_personnel,'05')>0 or INSTR(u.type_of_personnel,'0')>0)AND (INSTR (u.type_of_personnel, '06') <= 0)  and s.join_party_time >= '2012-11-08' )AS sbdyl,--获取党的十八大以来入党的党员数\n" +
				"((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' ))AS fzdyzs,--发展党员总数\n" +
				" ((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND u.SEX='女' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%'AND SEX='女' ))AS nfzdyzs,--女发展党员总数\n" +
				"ROUND(decode(((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' )),0,0,\n" +
				" ((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND u.SEX='女' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%'AND SEX='女' ))\n" +
				"  /((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' ))),2) as  nfzdyzsfb,  --女发展党员数比例\n" +
				" ((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND u.NATION!='01' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%'AND NATION!='01' ))AS ssmzfzdyzs,--发展少数民族党员数\n" +
				"ROUND(decode(((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' )),0,0,\n" +
				" ((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND u.NATION!='01' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%'AND NATION!='01' ))\n" +
				"  /((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' ))),2) as  ssmzfzdyzsfb,  --发展少数民族党员数比例\n" +
				"((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND (u.EDUCATION LIKE '%05%' OR u.EDUCATION LIKE '%06%' OR u.EDUCATION LIKE '%07%') )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%') ))AS dzfzdyzs,--发展获取大专以上党员数\n" +
				"ROUND(decode(((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' )),0,0,\n" +
				" ((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%'AND (u.EDUCATION LIKE '%05%' OR u.EDUCATION LIKE '%06%' OR u.EDUCATION LIKE '%07%') )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%'AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%')))\n" +
				"  /((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' ))),2) as  dzfzdyzsfb,  --发展获取大专以上党员数比例\n" +
				" \n" +
				" ((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u ,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )a where  a.age <= '35')+\n" +
				" (  select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u  where u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'	AND (	u.type_of_personnel LIKE '%04%'	OR u.type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND u.CRE_TIME Like '%2018%' )a where  a.age <= '35'))AS sswfzdyzs,--发展获取35岁以下党员数\n" +
				"ROUND(decode(((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' )),0,0,\n" +
				"((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u ,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )a where  a.age <= '35')+\n" +
				" (  select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u  where u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'	AND (	u.type_of_personnel LIKE '%04%'	OR u.type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND u.CRE_TIME Like '%2018%' )a where  a.age <= '35'))\n" +
				"  /((select count(*) from ddjs_dygl_userbasicinfo u,DDJS_SQRGL_PARTYCONSIDERATION s where  u.id = s.SUPER_ID and u.PARTY_ORGANIZATION_ID IN("+ids+") and u.VISIBLE = '1'  and (INSTR(type_of_personnel,'05')>0 or INSTR(type_of_personnel,'04')>0)  and s.ACTUAL_DEVELOPMENT_TIME LIKE '%2018%' )+\n" +
				" (	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and		VISIBLE = '1'	AND (	type_of_personnel LIKE '%04%'	OR type_of_personnel LIKE '%05%'	)AND (INSTR (type_of_personnel, '06') <= 0) AND (INSTR (type_of_personnel, '03') <= 0)AND CRE_TIME Like '%2018%' ))),2) as  sswfzdyzsfb  --发展获取35岁以下党员数比例\n" +
				"FROM\n" +
				"  ddjs_dygl_userbasicinfo";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		BigDecimal partyMember = BigDecimal.valueOf(0);
		BigDecimal femalePartyMember = BigDecimal.valueOf(0);
		BigDecimal femalePartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal minNationPartyMember = BigDecimal.valueOf(0);
		BigDecimal minNationPartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal collegeAndAboveDmPartyMember = BigDecimal.valueOf(0);
		BigDecimal collegeAndAbovePartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal thirtyFiveUnderPartyMember = BigDecimal.valueOf(0);
		BigDecimal thirtyFiveUnderPartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal thirtySixAbovePartyMember = BigDecimal.valueOf(0);
		BigDecimal thirtySixAbovePartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal fortySixAbovePartyMember = BigDecimal.valueOf(0);
		BigDecimal fortySixAbovePartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal sixtyOneAbovePartyMember = BigDecimal.valueOf(0);
		BigDecimal sixtyOneAbovePartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal beforEstablishmentPartyMember = BigDecimal.valueOf(0);
		BigDecimal afterEstablishmentPartyMember = BigDecimal.valueOf(0);
		BigDecimal greatRevolutionPartyMember = BigDecimal.valueOf(0);
		BigDecimal gangFourpartyMember = BigDecimal.valueOf(0);
		BigDecimal eighteenthMajorPartyMember = BigDecimal.valueOf(0);
		BigDecimal sixteenMajorPartyMember = BigDecimal.valueOf(0);
		BigDecimal eighteenthMajorMember = BigDecimal.valueOf(0);

		BigDecimal dmPartyMember = BigDecimal.valueOf(0);
		BigDecimal femaleDmPartyMember = BigDecimal.valueOf(0);
		BigDecimal femaleDmPartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal minNationDmPartyMember = BigDecimal.valueOf(0);
		BigDecimal minNationDmPartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal thirtyFiveUnderDmPartyMember = BigDecimal.valueOf(0);
		BigDecimal thirtyFiveUnderDmPartyMemberBl = BigDecimal.valueOf(0);

		BigDecimal collegeAndAbovePartyMember = BigDecimal.valueOf(0);
		BigDecimal collegeAndAboveDmPartyMemberBl = BigDecimal.valueOf(0);
		BigDecimal sswfzdxBl = BigDecimal.valueOf(0);
		BigDecimal dzfzdxVal = BigDecimal.valueOf(0);
		BigDecimal dzfzdxBl = BigDecimal.valueOf(0);
		if(list.size()>0){
			partyMember = list.get(0).get("dyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dyzs");//获取党员总数
			femalePartyMember = list.get(0).get("ndyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ndyzs");//获取女党员数
			femalePartyMemberBl = list.get(0).get("ndyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ndyzsfb");//获取女党员党员人数比例
			minNationPartyMember = list.get(0).get("ssmzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzdyzs");//获取少数民族数
			minNationPartyMemberBl = list.get(0).get("ssmzdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzdyzsfb");//获取少数民族数比例
			collegeAndAboveDmPartyMember = list.get(0).get("dzxldyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxldyzs");//获取大专以上党员数
			collegeAndAbovePartyMemberBl = list.get(0).get("dzxldyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxldyzsfb");//获取大专以上数比例
			thirtyFiveUnderPartyMember = list.get(0).get("sswdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswdyzs");//35岁以下
			thirtyFiveUnderPartyMemberBl = list.get(0).get("sswdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswdyzsfb");//获取35以下数比例
			thirtySixAbovePartyMember = list.get(0).get("ssldyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssldyzs");//--36岁以上，45以下
			thirtySixAbovePartyMemberBl = list.get(0).get("ssldyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssldyzsfb");//--36岁以上，45以下数比例
			fortySixAbovePartyMember = list.get(0).get("sldyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sldyzs");//--46岁以上，60以下
			fortySixAbovePartyMemberBl = list.get(0).get("sldyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sldyzsfb");//--46岁以上，60以下数比例
			sixtyOneAbovePartyMember = list.get(0).get("lsdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("lsdyzs");//--60岁以上
			sixtyOneAbovePartyMemberBl = list.get(0).get("lsdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("lsdyzsfb");//--获取60岁以上数比例
			beforEstablishmentPartyMember = list.get(0).get("jgqdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("jgqdyzs");//--建国前入党的党员数
			afterEstablishmentPartyMember = list.get(0).get("wgqdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("wgqdyzs");//--获取建国后至“文革”前入党的党员数
			greatRevolutionPartyMember = list.get(0).get("wgqjdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("wgqjdyzs");//--获取“文革”期间 入党的党员数
			gangFourpartyMember = list.get(0).get("srbqjdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("srbqjdyzs");//--获取粉碎“四人帮”至党的十一届三中全会前入党的党员数
			eighteenthMajorPartyMember = list.get(0).get("syjszqhh")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("syjszqhh");//--获取党的十一届三中全会后 至党的十六大前入党的党员数
			sixteenMajorPartyMember = list.get(0).get("sldh")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sldh");//--获取党的十六大后至党的十八大前入党的党员数
			eighteenthMajorMember = list.get(0).get("sbdyl")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sbdyl");//--获取党的十八大以来入党的党员数
			dmPartyMember = list.get(0).get("fzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("fzdyzs");//--发展党员总数
			femaleDmPartyMember = list.get(0).get("nfzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("nfzdyzs");//--女发展党员总数
			femaleDmPartyMemberBl = list.get(0).get("nfzdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("nfzdyzsfb");// --女发展党员数比例


			minNationDmPartyMember = list.get(0).get("ssmzfzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzfzdyzs");//--发展少数民族党员数
			minNationDmPartyMemberBl = list.get(0).get("ssmzfzdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzfzdyzsfb");//--发展少数民族党员数比例
			thirtyFiveUnderDmPartyMember = list.get(0).get("sswfzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswfzdyzs");//--发展获取35岁以下党员数
			thirtyFiveUnderDmPartyMemberBl = list.get(0).get("sswfzdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswfzdyzsfb");//--发展获取35岁以下党员数比例
			collegeAndAbovePartyMember = list.get(0).get("dzfzdyzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzfzdyzs");//--发展获取大专以上党员数
			collegeAndAboveDmPartyMemberBl = list.get(0).get("dzfzdyzsfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzfzdyzsfb");//  --发展获取大专以上党员数比例
			sswfzdxBl = list.get(0).get("sswsyxfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswsyxfzdxbfb");
			dzfzdxVal = list.get(0).get("dzxlysfzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlysfzdx");
			dzfzdxBl = list.get(0).get("dzxlysfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlysfzdxbfb");

		}
		String teamSituationTotalHtml = "一、党员队伍情况</br>党员总数<u>" + partyMember +"</u>名。</br>";
		teamSituationTotalHtml += "党员的性别、民族和学历：女党员<u>" + femalePartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += femalePartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；少数民族党员<u>" + minNationPartyMember+ "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += minNationPartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；大专及以上学历党员<u>" + collegeAndAboveDmPartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += collegeAndAbovePartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>。</br>党员的年龄：35  岁及以下的党员<u>" + thirtyFiveUnderPartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += thirtyFiveUnderPartyMemberBl.multiply(BigDecimal.valueOf(100))+ "%</u>；36  岁至45  岁的党员<u>" + thirtySixAbovePartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += thirtySixAbovePartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；46  岁至60  岁的党员<u>" + fortySixAbovePartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += fortySixAbovePartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；61  岁及以上的党员<u>" + sixtyOneAbovePartyMember + "</u>名，占党员总数的<u>";
		teamSituationTotalHtml += sixtyOneAbovePartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>。";
		teamSituationTotalHtml += "</br>党员的入党时间：建国前入党的党员<u>" + beforEstablishmentPartyMember + "</u>名，建国后至“文革”前入党的<u>" + afterEstablishmentPartyMember;
		teamSituationTotalHtml += "</u>名，“文革”期间入党的<u>" + greatRevolutionPartyMember + "</u>名，粉碎“四人帮”至党的十一届三中全会前入党的<u>" + gangFourpartyMember;
		teamSituationTotalHtml += "</u>名，党的十一届三中全会后至党的十六大前入党的<u>" + eighteenthMajorPartyMember + "</u>名，党的十六大后至党的十八大前入党的<u>" + sixteenMajorPartyMember;
		teamSituationTotalHtml += "</u>名，党的十八大以来入党的<u>" + eighteenthMajorMember + "</u>名。";
		teamSituationTotalHtml += "</br>党员的职业：工人<u>" + "0" + "</u>名，企事业单位、民办非企业单位管理人员<u>" +  "0" ;
		teamSituationTotalHtml += "</u>名，党政机关工作人员<u>" +  "0"  + "</u>名，离退休人员<u>" +  "0"  + "</u>名。</br></br>";
		teamSituationTotalHtml += "二、本年发展党员情况</br>发展党员总数<u>" + dmPartyMember + "</u>名。</br>发展党员的性别、民族、年龄和学历：发展女党员<u>" + femaleDmPartyMember;
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + femaleDmPartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；发展少数民族党员<u>" + minNationDmPartyMember;
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + minNationDmPartyMemberBl.multiply(BigDecimal.valueOf(100))+ "%</u>；发展35  岁及以下党员<u>" + thirtyFiveUnderDmPartyMember;
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + thirtyFiveUnderDmPartyMemberBl.multiply(BigDecimal.valueOf(100)) + "%</u>；发展具有大专及以上学历的党员<u>" + collegeAndAbovePartyMember;
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + collegeAndAboveDmPartyMemberBl.multiply(BigDecimal.valueOf(100))+ "%</u>。</br>发展党员的职业：工人<u>" +  "0";
		teamSituationTotalHtml += "</u>名，企事业单位、民办非企业单位管理人员<u>" + "0" + "</u>名，党政机关工作人员<u>" + "0";
		teamSituationTotalHtml += "</u>名。</br>在生产、工作一线和新的社会阶层中发展党员：在生产、工作一线发展党员<u>" + "0";
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + "0" + "%</u>。</br>发展的党员中，各行各业的先进模范人物<u>" + "0";
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + "0"+ "%</u>；共青团员<u>" + "0";
		teamSituationTotalHtml += "</u>名，占发展党员总数的<u>" + "0" + "%</u>。</br></br>";
		teamSituationTotalHtml += "三、党员表彰情况</br>";
		teamSituationTotalHtml += "本年受表彰的优秀共产党员<u>" + "0" + "</u>名，受表彰的优秀党务工作者<u>" + "0" + "</u>名。";

		return teamSituationTotalHtml;


	}

	@Override
	public List<DdjsDyglUserbasicinfoEntity> findByUserIdAndVisible(String userId, String visible) {
		return dyxxDao.findByUserIdAndVisible(userId,visible);
	}
	/**
	 * 根据id逻辑删除历史党员信息 对应列表
	 * @author 李帅
	 * @Date 2018年11月8日
	 * @return
	 */
	@Override
	public int deleteHistory(DdjsDyglHistoryEntity entity) {
		int n = 0;
		if(StringUtils.isNotBlank(entity.getId())){
			try {
				//逻辑删除试题
				String delQuestion = "update DdjsDyglHistoryEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+entity.getId()+"'";
				n = historyDao.update(delQuestion);
			} catch (Exception e) {
				log.info(e.getMessage());
				log.info("删除历史党员出现异常！");
				n=0;
			}
		}
		return n;
	}

	/**
	 * 保存人员基本信息
	 * @author 李帅
	 * @Date 2018年11月8日
	 * @return
	 */
	public DdjsDyglUserbasicinfoEntity saveUserForm(DdjsDyglUserbasicinfoEntity entity) throws IOException {
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
			entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			entity.setIsHistoryparty("0");
			entity.setTypeOfPersonnel("05");
			entity.setPartyOrganizationName(java.net.URLDecoder.decode(entity.getPartyOrganizationName(), "UTF-8"));
			entity.setSuperId(entity.getSuperId());
			entity = dyxxDao.save(entity);
		} else {
			DdjsDyglUserbasicinfoEntity oldEntity = dyxxDao.findOne(entity.getId());
			oldEntity.setBirthplace(entity.getBirthplace());
			oldEntity.setContactNumber(entity.getContactNumber());
			oldEntity.setDateOfBirth(entity.getDateOfBirth());
			oldEntity.setRyGegree(entity.getRyGegree());
			oldEntity.setDemocraticParties(entity.getDemocraticParties());
			oldEntity.setWorkingUnit(entity.getWorkingUnit());
			oldEntity.setWorkingTime(entity.getWorkingTime());
			oldEntity.setTechnicalPost(entity.getTechnicalPost());
			oldEntity.setSex(entity.getSex());
			oldEntity.setRegisteredResidence(entity.getRegisteredResidence());
			oldEntity.setPinCodes(entity.getPinCodes());
			oldEntity.setPartyOrganizationName(entity.getPartyOrganizationName());
			oldEntity.setPartyOrganizationId(entity.getPartyOrganizationId());
			oldEntity.setNewStratum(entity.getNewStratum());
			oldEntity.setNativePlace(entity.getNativePlace());
			oldEntity.setNation(entity.getNation());
			oldEntity.setName(entity.getName());
			oldEntity.setMaritalStatus(entity.getMaritalStatus());
			oldEntity.setRyMajor(entity.getRyMajor());
			oldEntity.setLocalPoliceStation(entity.getLocalPoliceStation());
			oldEntity.setJobLevel(entity.getJobLevel());
			oldEntity.setIsProbationary(entity.getIsProbationary());
			oldEntity.setIsPartyRepresentative(entity.getIsPartyRepresentative());
			oldEntity.setIsHistoryparty(entity.getIsHistoryparty());
			oldEntity.setRyIdentity(entity.getRyIdentity());
			oldEntity.setHomeAddress(entity.getHomeAddress());
			oldEntity.setHistoryTime(entity.getHistoryTime());
			oldEntity.setHistoryPartyname(entity.getHistoryPartyname());
			oldEntity.setHistoryPartyid(entity.getHistoryPartyid());
			oldEntity.setHealthStatus(entity.getHealthStatus());
			oldEntity.setRyGraduationAcademy(entity.getRyGraduationAcademy());
			oldEntity.setGoPartyname(entity.getGoPartyname());
			oldEntity.setGoPartyid(entity.getGoPartyid());
			oldEntity.setFrontlineSituation(entity.getFrontlineSituation());
			oldEntity.setFamilyOrigin(entity.getFamilyOrigin());
			oldEntity.setRyEducation(entity.getRyEducation());
			oldEntity.setIsHistoryparty("0");
			oldEntity.setUserId(entity.getUserId());
			oldEntity.setSuperId(entity.getSuperId());
			entity = dyxxDao.saveAndFlush(oldEntity);
			}
			return entity;
		}

	/**
	 * 保存党员基本情况
	 * @author 李帅
	 * @Date 2018年11月8日
	 * @return
	 */
	public DdjsDyglPartybasicinfoEntity savePartyForm(DdjsDyglPartybasicinfoEntity entity) throws IOException {
		String id = entity.getPartybasicinfoId();
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
			entity = partyBasicInfoDao.save(entity);
		} else {
			DdjsDyglPartybasicinfoEntity oldEntity = partyBasicInfoDao.findOne(entity.getPartybasicinfoId());
			oldEntity.setPartybasicinfoSuperId(entity.getPartybasicinfoSuperId());
			oldEntity.setLocationpartygroup(entity.getLocationpartygroup());
			oldEntity.setOrderNumber(entity.getOrderNumber());
			oldEntity.setJoinPartyTime(entity.getJoinPartyTime());
			oldEntity.setJoinPartyIntroducer(entity.getJoinPartyIntroducer());
			oldEntity.setIntroducerTime(entity.getIntroducerTime());
			oldEntity.setIntroducerSituation(entity.getIntroducerSituation());
			oldEntity.setDevelopmentType(entity.getDevelopmentType());
			oldEntity.setPartyPaymentBase(entity.getPartyPaymentBase());
			oldEntity.setDyRemarks(entity.getDyRemarks());
			entity = partyBasicInfoDao.saveAndFlush(oldEntity);
		}
		return entity;
	}
	/**
	 * 保存党员增加情况
	 * @author 李帅
	 * @Date 2018年11月8日
	 * @return
	 */
	public DdjsDyglIncreaseEntity saveIncreaseForm(DdjsDyglIncreaseEntity entity) throws IOException {
		String id = entity.getIncreaseId();
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
			entity = increseDao.save(entity);
		} else {
			DdjsDyglIncreaseEntity oldEntity = increseDao.findOne(entity.getIncreaseId());
			oldEntity.setIncreaseSuperId( entity.getIncreaseSuperId());
			oldEntity.setIncreaseTime(  entity.getIncreaseTime());
			oldEntity.setIncreasePartymember(entity.getIncreasePartymember());
			oldEntity.setTurnToParty(entity.getTurnToParty());
			oldEntity.setIncreaseReason(entity.getIncreaseReason());
			oldEntity.setOriginalIndividualStatus(entity.getOriginalIndividualStatus());
			oldEntity.setIncreaseRemarks( entity.getIncreaseRemarks());
			entity = increseDao.saveAndFlush(oldEntity);
		}
		return entity;
	}
}