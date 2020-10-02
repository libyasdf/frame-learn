package com.sinosoft.sinoep.modules.system.config.utillink.service;

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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.system.config.utillink.dao.UtilLinkDao;
import com.sinosoft.sinoep.modules.system.config.utillink.entity.UtilLink;
import com.sinosoft.sinoep.modules.system.config.utillink.util.UtilLinkUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
public class UtilLinkServiceImpl implements UtilLinkService {
	
	@Autowired
	private UtilLinkDao utilLinkDao;
	
	
	
	/**
	 * 获取常用工具
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:59:47
	 * @param id
	 * @return
	 */
	@Override
	public UtilLink getById(String id) {
		return utilLinkDao.findOne(id);
	}
	
	/**
	 * 
	 * TODO 保存常用工具
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:13:31
	 * @param utilLink
	 * @return
	 */
	@Override
	public UtilLink saveForm(UtilLink utilLink) {
		utilLink.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(utilLink.getId())) {

			utilLink.setCreTime(creTime);
			utilLink.setCreUserId(userId);
			utilLink.setCreUserName(userName);
			// 更新最后修改记录
			utilLink.setUpdateTime(creTime);
			utilLink.setUpdateUserId(userId);
			utilLink.setUpdateUserName(userName);
			utilLink.setSort(UtilLinkUtil.getSort());
			utilLink = utilLinkDao.save(utilLink);
		} else {
			UtilLink oldUtilLink = utilLinkDao.findOne(utilLink.getId());
			oldUtilLink.setName(utilLink.getName());
			oldUtilLink.setPath(utilLink.getPath());
			oldUtilLink.setRemark(utilLink.getRemark());
			// 更新最后修改记录
			oldUtilLink.setUpdateTime(creTime);
			oldUtilLink.setUpdateUserId(userId);
			oldUtilLink.setUpdateUserName(userName);
			utilLinkDao.save(oldUtilLink);
		}
		return utilLink;
	}
	
	/**
	 * 
	 * TODO 查询常用工具列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param utilLink
	 * @return
	 */
	@Override
	public List<UtilLink> getList(final UtilLink utilLink) {
		Sort sort = new Sort("sort", "creTime");
		return utilLinkDao.findAll(new Specification<UtilLink>() {
			@Override
			public Predicate toPredicate(Root<UtilLink> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
				if (!StringUtils.isBlank(utilLink.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + utilLink.getName() + "%");
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
	/**
	 * 
	 * TODO 查询常用工具列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:17:44
	 * @param utilLink
	 * @return
	 */
	@Override
	public List<UtilLink> getListExcludeId(final UtilLink utilLink) {
		Sort sort = new Sort("sort", "creTime");
		return utilLinkDao.findAll(new Specification<UtilLink>() {
			@Override
			public Predicate toPredicate(Root<UtilLink> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null;
				if (!StringUtils.isBlank(utilLink.getName())) {
					pName = cb.equal(root.get("name").as(String.class), utilLink.getName());
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(utilLink.getId())) {
					pName = cb.notEqual(root.get("id").as(String.class), utilLink.getId());
					predicates.add(pName);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
	
	/**
	 * 
	 * TODO 删除用工具
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:56:42
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update UtilLink t set t.visible = ? where t.id = ?";
		return utilLinkDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 
	 * TODO 用工具排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:21:17
	 * @param ids
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			UtilLink utilLink = utilLinkDao.findOne(ids[i]);
			utilLink.setSort(i);
			utilLinkDao.save(utilLink);
		}
	}

}
