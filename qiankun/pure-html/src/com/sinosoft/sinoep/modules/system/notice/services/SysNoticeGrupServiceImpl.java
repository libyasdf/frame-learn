package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeGrupDao;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeUserDao;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeVerifyDao;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeVerifyUserDao;
import com.sinosoft.sinoep.modules.system.notice.entity.*;
import com.sinosoft.sinoep.uias.controller.RecourseController;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.service.UserInfoServiceImpl;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.*;

/**
 * TODO 通知通告发送人群业务实现类
 * @author 杜建松
 * @Date 2019年1月8日 下午2:54:01
 */
@Service
public class SysNoticeGrupServiceImpl implements SysNoticeGrupService {

	private Log log = LogFactory.getLog(UserInfoServiceImpl.class);
	
	@Autowired
	private SysNoticeGrupDao sysNoticeGrupDao;

	@Autowired
	private SysNoticeUserDao sysNoticeUserDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RecourseController recourseController;

	@Autowired
	private UserInfoService userInfoService;


	@Override
	public SysNoticeGrup saveFroms(SysNoticeGrup entity) {
		SysNoticeGrup sysNoticeGrup = new SysNoticeGrup();
		if(StringUtils.isNotBlank(entity.getId())){
			SysNoticeGrup oldNotice = sysNoticeGrupDao.findOne(entity.getId());
			oldNotice.setSysGrupName(entity.getSysGrupName());
			oldNotice.setUpdateUserId(UserUtil.getCruUserId());
			oldNotice.setUpdateUserName(UserUtil.getCruUserName());
			oldNotice.setUpdateTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			oldNotice.setTypeName(entity.getTypeName());
			oldNotice.setOfficeScopeId(entity.getOfficeScopeId());
			oldNotice.setOfficeScopeName(entity.getOfficeScopeName());
			sysNoticeGrup = sysNoticeGrupDao.save(oldNotice);
		}else{
			entity.setCreUserId(UserUtil.getCruUserId());
			entity.setCreUserName(UserUtil.getCruUserName());
			entity.setCreDeptId(UserUtil.getCruDeptId());
			entity.setCreDeptName(UserUtil.getCruDeptName());
			entity.setCreChushiId(UserUtil.getCruChushiId());
			entity.setCreChushiName(UserUtil.getCruChushiName());
			entity.setCreJuId(UserUtil.getCruJuId());
			entity.setCreJuName(UserUtil.getCruJuName());
			entity.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			entity.setVisible(SysNoticeConstants.VISIBLE[1]);
			sysNoticeGrup = sysNoticeGrupDao.save(entity);
		}

		List<SysNoticeUser> sysNoticeUserList = new ArrayList<SysNoticeUser>();
		if(entity.getSysNoticeUserList().size()>0){
			for (SysNoticeUser sysNoticeUser :entity.getSysNoticeUserList()){
				sysNoticeUser.setGrupId(sysNoticeGrup.getId());
				sysNoticeUser.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
				sysNoticeUser.setCreUserId(UserUtil.getCruUserId());
				sysNoticeUser.setCreUserName(UserUtil.getCruUserName());
				sysNoticeUser.setVisible(CommonConstants.VISIBLE[1]);
				sysNoticeUser.setSysGrupName(entity.getSysGrupName());
				List<Dept> deptLists = userInfoService.getAllSuperDeptById(sysNoticeUser.getSysDeptId());
				for (int i = 0; i < deptLists.size(); i++) {
					Dept dept = deptLists.get(i);
					if (dept.getTreeId().length() == 12) {//处室
						sysNoticeUser.setSysChuShiId(dept.getDeptid());
						sysNoticeUser.setSysDeptUserName(dept.getDeptname()+sysNoticeUser.getSysUserName());
					}
				}
				if(sysNoticeUser.getSysChuShiId() ==null || sysNoticeUser.getSysChuShiId().equals("")){
					sysNoticeUser.setSysChuShiId(sysNoticeUser.getSysDeptId());
					sysNoticeUser.setSysDeptUserName(sysNoticeUser.getSysDeptName()+sysNoticeUser.getSysUserName());
				}
				sysNoticeUser = sysNoticeUserDao.save(sysNoticeUser);
				sysNoticeUserList.add(sysNoticeUser);
			}
		}
		sysNoticeGrup.setSysNoticeUserList(sysNoticeUserList);
		return sysNoticeGrup;
	}

