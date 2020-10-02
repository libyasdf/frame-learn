package com.sinosoft.sinoep.modules.info.xxfb.controller;

import java.math.BigDecimal;
import java.util.List;

import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.info.xxfb.common.InfoConstants;
import com.sinosoft.sinoep.modules.info.xxfb.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;
import com.sinosoft.sinoep.modules.info.xxfb.services.InfoColumnFbUserService;
import com.sinosoft.sinoep.modules.info.xxfb.services.InfoContentService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/info/content")
public class InfoContentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfoContentService service;

    @Autowired
    private SysWaitNoflowService waitNoflowService;

    @Autowired
    private InfoColumnFbUserService fbUserService;

    @Autowired
    private UserInfoService userInfoService;

    @LogAnnotation(value = "query" ,opName = "获取栏目下内容")
    @RequestMapping("getContnetList")
    @ResponseBody
    public PageImpl getContentList(PageImpl pageImpl,String columnId,String title,String creTime){
        Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getContentPageList(pageable1,pageImpl,columnId,title,creTime);
    }

    @LogAnnotation(value = "query" ,opName = "获取管理页面下的内容")
    @RequestMapping("getContnetList1")
    @ResponseBody
    public PageImpl getContentList1(PageImpl pageImpl,String columnId,String title,String creTime){
        Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
        return service.getContentPageList1(pageable1,pageImpl,columnId,title,creTime);
    }

    @LogAnnotation(value = "save",opName = "保存内容")
    @RequestMapping("saveFrom")
    @ResponseBody
    public JSONObject saveFrom(InfoContent entity){
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

    @LogAnnotation(value = "edit",opName = "修改页面")
    @RequestMapping("edit")
    @ResponseBody
    public JSONObject edit(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        InfoContent entity = new InfoContent();
        try{
            entity = service.getInfoContentById(id);
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
    public JSONObject sendFlow(InfoContent entity){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            entity .setSubflag(InfoConstants.SUBFLAG[1]);//设置状态为流程中
            entity = service.saveFrom(entity);
            InfoColumnFbUser fbUserEntity = fbUserService.getEntity(entity.getColumnId(),UserUtil.getCruUserId());
            SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
            waitNoflowEntity.setReceiveUserId(fbUserEntity.getShUserId());
            waitNoflowEntity.setReceiveUserName(fbUserEntity.getShUserName());
            JSONObject shJson = userInfoService.getDeptByUserIds(fbUserEntity.getShUserId());
            JSONArray shDeptArray = shJson.getJSONArray("data");
            JSONObject shDept = shDeptArray.getJSONObject(0);
            waitNoflowEntity.setReceiveDeptId(shDept.getString("deptid"));
            waitNoflowEntity.setReceiveDeptName((String)shDept.get("deptname"));
            waitNoflowEntity.setRolesNo("");
            waitNoflowEntity.setTableName("info_content");
            waitNoflowEntity.setTableId("id");
            waitNoflowEntity.setSourceId(entity.getId());
            waitNoflowEntity.setOpName("信息发布");
            waitNoflowEntity.setDaibanUrl("/modules/info/xxfb/content/infoContentAddForm.html?columnId="+entity.getColumnId());
            waitNoflowEntity.setTitle(entity.getTitle());
            waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"信息发布",entity.getTitle(),"/modules/info/xxfb/content/infoContentAddForm.html?columnId="+entity.getColumnId()+"&id="+entity.getId());
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
    public JSONObject noPass(InfoContent entity,String workItemId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            entity .setSubflag(InfoConstants.SUBFLAG[3]);//设置状态为退回
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
            waitNoflowEntity.setTableName("info_content");
            waitNoflowEntity.setTableId("id");
            waitNoflowEntity.setSourceId(entity.getId());
            waitNoflowEntity.setOpName("信息发布");
            waitNoflowEntity.setDaibanUrl("/modules/info/xxfb/content/infoContentAddForm.html?columnId="+entity.getColumnId());
            waitNoflowEntity.setTitle(entity.getTitle());
            waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"信息发布","entity.getTitle()","/modules/info/xxfb/content/infoContentAddForm.html?columnId="+entity.getColumnId()+"&id="+entity.getId());
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
    public PageImpl getContentByColumn(PageImpl page,String columnCode, InfoContent info){
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
                List<InfoContent> list = service.getZdList(columnId);
                pageImpl.setFlag("1");
                pageImpl.getData().setRows(list);
                pageImpl.getData().setTotal(list.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageImpl;
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
     * TODO 更新流程状态
     * @author 李利广
     * @Date 2018年9月5日 下午4:40:55
     * @param
     * @return
     */
    @LogAnnotation(value = "update",opName = "更新流程状态")
    @ResponseBody
    @RequestMapping("updateFlag")
    public JSONObject updateFlag(String id,String subflag){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            InfoContent infoContent =service.updateFlag(id,subflag);
            json.put("flag",subflag);
            if(infoContent.getSubflag().equals("5")){
                //发送消息通知
               /* sendMsg(sysNotice.getId());*/
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return json;
    }

}