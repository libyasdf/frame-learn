package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.dao.XxkhLearnTimeDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime;
import com.sinosoft.sinoep.user.util.UserUtil;
@Service
public class XxkhLearnTimeServiceImpl implements XxkhLearnTimeService {

	@Autowired
	private XxkhLearnTimeDao dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * @Override public List<XxkhLearnTime> list(XxkhLearnTime learnTime) {
	 * return dao.findAll(new Specification<XxkhLearnTime>() {
	 * 
	 * @Override public Predicate toPredicate(Root<XxkhLearnTime> root,
	 * CriteriaQuery<?> query, CriteriaBuilder cb) { List<Predicate> predicates
	 * = new ArrayList<>(); return cb.and(predicates.toArray(new Predicate[0]));
	 * } });
	 * 
	 * }
	 */

	@Override
	public XxkhLearnTime saveLearningRecord(XxkhLearnTime learnTime) {
		// 设置常用字段开始
		learnTime.setCreJuId(UserUtil.getCruJuId());
		learnTime.setCreJuName(UserUtil.getCruJuName());
		learnTime.setCreChushiId(UserUtil.getCruChushiId());
		learnTime.setCreChushiName(UserUtil.getCruChushiName());
		learnTime.setCreDeptId(UserUtil.getCruDeptId());
		learnTime.setCreDeptName(UserUtil.getCruDeptName());
		learnTime.setCreUserId(UserUtil.getCruUserId());
		learnTime.setCreUserName(UserUtil.getCruUserName());
		learnTime.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		learnTime.setVisible(CommonConstants.VISIBLE[1]);
		// 设置常用字段结束
		learnTime.setLearnDate(DateUtil.COMMON.getDateText(new Date()));
		learnTime.setLearnTimeH();
		return dao.saveAndFlush(learnTime);
	}

