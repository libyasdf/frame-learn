package com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.dao.MeetingApplyDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.dao.MeetingSignDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.entity.MeetingSignInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
public class MeetingSignServiceImpl implements MeetingSignService{
	@Autowired
	private MeetingSignDao meetingSignDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<MeetingSignInfo> saveForm(List<MeetingSignInfo> meetingSignInfos) throws Exception {
		List<MeetingSignInfo> newMeetingSignInfos = new ArrayList<MeetingSignInfo>();
		//根据会议的Id和卡号判断是否全量导入
		if(meetingSignInfos!=null && meetingSignInfos.size()>0){
			String meApplyId=meetingSignInfos.get(0).getMeetingApplyId();
			String sql = "select MEETING_APPLY_ID from HYGL_MEETING_SIGN t where t.MEETING_APPLY_ID=? and t.visible=1";
			List<MeetingSignInfo> signList = jdbcTemplate.query(sql, new Object[] {meApplyId}, new BeanPropertyRowMapper<MeetingSignInfo>(MeetingSignInfo.class));
			if(signList!=null && signList.size()>0){
				//有本次会议的id 根据卡号查询单条数据是否同步
				for(MeetingSignInfo mtSinInfo:meetingSignInfos){
					//根据卡号查询是否有记录  有不用同步 没有需要同步
					String cardNum=mtSinInfo.getCardId();
					String signInTime=mtSinInfo.getSignInTime();
					String cardNumSql = "select MEETING_APPLY_ID from HYGL_MEETING_SIGN t where t.MEETING_APPLY_ID=? and t.CARD_ID=? and t.SIGN_IN_TIME=? and t.visible=1";
					List<MeetingSignInfo> carNumList = jdbcTemplate.query(cardNumSql, new Object[] {meApplyId,cardNum,signInTime}, new BeanPropertyRowMapper<MeetingSignInfo>(MeetingSignInfo.class));
					if(carNumList.size()==0||carNumList==null){
						//没有数据则同步
						newMeetingSignInfos.add(saveForm(mtSinInfo));
					}
				}
			}else{
			//没有本次会议id 全量同步
				for(MeetingSignInfo meetingSignInfo : meetingSignInfos){
					newMeetingSignInfos.add(saveForm(meetingSignInfo));
				}
			}
		}
		return newMeetingSignInfos;
	}

	/**
	 * 保存
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:12:57
	 * @param meetingServiceNotice
	 * @return
	 * @throws Exception
	 */
	@Override
	public MeetingSignInfo saveForm(MeetingSignInfo meetingSignInfo) throws Exception {
		meetingSignInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String creUserId = UserUtil.getCruUserId();
		String creUserName = UserUtil.getCruUserName();
		String creDeptId = UserUtil.getCruDeptId();
		String creDeptName = UserUtil.getCruDeptName();
		if (StringUtils.isBlank(meetingSignInfo.getId())) {
			//新建保存
			meetingSignInfo.setCreTime(creTime);
			meetingSignInfo.setCreUserId(creUserId);
			meetingSignInfo.setCreUserName(creUserName);
			meetingSignInfo.setCreDeptId(creDeptId);
			meetingSignInfo.setCreDeptName(creDeptName);
			
			//临时数据
			//meetingServiceNotice.setMeetingroomId("402894826521dacb016521ff8d1f0000");
			//meetingServiceNotice.setMeetingroomName("三楼1310");
			//meetingServiceNotice.setMeetingStartDate("2018-08-23");
			//meetingServiceNotice.setMeetingStartTime("0");//0 上午 8:00 1下午 12:00
			//meetingServiceNotice.setMeetingEndDate("2018-08-23");
			//meetingServiceNotice.setMeetingEndTime("0");//0:上午 12:00 1下午18:00
			//拼接为一个新的日期范围，方便会议室预约情况的统计
			
			meetingSignInfo = meetingSignDao.save(meetingSignInfo);
		}else{
			//更新
			MeetingSignInfo oldMeetingServiceNotice = meetingSignDao.findOne(meetingSignInfo.getId());
			//更新人数据
			oldMeetingServiceNotice.setUpdateTime(creTime);
			oldMeetingServiceNotice.setUpdateUserId(creUserId);
			oldMeetingServiceNotice.setUpdateUserName(creUserName);
			//业务数据
			meetingSignInfo = meetingSignDao.save(oldMeetingServiceNotice);
		}
		return meetingSignInfo;
	}

	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" from MeetingSignInfo t ");
		querySql.append("	where  t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		// 起始时间
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.startTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.startTime <= ? ");
			para.add(endDate);
		}
		// 会议名称
		if (StringUtils.isNotBlank(meetingName)) {
			querySql.append("   and t.noticeName like ?");
			para.add("%"+meetingName+"%");
		}
		// 状态
		if (StringUtils.isNotBlank(status)) {
			querySql.append("   and t.status = ?");
			para.add(status);
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<MeetingSignInfo> page = meetingSignDao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

}
