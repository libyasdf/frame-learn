package com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.dao.AbsenteeismDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.entity.AbsenteeismInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 旷工ServiceImpl
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 下午2:15:29
 */
@Service
public class AbsenteeismInfoServiceImpl implements AbsenteeismInfoService {

	@Autowired
	private AbsenteeismDao absenteeismDao;
	
	@Autowired
	private TreeService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	/**
	 * 保存
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 上午11:48:21
	 * @param absenteeismInfo
	 * @return
	 */
	@Override
	public AbsenteeismInfo saveForm(AbsenteeismInfo absenteeismInfo) {
		absenteeismInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		absenteeismInfo.setUpdateUserId(UserUtil.getCruUserId());
		absenteeismInfo.setUpdateUserName(UserUtil.getCruUserName());
		
		if(StringUtils.isBlank(absenteeismInfo.getId())) {
			DeptAllInfo dept = userInfoService.getDeptInfoByDeptId(absenteeismInfo.getAbsApplicantUnitId());
			//新增
			absenteeismInfo.setCreTime(creTime);
			absenteeismInfo.setCreUserId(UserUtil.getCruUserId());
			absenteeismInfo.setCreUserName(UserUtil.getCruUserName());
			absenteeismInfo.setCreDeptId(UserUtil.getCruDeptId());
			absenteeismInfo.setCreDeptName(UserUtil.getCruDeptName());
			String chushiId = UserUtil.getCruChushiId();
			absenteeismInfo.setCreChushiId(chushiId);
			absenteeismInfo.setCreChushiName(UserUtil.getCruChushiName());
			absenteeismInfo.setCreJuId(UserUtil.getCruJuId());
			absenteeismInfo.setCreJuName(UserUtil.getCruJuName());
			absenteeismInfo.setApplicantUnitId(UserUtil.getCruDeptId());
			absenteeismInfo.setApplicantUnitName(UserUtil.getCruDeptName());
			absenteeismInfo.setAbsApplicantChushiId(dept.getChushiId());
			absenteeismInfo.setAbsApplicantChushiName(dept.getChushiName());
			absenteeismInfo = absenteeismDao.save(absenteeismInfo);
		}else {
			//修改
			AbsenteeismInfo oldinfo = absenteeismDao.findOne(absenteeismInfo.getId());
			oldinfo.setAbsenteeismDate(absenteeismInfo.getAbsenteeismDate());
			oldinfo.setAbsenteeismUserId(absenteeismInfo.getAbsenteeismUserId());
			oldinfo.setAbsenteeismUserName(absenteeismInfo.getAbsenteeismUserName());
			oldinfo.setAbsenteeismReason(absenteeismInfo.getAbsenteeismReason());
			oldinfo.setAbsenteeismType(absenteeismInfo.getAbsenteeismType());
			oldinfo.setAbsApplicantUnitId(absenteeismInfo.getAbsApplicantUnitId());
			oldinfo.setAbsApplicantUnitName(absenteeismInfo.getAbsApplicantUnitName());
			oldinfo.setAbsApplicantChushiId(absenteeismInfo.getAbsApplicantChushiId());
			oldinfo.setAbsApplicantChushiName(absenteeismInfo.getAbsApplicantChushiName());
			oldinfo.setUpdateTime(creTime);
			absenteeismDao.save(oldinfo);
		}
		return absenteeismInfo;
	}

	/**
	 * 获取旷工列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午1:02:21
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String userId) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from AbsenteeismInfo t ");
		querySql.append(" where  t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		String childDept = getChildDept();
		childDept = childDept.replaceAll("'", "");
		String inString = CommonUtils.solveSqlInjectionOfIn(childDept, para);
		querySql.append("   and t.creChushiId in (" + inString + ")");
		if(StringUtils.isNotBlank(userId)){
			String inString1 = CommonUtils.solveSqlInjectionOfIn(userId, para);
			querySql.append(" and t.absenteeismUserId in ("+inString1+") ");
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.absenteeismDate between ? and ?");
			para.add(startDate);
			para.add(endDate);
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.absenteeismDate desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<AbsenteeismInfo> page = absenteeismDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<AbsenteeismInfo> content = page.getContent();
		for (AbsenteeismInfo absenteeismInfo : content) {
			absenteeismInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id获取旷工信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午2:09:56
	 * @param id
	 * @return
	 */
	@Override
	public AbsenteeismInfo getById(String id) {
		AbsenteeismInfo absenteeismInfo = absenteeismDao.findOne(id);
		return absenteeismInfo;
	}

