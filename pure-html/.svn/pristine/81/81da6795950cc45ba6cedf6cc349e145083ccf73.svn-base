package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.entity.DxzhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.service.DxzhService;
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
 * TODO 三会一课 党小组会Controller
 * @Author: 李帅
 * @Date: 2018/8/28 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/dxzh/")
public class DxzhController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DxzhService dxzhService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 党小组会 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dxzhEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存支部委员大会")
    @RequestMapping("saveDxzh")
    @ResponseBody
    public JSONObject saveDxzh( DxzhEntity dxzhEntity) {
        JSONObject json = new JSONObject();
        try {
            dxzhEntity = dxzhService.saveDxzh(dxzhEntity);
            json.put("flag", 1);
            json.put("data", dxzhEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党小组会 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日 
     * @param pageImpl
     * @param dxzhEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党小组会列表")
    @ResponseBody
    @RequestMapping("getDxzhList")
    public PageImpl getDxzhList(PageImpl pageImpl, DxzhEntity dxzhEntity, DwxtOrg dwxtOrg) {
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
            if (StringUtils.isNotBlank(dxzhEntity.getMeetingTime())) {
                startTime =dxzhEntity.getMeetingTime().substring(0,(dxzhEntity.getMeetingTime().length()+1)/2-1).trim();
                endTime =dxzhEntity.getMeetingTime().substring((dxzhEntity.getMeetingTime().length()+1)/2,dxzhEntity.getMeetingTime().length()).trim();
            }
            PageImpl pageList = dxzhService.getPageList(pageable, pageImpl, dxzhEntity,startTime,endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除党小组会 对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param dxzh
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除党小组会对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(DxzhEntity dxzh) {
        JSONObject json = new JSONObject();
        try {
            int n = dxzhService.delete(dxzh);
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
        DxzhEntity dxzh = null;
        try {
            dxzh = dxzhService.getById(id);
            json.put("flag", "1");
            json.put("data", dxzh);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


}
