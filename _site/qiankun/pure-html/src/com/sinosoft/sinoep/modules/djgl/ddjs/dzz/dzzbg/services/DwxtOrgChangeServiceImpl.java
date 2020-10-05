package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.services;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.dao.DwxtOrgChangeDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.entity.DwxtOrgChange;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class DwxtOrgChangeServiceImpl implements DwxtOrgChangeService {

	@Autowired
	private DwxtOrgChangeDao dwxtOrgChangeDao;

	@Autowired
	private DwxtOrgService dwxtOrgService;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public PageImpl getPageList(PageImpl pageImpl, DwxtOrgChange dwxtOrgChange, String timeRange) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		List<Object> para = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();
		querySql.append("from DwxtOrgChange t where 1=1 ");
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("  and t.changeDate >= ? and t.changeDate <= ? ");
			para.add(startDate);
			para.add(endDate);
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.updateTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<DwxtOrgChange> page = dwxtOrgChangeDao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public DwxtOrgChange save(DwxtOrgChange dwxtOrgChange,String code) {
		dwxtOrgChange.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dwxtOrgChange.setUpdateUserId(UserUtil.getCruUserId());
		dwxtOrgChange.setUpdateUserName(UserUtil.getCruUserName());
		//DwxtOrg dwxtOrg = dwxtOrgService.findOne(dwxtOrgChange.getTargetId());
		dwxtOrgChange.setChangeId(dwxtOrgChange.getOriginalId());
		dwxtOrgChange.setChangeName(dwxtOrgChange.getOriginalName());
		dwxtOrgService.changeDzzOrg(dwxtOrgChange.getOriginalId(),dwxtOrgChange.getTargetId(),code);
		return  dwxtOrgChangeDao.saveAndFlush(dwxtOrgChange);
	}

	@Override
	public DwxtOrgChange findOne(String id) {
		return dwxtOrgChangeDao.findOne(id);
	}

}
