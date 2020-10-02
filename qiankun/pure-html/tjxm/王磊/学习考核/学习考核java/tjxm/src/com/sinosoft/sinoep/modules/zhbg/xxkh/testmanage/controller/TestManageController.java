package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services.XxkhPaperInfoService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.SysTestUserVo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.UserDeptVo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services.TestManageService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services.TestManageServiceImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.services.DataDictionarysService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONObject;

/**
 * TODO 考试管理控制层
 * @author 李颖洁  
 * @date 2018年8月16日  下午3:05:07
 */
@Controller
@RequestMapping("/zhbg/xxkh/testmanage")
public class TestManageController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TestManageService testManageService;
	
	@Autowired
	private SysWaitNoflowService sysWaitNoflowService;
	
	@Autowired
	private DataDictionarysService dataDictionarysService;
	
	@Autowired
	private XxkhPaperInfoService xxkhPaperInfoService;
	
	@Autowired
	private SysWaitNoflowService sysWatiNoflowService;
	
	@Autowired
	private NotifyController notifyController;
	
	/**
	 * TODO 保存考试基本信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午9:25:40
	 * @param info
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存考试信息")
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	public JSONObject saveTest(XxkhTestInfo info,String timeRange){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				info.setStartTime(startTime);
				info.setEndTime(endTime);
			}
			info = testManageService.edit(info);
			json.put("flag", "1");
			json.put("data", info);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 修改考试信息和数据回显
	 * @author 李颖洁  
	 * @date 2018年8月17日上午9:25:40
	 * @param info
	 * @return JSONObject
	 */
	@LogAnnotation(value = "edit",opName = "修改考试信息")
	@ResponseBody
	@RequestMapping(value="edit")
	public JSONObject editTest(XxkhTestInfo info){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据考试id 获取考试信息
			info = testManageService.getOne(info);
			json.put("flag", "1");
			json.put("data", info);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	} 
	
	/**
	 * TODO 获取职务和职级信息
	 * @author 李颖洁  
	 * @date 2018年8月31日上午9:28:08
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "获取职务和职级信息")
	@ResponseBody
	@RequestMapping("getPosition")
	public JSONObject getPosition() {
		JSONObject json = new JSONObject();
		try {
			json = testManageService.getUserPositon();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 删除考试通知数据
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:05:56
	 * @param id 考试id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject deletePlan(XxkhTestInfo info){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除考试信息、考试与试卷关联表、人员与试卷关联表
			int result = testManageService.delete(info);
			if (result>0) {
				XxkhUserPaper userPaper = new XxkhUserPaper();
				userPaper.setTestId(info.getId());
				int userRow = testManageService.deleteUserPaper(userPaper);
				XxkhTestPaper paper = new XxkhTestPaper();
				paper.setTestId(info.getId());
				int testRow = testManageService.deleteTestPaper(paper);
				json.put("flag", "1");
				json.put("msg", "删除数据成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 保存人员与试卷信息（不上报考试人员时的保存考试人员信息）
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param id 考试id
	 * @param name 考试名称
	 */
	@LogAnnotation(value = "save",opName = "保存不上报考试人员信息")
	@ResponseBody
	@RequestMapping("saveTestPerson")
	public JSONObject saveTestPerson(String id,String name,String deptId,String positions,String positionLevels) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//获取试卷信息
			List<XxkhTestPaper> testList  = testManageService.getPapersByTestId(id);
			//获取考试人员信息
			List<SysTestUserVo> userVoList = testManageService.getTestPersonInfo(deptId, positions, positionLevels);
			if(userVoList.size()>0){
				//保存考试、试卷、人员关联信息
				List<XxkhUserPaper> userList = testManageService.saveUserPaper(id,name,testList,userVoList);
				if(userList.size()!=0){
					json.put("flag", "1");
					json.put("data", userList);
				}else{
					json.put("flag", "0");
					json.put("msg", "保存失败！");
				}
			}else{
				json.put("flag", "0");
				json.put("msg", "没有符合条件的人员！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "保存人员试卷失败！");
		}
		return json;
	}
	
	/**
	 * TODO 	保存上报人员信息
	 * @author 李颖洁  
	 * @date 2018年8月28日下午3:17:47
	 * @param id 考试id
	 * @param personIds 上报人员id
	 * @param deptId 人员处室id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存上报考试人员信息")
	@ResponseBody
	@RequestMapping("saveReportPerson")
	public JSONObject saveReportPerson(String id,String personIds,String deptId,String name) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//获取试卷信息
			List<XxkhTestPaper> testList  = testManageService.getPapersByTestId(id);
			//保存考试、试卷、人员关联信息
			json = testManageService.saveReportPerson(id,personIds,deptId,testList,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 获取考试人员数量
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param id
	 */
	@LogAnnotation(value = "query",opName = "获取考试人员数量(根据职务选项)")
	@ResponseBody
	@RequestMapping("getTestPersonCount")
	public JSONObject getTestPersonCount(String deptId,String position,String positionLevel) {
		JSONObject json = new JSONObject();
		//JSONObject data = new JSONObject();
		json.put("flag", "0");
		try {
			json = testManageService.getTestPersonCount(deptId,position,positionLevel);
			log.info(json.toString());
			//data.put("rows", json.get("data"));
			//data.put("total", json.get("total"));
			json.put("flag", "1");
			json.put("data", json.get("data"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 获取考试人员数量列表(从考试与人员关联表)
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param id 考试id
	 */
	@LogAnnotation(value = "query",opName = "获取考试人员数量(从考试与人员关联表)")
	@ResponseBody
	@RequestMapping("getTestPersonCountInUserPaper")
	public PageImpl getTestPersonCountInUserPaper(PageImpl pageImpl,XxkhUserPaper paper) {
		pageImpl.setFlag("0");
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = testManageService.getTestPersonCountInUserPaper(pageable,paper, pageImpl);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 获取考试人员信息(单个部门查看上报人员信息)
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param id
	 */
	@LogAnnotation(value = "query",opName = "获取考试人员信息列表")
	@ResponseBody
	@RequestMapping("getTestPersonInfo")
	public PageImpl getTestPersonInfoByDeptId(PageImpl pageImpl,XxkhUserPaper userPar) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = testManageService.getTestPersonInfoByDeptId(pageable,pageImpl,userPar);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 删除考试人员信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:05:56
	 * @param paper
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除考试人员信息")
	@ResponseBody
	@RequestMapping("deleteTestPerson")
	public JSONObject deleteTestPerson(XxkhUserPaper paper){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除考试与人员信息
			int row = testManageService.deleteUserPaper(paper);
			json.put("flag", "1");
			json.put("data", row);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 判断本部门是否有上报员
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param deptIds
	 */
	@LogAnnotation(value = "query",opName = "查询部门是否有上报员")
	@ResponseBody
	@RequestMapping("hasReportUser")
	public JSONObject hasReportUserByDeptId(String deptIds,String deptNames,String roleNo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			json = testManageService.hasReportUserByDeptId(deptIds,deptNames,roleNo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 插入不走流程待办（发上报待办）
	 * @author 李颖洁  
	 * @date 2018年8月20日下午8:03:18
	 * @param id
	 * @param deptName
	 * @param deptId
	 * @param userId
	 * @param inceptId
	 * @param subject
	 * @param content
	 * @param messageURL
	 * @param daibanURL
	 * @param opName
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "发送待办")
	@RequestMapping("sendWaitNoflow")
	@ResponseBody
	public JSONObject sendWaitNoflow(String id,String user,String subject,String content,String messageURL,String daibanURL,String opName){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			JSONArray array = JSONArray.fromObject(user);
			List<UserDeptVo> list2 = JSONArray.toList(array, new UserDeptVo(), new JsonConfig());
			for (UserDeptVo vo : list2) {
				SysWaitNoflow waitNoflw = new SysWaitNoflow();
				waitNoflw.setReceiveDeptName(vo.getDeptname());//接收人部门
				waitNoflw.setReceiveDeptId(vo.getDeptid());//接收人部门id 必填
				waitNoflw.setReceiveUserId(vo.getUserid());//接受人id
				waitNoflw.setReceiveUserName("");//接受人name
				waitNoflw.setRolesNo("");//接受业务角色
				waitNoflw.setOpName(opName);//操作类型
				waitNoflw.setDaibanUrl(daibanURL);//待办url  必填
				waitNoflw.setTitle(subject);//待办显示标题
				waitNoflw.setTableId("");//业务表id
				waitNoflw.setTableName("xxkh_test_info");//业务表名
				waitNoflw.setSourceId(id);//业务id
				waitNoflw.setAttr1("");//预留字段1
				waitNoflw.setAttr2("");//预留字段2
				waitNoflw.setAttr3("");//预留字段3
				sysWaitNoflowService.saveWaitNoflow(waitNoflw, false, subject, content, messageURL);
			}
			XxkhTestInfo info = new XxkhTestInfo();
			info.setId(id);
			info.setState(XXKHCommonConstants.RECORD_STATE[1]);
			info = testManageService.updateStatus(info);
			if("1".equals(info.getState())){
				json.put("flag","1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 修改考试信息状态
	 * @author 李颖洁  
	 * @date 2018年8月21日上午9:30:46
	 * @param XxkhTestInfo
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "修改考试信息状态")
	@ResponseBody
	@RequestMapping(value="updateState")
	public JSONObject updateState(XxkhTestInfo info){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if(info.getId()!=null){
				//修改考试状态
				info = testManageService.updateStatus(info);
				//保存考试处室信息
				//info = xxkhTestInfoService.edit(info);
				json.put("flag", "1");
				json.put("data", info);
			}else{
				json.put("msg", "考试基本信息不存在！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "提交考试通知失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 判断本部门是否已上报考试人员
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param deptIds
	 */
	@LogAnnotation(value = "query",opName = "判断本部门是否已上报考试人员")
	@ResponseBody
	@RequestMapping("isReported")
	public JSONObject isReported(XxkhTestInfo info) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		StringBuilder dept = new StringBuilder();
		try {
			List<XxkhUserPaper> list  = testManageService.getUserPaperByTestId("", info.getId());
			info = testManageService.getOne(info);
			String[] arr = info.getTestChuShiIds().split(",");
			if(list.size()==0){
				json.put("msg","处室都没有上报考试人员！");
			}else{
				boolean f = true;
				for (XxkhUserPaper up : list) {
					boolean m = TestManageServiceImpl.hasIn(arr, up.getCandidateDeptId());
					if(!m){
						dept.append(up.getCandidateChushiName()+",");
						f = false;
					}
				}
				if(f){
					json.put("flag", "1");
				}else{
					json.put("msg",dept+"等处室都没有上报考试人员！请及时通知相关处室进行上报，相关处室全部上报后再发布！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 获取考试数据列表
	 * @author 李颖洁  
	 * @date 2018年8月21日下午4:57:16
	 * @param pageImpl
	 * @param timeRange
	 * @param name
	 * @param state
	 * @param type
	 * @param nodeId
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询考试数据列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getTestList(PageImpl pageImpl, String timeRange,XxkhTestInfo info) {
		String startTime = "";
		String endTime = "";
		pageImpl.setFlag("0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				info.setStartTime(startTime);
				info.setEndTime(endTime);
			}
			info.setCreJuId(UserUtil.getCruJuId());
			Page<XxkhTestInfo> page = testManageService.list(info, pageImpl);
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(page.getContent());
			pageImpl.getData().setTotal((int)page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 保存试卷与考试关联信息(批量或单个保存)
	 * @author 李颖洁  
	 * @date 2018年8月22日上午11:52:41
	 * @param info
	 * @param timeRange
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存试卷信息")
	@ResponseBody
	@RequestMapping(value="savePaper")
	public JSONObject savePaper(String testId,String paperId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//先删除再保存
			XxkhTestPaper paper = new XxkhTestPaper();
			paper.setTestId(testId);
			int row = testManageService.deleteTestPaper(paper);
			List<XxkhTestPaper> result = testManageService.saveTestPaper(testId,paperId);
			if(result.size()>=1){
				json.put("flag", "1");
				json.put("data", result);
				
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	}
	
	/**
	 * TODO 查询考试试卷信息(回显已选试卷名称)
	 * @author 李颖洁  
	 * @date 2018年8月22日下午2:39:53
	 * @param id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询考试试卷信息")
	@ResponseBody
	@RequestMapping(value="getPapers")
	public PageImpl getPapersByTestId(PageImpl pageImpl,XxkhTestPaper paper){
		pageImpl.setFlag("0");
		try {
			List<XxkhTestPaper> list = testManageService.getPapersByTestId(paper);
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(list);
			pageImpl.getData().setTotal(list.size());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 删除考试试卷数据（单个删除）
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:05:56
	 * @param paper
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除考试试卷数据")
	@ResponseBody
	@RequestMapping("deletePaper")
	public JSONObject deletePaper(XxkhTestPaper paper){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除考试与试卷关联表
			int row = testManageService.deleteTestPaper(paper);
			json.put("flag", "1");
			json.put("data", row);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 删除考试试卷数据（批量删除）
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:05:56
	 * @param paper
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "批量删除考试试卷数据")
	@ResponseBody
	@RequestMapping("deleteBatchPaper")
	public JSONObject deleteBatchPaper(String testId,String paperIds){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除考试与试卷关联表
			int row = testManageService.deleteBatchTestPaper(testId,paperIds);
			json.put("flag", "1");
			json.put("data", row);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 查询已选中考试试卷列表
	 * @author 李颖洁  
	 * @date 2018年8月25日上午10:16:50
	 * @param pageImpl
	 * @param xxkhQuestionQgroup
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询已选中考试试卷列表")
	@RequestMapping("getPapersList")
	@ResponseBody
	public JSONObject getCheckedPapersList(PageImpl pageImpl,XxkhTestPaper testPaper) {
		pageImpl.setPageSize(10);
		pageImpl.setPageNumber(1);
		JSONObject json = new JSONObject();
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		PageImpl list = testManageService.getPaperInfoList(pageImpl, testPaper, pageable);
		List<XxkhPaperInfo> rows = (List<XxkhPaperInfo>) list.getData().getRows();
		for(XxkhPaperInfo info :rows) {
			info.setCz(CommonConstants.OPTION_DELETE);
			List<DataDictionarys> pname = dataDictionarysService.getPname(info.getNodeId());
			StringBuffer name = new StringBuffer();
			for(DataDictionarys dictionarys:pname) {
				name.append(dictionarys.getName()+"-");
			}
			info.setNodeId(name.toString().substring(0, name.length()-1));
		}
		//list.getData().setRows(rows);
		//list.setFlag("1");
		json.put("flag", "1");
		json.put("data", rows);
		return json;
	}
	
	/**
	 * TODO 查询所有试卷信息(暂时不用了)
	 * @author 李颖洁  
	 * @date 2018年8月25日上午10:16:50
	 * @param pageImpl
	 * @param xxkhQuestionQgroup
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询所有试卷列表")
	@RequestMapping("getPaperInfoList")
	@ResponseBody
	public PageImpl getPaperInfoList(PageImpl pageImpl,XxkhTestPaper testPaper,XxkhPaperInfo info) {
		pageImpl.setFlag("0");
		try {
			//根据类别获取试卷列表
			Page<XxkhPaperInfo> list = xxkhPaperInfoService.list(info, pageImpl);
			List<XxkhPaperInfo> rows = (List<XxkhPaperInfo>) list.getContent();
			//根据考试id获取已选试卷列表
			List<XxkhTestPaper> tList = testManageService.getPapersByTestId(testPaper);
			if(!rows.isEmpty()){
				for (XxkhTestPaper tPaper : tList) {
					for(XxkhPaperInfo pInfo :rows) {
						if(tPaper.getTestId().equals(pInfo.getId())){
							//pInfo.setIsCheck("1");
						}else{
							//pInfo.setIsCheck("");
						}
					}
				}
			}
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(rows);
			pageImpl.getData().setTotal((int)list.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 根据部门和资源删除多条记录，上报后删除待办
	 * @author 李颖洁  
	 * @date 2018年8月28日下午5:02:23
	 * @param testId 考试id（待办中得业务id）
	 * @param receiveDeptId 
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除不走流程待办")
    @RequestMapping("deleteWaitNoflow")
    @ResponseBody
    public JSONObject deleteWaitNoflow(String testId,String receiveDeptId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        int num =sysWatiNoflowService.deleteWaitNoflow("",testId,receiveDeptId, "", "");
        if (num !=0){
            json.put("flag","1");
        }
        return json;
    }
	
	/**
	 * TODO 查询消息id
	 * @author 李颖洁  
	 * @date 2018年9月12日下午4:08:12
	 * @param notityMessage
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "更改消息状态和URL")
	@ResponseBody
	@RequestMapping(value="updateMessage")
	public JSONObject updateMessage(NotityMessage notityMessage){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			JSONObject js = testManageService.queryMessageId(notityMessage);
			Long id = Long.parseLong(js.getJSONArray("data").getJSONObject(0).get("id").toString());
			notifyController.edit(id);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据考试id获取考试人员(不分页),发送待办。每天00:00执行
	 * @Date 2018/9/4 17:24
	 * @Param [TestDate]
	 * @return net.sf.json.JSONObject
	 **/
	@Scheduled(cron="0 10 0 * * ?")//每月第一日0时执行
	//@Scheduled(fixedDelay = 1000*60*30)//30分钟执行一次
	@LogAnnotation(value = "query",opName = "根据日期查询所有已发布的考试，并且获取需要考试的人员信息")
	@RequestMapping("queryAllTestUser")
	@ResponseBody
	public void queryAllTestUser(){
		try {

			Calendar cale = null;
			cale = Calendar.getInstance();
			int year = cale.get(Calendar.YEAR);
			int monthA = cale.get(Calendar.MONTH) + 1;
			int dayA = cale.get(Calendar.DAY_OF_MONTH);
			String month = "";
			String day = "";
			if(monthA==0){
				monthA=12;
				year=year+1;
			}
			if (monthA < 10) {
				month = "0" + monthA;
			}else{
				month = ""+ monthA;
			}
			if (dayA < 10) {
				day = "0" + dayA;
			}else{
				day = "" + dayA;
			}
			String TestDate = year+"-"+month+"-"+day;
			//根据日期获取当日的考试。
			List<XxkhTestInfo> TestList = testManageService.queryTestByTestDate(TestDate);
			if(TestList.size()>0){
				//如果当天的考试数量大于0那么就获取人员id发待办
				for(int i = 0;i< TestList.size();i++){
					List<XxkhUserPaper>  list =testManageService.queryAllTestUser(TestList.get(i).getId());
					for(int j = 0;j<list.size();j++){
						//拿到人员id发送不走流程待办
						String content = TestList.get(i).getName()+"\n " +
								"所属大类：" +TestList.get(i).getType()+"\n " +
								"答题时长：" +TestList.get(i).getAnswerTime()+"\n " +
								"难易程度：" +TestList.get(i).getDifficultyLevel()+"\n " +
								"考试满分：" +TestList.get(i).getFullScore()+" 及格分数："+TestList.get(i).getPassScore()+"\n" +
								"考试时间："+TestList.get(i).getStartTime()+" - "+TestList.get(i).getEndTime();
						String subject = list.get(j).getTestName();
						String messageURL = "/modules/zhbg/xxkh/testManage/ksglEnterTest.html";

						SysWaitNoflow waitNoflw = new SysWaitNoflow();
						waitNoflw.setReceiveDeptName(list.get(j).getCandidateChushiName());//接收人部门
						waitNoflw.setReceiveDeptId(list.get(j).getCandidateChushiId());//接收人部门id 必填
						waitNoflw.setReceiveUserId(list.get(j).getUserId());//接受人id
						waitNoflw.setReceiveUserName("");//接受人name
						waitNoflw.setRolesNo("");//接受业务角色
						waitNoflw.setOpName("参加考试");//操作类型
						waitNoflw.setDaibanUrl(messageURL);//待办url  必填
						waitNoflw.setTitle(list.get(j).getTestName());//待办显示标题
						waitNoflw.setTableId("");//业务表id
						waitNoflw.setTableName("xxkh_test_info");//业务表名
						waitNoflw.setSourceId(TestList.get(i).getId());//业务id
						waitNoflw.setAttr1(list.get(j).getTestId());//预留字段1
						waitNoflw.setAttr2(list.get(j).getPaperId());//预留字段2
						waitNoflw.setAttr3("");//预留字段3
						waitNoflw.setDraftUserName("考试系统");
						sysWaitNoflowService.saveWaitNoflowSystem(waitNoflw,false,subject,content,"");

					}
				}
			}

			//根据日期获取结束时间为昨天的考试，删除待办
			List<XxkhTestInfo> deleteWaitNoflwTestList = testManageService.queryTestOfdeleteWaitNoflw();

			if(deleteWaitNoflwTestList.size()>0){
				//如果当天的考试数量大于0那么就获取人员id发待办
				for(int i = 0;i< deleteWaitNoflwTestList.size();i++){
					//根据业务表id(也就是考试的id)删除待办
					sysWaitNoflowService.deleteWaitNoflow("",deleteWaitNoflwTestList.get(i).getId(),"","","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 返回列表形式的考试信息
	 * @Date 2018/9/6 15:50
	 * @Param [pageImpl, pageable, info]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@LogAnnotation(value = "query",opName = "返回列表形式的考试信息")
	@RequestMapping("getTsetInfoId")
	@ResponseBody
	public JSONObject getTsetInfoId(XxkhTestInfo info){
		JSONObject json = new JSONObject();
		try {
			List<XxkhTestInfo> list = new ArrayList<>();
			info = testManageService.getOne(info);
			list.add(info);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", array);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 交卷后根据考试id，试卷id，考试人员id更新交卷状态,更新客观题得分
	 * @Date 2018/9/11 15:21
	 * @Param [info]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "update",opName = "交卷后根据考试id，试卷id，考试人员id更新交卷状态,更新客观题得分")
	@ResponseBody
	@RequestMapping("updateObjectiveScore")
	public JSONObject updateObjectiveScore(XxkhUserPaper info){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//更新主观题得分
			testManageService.updateObjectiveScore(info);
			json.put("flag", "1");
			json.put("msg", "更新客观题得分成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 进入考试更新首次进入试卷考试时间
	 * @Date 2018/9/20 19:41
	 * @Param [info]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "update",opName = "进入考试更新首次进入试卷考试时间")
	@ResponseBody
	@RequestMapping("updateBeginTestTime")
	public JSONObject updateBeginTestTime(XxkhUserPaper info){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//更新更新进入考试时间
			testManageService.updateBeginTestTime(info);
			json.put("flag", "1");
			json.put("msg", "更新首次进入试卷考试时间成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}

	/**
	 * 
	 * @Title: TestManageController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.controller
	 * @Description: TODO修改评卷状态 修改主观题的分数
	 * @author 颜振兴
	 * @date 2018年9月13日
	 * @param paper
	 * @return
	 */
	@RequestMapping("updatePingJuan")
	@ResponseBody
	public JSONObject updatePingJuan(XxkhUserPaper paper) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		XxkhUserPaper updateIsPingjuan = testManageService.updateIsPingjuan(paper);
		if (updateIsPingjuan!=null&&!updateIsPingjuan.getId().equals("")) {
			json.put("flag", "1");
			json.put("data",updateIsPingjuan);
		}
		return json;
		
	}
	
	
}
