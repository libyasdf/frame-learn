package com.sinosoft.sinoep.modules.system.notice.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeVerifyService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * TODO 通知公告审核控制类
 * @author 杜建松
 * @Date 2019年1月9日 下午4:08:31
 */
@Controller
@RequestMapping("system/noticeVerify")
public class SysNoticeVerifyController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysNoticeVerifyService noticeVerifyService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * TODO 查询当前用户的通知通告审核设置
	 * @author 杜建松
	 * @Date 2018年9月10日 上午9:56:11
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "查询当前用户的通知通告审核设置")
	@RequestMapping("edit")
	@ResponseBody
	public JSONObject edit(){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		SysNoticeVerify entity = new SysNoticeVerify();
		String userDeptId = "";
		try{
			String roleNo = UserUtil.getCruUserRoleNo();
			String [] roleNOStrs = roleNo.split(",");
			String deptLevels = "";
			for(int i=0;i<roleNOStrs.length;i++){
				if(roleNOStrs[i].equals("C611") || roleNOStrs[i].equals("D611")){
					deptLevels ="1";
				}
			}
			if(deptLevels.equals("1")){
				userDeptId = UserUtil.getCruOrgId();
			}else{
				userDeptId = UserUtil.getCruJuId();
			}
			entity = noticeVerifyService.getSysNoticeVerifyByDeptId(userDeptId);
			if(entity.getId()!= null){
				json.put("data",entity);
			}else{
				json.put("data","");
			}
			if(deptLevels.equals("1")){
				json.put("deptLevel","1");
			}else{
				json.put("deptLevel","0");
			}
			json.put("flag","1");
			json.put("msg","获取成功！");
		}catch (Exception e){
			e.printStackTrace();
			json.put("flag","-1");
			json.put("msg","获取失败!");
			log.error("获取失败！");
		}
		return json;
	}
	/**
	 * TODO 保存当前用户的通知通告审核设置
	 * @author 杜建松
	 * @Date 2018年9月10日 上午9:56:11
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存当前用户的通知通告审核设置")
	@RequestMapping("save")
	@ResponseBody
	public JSONObject saveFlow(@RequestBody SysNoticeVerify entity){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		String treeDept = "";
		json.put("flag",0);
		String deptLevels = "";
		try{
			String roleNo = UserUtil.getCruUserRoleNo();
			String [] roleNOStrs = roleNo.split(",");
			for(int i=0;i<roleNOStrs.length;i++){
				if(roleNOStrs[i].equals("C611") || roleNOStrs[i].equals("D611")){
					deptLevels ="1";
				}
			}
            //判断选择的发布人、审核人是否属于处级或科级
			List<SysNoticeVerifyUser> sysNoticeVerifyUsers = entity.getNoticeUserList();
			if(sysNoticeVerifyUsers.size()>0){
				for(int j=0;j<sysNoticeVerifyUsers.size();j++){
					if(sysNoticeVerifyUsers.get(j).getFbUserId()!=null && !sysNoticeVerifyUsers.get(j).getFbUserId().equals("")){
						String treeid = userInfoService.getDeptById(sysNoticeVerifyUsers.get(j).getFbUserDeptId()).getTreeId();
						String treeid2 = userInfoService.getDeptById(sysNoticeVerifyUsers.get(j).getShUserDeptId()).getTreeId();
						if(treeid.length() !=12 && treeid.length() !=16){
							treeDept ="0";
						}
						if(treeid2.length() !=12 && treeid2.length() !=16){
							treeDept ="0";
						}
					}
				}
			}
			//判断选择的部门管理员是否属于处级或科级
			String noticeVicifys = entity.getDeptGLUserDeptId();
			if(!noticeVicifys.equals("")){
				String[] noticeVicifyStr = noticeVicifys.split(",");
				for(String deptGLid:noticeVicifyStr){
					String treeid = userInfoService.getDeptById(deptGLid).getTreeId();
					if(treeid.length() !=12 && treeid.length() !=16){
						treeDept ="0";
					}
				}
			}
			//判断选择的部门管理员是否包含局级管理员
			String deptUsers = entity.getNoticeGlUserIds();
			if(!deptUsers.equals("")){
				String[] deptUserStr = deptUsers.split(",");
				for(String deptGLid:deptUserStr){
//					Map<String, String> rolesMap = RecourseUtils.getFlowRolesById(deptGLid);
//					String userRoles = rolesMap.get(UserConfigConsts.ROLE_NO);
//					if(userRoles.indexOf("C611") != -1 || userRoles.indexOf("C612") != -1 ){
//						treeDept ="2";
//					}
				}
			}

			if(treeDept.equals("")){
				entity = noticeVerifyService.saveFroms(entity,deptLevels);
				if(entity.getNoticeUserList()!=null || !entity.getDeptGLUserDeptId().equals("")){
					if(entity.getNoticeUserList().size()>0){
						json.put("shFlag","1");
					}else{
						json.put("shFlag","0");
					}
				}else{
					json.put("shFlag","0");
				}
				json.put("flag","1");
				json.put("treeDept","1");
				json.put("data",entity);
				json.put("msg","保存成功！");
			}else{
				json.put("treeDept",treeDept);
			}
		}catch (Exception e){
			e.printStackTrace();
			json.put("flag","-1");
			json.put("shFlag","-1");
			json.put("msg","保存异常！");
			log.error("保存异常！");
		}
		return json;
	}

	/**
	 * TODO 删除发布，审批人信息和授权
	 * @author 杜建松
	 * @Date 2018年9月10日 上午9:56:11
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除发布，审批人信息和授权")
	@RequestMapping("deleteItme")
	@ResponseBody
	public JSONObject deleteItem(String ids){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		String shResult ="";
		JSONObject json1 = noticeVerifyService.deleteItme(ids);
		String result = String.valueOf(json1.get("result"));
		if(json1.get("status")!=null){
			 shResult = String.valueOf(json1.get("status"));
		}
		json.put("shFlag",shResult);
		if(!result.equals("0") ){
			json.put("flag","1");
		}
		return json;
	}

	/**
	 * TODO 查询该管理员下是否有发布人和审核人
	 * @author 杜建松
	 * @Date 2018年9月10日 上午9:56:11
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询该管理员下是否有发布人和审核人")
	@RequestMapping("findByUseridAndVisible")
	@ResponseBody
	public JSONObject findByUseridAndVisible(){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		String userId = UserUtil.getCruUserId();
		List<SysNoticeVerifyUser> sysNoticeVerifyUserList = noticeVerifyService.findByUseridAndVisible(userId);
		if(sysNoticeVerifyUserList.size()>0){
			json.put("flag","1");
		}
		return json;
	}
}
