package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.dao.MeetingRoomInfoDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity.MeetingRoomInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.util.MeetingRoomUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 会议室管理
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:42:05
 */
@Service
public class MeetingRoomInfoServiceImp implements MeetingRoomInfoService{
	
	@Autowired
	private MeetingRoomInfoDao meetingRoomInfoDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取会议室信息
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:59:47
	 * @param id
	 * @return
	 */
	@Override
	public MeetingRoomInfo getById(String id) {
		return meetingRoomInfoDao.findOne(id);
	}
	/**
	 * 根据门牌号获取会议室信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月29日 上午10:12:54
	 * @param doorNumber
	 * @return
	 */
	@Override
	public List<MeetingRoomInfo> getByDoorNum(String doorNumber) {
		List<Object> para = new ArrayList<>();
		String sql="select t.door_number from HYGL_MEETINGROOM t where 1=1 and t.visible = ?";
		para.add(CommonConstants.VISIBLE[1]);
		if(StringUtils.isNotBlank(doorNumber)){
			sql+=" and t.door_number=?";
			para.add(doorNumber);
		}
		List<MeetingRoomInfo> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<>(MeetingRoomInfo.class),para.toArray());
		return list;
	}
	
	/**
	 * 
	 * TODO 保存会议室信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:13:31
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public MeetingRoomInfo saveForm(MeetingRoomInfo meetingRoomInfo) {
		meetingRoomInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(meetingRoomInfo.getId())) {

			meetingRoomInfo.setCreTime(creTime);
			meetingRoomInfo.setCreUserId(userId);
			meetingRoomInfo.setCreUserName(userName);
			// 更新最后修改记录
			meetingRoomInfo.setUpdateTime(creTime);
			meetingRoomInfo.setUpdateUserId(userId);
			meetingRoomInfo.setUpdateUserName(userName);
			meetingRoomInfo.setSort(MeetingRoomUtil.getSort());
			meetingRoomInfo = meetingRoomInfoDao.save(meetingRoomInfo);
		} else {
			MeetingRoomInfo oldMeetingRoomInfo = meetingRoomInfoDao.findOne(meetingRoomInfo.getId());
			oldMeetingRoomInfo.setMeetingRoomNo(meetingRoomInfo.getMeetingRoomNo());
			oldMeetingRoomInfo.setMeetingRoomName(meetingRoomInfo.getMeetingRoomName());
			oldMeetingRoomInfo.setLocation(meetingRoomInfo.getLocation());
			oldMeetingRoomInfo.setContent(meetingRoomInfo.getContent());
			oldMeetingRoomInfo.setDoorNumber(meetingRoomInfo.getDoorNumber());
			oldMeetingRoomInfo.setMeetingroomState(meetingRoomInfo.getMeetingroomState());
			oldMeetingRoomInfo.setLayout(meetingRoomInfo.getLayout());
			oldMeetingRoomInfo.setEquipment(meetingRoomInfo.getEquipment());
			oldMeetingRoomInfo.setManageDeptId(meetingRoomInfo.getManageDeptId());
			oldMeetingRoomInfo.setManageDeptName(meetingRoomInfo.getManageDeptName());
			oldMeetingRoomInfo.setNetwork(meetingRoomInfo.getNetwork());
			oldMeetingRoomInfo.setManageUserName(meetingRoomInfo.getMeetingRoomName());
			oldMeetingRoomInfo.setPhone(meetingRoomInfo.getPhone());
			oldMeetingRoomInfo.setRemark(meetingRoomInfo.getRemark());
			// 更新最后修改记录
			oldMeetingRoomInfo.setUpdateTime(creTime);
			oldMeetingRoomInfo.setUpdateUserId(userId);
			oldMeetingRoomInfo.setUpdateUserName(userName);
			meetingRoomInfoDao.save(oldMeetingRoomInfo);
		}
		return meetingRoomInfo;
	}
	
	/**
	 * 
	 * TODO 查询会议室列表
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:32:50
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String name) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from MeetingRoomInfo t ");
		querySql.append("	where  t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		if (StringUtils.isNotBlank(name)) {
			querySql.append("   and t.name like ?");
			para.add("%"+name+"%");
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<MeetingRoomInfo> page = meetingRoomInfoDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<MeetingRoomInfo> content = page.getContent();
        for (MeetingRoomInfo meetingRoomInfo : content) {
        	meetingRoomInfo.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 
	 * TODO 查询会议室列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public List<MeetingRoomInfo> getList(final MeetingRoomInfo meetingRoomInfo) {
		Order order = new Order(Direction.ASC, "sort");
		Order order1 = new Order(Direction.DESC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		list.add(order);
		Sort sort = new Sort(list);
		return meetingRoomInfoDao.findAll(new Specification<MeetingRoomInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingRoomInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				if(StringUtils.isNotBlank(meetingRoomInfo.getCz()) && "1".equals(meetingRoomInfo.getCz())){
					Predicate pVisible1 = cb.equal(root.get("meetingroomState").as(String.class), CommonConstants.VISIBLE[0]);
					predicates.add(pVisible1);
				}
				Predicate pName = null;
				if (!StringUtils.isBlank(meetingRoomInfo.getMeetingRoomName())) {
					pName = cb.like(root.get("meetingRoomName").as(String.class), "%" + meetingRoomInfo.getMeetingRoomName() + "%");
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
	/**
	 * 
	 * TODO 删除会议室
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:56:42
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update MeetingRoomInfo t set t.visible = ? where t.id = ?";
		return meetingRoomInfoDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 
	 * TODO 会议室排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:21:17
	 * @param ids
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MeetingRoomInfo meetingRoomInfo = meetingRoomInfoDao.findOne(ids[i]);
			meetingRoomInfo.setSort(i);
			meetingRoomInfoDao.save(meetingRoomInfo);
		}
	}
	
	/**
	 * 判断会议室编号是否重复
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 下午4:10:18
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public boolean checkSdi(MeetingRoomInfo meetingRoomInfo){
		//重复：false  不重复：true
		boolean valid = false;
		//判断是否重复
		List<MeetingRoomInfo> meetingRoomInfoList = getListExcludeId(meetingRoomInfo);
		if(0 == meetingRoomInfoList.size()){
			valid = true;
		}
		return valid;
	}
	/**
	 * 查询不是自己id的其他服务单位id相同的
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 下午4:09:19
	 * @param meetingRoomInfo
	 * @return
	 */
	public List<MeetingRoomInfo> getListExcludeId(final MeetingRoomInfo meetingRoomInfo) {
		Order order = new Order(Direction.ASC, "sort");
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		Sort sort = new Sort(list);
		return meetingRoomInfoDao.findAll(new Specification<MeetingRoomInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingRoomInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
					pName = cb.equal(root.get("meetingRoomNo").as(String.class), meetingRoomInfo.getMeetingRoomNo());
					predicates.add(pName);
				if (!StringUtils.isBlank(meetingRoomInfo.getId())) {
					pName = cb.notEqual(root.get("id").as(String.class), meetingRoomInfo.getId());
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	/**
	 * TODO 查询会议室列表不分页(代替getList方法)
	 * @author 李颖洁  
	 * @date 2019年4月9日下午7:54:45
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public List<MeetingRoomInfo> getList1(MeetingRoomInfo meetingRoomInfo) {
		StringBuilder sql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sql.append("select t.id,t.meetingroom_no meetingRoomNo,t.meetingroom_name meetingRoomName,t.location,t.content,t.layout,m.isUse from hygl_meetingroom  t ");
		sql.append("	left join (select count(t.meetingroom_id) isUse,t.meetingroom_id from hygl_meetingroom_useinfo t "
				+ "left join hygl_meeting_apply a on t.apply_id = a.id  where a.subflag = '5' or a.subflag = '1'"
				+ " and t.visible = '"+CommonConstants.VISIBLE[1]+"' group by t.meetingroom_id) m on t.id = m.meetingroom_id ");
		sql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"' ");
		if (!StringUtils.isBlank(meetingRoomInfo.getMeetingRoomName())) {
			sql.append(" and t.meetingroom_name like ?");
			para.add("%"+meetingRoomInfo.getMeetingRoomName()+"%");
		}
		sql.append("  order by t.cre_time desc ");
		List<MeetingRoomInfo> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(MeetingRoomInfo.class),para.toArray());
		return list;
	}
}
