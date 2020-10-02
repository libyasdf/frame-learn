package com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.service.ComeLateLeaveEarlyService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;

import net.sf.json.JSONObject;

/**
 * 迟到早退Controller
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月15日 上午9:13:26
 */
@Controller
@RequestMapping("zhbg/kqgl/comeLateLeaveEarly")
public class ComeLateLeaveEarlyController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ComeLateLeaveEarlyService comeLateLeaveEarlyService;
	/**
	 * 打开起草页面，填充数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public JSONObject add(String id) {
		JSONObject json = new JSONObject();
		KqglLeaveInfo kqglLeaveInfo = new KqglLeaveInfo();
		try {
			// TODO 获取创建人id，创建人名称，创建部门ID，创建部门名，创建人处室ID，创建人处室名，创建人二级局ID，创建人二级局名

			kqglLeaveInfo.setCreUserId("1");
			kqglLeaveInfo.setCreUserName("登录人姓名");
			kqglLeaveInfo.setCreDeptId("1");
			kqglLeaveInfo.setCreDeptName("登录人部门");

			json.put("flag", "1");
			json.put("data", kqglLeaveInfo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 保存迟到早退起草
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午2:40:35
	 * @param comeLateLeaveEarlyInfo
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存迟到早退起草")
	@ResponseBody
	@RequestMapping("saveForm")
	public ComeLateLeaveEarlyInfo saveForm(ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo) {
		try {
			comeLateLeaveEarlyInfo = comeLateLeaveEarlyService.saveForm(comeLateLeaveEarlyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return comeLateLeaveEarlyInfo;
	}

	/**
	 * 获取迟到早退列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午4:07:12
	 * @param pageImpl
	 * @param timeRange
	 * @param cdztUserId
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询迟到早退列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String state,String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl= comeLateLeaveEarlyService.getPageListDraft(pageable, pageImpl, startDate, endDate,state,subflag);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}

	/**
	 * 修改迟到早退
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午3:02:00
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改迟到早退")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo = null;
		try {
			comeLateLeaveEarlyInfo = comeLateLeaveEarlyService.getById(id);
			json.put("flag", "1");
			json.put("data", comeLateLeaveEarlyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 删除迟到早退信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午4:06:40
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除迟到早退信息")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = comeLateLeaveEarlyService.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 更新流程状态
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午4:05:26
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "更新流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id, String subflag) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			comeLateLeaveEarlyService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 根据起草人的Id获取起草人的角色编号
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月10日 下午7:35:55
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRoleNoByUserId")
	public String getRoleNoByUserId(String userId,String deptId){
		String roleNo=RecourseUtils.getFlowRoleNoByDept(userId, deptId);
		return roleNo;
	}
}
