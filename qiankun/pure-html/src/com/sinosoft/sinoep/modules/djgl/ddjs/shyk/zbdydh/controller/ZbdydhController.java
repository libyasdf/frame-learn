package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity.HytjEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity.ZbdydhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
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
 * TODO 三会一课 支部党员大会Controller
 * @Author: 李帅
 * @Date: 2018/8/23 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/zbdydh/")
public class ZbdydhController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 支部党员大会 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月23日
     * @param zbdydhEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存支部党员大会")
    @RequestMapping("saveZbdydh")
    @ResponseBody
    public JSONObject saveZbdydh( ZbdydhEntity zbdydhEntity) {
        JSONObject json = new JSONObject();
        try {
            zbdydhEntity = zbdydhService.saveZbdydh(zbdydhEntity);
            json.put("flag", 1);
            json.put("data", zbdydhEntity);
        } catch (Exception e) {
         //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 支部党员大会 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @param zbdydhEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询支部党员大会列表")
    @ResponseBody
    @RequestMapping("getZbdydhList")
    public PageImpl getZbdydhList(PageImpl pageImpl, ZbdydhEntity zbdydhEntity, DwxtOrg dwxtOrg) {
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
            if (StringUtils.isNotBlank(zbdydhEntity.getMeetingTime())) {
                startTime =zbdydhEntity.getMeetingTime().substring(0,(zbdydhEntity.getMeetingTime().length()+1)/2-1).trim();
                endTime =zbdydhEntity.getMeetingTime().substring((zbdydhEntity.getMeetingTime().length()+1)/2,zbdydhEntity.getMeetingTime().length()).trim();
            }
            PageImpl pageList = zbdydhService.getPageList(pageable, pageImpl, zbdydhEntity,startTime,endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除支部党员大会 对应列表
     * @author 李帅
     * @Date 2018年8月24日
     * @param zbdydh
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除支部党员大会对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(ZbdydhEntity zbdydh) {
        JSONObject json = new JSONObject();
        try {
            int n = zbdydhService.delete(zbdydh);
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
        ZbdydhEntity zbdydh = null;
        try {
            zbdydh = zbdydhService.getById(id);
            json.put("flag", "1");
            json.put("data", zbdydh);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     *三会一课会议次数统计
     * TODO
     * @author 李帅
     * @Date 2018年9月7日
     * @return
     * */
    @LogAnnotation(value = "query",opName = "查询年度计划统计")
    @ResponseBody
    @RequestMapping("getHytjStatistics")
    public PageImpl getHytjStatistics(PageImpl pageImpl, HytjEntity ndjhTjEntity, DwxtOrg dwxtOrg,String tableName ,String OrgIdVal) {
        dwxtOrg.setId(OrgIdVal);
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
            String startTime = "";
            String endTime = "";
            if (StringUtils.isNotBlank(ndjhTjEntity.getAnnual())) {
                startTime = ndjhTjEntity.getAnnual().substring(0, (ndjhTjEntity.getAnnual().length() + 1) / 2 - 1).trim();
                endTime = ndjhTjEntity.getAnnual().substring((ndjhTjEntity.getAnnual().length() + 1) / 2, ndjhTjEntity.getAnnual().length()).trim();
            }
            PageImpl pageList = zbdydhService.hytjStatistics(pageable,pageImpl, tableName, startTime, endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }

    /**
     *根据组织id得到组织名称
     * TODO
     * @author 李帅
     * @Date 2018年9月28日
     * @return
     * */
    @LogAnnotation(value = "query",opName = "查询组织名称")
    @ResponseBody
    @RequestMapping("getOrgName")
    public JSONObject getOrgName(String id){
        JSONObject json = new JSONObject();
        try {
            List<Map<String,Object>> orgNameList =  zbdydhService.getOrgName(id);
            json.put("flag", 1);
            json.put("data", orgNameList);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }


}
