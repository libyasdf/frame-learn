package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service.JtxxService;
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
 * TODO 三会一课 集体学习讨论Controller
 * @Author: 李帅
 * @Date: 2018/8/28 19:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/jtxx/")
public class JtxxController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JtxxService dkglService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 集体学习讨论 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dkglEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存集体学习讨论")
    @RequestMapping("saveJtxx")
    @ResponseBody
    public JSONObject saveJtxx( JtxxEntity dkglEntity) {
        JSONObject json = new JSONObject();
        try {
            dkglEntity = dkglService.saveJtxx(dkglEntity);
            json.put("flag", 1);
            json.put("data", dkglEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 集体学习讨论 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日 
     * @param pageImpl
     * @param dkglEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询集体学习讨论列表")
    @ResponseBody
    @RequestMapping("getJtxxList")
    public PageImpl getJtxxList(PageImpl pageImpl, JtxxEntity dkglEntity, DwxtOrg dwxtOrg) {
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
            if (StringUtils.isNotBlank(dkglEntity.getMeetingTime())) {
                startTime =dkglEntity.getMeetingTime().substring(0,(dkglEntity.getMeetingTime().length()+1)/2-1).trim();
                endTime =dkglEntity.getMeetingTime().substring((dkglEntity.getMeetingTime().length()+1)/2,dkglEntity.getMeetingTime().length()).trim();
            }
            PageImpl pageList = dkglService.getPageList(pageable, pageImpl, dkglEntity,startTime,endTime,ids);

            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除集体学习讨论 对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param jtxx
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除集体学习讨论对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(JtxxEntity jtxx) {
        JSONObject json = new JSONObject();
        try {
            int n = dkglService.delete(jtxx);
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
        JtxxEntity jtxx = null;
        try {
            jtxx = dkglService.getById(id);
            json.put("flag", "1");
            json.put("data", jtxx);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


}
