package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeDao;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeVerifyDao;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeVerifyUserDao;
import com.sinosoft.sinoep.modules.system.notice.entity.InfoNoticeVo;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import com.sinosoft.sinoep.uias.controller.RecourseController;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.*;

/**
 * TODO 通知通告审批业务实现类
 * @author 杜建松
 * @Date 2019年1月8日 下午2:54:01
 */
@Service
public class SysNoticeVerifyServiceImpl implements SysNoticeVerifyService {
	
	@Autowired
	private SysNoticeVerifyDao sysNoticeVerifyDao;

	@Autowired
	private SysNoticeVerifyUserDao sysNoticeVerifyUserDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RecourseController recourseController;

	@Autowired
	private UserInfoService userInfoService;

	@Override
	public SysNoticeVerify getSysNoticeVerifyByDeptId(String deptId) {
		SysNoticeVerify entity = new SysNoticeVerify();
		List<SysNoticeVerify> list = new ArrayList<SysNoticeVerify>();
		StringBuilder sql = new StringBuilder("from SysNoticeVerify t where t.visible = '1' and t.noticeDeptId ="+deptId);
		TypedQuery<SysNoticeVerify> noticeVerifys = sysNoticeVerifyDao.getEntityManager().createQuery(sql.toString(), SysNoticeVerify.class);
		list = noticeVerifys.getResultList();
		if(list.size()>0){
			entity =list.get(0);
			String verifyId = entity.getId();
			List<SysNoticeVerifyUser> userList = new ArrayList<SysNoticeVerifyUser>();
			StringBuilder sql2 = new StringBuilder("from SysNoticeVerifyUser t where t.visible = '1' and t.verifyId ="+"'"+verifyId+"'");
			TypedQuery<SysNoticeVerifyUser> verifyUserList = sysNoticeVerifyDao.getEntityManager().createQuery(sql2.toString(), SysNoticeVerifyUser.class);
			entity.setNoticeUserList(verifyUserList.getResultList());
		}
		return entity;
	}

