package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.entity.WmgLosecredit;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.services.WmgLosecreditService;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 外卖管理-失信人员controller
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:20:55
 */
@Controller
@RequestMapping("/mypage/wmgl/losecredit")
public class WmgLosecreditController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WmgLosecreditService secreditService;

    /**
     * TODO 查询分页数据
     * @author 李颖洁  
     * @date 2019年5月9日下午6:35:51
     * @param pageImpl
     * @param losecredit
     * @param timeRange
     * @return PageImpl
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "查询分页失信人员列表")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl,WmgLosecredit losecredit,String timeRange){
    	String startDate = "";
		String endDate = "";
        try {
        	if (StringUtils.isNotBlank(timeRange)) {
        		startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
        		endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
        	}
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = secreditService.list(pageable,pageImpl,losecredit,startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * TODO 修改、只读数据获取
     * @author 李颖洁  
     * @date 2019年5月7日下午3:04:30
     * @param id
     * @return JSONObject
     */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取修改页面数据")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		WmgLosecredit con = null;
		try {
			con = secreditService.getById(id);
			json.put("flag", "1");
			json.put("data", con);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 手动解除锁定状态
	 * @author 李颖洁  
	 * @date 2019年5月9日下午3:46:58
	 * @param id
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "解除锁定状态")
	@ResponseBody
	@RequestMapping("updateType")
	public JSONObject updateType(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int num = secreditService.updateType(id);
			if(num>0){
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 查询 当前用户是否被锁定
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午1:44:33
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取当前用户的锁定状态")
	@ResponseBody
	@RequestMapping("getLoseState")
	public JSONObject getLoseState(){
		JSONObject jsonOjb = new JSONObject();
		jsonOjb.put("flag", "0");
		Map<String,String> map = new HashMap<String,String>() ;
		try {
			map = secreditService.getLoseState();
			jsonOjb.put("flag", "1");
			jsonOjb.put("state", map.get("state").toString());
			jsonOjb.put("msg", map.get("msg").toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return jsonOjb;
	}
}
