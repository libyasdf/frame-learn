package com.sinosoft.sinoep.modules.video.authority.services;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.info.authority.common.InfoFbConstants;
import com.sinosoft.sinoep.modules.info.authority.dao.InfoFbGroupDao;
import com.sinosoft.sinoep.modules.info.authority.entity.ColumnFbGroup;
import com.sinosoft.sinoep.modules.info.authority.entity.ColumnModel;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoFbDept;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoFbDeptZwqx;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoFbDeptZwqxList;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoFbGroup;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoFbZwqx;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoModel;
import com.sinosoft.sinoep.modules.video.authority.common.ContentFbConstants;
import com.sinosoft.sinoep.modules.video.authority.dao.ContentFbDeptDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ContentFbDeptZwqxDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ContentFbDeptZwqxListDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ContentFbZwqxDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ProgramaFbDeptZwqxDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ProgramaFbDeptZwqxListDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ProgramaFbDpetDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ProgramaFbGroupDao;
import com.sinosoft.sinoep.modules.video.authority.dao.ProgramaFbZwqxDao;
import com.sinosoft.sinoep.modules.video.authority.dao.VideoFbGroupDao;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoFbDept;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoFbDeptZwqx;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoFbDeptZwqxList;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoFbGroup;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoFbZwqx;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoModel;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaFbDept;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaFbDeptZwqx;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaFbDeptZwqxList;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaFbGroup;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaFbZwqx;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaModel;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 信息发布职务权限业务类
 * @author 李利广
 * @Date 2018年9月15日 下午5:38:57
 */
