package com.sinosoft.sinoep.modules.system.config.honor.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.system.config.honor.dao.HonorDetailsDao;
import com.sinosoft.sinoep.modules.system.config.honor.entity.HonorDetails;
import com.sinosoft.sinoep.modules.system.config.honor.util.HonorUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.honor.dao.HonorDao;
import com.sinosoft.sinoep.modules.system.config.honor.entity.Honor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

@Service
public class HonorServiceImp implements HonorService{

	@Autowired
	HonorDao honorDao;

	@Autowired
	HonorDetailsDao honorDetailsDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public PageImpl getPageList(PageImpl pageImpl, Honor honor){
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		StringBuilder sb = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sb.append("from Honor t where t.visible='1' ");
		if(StringUtils.isNotBlank(honor.getYear())){
			sb.append(" and t.year = ? ");
			para.add(honor.getYear());
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sb.append(" order by t.year desc ");
		} else {
			sb.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<Honor> page = honorDao.query(sb.toString(), pageable,para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public Honor findByVisibleAndYear(String year) {
		return honorDao.findByVisibleAndYear("1",year);
	}


	@Override
	public Honor saveHonor(String year) {
		Honor honor = new Honor();
		honor.setCreUserId(UserUtil.getCruUserId());
		honor.setCreUserName(UserUtil.getCruUserName());
		honor.setCreDeptId(UserUtil.getCruDeptId());
		honor.setCreDeptName(UserUtil.getCruDeptName());
		honor.setCreChushiId(UserUtil.getCruChushiId());
		honor.setCreChushiName(UserUtil.getCruChushiName());
		honor.setCreJuId(UserUtil.getCruJuId());
		honor.setCreJuName(UserUtil.getCruJuName());
		honor.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		honor.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		honor.setUpdateUserId(UserUtil.getCruUserId());
		honor.setUpdateUserName(UserUtil.getCruUserName());
		honor.setVisible(CommonConstants.VISIBLE[1]);
		honor.setYear(year);
		honor.setIsPublish(CommonConstants.PUBLISH[0]);
		return honorDao.save(honor);
	}

	@Override
	public List<HonorDetails> findByHonor_Id(String honorId) {
		return honorDetailsDao.findByVisibleAndHonor_IdOrderByOrderNumberAsc(CommonConstants.VISIBLE[1],honorId);
	}

	@Override
	public HonorDetails editHonorDetails(HonorDetails honorDetails) {
		if(StringUtils.isNotBlank(honorDetails.getId())){
			HonorDetails old = honorDetailsDao.findOne(honorDetails.getId());
			honorDetails.setCreUserId(old.getCreUserId());
			honorDetails.setCreUserName(old.getCreUserName());
			honorDetails.setCreDeptId(old.getCreDeptId());
			honorDetails.setCreDeptName(old.getCreDeptName());
			honorDetails.setCreChushiId(old.getCreChushiId());
			honorDetails.setCreChushiName(old.getCreChushiName());
			honorDetails.setCreJuId(old.getCreJuId());
			honorDetails.setCreJuName(old.getCreJuName());
			honorDetails.setCreTime(old.getCreTime());
			honorDetails.setVisible(old.getVisible());
		}else{
			honorDetails.setHonor(honorDetails.getHonor());
			honorDetails.setCreUserId(UserUtil.getCruUserId());
			honorDetails.setCreUserName(UserUtil.getCruUserName());
			honorDetails.setCreDeptId(UserUtil.getCruDeptId());
			honorDetails.setCreDeptName(UserUtil.getCruDeptName());
			honorDetails.setCreChushiId(UserUtil.getCruChushiId());
			honorDetails.setCreChushiName(UserUtil.getCruChushiName());
			honorDetails.setCreJuId(UserUtil.getCruJuId());
			honorDetails.setCreJuName(UserUtil.getCruJuName());
			honorDetails.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			honorDetails.setVisible(CommonConstants.VISIBLE[1]);
			honorDetails.setOrderNumber(HonorUtil.getMaxNumber(honorDetails.getHonor().getId(),honorDetails.getFeatsType()));
		}
		honorDetails.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		honorDetails.setUpdateUserId(UserUtil.getCruUserId());
		honorDetails.setUpdateUserName(UserUtil.getCruUserName());
		return honorDetailsDao.save(honorDetails);
	}

	@Override
	public void changeNumber(String id, Integer number, String exchangeId, Integer exchangeNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update sys_honor_details set order_number = ? where id = ? ");
		StringBuffer sb1 = new StringBuffer();
		sb1.append(" update sys_honor_details set order_number = ? where id = ? ");
		jdbcTemplate.update(sb.toString(),exchangeNumber,id);
		jdbcTemplate.update(sb1.toString(),number,exchangeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Honor> getList(Honor honor) {
		StringBuffer sb = new StringBuffer();
		List<Object> para = new ArrayList<>();
		sb.append("from Honor t where t.visible='1' ");
		if(StringUtils.isNotBlank(honor.getYear())){
			sb.append(" and t.year = ? ");
			para.add(honor.getYear());
		}else{
			sb.append(" and isPublish='1' ");
		}
		sb.append(" order by t.year desc ");
		Query query = honorDao.getEntityManager().createQuery(sb.toString());
		for(int i=0;i<para.size();i++){
			query.setParameter(i+1, para.get(i));
		}
		List<Honor> list = new ArrayList<Honor>();
		list = (List<Honor>)query.getResultList();
		return  list;
	}


	/**
	 * 根据id删除一条记录
	 * TODO
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午1:32:23
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update Honor t set t.visible = ? where t.id = ?";
		String jpqlDetails = "update HonorDetails t set t.visible = ? where t.honor.id = ?";
		honorDetailsDao.update(jpqlDetails, CommonConstants.VISIBLE[0], id);
		return honorDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}

	@Override
	public int deleteDetails(String id) {
		String sb = "update HonorDetails t set t.visible = ? where t.id = ?";
		return honorDetailsDao.update(sb, CommonConstants.VISIBLE[0], id);
	}

	@Override
	public int updatePublish(String publish,String id) {
		String sb = "update Honor t set t.isPublish = ? where t.id = ?";
		return honorDetailsDao.update(sb, CommonConstants.PUBLISH[1], id);
	}


}
