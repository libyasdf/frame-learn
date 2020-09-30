package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity.WmglConfig;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.services.WmglConfigService;
import net.sf.json.JSONObject;
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
 * TODO 外卖管理-基础配置
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:20:55
 */
@Controller
@RequestMapping("/mypage/wmgl/config")
public class WmglConfigController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WmglConfigService configService;

    /**
     * TODO  查询列表数据，分页
     * @author 李颖洁  
     * @date 2019年5月7日下午3:03:42
     * @param pageImpl
     * @param isvalid
     * @return PageImpl
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "查询基础配置信息列表")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl, String period){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = configService.list(pageable,pageImpl,period);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * TODO 修改基础配置
     * @author 李颖洁  
     * @date 2019年5月7日下午2:49:45
     * @param con 基础配置信息
     * @return JSONObject
     */
    @LogAnnotation(opType = OpType.UPDATE,opName = "修改基础配置信息")
    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateConfig(WmglConfig con) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(con.getId())) {
            try {
                con = configService.updateConfig(con);
                json.put("flag", "1");
                json.put("data", con);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
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
		WmglConfig con = null;
		try {
			con = configService.getById(id);
			json.put("flag", "1");
			json.put("data", con);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取本年度的注意事项
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月16日 下午4:38:16
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取本年度的注意事项")
	@ResponseBody
	@RequestMapping("getAttendItem")
	public JSONObject getAttendItem(){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		String attendItem = "";
		try {
			attendItem = configService.getAttendItem();
		} catch (Exception e) {
			json.put("flag", "0");
		}
		json.put("data", attendItem);
		return json;
	}

}
