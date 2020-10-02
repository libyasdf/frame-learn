package com.sinosoft.sinoep.modules.system.notice.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeGrup;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeGrupService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 通知公告通知人群控制类
 * @author 杜建松
 * @Date 2018年9月5日 下午4:08:31
 */
@Controller
@RequestMapping("system/noticeGrup")
public class SysNoticeGrupController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysNoticeGrupService sysNoticeGrupService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SysWaitNoflowService waitNoflowService;
	
	/**
	 * TODO 查询部门通知群组列表
	 * @author 李利广
	 * @Date 2018年9月10日 上午9:56:11
	 * @param page
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询部门通知群组列表")
	@ResponseBody
	@RequestMapping("getNoticeGrupList")
	public PageImpl getNoticeGrupList(PageImpl page,String sysGrupName){
		page.setFlag("0");
		try {
			page = sysNoticeGrupService.getGrupList(page, sysGrupName);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取部门通知通告群组列表异常！");
		}
		return page;
	}

	/**
	 * TODO 查询局级通知群组列表
	 * @author 李利广
	 * @Date 2018年9月10日 上午9:56:11
	 * @param page
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询局级通知群组列表")
	@ResponseBody
	@RequestMapping("getNoticeGenaralsList")
	public PageImpl getNoticeGenaralsList(PageImpl page,String sysGrupName){
		page.setFlag("0");
		try {
			page = sysNoticeGrupService.getGenaralsList(page, sysGrupName);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取局级通知通告群组列表异常！");
		}
		return page;
	}
	/**
	 * TODO 保存通知人员群组
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:17:37
	 * @param
	 * @param
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存通知人员群组")
	@ResponseBody
	@RequestMapping("save")
	public JSONObject saveNoticeGrup(@RequestBody SysNoticeGrup sysNoticeGrup){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			sysNoticeGrup = sysNoticeGrupService.saveFroms(sysNoticeGrup);
			json.put("flag", "1");
			json.put("msg", "通知人员群组保存成功");
			json.put("data", sysNoticeGrup);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "通知人员群组保存失败！");
		}
		return json;
	}

	/**
	 * TODO 获取一条数据
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:45:51
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询一条通知人群信息")
	@ResponseBody
	@RequestMapping("view")
	public JSONObject getView(String id){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			SysNoticeGrup sysNoticeGrup = sysNoticeGrupService.getView(id);
			res.put("flag", "1");
			res.put("data", sysNoticeGrup);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "读取通知人群数据失败！");
		}
		return res;
	}

	/**
	 * TODO 删除通知人员群组中的人员
	 * @author 杜建松
	 * @Date 2018年9月10日 上午9:56:11
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除通知人员群组中的人员")
	@RequestMapping("deleteItme")
	@ResponseBody
	public JSONObject deleteItem(String ids){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		String shResult ="";
		JSONObject json1 = sysNoticeGrupService.deleteItme(ids);
		String result = String.valueOf(json1.get("result"));
		if(!result.equals("0") ){
			json.put("flag","1");
		}
		return json;
	}

	/**
	 * TODO 删除通知群组
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:45:51
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除通知群组")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delNotice(String noticeId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			Boolean boo = sysNoticeGrupService.delNotice(noticeId);
			if (boo) {
				res.put("flag", "1");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "删除失败！");
		}
		return res;
	}

	/**
	 * 获取部门人员树
	 * @param
	 * @return
	 */
	@RequestMapping("getGroupTree")
	@ResponseBody
	public String getGroupTree(){
		JSONObject json = sysNoticeGrupService.getGroup();
		JSONArray array = json.getJSONArray("data");
		return array.toString();
	}

	/**
	 * 获取调查问卷部门人员树
	 * @param
	 * @return
	 */
	@RequestMapping("getSurveyGroupTree")
	@ResponseBody
	public String getSurveyGroupTree(){
		JSONObject json = sysNoticeGrupService.getSurveyGroup();
		JSONArray array = json.getJSONArray("data");
		return array.toString();
	}


	/**
	 * 获取全局人员树
	 * @param
	 * @return
	 */
	@RequestMapping("getGenaralsGroupTree")
	@ResponseBody
	public String getGenaralsGroupTree(String typeCode){
		JSONObject json = sysNoticeGrupService.getGenaralsGroup(typeCode);
		JSONArray array = json.getJSONArray("data");
		return array.toString();
	}

	/**
	 * 获取群组下用户
	 * @param groupId 群组id
	 * @return
	 */
	@RequestMapping("getGroupUser")
	@ResponseBody
	public JSONObject getGroupUser(String groupId,String userid){
		return sysNoticeGrupService.getGroupUser(groupId,userid);
	}

}