	@Override
	public PageImpl getGrupList(PageImpl pageImpl, String sysGrupName) {
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.creUserId = '"+UserUtil.getCruUserId()+"'");
		sBuilder.append(" and t.isGenarals = '"+SysNoticeConstants.IS_GENARLAS[1]+"'");
		sBuilder.append(" and t.typeCode = 02");
		if (StringUtils.isNotBlank(sysGrupName)) {
			sBuilder.append(" and t.sysGrupName like '%"+sysGrupName+"%'");
		}

		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.creTime desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			sBuilder.append(" order by t.creTime desc");
		}
		Page<SysNoticeGrup> query = sysNoticeGrupDao.query(sBuilder.toString(), pageable);
		List<SysNoticeGrup> content = query.getContent();
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	//部门调查问卷群组列表
	@Override
	public PageImpl getSurveyGrupList(PageImpl pageImpl, String sysGrupName) {
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.creUserId = '"+UserUtil.getCruUserId()+"'");
		sBuilder.append(" and t.isGenarals = '"+SysNoticeConstants.IS_GENARLAS[1]+"'");
		sBuilder.append(" and t.typeCode = 04");
		if (StringUtils.isNotBlank(sysGrupName)) {
			sBuilder.append(" and t.sysGrupName like '%"+sysGrupName+"%'");
		}

		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.creTime desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			sBuilder.append(" order by t.creTime desc");
		}
		Page<SysNoticeGrup> query = sysNoticeGrupDao.query(sBuilder.toString(), pageable);
		List<SysNoticeGrup> content = query.getContent();
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	@Override
	public PageImpl getGenaralsList(PageImpl pageImpl, String sysGrupName) {
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.creUserId = '"+UserUtil.getCruUserId()+"'");
		sBuilder.append(" and t.isGenarals = '"+SysNoticeConstants.IS_GENARLAS[0]+"'");
		if (StringUtils.isNotBlank(sysGrupName)) {
			sBuilder.append(" and t.sysGrupName like '%"+sysGrupName+"%'");
		}

		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.creTime desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			sBuilder.append(" order by t.creTime desc");
		}
		Page<SysNoticeGrup> query = sysNoticeGrupDao.query(sBuilder.toString(), pageable);
		List<SysNoticeGrup> content = query.getContent();
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	@Override
	public SysNoticeGrup getView(String id) throws Exception {
		SysNoticeGrup sysNoticeGrup = sysNoticeGrupDao.findOne(id);
		String grupId = sysNoticeGrup.getId();
		List<SysNoticeUser> sysNoticeUsers = new ArrayList<SysNoticeUser>();
		StringBuilder sql2 = new StringBuilder("from SysNoticeUser t where t.visible = '1' and t.grupId ="+"'"+grupId+"'");
		TypedQuery<SysNoticeUser> sysNoticeUserTypedQuery = sysNoticeGrupDao.getEntityManager().createQuery(sql2.toString(), SysNoticeUser.class);
		sysNoticeGrup.setSysNoticeUserList(sysNoticeUserTypedQuery.getResultList());
		return sysNoticeGrup;
	}

