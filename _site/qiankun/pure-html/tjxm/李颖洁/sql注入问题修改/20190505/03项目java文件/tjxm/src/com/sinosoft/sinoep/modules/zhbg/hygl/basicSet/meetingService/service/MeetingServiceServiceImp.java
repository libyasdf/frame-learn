package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.service;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.dao.MeetingServiceDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingDeptAndServices;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.entity.ServiceDeptInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.service.ServiceDeptService;
import com.sinosoft.sinoep.user.util.UserUtil;
/**
 * 会务服务项管理
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:42:05
 */
@Service
public class MeetingServiceServiceImp implements MeetingServiceService{
	
	@Autowired
	private MeetingServiceDao meetingServiceDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ServiceDeptService serviceDeptService;
	
	/**
	 * 获取会务服务项信息
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:59:47
	 * @param id
	 * @return
	 */
	@Override
	public MeetingServiceInfo getById(String id) {
		return meetingServiceDao.findOne(id);
	}
	
	/**
	 * 
	 * TODO 保存会务服务项信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:13:31
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public MeetingDeptAndServices saveForm(MeetingDeptAndServices meetingDeptAndServices) {
		String id = meetingDeptAndServices.getPid();
		String serviceDeptId = meetingDeptAndServices.getServiceDeptId();
		String serviceDeptName = meetingDeptAndServices.getServiceDeptName();
		//保存主表
		ServiceDeptInfo serviceDeptInfo = new ServiceDeptInfo();
		serviceDeptInfo.setId(id);
		serviceDeptInfo.setServiceDeptId(serviceDeptId);
		serviceDeptInfo.setServiceDeptName(serviceDeptName);
		serviceDeptInfo = serviceDeptService.saveForm(serviceDeptInfo);
		
		// 遍历字表
		for(MeetingServiceInfo meetingServiceInfo : meetingDeptAndServices.getMeetingServiceList()){
			meetingServiceInfo.setId(meetingServiceInfo.getMeetingServiceId());
			meetingServiceInfo.setServiceDeptId(serviceDeptInfo.getId());
			saveMeetingServiceInfo(meetingServiceInfo);
		}
		//获取最新的数据
		MeetingServiceInfo meetingServiceInfoPara = new MeetingServiceInfo();
		meetingServiceInfoPara.setServiceDeptId(serviceDeptInfo.getId());
		List<MeetingServiceInfo> meetingServiceInfoList = getList(meetingServiceInfoPara);
		for(MeetingServiceInfo meetingServiceInfo : meetingServiceInfoList){
			meetingServiceInfo.setResponsibleUserDeptId(serviceDeptId);
		}
		meetingDeptAndServices.setPid(serviceDeptInfo.getId()); 
		meetingDeptAndServices.setMeetingServiceList(meetingServiceInfoList);
		
		return meetingDeptAndServices;
	}
	
	/**
	 * 
	 * TODO 查询会务服务项列表
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:32:50
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String serviceDeptName) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from MeetingServiceInfo t ");
		querySql.append("	where  t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		if (StringUtils.isNotBlank(serviceDeptName)) {
			querySql.append("   and t.serviceDeptName like ?");
			para.add("%"+serviceDeptName+"%");
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<MeetingServiceInfo> page = meetingServiceDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<MeetingServiceInfo> content = page.getContent();
        for (MeetingServiceInfo meetingServiceInfo : content) {
        	meetingServiceInfo.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 
	 * TODO 查询会务服务项列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param meetingRoomInfo
	 * @return
	 */
	@Override
	public List<MeetingServiceInfo> getList(final MeetingServiceInfo meetingServiceInfo) {
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		Sort sort = new Sort(list);
		List<MeetingServiceInfo> meetingServiceInfoList =  meetingServiceDao.findAll(new Specification<MeetingServiceInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
					pName = cb.equal(root.get("serviceDeptId").as(String.class), meetingServiceInfo.getServiceDeptId());
					predicates.add(pName);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
		//将负责人部门放进列表
		for(MeetingServiceInfo meetingService : meetingServiceInfoList){
			meetingService.setResponsibleUserDeptId(meetingServiceInfo.getResponsibleUserDeptId());
		}
		return meetingServiceInfoList;
	}
	
	/**
	 * 查询所有的会务服务（会议申请加载会务服务项）
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午10:04:00
	 * @param meetingServiceInfo
	 * @return
	 */
	@Override
	public List<MeetingServiceInfo> getAllList(final MeetingServiceInfo meetingServiceInfo) {
		Order order1 = new Order(Direction.ASC, "serviceDeptId");
		Order order2 = new Order(Direction.ASC, "responsibleUserId");
		Order order3 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		list.add(order2);
		list.add(order3);
		Sort sort = new Sort(list);
		return  meetingServiceDao.findAll(new Specification<MeetingServiceInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	/**
	 * 根据会务服务负责人查询所有的会务服务（会议申请加载会务服务项）
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午10:04:00
	 * @param meetingServiceInfo
	 * @return
	 */
	@Override
	public List<MeetingServiceInfo> getListByUser(final MeetingServiceInfo meetingServiceInfo) {
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		Sort sort = new Sort(list);
		return  meetingServiceDao.findAll(new Specification<MeetingServiceInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pUserId = null;
				pUserId = cb.equal(root.get("responsibleUserId").as(String.class), UserUtil.getCruUserId());
				predicates.add(pUserId);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	/**
	 * 
	 * TODO 删除会务服务项
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:56:42
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update MeetingServiceInfo t set t.visible = ? where t.id = ?";
		return meetingServiceDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 批量删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月2日 上午10:32:43
	 * @param id
	 * @return
	 */
	@Override
	public int deleteIds(String ids) {
		List<Object> para = new ArrayList<>();
		String inString = CommonUtils.solveSqlInjectionOfIn(ids, para);
		String jpql = "update HYGL_MEETING_SERVICE t set t.visible = '" + CommonConstants.VISIBLE[0] + "' where t.id in (" + inString + ")";
		return jdbcTemplate.update(jpql,para.toArray());
	}
	
	/**
	 * 根据外键 会务服务单位删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 上午11:01:48
	 * @param serviceDeptId
	 * @return
	 */
	@Override
	public int deleteByServiceDeptID(String serviceDeptId){
		String jpql = "update MeetingServiceInfo t set t.visible = ? where t.serviceDeptId = ?";
		return meetingServiceDao.update(jpql, CommonConstants.VISIBLE[0], serviceDeptId);
	}
	
	/**
	 * 
	 * TODO 会务服务项排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:21:17
	 * @param ids
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MeetingServiceInfo meetingServiceInfo = meetingServiceDao.findOne(ids[i]);
			//meetingServiceInfo.setSort(i);
			meetingServiceDao.save(meetingServiceInfo);
		}
	}
	
	//保存会务服务信息
	public void saveMeetingServiceInfo(MeetingServiceInfo meetingServiceInfo) {
		meetingServiceInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(meetingServiceInfo.getId())) {

			meetingServiceInfo.setCreTime(creTime);
			meetingServiceInfo.setCreUserId(userId);
			meetingServiceInfo.setCreUserName(userName);
			// 更新最后修改记录
			meetingServiceInfo.setUpdateTime(creTime);
			meetingServiceInfo.setUpdateUserId(userId);
			meetingServiceInfo.setUpdateUserName(userName);
			meetingServiceInfo = meetingServiceDao.save(meetingServiceInfo);
		} else {
			MeetingServiceInfo oldMeetingServiceInfo = meetingServiceDao.findOne(meetingServiceInfo.getId());
			oldMeetingServiceInfo.setRemark(meetingServiceInfo.getRemark());
			// 更新最后修改记录
			oldMeetingServiceInfo.setUpdateTime(creTime);
			oldMeetingServiceInfo.setUpdateUserId(userId);
			oldMeetingServiceInfo.setUpdateUserName(userName);
			oldMeetingServiceInfo.setResponsibleUserId(meetingServiceInfo.getResponsibleUserId());
			oldMeetingServiceInfo.setResponsibleUserName(meetingServiceInfo.getResponsibleUserName());
			oldMeetingServiceInfo.setResponsibleUserTelephone(meetingServiceInfo.getResponsibleUserTelephone());
			oldMeetingServiceInfo.setMeetingService(meetingServiceInfo.getMeetingService());
			oldMeetingServiceInfo.setResponsibleDeptId(meetingServiceInfo.getResponsibleDeptId());
			meetingServiceDao.save(oldMeetingServiceInfo);
		}
	}
	
}