	/**
	 * 根据id删除旷工信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午2:11:54
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update AbsenteeismInfo t set t.visible = ? where t.id = ?";
		try {
			result = absenteeismDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return result;
	}
	@Override
	public List<AbsenteeismInfo> getsByDate(String startDate, String endDate, String userId) {
			StringBuilder querySql = new StringBuilder();
			querySql.append("select * ");
			querySql.append("	from KQGL_ABSENTEEISM_INFO t");
			querySql.append("	where ");
			querySql.append("  t.visible = '" + CommonConstants.VISIBLE[1] + "'");
			String childDept = getChildDept();
			childDept = childDept.replaceAll("'", "");
			List<Object> para = new ArrayList<>();
			String inString = CommonUtils.solveSqlInjectionOfIn(childDept, para );
			querySql.append("   and t.cre_chushi_id in (" + inString + ")");
			// 拼接条件
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.absenteeism_date between ? and ?" );
				para.add(startDate);
				para.add(endDate);
			}
			if (StringUtils.isNotBlank(userId)) {  
				String inString1 = CommonUtils.solveSqlInjectionOfIn(userId, para );
				querySql.append("   and t.absenteeism_user_id in (" + inString1 +")" );
			}
			
			querySql.append("  order by t.application_time desc ");
			return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(AbsenteeismInfo.class),para.toArray());
	}
	
	private String getChildDept() {
		JSONArray depts = service.getDeptTreeByDeptId(UserUtil.getCruChushiId());
		StringBuilder sb = new StringBuilder();
		for (Object object : depts) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			sb.append(jsonObj.getString("uuid") + ",");
		}
		String childDept = CommonUtils.commomSplit(sb.toString());
		return childDept;
	}
	
	/**
	 * 查询某段时间某些人的旷工数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:37:35
	 * @param startDate
	 * @param endDate
	 * @param userids
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findUserData(String startDate, String endDate, String userids) {
		ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userids.split(",")));
		List<Object> para = new ArrayList<>();
		StringBuilder sb = new StringBuilder("select t.absenteeism_user_id userid,t.absenteeism_date date1,t.absenteeism_reason from KQGL_ABSENTEEISM_INFO t where t.visible='1'  ");
		useridIntoSql(useridList, sb,para);
		if(StringUtils.isNotBlank(startDate)){
			sb.append("  and t.absenteeism_date>= ? ");
			para.add(startDate);
		}if(StringUtils.isNotBlank(endDate)){
			sb.append("  and t.absenteeism_date<= ? ");
			para.add(endDate);
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		return list;
	}

	@Override
	public List<Map<String, Object>> findByParams(String timeRange,String dataType,String absenteeismType,String treeIds) {
		StringBuffer sb = new StringBuffer();
		List<Object> para = new ArrayList<>();
		sb.append("select cre_ju_id,cre_ju_name as bumen,cre_chushi_id,cre_chushi_name as chushi," );
		if(dataType.equals("user")){
			sb.append(" absenteeism_user_id,absenteeism_user_name as name, ");
		}
		sb.append("count(1) as SC from KQGL_ABSENTEEISM_INFO  where visible = '1' and ABSENTEEISM_TYPE = ? " );
		para.add(absenteeismType);
		String inString = CommonUtils.solveSqlInjectionOfIn(treeIds, para );
		sb.append("  and cre_ju_id in ("+ inString+") ");
		if (StringUtils.isNotBlank(timeRange)) {
			String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			boolean flg1 = MyUserUtil.isDateString(startDate, "yyyy-mm-dd");
			boolean flg2 = MyUserUtil.isDateString(endDate, "yyyy-mm-dd");
			if(startDate.length() == 10&&flg1&&flg2){
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,10),'yyyy-mm-dd') >= to_date(?,'yyyy-mm-dd') ");
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,10),'yyyy-mm-dd') <= to_date(?,'yyyy-mm-dd') ");
				para.add(startDate);
				para.add(endDate);
			}else if(startDate.length() == 7&&flg1&&flg2){
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,7),'yyyy-mm') >= to_date(?,'yyyy-mm') ");
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,7),'yyyy-mm') <= to_date(?,'yyyy-mm') ");
				para.add(startDate);
				para.add(endDate);
			}else if(flg1&&flg2){
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,4),'yyyy') >= to_date(?,'yyyy') ");
				sb.append(" and to_date(substr(ABSENTEEISM_DATE,1,4),'yyyy') <= to_date(?,'yyyy') ");
				para.add(startDate);
				para.add(endDate);
			}
		}
		sb.append(" group by cre_ju_id,cre_ju_name,cre_chushi_id,cre_chushi_name ");
		if(dataType.equals("user")){
			sb.append(" ,absenteeism_user_id,absenteeism_user_name");
		}
		return 	jdbcTemplate.queryForList(sb.toString(),para.toArray());
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
					sb.append(" t.absenteeism_user_id in (");
				}else{
					sb.append(" or t.absenteeism_user_id in (");
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
}
