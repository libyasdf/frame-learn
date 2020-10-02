package com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.dao.ComeLateLeaveEarlyDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 迟到早退ServiceImpl
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 下午8:39:59
 */
@Service
public class ComeLateLeaveEarlyServiceImpl implements ComeLateLeaveEarlyService {

	@Autowired
    private ComeLateLeaveEarlyDao comeLateLeaveEarlyDao;
	@Autowired
    private  JdbcTemplate jdbcTemplate;
	/**
	 * 保存
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月17日 下午3:32:32
	 * @param comeLateLeaveEarlyInfo
	 * @return
	 */
	@Override
	public ComeLateLeaveEarlyInfo saveForm(ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo) {
		comeLateLeaveEarlyInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		//按时间请假 手动输入请节哀时长时 出入的数据格式一致
//		if(StringUtils.isNotBlank(kqglLeaveInfo.getLeaveLongTime())){
//		  kqglLeaveInfo.setLeaveLongTime(kqglUtil.getNumFormate(kqglLeaveInfo.getLeaveLongTime(),1));
//		}
		if (StringUtils.isBlank(comeLateLeaveEarlyInfo.getId())) {
			// 创建人部门
			String creDeptId = UserUtil.getCruDeptId();
			String creDeptName = UserUtil.getCruDeptName();
			// 创建人处室
			String creChushiId = UserUtil.getCruChushiId();
			String creChushiName = UserUtil.getCruChushiName();
			// 创建人二级局
			String creJuId = UserUtil.getCruJuId();
			String creJuName = UserUtil.getCruJuName();

			comeLateLeaveEarlyInfo.setCreTime(creTime);
			comeLateLeaveEarlyInfo.setCreUserId(userId);
			comeLateLeaveEarlyInfo.setCreUserName(userName);
			comeLateLeaveEarlyInfo.setCreDeptId(creDeptId);
			comeLateLeaveEarlyInfo.setCreDeptName(creDeptName);
			comeLateLeaveEarlyInfo.setCreChushiId(creChushiId);
			comeLateLeaveEarlyInfo.setCreChushiName(creChushiName);
			comeLateLeaveEarlyInfo.setCreJuId(creJuId);
			comeLateLeaveEarlyInfo.setCreJuName(creJuName);
			comeLateLeaveEarlyInfo.setApplicantUnitId(creDeptId);
			comeLateLeaveEarlyInfo.setApplicantUnitName(creDeptName);
			//kqglLeaveInfo.setApplicationTime(creTime);
			// 更新最后修改记录
			comeLateLeaveEarlyInfo.setUpdateTime(creTime);
			comeLateLeaveEarlyInfo.setUpdateUserId(userId);
			comeLateLeaveEarlyInfo.setUpdateUserName(userName);
			comeLateLeaveEarlyInfo = comeLateLeaveEarlyDao.save(comeLateLeaveEarlyInfo);
		} else {
			ComeLateLeaveEarlyInfo oldComlateInfo = comeLateLeaveEarlyDao.findOne(comeLateLeaveEarlyInfo.getId());
			oldComlateInfo.setCdztUserId(comeLateLeaveEarlyInfo.getCdztUserId());
			oldComlateInfo.setCdztUserName(comeLateLeaveEarlyInfo.getCdztUserName());
			oldComlateInfo.setState(comeLateLeaveEarlyInfo.getState());
			oldComlateInfo.setCdztDate(comeLateLeaveEarlyInfo.getCdztDate());
			oldComlateInfo.setCdztReason(comeLateLeaveEarlyInfo.getCdztReason());
			
			// 更新最后修改记录
			oldComlateInfo.setUpdateTime(creTime);
			oldComlateInfo.setUpdateUserId(userId);
			oldComlateInfo.setUpdateUserName(userName);
			comeLateLeaveEarlyDao.save(oldComlateInfo);
		}
		return comeLateLeaveEarlyInfo;
	}

	/**
	 * 获取迟到早退列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月17日 下午3:33:35
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param cdztUserId
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String state,String subflag) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from ComeLateLeaveEarlyInfo t ");
		querySql.append(
				"	where t.creUserId = ? and t.subflag = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		//para.add(UserUtil.getCruDeptId());
		para.add(subflag);
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.cdztDate between '"+startDate+"' and '"+endDate+"'");
		}
		// 申请人
//		if (StringUtils.isNotBlank(cdztUserId)) {
//			querySql.append("   and t.cdztUserId in ("+CommonUtils.commomSplit(cdztUserId)+")");
//		}
		//
		if (StringUtils.isNotBlank(state)) {
			querySql.append("   and t.state='"+state+"'");
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<ComeLateLeaveEarlyInfo> page = comeLateLeaveEarlyDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<ComeLateLeaveEarlyInfo> content = page.getContent();
		for (ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo : content) {
			//if (ConfigConsts.START_FLAG.equals(comeLateLeaveEarlyInfo.getSubflag())) {
				comeLateLeaveEarlyInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			//}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id查询迟到早退信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午3:03:08
	 * @param id
	 * @return
	 */
	@Override
	public ComeLateLeaveEarlyInfo getById(String id) {
		ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo = comeLateLeaveEarlyDao.findOne(id);
		return comeLateLeaveEarlyInfo;
	}

	/**
	 * 根据id删除迟到早退信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午3:27:30
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update ComeLateLeaveEarlyInfo t set t.visible = ? where t.id = ?";
		try {
			result = comeLateLeaveEarlyDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新状态 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月18日 上午8:50:56
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public ComeLateLeaveEarlyInfo updateSubFlag(String id, String subflag) {
		ComeLateLeaveEarlyInfo kqglLeaveInfo = getById(id);
		kqglLeaveInfo.setSubflag(subflag);
		return comeLateLeaveEarlyDao.save(kqglLeaveInfo);
	}

	/**
	 * 
	 * 查询某些用户某段时间审批统计的迟到早退数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:01:08
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findUserData(String startDate, String endDate, String userid) {
		StringBuilder sb =  new StringBuilder("select t.cre_user_id userid,t.cdzt_date date1,t.state,t.cdzt_reason from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.visible='1' and t.subflag='5' and t.cre_user_id in(" + userid + ")  ");
		if(StringUtils.isNotBlank(startDate)){
			sb.append("  and to_date(t.cdzt_date,'yyyy-mm-dd') >= to_date('" + startDate + "','yyyy-mm-dd')");
		}if(StringUtils.isNotBlank(endDate)){
			sb.append("  and to_date(t.cdzt_date,'yyyy-mm-dd') <= to_date('" + endDate + "','yyyy-mm-dd') ");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
}
