package com.sinosoft.sinoep.modules.system.config.applicationlink.service;

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
import com.sinosoft.sinoep.modules.system.config.applicationlink.dao.ApplicationDao;
import com.sinosoft.sinoep.modules.system.config.applicationlink.entity.Application;
import com.sinosoft.sinoep.modules.system.config.applicationlink.util.ApplicationUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 
 * TODO 导航Service
 * @author 冯超
 * @Date 2018年5月7日 上午11:48:54
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationDao applicationDao;
	
	
	
	/**
	 * 获取导航信息
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:59:47
	 * @param id
	 * @return
	 */
	@Override
	public Application getById(String id) {
		return applicationDao.findOne(id);
	}
	
	/**
	 * 
	 * TODO 保存导航信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:13:31
	 * @param application
	 * @return
	 */
	@Override
	public Application saveForm(Application application) {
		application.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(application.getId())) {

			application.setCreTime(creTime);
			application.setCreUserId(userId);
			application.setCreUserName(userName);
			// 更新最后修改记录
			application.setUpdateTime(creTime);
			application.setUpdateUserId(userId);
			application.setUpdateUserName(userName);
			application.setSort(ApplicationUtil.getSort());
			application = applicationDao.save(application);
		} else {
			Application oldApplication = applicationDao.findOne(application.getId());
			oldApplication.setName(application.getName());
			oldApplication.setUrl(application.getUrl());
			oldApplication.setPath(application.getPath());
			oldApplication.setRemark(application.getRemark());
			// 更新最后修改记录
			oldApplication.setUpdateTime(creTime);
			oldApplication.setUpdateUserId(userId);
			oldApplication.setUpdateUserName(userName);
			applicationDao.save(oldApplication);
		}
		return application;
	}
	
	/**
	 * 
	 * TODO 查询导航列表
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
		
		querySql.append("	from Application t ");
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
		
        Page<Application> page = applicationDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<Application> content = page.getContent();
        for (Application application : content) {
        	application.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 
	 * TODO 查询导航列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param application
	 * @return
	 */
	@Override
	public List<Application> getList(final Application application) {
		Order order = new Order(Direction.ASC, "sort");
		Order order1 = new Order(Direction.ASC, "creTime");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		Sort sort = new Sort(list);
		return applicationDao.findAll(new Specification<Application>() {
			@Override
			public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
				if (!StringUtils.isBlank(application.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + application.getName() + "%");
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
	/**
	 * 
	 * TODO 删除导航
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:56:42
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update Application t set t.visible = ? where t.id = ?";
		return applicationDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 
	 * TODO 导航排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:21:17
	 * @param ids
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Application application = applicationDao.findOne(ids[i]);
			application.setSort(i);
			applicationDao.save(application);
		}
	}
	

}
