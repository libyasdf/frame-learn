package com.sinosoft.sinoep.modules.zhbg.kqgl.qj.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.service.OverTimeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service.KqglLeaveInfoService;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * 请假控制层 TODO
 * 
 * @author 冯超
 * @Date 2018年4月10日 下午4:41:33
 */
@Controller
@RequestMapping("zhbg/kqgl/qj")
public class KqglLeaveInfoController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KqglLeaveInfoService kqglLeaveInfoService;
	@Autowired
	private KqglUtil kqglUtil;
	@Autowired
	private OverTimeService overTimeService;
	@Autowired
	private DataDictionaryService serviceDic;

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
	 * 保存表单 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 上午10:59:59
	 * @param kqglLeaveInfo
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存请假起草")
	@ResponseBody
	@RequestMapping("saveForm")
	public KqglLeaveInfo saveForm(KqglLeaveInfo kqglLeaveInfo) {
		try {
			kqglLeaveInfo = kqglLeaveInfoService.saveForm(kqglLeaveInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return kqglLeaveInfo;
	}

	/**
	 * 请假列表 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 下午2:01:41
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询请假列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String leaveType, String creUserName,
			String applicantUnitName, String isLeaveInLieu, String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl= kqglLeaveInfoService.getPageListDraft(pageable, pageImpl, startDate, endDate, leaveType,
						isLeaveInLieu, subflag);
			} 
//			else {
//				pageImpl= kqglLeaveInfoService.getPageList(pageable, pageImpl, startDate, endDate, leaveType, creUserName,
//						applicantUnitName, isLeaveInLieu, subflag);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}

	/**
	 * 修改 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:36:41
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改请假")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		KqglLeaveInfo kqglLeaveInfo = null;
		try {
			kqglLeaveInfo = kqglLeaveInfoService.getById(id);
			json.put("flag", "1");
			json.put("data", kqglLeaveInfo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 删除 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:38:03
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除请假")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = kqglLeaveInfoService.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 更新状态 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月18日 上午8:52:58
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
			kqglLeaveInfoService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 
	 * 根据外出的开始时间和结束时间统计出日期
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGoOutTime")
	public String getGoOutTime(String startDate,String endDate,String startAmOrPm,String endAmOrPm){
		String data="";
		try {
			data=kqglUtil.getGoOutTime(startDate, endDate, startAmOrPm, endAmOrPm);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return data;
	}
	/**
	 * 获取当前用户的角色信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月8日 下午2:52:28
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRoleInfo")
	public JSONObject getRoleInfo(){
		//业务角色编号：
		JSONObject json = new JSONObject();
		try {
			String roleNo=UserUtil.getCruUserRoleNo();
			String[] roleNos=roleNo.split(",");
			List<String> list=Arrays.asList(roleNos);
			
			//循环正科长角色 判断是否存在
			for(int i=0;i<EmConstants.ZKZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.ZKZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("ifzkz","是");
				}
			}
			//循环副科长
			for(int i=0;i<EmConstants.FKZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.FKZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("iffkz","是");
				}
			}
			//正处长
			for(int i=0;i<EmConstants.ZCZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.ZCZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("ifzcz","是");
				}
			}
			//副处长
			for(int i=0;i<EmConstants.FCZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.FCZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("iffcz","是");
				}
			}
			for(int i=0;i<EmConstants.JZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.JZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("ifjz","是");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			json.put("flag", "0");
		}
		
		return json;
	}
	/**
	 * 根据起草人的Id获取起草人的角色编号
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月7日 下午1:25:30
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRoleNoByUserId")
	public String getRoleNoByUserId(String userId){
		String roleNo=RecourseUtils.getFlowRolesById(userId).get(UserConfigConsts.ROLE_NO);
		return roleNo;
	}
	
	/**
	 * 导出excel前查询表中是否有数据
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午9:28:10
	 * @param timeRange
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getQjData")
	public JSONObject selectDutyLog(String timeRange,String leaveType,String isLeaveInLieu){
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}			
			List<KqglLeaveInfo> selectlist = kqglLeaveInfoService.getsByDate(startDate, endDate, leaveType, isLeaveInLieu);// 查询数据
			if(selectlist.size()>0){
				json.put("flag", "0"); //0代表查到了数据
			}else{
				json.put("flag", "1"); //1代表没有查到数据
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 
	 *  导出Excel
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午10:12:51
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportQjData", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange, String leaveType, String isLeaveInLieu, HttpServletRequest request,
			HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<KqglLeaveInfo> dutyLoglist = kqglLeaveInfoService.getsByDate(startDate, endDate, leaveType,
					isLeaveInLieu);// 查询数据
			List<DataDictionary> dicts=serviceDic.getListByMark("leaveType", "1");
			for (KqglLeaveInfo emDutyLog : dutyLoglist) {
				// 将 代码（0，1）替换成 食谱状态（未交班，已交班）
//				if (StringUtils.isNotBlank(emDutyLog.getLeaveType())) {
//					emDutyLog.setLeaveType(EmConstants.Leave_Type[Integer.parseInt(emDutyLog.getLeaveType()) - 1]);
//				}
				for(DataDictionary dic:dicts){
					if(dic.getCode().equals(emDutyLog.getLeaveType())){
						emDutyLog.setLeaveType(dic.getName());
					}
				}
//				if (StringUtils.isNotBlank(emDutyLog.getIsLeaveInLieu())) {
//					emDutyLog.setIsLeaveInLieu(
//							EmConstants.Is_LeaveInLieu[Integer.parseInt(emDutyLog.getIsLeaveInLieu())]);
//				}
				// 设置请假日期
				String leaveDate = "";
//				String qjTimeType = emDutyLog.getLeaveTimeType();
//				if (StringUtils.isNotBlank(qjTimeType) && "1".equals(qjTimeType)) {
//					if (StringUtils.isNotBlank(emDutyLog.getLeaveStartTime())) {
//						leaveDate = "" + emDutyLog.getLeaveStartTime();
//					}
//					if (StringUtils.isNotBlank(emDutyLog.getLeaveEndTime())) {
//						leaveDate = leaveDate + " - " + emDutyLog.getLeaveEndTime();
//					}
//					if(StringUtils.isNotBlank(emDutyLog.getLeaveLongTime())){
//						emDutyLog.setLeaveLongTime(emDutyLog.getLeaveLongTime() + " /h");	
//					}
//				} else {
					
					if (StringUtils.isNotBlank(emDutyLog.getLeaveStartDate())) {
						leaveDate = "" + emDutyLog.getLeaveStartDate();
						if (StringUtils.isNotBlank(emDutyLog.getStartAmOrPm())) {
							if (emDutyLog.getStartAmOrPm() == "1") {
								leaveDate = leaveDate + "上午";
							} else {
								leaveDate = leaveDate + "下午";
							}
						}
					}
					if (StringUtils.isNotBlank(emDutyLog.getLeaveEndDate())) {
						leaveDate = leaveDate + " - " + emDutyLog.getLeaveEndDate();
						if (StringUtils.isNotBlank(emDutyLog.getEndAmOrPm())) {
							if (emDutyLog.getEndAmOrPm() == "1") {
								leaveDate = leaveDate + " 上午";
							} else {
								leaveDate = leaveDate + " 下午";
							}
						}
					}
					if(StringUtils.isNotBlank(emDutyLog.getLeaveLongTime())){
					   emDutyLog.setLeaveLongTime(emDutyLog.getLeaveLongTime());
					}
			//	}
				emDutyLog.setLeaveDate(leaveDate);
			}
			exportBean.setExportList(dutyLoglist);
			exportBean.setGetMethod(EmConstants.GET_METHOD);
			exportBean.setHeader(EmConstants.HEADER);
			exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
			exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);// 列宽默认20，可以自己指定
			exportBean.setFontName(EmConstants.FONT_NAME);// 默认宋体
			exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);// 文本字体大小默认11
			exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);// 表头字体大小默认14
			exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);// 表头行高默认600
			list.add(exportBean);
			exportData.excelProject(response, list, EmConstants.FILE_NAME[0]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年7月30日 下午4:24:52
	 * @param currentTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getOverTimeHours")
	public JSONObject getOverTimeHoursByQuarter(String currentTime) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObj=new JSONObject();
		//当前时间所属季度开始时间
		//当前时间所属季度的结束时间
		String beginQuarters="";
		String endQuerters="";
		try {
			if(StringUtils.isNotBlank(currentTime)){
				Date currDate=sdf.parse(currentTime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currDate);
				String currYear=String.valueOf(calendar.get(Calendar.YEAR));
				int month = calendar.get(Calendar.MONTH) + 1;//获取月份
				//第一季度
				if(StringUtils.isNotBlank(currYear)){
					if(month>=1 && month<=3){
						beginQuarters=currYear+"-01-01 00:00:00";
						endQuerters=currYear+"-03-31 23:59:59";
					}
					//第er季度
					if(month>=4 && month<=6){
						beginQuarters=currYear+"-04-01 00:00:00";
						endQuerters=currYear+"-06-30 23:59:59";
					}
					//第三季度
					if(month>=7 && month<=9){
						beginQuarters=currYear+"-07-01 00:00:00";
						endQuerters=currYear+"-09-30 23:59:59";
					}
					//第四季度
					if(month>=10 && month<=12){
						beginQuarters=currYear+"-10-01 00:00:00";
						endQuerters=currYear+"-12-31 23:59:59";
					}
				}
			}
			//计算季度的开始时间和结束时间计算加班的天数和小时数
			List<OverTime> list=overTimeService.getTalOverTimeDaysAndHours(beginQuarters, endQuerters);
			List<KqglLeaveInfo> list1=kqglLeaveInfoService.getTalQjDaysAndHours(beginQuarters, endQuerters);
			if(list!=null && list.size()>0){
				jsonObj.put("flag", "1");
				jsonObj.put("talLongTimeH", list.get(0).getTalLongTimeH()==null?"0.0":list.get(0).getTalLongTimeH());
				jsonObj.put("talLongTimeD", list.get(0).getTalLongTimeD()==null?"0.0":list.get(0).getTalLongTimeD());
			}
			else{
				jsonObj.put("flag", "0");
			}
			if(list1!=null && list1.size()>0){
				jsonObj.put("talLeaveLongTimeH", list1.get(0).getTalLeaveLongTimeH()==null?"0.0":list1.get(0).getTalLeaveLongTimeH());
				jsonObj.put("talLeaveLongTimeD", list1.get(0).getTalLeaveLongTimeD()==null?"0.0":list1.get(0).getTalLeaveLongTimeD());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonObj;
	}

}
