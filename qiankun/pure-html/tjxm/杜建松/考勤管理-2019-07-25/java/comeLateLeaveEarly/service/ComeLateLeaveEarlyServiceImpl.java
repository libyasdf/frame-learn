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
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.dao.ComeLateLeaveEarlyDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

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
			comeLateLeaveEarlyInfo.setApplicantChushiId(creChushiId);
			comeLateLeaveEarlyInfo.setApplicantChushiName(creChushiName);
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
			oldComlateInfo.setReasonType(comeLateLeaveEarlyInfo.getReasonType());
			oldComlateInfo.setIsChoose(comeLateLeaveEarlyInfo.getIsChoose());
			oldComlateInfo.setOtherContent(comeLateLeaveEarlyInfo.getOtherContent());
			
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
			querySql.append(" and t.cdztDate between ? and ?");
			para.add(startDate);
			para.add(endDate);
		}
		// 申请人
//		if (StringUtils.isNotBlank(cdztUserId)) {
//			querySql.append("   and t.cdztUserId in ("+CommonUtils.commomSplit(cdztUserId)+")");
//		}
		//
		if (StringUtils.isNotBlank(state)) {
			querySql.append("   and t.state= ?");
			para.add(state);
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
		ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userid.split(",")));
		List<Object> para = new ArrayList<>();
		StringBuilder sb =  new StringBuilder("select t.cre_user_id userid,t.cdzt_date date1,t.state,t.cdzt_reason from KQGL_COMELATE_LEAVEEARLY_INFO t where  t.visible='1' and t.subflag='5'   ");
		useridIntoSql(useridList, sb,para);
		boolean flg1 = MyUserUtil.isDateString(startDate, "yyyy-mm-dd");
		boolean flg2 = MyUserUtil.isDateString(endDate, "yyyy-mm-dd");
		if(StringUtils.isNotBlank(startDate)&&flg1){
			sb.append("  and to_date(t.cdzt_date,'yyyy-mm-dd') >= to_date(?,'yyyy-mm-dd')");
			para.add(startDate);
		}if(StringUtils.isNotBlank(endDate)&&flg2){
			sb.append("  and to_date(t.cdzt_date,'yyyy-mm-dd') <= to_date(?,'yyyy-mm-dd') ");
			para.add(endDate);
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		return list;
	}

	@Override
	public List<Map<String, Object>> groupByCdztReason(String timeRange, String dataType,String state,String treeIds) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Object> para = new ArrayList<>();
		String cdztReason = getCdztReason(timeRange,state);
		if(StringUtils.isNotBlank(cdztReason)){
			StringBuffer sb = new StringBuffer("select *  from (select t.cre_ju_id, t.cre_ju_name as bumen, cre_chushi_id,t.cre_chushi_name as chushi," );
			if(dataType.equals("user")){
				sb.append(" t.cre_user_id, t.cre_user_name as name,");
			}
			sb.append(" t.cdzt_reason from kqgl_comelate_leaveearly_info t where is_choose = '1' and reason_type = '0' ");
			sb.append(" and state = ? ");
			para.add(state);
			String inString = CommonUtils.solveSqlInjectionOfIn(treeIds, para);
			sb.append("  and cre_ju_id in ("+ inString+") ");
			if (StringUtils.isNotBlank(timeRange)) {
				String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				if(startDate.length() == 4 || endDate.length() == 4){
					startDate = startDate + "-01-01";
					endDate = endDate + "-12-31";
				}else if(startDate.length() == 7 || endDate.length() == 7){
					startDate = startDate + "-01";
					endDate = endDate + "-31";
				}
				sb.append(" and cdzt_date >= ? and cdzt_date <= ? ");
				para.add(startDate);
				para.add(endDate);
			}
			sb.append(" and subflag = '5') tt pivot(count(cdzt_reason)  for cdzt_reason in ("+ CommonUtils.commomSplit(cdztReason) +"))");
			list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		}
		return list;
	}

	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb,List<Object> para) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		String inString = "";
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" t.cre_user_id in (");
				}else{
					sb.append(" or t.cre_user_id in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				inString = CommonUtils.solveSqlInjectionOfIn(useridSb.toString(), para);
				sb.append(inString + " )");
			}
			sb.append(" )");
		}
	}


	private String getCdztReason(String timeRange,String state){
		List<Object> para = new ArrayList<>();
		StringBuffer sb = new StringBuffer("select distinct (t.cdzt_reason) from kqgl_comelate_leaveearly_info t ");
		sb.append("where is_choose = '1' and reason_type='0' and subflag = '5' ");
		sb.append(" and state= ? ");
		para.add(state);
		if (StringUtils.isNotBlank(timeRange)) {
			String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			if(startDate.length() == 4 || endDate.length() == 4){
				startDate = startDate + "-01-01";
				endDate = endDate + "-12-31";
			}else if(startDate.length() == 7 || endDate.length() == 7){
				startDate = startDate + "-01";
				endDate = endDate + "-31";
			}
			sb.append("and cdzt_date >= ? and cdzt_date <= ?");
			para.add(startDate);
			para.add(endDate);
		}
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		String cdztReason = "";
		if(list.size() > 0){
			for(Map<String,Object> map : list){
				cdztReason += (String)map.get("CDZT_REASON")+",";
			}
			cdztReason = cdztReason.substring(0,cdztReason.length()-1);
		}
		return cdztReason;
	}

}