	@Override
	public JSONObject deleteItme(String ids) {
		StringBuilder sql = new StringBuilder();
		JSONObject json = new JSONObject();
		int n = 0;
		try{
			if(StringUtils.isNotBlank(ids)){
				String[] idsArry = ids.split(",");
				for(String id : idsArry){
					sql.append("update SysNoticeUser t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.id = '"+id+"'");
					n = sysNoticeUserDao.update(sql.toString());
				}
			}
			json.put("result",n);
		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * TODO 删除通知群组
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:47:41
	 * @param noticeId
	 * @return
	 */
	@Override
	public Boolean delNotice(String noticeId) throws Exception{
		StringBuilder sql = new StringBuilder();
		StringBuilder sql1 = new StringBuilder();
		int count = 0;
		sql.append("update SysNoticeGrup t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.id = '"+noticeId+"'");
		count =sysNoticeGrupDao.update(sql.toString());
		sql1.append("update SysNoticeUser t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.grupId = '"+noticeId+"'");
		sysNoticeUserDao.update(sql1.toString());
		return count == 0?false:true;
	}

	@Override
	public JSONObject getGroup() {
		JSONObject json = new JSONObject();
		List<NoticeTree> noticeTreeList = new ArrayList<NoticeTree>();
		List<SysNoticeGrup> list = new ArrayList<SysNoticeGrup>();
		StringBuilder sql = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' and t.typeCode = 02 and t.creUserId = '"+UserUtil.getCruUserId()+"' and t.isGenarals = 02 order by t.creTime desc");
		TypedQuery<SysNoticeGrup> notices = sysNoticeGrupDao.getEntityManager().createQuery(sql.toString(), SysNoticeGrup.class);
		list = notices.getResultList();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				NoticeTree noticeTree = new NoticeTree();
				noticeTree.setOrderno("0");
				noticeTree.setName(list.get(i).getSysGrupName());
				noticeTree.setUupid(list.get(i).getTypeCode());
				noticeTree.setType("value");
				noticeTree.setUuid(list.get(i).getId());
				noticeTreeList.add(noticeTree);
			}
			NoticeTree noticeTree1 = new NoticeTree();
			noticeTree1.setOrderno("0");
			noticeTree1.setName("综合管理信息化系统");
			noticeTree1.setUupid("0");
			noticeTree1.setType("key");
			noticeTree1.setUuid(ConfigConsts.SYSTEM_ID);
			noticeTreeList.add(noticeTree1);

			NoticeTree noticeTree2 = new NoticeTree();
			noticeTree2.setOrderno("0");
			noticeTree2.setName(list.get(0).getTypeName());
			noticeTree2.setUupid(ConfigConsts.SYSTEM_ID);
			noticeTree2.setType("value");
			noticeTree2.setUuid(list.get(0).getTypeCode());
			noticeTreeList.add(noticeTree2);
		}
		json.put("flag","1");
		json.put("data",noticeTreeList);
		return json;
	}

	@Override
	public JSONObject getSurveyGroup() {
		JSONObject json = new JSONObject();
		List<NoticeTree> noticeTreeList = new ArrayList<NoticeTree>();
		List<SysNoticeGrup> list = new ArrayList<SysNoticeGrup>();
		StringBuilder sql = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' and t.typeCode = 04 and t.creUserId = '"+UserUtil.getCruUserId()+"' and t.isGenarals = 02  order by t.creTime desc");
		TypedQuery<SysNoticeGrup> notices = sysNoticeGrupDao.getEntityManager().createQuery(sql.toString(), SysNoticeGrup.class);
		list = notices.getResultList();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				NoticeTree noticeTree = new NoticeTree();
				noticeTree.setOrderno("0");
				noticeTree.setName(list.get(i).getSysGrupName());
				noticeTree.setUupid(list.get(i).getTypeCode());
				noticeTree.setType("value");
				noticeTree.setUuid(list.get(i).getId());
				noticeTreeList.add(noticeTree);
			}
			NoticeTree noticeTree1 = new NoticeTree();
			noticeTree1.setOrderno("0");
			noticeTree1.setName("综合管理信息化系统");
			noticeTree1.setUupid("0");
			noticeTree1.setType("key");
			noticeTree1.setUuid(ConfigConsts.SYSTEM_ID);
			noticeTreeList.add(noticeTree1);

