package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqglAttendenceHandleService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/kqgl/xtpz/attendanceHandle")
public class KqglAttendenceHandleController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KqglAttendenceHandleService kqglAttendenceHandleService;
	
	/**
	 * 
	 * TODO 获取考勤信息
	 * @author 冯超
	 * @Date 2018年6月1日 上午11:31:04
	 * @param pageImpl
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl, String timeRange) {
		try {
			String userId = UserUtil.getCruUserId();
			Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userId);
			//TODO 获取刷卡号
			String cardNumber = map.get(userId).getCardNumber();
			//String cardNumber = "01021";
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = kqglAttendenceHandleService.getPageList(pageable, pageImpl, timeRange ,cardNumber);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 获取数据
	 * @author 冯超
	 * @Date 2018年6月1日 下午12:51:37
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		KqglAttendenceHandle kqglAttendenceHandle = null;
		try {
			//TODO 获取当前用户的卡号
			String cardNumber = "01021";
			kqglAttendenceHandle = kqglAttendenceHandleService.getKqglAttendenceHandle(id,cardNumber);
			json.put("flag", "1");
			json.put("data", kqglAttendenceHandle);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
