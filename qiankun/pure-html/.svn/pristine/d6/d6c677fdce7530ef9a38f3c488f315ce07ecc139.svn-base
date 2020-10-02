package com.sinosoft.sinoep.modules.video.background.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.video.background.common.VideoConstants;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import com.sinosoft.sinoep.modules.video.background.services.VideoColumnService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/video/background/column")
public class VideoColumnController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VideoColumnService service;

    @LogAnnotation(value = "query",opName = "获取视频发布栏目树")
    @RequestMapping("/getColumnTree")
    @ResponseBody
    public JSONObject getColumnTree(String columnName,String userId){
        return service.getColumnTree(columnName,userId);
    }

    @LogAnnotation(value = "query",opName = "根据父id获取栏目列表")
    @RequestMapping("getColumnList")
    @ResponseBody
    public PageImpl getColumnList(PageImpl pageImpl, String superId, String columnName, String columnCode){
        Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getColumnPageList(pageable,pageImpl,superId,columnName,columnCode);
    }

    @LogAnnotation(value = "update",opName = "栏目排序")
    @RequestMapping("updatetreeSort")
    @ResponseBody
    public JSONObject updatetreeSort(String[] ids,String dropId,String superId,String nodeLevel){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            service.updatetreeSort(ids,dropId,superId,nodeLevel);
            json.put("flag","1");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return  json ;
    }

    @LogAnnotation(value = "save",opName = "保存视频发布栏目")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject saveFlow(@RequestBody VideoColumn entity,String isFirst){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try{
            entity = service.saveFroms(entity,isFirst);
            json.put("flag","1");
            json.put("data",entity);
            json.put("msg","保存成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","保存异常！");
            log.error("保存异常！");
        }
        return json;
    }

    @LogAnnotation(value = "update",opName = "修改页面")
    @RequestMapping("edit")
    @ResponseBody
    public JSONObject edit(String id){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        VideoColumn entity = new VideoColumn();
        try{
            entity = service.getInfoClumnById(id);
            json.put("flag","1");
            json.put("data",entity);
            json.put("msg","获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","获取失败!");
            log.error("获取失败！");
        }
        return json;
    }

    @LogAnnotation(value = "delete",opName = "删除栏目")
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(String ids){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        int result = service.delete(ids);
        if(result !=0){
            json.put("flag","1");
        }
        return json;
    }

    @LogAnnotation(value = "delete",opName = "删除发布，审批人信息")
    @RequestMapping("deleteItme")
    @ResponseBody
    public JSONObject deleteItem(String ids){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        int result = service.deleteItme(ids);
        if(result !=0 ){
            json.put("flag","1");
        }
        return json;
    }
    
    /**
     * 根据栏目编号，获取所有子栏目（不包括孙子栏目）
     * TODO 
     * @author 李利广
     * @Date 2018年9月20日 下午3:23:43
     * @param columnCode 栏目编号
     * @return
     */
    @LogAnnotation(value = "query",opName = "异步获取栏目树")
    @RequestMapping("getAllColumn")
    @ResponseBody
    public JSONObject getAllColumn(String columnCode){
    	JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
			List<VideoColumn> columnList = service.getAllColumn(columnCode);
			json.put("flag", "1");
			json.put("data", columnList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
        return json;
    }
    
    /**
     * 根据code获取一条栏目信息
     * TODO 
     * @author 李利广
     * @Date 2018年9月20日 下午3:58:36
     * @param columnCode
     * @return
     */
    @LogAnnotation(value = "query",opName = "根据code获取一条栏目信息")
    @RequestMapping("getColumnByCode")
    @ResponseBody
    public JSONObject getColumnByCode(String columnCode){
    	JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
        	VideoColumn column = service.getColumnByCode(columnCode);
			json.put("flag", "1");
			json.put("data", column);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
        return json;
    }
    
    @LogAnnotation(value = "query",opName = "查询当前人是否是栏目管理员")
    @RequestMapping("getShowPage")
    @ResponseBody
    public JSONObject getShowPage(String columnId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        json.put("isShowGlPage","0");
        try{
            if(StringUtils.isNotBlank(columnId)){
            	VideoColumn entity = service.getInfoClumnById(columnId);
                String rolesNo = UserUtil.getCruUserRoleNo();
                if(  rolesNo.contains(VideoConstants.VIDEOFBGLYKEROLENO) || rolesNo.contains(VideoConstants.VIDEOFBGLYCHUROLENO) || VideoConstants.VIDEOFBONEID.equals(columnId)){
                    json.put("isShowGlPage","1");
                    json.put("flag","1");
                }else if(StringUtils.isNotBlank(entity.getColumnGlUserIds())){
                	if(entity.getColumnGlUserIds().contains(UserUtil.getCruUserId())) {
                		 json.put("isShowGlPage","1");
                         json.put("flag","1");
                	}
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }
    
    @LogAnnotation(value = "query",opName = "查询当前人是否是栏目管理员")
    @RequestMapping("getColumnCz")
    @ResponseBody
    public JSONObject getColumnCz(String columnId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        json.put("isShowGlPage","0");
        try{
            if(StringUtils.isNotBlank(columnId)){
            	VideoColumn entity = service.getInfoClumnById(columnId);
                String rolesNo = UserUtil.getCruUserRoleNo();
                if(  rolesNo.contains(VideoConstants.VIDEOFBGLYKEROLENO) || rolesNo.contains(VideoConstants.VIDEOFBGLYCHUROLENO) ){
                    json.put("isShowGlPage","1");
                    json.put("flag","1");
                }else if(StringUtils.isNotBlank(entity.getColumnGlUserIds())){
                	if(entity.getColumnGlUserIds().contains(UserUtil.getCruUserId())) {
                		 json.put("isShowGlPage","1");
                         json.put("flag","1");
                	}
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }
    
    @LogAnnotation(value = "query",opName = "查询视频门户首页栏目")
    @RequestMapping("getHomePageColumns")
    @ResponseBody
    public List<VideoColumn> getHomePageColumns(){
    	List<VideoColumn> list = new ArrayList<VideoColumn>();
    	list=service.getHomePageColumns();
    	return list;
    }
    @LogAnnotation(value = "query",opName = "栏目编码重复校验")
    @RequestMapping(value = "check",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject check(String columnCode,String id){
        JSONObject json = new JSONObject();
        boolean valid  = service.checkDic(columnCode,id);
        json.put("valid",valid);
        return json;
    }
}