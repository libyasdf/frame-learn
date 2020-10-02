package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqLogDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqLog;

/**
 * 
 * TODO 考勤日志ServiceImpl
 * @author 冯超
 * @Date 2018年5月30日 上午10:06:40
 */
@Service
@Transactional
public class KqLogServiceImpl implements KqLogService {

	@Autowired
	private KqLogDao kqLogDao;

	/**
	 * 
	 * TODO 获取日志列表
	 * @author 冯超
	 * @Date 2018年5月31日 上午9:28:15
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate)
			throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append("	from KqLog t ");
		querySql.append("	where t.visible = '" + CommonConstants.VISIBLE[1] + "'");

		// 拼接条件
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.creTime >= ?");
			para.add(startDate + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.creTime <= ?");
			para.add(endDate + "24:00:00");
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqLog> page = kqLogDao.query(querySql.toString(), pageable, para.toArray());
		List<KqLog> content = page.getContent();
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int) page.getTotalElements());
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 根据id获取数据
	 * @author 冯超
	 * @Date 2018年5月31日 上午10:07:02
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public KqLog getById(String id) throws Exception {
		return kqLogDao.findOne(id);
	}

}
