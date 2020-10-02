package com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.entity.DjglPostManagementEntity;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.service.DjglPostManagementService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**   
 * @Title: Controller  
 * @Description: djgl_post_management
 * @author 
 * @date 2018-12-25 16:40:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/djglPostManagementController")
public class DjglPostManagementController  {
    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DjglPostManagementService djglPostManagementService;

	
	/**
	 * 保存djgl_post_management 页面
	 * @author 
	 * @Date 2018-12-25 16:40:55
	 * @param djglPostManagement
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存djgl_post_management页面")
    @RequestMapping("save")
    @ResponseBody
	public JSONObject save( DjglPostManagementEntity djglPostManagement) {
		JSONObject json = new JSONObject();
        try {
            djglPostManagement =  djglPostManagementService.save(djglPostManagement);
            json.put("flag", 1);
            json.put("data", djglPostManagement);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
	}

	
	 /**
	 * 根据id返回数据
	 * @author 
	 * @Date 2018-12-25 16:40:55
	 * @param
	 * @param id
	 * @return
	 */
    @LogAnnotation(value = "edit",opName = "根据id查数据")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DjglPostManagementEntity entity = null;
        try {
            entity = djglPostManagementService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     *返回年底需办结数据
     */
    @LogAnnotation(value = "yearSelect",opName = "年底需办结数据")
    @ResponseBody
    @RequestMapping("yearSelect")
    public JSONObject yearSelect(DjglPostManagementEntity djglPostManagement){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DjglPostManagementEntity entity = null;
        try {
            entity = djglPostManagementService.getNewEntity(djglPostManagement);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
    /**
     *返回最新需带入数据-变更
     */
    @LogAnnotation(value = "bgSelect",opName = "新需带入数据-变更")
    @ResponseBody
    @RequestMapping("bgSelect")
    public JSONObject bgSelect(DjglPostManagementEntity djglPostManagement){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DjglPostManagementEntity entity = null;
        try {
            entity = djglPostManagementService.getNewBGEntity(djglPostManagement);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


      /**
     * 根据id逻辑删除djgl_post_management 对应列表
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param djglPostManagement
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除djgl_post_management对应列表")
    @ResponseBody
    @RequestMapping("delete")
    public JSONObject delete(DjglPostManagementEntity djglPostManagement) {
        JSONObject json = new JSONObject();
        try {
            int n = djglPostManagementService.delete(djglPostManagement);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }

    @LogAnnotation(value = "update",opName = "根据id更新表流程标识")
    @ResponseBody
    @RequestMapping("updateBySubflag")
    public JSONObject delete(String logo,String value,String id) {
        JSONObject json = new JSONObject();
        try {
            String subflag="subflag";
            if("2".equals(logo)){
                subflag="yearSubflag";
            }
            int n = djglPostManagementService.updateBySubflag(subflag,value,id);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }
    
     /**
     * djgl_post_management  列表查询的方法
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param djglPostManagement
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询djgl_post_management列表")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl, DjglPostManagementEntity djglPostManagement,boolean identifying ) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        PageImpl pageList = djglPostManagementService.getPageList(pageable, pageImpl, djglPostManagement ,identifying);
        return pageList;
    }


    @LogAnnotation(value = "individualQuery",opName = "个人查询列表")
    @ResponseBody
    @RequestMapping("individualQuery")
    public PageImpl individualQuery(PageImpl pageImpl, DjglPostManagementEntity djglPostManagement) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        PageImpl pageList = djglPostManagementService.individualQuery(pageable, pageImpl, djglPostManagement );
        return pageList;
    }

    @LogAnnotation(value = "rangeQuery",opName = "范围查询列表")
    @ResponseBody
    @RequestMapping("rangeQuery")
    public Map rangeQuery(PageImpl pageImpl, DjglPostManagementEntity djglPostManagement,String cxDeptid,boolean bol,boolean bolrange) {
        //bolrange 是否是范围查询
        Map pageListMap = djglPostManagementService.rangeQuery(pageImpl, djglPostManagement,cxDeptid,bol,bolrange);
        return pageListMap;
    }


	
}
