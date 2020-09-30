package com.sinosoft.sinoep.modules.info.authority.services;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.fr.fs.base.entity.User;
import com.sinosoft.sinoep.modules.info.authority.dao.*;
import com.sinosoft.sinoep.modules.info.authority.entity.*;
import com.sinosoft.sinoep.modules.system.notice.dao.InfoFBUserGroupDao;
import com.sinosoft.sinoep.modules.system.notice.entity.InfoFBUserGroup;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.info.authority.common.InfoFbConstants;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 信息发布职务权限业务类
 * @author 李利广
 * @Date 2018年9月15日 下午5:38:57
 */
@Service
public class InfoFbZwqxServiceImpl implements InfoFbZwqxService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InfoFbDeptDao deptDao;

	@Autowired
	private InfoFbGroupDao groupDao;
	
	@Autowired
	private InfoFbZwqxDao zwqxDao;
	
	@Autowired
	private InfoFbDeptZwqxDao deptZwqxDao;
	
	@Autowired
	private InfoFbDeptZwqxListDao deptZwqxListDao;

	@Autowired
	private ColumnFbDpetDao columnDeptDao;

	@Autowired
	private ColumnFbZwqxDao columnZwqxDao;

	@Autowired
	private ColumnFbDeptZwqxDao columnDeptZwqxDao;

	@Autowired
	private ColumnFbDeptZwqxListDao columnDeptZwqxListDao;

	@Autowired
	private ColumnFbGroupDao columnFbGroupDao;

	@Autowired
	private InfoFBUserGroupDao infoFBUserGroupDao;

	@Autowired
	private InfoGenaralsDao infoGenaralsDao;



	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	@Override
	public InfoModel saveInfoZwqx(InfoModel model) {
		//保存之前先判断是否已经存在权限
		String contentId = model.getContentId();
		InfoModel authority = new InfoModel();
		if(StringUtils.isNotBlank(contentId)){
			authority= getAuthority(contentId);
		}
		if(authority.getDeptList().isEmpty() && authority.getGroupList().isEmpty() && authority.getZwqxList().isEmpty() && authority.getDeptZwqxList().isEmpty() && authority.getUserGroupList().isEmpty()){
			//四个集合都是空的，直接保存
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
	private InfoModel saveAuth(InfoModel model){
		//保存部门权限
		List<InfoFbDept> deptList = saveDept(model);
		//保存群组权限
		List<InfoFbGroup> groupList = saveGroup(model);
		//保存职务权限
		List<InfoFbZwqx> zwqxList = saveZwqx(model);
		//保存部门+职务list权限
		model = saveDeptZwqxList(model);
		//保存人群权限
		List<InfoFBUserGroup> userGroupList= saveUserGroup(model);
		model.setDeptList(deptList);
		model.setGroupList(groupList);
		model.setZwqxList(zwqxList);
		model.setUserGroupList(userGroupList);
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
	private List<InfoFbDept> saveDept(InfoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<InfoFbDept> deptList = model.getDeptList();
		if (deptList != null && deptList.size() > 0) {
			for (InfoFbDept dept : deptList) {
				dept.setCreTime(creTime);
				dept.setCreUserId(UserUtil.getCruUserId());
				dept.setCreUserName(UserUtil.getCruUserName());
				dept.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			deptList = deptDao.save(deptList);
		}
		return deptList;
	}

	/**
	 * 保存群组权限
	 * TODO
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:25:57
	 * @param model
	 * @return
	 */
	private List<InfoFbGroup> saveGroup(InfoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<InfoFbGroup> groupList = model.getGroupList();
		if (groupList != null && groupList.size() > 0) {
			for (InfoFbGroup group : groupList) {
				group.setCreTime(creTime);
				group.setCreUserId(UserUtil.getCruUserId());
				group.setCreUserName(UserUtil.getCruUserName());
				group.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			groupList = groupDao.save(groupList);
		}
		return groupList;
	}

	/**
	 * 保存人群权限
	 * TODO
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:25:57
	 * @param model
	 * @return
	 */
	private List<InfoFBUserGroup> saveUserGroup(InfoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<InfoFBUserGroup> userGroupList = model.getUserGroupList();
		if (userGroupList != null && userGroupList.size() > 0) {
			for (InfoFBUserGroup userGroup : userGroupList) {
				userGroup.setCreTime(creTime);
				userGroup.setCreUserId(UserUtil.getCruUserId());
				userGroup.setCreUserName(UserUtil.getCruUserName());
				userGroup.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			userGroupList = infoFBUserGroupDao.save(userGroupList);
		}
		return userGroupList;
	}

	/**
	 * 保存职务权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:26:58
	 * @param model
	 * @return
	 */
	private List<InfoFbZwqx> saveZwqx(InfoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<InfoFbZwqx> zwqxList = model.getZwqxList();
		if (zwqxList != null && zwqxList.size() > 0) {
			for (InfoFbZwqx zwqx : zwqxList) {
				zwqx.setCreTime(creTime);
				zwqx.setCreUserId(UserUtil.getCruUserId());
				zwqx.setCreUserName(UserUtil.getCruUserName());
				zwqx.setVisible(InfoFbConstants.VISIBLE[1]);
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
	private InfoModel saveDeptZwqxList(InfoModel model){
		List<InfoFbDeptZwqxList> deptZwqxList = model.getDeptZwqxList();
		List<InfoFbDeptZwqx> deptZwqxListList = model.getDeptZwqxListList();
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (deptZwqxList != null && deptZwqxList.size() > 0 && deptZwqxListList != null && deptZwqxListList.size() > 0) {
			for (InfoFbDeptZwqxList deptZwqxLi : deptZwqxList) {
				deptZwqxLi.setCreTime(creTime);
				deptZwqxLi.setCreUserId(UserUtil.getCruUserId());
				deptZwqxLi.setCreUserName(UserUtil.getCruUserName());
				deptZwqxLi.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			deptZwqxList = deptZwqxListDao.save(deptZwqxList);
			//保存部门+职务权限
			//deptZwqxList = saveInfoFbDeptZwqx(deptZwqxListList,deptZwqxList);
			for (InfoFbDeptZwqxList deptZwqxLis : deptZwqxList) {
				String deptZwqxListId = deptZwqxLis.getId();
				String line = deptZwqxLis.getLine();
				for (InfoFbDeptZwqx deptZwqx : deptZwqxListList) {
					if (deptZwqx.getLine().equals(line)) {
						deptZwqx.setCreTime(creTime);
						deptZwqx.setCreUserId(UserUtil.getCruUserId());
						deptZwqx.setCreUserName(UserUtil.getCruUserName());
						deptZwqx.setDeptZwqxListId(deptZwqxListId);
						deptZwqx.setVisible(InfoFbConstants.VISIBLE[1]);
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
	private boolean delOldAuth(InfoModel authority){
		boolean del = false;
		try {
			deptDao.delete(authority.getDeptList());
			zwqxDao.delete(authority.getZwqxList());
			deptZwqxDao.delete(authority.getDeptZwqxListList());
			deptZwqxListDao.delete(authority.getDeptZwqxList());
			groupDao.delete(authority.getGroupList());
			infoFBUserGroupDao.delete(authority.getUserGroupList());
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
	 * @param contentId
	 * @return
	 */
	@Override
	public InfoModel getAuthority(String contentId) {
		InfoModel model = new InfoModel();
		//查询部门权限
		StringBuilder deptHql = new StringBuilder("from InfoFbDept t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		TypedQuery<InfoFbDept> deptQuery = deptDao.getEntityManager().createQuery(deptHql.toString(), InfoFbDept.class);
		List<InfoFbDept> deptList = deptQuery.getResultList();
		model.setDeptList(deptList);
		//查询职务权限
		StringBuilder zwqxHql = new StringBuilder("from InfoFbZwqx t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<InfoFbZwqx> zwqxList = zwqxDao.getEntityManager().createQuery(zwqxHql.toString(), InfoFbZwqx.class).getResultList();
		model.setZwqxList(zwqxList);
		//查询部门+职务权限
		StringBuilder deptZwqxHql = new StringBuilder("from InfoFbDeptZwqxList t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<InfoFbDeptZwqxList> deptZwqxList = deptZwqxListDao.getEntityManager().createQuery(deptZwqxHql.toString(), InfoFbDeptZwqxList.class).getResultList();
		model.setDeptZwqxList(deptZwqxList);
		//查询部门+职务list权限
		StringBuilder deptZwqxListHql = new StringBuilder("from InfoFbDeptZwqx t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<InfoFbDeptZwqx> deptZwqxListList = deptZwqxDao.getEntityManager().createQuery(deptZwqxListHql.toString(), InfoFbDeptZwqx.class).getResultList();
		model.setDeptZwqxListList(deptZwqxListList);
		//查询群组权限list
		StringBuilder groupListHql = new StringBuilder("from InfoFbGroup t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<InfoFbGroup> groupList = groupDao.getEntityManager().createQuery(groupListHql.toString(), InfoFbGroup.class).getResultList();
		model.setGroupList(groupList);
		//查询人组权限list
		StringBuilder userGroupListHql = new StringBuilder("from InfoFBUserGroup t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<InfoFBUserGroup> userGroupList = groupDao.getEntityManager().createQuery(userGroupListHql.toString(), InfoFBUserGroup.class).getResultList();
		model.setUserGroupList(userGroupList);
		return model;
	}

	/**
	 * 保存栏目权限
	 * @param model
	 * @return
	 */
	@Override
	public ColumnModel saveColumnFbfw(ColumnModel model) {
		//保存之前先判断是否已经存在权限
		String columnId = model.getColumnId();
		ColumnModel columnAuthority = new ColumnModel();
		if(StringUtils.isNotBlank(columnId)) {
			columnAuthority = getColumnAuthority(columnId);
		}
		if(columnAuthority.getDeptList().isEmpty() && columnAuthority.getZwqxList().isEmpty() && columnAuthority.getDeptZwqxList().isEmpty() && columnAuthority.getGroupList().isEmpty()){
			//三个集合都是空的，直接保存
			saveColumnAuth(model);
		}else{
			//权限已存在，先删除，然后重新保存
			if(delOldColumnAuth(columnAuthority)){
				saveColumnAuth(model);
			}
		}
		return model;
	}

	/**
	 * 获取栏目权限
	 * @param columnId
	 * @return
	 */
	@Override
	public ColumnModel getColumnAuthority(String columnId) {
		ColumnModel model = new ColumnModel();
		//查询部门权限
		StringBuilder deptHql = new StringBuilder("from ColumnFbDept t where t.columnId = '"+columnId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		TypedQuery<ColumnFbDept> deptQuery = columnDeptDao.getEntityManager().createQuery(deptHql.toString(), ColumnFbDept.class);
		List<ColumnFbDept> deptList = deptQuery.getResultList();
		model.setDeptList(deptList);
		StringBuilder zwqxHql = new StringBuilder("from ColumnFbZwqx t where t.columnId = '"+columnId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<ColumnFbZwqx> zwqxList = columnZwqxDao.getEntityManager().createQuery(zwqxHql.toString(), ColumnFbZwqx.class).getResultList();
		model.setZwqxList(zwqxList);
		//查询部门+职务权限
		StringBuilder deptZwqxHql = new StringBuilder("from ColumnFbDeptZwqx t where t.columnId = '"+columnId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<ColumnFbDeptZwqx> deptZwqxList = columnDeptZwqxDao.getEntityManager().createQuery(deptZwqxHql.toString(), ColumnFbDeptZwqx.class).getResultList();
		model.setDeptZwqxList(deptZwqxList);
		//查询部门+职务list权限
		StringBuilder deptZwqxListHql = new StringBuilder("from ColumnFbDeptZwqxList t where t.columnId = '"+columnId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<ColumnFbDeptZwqxList> deptZwqxListList = columnDeptZwqxListDao.getEntityManager().createQuery(deptZwqxListHql.toString(), ColumnFbDeptZwqxList.class).getResultList();
		model.setDeptZwqxListList(deptZwqxListList);
		//查询栏目群组权限list
		StringBuilder groupListHql = new StringBuilder("from ColumnFbGroup t where t.columnId = '"+columnId+"' and t.visible='"+InfoFbConstants.VISIBLE[1]+"'");
		List<ColumnFbGroup> groupList =columnFbGroupDao.getEntityManager().createQuery(groupListHql.toString(),ColumnFbGroup.class).getResultList();
		model.setGroupList(groupList);
		return model;
	}

	//保存全局普发
	@Override
	public InfoGenarals saveGenarals(String contentId, String genarals) {
		String creTime = DateUtil.getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
		InfoGenarals infoGenarals = new InfoGenarals();
		StringBuilder deptHql = new StringBuilder("from InfoGenarals t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		TypedQuery<InfoGenarals> deptQuery = infoGenaralsDao.getEntityManager().createQuery(deptHql.toString(), InfoGenarals.class);
		List<InfoGenarals> genaralsList = deptQuery.getResultList();
		if(genaralsList.size()>0){
			InfoGenarals infoGenarals1 = genaralsList.get(0);
			infoGenarals1.setGenarals(genarals);
			infoGenarals1.setSysOrgId(UserUtil.getCruOrgId());
			infoGenarals = infoGenaralsDao.save(infoGenarals1);
		}else{
			infoGenarals.setContentId(contentId);
			infoGenarals.setCreTime(creTime);
			infoGenarals.setCreUserId(UserUtil.getCruUserId());
			infoGenarals.setCreUserName(UserUtil.getCruUserName());
			infoGenarals.setSysOrgId(UserUtil.getCruOrgId());
			infoGenarals.setVisible(InfoFbConstants.VISIBLE[1]);
			infoGenarals.setGenarals(genarals);
			infoGenarals = infoGenaralsDao.save(infoGenarals);
		}
		return infoGenarals;
	}

	@Override
	public List<InfoGenarals> getGenarals(String contentId) {
		StringBuilder deptHql = new StringBuilder("from InfoGenarals t where t.contentId = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		TypedQuery<InfoGenarals> deptQuery = infoGenaralsDao.getEntityManager().createQuery(deptHql.toString(), InfoGenarals.class);
		List<InfoGenarals> genaralsList = deptQuery.getResultList();
		return genaralsList;
	}

	/**
	 * 保存栏目权限
	 * @param model
	 * @return
	 */
	private ColumnModel saveColumnAuth(ColumnModel model){
		//保存栏目部门权限
		List<ColumnFbDept> deptList = saveColumnDept(model);
		//保存栏目职务权限
		List<ColumnFbZwqx> zwqxList = saveColumnZwqx(model);
		//保存栏目群组权限
		List<ColumnFbGroup> groupList = saveColumnGroup(model);
		//保存栏目部门+职务权限
		model = saveColumnDeptZwqxList(model);
		model.setDeptList(deptList);
		model.setZwqxList(zwqxList);
		model.setGroupList(groupList);
		return model;
	}

	/**
	 * 保存栏目部门权限
	 * @param model
	 * @return
	 */
	private List<ColumnFbDept> saveColumnDept(ColumnModel model){
		String creTime = DateUtil.getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
		List<ColumnFbDept> deptList = model.getDeptList();
		if (deptList != null && deptList.size() > 0) {
			for (ColumnFbDept dept : deptList) {
				dept.setCreTime(creTime);
				dept.setCreUserId(UserUtil.getCruUserId());
				dept.setCreUserName(UserUtil.getCruUserName());
				dept.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			deptList = columnDeptDao.save(deptList);
		}
		return deptList;
	}

	/**
	 * 保存栏目职务权限
	 * @param model
	 * @return
	 */
	private List<ColumnFbZwqx> saveColumnZwqx(ColumnModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<ColumnFbZwqx> zwqxList = model.getZwqxList();
		if (zwqxList != null && zwqxList.size() > 0) {
			for (ColumnFbZwqx zwqx : zwqxList) {
				zwqx.setCreTime(creTime);
				zwqx.setCreUserId(UserUtil.getCruUserId());
				zwqx.setCreUserName(UserUtil.getCruUserName());
				zwqx.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			zwqxList = columnZwqxDao.save(zwqxList);
		}
		return zwqxList;
	}

	/**
	 * 保存栏目群组权限
	 * @param model
	 * @return
	 */
	private List<ColumnFbGroup> saveColumnGroup(ColumnModel model){
		String creTime = DateUtil.getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
		List<ColumnFbGroup> groupList = model.getGroupList();
		if(groupList != null && groupList.size()>0){
			for(ColumnFbGroup group:groupList){
				group.setCreTime(creTime);
				group.setCreUserId(UserUtil.getCruUserId());
				group.setCreUserName(UserUtil.getCruUserName());
				group.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			groupList = columnFbGroupDao.save(groupList);
		}
		return groupList;
	}


	private ColumnModel saveColumnDeptZwqxList(ColumnModel model){
		List<ColumnFbDeptZwqx> deptZwqxList = model.getDeptZwqxList();
		List<ColumnFbDeptZwqxList> deptZwqxListList = model.getDeptZwqxListList();
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		if(deptZwqxList != null && deptZwqxList.size()>0 && deptZwqxListList != null && deptZwqxListList.size()>0){
			for(ColumnFbDeptZwqxList deptZwqxLi:deptZwqxListList){
				deptZwqxLi.setCreTime(creTime);
				deptZwqxLi.setCreUserId(UserUtil.getCruUserId());
				deptZwqxLi.setCreUserName(UserUtil.getCruUserName());
				deptZwqxLi.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			deptZwqxListList = columnDeptZwqxListDao.save(deptZwqxListList);
			//保存栏目部门+职务权限
			for(ColumnFbDeptZwqxList deptZwqxLis:deptZwqxListList){
				String deptZwqxListId = deptZwqxLis.getId();
				String line = deptZwqxLis.getLine();
				for (ColumnFbDeptZwqx deptZwqx : deptZwqxList) {
					if (deptZwqx.getLine().equals(line)) {
						deptZwqx.setCreTime(creTime);
						deptZwqx.setCreUserId(UserUtil.getCruUserId());
						deptZwqx.setCreUserName(UserUtil.getCruUserName());
						deptZwqx.setDeptZwqxListId(deptZwqxListId);
						deptZwqx.setVisible(InfoFbConstants.VISIBLE[1]);
					}
				}
			}
			deptZwqxList = columnDeptZwqxDao.save(deptZwqxList);
		}
		model.setDeptZwqxList(deptZwqxList);
		model.setDeptZwqxListList(deptZwqxListList);
		return model;
	}

	/**
	 * 删除栏目原有权限
	 * @param columnAuthority
	 * @return
	 */
	private boolean delOldColumnAuth(ColumnModel columnAuthority){
		boolean del = false;
		try{
			columnDeptDao.delete(columnAuthority.getDeptList());
			columnZwqxDao.delete(columnAuthority.getZwqxList());
			columnFbGroupDao.delete(columnAuthority.getGroupList());
			columnDeptZwqxDao.delete(columnAuthority.getDeptZwqxList());
			columnDeptZwqxListDao.delete(columnAuthority.getDeptZwqxListList());
			del = true;
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return del;
	}

}
