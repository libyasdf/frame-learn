package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.controller;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.entity.DdjsDyglPunishEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.service.DycfService;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**   
 * @Title: Controller  
 * @Description: 党员处分
 * @author 
 * @date 2019-02-27 16:48:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/ddjsDyglPunishController")
public class DycfController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DycfService ddjsDyglPunishService;
    @Autowired
    private DwxtOrgService dwxtOrgService;
	
	/**
	 * 保存党员处分 页面
	 * @author 
	 * @Date 2019-02-27 16:48:38
	 * @param ddjsDyglPunish
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存党员处分页面")
    @RequestMapping("save")
    @ResponseBody
	public JSONObject save( DdjsDyglPunishEntity ddjsDyglPunish) {
		 JSONObject json = new JSONObject();
        try {
            ddjsDyglPunish =  ddjsDyglPunishService.save(ddjsDyglPunish);
            json.put("flag", 1);
            json.put("data", ddjsDyglPunish);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
	}

	
	 /**
	 * 打开只读、修改页面时，查询数据进行渲染
	 * @author 
	 * @Date 2019-02-27 16:48:38
	 * @param
	 * @param id
	 * @return
	 */
    @LogAnnotation(value = "edit",opName = "修改党员处分页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglPunishEntity entity = null;
        try {
            entity = ddjsDyglPunishService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

      /**
     * 根据id逻辑删除党员处分 对应列表
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param ddjsDyglPunish
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除党员处分对应列表")
    @ResponseBody
    @RequestMapping("delete")
    public JSONObject delete(DdjsDyglPunishEntity ddjsDyglPunish) {
        JSONObject json = new JSONObject();
        try {
            int n = ddjsDyglPunishService.delete(ddjsDyglPunish);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }
    
     /**
     * 党员处分  列表查询的方法
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param ddjsDyglPunish
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员处分列表")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl, DdjsDyglPunishEntity ddjsDyglPunish, String id, String type) {
        if(type.equals("org")){
            DwxtOrg dwxtOrg = new DwxtOrg();
            dwxtOrg.setId(id);
            List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
            if(list.size() > 0) {
                String ids = "";
                for (Map<String, Object> map : list) {
                    if (map.get("type").toString().equals("org")) {
                        ids += map.get("id").toString() + ",";
                    }
                }
                id = ids.substring(0, ids.length() - 1);
            }
        }
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startTime ="";
        String endTime ="";
        if (StringUtils.isNotBlank(ddjsDyglPunish.getPunishDate())) {
            startTime =ddjsDyglPunish.getPunishDate().substring(0,(ddjsDyglPunish.getPunishDate().length()+1)/2-1).trim();
            endTime =ddjsDyglPunish.getPunishDate().substring((ddjsDyglPunish.getPunishDate().length()+1)/2,ddjsDyglPunish.getPunishDate().length()).trim();
        }
        PageImpl pageList = ddjsDyglPunishService.getPageList(pageable, pageImpl, ddjsDyglPunish,startTime,endTime,id,type);
        return pageList;
    }
    

	
}