	@Override
	public SysNoticeVerify saveFroms(SysNoticeVerify entity,String deptLevels) {
		SysNoticeVerify sysNoticeVerify = new SysNoticeVerify();
		try{
			if(deptLevels.equals("1")){
				sysNoticeVerify = sysNoticeVerifyDao.findByNoticeDeptIdAndVisible(UserUtil.getCruOrgId(),"1");
			}else{
				sysNoticeVerify = sysNoticeVerifyDao.findByNoticeDeptIdAndVisible(UserUtil.getCruJuId(),"1");
			}
			if(sysNoticeVerify ==null){
				entity.setVisible("1");
				entity.setCreUserId(UserUtil.getCruUserId());
				entity.setCreUserName(UserUtil.getCruUserName());
				entity.setCreChushiId(UserUtil.getCruChushiId());
				entity.setCreChushiName(UserUtil.getCruChushiName());
				entity.setCreJuId(UserUtil.getCruJuId());
				entity.setCreJuName(UserUtil.getCruJuName());
				entity.setCreTime(JDateToolkit.getNowDate1());
				if(deptLevels.equals("1")){
					entity.setNoticeDeptId(UserUtil.getCruOrgId());
				}else{
					entity.setNoticeDeptId(UserUtil.getCruJuId());
				}
				//给部门管理员授权
				String noticeGlUserIds = entity.getNoticeGlUserIds();
				if(!noticeGlUserIds.equals("")){
					String[] userGlstr =  noticeGlUserIds.split(",");
					String[] deptGlstr =  entity.getDeptGLUserDeptId().split(",");
					for(int i=0;i<userGlstr.length;i++){
						String treeGlId = userInfoService.getDeptById(deptGlstr[i]).getTreeId();
						if(treeGlId.length() ==12){
							 recourseController.accreditByRoleNo(userGlstr[i],deptGlstr[i],"C612","");
						}else{
							 recourseController.accreditByRoleNo(userGlstr[i],deptGlstr[i],"D612","");
						}
					}
				}
				sysNoticeVerify = sysNoticeVerifyDao.save(entity);
			}else{
				String[] deptGldeptIds ={};
				String[] userGlIds ={};
				if(sysNoticeVerify.getDeptGLUserDeptId()!=null && sysNoticeVerify.getDeptGLUserDeptId().length()>0){
					//数据库中的部门id和用户id
					deptGldeptIds = sysNoticeVerify.getDeptGLUserDeptId().split(",");
					userGlIds = sysNoticeVerify.getNoticeGlUserIds().split(",");
				}
					//页面传过来的部门id和用户id
					String[] deptGldeptIdstr = entity.getDeptGLUserDeptId().split(",");
					String[] userGlIdstr = entity.getNoticeGlUserIds().split(",");
					Set<String> set = new HashSet<>(Arrays.asList(userGlIdstr));
					Set<String> newset = new HashSet<>(Arrays.asList(userGlIds));
					for(int i=0;i<userGlIds.length;i++){
						if(!userGlIds[i].equals("")){
							if(!set.contains(userGlIds[i])){
								String treeId = userInfoService.getDeptById(deptGldeptIds[i]).getTreeId();
								if(treeId.length() ==12){
									//取消授权部门管理员
									recourseController.cancelAccreditByRoleNo(userGlIds[i],deptGldeptIds[i],"C612","");
								}else{
									//取消授权部门管理员
									recourseController.cancelAccreditByRoleNo(userGlIds[i],deptGldeptIds[i],"D612","");
								}
							}
						}
					}
					for(int j=0;j<userGlIdstr.length;j++){
						if(!userGlIdstr[j].equals("")){
							if(!newset.contains(userGlIdstr[j])){
								String treeId = userInfoService.getDeptById(deptGldeptIdstr[j]).getTreeId();
								if(treeId.length() ==12){
									//授权部门管理员
									recourseController.accreditByRoleNo(userGlIdstr[j],deptGldeptIdstr[j],"C612","");
								}else{
									//授权部门管理员
									recourseController.accreditByRoleNo(userGlIdstr[j],deptGldeptIdstr[j],"D612","");
								}
							}
						}
					}
				sysNoticeVerify.setUpdateUserId(UserUtil.getCruUserId());
				sysNoticeVerify.setUpdateUserName(UserUtil.getCruUserName());
				sysNoticeVerify.setUpdateTime(JDateToolkit.getNowDate1());
				sysNoticeVerify.setIsSP(entity.getIsSP());
				sysNoticeVerify.setNoticeGlUserIds(entity.getNoticeGlUserIds());
				sysNoticeVerify.setNoticeGlUserNames(entity.getNoticeGlUserNames());
				sysNoticeVerify.setDeptGLUserDeptId(entity.getDeptGLUserDeptId());
				sysNoticeVerify = sysNoticeVerifyDao.save(sysNoticeVerify);
			}
			List<SysNoticeVerifyUser> noticeUserList = new ArrayList<SysNoticeVerifyUser>();
			if(entity.getIsSP().equals("1")){
				if(entity.getNoticeUserList().size()>0){
					if(entity.getNoticeUserList().get(0).getFbUserId()!=null && !entity.getNoticeUserList().get(0).getFbUserId().equals("")){
						for (SysNoticeVerifyUser userEntity :entity.getNoticeUserList()){
							if(userEntity.getId().equals("")){
								//给发布人授权
								JSONObject jsonObject = fbAccredit(userEntity.getFbUserId(),userEntity.getFbUserDeptId(),deptLevels);
								String rolesNo = String.valueOf(jsonObject.get("roleNo"));
								userEntity.setFbUserRole(rolesNo);
								//给审核人授权
								JSONObject jsonObject1 =shAccredit(userEntity.getShUserId(),userEntity.getShUserDeptId(),deptLevels);
								String rolesNo1 = String.valueOf(jsonObject1.get("roleNo"));
								userEntity.setShUserRole(rolesNo1);

								userEntity.setVerifyId(sysNoticeVerify.getId());
								userEntity.setCreTime(JDateToolkit.getNowDate1());
								userEntity.setCreUserId(UserUtil.getCruUserId());
								userEntity.setCreUserName(UserUtil.getCruUserName());
								userEntity.setVisible(CommonConstants.VISIBLE[1]);
								userEntity = sysNoticeVerifyUserDao.save(userEntity);
								noticeUserList.add(userEntity);
							}else{
								SysNoticeVerifyUser sysNoticeVerifyUser1 = sysNoticeVerifyUserDao.findOne(userEntity.getId());
								//判断是否给发布人授权
								if(!sysNoticeVerifyUser1.getFbUserId().equals(userEntity.getFbUserId())){
									//发布人不同，取消之前的授权人
									//取消发布人授权
									recourseController.cancelAccreditByRoleNo(sysNoticeVerifyUser1.getFbUserId(),sysNoticeVerifyUser1.getFbUserDeptId(),sysNoticeVerifyUser1.getFbUserRole(),"");
									//给新的发布人授权
									JSONObject jsonObject = fbAccredit(userEntity.getFbUserId(),userEntity.getFbUserDeptId(),deptLevels);
									String rolesNo = String.valueOf(jsonObject.get("roleNo"));
									sysNoticeVerifyUser1.setFbUserRole(rolesNo);
								}
								//判断是否给审核人授权
								if(!sysNoticeVerifyUser1.getShUserId().equals(userEntity.getShUserId())){
									//审核人不同，取消之前的授权人
									//取消审核人授权
									recourseController.cancelAccreditByRoleNo(sysNoticeVerifyUser1.getShUserId(),sysNoticeVerifyUser1.getShUserDeptId(),sysNoticeVerifyUser1.getShUserRole(),"");
									//给新的审核人授权
									JSONObject jsonObject1 = fbAccredit(userEntity.getShUserId(),userEntity.getShUserDeptId(),deptLevels);
									String rolesNo1 = String.valueOf(jsonObject1.get("roleNo"));
									sysNoticeVerifyUser1.setShUserRole(rolesNo1);
								}
								sysNoticeVerifyUser1.setFbUserId(userEntity.getFbUserId());
								sysNoticeVerifyUser1.setFbUserName(userEntity.getFbUserName());
								sysNoticeVerifyUser1.setShUserId(userEntity.getShUserId());
								sysNoticeVerifyUser1.setShUserName(userEntity.getShUserName());
								sysNoticeVerifyUser1.setFbUserDeptId(userEntity.getFbUserDeptId());
								sysNoticeVerifyUser1.setShUserDeptId(userEntity.getShUserDeptId());
								sysNoticeVerifyUser1 = sysNoticeVerifyUserDao.save(sysNoticeVerifyUser1);
								noticeUserList.add(sysNoticeVerifyUser1);
							}
						}
					}
				}
			}
			sysNoticeVerify.setNoticeUserList(noticeUserList);
		}catch (Exception e){
		e.printStackTrace();
	}
		return sysNoticeVerify;
	}
	//给发布人授权
	public JSONObject fbAccredit(String userId,String deptId,String deptLevels){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		String treeId = userInfoService.getDeptById(deptId).getTreeId();
		//给发布人授权
		if(deptLevels.equals("1")){
			if(treeId.length() == 12){
				json1 = recourseController.accreditByRoleNo(userId,deptId,"C615","");
				json.put("roleNo","C615");
			}else{
				json1 = recourseController.accreditByRoleNo(userId,deptId,"D615","");
				json.put("roleNo","D615");
			}
		}else{
			if(treeId.length() == 12){
				json1 = recourseController.accreditByRoleNo(userId,deptId,"C616","");
				json.put("roleNo","C616");
			}else{
				json1 = recourseController.accreditByRoleNo(userId,deptId,"D616","");
				json.put("roleNo","D616");
			}
		}
		return json;
	}

