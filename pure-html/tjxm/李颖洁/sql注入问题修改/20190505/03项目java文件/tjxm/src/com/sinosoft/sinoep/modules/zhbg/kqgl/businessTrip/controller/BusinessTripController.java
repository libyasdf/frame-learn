package com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service.BusinessTripService;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * 出差Controller层 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:42:09
 */
@Controller
@RequestMapping("zhbg/kqgl/businessTrip")
public class BusinessTripController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BusinessTripService businessTripService;
	
	@Autowired
	private KqglUtil kqglUtil;

	/**
	 * hql查询出差申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:34:56
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询出差草稿列表")
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getList(PageImpl pageImpl, String busiTripType, String timeRange, String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim() + " 00:00:00";
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim() + "24:00:00";
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl=businessTripService.getPageListDraft(pageable, pageImpl, busiTripType, startDate, endDate, subflag);
			} else {
				pageImpl= businessTripService.getPageList(pageable, pageImpl, busiTripType, startDate, endDate, subflag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}

	/**
	 * 保存出差申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:35:42
	 * @param businessTrip
	 * @param ideaName
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存出差申请")
	@ResponseBody
	@RequestMapping("saveForm")
	public BusinessTrip saveForm(BusinessTrip businessTrip, String ideaName) {
		try {
			businessTrip = businessTripService.saveForm(businessTrip, ideaName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return businessTrip;
	}

	/**
	 * 删除出差申请草稿 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:35:58
	 * @param id
	 * @return
	 */
	@SameUrlData
	@LogAnnotation(value = "delete",opName = "删除出差草稿")
	@ResponseBody
	@RequestMapping("deleteBusinessTrip")
	public JSONObject deletebusinessTrip(String id) {
		JSONObject json = new JSONObject();
		int result = businessTripService.deleteBusinessTrip(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 出差申请打开只读、修改页面时，查询数据进行渲染 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:36:28
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "更新出差")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		BusinessTrip businessTrip = null;
		try {
			businessTrip = businessTripService.getById(id);
			json.put("flag", "1");
			json.put("data", businessTrip);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * TODO 更新出差业务表流程状态
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:54:17
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "更新出差流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id, String subflag) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			businessTripService.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
//		JSONObject json = new JSONObject();
//		String flag = businessTripService.updateSubFlag(id, subflag);
//		json.put("flag", flag);
//		return json;
	}
	/**
	 * 导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午5:55:42
	 * @param overTimeType
	 * @param timeRange
	 * @param subflag
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportDutyLog(String overTimeType, String timeRange, String subflag,HttpServletRequest request, HttpServletResponse response) {
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
			List<BusinessTrip> overTimeList = businessTripService.getList(overTimeType,startDate,endDate, subflag);// 查询数据
			exportBean.setExportList(overTimeList);
			exportBean.setGetMethod(EmConstants.GET_METHOD);
			exportBean.setHeader(EmConstants.HEADER);
			exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
			exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);//列宽默认20，可以自己指定
			exportBean.setFontName(EmConstants.FONT_NAME);//默认宋体
			exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);//文本字体大小默认11
			exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);//表头字体大小默认14
			exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);//表头行高默认600
			list.add(exportBean);
			exportData.excelProject(response, list, EmConstants.FILE_NAME[0]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}		
	}
	
	/**
	 * 根据出差的开始时间和结束时间统计出日期
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGoOutTime")
	public JSONObject getGoOutTime(String timeRange){
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		String data="";
		try {
			data=String.valueOf(kqglUtil.getDaysByDate(startDate, endDate));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		JSONObject json = new JSONObject();
		json.put("data", data);
		return json;
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
			//循环科长角色 判断是否存在
			for(int i=0;i<EmConstants.KZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.KZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("ifkz","是");
				}
			}
			for(int i=0;i<EmConstants.CZ_ROLE_NO.length;i++){
				if(list.contains(EmConstants.CZ_ROLE_NO[i])){
					json.put("flag", "1");
					json.put("ifcz","是");
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
	public String getRoleNoByUserId(String userId,String deptId){
		String roleNo=RecourseUtils.getFlowRoleNoByDept(userId, deptId);
		return roleNo;
	}
	
	/**
	 * 查询出差记录列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月29日 下午3:02:12
	 * @param pageImpl
	 * @param busiTripType
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询出差记录列表")
	@ResponseBody
	@RequestMapping("getBusinessTripRecordList")
	public PageImpl getBusinessTripRecordList(PageImpl pageImpl, String busiTripType, String timeRange, String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl=businessTripService.getBusinessTripRecordList(pageable, pageImpl, busiTripType, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}
	
}
