package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqglAttendenceHandleDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;

/**
 * 
 * TODO 考勤数据视图ServiceImpl
 * @author 冯超
 * @Date 2018年6月1日 上午9:33:06
 */
@Service
@Transactional
public class KqglAttendenceHandleServiceImpl implements KqglAttendenceHandleService{
	
	@Autowired
	private KqglAttendenceHandleDao kqglAttendenceHandleDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年6月1日 上午11:33:47
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param cardNumber
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String timeRange, String cardNumber) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append("	from KqglAttendenceHandle t ");
		querySql.append("	where  1=1 ");
		
		if (StringUtils.isNotBlank(cardNumber)) {
			querySql.append("   and t.cardNumber = ?");
			para.add(cardNumber);
		}else{
			querySql.append("   and t.cardNumber = '' ");
		}
		
		if (StringUtils.isNotBlank(timeRange)) {
			querySql.append("   and t.importMonth = ?");
			para.add(timeRange);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.importDate desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqglAttendenceHandle> page = kqglAttendenceHandleDao.query(querySql.toString(), pageable, para.toArray());
		List<KqglAttendenceHandle> content = page.getContent();
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int) page.getTotalElements());
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 获取数据
	 * @author 冯超
	 * @Date 2018年6月1日 下午12:50:09
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public KqglAttendenceHandle getById(String id) throws Exception {
		return kqglAttendenceHandleDao.findOne(id);
	}
	
	/**
	 * 
	 * TODO 根据日期和卡号获取数据
	 * @author 冯超
	 * @Date 2018年6月1日 下午2:01:05
	 * @param importDate
	 * @param cardNumber
	 * @return
	 */
	@Override
	public KqglAttendenceHandle getKqglAttendenceHandle(String importDate,String cardNumber) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from kqgl_attendence_handle t");
		querySql.append("	where 1=1");
		//卡号
		if (StringUtils.isNotBlank(cardNumber)) {
			querySql.append("   and t.card_number = '" + cardNumber + "'");
		}
		//日期
		if (StringUtils.isNotBlank(importDate)) {
			querySql.append("   and t.import_date = '" + importDate + "'");
		}
		List<KqglAttendenceHandle> list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class));
		KqglAttendenceHandle kqglAttendenceHandle = new KqglAttendenceHandle();
		if(list.size()>0){
			kqglAttendenceHandle = list.get(0);
		}
		return kqglAttendenceHandle;
	}
	
	/**
	 * 根据考勤卡号查询某段时间内的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月24日 下午5:38:16
	 * @param startDate
	 * @param endDate
	 * @param cardNum
	 * @return
	 */
	@Override
	public List<KqglAttendenceHandle> findByCondition(String startDate, String endDate, String cardNum) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from kqgl_attendence_handle t");
		querySql.append("	where t.card_number in (" + cardNum + ") ");
		//卡号
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.import_date >= '" + startDate + "' and t.import_date <= '" + endDate +"' ");
		}
		List<KqglAttendenceHandle> list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class));
		return list;
	}
	
	/**
	 * 查询某段时间某些卡号的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午11:26:26
	 * @param startDate
	 * @param endDate
	 * @param cardNum
	 * @return
	 */
	@Override
	public List<KqglAttendenceHandle> getKqInfo(String startDate, String endDate, String cardNum) {
		StringBuilder kqSql = new StringBuilder("select t.* from kqgl_attendence_handle t where t.card_number in (" + cardNum + ")");
		if(StringUtils.isNotBlank(startDate)){
			kqSql.append(" and t.import_date >= '" + startDate + "'");
		}
		if(StringUtils.isNotBlank(endDate)){
			kqSql.append("  and  t.import_date <= '" + endDate + "'");
		}
		List<KqglAttendenceHandle> kqData = jdbcTemplate.query(kqSql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class));
		return kqData;
	}
	

}
