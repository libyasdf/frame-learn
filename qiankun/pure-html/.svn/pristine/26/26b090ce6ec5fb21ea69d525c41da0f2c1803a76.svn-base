package com.key.authority.services;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.key.authority.common.AuthConstants;
import com.key.authority.dao.AuthDeptDao;
import com.key.authority.dao.AuthDeptZwqxDao;
import com.key.authority.dao.AuthDeptZwqxListDao;
import com.key.authority.dao.AuthZwqxDao;
import com.key.authority.entity.AuthDept;
import com.key.authority.entity.AuthDeptZwqx;
import com.key.authority.entity.AuthDeptZwqxList;
import com.key.authority.entity.AuthZwqx;
import com.key.authority.entity.AuthModel;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 信息发布职务权限业务类
 * @author 李利广
 * @Date 2018年9月15日 下午5:38:57
 */
@Service
public class AuthZwqxServiceImpl implements AuthZwqxService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthDeptDao deptDao;
	
	@Autowired
	private AuthZwqxDao zwqxDao;
	
	@Autowired
	private AuthDeptZwqxDao deptZwqxDao;
	
	@Autowired
	private AuthDeptZwqxListDao deptZwqxListDao;

	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	@Override
	public AuthModel saveInfoZwqx(AuthModel model) {
		//保存之前先判断是否已经存在权限
		String surveyId = model.getSurveyId();
		AuthModel authority = new AuthModel();
		if(StringUtils.isNotBlank(surveyId)){
			authority= getAuthority(surveyId);
		}
		if(authority.getDeptList().isEmpty() && authority.getZwqxList().isEmpty() && authority.getDeptZwqxList().isEmpty()){
			//三个集合都是空的，直接保存
			saveAuth(model);
		}else{
			//权限已存在，先删除，然后重新保存
			if(delOldAuth(authority)){
				saveAuth(model);
			}
		}
		return model;
	}
	
	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:43:12
	 * @param model
	 * @return
	 */
	private AuthModel saveAuth(AuthModel model){
		//保存部门权限
		List<AuthDept> deptList = saveDept(model);
		//保存职务权限
		List<AuthZwqx> zwqxList = saveZwqx(model);
		//保存部门+职务list权限
		model = saveDeptZwqxList(model);
		model.setDeptList(deptList);
		model.setZwqxList(zwqxList);
		return model;
	}
	
	/**
	 * 保存部门权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:25:57
	 * @param model
	 * @return
	 */
	private List<AuthDept> saveDept(AuthModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<AuthDept> deptList = model.getDeptList();
		if (deptList != null && deptList.size() > 0) {
			for (AuthDept dept : deptList) {
				dept.setCreTime(creTime);
				dept.setCreUserId(UserUtil.getCruUserId());
				dept.setCreUserName(UserUtil.getCruUserName());
				dept.setVisible(AuthConstants.VISIBLE[1]);
			}
			deptList = deptDao.save(deptList);
		}
		return deptList;
	}
	
	/**
	 * 保存职务权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:26:58
	 * @param model
	 * @return
	 */
	private List<AuthZwqx> saveZwqx(AuthModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<AuthZwqx> zwqxList = model.getZwqxList();
		if (zwqxList != null && zwqxList.size() > 0) {
			for (AuthZwqx zwqx : zwqxList) {
				zwqx.setCreTime(creTime);
				zwqx.setCreUserId(UserUtil.getCruUserId());
				zwqx.setCreUserName(UserUtil.getCruUserName());
				zwqx.setVisible(AuthConstants.VISIBLE[1]);
			}
			zwqxList = zwqxDao.save(zwqxList);
		}
		return zwqxList;
	}
	
	/**
	 * 保存部门+职务权限list
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:28:51
	 * @param model
	 * @return
	 */
	private AuthModel saveDeptZwqxList(AuthModel model){
		List<AuthDeptZwqxList> deptZwqxList = model.getDeptZwqxList();
		List<AuthDeptZwqx> deptZwqxListList = model.getDeptZwqxListList();
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (deptZwqxList != null && deptZwqxList.size() > 0 && deptZwqxListList != null && deptZwqxListList.size() > 0) {
			for (AuthDeptZwqxList deptZwqxLi : deptZwqxList) {
				deptZwqxLi.setCreTime(creTime);
				deptZwqxLi.setCreUserId(UserUtil.getCruUserId());
				deptZwqxLi.setCreUserName(UserUtil.getCruUserName());
				deptZwqxLi.setVisible(AuthConstants.VISIBLE[1]);
			}
			deptZwqxList = deptZwqxListDao.save(deptZwqxList);
			//保存部门+职务权限
			//deptZwqxList = saveInfoFbDeptZwqx(deptZwqxListList,deptZwqxList);
			for (AuthDeptZwqxList deptZwqxLis : deptZwqxList) {
				String deptZwqxListId = deptZwqxLis.getId();
				String line = deptZwqxLis.getLine();
				for (AuthDeptZwqx deptZwqx : deptZwqxListList) {
					if (deptZwqx.getLine().equals(line)) {
						deptZwqx.setCreTime(creTime);
						deptZwqx.setCreUserId(UserUtil.getCruUserId());
						deptZwqx.setCreUserName(UserUtil.getCruUserName());
						deptZwqx.setDeptZwqxListId(deptZwqxListId);
						deptZwqx.setVisible(AuthConstants.VISIBLE[1]);
					}
				}
			}
			deptZwqxListList = deptZwqxDao.save(deptZwqxListList);
		}
		model.setDeptZwqxListList(deptZwqxListList);
		model.setDeptZwqxList(deptZwqxList);
		return model;
	}
	
	/**
	 * 删除原有权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:34:18
	 * @param authority
	 * @return
	 */
	private boolean delOldAuth(AuthModel authority){
		boolean del = false;
		try {
			deptDao.delete(authority.getDeptList());
			zwqxDao.delete(authority.getZwqxList());
			deptZwqxDao.delete(authority.getDeptZwqxListList());
			deptZwqxListDao.delete(authority.getDeptZwqxList());
			del = true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return del;
	}
	
	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param surveyId
	 * @return
	 */
	@Override
	public AuthModel getAuthority(String surveyId) {
		AuthModel model = new AuthModel();
		//查询部门权限
		StringBuilder deptHql = new StringBuilder("from AuthDept t where t.surveyId = ? ");
		TypedQuery<AuthDept> deptQuery = deptDao.getEntityManager().createQuery(deptHql.toString(), AuthDept.class);
		deptQuery.setParameter(1, surveyId);
		List<AuthDept> deptList = deptQuery.getResultList();
		model.setDeptList(deptList);
		//查询职务权限
		StringBuilder zwqxHql = new StringBuilder("from AuthZwqx t where t.surveyId = ? ");
		TypedQuery<AuthZwqx> zwqxQuery = zwqxDao.getEntityManager().createQuery(zwqxHql.toString(), AuthZwqx.class);
		zwqxQuery.setParameter(1, surveyId);
		List<AuthZwqx> zwqxList = zwqxQuery.getResultList();
		model.setZwqxList(zwqxList);
		//查询部门+职务权限
		StringBuilder deptZwqxHql = new StringBuilder("from AuthDeptZwqxList t where t.surveyId = ? ");
		TypedQuery<AuthDeptZwqxList> deptZwqxQuery = deptZwqxListDao.getEntityManager().createQuery(deptZwqxHql.toString(), AuthDeptZwqxList.class);
		deptZwqxQuery.setParameter(1, surveyId);
		List<AuthDeptZwqxList> deptZwqxList = deptZwqxQuery.getResultList();
		model.setDeptZwqxList(deptZwqxList);
		//查询部门+职务list权限
		StringBuilder deptZwqxListHql = new StringBuilder("from AuthDeptZwqx t where t.surveyId = ? ");
		TypedQuery<AuthDeptZwqx> AuthDeptZwqxQuery = deptZwqxDao.getEntityManager().createQuery(deptZwqxListHql.toString(), AuthDeptZwqx.class);
		AuthDeptZwqxQuery.setParameter(1, surveyId);
		List<AuthDeptZwqx> deptZwqxListList = AuthDeptZwqxQuery.getResultList();
		model.setDeptZwqxListList(deptZwqxListList);
		return model;
	}

}
