package com.sinosoft.sinoep.modules.zhbg.kqgl.bq.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.service.SupplementSignService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqglAttendenceHandleService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * 补签控制层
 * @author 冯超
 * @Date 2018年4月10日 下午4:41:33
 */
@Controller
@RequestMapping("zhbg/kqgl/bq")
public class SupplementSignController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SupplementSignService supplementSignService;
	@Autowired
	private KqglAttendenceHandleService kqglAttendenceHandleService;


	/**
	 * 保存表单 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 上午10:59:59
	 * @param kqglLeaveInfo
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存补签")
	@ResponseBody
	@RequestMapping("saveForm")
	public SupplementSign saveForm(SupplementSign supplementSign) {
		try {
			supplementSign = supplementSignService.saveForm(supplementSign);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return supplementSign;
	}

	/**
	 * 列表 TODO
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
	@LogAnnotation(value = "query",opName = "查询补签草稿列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String supplementSignType, String creUserName,
			String applicantUnitName, String subflag) {
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim()+" 00:00:00";
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim()+" 23:59:59";
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		if (ConfigConsts.START_FLAG.equals(subflag)) {
			return supplementSignService.getPageListDraft(pageable, pageImpl, startDate, endDate, supplementSignType,subflag);
		} else {
			return supplementSignService.getPageList(pageable, pageImpl, startDate, endDate, supplementSignType, creUserName,
					applicantUnitName, subflag);
		}
	}

	/**
	 * 修改 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:36:41
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改补签")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		SupplementSign supplementSign = null;
		try {
			supplementSign = supplementSignService.getById(id);
			json.put("flag", "1");
			json.put("data", supplementSign);
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
	@LogAnnotation(value = "delete",opName = "删除补签")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = supplementSignService.delete(id);
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
	public JSONObject updateFlag(String id,String userId,String supplementSignDate,String supplementSignTime,String supplementSignType, String subflag) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			supplementSignService.updateSubFlag(id,userId,supplementSignDate,supplementSignTime,supplementSignType, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
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
	@RequestMapping("getBqData")
	public JSONObject selectDutyLog(String timeRange, String supplementSignType) {
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			List<SupplementSign> selectlist = supplementSignService.getsByDate(startDate, endDate, supplementSignType);// 查询数据
			if (selectlist.size() > 0) {
				json.put("flag", "0"); // 0代表查到了数据
			} else {
				json.put("flag", "1"); // 1代表没有查到数据
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出Excel
	 * 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午10:12:51
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportBqData", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange, String supplementSignType, HttpServletRequest request,
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
			List<SupplementSign> dutyLoglist = supplementSignService.getsByDate(startDate, endDate, supplementSignType);// 查询数据
			for (SupplementSign emDutyLog : dutyLoglist) {
				// 审批状态： 1：审批中 5： 审批通过 6：审批未通过 3：已撤办 0：草稿
				if (StringUtils.isNotBlank(emDutyLog.getSubflag())) {
					emDutyLog.setSubflag(EmConstants.SUBFLAG[Integer.parseInt(emDutyLog.getSubflag())]);
				}
				// 补签类型 0：补签入 1：补签出 2：补全天
				if (StringUtils.isNotBlank(emDutyLog.getSupplementSignType())) {
					emDutyLog.setSupplementSignType(
							EmConstants.SUPPLEMENTSIGN_TYPE[Integer.parseInt(emDutyLog.getSupplementSignType())]);
				}
				String signType = emDutyLog.getSupplementSignType();
				// supplementSignTime 补签时间
				switch (signType) {
				case "补签入":
					emDutyLog.setSupplementSignTime(emDutyLog.getSupplementSignStartTime());
					break;
				case "补签出":
					emDutyLog.setSupplementSignTime(emDutyLog.getSupplementSignEndTime());
					break;
				case "补全天":
					emDutyLog.setSupplementSignTime(
							emDutyLog.getSupplementSignStartTime() + " " + emDutyLog.getSupplementSignEndTime());
					break;
				}
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
	 * 根据补签日期 用户id查找打卡记录
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年7月31日 上午11:47:20
	 * @param supplementSignDate
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getQrQcInfo")
	public JSONObject getQrQcInfo(String supplementSignDate, String userId) {
		JSONObject json = new JSONObject();
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userId);
		SysFlowUserVo sysFlowUserVo=map.get(userId);
		String cardNum="";
		if(sysFlowUserVo!=null){
			cardNum=sysFlowUserVo.getCardNumber();
			if(StringUtils.isBlank(cardNum)){
				json.put("flag", "0");
				json.put("msg", "获取卡号失败！");
				return json;
			}
		}
		if(StringUtils.isNotBlank(cardNum)&&StringUtils.isNotBlank(supplementSignDate)){
			KqglAttendenceHandle kqglAttendenceHandle=kqglAttendenceHandleService.getKqglAttendenceHandle(supplementSignDate, cardNum);
			json.put("flag", "1");
			json.put("QrTime", kqglAttendenceHandle.getQrTime());
			json.put("QcTime", kqglAttendenceHandle.getQcTime());
		}else{
			json.put("flag", "0");
			json.put("msg", "获取考勤信息失败");
		}
		return json;
	}


}