@Service
public class ContentFbZwqxServiceImpl implements ContentFbZwqxService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ContentFbDeptDao deptDao;
	
	@Autowired
	private ContentFbZwqxDao zwqxDao;
	
	@Autowired
	private ContentFbDeptZwqxDao deptZwqxDao;
	
	@Autowired
	private ContentFbDeptZwqxListDao deptZwqxListDao;

	@Autowired
	private ProgramaFbDpetDao programaDeptDao;

	@Autowired
	private ProgramaFbZwqxDao programaZwqxDao;

	@Autowired
	private ProgramaFbDeptZwqxDao programaDeptZwqxDao;

	@Autowired
	private ProgramaFbDeptZwqxListDao programaDeptZwqxListDao;
	
	@Autowired
	private VideoFbGroupDao groupDao;
	
	@Autowired
	private ProgramaFbGroupDao columnFbGroupDao;



	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	@Override
	public VideoModel saveInfoZwqx(VideoModel model) {
		//保存之前先判断是否已经存在权限
		String contentId = model.getContentId();
		VideoModel authority = new VideoModel();
		if(StringUtils.isNotBlank(contentId)){
			authority= getAuthority(contentId);
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
	private VideoModel saveAuth(VideoModel model){
		//保存部门权限
		List<VideoFbDept> deptList = saveDept(model);
		//保存群组权限
		List<VideoFbGroup> groupList = saveGroup(model);
		//保存职务权限
		List<VideoFbZwqx> zwqxList = saveZwqx(model);
		//保存部门+职务list权限
		model = saveDeptZwqxList(model);
		model.setDeptList(deptList);
		model.setGroupList(groupList);
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
	private List<VideoFbDept> saveDept(VideoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<VideoFbDept> deptList = model.getDeptList();
		if (deptList != null && deptList.size() > 0) {
			for (VideoFbDept dept : deptList) {
				dept.setCreTime(creTime);
				dept.setCreUserId(UserUtil.getCruUserId());
				dept.setCreUserName(UserUtil.getCruUserName());
				dept.setVisible(ContentFbConstants.VISIBLE[1]);
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
	private List<VideoFbZwqx> saveZwqx(VideoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<VideoFbZwqx> zwqxList = model.getZwqxList();
		if (zwqxList != null && zwqxList.size() > 0) {
			for (VideoFbZwqx zwqx : zwqxList) {
				zwqx.setCreTime(creTime);
				zwqx.setCreUserId(UserUtil.getCruUserId());
				zwqx.setCreUserName(UserUtil.getCruUserName());
				zwqx.setVisible(ContentFbConstants.VISIBLE[1]);
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
	private VideoModel saveDeptZwqxList(VideoModel model){
		List<VideoFbDeptZwqxList> deptZwqxList = model.getDeptZwqxList();
		List<VideoFbDeptZwqx> deptZwqxListList = model.getDeptZwqxListList();
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (deptZwqxList != null && deptZwqxList.size() > 0 && deptZwqxListList != null && deptZwqxListList.size() > 0) {
			for (VideoFbDeptZwqxList deptZwqxLi : deptZwqxList) {
				deptZwqxLi.setCreTime(creTime);
				deptZwqxLi.setCreUserId(UserUtil.getCruUserId());
				deptZwqxLi.setCreUserName(UserUtil.getCruUserName());
				deptZwqxLi.setVisible(ContentFbConstants.VISIBLE[1]);
			}
			deptZwqxList = deptZwqxListDao.save(deptZwqxList);
			//保存部门+职务权限
			//deptZwqxList = saveInfoFbDeptZwqx(deptZwqxListList,deptZwqxList);
			for (VideoFbDeptZwqxList deptZwqxLis : deptZwqxList) {
				String deptZwqxListId = deptZwqxLis.getId();
				String line = deptZwqxLis.getLine();
				for (VideoFbDeptZwqx deptZwqx : deptZwqxListList) {
					if (deptZwqx.getLine().equals(line)) {
						deptZwqx.setCreTime(creTime);
						deptZwqx.setCreUserId(UserUtil.getCruUserId());
						deptZwqx.setCreUserName(UserUtil.getCruUserName());
						deptZwqx.setDeptZwqxListId(deptZwqxListId);
						deptZwqx.setVisible(ContentFbConstants.VISIBLE[1]);
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
	private boolean delOldAuth(VideoModel authority){
		boolean del = false;
		try {
			deptDao.delete(authority.getDeptList());
			zwqxDao.delete(authority.getZwqxList());
			deptZwqxDao.delete(authority.getDeptZwqxListList());
			deptZwqxListDao.delete(authority.getDeptZwqxList());
			groupDao.delete(authority.getGroupList());
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
	public VideoModel getAuthority(String contentId) {
		VideoModel model = new VideoModel();
		//查询部门权限
		StringBuilder deptHql = new StringBuilder("from VideoFbDept t where t.contentId = '"+contentId+"' and t.visible = '"+ContentFbConstants.VISIBLE[1]+"'");
		TypedQuery<VideoFbDept> deptQuery = deptDao.getEntityManager().createQuery(deptHql.toString(), VideoFbDept.class);
		List<VideoFbDept> deptList = deptQuery.getResultList();
		model.setDeptList(deptList);
		//查询职务权限
		StringBuilder zwqxHql = new StringBuilder("from VideoFbZwqx t where t.contentId = '"+contentId+"' and t.visible = '"+ContentFbConstants.VISIBLE[1]+"'");
		List<VideoFbZwqx> zwqxList = zwqxDao.getEntityManager().createQuery(zwqxHql.toString(), VideoFbZwqx.class).getResultList();
		model.setZwqxList(zwqxList);
		//查询部门+职务权限
		StringBuilder deptZwqxHql = new StringBuilder("from VideoFbDeptZwqxList t where t.contentId = '"+contentId+"' and t.visible = '"+ContentFbConstants.VISIBLE[1]+"'");
		List<VideoFbDeptZwqxList> deptZwqxList = deptZwqxListDao.getEntityManager().createQuery(deptZwqxHql.toString(), VideoFbDeptZwqxList.class).getResultList();
		model.setDeptZwqxList(deptZwqxList);
		//查询部门+职务list权限
		StringBuilder deptZwqxListHql = new StringBuilder("from VideoFbDeptZwqx t where t.contentId = '"+contentId+"' and t.visible = '"+ContentFbConstants.VISIBLE[1]+"'");
		List<VideoFbDeptZwqx> deptZwqxListList = deptZwqxDao.getEntityManager().createQuery(deptZwqxListHql.toString(), VideoFbDeptZwqx.class).getResultList();
		model.setDeptZwqxListList(deptZwqxListList);
		//查询群组权限list
		StringBuilder groupListHql = new StringBuilder("from VideoFbGroup t where t.contentId = '"+contentId+"' and t.visible = '"+ContentFbConstants.VISIBLE[1]+"'");
		List<VideoFbGroup> groupList = groupDao.getEntityManager().createQuery(groupListHql.toString(), VideoFbGroup.class).getResultList();
		model.setGroupList(groupList);
		return model;
	}

	/**
	 * 保存栏目权限
	 * @param model
	 * @return
	 */
	@Override
	public ProgramaModel saveColumnFbfw(ProgramaModel model) {
		//保存之前先判断是否已经存在权限
		String columnId = model.getColumnId();
		ProgramaModel columnAuthority = new ProgramaModel();
		if(StringUtils.isNotBlank(columnId)) {
			columnAuthority = getColumnAuthority(columnId);
		}
		if(columnAuthority.getDeptList().isEmpty() && columnAuthority.getZwqxList().isEmpty() && columnAuthority.getDeptZwqxList().isEmpty()){
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
	public ProgramaModel getColumnAuthority(String columnId) {
		ProgramaModel model = new ProgramaModel();
		//查询部门权限
		StringBuilder deptHql = new StringBuilder("from ProgramaFbDept t where t.columnId = '"+columnId+"'");
		TypedQuery<ProgramaFbDept> deptQuery = programaDeptDao.getEntityManager().createQuery(deptHql.toString(), ProgramaFbDept.class);
		List<ProgramaFbDept> deptList = deptQuery.getResultList();
		model.setDeptList(deptList);
		StringBuilder zwqxHql = new StringBuilder("from ProgramaFbZwqx t where t.columnId = '"+columnId+"'");
		List<ProgramaFbZwqx> zwqxList = programaZwqxDao.getEntityManager().createQuery(zwqxHql.toString(), ProgramaFbZwqx.class).getResultList();
		model.setZwqxList(zwqxList);
		//查询部门+职务权限
		StringBuilder deptZwqxHql = new StringBuilder("from ProgramaFbDeptZwqx t where t.columnId = '"+columnId+"'");
		List<ProgramaFbDeptZwqx> deptZwqxList = programaDeptZwqxDao.getEntityManager().createQuery(deptZwqxHql.toString(), ProgramaFbDeptZwqx.class).getResultList();
		model.setDeptZwqxList(deptZwqxList);
		//查询部门+职务list权限
		StringBuilder deptZwqxListHql = new StringBuilder("from ProgramaFbDeptZwqxList t where t.columnId = '"+columnId+"'");
		List<ProgramaFbDeptZwqxList> deptZwqxListList = programaDeptZwqxListDao.getEntityManager().createQuery(deptZwqxListHql.toString(), ProgramaFbDeptZwqxList.class).getResultList();
		model.setDeptZwqxListList(deptZwqxListList);
		//查询栏目群组权限list
		StringBuilder groupListHql = new StringBuilder("from ProgramaFbGroup t where t.columnId = '"+columnId+"' and t.visible='"+InfoFbConstants.VISIBLE[1]+"'");
		List<ProgramaFbGroup> groupList =columnFbGroupDao.getEntityManager().createQuery(groupListHql.toString(),ProgramaFbGroup.class).getResultList();
		model.setGroupList(groupList);
		return model;
	}

	/**
	 * 保存栏目权限
	 * @param model
	 * @return
	 */
	private ProgramaModel saveColumnAuth(ProgramaModel model){
		//保存栏目部门权限
		List<ProgramaFbDept> deptList = saveColumnDept(model);
		//保存栏目职务权限
		List<ProgramaFbZwqx> zwqxList = saveColumnZwqx(model);
		//保存栏目群组权限
		List<ProgramaFbGroup> groupList = saveColumnGroup(model);
		//保存栏目部门+职务权限
		model = saveColumnDeptZwqxList(model);
		model.setDeptList(deptList);
		model.setZwqxList(zwqxList);
		model.setGroupList(groupList);
		return model;
	}
	
	/**
	 * 保存栏目群组权限
	 * @param model
	 * @return
	 */
	private List<ProgramaFbGroup> saveColumnGroup(ProgramaModel model){
		String creTime = DateUtil.getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
		List<ProgramaFbGroup> groupList = model.getGroupList();
		if(groupList != null && groupList.size()>0){
			for(ProgramaFbGroup group:groupList){
				group.setCreTime(creTime);
				group.setCreUserId(UserUtil.getCruUserId());
				group.setCreUserName(UserUtil.getCruUserName());
				group.setVisible(InfoFbConstants.VISIBLE[1]);
			}
			groupList = columnFbGroupDao.save(groupList);
		}
		return groupList;
	}

	/**
	 * 保存栏目部门权限
	 * @param model
	 * @return
	 */
	private List<ProgramaFbDept> saveColumnDept(ProgramaModel model){
		String creTime = DateUtil.getDateText(new Date(),"yyyy-MM-dd HH:mm:ss");
		List<ProgramaFbDept> deptList = model.getDeptList();
		if (deptList != null && deptList.size() > 0) {
			for (ProgramaFbDept dept : deptList) {
				dept.setCreTime(creTime);
				dept.setCreUserId(UserUtil.getCruUserId());
				dept.setCreUserName(UserUtil.getCruUserName());
				dept.setVisible(ContentFbConstants.VISIBLE[1]);
			}
			deptList = programaDeptDao.save(deptList);
		}
		return deptList;
	}

	/**
	 * 保存栏目职务权限
	 * @param model
	 * @return
	 */
	private List<ProgramaFbZwqx> saveColumnZwqx(ProgramaModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<ProgramaFbZwqx> zwqxList = model.getZwqxList();
		if (zwqxList != null && zwqxList.size() > 0) {
			for (ProgramaFbZwqx zwqx : zwqxList) {
				zwqx.setCreTime(creTime);
				zwqx.setCreUserId(UserUtil.getCruUserId());
				zwqx.setCreUserName(UserUtil.getCruUserName());
				zwqx.setVisible(ContentFbConstants.VISIBLE[1]);
			}
			zwqxList = programaZwqxDao.save(zwqxList);
		}
		return zwqxList;
	}

	private ProgramaModel saveColumnDeptZwqxList(ProgramaModel model){
		List<ProgramaFbDeptZwqx> deptZwqxList = model.getDeptZwqxList();
		List<ProgramaFbDeptZwqxList> deptZwqxListList = model.getDeptZwqxListList();
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		if(deptZwqxList != null && deptZwqxList.size()>0 && deptZwqxListList != null && deptZwqxListList.size()>0){
			for(ProgramaFbDeptZwqxList deptZwqxLi:deptZwqxListList){
				deptZwqxLi.setCreTime(creTime);
				deptZwqxLi.setCreUserId(UserUtil.getCruUserId());
				deptZwqxLi.setCreUserName(UserUtil.getCruUserName());
				deptZwqxLi.setVisible(ContentFbConstants.VISIBLE[1]);
			}
			deptZwqxListList = programaDeptZwqxListDao.save(deptZwqxListList);
			//保存栏目部门+职务权限
			for(ProgramaFbDeptZwqxList deptZwqxLis:deptZwqxListList){
				String deptZwqxListId = deptZwqxLis.getId();
				String line = deptZwqxLis.getLine();
				for (ProgramaFbDeptZwqx deptZwqx : deptZwqxList) {
					if (deptZwqx.getLine().equals(line)) {
						deptZwqx.setCreTime(creTime);
						deptZwqx.setCreUserId(UserUtil.getCruUserId());
						deptZwqx.setCreUserName(UserUtil.getCruUserName());
						deptZwqx.setDeptZwqxListId(deptZwqxListId);
						deptZwqx.setVisible(ContentFbConstants.VISIBLE[1]);
					}
				}
			}
			deptZwqxList = programaDeptZwqxDao.save(deptZwqxList);
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
	private boolean delOldColumnAuth(ProgramaModel columnAuthority){
		boolean del = false;
		try{
			programaDeptDao.delete(columnAuthority.getDeptList());
			programaZwqxDao.delete(columnAuthority.getZwqxList());
			columnFbGroupDao.delete(columnAuthority.getGroupList());
			programaDeptZwqxDao.delete(columnAuthority.getDeptZwqxList());
			programaDeptZwqxListDao.delete(columnAuthority.getDeptZwqxListList());
			del = true;
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return del;
	}
	
	/**
	 * 保存群组权限
	 * TODO
	 * @author 李利广
	 * @Date 2018年9月16日 下午8:25:57
	 * @param model
	 * @return
	 */
	private List<VideoFbGroup> saveGroup(VideoModel model){
		String creTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		List<VideoFbGroup> groupList = model.getGroupList();
		if (groupList != null && groupList.size() > 0) {
			for (VideoFbGroup group : groupList) {
				group.setCreTime(creTime);
				group.setCreUserId(UserUtil.getCruUserId());
				group.setCreUserName(UserUtil.getCruUserName());
				group.setVisible(ContentFbConstants.VISIBLE[1]);
			}
			groupList = groupDao.save(groupList);
		}
		return groupList;
	}

}
