package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.service;

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
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.dao.MeetingServiceDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.service.MeetingServiceService;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.dao.ServiceDeptDao;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.entity.ServiceDeptInfo;
import com.sinosoft.sinoep.user.util.UserUtil;
/**
 * 会务服务单位信息
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:42:05
 */
@Service
public class ServiceDeptServiceImp implements ServiceDeptService{
	
	// 服务单位
	@Autowired
	private ServiceDeptDao serviceDeptDao;
	// 服务项
	@Autowired
	private MeetingServiceDao meetingServiceDao;
	
	@Autowired
	private MeetingServiceService meetingServiceService;
	
	/**
	 * 获取会议室信息
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:59:47
	 * @param id
	 * @return
	 */
	@Override
	public ServiceDeptInfo getById(String id) {
		return serviceDeptDao.findOne(id);
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
	public ServiceDeptInfo saveForm(ServiceDeptInfo serviceDeptInfo) {
		serviceDeptInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(serviceDeptInfo.getId())) {

			serviceDeptInfo.setCreTime(creTime);
			serviceDeptInfo.setCreUserId(userId);
			serviceDeptInfo.setCreUserName(userName);
			// 更新最后修改记录
			serviceDeptInfo.setUpdateTime(creTime);
			serviceDeptInfo.setUpdateUserId(userId);
			serviceDeptInfo.setUpdateUserName(userName);
			serviceDeptInfo = serviceDeptDao.save(serviceDeptInfo);
		} else {
			ServiceDeptInfo oldServiceDeptInfo = serviceDeptDao.findOne(serviceDeptInfo.getId());
			oldServiceDeptInfo.setRemark(serviceDeptInfo.getRemark());
			// 更新最后修改记录
			oldServiceDeptInfo.setUpdateTime(creTime);
			oldServiceDeptInfo.setUpdateUserId(userId);
			oldServiceDeptInfo.setUpdateUserName(userName);
			serviceDeptDao.save(oldServiceDeptInfo);
		}
		return serviceDeptInfo;
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
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String serviceDeptName) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from ServiceDeptInfo t ");
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
		
        Page<ServiceDeptInfo> page = serviceDeptDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<ServiceDeptInfo> content = page.getContent();
        for (ServiceDeptInfo serviceDeptInfo : content) {
        	serviceDeptInfo.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        	MeetingServiceInfo meetingServiceInfoPara = new MeetingServiceInfo();
        	
        	meetingServiceInfoPara.setServiceDeptId(serviceDeptInfo.getId());
        	List<MeetingServiceInfo> meetingServiceList = getMeetingServiceInfoList(meetingServiceInfoPara);
        	StringBuilder meetingService = new StringBuilder();
        	for(MeetingServiceInfo meetingServiceInfo : meetingServiceList){
        		meetingService.append(meetingServiceInfo.getMeetingService()+"，");
        	}
        	if(meetingService.length()!=0){
        		meetingService.deleteCharAt(meetingService.length()-1);
        	}else{
        		meetingService.append("-");
        	}
        	serviceDeptInfo.setMeetingServices(meetingService.toString());
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
	public List<ServiceDeptInfo> getList(final ServiceDeptInfo serviceDeptInfo) {
		Order order = new Order(Direction.ASC, "sort");
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		Sort sort = new Sort(list);
		return serviceDeptDao.findAll(new Specification<ServiceDeptInfo>() {
			@Override
			public Predicate toPredicate(Root<ServiceDeptInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
				if (!StringUtils.isBlank(serviceDeptInfo.getServiceDeptName())) {
					pName = cb.like(root.get("serviceDeptName").as(String.class), "%" + serviceDeptInfo.getServiceDeptName() + "%");
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
		//删除子表
		meetingServiceService.deleteByServiceDeptID(id);
		//删除父表
		String jpql = "update ServiceDeptInfo t set t.visible = ? where t.id = ?";
		return serviceDeptDao.update(jpql, CommonConstants.VISIBLE[0], id);
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
			ServiceDeptInfo serviceDeptInfo = serviceDeptDao.findOne(ids[i]);
			serviceDeptInfo.setSort(i);
			serviceDeptDao.save(serviceDeptInfo);
		}
	}
	
	/**
	 * 根据服务单位id获取服务项目
	 * TODO 
	 * @author 冯超
	 * @Date 2018年7月23日 下午2:24:14
	 * @param meetingServiceInfo
	 * @return
	 */
	public List<MeetingServiceInfo> getMeetingServiceInfoList(final MeetingServiceInfo meetingServiceInfo) {
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order1);
		Sort sort = new Sort(list);
		return meetingServiceDao.findAll(new Specification<MeetingServiceInfo>() {
			@Override
			public Predicate toPredicate(Root<MeetingServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
				if (!StringUtils.isBlank(meetingServiceInfo.getServiceDeptId())){
					pName = cb.equal(root.get("serviceDeptId").as(String.class), meetingServiceInfo.getServiceDeptId());
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
	/**
	 * 判断加的部门是否重复
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 下午2:08:38
	 * @param dic
	 * @param checkItem
	 * @return
	 */
	@Override
	public boolean checkSdi(ServiceDeptInfo serviceDeptInfo){
		//重复：false  不重复：true
		boolean valid = false;
		//判断是否重复
		List<ServiceDeptInfo> serviceDeptInfoList = getListExcludeId(serviceDeptInfo);
		if(0 == serviceDeptInfoList.size()){
			valid = true;
		}
		return valid;
	}
	
	//查询不是自己id的其他服务单位id相同的
	public List<ServiceDeptInfo> getListExcludeId(final ServiceDeptInfo serviceDeptInfo) {
		Order order = new Order(Direction.ASC, "sort");
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		Sort sort = new Sort(list);
		return serviceDeptDao.findAll(new Specification<ServiceDeptInfo>() {
			@Override
			public Predicate toPredicate(Root<ServiceDeptInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
					pName = cb.equal(root.get("serviceDeptId").as(String.class), serviceDeptInfo.getServiceDeptId());
					predicates.add(pName);
				if (!StringUtils.isBlank(serviceDeptInfo.getId())) {
					pName = cb.notEqual(root.get("id").as(String.class), serviceDeptInfo.getId());
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
}