	//给审核人授权
	public JSONObject shAccredit(String userId,String deptId,String deptLevels){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		String treeId = userInfoService.getDeptById(deptId).getTreeId();
		//给审核人授权
		if(deptLevels.equals("1")){
			if(treeId.length() == 12){
				json1 = recourseController.accreditByRoleNo(userId,deptId,"C613","");
				json.put("roleNo","C613");
			}else{
				json1 = recourseController.accreditByRoleNo(userId,deptId,"D613","");
				json.put("roleNo","D613");
			}
		}else{
			if(treeId.length() == 12){
				json1 = recourseController.accreditByRoleNo(userId,deptId,"C614","");
				json.put("roleNo","C614");
			}else{
				json1 = recourseController.accreditByRoleNo(userId,deptId,"D614","");
				json.put("roleNo","D614");
			}
		}
		return json;
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
					sql.append("update SysNoticeVerifyUser t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.id = '"+id+"'");
					n = sysNoticeVerifyUserDao.update(sql.toString());
					SysNoticeVerifyUser sysVerifyUser = sysNoticeVerifyUserDao.findOne(id);
					if(sysVerifyUser!=null){
						//取消发布人授权
						json = recourseController.cancelAccreditByRoleNo(sysVerifyUser.getFbUserId(),sysVerifyUser.getFbUserDeptId(),sysVerifyUser.getFbUserRole(),"");
						//取消审核人授权
						json = recourseController.cancelAccreditByRoleNo(sysVerifyUser.getShUserId(),sysVerifyUser.getShUserDeptId(),sysVerifyUser.getShUserRole(),"");
					}
				}
			}
			json.put("result",n);
		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public SysNoticeVerifyUser findByFbUserIdAndVisible(String fbUserId, String visible) {
		SysNoticeVerifyUser sysNoticeVerifyUser = new SysNoticeVerifyUser();
		StringBuilder sql = new StringBuilder("from SysNoticeVerifyUser t where t.visible = '1' and t.fbUserId ="+"'"+fbUserId+"'");
		TypedQuery<SysNoticeVerifyUser> noticeVerifys = sysNoticeVerifyUserDao.getEntityManager().createQuery(sql.toString(), SysNoticeVerifyUser.class);
		List<SysNoticeVerifyUser> sysNoticeVerifyUsers = noticeVerifys.getResultList();
		if(sysNoticeVerifyUsers.size()>0){
			sysNoticeVerifyUser = sysNoticeVerifyUsers.get(0);
		}
		return sysNoticeVerifyUser;
	}

	@Override
	public SysNoticeVerifyUser findByShUserIdAndVisible(String shUserId, String visible) {
		SysNoticeVerifyUser sysNoticeVerifyUser = new SysNoticeVerifyUser();
		StringBuilder sql = new StringBuilder("from SysNoticeVerifyUser t where t.visible = '1' and t.shUserId ="+"'"+shUserId+"'");
		TypedQuery<SysNoticeVerifyUser> noticeVerifys = sysNoticeVerifyUserDao.getEntityManager().createQuery(sql.toString(), SysNoticeVerifyUser.class);
		List<SysNoticeVerifyUser> sysNoticeVerifyUsers = noticeVerifys.getResultList();
		if(sysNoticeVerifyUsers.size()>0){
			sysNoticeVerifyUser = sysNoticeVerifyUsers.get(0);
		}
		return sysNoticeVerifyUser;
	}

	@Override
	public List<SysNoticeVerifyUser> findByUseridAndVisible(String userId) {
		StringBuilder sql = new StringBuilder("from SysNoticeVerifyUser t where t.visible = '1' and t.creUserId ="+"'"+userId+"'");
		TypedQuery<SysNoticeVerifyUser> noticeVerifys = sysNoticeVerifyUserDao.getEntityManager().createQuery(sql.toString(), SysNoticeVerifyUser.class);
		List<SysNoticeVerifyUser> sysNoticeVerifyUsers = noticeVerifys.getResultList();
		return sysNoticeVerifyUsers;
	}

}
