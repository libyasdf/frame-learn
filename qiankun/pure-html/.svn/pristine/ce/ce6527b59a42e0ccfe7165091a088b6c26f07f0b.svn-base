package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.entity.WmglNotice;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.services.WmglNoticeService;
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
 * TODO 外卖管理-通知公告controller 
 * @author 李颖洁  
 * @date 2019年5月10日  下午5:49:27
 */
@Controller
@RequestMapping("/mypage/wmgl/notice")
public class WmglNoticeController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WmglNoticeService noticeService;

    /**
     * TODO  查询列表数据，分页
     * @author 李颖洁  
     * @date 2019年5月7日下午3:03:42
     * @param pageImpl
     * @param isvalid
     * @return PageImpl
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "查询通知信息列表")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl, String isValid,String timeRange){
    	String startDate = "";
    	String endDate = "";
        try {
        	if(StringUtils.isNotBlank(timeRange)){
        		startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
        		endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
        	}
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = noticeService.list(pageable,pageImpl,isValid,startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * TODO 保存通知信息
     * @author 李颖洁  
     * @date 2019年5月14日下午1:58:51
     * @param notice 通知内容
     * @return JSONObject
     */
    @LogAnnotation(opType = OpType.SAVE,opName = "保存通知信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject saveForm(WmglNotice notice) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
        	notice = noticeService.saveForm(notice);
            json.put("flag", "1");
            json.put("data", notice);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
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
		WmglNotice con = null;
		try {
			con = noticeService.getById(id);
			json.put("flag", "1");
			json.put("data", con);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取最新的一条通知公告
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取最新的一条通知公告")
	@ResponseBody
	@RequestMapping("getLast")
	public JSONObject getLast(){
		JSONObject json = new JSONObject();
		 json.put("flag", "1");
		 String notice = "";
		 try {
			notice = noticeService.getLast();
			json.put("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * TODO 删除一条通知
	 * @author 李颖洁  
	 * @date 2019年5月14日下午4:14:22
	 * @param id
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除通知信息")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int num = noticeService.deleteById(id);
			if(num>0){
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
