package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.entity.ZzshhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.service.ZzshhService;
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

import java.util.List;
import java.util.Map;

/**
 * TODO 三会一课 组织生活会Controller
 * @Author: 李帅
 * @Date: 2018/8/28 19:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/zzshh/")
public class ZzshhController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZzshhService zzshhService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 组织生活会 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param zzshhEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存组织生活会")
    @RequestMapping("saveZzshh")
    @ResponseBody
    public JSONObject saveZzshh( ZzshhEntity zzshhEntity) {
        JSONObject json = new JSONObject();
        try {
            zzshhEntity = zzshhService.saveZzshh(zzshhEntity);
            json.put("flag", 1);
            json.put("data", zzshhEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 组织生活会 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageImpl
     * @param zzshhEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询组织生活会列表")
    @ResponseBody
    @RequestMapping("getZzshhList")
    public PageImpl getZzshhList(PageImpl pageImpl, ZzshhEntity zzshhEntity, DwxtOrg dwxtOrg) {
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            String startTime ="";
            String endTime ="";
            if (StringUtils.isNotBlank(zzshhEntity.getMeetingTime())) {
                startTime =zzshhEntity.getMeetingTime().substring(0,(zzshhEntity.getMeetingTime().length()+1)/2-1).trim();
                endTime =zzshhEntity.getMeetingTime().substring((zzshhEntity.getMeetingTime().length()+1)/2,zzshhEntity.getMeetingTime().length()).trim();
            }
            PageImpl pageList = zzshhService.getPageList(pageable, pageImpl, zzshhEntity,startTime,endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除组织生活会 对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param zzshh
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除组织生活会对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(ZzshhEntity zzshh) {
        JSONObject json = new JSONObject();
        try {
            int n = zzshhService.delete(zzshh);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }


    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月28日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        ZzshhEntity zzshh = null;
        try {
            zzshh = zzshhService.getById(id);
            json.put("flag", "1");
            json.put("data", zzshh);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


}
