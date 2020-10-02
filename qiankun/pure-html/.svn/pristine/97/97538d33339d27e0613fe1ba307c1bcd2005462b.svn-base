package com.sinosoft.sinoep.modules.system.config.msgVersion.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.modules.system.config.msgVersion.entity.MsgVersion;
import com.sinosoft.sinoep.modules.system.config.msgVersion.services.MsgVersionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 消息客户端版本管理控制器
 * @author 李利广
 * @Date 2019年03月08日 20:12:13
 */
@Controller()
@RequestMapping("/system/config/msgVersion")
public class MsgVersionController {

    private static Log log = LogFactory.getLog(MsgVersionController.class);

    @Autowired
    private MsgVersionService versionService;

    @Autowired
    private AffixService affixService;

    /**
     * TODO 根据版本号获取客户端更新信息，如果版本号为空，则获取最新客户端
     * @author 李利广
     * @Date 2019年03月08日 20:16:10
     * @param version 版本号（1.4.0）
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "query",opName = "检测、查询最新版本信息")
    @ResponseBody
    @RequestMapping("/getByVersion")
    public JSONObject getMsgByVersion(String version){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            MsgVersion msgVersion = versionService.getMsgByVersion(version);
            if (msgVersion != null){
                //查询附件ID
                JSONArray array = affixService.affixList("message_version",msgVersion.getId());
                if (!array.isEmpty()){
                    msgVersion.setAffixId(array.getJSONObject(0).getString("id"));
                    msgVersion.setAffixSize(array.getJSONObject(0).getString("fileSize"));
                    json.put("flag","1");
                    json.put("msg","查询成功");
                }else{
                    json.put("flag","2");
                    json.put("msg","客户端未上传，请联系管理员");
                }
                json.put("data",msgVersion);
            }else{
                json.put("msg","该版本号查询结果为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","查询客户端信息异常");
        }
        return json;
    }

    /**
     * TODO 获取最新版本号
     * @author 李利广
     * @Date 2019年03月08日 20:16:10
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "query",opName = "获取最新版本号")
    @ResponseBody
    @RequestMapping("/getNewVersion")
    public String getNewVersion(){
        String ver = "";
        try {
            MsgVersion msgVersion = versionService.getMsgByVersion("");
            if (msgVersion != null){
                ver = msgVersion.getVersion();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return ver;
    }

    /**
     * TODO 保存版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param msgVersion
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "save",opName = "保存版本信息")
    @ResponseBody
    @RequestMapping("/save")
    public JSONObject saveVersion(MsgVersion msgVersion){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            msgVersion = versionService.save(msgVersion);
            if (msgVersion != null){
                json.put("flag","1");
                json.put("msg","保存成功");
                json.put("data",msgVersion);
            }else{
                json.put("msg","保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","保存版本信息异常");
        }
        return json;
    }

    /**
     * TODO 删除版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param id
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "delete",opName = "删除版本信息")
    @ResponseBody
    @RequestMapping("/deleteVersion")
    public JSONObject deleteVersion(String id){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            if (StringUtils.isNotBlank(id)){
                boolean is = versionService.deleteVersion(id);
                if (is){
                    json.put("flag","1");
                    json.put("msg","删除成功");
                }else{
                    json.put("msg","保存失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","删除失败");
        }
        return json;
    }

    /**
     * TODO 查询版本信息列表
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param pageImpl
     * @param msgVersion
     * @param timeRange
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "query",opName = "查询版本信息列表")
    @ResponseBody
    @RequestMapping("/getVersionList")
    public PageImpl getVersionList(PageImpl pageImpl,MsgVersion msgVersion, String timeRange){
        try{
            String startDate = "";
            String endDate = "";
            if (StringUtils.isNotBlank(timeRange)) {
                startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
                endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
            }
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = versionService.getVersionList(pageable,pageImpl,msgVersion,startDate,endDate);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            pageImpl.setFlag("-1");
            pageImpl.setMsg("查询列表失败");
        }
        return pageImpl;
    }

    /**
     * TODO 根据ID查询一条数据
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param id
     * @return net.sf.json.JSONObject
     */
    @LogAnnotation(value = "query",opName = "根据ID查询一条版本信息")
    @ResponseBody
    @RequestMapping("/getById")
    public JSONObject getById(String id){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            if (StringUtils.isNotBlank(id)){
                MsgVersion version = versionService.getById(id);
                json.put("flag","1");
                json.put("msg","查询成功");
                json.put("data",version);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","删除失败");
        }
        return json;
    }
}