			NoticeTree noticeTree2 = new NoticeTree();
			noticeTree2.setOrderno("0");
			noticeTree2.setName(list.get(0).getTypeName());
			noticeTree2.setUupid(ConfigConsts.SYSTEM_ID);
			noticeTree2.setType("value");
			noticeTree2.setUuid(list.get(0).getTypeCode());
			noticeTreeList.add(noticeTree2);
		}
		json.put("flag","1");
		json.put("data",noticeTreeList);
		return json;
	}


	@Override
	public JSONObject getGenaralsGroup(String typeCode) {
		JSONObject json = new JSONObject();
		List<NoticeTree> noticeTreeList = new ArrayList<NoticeTree>();
		List<SysNoticeGrup> list = new ArrayList<SysNoticeGrup>();
		StringBuilder sql = new StringBuilder("from SysNoticeGrup t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' and t.typeCode = '"+typeCode.toString()+"' and t.isGenarals = 01 order by t.creTime desc");
		TypedQuery<SysNoticeGrup> notices = sysNoticeGrupDao.getEntityManager().createQuery(sql.toString(), SysNoticeGrup.class);
		list = notices.getResultList();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getOfficeScopeId()!=null && !list.get(i).getOfficeScopeId().equals("")){
					if(list.get(i).getOfficeScopeId().indexOf(UserUtil.getCruChushiId())!=-1){
						NoticeTree noticeTree = new NoticeTree();
						noticeTree.setOrderno("0");
						noticeTree.setName(list.get(i).getSysGrupName());
						noticeTree.setUupid(list.get(i).getTypeCode());
						noticeTree.setType("value");
						noticeTree.setUuid(list.get(i).getId());
						noticeTreeList.add(noticeTree);
					}
				}else{
					NoticeTree noticeTree = new NoticeTree();
					noticeTree.setOrderno("0");
					noticeTree.setName(list.get(i).getSysGrupName());
					noticeTree.setUupid(list.get(i).getTypeCode());
					noticeTree.setType("value");
					noticeTree.setUuid(list.get(i).getId());
					noticeTreeList.add(noticeTree);
				}
			}

			NoticeTree noticeTree2 = new NoticeTree();
			noticeTree2.setOrderno("0");
			noticeTree2.setName(list.get(0).getTypeName());
			noticeTree2.setUupid(ConfigConsts.SYSTEM_ID);
			noticeTree2.setType("value");
			noticeTree2.setUuid(list.get(0).getTypeCode());
			noticeTreeList.add(noticeTree2);

			NoticeTree noticeTree1 = new NoticeTree();
			noticeTree1.setOrderno("0");
			noticeTree1.setName("综合管理信息化系统");
			noticeTree1.setUupid("0");
			noticeTree1.setType("key");
			noticeTree1.setUuid(ConfigConsts.SYSTEM_ID);
			noticeTreeList.add(noticeTree1);
		}
		json.put("flag","1");
		json.put("data",noticeTreeList);
		return json;
	}

	@Override
	public JSONObject getGroupUser(String groupId,String userid) {
		JSONObject json = new JSONObject();
		List<SysNoticeUser> list = new ArrayList<SysNoticeUser>();
		List<NoticeUserTree> noticeUserTrees = new ArrayList<NoticeUserTree>();
		String sql = " from SysNoticeUser g where g.visible = '1' ";
		if(StringUtils.isNotBlank(groupId)){
			sql += " and g.grupId = '"+groupId+"'";
			/*json = this.getDateBySql(sql);*/
		}
		if(StringUtils.isNotBlank(userid)){
			sql += " and g.sysUserId = '"+userid+"'";
		}
		TypedQuery<SysNoticeUser> notices = sysNoticeUserDao.getEntityManager().createQuery(sql.toString(), SysNoticeUser.class);
		list = notices.getResultList();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				NoticeUserTree noticeTree = new NoticeUserTree();
				noticeTree.setId(list.get(i).getId());
				noticeTree.setGrup_id(list.get(i).getGrupId());
				noticeTree.setUserid(list.get(i).getSysUserId());
				noticeTree.setUsername(list.get(i).getSysDeptUserName());
				noticeTree.setSysDeptId(list.get(i).getSysDeptId());
				noticeUserTrees.add(noticeTree);
			}
		}
		json.put("flag","1");
		json.put("data",noticeUserTrees);
		return json;
	}

	//通知人员树的结构树实体类
	public class NoticeTree{

		private String orderno;

		private String name;

		private String uupid;

		private String type;

		private String uuid;

		public String getOrderno() {
			return orderno;
		}

		public void setOrderno(String orderno) {
			this.orderno = orderno;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUupid() {
			return uupid;
		}

		public void setUupid(String uupid) {
			this.uupid = uupid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
	}

	//通知人员树所包含人员的实体类
	public class NoticeUserTree{
		private String id;

		private String grup_id;

		private String userid;

		private String username;

		private String sysDeptId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getGrup_id() {
			return grup_id;
		}

		public void setGrup_id(String grup_id) {
			this.grup_id = grup_id;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getSysDeptId() {
			return sysDeptId;
		}

		public void setSysDeptId(String sysDeptId) {
			this.sysDeptId = sysDeptId;
		}
	}

}