	@Override
	public Page<XxkhLearnTime> learningTimeTablelist(final XxkhLearnTime dt, final PageImpl pageImpl) throws Exception {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		return dao.findAll(new Specification<XxkhLearnTime>() {
			@Override
			public Predicate toPredicate(Root<XxkhLearnTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				// Predicate pType = cb.equal(root.get("type").as(String.class),
				// type);
				Predicate pcreJuId = cb.equal(root.get("creJuId").as(String.class), dt.getCreJuId());
				// Predicate pSubflag =
				// cb.equal(root.get("type").as(String.class), type);
				predicates.add(pVisible);
				// predicates.add(pType);
				predicates.add(pcreJuId);
				Predicate nodeId = null, startTime = null, overTime = null;
				Order desc = null;
				// if (!StringUtils.isBlank(dt.getDataName())){
				// dataName = cb.like(root.get("dataName").as(String.class),
				// "%"+dt.getDataName()+"%");
				// predicates.add(dataName);
				// }
				if (!StringUtils.isBlank(dt.getInfoId())) {
					nodeId = cb.equal(root.get("infoId").as(String.class), dt.getInfoId());
					predicates.add(nodeId);
				}
				if (!StringUtils.isBlank(dt.getStartTime()) && !StringUtils.isBlank(dt.getOverTime())) {
					try {
						startTime = cb.greaterThanOrEqualTo(root.get("startTime").as(Date.class),
								sdf.parse(dt.getStartTime()));
						overTime = cb.lessThanOrEqualTo(root.get("overTime").as(Date.class),
								sdf.parse(dt.getOverTime()));
						predicates.add(startTime);
						predicates.add(overTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (pageImpl.getSortOrder().equals("desc")) {
					desc = cb.desc(root.get("creTime").as(String.class));
				}
				if (pageImpl.getSortOrder().equals("asc")) {
					desc = cb.asc(root.get("creTime").as(String.class));
				}
				query.where(cb.and(predicates.toArray(new Predicate[0])));
				query.orderBy(desc);
				return query.getRestriction();
			}
		}, pageable);
	}

	// add by hlt
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startTime, String endTime)
			throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" select sum(t.learnTimeH) as learnTimeH from XxkhLearnTime t ");
		querySql.append("	where t.creUserId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		// 起始时间
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			querySql.append("   and (t.startTime <= '" + startTime + "'  and t.overTime >='" + startTime + "'");
			querySql.append("   or t.startTime <= '" + endTime + "'  and t.overTime >='" + endTime + "'");
			querySql.append("	or t.startTime >='" + startTime + "'  and t.overTime<='" + endTime + "')");
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.learnDate desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<XxkhLearnTime> page = dao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public PageImpl getLearnTimeById(Pageable pageable, PageImpl pageImpl, String timeRange, String nodeIds) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(
				"	from XxkhLearnTime t where t.creUserId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		para.add(UserUtil.getCruUserId());
		// para.add(id);
		String startTime = "";
		String endTime = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim() + " 00:00:00";
			endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim() + " 23:59:59";
			querySql.append("	and (t.startTime <='" + endTime + "'  and t.overTime>='" + startTime + "')");
		}
		if (StringUtils.isNotBlank(nodeIds)) {
			querySql.append(" and t.infoId in (" + CommonUtils.commomSplit(nodeIds) + ")");
		}
		// 拼接排序语句 根据学习日期排序
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.startTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<XxkhLearnTime> page = dao.query(querySql.toString(), pageable, para.toArray());

		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public List<XxkhLearnTime> getLearnTotalTime(String startTime, String endTime, String nodeIds) {
		StringBuilder querySql = new StringBuilder();
		StringBuilder querySql1 = new StringBuilder();
		String cruUserId = UserUtil.getCruUserId();
		List<XxkhLearnTime> list1 = new ArrayList<XxkhLearnTime>();
		if (StringUtils.isNotBlank(nodeIds)) {
			querySql1.append("select info_id as infoId from xxkh_learn_time t where" + " t.cre_user_id='" + cruUserId
					+ "' and t.visible='" + CommonConstants.VISIBLE[1] + "' and t.info_id in ("
					+ CommonUtils.commomSplit(nodeIds) + ") order by t.learn_date desc");
			list1 = jdbcTemplate.query(querySql1.toString(), new BeanPropertyRowMapper<>(XxkhLearnTime.class));
		}
		if (list1 != null && list1.size() > 0) {
			// 先查询有没有对应的资料 如果有 在进行统计学习时长
			querySql.append("select nvl(sum(t.learn_time_h),0) as learnTimeH from xxkh_learn_time t where"
					+ " t.cre_user_id='" + cruUserId + "' and t.visible='" + CommonConstants.VISIBLE[1] + "'");
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				querySql.append("	and (t.start_time <='" + endTime + "'  and t.over_time>='" + startTime + "')");
			}

			if (StringUtils.isNotBlank(nodeIds)) {
				querySql.append(" and t.info_id in (" + CommonUtils.commomSplit(nodeIds) + ")");
			}
			querySql.append(" order by t.start_time desc");
			List<XxkhLearnTime> list = jdbcTemplate.query(querySql.toString(),
					new BeanPropertyRowMapper<>(XxkhLearnTime.class));
			return list;
		} else {
			return list1;
		}
	}

	@Override
	public PageImpl getLearnDeptTotalTime(PageImpl pageImpl,Pageable pageable,String deptIds, String chushiIds, String positions,
			String startTime, String endTime, String nodeIds) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select t.cre_ju_name creJuName," + "t.cre_chushi_name creChushiName,"
				+ "t.cre_user_id creUserId,t.cre_user_name creUserName,"
				+ "(case when sum(t.learn_time_h) is null  then 0.0 else sum(t.learn_time_h) end) as learnTimeH "
				+ "from xxkh_learn_time t where" + " t.visible='" + CommonConstants.VISIBLE[1] + "'");
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			querySql.append("	and (t.start_time <='" + endTime + "'  and t.over_time>='" + startTime + "')");
		}
		if (StringUtils.isNotBlank(deptIds)) {
			querySql.append(" and t.cre_ju_id in (" + CommonUtils.commomSplit(deptIds) + ")");
		}
		if (StringUtils.isNotBlank(chushiIds)) {
			querySql.append(" and t.cre_chushi_id in (" + CommonUtils.commomSplit(chushiIds) + ")");
		}
 
		if (StringUtils.isNotBlank(nodeIds)) {
			querySql.append(" and t.info_id in (" + CommonUtils.commomSplit(nodeIds) + ")");
		}
		querySql.append(" group by t.cre_ju_name,t.cre_chushi_name,t.cre_user_name,t.cre_user_id");
		querySql.append(" order by learnTimeH desc");
		
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		String resSql="select * from (select n.*,rownum RN from ("+querySql+") n) where RN between "+pre+" and "+after+"";
		
		List<XxkhLearnTime> list1 = jdbcTemplate.query(querySql.toString(),new BeanPropertyRowMapper<>(XxkhLearnTime.class));
		List<XxkhLearnTime> list = jdbcTemplate.query(resSql.toString(),
				new BeanPropertyRowMapper<>(XxkhLearnTime.class));
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(list);
		pageImpl.getData().setTotal((int)list1.size());
		return pageImpl;
	}

	@Override
	public PageImpl getDeptLearnTimeById(Pageable pageable, PageImpl pageImpl, String timeRange, String userId,
			String nodeIds) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from XxkhLearnTime t where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// para.add(UserUtil.getCruUserId());
		String startTime = "";
		String endTime = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim() + " 00:00:00";
			endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim() + " 23:59:59";
			querySql.append("	and (t.startTime <='" + endTime + "'  and t.overTime>='" + startTime + "')");
		}
		if (StringUtils.isNotBlank(userId)) {
			querySql.append(" and t.creUserId='" + userId + "'");
		}
		if (StringUtils.isNotBlank(nodeIds)) {
			querySql.append(" and t.infoId in (" + CommonUtils.commomSplit(nodeIds) + ")");
		}
		// 拼接排序语句 根据学习日期排序
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.startTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<XxkhLearnTime> page = dao.query(querySql.toString(), pageable, para.toArray());

		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/*
	 * @Override public List<XxkhLearnTime> listPage(XxkhLearnTime learnTime) {
	 * return dao.findList(learnTime); }
	 * 
	 * @Override public PageImpl listBuMenPage(PageImpl pageImpl, XxkhLearnTime
	 * learnTime, Pageable pageable) { StringBuilder querySql = new
	 * StringBuilder(); List<Object> para = new ArrayList<>(); querySql.append(
	 * "select new com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime(s.creDeptId,s.counts,s.times,t.counts) from (select t.creDeptId,t.type,sum(t.minutes) as times from  (select x.creDeptId,x.zlId,x.creUserId,x.minutes,d.type    from XxkhLearnTime x,DataTable d where x.zlId = d.id) t group by t.creDeptId,t.type) s,(select  creDeptId,count(1) as counts  from XxkhLearnTime group by creDeptId) t where s.creDeptId = t.creDeptId \r\n"
	 * + "");
	 * 
	 * Page<XxkhLearnTime> page =dao.query(querySql.toString(), pageable,
	 * para.toArray()); pageImpl.setFlag("1");
	 * pageImpl.getData().setRows(page.getContent());
	 * pageImpl.getData().setTotal((int)page.getTotalElements()); return
	 * pageImpl; }
	 * 
	 * @Override public List<XxkhLearnTime> listBuMenPage(XxkhLearnTime
	 * learnTime) { // TODO Auto-generated method stub return
	 * dao.findBuMenList(learnTime); }
	 * 
	 * @Override public List<XxkhLearnTime> deptUserList(XxkhLearnTime
	 * learnTime) { return dao.findDeptUserList(learnTime); }
	 */

}
