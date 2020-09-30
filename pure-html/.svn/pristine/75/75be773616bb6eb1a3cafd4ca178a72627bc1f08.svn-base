package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.dao.DataTableDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.DataTable;
import com.sinosoft.sinoep.user.util.UserUtil;


@Service
@Transactional
public class DataTableServiceImpl implements DataTableService {
	@Autowired
	private DataTableDao dao;
	
	@Override
	public Page<DataTable> list(final DataTable dt,final PageImpl pageImpl) throws Exception {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		return dao.findAll(new Specification<DataTable>() {
			@Override
			public Predicate toPredicate(Root<DataTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType =  cb.equal(root.get("type").as(String.class), dt.getType());
				//Predicate pcreJuId =  cb.equal(root.get("creJuId").as(String.class), dt.getCreJuId());
				predicates.add(pVisible);
				predicates.add(pType);
				//资料的维护和学习不需要添加juid的过滤，否则其他二级局看不到维护的资料
				//predicates.add(pcreJuId);
				Predicate dataName = null,nodeId=null,subflag;
				Order order=null;
				if (!StringUtils.isBlank(dt.getDataName())){
					dataName = cb.like(root.get("dataName").as(String.class), "%"+dt.getDataName()+"%");
					predicates.add(dataName);
				}
				if (!StringUtils.isBlank(dt.getNodeId())){
					nodeId = cb.equal(root.get("nodeId").as(String.class), dt.getNodeId());
					predicates.add(nodeId);
				}
				if (!StringUtils.isBlank(dt.getSubflag())){
					subflag = cb.equal(root.get("subflag").as(String.class), dt.getSubflag());
					predicates.add(subflag);
				}
				// 拼接排序语句
				if (StringUtils.isBlank(pageImpl.getSortName())) {
					order = cb.desc(root.get("creTime").as(String.class));
				} else {
					if(null != pageImpl.getSortOrder() && "asc".equals(pageImpl.getSortOrder().toLowerCase()) ){
						order = cb.asc(root.get(pageImpl.getSortName()).as(String.class));
					}else if(null != pageImpl.getSortOrder() && "desc".equals(pageImpl.getSortOrder().toLowerCase()) ){
						order = cb.desc(root.get(pageImpl.getSortName()).as(String.class));
					}
				}
				query.where(cb.and(predicates.toArray(new Predicate[0])));
				query.orderBy(order);
				return query.getRestriction();
			}
		},pageable);

		}
	

	@Override
	public DataTable save(DataTable dt) {
		
		dt.setCreDeptId(UserUtil.getCruDeptId());
		dt.setCreDeptName(UserUtil.getCruDeptName());
		dt.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dt.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dt.setCreDeptId(UserUtil.getCruUserId());
		dt.setCreDeptName(UserUtil.getCruUserName());
		dt.setUpdateUserId(UserUtil.getCruUserId());
		dt.setUpdateUserName(UserUtil.getCruUserName());
		dt.setCreUserName(UserUtil.getCruUserName());
		dt.setCreUserId(UserUtil.getCruUserId());
		dt.setVisible(CommonConstants.VISIBLE[1]);
		dt.setCreJuId(UserUtil.getCruJuId());
		dt.setCreJuName(UserUtil.getCruJuName());
		return dao.saveAndFlush(dt);
	}

	@Override
	public DataTable upDataTable(DataTable dt) {
		DataTable dataTable = dao.findOne(dt.getId());
		dataTable.setDataName(dt.getDataName());
		dataTable.setIsWillLearn(dt.getIsWillLearn());
		dataTable.setLatestStudyDate(dt.getLatestStudyDate());
		dataTable.setMinimumLearningTime(dt.getMinimumLearningTime());
		dataTable.setNodeId(dt.getNodeId());
		dataTable.setContext(dt.getContext());
		dataTable.setCreUserId(dt.getCreUserId());
		dataTable.setCreUserName(dt.getCreUserName());
		dataTable.setVideo(dt.getVideo());
		dataTable.setType(dt.getType());
		dataTable.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dataTable.setUpdateUserId(UserUtil.getCruUserId());
		dataTable.setUpdateUserName(UserUtil.getCruUserName());
		return dao.saveAndFlush(dataTable);
	}

	@Override
	public int delete(String id) {
		String jpql = "update DataTable t  set t.visible = ? where t.id = ?";
		int update = dao.update(jpql, CommonConstants.VISIBLE[0],id);
		return update;
	}

	@Override
	public DataTable getOne(String id) {
		// TODO Auto-generated method stub
		
		return dao.findOne(id);
	}
	
	
}
