package com.sinosoft.sinoep.modules.info.xxfb.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoOfficeScope;
import com.sinosoft.sinoep.modules.info.xxfb.services.InfoOfficeScopeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/info/officeScope")
public class InfoOfficeScopeController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfoOfficeScopeService infoOfficeScopeService;


    /**
     * TODO 查询当前用户的信息发布处室范围
     * @author 杜建松
     * @Date 2018年9月10日 上午9:56:11
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询当前用户的信息发布处室范围")
    @RequestMapping("getInfoOfficeScope")
    @ResponseBody
    public JSONObject getInfoOfficeScope(){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        InfoOfficeScope entity = new InfoOfficeScope();
        try{
            entity = infoOfficeScopeService.getInfoOfficeScope();
            if(entity.getId()!= null){
                json.put("data",entity);
            }else{
                json.put("data","");
            }
            json.put("flag","1");
            json.put("msg","获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","获取失败!");
            log.error("获取失败！");
        }
        return json;
    }

    @LogAnnotation(value = "save",opName = "保存内容")
    @RequestMapping("saveFroms")
    @ResponseBody
    public JSONObject saveFroms(InfoOfficeScope entity){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try{
            entity = infoOfficeScopeService.saveFroms(entity);
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

    /**
     * TODO 查询信息发布处室范围
     * @author 杜建松
     * @Date 2018年9月10日 上午9:56:11
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询信息发布处室范围")
    @RequestMapping("findScopeOffice")
    @ResponseBody
    public JSONObject findScopeOffice(){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        InfoOfficeScope entity = new InfoOfficeScope();
        List<String> officeIdList = new ArrayList<String>();
        List<String> officeNameList = new ArrayList<String>();
        try{
            entity = infoOfficeScopeService.getInfoOfficeScope();
            String officeId = entity.getOfficeScopeId();
            String officeName = entity.getOfficeScopeName();
            String[] officeIdStr = officeId.split(",");
            String[] officeNameStr = officeName.split(",");
            for(int i=0;i<officeIdStr.length;i++){
                officeIdList.add(officeIdStr[i]);
                officeNameList.add(officeNameStr[i]);
            }
            json.put("officeIdList",officeIdList);
            json.put("officeNameList",officeNameList);
            json.put("flag","1");
            json.put("msg","获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","获取失败!");
            log.error("获取失败！");
        }
        return json;
    }
}
