package com.sinosoft.sinoep.modules.zhbg.kqgl.bq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.constant.BqConfigConsts;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.dao.SupplementSignDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqAttendanceDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqAttendance;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

/**
 * 补签ServiceImpl TODO
 * 
 * @author 冯超
 * @Date 2018年4月11日 上午10:24:57
 */
@Service
public class SupplementSignServiceImpl implements SupplementSignService {

	@Autowired
	private SupplementSignDao supplementSignDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private KqAttendanceDao kqAttendanceDao;

	/**
	 * 保存 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 下午3:48:34
	 * @param supplementSign
	 * @return
	 */
	@Override
	public SupplementSign saveForm(SupplementSign supplementSign) {
		supplementSign.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(supplementSign.getId())) {
			// 创建人部门
			String creDeptId = UserUtil.getCruDeptId();
			String creDeptName = UserUtil.getCruDeptName();
			// 创建人处室
			String creChushiId = UserUtil.getCruChushiId();
			String creChushiName = UserUtil.getCruChushiName();
			// 创建人二级局
			String creJuId = UserUtil.getCruJuId();
			String creJuName = UserUtil.getCruJuName();
			supplementSign.setCreTime(creTime);
			supplementSign.setCreUserId(userId);
			supplementSign.setCreUserName(userName);
			supplementSign.setCreDeptId(creDeptId);
			supplementSign.setCreDeptName(creDeptName);
			supplementSign.setCreChushiId(creChushiId);
			supplementSign.setCreChushiName(creChushiName);
			supplementSign.setCreJuId(creJuId);
			supplementSign.setCreJuName(creJuName);
			supplementSign.setApplicantUnitId(creDeptId);
			supplementSign.setApplicantUnitName(creDeptName);
			//supplementSign.setApplicationTime(creTime);
			// 更新最后修改记录
			supplementSign.setUpdateTime(creTime);
			supplementSign.setUpdateUserId(userId);
			supplementSign.setUpdateUserName(userName);
			supplementSign = supplementSignDao.save(supplementSign);
		} else {
			SupplementSign oldSupplementSign = supplementSignDao.findOne(supplementSign.getId());
			oldSupplementSign.setLeaveTitle(supplementSign.getLeaveTitle());
			oldSupplementSign.setSupplementSignType(supplementSign.getSupplementSignType());
			oldSupplementSign.setSupplementSignDate(supplementSign.getSupplementSignDate());
			oldSupplementSign.setSupplementSignStartTime(supplementSign.getSupplementSignStartTime());
			oldSupplementSign.setSupplementSignEndTime(supplementSign.getSupplementSignEndTime());
			oldSupplementSign.setSupplementSignReason(supplementSign.getSupplementSignReason());
			// 更新最后修改记录
			oldSupplementSign.setUpdateTime(creTime);
			oldSupplementSign.setUpdateUserId(userId);
			oldSupplementSign.setUpdateUserName(userName);
			supplementSignDao.save(oldSupplementSign);
		}
		return supplementSign;
	}

	/**
	 * 查询请假审批列表 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 下午4:01:26
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String supplementSignType, String creUserName, String applicantUnitName, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(
				"select new com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign(f.id as yibanid,t.id, t.leaveTitle, t.subflag, t.creUserName, t.applicantUnitName, t.supplementSignType,");
		querySql.append("t.supplementSignStartTime, t.supplementSignEndTime)");
		querySql.append("	from SupplementSign t, FlowRead f ");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		/*
		 * if (StringUtils.isNotBlank(startDate)) { querySql.append(
		 * "   and t.leaveStartDate >= ?"); para.add(startDate); } if
		 * (StringUtils.isNotBlank(endDate)) { querySql.append(
		 * "   and t.leaveEndDate <= ?"); para.add(endDate); }
		 */
		// 请假类型
		if (StringUtils.isNotBlank(supplementSignType)) {
			querySql.append("   and t.supplementSignType = ?");
			para.add(supplementSignType);
		}
		// 申请人
		if (StringUtils.isNotBlank(creUserName)) {
			querySql.append("   and t.creUserName like ?");
			para.add("%" + creUserName + "%");
		}
		// 单位
		if (StringUtils.isNotBlank(applicantUnitName)) {
			querySql.append("   and t.applicantUnitName like ?");
			para.add("%" + applicantUnitName + "%");
		}

		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<SupplementSign> page = supplementSignDao.query(querySql.toString(), pageable, para.toArray());

		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 补签草稿列表 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 上午10:16:59
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String supplementSignType, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from SupplementSign t ");
		querySql.append(
				"	where t.creUserId = ? and t.subflag = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		//para.add(UserUtil.getCruDeptId());
		para.add(subflag);
		// 起始时间
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and ((t.supplementSignStartTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.supplementSignStartTime <= ?)or");
			para.add(endDate);
		}
		// 结束时间
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("  (t.supplementSignEndTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.supplementSignEndTime <= ?))");
			para.add(endDate);
		}
		// 补签类型
		if (StringUtils.isNotBlank(supplementSignType)) {
			querySql.append("   and t.supplementSignType = ?");
			para.add(supplementSignType);
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<SupplementSign> page = supplementSignDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<SupplementSign> content = page.getContent();
		for (SupplementSign supplementSign : content) {
			if (ConfigConsts.START_FLAG.equals(supplementSign.getSubflag())) {
				supplementSign.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id获取补签信息 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午12:00:58
	 * @param id
	 * @return
	 */
	@Override
	public SupplementSign getById(String id) {
		SupplementSign supplementSign = supplementSignDao.findOne(id);
		// 补签类型（只读）
		if (!"".equals(supplementSign.getSupplementSignType()) && supplementSign.getSupplementSignType() != null) {
			if ("0".equals(supplementSign.getSupplementSignType())) {
				supplementSign.setSupplementSignTypeName("补签入");
			} else if ("1".equals(supplementSign.getSupplementSignType())) {
				supplementSign.setSupplementSignTypeName("补签出");
			} else if ("2".equals(supplementSign.getSupplementSignType())) {
				supplementSign.setSupplementSignTypeName("补全天");
			}
		}
		return supplementSign;
	}

	/**
	 * 删除数据 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:43:11
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update SupplementSign t set t.visible = ? where t.id = ?";
		try {
			result = supplementSignDao.update(jpql, CommonConstants.VISIBLE[0], id);
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
	public SupplementSign updateSubFlag(String id, String userId, String supplementSignDate, String supplementSignTime,
			String supplementSignType, String subflag) {
		SupplementSign supplementSign = getById(id);
		supplementSign.setSubflag(subflag);
		// 判断subflag 流程结束插入一条考勤日志
		if (subflag.equals(ConfigConsts.APPROVAL_FLAG) || subflag.equals(ConfigConsts.NO_APPROVAL_FLAG)) {
			saveKqAttendanceLog(userId, supplementSignDate, supplementSignTime, supplementSignType);
		}
		return supplementSignDao.save(supplementSign);
	}

	// 补签流程结束后 插入一条考勤数据
	public void saveKqAttendanceLog(String userId, String supplementSignDate, String supplementSignTime,
			String supplementSignType) {
		KqAttendance kqAttendance = new KqAttendance();
		kqAttendance.setCreUserId(UserUtil.getCruUserId());
		kqAttendance.setCreUserName(UserUtil.getCruUserName());
		kqAttendance.setCreDeptId(UserUtil.getCruDeptId());
		kqAttendance.setCreDeptName(UserUtil.getCruDeptName());
		kqAttendance.setCreChuShiId(UserUtil.getCruChushiId());
		kqAttendance.setCreChuShiName(UserUtil.getCruChushiName());
		kqAttendance.setCreJuId(UserUtil.getCruJuId());
		kqAttendance.setCreJuName(UserUtil.getCruJuName());
		kqAttendance.setVisible(CommonConstants.VISIBLE[1]);
		kqAttendance.setCreTime(JDateToolkit.getNowDate4());
		// 补签日期
		kqAttendance.setImportDate(supplementSignDate);
		// 根据补签类型 判断闸机号 todo
		String gateNumber = "";
		if (StringUtils.isNotBlank(supplementSignType) && supplementSignType.equals("0")) {
			gateNumber = BqConfigConsts.GATE_NUMBER_BQR;
		}
		if (StringUtils.isNotBlank(supplementSignType) && supplementSignType.equals("1")) {
			gateNumber = BqConfigConsts.GATE_NUMBER_BQC;
		}
		kqAttendance.setGateNumber(gateNumber);
		// 刷卡时间需要判断 补签开始时间 补签结束时间
		kqAttendance.setCareTime(supplementSignTime);
		// 获取卡号
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userId);
		SysFlowUserVo sysFlowUserVo = map.get(userId);
		if (sysFlowUserVo != null) {
			kqAttendance.setCardNumber(sysFlowUserVo.getCardNumber() == null ? "" : sysFlowUserVo.getCardNumber());
		}
		// 导入
		kqAttendanceDao.save(kqAttendance);

	}

	@Override
	public List<SupplementSign> getsByDate(String startDate, String endDate, String supplementSignType) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select * ");
		querySql.append("	from kqgl_supplement_sign_info t");
		querySql.append("	where ");
		querySql.append("  t.cre_user_id= ? and t.visible = '"
				+ CommonConstants.VISIBLE[1] + "'");
		para.add(UserUtil.getCruUserId());
		// 拼接条件
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.supplement_sign_start_time >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.supplement_sign_end_time <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(supplementSignType)) {
			querySql.append(" and t.supplement_sign_type= ?");
			para.add(supplementSignType);
		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(SupplementSign.class),para.toArray());
	}

}
