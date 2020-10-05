package com.sinosoft.sinoep.modules.dagl.bygl.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.UserDeptVo;
import com.sinosoft.sinoep.modules.dagl.bygl.services.StudyingService;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 王富康
 * @Description //TODO 编研管理Controller
 * @Date 2018/12/18 20:16
 * @Param
 * @return
 **/
@Controller
@RequestMapping("dagl/bygl/studying")
public class StudyingController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudyingService studyingService;

	@Autowired
	private SysWaitNoflowService sysWaitNoflowService;

    @Autowired
	private AffixService affixService;

    /**
     * 根据编研id，查找那些处室的编研材料还未进行确认
     * @author 王磊
     * @Date 2018年12月20日 下午3:22:23
     * @param bianYanId
     * @return
     */
    @LogAnnotation(value = "query",opName = "查看那些处室的编研材料还未确认")
	@ResponseBody
	@RequestMapping("queryNotConfirm")
	public JSONObject queryNotConfirm(String bianYanId) {
		JSONObject json = new JSONObject();
    	try{
			json.put("data", studyingService.findChuShiNotConfirm(bianYanId));
			json.put("flag", 1);
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}

		return json;
	}
    
    /**
     * 根据编研管理业务表id，合并文件
     * @author 王磊
     * @Date 2018年12月20日 下午3:17:07
     * @param request
     * @param response
     * @param id
     * @return 0合并失败，1合并成功
     */
    @LogAnnotation(value = "save",opName = "编研管理汇总")
	@ResponseBody
	@RequestMapping("huiZongById")
	public JSONObject huiZongById(HttpServletRequest request,HttpServletResponse response,String id) {
    	JSONObject json = new JSONObject();
    	String strPath = request.getSession().getServletContext().getRealPath("/");//获取服务器路径
    	log.info("服务器路径为："+strPath);//linux 为： /usr/local/tomcat-8080/webapps/ROOT/
		final String basePath =  strPath+"tempFile/";
		final String bianYanId = id;
		//数据库不存在合并后的文件,直接把服务器目录下的合并文件提供给用户下载
		String affixId = "";
		//保存文件、获得文件名
		List<String> fileNameList = new ArrayList<String>();
		try{
			fileNameList = affixService.saveAndGetFileNames(basePath, bianYanId);
	    	//合并文件，并保存到服务器路径下
			studyingService.mergeWords(basePath+bianYanId+"/", fileNameList);
			//上传合并后文件到数据库
			affixId = affixService.uploadFile(bianYanId, basePath+bianYanId+"/");
			log.info("生成合并后附件的id为："+affixId);
			//修改编研的状态为已汇总
			Studying studying = studyingService.getStudyingById(id,"");
			studying.setStatus(DAGLCommonConstants.STUDYING_STATUS[2]);
			studying = studyingService.saveStudying(studying);
			if("2".equals(studying.getStatus())){
				json.put("flag","1");
			}
			json.put("data", affixId);
		}catch(Exception e) {
			e.printStackTrace();
			log.info("下载、合并、上传文件到数据库异常！");
			json.put("flag", "0");
		}
		//直接把服务器目录下合并文件提供给用户下载
		//studyingService.downloadFormServer(response, basePath+bianYanId+"/","编研管理合并后的用户文件.docx");
		return json;
	}
    
    /**
     * 根据编研id判断是否存在合并后的文件
     * @author 王磊
     * @Date 2018年12月20日 下午3:00:25
     * @return 如果存在，返回附件id
     */
    @LogAnnotation(value = "query",opName = "编研管理判断是否存在合并后的文件")
    @ResponseBody
	@RequestMapping("queryAffixId")
	public JSONObject queryAffixId(String bianYanId) {
    	JSONObject json = new JSONObject();
    	//判断是否已经存在合并后的文件
		String nowAffixId = affixService.fileIsExist(bianYanId);
    	if(StringUtils.isNotEmpty(nowAffixId)){
    		json.put("flag", nowAffixId);
    	}else{
    		json.put("flag", "");
    	}
    	return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询编研的列表
	 * @Date 2018/12/20 11:17
	 * @Param [pageImpl, month, isReport, reportStatus]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@LogAnnotation(value = "query",opName = "查询编研的列表")
	@RequestMapping(value = "getStudyingData", method = RequestMethod.GET)
	@ResponseBody
	public PageImpl getStudyingData(PageImpl pageImpl, Studying studying){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = studyingService.getStudyingData(pageable,pageImpl,studying);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研信息
	 * @Date 2018/12/20 14:08
	 * @Param [id]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "delete",opName = "删除编研信息")
	@ResponseBody
	@RequestMapping("deleteStudying")
	public JSONObject deleteStudying(String id) {
		JSONObject json = new JSONObject();
		int result = studyingService.deleteStudying(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 新增编研信息
	 * @Date 2018/12/20 17:22
	 * @Param [studying]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "save",opName = "新增编研信息")
	@RequestMapping("saveStudying")
	@ResponseBody
	public JSONObject saveStudying(@RequestBody Studying studying) {

		JSONObject json = new JSONObject();

		try {
			studying = studyingService.saveStudying(studying);
			json.put("flag", 1);
			json.put("data", studying);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}

		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据id获取一条编研及编研子表信息，
	 * @Date 2018/12/20 17:22
	 * @Param [id]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "根据id获取一条编研及编研子表信息")
	@RequestMapping(value = "getStudyingById")
	@ResponseBody
	public JSONObject getStudyingById(String id, String chushiId) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			Studying st = studyingService.getStudyingById(id,chushiId);
			json.put("flag","1");
			json.put("data",st);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据编研id查询分发列表
	 * @Date 2018/12/21 9:22
	 * @Param [pageImpl, studying]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@LogAnnotation(value = "query",opName = "根据编研id查询分发列表")
	@RequestMapping(value = "getStudyingFenFaData", method = RequestMethod.GET)
	@ResponseBody
	public List<StudyingSub> getStudyingFenFaData(String id, String studyingId, String sequence, String chushiId){
		List<StudyingSub> studyingSubs = new ArrayList<StudyingSub>();
		try {
			studyingSubs = studyingService.getStudyingFenFaData(studyingId, sequence,chushiId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return studyingSubs;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据分发id获取一个子表的分发信息
	 * @Date 2018/12/28 14:41
	 * @Param [id]
	 * @return java.util.List<com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub>
	 **/
	@LogAnnotation(value = "query",opName = "根据分发id获取一个子表的分发信息")
	@RequestMapping(value = "getStudyingSub", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getStudyingSub(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			StudyingSub studyingSub = studyingService.getStudyingSub(id);
			json.put("flag","1");
			json.put("data",studyingSub);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}


	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研子表信息
	 * @Date 2018/12/27 9:25
	 * @Param [id]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "delete",opName = "删除编研信息")
	@ResponseBody
	@RequestMapping("deleteStudyingSub")
	public JSONObject deleteStudyingSub(String ids,String affixIds) {
		JSONObject json = new JSONObject();
		int result = studyingService.deleteStudyingSub(ids,affixIds);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 发送档案编研待办
	 * @Date 2018/12/28 12:00
	 * @Param [id, user, subject, content, messageURL, daibanURL, opName]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "save",opName = "发送待办")
	@RequestMapping("sendWaitNoflow")
	@ResponseBody
	public JSONObject sendWaitNoflow(String id,String user,String subject,String content,String messageURL,String daibanURL,String opName){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			JSONArray array = JSONArray.fromObject(user);
			List<UserDeptVo> list2 = JSONArray.toList(array, new UserDeptVo(), new JsonConfig());
			StringBuilder strb = new StringBuilder("");
			for (UserDeptVo vo : list2) {
				//如果没有给这个处室的指定业务编号人员发过
				if(!strb.toString().contains(vo.getDeptid())){
					strb.append(vo.getDeptid()+",");
					SysWaitNoflow waitNoflw = new SysWaitNoflow();
					//不走流程待办发送发送调整 王磊 20190418
					waitNoflw.setReceiveDeptName(vo.getDeptname());//接收人部门
					waitNoflw.setReceiveDeptId(vo.getDeptid());//接收人部门id 必填
					//waitNoflw.setReceiveUserId(vo.getUserid());//接受人id
					waitNoflw.setReceiveUserName("");//接受人name
					waitNoflw.setRolesNo(DAGLCommonConstants.LJDWGLY);//接受业务角色
					waitNoflw.setOpName(opName);//操作类型
					waitNoflw.setDaibanUrl(daibanURL+"?id="+id);//待办url  必填
					waitNoflw.setTitle(subject);//待办显示标题
					waitNoflw.setTableId("");//业务表id
					waitNoflw.setTableName("studying");//业务表名
					waitNoflw.setSourceId(id);//业务id
					waitNoflw.setAttr1("");//预留字段1
					waitNoflw.setAttr2("");//预留字段2
					waitNoflw.setAttr3("");//预留字段3
					sysWaitNoflowService.saveWaitNoflow(waitNoflw, true, subject, content, messageURL+"?id="+id);//发送消息
				}
			}
			//修改编研的状态
			Studying studying = studyingService.getStudyingById(id,"");
			studying.setStatus(DAGLCommonConstants.STUDYING_STATUS[1]);
			studying = studyingService.saveStudying(studying);
			if("1".equals(studying.getStatus())){
				json.put("flag","1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);

		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据子表ID修改反馈状态
	 * @Date 2018/12/29 14:17
	 * @Param [id]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "update",opName = "根据子表ID修改反馈状态")
	@ResponseBody
	@RequestMapping("updateStudyingSubIsBack")
	public JSONObject updateStudyingSubIsBack(String id,String type) {
		JSONObject json = new JSONObject();
		int result = studyingService.updateStudyingSubIsBack(id,type);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 档案编研确认
	 * @Date 2019/1/2 10:26
	 * @Param [id, type]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "update",opName = "档案编研确认")
	@ResponseBody
	@RequestMapping("StudyingSubOk")
	public JSONObject StudyingSubOk(String id) {
		JSONObject json = new JSONObject();
		int result = studyingService.StudyingSubOk(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询部门下是否含有某个业务角色编号的人员
	 * @Date 2019/1/2 10:27
	 * @Param [id, type]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "查询部门下是否含有某个业务角色编号的人员")
	@ResponseBody
	@RequestMapping("hasReportUser")
	public JSONObject hasReportUserByDeptId(String deptIds,String deptNames,String roleNo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			json = studyingService.hasReportUserByDeptId(deptIds,deptNames,roleNo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
