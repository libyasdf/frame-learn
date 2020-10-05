package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.entity.ZbwyhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.service.ZbwyhService;
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
 * TODO 三会一课 支部委員会Controller
 * @Author: 李帅
 * @Date: 2018/8/23 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/zbwyh/")
public class ZbwyhController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZbwyhService zbwyhService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 支部委员会 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月23日
     * @param zbwyhEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存支部委员大会")
    @RequestMapping("saveZbwyh")
    @ResponseBody
    public JSONObject saveZbwyh( ZbwyhEntity zbwyhEntity) {
        JSONObject json = new JSONObject();
        try {
            zbwyhEntity = zbwyhService.saveZbwyh(zbwyhEntity);
            json.put("flag", 1);
            json.put("data", zbwyhEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 支部委员会 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @param zbwyhEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询支部委员会列表")
    @ResponseBody
    @RequestMapping("getZbwyhList")
    public PageImpl getZbwyhList(PageImpl pageImpl, ZbwyhEntity zbwyhEntity, DwxtOrg dwxtOrg) {
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
            if (StringUtils.isNotBlank(zbwyhEntity.getMeetingTime())) {
                startTime =zbwyhEntity.getMeetingTime().substring(0,(zbwyhEntity.getMeetingTime().length()+1)/2-1).trim();
                endTime =zbwyhEntity.getMeetingTime().substring((zbwyhEntity.getMeetingTime().length()+1)/2,zbwyhEntity.getMeetingTime().length()).trim();
            }
            PageImpl pageList = zbwyhService.getPageList(pageable, pageImpl, zbwyhEntity,startTime,endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除支部委员会 对应列表
     * @author 李帅
     * @Date 2018年8月24日
     * @param zbwyh
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除支部委员会对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(ZbwyhEntity zbwyh) {
        JSONObject json = new JSONObject();
        try {
            int n = zbwyhService.delete(zbwyh);
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
     * @Date 2018年8月27日
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
        ZbwyhEntity zbwyh = null;
        try {
            zbwyh = zbwyhService.getById(id);
            json.put("flag", "1");
            json.put("data", zbwyh);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


}
