package com.sinosoft.sinoep.modules.video.background.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.video.background.common.VideoConstants;
import com.sinosoft.sinoep.modules.video.background.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.video.background.entity.VVideoAndPdf;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;
import com.sinosoft.sinoep.modules.video.background.entity.VideoContent;
import com.sinosoft.sinoep.modules.video.background.entity.VideoInfo;
import com.sinosoft.sinoep.modules.video.background.services.VideoColumnFbUserService;
import com.sinosoft.sinoep.modules.video.background.services.VideoContentService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/video/content")
public class VideoContentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VideoContentService service;
    @Autowired
    private SysWaitNoflowService waitNoflowService;
    @Autowired
    private VideoColumnFbUserService fbUserService;
    @Autowired
    private UserInfoService userInfoService;

    @LogAnnotation(value = "query" ,opName = "获取栏目下内容")
    @RequestMapping("getContnetList")
    @ResponseBody
    public PageImpl getContentList(PageImpl pageImpl,String columnId,String title,String creTime){
        Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getContentPageList(pageable1,pageImpl,columnId,title,creTime);
    }
    
    @LogAnnotation(value = "query" ,opName = "获取栏目下内容")
    @RequestMapping("getFbList")
    @ResponseBody
    public PageImpl getFbList(PageImpl pageImpl,String columnId,String title,String creTime){
        Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getFbPageList(pageable1,pageImpl,columnId,title,creTime);
    }
    
    @LogAnnotation(value = "save",opName = "保存内容")
    @RequestMapping("saveFrom")
    @ResponseBody
    public JSONObject saveFrom(VideoContent entity){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try{
            entity = service.saveFrom(entity);
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
    
    @LogAnnotation(value = "save",opName = "保存上传视频对应的文件")
 	@RequestMapping("saveFile")
 	@ResponseBody
 	public JSONObject saveFile(@RequestBody VVideoAndPdf videoAndPdf) {
 		JSONObject json = new JSONObject();
 		try {
 			json.put("flag", 1);
 			json.put("file", service.saveForm(videoAndPdf));
 		} catch (Exception e) {
 			log.error(e.getMessage(),e);
 			json.put("flag", 0);
 		}
 		return json;
 	}
    
    @LogAnnotation(value = "edit",opName = "修改页面")
    @RequestMapping("edit")
    @ResponseBody
    public JSONObject edit(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        VideoContent entity = new VideoContent();
        try{
            entity = service.getVideoContentById(id);
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

    @LogAnnotation(value = "delete",opName = "删除内容")
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(String ids) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        int result = service.delete(ids);
        if(result !=0){
            json.put("flag","1");
        }
        return json;
    }

    @LogAnnotation(value = "update",opName = "信息发布发送")
    @RequestMapping("sendFlow")
    @ResponseBody
    public JSONObject sendFlow(VideoContent entity){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            entity .setSubflag(VideoConstants.SUBFLAG[1]);//设置状态为流程中
            entity = service.saveFrom(entity);
            VideoColumnFbUser fbUserEntity = fbUserService.getEntity(entity.getColumnId(),UserUtil.getCruUserId());
            SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
            waitNoflowEntity.setReceiveUserId(fbUserEntity.getShUserId());
            waitNoflowEntity.setReceiveUserName(fbUserEntity.getShUserName());
            JSONObject shJson = userInfoService.getDeptByUserIds(fbUserEntity.getShUserId());
            JSONArray shDeptArray = shJson.getJSONArray("data");
            JSONObject shDept = shDeptArray.getJSONObject(0);
            waitNoflowEntity.setReceiveDeptId((shDept.get("deptid")).toString());
            waitNoflowEntity.setReceiveDeptName((String)shDept.get("deptname"));
            waitNoflowEntity.setRolesNo("");
            waitNoflowEntity.setTableName("video_content");
            waitNoflowEntity.setTableId("id");
            waitNoflowEntity.setSourceId(entity.getId());
            waitNoflowEntity.setOpName("视频发布");
            waitNoflowEntity.setDaibanUrl("/modules/video/background/content/videoContentUpdateForm.html?columnId="+entity.getColumnId());
            waitNoflowEntity.setTitle(entity.getTitle());
            waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"视频发布",entity.getTitle(),"/modules/video/background/content/videoContentUpdateForm.html?columnId="+entity.getColumnId()+"&id="+entity.getId());
            json.put("flag","1");
            json.put("data",entity);
            json.put("waitNoflowEntity",waitNoflowEntity);
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }

    @LogAnnotation(value = "update",opName = "审批不通过")
    @RequestMapping("noPass")
    @ResponseBody
    public JSONObject noPass(VideoContent entity,String workItemId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            entity .setSubflag(VideoConstants.SUBFLAG[3]);//设置状态为退回
            entity = service.saveFrom(entity);
            SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
            if(StringUtils.isNotBlank(workItemId)){
                SysWaitNoflow oldFlowEntity = waitNoflowService.getSysWaitNoflow(workItemId);
                if(oldFlowEntity != null){
                    waitNoflowEntity.setReceiveUserId(oldFlowEntity.getDraftUserId());
                    waitNoflowEntity.setReceiveUserName(oldFlowEntity.getDraftUserName());
                    waitNoflowEntity.setReceiveDeptName(oldFlowEntity.getDraftDeptName());
                    waitNoflowEntity.setReceiveDeptId(oldFlowEntity.getDraftDeptId());
                }
            }
            waitNoflowEntity.setRolesNo("");
            waitNoflowEntity.setTableName("video_content");
            waitNoflowEntity.setTableId("id");
            waitNoflowEntity.setSourceId(entity.getId());
            waitNoflowEntity.setOpName("视频发布");
            waitNoflowEntity.setDaibanUrl("/modules/video/background/content/videoContentUpdateForm.html?columnId="+entity.getColumnId());
            waitNoflowEntity.setTitle(entity.getTitle());
            waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"视频发布","退回："+entity.getTitle(),"/modules/video/background/content/videoContentUpdateForm.html?columnId="+entity.getColumnId()+"&id="+entity.getId());
            json.put("flag","1");
            json.put("data",entity);
            json.put("waitNoflowEntity",waitNoflowEntity);
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }
    /*
     * @param entity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询是否有发送，发布，审批通过，审批不通过和是否有发布范围的权限")
    @RequestMapping("queryQx")
    @ResponseBody
    public JSONObject queryQx(String columnId,String contentId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            json = service.queryQx(columnId,contentId);
            json.put("flag","1");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }

    @LogAnnotation(value = "query",opName = "获取审核列表")
    @RequestMapping("getSpList")
    @ResponseBody
    public PageImpl getSpList(PageImpl pageImpl,String columnName,String title){
        Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getSpList(pageable1,pageImpl,columnName,title);
    }
    
    /**
     * 获取当前用户收到的信息(分页)
     * TODO 
     * @author 李利广
     * @Date 2018年9月18日 下午9:11:13
     * @param page
     * @param columnCode 栏目编码
     * @return
     */
    @LogAnnotation(value = "query",opName = "获取当前用户收到的信息")
    @RequestMapping("getContentByColumn")
    @ResponseBody
    public PageImpl getContentByColumn(PageImpl page,String columnCode, VideoContent info){
    	page.setFlag("0");
    	try {
    		page = service.getContentByColumn(page, columnCode,info);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
    	return page;
    }
    
    /**
     * 获取轮播图
     * TODO 
     * @author 李利广
     * @Date 2018年9月19日 下午2:49:47
     * @param columnCode
     * @return
     */
    @LogAnnotation(value = "query",opName = "获取轮播图")
    @RequestMapping("getImgsByColumn")
    @ResponseBody
    public JSONObject getImgsByColumn(String columnCode){
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
    	try {
    		List<BannerImgVo> imgPaths = service.getImgsByColumn(columnCode);
    		json.put("flag", "1");
    		json.put("data", imgPaths);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
    	return json;
    }
    

    @LogAnnotation(value = "update",opName = "修改置顶状态")
    @RequestMapping("updateZd")
    @ResponseBody
    public JSONObject updateZdByConetenId(String contentIds,String isZd){//置顶：0 取消置顶:null
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            int n = service.updateZdByConetenIds(contentIds,isZd);
            if(n > 0){
                json.put("flag","1");
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }

    @LogAnnotation(value = "query",opName = "获取排序列表，根据columnId")
    @RequestMapping(value = "getZdList")
    @ResponseBody
    public PageImpl getZdList(PageImpl pageImpl,String columnId){
        try{
            if(StringUtils.isNotBlank(columnId)) {
                List<VideoContent> list = service.getZdList(columnId);
                pageImpl.setFlag("1");
                pageImpl.getData().setRows(list);
                pageImpl.getData().setTotal(list.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageImpl;
    }
    
    @LogAnnotation(value = "query",opName = "获取排序列表，根据columnId")
    @RequestMapping(value = "getZdList1")
    @ResponseBody
    public JSONObject getZdList1(PageImpl pageImpl,String columnId){
    	JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<VideoContent>list=new ArrayList<VideoContent>();
		try {
			  list = service.getZdList(columnId);
			JSONObject data = new JSONObject();
			data.put("total",list.size());
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			data.put("rows", array);
			json.put("data",data);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
    }

    @LogAnnotation(value = "update",opName = "置顶排序")
    @RequestMapping("orderZd")
    @ResponseBody
    public JSONObject orderZd(String ids){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            int n = service.orderZd(ids);
            if(n == 1 ){
                json.put("flag","1");
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        return json;
    }
    
    /**
     * 根据栏目id获取首页内容
     * TODO 
     * @author 马秋霞
     * @Date 2018年11月16日 上午9:52:08
     * @param columnId
     * @return
     */
    @RequestMapping("getHomePageContent")
    @ResponseBody
    public JSONObject getHomePageContent(PageImpl page,String columnId,String flag,String columnName){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
        	dataMap = service.getHomePageContent(page,columnId,flag,columnName);
            
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
        }
        json.put("data", dataMap);
        return json;
    }
    
    /**
     * 根据栏目id获取所有的内容
     * @param columnId
     * @param title
     * @return
     */
    @RequestMapping("getContentByColumnId")
    @ResponseBody
    public JSONObject getContentByColumnId(String columnId,String title){
    	 JSONObject json = new JSONObject();
         json.put("flag","0");
         List<VideoContent> list = new ArrayList<VideoContent>();
         try{
        	 list = service.getContentByColumnId(columnId,title);
         }catch (Exception e){
             e.printStackTrace();
             json.put("flag","-1");
         }
         json.put("data", list);
         return json;
    }
    
    /**
     * 获取某个内容下是视频
     * TODO 
     * @author 马秋霞
     * @Date 2018年12月3日 下午2:41:26
     * @param contentId
     * @return
     */
    @RequestMapping("getVideoByContentId")
    @ResponseBody
    public JSONObject getVideoByContentId(String contentId,String fileName){
    	 JSONObject json = new JSONObject();
         json.put("flag","0");
         List<VideoInfo> list = new ArrayList<VideoInfo>();
         try{
        	 list = service.getVideoByContentId(contentId,fileName);
         }catch (Exception e){
             e.printStackTrace();
             json.put("flag","-1");
         }
         json.put("data", list);
         return json;
    }
}